package ru.otus.vsh.hw03.runner;

import com.google.common.collect.Maps;
import ru.otus.vsh.hw03.annotations.After;
import ru.otus.vsh.hw03.annotations.Before;
import ru.otus.vsh.hw03.annotations.Test;
import ru.otus.vsh.hw03.annotations.Times;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * один экземпляр отвечает за запуск одного тестового класса.
 */
public class Runner {

    private final Class<?> aClass;

    private final List<Method> beforeMethods;
    private final List<Method> afterMethods;
    private final List<TestInfo> testMethods;

    private final Map<TestStatus, Integer> result;

    /**
     * @param aClass - Class тестового класса
     */
    private Runner(Class<?> aClass) {
        this.aClass = aClass;
        beforeMethods = getAnnotatedMethods(Before.class);
        afterMethods = getAnnotatedMethods(After.class);
        testMethods = getAnnotatedMethods(Test.class).stream().map(TestInfo::new).collect(Collectors.toList());

        result = Maps.newEnumMap(TestStatus.class);
        resetResults();
    }

    /**
     * @param className - FQN тестового класса
     */
    @Nonnull
    public static Runner get(String className) throws ClassNotFoundException {
        Class<?> aClass = Class.forName(className);
        return new Runner(aClass);
    }

    /**
     * @return статистику: сколько всего было запусков (с учетов повторений от аннотации [Times])
     * и сколько из них в каждом поддерживаемом статусе (пока только Success и Failed)
     */
    public Map<TestStatus, Integer> getResult() {
        return Maps.immutableEnumMap(result);
    }

    /**
     * Запуск одного тестового метода.
     * Здесь есть 2 дополнительных момента:
     * - Во-первых, один запуск тестового метода состоит из трех этапов:
     * -- запуск в произвольном порядке всех методов, помеченных аннотацией [Before];
     * -- запуск собственно тестового метода – метода, помеченного аннотацией [Test];
     * -- запуск в произвольном порядке всех методов, помеченных аннотацией [After].
     * - Во-вторых, каждый набор этапов для каждого тестового метода запускается столько раз
     * сколько указано в аннотации [Times].
     * Если эта аннотация не задана – то запуск происходит только 1 раз.
     */
    public void run() {
        if (testMethods.isEmpty())
            throw new IllegalStateException("Test class does not contain any test method (with annotation @Test)");

        resetResults();

        for (TestInfo data : testMethods) {
            Map<TestStatus, Integer> result = executeTestSet(data);
            for (TestStatus status : TestStatus.values()) {
                this.result.put(status, this.result.get(status) + result.get(status));
            }
        }
    }

    @Nonnull
    private List<Method> getAnnotatedMethods(Class<? extends Annotation> annotation) {
        Method[] all = aClass.getDeclaredMethods();
        ArrayList<Method> result = new ArrayList<>(all.length);
        for (Method method : all) {
            if (method.isAnnotationPresent(annotation)) {
                result.add(method);
            }
        }
        return result;
    }

    private Map<TestStatus, Integer> executeTestSet(TestInfo info) {
        Object testObject;
        int passed = 0;
        int failed = 0;
        try {
            testObject = aClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalStateException("Error on creating an object of type " + aClass.getSimpleName(), e);
        }
        for (int i = 0; i < info.times; i++) {
            System.out.println("Running: '" + info.name + "'. Run #" + (i + 1) + "......");
            boolean hasPassed = false;
            try {
                for (Method method : beforeMethods) {
                    method.setAccessible(true);
                    method.invoke(testObject);
                }
                info.testMethod.setAccessible(true);
                info.testMethod.invoke(testObject);
                hasPassed = true;
            } catch (Throwable e) {
                e.printStackTrace();
            } finally {
                try {
                    for (Method method : afterMethods) {
                        method.setAccessible(true);
                        method.invoke(testObject);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    hasPassed = false;
                }
            }
            if (hasPassed) passed++;
            else failed++;
        }
        return Map.of(TestStatus.SUCCESS, passed, TestStatus.FAILED, failed);
    }

    private void resetResults() {
        for (TestStatus status : TestStatus.values()) {
            result.put(status, 0);
        }
    }

    /**
     * Класс [TestInfo] предназначен для хранения дополнительной
     * информации для каждого тестового метода:
     * [testMethod] - сам тестовый метод
     * [name] - читаемое имя – задается параметром [value] аннотации [Test]
     * [times] - количество повторений – задается аннотацией [Times]
     */
    private static class TestInfo {
        private final Method testMethod;
        private final int times;
        private final String name;

        public TestInfo(Method testMethod) {
            this.testMethod = testMethod;
            String annotationValue = this.testMethod.getAnnotation(Test.class).value();
            if (annotationValue.isEmpty()) {
                this.name = this.testMethod.getName();
            } else this.name = annotationValue;
            if (this.testMethod.isAnnotationPresent(Times.class)) {
                this.times = this.testMethod.getAnnotation(Times.class).value();
            } else {
                this.times = 1;
            }
        }
    }
}

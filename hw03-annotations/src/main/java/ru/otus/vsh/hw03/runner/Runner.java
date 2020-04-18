package ru.otus.vsh.hw03.runner;

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

public class Runner {
    public enum Status {SUCCESS, FAILED}

    private final Class<?> aClass;

    private Runner(String className) throws ClassNotFoundException {
        aClass = Class.forName(className);
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

    @Nonnull
    private List<Method> getBeforeMethods() {
        return getAnnotatedMethods(Before.class);
    }

    @Nonnull
    private List<Method> getAfterMethods() {
        return getAnnotatedMethods(After.class);
    }

    @Nonnull
    private List<Method> getTestMethods() {
        return getAnnotatedMethods(Test.class);
    }

    private static class TestExecutionData {
        private final Method testMethod;
        private final List<Method> beforeMethods;
        private final List<Method> afterMethods;
        private final int times;
        private final String name;

        public TestExecutionData(Method testMethod,
                                 List<Method> beforeMethods,
                                 List<Method> afterMethods) {
            this.testMethod = testMethod;
            String annotationValue = this.testMethod.getAnnotation(Test.class).value();
            if (annotationValue.isEmpty()) {
                this.name = this.testMethod.getName();
            } else this.name = annotationValue;
            this.beforeMethods = beforeMethods;
            this.afterMethods = afterMethods;
            if (this.testMethod.isAnnotationPresent(Times.class)) {
                this.times = this.testMethod.getAnnotation(Times.class).value();
            } else {
                this.times = 1;
            }
        }
    }

    @Nonnull
    public static Map<Status, Integer> run(String className) throws ClassNotFoundException {
        Runner runner = new Runner(className);

        List<Method> beforeMethods = runner.getBeforeMethods();
        List<Method> afterMethods = runner.getAfterMethods();
        List<Method> testMethods = runner.getTestMethods();

        if (testMethods.isEmpty())
            throw new IllegalStateException("Test class does not contain any test method (with annotation @Test)");

        List<TestExecutionData> executionData = new ArrayList<>(testMethods.size());
        testMethods.forEach((method) -> executionData.add(new TestExecutionData(method, beforeMethods, afterMethods)));

        int passed = 0;
        int failed = 0;
        for (TestExecutionData data : executionData) {
            Map<Status, Integer> result = runner.executeTest(data);
            passed += result.get(Status.SUCCESS);
            failed += result.get(Status.FAILED);
        }
        return Map.of(Status.SUCCESS, passed, Status.FAILED, failed);
    }

    private Map<Status, Integer> executeTest(TestExecutionData data) {
        Object testObject;
        int passed = 0;
        int failed = 0;
        try {
            testObject = aClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalStateException("Error on creating an object of type " + aClass.getSimpleName(), e);
        }
        for (int i = 0; i < data.times; i++) {
            System.out.println("Running: '" + data.name + "'. Run #" + (i + 1) + "......");
            boolean hasPassed = false;
            try {
                for (Method method : data.beforeMethods) {
                    method.setAccessible(true);
                    method.invoke(testObject);
                }
                data.testMethod.setAccessible(true);
                data.testMethod.invoke(testObject);
                hasPassed = true;
            } catch (Throwable e) {
                e.printStackTrace();
            } finally {
                try {
                    for (Method method : data.afterMethods) {
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
        return Map.of(Status.SUCCESS, passed, Status.FAILED, failed);
    }
}

package ru.otus.vsh.hw13.appcontainer;

import ru.otus.vsh.hw13.appcontainer.api.AppComponent;
import ru.otus.vsh.hw13.appcontainer.api.AppComponentsContainer;
import ru.otus.vsh.hw13.appcontainer.api.AppComponentsContainerConfig;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(@Nonnull Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    public AppComponentsContainerImpl(@Nonnull Class<?>... initialConfigClasses) {
        processConfig(initialConfigClasses);
    }

    private void processConfig(@Nonnull Class<?>... configClasses) {
        checkConfigClass(configClasses);
        var structures = Arrays.stream(configClasses)
                .map(this::fillUpStructure)
                .flatMap(Collection::stream)
                .sorted()
                .collect(Collectors.toList());

        structures.forEach(cs -> {
            var paramObjectList = cs
                    .getParamList(structures).stream()
                    .map(appComponentsByName::get)
                    .collect(Collectors.toList());
            var object = cs.createComponent(paramObjectList);
            appComponents.add(object);
            appComponentsByName.put(cs.getName(), object);
        });

    }

    private List<ComponentStructure> fillUpStructure(@Nonnull Class<?> configClass) {
        try {
            var configClassObject = configClass.getConstructor().newInstance();
            return Arrays.stream(configClass.getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(AppComponent.class))
                    .map(method -> {
                        var compAnnotation = method.getAnnotation(AppComponent.class);
                        return new ComponentStructure(
                                compAnnotation.order(),
                                compAnnotation.name(),
                                method.getReturnType().getName(),
                                configClassObject,
                                method
                        );
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new IllegalStateException(String.format("'%s' either does not have a default constructor or cannot create instance by it", configClass.getName()));
        }
    }

    private void checkConfigClass(@Nonnull Class<?>... configClasses) {
        var incorrectConfigs = Arrays.stream(configClasses)
                .filter(config -> !config.isAnnotationPresent(AppComponentsContainerConfig.class))
                .map(Class::getName)
                .collect(Collectors.toUnmodifiableList())
                .toArray();

        if (incorrectConfigs.length > 0) {
            throw new IllegalArgumentException(String.format("Given classes are not config %s", Arrays.toString(incorrectConfigs)));
        }
    }

    @Override
    public <C> C getAppComponent(@Nonnull Class<C> componentClass) {
        return componentClass.cast(
                appComponents.stream()
                        .filter(c -> {
                            try {
                                componentClass.cast(c);
                                return true;
                            } catch (ClassCastException cannotBeCast) {
                                return false;
                            }
                        })
                        .findFirst()
                        .orElseThrow()
        );
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return (C) appComponentsByName.get(componentName);
    }
}

package ru.otus.vsh.hw13.appcontainer;

import ru.otus.vsh.hw13.appcontainer.api.AppComponent;
import ru.otus.vsh.hw13.appcontainer.api.AppComponentsContainer;
import ru.otus.vsh.hw13.appcontainer.api.AppComponentsContainerConfig;
import ru.otus.vsh.hw13.config.AppConfig;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        var structures = Arrays.stream(configClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(AppComponent.class))
                .map(method -> {
                    var compAnnotation = method.getAnnotation(AppComponent.class);
                    return new ComponentStructure(
                            compAnnotation.order(),
                            compAnnotation.name(),
                            method.getReturnType().getName(),
                            method
                    );
                })
                .sorted()
                .collect(Collectors.toList());

        // create object of AppConfig
        var appConfigObject = new AppConfig();

        structures.forEach(cs -> {
            if (cs.getOrder() == 0) {
                try {
                    var object = cs.getCreator().invoke(appConfigObject, null);
                    appComponents.add(object);
                    appComponentsByName.put(cs.getName(), object);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    var params = cs.getCreator().getParameterTypes();
                    var paramObjects = new ArrayList<>();
                    for (var param : params) {
                        var paramObjectName = structures.stream()
                                .filter(csp -> csp.getComponentClassName().equals(param.getName()))
                                .findFirst()
                                .orElseThrow(RuntimeException::new)
                                .getName();
                        paramObjects.add(appComponentsByName.get(paramObjectName));
                    }
                    var object = cs.getCreator().invoke(appConfigObject, paramObjects.toArray());
                    appComponents.add(object);
                    appComponentsByName.put(cs.getName(), object);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
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

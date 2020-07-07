package ru.otus.vsh.hw13.appcontainer;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class ComponentStructure implements Comparable<ComponentStructure> {
    private final int order;
    private final String name;
    private final String componentClassName;
    private final Object configObject;
    private final Method creator;

    public ComponentStructure(int order,
                              @Nonnull String name,
                              @Nonnull String componentClassName,
                              @Nonnull Object configObject,
                              @Nonnull Method creator) {
        this.order = order;
        this.name = name;
        this.componentClassName = componentClassName;
        this.configObject = configObject;
        this.creator = creator;
    }

    public int getOrder() {
        return order;
    }

    public String getName() {
        return name;
    }

    public String getComponentClassName() {
        return componentClassName;
    }

    public Method getCreator() {
        return creator;
    }

    public Object getConfigObject() {
        return configObject;
    }

    @Nonnull
    public List<String> getParamList(@Nonnull List<ComponentStructure> other) {
        if (order == 0) {
            return Collections.emptyList();
        } else {
            var params = creator.getParameterTypes();
            return Arrays.stream(params)
                    .map(param -> other.stream()
                            .filter(csp -> csp.getComponentClassName().equals(param.getName()))
                            .findFirst()
                            .orElseThrow(RuntimeException::new)
                            .getName())
                    .collect(Collectors.toList());
        }
    }

    @Nonnull
    public Object createComponent(@Nonnull List<Object> paramList) {
        try {
            return creator.invoke(configObject, paramList.isEmpty() ? null : paramList.toArray());
        } catch (Exception e) {
            throw new RuntimeException(String.format("Error during creating a component '%s'", name));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComponentStructure that = (ComponentStructure) o;
        return order == that.order &&
                name.equals(that.name) &&
                componentClassName.equals(that.componentClassName) &&
                configObject.equals(that.configObject) &&
                creator.equals(that.creator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, name, componentClassName, configObject, creator);
    }

    @Override
    public int compareTo(@Nonnull ComponentStructure o) {
        if (order > o.getOrder()) return 1;
        else if (order < o.getOrder()) return -1;
        else return Objects.compare(name, o.name, String::compareTo);
    }
}

package ru.otus.vsh.hw13.appcontainer;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;
import java.util.Objects;

public class ComponentStructure implements Comparable<ComponentStructure> {
    private final int order;
    private final String name;
    private final String componentClassName;
    private final Method creator;

    public ComponentStructure(int order,
                              @Nonnull String name,
                              @Nonnull String componentClassName,
                              @Nonnull Method creator) {
        this.order = order;
        this.name = name;
        this.componentClassName = componentClassName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComponentStructure that = (ComponentStructure) o;
        return order == that.order &&
                name.equals(that.name) &&
                componentClassName.equals(that.componentClassName) &&
                creator.equals(that.creator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, name, componentClassName, creator);
    }

    @Override
    public int compareTo(@Nonnull ComponentStructure o) {
        if (order > o.getOrder()) return 1;
        else if (order < o.getOrder()) return -1;
        else return Objects.compare(name, o.name, String::compareTo);
    }
}

package ru.otus.vsh.hw08.json.classes.collections;

import ru.otus.vsh.hw08.json.classes.Colors;

import java.util.Objects;
import java.util.Set;

public class CollectionOfEnumFields {
    Set<Colors> colors;

    public CollectionOfEnumFields(Set<Colors> colors) {
        this.colors = colors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CollectionOfEnumFields that = (CollectionOfEnumFields) o;
        return colors.equals(that.colors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(colors);
    }
}

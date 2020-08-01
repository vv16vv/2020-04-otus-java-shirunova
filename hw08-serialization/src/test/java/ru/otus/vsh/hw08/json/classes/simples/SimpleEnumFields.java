package ru.otus.vsh.hw08.json.classes.simples;

import ru.otus.vsh.hw08.json.classes.Colors;

import java.util.Objects;

public class SimpleEnumFields {
    Colors colors;

    public SimpleEnumFields(Colors colors) {
        this.colors = colors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleEnumFields that = (SimpleEnumFields) o;
        return colors == that.colors;
    }

    @Override
    public int hashCode() {
        return Objects.hash(colors);
    }
}

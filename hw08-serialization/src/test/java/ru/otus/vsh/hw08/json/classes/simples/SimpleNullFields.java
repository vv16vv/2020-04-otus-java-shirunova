package ru.otus.vsh.hw08.json.classes.simples;

import java.util.Objects;

public class SimpleNullFields {
    String nullableString;

    public SimpleNullFields(String nullableString) {
        this.nullableString = nullableString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleNullFields that = (SimpleNullFields) o;
        return Objects.equals(nullableString, that.nullableString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nullableString);
    }
}

package ru.otus.vsh.hw08.json.classes.simples;

import java.util.Objects;

public class SimpleObjectFields {
    SimpleObject object;

    public SimpleObjectFields(SimpleObject object) {
        this.object = object;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleObjectFields that = (SimpleObjectFields) o;
        return object.equals(that.object);
    }

    @Override
    public int hashCode() {
        return Objects.hash(object);
    }
}

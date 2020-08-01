package ru.otus.vsh.hw08.json.classes.simples;

import java.util.Objects;

public class SimpleObject {
    int x;
    String description;

    public SimpleObject(int x, String description) {
        this.x = x;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleObject that = (SimpleObject) o;
        return x == that.x &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, description);
    }
}

package ru.otus.vsh.hw08.json.classes;

import ru.otus.vsh.hw08.json.classes.simples.SimpleObject;

import java.util.Objects;

public class WithInheritance extends SimpleObject {
    private long aLong;

    public WithInheritance(int x, String description, long aLong) {
        super(x, description);
        this.aLong = aLong;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        WithInheritance that = (WithInheritance) o;
        return aLong == that.aLong;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), aLong);
    }
}

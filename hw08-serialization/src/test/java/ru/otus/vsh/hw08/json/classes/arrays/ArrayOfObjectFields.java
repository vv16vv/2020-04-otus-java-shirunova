package ru.otus.vsh.hw08.json.classes.arrays;

import ru.otus.vsh.hw08.json.classes.simples.SimpleObject;

import java.util.Arrays;

public class ArrayOfObjectFields {
    SimpleObject[] aObjects;

    public ArrayOfObjectFields(SimpleObject[] aObjects) {
        this.aObjects = aObjects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArrayOfObjectFields that = (ArrayOfObjectFields) o;
        return Arrays.equals(aObjects, that.aObjects);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(aObjects);
    }
}


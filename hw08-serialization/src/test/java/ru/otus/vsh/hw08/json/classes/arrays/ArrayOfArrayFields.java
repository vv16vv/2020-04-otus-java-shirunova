package ru.otus.vsh.hw08.json.classes.arrays;

import ru.otus.vsh.hw08.json.classes.simples.SimpleObject;

import java.util.Arrays;

public class ArrayOfArrayFields {
    int[][] aIntInts;
    Long[][] aLongLongs;
    SimpleObject[][] aSimpleSimpleObjects;

    public ArrayOfArrayFields(int[][] aIntInts, Long[][] aLongLongs, SimpleObject[][] aSimpleSimpleObjects) {
        this.aIntInts = aIntInts;
        this.aLongLongs = aLongLongs;
        this.aSimpleSimpleObjects = aSimpleSimpleObjects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArrayOfArrayFields that = (ArrayOfArrayFields) o;
        return Arrays.deepEquals(aIntInts, that.aIntInts) &&
                Arrays.deepEquals(aLongLongs, that.aLongLongs) &&
                Arrays.deepEquals(aSimpleSimpleObjects, that.aSimpleSimpleObjects);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(aIntInts);
        result = 31 * result + Arrays.hashCode(aLongLongs);
        result = 31 * result + Arrays.hashCode(aSimpleSimpleObjects);
        return result;
    }
}

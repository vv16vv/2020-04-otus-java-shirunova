package ru.otus.vsh.hw08.json.classes.arrays;

import java.util.Arrays;
import java.util.List;

public class ArrayOfCollectionFields {
    List<String>[] aLists;

    public ArrayOfCollectionFields(List<String>[] aLists) {
        this.aLists = aLists;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArrayOfCollectionFields that = (ArrayOfCollectionFields) o;
        return Arrays.equals(aLists, that.aLists);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(aLists);
    }
}

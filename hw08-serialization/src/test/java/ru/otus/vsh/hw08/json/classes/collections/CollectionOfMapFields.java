package ru.otus.vsh.hw08.json.classes.collections;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CollectionOfMapFields {
    List<Map<String, Integer>> aStringIntegerMapList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CollectionOfMapFields that = (CollectionOfMapFields) o;
        return aStringIntegerMapList.equals(that.aStringIntegerMapList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aStringIntegerMapList);
    }

    public CollectionOfMapFields(List<Map<String, Integer>> aStringIntegerMapList) {
        this.aStringIntegerMapList = aStringIntegerMapList;
    }
}

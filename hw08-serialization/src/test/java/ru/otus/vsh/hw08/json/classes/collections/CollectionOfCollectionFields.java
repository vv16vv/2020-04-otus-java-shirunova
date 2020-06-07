package ru.otus.vsh.hw08.json.classes.collections;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class CollectionOfCollectionFields {
    List<Set<Integer>> aIntegerSetList;

    public CollectionOfCollectionFields(List<Set<Integer>> aIntegerSetList) {
        this.aIntegerSetList = aIntegerSetList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CollectionOfCollectionFields that = (CollectionOfCollectionFields) o;
        return aIntegerSetList.equals(that.aIntegerSetList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aIntegerSetList);
    }
}

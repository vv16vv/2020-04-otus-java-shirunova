package ru.otus.vsh.hw08.json.classes.collections;

import java.util.*;

public class CollectionOfWrapperFields {
    Collection<Integer> aIntCollection;
    Set<String> aStringSet;
    ArrayList<Double> aDoubleList;

    public CollectionOfWrapperFields(Collection<Integer> aIntCollection, Set<String> aStringSet, ArrayList<Double> aDoubleList) {
        this.aIntCollection = aIntCollection;
        this.aStringSet = aStringSet;
        this.aDoubleList = aDoubleList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CollectionOfWrapperFields that = (CollectionOfWrapperFields) o;
        return aIntCollection.equals(that.aIntCollection) &&
                aStringSet.equals(that.aStringSet) &&
                aDoubleList.equals(that.aDoubleList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aIntCollection, aStringSet, aDoubleList);
    }
}

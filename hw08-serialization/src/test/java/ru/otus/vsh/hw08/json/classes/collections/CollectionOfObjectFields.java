package ru.otus.vsh.hw08.json.classes.collections;

import ru.otus.vsh.hw08.json.classes.simples.SimpleObject;

import java.util.Arrays;
import java.util.Deque;
import java.util.Objects;

public class CollectionOfObjectFields {
    Deque<SimpleObject> aSimpleObjectList;

    public CollectionOfObjectFields(Deque<SimpleObject> aSimpleObjectList) {
        this.aSimpleObjectList = aSimpleObjectList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CollectionOfObjectFields that = (CollectionOfObjectFields) o;
        return Arrays.equals(aSimpleObjectList.toArray(), that.aSimpleObjectList.toArray());
    }

    @Override
    public int hashCode() {
        return Objects.hash(aSimpleObjectList);
    }
}

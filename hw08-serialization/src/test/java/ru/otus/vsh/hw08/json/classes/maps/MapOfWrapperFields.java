package ru.otus.vsh.hw08.json.classes.maps;

import java.util.Map;
import java.util.Objects;

public class MapOfWrapperFields {
    Map<String, Integer> aStringIntegerMap;

    public MapOfWrapperFields(Map<String, Integer> aStringIntegerMap) {
        this.aStringIntegerMap = aStringIntegerMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapOfWrapperFields that = (MapOfWrapperFields) o;
        return aStringIntegerMap.equals(that.aStringIntegerMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aStringIntegerMap);
    }
}

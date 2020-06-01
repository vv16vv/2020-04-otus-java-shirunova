package ru.otus.vsh.hw08.json.classes.maps;

import ru.otus.vsh.hw08.json.classes.Colors;
import ru.otus.vsh.hw08.json.classes.simples.SimpleObject;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MapFields {
    Map<String, Integer> aStringIntegerMap;
    HashMap<Byte, SimpleObject> aByteObjectMap;
    EnumMap<Colors, String> aEnumStringMap;

    public MapFields(Map<String, Integer> aStringIntegerMap, HashMap<Byte, SimpleObject> aByteObjectMap, EnumMap<Colors, String> aEnumStringMap) {
        this.aStringIntegerMap = aStringIntegerMap;
        this.aByteObjectMap = aByteObjectMap;
        this.aEnumStringMap = aEnumStringMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapFields mapFields = (MapFields) o;
        return aStringIntegerMap.equals(mapFields.aStringIntegerMap) &&
                aByteObjectMap.equals(mapFields.aByteObjectMap) &&
                aEnumStringMap.equals(mapFields.aEnumStringMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aStringIntegerMap, aByteObjectMap, aEnumStringMap);
    }
}

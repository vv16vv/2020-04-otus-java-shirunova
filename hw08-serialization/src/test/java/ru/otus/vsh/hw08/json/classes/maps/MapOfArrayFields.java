package ru.otus.vsh.hw08.json.classes.maps;

import java.util.Map;

public class MapOfArrayFields {
    Map<String, Integer[]> aStringIntegersMap;
    Map<String[], Integer> aStringsIntegerMap;

    public MapOfArrayFields(Map<String, Integer[]> aStringIntegersMap, Map<String[], Integer> aStringsIntegerMap) {
        this.aStringIntegersMap = aStringIntegersMap;
        this.aStringsIntegerMap = aStringsIntegerMap;
    }
}

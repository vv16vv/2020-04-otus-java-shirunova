package ru.otus.vsh.hw08.json;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.vsh.hw08.json.classes.Colors;
import ru.otus.vsh.hw08.json.classes.maps.MapFields;
import ru.otus.vsh.hw08.json.classes.maps.MapOfArrayFields;
import ru.otus.vsh.hw08.json.classes.maps.MapOfWrapperFields;
import ru.otus.vsh.hw08.json.classes.simples.SimpleObject;

import java.util.EnumMap;
import java.util.HashMap;

class DiyJsonMapTest {
    private Gson gson;

    Object test(Object o, Class<?> type) {
        try {
            var modelJson = gson.toJson(o);
            System.out.println("modelJson = " + modelJson);
            var json = DiyJson.toJson(o);
            System.out.println("DIY json = " + json);
            return gson.fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @BeforeEach
    void before() {
        gson = new Gson();
    }

    @Test
    void mapFields() {
        var aStringIntegerMap = new HashMap<String, Integer>();
        aStringIntegerMap.put("first", 1);
        aStringIntegerMap.put("second", 2);
        var aByteObjectMap = new HashMap<Byte, SimpleObject>();
        var object1 = new SimpleObject(1, "one");
        var object2 = new SimpleObject(2, "two");
        aByteObjectMap.put((byte) 100, object1);
        aByteObjectMap.put((byte) 200, object2);
        var aEnumObjectMap = new EnumMap<Colors, String>(Colors.class);
        aEnumObjectMap.put(Colors.Red, "rojo");
        aEnumObjectMap.put(Colors.Yellow, "amarillo");
        aEnumObjectMap.put(Colors.Green, "verde");
        var object = new MapFields(
                aStringIntegerMap,
                aByteObjectMap,
                aEnumObjectMap
        );
        var converted = test(object, MapFields.class);
        assert object.equals(converted);
    }

    @Test
    void mapOfWrapperFields() {
        var aStringIntegerMap = new HashMap<String, Integer>();
        aStringIntegerMap.put("first", 1);
        aStringIntegerMap.put("second", 2);
        var object = new MapOfWrapperFields(
                aStringIntegerMap
        );
        var converted = test(object, MapOfWrapperFields.class);
        assert object.equals(converted);
    }

    @Test
    void mapOfArrayFields() {
        var aInts1 = new Integer[]{1, 3};
        var aInts2 = new Integer[]{2, 4};
        var aStringIntegersMap = new HashMap<String, Integer[]>();
        aStringIntegersMap.put("odd", aInts1);
        aStringIntegersMap.put("even", aInts2);
        var aStrings1 = new String[]{"A", "E", "I", "O", "U"};
        var aStrings2 = new String[]{"B", "C", "D", "F", "G"};
        var aStringsIntegerMap = new HashMap<String[], Integer>();
        aStringsIntegerMap.put(aStrings1, 5);
        aStringsIntegerMap.put(aStrings2, 21);
        var object = new MapOfArrayFields(
                aStringIntegersMap,
                aStringsIntegerMap
        );
        var converted = test(object, MapOfArrayFields.class);
        assert object.equals(converted);
    }

}
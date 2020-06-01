package ru.otus.vsh.hw08.json;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.vsh.hw08.json.classes.Colors;
import ru.otus.vsh.hw08.json.classes.arrays.*;
import ru.otus.vsh.hw08.json.classes.simples.SimpleObject;

import java.util.ArrayList;
import java.util.List;

class DiyJsonArraysTest {
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
    void arrayOfPrimitiveFields() {
        var object = new ArrayOfPrimitiveFields(
                new int[]{1, 2},
                new byte[]{(byte) 2},
                new long[]{3L},
                new short[]{(short) 4},
                new boolean[]{true, false, true},
                new float[]{5.6f, -1.45f, 0.0f},
                new double[]{7.8},
                new char[]{'9', '$'});
        var converted = test(object, ArrayOfPrimitiveFields.class);
        assert object.equals(converted);
    }

    @Test
    void arrayOfWrapperFields() {
        var object = new ArrayOfWrapperFields(
                new Integer[]{1, 2},
                new Byte[]{(byte) 2},
                new Long[]{3L},
                new Short[]{(short) 4, null, (short) 5},
                new Boolean[]{true, false, true},
                new Float[]{5.6f, -1.45f, 0.0f},
                new Double[]{7.8},
                new Character[]{'9', '$'},
                new String[]{"hello", "world"});
        var converted = test(object, ArrayOfWrapperFields.class);
        assert object.equals(converted);
    }

    @Test
    void arrayOfEnumFields() {
        var aEnumArray = new Colors[]{Colors.Green};
        var object = new ArrayOfEnumFields(aEnumArray);
        var converted = test(object, ArrayOfEnumFields.class);
        assert object.equals(converted);
    }

    @Test
    void arrayOfObjectFields() {
        var object0 = new SimpleObject(0, null);
        var object1 = new SimpleObject(1, "one");
        var object2 = new SimpleObject(2, "two");
        var aObjects = new SimpleObject[]{object0, object1, object2};
        var object = new ArrayOfObjectFields(aObjects);
        var converted = test(object, ArrayOfObjectFields.class);
        assert object.equals(converted);
    }

    @Test
    void arrayOfArrayFields() {
        var aIntInts = new int[][]{{1, 2, 3}, {4, 5, 6}};
        var aLongLongs = new Long[][]{{2L, 3L, 4L, null}};
        var object1 = new SimpleObject(1, "one");
        var object2 = new SimpleObject(2, "two");
        var aObjects = new SimpleObject[][]{{object1}, {object2}};
        var object = new ArrayOfArrayFields(
                aIntInts,
                aLongLongs,
                aObjects);
        var converted = test(object, ArrayOfArrayFields.class);
        assert object.equals(converted);
    }

    @SuppressWarnings({"RedundantCast", "unchecked"})
    @Test
    void arrayOfCollectionFields() {
        var aStringList1 = new ArrayList<String>();
        aStringList1.add("Hello");
        aStringList1.add(null);
        var aStringList2 = new ArrayList<String>();
        aStringList2.add("Good bye");
        var aStringLists = new List[]{aStringList1, aStringList2};
        var object = new ArrayOfCollectionFields(
                (List<String>[])aStringLists
        );
        var converted = test(object, ArrayOfCollectionFields.class);
        assert object.equals(converted);
    }
}
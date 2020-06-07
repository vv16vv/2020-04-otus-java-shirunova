package ru.otus.vsh.hw08.json;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.vsh.hw08.json.classes.Colors;
import ru.otus.vsh.hw08.json.classes.simples.*;

class DiyJsonTest {
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
    void simplePrimitiveFields() {
        var object = new SimplePrimitiveFields(
                1,
                (byte) 2,
                3L,
                (short) 4,
                true,
                5.6f,
                7.8,
                '9');
        var converted = test(object, SimplePrimitiveFields.class);
        assert object.equals(converted);
    }

    @Test
    void simpleWrapperFields() {
        var object = new SimpleWrapperFields(
                1,
                (byte) 2,
                3L,
                (short) 4,
                Boolean.TRUE,
                5.6f,
                7.8,
                '9',
                "new string");
        var converted = test(object, SimpleWrapperFields.class);
        assert object.equals(converted);
    }

    @Test
    void simpleObjectFields() {
        var object = new SimpleObjectFields(new SimpleObject(1, "one"));
        var converted = test(object, SimpleObjectFields.class);
        assert object.equals(converted);
    }

    @Test
    void simpleEnumFields() {
        var object = new SimpleEnumFields(Colors.Red);
        var converted = test(object, SimpleEnumFields.class);
        assert object.equals(converted);
    }

    @Test
    void simpleNullFields() {
        var object = new SimpleNullFields(null);
        var converted = test(object, SimpleNullFields.class);
        assert object.equals(converted);
    }
}
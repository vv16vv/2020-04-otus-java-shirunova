package ru.otus.vsh.hw08.json;

import ru.otus.vsh.hw08.json.types.ObjectType;

import javax.annotation.Nonnull;
import javax.json.Json;

public class DiyJson {
    @Nonnull
    public static String toJson(@Nonnull Object o) throws IllegalAccessException, IllegalArgumentException {
        var result = Json.createObjectBuilder();
        var objectType = new ObjectType(o.getClass(), o);
        objectType.addAsObjectValue(result);
        return result.build().toString();
    }
}

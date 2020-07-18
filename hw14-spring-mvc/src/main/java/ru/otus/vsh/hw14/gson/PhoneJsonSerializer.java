package ru.otus.vsh.hw14.gson;

import com.google.gson.*;
import ru.otus.vsh.hw14.dbCore.model.Phone;

import java.lang.reflect.Type;

public class PhoneJsonSerializer implements JsonSerializer<Phone> {
    @Override
    public JsonElement serialize(Phone src, Type typeOfSrc, JsonSerializationContext context) {
        var json = new JsonObject();
        json.add("id", new JsonPrimitive(src.getId()));
        json.add("phone number", new JsonPrimitive(src.getNumber()));
        json.add("user", new JsonPrimitive(src.getUser().getName()));
        return json;
    }
}

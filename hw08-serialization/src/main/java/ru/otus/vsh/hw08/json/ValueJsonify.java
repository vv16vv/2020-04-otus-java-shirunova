package ru.otus.vsh.hw08.json;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

public interface ValueJsonify {
    void addNamedObjectValue(JsonObjectBuilder builder, String name);

    void addArrayValue(JsonArrayBuilder builder);
}



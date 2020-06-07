package ru.otus.vsh.hw08.json;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

public interface SingleValueJsonify {
    void addNamedObjectValue(JsonObjectBuilder builder, String name);

    void addOnlyValue(JsonArrayBuilder builder);
}



package ru.otus.vsh.hw08.json.types;

import ru.otus.vsh.hw08.json.ValueJsonify;

import javax.annotation.Nonnull;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

public class NullType implements ValueJsonify {
    @Override
    public void addNamedObjectValue(@Nonnull JsonObjectBuilder builder, @Nonnull String name) {
        builder.addNull(name);
    }

    @Override
    public void addArrayValue(@Nonnull JsonArrayBuilder builder) {
        builder.addNull();
    }
}

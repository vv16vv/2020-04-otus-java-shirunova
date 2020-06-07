package ru.otus.vsh.hw08.json.types;

import ru.otus.vsh.hw08.json.SingleValueJsonify;

import javax.annotation.Nonnull;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

public class NullType implements SingleValueJsonify {
    @Override
    public void addNamedObjectValue(@Nonnull JsonObjectBuilder builder, @Nonnull String name) {
        builder.addNull(name);
    }

    @Override
    public void addOnlyValue(@Nonnull JsonArrayBuilder builder) {
        builder.addNull();
    }
}

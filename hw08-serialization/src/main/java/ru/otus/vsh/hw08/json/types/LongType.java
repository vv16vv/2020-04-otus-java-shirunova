package ru.otus.vsh.hw08.json.types;

import ru.otus.vsh.hw08.json.ValueJsonify;

import javax.annotation.Nonnull;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

public class LongType implements ValueJsonify {
    private final long aPrimitive;

    public LongType(@Nonnull Object objectValue) {
        if (objectValue.getClass().isPrimitive())
            aPrimitive = (long) objectValue;
        else aPrimitive = (Long) objectValue;
    }

    @Override
    public void addNamedObjectValue(@Nonnull JsonObjectBuilder builder, @Nonnull String name) {
        builder.add(name, aPrimitive);
    }

    @Override
    public void addArrayValue(@Nonnull JsonArrayBuilder builder) {
        builder.add(aPrimitive);
    }

}

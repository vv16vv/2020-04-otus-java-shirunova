package ru.otus.vsh.hw08.json.types;

import ru.otus.vsh.hw08.json.ValueJsonify;

import javax.annotation.Nonnull;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

public class ShortType implements ValueJsonify {
    private final short aPrimitive;

    public ShortType(@Nonnull Object objectValue) {
        if (objectValue.getClass().isPrimitive())
            aPrimitive = (short) objectValue;
        else aPrimitive = (Short) objectValue;
    }

    @Override
    public void addNamedObjectValue(@Nonnull JsonObjectBuilder builder, @Nonnull String name) {
        builder.add(name, aPrimitive);
    }

    @Override
    public void addOnlyValue(@Nonnull JsonArrayBuilder builder) {
        builder.add(aPrimitive);
    }

}

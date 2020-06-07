package ru.otus.vsh.hw08.json.types;

import ru.otus.vsh.hw08.json.SingleValueJsonify;

import javax.annotation.Nonnull;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

public class FloatType implements SingleValueJsonify {
    private final float aPrimitive;

    public FloatType(@Nonnull Object objectValue) {
        if (objectValue.getClass().isPrimitive())
            aPrimitive = (float) objectValue;
        else aPrimitive = (Float) objectValue;
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

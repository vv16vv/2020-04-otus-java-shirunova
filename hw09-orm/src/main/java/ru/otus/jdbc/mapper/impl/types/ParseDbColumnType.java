package ru.otus.jdbc.mapper.impl.types;

import javax.annotation.Nonnull;

public final class ParseDbColumnType {
    @Nonnull
    public static DbColumnType parse(@Nonnull Class<?> fieldType) {
        switch (fieldType.toString()) {
            case "class java.lang.String":
                return new StringType();
            case "class java.lang.Integer":
            case "int":
                return new IntType();
            case "class java.lang.Long":
            case "long":
                return new LongType();
            case "class java.lang.Float":
            case "float":
                return new FloatType();
            // TODO all types
            default:
                throw new UnsupportedOperationException(String.format("Unsupported column type %s", fieldType.toString()));
        }
    }
}

package ru.otus.vsh.hw08.json.types;

import ru.otus.vsh.hw08.json.ObjectValueJsonify;
import ru.otus.vsh.hw08.json.ValueJsonify;

import javax.annotation.Nonnull;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ObjectType implements ValueJsonify, ObjectValueJsonify {
    private final Class<?> objectType;
    private final Object objectValue;

    public ObjectType(Class<?> objectType, Object objectValue) {
        this.objectType = objectType;
        this.objectValue = objectValue;
    }

    @Override
    public void addAsObjectValue(@Nonnull JsonObjectBuilder builder) {
        try {
            for (Field field : objectType.getDeclaredFields()) {
                if (Modifier.isTransient(field.getModifiers())) continue;
                field.setAccessible(true);
                var value = field.get(objectValue);
                var name = field.getName();
                var type = field.getType();
                var genericType = field.getGenericType();
                var knownType = TypeFactory.recognizeType(type, value, genericType);
                knownType.addNamedObjectValue(builder, name);
            }
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public void addNamedObjectValue(@Nonnull JsonObjectBuilder builder, @Nonnull String name) {
        var nestedObject = Json.createObjectBuilder();
        addAsObjectValue(nestedObject);
        builder.add(name, nestedObject);
    }

    @Override
    public void addArrayValue(@Nonnull JsonArrayBuilder builder) {
        var nestedObject = Json.createObjectBuilder();
        addAsObjectValue(nestedObject);
        builder.add(nestedObject);
    }
}

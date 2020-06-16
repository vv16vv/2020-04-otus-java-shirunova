package ru.otus.jdbc.mapper.impl;

import ru.otus.core.annotations.Id;
import ru.otus.jdbc.mapper.EntityClassMetaData;

import javax.annotation.Nonnull;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    private final String name;
    private final Constructor<T> constructor;
    private final Field id;
    private final List<Field> otherFields;


    private EntityClassMetaDataImpl(String name, Constructor<T> constructor, Field id, List<Field> otherFields) {
        this.name = name;
        this.constructor = constructor;
        this.id = id;
        this.otherFields = otherFields;
    }

    @Nonnull
    public static <T> EntityClassMetaData<T> create(@Nonnull Class<T> tableType) {
        var name = tableType.getSimpleName();
        Constructor<T> constructor;
        try {
            constructor = tableType.getDeclaredConstructor();
            if (!Modifier.isPublic(constructor.getModifiers()))
                constructor.setAccessible(true);
        } catch (NoSuchMethodException notFound) {
            throw new IllegalArgumentException(String.format("Table '%s' does not contain default constructor", name));
        }
        Field id = null;
        var otherFields = new ArrayList<Field>();
        for (var field : tableType.getDeclaredFields()) {
            if (!Modifier.isPublic(field.getModifiers()))
                field.setAccessible(true);
            if (field.isAnnotationPresent(Id.class)) {
                id = field;
            } else otherFields.add(field);
        }
        if (id == null) throw new IllegalArgumentException(String.format("Table '%s' does not contain field id", name));
        return new EntityClassMetaDataImpl<T>(name, constructor, id, otherFields.stream().sorted(Comparator.comparing(Field::getName)).collect(Collectors.toList()));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Constructor<T> getConstructor() {
        return constructor;
    }

    @Override
    public Field getIdField() {
        return id;
    }

    @Override
    public List<Field> getAllFields() {
        var result = new ArrayList<Field>();
        result.add(id);
        result.addAll(List.copyOf(otherFields));
        return result;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return otherFields;
    }
}

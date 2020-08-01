package ru.otus.vsh.hw08.json.types;

import ru.otus.vsh.hw08.json.ValueJsonify;

import javax.annotation.Nonnull;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

public class ArrayType implements ValueJsonify {
    private final Object[] array;
    private final Type genericItemType;
    private final Class<?> arrayItemType;

    private ArrayType(@Nonnull Class<?> objectType, @Nonnull Object[] objectValue, @Nonnull Type genericArrayType) {
        this.genericItemType = genericArrayType;
        this.array = objectValue;
        this.arrayItemType = objectType.componentType();
    }

    @Nonnull
    public static ArrayType fromArray(@Nonnull Class<?> objectType, @Nonnull Object objectValue, @Nonnull Type genericArrayType) {
        Type genericItemType = genericArrayType;
        if (genericArrayType instanceof GenericArrayType)
            genericItemType = ((GenericArrayType) genericArrayType).getGenericComponentType();
        else if (genericArrayType instanceof ParameterizedType)
            genericItemType = ((ParameterizedType) genericArrayType).getActualTypeArguments()[0];
        return new ArrayType(objectType, convertToArray(objectValue), genericItemType);
    }

    @Nonnull
    public static ArrayType fromCollection(@Nonnull Class<?> objectType, @Nonnull Object objectValue, @Nonnull Type genericArrayType) {
        Object[] nestedValues;
        if (objectValue instanceof Collection<?>)
            nestedValues = ((Collection<?>) objectValue).toArray();
        else throw new IllegalStateException("Expected array of Collection items");
        var nestedType = objectValue.getClass().componentType();
        var nestedGenericType = genericArrayType;
        if (genericArrayType instanceof ParameterizedType) {
            nestedGenericType = ((ParameterizedType) genericArrayType).getActualTypeArguments()[0];
            if (nestedGenericType instanceof ParameterizedType)
                nestedType = (Class<?>) ((ParameterizedType) genericArrayType).getRawType();
            else if (nestedGenericType instanceof Class<?>) nestedType = (Class<?>) nestedGenericType;
        }
        return new ArrayType(nestedType.arrayType(), nestedValues, nestedGenericType);
    }

    @Nonnull
    private static Object[] convertToArray(@Nonnull Object objectArray) {
        int length = Array.getLength(objectArray);
        Object[] outputArray = new Object[length];
        for (int i = 0; i < length; ++i) {
            outputArray[i] = Array.get(objectArray, i);
        }
        return outputArray;
    }

    @Override
    public void addNamedObjectValue(@Nonnull JsonObjectBuilder builder,
                                    @Nonnull String name) {
        var arrayBuilder = Json.createArrayBuilder();
        addArrayValue(arrayBuilder);
        builder.add(name, arrayBuilder);
    }

    @Override
    public void addArrayValue(@Nonnull JsonArrayBuilder builder) {
        for (Object item : array) {
            ValueJsonify knownType = TypeFactory.recognizeType(arrayItemType, item, genericItemType);
            if (arrayItemType.isArray() || TypeFactory.isCollection(arrayItemType)) {
                var arrayBuilder = Json.createArrayBuilder();
                knownType.addArrayValue(arrayBuilder);
                builder.add(arrayBuilder);
            } else {
                knownType.addArrayValue(builder);
            }
        }

    }

}

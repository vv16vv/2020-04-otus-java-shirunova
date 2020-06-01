package ru.otus.vsh.hw08.json;

import javax.annotation.Nonnull;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Collection;

public final class DiyJson {
    private static final String[] primitiveWrappers = new String[]{
            "java.lang.Integer",
            "java.lang.Byte",
            "java.lang.Long",
            "java.lang.Short",
            "java.lang.Boolean",
            "java.lang.Float",
            "java.lang.Double",
            "java.lang.Character",
            "java.lang.String"
    };

    @Nonnull
    public static String toJson(@Nonnull Object o) throws IllegalAccessException, IllegalArgumentException {
        var result = Json.createObjectBuilder();
        addObject(result, o);
        return result.build().toString();
    }

    private static void addObject(@Nonnull JsonObjectBuilder builder, @Nonnull Object o) throws IllegalAccessException {
        for (Field field : o.getClass().getDeclaredFields()) {
            if (Modifier.isTransient(field.getModifiers())) continue;
            field.setAccessible(true);
            var name = field.getName();
            var type = field.getType();
            var genericType = field.getGenericType();
            var value = field.get(o);
            if (field.get(o) == null) continue;
            if (type.isPrimitive()) {
                addNamedPrimitiveValue(builder, value, type, name);
                continue;
            }
            if (type.isArray()) {
                addNamedArrayValue(builder, value, type, name, genericType);
                continue;
            }
            if (isPrimitiveWrapper(type.toString())) {
                addNamedWrapperValue(builder, value, type, name);
                continue;
            }
            if (isCollection(type)) {
                addNamedCollectionValue(builder, value, type, name, genericType);
                continue;
            }
            if (isEnum(type)) {
                addNamedWrapperValue(builder, value, type, name);
                continue;
            }
            var object = field.get(o);
            var nestedObject = Json.createObjectBuilder();
            addObject(nestedObject, object);
            builder.add(name, nestedObject);

        }
    }

    private static void addNamedPrimitiveValue(@Nonnull JsonObjectBuilder builder, @Nonnull Object o, @Nonnull Class<?> objectType, @Nonnull String name) throws IllegalAccessException {
        switch (objectType.toString()) {
            case "byte": {
                builder.add(name, (byte) o);
                break;
            }
            case "int": {
                builder.add(name, (int) o);
                break;
            }
            case "long": {
                builder.add(name, (long) o);
                break;
            }
            case "short": {
                builder.add(name, (short) o);
                break;
            }
            case "boolean": {
                builder.add(name, (boolean) o);
                break;
            }
            case "float": {
                builder.add(name, (float) o);
                break;
            }
            case "double": {
                builder.add(name, (double) o);
                break;
            }
            case "char": {
                builder.add(name, "" + (char) o);
                break;
            }
        }
    }

    private static void addPrimitiveArrayValue(@Nonnull JsonArrayBuilder builder, @Nonnull Object o, int index, @Nonnull Class<?> objectType) throws IllegalAccessException {
        switch (objectType.toString()) {
            case "byte": {
                builder.add(Array.getByte(o, index));
                break;
            }
            case "int": {
                builder.add(Array.getInt(o, index));
                break;
            }
            case "long": {
                builder.add(Array.getLong(o, index));
                break;
            }
            case "short": {
                builder.add(Array.getShort(o, index));
                break;
            }
            case "boolean": {
                builder.add(Array.getBoolean(o, index));
                break;
            }
            case "float": {
                builder.add(Array.getFloat(o, index));
                break;
            }
            case "double": {
                builder.add(Array.getDouble(o, index));
                break;
            }
            case "char": {
                // Если не конвертировать char в строку, то в json он будет записать как число
                builder.add("" + Array.getChar(o, index));
                break;
            }
        }
    }

    private static void addWrapperArrayValue(@Nonnull JsonArrayBuilder builder, Object o, @Nonnull String typeString) throws IllegalAccessException {
        if (o == null) builder.addNull();
        else {
            switch (typeString) {
                case "java.lang.Byte": {
                    builder.add((Byte) o);
                    break;
                }
                case "java.lang.Integer": {
                    builder.add((Integer) o);
                    break;
                }
                case "java.lang.Long": {
                    builder.add((Long) o);
                    break;
                }
                case "java.lang.Short": {
                    builder.add((Short) o);
                    break;
                }
                case "java.lang.Boolean": {
                    builder.add((Boolean) o);
                    break;
                }
                case "java.lang.Float": {
                    builder.add((Float) o);
                    break;
                }
                case "java.lang.Double": {
                    builder.add((Double) o);
                    break;
                }
                case "java.lang.Character":
                case "java.lang.String":
                default: {
                    builder.add(o.toString());
                    break;
                }
            }
        }
    }

    private static void addNamedWrapperValue(@Nonnull JsonObjectBuilder builder, @Nonnull Object o, @Nonnull Class<?> objectType, @Nonnull String name) throws IllegalAccessException {
        switch (objectType.toString()) {
            case "class java.lang.Byte": {
                builder.add(name, (Byte) o);
                break;
            }
            case "class java.lang.Integer": {
                builder.add(name, (Integer) o);
                break;
            }
            case "class java.lang.Long": {
                builder.add(name, (Long) o);
                break;
            }
            case "class java.lang.Short": {
                builder.add(name, (Short) o);
                break;
            }
            case "class java.lang.Boolean": {
                builder.add(name, (Boolean) o);
                break;
            }
            case "class java.lang.Float": {
                builder.add(name, (Float) o);
                break;
            }
            case "class java.lang.Double": {
                builder.add(name, (Double) o);
                break;
            }
            case "class java.lang.Character":
            case "class java.lang.String":
            default: {
                builder.add(name, o.toString());
                break;
            }
        }
    }

    private static boolean isPrimitiveWrapper(@Nonnull String typeString) {
        return Arrays.stream(primitiveWrappers).anyMatch(s -> typeString.replaceFirst("class ", "").replaceFirst("interface", "").equals(s));
    }

    private static boolean isCollection(@Nonnull Class<?> type) {
        if (type.toString().equals("interface java.util.Collection")) return true;
        var result = false;
        for (Class<?> anInterface : type.getInterfaces()) {
            result = result || isCollection(anInterface);
        }
        return result;
    }

    private static boolean isEnum(@Nonnull Class<?> type) {
        return type.isEnum();
    }

    private static void addArrayValue(@Nonnull JsonArrayBuilder builder,
                                      @Nonnull Object array,
                                      @Nonnull Class<?> arrayItemType,
                                      Type genericItemType) throws IllegalAccessException {
        if (arrayItemType.isPrimitive()) {
            final Object[] boxedObjects = new Object[Array.getLength(array)];
            for (int index = 0; index < boxedObjects.length; index++) {
                addPrimitiveArrayValue(builder, array, index, arrayItemType);
            }
        } else if (isPrimitiveWrapper(arrayItemType.toString()) || isEnum(arrayItemType)) {
            var fieldValues = (Object[]) array;
            for (Object value : fieldValues) {
                addWrapperArrayValue(builder, value, arrayItemType.toString().substring("class ".length()));
            }
        } else if (arrayItemType.isArray()) {
            var fieldValues = (Object[]) array;
            for (Object value : fieldValues) {
                var arrayBuilder = Json.createArrayBuilder();
                addArrayValue(arrayBuilder, value, arrayItemType.componentType(), genericItemType);
                builder.add(arrayBuilder);
            }
        } else if (isCollection(arrayItemType)) {
            var fieldValues = (Object[]) array;
            for (Object value : fieldValues) {
                var arrayBuilder = Json.createArrayBuilder();
                Object[] nestedValues;
                if (value instanceof Collection<?>)
                    nestedValues = ((Collection<?>) value).toArray();
                else throw new IllegalStateException("Expected array of Collection items");
                var nestedType = value.getClass().componentType();
                var nestedGenericType = genericItemType;
                if (genericItemType instanceof ParameterizedType) {
                    nestedGenericType = ((ParameterizedType) genericItemType).getActualTypeArguments()[0];
                    if (nestedGenericType instanceof ParameterizedType)
                        nestedType = (Class<?>) ((ParameterizedType) genericItemType).getRawType();
                    else if (nestedGenericType instanceof Class<?>) nestedType = (Class<?>) nestedGenericType;
                }
                addArrayValue(arrayBuilder, nestedValues, nestedType, nestedGenericType);
                builder.add(arrayBuilder);
            }
        } else {
            var fieldValues = (Object[]) array;
            for (Object value : fieldValues) {
                var jsonObjectBuilder = Json.createObjectBuilder();
                addObject(jsonObjectBuilder, value);
                builder.add(jsonObjectBuilder);
            }
        }
    }

    private static void addNamedArrayValue(@Nonnull JsonObjectBuilder builder,
                                           @Nonnull Object array,
                                           @Nonnull Class<?> arrayType,
                                           String fieldName,
                                           Type genericArrayType) throws IllegalAccessException {
        var arrayBuilder = Json.createArrayBuilder();
        Type genericItemType = genericArrayType;
        if (genericArrayType instanceof GenericArrayType)
            genericItemType = ((GenericArrayType) genericArrayType).getGenericComponentType();
        else if (genericArrayType instanceof ParameterizedType)
            genericItemType = ((ParameterizedType) genericArrayType).getActualTypeArguments()[0];
        addArrayValue(arrayBuilder, array, arrayType.componentType(), genericItemType);
        builder.add(fieldName, arrayBuilder);
    }

    @SuppressWarnings({"rawtypes"})
    private static void addNamedCollectionValue(@Nonnull JsonObjectBuilder builder,
                                                @Nonnull Object collection,
                                                @Nonnull Class<?> collectionType,
                                                @Nonnull String name,
                                                Type genericItemType) throws IllegalAccessException {
        var arrayBuilder = Json.createArrayBuilder();
        final var fieldValues = ((Collection) collection).toArray();
        var nestedType = fieldValues.getClass().componentType();
        var nestedGenericType = genericItemType;
        if (genericItemType instanceof ParameterizedType) {
            nestedGenericType = ((ParameterizedType) genericItemType).getActualTypeArguments()[0];
            if (nestedGenericType instanceof ParameterizedType)
                nestedType = (Class<?>) ((ParameterizedType) nestedGenericType).getRawType();
            else if (nestedGenericType instanceof Class<?>) nestedType = (Class<?>) nestedGenericType;
        }
        addArrayValue(arrayBuilder, fieldValues, nestedType, nestedGenericType);

        builder.add(name, arrayBuilder);
    }

}

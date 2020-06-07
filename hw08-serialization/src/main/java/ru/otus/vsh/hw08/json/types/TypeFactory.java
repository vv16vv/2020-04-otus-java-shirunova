package ru.otus.vsh.hw08.json.types;

import ru.otus.vsh.hw08.json.SingleValueJsonify;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Type;
import java.util.Arrays;

public final class TypeFactory {

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

    private TypeFactory() {
    }

    public static SingleValueJsonify recognizeType(@Nonnull Class<?> objectType,
                                                   @Nullable Object objectValue,
                                                   @Nonnull Type genericType) {
        if (objectValue == null) return new NullType();
        if (objectType.isPrimitive() || isPrimitiveWrapper(objectType.toString()) || objectType.isEnum()) {
            return TypeFactory.recognizeSingleValue(objectType, objectValue);
        }
        if (objectType.isArray()) {
            return ArrayType.fromArray(objectType, objectValue, genericType);
        }
        if (isCollection(objectType)) {
            return ArrayType.fromCollection(objectType, objectValue, genericType);
        }
        return new ObjectType(objectType, objectValue);
    }

    @Nonnull
    public static SingleValueJsonify recognizeSingleValue(@Nonnull Class<?> objectType,
                                                          @Nonnull Object objectValue) {
        switch (objectType.toString()) {
            case "byte":
            case "class java.lang.Byte":
                return new ByteType(objectValue);
            case "int":
            case "class java.lang.Integer":
                return new IntType(objectValue);
            case "long":
            case "class java.lang.Long":
                return new LongType(objectValue);
            case "short":
            case "class java.lang.Short":
                return new ShortType(objectValue);
            case "boolean":
            case "class java.lang.Boolean":
                return new BooleanType(objectValue);
            case "float":
            case "class java.lang.Float":
                return new FloatType(objectValue);
            case "double":
            case "class java.lang.Double":
                return new DoubleType(objectValue);
            case "char":
            case "class java.lang.Character":
            case "class java.lang.String":
            default:
                return new StringType(objectValue);
        }
    }

    public static boolean isPrimitiveWrapper(@Nonnull String typeString) {
        return Arrays.stream(primitiveWrappers).anyMatch(
                s -> typeString
                        .replaceFirst("class ", "")
                        .replaceFirst("interface", "")
                        .equals(s)
        );
    }

    public static boolean isCollection(@Nonnull Class<?> type) {
        if (type.toString().equals("interface java.util.Collection")) return true;
        var result = false;
        for (Class<?> anInterface : type.getInterfaces()) {
            result = result || isCollection(anInterface);
        }
        return result;
    }

}

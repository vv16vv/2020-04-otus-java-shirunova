package ru.otus.vsh.hw05.test;

import ru.otus.vsh.hw05.spy.Log;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class WithLoggedAnnotations {

    @Log
    public void noParameters() {
        System.out.println("Method does not contain any parameters");
    }

    @Log
    public void primitiveValues(int i, short s, byte b, char c, boolean bb, long l, double d, float f) {
        System.out.println(String.format("Method gets 8 parameters of each primitive type: " +
                "int = %d, short = %d, byte = %d, char = '%c', " +
                "boolean = %b, long = %d, double = %f, float = %g", i, s, b, c, bb, l, d, f));
    }

    @Log
    public void objectValues(String str, List<?> list, Map<?, ?> map, Object o) {
        System.out.println(String.format("Method gets 4 parameters of some object types: " +
                "String = %s, List = %s, Map = %s, Object = '%s', ", str, list, map, o));
    }

    @Log
    public void oneDArrays(int[] ints, @Nonnull String... strings) {
        System.out.println(String.format("Method contains 2 parameters: array of ints = %s and " +
                "array of Strings (set as vararg) = %s", Arrays.toString(ints), Arrays.toString(strings)));
    }

    @Log
    public void multiDArrays(long[][] longs1, Long[][] longs2) {
        System.out.println(String.format("Method contains 2 parameter: array of array of longs = %s and of Longs = %s",
                Arrays.deepToString(longs1), Arrays.deepToString(longs2)));
    }

}

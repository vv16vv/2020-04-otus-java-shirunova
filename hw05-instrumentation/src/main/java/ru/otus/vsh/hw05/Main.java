package ru.otus.vsh.hw05;

import ru.otus.vsh.hw05.test.WithLoggedAnnotations;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println("Start to test class WithLoggedAnnotations -->");
        testWithLoggedAnnotations();
        System.out.println("<-- Finish to test class WithLoggedAnnotations");
    }

    public static void testWithLoggedAnnotations() {
        WithLoggedAnnotations instance = new WithLoggedAnnotations();

        instance.noParameters();
        System.out.println();

        instance.primitiveValues(1,(short)2, (byte)3, 'A',true, 1234567890L,2.718281828459045,3.14f);
        System.out.println();

        instance.objectValues("Hello ASM", List.of("one", "two"), Map.of(true, "ИСТИНА", false, "ЛОЖЬ"), new Object());
        System.out.println();

        instance.oneDArrays(new int[]{6, 4},"два", "пара", "неуд", "лебедь");
        System.out.println();

        instance.multiDArrays(new long[][]{{1L, 2L, 3L}, {4L, 5L, 6L}}, new Long[][]{{30L, 20L, 10L}, {50L, 60L, 70L}});
        System.out.println();

    }
}

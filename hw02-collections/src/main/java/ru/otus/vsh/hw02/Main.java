package ru.otus.vsh.hw02;

import com.google.common.collect.ImmutableList;

import java.util.Collections;
import java.util.Comparator;

public class Main {
    private static final ImmutableList<String> NAMES = ImmutableList.of(
            "Ann", "Ben", "Clare", "Dan", "Eugene", "Fred",
            "George", "Harold", "Ivan", "Jane", "Kate", "Lucy",
            "Minny", "Nina", "Olga", "Pete", "Quinn", "Rob",
            "Sofie", "Teddy", "Una", "Victoria", "Wally",
            "Xavier", "Zachary");


    @SuppressWarnings("Java8ListSort")
    public static void main(String[] args) {
        DiyArrayList<String> diy1 = new DiyArrayList<>(String.class);

        System.out.println("--- Test for Collections.addAll ---");
        String[] names = new String[NAMES.size()];
        NAMES.toArray(names);
        Collections.addAll(diy1, names);
        diy1.forEach(System.out::println);

        System.out.println("\n--- Test for Collections.copy ---");
        DiyArrayList<String> diy2 = new DiyArrayList<>(String.class, diy1.size());
        for (int i = 0; i < diy1.size(); i++) {
            diy2.add("");
        }
        Collections.copy(diy2, diy1);
        diy2.forEach(System.out::println);

        System.out.println("\n--- Test for Collections.sort ---");
        Collections.sort(diy1, Comparator.comparingInt(String::length));
        diy1.forEach(System.out::println);

    }
}

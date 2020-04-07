package ru.otus.vsh.hw01;

import com.google.common.collect.ImmutableSet;

public class HelloOtus {
    private static final ImmutableSet<String> TRAFFIC_LIGHTS = ImmutableSet.of(
            "red",
            "yellow",
            "green");

    public static void main(String[] args) {
        TRAFFIC_LIGHTS.forEach(System.out::println);
    }
}

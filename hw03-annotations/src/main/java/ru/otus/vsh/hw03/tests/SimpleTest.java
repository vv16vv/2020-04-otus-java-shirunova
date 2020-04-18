package ru.otus.vsh.hw03.tests;

import ru.otus.vsh.hw03.annotations.Test;

public class SimpleTest {

    @Test("The simplest test")
    void alwaysPass() {
        System.out.println("Test alwaysPass - passed");
    }

}

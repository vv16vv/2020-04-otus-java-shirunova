package ru.otus.vsh.hw03.tests;

import ru.otus.vsh.hw03.annotations.Test;

public class ExceptionInTestTest {

    @Test("The test with exception")
    void alwaysPass() throws Exception {
        System.out.println("Test alwaysPass - failed");
        throw new Exception("AAA");
    }
}

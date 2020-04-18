package ru.otus.vsh.hw03.tests;

import ru.otus.vsh.hw03.annotations.After;
import ru.otus.vsh.hw03.annotations.Test;

public class ExceptionInAfterTest {

    @After
    void tearDownWithException() throws Exception {
        throw new Exception("What have I done?");
    }

    @Test
    void first() {
        System.out.println("Some message");
    }

    @Test
    void second() {
        System.out.println("Another message");
    }
}

package ru.otus.vsh.hw03.tests;

import ru.otus.vsh.hw03.annotations.Before;
import ru.otus.vsh.hw03.annotations.Test;

public class ExceptionInBeforeTest {

    @Before
    void setupWithException() throws Exception {
        throw new Exception("I don't want to be executed!");
    }

    @Test
    void neverExecuted() {
        System.out.println("This message should be never seen");
    }

    @Test
    void anotherNeverExecuted() {
        System.out.println("This message should be never seen");
    }
}

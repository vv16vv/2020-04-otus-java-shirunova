package ru.otus.vsh.hw03.tests;

import ru.otus.vsh.hw03.annotations.After;
import ru.otus.vsh.hw03.annotations.Before;
import ru.otus.vsh.hw03.annotations.Test;

public class SeveralTestsTest {
    @Before
    void setup() {
        System.out.println("setup");
    }

    @After
    void tearDown() {
        System.out.println("tear down");
    }

    @Test("First test")
    void test1() {
        System.out.println("first test");
    }

    @Test("Second test")
    void test2() {
        System.out.println("second test");
    }

}

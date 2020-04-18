package ru.otus.vsh.hw03.tests;

import ru.otus.vsh.hw03.annotations.After;
import ru.otus.vsh.hw03.annotations.Before;

public class EmptyWithBeforeAfterTest {
    @Before
    void setup() {
        System.out.println("setup");
    }

    @After
    void tearDown() {
        System.out.println("tear down");
    }
}

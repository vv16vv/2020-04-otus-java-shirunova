package ru.otus.vsh.hw03.tests;

import ru.otus.vsh.hw03.annotations.After;
import ru.otus.vsh.hw03.annotations.Before;
import ru.otus.vsh.hw03.annotations.Test;
import ru.otus.vsh.hw03.annotations.Times;

import java.util.Random;

public class WithTimesTest {
    Random generator;

    @Before
    void setup() {
        generator = new Random();
        System.out.println("setup");
    }

    @After
    void tearDown() {
        generator = null;
        System.out.println("tear down");
    }

    @Test("The coin test")
    @Times(30)
    void coin() {
        int coinValue = generateRandomNumber(1, 2);
        if (coinValue != 1 && coinValue != 2)
            throw new IllegalStateException("it's neigher tail or heads of the coin");
        System.out.println("Let's toss a coin.Result: " + coinValue + " - passed");
    }

    // both bounds are inclusive + 1
    private int generateRandomNumber(int lo, int hi) {
        return generator.nextInt(hi - lo + 2) + lo;
    }
}

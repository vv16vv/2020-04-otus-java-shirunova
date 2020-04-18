package ru.otus.vsh.hw03;

import ru.otus.vsh.hw03.runner.Runner;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try {
//            Runner runner = new Runner(args[1]);
//            Map<Runner.Status, Integer> tests = Runner.run("ru.otus.vsh.hw03.tests.TestTest");
            Map<Runner.Status, Integer> tests = Runner.run("ru.otus.vsh.hw03.tests.ExceptionInBeforeTest");

            System.out.println("Status: passed = " + tests.get(Runner.Status.SUCCESS) +
                    "; failed = " + tests.get(Runner.Status.FAILED) +
                    "; total = " + (tests.get(Runner.Status.SUCCESS) + tests.get(Runner.Status.FAILED)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

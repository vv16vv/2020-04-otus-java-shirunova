package ru.otus.vsh.hw03;

import ru.otus.vsh.hw03.runner.Runner;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try {
            if (args.length == 0)
                throw new IllegalArgumentException("Not enough parameters. Specify a fully qualified name of the tested class." +
                        "\nUse one of following:" +
                        "\n\tru.otus.vsh.hw03.tests.ComplexTest" +
                        "\n\tru.otus.vsh.hw03.tests.EmptyTest" +
                        "\n\tru.otus.vsh.hw03.tests.EmptyWithBeforeAfterTest" +
                        "\n\tru.otus.vsh.hw03.tests.ExceptionInAfterTest" +
                        "\n\tru.otus.vsh.hw03.tests.ExceptionInBeforeTest" +
                        "\n\tru.otus.vsh.hw03.tests.ExceptionInTestTest" +
                        "\n\tru.otus.vsh.hw03.tests.SimpleTest");
            Map<Runner.Status, Integer> tests = Runner.run(args[0]);

            System.out.println("Status: passed = " + tests.get(Runner.Status.SUCCESS) +
                    "; failed = " + tests.get(Runner.Status.FAILED) +
                    "; total = " + (tests.get(Runner.Status.SUCCESS) + tests.get(Runner.Status.FAILED)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

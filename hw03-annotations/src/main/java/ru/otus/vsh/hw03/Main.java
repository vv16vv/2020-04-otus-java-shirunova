package ru.otus.vsh.hw03;

import ru.otus.vsh.hw03.runner.Runner;
import ru.otus.vsh.hw03.runner.TestStatus;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try {
            if (args.length == 0)
                throw new IllegalArgumentException("Not enough parameters. Specify a fully qualified name of the tested class." +
                        "\nUse one of following:" +
                        "\n\tru.otus.vsh.hw03.tests.EmptyTest" +
                        "\n\tru.otus.vsh.hw03.tests.EmptyWithBeforeAfterTest" +
                        "\n\tru.otus.vsh.hw03.tests.ExceptionInAfterTest" +
                        "\n\tru.otus.vsh.hw03.tests.ExceptionInBeforeTest" +
                        "\n\tru.otus.vsh.hw03.tests.ExceptionInTestTest" +
                        "\n\tru.otus.vsh.hw03.tests.SimpleTest" +
                        "\n\tru.otus.vsh.hw03.tests.SeveralTestsTest" +
                        "\n\tru.otus.vsh.hw03.tests.WithTimesTest");

            Runner runner = new Runner(args[0]);
            runner.run();
            Map<TestStatus, Integer> tests = runner.getResult();

            System.out.println("Status: passed = " + tests.get(TestStatus.SUCCESS) +
                    "; failed = " + tests.get(TestStatus.FAILED) +
                    "; total = " + tests.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

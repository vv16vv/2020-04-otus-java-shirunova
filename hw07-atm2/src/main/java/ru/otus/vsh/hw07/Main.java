package ru.otus.vsh.hw07;

import ru.otus.vsh.hw07.api.Atm;
import ru.otus.vsh.hw07.api.AtmBuilder;
import ru.otus.vsh.hw07.api.Banknote;
import ru.otus.vsh.hw07.api.CantHandOutMoneyException;
import ru.otus.vsh.hw07.api.actions.AtmAction;
import ru.otus.vsh.hw07.api.actions.AtmGetValueAction;
import ru.otus.vsh.hw07.api.actions.AtmInitiateAction;
import ru.otus.vsh.hw07.api.actions.AtmPutMoneyAction;
import ru.otus.vsh.hw07.impl.Department;

import javax.annotation.Nonnull;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class Main {
    private final static String datePattern = "dd MMM yyyy";
    private final static DateFormat dateFormat = new SimpleDateFormat(datePattern);

    public static void main(String[] args) {
        Department department = new Department("Приморский");
        Atm emptyAtm = new AtmBuilder("first").build();
        emptyAtm.accept(Map.of(
                Banknote.FIVE_THOUSAND, 100
        ));
        Atm atmWithMoney = new AtmBuilder("second")
                .setInitialMoney(Map.of(
                        Banknote.THOUSAND, 100
                ))
                .build();
        department.addAtm(emptyAtm);
        department.addAtm(atmWithMoney);
        (new AtmGetValueAction()).process(department);

        AtmAction putSomeMoney = new AtmPutMoneyAction(Map.of(
                Banknote.FIVE_HUNDRED, 100
        )).next(new AtmGetValueAction());
        AtmAction dayOpener = new AtmInitiateAction(String.format("Открыть день %s", dateFormat.format(new Date()))).next(putSomeMoney);
        dayOpener.process(department);

        processOutput(emptyAtm, 1000);
        printCurrentMoneyState(department);
        processOutput(atmWithMoney, 5000);
        printCurrentMoneyState(department);

        AtmAction dayCloser = new AtmGetValueAction().next(new AtmInitiateAction(String.format("Закрыть день %s", dateFormat.format(new Date()))));
        dayCloser.process(department);

    }

    private static void processOutput(@Nonnull Atm atm, long sum) {
        try {
            var result = atm.handout(sum);
            System.out.printf("ATM '%s' выдал %d рублей следующими купюрами: %s%n", atm.id(), sum, result.toString());
        } catch (CantHandOutMoneyException e) {
            System.out.printf("ATM '%s' не смог выдать сумму %d рублей%n", atm.id(), sum);
        }
    }

    private static void printCurrentMoneyState(@Nonnull Department department) {
        System.out.printf("Отделение '%s': текущий остаток (без опроса всех банкоматов) %d рублей%n", department.id(), department.getCurrentMoneyMomentary());
    }

}

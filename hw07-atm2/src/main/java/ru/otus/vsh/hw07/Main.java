package ru.otus.vsh.hw07;

import ru.otus.vsh.hw07.api.*;
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
    private final Department department = new Department("Приморский");
    private final String datePattern = "dd MMM yyyy";
    private final DateFormat dateFormat = new SimpleDateFormat(datePattern);

    public static void main(String[] args) {
        var main = new Main();
        Atm emptyAtm = new AtmBuilder("first")
                .setAtmValueChangeListener(main.department)
                .build();
        emptyAtm.accept(Map.of(
                Banknote.FIVE_THOUSAND, 100
        ));
        Atm atmWithMoney = new AtmBuilder("second")
                .setAtmValueChangeListener(main.department)
                .setInitialMoney(Map.of(
                        Banknote.THOUSAND, 100
                ))
                .build();
        main.department.addAtm(emptyAtm);
        main.department.addAtm(atmWithMoney);

        AtmAction putSomeMoney = new AtmPutMoneyAction(Map.of(
                Banknote.FIVE_HUNDRED, 100
        )).next(new AtmGetValueAction());
        AtmAction dayOpener = new AtmInitiateAction(String.format("Открыть день %s", main.dateFormat.format(new Date()))).next(putSomeMoney);
        dayOpener.process(main.department);

        main.processOutput(emptyAtm, 1000);
        main.processOutput(atmWithMoney, 5000);

        AtmAction dayCloser = new AtmGetValueAction().next(new AtmInitiateAction(String.format("Закрыть день %s", main.dateFormat.format(new Date()))));
        dayCloser.process(main.department);

    }

    private void processOutput(@Nonnull Atm atm, long sum) {
        try {
            var result = atm.handout(sum);
            System.out.printf("ATM '%s' выдал %d рублей следующими купюрами: %s%n", atm.id(), sum, result.toString());
        } catch (CantHandOutMoneyException e) {
            System.out.printf("ATM '%s' не смог выдать сумму %d рублей%n", atm.id(), sum);
        }
    }

}

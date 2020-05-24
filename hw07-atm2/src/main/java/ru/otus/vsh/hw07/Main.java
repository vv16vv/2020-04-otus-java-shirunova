package ru.otus.vsh.hw07;


import ru.otus.vsh.hw07.api.Atm;
import ru.otus.vsh.hw07.api.Banknote;
import ru.otus.vsh.hw07.api.CantHandOutMoneyException;
import ru.otus.vsh.hw07.impl.AtmImpl;
import ru.otus.vsh.hw07.impl.Department;

import java.util.Map;

public class Main {
    private final Department department = new Department();

    public static void main(String[] args) {
        var main = new Main();
        Atm emptyAtm = new AtmImpl("first");
        emptyAtm.accept(Map.of(
                Banknote.FIVE_THOUSAND, 100
        ));
        Atm atmWithMoney = new AtmImpl("second", Map.of(
                Banknote.THOUSAND, 100
        ));
        main.department.addAtm(emptyAtm);
        main.department.addAtm(atmWithMoney);

        main.currentState();

        main.processOutput(emptyAtm, 1000);
        main.processOutput(atmWithMoney, 5000);

        main.department.initiate("инкассация 24 мая");
        main.currentState();

    }

    private void currentState() {
        System.out.println(String.format("Текущий остаток в отделении %d", department.currentValue()));
    }

    private void processOutput(Atm atm, long sum) {
        try {
            var result = atm.handout(sum);
            System.out.println(String.format("ATM '%s' выдал %d рублей следующими купюрами: %s", atm.id(), sum, result.toString()));
        } catch (CantHandOutMoneyException e) {
            System.out.println(String.format("ATM '%s' не смог выдать сумму %d рублей", atm.id(), sum));
        }
        currentState();
    }

}

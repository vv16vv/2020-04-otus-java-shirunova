package ru.otus.vsh.hw06;

import ru.otus.vsh.hw06.api.Atm;
import ru.otus.vsh.hw06.api.Banknote;
import ru.otus.vsh.hw06.api.CantHandOutMoneyException;
import ru.otus.vsh.hw06.impl.AtmImpl;

import java.util.Map;

public class Main {
    private final Atm atm = new AtmImpl();

    public static void main(String[] args) {
        var main = new Main();
        main.atm.accept(Map.of(
                Banknote.FIVE_THOUSAND, 100,
                Banknote.THOUSAND, 100,
                Banknote.FIVE_HUNDRED, 100,
                Banknote.HUNDRED, 1000,
                Banknote.FIFTY, 1000
        ));
        main.currentState();
        main.processOutput(10);
        main.processOutput(100);
        main.processOutput(150);
        main.processOutput(200);
        main.processOutput(34550);
        main.processOutput(800000);
        main.processOutput(764300);
        main.processOutput(1000);
        main.processOutput(500);
        main.processOutput(150);
        main.processOutput(50);
    }

    private void currentState(){
        System.out.println(String.format("ATM contains %d roubles", atm.currentValue()));
    }

    private void processOutput(long sum){
        try {
            var result = atm.handout(sum);
            System.out.println(String.format("ATM hands out for sum %d roubles following notes: %s", sum, result.toString()));
        }
        catch (CantHandOutMoneyException e){
            System.out.println(String.format("ATM failed to hand out the sum %d", sum));
        }
        currentState();
    }

}

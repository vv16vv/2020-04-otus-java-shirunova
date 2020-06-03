package ru.otus.vsh.hw07.impl;

import org.junit.jupiter.api.Test;
import ru.otus.vsh.hw07.api.AtmBuilder;
import ru.otus.vsh.hw07.api.Banknote;

import java.util.Map;

public class AtmResetTest {

    @Test
    void resetToEmpty() {
        var atm = new AtmBuilder("first").build();
        atm.accept(Map.of(
                Banknote.FIFTY, 1,
                Banknote.TEN, 1
        ));
        atm.reset("reset to empty");
        assert (atm.currentMoney() == 0);
    }

    @Test
    void resetToWithMoney() {
        var initialState = Map.of(
                Banknote.FIFTY, 1,
                Banknote.TEN, 1
        );
        var atm = new AtmBuilder("first")
                .setInitialMoney(initialState)
                .build();
        atm.accept(Map.of(
                Banknote.FIVE_HUNDRED, 1,
                Banknote.HUNDRED, 1
        ));
        atm.reset("reset to with money");
        assert (atm.currentMoney() == 60);
    }
}

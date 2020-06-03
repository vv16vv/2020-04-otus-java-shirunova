package ru.otus.vsh.hw07.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.vsh.hw07.api.AtmBuilder;
import ru.otus.vsh.hw07.api.Banknote;
import ru.otus.vsh.hw07.api.CantHandOutMoneyException;

import java.util.Map;

class AtmHandoutTest {

    @Test
    void fromEmptyAtm() {
        var atm = new AtmBuilder("first").build();
        Assertions.assertThrows(IllegalStateException.class, () -> atm.handout(10));
    }

    @Test
    void negativeSum() {
        var atm = new AtmBuilder("first").build();
        atm.accept(Map.of(Banknote.TEN, 1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> atm.handout(-10));
    }

    @Test
    void askedMoreThenPresent() {
        var atm = new AtmBuilder("first").build();
        atm.accept(Map.of(Banknote.TEN, 1));
        Assertions.assertThrows(CantHandOutMoneyException.class, () -> atm.handout(20));
    }

    @Test
    void noSuitableNotes() {
        var atm = new AtmBuilder("first").build();
        atm.accept(Map.of(Banknote.FIFTY, 1));
        Assertions.assertThrows(CantHandOutMoneyException.class, () -> atm.handout(20));
    }

    @Test
    void notDividedOnMinimalValue() {
        var atm = new AtmBuilder("first").build();
        atm.accept(Map.of(
                Banknote.FIFTY, 1,
                Banknote.HUNDRED, 1
        ));
        Assertions.assertThrows(CantHandOutMoneyException.class, () -> atm.handout(60));
        Assertions.assertEquals(150, atm.currentMoney());
    }

    @Test
    void notEnoughNotes() {
        var atm = new AtmBuilder("first").build();
        atm.accept(Map.of(
                Banknote.FIFTY, 2,
                Banknote.TEN, 1
        ));
        Assertions.assertThrows(CantHandOutMoneyException.class, () -> atm.handout(70));
        Assertions.assertEquals(110, atm.currentMoney());
    }

    @Test
    void correct() {
        var atm = new AtmBuilder("first").build();
        atm.accept(Map.of(
                Banknote.FIFTY, 2,
                Banknote.TEN, 1
        ));
        var result = atm.handout(60);
        var expected = Map.of(
                Banknote.FIFTY, 1,
                Banknote.TEN, 1
        );
        Assertions.assertEquals(result, expected);
        Assertions.assertEquals(50, atm.currentMoney());
    }

    @Test
    void changeMinimalBanknote() {
        var atm = new AtmBuilder("first").build();
        atm.accept(Map.of(
                Banknote.FIFTY, 1,
                Banknote.TEN, 1
        ));
        var resultSuccess = atm.handout(10);
        var expectedSuccess = Map.of(
                Banknote.TEN, 1
        );
        Assertions.assertEquals(resultSuccess, expectedSuccess);
        Assertions.assertThrows(CantHandOutMoneyException.class, () -> atm.handout(10));
    }

}
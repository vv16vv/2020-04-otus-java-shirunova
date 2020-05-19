package ru.otus.vsh.hw06.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AssertionsKt;
import org.junit.jupiter.api.Test;
import ru.otus.vsh.hw06.api.Atm;
import ru.otus.vsh.hw06.api.Banknote;
import ru.otus.vsh.hw06.api.CantHandOutMoneyException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AtmHandoutTest {

    @Test
    void fromEmptyAtm() {
        Atm atm = new AtmImpl();
        Assertions.assertThrows(IllegalStateException.class, () -> atm.handout(10));
    }

    @Test
    void negativeSum() {
        Atm atm = new AtmImpl();
        atm.accept(Map.of(Banknote.TEN, 1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> atm.handout(-10));
    }

    @Test
    void askedMoreThenPresent() {
        Atm atm = new AtmImpl();
        atm.accept(Map.of(Banknote.TEN, 1));
        Assertions.assertThrows(CantHandOutMoneyException.class, () -> atm.handout(20));
    }

    @Test
    void noSuitableNotes() {
        Atm atm = new AtmImpl();
        atm.accept(Map.of(Banknote.FIFTY, 1));
        Assertions.assertThrows(CantHandOutMoneyException.class, () -> atm.handout(20));
    }

    @Test
    void notDividedOnMinimalValue() {
        Atm atm = new AtmImpl();
        atm.accept(Map.of(
                Banknote.FIFTY, 1,
                Banknote.HUNDRED, 1
        ));
        Assertions.assertThrows(CantHandOutMoneyException.class, () -> atm.handout(60));
        Assertions.assertEquals(150, atm.currentValue());
    }

    @Test
    void notEnoughNotes() {
        Atm atm = new AtmImpl();
        atm.accept(Map.of(
                Banknote.FIFTY, 2,
                Banknote.TEN, 1
        ));
        Assertions.assertThrows(CantHandOutMoneyException.class, () -> atm.handout(70));
        Assertions.assertEquals(110, atm.currentValue());
    }

    @Test
    void correct() {
        Atm atm = new AtmImpl();
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
        Assertions.assertEquals(50, atm.currentValue());
    }

    @Test
    void changeMinimalBanknote(){
        Atm atm = new AtmImpl();
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
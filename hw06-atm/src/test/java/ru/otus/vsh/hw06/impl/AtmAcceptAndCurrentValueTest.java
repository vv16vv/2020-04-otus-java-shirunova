package ru.otus.vsh.hw06.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.vsh.hw06.api.Atm;
import ru.otus.vsh.hw06.api.Banknote;

import java.util.Map;

class AtmAcceptAndCurrentValueTest {

    @Test
    void emptyValue() {
        Atm atm = new AtmImpl();
        Assertions.assertEquals(0, atm.currentValue());
    }

    @Test
    void oneNote10() {
        Atm atm = new AtmImpl();
        atm.accept(Map.of(Banknote.TEN, 1));
        Assertions.assertEquals(10, atm.currentValue());
    }

    @Test
    void oneNote50(){
        Atm atm = new AtmImpl();
        atm.accept(Map.of(Banknote.FIFTY, 1));
        Assertions.assertEquals(50, atm.currentValue());
    }

    @Test
    void oneNote100(){
        Atm atm = new AtmImpl();
        atm.accept(Map.of(Banknote.HUNDRED, 1));
        Assertions.assertEquals(100, atm.currentValue());
    }

    @Test
    void oneNote200(){
        Atm atm = new AtmImpl();
        atm.accept(Map.of(Banknote.TWO_HUNDRED, 1));
        Assertions.assertEquals(200, atm.currentValue());
    }

    @Test
    void oneNote500(){
        Atm atm = new AtmImpl();
        atm.accept(Map.of(Banknote.FIVE_HUNDRED, 1));
        Assertions.assertEquals(500, atm.currentValue());
    }

    @Test
    void oneNote1000(){
        Atm atm = new AtmImpl();
        atm.accept(Map.of(Banknote.THOUSAND, 1));
        Assertions.assertEquals(1000, atm.currentValue());
    }

    @Test
    void oneNote2000(){
        Atm atm = new AtmImpl();
        atm.accept(Map.of(Banknote.TWO_THOUSAND, 1));
        Assertions.assertEquals(2000, atm.currentValue());
    }

    @Test
    void oneNote5000(){
        Atm atm = new AtmImpl();
        atm.accept(Map.of(Banknote.FIVE_THOUSAND, 1));
        Assertions.assertEquals(5000, atm.currentValue());
    }

    @Test
    void allNotes(){
        Atm atm = new AtmImpl();
        atm.accept(Map.of(
                Banknote.TEN, 1,
                Banknote.FIFTY, 1,
                Banknote.HUNDRED, 1,
                Banknote.TWO_HUNDRED, 1,
                Banknote.FIVE_HUNDRED, 1,
                Banknote.THOUSAND, 1,
                Banknote.TWO_THOUSAND, 1,
                Banknote.FIVE_THOUSAND, 1
        ));
        assert(atm.currentValue() == 8860);
    }

    @Test
    void complex(){
        Atm atm = new AtmImpl();
        atm.accept(Map.of(
                Banknote.TEN, 5,
                Banknote.FIFTY, 5,
                Banknote.HUNDRED, 5,
                Banknote.TWO_HUNDRED, 5,
                Banknote.FIVE_HUNDRED, 5,
                Banknote.THOUSAND, 5,
                Banknote.TWO_THOUSAND, 5,
                Banknote.FIVE_THOUSAND, 5
        ));
        assert(atm.currentValue() == 44300);
    }

}
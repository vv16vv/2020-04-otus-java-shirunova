package ru.otus.vsh.hw07.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.vsh.hw07.api.AtmBuilder;
import ru.otus.vsh.hw07.api.Banknote;

import java.util.Map;

class AtmAcceptAndCurrentValueTest {

    @Test
    void emptyValue() {
        var atm = new AtmBuilder("first").build();
        Assertions.assertEquals(0, atm.currentMoney());
    }

    @Test
    void oneNote10() {
        var atm = new AtmBuilder("first").build();
        atm.accept(Map.of(Banknote.TEN, 1));
        Assertions.assertEquals(10, atm.currentMoney());
    }

    @Test
    void oneNote50(){
        var atm = new AtmBuilder("first").build();
        atm.accept(Map.of(Banknote.FIFTY, 1));
        Assertions.assertEquals(50, atm.currentMoney());
    }

    @Test
    void oneNote100(){
        var atm = new AtmBuilder("first").build();
        atm.accept(Map.of(Banknote.HUNDRED, 1));
        Assertions.assertEquals(100, atm.currentMoney());
    }

    @Test
    void oneNote200(){
        var atm = new AtmBuilder("first").build();
        atm.accept(Map.of(Banknote.TWO_HUNDRED, 1));
        Assertions.assertEquals(200, atm.currentMoney());
    }

    @Test
    void oneNote500(){
        var atm = new AtmBuilder("first").build();
        atm.accept(Map.of(Banknote.FIVE_HUNDRED, 1));
        Assertions.assertEquals(500, atm.currentMoney());
    }

    @Test
    void oneNote1000(){
        var atm = new AtmBuilder("first").build();
        atm.accept(Map.of(Banknote.THOUSAND, 1));
        Assertions.assertEquals(1000, atm.currentMoney());
    }

    @Test
    void oneNote2000(){
        var atm = new AtmBuilder("first").build();
        atm.accept(Map.of(Banknote.TWO_THOUSAND, 1));
        Assertions.assertEquals(2000, atm.currentMoney());
    }

    @Test
    void oneNote5000(){
        var atm = new AtmBuilder("first").build();
        atm.accept(Map.of(Banknote.FIVE_THOUSAND, 1));
        Assertions.assertEquals(5000, atm.currentMoney());
    }

    @Test
    void allNotes(){
        var atm = new AtmBuilder("first").build();
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
        assert(atm.currentMoney() == 8860);
    }

    @Test
    void complex(){
        var atm = new AtmBuilder("first").build();
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
        assert(atm.currentMoney() == 44300);
    }

}
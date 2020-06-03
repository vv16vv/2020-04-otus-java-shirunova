package ru.otus.vsh.hw07.impl;

import org.junit.jupiter.api.Test;
import ru.otus.vsh.hw07.api.AtmBuilder;
import ru.otus.vsh.hw07.api.Banknote;
import ru.otus.vsh.hw07.api.actions.AtmAction;
import ru.otus.vsh.hw07.api.actions.AtmGetValueAction;

import java.util.Map;

public class DepartmentTest {
    @Test
    void emptyDepartment() {
        var department = new Department("store");
        assert department.getCurrentMoney() == 0L;
    }

    @Test
    void addEmptyAtm() {
        var department = new Department("store");
        var atm = new AtmBuilder("first").build();
        department.addAtm(atm);
        assert department.getCurrentMoney() == 0L;
    }

    @Test
    void addFullAtm() {
        var department = new Department("store");
        var initialState = Map.of(
                Banknote.FIFTY, 1,
                Banknote.TEN, 1
        );
        var atm = new AtmBuilder("first")
                .setInitialMoney(initialState)
                .build();
        department.addAtm(atm);
        AtmAction getValueAction = new AtmGetValueAction();
        getValueAction.process(department);
        assert department.getCurrentMoney() == 60L;
    }

    @Test
    void handOutMoney() {
        var department = new Department("store");
        var initialState = Map.of(
                Banknote.FIFTY, 1,
                Banknote.TEN, 1
        );
        var atm = new AtmBuilder("first")
                .setInitialMoney(initialState)
                .build();
        department.addAtm(atm);
        AtmAction getValueAction = new AtmGetValueAction();
        getValueAction.process(department);
        atm.handout(50L);
        assert department.getCurrentMoney() == 10L;
    }

    @Test
    void acceptMoney() {
        var department = new Department("store");
        var initialState = Map.of(
                Banknote.FIFTY, 1,
                Banknote.TEN, 1
        );
        var atm = new AtmBuilder("first")
                .setInitialMoney(initialState)
                .build();
        department.addAtm(atm);
        AtmAction getValueAction = new AtmGetValueAction();
        getValueAction.process(department);
        atm.accept(Map.of(
                Banknote.FIVE_HUNDRED, 1
        ));
        assert department.getCurrentMoney() == 560L;
    }
}

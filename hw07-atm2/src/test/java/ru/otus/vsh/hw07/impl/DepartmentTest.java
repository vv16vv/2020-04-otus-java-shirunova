package ru.otus.vsh.hw07.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.vsh.hw07.api.AtmBuilder;
import ru.otus.vsh.hw07.api.Banknote;
import ru.otus.vsh.hw07.api.actions.AtmAction;
import ru.otus.vsh.hw07.api.actions.AtmGetValueAction;
import ru.otus.vsh.hw07.api.actions.AtmInitiateAction;
import ru.otus.vsh.hw07.api.actions.AtmPutMoneyAction;

import java.util.Map;

public class DepartmentTest {
    @Test
    void emptyDepartment() {
        var department = new Department("store");
        Assertions.assertEquals(0L, department.getCurrentMoneyMomentary());
    }

    @Test
    void addEmptyAtm() {
        var department = new Department("store");
        var atm = new AtmBuilder("first").build();
        department.addAtm(atm);
        Assertions.assertEquals(0L, department.getCurrentMoneyMomentary());
    }

    @Test
    void addFullAtm() {
        var department = new Department("store");
        var initialState = Map.of(
                Banknote.FIFTY, 1,
                Banknote.TEN, 1
        );
        var atm = new AtmBuilder("first")
                .withInitialMoney(initialState)
                .build();
        department.addAtm(atm);
        Assertions.assertEquals(60L, department.getCurrentMoneyMomentary());
    }

    @Test
    void removeAtm() {
        var department = new Department("store");
        var initialState = Map.of(
                Banknote.FIFTY, 1,
                Banknote.TEN, 1
        );
        var atm = new AtmBuilder("first")
                .withInitialMoney(initialState)
                .build();
        department.addAtm(atm);
        department.removeAtm(atm);
        Assertions.assertEquals(0L, department.getCurrentMoneyMomentary());
    }

    @Test
    void handOutMoney() {
        var department = new Department("store");
        var initialState = Map.of(
                Banknote.FIFTY, 1,
                Banknote.TEN, 1
        );
        var atm = new AtmBuilder("first")
                .withInitialMoney(initialState)
                .build();
        department.addAtm(atm);
        atm.handout(50L);
        Assertions.assertEquals(10L, department.getCurrentMoneyMomentary());
    }

    @Test
    void acceptMoney() {
        var department = new Department("store");
        var initialState = Map.of(
                Banknote.FIFTY, 1,
                Banknote.TEN, 1
        );
        var atm = new AtmBuilder("first")
                .withInitialMoney(initialState)
                .build();
        department.addAtm(atm);
        atm.accept(Map.of(
                Banknote.FIVE_HUNDRED, 1
        ));
        Assertions.assertEquals(560L, department.getCurrentMoneyMomentary());
    }

    @Test
    void acceptMoneyAction() {
        var department = new Department("store");
        var atm1 = new AtmBuilder("first").build();
        var atm2 = new AtmBuilder("second").build();
        department.addAtm(atm1);
        department.addAtm(atm2);
        var putMoneyAction = new AtmPutMoneyAction(Map.of(
                Banknote.FIVE_HUNDRED, 2
        )).next(new AtmGetValueAction());
        putMoneyAction.process(department);
        Assertions.assertEquals(1000L, department.getCurrentMoneyMomentary());
    }

    @Test
    void acceptMoneyResetActionEmptyAtm() {
        var department = new Department("store");
        var atm1 = new AtmBuilder("first").build();
        var atm2 = new AtmBuilder("second").build();
        department.addAtm(atm1);
        department.addAtm(atm2);
        var putMoneyAction = new AtmPutMoneyAction(Map.of(
                Banknote.FIVE_HUNDRED, 2
        )).next(new AtmGetValueAction());
        AtmAction initiateAction = new AtmInitiateAction("Сбросить к исходному состоянию").next(putMoneyAction);
        initiateAction.process(department);
        Assertions.assertEquals(1000L, department.getCurrentMoneyMomentary());
    }

    @Test
    void resetActionFullAtm() {
        var department = new Department("store");
        var initialState1 = Map.of(
                Banknote.FIFTY, 1,
                Banknote.TEN, 1
        );
        var atm1 = new AtmBuilder("first")
                .withInitialMoney(initialState1)
                .build();
        var initialState2 = Map.of(
                Banknote.HUNDRED, 1,
                Banknote.TEN, 1
        );
        var atm2 = new AtmBuilder("second")
                .withInitialMoney(initialState2)
                .build();
        department.addAtm(atm1);
        department.addAtm(atm2);
        AtmAction initiateAction = new AtmInitiateAction("Сбросить к исходному состоянию");
        initiateAction.process(department);
        Assertions.assertEquals(170L, department.getCurrentMoneyMomentary());
    }

    @Test
    void addMoneyAfterResetActionFullAtmEvenNotes() {
        var department = new Department("store");
        var initialState1 = Map.of(
                Banknote.FIFTY, 1,
                Banknote.TEN, 1
        );
        var atm1 = new AtmBuilder("first")
                .withInitialMoney(initialState1)
                .build();
        var initialState2 = Map.of(
                Banknote.HUNDRED, 1,
                Banknote.TEN, 1
        );
        var atm2 = new AtmBuilder("second")
                .withInitialMoney(initialState2)
                .build();
        department.addAtm(atm1);
        department.addAtm(atm2);
        var putMoneyAction = new AtmPutMoneyAction(Map.of(
                Banknote.FIVE_HUNDRED, 2
        )).next(new AtmGetValueAction());
        AtmAction initiateAction = new AtmInitiateAction("Сбросить к исходному состоянию").next(putMoneyAction);
        initiateAction.process(department);
        Assertions.assertEquals(1170L, department.getCurrentMoneyMomentary());
    }

    @Test
    void addMoneyAfterResetActionFullAtmOddNotes() {
        var department = new Department("store");
        var initialState1 = Map.of(
                Banknote.FIFTY, 1,
                Banknote.TEN, 1
        );
        var atm1 = new AtmBuilder("first")
                .withInitialMoney(initialState1)
                .build();
        var initialState2 = Map.of(
                Banknote.HUNDRED, 1,
                Banknote.TEN, 1
        );
        var atm2 = new AtmBuilder("second")
                .withInitialMoney(initialState2)
                .build();
        department.addAtm(atm1);
        department.addAtm(atm2);
        var putMoneyAction = new AtmPutMoneyAction(Map.of(
                Banknote.FIVE_HUNDRED, 3
        )).next(new AtmGetValueAction());
        AtmAction initiateAction = new AtmInitiateAction("Сбросить к исходному состоянию").next(putMoneyAction);
        initiateAction.process(department);
        Assertions.assertEquals(1670L, department.getCurrentMoneyMomentary());
    }
}

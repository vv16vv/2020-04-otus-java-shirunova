package ru.otus.vsh.hw07.api.actions;

import ru.otus.vsh.hw07.impl.Department;

import javax.annotation.Nonnull;

public class AtmGetValueAction extends AtmAction {
    @Override
    public void action(@Nonnull Department department) {
        department.requestMoney("узнать остаток");
        department.waitAllAtmsPolled();
        System.out.printf("Отделение '%s': текущий остаток %d рублей%n", department.id(), department.getCurrentMoneyMomentary());
    }
}

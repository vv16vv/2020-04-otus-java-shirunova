package ru.otus.vsh.hw07.api.actions;

import ru.otus.vsh.hw07.impl.Department;

public class AtmGetValueAction extends AtmAction {
    @Override
    public void action(Department department) {
        System.out.printf("Отделение '%s': остаток %d рублей%n", department.id(), department.getCurrentValue());
    }
}

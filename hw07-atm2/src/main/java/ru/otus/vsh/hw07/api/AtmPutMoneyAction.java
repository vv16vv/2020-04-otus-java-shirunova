package ru.otus.vsh.hw07.api;

import ru.otus.vsh.hw07.impl.Department;

import javax.annotation.Nonnull;
import java.util.EnumMap;
import java.util.Map;

public class AtmPutMoneyAction extends AtmAction {
    private final Map<Banknote, Integer> sum = new EnumMap(Banknote.class);

    public AtmPutMoneyAction(Map<Banknote, Integer> sum) {
        this.sum.putAll(sum);
    }

    @Override
    public void action(@Nonnull Department department) {
        department.acceptMoney(sum);
    }
}

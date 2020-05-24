package ru.otus.vsh.hw07.impl;

import ru.otus.vsh.hw07.api.Atm;
import ru.otus.vsh.hw07.api.AvailableValue;
import ru.otus.vsh.hw07.api.InitiateListener;

import java.util.ArrayList;
import java.util.List;

public class Department implements AvailableValue {
    private final List<Atm> atms = new ArrayList<>();

    public void addAtm(Atm atm) {
        atms.add(atm);
    }

    public void removeAtm(InitiateListener atm) {
        atms.remove(atm);
    }

    public void initiate(String reason) {
        atms.forEach(atm -> atm.onInitiate(reason));
    }

    @Override
    public long currentValue() {
        return atms.stream()
                .mapToLong(AvailableValue::currentValue)
                .reduce(0L, Long::sum);
    }
}

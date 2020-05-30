package ru.otus.vsh.hw07.impl;

import ru.otus.vsh.hw07.api.Atm;
import ru.otus.vsh.hw07.api.AtmListener;
import ru.otus.vsh.hw07.api.Banknote;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Department {
    private final List<Atm> atms = new ArrayList<>();
    private final String id;

    public Department(String id) {
        this.id = id;
    }

    public void addAtm(Atm atm) {
        atms.add(atm);
    }

    public void removeAtm(Atm atm) {
        atms.remove(atm);
    }

    public void acceptMoney(Map<Banknote, Integer> sum) {
        sum.forEach((banknote, quantity) -> {
            if (atms.isEmpty())
                throw new IllegalStateException(String.format("В отделении '%s' нет банкоматов!", id()));
            int quantForEachAtm = quantity / atms.size();
            int addForFirstAtm = quantity - quantForEachAtm * quantity;
            for (Atm atm : atms) {
                atm.accept(Map.of(banknote, quantForEachAtm));
            }
            if (addForFirstAtm > 0)
                atms.get(0).accept(Map.of(banknote, addForFirstAtm));
        });
    }

    public void initiate(String reason) {
        atms.forEach(atm -> {
            if (atm instanceof AtmListener) {
                ((AtmListener) atm).onInitiate(reason);
            }
        });
    }

    public long getCurrentValue() {
        return atms.stream()
                .mapToLong(Atm::currentValue)
                .reduce(0L, Long::sum);
    }

    public String id() {
        return id;
    }
}

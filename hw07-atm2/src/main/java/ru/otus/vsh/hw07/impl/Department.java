package ru.otus.vsh.hw07.impl;

import ru.otus.vsh.hw07.api.Atm;
import ru.otus.vsh.hw07.api.Banknote;
import ru.otus.vsh.hw07.api.ValueChangeOperation;
import ru.otus.vsh.hw07.api.listeners.AtmInitiateListener;
import ru.otus.vsh.hw07.api.listeners.AtmValueChangeListener;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class Department implements AtmValueChangeListener, AtmInitiateListener {
    private final List<Atm> atms = new ArrayList<>();
    private final String id;
    private final AtomicLong currentMoney = new AtomicLong(0L);

    public Department(String id) {
        this.id = id;
    }

    public void addAtm(Atm atm) {
        atms.add(atm);
    }

    public void removeAtm(Atm atm) {
        atms.remove(atm);
    }

    public void acceptMoney(@Nonnull Map<Banknote, Integer> sum) {
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
            if (atm instanceof AtmInitiateListener) {
                ((AtmInitiateListener) atm).onInitiate(reason);
            }
        });
    }

    public long getCurrentMoney() {
        return currentMoney.get();
    }

    public String id() {
        return id;
    }

    @Override
    public void onValueChange(long delta, ValueChangeOperation operation) {
        if (operation == ValueChangeOperation.HAND_OUT) currentMoney.getAndAdd(-1 * delta) ;
        else currentMoney.getAndAdd(delta);
    }

    @Override
    public void onInitiate(String reason) {

    }
}

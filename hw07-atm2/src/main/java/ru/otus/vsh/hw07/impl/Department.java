package ru.otus.vsh.hw07.impl;

import ru.otus.vsh.hw07.api.Atm;
import ru.otus.vsh.hw07.api.Banknote;
import ru.otus.vsh.hw07.api.listeners.AtmResetListener;
import ru.otus.vsh.hw07.api.listeners.AtmStatus;
import ru.otus.vsh.hw07.api.listeners.AtmValueChangeListener;
import ru.otus.vsh.hw07.api.listeners.ValueChangeOperation;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class Department implements AtmValueChangeListener, AtmResetListener {
    private final List<Atm> atms = new ArrayList<>();
    private final String id;
    private final AtomicLong currentMoney = new AtomicLong(0L);

    public Department(String id) {
        this.id = id;
    }

    public void addAtm(@Nonnull Atm atm) {
        atms.add(atm);
        atm.addAtmValueChangeListener(this);
        atm.addAtmResetListener(this);
    }

    public void removeAtm(@Nonnull Atm atm) {
        atm.removeAtmResetListener();
        atm.removeAtmValueChangeListener();
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
        synchronized (this) {
            currentMoney.set(0L);
            atms.forEach(atm -> new Thread(() -> atm.reset(reason), atm.id()).start());
        }
    }

    public long getCurrentMoney() {
        return currentMoney.get();
    }

    public String id() {
        return id;
    }

    @Override
    public void onValueChange(long delta, ValueChangeOperation operation) {
        if (operation == ValueChangeOperation.HAND_OUT) currentMoney.getAndAdd(-1 * delta);
        else currentMoney.getAndAdd(delta);
    }

    @Override
    public void onReset(AtmStatus statusAfterReset, String message, Long moneyAfterReset) {
        switch (statusAfterReset) {
            case OK: {
                System.out.println(message);
                currentMoney.getAndAdd(moneyAfterReset);
                break;
            }
            case FAILED: {
                System.out.println(message);
                break;
            }
        }
    }
}

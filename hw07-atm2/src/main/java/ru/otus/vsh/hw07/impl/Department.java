package ru.otus.vsh.hw07.impl;

import ru.otus.vsh.hw07.api.Atm;
import ru.otus.vsh.hw07.api.AtmException;
import ru.otus.vsh.hw07.api.Banknote;
import ru.otus.vsh.hw07.api.listeners.*;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class Department implements AtmValueChangeListener, AtmResetListener, AtmRequestMoneyListener {
    private final List<Atm> atms = new ArrayList<>();
    private final String id;
    private final AtomicLong currentMoney = new AtomicLong(0L);
    private final Map<String, Boolean> atmPolled = new ConcurrentHashMap<>();

    public Department(String id) {
        this.id = id;
    }

    public void addAtm(@Nonnull Atm atm) {
        atms.add(atm);
        atmPolled.put(atm.id(), false);
        atm.addAtmValueChangeListener(this);
        atm.addAtmResetListener(this);
        atm.addAtmRequestMoneyListener(this);
        currentMoney.getAndAdd(atm.currentMoney());
    }

    public void removeAtm(@Nonnull Atm atm) {
        atm.removeAtmResetListener();
        atm.removeAtmValueChangeListener();
        atm.removeAtmRequestMoneyListener();
        atmPolled.remove(atm.id());
        atms.remove(atm);
        currentMoney.getAndAdd(-1 * atm.currentMoney());
    }

    public void acceptMoney(@Nonnull Map<Banknote, Integer> sum) {
        sum.forEach((banknote, quantity) -> {
            if (atms.isEmpty())
                throw new IllegalStateException(String.format("В отделении '%s' нет банкоматов!", id()));
            int quantForEachAtm = quantity / atms.size();
            int addForFirstAtm = quantity - quantForEachAtm * atms.size();
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
            atmPolled.keySet().forEach(key -> atmPolled.put(key, false));
            atms.forEach(atm -> new Thread(() -> atm.reset(reason), atm.id()).start());
        }
    }

    public void requestMoney(String reason) {
        synchronized (this) {
            currentMoney.set(0L);
            atmPolled.keySet().forEach(key -> atmPolled.put(key, false));
            atms.forEach(atm -> new Thread(atm::calculateCurrentMoney, atm.id()).start());
        }
    }

    public long getCurrentMoneyMomentary() {
        return currentMoney.get();
    }

    private boolean isUnPolledAtms() {
        System.out.println("isUnPolledAtms: atmPolled = " + atmPolled.toString());
        return atmPolled.entrySet().stream()
                .anyMatch(entry -> entry.getValue().equals(false));
    }

    @SuppressWarnings("BusyWait")
    public void waitAllAtmsPolled() {
        var timeout = 5000L;
        var start = System.currentTimeMillis();
        var current = start;
        while (isUnPolledAtms() && (current - start) < timeout) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException ignore) {
            }
            current = System.currentTimeMillis();
        }
    }

    public String id() {
        return id;
    }

    @Override
    public void onValueChange(long delta, ValueChangeOperation operation, String atmId) {
        synchronized (this) {
            if (operation == ValueChangeOperation.HAND_OUT) currentMoney.getAndAdd(-1 * delta);
            else currentMoney.getAndAdd(delta);
            atmPolled.put(atmId, true);
        }
    }

    @Override
    public void onReset(@Nonnull AtmStatus statusAfterReset, String message, String atmId) {
        switch (statusAfterReset) {
            case OK: {
                System.out.println(message);
                break;
            }
            case FAILED:
                throw new AtmException(message);
        }
    }

    @Override
    public void onRequestMoney(long moneyState, String atmId) {
        synchronized (this) {
            currentMoney.getAndAdd(moneyState);
            atmPolled.put(atmId, true);
        }
    }
}

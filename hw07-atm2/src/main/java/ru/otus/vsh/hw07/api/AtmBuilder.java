package ru.otus.vsh.hw07.api;

import javax.annotation.Nonnull;
import java.util.Map;

import ru.otus.vsh.hw07.api.listeners.AtmRequestMoneyListener;
import ru.otus.vsh.hw07.api.listeners.AtmResetListener;
import ru.otus.vsh.hw07.api.listeners.AtmValueChangeListener;
import ru.otus.vsh.hw07.impl.AtmImpl;

public class AtmBuilder {
    private final String id;
    private Map<Banknote, Integer> initialMoney = null;
    private AtmValueChangeListener valueChangeListener = null;
    private AtmResetListener resetListener = null;
    private AtmRequestMoneyListener requestMoneyListener = null;

    public AtmBuilder(String id) {
        this.id = id;
    }

    public AtmBuilder setInitialMoney(@Nonnull Map<Banknote, Integer> initialMoney) {
        this.initialMoney = initialMoney;
        return this;
    }

    public AtmBuilder setAtmValueChangeListener(AtmValueChangeListener valueChangeListener){
        this.valueChangeListener = valueChangeListener;
        return this;
    }

    public AtmBuilder setAtmResetListener(AtmResetListener resetListener){
        this.resetListener = resetListener;
        return this;
    }

    public AtmBuilder setAtmRequestMoneyListener(AtmRequestMoneyListener requestMoneyListener){
        this.requestMoneyListener = requestMoneyListener;
        return this;
    }

    public Atm build(){
        return AtmImpl.AtmCreator(id, initialMoney, valueChangeListener, resetListener, requestMoneyListener);
    }

}

package ru.otus.vsh.hw07.api;

import ru.otus.vsh.hw07.api.listeners.AtmRequestMoneyListener;
import ru.otus.vsh.hw07.api.listeners.AtmResetListener;
import ru.otus.vsh.hw07.api.listeners.AtmValueChangeListener;

import java.util.Map;

public interface Atm {
    String id();

    /**
     * Filling ATM up
     *
     * @param income what banknotes and how many of every value
     */
    void accept(Map<Banknote, Integer> income);

    /**
     * @param sum expected money
     * @return list of banknotes composing the [sum]
     * @throws CantHandOutMoneyException if any problem with forming hand out occurs
     */
    Map<Banknote, Integer> handout(long sum);

    /**
     * @return money sum of all banknotes in the ATM at the moment
     */
    long currentMoney();

    /**
     * Initiate collecting of current values
     * */
    void calculateCurrentMoney();

    /**
     * put the ATM to initial state
     */
    void reset(String reason);

    /**
     * Add listener to catch changes in money
     */
    void addAtmValueChangeListener(AtmValueChangeListener listener);

    void removeAtmValueChangeListener();

    /**
     * Add listener to catch resetting the ATM to the initial state
     */
    void addAtmResetListener(AtmResetListener listener);

    void removeAtmResetListener();

    /**
     * Add listener to catch returning the current money state
     */
    void addAtmRequestMoneyListener(AtmRequestMoneyListener listener);

    void removeAtmRequestMoneyListener();
}

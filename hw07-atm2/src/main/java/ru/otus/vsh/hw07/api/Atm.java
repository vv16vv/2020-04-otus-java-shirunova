package ru.otus.vsh.hw07.api;

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
    long currentValue();
}

package ru.otus.vsh.hw07.api;

public interface AvailableValue {
    /**
     * @return money sum of all banknotes in the ATM at the moment
     */
    long currentValue();
}

package ru.otus.vsh.hw06.api;

public class CantHandOutMoneyException extends RuntimeException {
    public CantHandOutMoneyException(long sum) {
        super(String.format("Не могу выдать %d рублей", sum));
    }
}

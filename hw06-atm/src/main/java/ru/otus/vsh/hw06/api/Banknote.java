package ru.otus.vsh.hw06.api;

public enum Banknote {
    FIVE_THOUSAND(5000),
    TWO_THOUSAND(2000),
    THOUSAND(1000),
    FIVE_HUNDRED(500),
    TWO_HUNDRED(200),
    HUNDRED(100),
    FIFTY(50),
    TEN(10);

    private final long nominal;

    Banknote(long nominal) {
        this.nominal = nominal;
    }

    public long getNominal() {
        return nominal;
    }
}

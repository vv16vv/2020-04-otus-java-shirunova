package ru.otus.vsh.hw06.api;

public interface Cell {
    Banknote getBanknote();

    boolean isEmpty();
    int getCount();

    void add(int quantity);
    void handout(int quantity);

    long getSum();
}

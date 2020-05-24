package ru.otus.vsh.hw07.api;

import java.util.Map;

public interface AtmState {
    Map<Banknote, Cell> getCells();

    AtmState copy();
}

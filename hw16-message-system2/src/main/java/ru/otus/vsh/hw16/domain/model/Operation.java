package ru.otus.vsh.hw16.domain.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Operation {
    MULTIPLICATION("*"),
    DIVISION("/")
    ;

    private final String op;

    @Override
    public String toString() {
        return op;
    }
}

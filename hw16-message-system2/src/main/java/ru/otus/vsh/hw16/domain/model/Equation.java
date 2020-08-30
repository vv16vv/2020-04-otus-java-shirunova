package ru.otus.vsh.hw16.domain.model;

import lombok.Getter;

@Getter
public abstract class Equation {
    protected final int leftPart;
    protected final int rightPart;
    protected final int result;
    protected final Operation operation;

    public Equation(int leftPart, int rightPart, Operation operation) {
        this.leftPart = leftPart;
        this.rightPart = rightPart;
        this.operation = operation;
        this.result = calcResult();
    }

    protected abstract int calcResult();

    @Override
    public String toString() {
        return String.format("%d %s %d = ?", leftPart, operation, rightPart);
    }

    public String toStringWithResult() {
        return String.format("%d %s %d = %d", leftPart, operation, rightPart, result);
    }
}

package ru.otus.vsh.hw16.domain.model;

public class MultiplicationEquation extends Equation {

    public MultiplicationEquation(int leftPart, int rightPart) {
        super(leftPart, rightPart, Operation.MULTIPLICATION);
    }

    @Override
    protected int calcResult() {
        return leftPart * rightPart;
    }

}

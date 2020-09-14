package ru.otus.vsh.hw16.domain.model;

public class DivisionEquation extends Equation {

    public DivisionEquation(int leftPart, int rightPart) {
        super(leftPart, rightPart, Operation.DIVISION);
    }

    @Override
    protected int calcResult() {
        return leftPart / rightPart;
    }

}

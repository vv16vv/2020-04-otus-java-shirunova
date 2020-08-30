package ru.otus.vsh.hw16.domain.services;

import ru.otus.vsh.hw16.domain.model.Equation;

import java.util.List;

public interface EquationPreparer {
    List<Equation> prepareEquationsFor(int base);
}

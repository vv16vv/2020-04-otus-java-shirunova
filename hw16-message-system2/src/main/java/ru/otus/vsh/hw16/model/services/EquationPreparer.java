package ru.otus.vsh.hw16.model.services;

import ru.otus.vsh.hw16.model.domain.Equation;

import java.util.List;

public interface EquationPreparer {
    List<Equation> prepareEquationsFor(int base);
}

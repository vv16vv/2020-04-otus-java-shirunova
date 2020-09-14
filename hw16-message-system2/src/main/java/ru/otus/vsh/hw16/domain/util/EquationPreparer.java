package ru.otus.vsh.hw16.domain.util;

import lombok.experimental.UtilityClass;
import ru.otus.vsh.hw16.domain.messageSystemClient.data.EquationData;
import ru.otus.vsh.hw16.domain.model.DivisionEquation;
import ru.otus.vsh.hw16.domain.model.MultiplicationEquation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@UtilityClass
public class EquationPreparer {
    private final int max = 10;

    public List<EquationData> get(int base) {
        List<EquationData> equations = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            var multiplicationEquation = new MultiplicationEquation(base, i + 1);
            var divisionEquation = new DivisionEquation(multiplicationEquation.getResult(), base);
            equations.add(new EquationData(multiplicationEquation));
            equations.add(new EquationData(divisionEquation));
        }
        Collections.shuffle(equations);
        return equations;
    }
}

package ru.otus.vsh.hw06.impl;

import com.google.common.collect.Maps;
import ru.otus.vsh.hw06.api.Atm;
import ru.otus.vsh.hw06.api.Banknote;
import ru.otus.vsh.hw06.api.CantHandOutMoneyException;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class AtmImpl implements Atm {
    private final Map<Banknote, Integer> cells = new EnumMap<>(Banknote.class);
    private Banknote minimal = null;

    @Override
    public void accept(Map<Banknote, Integer> income) {
        income.forEach((note, quantity) -> {
            if (quantity < 0)
                throw new IllegalStateException(String.format("Количество банкнот не может быть отрицательным (%d)", quantity));
            if (minimal == null || minimal.getNominal() > note.getNominal()) minimal = note;
            Integer currentQuantity = cells.getOrDefault(note, 0);
            cells.put(note, quantity + currentQuantity);
        });
    }

    @Override
    public Map<Banknote, Integer> handout(long sum) {
        if (sum <= 0 )
            throw new IllegalArgumentException(String.format("Не могу выдать отрицательную сумму %s", sum));
        if (minimal == null )
            throw new IllegalStateException("Банкомат не инициализирован");
        if (sum % minimal.getNominal() != 0 || sum > currentValue())
            throw new CantHandOutMoneyException(sum);
        Map<Banknote, Integer> result = new HashMap<>();
        var copyCells = Maps.newEnumMap(cells);
        long remainingSum = sum;
        var iterator = copyCells.keySet().iterator();
        while (remainingSum > 0 && iterator.hasNext()) {
            Banknote note = iterator.next();
            int expectedQuantity = (int) (remainingSum / note.getNominal());
            int presentQuantity = copyCells.getOrDefault(note, 0);
            if (expectedQuantity > presentQuantity) expectedQuantity = presentQuantity;
            if (expectedQuantity > 0) {
                result.put(note, expectedQuantity);
                remainingSum = remainingSum - expectedQuantity * note.getNominal();
                copyCells.put(note, presentQuantity - expectedQuantity);
            }
        }
        if (remainingSum > 0) {
            throw new CantHandOutMoneyException(sum);
        }
        else {
            cells.putAll(copyCells);
        }
        return result;
    }

    @Override
    public long currentValue() {
        return cells.keySet().stream()
                .mapToLong(note -> note.getNominal() * cells.getOrDefault(note, 0))
                .sum();
    }
}

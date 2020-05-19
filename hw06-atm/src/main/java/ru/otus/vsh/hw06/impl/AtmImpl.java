package ru.otus.vsh.hw06.impl;

import ru.otus.vsh.hw06.api.Atm;
import ru.otus.vsh.hw06.api.Banknote;
import ru.otus.vsh.hw06.api.CantHandOutMoneyException;
import ru.otus.vsh.hw06.api.Cell;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.Map;

public class AtmImpl implements Atm {
    private final Map<Banknote, Cell> cells = new EnumMap<>(Banknote.class);
    private Banknote minimal = null;

    @Override
    public void accept(@Nonnull Map<Banknote, Integer> income) {
        income.forEach((note, quantity) -> {
            cells.putIfAbsent(note, new CellImpl(note));
            cells.get(note).add(quantity);
        });
        calculateMinimalBanknote();
    }

    private void calculateMinimalBanknote() {
        minimal = cells.values().stream()
                .filter(cell -> !cell.isEmpty())
                .map(Cell::getBanknote)
                .min(Comparator.comparingLong(Banknote::getNominal))
                .orElseThrow(IllegalStateException::new);
    }

    @Override
    public Map<Banknote, Integer> handout(long sum) {
        if (minimal == null)
            throw new IllegalStateException("Банкомат не загружен");
        if (sum <= 0) {
            throw new IllegalArgumentException(String.format("Не могу выдать отрицательную сумму %s", sum));
        }
        if (sum % minimal.getNominal() != 0 || sum > currentValue())
            throw new CantHandOutMoneyException(sum);
        Map<Banknote, Integer> result = new EnumMap<>(Banknote.class);
        long remainingSum = sum;
        var iterator = cells.values().iterator();
        while (remainingSum > 0 && iterator.hasNext()) {
            Cell cell = iterator.next();
            int presentQuantity = cell.getCount();
            if (presentQuantity == 0) continue;
            int expectedQuantity = (int) (remainingSum / cell.getBanknote().getNominal());
            if (expectedQuantity > presentQuantity) expectedQuantity = presentQuantity;
            if (expectedQuantity > 0) {
                result.put(cell.getBanknote(), expectedQuantity);
                remainingSum = remainingSum - expectedQuantity * cell.getBanknote().getNominal();
            }
        }
        if (remainingSum > 0) {
            throw new CantHandOutMoneyException(sum);
        } else {
            result.forEach((key, value) -> cells.get(key).handout(value));
            calculateMinimalBanknote();
        }
        return result;
    }

    @Override
    public long currentValue() {
        return cells.values().stream()
                .filter(cell -> !cell.isEmpty())
                .map(Cell::getSum)
                .reduce(0L, Long::sum);
    }
}

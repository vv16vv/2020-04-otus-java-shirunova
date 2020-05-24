package ru.otus.vsh.hw07.impl;

import ru.otus.vsh.hw07.api.*;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;

public class AtmImpl implements Atm {
    private final AtmMemento initialState;
    private final String id;
    private AtmState state;
    private Banknote minimal = null;


    public AtmImpl(String id) {
        this.id = id;
        this.state = new AtmStateImpl();
        this.initialState = new AtmMemento(state);
    }

    public AtmImpl(String id, Map<Banknote, Integer> initialMoney) {
        this.id = id;
        this.state = new AtmStateImpl();
        accept(initialMoney);
        this.initialState = new AtmMemento(state);
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public void accept(@Nonnull Map<Banknote, Integer> income) {
        income.forEach((note, quantity) -> {
            state.getCells().putIfAbsent(note, new CellImpl(note));
            state.getCells().get(note).add(quantity);
        });
        calculateMinimalBanknote();
        System.out.println(String.format("%s: принял следующие купюры %s", id, income.toString()));
    }

    private void calculateMinimalBanknote() {
        minimal = state.getCells().values().stream()
                .filter(cell -> !cell.isEmpty())
                .map(Cell::getBanknote)
                .min(Comparator.comparingLong(Banknote::getNominal))
                .orElseThrow(IllegalStateException::new);
    }

    @Override
    public Map<Banknote, Integer> handout(long sum) {
        if (minimal == null)
            throw new IllegalStateException(String.format("Банкомат '%s' не загружен", id));
        if (sum <= 0) {
            throw new IllegalArgumentException(String.format("%s: Не могу выдать отрицательную сумму %s", id, sum));
        }
        if (sum % minimal.getNominal() != 0 || sum > currentValue())
            throw new CantHandOutMoneyException(sum);
        Map<Banknote, Integer> result = new EnumMap<>(Banknote.class);
        long remainingSum = sum;
        var iterator = state.getCells().values().iterator();
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
            result.forEach((key, value) -> state.getCells().get(key).handout(value));
            calculateMinimalBanknote();
        }
        return result;
    }

    @Override
    public long currentValue() {
        return state.getCells().values().stream()
                .filter(cell -> !cell.isEmpty())
                .map(Cell::getSum)
                .reduce(0L, Long::sum);
    }

    @Override
    public void onInitiate(String reason) {
        System.out.println(String.format("%s: Возврат в исходное состояние по причине '%s'", id, reason));
        state = initialState.getState();
        System.out.println(String.format("%s: В наличие следующие купюры'%s'", id, state.getCells().toString()));
    }

    private static class AtmStateImpl implements AtmState {
        private final Map<Banknote, Cell> cells;

        public AtmStateImpl() {
            this.cells = new EnumMap<>(Banknote.class);
        }

        public AtmStateImpl(AtmState cells) {
            this.cells = cells.copy().getCells();
        }

        public AtmStateImpl(Map<Banknote, Cell> cells) {
            this.cells = cells;
        }

        @Override
        public Map<Banknote, Cell> getCells() {
            return cells;
        }

        public AtmState copy() {
            Map<Banknote, Cell> newCells = cells.entrySet()
                    .stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().copy(), (a, b) -> b, () -> new EnumMap<>(Banknote.class)));
            return new AtmStateImpl(newCells);
        }
    }

    private static class AtmMemento {
        private final AtmState state;

        public AtmMemento(AtmState state) {
            this.state = new AtmStateImpl(state);
        }

        public AtmState getState() {
            return state.copy();
        }
    }
}

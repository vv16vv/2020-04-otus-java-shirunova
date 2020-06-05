package ru.otus.vsh.hw07.impl;

import ru.otus.vsh.hw07.api.*;
import ru.otus.vsh.hw07.api.listeners.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;

public class AtmImpl implements Atm {
    private final String id;
    private AtmState initialState;
    private AtmState state;
    private Banknote minimal = null;
    private AtmValueChangeListener valueChangeListener = null;
    private AtmResetListener resetListener = null;
    private AtmRequestMoneyListener requestMoneyListener = null;
    private boolean isInitiated = false;

    private AtmImpl(String id) {
        this.id = id;
        this.state = new AtmStateImpl();
    }

    @Nonnull
    public static Atm AtmCreator(@Nonnull String id,
                                 @Nullable Map<Banknote, Integer> initialMoney,
                                 @Nullable AtmValueChangeListener valueChangeListener,
                                 @Nullable AtmResetListener resetListener,
                                 @Nullable AtmRequestMoneyListener requestMoneyListener) {
        var atm = new AtmImpl(id);
        if (initialMoney != null && !initialMoney.isEmpty()) {
            atm.accept(initialMoney);
        }
        atm.initiate();
        if (valueChangeListener != null)
            atm.addAtmValueChangeListener(valueChangeListener);
        if (resetListener != null)
            atm.addAtmResetListener(resetListener);
        if (requestMoneyListener != null)
            atm.addAtmRequestMoneyListener(requestMoneyListener);
        return atm;
    }

    @Override
    public void addAtmRequestMoneyListener(AtmRequestMoneyListener listener) {
        this.requestMoneyListener = listener;
    }

    @Override
    public void removeAtmRequestMoneyListener() {
        this.requestMoneyListener = null;
    }

    @Override
    public void addAtmValueChangeListener(AtmValueChangeListener listener) {
        this.valueChangeListener = listener;
    }

    @Override
    public void removeAtmValueChangeListener() {
        this.valueChangeListener = null;
    }

    @Override
    public void addAtmResetListener(AtmResetListener listener) {
        this.resetListener = listener;
    }

    @Override
    public void removeAtmResetListener() {
        this.resetListener = null;
    }

    private void initiate() {
        if (isInitiated) return;
        this.initialState = this.state.copy();
        this.isInitiated = true;
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
        if (isInitiated && valueChangeListener != null) {
            var incomeMoney = income.entrySet().stream()
                    .map(entry -> entry.getKey().getNominal() * entry.getValue())
                    .reduce(0L, Long::sum);
            valueChangeListener.onValueChange(incomeMoney, ValueChangeOperation.HAND_IN, id);
        }
        System.out.printf("АТМ '%s': принял следующие купюры %s%n", id, income.toString());
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
            throw new IllegalStateException(String.format("ATM '%s' не загружен", id));
        if (sum <= 0) {
            throw new IllegalArgumentException(String.format("ATM '%s': Не могу выдать отрицательную сумму %s", id, sum));
        }
        if (sum % minimal.getNominal() != 0 || sum > currentMoney())
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
            if (isInitiated && valueChangeListener != null) {
                valueChangeListener.onValueChange(sum, ValueChangeOperation.HAND_OUT, id);
            }
            calculateMinimalBanknote();
        }
        return result;
    }

    public long currentMoney() {
        return state.getCells().values().stream()
                .filter(cell -> !cell.isEmpty())
                .map(Cell::getSum)
                .reduce(0L, Long::sum);
    }

    @Override
    public void calculateCurrentMoney() {
        if (requestMoneyListener != null) {
            requestMoneyListener.onRequestMoney(currentMoney(), id);
        }
    }

    @Override
    public void reset(String reason) {
        System.out.printf("ATM '%s': Возврат в исходное состояние по причине '%s'%n", id, reason);
        AtmStatus status;
        String message;
        try {
            state = initialState.copy();
            status = AtmStatus.OK;
            message = String.format("ATM '%s': успешно вернулся в исходное состояние", id);
            System.out.printf("ATM '%s': В наличии следующие купюры'%s'%n", id, state.getCells().toString());
        } catch (Exception e) {
            status = AtmStatus.FAILED;
            message = String.format("ATM '%s': ошибка при возвращении в исходное состояние %s", id, e.getMessage());
        }
        if (resetListener != null) {
            resetListener.onReset(status, message, id);
        }
        if (requestMoneyListener != null) {
            requestMoneyListener.onRequestMoney(currentMoney(), id);
        }
    }

    private static class AtmStateImpl implements AtmState {
        private final Map<Banknote, Cell> cells;

        public AtmStateImpl() {
            this.cells = new EnumMap<>(Banknote.class);
        }

        public AtmStateImpl(@Nonnull Map<Banknote, Cell> cells) {
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
}

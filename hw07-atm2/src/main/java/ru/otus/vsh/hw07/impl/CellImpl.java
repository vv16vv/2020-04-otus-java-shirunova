package ru.otus.vsh.hw07.impl;

import ru.otus.vsh.hw07.api.Banknote;
import ru.otus.vsh.hw07.api.Cell;

public class CellImpl implements Cell, Cloneable {
    private final Banknote banknote;
    private int count = 0;

    public CellImpl(Banknote banknote) {
        this.banknote = banknote;
    }

    CellImpl(Banknote banknote, int count) {
        this.banknote = banknote;
        this.count = count;
    }

    @Override
    public Banknote getBanknote() {
        return banknote;
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public void add(int quantity) {
        if (quantity <= 0)
            throw new IllegalArgumentException(String.format("Количество добавляемых банкнот %d не может быть отрицательным", quantity));
        count += quantity;
    }

    @Override
    public void handout(int quantity) {
        if (quantity <= 0)
            throw new IllegalArgumentException(String.format("Количество выдаваемых банкнот %d не может быть отрицательным", quantity));
        if (quantity > count) throw new IllegalArgumentException("Недостаточное количество банкнот");
        count -= quantity;
    }

    @Override
    public long getSum() {
        return count * banknote.getNominal();
    }

    @Override
    public String toString() {
        return String.format("%s: %d", banknote, count);
    }

    @Override
    public Cell copy() {
        return new CellImpl(banknote, count);
    }
}

package ru.otus.vsh.hw07.api.listeners;

import ru.otus.vsh.hw07.api.ValueChangeOperation;

public interface AtmValueChangeListener {
    void onValueChange(long delta, ValueChangeOperation operation);
}

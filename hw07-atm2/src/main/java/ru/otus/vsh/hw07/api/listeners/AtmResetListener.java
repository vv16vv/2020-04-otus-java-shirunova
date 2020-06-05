package ru.otus.vsh.hw07.api.listeners;

public interface AtmResetListener {
    void onReset(AtmStatus statusAfterReset, String message, String atmId);
}

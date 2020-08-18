package ru.otus.vsh.hw16.model.services;

public interface IOService {
    void out(String message);

    String readLn(String prompt);

    int readInt(String prompt);
}

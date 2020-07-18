package ru.otus.vsh.hw14.dbCore.dbService.api;

public class DbServiceException extends RuntimeException {
    public DbServiceException(Exception e) {
        super(e);
    }
}

package ru.otus.vsh.hw12.dbCore.dbService.api;

public class DbServiceException extends RuntimeException {
    public DbServiceException(Exception e) {
        super(e);
    }
}

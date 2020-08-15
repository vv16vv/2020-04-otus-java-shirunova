package ru.otus.vsh.hw16.dbCore.dbService.api;

public class DbServiceException extends RuntimeException {
    public DbServiceException(Exception e) {
        super(e);
    }
}

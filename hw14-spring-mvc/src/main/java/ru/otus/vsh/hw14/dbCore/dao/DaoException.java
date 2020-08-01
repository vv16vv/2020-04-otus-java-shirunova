package ru.otus.vsh.hw14.dbCore.dao;

public class DaoException extends RuntimeException {
    public DaoException(Exception ex) {
        super(ex);
    }
}

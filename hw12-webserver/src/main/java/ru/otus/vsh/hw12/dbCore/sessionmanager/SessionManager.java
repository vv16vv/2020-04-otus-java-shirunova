package ru.otus.vsh.hw12.dbCore.sessionmanager;

import javax.persistence.EntityManager;

public interface SessionManager extends AutoCloseable {
    void beginSession();

    void commitSession();

    void rollbackSession();

    void close();

    EntityManager getEntityManager();

    DatabaseSession getCurrentSession();
}

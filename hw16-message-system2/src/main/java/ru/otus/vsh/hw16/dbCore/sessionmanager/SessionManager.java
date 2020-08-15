package ru.otus.vsh.hw16.dbCore.sessionmanager;

import javax.persistence.EntityManager;

public interface SessionManager extends AutoCloseable {
    void beginSession();

    void commitSession();

    void rollbackSession();

    void close();

    EntityManager getEntityManager();

    DatabaseSession getCurrentSession();
}

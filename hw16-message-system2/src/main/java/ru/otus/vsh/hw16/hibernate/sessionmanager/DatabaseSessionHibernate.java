package ru.otus.vsh.hw16.hibernate.sessionmanager;

import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.otus.vsh.hw16.dbCore.sessionmanager.DatabaseSession;


@Getter
public class DatabaseSessionHibernate implements DatabaseSession {
    private final Session session;
    private final Transaction transaction;

    DatabaseSessionHibernate(Session session) {
        this.session = session;
        this.transaction = session.beginTransaction();
    }

    public void close() {
        if (transaction.isActive()) {
            transaction.commit();
        }
        session.close();
    }
}

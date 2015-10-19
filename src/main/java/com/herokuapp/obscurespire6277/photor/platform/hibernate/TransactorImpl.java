package com.herokuapp.obscurespire6277.photor.platform.hibernate;

import org.hibernate.Session;

import static com.herokuapp.obscurespire6277.photor.platform.hibernate.HibernateUtil.getSessionFactory;

public class TransactorImpl implements Transactor {

    @Override
    public void execute(WithVoidSession withVoidSession) {
        withVoidSession.run(getSession());
    }

    @Override
    public void execute(WithVoidReadOnlySession withVoidReadOnlySession) {
        withVoidReadOnlySession.run(getReadOnlySession());
    }

    @Override
    public <T> T execute(WithSession<T> withSession) {
        return withSession.run(getSession());
    }

    @Override
    public <T> T execute(WithReadOnlySession<T> withReadOnlySession) {
        return withReadOnlySession.run(getReadOnlySession());
    }

    @Override
    public <T extends HibernateEntity> Id<T> save(T hibernateEntity) {
        return execute(new WithSession<Id<T>>() {
            @Override
            public Id<T> run(DbSession session) {
                return session.save(hibernateEntity);
            }
        });
    }

    private DbSession getSession() {
        Session session = getSessionFactory().openSession();
        session.setDefaultReadOnly(false);
        return DbSessionImpl.of(session);
    }

    private DbSession getReadOnlySession() {
        Session session = getSessionFactory().openSession();
        session.setDefaultReadOnly(true);
        return DbSessionImpl.of(session);
    }

}

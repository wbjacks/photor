package com.herokuapp.obscurespire6277.photor.platform.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;

import static com.herokuapp.obscurespire6277.photor.platform.hibernate.HibernateUtil.getSessionFactory;

public class TransactorImpl implements Transactor {

    @Override
    public void execute(WithVoidSession withVoidSession) {
        withAbstractVoidSession(withVoidSession, getSession());
    }

    @Override
    public void execute(WithVoidReadOnlySession withVoidReadOnlySession) {
        withAbstractVoidSession(withVoidReadOnlySession, getReadOnlySession());
    }

    @Override
    public <T> T execute(WithSession<T> withSession) {
        return (T) withAbstractSession(withSession, getSession());
    }

    @Override
    public <T> T execute(WithReadOnlySession<T> withReadOnlySession) {
        return (T) withAbstractSession(withReadOnlySession, getReadOnlySession());
    }

    @Override
    public <T extends HibernateEntity> Id<T> save(T hibernateEntity) {
        return execute(new WithSession<Id<T>>() {
            @Override
            public Id<T> run(TypeSafeSessionWrapper session) {
                return session.save(hibernateEntity);
            }
        });
    }

    private Session getSession() {
        Session session = getSessionFactory().openSession();
        session.setDefaultReadOnly(false);
        return session;
    }

    private Session getReadOnlySession() {
        Session session = getSessionFactory().openSession();
        session.setDefaultReadOnly(true);
        return session;
    }

    private <T> T withAbstractSession(WithAbstractSession<T> withAbstractSession, Session session) {
        Transaction transaction = session.beginTransaction();
        T returnValue;
        try {
            returnValue = withAbstractSession.run(TypeSafeSessionWrapperImpl.of(session));
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
        transaction.commit();
        return returnValue;
    }

    private void withAbstractVoidSession(WithAbstractVoidSession withAbstractVoidSession, Session session) {
        Transaction transaction = session.beginTransaction();
        try {
            withAbstractVoidSession.run(TypeSafeSessionWrapperImpl.of(session));
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
        transaction.commit();
    }

}

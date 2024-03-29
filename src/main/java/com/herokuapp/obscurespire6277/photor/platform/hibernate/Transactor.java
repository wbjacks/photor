package com.herokuapp.obscurespire6277.photor.platform.hibernate;

public interface Transactor {

    <T extends HibernateEntity> Id<T> save(T hibernateEntity);

    void execute(WithVoidSession withVoidSession);

    void execute(WithVoidReadOnlySession withVoidReadOnlySession);

    <T> T execute(WithSession<T> withSession);

    <T> T execute(WithReadOnlySession<T> withReadOnlySession);

}

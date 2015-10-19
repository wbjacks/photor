package com.herokuapp.obscurespire6277.photor.platform.hibernate;

public interface Transactor {

    void execute(WithVoidSession withVoidSession);

    void execute(WithVoidReadOnlySession withVoidReadOnlySession);

    <T> T execute(WithSession<T> withSession);

    <T> T execute(WithReadOnlySession<T> withReadOnlySession);

}

package com.herokuapp.obscurespire6277.photor.platform.hibernate;

public interface WithReadOnlySession<T> {

    T run(DbSession readOnlySession);

}

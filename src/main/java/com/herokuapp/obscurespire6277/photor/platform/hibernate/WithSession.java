package com.herokuapp.obscurespire6277.photor.platform.hibernate;

public interface WithSession<T> {

    T run(DbSession session);

}

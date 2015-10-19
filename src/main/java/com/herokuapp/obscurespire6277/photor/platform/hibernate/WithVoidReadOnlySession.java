package com.herokuapp.obscurespire6277.photor.platform.hibernate;

public interface WithVoidReadOnlySession {

    void run(DbSession readOnlySession);

}

package com.herokuapp.obscurespire6277.photor.platform.hibernate;

public interface WithVoidReadOnlySession extends WithAbstractVoidSession {

    @Override
    void run(TypeSafeSessionWrapper readOnlySession);

}

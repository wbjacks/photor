package com.herokuapp.obscurespire6277.photor.platform.hibernate;

interface WithAbstractVoidSession {

    void run(TypeSafeSessionWrapper readOnlySession);

}

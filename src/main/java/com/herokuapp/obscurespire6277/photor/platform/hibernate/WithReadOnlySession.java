package com.herokuapp.obscurespire6277.photor.platform.hibernate;

public interface WithReadOnlySession<T> extends WithAbstractSession<T> {

    @Override
    <T> T run(TypeSafeSessionWrapper readOnlySession);

}

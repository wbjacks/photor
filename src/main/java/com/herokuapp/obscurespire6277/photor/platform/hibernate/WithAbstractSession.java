package com.herokuapp.obscurespire6277.photor.platform.hibernate;

interface WithAbstractSession<T> {

    <T> T run(TypeSafeSessionWrapper session);

}

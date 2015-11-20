package com.herokuapp.obscurespire6277.photor.platform.hibernate.types;

import org.hibernate.HibernateException;

/* https://code.google.com/p/aphillips/ */
public abstract class ImmutableUserType extends MutableUserType {

    public final boolean isMutable() {
        return false;
    }

    public Object deepCopy(Object value) throws HibernateException {
        // for immutable objects, a reference to the original is fine
        return value;
    }

}

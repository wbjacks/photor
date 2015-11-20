package com.herokuapp.obscurespire6277.photor.platform.hibernate.types;

import org.hibernate.HibernateException;
import org.hibernate.type.SerializationException;
import org.hibernate.usertype.UserType;

import java.io.Serializable;

/* https://code.google.com/p/aphillips/ */
public abstract class MutableUserType implements UserType {

    public boolean isMutable() {
        return true;
    }

    public boolean equals(Object x, Object y) throws HibernateException {
        if (x == null && y == null) {
            return true;
        }
        else if (x == null || y == null) {
            return false;
        }
        else {
            return x.equals(y);
        }
    }

    public int hashCode(Object x) throws HibernateException {
        assert (x != null);
        return x.hashCode();
    }

    public Object assemble(Serializable cached, Object owner)
            throws HibernateException {
        // also safe for mutable objects
        return deepCopy(cached);
    }

    /**
     * Disassembles the object in preparation for serialization.
     * See {@link org.hibernate.usertype.UserType#disassemble(java.lang.Object)}.
     * <p>
     * Expects {@link #deepCopy(Object)} to return a {@code Serializable}.
     * <strong>Subtypes whose {@code deepCopy} implementation returns a
     * non-serializable object must override this method.</strong>
     */
    public Serializable disassemble(Object value) throws HibernateException {
        // also safe for mutable objects
        Object deepCopy = deepCopy(value);

        if (!(deepCopy instanceof Serializable)) {
            throw new SerializationException(
                    String.format("deepCopy of %s is not serializable", value), null);
        }

        return (Serializable) deepCopy;
    }

    public Object replace(Object original, Object target, Object owner)
            throws HibernateException {
        // also safe for mutable objects
        return deepCopy(original);
    }
}
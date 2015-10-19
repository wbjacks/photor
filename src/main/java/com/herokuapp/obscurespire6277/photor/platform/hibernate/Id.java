package com.herokuapp.obscurespire6277.photor.platform.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.ResultSetIdentifierConsumer;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A type safe wrapper around a Hibernate entity's id
 */
public class Id<T extends HibernateEntity> implements UserType, ResultSetIdentifierConsumer {

    private final Long _id;

    private Id(long id) {
        _id = id;
    }

    public static <T extends HibernateEntity> Id<T> of(long id) {
        return new Id<T>(id);
    }

    public long toLong() {
        return _id;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }

        return that != null && this.getClass().equals(that.getClass()) && this._id == ((Id) that).toLong();
    }

    @Override
    public int hashCode() {
        return _id.hashCode();
    }

    // TODO: AMITH (make IDs natively persistable inside a HibernateEntity)

    @Override
    public Serializable consumeIdentifier(ResultSet resultSet) {
        return null;
    }

    @Override
    public int[] sqlTypes() {
        return new int[0];
    }

    @Override
    public Class returnedClass() {
        return null;
    }

    @Override
    public boolean equals(Object o, Object o1) throws HibernateException {
        return false;
    }

    @Override
    public int hashCode(Object o) throws HibernateException {
        return 0;
    }

    @Override
    public Id<?> nullSafeGet(ResultSet resultSet, String[] names, SessionImplementor sessionImplementor, Object o)
            throws HibernateException, SQLException {
        BigDecimal n = resultSet.getBigDecimal(names[0]);

        if (resultSet.wasNull()) {
            return null;
        }

        return null;
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object o, int i, SessionImplementor sessionImplementor) throws HibernateException, SQLException {

    }

    @Override
    public Object deepCopy(Object o) throws HibernateException {
        return null;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object o) throws HibernateException {
        return null;
    }

    @Override
    public Object assemble(Serializable serializable, Object o) throws HibernateException {
        return null;
    }

    @Override
    public Object replace(Object o, Object o1, Object o2) throws HibernateException {
        return null;
    }

}

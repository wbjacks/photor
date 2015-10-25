package com.herokuapp.obscurespire6277.photor.platform.hibernate;

/**
 * A type safe wrapper around a Hibernate entity's id
 */
public class Id<T extends HibernateEntity> {

    private Long _id;

    private Id(long id) {
        _id = id;
    }

    public static <T extends HibernateEntity> Id<T> of(long id) {
        return new Id<>(id);
    }

    public long toLong() {
        return _id;
    }

    @Override
    public String toString() {
        return String.format("Id(%s)", _id);
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

}

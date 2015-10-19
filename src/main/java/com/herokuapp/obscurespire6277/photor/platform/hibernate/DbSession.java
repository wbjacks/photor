package com.herokuapp.obscurespire6277.photor.platform.hibernate;

import org.hibernate.Session;

/**
 * A type-safe wrapper around a Hibernate session.
 */
public interface DbSession {

    <T extends HibernateEntity> Id<T> save(T entity);

    <T extends HibernateEntity> T get(Class<T> cla$$, Id<T> id);

    Session getSession();

}

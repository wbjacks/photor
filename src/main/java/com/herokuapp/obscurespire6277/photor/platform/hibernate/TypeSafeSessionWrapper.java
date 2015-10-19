package com.herokuapp.obscurespire6277.photor.platform.hibernate;

import org.hibernate.Session;

import java.util.Optional;

/**
 * A type-safe wrapper around a Hibernate session.
 */
public interface TypeSafeSessionWrapper {

    <T extends HibernateEntity> Id<T> save(T entity);

    <T extends HibernateEntity> Optional<T> get(Class<T> cla$$, Id<T> id);

    <T extends HibernateEntity> T getOrThrow(Class<T> cla$$, Id<T> id);

    Session getSession();

}

package com.herokuapp.obscurespire6277.photor.platform.hibernate;

import org.hibernate.Session;

import java.util.Optional;

class TypeSafeSessionWrapperImpl implements TypeSafeSessionWrapper {

    private final Session _session;

    private TypeSafeSessionWrapperImpl(Session session) {
        this._session = session;
    }

    static TypeSafeSessionWrapper of(Session session) {
        return new TypeSafeSessionWrapperImpl(session);
    }

    @Override
    public <T extends HibernateEntity> Id<T> save(T entity) {
        return Id.of((Long) _session.save(entity));
    }

    @Override
    public <T extends HibernateEntity> Optional<T> get(Class<T> cla$$, Id<T> id) {
        return Optional.ofNullable(_session.get(cla$$, id.toLong()));
    }

    @Override
    public <T extends HibernateEntity> T getOrThrow(Class<T> cla$$, Id<T> id) {
        return get(cla$$, id).get();
    }

    @Override
    public Session getSession() {
        return _session;
    }

}

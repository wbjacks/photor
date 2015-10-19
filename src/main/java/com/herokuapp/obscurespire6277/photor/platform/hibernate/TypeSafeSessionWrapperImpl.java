package com.herokuapp.obscurespire6277.photor.platform.hibernate;

import org.hibernate.Session;

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
    public <T extends HibernateEntity> T get(Class<T> cla$$, Id<T> id) {
        return _session.get(cla$$, id.toLong());
    }

    @Override
    public Session getSession() {
        return _session;
    }

}

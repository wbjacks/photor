package com.herokuapp.obscurespire6277.photor.platform.hibernate;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.Optional;

class TypeSafeSessionWrapperImpl implements TypeSafeSessionWrapper {
    private static final Logger _logger = Logger.getLogger(TypeSafeSessionWrapper.class);

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
    @SuppressWarnings("unchecked")
    public <T extends HibernateEntity> Optional<T> getByUniqueFieldValue(Class<T> cla$$, String field, Object value) {
        try {
            return Optional.ofNullable((T) _session.createCriteria(cla$$).add(Restrictions.eq(field, value)).uniqueResult());
        } catch (HibernateException e) {
            _logger.error(String.format("Non-unique results returned for field %s when fetching as unique for value %s.", field, value.toString()));
            return Optional.empty();
        }
    }

    @Override
    public <T> void update(T entity) {
        _session.update(entity);
    }

    @Override
    public <T extends HibernateEntity> T getOrThrow(Class<T> cla$$, Id<T> id) {
        return get(cla$$, id).get();
    }
}

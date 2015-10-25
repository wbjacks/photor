package com.herokuapp.obscurespire6277.photor.platform.hibernate;

import java.util.function.Function;

public interface HibernateEntity<T extends HibernateEntity> {

    Function<HibernateEntity, Id> TO_ID = (hibernateEntity -> hibernateEntity.getId());

    Id<T> getId();

}

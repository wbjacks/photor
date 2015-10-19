package com.herokuapp.obscurespire6277.photor.platform.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.reflections.Reflections;

import java.util.Set;

public class HibernateUtil {

    private static Set<Class<? extends HibernateEntity>> hibernateEntities =
            new Reflections("com.herokuapp.obscurespire6277.photor.entities").getSubTypesOf(HibernateEntity.class);
    private static SessionFactory _sessionFactory;

    private static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration()
                .configure(HibernateUtil.class.getResource("/hibernate.cfg.xml"));
        for (Class<? extends HibernateEntity> hibernateEntity : hibernateEntities) {
            configuration = configuration.addAnnotatedClass(hibernateEntity);
        }
        StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
        serviceRegistryBuilder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
        _sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        return _sessionFactory;
    }

    public static SessionFactory getSessionFactory() {
        if (_sessionFactory == null || _sessionFactory.isClosed()) {
            buildSessionFactory();
        }
        return _sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }

}

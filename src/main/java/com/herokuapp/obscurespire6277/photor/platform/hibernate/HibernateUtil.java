package com.herokuapp.obscurespire6277.photor.platform.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.reflections.Reflections;

public class HibernateUtil {

    private static SessionFactory _sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        if (_sessionFactory == null || _sessionFactory.isClosed())
        {
            Configuration configuration = new Configuration()
                    .configure(HibernateUtil.class.getResource("/hibernate.cfg.xml"));
            Reflections reflections = new Reflections("com.herokuapp.obscurespire6277.photor.entities");
            for (Class<? extends HibernateEntity> hibernateEntity : reflections.getSubTypesOf(HibernateEntity.class)) {
                configuration = configuration.addAnnotatedClass(hibernateEntity);
            }
            StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
            serviceRegistryBuilder.applySettings(configuration.getProperties());
            ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
            _sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        return _sessionFactory;
    }

    public static SessionFactory getSessionFactory() {
        buildSessionFactory();
        return _sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }

}

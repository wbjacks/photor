package com.herokuapp.obscurespire6277.photor.platform.hibernate;

import com.herokuapp.obscurespire6277.photor.entities.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

    private static SessionFactory _sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        if (_sessionFactory == null || _sessionFactory.isClosed())
        {
            Configuration configuration = new Configuration()
                    .configure(HibernateUtil.class.getResource("hibernate.cfg.xml"))
                    .addAnnotatedClass(User.class)
                    .addAnnotatedClass(Photo.class)
                    .addAnnotatedClass(Like.class)
                    .addAnnotatedClass(Comment.class)
                    .addAnnotatedClass(LogIn.class);
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

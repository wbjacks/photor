package com.herokuapp.obscurespire6277.photor.util;

import com.herokuapp.obscurespire6277.photor.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

    private static SessionFactory sessionFactory = buildSessionFactory();

    private static Class[] ENTITIES = new Class[] {
            User.class,
            Photo.class,
            Like.class,
            Comment.class,
            LogIn.class,
    };

    private static SessionFactory buildSessionFactory() {
        if (sessionFactory == null)
        {
            Configuration configuration = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(User.class)
                    .addAnnotatedClass(Photo.class)
                    .addAnnotatedClass(Like.class)
                    .addAnnotatedClass(Comment.class)
                    .addAnnotatedClass(LogIn.class);
            StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
            serviceRegistryBuilder.applySettings(configuration.getProperties());
            ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        return sessionFactory;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }

}

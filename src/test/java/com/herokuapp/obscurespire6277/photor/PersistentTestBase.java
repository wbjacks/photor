package com.herokuapp.obscurespire6277.photor;

import org.hibernate.Session;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import static com.herokuapp.obscurespire6277.photor.util.HibernateUtil.getSessionFactory;
import static com.herokuapp.obscurespire6277.photor.util.HibernateUtil.shutdown;

public class PersistentTestBase {

    public static Session session;

    @BeforeClass
    public static void beforeClass() {
        session = getSessionFactory().openSession();
    }

    @Before
    public void before() {
        session.beginTransaction();
    }

    @After
    public void after() {
        session.getTransaction().commit();
    }

    @AfterClass
    public static void afterClass() {
        shutdown();
    }

}

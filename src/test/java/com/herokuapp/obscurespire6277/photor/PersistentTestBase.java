package com.herokuapp.obscurespire6277.photor;

import com.herokuapp.obscurespire6277.photor.platform.hibernate.Transactor;
import com.herokuapp.obscurespire6277.photor.platform.hibernate.TransactorImpl;
import org.junit.After;
import org.junit.BeforeClass;

import static com.herokuapp.obscurespire6277.photor.platform.hibernate.HibernateUtil.shutdown;

public class PersistentTestBase {

    public static Transactor _transactor;

    @BeforeClass
    public static void beforeClass() {
        _transactor = new TransactorImpl();
    }

    @After
    public void afterClass() {
        shutdown();
    }

}

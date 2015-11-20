package com.herokuapp.obscurespire6277.photor.platform.repos;

import com.herokuapp.obscurespire6277.photor.platform.hibernate.Transactor;
import org.junit.Test;

import static org.easymock.EasyMock.*;

public class UserRepositoryServiceImplTest {

    @Test
    public void testSaveUserLoginAndUpdateTokenThrowsErrorWhenUserNotFound() {
    }

    private static class Builder {
        private final Transactor _transactor;

        private Builder(Transactor transactor) {
            _transactor = transactor;
        }

        private UserRepositoryServiceImpl build() {
            return new UserRepositoryServiceImpl(_transactor);
        }
    }

}

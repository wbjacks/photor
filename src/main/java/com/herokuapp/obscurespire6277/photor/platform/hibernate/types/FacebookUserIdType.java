package com.herokuapp.obscurespire6277.photor.platform.hibernate.types;

import com.herokuapp.obscurespire6277.photor.platform.models.FacebookLongToken;
import com.herokuapp.obscurespire6277.photor.platform.models.FacebookUserId;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import spark.utils.StringUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class FacebookUserIdType extends ImmutableUserType {
    @Override
    public int[] sqlTypes() {
        return new int[]{Types.VARCHAR};
    }

    @Override
    public Class returnedClass() {
        return FacebookUserId.class;
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
        String value = rs.getString(names[0]);
        return StringUtils.isEmpty(value) ? null : new FacebookUserId(value);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor
            session) throws HibernateException, SQLException {
        st.setString(index, value == null ? null : ((FacebookUserId) value).getId());
    }
}

package com.iliuxa.labshelperapp.model.dao;

import com.iliuxa.labshelperapp.pojo.Email;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public class EmailDao extends BaseDaoImpl<Email, Integer>{

    public EmailDao(ConnectionSource connectionSource, Class<Email> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public Email getEmail() throws SQLException {
        if(queryForAll().size() == 0){
            return null;
        } else return queryForAll().get(0);
    }

    public void replaceEmail(Email newEmail) throws SQLException {
        Email oldEmail = getEmail();
        oldEmail.setEmail(newEmail.getEmail());
        oldEmail.setPassword(newEmail.getPassword());
        update(oldEmail);
    }
}

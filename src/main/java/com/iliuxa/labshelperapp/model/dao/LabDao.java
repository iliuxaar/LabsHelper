package com.iliuxa.labshelperapp.model.dao;

import com.iliuxa.labshelperapp.pojo.Lab;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public class LabDao extends BaseDaoImpl<Lab, Integer> {

    public LabDao(ConnectionSource connectionSource, Class<Lab> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }
}

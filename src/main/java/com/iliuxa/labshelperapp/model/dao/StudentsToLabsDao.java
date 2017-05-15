package com.iliuxa.labshelperapp.model.dao;

import com.iliuxa.labshelperapp.pojo.StudentsToLabs;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public class StudentsToLabsDao extends BaseDaoImpl<StudentsToLabs, Integer> {

    public StudentsToLabsDao(ConnectionSource connectionSource, Class<StudentsToLabs> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }
}

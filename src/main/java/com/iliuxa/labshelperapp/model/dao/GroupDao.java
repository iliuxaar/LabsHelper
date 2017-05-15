package com.iliuxa.labshelperapp.model.dao;

import com.iliuxa.labshelperapp.pojo.Group;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupDao extends BaseDaoImpl<Group, Integer>{

    public GroupDao(ConnectionSource connectionSource, Class<Group> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<Group> getGroups() throws SQLException {
        return queryForAll();
    }
}

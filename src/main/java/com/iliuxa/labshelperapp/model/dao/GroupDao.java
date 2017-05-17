package com.iliuxa.labshelperapp.model.dao;

import com.iliuxa.labshelperapp.pojo.Group;
import com.iliuxa.labshelperapp.pojo.Lab;
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

    public Group createField(Group group) throws SQLException {
        if(getEqualsGroup(group) == null) create(group);
        return getEqualsGroup(group);
    }

    public Group getEqualsGroup(Group group) throws SQLException {
        List<Group> groups = queryForMatching(group);
        if (groups != null && groups.size() > 0) return groups.get(0);
        else return null;
    }
}

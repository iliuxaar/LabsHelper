package com.iliuxa.labshelperapp.model.dao;

import com.iliuxa.labshelperapp.pojo.GroupsToLabs;
import com.iliuxa.labshelperapp.pojo.Lab;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

public class GroupsToLabsDao extends BaseDaoImpl<GroupsToLabs, Integer> {

    public GroupsToLabsDao(ConnectionSource connectionSource, Class<GroupsToLabs> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public GroupsToLabs getTimesOfGroupForLab(int groupId, int labId) throws SQLException {
        return query(queryBuilder().where().eq(GroupsToLabs.FIELD_GROUP_ID, groupId)
                .eq(GroupsToLabs.FIELD_LAB_ID, labId).prepare()).get(0);
    }

    public GroupsToLabs createField(GroupsToLabs groupsToLabs) throws SQLException {
        if(getEqualsGroupsToLab(groupsToLabs) == null) create(groupsToLabs);
        return getEqualsGroupsToLab(groupsToLabs);
    }

    public GroupsToLabs getEqualsGroupsToLab(GroupsToLabs groupsToLabs) throws SQLException {
        List<GroupsToLabs> tempGroupsToLabs = queryForMatching(groupsToLabs);
        if(tempGroupsToLabs != null && tempGroupsToLabs.size() > 0) return tempGroupsToLabs.get(0);
        else return null;
    }
}

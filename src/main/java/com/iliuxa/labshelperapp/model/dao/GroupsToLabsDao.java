package com.iliuxa.labshelperapp.model.dao;

import com.iliuxa.labshelperapp.pojo.GroupsToLabs;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public class GroupsToLabsDao extends BaseDaoImpl<GroupsToLabs, Integer> {

    public GroupsToLabsDao(ConnectionSource connectionSource, Class<GroupsToLabs> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public GroupsToLabs getTimesOfGroupForLab(int groupId, int labId) throws SQLException {
        return query(queryBuilder().where().eq(GroupsToLabs.FIELD_GROUP_ID, groupId)
                .eq(GroupsToLabs.FIELD_LAB_ID, labId).prepare()).get(0);
    }

    public boolean isFieldCreated(GroupsToLabs groupsToLabs) throws SQLException {
        return queryForSameId(groupsToLabs) != null;
    }
}

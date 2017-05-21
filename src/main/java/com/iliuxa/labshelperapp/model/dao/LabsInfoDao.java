package com.iliuxa.labshelperapp.model.dao;

import com.iliuxa.labshelperapp.pojo.Group;
import com.iliuxa.labshelperapp.pojo.Lab;
import com.iliuxa.labshelperapp.pojo.LabsInfo;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

public class LabsInfoDao extends BaseDaoImpl<LabsInfo, Integer> {

    public LabsInfoDao(ConnectionSource connectionSource, Class<LabsInfo> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public LabsInfo createField(LabsInfo labsInfo) throws SQLException {
        if(getEqualsLabsInfo(labsInfo) == null) create(labsInfo);
        return getEqualsLabsInfo(labsInfo);
    }

    public LabsInfo getEqualsLabsInfo(LabsInfo labsInfo) throws SQLException {
        List<LabsInfo> temp = queryForMatching(labsInfo);
        if(temp != null && temp.size() > 0) return temp.get(0);
        else return null;
    }

    public LabsInfo getLabsInfo(LabsInfo labsInfo) throws SQLException {
        List<LabsInfo> temp = query(queryBuilder()
                .where()
                .eq(LabsInfo.FIELD_LAB_NAME, labsInfo.getLabName())
                .and()
                .eq(LabsInfo.FIELD_TERM, labsInfo.getTerm())
                .prepare());
        if(temp != null && temp.size() > 0) return temp.get(0);
        else return null;
    }
}

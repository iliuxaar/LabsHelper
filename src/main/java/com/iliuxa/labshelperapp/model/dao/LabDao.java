package com.iliuxa.labshelperapp.model.dao;

import com.iliuxa.labshelperapp.pojo.GroupsToLabs;
import com.iliuxa.labshelperapp.pojo.Lab;
import com.iliuxa.labshelperapp.pojo.LabsInfo;
import com.iliuxa.labshelperapp.pojo.Student;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

public class LabDao extends BaseDaoImpl<Lab, Integer> {

    public LabDao(ConnectionSource connectionSource, Class<Lab> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public Lab createField(Lab lab) throws SQLException {
        if(getEqualsLab(lab) == null){
            int test = create(lab);
            test++;
        }
        return getEqualsLab(lab);
    }

    public Lab getEqualsLab(Lab lab) throws SQLException {
        List<Lab> labs = queryForMatching(lab);
        if(labs != null && labs.size() > 0) return labs.get(0);
        else return null;
    }

    public List<Lab> getLabsByLabsInfoId(LabsInfo labsInfo) throws SQLException {
        return query(queryBuilder().where().eq(Lab.FIELD_LABS_INFO_ID, labsInfo.getId()).prepare());
    }

    public Lab getLab(int labsInfoId, int labNumber) throws SQLException {
        List<Lab> labs = queryForMatching(new Lab(labNumber, labsInfoId));
        if(labs.size() != 0)
            return labs.get(0);
        return null;
    }

    public Lab getLabById(int labId) throws SQLException {
        return  queryForId(labId);
    }
}

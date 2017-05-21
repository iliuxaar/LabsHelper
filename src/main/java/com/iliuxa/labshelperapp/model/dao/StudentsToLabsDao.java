package com.iliuxa.labshelperapp.model.dao;

import com.iliuxa.labshelperapp.pojo.Lab;
import com.iliuxa.labshelperapp.pojo.Student;
import com.iliuxa.labshelperapp.pojo.StudentsToLabs;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

public class StudentsToLabsDao extends BaseDaoImpl<StudentsToLabs, Integer> {

    public StudentsToLabsDao(ConnectionSource connectionSource, Class<StudentsToLabs> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public StudentsToLabs createField(StudentsToLabs studentsToLabs) throws SQLException {
        if (getEqualsStudentsToLabs(studentsToLabs) == null) create(studentsToLabs);
        return getEqualsStudentsToLabs(studentsToLabs);
    }

    public StudentsToLabs getEqualsStudentsToLabs(StudentsToLabs studentsToLabs) throws SQLException {
        List<StudentsToLabs> temp = queryForMatching(studentsToLabs);
        if(temp != null && temp.size() > 0) return temp.get(0);
        else return null;
    }

    public List<StudentsToLabs> getStudentsToLabsByLabId(Lab lab) throws SQLException {
        return query(queryBuilder().where().eq(StudentsToLabs.FIELD_LAB_ID, lab.getId()).prepare());
    }
}

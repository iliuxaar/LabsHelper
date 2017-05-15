package com.iliuxa.labshelperapp.model.dao;


import com.iliuxa.labshelperapp.pojo.Group;
import com.iliuxa.labshelperapp.pojo.Student;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

public class StudentDao extends BaseDaoImpl<Student, Integer>{

    public StudentDao(ConnectionSource connectionSource, Class<Student> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<Student> getStudentByGroup(Group group) throws SQLException {
        return query(queryBuilder().where().eq(Student.FIELD_GROUP_ID, group.getId()).prepare());
    }
}

package com.iliuxa.labshelperapp.model.dao;


import com.iliuxa.labshelperapp.pojo.Group;
import com.iliuxa.labshelperapp.pojo.Lab;
import com.iliuxa.labshelperapp.pojo.Student;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
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

    public Student createField(Student student) throws SQLException {
        if(getEqualsStudent(student) == null) create(student);
        return getEqualsStudent(student);
    }

    public Student getEqualsStudent(Student student) throws SQLException {
        List<Student> students = queryForMatching(student);
        if(students != null && students.size() > 0) return students.get(0);
        else return null;
    }

}

package com.iliuxa.labshelperapp.model;

import com.iliuxa.labshelperapp.model.dao.*;
import com.iliuxa.labshelperapp.pojo.*;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class DataBaseHelper{

    private final static String DATABASE_URL = "jdbc:sqlite:";
    private static final String DATABASE_NAME = "labsHelper.s3db";
    private static final int DATABASE_VERSION = 1;

    private ConnectionSource mConnectionSource;
    private StudentDao mStudentDao;
    private GroupDao mGroupDao;
    private GroupsToLabsDao mGroupsToLabs;
    private LabDao mLabDao;
    private StudentsToLabsDao mStudentsToLabs;

    public void openDataBase() throws SQLException {
        mConnectionSource = new JdbcConnectionSource(DATABASE_URL + DATABASE_NAME);
        createTable();
    }

    private void createTable() throws SQLException {
        TableUtils.createTableIfNotExists(mConnectionSource, Student.class);
        TableUtils.createTableIfNotExists(mConnectionSource, Group.class);
        TableUtils.createTableIfNotExists(mConnectionSource, Lab.class);
        TableUtils.createTableIfNotExists(mConnectionSource, GroupsToLabs.class);
        TableUtils.createTableIfNotExists(mConnectionSource, StudentsToLabs.class);
        //todo add tables

        getStudentDao();
        getGroupDao();

        /*mGroupDao.create(new Group("350503"));
        mGroupDao.create(new Group("350504"));
        mGroupDao.create(new Group("350505"));*/

        /*mStudentDao.create(new Student("fsg",1));
        mStudentDao.create(new Student("asd",2));
        mStudentDao.create(new Student("qwe",3));*/
    }

    public StudentDao getStudentDao() throws SQLException {
        if(mStudentDao == null){
            mStudentDao = new StudentDao(mConnectionSource, Student.class);
        }
        return mStudentDao;
    }

    public GroupDao getGroupDao() throws SQLException {
        if(mGroupDao == null){
            mGroupDao = new GroupDao(mConnectionSource, Group.class);
        }
        return mGroupDao;
    }

    public GroupsToLabsDao getmGroupsToLabs() throws SQLException {
        if(mGroupsToLabs == null){
            mGroupsToLabs = new GroupsToLabsDao(mConnectionSource, GroupsToLabs.class);
        }
        return mGroupsToLabs;
    }

    public LabDao getmLabDao() throws SQLException {
        if(mLabDao == null){
            mLabDao = new LabDao(mConnectionSource, Lab.class);
        }
        return mLabDao;
    }

    public StudentsToLabsDao getmStudentsToLabs() throws SQLException {
        if(mStudentsToLabs == null){
            mStudentsToLabs = new StudentsToLabsDao(mConnectionSource, StudentsToLabs.class);
        }
        return mStudentsToLabs;
    }

    public void closeDataBase() throws IOException {
        mConnectionSource.close();
        mGroupDao = null;
        mStudentDao = null;
    }

    public ObservableList<StudentInfo> getStudentInfo(Group group) throws SQLException {
        List<Student> students = getStudentDao().getStudentByGroup(group);
        ObservableList<StudentInfo> studentsInfo = FXCollections.observableArrayList();
        for(Student student : students){
            StudentInfo studentInfo = new StudentInfo();
            studentInfo.setStudent(student);
            //studentInfo.setMarks();
            studentsInfo.add(studentInfo);
        }
        return studentsInfo;
    }
}

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

    private ConnectionSource mConnectionSource;
    private StudentDao mStudentDao;
    private GroupDao mGroupDao;
    private GroupsToLabsDao mGroupsToLabsDao;
    private LabDao mLabDao;
    private StudentsToLabsDao mStudentsToLabsDao;
    private LabsInfoDao mLabsInfoDao;

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
        TableUtils.createTableIfNotExists(mConnectionSource, LabsInfo.class);
        //todo add tables

        initDao();

        //Student student = mStudentDao.getEqualsStudent(studentik);
        //mGroupDao.create(new Group("350503"));
        /*mGroupDao.create(new Group("350504"));
        mGroupDao.create(new Group("350505"));*/

        /*mStudentDao.create(new Student("fsg",1));
        mStudentDao.create(new Student("asd",2));
        mStudentDao.create(new Student("qwe",3));*/
    }

    private void initDao() throws SQLException {
        getStudentDao();
        getGroupDao();
        getLabDao();
        getLabsInfoDao();
        getGroupsToLabsDao();
        getStudentsToLabsDao();
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

    public GroupsToLabsDao getGroupsToLabsDao() throws SQLException {
        if(mGroupsToLabsDao == null){
            mGroupsToLabsDao = new GroupsToLabsDao(mConnectionSource, GroupsToLabs.class);
        }
        return mGroupsToLabsDao;
    }

    public LabDao getLabDao() throws SQLException {
        if(mLabDao == null){
            mLabDao = new LabDao(mConnectionSource, Lab.class);
        }
        return mLabDao;
    }

    public StudentsToLabsDao getStudentsToLabsDao() throws SQLException {
        if(mStudentsToLabsDao == null){
            mStudentsToLabsDao = new StudentsToLabsDao(mConnectionSource, StudentsToLabs.class);
        }
        return mStudentsToLabsDao;
    }

    public LabsInfoDao getLabsInfoDao() throws SQLException {
        if(mLabsInfoDao == null){
            mLabsInfoDao =  new LabsInfoDao(mConnectionSource, LabsInfo.class);
        }
        return mLabsInfoDao;
    }

    public void closeDataBase() throws IOException {
        mConnectionSource.close();
        mGroupDao = null;
        mStudentDao = null;
        mGroupsToLabsDao = null;
        mStudentsToLabsDao = null;
        mLabDao = null;
        mLabsInfoDao = null;
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

    public void createFieldsAfterDownload(LabsInfo labsInfo, Group group, Student student
            , StudentsToLabs studentsToLabs, Lab lab, GroupsToLabs groupsToLabs) throws SQLException {

        LabsInfo tempLabsInfo = mLabsInfoDao.createField(labsInfo);
        lab.setLabInfoId(tempLabsInfo.getId());
        Lab tempLab = mLabDao.createField(lab);
        Group tempGroup = mGroupDao.createField(group);
        student.setGroupId(tempGroup.getId());
        Student tempStudent = mStudentDao.createField(student);
        studentsToLabs.setStudentId(tempStudent.getId());
        studentsToLabs.setLabId(tempLab.getId());
        StudentsToLabs tempStudentsToLabs = mStudentsToLabsDao.createField(studentsToLabs);
        groupsToLabs.setLabId(tempLab.getId());
        groupsToLabs.setGroupId(tempGroup.getId());
        GroupsToLabs tempGroupsToLabs = mGroupsToLabsDao.createField(groupsToLabs);

    }
}

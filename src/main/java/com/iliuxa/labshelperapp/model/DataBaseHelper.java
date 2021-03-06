package com.iliuxa.labshelperapp.model;

import com.iliuxa.labshelperapp.model.dao.*;
import com.iliuxa.labshelperapp.pojo.*;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
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
    private EmailDao mEmailDao;

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
        TableUtils.createTableIfNotExists(mConnectionSource, Email.class);

        initDao();
    }

    private void initDao() throws SQLException {
        getStudentDao();
        getGroupDao();
        getLabDao();
        getLabsInfoDao();
        getGroupsToLabsDao();
        getStudentsToLabsDao();
        getEmailDao();
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

    public EmailDao getEmailDao() throws SQLException {
        if(mEmailDao == null){
            mEmailDao = new EmailDao(mConnectionSource, Email.class);
        }
        return mEmailDao;
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

    public List<StudentInfo> getStudentInfo(Group group) throws SQLException {
        List<Student> students = getStudentDao().getStudentByGroup(group);
        List<StudentInfo> studentsInfo = FXCollections.observableArrayList();
        for(Student student : students){
            StudentInfo studentInfo = new StudentInfo();
            studentInfo.setStudent(student);
            List<StudentsToLabs> studentsToLabs = getStudentsToLabsDao().getStudentsToLabsByStudentId(student);
            ArrayList<Integer> marks = new ArrayList<>();
            for(int i = 0; i < getLabsInfoDao().getLabsInfoById(getLabDao().getLabById(studentsToLabs.get(0).getLabId())).getLabCount(); i++){
                marks.add(i ,null);
            }
            for(StudentsToLabs stl : studentsToLabs){
                Lab lab = getLabDao().getLabById(stl.getLabId());
                Integer mark = stl.getLabMark();
                marks.add(lab.getlabNumber()-1 ,stl.getLabMark());
            }
            studentInfo.setMarks(marks);
            studentsInfo.add(studentInfo);
        }
        return studentsInfo;
    }

    public List<StudentInfo> getStudentsBySubgroup(int subGroup, Group group) throws SQLException {
        List<Student> students = getStudentDao().getStudentByGroup(group);
        List<StudentInfo> studentsInfo = FXCollections.observableArrayList();
        for(Student student : students) {
            if (student.getSubGroup() == subGroup) {
                StudentInfo studentInfo = new StudentInfo();
                studentInfo.setStudent(student);
                List<StudentsToLabs> studentsToLabs = getStudentsToLabsDao().getStudentsToLabsByStudentId(student);
                ArrayList<Integer> marks = new ArrayList<>();
                for(int i = 0; i < getLabsInfoDao().getLabsInfoById(getLabDao().getLabById(studentsToLabs.get(0).getLabId())).getLabCount(); i++){
                    marks.add(i ,null);
                }
                for(StudentsToLabs stl : studentsToLabs){
                    Lab lab = getLabDao().getLabById(stl.getLabId());
                    Integer mark = stl.getLabMark();
                    marks.add(lab.getlabNumber()-1 ,stl.getLabMark());
                }
                studentInfo.setMarks(marks);
                studentsInfo.add(studentInfo);
            }
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

    public void createLab(LabsInfo labsInfo) throws SQLException {
        if( mLabsInfoDao.getEqualsLabsInfo(labsInfo) == null) {
            LabsInfo tempLabsInfo = mLabsInfoDao.createField(labsInfo);
            for (int i = 0; i < tempLabsInfo.getLabCount(); i++) {
                mLabDao.create(new Lab(i + 1, tempLabsInfo.getId()));
            }
        } else return;
    }

    public void changeLab(LabsInfo labsInfo) throws SQLException, IOException {
        LabsInfo temp = mLabsInfoDao.getLabsInfo(labsInfo);
        int lastLabCount = temp.getLabCount();
        temp.setLabCount(labsInfo.getLabCount());
        temp.setLabName(labsInfo.getLabName());
        temp.setTerm(labsInfo.getTerm());
        mLabsInfoDao.update(temp);
        if(lastLabCount > labsInfo.getLabCount()){
            int dif = lastLabCount - labsInfo.getLabCount();
            for(int i = lastLabCount - dif; i < lastLabCount; i++){
                Lab lab = mLabDao.getEqualsLab(new Lab(i + 1 , temp.getId()));
                mLabDao.delete(lab);
                for(StudentsToLabs stl : mStudentsToLabsDao.getStudentsToLabsByLabId(lab)){
                    if (stl == null) continue;
                    mStudentsToLabsDao.delete(stl);
                    String dir = new File("").getAbsolutePath() + "\\";
                    Files.delete(new File(dir + stl.getLabPath()).toPath());
                }
                for(GroupsToLabs gtl : mGroupsToLabsDao.getGroupsToLabsByLabId(lab)){
                    if(gtl == null) continue;
                    mGroupsToLabsDao.delete(gtl);
                }
            }
        } else if (lastLabCount < labsInfo.getLabCount()){
            int dif = labsInfo.getLabCount() - lastLabCount;
            for(int i = dif - labsInfo.getLabCount(); i < labsInfo.getLabCount(); i++){
                Lab lab = new Lab(i + 1 , temp.getId());
                mLabDao.createField(lab);
            }
        }
    }

    public void deleteLab(LabsInfo labsInfo) throws SQLException {
        LabsInfo tempLabsInfo = mLabsInfoDao.getEqualsLabsInfo(labsInfo);
        mLabsInfoDao.delete(tempLabsInfo);
        for (Lab lab : mLabDao.getLabsByLabsInfoId(tempLabsInfo)){
            mLabDao.delete(lab);//todo add deleting files
            for(StudentsToLabs stl : mStudentsToLabsDao.getStudentsToLabsByLabId(lab)){
                if (stl == null) continue;
                mStudentsToLabsDao.delete(stl);
            }
            for(GroupsToLabs gtl : mGroupsToLabsDao.getGroupsToLabsByLabId(lab)){
                if(gtl == null) continue;
                mGroupsToLabsDao.delete(gtl);
            }
        }
    }

    public List<Group> getGroupsWithSameLabName(LabsInfo labsInfo) throws SQLException {
        List<Lab> labs = mLabDao.getLabsByLabsInfoId(labsInfo);
        List<Integer> groups = new ArrayList<>();
        for(Lab lab : labs){
            if(groups.indexOf(lab.getId()) == -1){
                groups.add(lab.getId());
            }
        }

        List<GroupsToLabs> groupsToLabs = new ArrayList<>();
        for(int i = 0; i < groups.size(); i++){
            groupsToLabs.addAll(mGroupsToLabsDao.getGroupsToLabsByLabId(groups.get(i)));
        }

        groups.clear();

        for (GroupsToLabs group : groupsToLabs){
            if(groupsToLabs.indexOf(group.getGroupId()) == -1){
                if(groups.indexOf(group.getGroupId()) == -1)
                groups.add(group.getGroupId());
            }
        }

        List<Group> groupList = new ArrayList<>();
        for(int i = 0; i < groups.size(); i++){
            groupList.add(mGroupDao.queryForId(groups.get(i)));
        }

        return groupList;
    }

    public GroupsToLabs getGropsToLabsInfo(int labNumber, int groupId, int subGroup, int labsInfoId) throws SQLException {
        Lab lab = mLabDao.getLab(labsInfoId, labNumber);
        if(lab != null)
            return mGroupsToLabsDao.createField(new GroupsToLabs(groupId, lab.getId(), subGroup));
        return null;
    }

    public List<StudentsLabs> getStudentsLabs(Student student) throws SQLException {
        List<StudentsLabs> studentsLabs = new ArrayList<>();
        List<StudentsToLabs> studentsToLabs;
        studentsToLabs = mStudentsToLabsDao.getStudentsToLabsByStudentId(student);
        if(studentsToLabs == null) return null;
        for(StudentsToLabs tempSTL : studentsToLabs){
            Lab lab = mLabDao.getLabById(tempSTL.getLabId());
            LabsInfo labsInfo = mLabsInfoDao.queryForId(lab.getLabInfoId());
            StudentsLabs temp = new StudentsLabs(labsInfo, lab, tempSTL);
            studentsLabs.add(temp);
        }
        return studentsLabs;
    }

    public List<StudentInfo> getLaggingStudents(Group group) throws SQLException {
        List<Student> students = getStudentDao().getStudentByGroup(group);
        List<StudentInfo> studentsInfo = FXCollections.observableArrayList();
        student: for(Student student : students) {
                StudentInfo studentInfo = new StudentInfo();
                studentInfo.setStudent(student);
                List<StudentsToLabs> studentsToLabs = getStudentsToLabsDao().getStudentsToLabsByStudentId(student);
                int labCount = getLabsInfoDao().getLabsInfoById(getLabDao().getLabById(studentsToLabs.get(0).getLabId())).getLabCount();
                if(studentsToLabs.size() < labCount) {
                    studentsInfo.add(studentInfo);
                }else{
                    for (StudentsToLabs stl : studentsToLabs) {
                        if(stl.getLabMark() < 1 & stl.getLabMark() > 10){
                            studentsInfo.add(studentInfo);
                            continue student;
                        }
                    }

                }
        }
        return studentsInfo;
    }

    public List<StudentInfo> getGoodStudents(Group group) throws SQLException {
        List<Student> students = getStudentDao().getStudentByGroup(group);
        List<StudentInfo> studentsInfo = FXCollections.observableArrayList();
        for(Student student : students) {
            StudentInfo studentInfo = new StudentInfo();
            studentInfo.setStudent(student);
            List<StudentsToLabs> studentsToLabs = getStudentsToLabsDao().getStudentsToLabsByStudentId(student);
            int labCount = getLabsInfoDao().getLabsInfoById(getLabDao().getLabById(studentsToLabs.get(0).getLabId())).getLabCount();
            if(studentsToLabs.size() == labCount) {
                for (StudentsToLabs stl : studentsToLabs) {
                    if(stl.getLabMark() > 0 & stl.getLabMark() < 11){
                        studentsInfo.add(studentInfo);

                    }
                }

            }
        }
        return studentsInfo;
    }
}

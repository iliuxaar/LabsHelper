package com.iliuxa.labshelperapp.presenter;

import com.iliuxa.labshelperapp.pojo.Email;
import com.iliuxa.labshelperapp.pojo.Group;
import com.iliuxa.labshelperapp.pojo.LabsInfo;
import com.iliuxa.labshelperapp.pojo.StudentInfo;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ILabsInfoPresenter {

    void loadLabs();
    ObservableList<LabsInfo> getLabNameList();
    ObservableList<Group> getGroupList();
    ObservableList<StudentInfo> getStudentsList();
    void getStudents(Group group) throws SQLException;
    void getStudentBySubgroup(Integer subGroup, Group group)throws SQLException;
    void getGroupsByLabName(LabsInfo labsInfo) throws SQLException;
    ArrayList<TableColumn<StudentInfo, Integer>> getStudentsInfo(LabsInfo labsInfo, Group group) throws SQLException;

    Email getEmail()throws SQLException;

    void getFilteredStudents(Integer num, Group group) throws SQLException;
}

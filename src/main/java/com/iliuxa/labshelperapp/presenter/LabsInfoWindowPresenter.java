package com.iliuxa.labshelperapp.presenter;

import com.iliuxa.labshelperapp.model.DataBaseFactory;
import com.iliuxa.labshelperapp.model.DataBaseHelper;
import com.iliuxa.labshelperapp.pojo.Email;
import com.iliuxa.labshelperapp.pojo.Group;
import com.iliuxa.labshelperapp.pojo.LabsInfo;
import com.iliuxa.labshelperapp.pojo.StudentInfo;
import com.iliuxa.labshelperapp.view.ILabsInfoWindowView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;

import java.sql.SQLException;
import java.util.ArrayList;

public class LabsInfoWindowPresenter implements ILabsInfoPresenter{

    private ILabsInfoWindowView mView;

    private ObservableList<StudentInfo> mStudents;
    private ObservableList<Group> mGroups;
    private ObservableList<LabsInfo> mLabsName;
    private DataBaseHelper mDataBaseHelper;
    private ArrayList<Integer> mLabNumber = new ArrayList<>();

    public LabsInfoWindowPresenter(ILabsInfoWindowView mView) {
        this.mView = mView;
        mDataBaseHelper = DataBaseFactory.getInstance().getDataBase();
        mStudents = FXCollections.observableArrayList();
        mGroups = FXCollections.observableArrayList();
        mLabsName = FXCollections.observableArrayList();
    }

    private void getLabsName() throws SQLException {
        mLabsName.addAll(mDataBaseHelper.getLabsInfoDao().queryForAll());
    }

    @Override
    public void getStudents(Group group) throws SQLException {
        mStudents.clear();
        mStudents.addAll(mDataBaseHelper.getStudentInfo(group));
    }

    @Override
    public void getGroupsByLabName(LabsInfo labsInfo) throws SQLException {
        mGroups.clear();
        mGroups.addAll(DataBaseFactory.getInstance().getDataBase().getGroupsWithSameLabName(labsInfo));
    }

    @Override
    public ArrayList<TableColumn<StudentInfo, Integer>> getStudentsInfo(LabsInfo labsInfo, Group group) throws SQLException {
        ArrayList<TableColumn<StudentInfo, Integer>> columns = new ArrayList<>();
        mLabNumber.clear();
        mStudents.clear();
        for (int i = 0; i < labsInfo.getLabCount(); i++) {
            mLabNumber.add(i + 1);
        }
        mStudents.addAll(mDataBaseHelper.getStudentInfo(group));
        for(int lab : mLabNumber){
            columns.add(new TableColumn<>("À‡· " + lab));
        }
        return columns;
    }

    @Override
    public Email getEmail() throws SQLException {
        return mDataBaseHelper.getEmailDao().getEmail();
    }

    @Override
    public void getStudentBySubgroup(Integer subGroup, Group group) throws SQLException {
        mStudents.clear();
        mStudents.addAll(mDataBaseHelper.getStudentsBySubgroup(subGroup, group));
    }

    @Override
    public ObservableList<LabsInfo> getLabNameList() {
        try {
            getLabsName();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mLabsName;
    }

    @Override
    public ObservableList<Group> getGroupList() {
        return mGroups;
    }

    @Override
    public ObservableList<StudentInfo> getStudentsList() {
        return mStudents;
    }

    @Override
    public void loadLabs(){

    }

    @Override
    public void getFilteredStudents(Integer num, Group group) throws SQLException {
        if(num == 1){
            mStudents.clear();
            mStudents.addAll(mDataBaseHelper.getLaggingStudents(group));
        }else if( num == 2){
            mStudents.clear();
            mStudents.addAll(mDataBaseHelper.getGoodStudents(group));
        }
    }
}

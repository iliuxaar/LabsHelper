package com.iliuxa.labshelperapp.view;

import com.iliuxa.labshelperapp.model.DataBaseFactory;
import com.iliuxa.labshelperapp.pojo.*;
import com.iliuxa.labshelperapp.util.DateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Date;

public class DatesDialog {

    private ObservableList<LabsInfo> mLabName;
    private ObservableList<Group> mGroups;
    private ObservableList<Integer> mLabNumber;
    private ObservableList<Integer> mSubGroup;

    private Stage dialogStage;
    private GroupsToLabs mCurrentGroupsToLabs;

    @FXML private TableView<Group> groupNumberList;
    @FXML private TableView<LabsInfo> labNameList;
    @FXML private TableColumn<Group, String> groupCell;
    @FXML private TableColumn<LabsInfo, String> labNameCell;
    @FXML private ChoiceBox<Integer> subGroupPicker;
    @FXML private ChoiceBox<Integer> labNumberPicker;
    @FXML private DatePicker beginDate;
    @FXML private DatePicker endDate;

    @FXML
    private void initialize() throws SQLException {
        mLabName = FXCollections.observableArrayList();
        mGroups = FXCollections.observableArrayList();
        mLabNumber = FXCollections.observableArrayList();
        mSubGroup = FXCollections.observableArrayList(1,2);

        mLabName.addAll(DataBaseFactory.getInstance().getDataBase().getLabsInfoDao().queryForAll());

        groupCell.setCellValueFactory(cellData -> cellData.getValue().groupProperty());
        labNameCell.setCellValueFactory(cellData -> cellData.getValue().labNameProperty());

        subGroupPicker.setItems(mSubGroup);
        subGroupPicker.setValue(mSubGroup.get(0));

        groupNumberList.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> updateFields());

        labNameList.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> updateGroups(newValue));

        labNumberPicker.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> updateContent());

        subGroupPicker.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> updateContent());

        groupNumberList.setItems(mGroups);
        labNameList.setItems(mLabName);
        labNumberPicker.setItems(mLabNumber);
        subGroupPicker.setItems(mSubGroup);
        subGroupPicker.setValue(mSubGroup.get(0));
    }

    private void updateFields() {
            mLabNumber.clear();
            for (int i = 0; i < labNameList.getSelectionModel().getSelectedItem().getLabCount(); i++) {
                mLabNumber.add(i + 1);
            }
            updateContent();
    }

    private void updateContent(){
        try {
            int labNumber = labNumberPicker.getValue();
            int groupId = groupNumberList.getSelectionModel().getSelectedItem().getId();
            int subGroup = subGroupPicker.getValue();
            int labsInfoId = labNameList.getSelectionModel().getSelectedItem().getId();
            mCurrentGroupsToLabs = DataBaseFactory.getInstance().getDataBase()
                    .getGropsToLabsInfo(labNumber, groupId, subGroup, labsInfoId);
            if(mCurrentGroupsToLabs != null) {
                String beginDateString = DateUtil.format(mCurrentGroupsToLabs.getBeginTime());
                String endDateString = DateUtil.format(mCurrentGroupsToLabs.getDeadLine());
                    beginDate.setValue(DateUtil.getLocalDate(beginDateString));
                    endDate.setValue(DateUtil.getLocalDate(endDateString));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateGroups(LabsInfo newValue) {
        try {
            mGroups.addAll(DataBaseFactory.getInstance().getDataBase().getGroupsWithSameLabName(newValue));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onApplyClick() throws SQLException {
        int labNumber = labNumberPicker.getValue();
        int groupId = groupNumberList.getSelectionModel().getSelectedItem().getId();
        int subGroup = subGroupPicker.getValue();
        int labsInfoId = labNameList.getSelectionModel().getSelectedItem().getId();
        Date beginTime = DateUtil.parse(DateUtil.format(beginDate.getValue()));
        Date endTime = DateUtil.parse(DateUtil.format(endDate.getValue()));
        if(beginTime != null)
            mCurrentGroupsToLabs.setBeginTime(beginTime);
        if(endTime !=null)
            mCurrentGroupsToLabs.setDeadLine(endTime);
        DataBaseFactory.getInstance().getDataBase().getGroupsToLabsDao().update(mCurrentGroupsToLabs);
    }

    public void onCancelClick(){
        dialogStage.close();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
}

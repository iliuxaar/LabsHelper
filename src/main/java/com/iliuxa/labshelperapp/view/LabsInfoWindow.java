package com.iliuxa.labshelperapp.view;


import com.iliuxa.labshelperapp.application.MainApp;
import com.iliuxa.labshelperapp.model.DataBaseFactory;
import com.iliuxa.labshelperapp.model.LabsLoader;
import com.iliuxa.labshelperapp.pojo.*;
import com.iliuxa.labshelperapp.model.DataBaseHelper;
import com.iliuxa.labshelperapp.util.DateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class LabsInfoWindow implements BaseView{

    private MainApp mainApp;

    private ObservableList<StudentInfo> mStudents;
    private ObservableList<Group> mGroups;
    private ObservableList<LabsInfo> mLabsName;

    private ArrayList<Integer> mLabNumber = new ArrayList<>();

    private DataBaseHelper mDataBaseHelper;

    @FXML private TableView<Group> groupList;
    @FXML private TableColumn<Group, String> groupCell;
    @FXML private TableView<LabsInfo> labNameList;
    @FXML private TableColumn<LabsInfo, String> labNameCell;
    @FXML private TableView<StudentInfo> studentTable;
    @FXML private TableColumn<StudentInfo, String> studentNameCell;
    private ToggleGroup subGroupFilter = new ToggleGroup();
    @FXML private ToggleButton subGroup_1;
    @FXML private ToggleButton subGroup_2;

    @FXML
    private void initialize() throws IOException, SQLException, MessagingException {
        mDataBaseHelper = DataBaseFactory.getInstance().getDataBase();
        //LabsLoader labsLoader = new LabsLoader();
        //labsLoader.downloadEmailAttachments("iliuxa.ariko@gmail.com", "iliuxa250595");
        initLists();
        initColumn();
        initListeners();
        initToggleButton();
        //todo add disable subgroups
        labNameList.setItems(mLabsName);
        groupList.setItems(mGroups);
        studentTable.setItems(mStudents);
    }

    private void initLists(){
        try {
            mLabsName = FXCollections.observableArrayList();
            mLabsName.addAll(mDataBaseHelper.getLabsInfoDao().queryForAll());
            mStudents = FXCollections.observableArrayList();
            mGroups = FXCollections.observableArrayList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initListeners(){
        labNameList.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> onLabNameClick(newValue));
        groupList.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> onGroupClick());
        subGroupFilter.selectedToggleProperty().addListener(event->{
                if(subGroupFilter.getSelectedToggle() != null){
                    ToggleButton temp = (ToggleButton)subGroupFilter.getSelectedToggle();
                    if((Integer)temp.getUserData() == 1) {
                        subGroup_1.setStyle("-fx-base: lightgreen;");
                        subGroup_2.setStyle(null);
                    } else{
                        subGroup_2.setStyle("-fx-base: lightgreen;");
                        subGroup_1.setStyle(null);
                    }
                    onSubGroupFilterClick((Integer) subGroupFilter.getSelectedToggle().getUserData());
                } else {
                    subGroup_1.setStyle(null);
                    subGroup_2.setStyle(null);
                    onSubGroupFilterCancel();
                }
            });
        studentTable.setOnMouseClicked(event -> {
            System.out.println(event.getClickCount());
            if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
                try {
                    StudentInfo studentInfo = studentTable.getSelectionModel().getSelectedItem();
                    mainApp.showStudentInfo(studentInfo.getStudent());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initColumn(){
        studentNameCell.setCellValueFactory(cellData -> cellData.getValue().getStudent().nameProperty());
        groupCell.setCellValueFactory(cellData -> cellData.getValue().groupProperty());
        labNameCell.setCellValueFactory(cellData -> cellData.getValue().labNameProperty());
    }

    private void initToggleButton(){
        subGroup_1.setToggleGroup(subGroupFilter);
        subGroup_2.setToggleGroup(subGroupFilter);
        subGroup_1.setUserData(1);
        subGroup_2.setUserData(2);
    }

    private void onGroupClick() {
        mLabNumber.clear();
        mStudents.clear();
        for (int i = 0; i < labNameList.getSelectionModel().getSelectedItem().getLabCount(); i++) {
            mLabNumber.add(i + 1);
        }
        try {
            Group group = groupList.getSelectionModel().getSelectedItem();
            mStudents.addAll(mDataBaseHelper.getStudentInfo(group));
            for(int lab : mLabNumber){
                TableColumn<StudentInfo, Integer> column = new TableColumn<>("À‡· " + lab);
                studentTable.getColumns().add(column);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        //todo create studentsInfo
    }

    private void onLabNameClick(LabsInfo newValue){
        mGroups.clear();
        try {
            mGroups.addAll(DataBaseFactory.getInstance().getDataBase().getGroupsWithSameLabName(newValue));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void onStudentClick(StudentInfo newValue) {

    }

    private void onSubGroupFilterCancel() {
        mStudents.clear();
        try {
            mStudents.addAll(mDataBaseHelper.getStudentInfo(groupList.getSelectionModel().getSelectedItem()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void onSubGroupFilterClick(Integer subGroup) {
        mStudents.clear();
        try {
            mStudents.addAll(mDataBaseHelper.getStudentsBySubgroup(subGroup, groupList.getSelectionModel().getSelectedItem()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setMainApp(MainApp app) {
        mainApp = app;
    }
}

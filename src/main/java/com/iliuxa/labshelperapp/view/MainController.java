package com.iliuxa.labshelperapp.view;


import com.iliuxa.labshelperapp.model.DataBaseFactory;
import com.iliuxa.labshelperapp.model.LabsLoader;
import com.iliuxa.labshelperapp.pojo.Group;
import com.iliuxa.labshelperapp.pojo.Student;
import com.iliuxa.labshelperapp.model.DataBaseHelper;
import com.iliuxa.labshelperapp.pojo.StudentInfo;
import com.iliuxa.labshelperapp.pojo.StudentsToLabs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.SQLException;


public class MainController {

    private ObservableList<StudentInfo> mStudents;
    private ObservableList<Group> mGroups;

    private DataBaseHelper mDataBaseHelper;

    @FXML private TableView<Group> group_list;
    @FXML private TableView<StudentInfo> student_table;
    @FXML private TableColumn<Group, String> group;
    @FXML private TableColumn<StudentInfo, String> name;
    @FXML private TableColumn<StudentInfo, Integer> lab_1;
    @FXML private TableColumn<StudentInfo, Integer> lab_2;
    @FXML private TableColumn<StudentInfo, Integer> lab_3;
    @FXML private TableColumn<StudentInfo, Integer> lab_4;
    @FXML private TableColumn<StudentInfo, Integer> lab_5;
    @FXML private TableColumn<StudentInfo, Integer> lab_6;
    @FXML private TableColumn<StudentInfo, Integer> lab_7;
    @FXML private TableColumn<StudentInfo, Integer> lab_8;

    @FXML
    private void initialize(){

        initCollumn();
        LabsLoader labsLoader = new LabsLoader();
        try {
            labsLoader.downloadEmailAttachments("iliuxa.ariko@gmail.com","iliuxa250595");
        } catch (MessagingException | SQLException | IOException e) {
            e.printStackTrace();
        }
        mStudents = FXCollections.observableArrayList();
        mGroups = FXCollections.observableArrayList();


        try {
            mGroups.addAll(DataBaseFactory.getInstance().getDataBase().getGroupDao().getGroups());
        } catch (SQLException e) {
            e.printStackTrace();
        }


        group_list.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> update(newValue));

        group_list.setItems(mGroups);


        update(mGroups.get(0));
        student_table.setItems(mStudents);

    }

    private void update(Group group){
        mStudents.clear();
        try {
            mStudents = DataBaseFactory.getInstance().getDataBase().getStudentInfo(group);
            student_table.setItems(mStudents);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initCollumn(){
        name.setCellValueFactory(cellData -> cellData.getValue().getStudent().nameProperty());
        lab_1.setCellValueFactory(cellData -> cellData.getValue().marksProperty(1).asObject());
        lab_2.setCellValueFactory(cellData -> cellData.getValue().marksProperty(2).asObject());
        lab_3.setCellValueFactory(cellData -> cellData.getValue().marksProperty(3).asObject());
        lab_4.setCellValueFactory(cellData -> cellData.getValue().marksProperty(4).asObject());
        lab_5.setCellValueFactory(cellData -> cellData.getValue().marksProperty(5).asObject());
        lab_6.setCellValueFactory(cellData -> cellData.getValue().marksProperty(6).asObject());
        lab_7.setCellValueFactory(cellData -> cellData.getValue().marksProperty(7).asObject());
        lab_8.setCellValueFactory(cellData -> cellData.getValue().marksProperty(8).asObject());

        group.setCellValueFactory(cellData -> cellData.getValue().groupProperty());
    }
}

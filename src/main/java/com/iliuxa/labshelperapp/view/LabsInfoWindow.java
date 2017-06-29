package com.iliuxa.labshelperapp.view;

import com.iliuxa.labshelperapp.application.MainApp;
import com.iliuxa.labshelperapp.model.LabsLoader;
import com.iliuxa.labshelperapp.pojo.*;
import com.iliuxa.labshelperapp.presenter.ILabsInfoPresenter;
import com.iliuxa.labshelperapp.presenter.LabsInfoWindowPresenter;
import com.iliuxa.labshelperapp.util.StatisticsBuilder;
import javafx.beans.property.IntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class LabsInfoWindow implements BaseView, ILabsInfoWindowView{

    private MainApp mainApp;
    private ILabsInfoPresenter mLabsInfoPresenter;

    @FXML private TableView<Group> groupList;
    @FXML private TableColumn<Group, String> groupCell;
    @FXML private TableView<LabsInfo> labNameList;
    @FXML private TableColumn<LabsInfo, String> labNameCell;
    @FXML private TableView<StudentInfo> studentTable;
    @FXML private TableColumn<StudentInfo, String> studentNameCell;
    private ToggleGroup subGroupFilter = new ToggleGroup();
    private ToggleGroup filter = new ToggleGroup();
    @FXML private ToggleButton subGroup_1;
    @FXML private ToggleButton subGroup_2;
    @FXML private ToggleButton filter_1;
    @FXML private ToggleButton filter_2;

    @FXML
    private void initialize() throws IOException, SQLException, MessagingException {
        mLabsInfoPresenter = new LabsInfoWindowPresenter(this);
        LabsLoader labsLoader = new LabsLoader();
        try{
            Email email = mLabsInfoPresenter.getEmail();
            if(email == null); //mainApp.showEmailDialog();
            else {
                if(MainApp.firstLoad) {
                    labsLoader.downloadEmailAttachments(email.getEmail(), email.getPassword());
                    MainApp.firstLoad = false;
                }
            }
        }catch (MessagingException e){

        }catch (IOException e){

        }
        initColumn();
        initListeners();
        initToggleButton();


        final ContextMenu cm = new ContextMenu();
        MenuItem cmItem1 = new MenuItem("Создать статистику группы");
        MenuItem cmItem2 = new MenuItem("Редактировать группу");
//        cmItem1.setOnAction(new EventHandler<ActionEvent>() {
//            public void handle(ActionEvent e) {
//                Clipboard clipboard = Clipboard.getSystemClipboard();
//                ClipboardContent content = new ClipboardContent();
//                content.putImage(pic.getImage());
//                clipboard.setContent(content);
//            }
//        });
        cmItem1.setOnAction(event -> {
            try {
                StatisticsBuilder.writeIntoExcel(groupList.getSelectionModel().getSelectedItem().getGroup(), mLabsInfoPresenter.getStudentsList(),mainApp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        cm.getItems().add(cmItem1);
        cm.getItems().add(cmItem2);
        groupList.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getButton() == MouseButton.SECONDARY)
                cm.show(groupList, event.getScreenX(), event.getScreenY());
        }
        );

        labNameList.setItems(mLabsInfoPresenter.getLabNameList());
        groupList.setItems(mLabsInfoPresenter.getGroupList());
        studentTable.setItems(mLabsInfoPresenter.getStudentsList());
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
                    onFilterCancel();
                }
            });

        filter.selectedToggleProperty().addListener(event ->{
            if(filter.getSelectedToggle() != null){
                ToggleButton temp = (ToggleButton)filter.getSelectedToggle();
                if((Integer)temp.getUserData() == 1) {
                    filter_1.setStyle("-fx-base: lightgreen;");
                    filter_2.setStyle(null);
                } else{
                    filter_2.setStyle("-fx-base: lightgreen;");
                    filter_1.setStyle(null);
                }
                onFilterClick((Integer) filter.getSelectedToggle().getUserData());
            } else {
                filter_1.setStyle(null);
                filter_2.setStyle(null);
                onFilterCancel();
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
        filter_1.setToggleGroup(filter);
        filter_2.setToggleGroup(filter);
        filter_1.setUserData(1);
        filter_2.setUserData(2);
    }

    private void onGroupClick() {
        ArrayList<TableColumn<StudentInfo, Integer>> columns = null;
        try {
            columns = mLabsInfoPresenter.getStudentsInfo
                    (labNameList.getSelectionModel().getSelectedItem(), groupList.getSelectionModel().getSelectedItem());

            for(int i = 0; i < columns.size(); i++){
                TableColumn<StudentInfo, Integer> column;
                column = columns.get(i);
                int finalI = i;
                column.setCellValueFactory(cellData -> cellData.getValue().marksProperty(finalI).asObject());
                studentTable.getColumns().add(column);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void onLabNameClick(LabsInfo newValue){
        try {
            mLabsInfoPresenter.getGroupsByLabName(newValue);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void onFilterCancel() {
        try {
            mLabsInfoPresenter.getStudents(groupList.getSelectionModel().getSelectedItem());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void onSubGroupFilterClick(Integer subGroup) {
        try {
            mLabsInfoPresenter.getStudentBySubgroup(subGroup, groupList.getSelectionModel().getSelectedItem());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void onFilterClick(Integer num) {
        try {
            mLabsInfoPresenter.getFilteredStudents(num, groupList.getSelectionModel().getSelectedItem());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setMainApp(MainApp app) {
        mainApp = app;
    }

}

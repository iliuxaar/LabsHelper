package com.iliuxa.labshelperapp.view;

import com.iliuxa.labshelperapp.application.MainApp;
import com.iliuxa.labshelperapp.model.DataBaseFactory;
import com.iliuxa.labshelperapp.model.DataBaseHelper;
import com.iliuxa.labshelperapp.model.LabsLoader;
import com.iliuxa.labshelperapp.pojo.Student;
import com.iliuxa.labshelperapp.pojo.StudentsLabs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class StudentWindow implements BaseView {

    private MainApp mainApp;
    private Student mStudent;
    private ObservableList<StudentsLabs> mStudentsLabs = FXCollections.observableArrayList();

    private DataBaseHelper mDataBaseHelper;

    @FXML private ImageView backButton;
    @FXML private TableView<StudentsLabs> labsTable;
    @FXML private TableColumn<StudentsLabs , String> labNameCell;
    @FXML private TableColumn<StudentsLabs , String> fileNameCell;
    @FXML private TableColumn<StudentsLabs , Integer> markCell;
    @FXML private TableColumn<StudentsLabs , String> labNumberCell;

    @FXML
    private void initialize() throws SQLException {
        mDataBaseHelper = DataBaseFactory.getInstance().getDataBase();


        backButton.setOnMouseClicked(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                try {
                    mainApp.showLabsInfoWindow();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        labNameCell.setCellValueFactory(cellData -> cellData.getValue().labNameProperty());
        fileNameCell.setCellValueFactory(cellData -> cellData.getValue().fileNameProperty());
        markCell.setCellValueFactory(cellData -> cellData.getValue().markProperty().asObject());
        labNumberCell.setCellValueFactory(cellData -> cellData.getValue().labNumberProperty());

        labsTable.setOnMouseClicked(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
                if (Desktop.isDesktopSupported()) {
                    try {
                        Desktop.getDesktop().open(new File(LabsLoader.PATH_ABSOLUTE
                                + labsTable.getSelectionModel().getSelectedItem().getStudentsToLabs().getLabPath()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        labsTable.setItems(mStudentsLabs);
    }

    @Override
    public void setMainApp(MainApp app) {
        mainApp = app;
    }

    public void setStudent(Student student){
        mStudent = student;
        try {
            mStudentsLabs.addAll(mDataBaseHelper.getStudentsLabs(mStudent));
            labsTable.setItems(mStudentsLabs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

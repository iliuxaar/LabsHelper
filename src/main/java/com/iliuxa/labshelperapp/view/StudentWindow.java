package com.iliuxa.labshelperapp.view;

import com.iliuxa.labshelperapp.application.MainApp;
import com.iliuxa.labshelperapp.model.DataBaseFactory;
import com.iliuxa.labshelperapp.model.DataBaseHelper;
import com.iliuxa.labshelperapp.pojo.Student;
import com.iliuxa.labshelperapp.pojo.StudentsLabs;
import com.iliuxa.labshelperapp.pojo.StudentsToLabs;
import com.iliuxa.labshelperapp.presenter.IStudentWindowPresenter;
import com.iliuxa.labshelperapp.presenter.StudentWindowPresenter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.SQLException;

public class StudentWindow implements BaseView, IStudentWindowView {

    private MainApp mainApp;
    private Student mStudent;
    IStudentWindowPresenter mStudentWindowPresenter;

    @FXML private ImageView backButton;
    @FXML private TableView<StudentsLabs> labsTable;
    @FXML private TableColumn<StudentsLabs , String> labNameCell;
    @FXML private TableColumn<StudentsLabs , String> fileNameCell;
    @FXML private TableColumn<StudentsLabs , Integer> markCell;
    @FXML private TableColumn<StudentsLabs , String> labNumberCell;

    private EventHandler<MouseEvent> mOnMouseClick = event -> {
        if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
            try {
                mStudentWindowPresenter.openFile(labsTable.getSelectionModel().getSelectedItem().getStudentsToLabs());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    private EventHandler<MouseEvent> mOnBackClick = event -> {
        if(event.getButton().equals(MouseButton.PRIMARY)) {
            try {
                mainApp.showLabsInfoWindow();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    @FXML
    private void initialize() throws SQLException {
        mStudentWindowPresenter = new StudentWindowPresenter(this);
        initCells();
        initListeners();
    }

    private void initCells(){
        labNameCell.setCellValueFactory(cellData -> cellData.getValue().labNameProperty());
        fileNameCell.setCellValueFactory(cellData -> cellData.getValue().fileNameProperty());
        markCell.setCellValueFactory(cellData -> cellData.getValue().markProperty().asObject());
        labNumberCell.setCellValueFactory(cellData -> cellData.getValue().labNumberProperty());
    }

    private void initListeners(){
        backButton.setOnMouseClicked(mOnBackClick);
        labsTable.setOnMouseClicked(mOnMouseClick);
    }

    @Override
    public void setMainApp(MainApp app) {
        mainApp = app;
    }

    public void setStudent(Student student){
        mStudent = student;
        labsTable.setItems(mStudentWindowPresenter.updateContent(mStudent));
    }

    @Override
    public void showMarkDialog(StudentsToLabs studentsToLabs) {
        try {
            mainApp.showMarkDialog(labsTable.getSelectionModel().getSelectedItem().getStudentsToLabs());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateTable() {

    }
}

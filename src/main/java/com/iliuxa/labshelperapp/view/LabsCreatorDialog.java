package com.iliuxa.labshelperapp.view;

import com.iliuxa.labshelperapp.model.DataBaseFactory;
import com.iliuxa.labshelperapp.pojo.LabsInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LabsCreatorDialog {

    private Stage dialogStage;
    private ObservableList<LabsInfo> mLabName;

    @FXML private TableView<LabsInfo> labsNameList;
    @FXML private TableColumn<LabsInfo, String> nameCell;
    @FXML private TextField nameField;
    @FXML private TextField countField;
    @FXML private TextField termField;
    @FXML private Label errorLabel;

    @FXML
    private void initialize() throws SQLException {
        mLabName = FXCollections.observableArrayList();

        updateContent();

        labsNameList.setItems(mLabName);

        nameCell.setCellValueFactory(cellData -> cellData.getValue().labNameProperty());
        labsNameList.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> updateFields(newValue));
    }

    private void updateFields(LabsInfo newValue) {
        nameField.setText(newValue.getLabName());
        countField.setText(String.valueOf(newValue.getLabCount()));
        termField.setText(String.valueOf(newValue.getTerm()));
    }

    public void onCreateClick() throws SQLException {
        DataBaseFactory.getInstance().getDataBase().createLab(verifyData());
        updateContent();
    }

    public void onDeleteClick() throws SQLException {
        DataBaseFactory.getInstance().getDataBase().deleteLab(verifyData());
        updateContent();
    }

    public void onChangeClick() throws SQLException, IOException {
        DataBaseFactory.getInstance().getDataBase().changeLab(verifyData());
        updateContent();
    }

    private LabsInfo verifyData(){
        String name = nameField.getText();
        int term = 0;
        int count = 0;
        try {
            term = Integer.parseInt(termField.getText());
            count = Integer.parseInt(countField.getText());
        }catch (NumberFormatException e){
            errorLabel.setVisible(true);
            errorLabel.setText("Неправильные данные");
            return null;
        }
        return new LabsInfo(count, name, term);
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    private void updateContent() throws SQLException {
        mLabName.clear();
        mLabName.addAll(DataBaseFactory.getInstance().getDataBase().getLabsInfoDao().queryForAll());
    }
}

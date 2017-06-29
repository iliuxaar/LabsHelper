package com.iliuxa.labshelperapp.view;

import com.iliuxa.labshelperapp.model.DataBaseFactory;
import com.iliuxa.labshelperapp.model.DataBaseHelper;
import com.iliuxa.labshelperapp.pojo.StudentsToLabs;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class MarkDialog implements BaseDialog{

    Stage dialogStage;
    StudentsToLabs studentsToLabs;

    @FXML private TextField markText;

    @FXML
    private void initialize() throws SQLException {
        //markText.setText(String.valueOf(DataBaseFactory.getInstance().getDataBase().getStudentsToLabsDao().getEqualsStudentsToLabs(studentsToLabs).getLabMark()));
    }

    @Override
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setStudentsToLabs(StudentsToLabs studentsToLabs){
        this.studentsToLabs = studentsToLabs;
    }

    @FXML
    private void onApply(){
        try {
            DataBaseFactory.getInstance().getDataBase().getStudentsToLabsDao().setLabMark(studentsToLabs, Integer.valueOf(markText.getText()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dialogStage.close();
    }

    @FXML
    private void onCancel(){
        dialogStage.close();
    }

    @FXML
    private void test() throws SQLException {
        markText.setText(String.valueOf(DataBaseFactory.getInstance().getDataBase().getStudentsToLabsDao().getEqualsStudentsToLabs(studentsToLabs).getLabMark()));
    }
}

package com.iliuxa.labshelperapp.presenter;

import com.iliuxa.labshelperapp.model.DataBaseFactory;
import com.iliuxa.labshelperapp.model.DataBaseHelper;
import com.iliuxa.labshelperapp.model.LabsLoader;
import com.iliuxa.labshelperapp.pojo.Student;
import com.iliuxa.labshelperapp.pojo.StudentsLabs;
import com.iliuxa.labshelperapp.pojo.StudentsToLabs;
import com.iliuxa.labshelperapp.view.IStudentWindowView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class StudentWindowPresenter implements IStudentWindowPresenter {

    IStudentWindowView mView;
    private ObservableList<StudentsLabs> mStudentsLabs = FXCollections.observableArrayList();
    private DataBaseHelper mDataBaseHelper;

    public StudentWindowPresenter(IStudentWindowView view){
        mView = view;
        mDataBaseHelper = DataBaseFactory.getInstance().getDataBase();
    }

    @Override
    public void openFile(StudentsToLabs studentsToLabs) throws IOException {
        String path = studentsToLabs.getLabPath();
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(new File(LabsLoader.PATH_ABSOLUTE + path));
            mView.showMarkDialog(studentsToLabs);
        }
    }

    @Override
    public ObservableList<StudentsLabs> updateContent(Student student) {
        try {
            mStudentsLabs.addAll(mDataBaseHelper.getStudentsLabs(student));
            return mStudentsLabs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

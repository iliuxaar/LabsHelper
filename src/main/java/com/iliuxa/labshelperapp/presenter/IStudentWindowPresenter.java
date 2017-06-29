package com.iliuxa.labshelperapp.presenter;

import com.iliuxa.labshelperapp.pojo.Student;
import com.iliuxa.labshelperapp.pojo.StudentsLabs;
import com.iliuxa.labshelperapp.pojo.StudentsToLabs;
import javafx.collections.ObservableList;

import java.io.IOException;

public interface IStudentWindowPresenter {
    void openFile(StudentsToLabs studentsToLabs) throws IOException;
    ObservableList<StudentsLabs> updateContent(Student student);
}

package com.iliuxa.labshelperapp.view;

import com.iliuxa.labshelperapp.pojo.StudentsToLabs;

public interface IStudentWindowView {

    void showMarkDialog(StudentsToLabs studentsToLabs);
    void updateTable();
}

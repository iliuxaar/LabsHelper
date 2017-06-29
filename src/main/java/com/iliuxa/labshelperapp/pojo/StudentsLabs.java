package com.iliuxa.labshelperapp.pojo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StudentsLabs {

    private LabsInfo labsInfo;
    private Lab lab;
    private StudentsToLabs studentsToLabs;

    public LabsInfo getLabsInfo() {
        return labsInfo;
    }

    public void setLabsInfo(LabsInfo labsInfo) {
        this.labsInfo = labsInfo;
    }

    public Lab getLab() {
        return lab;
    }

    public void setLab(Lab lab) {
        this.lab = lab;
    }

    public StudentsToLabs getStudentsToLabs() {
        return studentsToLabs;
    }

    public void setStudentsToLabs(StudentsToLabs studentsToLabs) {
        this.studentsToLabs = studentsToLabs;
    }

    public StudentsLabs(LabsInfo labsInfo, Lab lab, StudentsToLabs studentsToLabs) {
        this.labsInfo = labsInfo;
        this.lab = lab;
        this.studentsToLabs = studentsToLabs;
    }

    public StringProperty labNameProperty(){
        return new SimpleStringProperty(labsInfo.getLabName());
    }

    public StringProperty fileNameProperty(){
        String[] path = studentsToLabs.getLabPath().split("\\\\");
        return new SimpleStringProperty(path[path.length - 1]);
    }

    public IntegerProperty markProperty(){
        return new SimpleIntegerProperty(studentsToLabs.getLabMark());
    }

    public StringProperty labNumberProperty(){
        return new SimpleStringProperty("À‡·" + lab.getlabNumber());
    }
}

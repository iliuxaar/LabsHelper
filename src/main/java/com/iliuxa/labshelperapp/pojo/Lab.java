package com.iliuxa.labshelperapp.pojo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@DatabaseTable(tableName = "Lab")
public class Lab {

    public static final String FIELD_LAB_NUMBER = "lab_number";
    public static final String FIELD_LAB_NAME = "lab_name";

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = FIELD_LAB_NUMBER, canBeNull = false)
    private int labNumber;

    @DatabaseField(columnName = FIELD_LAB_NAME, canBeNull = false)
    private String labName;

    Lab(){}

    public Lab(int labNumber, String labName){
        this.labNumber = labNumber;
        this.labName = labName;
    }

    public int getId() {
        return id;
    }

    public int getlabNumber() {
        return labNumber;
    }

    public void setlabNumber(int labNumber) {
        this.labNumber = labNumber;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public IntegerProperty labNumberProperty(){
        return new SimpleIntegerProperty(labNumber);
    }

    public StringProperty labNameProperty(){
        return new SimpleStringProperty(labName);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        Lab lab = (Lab) obj;
        if(!labName.equals(lab.labName))return false;
        return labNumber == lab.labNumber;
    }

}

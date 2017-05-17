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
    public static final String FIELD_LABS_INFO_ID = "labs_info_id";

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = FIELD_LAB_NUMBER, canBeNull = false)
    private int labNumber;

    @DatabaseField(columnName = FIELD_LABS_INFO_ID, canBeNull = false)
    private int labInfoId;

    Lab(){}

    public Lab(int labNumber){
        this.labNumber = labNumber;
    }

    public Lab(int labNumber, int labInfoId){
        this.labNumber = labNumber;
        this.labInfoId = labInfoId;
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

    public int getLabInfoId() {
        return labInfoId;
    }

    public void setLabInfoId(int labInfoId) {
        this.labInfoId = labInfoId;
    }

    public IntegerProperty labNumberProperty(){
        return new SimpleIntegerProperty(labNumber);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        Lab lab = (Lab) obj;
        return labNumber == lab.labNumber;
    }

}

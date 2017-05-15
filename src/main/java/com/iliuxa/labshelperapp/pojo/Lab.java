package com.iliuxa.labshelperapp.pojo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

@DatabaseTable(tableName = "Lab")
public class Lab {

    public static final String FIELD_LAB_NUMBER = "lab_number";

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = FIELD_LAB_NUMBER, canBeNull = false)
    private int labNumber;

    Lab(){}

    public Lab(int labNumber){
        this.labNumber = labNumber;
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

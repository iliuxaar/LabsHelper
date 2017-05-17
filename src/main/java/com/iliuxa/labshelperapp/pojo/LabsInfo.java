package com.iliuxa.labshelperapp.pojo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@DatabaseTable(tableName = "LabsInfo")
public class LabsInfo {

    public static final String FIELD_LAB_COUNT = "lab_count";
    public static final String FIELD_TERM = "term";
    public static final String FIELD_LAB_NAME = "lab_name";

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = FIELD_LAB_COUNT)
    private int labCount;

    @DatabaseField(columnName = FIELD_LAB_NAME)
    private String labName;

    @DatabaseField(columnName = FIELD_TERM)
    private int term;

    LabsInfo(){}

    public LabsInfo(int labCount, String labName, int term){
        this.labCount = labCount;
        this.labName = labName;
        this.term = term;
    }

    public LabsInfo(int labCount, String labName){
        this.labCount = labCount;
        this.labName = labName;
    }

    public LabsInfo(String labName, int term){
        this.term = term;
        this.labName = labName;
    }

    public LabsInfo(int term){
        this.term = term;
    }

    public int getId() {
        return id;
    }

    public int getLabCount() {
        return labCount;
    }

    public void setLabCount(int labCount) {
        this.labCount = labCount;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public IntegerProperty labCountProperty(){
        return new SimpleIntegerProperty(labCount);
    }

    public StringProperty labNameProperty(){
        return new SimpleStringProperty(labName);
    }


}

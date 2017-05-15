package com.iliuxa.labshelperapp.pojo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

@DatabaseTable(tableName = "students_to_labs")
public class StudentsToLabs {

    public static final String FIELD_LAB_ID = "lab_id";
    public static final String FIELD_STUDENT_ID = "student_id";
    public static final String FIELD_LAB_MARK = "lab_mark";
    public static final String FIELD_LAB_PATH = "lab_path";

    @DatabaseField(columnName = FIELD_LAB_ID, canBeNull = false)
    private int labId;

    @DatabaseField(columnName = FIELD_STUDENT_ID, canBeNull = false)
    private int studentId;

    @DatabaseField(columnName = FIELD_LAB_MARK, canBeNull = true)
    private int labMark;

    @DatabaseField(columnName = FIELD_LAB_PATH, canBeNull = false)
    private String labPath;

    StudentsToLabs(){}

    public StudentsToLabs(int labId, int studentId, String labPath){
        this.labId = labId;
        this.studentId = studentId;
        this.labPath = labPath;
    }

    public StudentsToLabs(int labId, int studentId, String labPath, int labMark){
        this.labId = labId;
        this.studentId = studentId;
        this.labPath = labPath;
        this.labMark = labMark;
    }

    public int getLabId() {
        return labId;
    }

    public void setLabId(int labId) {
        this.labId = labId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getLabMark() {
        return labMark;
    }

    public void setLabMark(int labMark) {
        this.labMark = labMark;
    }

    public String getLabPath() {
        return labPath;
    }

    public void setLabPath(String labPath) {
        this.labPath = labPath;
    }

    public IntegerProperty labMarkProperty(){
        return new SimpleIntegerProperty(labMark);
    }

    @Override
    public int hashCode() {
        return labPath.hashCode() * 31;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        StudentsToLabs studentsToLabs = (StudentsToLabs) obj;
        if (studentId != studentsToLabs.studentId) return false;
        if (labId != studentsToLabs.labId) return false;
        if (labMark != studentsToLabs.labMark) return false;
        return labPath.equals(studentsToLabs.labPath);
    }
}

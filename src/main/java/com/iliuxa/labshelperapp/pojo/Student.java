package com.iliuxa.labshelperapp.pojo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@DatabaseTable(tableName = "Student")
public class Student {

    public static final String FIELD_FIRST_NAME = "name";
    public static final String FIELD_GROUP_ID = "group_id";

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = FIELD_FIRST_NAME, canBeNull = false)
    private String name;

    @DatabaseField(columnName = FIELD_GROUP_ID, canBeNull = false)
    private int groupId;

    Student(){}

    public Student(String name, int groupId) {
        this.name = name;
        this.groupId = groupId;
    }

    public Student(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public StringProperty nameProperty(){
        return new SimpleStringProperty(name);
    }

    @Override
    public int hashCode() {
        return name.hashCode() * 31;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        Student student = (Student) obj;
        return name.equals(student.name) && groupId == student.groupId;
    }
}

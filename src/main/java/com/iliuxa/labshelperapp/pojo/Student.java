package com.iliuxa.labshelperapp.pojo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@DatabaseTable(tableName = "Student")
public class Student {

    public static final String FIELD_FIRST_NAME = "name";
    public static final String FIELD_GROUP_ID = "group_id";
    public static final String FIELD_SUBGROUP = "subgroup";

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = FIELD_FIRST_NAME, canBeNull = false)
    private String name;

    @DatabaseField(columnName = FIELD_GROUP_ID, canBeNull = false)
    private int groupId;

    @DatabaseField(columnName = FIELD_SUBGROUP, canBeNull = false)
    private int subGroup;

    Student(){}

    public Student(String name, int groupId, int subGroup) {
        this.name = name;
        this.groupId = groupId;
    }

    public Student(String name, int subGroup) {
        this.name = name;
        this.subGroup = subGroup;
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

    public int getSubGroup() {
        return subGroup;
    }

    public void setSubGroup(int subGroup) {
        this.subGroup = subGroup;
    }

    public StringProperty nameProperty(){
        return new SimpleStringProperty(name);
    }

    public IntegerProperty subGroupProperty(){
        return new SimpleIntegerProperty(subGroup);
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
        return name.equals(student.name) && groupId == student.groupId && subGroup == student.subGroup;
    }


}

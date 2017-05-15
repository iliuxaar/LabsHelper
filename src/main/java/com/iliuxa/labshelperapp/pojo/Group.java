package com.iliuxa.labshelperapp.pojo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@DatabaseTable(tableName = "Group")
public class Group {

    public static final String FIELD_GROUP = "group_number";

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = FIELD_GROUP, canBeNull = false)
    private String group;

    Group(){}

    public Group(String group){
        this.group = group;
    }

    public int getId() {
        return id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public StringProperty groupProperty() {
        return new SimpleStringProperty(group);
    }

    @Override
    public int hashCode() {
        return group.hashCode() * 31;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        Group group = (Group) obj;
        return group.equals(group.group);
    }
}

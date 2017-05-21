package com.iliuxa.labshelperapp.pojo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "groups_to_lab")
public class GroupsToLabs {

    public static final String FIELD_GROUP_ID = "group_id";
    public static final String FIELD_LAB_ID = "lab_id";
    public static final String FIELD_DEAD_LINE = "dead_line";
    public static final String FIELD_BEGIN_TIME = "begin_time";
    public static final String FIELD_SUBGROUP = "subgroup";

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = FIELD_GROUP_ID, canBeNull = false)
    private int groupId;

    @DatabaseField(columnName = FIELD_LAB_ID, canBeNull = false)
    private int labId;

    @DatabaseField(columnName = FIELD_DEAD_LINE, canBeNull = true)
    private Date deadLine;

    @DatabaseField(columnName = FIELD_BEGIN_TIME, canBeNull = true)
    private Date beginTime;

    @DatabaseField(columnName = FIELD_SUBGROUP, canBeNull = false)
    private int subGroup;

    GroupsToLabs(){}

    public GroupsToLabs(int groupId, int labId , int subGroup){
        this.groupId = groupId;
        this.labId = labId;
        this.subGroup = subGroup;
    }

    public GroupsToLabs(int groupId, int labId, Date beginTime){
        this.groupId = groupId;
        this.labId = labId;
        this.beginTime = beginTime;
    }

    public GroupsToLabs(int groupId, int labId, Date beginTime, Date deadLine){
        this.groupId = groupId;
        this.labId = labId;
        this.beginTime = beginTime;
        this.deadLine = deadLine;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getLabId() {
        return labId;
    }

    public void setLabId(int labId) {
        this.labId = labId;
    }

    public Date getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public int getSubGroup() {
        return subGroup;
    }

    public void setSubGroup(int subGroup) {
        this.subGroup = subGroup;
    }

    //todo add property

    @Override
    public int hashCode() {
        int result = beginTime.hashCode();
        result = 31 * result + deadLine.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        GroupsToLabs groupsToLabs = (GroupsToLabs) obj;
        if (groupId != groupsToLabs.groupId) return false;
        if (labId != groupsToLabs.labId) return false;
        if (!beginTime.equals(groupsToLabs.beginTime)) return false;
        return deadLine.equals(groupsToLabs.deadLine);
    }

    public int getId() {
        return id;
    }
}

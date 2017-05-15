package com.iliuxa.labshelperapp.pojo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;

public class StudentInfo {

    private Student student;
    private ArrayList<Integer> marks;
    private Integer total;

    public StudentInfo(Student student, ArrayList<Integer> marks) {
        this.student = student;
        this.marks = marks;
    }

    public StudentInfo() {
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public ArrayList<Integer> getMarks() {
        return marks;
    }

    public void setMarks(ArrayList<Integer> marks) {
        this.marks = marks;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public IntegerProperty marksProperty(int labNumber){
        if (marks == null || marks.get(labNumber - 1) == null){
            return new SimpleIntegerProperty(0);
        }
        else return new SimpleIntegerProperty(marks.get(labNumber - 1));
    }
}

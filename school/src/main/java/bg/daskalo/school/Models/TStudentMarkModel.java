package bg.daskalo.school.Models;

import java.util.ArrayList;

public class TStudentMarkModel {
    private ArrayList<Long> firstTermIds;
    private ArrayList<Long> secondTermIds;
    private String studentNames;
    private String firstTerm;
    private String firstTermFinal;
    private String secondTerm;
    private String secondTermFinal;
    private String yearly;

    public TStudentMarkModel(String studentNames) {
        this.studentNames = studentNames;
    }

    public ArrayList<Long> getFirstTermIds() {
        return firstTermIds;
    }

    public void setFirstTermIds(ArrayList<Long> firstTermIds) {
        this.firstTermIds = firstTermIds;
    }

    public ArrayList<Long> getSecondTermIds() {
        return secondTermIds;
    }

    public void setSecondTermIds(ArrayList<Long> secondTermIds) {
        this.secondTermIds = secondTermIds;
    }

    public String getStudentNames() {
        return studentNames;
    }

    public void setStudentNames(String studentName) {
        this.studentNames = studentName;
    }

    public String getFirstTerm() {
        return firstTerm;
    }

    public void setFirstTerm(String firstTerm) {
        this.firstTerm = firstTerm;
    }

    public String getFirstTermFinal() {
        return firstTermFinal;
    }

    public void setFirstTermFinal(String firstTermFinal) {
        this.firstTermFinal = firstTermFinal;
    }

    public String getSecondTerm() {
        return secondTerm;
    }

    public void setSecondTerm(String secondTerm) {
        this.secondTerm = secondTerm;
    }

    public String getSecondTermFinal() {
        return secondTermFinal;
    }

    public void setSecondTermFinal(String secondTermFinal) {
        this.secondTermFinal = secondTermFinal;
    }

    public String getYearly() {
        return yearly;
    }

    public void setYearly(String yearly) {
        this.yearly = yearly;
    }
}

package bg.daskalo.school.Models;

import java.util.Date;

public class StudentAbsenceModel {
    private String studentName;
    private boolean isAbsence;
    private boolean isExcused;
    private Date date;

    public StudentAbsenceModel(String studentName, boolean isAbsence, boolean isExcused, Date date) {
        this.studentName = studentName;
        this.isAbsence = isAbsence;
        this.isExcused = isExcused;
        this.date = date;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public boolean isAbsence() {
        return isAbsence;
    }

    public void setAbsence(boolean absence) {
        isAbsence = absence;
    }

    public boolean isExcused() {
        return isExcused;
    }

    public void setExcused(boolean excused) {
        isExcused = excused;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

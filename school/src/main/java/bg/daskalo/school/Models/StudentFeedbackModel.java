package bg.daskalo.school.Models;

import java.util.Date;

public class StudentFeedbackModel {
    private String studentName;
    private String description;
    private Date date;

    public StudentFeedbackModel(String studentName, String description, Date date) {
        this.studentName = studentName;
        this.description = description;
        this.date = date;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

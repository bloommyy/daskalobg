package bg.daskalo.school.Models;

public class StudentFeedbackModel {
    private String subjectName;
    private String description;
    private String date;

    public StudentFeedbackModel(String subjectName, String description, String date) {
        this.subjectName = subjectName;
        this.description = description;
        this.date = date;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

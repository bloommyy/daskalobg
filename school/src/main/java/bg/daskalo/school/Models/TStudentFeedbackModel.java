package bg.daskalo.school.Models;

public class TStudentFeedbackModel {
    private Long id;
    private String studentName;
    private String description;
    private String date;

    public TStudentFeedbackModel(Long id, String studentName, String description, String date) {
        this.id = id;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

package bg.daskalo.school.Models;

public class StudentAbsenceModel {
    private String subjectName;
    private String type;
    private String isExcused;
    private String date;

    public StudentAbsenceModel(String subjectName, String type, String isExcused, String date) {
        this.subjectName = subjectName;
        this.type = type;
        this.isExcused = isExcused;
        this.date = date;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIsExcused() {
        return isExcused;
    }

    public void setIsExcused(String isExcused) {
        this.isExcused = isExcused;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

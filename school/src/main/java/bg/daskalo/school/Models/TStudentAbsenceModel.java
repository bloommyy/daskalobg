package bg.daskalo.school.Models;

public class TStudentAbsenceModel {
    private Long id;
    private String studentNames;
    private String type;
    private String isExcused;
    private String date;

    public TStudentAbsenceModel(Long id, String studentNames, String type, String isExcused, String date) {
        this.id = id;
        this.studentNames = studentNames;
        this.type = type;
        this.isExcused = isExcused;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentNames() {
        return studentNames;
    }

    public void setStudentNames(String studentNames) {
        this.studentNames = studentNames;
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

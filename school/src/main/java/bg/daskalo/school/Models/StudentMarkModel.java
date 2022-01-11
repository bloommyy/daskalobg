package bg.daskalo.school.Models;

public class StudentMarkModel {
    private String studentName;
    private Integer mark;
    private Integer term;

    public StudentMarkModel(String studentName, Integer mark, Integer term) {
        this.studentName = studentName;
        this.mark = mark;
        this.term = term;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }
}

package bg.daskalo.school.Models;

public class StudentMarkModel {
    private Long id;
    private String studentName;
    private Integer mark;
    private Integer term;

    public StudentMarkModel(Long id, String studentName, Integer mark, Integer term) {
        this.id = id;
        this.studentName = studentName;
        this.mark = mark;
        this.term = term;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

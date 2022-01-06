package bg.daskalo.school.Entities;

import javax.persistence.*;

@Entity
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    private String studentClass;
    private String classNum;

    private Integer subjectId;
    private Integer mark;
    private Integer term;


    public Mark(Student student, String studentClass, String classNum, Integer subjectId, Integer mark, Integer term) {
        this.student = student;
        this.studentClass = studentClass;
        this.classNum = classNum;
        this.subjectId = subjectId;
        this.mark = mark;
        this.term = term;
    }

    public Mark() {
    }

    public Long getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getStudentClassNum() {
        return classNum;
    }

    public void setStudentClassNum(String classNum) {this.classNum = classNum; }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {this.subjectId = subjectId; }

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

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

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    private String studentClass;
    private String classNum;

    private Integer mark;
    private Integer term;


    public Mark(Student student, String studentClass, String classNum, Subject subject, Integer mark, Integer term) {
        this.student = student;
        this.studentClass = studentClass;
        this.classNum = classNum;
        this.subject = subject;
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

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {this.subject = subject; }

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

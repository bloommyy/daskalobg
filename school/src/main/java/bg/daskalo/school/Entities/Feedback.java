package bg.daskalo.school.Entities;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Feedback {
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

    private Date date;

    private String description;

    public Feedback(Student student, String studentClass, String classNum, Date date, Subject subject, String description) {
        this.student = student;
        this.studentClass = studentClass;
        this.classNum = classNum;
        this.date = date;
        this.subject = subject;
        this.description = description;
    }

    public Feedback() {
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

    public void setStudentClassNum(String classNum) {
        this.classNum = classNum;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {this.description = description; }


}

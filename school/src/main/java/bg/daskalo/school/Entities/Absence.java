package bg.daskalo.school.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Absence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    private Date date;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    private boolean isAbsence;
    private boolean isExcused;

    public Absence() {
    }

    public Absence(Student student, Date date, boolean isAbsence, Subject subject) {
        this.student = student;
        this.date = date;
        this.isAbsence = isAbsence;
        this.subject = subject;
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

    public boolean isAbsence() {
        return isAbsence;
    }

    public void setAbsence(boolean absence) {
        isAbsence = absence;
    }

    public boolean isExcused() {
        return isExcused;
    }

    public void setExcused(boolean excused) {
        isExcused = excused;
    }
}

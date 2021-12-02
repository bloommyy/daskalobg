package bg.daskalo.school.Entities;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Absence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long a_id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student a_student;

    private Date a_date;

    //Todo: Uncomment when Subject class is created
//    private Subject a_subject;

    private boolean a_isAbsence;
    private boolean a_isExcused;

    public Absence() {
    }

    public Absence(Student a_student, Date a_date, boolean a_isAbsence) {
        this.a_student = a_student;
        this.a_date = a_date;
        this.a_isAbsence = a_isAbsence;
    }

    public Long getId() {
        return a_id;
    }

    public Student getStudent() {
        return a_student;
    }

    public void setStudent(Student a_student) {
        this.a_student = a_student;
    }

    public Date getDate() {
        return a_date;
    }

    public void setDate(Date a_date) {
        this.a_date = a_date;
    }

    //Todo: Uncomment when Subject class is created
//    public Subject getSubject() {
//        return a_subject;
//    }
//
//    public void setSubject(Subject a_subject) {
//        this.a_subject = a_subject;
//    }

    public boolean isAbsence() {
        return a_isAbsence;
    }

    public void setIsAbsence(boolean a_isAbsence) {
        this.a_isAbsence = a_isAbsence;
    }

    public boolean isExcused() {
        return a_isExcused;
    }

    public void setIsExcused(boolean a_isExcused) {
        this.a_isExcused = a_isExcused;
    }
}

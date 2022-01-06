package bg.daskalo.school.Entities;

import bg.daskalo.school.Entities.Login.StudentLogin;

import javax.persistence.*;
import java.util.List;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @OneToOne
//    private StudentLogin login;

    private String firstName;
    private String middleName;
    private String lastName;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String egn;

    private String stClass;
    private Integer stClassNum;

    @OneToMany(mappedBy = "student")
    private List<Absence> absences;

    @OneToMany(mappedBy = "student")
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "student")
    private List<Mark> marks;

    //bahti golemiq constructor brat
    public Student(String firstName, String middleName, String lastName,
                   String email, String egn,
                   String stClass) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.egn = egn;
        this.stClass = stClass;
    }

    public Student() {
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEgn() {
        return egn;
    }

    public void setEgn(String egn) {
        this.egn = egn;
    }

    public String getStClass() {
        return stClass;
    }

    public void setStClass(String stClass) {
        this.stClass = stClass;
    }

    public Integer getStClassNum() {
        return stClassNum;
    }

    public void setStClassNum(Integer stClassNum) {
        this.stClassNum = stClassNum;
    }

    public List<Absence> getAbsences() {
        return absences;
    }

    public void setAbsences(List<Absence> absences) {
        this.absences = absences;
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public List<Mark> getMarks() {
        return marks;
    }

    public void setMarks(List<Mark> marks) {
        this.marks = marks;
    }
}

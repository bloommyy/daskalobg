package bg.daskalo.school.Entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long st_id;

    private String st_first_name;
    private String st_middle_name;
    private String st_last_name;

    private String st_email;
    private String st_password;
    private String st_egn;
    private String st_class;
    private Integer st_class_num;

    @OneToMany(mappedBy = "a_student")
    private List<Absence> st_absences;

    //Todo: Uncomment when Feedback and Mark are created
//    @OneToMany(mappedBy = "f_student")
//    private List<Feedback> st_feedbacks;
//
//    @OneToMany(mappedBy = "m_student")
//    private List<Mark> st_marks;

    //bahti golemiq constructor brat
    public Student(String st_first_name, String st_middle_name, String st_last_name,
                   String st_email, String st_password, String st_egn,
                   String st_class, Integer st_class_num) {
        this.st_first_name = st_first_name;
        this.st_middle_name = st_middle_name;
        this.st_last_name = st_last_name;
        this.st_email = st_email;
        this.st_password = st_password;
        this.st_egn = st_egn;
        this.st_class = st_class;
        this.st_class_num = st_class_num;
    }

    public Student() {
    }

    public Long getId() {
        return st_id;
    }

    public String getFirstName() {
        return st_first_name;
    }

    public void setFirstName(String st_first_name) {
        this.st_first_name = st_first_name;
    }

    public String getMiddleName() {
        return st_middle_name;
    }

    public void setMiddleName(String st_middle_name) {
        this.st_middle_name = st_middle_name;
    }

    public String getLastName() {
        return st_last_name;
    }

    public void setLastName(String st_last_name) {
        this.st_last_name = st_last_name;
    }

    public String getFullName(){
        return st_first_name + " " + st_middle_name + " " + st_last_name;
    }

    public String getEmail() {
        return st_email;
    }

    public void setEmail(String st_email) {
        this.st_email = st_email;
    }

    public String getPassword() {
        return st_password;
    }

    public void setPassword(String st_password) {
        this.st_password = st_password;
    }

    public String getEGN() {
        return st_egn;
    }

    public void setEGN(String st_egn) {
        this.st_egn = st_egn;
    }

    public String getStClass() {
        return st_class;
    }

    public void setStClass(String st_class) {
        this.st_class = st_class;
    }

    public Integer getClassNum() {
        return st_class_num;
    }

    public void setClassNum(Integer st_class_num) {
        this.st_class_num = st_class_num;
    }

    public List<Absence> getAbsences() {
        return st_absences;
    }

    public void setAbsences(List<Absence> st_absences) {
        this.st_absences = st_absences;
    }

    //Todo: Uncomment when Feedback and Mark are created
//    public List<Feedback> getFeedbacks() {
//        return st_feedbacks;
//    }
//
//    public void setFeedbacks(List<Feedback> st_feedbacks) {
//        this.st_feedbacks = st_feedbacks;
//    }
//
//    public List<Mark> getMarks() {
//        return st_marks;
//    }
//
//    public void setMarks(List<Mark> st_marks) {
//        this.st_marks = st_marks;
//    }
}

package bg.daskalo.school.Entities;

import javax.persistence.*;

@Entity
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long t_id;

    private String t_first_name;
    private String t_middle_name;
    private String t_last_name;

    private String t_email;
    private String t_password;
    private Integer t_subject_id;


    @OneToOne(mappedBy = "sj_teacher")
    private Subject t_subject;

    public Teacher(String t_first_name, String t_middle_name, String t_last_name,
                   String t_email, String t_password,
                   Integer t_subject_id) {
        this.t_first_name = t_first_name;
        this.t_middle_name = t_middle_name;
        this.t_last_name = t_last_name;
        this.t_email = t_email;
        this.t_password = t_password;
        this.t_subject_id = t_subject_id;
    }

    public Teacher() {

    }
    public Long getId() {
        return t_id;
    }

    public String getFirstName() {
        return t_first_name;
    }
    public void setFirstName(String t_first_name) {
        this.t_first_name = t_first_name;
    }

    public String getMiddleName() {return t_middle_name;}
    public void setMiddleName(String t_middle_name) {this.t_middle_name = t_middle_name; }

    public String getLastLame() {return t_last_name; }

    public void setLastName(String t_last_name) {this.t_last_name = t_last_name; }

    public String getEmail() {return t_email; }

    public void setEmail(String t_email) {this.t_email = t_email; }

    public String getPassword() {return t_password; }

    public void setPassword(String t_password) {this.t_password = t_password; }


    public Integer getSubjectId() {return t_subject_id; }

    public void setSubjectId(Integer t_subject_id) { this.t_subject_id = t_subject_id; }

    public Subject getSubject() {
        return t_subject;
    }

    public void setSubject(Subject t_subject) {
        this.t_subject = t_subject;
    }
}

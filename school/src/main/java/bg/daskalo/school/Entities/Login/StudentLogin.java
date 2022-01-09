package bg.daskalo.school.Entities.Login;

import bg.daskalo.school.Entities.Student;

import javax.persistence.*;

@Entity
public class StudentLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Student student;

    private String password;

    public StudentLogin() {
    }

    public StudentLogin(Student student, String password) {
        this.student = student;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

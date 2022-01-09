package bg.daskalo.school.Entities.Login;

import bg.daskalo.school.Entities.Teacher;

import javax.persistence.*;

@Entity
public class TeacherLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Teacher teacher;

    private String password;

    public TeacherLogin() {
    }

    public TeacherLogin(Teacher teacher, String password) {
        this.teacher = teacher;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


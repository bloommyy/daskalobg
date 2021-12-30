package bg.daskalo.school.Entities.Login;

import bg.daskalo.school.Entities.Teacher;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity
public class TeacherLogin {
    @Id
    private Long id;

    @MapsId
    @OneToOne
    private Teacher teacher;

    private String password;

    public TeacherLogin() {
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


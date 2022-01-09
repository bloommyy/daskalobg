package bg.daskalo.school.Entities;

import javax.persistence.*;

@Entity
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    private String name;
    private String sjClass;

    public Subject(String name, String sjClass) {
        this.name = name;
        this.sjClass = sjClass;
    }

    public Subject() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSjClass() {
        return sjClass;
    }

    public void setSjClass(String sjClass) {
        this.sjClass = sjClass;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}

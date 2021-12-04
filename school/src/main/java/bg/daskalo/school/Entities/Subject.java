package bg.daskalo.school.Entities;

import javax.persistence.*;

@Entity
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sj_id;

    @OneToOne
    @JoinColumn(name="teacher_id")
    private Teacher sj_teacher;




    private String sj_name;
    private String sj_class;


    public Subject(String sj_name, String sj_class)
    {
        this.sj_name = sj_name;
        this.sj_class = sj_class;

    }
    public Subject() {

    }

    public Long getId() {return sj_id; }



    public String getSjName() {return sj_name; }

    public void setSjName(String sj_name) { this.sj_name = sj_name; }

    public String getSjClass() {return sj_class; }

    public void setSjClass(String sj_class) {this.sj_class = sj_class; }
}

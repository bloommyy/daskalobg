package bg.daskalo.school.Entities;

import javax.persistence.*;

@Entity
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long f_id;


    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student f_student;

    private String f_student_class;
    private String f_student_class_num;

    private Date f_date;

    private Integer f_subject_id;
    private String f_description;

    public Feedback(Student f_student,String f_student_class, String f_student_class_num,Date f_date,Integer f_subject_id,String f_description)
    {
        this.f_student = f_student;
        this.f_student_class = f_student_class;
        this.f_student_class_num = f_student_class_num;
        this.f_date = f_date;
        this.f_subject_id = f_subject_id;
        this.f_description = f_description;
    }

    public Feedback()
    {
    }
    public Long getId()
    {
        return f_id;
    }
    public Student getStudent()
    {
        return f_student;
    }
    public void setStudent(Student f_student)
    {
        this.f_student = f_student;
    }
    public String getStudenClass()
    {
        return f_student_class;
    }
    public void setStudenClass(String f_student_class)
    {
        this.f_student_class = f_student_class;
    }
    public String getStudenClassNum()
    {
        return f_student_class_num;
    }
    public void setStudenClassNum(String f_student_class_num)
    {
        this.f_student_class_num = f_student_class_num;
    }
    public Date getDate()
    {
        return f_date;
    }
    public void setDate(Date f_date)
    {
        this.f_date=f_date;
    }
    public Integer getSubjectId()
    {
        return f_subject_id;
    }
    public void setSubjectId(Integer f_subject_id)
    {
        this.f_subject_id = f_subject_id;
    }
    public String getDescription()
    {
        return f_description;
    }
    public void setDescription(String f_description)
    {
        this.f_description = f_description;
    }


}

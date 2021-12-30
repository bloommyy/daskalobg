package bg.daskalo.school.Entities;

import javax.persistence.*;

@Entity
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long m_id;


    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student m_student;

    private String m_student_class;
    private String m_student_class_num;

    private Integer m_subject_id;
    private Integer m_mark;
    private Integer m_term;


    public Mark(Student m_student, String m_student_class, String m_student_class_num, Integer m_subject_id, Integer m_mark, Integer m_term) {
        this.m_student = m_student;
        this.m_student_class = m_student_class;
        this.m_student_class_num = m_student_class_num;
        this.m_subject_id = m_subject_id;
        this.m_mark = m_mark;
        this.m_term = m_term;
    }

    public Mark() {
    }

    public Long getId() {
        return m_id;
    }

    public Student getStudent() {
        return m_student;
    }

    public void setStudent(Student m_student) {
        this.m_student = m_student;
    }

    public String getStudenClass() {
        return m_student_class;
    }

    public void setStudenClass(String m_student_class) {
        this.m_student_class = m_student_class;
    }

    public String getStudenClassNum() {
        return m_student_class_num;
    }

    public void setStudenClassNum(String m_student_class_num) {
        this.m_student_class_num = m_student_class_num;
    }

    public Integer getSubjectId() {
        return m_subject_id;
    }

    public void setSubjectId(Integer m_subject_id) {
        this.m_subject_id = m_subject_id;
    }

    public Integer getMark() {
        return m_mark;
    }

    public void setMark(Integer m_mark) {
        this.m_mark = m_mark;
    }

    public Integer getTerm() {
        return m_term;
    }

    public void setTerm(Integer m_term) {
        this.m_term = m_term;
    }
}

package bg.daskalo.school.Repositories;

import bg.daskalo.school.Entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query(value = "SELECT * from student " +
            "where st_class = :#{#stClass} " +
            "order by first_name, last_name"
            , nativeQuery = true)
    List<Student> fetchSortedStudentsByStClass(String stClass);

    Student findStudentByEmail(String email);

    @Modifying
    @Query("update Student st " +
            "set st.stClassNum = :#{#stClassNum} " +
            "where st.id = :#{#id}")
    void updateStudentClassNum(UUID id, Integer stClassNum);

    @Modifying
    @Query("update Student st " +
            "set st.stClassNum = null " +
            "where st.stClass = :#{#stClass}")
    void setStudentsClassNumToNull(String stClass);

    Student findStudentById(UUID id);

    Set<Student> findStudentsByStClass(String stClass);
}

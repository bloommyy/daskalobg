package bg.daskalo.school.Repositories;

import bg.daskalo.school.Entities.Student;
import bg.daskalo.school.Entities.Login.StudentLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findStudentsByStClassOrderByStClassNumAsc(String stClass);

    Student findStudentByLogin(StudentLogin login);

    Student findStudentByFirstNameAndMiddleNameAndLastName(String firstName,
                                                           String middleName,
                                                           String lastName);
}

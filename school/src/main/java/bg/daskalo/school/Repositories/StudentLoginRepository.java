package bg.daskalo.school.Repositories;

import bg.daskalo.school.Entities.Login.StudentLogin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentLoginRepository extends JpaRepository<StudentLogin, Long> {
    StudentLogin findStudentLoginByPassword(String password);
}

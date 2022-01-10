package bg.daskalo.school.Repositories;

import bg.daskalo.school.Entities.Login.StudentLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentLoginRepository extends JpaRepository<StudentLogin, Long> {

    List<StudentLogin> findStudentsLoginByPassword(String password);
}

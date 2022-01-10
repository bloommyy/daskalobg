package bg.daskalo.school.Repositories;

import bg.daskalo.school.Entities.Login.TeacherLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherLoginRepository extends JpaRepository<TeacherLogin, Long> {

    List<TeacherLogin> findTeacherLoginsByPassword(String password);
}

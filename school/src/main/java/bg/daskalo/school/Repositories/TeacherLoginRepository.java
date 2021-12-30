package bg.daskalo.school.Repositories;

import bg.daskalo.school.Entities.Login.TeacherLogin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherLoginRepository extends JpaRepository<TeacherLogin, Long> {
    TeacherLogin findTeacherLoginByPassword(String password);
}

package bg.daskalo.school.Repositories;

import bg.daskalo.school.Entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Teacher findTeacherByEmail(String email);

    Teacher findTeacherById(Long id);
}

package bg.daskalo.school.Repositories;

import bg.daskalo.school.Entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;
import java.util.UUID;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Teacher findTeacherByEmail(String email);

    Teacher findTeacherById(UUID id);

    @Query(value = "SELECT DISTINCT st_class from student",
            nativeQuery = true)
    Set<String> fetchAllClasses();
}

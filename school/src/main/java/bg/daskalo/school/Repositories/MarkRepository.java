package bg.daskalo.school.Repositories;

import bg.daskalo.school.Entities.Mark;
import bg.daskalo.school.Entities.Student;
import bg.daskalo.school.Entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MarkRepository extends JpaRepository<Mark, Long> {

    Mark findMarkById(Long id);
}

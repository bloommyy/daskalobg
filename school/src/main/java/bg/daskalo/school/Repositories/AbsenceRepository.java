package bg.daskalo.school.Repositories;

import bg.daskalo.school.Entities.Absence;
import bg.daskalo.school.Entities.Student;
import bg.daskalo.school.Entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AbsenceRepository extends JpaRepository<Absence, Long> {
    List<Absence> findAllByStudentAndSubjectOrderByDate(Student student, Subject subject);
}

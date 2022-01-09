package bg.daskalo.school.Repositories;

import bg.daskalo.school.Entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    List<Subject> findAllBySjClass(String sjClass);

    Subject findSubjectById(Long id);

    Optional<Subject> findSubjectByNameAndSjClass(String name, String sjClass);
}

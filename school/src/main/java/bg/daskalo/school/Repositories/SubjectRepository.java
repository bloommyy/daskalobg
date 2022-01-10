package bg.daskalo.school.Repositories;

import bg.daskalo.school.Entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    Set<Subject> findAllBySjClass(String sjClass);

    Subject findSubjectById(Long id);

    Subject findSubjectByNameAndSjClass(String name, String sjClass);
}

package bg.daskalo.school.Repositories;

import bg.daskalo.school.Entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findAllBySjClass(String sjClass);

    Subject findSubjectById(Long id);
}

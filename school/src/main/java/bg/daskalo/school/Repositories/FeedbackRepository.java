package bg.daskalo.school.Repositories;

import bg.daskalo.school.Entities.Absence;
import bg.daskalo.school.Entities.Feedback;
import bg.daskalo.school.Entities.Student;
import bg.daskalo.school.Entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findAllByStudentAndSubject(Student student, Subject subject);
    
    Optional<Feedback> findFeedbackByStudentAndSubject(Student student, Subject subject);

    Optional<Feedback> findFeedbackById(Long id);
}

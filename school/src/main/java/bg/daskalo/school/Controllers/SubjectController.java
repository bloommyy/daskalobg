package bg.daskalo.school.Controllers;

import bg.daskalo.school.Entities.Mark;
import bg.daskalo.school.Entities.Student;
import bg.daskalo.school.Entities.Subject;
import bg.daskalo.school.Entities.Teacher;
import bg.daskalo.school.Repositories.SubjectRepository;
import bg.daskalo.school.Repositories.TeacherRepository;
import bg.daskalo.school.Utils.Validation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/subject")
public class SubjectController {
    private final SubjectRepository subjectRepo;
    private final TeacherRepository teacherRepo;

    public SubjectController(SubjectRepository subjectRepo,
                             TeacherRepository teacherRepo) {

        this.subjectRepo = subjectRepo;
        this.teacherRepo = teacherRepo;
    }

    @PostMapping("/add/subject")
    public ResponseEntity<?> addSubject(String name, String sjClass) {
        Optional<Subject> subject = subjectRepo.findSubjectByNameAndSjClass(name, sjClass);
        if(subject != null)
            return ResponseEntity.ok("Subject already exists!");

        return ResponseEntity.ok("Subject" + subjectRepo.save(new Subject(name, sjClass)).getName() + "has been added.");

    }

    @DeleteMapping("/delete/subject")
    public ResponseEntity<?> deleteSubject(String name, String sjClass) {
        Optional<Subject> deleteSubject = subjectRepo.findSubjectByNameAndSjClass(name, sjClass);
        if(deleteSubject.isEmpty())
            return ResponseEntity.ok("Subject not found!");
        subjectRepo.delete(deleteSubject.get());

        return ResponseEntity.ok("Subject was deleted.");
    }
}



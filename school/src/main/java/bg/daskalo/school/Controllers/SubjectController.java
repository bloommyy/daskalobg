package bg.daskalo.school.Controllers;

import bg.daskalo.school.Entities.Subject;
import bg.daskalo.school.Repositories.SubjectRepository;
import bg.daskalo.school.Repositories.TeacherRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

        if (subject.isPresent())
            return new ResponseEntity<>("Subject already exists.", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>("Subject" + subjectRepo.save(new Subject(name, sjClass)).getName() + "has been added.", HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/subject")
    public ResponseEntity<?> deleteSubject(String name, String sjClass) {
        Optional<Subject> deleteSubject = subjectRepo.findSubjectByNameAndSjClass(name, sjClass);

        if (!deleteSubject.isPresent())
            return new ResponseEntity<>("Subject not found.", HttpStatus.BAD_REQUEST);

        subjectRepo.delete(deleteSubject.get());

        return ResponseEntity.ok("Subject was deleted.");
    }

    @GetMapping("/subjectsByClass")
    public ResponseEntity<?> getSubjectsByClass(String sjClass) {
        List<Subject> subjects = subjectRepo.findAllBySjClass(sjClass);

        if(subjects == null)
            return new ResponseEntity<>("No subjects found.", HttpStatus.BAD_REQUEST);

        return ResponseEntity.ok(subjects);
    }
}



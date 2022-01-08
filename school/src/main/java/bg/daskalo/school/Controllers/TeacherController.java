package bg.daskalo.school.Controllers;

import bg.daskalo.school.Entities.*;
import bg.daskalo.school.Entities.Login.TeacherLogin;
import bg.daskalo.school.Payload.Request.PersistTeacherRequest;
import bg.daskalo.school.Repositories.*;
import bg.daskalo.school.Utils.Security;
import bg.daskalo.school.Utils.Validation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherRepository teacherRepo;
    private final TeacherLoginRepository teacherLoginRepo;
    private final SubjectRepository subjectRepo;
    private final MarkRepository markRepo;
    private final AbsenceRepository absenceRepo;
    private final FeedbackRepository feedbackRepo;
    private final StudentRepository studentRepo;

    public TeacherController(TeacherRepository teacherRepo,
                             TeacherLoginRepository teacherLoginRepo,
                             SubjectRepository subjectRepo,
                             MarkRepository markRepo,
                             AbsenceRepository absenceRepo,
                             FeedbackRepository feedbackRepo,
                             StudentRepository studentRepo) {
        this.teacherRepo = teacherRepo;
        this.teacherLoginRepo = teacherLoginRepo;
        this.subjectRepo = subjectRepo;
        this.markRepo = markRepo;
        this.absenceRepo = absenceRepo;
        this.feedbackRepo = feedbackRepo;
        this.studentRepo = studentRepo;
    }

    @Transactional
    @PostMapping("/register")
    public ResponseEntity<?> persistTeacher(@RequestBody PersistTeacherRequest request) throws NoSuchAlgorithmException {
        Teacher t = teacherRepo.findTeacherByEmail(request.getEmail());
        Student st = studentRepo.findStudentByEmail(request.getEmail());

        if (t != null || st != null)
            return new ResponseEntity<>("That email is already in use.", HttpStatus.BAD_REQUEST);

        Subject tSubject = subjectRepo.findSubjectById(request.getSubjectId());
        if (tSubject == null)
            return new ResponseEntity<>("Subject not found.", HttpStatus.BAD_REQUEST);

        if (!Validation.validateRegistrationTeacher(request.getFirstName(),
                request.getMiddleName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword()))
            return new ResponseEntity<>("Teacher wasn't registered: One or more invalid parameters.", HttpStatus.BAD_REQUEST);

        String hashedPass = Security.encrypt(request.getPassword());

        t = new Teacher(request.getFirstName(),
                request.getMiddleName(),
                request.getLastName(),
                request.getEmail(),
                tSubject);

        TeacherLogin tLogin = new TeacherLogin(t, hashedPass);

        try {
            teacherRepo.save(t);
            teacherLoginRepo.save(tLogin);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseEntity<>("An error occurred and the teacher wasn't registered!", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Registered new teacher " + t.getFirstName() + " "
                + t.getMiddleName().charAt(0) + ". " + t.getLastName() + "!", HttpStatus.CREATED);
    }

    @PostMapping("/add/mark")
    public ResponseEntity<?> addMark(Long stId, Integer mark, Long subjectId, Integer term) {
        Student student = studentRepo.findStudentById(stId);
        if (student == null)
            return new ResponseEntity<>("Student not found.", HttpStatus.BAD_REQUEST);

        Subject subject = subjectRepo.findSubjectById(subjectId);
        if (subject == null)
            return new ResponseEntity<>("Subject not found.", HttpStatus.BAD_REQUEST);

        if (!Validation.validateTerm(term))
            return new ResponseEntity<>("Term is invalid.", HttpStatus.BAD_REQUEST);

        if (!Validation.validateMark(mark))
            return new ResponseEntity<>("Mark is invalid.", HttpStatus.BAD_REQUEST);

        Mark addMark = markRepo.save(new Mark(student, subject, mark, term));
        return new ResponseEntity<>(addMark, HttpStatus.CREATED);

    }

    @DeleteMapping("/delete/mark")
    public ResponseEntity<?> deleteMark(Long id) {
        Optional<Mark> deleteMark = markRepo.findMarkById(id);

        if (!deleteMark.isPresent())
            return new ResponseEntity<>("Mark not found.", HttpStatus.BAD_REQUEST);

        markRepo.delete(deleteMark.get());

        return ResponseEntity.ok("Mark was deleted.");
    }

    @PostMapping("/add/feedback")
    public ResponseEntity<?> addFeedback(Date date, Long subjectId, Long stId, String description) {
        Student student = studentRepo.findStudentById(stId);
        Subject subject = subjectRepo.findSubjectById(subjectId);

        if (student == null)
            return new ResponseEntity<>("Student not found.", HttpStatus.BAD_REQUEST);

        if (subject == null)
            return new ResponseEntity<>("Subject not found.", HttpStatus.BAD_REQUEST);

        if (description == null)
            return new ResponseEntity<>("Enter a valid feedback.", HttpStatus.BAD_REQUEST);

        Feedback addFeedback = feedbackRepo.save(new Feedback(student, date, subject, description));
        return new ResponseEntity<>(addFeedback, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/feedback")
    public ResponseEntity<?> deleteFeedback(Student student, Subject subject) {
        Optional<Feedback> deleteFeedback = feedbackRepo.findFeedbackByStudentAndSubject(student, subject);

        if (!deleteFeedback.isPresent())
            return new ResponseEntity<>("Feedback not found.", HttpStatus.BAD_REQUEST);

        feedbackRepo.delete(deleteFeedback.get());

        return ResponseEntity.ok("Feedback was deleted.");
    }

    @PostMapping("/add/absence")
    public ResponseEntity<?> addAbsence(Date date, Long subjectId, Long stId, boolean isAbsence) {
        Student student = studentRepo.findStudentById(stId);
        Subject subject = subjectRepo.findSubjectById(subjectId);

        if (student == null)
            return new ResponseEntity<>("Student not found.", HttpStatus.BAD_REQUEST);

        if (subject == null)
            return new ResponseEntity<>("Subject not found.", HttpStatus.BAD_REQUEST);

        Absence addAbsence = absenceRepo.save(new Absence(student, date, isAbsence));
        return new ResponseEntity<>(addAbsence, HttpStatus.CREATED);

    }

    @DeleteMapping("/delete/absence")
    public ResponseEntity<?> deleteAbsence(Student student, Subject subject) {
        Optional<Absence> deleteAbsence = absenceRepo.findAbsenceByStudentAndAndSubject(student, subject);

        if (!deleteAbsence.isPresent())
            return new ResponseEntity<>("Absence not found.", HttpStatus.BAD_REQUEST);

        absenceRepo.delete(deleteAbsence.get());

        return ResponseEntity.ok("Absence was deleted.");
    }
}

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
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/teacher")
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
    public ResponseEntity<?> addMark(@RequestParam(value = "stId") UUID stId, Integer mark, @RequestParam(value = "teacherId") UUID teacherId, Integer term) {
        Student student = studentRepo.findStudentById(stId);
        Teacher teacher = teacherRepo.findTeacherById(teacherId);

        if (student == null)
            return new ResponseEntity<>("Student not found.", HttpStatus.BAD_REQUEST);

        if (teacher == null)
            return new ResponseEntity<>("Teacher not found.", HttpStatus.BAD_REQUEST);

        if (!Validation.validateTerm(term))
            return new ResponseEntity<>("Term is invalid.", HttpStatus.BAD_REQUEST);

        if (!Validation.validateMark(mark))
            return new ResponseEntity<>("Mark is invalid.", HttpStatus.BAD_REQUEST);

        Mark addMark = markRepo.save(new Mark(student, teacher.getSubject(), mark, term));
        return new ResponseEntity<>("Added mark " + addMark.getMark() +
                " to " + addMark.getStudent().getFirstName()
                + " " + addMark.getStudent().getMiddleName().charAt(0) + ". " +
                addMark.getStudent().getLastName(), HttpStatus.CREATED);
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
    public ResponseEntity<?> addFeedback(Long subjectId,@RequestParam(value = "stId") UUID stId, String description) {
        Student student = studentRepo.findStudentById(stId);
        Subject subject = subjectRepo.findSubjectById(subjectId);

        if (student == null)
            return new ResponseEntity<>("Student not found.", HttpStatus.BAD_REQUEST);

        if (subject == null)
            return new ResponseEntity<>("Subject not found.", HttpStatus.BAD_REQUEST);

        if (description == null)
            return new ResponseEntity<>("Enter a valid feedback.", HttpStatus.BAD_REQUEST);

        Feedback addFeedback = feedbackRepo.save(new Feedback(student, new Date(), subject, description));
        return new ResponseEntity<>("Feedback : " + addFeedback.getDescription() + " saved successfully.", HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/feedback")
    public ResponseEntity<?> deleteFeedback(Long id) {
        Optional<Feedback> deleteFeedback = feedbackRepo.findFeedbackById(id);

        if (!deleteFeedback.isPresent())
            return new ResponseEntity<>("Feedback not found.", HttpStatus.BAD_REQUEST);

        feedbackRepo.delete(deleteFeedback.get());

        return ResponseEntity.ok("Feedback was deleted.");
    }

    @PostMapping("/add/absence")
    public ResponseEntity<?> addAbsence(Long subjectId, @RequestParam(value = "stId") UUID stId, boolean isAbsence) {
        Student student = studentRepo.findStudentById(stId);
        Subject subject = subjectRepo.findSubjectById(subjectId);

        if (student == null)
            return new ResponseEntity<>("Student not found.", HttpStatus.BAD_REQUEST);

        if (subject == null)
            return new ResponseEntity<>("Subject not found.", HttpStatus.BAD_REQUEST);

        Absence addAbsence = absenceRepo.save(new Absence(student, new Date(), isAbsence, subject));
        return new ResponseEntity<>("Absence given to " + addAbsence.getStudent().getFirstName() + ".", HttpStatus.CREATED);
    }

    @PostMapping("/excuse/absence")
    public ResponseEntity<?> excuseAbsence(Long id){
        Optional<Absence> excuseAbsence = absenceRepo.findAbsenceById(id);

        if(!excuseAbsence.isPresent())
            return new ResponseEntity<>("Absence not found.", HttpStatus.BAD_REQUEST);

        excuseAbsence.get().setExcused(true);

        absenceRepo.save(excuseAbsence.get());

        return ResponseEntity.ok("Excused absence on date " + excuseAbsence.get().getDate() + ".");
    }

    @DeleteMapping("/delete/absence")
    public ResponseEntity<?> deleteAbsence(Long id) {
        Optional<Absence> deleteAbsence = absenceRepo.findAbsenceById(id);

        if (!deleteAbsence.isPresent())
            return new ResponseEntity<>("Absence not found.", HttpStatus.BAD_REQUEST);

        absenceRepo.delete(deleteAbsence.get());

        return ResponseEntity.ok("Absence was deleted.");
    }
}

package bg.daskalo.school.Controllers;

import bg.daskalo.school.Entities.Login.StudentLogin;
import bg.daskalo.school.Entities.Login.TeacherLogin;
import bg.daskalo.school.Entities.Mark;
import bg.daskalo.school.Entities.Subject;
import bg.daskalo.school.Entities.Teacher;
import bg.daskalo.school.Entities.Student;
import bg.daskalo.school.Payload.Request.PersistTeacherRequest;
import bg.daskalo.school.Repositories.*;
import bg.daskalo.school.Utils.Security;
import bg.daskalo.school.Utils.Validation;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.List;

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

        if (t != null) {
            return ResponseEntity.ok("That teacher is already registered.");
        }

        Subject tSubject = subjectRepo.findSubjectById(request.getSubjectId());
        if(tSubject == null)
            return ResponseEntity.ok("Subject not found.");

        if (!Validation.validateRegistrationTeacher(request.getFirstName(),
                request.getMiddleName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword()))
            return ResponseEntity.ok("Teacher wasn't registered: One or more invalid parameters.");

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
            return ResponseEntity.ok("An error occurred and the teacher wasn't registered!");
        }

        return new ResponseEntity<>("Registered new teacher " + t.getFirstName() + " "
                + t.getMiddleName().charAt(0) + ". " + t.getLastName() + "!", HttpStatus.CREATED);
    }

    @GetMapping("/login")
    public ResponseEntity<?> loginTeacher(String email, String password) throws NoSuchAlgorithmException {

        if (email == null ||
                password == null)
            return ResponseEntity.ok("Incorrect email or password.");

        if (email.isEmpty() ||
                password.isEmpty())
            return ResponseEntity.ok("Incorrect email or password.");

        String hashedPassword = Security.encrypt(password);

        List<TeacherLogin> tsLogin = teacherLoginRepo.findTeacherLoginsByPassword(hashedPassword);

        for (TeacherLogin tLog : tsLogin) {
            if (tLog.getTeacher().getEmail().equals(email))
                return ResponseEntity.ok("Teacher found with id:" + tLog.getTeacher().getId());
        }

        return ResponseEntity.ok("Incorrect email or password.");
    }

    //adding marks

    @PostMapping("/add/marks")
    public ResponseEntity<?> addMark(Long stId, Integer mark, Long subjectId, Integer term) {
        Student student = studentRepo.findStudentById(stId);
        if(student == null)
            return ResponseEntity.ok("Student not found.");

        Subject subject = subjectRepo.findSubjectById(subjectId);
        if(subject == null)
            return ResponseEntity.ok("Subject not found.");

        if(!Validation.validateTerm(term))
            return ResponseEntity.ok("Enter a valid term.");

        if(!Validation.validateMark(mark))
            return ResponseEntity.ok("Enter valid mark!");

        Mark addMark = markRepo.save(new Mark(student, subject, mark, term));
        return ResponseEntity.ok(mark);

    }

    @DeleteMapping("/delete/marks")
    public ResponseEntity<?> deleteMark(Student student, Subject subject) {
        Optional<Mark> deleteMark = markRepo.findMarkByStudentAndAndSubject(student, subject);
        if(deleteMark.isEmpty())
            return ResponseEntity.ok("Mark not found!");
        markRepo.delete(deleteMark.get());

        return ResponseEntity.ok("Mark was delete.");

    }
// added feedback/absence methods
    @PostMapping("/add/feedback")
    public ResponseEntity<?> addFeedback(Date date, Long subjectId,Long stId ,String description) {
        Student student = studentRepo.findStudentById(stId);
        if(student == null)
            return ResponseEntity.ok("Student not found.");

        Subject subject = subjectRepo.findSubjectById(subjectId);
        if(subject == null)
            return ResponseEntity.ok("Subject not found.");

        if(description==null)
            return ResponseEntity.ok("Enter a valid feedback.");

        Feedback addFeedback = feedbackRepo.save(new Feedback(student, date ,subject, description));
        return ResponseEntity.ok(description);

    }
    
    @DeleteMapping("/delete/feedback")
    public ResponseEntity<?> deleteFeedback(Student student, Subject subject) {
        Optional<Feedback> deleteFeedback = feedbackRepo.findFeedbackByStudentAndAndSubject(student, subject);
        if(deleteFeedback.isEmpty())
            return ResponseEntity.ok("Feedback not found!");
        markRepo.delete(deleteFeedback.get());

        return ResponseEntity.ok("Feedback was delete.");

    }
    
    @PostMapping("/add/absence")
    public ResponseEntity<?> addAbsence(Date date,Long subjectId,Long stId, boolean isAbsence) {
        Student student = studentRepo.findStudentById(stId);
        if(student == null)
            return ResponseEntity.ok("Student not found.");

        Subject subject = subjectRepo.findSubjectById(subjectId);
        if(subject == null)
            return ResponseEntity.ok("Subject not found.");

        Absence addAbsence = absenceRepo.save(new Absence(student, date ,isAbsence));
        return ResponseEntity.ok(isAbsence);

    }
    
    @DeleteMapping("/delete/absence")
    public ResponseEntity<?> deleteAbsence(Student student, Subject subject) {
        Optional<Absence> deleteAbsence = absenceRepo.findAbsenceByStudentAndAndSubject(student, subject);
        if(deleteAbsence.isEmpty())
            return ResponseEntity.ok("Absence not found!");
        markRepo.delete(deleteAbsence.get());

        return ResponseEntity.ok("Absence was delete.");

    }
}

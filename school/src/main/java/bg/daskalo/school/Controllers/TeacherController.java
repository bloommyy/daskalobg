package bg.daskalo.school.Controllers;

import bg.daskalo.school.Entities.*;
import bg.daskalo.school.Entities.Login.TeacherLogin;
import bg.daskalo.school.Models.TStudentAbsenceModel;
import bg.daskalo.school.Models.TStudentFeedbackModel;
import bg.daskalo.school.Models.TStudentMarkModel;
import bg.daskalo.school.Payload.Request.PersistTeacherRequest;
import bg.daskalo.school.Repositories.*;
import bg.daskalo.school.Utils.HelpfulThings;
import bg.daskalo.school.Utils.Security;
import bg.daskalo.school.Utils.Validation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Set;
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

    @PostMapping("/mark/add")
    public ResponseEntity<?> addMark(@RequestParam(value = "stId") UUID stId, Integer mark, @RequestParam(value = "teacherId") UUID teacherId, Integer term) {
        if(term == null)
            return new ResponseEntity<>("term is invalid.", HttpStatus.BAD_REQUEST);

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

        String stClass = student.getStClass();
        Set<Student> students = studentRepo.findStudentsByStClass(stClass);

        List<TStudentMarkModel> studentMarkModels = HelpfulThings.ProcessMarks(students, teacher.getSubject());

        return new ResponseEntity<>(studentMarkModels, HttpStatus.CREATED);
    }

    @DeleteMapping("/mark/delete")
    public ResponseEntity<?> deleteMark(@RequestParam(value = "teacherId") UUID teacherId, Long id) {
        if(id == null)
            return new ResponseEntity<>("id is invalid.", HttpStatus.BAD_REQUEST);

        Teacher teacher = teacherRepo.findTeacherById(teacherId);

        if (teacher == null){
            return new ResponseEntity<>("Missing privileges.", HttpStatus.BAD_REQUEST);
        }

        Mark deleteMark = markRepo.findMarkById(id);

        if (deleteMark == null)
            return new ResponseEntity<>("Mark not found.", HttpStatus.BAD_REQUEST);

        markRepo.delete(deleteMark);

        String stClass = deleteMark.getStudent().getStClass();
        Set<Student> students = studentRepo.findStudentsByStClass(stClass);

        List<TStudentMarkModel> studentMarkModels = HelpfulThings.ProcessMarks(students, teacher.getSubject());

        return ResponseEntity.ok(studentMarkModels);
    }

    @PostMapping("/feedback/add")
    public ResponseEntity<?> addFeedback(@RequestParam(value = "teacherId") UUID teacherId, @RequestParam(value = "stId") UUID stId, String description) {
        Student student = studentRepo.findStudentById(stId);
        Teacher teacher = teacherRepo.findTeacherById(teacherId);

        if (student == null)
            return new ResponseEntity<>("Student not found.", HttpStatus.BAD_REQUEST);

        if (teacher == null)
            return new ResponseEntity<>("Subject not found.", HttpStatus.BAD_REQUEST);

        if (description == null)
            return new ResponseEntity<>("Enter a valid feedback.", HttpStatus.BAD_REQUEST);

        feedbackRepo.save(new Feedback(student, new Date(), teacher.getSubject(), description));

        String stClass = student.getStClass();
        Set<Student> students = studentRepo.findStudentsByStClass(stClass);

        List<TStudentFeedbackModel> studentMarkModels = HelpfulThings.ProcessFeedbacks(students, teacher.getSubject());

        return new ResponseEntity<>(studentMarkModels, HttpStatus.CREATED);
    }

    @DeleteMapping("/feedback/delete")
    public ResponseEntity<?> deleteFeedback(@RequestParam(value = "teacherId") UUID teacherId, Long id) {
        if(id == null)
            return new ResponseEntity<>("id is invalid.", HttpStatus.BAD_REQUEST);

        Teacher teacher = teacherRepo.findTeacherById(teacherId);

        if (teacher == null){
            return new ResponseEntity<>("Missing privileges.", HttpStatus.BAD_REQUEST);
        }

        Feedback deleteFeedback = feedbackRepo.findFeedbackById(id);

        if (deleteFeedback == null)
            return new ResponseEntity<>("Feedback not found.", HttpStatus.BAD_REQUEST);

        feedbackRepo.delete(deleteFeedback);

        String stClass = deleteFeedback.getStudent().getStClass();
        Set<Student> students = studentRepo.findStudentsByStClass(stClass);

        List<TStudentFeedbackModel> studentMarkModels = HelpfulThings.ProcessFeedbacks(students, teacher.getSubject());

        return ResponseEntity.ok(studentMarkModels);
    }

    @PostMapping("/absence/add")
    public ResponseEntity<?> addAbsence(@RequestParam(value = "teacherId") UUID teacherId, @RequestParam(value = "stId") UUID stId, boolean isAbsence) {
        Student student = studentRepo.findStudentById(stId);
        Teacher teacher = teacherRepo.findTeacherById(teacherId);

        if (student == null)
            return new ResponseEntity<>("Student not found.", HttpStatus.BAD_REQUEST);

        if (teacher == null)
            return new ResponseEntity<>("Teacher not found.", HttpStatus.BAD_REQUEST);

        Subject subject = teacher.getSubject();
        Absence addAbsence = absenceRepo.save(new Absence(student, new Date(), isAbsence, subject));

        String stClass = student.getStClass();
        Set<Student> students = studentRepo.findStudentsByStClass(stClass);

        List<TStudentAbsenceModel> studentAbsenceList = HelpfulThings.ProcessAbsences(students, subject);

        return new ResponseEntity<>(studentAbsenceList, HttpStatus.CREATED);
    }

    @PutMapping("/absence/excuse")
    public ResponseEntity<?> excuseAbsence(@RequestParam(value = "teacherId") UUID teacherId, Long id) {
        if(id == null)
            return new ResponseEntity<>("id is invalid.", HttpStatus.BAD_REQUEST);

        Teacher teacher = teacherRepo.findTeacherById(teacherId);

        if (teacher == null){
            return new ResponseEntity<>("Missing privileges.", HttpStatus.BAD_REQUEST);
        }

        Absence excuseAbsence = absenceRepo.findAbsenceById(id);

        if (excuseAbsence == null)
            return new ResponseEntity<>("Absence not found.", HttpStatus.BAD_REQUEST);

        if (!excuseAbsence.isAbsence())
            return new ResponseEntity<>("Half absence cannot be excused.", HttpStatus.BAD_REQUEST);

        excuseAbsence.setExcused(true);

        absenceRepo.save(excuseAbsence);

        String stClass = excuseAbsence.getStudent().getStClass();
        Set<Student> students = studentRepo.findStudentsByStClass(stClass);

        List<TStudentAbsenceModel> studentAbsenceList = HelpfulThings.ProcessAbsences(students, teacher.getSubject());

        return ResponseEntity.ok(studentAbsenceList);
    }

    @DeleteMapping("/absence/delete")
    public ResponseEntity<?> deleteAbsence(@RequestParam(value = "teacherId") UUID teacherId, Long id) {
        if(id == null)
            return new ResponseEntity<>("id is invalid.", HttpStatus.BAD_REQUEST);

        Teacher teacher = teacherRepo.findTeacherById(teacherId);

        if (teacher == null){
            return new ResponseEntity<>("Missing privileges.", HttpStatus.BAD_REQUEST);
        }

        Absence deleteAbsence = absenceRepo.findAbsenceById(id);

        if (deleteAbsence == null)
            return new ResponseEntity<>("Absence not found.", HttpStatus.BAD_REQUEST);

        absenceRepo.delete(deleteAbsence);

        String stClass = deleteAbsence.getStudent().getStClass();
        Set<Student> students = studentRepo.findStudentsByStClass(stClass);

        List<TStudentAbsenceModel> studentAbsenceList = HelpfulThings.ProcessAbsences(students, teacher.getSubject());

        return ResponseEntity.ok(studentAbsenceList);
    }

    @GetMapping("/classes")
    public ResponseEntity<?> getAllClasses(@RequestParam(value = "teacherId") UUID teacherId) {
        Teacher teacher = teacherRepo.findTeacherById(teacherId);

        if (teacher == null){
            return new ResponseEntity<>("Missing privileges.", HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(teacherRepo.fetchAllClasses());
    }
}

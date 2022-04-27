package bg.daskalo.school.Controllers;

import bg.daskalo.school.Entities.*;
import bg.daskalo.school.Entities.Login.StudentLogin;
import bg.daskalo.school.Models.StudentAbsenceModel;
import bg.daskalo.school.Models.StudentFeedbackModel;
import bg.daskalo.school.Models.StudentMarkModel;
import bg.daskalo.school.Payload.Request.PersistStudentRequest;
import bg.daskalo.school.Repositories.*;
import bg.daskalo.school.Utils.Security;
import bg.daskalo.school.Utils.Validation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentRepository studentRepo;
    private final StudentLoginRepository studentLoginRepo;
    private final TeacherRepository teacherRepo;
    private final SubjectRepository subjectRepo;
    private final MarkRepository markRepo;

    StudentController(StudentRepository studentRepo, StudentLoginRepository studentLoginRepo, TeacherRepository teacherRepo, SubjectRepository subjectRepo, MarkRepository markRepo) {
        this.studentRepo = studentRepo;
        this.studentLoginRepo = studentLoginRepo;
        this.teacherRepo = teacherRepo;
        this.subjectRepo = subjectRepo;
        this.markRepo = markRepo;
    }

    @Transactional
    @PostMapping("/register")
    public ResponseEntity<?> persistStudent(@RequestBody PersistStudentRequest request) throws NoSuchAlgorithmException {
        Student st = studentRepo.findStudentByEmail(request.getEmail());
        Teacher teacher = teacherRepo.findTeacherByEmail(request.getEmail());

        if (st != null || teacher != null)
            return ResponseEntity.ok("That email is already in use.");

        if (!Validation.validateRegistrationStudent(request.getFirstName(),
                request.getMiddleName(),
                request.getLastName(),
                request.getEmail(),
                request.getEgn(),
                request.getPassword(),
                request.getStClass()))
            return ResponseEntity.ok("Student wasn't registered: One or more invalid parameters.");

        String hashedPass = Security.encrypt(request.getPassword());

        st = new Student(request.getFirstName(),
                request.getMiddleName(),
                request.getLastName(),
                request.getEmail(),
                request.getEgn(),
                request.getStClass());

        StudentLogin stLogin = new StudentLogin(st, hashedPass);

        try {
            studentRepo.save(st);
            studentLoginRepo.save(stLogin);

            Integer numberInClass = 1;
            studentRepo.setStudentsClassNumToNull(request.getStClass());

            List<Student> stInClass = studentRepo.fetchSortedStudentsByStClass(request.getStClass());

            for (Student student : stInClass) {
                studentRepo.updateStudentClassNum(student.getId(), numberInClass++);
            }

            return new ResponseEntity<>("Registered new student " + st.getFirstName() + " "
                    + st.getMiddleName().charAt(0) + ". " + st.getLastName() + "!", HttpStatus.CREATED);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseEntity.ok("An error occurred and the student wasn't registered!");
        }
    }

    @GetMapping("/marks")
    public ResponseEntity<?> getMarks(@RequestParam(value = "stId") UUID stId) {
        Student st = studentRepo.findStudentById(stId);

        if (st == null)
            return new ResponseEntity<>("Student not found.", HttpStatus.BAD_REQUEST);

        List<Mark> marks = st.getMarks();

        return ResponseEntity.ok(marks);
    }

    @GetMapping("/absences")
    public ResponseEntity<?> getAbsences(@RequestParam(value = "stId") UUID stId) {
        Student st = studentRepo.findStudentById(stId);

        if (st == null)
            return new ResponseEntity<>("Student not found.", HttpStatus.BAD_REQUEST);

        List<Absence> absences = st.getAbsences();

        return ResponseEntity.ok(absences);
    }

    @GetMapping("/feedbacks")
    public ResponseEntity<?> getFeedbacks(@RequestParam(value = "stId") UUID stId) {
        Student st = studentRepo.findStudentById(stId);

        if (st == null)
            return new ResponseEntity<>("Student not found.", HttpStatus.BAD_REQUEST);

        List<Feedback> feedbacks = st.getFeedbacks();

        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping("/marks/byClassAndSubject")
    public ResponseEntity<?> getMarksByClassAndBySubject(String stClass, Long sjId) {
        Set<Student> students = studentRepo.findStudentsByStClass(stClass);
        Subject subject = subjectRepo.findSubjectById(sjId);

        if (students != null & students.isEmpty())
            return new ResponseEntity<>("Students not found.", HttpStatus.BAD_REQUEST);

        if (subject == null)
            return new ResponseEntity<>("Subject not found.", HttpStatus.BAD_REQUEST);

        List<StudentMarkModel> studentMarkList = new ArrayList<>();

        for (Student student : students) {
            for (Mark mark : student.getMarks()) {
                if (mark.getSubject() == subject) {
                    studentMarkList.add(new StudentMarkModel(
                            mark.getId(),
                            student.getFirstName() + " " + student.getMiddleName() + " " +
                                    student.getLastName(),
                            mark.getMark(), mark.getTerm()
                    ));
                }
            }
        }

        return ResponseEntity.ok(studentMarkList);
    }

    @GetMapping("/absences/byClassAndSubject")
    public ResponseEntity<?> getAbsencesByClassAndBySubject(String stClass, Long sjId) {
        Set<Student> students = studentRepo.findStudentsByStClass(stClass);
        Subject subject = subjectRepo.findSubjectById(sjId);

        if (students != null & students.isEmpty())
            return new ResponseEntity<>("Students not found.", HttpStatus.BAD_REQUEST);

        if (subject == null)
            return new ResponseEntity<>("Subject not found.", HttpStatus.BAD_REQUEST);

        List<StudentAbsenceModel> studentAbsenceList = new ArrayList<>();

        for (Student student : students) {
            for (Absence absence : student.getAbsences()) {
                if (absence.getSubject() == subject) {
                    studentAbsenceList.add(new StudentAbsenceModel(
                            absence.getId(),
                            student.getFirstName() + " " + student.getMiddleName() + " " +
                                    student.getLastName(),
                            absence.isAbsence(), absence.isExcused(), absence.getDate()
                    ));
                }
            }
        }

        return ResponseEntity.ok(studentAbsenceList);
    }

    @GetMapping("/feedbacks/byClassAndSubject")
    public ResponseEntity<?> getFeedbacksByClassAndBySubject(String stClass, Long sjId) {
        Set<Student> students = studentRepo.findStudentsByStClass(stClass);
        Subject subject = subjectRepo.findSubjectById(sjId);

        if (students != null & students.isEmpty())
            return new ResponseEntity<>("Students not found.", HttpStatus.BAD_REQUEST);

        if (subject == null)
            return new ResponseEntity<>("Subject not found.", HttpStatus.BAD_REQUEST);

        List<StudentFeedbackModel> studentFeedackList = new ArrayList<>();

        for (Student student : students) {
            for (Feedback feedback : student.getFeedbacks()) {
                if (feedback.getSubject() == subject) {
                    studentFeedackList.add(new StudentFeedbackModel(
                            student.getFirstName() + " " + student.getMiddleName() + " " +
                                    student.getLastName(),
                            feedback.getDescription(), feedback.getDate()
                    ));
                }
            }
        }

        return ResponseEntity.ok(studentFeedackList);
    }

    @GetMapping("/nameByClass")
    public ResponseEntity<?> getStudentNamesByClass(String stClass) {
        Set<Student> students = studentRepo.findStudentsByStClass(stClass);

        if (students != null & students.isEmpty())
            return new ResponseEntity<>("Students not found.", HttpStatus.BAD_REQUEST);

        return ResponseEntity.ok(students);
    }
}

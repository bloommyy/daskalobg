package bg.daskalo.school.Controllers;

import bg.daskalo.school.Entities.*;
import bg.daskalo.school.Entities.Login.StudentLogin;
import bg.daskalo.school.Payload.Request.PersistStudentRequest;
import bg.daskalo.school.Repositories.StudentLoginRepository;
import bg.daskalo.school.Repositories.StudentRepository;
import bg.daskalo.school.Repositories.TeacherRepository;
import bg.daskalo.school.Utils.Security;
import bg.daskalo.school.Utils.Validation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentRepository studentRepo;
    private final StudentLoginRepository studentLoginRepo;
    private final TeacherRepository teacherRepo;

    StudentController(StudentRepository studentRepo, StudentLoginRepository studentLoginRepo, TeacherRepository teacherRepo) {
        this.studentRepo = studentRepo;
        this.studentLoginRepo = studentLoginRepo;
        this.teacherRepo = teacherRepo;
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
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseEntity.ok("An error occurred and the student wasn't registered!");
        }

        Integer numberInClass = 1;
        studentRepo.setStudentClassNumToNull(request.getStClass());
        List<Student> stInClass = studentRepo.fetchSortedStudentsByStClass(request.getStClass());

        for (Student student : stInClass) {
            studentRepo.updateStudentClassNum(student.getId(), numberInClass++);
        }

        return new ResponseEntity<>("Registered new student " + st.getFirstName() + " "
                + st.getMiddleName().charAt(0) + ". " + st.getLastName() + "!", HttpStatus.CREATED);
    }

    @GetMapping("/marks")
    public ResponseEntity<?> getMarks(UUID stId) {
        Student st = studentRepo.findStudentById(stId);

        if(st == null)
            return new ResponseEntity<>("Student not found.", HttpStatus.BAD_REQUEST);

        List<Mark> marks = st.getMarks();

        return ResponseEntity.ok(marks);
    }

    @GetMapping("/absences")
    public ResponseEntity<?> getAbsences(UUID stId) {
        Student st = studentRepo.findStudentById(stId);

        if(st == null)
            return new ResponseEntity<>("Student not found.", HttpStatus.BAD_REQUEST);

        List<Absence> absences = st.getAbsences();

        return ResponseEntity.ok(absences);
    }

    @GetMapping("/feedbacks")
    public ResponseEntity<?> getFeedbacks(UUID stId) {
        Student st = studentRepo.findStudentById(stId);

        if(st == null)
            return new ResponseEntity<>("Student not found.", HttpStatus.BAD_REQUEST);

        List<Feedback> feedbacks = st.getFeedbacks();

        return ResponseEntity.ok(feedbacks);
    }
}

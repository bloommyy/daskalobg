package bg.daskalo.school.Controllers;

import bg.daskalo.school.Entities.Login.StudentLogin;
import bg.daskalo.school.Entities.Student;
import bg.daskalo.school.Payload.Request.PersistStudentRequest;
import bg.daskalo.school.Repositories.StudentLoginRepository;
import bg.daskalo.school.Repositories.StudentRepository;
import bg.daskalo.school.Utils.Security;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentRepository studentRepo;
    private final StudentLoginRepository studentLoginRepo;

    StudentController(StudentRepository studentRepo, StudentLoginRepository studentLoginRepo) {
        this.studentRepo = studentRepo;
        this.studentLoginRepo = studentLoginRepo;
    }


    private static boolean validateRegistration(String fname,
                                                String mname,
                                                String lname,
                                                String email,
                                                String egn,
                                                String password,
                                                String stClass) {

        if (fname == null ||
                mname == null ||
                lname == null ||
                email == null ||
                egn == null ||
                password == null ||
                stClass == null)
            return false;

        if (fname.isEmpty() ||
                mname.isEmpty() ||
                lname.isEmpty() ||
                email.isEmpty() ||
                egn.isEmpty() ||
                password.isEmpty() ||
                stClass.isEmpty())
            return false;

        for (char c : fname.toCharArray()) {
            if (!Character.isLetter(c))
                return false;
        }

        for (char c : mname.toCharArray()) {
            if (!Character.isLetter(c))
                return false;
        }

        for (char c : lname.toCharArray()) {
            if (!Character.isLetter(c))
                return false;
        }

        if (!EmailValidator.getInstance(true).isValid(email))
            return false;

        if (egn.length() != 10)
            return false;

        for (char c : egn.toCharArray()) {
            if (!Character.isDigit(c))
                return false;
        }

        return password.length() >= 8;
    }

    @Transactional
    @PostMapping("/register")
    public ResponseEntity<?> persistStudent(@RequestBody PersistStudentRequest request) throws NoSuchAlgorithmException {
        Student st = studentRepo.findStudentByEgn(request.getEgn());

        if (st != null) {
            return ResponseEntity.ok("That student is already registered.");
        }

        if (!validateRegistration(request.getFirstName(),
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
            //Test();
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

    @GetMapping("/login")
    public ResponseEntity<?> loginStudent(String egn, String password) throws NoSuchAlgorithmException {

        if (egn == null ||
                password == null)
            return ResponseEntity.ok("Incorrect EGN or password.");

        if (egn.isEmpty() ||
                password.isEmpty())
            return ResponseEntity.ok("Incorrect EGN or password.");

        String hashedPassword = Security.encrypt(password);

        List<StudentLogin> stsLogin = studentLoginRepo.findStudentsLoginByPassword(hashedPassword);

        for (StudentLogin stLog : stsLogin) {
            if (stLog.getStudent().getEgn().equals(egn))
                return ResponseEntity.ok("Student found with id:" + stLog.getStudent().getId());
        }

        return ResponseEntity.ok("Incorrect EGN or password.");
    }

    private void Test() throws Exception {
        throw new Exception("test");
    }
}

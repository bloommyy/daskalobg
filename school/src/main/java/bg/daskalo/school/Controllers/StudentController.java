package bg.daskalo.school.Controllers;

import bg.daskalo.school.Entities.Login.StudentLogin;
import bg.daskalo.school.Entities.Student;
import bg.daskalo.school.Repositories.StudentLoginRepository;
import bg.daskalo.school.Repositories.StudentRepository;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
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

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private static boolean Validate(String fname,
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

        return true;
    }

    @Transactional
    @PostMapping("/register")
    public ResponseEntity<?> persistStudent(String fname,
                                            String mname,
                                            String lname,
                                            String email,
                                            String egn,
                                            String stClass,
                                            String password) throws NoSuchAlgorithmException {
        Student st = studentRepo.findStudentByEgn(egn);

        if (st != null) {
            return ResponseEntity.ok("That student is already registered.");
        }

        if (!Validate(fname, mname, lname, email, egn, password, stClass))
            return ResponseEntity.ok("Student wasn't registered: One or more invalid parameters.");

        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        final byte[] hashbytes = digest.digest(
                password.getBytes(StandardCharsets.UTF_8));
        String hashedPass = bytesToHex(hashbytes);

        st = new Student(fname, mname, lname, email, egn, stClass);
        StudentLogin stLogin = new StudentLogin(st, hashedPass);

        try {
            studentRepo.save(st);
            studentLoginRepo.save(stLogin);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseEntity.ok("An error occurred and the student wasn't registered!");
        }

        Integer numberInClass = 1;
        studentRepo.setStudentClassNumToNull(stClass);
        List<Student> stInClass = studentRepo.fetchSortedStudentsByStClass(stClass);

        for (Student student : stInClass) {
            studentRepo.updateStudentClassNum(student.getId(), numberInClass++);
        }

        return new ResponseEntity<>("Registered new student " + fname + " " + mname.charAt(0) + ". " + lname + "!", HttpStatus.CREATED);
    }
}

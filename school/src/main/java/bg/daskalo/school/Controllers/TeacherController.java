package bg.daskalo.school.Controllers;

import bg.daskalo.school.Entities.Login.StudentLogin;
import bg.daskalo.school.Entities.Login.TeacherLogin;
import bg.daskalo.school.Entities.Teacher;
import bg.daskalo.school.Payload.Request.PersistTeacherRequest;
import bg.daskalo.school.Repositories.TeacherLoginRepository;
import bg.daskalo.school.Repositories.TeacherRepository;
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
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherRepository teacherRepo;
    private final TeacherLoginRepository teacherLoginRepo;

    public TeacherController(TeacherRepository teacherRepo, TeacherLoginRepository teacherLoginRepo) {
        this.teacherRepo = teacherRepo;
        this.teacherLoginRepo = teacherLoginRepo;
    }

    private static boolean validateRegistration(String fname,
                                                String mname,
                                                String lname,
                                                String email,
                                                String password) {

        if (fname == null ||
                mname == null ||
                lname == null ||
                email == null ||
                password == null)
            return false;

        if (fname.isEmpty() ||
                mname.isEmpty() ||
                lname.isEmpty() ||
                email.isEmpty() ||
                password.isEmpty())
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

        return password.length() >= 8;
    }

    @Transactional
    @PostMapping("/register")
    public ResponseEntity<?> persistTeacher(@RequestBody PersistTeacherRequest request) throws NoSuchAlgorithmException {
        Teacher t = teacherRepo.findTeacherByEmail(request.getEmail());

        if (t != null) {
            return ResponseEntity.ok("That teacher is already registered.");
        }

        if (!validateRegistration(request.getFirstName(),
                request.getMiddleName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword()))
            return ResponseEntity.ok("Teacher wasn't registered: One or more invalid parameters.");

        String hashedPass = Security.encrypt(request.getPassword());

        t = new Teacher(request.getFirstName(),
                request.getMiddleName(),
                request.getLastName(),
                request.getEmail());

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
}

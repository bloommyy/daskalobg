package bg.daskalo.school.Controllers;

import bg.daskalo.school.Entities.Login.StudentLogin;
import bg.daskalo.school.Entities.Login.TeacherLogin;
import bg.daskalo.school.Payload.Request.LoginRequest;
import bg.daskalo.school.Payload.Response.StudentLoginResponse;
import bg.daskalo.school.Repositories.StudentLoginRepository;
import bg.daskalo.school.Repositories.StudentRepository;
import bg.daskalo.school.Repositories.TeacherLoginRepository;
import bg.daskalo.school.Repositories.TeacherRepository;
import bg.daskalo.school.Utils.Security;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/login")
public class LoginController {

    private final StudentRepository studentRepo;
    private final StudentLoginRepository studentLoginRepo;
    private final TeacherRepository teacherRepo;
    private final TeacherLoginRepository teacherLoginRepo;

    public LoginController(StudentRepository studentRepo,
                           StudentLoginRepository studentLoginRepo,
                           TeacherRepository teacherRepo,
                           TeacherLoginRepository teacherLoginRepo) {
        this.studentRepo = studentRepo;
        this.studentLoginRepo = studentLoginRepo;
        this.teacherRepo = teacherRepo;
        this.teacherLoginRepo = teacherLoginRepo;
    }

    @PostMapping()
    public ResponseEntity<?> login(@RequestBody LoginRequest request) throws NoSuchAlgorithmException {

        if (request.getEmail() == null ||
                request.getPassword() == null)
            return new ResponseEntity<>("Incorrect email or password.", HttpStatus.BAD_REQUEST);

        if (request.getEmail().isEmpty() ||
                request.getPassword().isEmpty())
            return new ResponseEntity<>("Incorrect email or password.", HttpStatus.BAD_REQUEST);

        String hashedPassword = Security.encrypt(request.getPassword());

        List<StudentLogin> stsLogin = studentLoginRepo.findStudentsLoginByPassword(hashedPassword);

        for (StudentLogin stLog : stsLogin) {
            if (stLog.getStudent().getEmail().equals(request.getEmail()))
                return ResponseEntity.ok(new StudentLoginResponse(stLog.getStudent()));
        }

        List<TeacherLogin> tsLogin = teacherLoginRepo.findTeacherLoginsByPassword(hashedPassword);

        for (TeacherLogin tLog : tsLogin) {
            if (tLog.getTeacher().getEmail().equals(request.getEmail()))
                return ResponseEntity.ok(tLog.getTeacher());
        }

        return new ResponseEntity<>("Incorrect email or password.", HttpStatus.BAD_REQUEST);
    }
}

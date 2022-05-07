package bg.daskalo.school.Controllers;

import bg.daskalo.school.Entities.*;
import bg.daskalo.school.Entities.Login.StudentLogin;
import bg.daskalo.school.Models.*;
import bg.daskalo.school.Payload.Request.PersistStudentRequest;
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
import java.text.DecimalFormat;
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

        ArrayList<StudentMarkModel> models = new ArrayList<>();

        Set<Subject> subjects = subjectRepo.findAllBySjClassOrderByName(st.getStClass().substring(0, st.getStClass().length() - 1));
        for (Subject sj : subjects) {
            models.add(new StudentMarkModel(sj.getName()));
        }

        ArrayList<Mark> marks = new ArrayList<>(st.getMarks());
        marks = HelpfulThings.ReverseList(marks);

        for (Mark mark : marks) {
            for (StudentMarkModel model : models) {
                if (model.getSubject().equals(mark.getSubject().getName())) {
                    if (mark.getTerm() == 1) {
                        String firstTermMarks = model.getFirstTerm() == null ? "" : model.getFirstTerm();
                        firstTermMarks += mark.getMark().toString();
                        firstTermMarks += ", ";
                        model.setFirstTerm(firstTermMarks);
                    } else {
                        String secondTermMarks = model.getSecondTerm() == null ? "" : model.getSecondTerm();
                        secondTermMarks += mark.getMark().toString();
                        secondTermMarks += ", ";
                        model.setSecondTerm(secondTermMarks);
                    }
                    break;
                }
            }
        }

        for (StudentMarkModel model : models) {
            model.setFirstTerm(HelpfulThings.FixString(model.getFirstTerm()));
            model.setSecondTerm(HelpfulThings.FixString(model.getSecondTerm()));
            model.setFirstTermFinal(HelpfulThings.Average(model.getFirstTerm()));
            model.setSecondTermFinal(HelpfulThings.Average(model.getSecondTerm()));
            String mark = model.getFirstTermFinal().replace(',', '.');
            if (mark.isEmpty())
                mark = "0";
            double yearly = Double.parseDouble(mark);
            mark = model.getSecondTermFinal().replace(',', '.');
            if (mark.isEmpty()) {
                model.setYearly("");
                continue;
            }
            yearly += Double.parseDouble(mark);
            yearly /= 2;
            model.setYearly(String.valueOf(yearly));
        }

        return ResponseEntity.ok(models);
    }

    @GetMapping("/absences")
    public ResponseEntity<?> getAbsences(@RequestParam(value = "stId") UUID stId) {
        Student st = studentRepo.findStudentById(stId);

        if (st == null)
            return new ResponseEntity<>("Student not found.", HttpStatus.BAD_REQUEST);

        List<Absence> absences = st.getAbsences();

        ArrayList<StudentAbsenceModel> absenceModels = new ArrayList<>();
        for (Absence abs : absences) {
            absenceModels.add(new StudentAbsenceModel(abs.getSubject().getName(),
                    (abs.isAbsence() ? "Отсъствие" : "Закъснение"),
                    (abs.isExcused() ? "Да" : "Не"),
                    HelpfulThings.TimeConvert(abs.getDate())));
        }

        return ResponseEntity.ok(absenceModels);
    }

    @GetMapping("/feedbacks")
    public ResponseEntity<?> getFeedbacks(@RequestParam(value = "stId") UUID stId) {
        Student st = studentRepo.findStudentById(stId);

        if (st == null)
            return new ResponseEntity<>("Student not found.", HttpStatus.BAD_REQUEST);

        List<Feedback> feedbacks = st.getFeedbacks();
        ArrayList<StudentFeedbackModel> feedbackModels = new ArrayList<>();
        for (Feedback fb : feedbacks){
            feedbackModels.add(new StudentFeedbackModel(fb.getSubject().getName(),
                    fb.getDescription(),
                    HelpfulThings.TimeConvert(fb.getDate())));
        }

        return ResponseEntity.ok(feedbackModels);
    }

    @GetMapping("/marks/byClassAndSubject")
    public ResponseEntity<?> getMarksByClassAndBySubject(String stClass, Long sjId) {
        Set<Student> students = studentRepo.findStudentsByStClass(stClass);
        Subject subject = subjectRepo.findSubjectById(sjId);

        if (students != null & students.isEmpty())
            return new ResponseEntity<>("Students not found.", HttpStatus.BAD_REQUEST);

        if (subject == null)
            return new ResponseEntity<>("Subject not found.", HttpStatus.BAD_REQUEST);

        List<TStudentMarkModel> studentMarkList = HelpfulThings.ProcessMarks(students, subject);

        return ResponseEntity.ok(studentMarkList);
    }

    @GetMapping("/absences/byClassAndSubject")
    public ResponseEntity<?> getAbsencesByClassAndBySubject(String stClass, Long sjId) {
        Set<Student> students = studentRepo.findStudentsByStClass(stClass);
        Subject subject = subjectRepo.findSubjectById(sjId);

        if (students != null & students.isEmpty())
            return new ResponseEntity<>("Student not found.", HttpStatus.BAD_REQUEST);

        if (subject == null)
            return new ResponseEntity<>("Subject not found.", HttpStatus.BAD_REQUEST);

        List<TStudentAbsenceModel> studentAbsenceList = HelpfulThings.ProcessAbsences(students, subject);

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

        List<TStudentFeedbackModel> studentFeedackList = HelpfulThings.ProcessFeedbacks(students, subject);

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

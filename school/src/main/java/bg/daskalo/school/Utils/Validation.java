package bg.daskalo.school.Utils;

import org.apache.commons.validator.routines.EmailValidator;

public class Validation {
    public static boolean validateRegistrationTeacher(String fname,
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

    public static boolean validateRegistrationStudent(String fname,
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

    public static boolean validateMark(Integer mark) {
        return mark != null &&
                (mark >= 2 && mark <= 6);
    }

    public static boolean validateTerm(Integer term) {
        return term >= 1 && term <= 2;
    }
}

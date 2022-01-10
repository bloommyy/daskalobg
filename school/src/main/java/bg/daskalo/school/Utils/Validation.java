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

        if (!validateName(fname) ||
                !validateName(mname) ||
                !validateName(lname))
            return false;

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

        if (!validateName(fname) ||
                !validateName(mname) ||
                !validateName(lname))
            return false;

        if (!EmailValidator.getInstance(true).isValid(email))
            return false;

        if (egn.length() != 10)
            return false;

        for (char c : egn.toCharArray()) {
            if (!Character.isDigit(c))
                return false;
        }

        if (stClass.length() == 2) {
            if (!Character.isDigit(stClass.charAt(0)))
                return false;

            if (!Character.isLetter(stClass.charAt(1)))
                return false;
        } else if (stClass.length() == 3) {
            if (!Character.isDigit(stClass.charAt(0)) || !Character.isDigit(stClass.charAt(1)))
                return false;

            if (!Character.isLetter(stClass.charAt(2)))
                return false;
        }

        return password.length() >= 8;
    }

    public static boolean validateMark(Integer mark) {
        return mark != null &&
                (mark >= 2 && mark <= 6);
    }

    public static boolean validateTerm(Integer term) {
        return (term >= 1 && term <= 2);
    }

    public static boolean validateName(String name) {
        for (char c : name.toCharArray()) {
            if (!Character.isLetter(c))
                return false;
        }

        return true;
    }
}

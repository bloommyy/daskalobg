package bg.daskalo.school.Utils;

import bg.daskalo.school.Entities.*;
import bg.daskalo.school.Models.TStudentAbsenceModel;
import bg.daskalo.school.Models.TStudentFeedbackModel;
import bg.daskalo.school.Models.TStudentMarkModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.DecimalFormat;
import java.util.*;

public class HelpfulThings {
    public static String TimeConvert(Date date){
        String months[] = {"Януари", "Февруари", "Март", "Април", "Май", "Юни", "Юли", "Август", "Септември", "Октомври", "Ноември", "Декември"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        String month = months[calendar.get(Calendar.MONTH)];
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        int sec = calendar.get(Calendar.SECOND);

        StringBuilder builder = new StringBuilder();
        builder.append(day);
        builder.append(" ");
        builder.append(month);
        builder.append(" ");
        builder.append(year);
        builder.append(" ");
        builder.append(hour);
        builder.append(":");
        builder.append(min);
        builder.append(":");
        builder.append(sec);

        return builder.toString();
    }

    public static <T> ArrayList<T> ReverseList(ArrayList<T> list){
        ArrayList<T> revList = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0; i--){
            revList.add(list.get(i));
        }

        return revList;
    }

    public static String FixString(String str){
        if (str == null)
            return "";

        int strSize = str.length();
        if (str.charAt(strSize - 1) == ' ' && str.charAt(strSize - 2) == ',')
            return str.substring(0, strSize - 2);
        return str;
    }

    public static String Average(String markStr){
        try{
            String[] marks = markStr.split(", ");
            double sum = 0;
            for (String m : marks){
                sum += Integer.parseInt(m);
            }
            if (marks.length == 0)
                return "";

            double res = sum / marks.length;
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            return df.format(res);
        }catch (Exception e){
            return "";
        }
    }

    public static List<TStudentMarkModel> ProcessMarks(Set<Student> students, Subject subject){
        List<TStudentMarkModel> studentMarkList = new ArrayList<>();

        int currStudent = 0;
        for (Student student : students) {
            studentMarkList.add(new TStudentMarkModel(student.getFirstName() + " " + student.getMiddleName() + " " +
                    student.getLastName()));

            ArrayList<Integer> firstTermMarks = new ArrayList<>();
            ArrayList<Integer> secondTermMarks = new ArrayList<>();
            TStudentMarkModel model = studentMarkList.get(currStudent);
            ArrayList<Long> firstTermIds = new ArrayList<>();
            ArrayList<Long> secondTermIds = new ArrayList<>();
            for (Mark mark : student.getMarks()) {
                if (mark.getSubject() != subject)
                    continue;

                if (mark.getTerm() == 1) {
                    firstTermIds.add(mark.getId());
                    firstTermMarks.add(mark.getMark());
                } else {
                    secondTermIds.add(mark.getId());
                    secondTermMarks.add(mark.getMark());
                }
            }
            firstTermMarks = HelpfulThings.ReverseList(firstTermMarks);
            secondTermMarks = HelpfulThings.ReverseList(secondTermMarks);
            model.setFirstTermIds(HelpfulThings.ReverseList(firstTermIds));
            model.setSecondTermIds(HelpfulThings.ReverseList(secondTermIds));

            String firstTerm = firstTermMarks.toString();
            model.setFirstTerm(firstTerm.substring(1, firstTerm.length() - 1));

            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);

            double sum = 0;
            double firstTermDouble = 0;
            double secondTermDouble = 0;
            for (Integer mark : firstTermMarks)
                sum += mark;

            if (sum != 0) {
                firstTermDouble = sum / firstTermMarks.size();
                model.setFirstTermFinal(df.format(firstTermDouble));
            }

            String secondTerm = secondTermMarks.toString();
            model.setSecondTerm(secondTerm.substring(1, secondTerm.length() - 1));

            sum = 0;
            for (Integer mark : secondTermMarks)
                sum += mark;
            if (sum != 0) {
                secondTermDouble = sum / secondTermMarks.size();
                model.setSecondTermFinal(df.format(secondTermDouble));
            }

            if (secondTermDouble != 0)
                model.setYearly(df.format((firstTermDouble + secondTermDouble) / 2));

            currStudent++;
        }

        return studentMarkList;
    }

    public static List<TStudentAbsenceModel> ProcessAbsences(Set<Student> students, Subject subject){
        List<TStudentAbsenceModel> studentAbsenceList = new ArrayList<>();
        for (Student student : students) {
            for (Absence absence : student.getAbsences()) {
                if (absence.getSubject() == subject) {
                    studentAbsenceList.add(new TStudentAbsenceModel(
                            absence.getId(),
                            (student.getFirstName() + " " + student.getMiddleName() + " " +
                                    student.getLastName()),
                            (absence.isAbsence() == true ? "Отсъствие" : "Закъснение"), (absence.isExcused() == true ? "Да" : "Не"), HelpfulThings.TimeConvert(absence.getDate())
                    ));
                }
            }
        }

        return  studentAbsenceList;
    }

    public static List<TStudentFeedbackModel> ProcessFeedbacks(Set<Student> students, Subject subject){
        List<TStudentFeedbackModel> studentFeedackList = new ArrayList<>();
        for (Student student : students) {
            for (Feedback feedback : student.getFeedbacks()) {
                if (feedback.getSubject() == subject) {
                    studentFeedackList.add(new TStudentFeedbackModel(
                            feedback.getId(),
                            student.getFirstName() + " " + student.getMiddleName() + " " +
                                    student.getLastName(),
                            feedback.getDescription(), HelpfulThings.TimeConvert(feedback.getDate())
                    ));
                }
            }
        }

        return studentFeedackList;
    }
}


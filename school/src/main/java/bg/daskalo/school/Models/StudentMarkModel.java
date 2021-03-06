package bg.daskalo.school.Models;

public class StudentMarkModel {
    private String subject;
    private String firstTerm;
    private String firstTermFinal;
    private String secondTerm;
    private String secondTermFinal;
    private String yearly;

    public StudentMarkModel(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFirstTerm() {
        return firstTerm;
    }

    public void setFirstTerm(String firstTerm) {
        this.firstTerm = firstTerm;
    }

    public String getFirstTermFinal() {
        return firstTermFinal;
    }

    public void setFirstTermFinal(String firstTermFinal) {
        this.firstTermFinal = firstTermFinal;
    }

    public String getSecondTerm() {
        return secondTerm;
    }

    public void setSecondTerm(String secondTerm) {
        this.secondTerm = secondTerm;
    }

    public String getSecondTermFinal() {
        return secondTermFinal;
    }

    public void setSecondTermFinal(String secondTermFinal) {
        this.secondTermFinal = secondTermFinal;
    }

    public String getYearly() {
        return yearly;
    }

    public void setYearly(String yearly) {
        this.yearly = yearly;
    }
}

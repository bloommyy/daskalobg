package bg.daskalo.school.Payload.Response;

import bg.daskalo.school.Entities.Student;

import java.util.UUID;

public class StudentLoginResponse {
    private UUID uuid;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String egn;
    private String stClass;
    private Integer stClassNum;

    public StudentLoginResponse(Student st) {
        this.uuid = st.getId();
        this.firstName = st.getFirstName();
        this.middleName = st.getMiddleName();
        this.lastName = st.getLastName();
        this.email = st.getEmail();
        this.egn = st.getEgn();
        this.stClass = st.getStClass();
        this.stClassNum = st.getStClassNum();
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEgn() {
        return egn;
    }

    public void setEgn(String egn) {
        this.egn = egn;
    }

    public String getStClass() {
        return stClass;
    }

    public void setStClass(String stClass) {
        this.stClass = stClass;
    }

    public Integer getStClassNum() {
        return stClassNum;
    }

    public void setStClassNum(Integer stClassNum) {
        this.stClassNum = stClassNum;
    }
}

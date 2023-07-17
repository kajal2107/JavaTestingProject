package dataobjects;

public class People {

    private String personName;
    private String personRealLastName;
    private String personEmail;
    private String personTitle;
    private String personPhone;
    private String personTimeZone;
    private String personAbout;
    private String[] personRole;

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonRealLastName() {
        return personRealLastName;
    }

    public void setPersonRealLastName(String personRealLastName) {
        this.personRealLastName = personRealLastName;
    }

    public String getPersonEmail() {
        return personEmail;
    }

    public void setPersonEmail(String personEmail) {
        this.personEmail = personEmail;
    }

    public String getPersonTitle() {
        return personTitle;
    }

    public void setPersonTitle(String personTitle) {
        this.personTitle = personTitle;
    }

    public String getPersonPhone() {
        return personPhone;
    }

    public void setPersonPhone(String personPhone) {
        this.personPhone = personPhone;
    }

    public void setPersonTimeZone(String personTimeZone) {
        this.personTimeZone = personTimeZone;
    }

    public String getPersonAbout() {
        return personAbout;
    }

    public void setPersonAbout(String personAbout) {
        this.personAbout = personAbout;
    }

    public String[] getPersonRole() {
        return personRole;
    }

    public void setPersonRole(String[] personRole) {
        this.personRole = personRole;
    }

}

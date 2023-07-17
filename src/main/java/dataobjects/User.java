package dataobjects;


import java.util.List;

public class User {

    private String description;
    private String email;
    private String password;
    private String newPassword;
    private String name;
    private String realFirstName;
    private String realLastName;
    private List<String> userRoles;

    public List<String> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<String> userRoles) {
        this.userRoles = userRoles;
    }

    public String getRealFirstName() {
        return realFirstName;
    }

    public void setRealFirstName(String realFirstName) {
        this.realFirstName = realFirstName;
    }

    public String getRealLastName() {
        return realLastName;
    }

    public void setRealLastName(String realLastName) {
        this.realLastName = realLastName;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

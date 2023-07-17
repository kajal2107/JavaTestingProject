package enums.users;

import java.util.Arrays;

public enum UserRole {

    Administrator("Administrator"),
    BibleTranslationLiaison("Bible Translation Liaison"),
    Consultant("Consultant"),
    ConsultantManager("Consultant Manager"),
    Controller("Controller"),
    FieldPartner("Field Partner"),
    FinancialAnalyst("Financial Analyst"),
    FieldOperationsDirector("Field Operations Director"),
    Fundraising("Fundraising"),
    Intern("Intern"),
    LeadFinancialAnalyst("Lead Financial Analyst"),
    Leadership("Leadership"),
    Liaison("Liaison"),
    Marketing("Marketing"),
    Mentor("Mentor"),
    OfficeOfThePresident("Office Of The President"),
    ProjectManager("Project Manager"),
    RegionalCommunicationsCoordinator("Regional Communications Coordinator"),
    RegionalDirector("Regional Director"),
    StaffMember("Staff Member"),
    Translator("Translator");

    public void setRole(String role) {
        this.role = role;
    }

    // declaring private variable for getting values
    private String role;

    // Constructor that will set value from bracket i.e. String
    UserRole(String role) {
        this.role = role;
    }

    // getter method
    public String getRole() {
        return this.role;
    }

    public static String[] getAllUserRole() {
        UserRole[] signals = UserRole.values();

        String[] names = new String[signals.length];

        for (int i = 0; i < signals.length; i++) {
            names[i] = signals[i].role;
        }
        return names;
    }

}

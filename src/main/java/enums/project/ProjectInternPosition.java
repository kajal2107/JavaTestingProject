package enums.project;

public enum ProjectInternPosition {

    ExegeticalFacilitator("Exegetical Facilitator"),
    ConsultantInTraining("Consultant In Training"),
    Finance("Finance"),
    ScriptureEngagement("Scripture Engagement"),
    Communication("Communication"),
    LanguageProgramManager("Language Program Manager"),
    LeadershipDevelopment("Leadership Development"),
    Literacy("Literacy"),
    Mobilization("Mobilization"),
    OralFacilitator("Oral Facilitator"),
    Personnel("Personnel"),
    Administration("Administration"),
    Technology("Technology"),
    TranslationFacilitator("Translation Facilitator");


    // declaring private variable for getting values
    private String internPosition;

    public String getInternPosition() {
        return internPosition;
    }

    public void setInternPosition(String internPosition) {
        this.internPosition = internPosition;
    }

    // Constructor that will set value from bracket i.e. String
    ProjectInternPosition(String internPosition) {
        this.internPosition = internPosition;
    }

}

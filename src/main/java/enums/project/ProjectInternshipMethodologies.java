package enums.project;

public enum ProjectInternshipMethodologies {

    Paratext("Paratext"),
    OtherWritten("Other Written"),
    Render("Render"),
    OtherOralTranslation("Other Oral Translation"),
    BibleStories("Bible Stories"),
    BibleStorying("Bible Storying"),
    OneStory("One Story"),
    OtherOralStories("Other Oral Stories"),
    Film("Film"),
    SignLanguage("Sign Language"),
    OtherVisual("Other Visual");

    private String internshipMethodology;

    public String getInternshipMethodology() {
        return internshipMethodology;
    }

    public void setInternshipMethodology(String internshipMethodology) {
        this.internshipMethodology = internshipMethodology;
    }

    // Constructor that will set value from bracket i.e. String
    ProjectInternshipMethodologies(String internshipMethodology) {
        this.internshipMethodology = internshipMethodology;
    }

}

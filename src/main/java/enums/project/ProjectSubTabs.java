package enums.project;

public enum ProjectSubTabs {

    All("All"),
    Pinned("Pinned"),
    Mine("Mine");

    // declaring private variable for getting values
    private String projectSubTabs;

    public String getProjectSubTabs() {
        return projectSubTabs;
    }

    public void setProjectSubTabs(String projectSubTabs) {
        this.projectSubTabs = projectSubTabs;
    }

    // Constructor that will set value from bracket i.e. String
    ProjectSubTabs(String projectSubTabs) {
        this.projectSubTabs = projectSubTabs;
    }

}

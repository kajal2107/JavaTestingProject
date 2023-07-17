package enums.project;

public enum ProjectPostsCategory {

    Note("Note"),
    Story("Story"),
    Prayer("Prayer");

    // declaring private variable for getting values
    private final String projectPostsCategory;

    public String getProjectPostsCategory() {
        return projectPostsCategory;
    }

    // Constructor that will set value from bracket i.e. String
    ProjectPostsCategory(String projectPostsCategory) {
        this.projectPostsCategory = projectPostsCategory;
    }

}

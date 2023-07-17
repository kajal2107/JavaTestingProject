package enums.project;

public enum ProjectPostsShareability {

    ProjectTeam("Project Team"),
    Internal("Internal"),
    AskToShareExternally("Ask To Share Externally"),
    External("External");

    public String getProjectPostsShareability() {
        return projectCommentShareability;
    }

    // declaring private variable for getting values
    private final String projectCommentShareability;

    // Constructor that will set value from bracket i.e. String
    ProjectPostsShareability(String projectCommentShareability) {
        this.projectCommentShareability = projectCommentShareability;
    }

}

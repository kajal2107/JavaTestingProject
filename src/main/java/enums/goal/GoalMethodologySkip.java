package enums.goal;

public enum GoalMethodologySkip {

    SkipExtractingGoals("Skip extracting goals");

    // declaring private variable for getting values
    private final String goalMethodologySkip;

    public String getGoalMethodologySkip() {
        return goalMethodologySkip;
    }

    // Constructor that will set value from bracket i.e. String
    GoalMethodologySkip(String goalMethodologySkip) {
        this.goalMethodologySkip = goalMethodologySkip;
    }
}

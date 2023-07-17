package enums.goal;

public enum GoalDropdown {

    Steps("Steps"),
    DistributionMethods("Distribution Methods"),
    PartnersProducingTheseDistributionMethods("Partners Producing these Distribution Methods"),
    Methodology("Methodology"),
    ProgressTarget("Progress Target"),
    ScriptureReference("Scripture Reference"),
    ProgressMeasurement("Progress Measurement"),
    CompletionDescription("Completion Description");

    public String getGoalDropdown() {
        return goalDropdown;
    }

    // declaring private variable for getting values
    private final String goalDropdown;

    // Constructor that will set value from bracket i.e. String
    GoalDropdown(String goalDropdown) {
        this.goalDropdown = goalDropdown;
    }

}

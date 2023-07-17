package enums.goal;

public enum GoalVisualOtherSteps {

    Completed("Completed");

    // declaring private variable for getting values
    String goalVisualOtherSteps;

    public String getGoalVisualOtherSteps() {
        return goalVisualOtherSteps;
    }

    public static String[] getGoalVisualOtherStepsByName(String[] goalVisualOtherSteps) {
        return goalVisualOtherSteps;
    }

    // Constructor that will set value from bracket i.e. String
    GoalVisualOtherSteps(String goalVisualOtherSteps) {
        this.goalVisualOtherSteps = goalVisualOtherSteps;
    }

    public static String[] getAllGoalVisualOtherSteps() {
        GoalVisualOtherSteps[] signals = GoalVisualOtherSteps.values();

        String[] names = new String[signals.length];

        for (int i = 0; i < signals.length; i++) {
            names[i] = signals[i].goalVisualOtherSteps;
        }
        return names;
    }

}








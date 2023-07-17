package enums.goal;

public enum GoalOralStoriesSteps {

    Craft("Craft"),
    Test("Test"),
    Check("Check"),
    Completed("Completed");

    // declaring private variable for getting values
    String goalOralStoriesSteps;

    public String getGoalOralStoriesSteps() {
        return goalOralStoriesSteps;
    }

    // Constructor that will set value from bracket i.e. String
    GoalOralStoriesSteps(String goalOralStoriesSteps) {
        this.goalOralStoriesSteps = goalOralStoriesSteps;
    }

    public static String[] getAllOralStoriesSteps() {
        GoalOralStoriesSteps[] signals = GoalOralStoriesSteps.values();

        String[] names = new String[signals.length];

        for (int i = 0; i < signals.length; i++) {
            names[i] = signals[i].goalOralStoriesSteps;
        }
        return names;
    }

}








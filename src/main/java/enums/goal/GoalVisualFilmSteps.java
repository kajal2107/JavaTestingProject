package enums.goal;

public enum GoalVisualFilmSteps {

    Translate("Translate"),
    Completed("Completed");

    // declaring private variable for getting values
    String goalVisualFilmSteps;

    public String getGoalVisualFilmSteps() {
        return goalVisualFilmSteps;
    }

    // Constructor that will set value from bracket i.e. String
    GoalVisualFilmSteps(String goalVisualFilmSteps) {
        this.goalVisualFilmSteps = goalVisualFilmSteps;
    }

    public static String[] getAllGoalVisualFilmSteps() {
        GoalVisualFilmSteps[] signals = GoalVisualFilmSteps.values();

        String[] names = new String[signals.length];

        for (int i = 0; i < signals.length; i++) {
            names[i] = signals[i].goalVisualFilmSteps;
        }
        return names;
    }

}








package enums.goal;

public enum GoalEthnoArtSteps {

    Develop("Develop"),
    Completed("Completed");

    // declaring private variable for getting values
    String goalEthnoArtSteps;

    public String getGoalEthnoArtSteps() {
        return goalEthnoArtSteps;
    }

    // Constructor that will set value from bracket i.e. String
    GoalEthnoArtSteps(String goalEthnoArtSteps) {
        this.goalEthnoArtSteps = goalEthnoArtSteps;
    }

    public static String[] getAllGoalEthnoArtSteps() {
        GoalEthnoArtSteps[] signals = GoalEthnoArtSteps.values();

        String[] names = new String[signals.length];

        for (int i = 0; i < signals.length; i++) {
            names[i] = signals[i].goalEthnoArtSteps;
        }
        return names;
    }

}








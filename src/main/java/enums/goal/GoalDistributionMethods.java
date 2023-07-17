package enums.goal;

public enum GoalDistributionMethods {

    Print("Print"),
    Web("Web"),
    EBook("E-Book"),
    App("App"),
    Audio("Audio"),
    TrainedStoryTellers("Trained Story Tellers"),
    Video("Video"),
    Other("Other");

    // declaring private variable for getting values
    String goalDistributionMethods;

    public String getGoalDistributionMethods() {
        return goalDistributionMethods;
    }

    public static String[] getGoalDistributionMethodsByName(String[] goalDistributionMethods) {
        return goalDistributionMethods;
    }

    // Constructor that will set value from bracket i.e. String
    GoalDistributionMethods(String goalDistributionMethods) {
        this.goalDistributionMethods = goalDistributionMethods;
    }

    public static String[] getAllDistributionMethodsNames() {
        GoalDistributionMethods[] signals = GoalDistributionMethods.values();

        String[] names = new String[signals.length];

        for (int i = 0; i < signals.length; i++) {
            names[i] = signals[i].goalDistributionMethods;
        }
        return names;
    }

}








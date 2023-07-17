package enums.goal;

public enum GoalVisualSignLanguageSteps {

    ExegesisAndFirstDraft("Exegesis & First Draft"),
    TeamCheck("Team Check"),
    CommunityTesting("Community Testing"),
    BackTranslation("Back Translation"),
    ConsultantCheck("Consultant Check"),
    Completed("Completed");

    // declaring private variable for getting values
    String goalVisualSignLanguageSteps;

    public String getGoalVisualSignLanguageSteps() {
        return goalVisualSignLanguageSteps;
    }

    public static String[] getGoalVisualSignLanguageStepsByName(String[] goalVisualSignLanguageSteps) {
        return goalVisualSignLanguageSteps;
    }

    // Constructor that will set value from bracket i.e. String
    GoalVisualSignLanguageSteps(String goalVisualSignLanguageSteps) {
        this.goalVisualSignLanguageSteps = goalVisualSignLanguageSteps;
    }

    public static String[] getAllGoalVisualSignLanguageSteps() {
        GoalVisualSignLanguageSteps[] signals = GoalVisualSignLanguageSteps.values();

        String[] names = new String[signals.length];

        for (int i = 0; i < signals.length; i++) {
            names[i] = signals[i].goalVisualSignLanguageSteps;
        }
        return names;
    }

}








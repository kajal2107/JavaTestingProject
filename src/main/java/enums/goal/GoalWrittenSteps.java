package enums.goal;

public enum GoalWrittenSteps {

    TeamCheck("Team Check"),
    Completed("Completed"),
    BackTranslation("Back Translation"),
    ConsultantCheck("Consultant Check"),
    CommunityTesting("Community Testing"),
    ExegesisAndFirstDraft("Exegesis & First Draft");

    // declaring private variable for getting values
    String goalWrittenSteps;

    // Constructor that will set value from bracket i.e. String
    GoalWrittenSteps(String goalWrittenSteps) {
        this.goalWrittenSteps = goalWrittenSteps;
    }

    public static String[] getAllGoalWrittenSteps() {
        GoalWrittenSteps[] signals = GoalWrittenSteps.values();

        String[] names = new String[signals.length];

        for (int i = 0; i < signals.length; i++) {
            names[i] = signals[i].goalWrittenSteps;
        }
        return names;
    }

}








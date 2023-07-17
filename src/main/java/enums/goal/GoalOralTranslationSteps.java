package enums.goal;

public enum GoalOralTranslationSteps {

    InternalizationAndDrafting("Internalization & Drafting"),
    PeerRevision("Peer Revision"),
    CommunityTesting("Community Testing"),
    BackTranslation("Back Translation"),
    ConsultantCheck("Consultant Check"),
    Completed("Completed");

    // declaring private variable for getting values
    String goalOralTranslationSteps;

    // Constructor that will set value from bracket i.e. String
    GoalOralTranslationSteps(String goalOralTranslationSteps) {
        this.goalOralTranslationSteps = goalOralTranslationSteps;
    }

    public static String[] getAllOralTranslationSteps() {
        GoalOralTranslationSteps[] signals = GoalOralTranslationSteps.values();

        String[] names = new String[signals.length];

        for (int i = 0; i < signals.length; i++) {
            names[i] = signals[i].goalOralTranslationSteps;
        }
        return names;
    }

}








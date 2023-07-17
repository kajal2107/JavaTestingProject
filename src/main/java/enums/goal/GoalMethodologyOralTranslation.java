package enums.goal;

public enum GoalMethodologyOralTranslation {

    Render("Render"),
    Audacity("Audacity"),
    AdobeAudition("Adobe Audition"),
    Other("Other");

    // declaring private variable for getting values
    private String goalMethodologyOralTranslation;

    public String getGoalMethodologyOralTranslation() {
        return goalMethodologyOralTranslation;
    }

    // Constructor that will set value from bracket i.e. String
    GoalMethodologyOralTranslation(String goalMethodologyOralTranslation) {
        this.goalMethodologyOralTranslation = goalMethodologyOralTranslation;
    }

    /**
     * Get all enum constant values (bracket values) as array
     *
     * @return all enum values defined in brackets
     */
    public static String[] getAllGoalMethodologyOralTranslationValues() {
        GoalMethodologyOralTranslation[] values = GoalMethodologyOralTranslation.values();

        String[] names = new String[values.length];

        for (int i = 0; i < values.length; i++) {
            names[i] = values[i].goalMethodologyOralTranslation;
        }

        return names;

    }

}

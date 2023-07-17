package enums.goal;

public enum GoalMethodologyType {

    Written("Written"),
    OralTranslation("Oral Translation"),
    OralStories("Oral Stories"),
    Visual("Visual");

    // declaring private variable for getting values
    private String goalMethodologyType;

    public String getGoalMethodologyType() {
        return goalMethodologyType;
    }

    // Constructor that will set value from bracket i.e. String
    GoalMethodologyType(String goalMethodologyType) {
        this.goalMethodologyType = goalMethodologyType;
    }

    /**
     * Get all enum values as array
     * <p>
     * Get all enum constant values (bracket values)
     *
     * @return all enum values defined in brackets
     */
    public static String[] getAllGoalMethodologyTypeValues() {
        GoalMethodologyType[] values = GoalMethodologyType.values();

        String[] names = new String[values.length];

        for (int i = 0; i < values.length; i++) {
            names[i] = values[i].goalMethodologyType;
        }

        return names;

    }

}

package enums.goal;

public enum GoalMethodologyVisual {

    Film("Film"),
    SignLanguage("Sign Language"),
    Other("Other");

    // declaring private variable for getting values
    private final String goalMethodologyVisualType;

    public String getGoalMethodologyVisualType() {
        return goalMethodologyVisualType;
    }

    // Constructor that will set value from bracket i.e. String
    GoalMethodologyVisual(String goalMethodologyVisual) {
        this.goalMethodologyVisualType = goalMethodologyVisual;
    }

    /**
     * Get all enum constant values (bracket values) as array
     *
     * @return all enum values defined in brackets
     */
    public static String[] getAllGoalMethodologyVisualValues() {
        GoalMethodologyVisual[] values = GoalMethodologyVisual.values();

        String[] names = new String[values.length];

        for (int i = 0; i < values.length; i++) {
            names[i] = values[i].goalMethodologyVisualType;
        }

        return names;

    }

}

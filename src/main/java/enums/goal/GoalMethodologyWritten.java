package enums.goal;

public enum GoalMethodologyWritten {

    ParaText("Paratext"),
    Other("Other");

    // declaring private variable for getting values
    private String goalMethodologyWritten;

    public String getGoalMethodologyWritten() {
        return goalMethodologyWritten;
    }

    // Constructor that will set value from bracket i.e. String
    GoalMethodologyWritten(String goalMethodologyWritten) {
        this.goalMethodologyWritten = goalMethodologyWritten;
    }

    /**
     * Get all enum constant values (bracket values) as array
     *
     * @return all enum values defined in brackets
     */
    public static String[] getAllGoalMethodologyWrittenValues() {
        GoalMethodologyWritten[] values = GoalMethodologyWritten.values();

        String[] names = new String[values.length];

        for (int i = 0; i < values.length; i++) {
            names[i] = values[i].goalMethodologyWritten;
        }

        return names;

    }


}

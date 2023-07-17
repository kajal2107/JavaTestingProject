package enums.goal;

public enum GoalPurposes {

    EvangelismChurchPlanting("Evangelism Church Planting"),
    ChurchLife("Church Life"),
    ChurchMaturity("Church Maturity"),
    SocialIssues("Social Issues"),
    Discipleship("Discipleship");

    // declaring private variable for getting values
    public String goalPurposes;

    public String getGoalPurposes() {
        return goalPurposes;
    }

    // Constructor that will set value from bracket i.e. String
    GoalPurposes(String goalPurposes) {
        this.goalPurposes = goalPurposes;
    }

    public static String[] getAllGoalPurposesValues() {
        GoalPurposes[] signals = GoalPurposes.values();

        String[] names = new String[signals.length];

        for (int i = 0; i < signals.length; i++) {
            names[i] = signals[i].goalPurposes;
        }

        return names;

    }

}

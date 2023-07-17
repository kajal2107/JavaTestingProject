package enums.goal;

public enum GoalType {

    Scripture("Scripture"),
    Story("Story"),
    Film("Film"),
    EthnoArt("Ethno Art"),
    Other("Other");

    // declaring private variable for getting values
    private final String goalType;

    public String getGoalType() {
        return goalType;
    }

    // Constructor that will set value from bracket i.e. String
    GoalType(String goalType) {
        this.goalType = goalType;
    }

}

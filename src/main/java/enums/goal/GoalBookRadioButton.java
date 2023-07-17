package enums.goal;

public enum GoalBookRadioButton {

    FullBook("Full Book"),
    partialKnown("Partial Book - Known References"),
    partialUnknown("Partial Book - Unknown References - Only total verse count");

    public String getGoalBookRadioButton() {
        return goalBookRadioButton;
    }

    // declaring private variable for getting values
    private final String goalBookRadioButton;

    // Constructor that will set value from bracket i.e. String
    GoalBookRadioButton(String goalBookRadioButton) {
        this.goalBookRadioButton = goalBookRadioButton;
    }

}

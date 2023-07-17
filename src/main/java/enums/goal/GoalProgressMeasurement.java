package enums.goal;

public enum GoalProgressMeasurement {

    Percent("Percent"),
    Number("Number"),
    DoneNotDone("Done / Not Done");

    // declaring private variable for getting values
    String goalProgressMeasurement;

    public String getGoalProgressMeasurement() {
        return goalProgressMeasurement;
    }

    // Constructor that will set value from bracket i.e. String
    GoalProgressMeasurement(String goalProgressMeasurement) {
        this.goalProgressMeasurement = goalProgressMeasurement;
    }

    public static String[] getAllProgressMeasurementsNames() {
        GoalProgressMeasurement[] signals = GoalProgressMeasurement.values();

        String[] names = new String[signals.length];

        for (int i = 0; i < signals.length; i++) {
            names[i] = signals[i].goalProgressMeasurement;
        }
        return names;
    }

}








package enums.goal;

public enum GoalScriptureRefNewTestament {

    Matthew("Matthew"),
    Mark("Mark"),
    Luke("Luke"),
    John("John"),
    Acts("Acts"),
    Romans("Romans"),
    OneCorinthians("1 Corinthians"),
    TwoCorinthians("2 Corinthians"),
    Galatians("Galatians"),
    Ephesians("Ephesians"),
    Philippians("Philippians"),
    Colossians("Colossians"),
    OneThessalonians("1 Thessalonians"),
    TwoThessalonians("2 Thessalonians"),
    OneTimothy("1 Timothy"),
    TwoTimothy("2 Timothy"),
    Titus("Titus"),
    Philemon("Philemon"),
    Hebrews("Hebrews"),
    James("James"),
    OnePeter("1 Peter"),
    TwoPeter("2 Peter"),
    OneJohn("1 John"),
    TwoJohn("2 John"),
    ThreeJohn("3 John"),
    Jude("Jude"),
    Revelation("Revelation");

    // declaring private variable for getting values
    private String goalNewTestament;

    public String getGoalNewTestament() {
        return goalNewTestament;
    }

    // Constructor that will set value from bracket i.e. String
    GoalScriptureRefNewTestament(String goalNewTestament) {
        this.goalNewTestament = goalNewTestament;
    }

    /**
     * Get all enum values as array
     * <p>
     * Get all enum constant values (bracket values)
     *
     * @return all enum values defined in brackets
     */
    public static String[] getAllGoalScriptRefNewTestamentValues() {
        GoalScriptureRefNewTestament[] signals = GoalScriptureRefNewTestament.values();

        String[] names = new String[signals.length];

        for (int i = 0; i < signals.length; i++) {
            names[i] = signals[i].goalNewTestament;
        }

        return names;

    }

}

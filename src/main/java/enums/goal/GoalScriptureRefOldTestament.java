package enums.goal;

public enum GoalScriptureRefOldTestament {

    Genesis("Genesis"),
    Exodus("Exodus"),
    Leviticus("Leviticus"),
    Numbers("Numbers"),
    Deuteronomy("Deuteronomy"),
    Joshua("Joshua"),
    Judges("Judges"),
    Ruth("Ruth"),
    OneSamuel("1 Samuel"),
    TwoSamuel("2 Samuel"),
    OneKings("1 Kings"),
    TwoKings("2 Kings"),
    OneChronicles("1 Chronicles"),
    TwoChronicles("2 Chronicles"),
    Ezra("Ezra"),
    Nehemiah("Nehemiah"),
    Esther("Esther"),
    Job("Job"),
    Psalm("Psalm"),
    Proverbs("Proverbs"),
    Ecclesiastes("Ecclesiastes"),
    SongOfSolomon("Song of Solomon"),
    Isaiah("Isaiah"),
    Jeremiah("Jeremiah"),
    Lamentations("Lamentations"),
    Ezekiel("Ezekiel"),
    Daniel("Daniel"),
    Hosea("Hosea"),
    Joel("Joel"),
    Amos("Amos"),
    Obadiah("Obadiah"),
    Jonah("Jonah"),
    Micah("Micah"),
    Nahum("Nahum"),
    Habakkuk("Habakkuk"),
    Zephaniah("Zephaniah"),
    Haggai("Haggai"),
    Zechariah("Zechariah"),
    Malachi("Malachi");

    private String goalOldTestament;

    public String getGoalOldTestament() {
        return goalOldTestament;
    }

    // Constructor that will set value from bracket i.e. String
    GoalScriptureRefOldTestament(String goalOldTestament) {
        this.goalOldTestament = goalOldTestament;
    }

    /**
     * Get all enum values as array
     * <p>
     * Get all enum constant values (bracket values)
     *
     * @return all enum values defined in brackets
     */
    public static String[] getAllGoalScriptRefOldTestamentValues() {
        GoalScriptureRefOldTestament[] signals = GoalScriptureRefOldTestament.values();

        String[] names = new String[signals.length];

        for (int i = 0; i < signals.length; i++) {
            names[i] = signals[i].goalOldTestament;
        }

        return names;

    }

}









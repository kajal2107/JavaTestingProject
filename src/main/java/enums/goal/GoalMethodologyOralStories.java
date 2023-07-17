package enums.goal;

public enum GoalMethodologyOralStories {

    StoryTogether("Story Together"),
    SeedCompanyMethod("Seed Company Method"),
    OneStory("One Story"),
    CraftTwoTell("Craft 2 Tell"),
    Other("Other");

    // declaring private variable for getting values
    private String goalMethodologyOralStories;

    public String getGoalMethodologyOralStories() {
        return goalMethodologyOralStories;
    }
    // Constructor that will set value from bracket i.e. String
    GoalMethodologyOralStories(String goalMethodologyOralStories) {
        this.goalMethodologyOralStories = goalMethodologyOralStories;
    }

    /**
     * Get all enum constant values (bracket values) as array
     *
     * @return all enum values defined in brackets
     */
    public static String[] getAllGoalMethodologyOralStoriesValues() {
        GoalMethodologyOralStories[] values = GoalMethodologyOralStories.values();

        String[] names = new String[values.length];

        for (int i = 0; i < values.length; i++) {
            names[i] = values[i].goalMethodologyOralStories;
        }

        return names;

    }

}

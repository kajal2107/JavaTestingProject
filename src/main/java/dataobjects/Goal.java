package dataobjects;

import enums.goal.GoalType;

public class Goal {
    private GoalType goalType;
    private String goalName;
    private String goalStoryName;
    private String goalEthnoArtName;
    private String goalFilmName;
    private String goalOtherTitle;
    private String goalOtherDescription;
    private String methodologyType;
    private String methodologyValue;
    private String goalCompletionDescription;
    private String oldScriptureReference;
    private String newScriptureReference;
    private Integer goalProgressTarget;
    private Integer goalVerses;
    private Integer goalStepsProgressValue;
    private String[] goalDistributionMethods;
    private String[] goalProgressMeasurement;
    private String[] steps;
    private String[] allSteps;

    public String getGoalCompletionDescription() {
        return goalCompletionDescription;
    }

    public void setGoalCompletionDescription(String goalCompletionDescription) {
        this.goalCompletionDescription = goalCompletionDescription;
    }

    public Integer getGoalProgressTarget() {
        return goalProgressTarget;
    }

    public void setGoalProgressTarget(Integer goalProgressTarget) {
        this.goalProgressTarget = goalProgressTarget;
    }

    public String getGoalFilmName() {
        return goalFilmName;
    }

    public void setGoalFilmName(String goalFilmName) {
        this.goalFilmName = goalFilmName;
    }

    public String getGoalStoryName() {
        return goalStoryName;
    }

    public void setGoalStoryName(String goalStoryName) {
        this.goalStoryName = goalStoryName;
    }

    public GoalType getGoalType() {
        return goalType;
    }

    public void setGoalType(GoalType goalType) {
        this.goalType = goalType;
    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public String getOldScriptureReference() {
        return oldScriptureReference;
    }

    public void setOldScriptureReference(String oldScriptureReference) {
        this.oldScriptureReference = oldScriptureReference;
    }

    public String getNewScriptureReference() {
        return newScriptureReference;
    }

    public void setNewScriptureReference(String newScriptureReference) {
        this.newScriptureReference = newScriptureReference;
    }

    public String[] getGoalDistributionMethods() {
        return goalDistributionMethods;
    }

    public void setGoalDistributionMethods(String[] goalDistributionMethods) {
        this.goalDistributionMethods = goalDistributionMethods;
    }

    public String getMethodologyType() {
        return methodologyType;
    }

    public void setMethodologyType(String methodologyType) {
        this.methodologyType = methodologyType;
    }

    public String getMethodologyValue() {
        return methodologyValue;
    }

    public void setMethodologyValue(String methodologyValue) {
        this.methodologyValue = methodologyValue;
    }

    public String[] getSteps() {
        return steps;
    }

    public void setSteps(String[] steps) {
        this.steps = steps;
    }

    public Integer getGoalStepsProgressValue() {
        return goalStepsProgressValue;
    }

    public void setGoalStepsProgressValue(Integer goalStepsProgressValue) {
        this.goalStepsProgressValue = goalStepsProgressValue;
    }

    public String getGoalOtherTitle() {
        return goalOtherTitle;
    }

    public void setGoalOtherTitle(String goalOtherTitle) {
        this.goalOtherTitle = goalOtherTitle;
    }

    public String getGoalOtherDescription() {
        return goalOtherDescription;
    }

    public void setGoalOtherDescription(String goalOtherDescription) {
        this.goalOtherDescription = goalOtherDescription;
    }

    public void setGoalProgressMeasurement(String[] goalProgressMeasurement) {
        this.goalProgressMeasurement = goalProgressMeasurement;
    }

    public Integer getGoalVerses() {
        return goalVerses;
    }

    public void setGoalVerses(Integer goalVerses) {
        this.goalVerses = goalVerses;
    }

    public String getGoalEthnoArtName() {
        return goalEthnoArtName;
    }

    public void setGoalEthnoArtName(String goalEthnoArtName) {
        this.goalEthnoArtName = goalEthnoArtName;
    }

    public String[] getAllSteps() {
        return allSteps;
    }

    public void setAllSteps(String[] allSteps) {
        this.allSteps = allSteps;
    }
}

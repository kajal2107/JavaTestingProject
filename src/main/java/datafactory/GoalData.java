package datafactory;

import dataobjects.Goal;
import enums.goal.*;
import utilities.JavaHelpers;

public class GoalData {

    public Goal getCreateGoalData() throws InterruptedException {

        Goal data = new Goal();
        String timeStamp = new JavaHelpers().getTimeStamp();

        data.setGoalStoryName("Story" + timeStamp);
        data.setGoalEthnoArtName("EthnoArt" + timeStamp);
        data.setGoalFilmName("Film" + timeStamp);
        data.setGoalOtherTitle("Other" + timeStamp);
        data.setGoalOtherDescription("Other Description" + timeStamp);
        data.setGoalCompletionDescription("Completion Description" + timeStamp);
        data.setGoalStepsProgressValue(JavaHelpers.getIntegerRandomNumber(1, 100));
        data.setGoalProgressTarget(JavaHelpers.getIntegerRandomNumber(1, 100));
        data.setGoalVerses(JavaHelpers.getIntegerRandomNumber(1, 10));

        String randomScriptRefsOldTestament = JavaHelpers.getRandomStringFromArray(GoalScriptureRefOldTestament.getAllGoalScriptRefOldTestamentValues());
        data.setOldScriptureReference(randomScriptRefsOldTestament);

        String randomScriptRefsNewTestament = JavaHelpers.getRandomStringFromArray(GoalScriptureRefNewTestament.getAllGoalScriptRefNewTestamentValues());
        data.setNewScriptureReference(randomScriptRefsNewTestament);

        String[] randomDistributionMethods = JavaHelpers.generateRandomArrayFromArray(1, GoalDistributionMethods.getAllDistributionMethodsNames()).toArray(new String[0]);
        data.setGoalDistributionMethods(randomDistributionMethods);

        String[] randomProgressMeasurement = JavaHelpers.generateRandomArrayFromArray(1, GoalProgressMeasurement.getAllProgressMeasurementsNames()).toArray(new String[0]);
        data.setGoalProgressMeasurement(randomProgressMeasurement);

        String methodologyType = JavaHelpers.getRandomStringFromArray(GoalMethodologyType.getAllGoalMethodologyTypeValues());
        data.setMethodologyType(methodologyType);

        if (methodologyType.equals(GoalMethodologyType.Written.getGoalMethodologyType())) {
            String[] stepsValue = JavaHelpers.generateRandomArrayFromArray(3, GoalWrittenSteps.getAllGoalWrittenSteps()).toArray(new String[0]);
            data.setSteps(stepsValue);

            String methodologyValue = JavaHelpers.getRandomStringFromArray(GoalMethodologyWritten.getAllGoalMethodologyWrittenValues());
            data.setMethodologyValue(methodologyValue);
        } else if (methodologyType.equals(GoalMethodologyType.OralTranslation.getGoalMethodologyType())) {
            String[] stepsValue = JavaHelpers.generateRandomArrayFromArray(3, GoalOralTranslationSteps.getAllOralTranslationSteps()).toArray(new String[0]);
            data.setSteps(stepsValue);

            String methodologyValue = JavaHelpers.getRandomStringFromArray(GoalMethodologyOralTranslation.getAllGoalMethodologyOralTranslationValues());
            data.setMethodologyValue(methodologyValue);
        } else if (methodologyType.equals(GoalMethodologyType.OralStories.getGoalMethodologyType())) {
            String[] stepsValue = JavaHelpers.generateRandomArrayFromArray(3, GoalOralStoriesSteps.getAllOralStoriesSteps()).toArray(new String[0]);
            data.setSteps(stepsValue);

            String methodologyValue = JavaHelpers.getRandomStringFromArray(GoalMethodologyOralStories.getAllGoalMethodologyOralStoriesValues());
            data.setMethodologyValue(methodologyValue);

        } else if (methodologyType.equals(GoalMethodologyType.Visual.getGoalMethodologyType())) {

            String methodologyValue = JavaHelpers.getRandomStringFromArray(GoalMethodologyVisual.getAllGoalMethodologyVisualValues());

            if (methodologyValue.equals(GoalMethodologyVisual.Film.getGoalMethodologyVisualType())) {
                String[] stepsValue = JavaHelpers.generateRandomArrayFromArray(1, GoalVisualFilmSteps.getAllGoalVisualFilmSteps()).toArray(new String[0]);
                data.setMethodologyValue(methodologyValue);
                data.setSteps(stepsValue);
            } else if (methodologyValue.equals(GoalMethodologyVisual.SignLanguage.getGoalMethodologyVisualType())) {
                String[] stepsValue = JavaHelpers.generateRandomArrayFromArray(1, GoalVisualSignLanguageSteps.getAllGoalVisualSignLanguageSteps()).toArray(new String[0]);
                data.setMethodologyValue(methodologyValue);
                data.setSteps(stepsValue);
            } else if (methodologyValue.equals(GoalMethodologyVisual.Other.getGoalMethodologyVisualType())) {
                String[] stepsValue = JavaHelpers.generateRandomArrayFromArray(1, GoalVisualOtherSteps.getAllGoalVisualOtherSteps()).toArray(new String[0]);
                data.setMethodologyValue(methodologyValue);
                data.setSteps(stepsValue);
            }
        }

        // Get all steps value according to methodology type

        if (methodologyType.equals(GoalMethodologyType.Written.getGoalMethodologyType())) {
            String[] allStepsValue = GoalWrittenSteps.getAllGoalWrittenSteps();
            data.setAllSteps(allStepsValue);

            String methodologyValue = JavaHelpers.getRandomStringFromArray(GoalMethodologyWritten.getAllGoalMethodologyWrittenValues());
            data.setMethodologyValue(methodologyValue);

        } else if (methodologyType.equals(GoalMethodologyType.OralTranslation.getGoalMethodologyType())) {
            String[] allStepsValue = GoalOralTranslationSteps.getAllOralTranslationSteps();
            data.setAllSteps(allStepsValue);

            String methodologyValue = JavaHelpers.getRandomStringFromArray(GoalMethodologyOralTranslation.getAllGoalMethodologyOralTranslationValues());
            data.setMethodologyValue(methodologyValue);

        } else if (methodologyType.equals(GoalMethodologyType.OralStories.getGoalMethodologyType())) {
            String[] allStepsValue = GoalOralStoriesSteps.getAllOralStoriesSteps();
            data.setAllSteps(allStepsValue);

            String methodologyValue = JavaHelpers.getRandomStringFromArray(GoalMethodologyOralStories.getAllGoalMethodologyOralStoriesValues());
            data.setMethodologyValue(methodologyValue);

        } else if (methodologyType.equals(GoalMethodologyType.Visual.getGoalMethodologyType())) {
            String methodologyValue = JavaHelpers.getRandomStringFromArray(GoalMethodologyVisual.getAllGoalMethodologyVisualValues());

            if (methodologyValue.equals(GoalMethodologyVisual.Film.getGoalMethodologyVisualType())) {
                String[] allStepsValue = GoalVisualFilmSteps.getAllGoalVisualFilmSteps();
                data.setMethodologyValue(methodologyValue);
                data.setAllSteps(allStepsValue);

            } else if (methodologyValue.equals(GoalMethodologyVisual.SignLanguage.getGoalMethodologyVisualType())) {
                String[] allStepsValue = GoalVisualSignLanguageSteps.getAllGoalVisualSignLanguageSteps();
                data.setMethodologyValue(methodologyValue);
                data.setAllSteps(allStepsValue);

            } else if (methodologyValue.equals(GoalMethodologyVisual.Other.getGoalMethodologyVisualType())) {
                String[] allStepsValue = GoalVisualOtherSteps.getAllGoalVisualOtherSteps();
                data.setMethodologyValue(methodologyValue);
                data.setAllSteps(allStepsValue);
            }
        }

        return data;
    }

    public Goal getCreateEthnoArtGoalData() throws InterruptedException {

        Goal ethnoArtData = new Goal();
        String timeStamp = new JavaHelpers().getTimeStamp();

        ethnoArtData.setGoalEthnoArtName("EthnoArt" + timeStamp);
        ethnoArtData.setGoalCompletionDescription("Completion Description" + timeStamp);
        ethnoArtData.setGoalStepsProgressValue(JavaHelpers.getIntegerRandomNumber(1, 100));
        ethnoArtData.setGoalProgressTarget(JavaHelpers.getIntegerRandomNumber(1, 100));
        ethnoArtData.setGoalVerses(JavaHelpers.getIntegerRandomNumber(1, 10));

        String randomScriptRefsOldTestament = JavaHelpers.getRandomStringFromArray(GoalScriptureRefOldTestament.getAllGoalScriptRefOldTestamentValues());
        ethnoArtData.setOldScriptureReference(randomScriptRefsOldTestament);

        String randomScriptRefsNewTestament = JavaHelpers.getRandomStringFromArray(GoalScriptureRefNewTestament.getAllGoalScriptRefNewTestamentValues());
        ethnoArtData.setNewScriptureReference(randomScriptRefsNewTestament);

        String[] randomDistributionMethods = JavaHelpers.generateRandomArrayFromArray(1, GoalDistributionMethods.getAllDistributionMethodsNames()).toArray(new String[0]);
        ethnoArtData.setGoalDistributionMethods(randomDistributionMethods);

        String[] randomProgressMeasurement = JavaHelpers.generateRandomArrayFromArray(1, GoalProgressMeasurement.getAllProgressMeasurementsNames()).toArray(new String[0]);
        ethnoArtData.setGoalProgressMeasurement(randomProgressMeasurement);

        String methodologyType = JavaHelpers.getRandomStringFromArray(GoalMethodologyType.getAllGoalMethodologyTypeValues());
        ethnoArtData.setMethodologyType(methodologyType);

        String[] stepsValue = JavaHelpers.generateRandomArrayFromArray(1, GoalEthnoArtSteps.getAllGoalEthnoArtSteps()).toArray(new String[0]);
        ethnoArtData.setSteps(stepsValue);

        // Generate random array for methodology

        if (methodologyType.equals(GoalMethodologyType.Written.getGoalMethodologyType())) {
            String methodologyValue = JavaHelpers.getRandomStringFromArray(GoalMethodologyWritten.getAllGoalMethodologyWrittenValues());
            ethnoArtData.setMethodologyValue(methodologyValue);
        } else if (methodologyType.equals(GoalMethodologyType.OralTranslation.getGoalMethodologyType())) {
            String methodologyValue = JavaHelpers.getRandomStringFromArray(GoalMethodologyOralTranslation.getAllGoalMethodologyOralTranslationValues());
            ethnoArtData.setMethodologyValue(methodologyValue);
        } else if (methodologyType.equals(GoalMethodologyType.OralStories.getGoalMethodologyType())) {
            String methodologyValue = JavaHelpers.getRandomStringFromArray(GoalMethodologyOralStories.getAllGoalMethodologyOralStoriesValues());
            ethnoArtData.setMethodologyValue(methodologyValue);
        } else {
            String methodologyValue = JavaHelpers.getRandomStringFromArray(GoalMethodologyVisual.getAllGoalMethodologyVisualValues());
            ethnoArtData.setMethodologyValue(methodologyValue);
        }


        // Get all steps value according to methodology type

        if (methodologyType.equals(GoalMethodologyType.Written.getGoalMethodologyType())) {
            String[] allStepsValue = GoalEthnoArtSteps.getAllGoalEthnoArtSteps();
            ethnoArtData.setAllSteps(allStepsValue);

            String methodologyValue = JavaHelpers.getRandomStringFromArray(GoalMethodologyWritten.getAllGoalMethodologyWrittenValues());
            ethnoArtData.setMethodologyValue(methodologyValue);

        } else if (methodologyType.equals(GoalMethodologyType.OralTranslation.getGoalMethodologyType())) {
            String[] allStepsValue = GoalEthnoArtSteps.getAllGoalEthnoArtSteps();
            ethnoArtData.setAllSteps(allStepsValue);

            String methodologyValue = JavaHelpers.getRandomStringFromArray(GoalMethodologyOralTranslation.getAllGoalMethodologyOralTranslationValues());
            ethnoArtData.setMethodologyValue(methodologyValue);

        } else if (methodologyType.equals(GoalMethodologyType.OralStories.getGoalMethodologyType())) {
            String[] allStepsValue = GoalEthnoArtSteps.getAllGoalEthnoArtSteps();
            ethnoArtData.setAllSteps(allStepsValue);

            String methodologyValue = JavaHelpers.getRandomStringFromArray(GoalMethodologyOralStories.getAllGoalMethodologyOralStoriesValues());
            ethnoArtData.setMethodologyValue(methodologyValue);

        } else if (methodologyType.equals(GoalMethodologyType.Visual.getGoalMethodologyType())) {
            String[] allStepsValue = GoalEthnoArtSteps.getAllGoalEthnoArtSteps();
            ethnoArtData.setAllSteps(allStepsValue);

            String methodologyValue = JavaHelpers.getRandomStringFromArray(GoalMethodologyVisual.getAllGoalMethodologyVisualValues());
            ethnoArtData.setMethodologyValue(methodologyValue);
        }

        return ethnoArtData;
    }

    public Goal getSearchGoalData() {
        Goal data = new Goal();
        data.setGoalStoryName("Story");
        data.setGoalEthnoArtName("EthnoArt");
        data.setGoalFilmName("Film");
        return data;

    }

}

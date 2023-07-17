package funcLibs.project;

import dataobjects.Goal;
import enums.goal.GoalDropdown;
import enums.goal.GoalType;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import pageobjects.cord.projects.ProjectDetailsPO;
import pageobjects.cord.projects.languageEngagement.ProjectLanguageEngagementDetailsPO;
import pageobjects.cord.projects.languageEngagement.goal.CreateGoalPO;
import pageobjects.cord.projects.languageEngagement.goal.CreateGoalTypePO;
import utilities.SeleniumHelpers;

import java.util.Arrays;

public class GoalFunctions {

    protected WebDriver driver;
    protected SeleniumHelpers selenium;

    public GoalFunctions(WebDriver driver, SeleniumHelpers selenium) {
        this.driver = driver;
        this.selenium = selenium;
    }

    /**
     * create Goal
     *
     * @param goalData goal data
     */

    public void createGoal(Goal goalData, String languageName, String partnerName) {

        try {

            CreateGoalPO createGoalPO = new CreateGoalPO(driver);
            ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
            CreateGoalTypePO createGoalTypePO = new CreateGoalTypePO(driver);
            ProjectLanguageEngagementDetailsPO projectLanguageEngagementDetailsPO = new ProjectLanguageEngagementDetailsPO(driver);

            Reporter.log("Step 1 - Click on view details button and update language engagement ");
            projectDetails.clickOnViewDetailsLanguageEngagementButton(languageName);

            Reporter.log("Step 2 - Click on create goal button and choose goal");
            projectLanguageEngagementDetailsPO.clickCreateGoalButton();
            createGoalPO.selectGoalTypeByName(GoalType.Story.getGoalType());

            Reporter.log("Step 4 - Create a Selected goal");
            createGoalPO.enterGoalNameAndAddGoal(GoalType.Story, goalData);
            createGoalTypePO.clickOnGoalTypeSubmitButton();

            Reporter.log("Step 5 - Click on goal scripture reference dropdown and select old and new testament");
            createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.ScriptureReference.getGoalDropdown());
            createGoalPO.selectGoalScriptureRefOldTestamentByName(goalData);

            Reporter.log("Step 6 - Click on goal distribution methods dropdown and choose distributionMethods");
            createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.DistributionMethods.getGoalDropdown());
            createGoalPO.selectGoalDistributionMethodsByName(goalData.getGoalDistributionMethods());

            Reporter.log("Step 7 - Choose partners producing these distribution methods");
            createGoalPO.selectGoalDistributionMethodsByName(partnerName);

            Reporter.log("Step 8 - Click on goal methodology dropdown and choose methodology");
            createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.Methodology.getGoalDropdown());
            createGoalPO.selectGoalMethodologyByTypeAndValue(goalData.getMethodologyType(), goalData.getMethodologyValue());

            Reporter.log("Step 9 - Click on goal steps dropdown and deselect the steps");
            createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.Steps.getGoalDropdown());

            Object[] allStepObject = Arrays.stream(goalData.getAllSteps()).toArray();
            String[] allSteps = Arrays.copyOf(allStepObject, allStepObject.length, String[].class);
            createGoalPO.deSelectGoalStepsByNameMethodologyType(goalData.getMethodologyType(), allSteps);

            Reporter.log("Step 10 - Click on goal steps dropdown and select the steps");
            Object[] object = Arrays.stream(goalData.getSteps()).toArray();
            String[] steps = Arrays.copyOf(object, object.length, String[].class);
            createGoalPO.selectGoalStepsByNameMethodologyType(goalData.getMethodologyType(), steps);

            Reporter.log("Step 11 - Click on goal Completion Description dropdown and add Completion Description");
            createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.CompletionDescription.getGoalDropdown());
            createGoalPO.fillCompletionDescriptionDetail(goalData);
            createGoalPO.clickOnSaveGoalButton();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * edit Goal
     *
     * @param editGoalData data
     */

    public void editGoal(Goal editGoalData, Goal goalData, String partnerName) {

        try {

            CreateGoalPO createGoalPO = new CreateGoalPO(driver);
            ProjectLanguageEngagementDetailsPO projectLanguageEngagementDetailsPO = new ProjectLanguageEngagementDetailsPO(driver);

            Reporter.log("Step 1 - Click on created Goal");
            projectLanguageEngagementDetailsPO.clickOnGoalCard();

            Reporter.log("Step 2 - Click on goal scripture edit button");
            projectLanguageEngagementDetailsPO.clickEditGoalButton();

            Reporter.log("Step 3 - Click on goal scripture reference dropdown and select old and new testament");
            createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.ScriptureReference.getGoalDropdown());
            createGoalPO.selectGoalScriptureRefOldTestamentByName(editGoalData);

            Reporter.log("Step 4 - Click on goal distribution methods dropdown and choose distributionMethods");
            createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.DistributionMethods.getGoalDropdown());
            createGoalPO.selectGoalDistributionMethodsByName(goalData.getGoalDistributionMethods());
            createGoalPO.selectGoalDistributionMethodsByName(editGoalData.getGoalDistributionMethods());

            Reporter.log("Step 5 - Choose partners producing these distribution methods");
            createGoalPO.selectGoalDistributionMethodsByName(partnerName);

            Reporter.log("Step 6 - Click on goal methodology dropdown and choose methodology");
            createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.Methodology.getGoalDropdown());
            createGoalPO.selectGoalMethodologyByTypeAndValue(editGoalData.getMethodologyType(), editGoalData.getMethodologyValue());
            createGoalPO.selectGoalMethodologyByTypeAndValue(editGoalData.getMethodologyType(), editGoalData.getMethodologyValue());

            Reporter.log("Step 7 - Click on goal steps dropdown and deselect the steps");
            createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.Steps.getGoalDropdown());

            Object[] allStepObject = Arrays.stream(editGoalData.getAllSteps()).toArray();
            String[] allSteps = Arrays.copyOf(allStepObject, allStepObject.length, String[].class);
            createGoalPO.deSelectGoalStepsByNameMethodologyType(editGoalData.getMethodologyType(), allSteps);

            Reporter.log("Step 8 - Click on goal steps dropdown and select the steps");
            Object[] object = Arrays.stream(editGoalData.getSteps()).toArray();
            String[] steps = Arrays.copyOf(object, object.length, String[].class);
            createGoalPO.selectGoalStepsByNameMethodologyType(editGoalData.getMethodologyType(), steps);

            Reporter.log("Step 9 - Click on goal Completion Description dropdown and add Completion Description");
            createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.CompletionDescription.getGoalDropdown());
            createGoalPO.fillCompletionDescriptionDetail(editGoalData);
            createGoalPO.clickOnSaveGoalButton();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

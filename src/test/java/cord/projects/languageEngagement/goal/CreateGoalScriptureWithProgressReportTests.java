package cord.projects.languageEngagement.goal;

import base.BaseTest;
import datafactory.EngagementData;

import datafactory.GoalData;
import datafactory.PartnerData;
import datafactory.ProjectData;
import dataobjects.*;
import enums.goal.*;
import funcLibs.project.PartnershipFunctions;
import funcLibs.project.ProjectFunctions;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import pageobjects.cord.projects.intershipEngagement.ProjectEngagementDetailsUpdateStartEndDatePO;
import pageobjects.cord.projects.languageEngagement.ProjectLanguageEngagementDetailsPO;
import pageobjects.cord.projects.languageEngagement.goal.CreateGoalPO;
import pageobjects.cord.projects.languageEngagement.goal.CreateGoalTypePO;
import pageobjects.cord.projects.languageEngagement.goal.GoalDetailsPO;
import pageobjects.cord.projects.languageEngagement.goal.UpdateGoalProgressReportPO;

import java.util.Arrays;

public class CreateGoalScriptureWithProgressReportTests extends BaseTest {

    /*Test 1 : Verify that user can create scripture Goal with progress report successfully*/
    @Test
    public void VerifyUserCanCreateScriptureGoalWithProgressReportSuccessfully() throws InterruptedException, ParseException, java.text.ParseException {

        /*
         * We can change whole test process for specific user from here.
         * */
        User myUser = adminUser;

        CreateGoalPO createGoalPO = new CreateGoalPO(driver);
        GoalDetailsPO goalDetailsPO = new GoalDetailsPO(driver);
        CreateGoalTypePO createGoalTypePO = new CreateGoalTypePO(driver);
        Project data = new ProjectData().getCreateTranslationProjectData();
        String createdProjectName = data.getProjectName();
        Internship internData = new EngagementData().getCreateEngagementData();
        Partner partnerShipAdminData = new PartnerData().getCreatePartnerData();
        UpdateGoalProgressReportPO updateGoalProgressReportPO = new UpdateGoalProgressReportPO(driver);
        ProjectLanguageEngagementDetailsPO projectLanguageEngagementDetailsPO = new ProjectLanguageEngagementDetailsPO(driver);
        ProjectEngagementDetailsUpdateStartEndDatePO projectEngagementDetailsUpdateStartEndDatePO = new ProjectEngagementDetailsUpdateStartEndDatePO(driver);

        Reporter.log("Step 1 - Create project and partnership");
        Goal goalData = new GoalData().getCreateGoalData();
        new ProjectFunctions(driver, selenium).createProjectAndGotoProjectDetailsPage(myUser, data);
        new PartnershipFunctions(driver, selenium).createProjectPartnership(partnerShipAdminData, createdProjectName, adminUser);

        selenium.back();

        Reporter.log("Step 2 - Create Language Engagement");
        new ProjectFunctions(driver, selenium).createLanguageEngagementAndGotoLanguageEngagementDetailsPage();

        Reporter.log("Step 3 - Click on start-end date button and enter date");
        projectLanguageEngagementDetailsPO.clickLanguageEngagementStartEndDateButton();
        projectEngagementDetailsUpdateStartEndDatePO.fillEngagementStartEndDateDetailsAndClickOnSaveButton(internData);

        Reporter.log("Step 4 - Click on create goal button and choose goal");
        projectLanguageEngagementDetailsPO.clickCreateGoalButton();
        createGoalPO.selectGoalTypeByName(GoalType.Story.getGoalType());

        Reporter.log("Step 5 - Create a Selected goal");
        createGoalPO.enterGoalNameAndAddGoal(GoalType.Story, goalData);
        createGoalTypePO.clickOnGoalTypeSubmitButton();

        Reporter.log("Step 6 - Click on goal scripture reference dropdown and select old and new testament");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.ScriptureReference.getGoalDropdown());
        createGoalPO.selectGoalScriptureRefOldTestamentByName(goalData);

        Reporter.log("Step 6 - Click on goal distribution methods dropdown and choose distributionMethods");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.DistributionMethods.getGoalDropdown());
        createGoalPO.selectGoalDistributionMethodsByName(goalData.getGoalDistributionMethods());

        Reporter.log("Step 7 - Choose partners producing these distribution methods");
        createGoalPO.selectGoalDistributionMethodsByName(partnerShipAdminData.getOrganizationName());

        Reporter.log("Step 9 - Click on goal methodology dropdown and choose methodology");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.Methodology.getGoalDropdown());
        createGoalPO.selectGoalMethodologyByTypeAndValue(goalData.getMethodologyType(), goalData.getMethodologyValue());

        Reporter.log("Step 9 - Click on goal steps dropdown and deselect the steps");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.Steps.getGoalDropdown());
        createGoalPO.deSelectGoalStepsByNameMethodologyType(goalData.getMethodologyType(), goalData.getAllSteps());

        Reporter.log("Step 10 - Click on goal steps dropdown and select the steps");
        createGoalPO.selectGoalStepsByNameMethodologyType(goalData.getMethodologyType(), goalData.getSteps());

        Reporter.log("Step 11 - Click on Goal Completion Description dropdown and add details");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.CompletionDescription.getGoalDropdown());
        createGoalPO.fillCompletionDescriptionDetail(goalData);

        Reporter.log("Step 12 - Click on Goal save button");
        createGoalPO.clickOnSaveGoalButton();

        Reporter.log("Step 13 - Verify goal type");
        Assert.assertEquals(projectLanguageEngagementDetailsPO.getGoalText(), (goalData.getGoalStoryName()), "Goal type is not matched");

        Reporter.log("Step 14 - Click on created goal");
        projectLanguageEngagementDetailsPO.clickOnGoalCard();

        Reporter.log("Step 15 - verify goal details");
        Assert.assertEquals(Arrays.stream(goalDetailsPO.getGoalDistributionMethodsText().split(",")).map(String::trim).toArray(String[]::new), GoalDistributionMethods.getGoalDistributionMethodsByName(goalData.getGoalDistributionMethods()), "Goal distribution method is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalDistributionMethodsPartnerText(), partnerShipAdminData.getOrganizationName(), "Goal distribution method partner is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalMethodologyText(), goalData.getMethodologyType() + " - " + goalData.getMethodologyValue(), "Goal methodology is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalCompletionDescriptionText(), (goalData.getGoalCompletionDescription()), "Goal completion description is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalScriptureReferenceOldTestamentText(), goalData.getOldScriptureReference(), "Goal old scriptureReference is not matched");

        Reporter.log("Step 16 - Refresh page and verify Goal details");
        selenium.refreshPage();
        Assert.assertEquals(Arrays.stream(goalDetailsPO.getGoalDistributionMethodsText().split(",")).map(String::trim).toArray(String[]::new), GoalDistributionMethods.getGoalDistributionMethodsByName(goalData.getGoalDistributionMethods()), "Goal mediums is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalDistributionMethodsPartnerText(), partnerShipAdminData.getOrganizationName(), "Goal distribution method partner is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalMethodologyText(), goalData.getMethodologyType() + " - " + goalData.getMethodologyValue(), "Goal methodology is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalCompletionDescriptionText(), (goalData.getGoalCompletionDescription()), "Goal completion description is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalScriptureReferenceOldTestamentText(), goalData.getOldScriptureReference(), "Goal old scriptureReference is not matched");

        Reporter.log("Step 17 - Click on progress report step button by name");
        goalDetailsPO.clickOnProgressReportStepsButtonByStepName(goalData.getSteps()[0]);
        updateGoalProgressReportPO.fillProgressReportDetailsAndClickOnTheSubmitButton(goalData);

        Reporter.log("Step 18 - Verify progress report step percentage");
        Assert.assertEquals(goalDetailsPO.getPercentageOfProgressReportSteps(goalData.getSteps()[0]), goalData.getGoalStepsProgressValue() + "% Completed", "Goal methodology is not matched");
    }
}





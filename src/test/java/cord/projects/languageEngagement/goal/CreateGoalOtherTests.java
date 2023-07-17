package cord.projects.languageEngagement.goal;

import base.BaseTest;
import datafactory.*;
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
import pageobjects.cord.projects.languageEngagement.goal.GoalDetailsPO;
import pageobjects.cord.projects.languageEngagement.goal.UpdateGoalProgressReportPO;
import utilities.Constants;

import java.util.Arrays;

public class CreateGoalOtherTests extends BaseTest {

    /*Test 1 : Verify that user can create other goal with percent measurement successfully*/
    @Test
    public void verifyUserCanCreateGoalOtherWithPercentMeasurementSuccessfully() throws InterruptedException, ParseException, java.text.ParseException {

        /*
         * We can change whole test process for specific user from here.
         * */
        User myUser = adminUser;

        GoalDetailsPO goalDetailsPO = new GoalDetailsPO(driver);
        CreateGoalPO createGoalPO = new CreateGoalPO(driver);
        Project data = new ProjectData().getCreateTranslationProjectData();
        String createdProjectName = data.getProjectName();
        Internship internData = new EngagementData().getCreateEngagementData();
        Partner partnerShipAdminData = new PartnerData().getCreatePartnerData();
        UpdateGoalProgressReportPO updateGoalProgressReportPO = new UpdateGoalProgressReportPO(driver);
        ProjectLanguageEngagementDetailsPO projectLanguageEngagementDetailsPO = new ProjectLanguageEngagementDetailsPO(driver);

        Reporter.log("Step 1 - Create project and partnership");
        Goal goalData = new GoalData().getCreateGoalData();
        new ProjectFunctions(driver, selenium).createProjectAndGotoProjectDetailsPage(myUser, data);
        new PartnershipFunctions(driver, selenium).createProjectPartnership(partnerShipAdminData, createdProjectName, adminUser);

        selenium.back();

        Reporter.log("Step 2 - Create Language Engagement");
        new ProjectFunctions(driver, selenium).createLanguageEngagementAndGotoLanguageEngagementDetailsPage();

        ProjectEngagementDetailsUpdateStartEndDatePO projectEngagementDetailsUpdateStartEndDatePO = new ProjectEngagementDetailsUpdateStartEndDatePO(driver);
        projectLanguageEngagementDetailsPO.clickLanguageEngagementStartEndDateButton();
        projectEngagementDetailsUpdateStartEndDatePO.fillEngagementStartEndDateDetailsAndClickOnSaveButton(internData);

        Reporter.log("Step 3 - Click on create goal button and choose goal type");
        projectLanguageEngagementDetailsPO.clickCreateGoalButton();
        createGoalPO.selectGoalTypeByName(GoalType.Other.getGoalType());

        Reporter.log("Step 4 - Fill title and description details");
        createGoalPO.fillOtherGoalTitleAndDescriptionDetail(goalData);

        Reporter.log("Step 5 - Click on goal distribution methods dropdown and choose distributionMethods");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.DistributionMethods.getGoalDropdown());
        createGoalPO.selectGoalDistributionMethodsByName(goalData.getGoalDistributionMethods());

        Reporter.log("Step 6 - Choose partners producing these distribution methods");
        createGoalPO.selectGoalDistributionMethodsByName(partnerShipAdminData.getOrganizationName());

        Reporter.log("Step 7 - Click on goal methodology dropdown and choose methodology");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.Methodology.getGoalDropdown());
        createGoalPO.selectGoalMethodologyByTypeAndValue(goalData.getMethodologyType(), goalData.getMethodologyValue());

        Reporter.log("Step 8 - Click on goal progress measurement dropdown and choose percent progress measurement");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.ProgressMeasurement.getGoalDropdown());
        createGoalPO.selectGoalProgressMeasurementByName(GoalProgressMeasurement.Percent.getGoalProgressMeasurement());

        Reporter.log("Step 9 - Click on goal Completion Description dropdown and add Completion Description");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.CompletionDescription.getGoalDropdown());
        createGoalPO.fillCompletionDescriptionDetail(goalData);
        createGoalPO.clickOnSaveGoalButton();

        Assert.assertEquals(projectLanguageEngagementDetailsPO.getGoalText(), (goalData.getGoalOtherTitle()), "goal other type is not matched");

        Reporter.log("Step 10 - Click on created goal");
        projectLanguageEngagementDetailsPO.clickOnGoalCard();

        selenium.refreshPage();

        Reporter.log("Step 11 - Click on progress report step button by name");
        goalDetailsPO.clickOnProgressReportStepsButtonByStepName(GoalEthnoArtSteps.Completed.getGoalEthnoArtSteps());

        Reporter.log("Step 12 - Fill the progress report details");
        updateGoalProgressReportPO.fillProgressReportDetailsAndClickOnTheSubmitButton(goalData);

        Reporter.log("Step 13 - verify goal details");
        Assert.assertEquals(goalDetailsPO.getGoalDescriptionText(), (goalData.getGoalOtherDescription()), "Goal description is not matched");
        Assert.assertEquals(Arrays.stream(goalDetailsPO.getGoalDistributionMethodsText().split(",")).map(String::trim).toArray(String[]::new), GoalDistributionMethods.getGoalDistributionMethodsByName(goalData.getGoalDistributionMethods()), "Goal distribution method is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalDistributionMethodsPartnerText(), partnerShipAdminData.getOrganizationName(), "Goal distribution method partner is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalMethodologyText(), goalData.getMethodologyType() + " - " + goalData.getMethodologyValue(), "Goal methodology is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalCompletionDescriptionText(), (goalData.getGoalCompletionDescription()), "Goal completion description is not matched");
        Assert.assertEquals(goalDetailsPO.getPercentageOfProgressReportSteps(GoalEthnoArtSteps.Completed.getGoalEthnoArtSteps()), goalData.getGoalStepsProgressValue() + "% Completed", "Goal progress measurement percent  is not matched");
    }

    /*Test 2 : Verify that user can create other goal with number measurement successfully */
    @Test
    public void verifyUserCanCreateGoalOtherWithNumberMeasurementSuccessfully() throws InterruptedException, ParseException, java.text.ParseException {

        /*
         * We can change whole test process for specific user from here.
         * */
        User myUser = adminUser;

        GoalDetailsPO goalDetailsPO = new GoalDetailsPO(driver);
        CreateGoalPO createGoalPO = new CreateGoalPO(driver);
        Project data = new ProjectData().getCreateTranslationProjectData();
        String createdProjectName = data.getProjectName();
        Internship internData = new EngagementData().getCreateEngagementData();
        Partner partnerShipAdminData = new PartnerData().getCreatePartnerData();
        UpdateGoalProgressReportPO updateGoalProgressReportPO = new UpdateGoalProgressReportPO(driver);
        ProjectLanguageEngagementDetailsPO projectLanguageEngagementDetailsPO = new ProjectLanguageEngagementDetailsPO(driver);

        Reporter.log("Step 1 - Create project and partnership");
        Goal goalData = new GoalData().getCreateGoalData();
        new ProjectFunctions(driver, selenium).createProjectAndGotoProjectDetailsPage(myUser, data);
        new PartnershipFunctions(driver, selenium).createProjectPartnership(partnerShipAdminData, createdProjectName, adminUser);

        selenium.back();

        Reporter.log("Step 2 - Create Language Engagement");
        new ProjectFunctions(driver, selenium).createLanguageEngagementAndGotoLanguageEngagementDetailsPage();

        ProjectEngagementDetailsUpdateStartEndDatePO projectEngagementDetailsUpdateStartEndDatePO = new ProjectEngagementDetailsUpdateStartEndDatePO(driver);
        projectLanguageEngagementDetailsPO.clickLanguageEngagementStartEndDateButton();
        projectEngagementDetailsUpdateStartEndDatePO.fillEngagementStartEndDateDetailsAndClickOnSaveButton(internData);

        Reporter.log("Step 3 - Click on create goal button and choose goal type");
        projectLanguageEngagementDetailsPO.clickCreateGoalButton();
        createGoalPO.selectGoalTypeByName(GoalType.Other.getGoalType());

        Reporter.log("Step 4 - Fill title and description details");
        createGoalPO.fillOtherGoalTitleAndDescriptionDetail(goalData);

        Reporter.log("Step 5 - Click on goal distribution methods dropdown and choose distributionMethods");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.DistributionMethods.getGoalDropdown());
        createGoalPO.selectGoalDistributionMethodsByName(goalData.getGoalDistributionMethods());

        Reporter.log("Step 6 - Choose partners producing these distribution methods");
        createGoalPO.selectGoalDistributionMethodsByName(partnerShipAdminData.getOrganizationName());

        Reporter.log("Step 7 - Click on goal methodology dropdown and choose methodology");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.Methodology.getGoalDropdown());
        createGoalPO.selectGoalMethodologyByTypeAndValue(goalData.getMethodologyType(), goalData.getMethodologyValue());

        Reporter.log("Step 8 - Click on goal progress measurement dropdown and choose percent progress measurement");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.ProgressMeasurement.getGoalDropdown());
        createGoalPO.selectGoalProgressMeasurementByName(GoalProgressMeasurement.Number.getGoalProgressMeasurement());

        Reporter.log("Step 9 - Click on goal progress target dropdown and fill progress target details");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.ProgressTarget.getGoalDropdown());
        createGoalPO.fillProgressTargetDetail(goalData);

        Reporter.log("Step 10 - Click on goal Completion Description dropdown and add Completion Description");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.CompletionDescription.getGoalDropdown());
        createGoalPO.fillCompletionDescriptionDetail(goalData);
        createGoalPO.clickOnSaveGoalButton();

        Assert.assertEquals(projectLanguageEngagementDetailsPO.getGoalText(), (goalData.getGoalOtherTitle()), "goal other type is not matched");

        Reporter.log("Step 11 - Click on created goal");
        projectLanguageEngagementDetailsPO.clickOnGoalCard();

        selenium.refreshPage();

        Reporter.log("Step 12 - Click on progress report step button by name");
        goalDetailsPO.clickOnProgressReportStepsButtonByStepName(GoalEthnoArtSteps.Completed.getGoalEthnoArtSteps());

        Reporter.log("Step 13 - verify goal details");
        Assert.assertEquals(goalDetailsPO.getGoalDescriptionText(), (goalData.getGoalOtherDescription()), "Goal description is not matched");
        Assert.assertEquals(Arrays.stream(goalDetailsPO.getGoalDistributionMethodsText().split(",")).map(String::trim).toArray(String[]::new), GoalDistributionMethods.getGoalDistributionMethodsByName(goalData.getGoalDistributionMethods()), "Goal distribution method is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalDistributionMethodsPartnerText(), partnerShipAdminData.getOrganizationName(), "Goal distribution method partner is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalMethodologyText(), goalData.getMethodologyType() + " - " + goalData.getMethodologyValue(), "Goal methodology is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalCompletionDescriptionText(), (goalData.getGoalCompletionDescription()), "Goal completion description is not matched");
        Assert.assertEquals(updateGoalProgressReportPO.getGoalProgressTargetText(), Constants.goalProgressTargetSuggestionText + (goalData.getGoalProgressTarget()), "Goal progress target is not matched");

    }

    /*Test 3 : Verify that user can create other goal with done not done measurement successfully */
    @Test
    public void verifyUserCanCreateGoalOtherWithDoneNotDoneMeasurementSuccessfully() throws InterruptedException, ParseException, java.text.ParseException {

        /*
         * We can change whole test process for specific user from here.
         * */
        User myUser = adminUser;

        GoalDetailsPO goalDetailsPO = new GoalDetailsPO(driver);
        CreateGoalPO createGoalPO = new CreateGoalPO(driver);
        Project data = new ProjectData().getCreateTranslationProjectData();
        String createdProjectName = data.getProjectName();
        Internship internData = new EngagementData().getCreateEngagementData();
        Partner partnerShipAdminData = new PartnerData().getCreatePartnerData();
        UpdateGoalProgressReportPO updateGoalProgressReportPO = new UpdateGoalProgressReportPO(driver);
        ProjectLanguageEngagementDetailsPO projectLanguageEngagementDetailsPO = new ProjectLanguageEngagementDetailsPO(driver);

        Reporter.log("Step 1 - Create project and partnership");
        Goal goalData = new GoalData().getCreateGoalData();
        new ProjectFunctions(driver, selenium).createProjectAndGotoProjectDetailsPage(myUser, data);
        new PartnershipFunctions(driver, selenium).createProjectPartnership(partnerShipAdminData, createdProjectName, adminUser);

        selenium.back();

        Reporter.log("Step 2 - Create Language Engagement");
        new ProjectFunctions(driver, selenium).createLanguageEngagementAndGotoLanguageEngagementDetailsPage();

        ProjectEngagementDetailsUpdateStartEndDatePO projectEngagementDetailsUpdateStartEndDatePO = new ProjectEngagementDetailsUpdateStartEndDatePO(driver);
        selenium.hardWait(3);
        projectLanguageEngagementDetailsPO.clickLanguageEngagementStartEndDateButton();
        projectEngagementDetailsUpdateStartEndDatePO.fillEngagementStartEndDateDetailsAndClickOnSaveButton(internData);

        Reporter.log("Step 3 - Click on create goal button and choose goal type");
        projectLanguageEngagementDetailsPO.clickCreateGoalButton();
        createGoalPO.selectGoalTypeByName(GoalType.Other.getGoalType());

        Reporter.log("Step 4 - Fill title and description details");
        createGoalPO.fillOtherGoalTitleAndDescriptionDetail(goalData);

        Reporter.log("Step 5 - Click on goal distribution methods dropdown and choose distributionMethods");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.DistributionMethods.getGoalDropdown());
        createGoalPO.selectGoalDistributionMethodsByName(goalData.getGoalDistributionMethods());

        Reporter.log("Step 6 - Choose partners producing these distribution methods");
        createGoalPO.selectGoalDistributionMethodsByName(partnerShipAdminData.getOrganizationName());

        Reporter.log("Step 7 - Click on goal methodology dropdown and choose methodology");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.Methodology.getGoalDropdown());
        createGoalPO.selectGoalMethodologyByTypeAndValue(goalData.getMethodologyType(), goalData.getMethodologyValue());

        Reporter.log("Step 8 - Click on goal progress measurement dropdown and choose percent progress measurement");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.ProgressMeasurement.getGoalDropdown());
        createGoalPO.selectGoalProgressMeasurementByName(GoalProgressMeasurement.DoneNotDone.getGoalProgressMeasurement());

        Reporter.log("Step 10 - Click on goal Completion Description dropdown and add Completion Description");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.CompletionDescription.getGoalDropdown());
        createGoalPO.fillCompletionDescriptionDetail(goalData);
        createGoalPO.clickOnSaveGoalButton();

        Assert.assertEquals(projectLanguageEngagementDetailsPO.getGoalText(), (goalData.getGoalOtherTitle()), "goal other type is not matched");

        Reporter.log("Step 11 - Click on created goal");
        projectLanguageEngagementDetailsPO.clickOnGoalCard();

        selenium.refreshPage();

        Reporter.log("Step 12 - Click on progress report step button by name and click on progress status check box");
        goalDetailsPO.clickOnProgressReportStepsButtonByStepName(GoalEthnoArtSteps.Completed.getGoalEthnoArtSteps());
        updateGoalProgressReportPO.clickOnTheProgressStatus();

        Reporter.log("Step 13 - verify goal details");
        Assert.assertEquals(goalDetailsPO.getGoalDescriptionText(), (goalData.getGoalOtherDescription()), "Goal description is not matched");
        Assert.assertEquals(Arrays.stream(goalDetailsPO.getGoalDistributionMethodsText().split(",")).map(String::trim).toArray(String[]::new), GoalDistributionMethods.getGoalDistributionMethodsByName(goalData.getGoalDistributionMethods()), "Goal distribution method is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalDistributionMethodsPartnerText(), partnerShipAdminData.getOrganizationName(), "Goal distribution method partner is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalMethodologyText(), goalData.getMethodologyType() + " - " + goalData.getMethodologyValue(), "Goal methodology is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalCompletionDescriptionText(), (goalData.getGoalCompletionDescription()), "Goal completion description is not matched");
        Assert.assertTrue(updateGoalProgressReportPO.isGoalProgressMeasurementStatusPresent(), "Goal progress measurement status is not present");

    }

}
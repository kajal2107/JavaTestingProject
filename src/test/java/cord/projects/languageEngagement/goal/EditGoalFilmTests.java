package cord.projects.languageEngagement.goal;

import base.BaseTest;
import datafactory.GoalData;
import datafactory.PartnerData;
import datafactory.ProjectData;
import dataobjects.Goal;
import dataobjects.Partner;
import dataobjects.Project;
import dataobjects.User;
import enums.goal.GoalBookRadioButton;
import enums.goal.GoalDropdown;
import enums.goal.GoalDistributionMethods;
import enums.goal.GoalType;
import funcLibs.project.PartnershipFunctions;
import funcLibs.project.ProjectFunctions;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageobjects.cord.account.LoginPO;
import pageobjects.cord.projects.languageEngagement.ProjectLanguageEngagementDetailsPO;
import pageobjects.cord.projects.languageEngagement.goal.CreateGoalPO;
import pageobjects.cord.projects.languageEngagement.goal.CreateGoalTypePO;
import pageobjects.cord.projects.languageEngagement.goal.GoalDetailsPO;
import utilities.Constants;
import utilities.DriverManager;
import utilities.SeleniumHelpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditGoalFilmTests extends BaseTest {

    protected String projectDetailPageUrl;
    Goal goalData = new GoalData().getCreateGoalData();
    Goal editGoalData = new GoalData().getCreateGoalData();
    Partner partnerShipAdminData = new PartnerData().getCreatePartnerData();

    public EditGoalFilmTests() throws InterruptedException {
    }

    @BeforeClass
    public void beforeClass() throws InterruptedException {
        WebDriver localDriver;
        DriverManager drivermanager;

        drivermanager = new DriverManager();
        localDriver = drivermanager.setUp("chrome");
        SeleniumHelpers localSelenium = new SeleniumHelpers(localDriver);

        /*
         * We can change whole test process for specific user from here.
         * */

        User myUser = adminUser;

        CreateGoalPO createGoalPO = new CreateGoalPO(localDriver);
        CreateGoalTypePO createGoalTypePO = new CreateGoalTypePO(localDriver);
        Project data = new ProjectData().getCreateTranslationProjectData();
        String createdProjectName = data.getProjectName();
        ProjectLanguageEngagementDetailsPO projectLanguageEngagementDetailsPO = new ProjectLanguageEngagementDetailsPO(localDriver);

        Reporter.log("Step 1 - Create project and partnership");
        new ProjectFunctions(localDriver, localSelenium).createProjectAndGotoProjectDetailsPage(myUser, data);
        new PartnershipFunctions(localDriver, localSelenium).createProjectPartnership(partnerShipAdminData, createdProjectName, adminUser);

        localSelenium.back();

        Reporter.log("Step 2 - Create Language Engagement");
        new ProjectFunctions(localDriver, localSelenium).createLanguageEngagementAndGotoLanguageEngagementDetailsPage();

        Reporter.log("Step 3 - Click on create Goal button and choose Goal");
        projectLanguageEngagementDetailsPO.clickCreateGoalButton();
        createGoalPO.selectGoalTypeByName(GoalType.Film.getGoalType());

        Reporter.log("Step 4 - Create a Selected Goal");
        createGoalPO.enterGoalNameAndAddGoal(GoalType.Film, goalData);
        createGoalTypePO.clickOnGoalTypeSubmitButton();

        Reporter.log("Step 5 - Click on goal scripture reference dropdown and select old and new testament");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.ScriptureReference.getGoalDropdown());
        createGoalPO.selectGoalScriptureRefOldTestamentByName(goalData);

        Reporter.log("Step 6 - Click on goal distribution methods dropdown and choose distributionMethods");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.DistributionMethods.getGoalDropdown());
        createGoalPO.selectGoalDistributionMethodsByName(goalData.getGoalDistributionMethods());

        Reporter.log("Step 7 - Choose partners producing these distribution methods");
        createGoalPO.selectGoalDistributionMethodsByName(partnerShipAdminData.getOrganizationName());

        Reporter.log("Step 8 - Click on Goal methodology dropdown and choose methodology");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.Methodology.getGoalDropdown());
        createGoalPO.selectGoalMethodologyByTypeAndValue(goalData.getMethodologyType(), goalData.getMethodologyValue());

        Reporter.log("Step 9 - Click on goal steps dropdown and deselect the steps");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.Steps.getGoalDropdown());
        createGoalPO.deSelectGoalStepsByNameMethodologyType(goalData.getMethodologyType(), goalData.getAllSteps());

        Reporter.log("Step 10 - Click on goal steps dropdown and select the steps");
        createGoalPO.selectGoalStepsByNameMethodologyType(goalData.getMethodologyType(), goalData.getSteps());

        Reporter.log("Step 11 - Click on goal Completion Description dropdown and add Completion Description");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.CompletionDescription.getGoalDropdown());
        createGoalPO.fillCompletionDescriptionDetail(goalData);
        createGoalPO.clickOnSaveGoalButton();

        projectDetailPageUrl = localDriver.getCurrentUrl();
        drivermanager.tearDown();
    }


    /*Test 1 : Verify that user can edit scripture Goal successfully*/
    @Test
    public void verifyUserCanEditScriptureGoalSuccessfully() throws InterruptedException {

        LoginPO login = new LoginPO(driver);
        CreateGoalPO createGoalPO = new CreateGoalPO(driver);
        GoalDetailsPO goalDetailsPO = new GoalDetailsPO(driver);
        ProjectLanguageEngagementDetailsPO projectLanguageEngagementDetailsPO = new ProjectLanguageEngagementDetailsPO(driver);

        Reporter.log("Step 1 - Login and navigate to project detail page");
        selenium.navigateToPage(projectDetailPageUrl);
        login.fillLoginDetailsAndPerformLogin(adminUser.getEmail(), adminUser.getPassword());

        Reporter.log("Step 2 - Click on created Goal");
        projectLanguageEngagementDetailsPO.clickOnGoalCard();

        Reporter.log("Step 3 - Click on Goal scripture edit button");
        projectLanguageEngagementDetailsPO.clickEditGoalButton();

        Reporter.log("Step 4 - Click on Goal scripture reference dropdown and select new testament");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.ScriptureReference.getGoalDropdown());
        String[] scripturePartialKnownChapters = Constants.scripturePartialKnownChapter;
        createGoalPO.selectGoalScriptureRefNewTestamentByName(editGoalData);

        Reporter.log("Step 5 - Select partial known radio button with different chapters");
        createGoalPO.selectGoalPartialKnownRadioButtonWithChapters(GoalBookRadioButton.partialKnown.getGoalBookRadioButton(), scripturePartialKnownChapters);

        Reporter.log("Step 6 - Click on goal distribution methods dropdown and choose distributionMethods");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.DistributionMethods.getGoalDropdown());
        createGoalPO.selectGoalDistributionMethodsByName(goalData.getGoalDistributionMethods());
        createGoalPO.selectGoalDistributionMethodsByName(editGoalData.getGoalDistributionMethods());

        Reporter.log("Step 7 - Choose partners producing these distribution methods");
        createGoalPO.selectGoalDistributionMethodsByName(partnerShipAdminData.getOrganizationName());

        Reporter.log("Step 8 - Click on Goal methodology dropdown and choose methodology");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.Methodology.getGoalDropdown());
        createGoalPO.selectGoalMethodologyByTypeAndValue(goalData.getMethodologyType(), goalData.getMethodologyValue());
        createGoalPO.selectGoalMethodologyByTypeAndValue(editGoalData.getMethodologyType(), editGoalData.getMethodologyValue());

        Reporter.log("Step 9 - Click on goal steps dropdown and deselect the steps");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.Steps.getGoalDropdown());
        createGoalPO.deSelectGoalStepsByNameMethodologyType(editGoalData.getMethodologyType(), editGoalData.getAllSteps());

        Reporter.log("Step 10 - Click on goal steps dropdown and select the steps");
        createGoalPO.selectGoalStepsByNameMethodologyType(editGoalData.getMethodologyType(), editGoalData.getSteps());

        Reporter.log("Step 11 - Click on Goal Completion Description dropdown and add Completion Description");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.CompletionDescription.getGoalDropdown());
        createGoalPO.fillCompletionDescriptionDetail(editGoalData);

        Reporter.log("Step 12 - Click on Goal save button");
        createGoalPO.clickOnSaveGoalButton();

        Reporter.log("Step 13 - Verify goal type");
        Assert.assertEquals(goalDetailsPO.getGoalNameText(), (goalData.getGoalFilmName()), "Goal type is not matched");

        Reporter.log("Step 14 - Create a array list and store");
        List<String> myList = new ArrayList<>();
        myList.add(editGoalData.getNewScriptureReference() + " " + scripturePartialKnownChapters[0] + " ");

        Object[] expectedScriptures = Arrays.stream(myList.stream().map(String::trim).toArray()).sorted().toArray();
        String[] expectedScripturesStringArray = Arrays.copyOf(expectedScriptures, expectedScriptures.length, String[].class);

        Reporter.log("Step 15 - verify goal details");
        Assert.assertEquals(Arrays.stream(goalDetailsPO.getGoalDistributionMethodsText().split(",")).map(String::trim).toArray(String[]::new), GoalDistributionMethods.getGoalDistributionMethodsByName(editGoalData.getGoalDistributionMethods()), "Goal distribution method is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalDistributionMethodsPartnerText(), partnerShipAdminData.getOrganizationName(), "Goal distribution method partner is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalMethodologyText(), editGoalData.getMethodologyType() + " - " + editGoalData.getMethodologyValue(), "Goal methodology is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalCompletionDescriptionText(), (editGoalData.getGoalCompletionDescription()), "Goal completion description is not matched");

        Object[] actualScriptures = Arrays.stream(goalDetailsPO.getGoalScriptureReferenceNewTestamentText().split("\n")).sorted().toArray();
        String[] actualScripturesStringArray = Arrays.copyOf(actualScriptures, actualScriptures.length, String[].class);
        Assert.assertEquals(actualScripturesStringArray, expectedScripturesStringArray, "Goal new scriptureReference is not matched");

        Reporter.log("Step 16 - Refresh page and verify Goal details");
        selenium.refreshPage();
        selenium.hardWait(3);

        Assert.assertEquals(Arrays.stream(goalDetailsPO.getGoalDistributionMethodsText().split(",")).map(String::trim).toArray(String[]::new), GoalDistributionMethods.getGoalDistributionMethodsByName(editGoalData.getGoalDistributionMethods()), "Goal distribution method is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalDistributionMethodsPartnerText(), partnerShipAdminData.getOrganizationName(), "Goal distribution method partner is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalMethodologyText(), editGoalData.getMethodologyType() + " - " + editGoalData.getMethodologyValue(), "Goal methodology is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalCompletionDescriptionText(), (editGoalData.getGoalCompletionDescription()), "Goal completion description is not matched");
        Assert.assertEquals(actualScripturesStringArray, expectedScripturesStringArray, "Goal new scriptureReference is not matched");

        Reporter.log("Step 17 - Refresh page and verify Goal details");
        selenium.refreshPage();

        Reporter.log("Step 18 - Click on Goal scripture edit button");
        projectLanguageEngagementDetailsPO.clickEditGoalButton();

        Reporter.log("Step 19 - Click on Goal delete  button");
        createGoalPO.clickOnDeleteGoalButton();

        Reporter.log("Step 20 - Verify that deleted Goal is not present");
        Assert.assertFalse(projectLanguageEngagementDetailsPO.isGoalPresent(), "Goal is present");
    }

}





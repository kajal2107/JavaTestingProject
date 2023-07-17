package cord.projects.languageEngagement.goal;

import base.BaseTest;
import datafactory.GoalData;
import datafactory.PartnerData;
import datafactory.ProjectData;
import dataobjects.Goal;
import dataobjects.Partner;
import dataobjects.Project;
import dataobjects.User;
import enums.goal.*;
import funcLibs.project.PartnershipFunctions;
import funcLibs.project.ProjectFunctions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import pageobjects.cord.projects.languageEngagement.ProjectLanguageEngagementDetailsPO;
import pageobjects.cord.projects.languageEngagement.goal.CreateGoalPO;
import pageobjects.cord.projects.languageEngagement.goal.CreateGoalTypePO;
import pageobjects.cord.projects.languageEngagement.goal.GoalDetailsPO;
import utilities.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateGoalEthnoArtTests extends BaseTest {

    /*Test 1 : Verify that user can create ethno art goal full book successfully*/
    @Test
    public void verifyUserCanCreateEthnoArtGoalWithFullBookSuccessfully() throws InterruptedException {

        /*
         * We can change whole test process for specific user from here.
         * */
        User myUser = adminUser;

        CreateGoalPO createGoalPO = new CreateGoalPO(driver);
        GoalDetailsPO goalDetailsPO = new GoalDetailsPO(driver);
        CreateGoalTypePO createGoalTypePO = new CreateGoalTypePO(driver);
        Project data = new ProjectData().getCreateTranslationProjectData();
        String createdProjectName = data.getProjectName();
        Partner partnerShipAdminData = new PartnerData().getCreatePartnerData();
        ProjectLanguageEngagementDetailsPO projectLanguageEngagementDetailsPO = new ProjectLanguageEngagementDetailsPO(driver);

        Reporter.log("Step 1 - Create project and partnership");
        Goal goalEthnoArtData = new GoalData().getCreateEthnoArtGoalData();
        new ProjectFunctions(driver, selenium).createProjectAndGotoProjectDetailsPage(myUser, data);
        new PartnershipFunctions(driver, selenium).createProjectPartnership(partnerShipAdminData, createdProjectName, adminUser);

        selenium.back();

        Reporter.log("Step 2 - Create Language Engagement");
        new ProjectFunctions(driver, selenium).createLanguageEngagementAndGotoLanguageEngagementDetailsPage();

        Reporter.log("Step 3 - Click on create goal button and choose goal type");
        projectLanguageEngagementDetailsPO.clickCreateGoalButton();
        createGoalPO.selectGoalTypeByName(GoalType.EthnoArt.getGoalType());

        Reporter.log("Step 4 - Create a Selected goal");
        createGoalPO.enterGoalNameAndAddGoal(GoalType.EthnoArt, goalEthnoArtData);
        createGoalTypePO.clickOnGoalTypeSubmitButton();

        Reporter.log("Step 5 - Click on goal scripture reference dropdown and select old and new testament");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.ScriptureReference.getGoalDropdown());
        createGoalPO.selectGoalScriptureRefOldTestamentByName(goalEthnoArtData);

        Reporter.log("Step 6 - Click on goal distribution methods dropdown and choose distributionMethods");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.DistributionMethods.getGoalDropdown());
        createGoalPO.selectGoalDistributionMethodsByName(goalEthnoArtData.getGoalDistributionMethods());

        Reporter.log("Step 7 - Choose partners producing these distribution methods");
        createGoalPO.selectGoalDistributionMethodsByName(partnerShipAdminData.getOrganizationName());

        Reporter.log("Step 8 - Click on goal methodology dropdown and choose methodology");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.Methodology.getGoalDropdown());
        createGoalPO.selectGoalMethodologyByTypeAndValue(goalEthnoArtData.getMethodologyType(), goalEthnoArtData.getMethodologyValue());

        Reporter.log("Step 9 - Click on goal progress target dropdown and choose methodology");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.ProgressTarget.getGoalDropdown());
        createGoalPO.fillProgressTargetDetail(goalEthnoArtData);

        Reporter.log("Step 10 - Click on goal steps dropdown and deselect the steps");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.Steps.getGoalDropdown());
        createGoalPO.deSelectGoalStepsByNameMethodologyType(goalEthnoArtData.getMethodologyType(), goalEthnoArtData.getAllSteps());

        Reporter.log("Step 11 - Click on goal steps dropdown and select the steps");
        createGoalPO.selectGoalStepsByNameMethodologyType(goalEthnoArtData.getMethodologyType(), goalEthnoArtData.getSteps());

        Reporter.log("Step 12 - Click on goal Completion Description dropdown and add Completion Description");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.CompletionDescription.getGoalDropdown());
        createGoalPO.fillCompletionDescriptionDetail(goalEthnoArtData);
        createGoalPO.clickOnSaveGoalButton();

        Assert.assertEquals(projectLanguageEngagementDetailsPO.getGoalText(), (goalEthnoArtData.getGoalEthnoArtName()), "goal type is not matched");

        Reporter.log("Step 13 - Click on created goal");
        projectLanguageEngagementDetailsPO.clickOnGoalCard();

        Reporter.log("Step 14 - verify goal details");
        Assert.assertEquals(Arrays.stream(goalDetailsPO.getGoalDistributionMethodsText().split(",")).map(String::trim).toArray(String[]::new), GoalDistributionMethods.getGoalDistributionMethodsByName(goalEthnoArtData.getGoalDistributionMethods()), "Goal distribution method is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalDistributionMethodsPartnerText(), partnerShipAdminData.getOrganizationName(), "Goal distribution method partner is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalMethodologyText(), goalEthnoArtData.getMethodologyType() + " - " + goalEthnoArtData.getMethodologyValue(), "Goal methodology is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalCompletionDescriptionText(), (goalEthnoArtData.getGoalCompletionDescription()), "Goal completion description is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalScriptureReferenceOldTestamentText(), goalEthnoArtData.getOldScriptureReference(), "Goal old scriptureReference is not matched");

        Reporter.log("Step 15 - Refresh page and verify Goal details");
        selenium.refreshPage();
        Assert.assertEquals(Arrays.stream(goalDetailsPO.getGoalDistributionMethodsText().split(",")).map(String::trim).toArray(String[]::new), GoalDistributionMethods.getGoalDistributionMethodsByName(goalEthnoArtData.getGoalDistributionMethods()), "Goal distribution method is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalDistributionMethodsPartnerText(), partnerShipAdminData.getOrganizationName(), "Goal distribution method partner is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalMethodologyText(), goalEthnoArtData.getMethodologyType() + " - " + goalEthnoArtData.getMethodologyValue(), "Goal methodology is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalCompletionDescriptionText(), (goalEthnoArtData.getGoalCompletionDescription()), "Goal completion description is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalScriptureReferenceOldTestamentText(), goalEthnoArtData.getOldScriptureReference(), "Goal old scriptureReference is not matched");
    }


    /*Test 2 : Verify that user can create ethno art  Goal partial book known references from existing ethno art with chapters successfully*/
    @Test
    public void verifyUserCanCreateEthnoArtGoalPartialBookKnownReferencesFromExistingEthnoArtWithChaptersSuccessfully() throws InterruptedException {

        /*
         * We can change whole test process for specific user from here.
         * */
        User myUser = adminUser;

        CreateGoalPO createGoalPO = new CreateGoalPO(driver);
        GoalDetailsPO goalDetailsPO = new GoalDetailsPO(driver);
        Goal searchGoalData = new GoalData().getSearchGoalData();
        ProjectLanguageEngagementDetailsPO projectLanguageEngagementDetailsPO = new ProjectLanguageEngagementDetailsPO(driver);
        Project data = new ProjectData().getCreateTranslationProjectData();
        String createdProjectName = data.getProjectName();
        Partner partnerShipAdminData = new PartnerData().getCreatePartnerData();

        Reporter.log("Step 1 - Create project and partnership");
        Goal goalEthnoArtData = new GoalData().getCreateEthnoArtGoalData();
        new ProjectFunctions(driver, selenium).createProjectAndGotoProjectDetailsPage(myUser, data);
        new PartnershipFunctions(driver, selenium).createProjectPartnership(partnerShipAdminData, createdProjectName, adminUser);

        selenium.back();

        Reporter.log("Step 2 - Create Language Engagement");
        new ProjectFunctions(driver, selenium).createLanguageEngagementAndGotoLanguageEngagementDetailsPage();

        Reporter.log("Step 3 - Click on create Goal button and choose Goal type");
        projectLanguageEngagementDetailsPO.clickCreateGoalButton();
        createGoalPO.selectGoalTypeByName(GoalType.EthnoArt.getGoalType());

        Reporter.log("Step 4 - Search a Created Goal");
        createGoalPO.searchGoalNameAndAddGoal(GoalType.EthnoArt, searchGoalData);

        Reporter.log("Step 5 - Create a Selected goal");
        createGoalPO.enterGoalNameAndAddGoal(GoalType.EthnoArt, searchGoalData);

        Reporter.log("Step 6 - Click on Goal scripture reference dropdown and select new testament");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.ScriptureReference.getGoalDropdown());
        String[] scripturePartialKnownChapters = Constants.scripturePartialKnownChapter;
        createGoalPO.selectGoalScriptureRefNewTestamentByName(goalEthnoArtData);

        Reporter.log("Step 7 - Select partial known radio button with different chapters");
        createGoalPO.selectGoalPartialKnownRadioButtonWithChapters(GoalBookRadioButton.partialKnown.getGoalBookRadioButton(), scripturePartialKnownChapters);

        Reporter.log("Step 8 - Click on goal distribution methods dropdown and choose distributionMethods");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.DistributionMethods.getGoalDropdown());
        createGoalPO.selectGoalDistributionMethodsByName(goalEthnoArtData.getGoalDistributionMethods());

        Reporter.log("Step 9 - Choose partners producing these distribution methods");
        createGoalPO.selectGoalDistributionMethodsByName(partnerShipAdminData.getOrganizationName());

        Reporter.log("Step 10 - Click on Goal methodology dropdown and choose methodology");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.Methodology.getGoalDropdown());
        createGoalPO.selectGoalMethodologyByTypeAndValue(goalEthnoArtData.getMethodologyType(), goalEthnoArtData.getMethodologyValue());

        Reporter.log("Step 11 - Click on goal progress target dropdown and choose methodology");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.ProgressTarget.getGoalDropdown());
        createGoalPO.fillProgressTargetDetail(goalEthnoArtData);

        Reporter.log("Step 12 - Click on goal steps dropdown and deselect the steps");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.Steps.getGoalDropdown());

        createGoalPO.selectGoalStepsByNameMethodologyType(goalEthnoArtData.getMethodologyType(), goalEthnoArtData.getAllSteps()); //Todo bug(#1021)

        Reporter.log("Step 13 - Click on goal steps dropdown and select the steps");
        createGoalPO.selectGoalStepsByNameMethodologyType(goalEthnoArtData.getMethodologyType(), goalEthnoArtData.getSteps());

        Reporter.log("Step 14 - Click on Goal Completion Description dropdown and add Completion Description");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.CompletionDescription.getGoalDropdown());
        createGoalPO.fillCompletionDescriptionDetail(goalEthnoArtData);
        createGoalPO.clickOnSaveGoalButton();

        Reporter.log("Step 15 - Verify Goal type");
        Assert.assertEquals(projectLanguageEngagementDetailsPO.getGoalText(), (searchGoalData.getGoalEthnoArtName()), "Goal type is not matched");

        Reporter.log("Step 16 - Click on created Goal");
        projectLanguageEngagementDetailsPO.clickOnGoalCard();

        Reporter.log("Step 17 - Create a array list and store");
        List<String> myList = new ArrayList<>();
        myList.add(goalEthnoArtData.getNewScriptureReference() + " " + scripturePartialKnownChapters[0] + " ");

        Object[] expectedScriptures = Arrays.stream(myList.stream().map(String::trim).toArray()).sorted().toArray();
        String[] expectedScripturesStringArray = Arrays.copyOf(expectedScriptures, expectedScriptures.length, String[].class);

        Reporter.log("Step 18 - verify goal details");
        Object[] actualScriptures = Arrays.stream(goalDetailsPO.getGoalScriptureReferenceNewTestamentText().split("\n")).sorted().toArray();
        String[] actualScripturesStringArray = Arrays.copyOf(actualScriptures, actualScriptures.length, String[].class);

        Assert.assertEquals(Arrays.stream(goalDetailsPO.getGoalDistributionMethodsText().split(",")).map(String::trim).toArray(String[]::new), GoalDistributionMethods.getGoalDistributionMethodsByName(goalEthnoArtData.getGoalDistributionMethods()), "Goal distribution method is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalDistributionMethodsPartnerText(), partnerShipAdminData.getOrganizationName(), "Goal distribution method partner is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalMethodologyText(), goalEthnoArtData.getMethodologyType() + " - " + goalEthnoArtData.getMethodologyValue(), "Goal methodology is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalCompletionDescriptionText(), (goalEthnoArtData.getGoalCompletionDescription()), "Goal completion description is not matched");
        Assert.assertEquals(actualScripturesStringArray, expectedScripturesStringArray, "Goal new scriptureReference is not matched");

        Reporter.log("Step 19 - Refresh page and verify Goal details");
        selenium.refreshPage();
        Assert.assertEquals(Arrays.stream(goalDetailsPO.getGoalDistributionMethodsText().split(",")).map(String::trim).toArray(String[]::new), GoalDistributionMethods.getGoalDistributionMethodsByName(goalEthnoArtData.getGoalDistributionMethods()), "Goal mediums is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalDistributionMethodsPartnerText(), partnerShipAdminData.getOrganizationName(), "Goal distribution method partner is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalMethodologyText(), goalEthnoArtData.getMethodologyType() + " - " + goalEthnoArtData.getMethodologyValue(), "Goal methodology is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalCompletionDescriptionText(), (goalEthnoArtData.getGoalCompletionDescription()), "Goal completion description is not matched");
        Assert.assertEquals(actualScripturesStringArray, expectedScripturesStringArray, "Goal new scriptureReference is not matched");
    }

    /*Test 3 : Verify that user can create ethnoArt Goal partial book known references from existing ethno art with different chapters successfully*/
    @Test
    public void verifyUserCanCreateEthnoArtGoalPartialBookKnownReferencesFromExistingEthnoArtWithDifferentChaptersSuccessfully() throws InterruptedException {

        /*
         * We can change whole test process for specific user from here.
         * */

        User myUser = adminUser;

        CreateGoalPO createGoalPO = new CreateGoalPO(driver);
        GoalDetailsPO goalDetailsPO = new GoalDetailsPO(driver);
        Goal searchGoalData = new GoalData().getSearchGoalData();
        ProjectLanguageEngagementDetailsPO projectLanguageEngagementDetailsPO = new ProjectLanguageEngagementDetailsPO(driver);
        Project data = new ProjectData().getCreateTranslationProjectData();
        String createdProjectName = data.getProjectName();
        Partner partnerShipAdminData = new PartnerData().getCreatePartnerData();

        Reporter.log("Step 1 - Create project and partnership");
        Goal goalEthnoArtData = new GoalData().getCreateEthnoArtGoalData();
        new ProjectFunctions(driver, selenium).createProjectAndGotoProjectDetailsPage(myUser, data);
        new PartnershipFunctions(driver, selenium).createProjectPartnership(partnerShipAdminData, createdProjectName, adminUser);

        selenium.back();

        Reporter.log("Step 2 - Create Language Engagement");
        new ProjectFunctions(driver, selenium).createLanguageEngagementAndGotoLanguageEngagementDetailsPage();

        Reporter.log("Step 3 - Click on create Goal button and choose Goal type");
        projectLanguageEngagementDetailsPO.clickCreateGoalButton();
        createGoalPO.selectGoalTypeByName(GoalType.EthnoArt.getGoalType());

        Reporter.log("Step 4 - Search a Created Goal");
        createGoalPO.searchGoalNameAndAddGoal(GoalType.EthnoArt, searchGoalData);

        Reporter.log("Step 6 - Click on Goal scripture reference dropdown and select new testament");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.ScriptureReference.getGoalDropdown());
        String[] scripturePartialKnownChapters = Constants.scripturePartialKnownGenesisChapter;
        createGoalPO.selectGoalScriptureRefBookByName();

        Reporter.log("Step 7 - Select partial known radio button with different chapters");
        createGoalPO.selectGoalPartialKnownRadioButtonWithChapters(GoalBookRadioButton.partialKnown.getGoalBookRadioButton(), scripturePartialKnownChapters);

        Reporter.log("Step 8 - Click on goal distribution methods dropdown and choose distributionMethods");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.DistributionMethods.getGoalDropdown());
        createGoalPO.selectGoalDistributionMethodsByName(goalEthnoArtData.getGoalDistributionMethods());

        Reporter.log("Step 9 - Choose partners producing these distribution methods");
        createGoalPO.selectGoalDistributionMethodsByName(partnerShipAdminData.getOrganizationName());

        Reporter.log("Step 10 - Click on Goal methodology dropdown and choose methodology");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.Methodology.getGoalDropdown());
        createGoalPO.selectGoalMethodologyByTypeAndValue(goalEthnoArtData.getMethodologyType(), goalEthnoArtData.getMethodologyValue());

        Reporter.log("Step 11 - Click on goal progress target dropdown and choose methodology");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.ProgressTarget.getGoalDropdown());
        createGoalPO.fillProgressTargetDetail(goalEthnoArtData);

        Reporter.log("Step 12 - Click on goal steps dropdown and deselect the steps");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.Steps.getGoalDropdown());
        createGoalPO.selectGoalStepsByNameMethodologyType(goalEthnoArtData.getMethodologyType(), goalEthnoArtData.getAllSteps()); //Todo bug(#1021)

        Reporter.log("Step 13 - Click on goal steps dropdown and select the steps");
        createGoalPO.selectGoalStepsByNameMethodologyType(goalEthnoArtData.getMethodologyType(), goalEthnoArtData.getSteps());

        Reporter.log("Step 14 - Click on Goal Completion Description dropdown and add Completion Description");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.CompletionDescription.getGoalDropdown());
        createGoalPO.fillCompletionDescriptionDetail(goalEthnoArtData);
        createGoalPO.clickOnSaveGoalButton();

        Assert.assertEquals(projectLanguageEngagementDetailsPO.getGoalText(), (searchGoalData.getGoalEthnoArtName()), "goal type is not matched");

        Reporter.log("Step 12 - Click on created goal");
        projectLanguageEngagementDetailsPO.clickOnGoalCard();

        Reporter.log("Step 15 - Create a array list and store");
        List<String> myList = new ArrayList<>();
        myList.add(Constants.scriptureReferenceBook + " " + scripturePartialKnownChapters[0] + " ");
        myList.add(Constants.scriptureReferenceBook + " " + scripturePartialKnownChapters[1] + " ");
        myList.add(Constants.scriptureReferenceBook + " " + scripturePartialKnownChapters[2] + " ");

        Object[] expectedScriptures = Arrays.stream(myList.stream().map(String::trim).toArray()).sorted().toArray();
        String[] expectedScripturesStringArray = Arrays.copyOf(expectedScriptures, expectedScriptures.length, String[].class);

        Reporter.log("Step 16 - verify goal details");
        Object[] actualScriptures = Arrays.stream(goalDetailsPO.getGoalScriptureReferenceNewTestamentText().split("\n")).sorted().toArray();
        String[] actualScripturesStringArray = Arrays.copyOf(actualScriptures, actualScriptures.length, String[].class);

        Assert.assertEquals(Arrays.stream(goalDetailsPO.getGoalDistributionMethodsText().split(",")).map(String::trim).toArray(String[]::new), GoalDistributionMethods.getGoalDistributionMethodsByName(goalEthnoArtData.getGoalDistributionMethods()), "Goal distribution method is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalDistributionMethodsPartnerText(), partnerShipAdminData.getOrganizationName(), "Goal distribution method partner is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalMethodologyText(), goalEthnoArtData.getMethodologyType() + " - " + goalEthnoArtData.getMethodologyValue(), "Goal methodology is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalCompletionDescriptionText(), (goalEthnoArtData.getGoalCompletionDescription()), "Goal completion description is not matched");
        Assert.assertEquals(actualScripturesStringArray, expectedScripturesStringArray, "Goal new scriptureReference is not matched");

        Reporter.log("Step 19 - Refresh page and verify Goal details");
        selenium.refreshPage();
        Assert.assertEquals(Arrays.stream(goalDetailsPO.getGoalDistributionMethodsText().split(",")).map(String::trim).toArray(String[]::new), GoalDistributionMethods.getGoalDistributionMethodsByName(goalEthnoArtData.getGoalDistributionMethods()), "Goal mediums is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalDistributionMethodsPartnerText(), partnerShipAdminData.getOrganizationName(), "Goal distribution method partner is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalMethodologyText(), goalEthnoArtData.getMethodologyType() + " - " + goalEthnoArtData.getMethodologyValue(), "Goal methodology is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalCompletionDescriptionText(), (goalEthnoArtData.getGoalCompletionDescription()), "Goal completion description is not matched");
        Assert.assertEquals(actualScripturesStringArray, expectedScripturesStringArray, "Goal new scriptureReference is not matched");
    }

}






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
import pageobjects.cord.projects.languageEngagement.goal.GoalDetailsPO;
import utilities.Constants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateGoalScriptureTests extends BaseTest {

    /*Test 1 : Verify that user can create scripture Goal successfully*/
    @Test
    public void verifyUserCanCreateScriptureGoalWithFullBookSSuccessfully() throws InterruptedException {

        /*
         * We can change whole test process for specific user from here.
         * */
        User myUser = adminUser;

        GoalDetailsPO goalDetailsPO = new GoalDetailsPO(driver);
        CreateGoalPO createGoalPO = new CreateGoalPO(driver);
        ProjectLanguageEngagementDetailsPO projectLanguageEngagementDetailsPO = new ProjectLanguageEngagementDetailsPO(driver);
        Project data = new ProjectData().getCreateTranslationProjectData();
        String createdProjectName = data.getProjectName();
        Partner partnerShipAdminData = new PartnerData().getCreatePartnerData();

        Reporter.log("Step 1 - Create project and partnership");
        Goal goalData = new GoalData().getCreateGoalData();
        new ProjectFunctions(driver, selenium).createProjectAndGotoProjectDetailsPage(myUser, data);
        new PartnershipFunctions(driver, selenium).createProjectPartnership(partnerShipAdminData, createdProjectName, adminUser);

        selenium.back();

        Reporter.log("Step 2 - Create Language Engagement");
        new ProjectFunctions(driver, selenium).createLanguageEngagementAndGotoLanguageEngagementDetailsPage();

        Reporter.log("Step 3 - Click on create Goal button and choose Goal");
        projectLanguageEngagementDetailsPO.clickCreateGoalButton();
        createGoalPO.selectGoalTypeByName(GoalType.Scripture.getGoalType());

        Reporter.log("Step 4 - Click on Goal scripture reference dropdown and select old and new testament");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.ScriptureReference.getGoalDropdown());
        createGoalPO.selectGoalScriptureRefOldTestamentByName(goalData);

        Reporter.log("Step 6 - Click on goal distribution methods dropdown and choose distributionMethods");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.DistributionMethods.getGoalDropdown());
        createGoalPO.selectGoalDistributionMethodsByName(goalData.getGoalDistributionMethods());

        Reporter.log("Step 6 - Choose partners producing these distribution methods");
        createGoalPO.selectGoalDistributionMethodsByName(partnerShipAdminData.getOrganizationName());

        Reporter.log("Step 7 - Click on Goal methodology dropdown and choose methodology");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.Methodology.getGoalDropdown());
        createGoalPO.selectGoalMethodologyByTypeAndValue(goalData.getMethodologyType(), goalData.getMethodologyValue());

        Reporter.log("Step 8 - Click on goal steps dropdown and deselect the steps");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.Steps.getGoalDropdown());
        createGoalPO.deSelectGoalStepsByNameMethodologyType(goalData.getMethodologyType(), goalData.getAllSteps());

        Reporter.log("Step 9 - Click on goal steps dropdown and select the steps");
        createGoalPO.selectGoalStepsByNameMethodologyType(goalData.getMethodologyType(), goalData.getSteps());

        Reporter.log("Step 10 - Click on Goal Completion Description dropdown and add Completion Description");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.CompletionDescription.getGoalDropdown());
        createGoalPO.fillCompletionDescriptionDetail(goalData);

        Reporter.log("Step 11 - Click on Goal save button");
        createGoalPO.clickOnSaveGoalButton();
        Assert.assertEquals(projectLanguageEngagementDetailsPO.getGoalText(), goalData.getOldScriptureReference(), "Goal type is not matched");

        Reporter.log("Step 12 - Click on created Goal");
        projectLanguageEngagementDetailsPO.clickOnGoalCard();

        Reporter.log("Step 13 - verify goal details");
        Assert.assertEquals(Arrays.stream(goalDetailsPO.getGoalDistributionMethodsText().split(",")).map(String::trim).toArray(String[]::new), GoalDistributionMethods.getGoalDistributionMethodsByName(goalData.getGoalDistributionMethods()), "Goal distribution method is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalDistributionMethodsPartnerText(), partnerShipAdminData.getOrganizationName(), "Goal distribution method partner is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalMethodologyText(), goalData.getMethodologyType() + " - " + goalData.getMethodologyValue(), "Goal methodology is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalCompletionDescriptionText(), (goalData.getGoalCompletionDescription()), "Goal completion description is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalScriptureReferenceOldTestamentText(), goalData.getOldScriptureReference(), "Goal old scriptureReference is not matched");

        Reporter.log("Step 14 - Refresh page and verify Goal details");
        selenium.refreshPage();
        Assert.assertEquals(Arrays.stream(goalDetailsPO.getGoalDistributionMethodsText().split(",")).map(String::trim).toArray(String[]::new), GoalDistributionMethods.getGoalDistributionMethodsByName(goalData.getGoalDistributionMethods()), "Goal distribution method is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalDistributionMethodsPartnerText(), partnerShipAdminData.getOrganizationName(), "Goal distribution method partner is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalMethodologyText(), goalData.getMethodologyType() + " - " + goalData.getMethodologyValue(), "Goal methodology is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalCompletionDescriptionText(), (goalData.getGoalCompletionDescription()), "Goal completion description is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalScriptureReferenceOldTestamentText(), goalData.getOldScriptureReference(), "Goal old scriptureReference is not matched");

    }

    /*Test 2 : Verify that user can create scripture Goal partial book known references with chapter successfully*/
    @Test
    public void verifyUserCanCreateScriptureGoalPartialBookKnownReferencesWithChaptersSuccessfully() throws InterruptedException {

        /*
         * We can change whole test process for specific user from here.
         * */
        User myUser = adminUser;
        CreateGoalPO createGoalPO = new CreateGoalPO(driver);
        GoalDetailsPO goalDetailsPO = new GoalDetailsPO(driver);
        Project data = new ProjectData().getCreateTranslationProjectData();
        Goal goalData = new GoalData().getCreateGoalData();
        String createdProjectName = data.getProjectName();
        Partner partnerShipAdminData = new PartnerData().getCreatePartnerData();
        ProjectLanguageEngagementDetailsPO projectLanguageEngagementDetailsPO = new ProjectLanguageEngagementDetailsPO(driver);

        Reporter.log("Step 1 - Create project and partnership");
        new ProjectFunctions(driver, selenium).createProjectAndGotoProjectDetailsPage(myUser, data);
        new PartnershipFunctions(driver, selenium).createProjectPartnership(partnerShipAdminData, createdProjectName, adminUser);

        selenium.back();

        Reporter.log("Step 2 - Create Language Engagement");
        new ProjectFunctions(driver, selenium).createLanguageEngagementAndGotoLanguageEngagementDetailsPage();

        Reporter.log("Step 3 - Click on create Goal button and choose Goal");
        projectLanguageEngagementDetailsPO.clickCreateGoalButton();
        createGoalPO.selectGoalTypeByName(GoalType.Scripture.getGoalType());

        Reporter.log("Step 4 - Click on Goal scripture reference dropdown and select old and new testament chapters");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.ScriptureReference.getGoalDropdown());

        String[] scripturePartialKnownChapters = Constants.scripturePartialKnownChapter;
        createGoalPO.selectGoalScriptureRefNewTestamentByName(goalData);

        Reporter.log("Step 5 - Select partial known radio button with different chapters");
        createGoalPO.selectGoalPartialKnownRadioButtonWithChapters(GoalBookRadioButton.partialKnown.getGoalBookRadioButton(), scripturePartialKnownChapters);

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

        Object[] allStepObject = Arrays.stream(goalData.getAllSteps()).toArray();
        String[] allSteps = Arrays.copyOf(allStepObject, allStepObject.length, String[].class);
        createGoalPO.deSelectGoalStepsByNameMethodologyType(goalData.getMethodologyType(), allSteps);

        Reporter.log("Step 10 - Click on goal steps dropdown and select the steps");
        Object[] object = Arrays.stream(goalData.getSteps()).toArray();
        String[] steps = Arrays.copyOf(object, object.length, String[].class);
        createGoalPO.selectGoalStepsByNameMethodologyType(goalData.getMethodologyType(), steps);

        Reporter.log("Step 11 - Click on Goal Completion Description dropdown and add Completion Description");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.CompletionDescription.getGoalDropdown());
        createGoalPO.fillCompletionDescriptionDetail(goalData);

        Reporter.log("Step 12 - Click on Goal save button");
        createGoalPO.clickOnSaveGoalButton();

        Reporter.log("Step 13 - Create a array list and store");
        List<String> myList = new ArrayList<>();
        myList.add(goalData.getNewScriptureReference() + " " + scripturePartialKnownChapters[0] + " ");

        Object[] expectedScriptures = Arrays.stream(myList.stream().map(String::trim).toArray()).sorted().toArray();
        String[] expectedScripturesStringArray = Arrays.copyOf(expectedScriptures, expectedScriptures.length, String[].class);

        Reporter.log("Step 14 - Verify Goal type");
        Assert.assertEquals(projectLanguageEngagementDetailsPO.getGoalTypeText(), GoalType.Scripture.getGoalType(), "Goal type is not matched");

        Reporter.log("Step 15 - Click on created Goal");
        projectLanguageEngagementDetailsPO.clickOnGoalCard();

        Reporter.log("Step 16 - verify goal details");
        Object[] actualScriptures = Arrays.stream(goalDetailsPO.getGoalScriptureReferenceNewTestamentText().split("\n")).sorted().toArray();
        String[] actualScripturesStringArray = Arrays.copyOf(actualScriptures, actualScriptures.length, String[].class);

        Assert.assertEquals(Arrays.stream(goalDetailsPO.getGoalDistributionMethodsText().split(",")).map(String::trim).toArray(String[]::new), GoalDistributionMethods.getGoalDistributionMethodsByName(goalData.getGoalDistributionMethods()), "Goal distribution method is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalDistributionMethodsPartnerText(), partnerShipAdminData.getOrganizationName(), "Goal distribution method partner is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalMethodologyText(), goalData.getMethodologyType() + " - " + goalData.getMethodologyValue(), "Goal methodology is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalCompletionDescriptionText(), (goalData.getGoalCompletionDescription()), "Goal completion description is not matched");
        Assert.assertEquals(actualScripturesStringArray, expectedScripturesStringArray, "Goal new scriptureReference is not matched");

        Reporter.log("Step 17 - Refresh page and verify Goal details");
        selenium.refreshPage();
        Assert.assertEquals(Arrays.stream(goalDetailsPO.getGoalDistributionMethodsText().split(",")).map(String::trim).toArray(String[]::new), GoalDistributionMethods.getGoalDistributionMethodsByName(goalData.getGoalDistributionMethods()), "Goal distribution method is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalDistributionMethodsPartnerText(), partnerShipAdminData.getOrganizationName(), "Goal distribution method partner is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalMethodologyText(), goalData.getMethodologyType() + " - " + goalData.getMethodologyValue(), "Goal methodology is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalCompletionDescriptionText(), (goalData.getGoalCompletionDescription()), "Goal completion description is not matched");
        Assert.assertEquals(actualScripturesStringArray, expectedScripturesStringArray, "Goal new scriptureReference is not matched");

    }

    /*Test 3 : Verify that user can create scripture Goal partial book known references with different chapter successfully*/
    @Test
    public void verifyUserCanCreateScriptureGoalPartialBookKnownReferencesWithDifferentChaptersSuccessfully() throws InterruptedException {

        /*
         * We can change whole test process for specific user from here.
         * */
        User myUser = adminUser;
        CreateGoalPO createGoalPO = new CreateGoalPO(driver);
        GoalDetailsPO goalDetailsPO = new GoalDetailsPO(driver);
        Project data = new ProjectData().getCreateTranslationProjectData();
        Goal goalData = new GoalData().getCreateGoalData();
        String createdProjectName = data.getProjectName();
        Partner partnerShipAdminData = new PartnerData().getCreatePartnerData();
        ProjectLanguageEngagementDetailsPO projectLanguageEngagementDetailsPO = new ProjectLanguageEngagementDetailsPO(driver);

        Reporter.log("Step 1 - Create project and partnership");
        new ProjectFunctions(driver, selenium).createProjectAndGotoProjectDetailsPage(myUser, data);
        new PartnershipFunctions(driver, selenium).createProjectPartnership(partnerShipAdminData, createdProjectName, adminUser);

        selenium.back();

        Reporter.log("Step 2 - Create Language Engagement");
        new ProjectFunctions(driver, selenium).createLanguageEngagementAndGotoLanguageEngagementDetailsPage();

        Reporter.log("Step 3 - Click on create Goal button and choose Goal");
        projectLanguageEngagementDetailsPO.clickCreateGoalButton();
        createGoalPO.selectGoalTypeByName(GoalType.Scripture.getGoalType());

        Reporter.log("Step 4 - Click on Goal scripture reference dropdown and select old and new testament chapters");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.ScriptureReference.getGoalDropdown());
        String[] scripturePartialKnownChapters = Constants.scripturePartialKnownGenesisChapter;
        createGoalPO.selectGoalScriptureRefBookByName();

        Reporter.log("Step 5 - Select partial known radio button with different chapters");
        createGoalPO.selectGoalPartialKnownRadioButtonWithChapters(GoalBookRadioButton.partialKnown.getGoalBookRadioButton(), scripturePartialKnownChapters);

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

        Object[] allStepObject = Arrays.stream(goalData.getAllSteps()).toArray();
        String[] allSteps = Arrays.copyOf(allStepObject, allStepObject.length, String[].class);
        createGoalPO.deSelectGoalStepsByNameMethodologyType(goalData.getMethodologyType(), allSteps);

        Reporter.log("Step 10 - Click on goal steps dropdown and select the steps");
        Object[] object = Arrays.stream(goalData.getSteps()).toArray();
        String[] steps = Arrays.copyOf(object, object.length, String[].class);
        createGoalPO.selectGoalStepsByNameMethodologyType(goalData.getMethodologyType(), steps);

        Reporter.log("Step 11 - Click on Goal Completion Description dropdown and add Completion Description");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.CompletionDescription.getGoalDropdown());
        createGoalPO.fillCompletionDescriptionDetail(goalData);

        Reporter.log("Step 12 - Click on Goal save button");
        createGoalPO.clickOnSaveGoalButton();

        Reporter.log("Step 13 - Create a array list and store");
        List<String> myList = new ArrayList<>();
        myList.add(Constants.scriptureReferenceBook + " " + scripturePartialKnownChapters[0] + " ");
        myList.add(Constants.scriptureReferenceBook + " " + scripturePartialKnownChapters[1] + " ");
        myList.add(Constants.scriptureReferenceBook + " " + scripturePartialKnownChapters[2] + " ");

        Object[] expectedScriptures = Arrays.stream(myList.stream().map(String::trim).toArray()).sorted().toArray();
        String[] expectedScripturesStringArray = Arrays.copyOf(expectedScriptures, expectedScriptures.length, String[].class);

        Reporter.log("Step 14 - Verify Goal type");
        Assert.assertEquals(projectLanguageEngagementDetailsPO.getGoalTypeText(), GoalType.Scripture.getGoalType(), "Goal type is not matched");

        Reporter.log("Step 15 - Click on created Goal");
        projectLanguageEngagementDetailsPO.clickOnGoalCard();

        Reporter.log("Step 16 - verify goal details");
        Object[] actualScriptures = Arrays.stream(goalDetailsPO.getGoalScriptureReferenceNewTestamentText().split("\n")).sorted().toArray();
        String[] actualScripturesStringArray = Arrays.copyOf(actualScriptures, actualScriptures.length, String[].class);

        Assert.assertEquals(Arrays.stream(goalDetailsPO.getGoalDistributionMethodsText().split(",")).map(String::trim).toArray(String[]::new), GoalDistributionMethods.getGoalDistributionMethodsByName(goalData.getGoalDistributionMethods()), "Goal distribution method is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalDistributionMethodsPartnerText(), partnerShipAdminData.getOrganizationName(), "Goal distribution method partner is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalMethodologyText(), goalData.getMethodologyType() + " - " + goalData.getMethodologyValue(), "Goal methodology is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalCompletionDescriptionText(), (goalData.getGoalCompletionDescription()), "Goal completion description is not matched");
        Assert.assertEquals(actualScripturesStringArray, expectedScripturesStringArray, "Goal new scriptureReference is not matched");

        Reporter.log("Step 17 - Refresh page and verify Goal details");
        selenium.refreshPage();
        Assert.assertEquals(Arrays.stream(goalDetailsPO.getGoalDistributionMethodsText().split(",")).map(String::trim).toArray(String[]::new), GoalDistributionMethods.getGoalDistributionMethodsByName(goalData.getGoalDistributionMethods()), "Goal distribution method is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalDistributionMethodsPartnerText(), partnerShipAdminData.getOrganizationName(), "Goal distribution method partner is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalMethodologyText(), goalData.getMethodologyType() + " - " + goalData.getMethodologyValue(), "Goal methodology is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalCompletionDescriptionText(), (goalData.getGoalCompletionDescription()), "Goal completion description is not matched");
        Assert.assertEquals(actualScripturesStringArray, expectedScripturesStringArray, "Goal new scriptureReference is not matched");

    }

    /*Test 4 : Verify that user can create scripture Goal partial book Unknown references with total verses successfully*/
    @Test
    public void verifyUserCanCreateScriptureGoalPartialBookUnKnownReferencesWithTotalVersesSuccessfully() throws InterruptedException {

        /*
         * We can change whole test process for specific user from here.
         * */
        User myUser = adminUser;

        CreateGoalPO createGoalPO = new CreateGoalPO(driver);
        GoalDetailsPO goalDetailsPO = new GoalDetailsPO(driver);
        Project data = new ProjectData().getCreateTranslationProjectData();
        Goal goalData = new GoalData().getCreateGoalData();
        String createdProjectName = data.getProjectName();
        Partner partnerShipAdminData = new PartnerData().getCreatePartnerData();
        ProjectLanguageEngagementDetailsPO projectLanguageEngagementDetailsPO = new ProjectLanguageEngagementDetailsPO(driver);

        Reporter.log("Step 1 - Create project and partnership");
        new ProjectFunctions(driver, selenium).createProjectAndGotoProjectDetailsPage(myUser, data);
        new PartnershipFunctions(driver, selenium).createProjectPartnership(partnerShipAdminData, createdProjectName, adminUser);

        selenium.back();

        Reporter.log("Step 2 - Create Language Engagement");
        new ProjectFunctions(driver, selenium).createLanguageEngagementAndGotoLanguageEngagementDetailsPage();

        Reporter.log("Step 3 - Click on create Goal button and choose Goal");
        projectLanguageEngagementDetailsPO.clickCreateGoalButton();
        createGoalPO.selectGoalTypeByName(GoalType.Scripture.getGoalType());

        Reporter.log("Step 4 - Click on Goal scripture reference dropdown and select old and new testament chapters");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.ScriptureReference.getGoalDropdown());
        createGoalPO.selectGoalScriptureRefNewTestamentByName(goalData);

        Reporter.log("Step 5 - Select partial Unknown radio button with verses");
        createGoalPO.selectGoalPartialUnKnownRadioButtonWithTotalVerses(GoalBookRadioButton.partialUnknown.getGoalBookRadioButton(), goalData.getGoalVerses());
        String totalVersesText = createGoalPO.getGoalTotalVersesText();
        String totalVersesDigit = totalVersesText.substring(totalVersesText.indexOf("has")).replaceAll("[^\\d]", " ").trim().replaceAll(" +", " ");

        Reporter.log("Step 6 - Click on Goal distribution methods dropdown and choose distribution methods");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.DistributionMethods.getGoalDropdown());
        createGoalPO.selectGoalDistributionMethodsByName(goalData.getGoalDistributionMethods());

        Reporter.log("Step 7 - Choose partners producing these distribution methods");
        createGoalPO.selectGoalDistributionMethodsByName(partnerShipAdminData.getOrganizationName());

        Reporter.log("Step 8 - Click on Goal methodology dropdown and choose methodology");
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

        Reporter.log("Step 11 - Click on Goal Completion Description dropdown and add Completion Description");
        createGoalPO.clickOnGoalTypeDropdownByName(GoalDropdown.CompletionDescription.getGoalDropdown());
        createGoalPO.fillCompletionDescriptionDetail(goalData);

        Reporter.log("Step 12 - Click on Goal save button");
        createGoalPO.clickOnSaveGoalButton();

        Reporter.log("Step 13 - Verify Goal type");
        Assert.assertEquals(projectLanguageEngagementDetailsPO.getGoalTypeText(), GoalType.Scripture.getGoalType(), "Goal type is not matched");

        Reporter.log("Step 14 - Click on created Goal");
        projectLanguageEngagementDetailsPO.clickOnGoalCard();

        Reporter.log("Step 15 - Verify goal details");
        String scriptureGoalText = goalDetailsPO.getGoalNameText().split("\\(")[0];
        Assert.assertEquals(scriptureGoalText.trim(), goalData.getNewScriptureReference(), "Goal type is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalNameText(), goalData.getNewScriptureReference() + " (" + goalData.getGoalVerses() + " / " + totalVersesDigit + " verses)", "Goal total verses is not match");
        Assert.assertEquals(Arrays.stream(goalDetailsPO.getGoalDistributionMethodsText().split(",")).map(String::trim).toArray(String[]::new), GoalDistributionMethods.getGoalDistributionMethodsByName(goalData.getGoalDistributionMethods()), "Goal distribution method is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalDistributionMethodsPartnerText(), partnerShipAdminData.getOrganizationName(), "Goal distribution method partner is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalMethodologyText(), goalData.getMethodologyType() + " - " + goalData.getMethodologyValue(), "Goal methodology is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalCompletionDescriptionText(), (goalData.getGoalCompletionDescription()), "Goal completion description is not matched");

        Reporter.log("Step 16 - Refresh page and verify Goal details");
        selenium.refreshPage();
        Assert.assertEquals(scriptureGoalText.trim(), goalData.getNewScriptureReference(), "Goal type is not matched");
        selenium.hardWait(3);
        Assert.assertEquals(goalDetailsPO.getGoalNameText(), goalData.getNewScriptureReference() + " (" + goalData.getGoalVerses() + " / " + totalVersesDigit + " verses)", "Goal total verses is not match");
        Assert.assertEquals(Arrays.stream(goalDetailsPO.getGoalDistributionMethodsText().split(",")).map(String::trim).toArray(String[]::new), GoalDistributionMethods.getGoalDistributionMethodsByName(goalData.getGoalDistributionMethods()), "Goal distribution method is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalDistributionMethodsPartnerText(), partnerShipAdminData.getOrganizationName(), "Goal distribution method partner is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalMethodologyText(), goalData.getMethodologyType() + " - " + goalData.getMethodologyValue(), "Goal methodology is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalCompletionDescriptionText(), (goalData.getGoalCompletionDescription()), "Goal completion description is not matched");

    }
}

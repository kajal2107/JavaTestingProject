package funcLibs.project;

import dataobjects.Internship;
import dataobjects.Language;
import enums.goal.GoalMethodologySkip;
import enums.menu.CreateItemTypes;
import enums.users.UserRole;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import pageobjects.cord.common.HeaderPO;
import pageobjects.cord.common.LeftNavPO;
import pageobjects.cord.languages.CreateUpdateLanguagePO;
import pageobjects.cord.projects.ProjectDetailsCreateLanguageEngagementPO;
import pageobjects.cord.projects.ProjectDetailsPO;
import pageobjects.cord.projects.ProjectsListingPO;
import pageobjects.cord.projects.intershipEngagement.*;
import pageobjects.cord.projects.languageEngagement.ProjectEngagementDetailsUpdateParaTextRegisterIdPO;
import pageobjects.cord.projects.languageEngagement.ProjectLanguageEngagementDetailsPO;
import pageobjects.cord.projects.languageEngagement.ProjectLanguageEngagementDetailsUpdateExtractGoalsPO;
import utilities.JavaHelpers;
import utilities.SeleniumHelpers;

public class LanguageFunctions {

    protected WebDriver driver;
    protected SeleniumHelpers selenium;
    public static final String PROPERTY_TEST_FILE = "src/main/resources/testData/TestFiles.properties";

    public LanguageFunctions(WebDriver driver, SeleniumHelpers selenium) {
        this.driver = driver;
        this.selenium = selenium;
    }

    /**
     * create language
     *
     * @param data LanguageData
     */

    public void createLanguage(dataobjects.Language data) {

        try {
            LeftNavPO leftNav = new LeftNavPO(driver);
            CreateUpdateLanguagePO createUpdateLanguage = new CreateUpdateLanguagePO(driver);

            Reporter.log("Step 1- Click on crate new item and select language menu");
            leftNav.clickOnCreateNewItemAndSelectType(CreateItemTypes.Language.toString());

            Reporter.log("Step 2- Enter valid details and click on submit button");
            createUpdateLanguage.clearAndFillLanguageDetails(data);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * create project language engagement
     *
     * @param projectName projectName
     */

    public void createProjectLanguageEngagements(Language data, String projectName,dataobjects.User user) {

        try {

            ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
            LeftNavPO sideHeader = new LeftNavPO(driver);
            ProjectsListingPO projectsListingPO = new ProjectsListingPO(driver);
            ProjectDetailsCreateLanguageEngagementPO projectDetailsCreateLanguageEngagementPO = new ProjectDetailsCreateLanguageEngagementPO(driver);
            HeaderPO headerPO = new HeaderPO(driver);

            Reporter.log("Step 1 - Find created project");
            sideHeader.clickOnProjectsMenu();
            if (!projectsListingPO.isProjectPresent(projectName)) {
                headerPO.searchItemGloballyByName(projectName);
            }
            projectsListingPO.clickOnProjectName(projectName);

            Reporter.log("Step 2 - Click on add language engagement button and create language engagement");
            projectDetails.clickOnLanguageEngagementButton();
            if (user.getUserRoles().contains(UserRole.Liaison.getRole())) {
                projectDetailsCreateLanguageEngagementPO.searchLanguageEngagementName(data);
            }
            else
            {projectDetailsCreateLanguageEngagementPO.fillLanguageEngagementDetailAndClickOnSubmitButton(data);}

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * edit project language engagement
     *
     * @param langData languageData
     */

    public void editProjectLanguageEngagements(Internship internData, Language langData) {

        try {

            ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
            ProjectEngagementDetailsUpdateStartEndDatePO projectEngagementDetailsUpdateStartEndDatePO = new ProjectEngagementDetailsUpdateStartEndDatePO(driver);
            ProjectLanguageEngagementDetailsPO projectLanguageEngagementDetailsPO = new ProjectLanguageEngagementDetailsPO(driver);
            ProjectEngagementDetailsUpdateParaTextRegisterIdPO projectEngagementDetailsUpdateParaTextRegisterIdPO = new ProjectEngagementDetailsUpdateParaTextRegisterIdPO(driver);
            ProjectLanguageEngagementDetailsUpdateExtractGoalsPO projectLanguageEngagementDetailsUpdateExtractGoalsPO = new ProjectLanguageEngagementDetailsUpdateExtractGoalsPO(driver);

            Reporter.log("Step 1 - Click on view details button and update language engagement ");
            projectDetails.clickOnViewDetailsLanguageEngagementButton(langData.getLanguageEngagementName());

            Reporter.log("Step 2 - Advance Language Engagement status from 'In Development' to 'Active'"); //Todo bug #762 RW access

            Reporter.log("Step 3 - Click on start end date button and update project language engagement start end date");
            projectLanguageEngagementDetailsPO.clickLanguageEngagementStartEndDateButton();
            projectEngagementDetailsUpdateStartEndDatePO.fillEngagementStartEndDateDetailsAndClickOnSaveButton(internData);

            Reporter.log("Step 4 - Click on para text register button and update project language engagement para text register");
            projectLanguageEngagementDetailsPO.clickLanguageEngagementParaTextRegistryIdButton();
            projectEngagementDetailsUpdateParaTextRegisterIdPO.fillEngagementParaTextRegistryIdDetailsAndClickOnSaveButton(internData);

            Reporter.log("Step 5 - Click on dedication toggle button edit button and update estimated and actual date");
            projectLanguageEngagementDetailsPO.clickDedicationToggleButton();
            projectLanguageEngagementDetailsPO.fillLanguageEngagementEstimatedActualDateDetails(internData);

            Reporter.log("Step 6 - Enter translation date details");
            projectLanguageEngagementDetailsPO.clickTranslationCompleteDateAndFillDetail(internData);

            Reporter.log("Step 7 - Enter disbursement complete date details");
            projectLanguageEngagementDetailsPO.clickOnDisbursementCompleteDateAndFillDetail(internData);

            Reporter.log("Step 8 - Click on planning and progress button and add file");
            String textFileName = JavaHelpers.getPropertyValue(PROPERTY_TEST_FILE, "textFile");
            projectLanguageEngagementDetailsPO.clickOnAddPlanningAndProgressButton(textFileName);
            projectLanguageEngagementDetailsUpdateExtractGoalsPO.clickOnExtractGoalMethodologyRadioButton(GoalMethodologySkip.SkipExtractingGoals.getGoalMethodologySkip());
            selenium.hardWait(5);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

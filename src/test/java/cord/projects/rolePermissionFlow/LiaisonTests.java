package cord.projects.rolePermissionFlow;

import base.BaseTest;
import datafactory.*;
import dataobjects.*;
import enums.menu.CreateItemTypes;
import enums.project.ProjectStep;
import enums.project.ProjectSubTabs;
import enums.users.UserRole;
import funcLibs.project.*;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageobjects.cord.common.HeaderPO;
import pageobjects.cord.common.LeftNavPO;
import pageobjects.cord.languages.LanguageDetailPO;
import pageobjects.cord.partners.PartnersListingPO;
import pageobjects.cord.people.PeopleDetailsPO;
import pageobjects.cord.people.PeoplesPO;
import pageobjects.cord.people.UpdatePeoplePO;
import pageobjects.cord.projects.*;
import pageobjects.cord.projects.intershipEngagement.*;
import pageobjects.cord.projects.languageEngagement.ProjectLanguageEngagementDetailsPO;
import pageobjects.cord.projects.languageEngagement.goal.CreateGoalPO;
import utilities.Constants;
import utilities.DriverManager;
import utilities.JavaHelpers;
import utilities.SeleniumHelpers;

public class LiaisonTests extends BaseTest {

    protected String strLsnPeopleUrl;
    protected String strLsnInternshipUrl;
    protected String strLsnPartnershipUrl;
    protected String strFundingPartnershipUrl;
    protected String strLsnProjectCreateGoalUrl;
    protected String strLsnProjectTeamMemberUrl;
    protected String strLsnLanguageTranslationUrl;
    People lsnPeopleFpmData = new PeopleData().getPeopleData();
    Project lsnAdminData = new ProjectData().getCreateProjectData();
    Project lsnAdminLangData = new ProjectData().getCreateTranslationProjectData();
    Project lsnAdminBudgetData = new ProjectData().getCreateProjectData();
    Project lsnAdminLocationData = new ProjectData().getCreateProjectData();
    String lsnCreatedBudgetProjectName = lsnAdminBudgetData.getProjectName();
    String lsnLocationProjectName = lsnAdminLocationData.getProjectName();
    Project lsnAdminTranslationData = new ProjectData().getCreateTranslationProjectData();
    Project lsnAdminTransEngagementData = new ProjectData().getCreateTranslationProjectData();
    String lsnCreatedTranslationProjectName = lsnAdminTranslationData.getProjectName();
    String lsnCreatedProjectName = lsnAdminData.getProjectName();
    People lsnPeopleAdminData = new PeopleData().getPeopleData();
    Project lsnEditProjectAdminData = new ProjectData().getUpdateProjectData();
    Language lsnLanguageAdminData = new LanguageData().getLanguageData();
    Partner lsnPartnerAdminData = new PartnerData().getCreatePartnerData();
    Goal lsnGoalData = new GoalData().getCreateGoalData();

    public LiaisonTests() throws ParseException, java.text.ParseException, InterruptedException {
    }

    public static final String PROPERTY_TEST_FILE = "src/main/resources/testData/TestFiles.properties";

    @BeforeClass
    public void setupTestData() throws InterruptedException {

        WebDriver driver;
        DriverManager drivermanager;

        drivermanager = new DriverManager();
        driver = drivermanager.setUp("chrome");
        SeleniumHelpers selenium = new SeleniumHelpers(driver);

        Reporter.log("Step 1 - Create Project by admin user and logout that user");
        new LoginFunctions(driver, selenium).loginByGivenUser(adminUser);
        new ProjectFunctions(driver, selenium).createProject(lsnAdminData);
        new ProjectFunctions(driver, selenium).addMemberToProject(liaisonUser, lsnAdminData);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 2 - Create Project language and language engagement by admin for liaison user and logout that admin user");
        new LoginFunctions(driver, selenium).loginByGivenUser(adminUser);
        new ProjectFunctions(driver, selenium).createProject(lsnAdminLangData);
        new ProjectFunctions(driver, selenium).addMemberToProject(liaisonUser, lsnAdminLangData);
        new LanguageFunctions(driver, selenium).createLanguage(lsnLanguageAdminData);
        new LanguageFunctions(driver, selenium).createProjectLanguageEngagements(lsnLanguageAdminData, lsnAdminLangData.getProjectName(), adminUser);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 2 - Create Project by admin user,add location and logout that user");
        new ProjectFunctions(driver, selenium).createProject(lsnAdminLocationData);
        new ProjectFunctions(driver, selenium).addMemberToProject(liaisonUser, lsnAdminLocationData);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 2 - Create Budget Project by admin user and logout that user");
        new ProjectFunctions(driver, selenium).createProject(lsnAdminBudgetData);
        new ProjectFunctions(driver, selenium).addMemberToProject(liaisonUser, lsnAdminBudgetData);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 3 - Create TranslationType Project by admin user and logout that user");
        new ProjectFunctions(driver, selenium).createProject(lsnAdminTranslationData);
        new ProjectFunctions(driver, selenium).addMemberToProject(liaisonUser, lsnAdminTranslationData);
        new PartnershipFunctions(driver, selenium).createProjectPartnership(lsnPartnerAdminData ,lsnCreatedTranslationProjectName,adminUser);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(fpmUser);

        Reporter.log("Step 4 - Create Project by project manager and logout that user");
        new ProjectFunctions(driver, selenium).createProject(lsnAdminTransEngagementData);
        new ProjectFunctions(driver, selenium).addMemberToProject(liaisonUser, lsnAdminTransEngagementData);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 5 - Create People by admin user and logout that user");
        new PeopleFunctions(driver, selenium).createPeople(lsnPeopleAdminData, adminUser);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(fpmUser);

        Reporter.log("Step 6 - Create People by project manager user and logout that user");
        new PeopleFunctions(driver, selenium).createPeople(lsnPeopleFpmData, fpmUser);
        strLsnPeopleUrl = driver.getCurrentUrl();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 8 - Create internship engagements by admin user and logout that user");
        new InternshipEngagementsFunctions(driver, selenium).createProjectInternshipEngagements(adminUser, lsnCreatedProjectName);
        strLsnInternshipUrl = driver.getCurrentUrl();
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 9 - Create language engagements by admin user and logout that user");
        new LanguageFunctions(driver, selenium).createProjectLanguageEngagements(lsnLanguageAdminData, lsnCreatedTranslationProjectName, adminUser);
        strLsnLanguageTranslationUrl = driver.getCurrentUrl();
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 10 - Create Partner by admin user and logout that user");
        new PartnerFunctions(driver, selenium).createPartner(lsnPartnerAdminData);
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 11 - Create partnership by admin user");
        new PartnershipFunctions(driver, selenium).createProjectPartnership(lsnPartnerAdminData, lsnCreatedTranslationProjectName,adminUser);
        strLsnPartnershipUrl = driver.getCurrentUrl();
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 12 - Add project team member with multiple user role and logout that user");
        new ProjectFunctions(driver, selenium).addMemberToProject(rdFodUser, lsnAdminData);
        strLsnProjectTeamMemberUrl = driver.getCurrentUrl();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 13 - Create goal by admin user");
        selenium.navigateToPage(strLsnLanguageTranslationUrl);
        new GoalFunctions(driver, selenium).createGoal(lsnGoalData, lsnLanguageAdminData.getLanguageEngagementName(),lsnPartnerAdminData.getOrganizationName());
        strLsnProjectCreateGoalUrl = driver.getCurrentUrl();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 14 - Create funding(type) partnership by admin user");
        new ProjectFunctions(driver, selenium).editInternshipProjectByName(lsnEditProjectAdminData, lsnCreatedBudgetProjectName,adminUser);
        new PartnershipFunctions(driver, selenium).createProjectFundingPartnership(lsnPartnerAdminData, lsnEditProjectAdminData.getProjectName());
        strFundingPartnershipUrl = driver.getCurrentUrl();
        drivermanager.tearDown();
    }

    /*Test 1 : Verify that liaison can view all member of projects */
    @Test(priority = 1)
    public void VerifyLiaisonCanViewAllMemberOfProjectsSuccessfully() throws InterruptedException {

        LeftNavPO sideHeader = new LeftNavPO(driver);
        ProjectsListingPO projectsListingPO = new ProjectsListingPO(driver);
        ProjectDetailsPO projectDetailsPO = new ProjectDetailsPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with liaison user");
        new LoginFunctions(driver, selenium).loginByGivenUser(liaisonUser);

        Reporter.log("Step 2 - Verify that projects is displayed which is created by admin");
        sideHeader.clickOnProjectsMenu();
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());

        if (!projectsListingPO.isProjectPresent(lsnAdminData.getProjectName())) {
            headerPO.searchItemGloballyByName(lsnAdminData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(lsnAdminData.getProjectName());
        Assert.assertEquals(projectDetailsPO.getProjectName(), lsnAdminData.getProjectName(), "Project is not present : " + lsnAdminData.getProjectName());

        Reporter.log("Step 3 - Verify that projects is displayed which is created by FPM user");
        sideHeader.clickOnProjectsMenu();
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());

        if (!projectsListingPO.isProjectPresent(lsnAdminTransEngagementData.getProjectName())) {
            headerPO.searchItemGloballyByName(lsnAdminTransEngagementData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(lsnAdminTransEngagementData.getProjectName());
        Assert.assertEquals(projectDetailsPO.getProjectName(), lsnAdminTransEngagementData.getProjectName(), "Project is not present : " + lsnAdminTransEngagementData.getProjectName());

    }

    /*Test 2 : Verify that liaison can view mine peoples*/ //Todo bug(#872)
    @Test(priority = 2)
    public void VerifyLiaisonCanViewAllPeoplesSuccessfully() throws InterruptedException {

        LeftNavPO sideHeader = new LeftNavPO(driver);
        PeoplesPO peoples = new PeoplesPO(driver);
        PeopleDetailsPO peopleDetails = new PeopleDetailsPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with liaison user");
        selenium.navigateToPage(strLsnPeopleUrl);
        new LoginFunctions(driver, selenium).loginByGivenUser(liaisonUser);

        Reporter.log("Step 2 - Verify that only mine people is displayed");
        sideHeader.clickOnPeopleMenu();
        if (!peoples.isPeoplePresent(liaisonUser.getRealFirstName()+" "+liaisonUser.getRealLastName())) {
            headerPO.searchItemGloballyByName(liaisonUser.getRealFirstName()+" "+liaisonUser.getRealLastName());
        }
        peoples.clickOnPeopleName(liaisonUser.getRealFirstName()+" "+liaisonUser.getRealLastName());
        Assert.assertEquals(peopleDetails.getPeopleName(), liaisonUser.getRealFirstName() + " " + liaisonUser.getRealLastName(), "People is not present : " + liaisonUser.getRealFirstName() + " " + liaisonUser.getRealLastName());

        Reporter.log("Step 3 - Search project manager user and click on that people");
        sideHeader.clickOnPeopleMenu();
        if (!peoples.isPeoplePresent(fpmUser.getRealFirstName() +" " + fpmUser.getRealLastName())) {
            headerPO.searchItemGloballyByName(fpmUser.getRealFirstName() +" " + fpmUser.getRealLastName());
        }
        peoples.clickOnPeopleName();
        peopleDetails.clickOnNullText();

        String fpmPeopleMessage = "You don't have permission to view this person's name";

        Reporter.log("Step 4 - Verify that null value is present");
        Assert.assertEquals(peopleDetails.getPermissionToolTipText(),fpmPeopleMessage,"null user does not displayed");

    }

    /*Test 3 : Verify that liaison can edit mine peoples */
    @Test(priority = 3)
    public void VerifyThatLiaisonCanEditMinePeoples() throws InterruptedException {

        PeoplesPO peoples = new PeoplesPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);
        LeftNavPO sideHeader = new LeftNavPO(driver);
        UpdatePeoplePO updatePeople = new UpdatePeoplePO(driver);
        PeopleDetailsPO peopleDetails = new PeopleDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with liaison user");
        new LoginFunctions(driver, selenium).loginByGivenUser(liaisonUser);

        Reporter.log("Step 2 - Search user which have a liaison role by email and click on people name");
        sideHeader.clickOnPeopleMenu();
        if (!peoples.isPeoplePresent(liaisonUser.getEmail())) {
            headerPO.searchItemGloballyByName(liaisonUser.getEmail());
        }
        peoples.clickOnPeopleName(liaisonUser.getRealFirstName() + " " + liaisonUser.getRealLastName());

        Reporter.log("Step 3 - Click on edit icon and edit mine person detail");
        peopleDetails.clickOnEditButton();
        updatePeople.clearAndUpdatePersonDetail(lsnPeopleAdminData);
        updatePeople.clickOnSubmitButton();

        Reporter.log("Step 4 - Verify updated person details");
        //  Assert.assertEquals(liaisonUser.getEmail(), peopleDetails.getEmail(), "email doesn't match : "); //Todo bug(#762) Global W access
        Assert.assertEquals(lsnPeopleAdminData.getPersonTitle(), peopleDetails.getTitle(), "title doesn't match : ");
        Assert.assertEquals(lsnPeopleAdminData.getPersonPhone(), peopleDetails.getPhoneNumber(), "phone number doesn't match : ");
        Assert.assertEquals(lsnPeopleAdminData.getPersonAbout(), peopleDetails.getAbout(), "About doesn't match : ");
        Assert.assertEquals(UserRole.Liaison.getRole(), peopleDetails.getRoles(), "Roles doesn't match : ");

        Reporter.log("Step 5 - Again Click on edit icon and reset the people");
        peopleDetails.clickOnEditButton();
        updatePeople.fillFirstnameLastNameAndEmailDetail(liaisonUser.getRealFirstName(), liaisonUser.getRealLastName(),liaisonUser.getEmail());
        updatePeople.clickOnSubmitButton();
        Assert.assertEquals(liaisonUser.getRealFirstName() + " " + liaisonUser.getRealLastName(), peopleDetails.getPeopleName(), "personName doesn't match : ");

    }

    /*Test 4 : Verify that liaison can view member languages and edit icon is not present*/
    @Test(priority = 4)
    public void VerifyLiaisonCanViewAllLanguagesAndEditIconIsNotPresent() throws InterruptedException {

        LeftNavPO sideHeader = new LeftNavPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);
        ProjectDetailsPO projectDetailsPO = new ProjectDetailsPO(driver);
        ProjectLanguageEngagementDetailsPO projectLanguageEngagementDetailsPO = new ProjectLanguageEngagementDetailsPO(driver);

        ProjectsListingPO projectsListingPO = new ProjectsListingPO(driver);
        LanguageDetailPO languageDetailPO = new LanguageDetailPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with liaison user");
        new LoginFunctions(driver, selenium).loginByGivenUser(liaisonUser);

        Reporter.log("Step 2 - Verify that projects is displayed which is created by admin");
        sideHeader.clickOnProjectsMenu();
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());

        if (!projectsListingPO.isProjectPresent(lsnAdminLangData.getProjectName())) {
            headerPO.searchItemGloballyByName(lsnAdminLangData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(lsnAdminLangData.getProjectName());

        Reporter.log("Step 3 - Click on view details button of language engagement ");
        projectDetailsPO.clickOnViewDetailsLanguageEngagementButton(lsnLanguageAdminData.getLanguageEngagementName());

        Reporter.log("Step 4 - Verify that language is displayed which is created by admin and edit icon is not present");
        Assert.assertEquals(projectLanguageEngagementDetailsPO.geLanguageEngagementText(), lsnLanguageAdminData.getLanguageEngagementName(), "Language is not present : " + lsnLanguageAdminData.getPubliicName());
        Assert.assertFalse(languageDetailPO.isEditIconPresent(), "Language edit icon is present : ");

    }

    /*Test 5 : Verify that liaison can not allow view partners*/
    @Test(priority = 5)
    public void VerifyLiaisonCanNotAllowViewAllPartnersSuccessfully() throws InterruptedException {

        LeftNavPO sideHeader = new LeftNavPO(driver);
        PartnersListingPO partnerListing = new PartnersListingPO(driver);
        String partnerCount = "0 Partners";

        Reporter.log("Step 1 - Navigate to url and login with liaison user");
        new LoginFunctions(driver, selenium).loginByGivenUser(liaisonUser);

        Reporter.log("Step 2 - Verify that partner count is 0");
        sideHeader.clickOnPartnersMenu();
        Assert.assertEquals(partnerCount, partnerListing.getPartnerCount(), "Partner count is not match");
    }

    /*Test 7 : Verify that liaison can upload edit project files */
    @Test(priority = 7)
    public void VerifyThatLiaisonCanUploadEditProjectFilesSuccessfully() throws InterruptedException {

        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectDetailsFilesListingPO projectDetailsFilesListingPO = new ProjectDetailsFilesListingPO(driver);
        ProjectDetailsFilesHistoryListingPO projectDetailsFilesHistoryListingPO = new ProjectDetailsFilesHistoryListingPO(driver);
        ProjectDetailsFileListingRenameFileFolderPO projectDetailsFileListingRenameFileFolderPO = new ProjectDetailsFileListingRenameFileFolderPO(driver);
        ProjectDetailsFileListingDeleteFileFolderPO projectDetailsFileListingDeleteFileFolderPO = new ProjectDetailsFileListingDeleteFileFolderPO(driver);

        String textFileName = JavaHelpers.getPropertyValue(PROPERTY_TEST_FILE, "textFile");

        Reporter.log("Step 1 - Entering valid sign in details and login to application");
        new LoginFunctions(driver, selenium).loginByGivenUser(liaisonUser);

        Reporter.log("Step 2 -  Login with project manager user and upload file in created project");
        new FileFunctions(driver, selenium).uploadFile(lsnCreatedProjectName, textFileName);

        Reporter.log("Step 3 - Verify that project file is uploaded by project manager user ");
        Assert.assertEquals(textFileName, projectDetails.getUploadedFileName(), "uploaded file name doesn't match : ");

        Reporter.log("Step 4 - Click on file view button");
        projectDetails.clickOnViewFileButton();

        Reporter.log("Step 5 - Click on kebab menu and add new version of file");
        String versionFileName = JavaHelpers.getPropertyValue(PROPERTY_TEST_FILE, "newVersionFile");
        projectDetailsFilesListingPO.clickOnKebabMenuAndAddNewFileVersion(projectDetailsFilesListingPO.getLastFileUploadText().replace(".txt", ""), versionFileName);
        selenium.hardWait(5);
        Assert.assertEquals(projectDetailsFilesListingPO.getLastFileUploadText(), versionFileName, "project new file version name doesn't match : ");
        projectDetailsFilesListingPO.closeTheKebabMenu();

        Reporter.log("Step 6 - Click on kebab menu and view the history of file");
        projectDetailsFilesListingPO.clickOnKebabMenuAndViewFileHistory(projectDetailsFilesListingPO.getFirstFileUploadText().replace(".txt", ""));
        Assert.assertTrue(projectDetailsFilesHistoryListingPO.isFilePresent(versionFileName), "file is not present : ");

        Reporter.log("Step 7 - Click on history kebab menu and rename the file");
        projectDetailsFilesHistoryListingPO.clickOnHistoryKebabMenuAndRenameFileFolder(projectDetailsFilesListingPO.getLastFileUploadText());
        projectDetailsFileListingRenameFileFolderPO.clearAndRenameFileFolderName(lsnEditProjectAdminData);
        Assert.assertTrue(projectDetailsFilesHistoryListingPO.isFilePresent(lsnEditProjectAdminData.getFileName() + ".txt"), "renamed file is not present : ");
        Assert.assertFalse(projectDetailsFilesHistoryListingPO.isFilePresent(versionFileName), "renamed file is present : ");

        Reporter.log("Step 8 - Click on history kebab menu and delete created file");
        projectDetailsFilesHistoryListingPO.clickOnKebabMenuAndDeleteFileFolder(projectDetailsFilesListingPO.getFirstFileUploadText());
        projectDetailsFileListingDeleteFileFolderPO.clickOnFileDeleteButton();
        Assert.assertFalse(projectDetailsFilesListingPO.isFileFolderPresent(lsnEditProjectAdminData.getFileName()), "deleted file is present : ");

    }

    /*Test 8 : Verify that liaison can not see project budget */
    @Test(priority = 8)
    public void VerifyThatLiaisonCanNotSeeProjectBudgetSuccessfully() throws InterruptedException {

        ProjectDetailsBudgetListingPO projectDetailsBudgetListingPO = new ProjectDetailsBudgetListingPO(driver);
        ProjectDetailsPartnershipsListingPO projectDetailsPartnershipsListingPO = new ProjectDetailsPartnershipsListingPO(driver);
        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);

        Reporter.log("Step 1 - Entering valid sign in details and login to application");
        new LoginFunctions(driver, selenium).loginByGivenUser(liaisonUser);

        Reporter.log("Step 2 - Navigate on partnership detail page and click on project link button");
        selenium.navigateToPage(strFundingPartnershipUrl);
        projectDetailsPartnershipsListingPO.clickOnProjectLinkButton();

        Reporter.log("Step 3 - Click on budget detail button");
        projectDetails.clickOnBudgetButton();

        Reporter.log("Step 4 - Verify that project budget name is not available");
        Assert.assertEquals(projectDetailsBudgetListingPO.getBudgetNameText(), "", "Budget name is available");
    }

    /*Test 9 : Verify that liaison can read team member */ // Todo (#Bug 875)

    /*Test 10 : Verify that liaison can not edit project team member */
    @Test(priority = 10)
    public void VerifyThatLiaisonCanNotEditProjectTeamMemberSuccessfully() throws InterruptedException {

        ProjectMembersUpdateTeamMembersPO projectMembersUpdateTeamMembersPO = new ProjectMembersUpdateTeamMembersPO(driver);

        Reporter.log("Step 1 -  Login with liaison user and edit team member in created project");
        new LoginFunctions(driver, selenium).loginByGivenUser(liaisonUser);
        selenium.navigateToPage(strLsnProjectTeamMemberUrl);
        selenium.hardWait(3);
        new TeamMemberFunctions(driver, selenium).editProjectTeamMember(liaisonUser);

        String teamMemberErrorMessage = Constants.teamMemberErrorMessage;

        Reporter.log("Step 2 - Verify that project team member is not edited by liaison user");
        Assert.assertEquals(teamMemberErrorMessage, projectMembersUpdateTeamMembersPO.getTeamMemberErrorText(), "Team member error message is not matched");
    }

    /*Test 11 : Verify that liaison can not create project partnership */
    @Test(priority = 11)
    public void VerifyThatLiaisonCanNotCreateProjectPartnership() {

        ProjectMembersCreatePartnershipPO projectMembersCreatePartnershipPO = new ProjectMembersCreatePartnershipPO(driver);

        Reporter.log("Step 1 - Login with liaison user");
        new LoginFunctions(driver, selenium).loginByGivenUser(liaisonUser);

        Reporter.log("Step 2 - Create Partnership with liaison user");
        new PartnershipFunctions(driver, selenium).createProjectPartnership(lsnPartnerAdminData, lsnCreatedProjectName,liaisonUser);

        String searchedPartnershipName = "No options";

        Reporter.log("Step 3 - Verify that user can not create partnership");
        Assert.assertEquals(searchedPartnershipName, projectMembersCreatePartnershipPO.getSearchedPartnershipText(), "project partnership searched text is matched ");
    }

    /*Test 12 : Verify that liaison can not edit project partnership*/
    @Test(priority = 12)
    public void VerifyThatLiaisonCanNotEditProjectPartnership() throws InterruptedException {

        ProjectDetailsPartnershipsEditPO projectDetailsPartnershipsEditPO = new ProjectDetailsPartnershipsEditPO(driver);
        ProjectDetailsPartnershipsListingPO partnershipsListingPO = new ProjectDetailsPartnershipsListingPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with liaison user");
        new LoginFunctions(driver, selenium).loginByGivenUser(liaisonUser);
        selenium.navigateToPage(strLsnPartnershipUrl);
        selenium.hardWait(3);

        Reporter.log("Step 2 - Click on partnership edit button");
        partnershipsListingPO.clickOnEditOrganizationButton();

        Reporter.log("Step 3 - Verify that project partnership is not edited by liaison user");
        Assert.assertFalse(projectDetailsPartnershipsEditPO.isPartnershipTypesButtonEnabled(), "Partnership Types checkbox is enabled : ");  //Todo Bug(#934)
        Assert.assertFalse(projectDetailsPartnershipsEditPO.isFinancialReportingTypeRadioButtonEnabled(), "Partnership Financial Reporting Type radio button is enabled : ");
        Assert.assertFalse(projectDetailsPartnershipsEditPO.isAgreementStatusEnabled(), "Partnership Agreement  Status radio button is enabled : ");
        Assert.assertFalse(projectDetailsPartnershipsEditPO.isMouStatusEnabled(), "Partnership Mou Status radio button is enabled : ");
        Assert.assertFalse(projectDetailsPartnershipsEditPO.isMouStartDateTextBoxEnabled(), "Partnership mou start date text box is enabled : ");
        Assert.assertFalse(projectDetailsPartnershipsEditPO.isMouEndDateTextBoxEnabled(), "Partnership  mou end date text box is enabled : ");

    }

    /*Test 14 : Verify that liaison can not edit project internship engagements */
    @Test(priority = 14)
    public void VerifyThatLiaisonCanNotEditProjectInternshipEngagements() throws InterruptedException {

        ProjectInternshipDetailsPO projectInternshipDetailsPO = new ProjectInternshipDetailsPO(driver);
        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectInternshipDetailsUpdateCountryOfOriginPO projectInternshipDetailsUpdateCountryOfOriginPO = new ProjectInternshipDetailsUpdateCountryOfOriginPO(driver);
        ProjectInternshipDetailsUpdateInternPositionPO projectInternshipDetailsUpdateInternPositionPO = new ProjectInternshipDetailsUpdateInternPositionPO(driver);
        ProjectEngagementDetailsUpdateStartEndDatePO projectEngagementDetailsUpdateStartEndDatePO = new ProjectEngagementDetailsUpdateStartEndDatePO(driver);
        ProjectInternshipLanguageDetailsUpdateStatusPO projectInternshipLanguageDetailsUpdateStatusPO = new ProjectInternshipLanguageDetailsUpdateStatusPO(driver);
        ProjectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO projectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO = new ProjectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO(driver);
        ProjectEngagementDetailsUpdateDisbursementCompleteDatePO projectEngagementDetailsUpdateDisbursementCompleteDatePO = new ProjectEngagementDetailsUpdateDisbursementCompleteDatePO(driver);

        Reporter.log("Step 1 -  Login with liaison user and navigate in internship url");
        new LoginFunctions(driver, selenium).loginByGivenUser(liaisonUser);
        selenium.navigateToPage(strLsnInternshipUrl);

        selenium.hardWait(3);

        Reporter.log("Step 2 - Try to edit internship engagement status");
        projectDetails.clickOnEditInternshipButtonWithOutUser();

        Reporter.log("Step 3 - Click on internship status button");
        projectInternshipDetailsPO.clickInternshipEngagementStatusButton();

        String internshipStatusErrorMessage = "The status of this engagement is unable to be changed.";
        Assert.assertEquals(internshipStatusErrorMessage, projectInternshipLanguageDetailsUpdateStatusPO.getInternshipStatusErrorText(), "project internship status error message doesn't match : ");

        Reporter.log("Step 4 - Click on internship status close button");
        projectInternshipLanguageDetailsUpdateStatusPO.clickOnInternshipStatusCloseButton();

        Reporter.log("Step 5 - Try to edit internship engagement start end date");
        projectInternshipDetailsPO.clickInternshipStartEndDateButton();
        Assert.assertFalse(projectEngagementDetailsUpdateStartEndDatePO.isEngagementStartDateEnabled(), "Internship start date textBox is enabled : ");
        Assert.assertFalse(projectEngagementDetailsUpdateStartEndDatePO.isEngagementEndDateEnabled(), "Internship end date textBox is enabled : ");

        Reporter.log("Step 6 - Click on internship start end date close button");
        projectEngagementDetailsUpdateStartEndDatePO.clickOnEngagementStartEndDateCloseButton();

        Reporter.log("Step 7 - Try to edit internship intern position");
        projectInternshipDetailsPO.clickEnterInternPositionButton();
        Assert.assertFalse(projectInternshipDetailsUpdateInternPositionPO.isInternPositionTextBoxEnabled(), "Internship intern position textBox is enabled : ");

        Reporter.log("Step 8 - Click on internship intern close button");
        projectInternshipDetailsUpdateInternPositionPO.clickOnInternshipInternPositionCloseButton();

        Reporter.log("Step 9 - Try to edit internship country of origin");
        projectInternshipDetailsPO.clickEnterCountryOfOriginButton();
        Assert.assertFalse(projectInternshipDetailsUpdateCountryOfOriginPO.isInternshipCountryOfOriginTextBoxEnabled(), "Internship country of origin textBox is enabled : ");

        Reporter.log("Step 10 - Click on internship country of origin close button");
        projectInternshipDetailsUpdateCountryOfOriginPO.clickOnInternshipCountryOfOriginCloseButton();

        Reporter.log("Step 11 - Try to edit internship growth plan complete date");
        projectInternshipDetailsPO.clickGrowthPlanCompleteDateButton();
        Assert.assertFalse(projectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO.isTranslationCompleteDateTextBoxEnabled(), "Internship growth plan date textBox is enabled : ");

        Reporter.log("Step 12 - Click on internship growth plan date close button");
        projectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO.clickOnInternshipGrowthCompleteDateCloseButton();

        Reporter.log("Step 13 - Try to edit internship disbursement complete date");
        projectInternshipDetailsPO.clickOnDisbursementCompleteDateButton();
        Assert.assertFalse(projectEngagementDetailsUpdateDisbursementCompleteDatePO.isInternshipDisbursementCompleteDateTextBoxEnabled(), "Internship disbursement complete date textBox is enabled : ");

        Reporter.log("Step 14 - Click on internship disbursement complete date close button");
        projectEngagementDetailsUpdateDisbursementCompleteDatePO.clickOnInternshipDisbursementCompleteDateCloseButton();

        Reporter.log("Step 17 - Try to edit internship add growth plan");
        Assert.assertFalse(projectInternshipDetailsPO.isGrowthPlanButtonEnabled(), "Internship growth plan button is enabled : ");

        Reporter.log("Step 18 - Try to edit internship mentor");
        Assert.assertFalse(projectInternshipDetailsPO.isInternshipAddMentorButtonEnabled(), "Internship add mentor button is enabled : ");

        Reporter.log("Step 19 - Try to edit internship actual estimated date");
        String disableEditButtonColor = "rgba(0, 0, 0, 0.26)";
        Assert.assertEquals(disableEditButtonColor, projectInternshipDetailsPO.getEditButtonColor(), "edit button color doesn't match : ");

    }

    /*Test 15 : Verify that liaison can read the location and can't edit project location */
    @Test(priority = 15)
    public void VerifyThatLiaisonCanReadTheLocationAndCanNotEditProjectLocation() throws InterruptedException {

        HeaderPO headerPO = new HeaderPO(driver);
        ProjectDetailsPO projectDetailsPO = new ProjectDetailsPO(driver);
        ProjectsListingPO projectsListingPO = new ProjectsListingPO(driver);
        ProjectDetailsUpdateLocationAndFieldRegionPO projectDetailLocationFieldRegionPO = new ProjectDetailsUpdateLocationAndFieldRegionPO(driver);
        ProjectDetailsUpdateLocationAndFieldRegionPO projectDetailsUpdateLocationAndFieldRegionPO = new ProjectDetailsUpdateLocationAndFieldRegionPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with liaison user");
        new LoginFunctions(driver, selenium).loginByGivenUser(adminUser);

        Reporter.log("Step 2 - Search added member project and click on location button");
        if (!projectsListingPO.isProjectPresent(lsnLocationProjectName)) {
            headerPO.searchItemGloballyByName(lsnLocationProjectName);
        }
        projectsListingPO.clickOnProjectName(lsnAdminData.getProjectName());

        Reporter.log("Step 3 - Click on project location and field region button and try to edit with admin user ");
        projectDetailsPO.clickProjectLocationAndFieldRegionButton();
        projectDetailLocationFieldRegionPO.fillLocationAndFieldRegionDetailsAndClickOnSaveButton(lsnEditProjectAdminData);

        Reporter.log("Step 4 - sign out the admin user and login with liaison user");
        new LoginFunctions(driver, selenium).signOutAndChangeUser(liaisonUser);

        Reporter.log("Step 5 - Search added member project and click on location button");
        if (!projectsListingPO.isProjectPresent(lsnLocationProjectName)) {
            headerPO.searchItemGloballyByName(lsnLocationProjectName);
        }
        projectsListingPO.clickOnProjectName(lsnAdminData.getProjectName());
        projectDetailsPO.clickProjectLocationAndFieldRegionButton();

        Reporter.log("Step 6 - Verify the location textBox is editable or not");
        Assert.assertFalse(projectDetailsUpdateLocationAndFieldRegionPO.isLocationTextBoxEnabled(), "Location textBox is enable");
    }

    /*Test 16 : Verify that liaison can not create language partner project and location */
    @Test(priority = 16)
    public void VerifyThatLiaisonCanNotCreateLanguagePartnerProjectAndLocation() throws InterruptedException {

        LeftNavPO leftNavPO = new LeftNavPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with liaison user");
        new LoginFunctions(driver, selenium).loginByGivenUser(liaisonUser);

        Reporter.log("Step 2 - Enter location name and click on view location button");
        leftNavPO.clickOnCreateNewItemMenuButton();

        Reporter.log("Step 3 - Verify language location project and partner option is not present");
        Assert.assertFalse(leftNavPO.isMenuItemPresent(CreateItemTypes.Language.toString()), "Language menu item is present:");
        Assert.assertFalse(leftNavPO.isMenuItemPresent(CreateItemTypes.Location.toString()), "Location menu item is present:");
        Assert.assertFalse(leftNavPO.isMenuItemPresent(CreateItemTypes.Project.toString()), "Project menu item is present:");
        Assert.assertFalse(leftNavPO.isMenuItemPresent(CreateItemTypes.Partner.toString()), "Partner menu item is present:");

        Reporter.log("Step 4 - Verify project person option is present");
        Assert.assertTrue(leftNavPO.isMenuItemPresent(CreateItemTypes.Person.toString()), "Person menu item is not present:");
    }

    /*Test 17 : Verify that liaison can not create project language engagements and created language engagement can read  */
    @Test(priority = 17)
    public void VerifyThatLiaisonCanNotCreateProjectLanguageEngagementsAndCreatedLanguageEngagementCanRead() throws InterruptedException {

        ProjectDetailsPO projectDetailsPO = new ProjectDetailsPO(driver);
        ProjectLanguageEngagementDetailsPO projectLanguageEngagementDetailsPO = new ProjectLanguageEngagementDetailsPO(driver);
        ProjectDetailsCreateLanguageEngagementPO projectDetailsCreateLanguageEngagementPO = new ProjectDetailsCreateLanguageEngagementPO(driver);

        Reporter.log("Step 1 - Login with liaison user");
        new LoginFunctions(driver, selenium).loginByGivenUser(liaisonUser);

        Reporter.log("Step 2 - Try to create language engagement with liaison user");
        new LanguageFunctions(driver, selenium).createProjectLanguageEngagements(lsnLanguageAdminData, lsnCreatedTranslationProjectName, liaisonUser);

        String searchedLanguageEngagementName = "No options";

        Reporter.log("Step 3 - Verify that user can not create language engagement");
        Assert.assertEquals(searchedLanguageEngagementName, projectDetailsCreateLanguageEngagementPO.getSearchedLanguageEngagementText(), "project language engagement error message doesn't match : ");

        Reporter.log("Step 4 - Navigate on created language engagement url");
        selenium.navigateToPage(strLsnLanguageTranslationUrl);

        Reporter.log("Step 5 - Verify that user can read language engagement");
        projectDetailsPO.clickOnViewDetailsLanguageEngagementButton(lsnLanguageAdminData.getLanguageEngagementName());
        Assert.assertEquals(lsnLanguageAdminData.getLanguageEngagementName(), projectLanguageEngagementDetailsPO.geLanguageEngagementText(), "project language engagement error message doesn't match : ");
    }

    /*Test 18 : Verify that liaison can not edit language engagements details */
    @Test(priority = 18)
    public void VerifyThatLiaisonCanNotEditProjectLanguageEngagements() throws InterruptedException {

        ProjectLanguageEngagementDetailsPO projectLanguageEngagementDetailsPO = new ProjectLanguageEngagementDetailsPO(driver);
        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectEngagementDetailsUpdateStartEndDatePO projectEngagementDetailsUpdateStartEndDatePO = new ProjectEngagementDetailsUpdateStartEndDatePO(driver);
        ProjectInternshipLanguageDetailsUpdateStatusPO projectInternshipLanguageDetailsUpdateStatusPO = new ProjectInternshipLanguageDetailsUpdateStatusPO(driver);
        ProjectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO projectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO = new ProjectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO(driver);

        Reporter.log("Step 1 -  Login with liaison user and navigate in language engagement url");
        new LoginFunctions(driver, selenium).loginByGivenUser(liaisonUser);
        selenium.navigateToPage(strLsnLanguageTranslationUrl);
        selenium.hardWait(3);

        Reporter.log("Step 2 - Click on view details button");
        projectDetails.clickOnViewDetailsLanguageEngagementButton(lsnLanguageAdminData.getLanguageEngagementName());

        Reporter.log("Step 3 - Click on language engagement status button");
        projectLanguageEngagementDetailsPO.clickLanguageEngagementStatusButton();
        String languageEngagementStatusErrorMessage = "The status of this engagement is unable to be changed.";
        Assert.assertEquals(languageEngagementStatusErrorMessage, projectInternshipLanguageDetailsUpdateStatusPO.getInternshipStatusErrorText(), "project language engagement status error message doesn't match : ");

        Reporter.log("Step 4 - Click on language engagement close button");
        projectInternshipLanguageDetailsUpdateStatusPO.clickOnInternshipStatusCloseButton();

        Reporter.log("Step 5 - Try to edit language engagement start end date");
        projectLanguageEngagementDetailsPO.clickLanguageEngagementStartEndDateButton();
        Assert.assertFalse(projectEngagementDetailsUpdateStartEndDatePO.isEngagementStartDateEnabled(), "Language engagement start date textBox is enabled : ");
        Assert.assertFalse(projectEngagementDetailsUpdateStartEndDatePO.isEngagementEndDateEnabled(), "Language engagement end date textBox is enabled : ");

        Reporter.log("Step 6 - Click on language engagement start end date close button");
        projectEngagementDetailsUpdateStartEndDatePO.clickOnEngagementStartEndDateCloseButton();

        Reporter.log("Step 7 - Try to edit language engagement translation complete date");
        Assert.assertFalse(projectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO.isTranslationCompleteDateTextBoxEnabled(), "Language engagement intern position textBox is enabled : ");

        Reporter.log("Step 8 - Try to edit language engagement actual estimated date");
        Assert.assertFalse(projectLanguageEngagementDetailsPO.isEstimatedDateTextBoxEnabled(), "Language engagement estimated date textBox is enabled : ");
        Assert.assertFalse(projectLanguageEngagementDetailsPO.isActualDateTextBoxEnabled(), "Language engagement actual date textBox is enabled : ");
    }

    /*Test 19 : Verify that liaison can not Add project goals */
    @Test(priority = 19)
    public void VerifyThatLiaisonCanNotAddProjectGoals() throws InterruptedException {

        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectLanguageEngagementDetailsPO projectLanguageEngagementDetailsPO = new ProjectLanguageEngagementDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with liaison user");
        new LoginFunctions(driver, selenium).loginByGivenUser(liaisonUser);
        selenium.navigateToPage(strLsnLanguageTranslationUrl);
        selenium.hardWait(3);

        Reporter.log("Step 2 - Click on view details button");
        projectDetails.clickOnViewDetailsLanguageEngagementButton(lsnLanguageAdminData.getLanguageEngagementName());

        Reporter.log("Step 3 - Verify the Add goal button is not present on language engagement detail page");
        Assert.assertFalse(projectLanguageEngagementDetailsPO.isAddGoalButtonPresent(), "Add goal button is present "); //#bug

    }

    /*Test 20 : Verify that liaison can read created project goals */
    @Test(priority = 20)
    public void VerifyThatLiaisonCanReadProjectGoals() throws InterruptedException {

        CreateGoalPO createGoalPO = new CreateGoalPO(driver);
        ProjectLanguageEngagementDetailsPO projectLanguageEngagementDetailsPO = new ProjectLanguageEngagementDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with liaison user");
        new LoginFunctions(driver, selenium).loginByGivenUser(liaisonUser);
        selenium.navigateToPage(strLsnProjectCreateGoalUrl);
        selenium.hardWait(3);

        Reporter.log("Step 2 - Click on goal text button");
        projectLanguageEngagementDetailsPO.clickOnGoalCard();

        Reporter.log("Step 3 - Click on goal edit button");
        projectLanguageEngagementDetailsPO.clickEditGoalButton();

        Reporter.log("Step 4 - Verify that liaison can read created project goals");
        String disableGoalColor = "rgba(60, 68, 78, 1)";
        Assert.assertEquals(disableGoalColor, createGoalPO.getDisabledEditGoalDetailsColor(), "Goal detail color doesn't match : ");

    }

    /*Test 21 : Verify that liaison can not edit projects */
    @Test(priority = 21)
    public void VerifyThatLiaisonCanNotEditProjectsSuccessfully() throws InterruptedException {

        LeftNavPO sideHeader = new LeftNavPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);
        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectsListingPO projectsListingPO = new ProjectsListingPO(driver);
        ProjectDetailsUpdateStepsPO projectDetailsUpdateStepsPO = new ProjectDetailsUpdateStepsPO(driver);
        ProjectDetailsUpdateEstimatedSubmissionDatePO projectDetailsUpdateEstimatedSubmissionDatePO = new ProjectDetailsUpdateEstimatedSubmissionDatePO(driver);
        ProjectDetailsUpdateStartEndDatePO projectDetailsUpdateStartEndDatePO = new ProjectDetailsUpdateStartEndDatePO(driver);
        ProjectDetailsUpdateLocationAndFieldRegionPO projectDetailsUpdateLocationAndFieldRegionPO = new ProjectDetailsUpdateLocationAndFieldRegionPO(driver);

        Reporter.log("Step 1 -  Login with liaison user and edit created project");
        new LoginFunctions(driver, selenium).loginByGivenUser(liaisonUser);

        Reporter.log("Step 2 - Verify that project is displayed which is edited by liaison user ");
        sideHeader.clickOnProjectsMenu();
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());

        if (!projectsListingPO.isProjectPresent(lsnAdminData.getProjectName())) {
            headerPO.searchItemGloballyByName(lsnAdminData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(lsnAdminData.getProjectName());

        Reporter.log("Step 3 - Click on project location and field region button and try to edit with liaison user ");
        projectDetails.clickProjectLocationAndFieldRegionButton();

        Reporter.log("Step 4 - Verify that liaison can't edit location and field region");
        Assert.assertFalse(projectDetailsUpdateLocationAndFieldRegionPO.isFieldRegionTextBoxEnabled(), "project field region textBox is enabled : ");
        Assert.assertFalse(projectDetailsUpdateLocationAndFieldRegionPO.isLocationTextBoxEnabled(), "project location textBox is enabled : ");

        Reporter.log("Step 5 - Click on location field region close button");
        projectDetailsUpdateLocationAndFieldRegionPO.clickOnLocationFieldRegionCloseButton();

        Reporter.log("Step 6 - Click on project start end date button");
        projectDetails.clickProjectStartEndDateButton();

        Reporter.log("Step 7 - Verify that liaison can't edit start end date");
        Assert.assertFalse(projectDetailsUpdateStartEndDatePO.isStartDateTextBoxEnabled(), "project start date textBox is enabled : ");
        Assert.assertFalse(projectDetailsUpdateStartEndDatePO.isEndDateTextBoxEnabled(), "project end date textBox is enabled: ");

        Reporter.log("Step 8 - Click on start end date close button");
        projectDetailsUpdateStartEndDatePO.clickOnStartEndDateCloseButton();


        Reporter.log("Step 9 - Click on project estimated submission date button and try to edit with liaison user ");
        projectDetails.clickProjectEstimatedSubmissionButton();

        Reporter.log("Step 10 - Verify that liaison can't edit estimated submission date");
        Assert.assertFalse(projectDetailsUpdateEstimatedSubmissionDatePO.isEstimatedSubmissionDateTextBoxEnabled(), "project estimated submission date textBox is enabled:");

        Reporter.log("Step 11 - Click on estimated submission date close button");
        projectDetailsUpdateEstimatedSubmissionDatePO.clickOnEstimatedSubmissionDateCloseButton();

        Reporter.log("Step 12 - Click on project status button and try to edit with liaison user ");
        projectDetails.clickOnProjectStepByName(ProjectStep.EarlyConversations.getStep());

        Reporter.log("Step 13 - Verify that liaison can't edit status");
        String statusErrorMessage = "The status of this project is unable to be changed.";
        Assert.assertEquals(statusErrorMessage, projectDetailsUpdateStepsPO.getProjectStatusError(), "project status error text doesn't match");

    }

}

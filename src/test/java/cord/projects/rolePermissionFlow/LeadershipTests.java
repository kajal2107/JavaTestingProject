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
import pageobjects.cord.languages.LanguagesPO;
import pageobjects.cord.location.LocationDetailsPO;
import pageobjects.cord.location.LocationsListingPO;
import pageobjects.cord.partners.EditPartnerPO;
import pageobjects.cord.partners.PartnerDetailsPO;
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

public class LeadershipTests extends BaseTest {

    protected String strLeaderPeopleUrl;
    protected String strLeaderLanguageUrl;
    protected String strLeaderProjectUrl;
    protected String strLeaderInternshipUrl;
    protected String strLeaderPartnershipUrl;
    protected String strLeaderFundingPartnershipUrl;
    protected String strLeaderProjectCreateGoalUrl;
    protected String strLeaderProjectTeamMemberUrl;
    protected String strLeaderLanguageTranslationUrl;
    People leaderPeopleFpmData = new PeopleData().getPeopleData();
    Project leaderAdminBudgetData = new ProjectData().getCreateProjectData();
    String leaderCreatedBudgetProjectName = leaderAdminBudgetData.getProjectName();
    Project leaderAdminTranslationData = new ProjectData().getCreateTranslationProjectData();
    Project leaderAdminTransEngagementData = new ProjectData().getCreateTranslationProjectData();
    String leaderCreatedTransEngagementProjectName = leaderAdminTransEngagementData.getProjectName();
    String leaderCreatedTranslationProjectName = leaderAdminTranslationData.getProjectName();
    Project leaderAdminData = new ProjectData().getCreateProjectData();
    String leaderCreatedProjectName = leaderAdminData.getProjectName();
    People leaderPeopleAdminData = new PeopleData().getPeopleData();
    People editLeaderPeopleAdminData = new PeopleData().getPeopleData();
    Project leaderEditProjectAdminData = new ProjectData().getUpdateProjectData();
    Language leaderLanguageAdminData = new LanguageData().getLanguageData();
    Partner leaderPartnerAdminData = new PartnerData().getCreatePartnerData();
    Partner leaderEditPartnerAdminData = new PartnerData().getEditPartnerData();
    Goal leaderGoalData = new GoalData().getCreateGoalData();
    Location leaderLocationData = new LocationData().getLocationData();
    String leaderLocationName = leaderLocationData.getLocationName();

    public LeadershipTests() throws ParseException, java.text.ParseException, InterruptedException {
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
        new ProjectFunctions(driver, selenium).createProject(leaderAdminData);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 2 - Create Budget Project by admin user and logout that user");
        new ProjectFunctions(driver, selenium).createProject(leaderAdminBudgetData);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 3 - Create TranslationType Project by admin user and logout that user");
        new ProjectFunctions(driver, selenium).createProject(leaderAdminTranslationData);
        new PartnershipFunctions(driver, selenium).createProjectPartnership(leaderPartnerAdminData ,leaderCreatedTranslationProjectName,adminUser);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(fpmUser);

        Reporter.log("Step 4 - Create Project by project manager and logout that user");
        new ProjectFunctions(driver, selenium).createProject(leaderAdminTransEngagementData);
        strLeaderProjectUrl = driver.getCurrentUrl();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 5 - Create People by admin user");
        new PeopleFunctions(driver, selenium).createPeople(leaderPeopleAdminData, adminUser);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(fpmUser);

        Reporter.log("Step 6 - Create People by project manager user");
        new PeopleFunctions(driver, selenium).createPeople(leaderPeopleFpmData, fpmUser);
        strLeaderPeopleUrl = driver.getCurrentUrl();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 7 - Create Language by admin user");
        new LanguageFunctions(driver, selenium).createLanguage(leaderLanguageAdminData);
        strLeaderLanguageUrl = driver.getCurrentUrl();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 8 - Create internship engagements by admin user");
        new InternshipEngagementsFunctions(driver, selenium).createProjectInternshipEngagements(adminUser, leaderCreatedProjectName);
        strLeaderInternshipUrl = driver.getCurrentUrl();
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 9 - Create language engagements by admin user");
        new LanguageFunctions(driver, selenium).createProjectLanguageEngagements(leaderLanguageAdminData, leaderCreatedTranslationProjectName, adminUser);
        strLeaderLanguageTranslationUrl = driver.getCurrentUrl();
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 10 - Create Partner by admin user");
        new PartnerFunctions(driver, selenium).createPartner(leaderPartnerAdminData);
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 11 - Create partnership by admin user");
        new PartnershipFunctions(driver, selenium).createProjectPartnership(leaderPartnerAdminData, leaderCreatedTranslationProjectName, adminUser);
        strLeaderPartnershipUrl = driver.getCurrentUrl();
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 12 - Add project team member with multiple user role");
        new ProjectFunctions(driver, selenium).addMemberToProject(rdFodUser, leaderAdminData);
        strLeaderProjectTeamMemberUrl = driver.getCurrentUrl();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 13 - Create Location by admin user");
        new LocationFunctions(driver, selenium).createLocation(leaderLocationData);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 14 - Create goal by admin user");
        selenium.navigateToPage(strLeaderLanguageTranslationUrl);
        new GoalFunctions(driver, selenium).createGoal(leaderGoalData, leaderLanguageAdminData.getLanguageEngagementName(),leaderPartnerAdminData.getOrganizationName());
        strLeaderProjectCreateGoalUrl = driver.getCurrentUrl();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 15 - Create funding partnership by admin user");
        new ProjectFunctions(driver, selenium).editInternshipProjectByName(leaderEditProjectAdminData, leaderCreatedBudgetProjectName,adminUser);
        new PartnershipFunctions(driver, selenium).createProjectFundingPartnership(leaderPartnerAdminData, leaderEditProjectAdminData.getProjectName());
        strLeaderFundingPartnershipUrl = driver.getCurrentUrl();
        drivermanager.tearDown();
    }

    /*Test 1 : Verify that leadership can view all projects*/
    @Test(priority = 1)
    public void VerifyLeadershipCanViewAllProjectsSuccessfully() throws InterruptedException {

        HeaderPO headerPO = new HeaderPO(driver);
        LeftNavPO sideHeader = new LeftNavPO(driver);
        ProjectsListingPO projectsListingPO = new ProjectsListingPO(driver);
        ProjectDetailsPO projectDetailsPO = new ProjectDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with leadership user");
        new LoginFunctions(driver, selenium).loginByGivenUser(leaderUser);

        Reporter.log("Step 2 - Verify that projects is displayed which is created by admin");
        sideHeader.clickOnProjectsMenu();
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());

        if (!projectsListingPO.isProjectPresent(leaderAdminData.getProjectName())) {
            headerPO.searchItemGloballyByName(leaderAdminData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(leaderAdminData.getProjectName());
        Assert.assertEquals(projectDetailsPO.getProjectName(), leaderAdminData.getProjectName(), "Project is not present : " + leaderAdminData.getProjectName());

        Reporter.log("Step 3 - Verify that projects is displayed which is created by FPM user");
        sideHeader.clickOnProjectsMenu();
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());

        if (!projectsListingPO.isProjectPresent(leaderAdminTransEngagementData.getProjectName())) {
            headerPO.searchItemGloballyByName(leaderAdminTransEngagementData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(leaderAdminTransEngagementData.getProjectName());
        Assert.assertEquals(projectDetailsPO.getProjectName(), leaderAdminTransEngagementData.getProjectName(), "Project is not present : " + leaderAdminTransEngagementData.getProjectName());

    }

    /*Test 2 : Verify that leadership can view all peoples*/
    @Test(priority = 2)
    public void VerifyLeadershipCanViewAllPeoplesSuccessfully() throws InterruptedException {

        HeaderPO headerPO = new HeaderPO(driver);
        PeoplesPO peoples = new PeoplesPO(driver);
        LeftNavPO sideHeader = new LeftNavPO(driver);
        PeopleDetailsPO peopleDetails = new PeopleDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with leadership user");
        selenium.navigateToPage(strLeaderPeopleUrl);
        new LoginFunctions(driver, selenium).loginByGivenUser(leaderUser);

        Reporter.log("Step 2 - Verify that peoples is displayed which is created by admin");
        sideHeader.clickOnPeopleMenu();
        if (!peoples.isPeoplePresent(leaderPeopleAdminData.getPersonName())) {
            headerPO.searchItemGloballyByName(leaderPeopleAdminData.getPersonName());
        }
        peoples.clickOnPeopleName(leaderPeopleAdminData.getPersonName());
        Assert.assertEquals(peopleDetails.getPeopleName().replaceAll("\\s.*", ""), leaderPeopleAdminData.getPersonName(), "People is not present : " + leaderPeopleAdminData.getPersonName());

        Reporter.log("Step 3 - Verify that peoples is displayed which is created by FPM user");
        sideHeader.clickOnPeopleMenu();
        if (!peoples.isPeoplePresent(leaderPeopleFpmData.getPersonName())) {
            headerPO.searchItemGloballyByName(leaderPeopleFpmData.getPersonName());
        }
        peoples.clickOnPeopleName(leaderPeopleFpmData.getPersonName());
        Assert.assertEquals(peopleDetails.getPeopleName().replaceAll("\\s.*", ""), leaderPeopleFpmData.getPersonName(), "People is not present : " + leaderPeopleFpmData.getPersonName());

    }

    /*Test 3 : Verify that leadership can edit mine peoples */
    @Test(priority = 3)
    public void VerifyThatLeadershipCanEditMinePeoples() throws InterruptedException {

        PeoplesPO peoples = new PeoplesPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);
        LeftNavPO sideHeader = new LeftNavPO(driver);
        UpdatePeoplePO updatePeople = new UpdatePeoplePO(driver);
        PeopleDetailsPO peopleDetails = new PeopleDetailsPO(driver);

        Reporter.log("Step 1 - Login with leadership user");
        new LoginFunctions(driver, selenium).loginByGivenUser(leaderUser);

        Reporter.log("Step 2 - Search user which have a leadership role by email and click on people name");
        sideHeader.clickOnPeopleMenu();
        if (!peoples.isPeoplePresent(leaderUser.getEmail())) {
            headerPO.searchItemGloballyByName(leaderUser.getEmail());
        }
        peoples.clickOnPeopleName(leaderUser.getRealFirstName() + " " + leaderUser.getRealLastName());

        Reporter.log("Step 3 - Click on edit icon and edit mine person detail");
        peopleDetails.clickOnEditButton();
        updatePeople.clearAndUpdatePersonDetail(editLeaderPeopleAdminData);
        updatePeople.clickOnSubmitButton();

        Reporter.log("Step 4 - Verify updated person details");
        Assert.assertEquals(editLeaderPeopleAdminData.getPersonEmail(), peopleDetails.getEmail(), "email doesn't match : ");
        Assert.assertEquals(editLeaderPeopleAdminData.getPersonTitle(), peopleDetails.getTitle(), "title doesn't match : ");
        Assert.assertEquals(editLeaderPeopleAdminData.getPersonPhone(), peopleDetails.getPhoneNumber(), "phone number doesn't match : ");
        Assert.assertEquals(editLeaderPeopleAdminData.getPersonAbout(), peopleDetails.getAbout(), "About doesn't match : ");
        Assert.assertEquals(UserRole.Leadership.getRole(), peopleDetails.getRoles(), "Roles doesn't match : ");

        Reporter.log("Step 5 - Again Click on edit icon and reset the people");
        peopleDetails.clickOnEditButton();
        updatePeople.fillFirstnameLastNameAndEmailDetail(leaderUser.getRealFirstName(), leaderUser.getRealLastName(), leaderUser.getEmail());
        updatePeople.clickOnSubmitButton();
        Assert.assertEquals(leaderUser.getRealFirstName() + " " + leaderUser.getRealLastName(), peopleDetails.getPeopleName(), "personName doesn't match : ");
        Assert.assertEquals(leaderUser.getEmail(), peopleDetails.getEmail(), "personEmail doesn't match : ");
    }

    /*Test 4 : Verify that leadership can view all languages and edit icon is not present*/
    @Test(priority = 4)
    public void VerifyLeadershipCanViewAllLanguagesAndEditIconIsNotPresent() throws InterruptedException {

        HeaderPO headerPO = new HeaderPO(driver);
        LeftNavPO sideHeader = new LeftNavPO(driver);
        LanguagesPO languages = new LanguagesPO(driver);
        LanguageDetailPO languageDetailPO = new LanguageDetailPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with leadership user");
        selenium.navigateToPage(strLeaderLanguageUrl);
        new LoginFunctions(driver, selenium).loginByGivenUser(leaderUser);

        Reporter.log("Step 2 - Verify that language is displayed which is created by admin and edit icon is not present");
        sideHeader.clickOnLanguagesMenu();
        if (!languages.isLanguagePresent(leaderLanguageAdminData.getLanguageName())) {
            headerPO.searchItemGloballyByName(leaderLanguageAdminData.getLanguageName());
        }
        languages.clickOnLanguageName(leaderLanguageAdminData.getLanguageName());
        Assert.assertEquals(languageDetailPO.getLanguageName(), leaderLanguageAdminData.getPubliicName(), "Language is not present : " + leaderLanguageAdminData.getPubliicName());
        Assert.assertFalse(languageDetailPO.isEditIconPresent(), "Language edit icon is present : ");
    }

    /*Test 5 : Verify that leadership can view all partners*/
    @Test(priority = 5)
    public void VerifyLeadershipCanViewAllPartnersSuccessfully() throws InterruptedException {

        HeaderPO headerPO = new HeaderPO(driver);
        LeftNavPO sideHeader = new LeftNavPO(driver);
        PartnersListingPO partnerListing = new PartnersListingPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with leadership user");
        new LoginFunctions(driver, selenium).loginByGivenUser(leaderUser);

        Reporter.log("Step 2 - Verify that partner is displayed which is created by admin");
        sideHeader.clickOnPartnersMenu();
        if (!partnerListing.isPartnerPresent(leaderPartnerAdminData.getPartnerName())) {
            headerPO.searchItemGloballyByName(leaderPartnerAdminData.getPartnerName());
        }
        Assert.assertTrue(partnerListing.isPartnerPresent(leaderPartnerAdminData.getPartnerName()), "Partner is not present : " + leaderPartnerAdminData.getPartnerName());
    }

    /*Test 6 : Verify that leadership can't edit partners details */  //Todo bug(#837) Global R access
    @Test(priority = 6)
    public void VerifyThatLeadershipCanNotEditAllPartnersDetails() throws InterruptedException {

        EditPartnerPO editPartnerPO = new EditPartnerPO(driver);
        PartnerDetailsPO partnerDetailsPO = new PartnerDetailsPO(driver);

        Reporter.log("Step 1 - Login with leadership user");
        new LoginFunctions(driver, selenium).loginByGivenUser(leaderUser);

        Reporter.log("Step 2 - Verify that partner global innovations client is not edited by leadership user ");
        new PartnerFunctions(driver, selenium).editPartnerGlobalInnovationsClient(leaderPartnerAdminData.getPartnerName());
        Assert.assertTrue(editPartnerPO.isGlobalInnovationsClientErrorMessagePresent(), "Partner global innovations client error message is not present : ");
        editPartnerPO.clickOnCancelButton();

        Reporter.log("Step 3 - Verify that partner active inActive status is not edited by leadership user ");
        new PartnerFunctions(driver, selenium).editPartnerActiveInActiveStatus(leaderPartnerAdminData.getPartnerName());
        Assert.assertTrue(editPartnerPO.isActiveInActiveErrorMessagePresent(), "Partner active inActive error message is not present : ");
        editPartnerPO.clickOnCancelButton();

        Reporter.log("Step 4 - Verify that partner pmc entity code is not edited by leadership user ");
        new PartnerFunctions(driver, selenium).editPartnerPmcEntityCode(leaderPartnerAdminData.getPartnerName(), leaderEditPartnerAdminData);
        Assert.assertTrue(editPartnerPO.isPmcEntityCodeErrorMessagePresent(), "Partner pmc entity code error message is not present : ");
        editPartnerPO.clickOnCancelButton();

        Reporter.log("Step 5 - Verify that partner point of contact is not edited by leadership user ");
        new PartnerFunctions(driver, selenium).editPartnerPointOfContact(leaderPartnerAdminData.getPartnerName(), leaderUser);
        Assert.assertTrue(editPartnerPO.isPointOfContactErrorMessagePresent(), "Partner point of contact error message is not present : ");
        editPartnerPO.clickOnCancelButton();

        Reporter.log("Step 6 - Verify that partner types address is not edited by leadership user ");
        Assert.assertFalse(partnerDetailsPO.isPartnerTypesEditButtonEnabled(), "Partner types edit button is enabled: ");
        Assert.assertFalse(partnerDetailsPO.isPartnerAddressEditButtonEnabled(), "Partner address edit button is enabled: ");

    }

    /*Test 7 : Verify that leadership can read project files */
    @Test(priority = 7)
    public void VerifyThatLeadershipCanReadProjectFilesSuccessfully() throws InterruptedException {

        HeaderPO headerPO = new HeaderPO(driver);
        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectsListingPO projectsListingPO = new ProjectsListingPO(driver);
        ProjectDetailsFilesListingPO projectDetailsFilesListingPO = new ProjectDetailsFilesListingPO(driver);
        ProjectDetailsFilesHistoryListingPO projectDetailsFilesHistoryListingPO = new ProjectDetailsFilesHistoryListingPO(driver);
        ProjectDetailsFileListingRenameFileFolderPO projectDetailsFileListingRenameFileFolderPO = new ProjectDetailsFileListingRenameFileFolderPO(driver);

        String textFileName = JavaHelpers.getPropertyValue(PROPERTY_TEST_FILE, "textFile");

        Reporter.log("Step 1 - Entering valid sign in details and login to application");
        new LoginFunctions(driver, selenium).loginByGivenUser(adminUser);

        Reporter.log("Step 2 -  Login with admin user and upload file in created project");
        new FileFunctions(driver, selenium).uploadFile(leaderCreatedProjectName, textFileName);

        Reporter.log("Step 3 - Verify that project file is uploaded by admin user can view leadership user ");
        new LoginFunctions(driver, selenium).signOutAndChangeUser(leaderUser);

        Reporter.log("Step 4 - Click on all tabs and find the created project");
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());
        if (!projectsListingPO.isProjectPresent(leaderAdminData.getProjectName())) {
            headerPO.searchItemGloballyByName(leaderAdminData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(leaderAdminData.getProjectName());

        Reporter.log("Step 5 - Click on view file button and verify the uploaded file");
        projectDetails.clickOnViewFileButton();
        Assert.assertTrue(projectDetailsFilesListingPO.isFileFolderPresent(textFileName.replace(".txt", "")), "uploaded file name doesn't match : ");

        Reporter.log("Step 6 - Logout the leadership user and login with admin user");
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 7 - Click on all tabs and find the created project");
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());
        if (!projectsListingPO.isProjectPresent(leaderAdminData.getProjectName())) {
            headerPO.searchItemGloballyByName(leaderAdminData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(leaderAdminData.getProjectName());

        Reporter.log("Step 8 - Click on view file button");
        projectDetails.clickOnViewFileButton();

        Reporter.log("Step 9 - Click on kebab menu and add new version of file");
        String versionFileName = JavaHelpers.getPropertyValue(PROPERTY_TEST_FILE, "newVersionFile");
        projectDetailsFilesListingPO.clickOnKebabMenuAndAddNewFileVersion(textFileName.replace(".txt", ""), versionFileName);
        selenium.refreshPage();

        Reporter.log("Step 10 - Logout the admin user and login with fundraising user");
        new LoginFunctions(driver, selenium).signOutAndChangeUser(leaderUser);

        Reporter.log("Step 11 - Click on all tabs and find the created project");
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());
        if (!projectsListingPO.isProjectPresent(leaderAdminData.getProjectName())) {
            headerPO.searchItemGloballyByName(leaderAdminData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(leaderAdminData.getProjectName());

        Reporter.log("Step 12 - Click on view file button");
        projectDetails.clickOnViewFileButton();

        Reporter.log("Step 13 - Click on kebab menu and view the history of file");
        projectDetailsFilesListingPO.clickOnKebabMenuAndViewFileHistory(versionFileName.replace(".txt", ""));
        Assert.assertTrue(projectDetailsFilesHistoryListingPO.isFilePresent(versionFileName), "file is not present : ");

        Reporter.log("Step 14 - Logout the fundraising user and login with admin user");
        selenium.refreshPage();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());
        if (!projectsListingPO.isProjectPresent(leaderAdminData.getProjectName())) {
            headerPO.searchItemGloballyByName(leaderAdminData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(leaderAdminData.getProjectName());

        Reporter.log("Step 15 - Click on view file button");
        projectDetails.clickOnViewFileButton();

        Reporter.log("Step 16 - Click on history kebab menu and rename the file");
        projectDetailsFilesListingPO.clickOnKebabMenuAndViewFileHistory(versionFileName.replace(".txt", ""));
        projectDetailsFilesHistoryListingPO.clickOnHistoryKebabMenuAndRenameFileFolder(versionFileName);
        projectDetailsFileListingRenameFileFolderPO.clearAndRenameFileFolderName(leaderEditProjectAdminData);

        Reporter.log("Step 17 - Logout the admin user and login with fundraising user");
        selenium.refreshPage();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(leaderUser);

        Reporter.log("Step 18 - Click on all tabs and find the created project");
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());
        if (!projectsListingPO.isProjectPresent(leaderAdminData.getProjectName())) {
            headerPO.searchItemGloballyByName(leaderAdminData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(leaderAdminData.getProjectName());
        projectDetails.clickOnViewFileButton();

        Reporter.log("Step 19 - Click on kebab menu and view the history of file");
        projectDetailsFilesListingPO.clickOnKebabMenuAndViewFileHistory(versionFileName.replace(".txt", ""));

        Reporter.log("Step 20 - Verify that renamed file is read by leadership user");
        Assert.assertTrue(projectDetailsFilesHistoryListingPO.isFilePresent(leaderEditProjectAdminData.getFileName() + ".txt"), "renamed file is not present : ");
        Assert.assertFalse(projectDetailsFilesHistoryListingPO.isFilePresent(versionFileName), "renamed file is present : ");

    }

    /*Test 8 : Verify that leadership can read project budget */
    @Test(priority = 8)
    public void VerifyThatLeadershipCanReadProjectBudgetSuccessfully() throws InterruptedException {

        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectDetailsBudgetListingPO projectDetailsBudgetListingPO = new ProjectDetailsBudgetListingPO(driver);
        ProjectDetailsPartnershipsListingPO projectDetailsPartnershipsListingPO = new ProjectDetailsPartnershipsListingPO(driver);

        Reporter.log("Step 1 - Entering valid sign in details and login to application");
        new LoginFunctions(driver, selenium).loginByGivenUser(leaderUser);

        Reporter.log("Step 2 - Navigate on partnership detail page and click on project link button");
        selenium.navigateToPage(strLeaderFundingPartnershipUrl);
        selenium.hardWait(3);
        projectDetailsPartnershipsListingPO.clickOnProjectLinkButton();

        Reporter.log("Step 3 - Click on budget detail button");
        projectDetails.clickOnBudgetButton();

        Reporter.log("Step 4 - Verify that project budget is not edited by leadership user");
        Assert.assertFalse(projectDetailsBudgetListingPO.isAddUniversalTemplateButtonEnabled(), "Budget add universal template file button is enabled");
        Assert.assertFalse(projectDetailsBudgetListingPO.isClickToEditButtonLinkPresent(), "Budget click to edit button link is present");

    }

    /*Test 9 : Verify that leadership can add project team member */
    @Test(priority = 9)
    public void VerifyThatLeadershipCanAddProjectTeamMemberSuccessfully() throws InterruptedException {

        ProjectDetailsTeamMembersListingPO projectDetailsTeamMembersListingPO = new ProjectDetailsTeamMembersListingPO(driver);

        Reporter.log("Step 1 -  Login with leadership user and navigate on team member listing page");
        new LoginFunctions(driver, selenium).loginByGivenUser(leaderUser);
        selenium.navigateToPage(strLeaderProjectTeamMemberUrl);
        selenium.hardWait(3);

        Reporter.log("Step 2 - Verify that project add team member button is present or not");
        Assert.assertFalse(projectDetailsTeamMembersListingPO.isAddButtonPresent(), "project member add button is present");
    }

    /*Test 10 : Verify that leadership can not edit project team member */
    @Test(priority = 10)
    public void VerifyThatLeadershipCanNotEditProjectTeamMemberSuccessfully() {

        ProjectMembersUpdateTeamMembersPO projectMembersUpdateTeamMembersPO = new ProjectMembersUpdateTeamMembersPO(driver);

        Reporter.log("Step 1 -  Login with leadership user and edit team member in created project");
        new LoginFunctions(driver, selenium).loginByGivenUser(leaderUser);
        selenium.navigateToPage(strLeaderProjectTeamMemberUrl);
        new TeamMemberFunctions(driver, selenium).editProjectTeamMember(rdFodUser);

        String teamMemberErrorMessage = Constants.teamMemberErrorMessage;

        Reporter.log("Step 2 - Verify that project team member is not edited by leadership user");
        Assert.assertEquals(teamMemberErrorMessage, projectMembersUpdateTeamMembersPO.getTeamMemberErrorText(), "Team member error message is not matched");
    }

    /*Test 11 : Verify that leadership can not create project partnership */ //todo Bug(#850)
    @Test(priority = 11)
    public void VerifyThatLeadershipCanNotCreateProjectPartnership() throws InterruptedException {

        HeaderPO headerPO = new HeaderPO(driver);
        ProjectDetailsPO projectDetailsPO = new ProjectDetailsPO(driver);
        ProjectsListingPO projectsListingPO = new ProjectsListingPO(driver);
        ProjectDetailsPartnershipsListingPO projectDetailsPartnershipsListingPO = new ProjectDetailsPartnershipsListingPO(driver);

        Reporter.log("Step 1 - Login with leadership user");
        new LoginFunctions(driver, selenium).loginByGivenUser(leaderUser);

        Reporter.log("Step 2 - Search created project by admin user");
        if (!projectsListingPO.isProjectPresent(leaderAdminData.getProjectName())) {
            headerPO.searchItemGloballyByName(leaderAdminData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(leaderAdminData.getProjectName());

        Reporter.log("Step 3 - Click on the partnership button");
        projectDetailsPO.clickOnPartnershipsButton();

        Reporter.log("Step 4 - Verify that project partnership is not created by leadership user");
        Assert.assertFalse(projectDetailsPartnershipsListingPO.isProjectAddPartnershipButtonPresent(), "Partnership add button is not matched");

    }

    /*Test 12 : Verify that leadership can not edit project partnership*/
    @Test(priority = 12)
    public void VerifyThatLeadershipCanNotEditProjectPartnership() throws InterruptedException {

        ProjectDetailsPartnershipsListingPO partnershipsListingPO = new ProjectDetailsPartnershipsListingPO(driver);
        ProjectDetailsPartnershipsEditPO projectDetailsPartnershipsEditPO = new ProjectDetailsPartnershipsEditPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with leadership user");
        new LoginFunctions(driver, selenium).loginByGivenUser(leaderUser);
        selenium.navigateToPage(strLeaderPartnershipUrl);

        selenium.hardWait(3);

        Reporter.log("Step 2 - Click on partnership edit button");
        partnershipsListingPO.clickOnEditOrganizationButton();

        Reporter.log("Step 3 - Verify that project partnership is not edited by leadership user");
        Assert.assertFalse(projectDetailsPartnershipsEditPO.isPartnershipTypesButtonEnabled(), "Partnership Types checkbox is enabled : ");
        Assert.assertFalse(projectDetailsPartnershipsEditPO.isFinancialReportingTypeRadioButtonEnabled(), "Partnership Financial Reporting Type radio button is enabled : ");
        Assert.assertFalse(projectDetailsPartnershipsEditPO.isAgreementStatusEnabled(), "Partnership Agreement  Status radio button is enabled : ");
        Assert.assertFalse(projectDetailsPartnershipsEditPO.isMouStatusEnabled(), "Partnership Mou Status radio button is enabled : ");
        Assert.assertFalse(projectDetailsPartnershipsEditPO.isMouStartDateTextBoxEnabled(), "Partnership mou start date text box is enabled : ");
        Assert.assertFalse(projectDetailsPartnershipsEditPO.isMouEndDateTextBoxEnabled(), "Partnership  mou end date text box is enabled : ");

    }

    /*Test 13 : Verify that leadership can not create project internship engagements*/ //todo Bug(#850)
    @Test(priority = 13)
    public void VerifyThatLeadershipCanNotCreateProjectInternshipEngagements() {

        ProjectDetailsCreateInternshipPO projectDetailsCreateInternshipPO = new ProjectDetailsCreateInternshipPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with leadership user");
        new LoginFunctions(driver, selenium).loginByGivenUser(leaderUser);

        Reporter.log("Step 2 - Try to create internship engagement with leadership user");
        new InternshipEngagementsFunctions(driver, selenium).createProjectInternshipEngagements(leaderUser, leaderCreatedProjectName);

        String internshipErrorMessage = Constants.engagementErrorMessage;

        Reporter.log("Step 3 - Verify tha user can not create internship engagement");
        Assert.assertEquals(internshipErrorMessage, projectDetailsCreateInternshipPO.getInternshipErrorMessage(), "project internship error message doesn't match : ");
    }

    /*Test 14 : Verify that leadership can not edit project internship engagements except communications CompleteDate */ //todo Bug(#850)
    @Test(priority = 14)
    public void VerifyThatLeadershipCanNotEditProjectInternshipEngagementsExceptCommunicationDate() throws InterruptedException {

        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectInternshipDetailsPO projectInternshipDetailsPO = new ProjectInternshipDetailsPO(driver);
        ProjectInternshipDetailsUpdateCountryOfOriginPO projectInternshipDetailsUpdateCountryOfOriginPO = new ProjectInternshipDetailsUpdateCountryOfOriginPO(driver);
        ProjectInternshipDetailsUpdateInternPositionPO projectInternshipDetailsUpdateInternPositionPO = new ProjectInternshipDetailsUpdateInternPositionPO(driver);
        ProjectEngagementDetailsUpdateStartEndDatePO projectEngagementDetailsUpdateStartEndDatePO = new ProjectEngagementDetailsUpdateStartEndDatePO(driver);
        ProjectInternshipLanguageDetailsUpdateStatusPO projectInternshipLanguageDetailsUpdateStatusPO = new ProjectInternshipLanguageDetailsUpdateStatusPO(driver);
        ProjectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO projectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO = new ProjectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO(driver);
        ProjectEngagementDetailsUpdateDisbursementCompleteDatePO projectEngagementDetailsUpdateDisbursementCompleteDatePO = new ProjectEngagementDetailsUpdateDisbursementCompleteDatePO(driver);

        Reporter.log("Step 1 -  Login with leadership user and navigate in internship url");
        new LoginFunctions(driver, selenium).loginByGivenUser(leaderUser);
        selenium.navigateToPage(strLeaderInternshipUrl);

        Reporter.log("Step 2 - Try to edit internship engagement status");
        projectDetails.clickOnEditInternshipButton(adminUser.getRealFirstName() + " " + adminUser.getRealLastName());

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
        String disableEditButtonColor = Constants.disableEditButtonColorCode;
        Assert.assertEquals(disableEditButtonColor, projectInternshipDetailsPO.getEditButtonColor(), "edit button color doesn't match : ");

    }

    /*Test 15 : Verify that leadership can not edit project location */
    @Test(priority = 15)
    public void VerifyThatLeadershipCanNotEditProjectLocation() throws InterruptedException {

        HeaderPO headerPO = new HeaderPO(driver);
        LocationsListingPO locationsListingPO = new LocationsListingPO(driver);
        LocationDetailsPO locationDetailsPO = new LocationDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with leadership user");
        new LoginFunctions(driver, selenium).loginByGivenUser(leaderUser);

        Reporter.log("Step 2 - Enter location name and click on view location button");
        headerPO.searchItemGloballyByName(leaderLocationName);
        locationsListingPO.clickOnFirstViewLocationButton();

        Reporter.log("Step 3 - Verify the location edit button is not present");
        Assert.assertFalse(locationDetailsPO.isLocationEditButtonPresent(), "Location edit button is present:");
    }

    /*Test 16 : Verify that leadership can not create language partner project and location */
    @Test(priority = 16)
    public void VerifyThatLeadershipCanNotCreateLanguagePartnerProjectAndLocation() throws InterruptedException {

        LeftNavPO leftNavPO = new LeftNavPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with leadership user");
        new LoginFunctions(driver, selenium).loginByGivenUser(leaderUser);

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

    /*Test 17 : Verify that leadership can not create project language engagements */  //todo Bug(#850)
    @Test(priority = 17)
    public void VerifyThatLeadershipCanNotCreateProjectLanguageEngagements() {

        ProjectDetailsCreateLanguageEngagementPO projectDetailsCreateLanguageEngagementPO = new ProjectDetailsCreateLanguageEngagementPO(driver);

        Reporter.log("Step 1 - Login with leadership user");
        new LoginFunctions(driver, selenium).loginByGivenUser(leaderUser);

        Reporter.log("Step 2 - Try to create language engagement with leadership user");
        new LanguageFunctions(driver, selenium).createProjectLanguageEngagements(leaderLanguageAdminData, leaderCreatedTransEngagementProjectName, leaderUser);

        String languageEngagementErrorMessage = Constants.engagementErrorMessage;

        Reporter.log("Step 3 - Verify tha user can not create language engagement");
        Assert.assertEquals(languageEngagementErrorMessage, projectDetailsCreateLanguageEngagementPO.getLanguageEngagementErrorMessage(), "project language engagement error message doesn't match : ");
    }

    /*Test 18 : Verify that leadership can not edit language engagements details */
    @Test(priority = 18)
    public void VerifyThatLeadershipCanNotEditProjectLanguageEngagements() throws InterruptedException {

        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectLanguageEngagementDetailsPO projectLanguageEngagementDetailsPO = new ProjectLanguageEngagementDetailsPO(driver);
        ProjectEngagementDetailsUpdateStartEndDatePO projectEngagementDetailsUpdateStartEndDatePO = new ProjectEngagementDetailsUpdateStartEndDatePO(driver);
        ProjectInternshipLanguageDetailsUpdateStatusPO projectInternshipLanguageDetailsUpdateStatusPO = new ProjectInternshipLanguageDetailsUpdateStatusPO(driver);
        ProjectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO projectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO = new ProjectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO(driver);
        ProjectEngagementDetailsUpdateDisbursementCompleteDatePO projectEngagementDetailsUpdateDisbursementCompleteDatePO = new ProjectEngagementDetailsUpdateDisbursementCompleteDatePO(driver);

        Reporter.log("Step 1 -  Login with leadership user and navigate in language engagement url");
        new LoginFunctions(driver, selenium).loginByGivenUser(leaderUser);
        selenium.navigateToPage(strLeaderLanguageTranslationUrl);

        Reporter.log("Step 2 - Click on view details button");
        projectDetails.clickOnViewDetailsLanguageEngagementButton(leaderLanguageAdminData.getLanguageEngagementName());

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

        Reporter.log("Step 8 - Try to edit language engagement disbursement complete date");
        Assert.assertFalse(projectEngagementDetailsUpdateDisbursementCompleteDatePO.isInternshipDisbursementCompleteDateTextBoxEnabled(), "Language engagement disbursement complete date textBox is enabled : ");

        Reporter.log("Step 9 - Try to edit language engagement actual estimated date");
        Assert.assertFalse(projectLanguageEngagementDetailsPO.isEstimatedDateTextBoxEnabled(), "Language engagement estimated date textBox is enabled : ");
        Assert.assertFalse(projectLanguageEngagementDetailsPO.isActualDateTextBoxEnabled(), "Language engagement actual date textBox is enabled : ");

    }

    /*Test 19 : Verify that leadership can not Add project goals */ //todo Bug(#2128)
    @Test(priority = 18)
    public void VerifyThatLeadershipCanNotAddProjectGoals() throws InterruptedException {

        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectLanguageEngagementDetailsPO projectLanguageEngagementDetailsPO = new ProjectLanguageEngagementDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with leadership user");
        new LoginFunctions(driver, selenium).loginByGivenUser(leaderUser);
        selenium.navigateToPage(strLeaderLanguageTranslationUrl);
        selenium.hardWait(3);

        Reporter.log("Step 2 - Click on view details button");
        projectDetails.clickOnViewDetailsLanguageEngagementButton(leaderLanguageAdminData.getLanguageEngagementName());

        Reporter.log("Step 3 - Verify the Add goal button is not present on language engagement detail page");
        Assert.assertFalse(projectLanguageEngagementDetailsPO.isAddGoalButtonPresent(), "Add goal button is present ");

    }

    /*Test 20 : Verify that leadership can read created project goals */
    @Test(priority = 20)
    public void VerifyThatLeadershipCanReadProjectGoals() throws InterruptedException {

        CreateGoalPO createGoalPO = new CreateGoalPO(driver);
        ProjectLanguageEngagementDetailsPO projectLanguageEngagementDetailsPO = new ProjectLanguageEngagementDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with leadership user");
        new LoginFunctions(driver, selenium).loginByGivenUser(leaderUser);
        selenium.navigateToPage(strLeaderProjectCreateGoalUrl);
        selenium.hardWait(3);

        Reporter.log("Step 2 - Click on Created goal");
        projectLanguageEngagementDetailsPO.clickOnGoalCard();

        Reporter.log("Step 3 - Click on goal edit button");
        projectLanguageEngagementDetailsPO.clickEditGoalButton();

        Reporter.log("Step 4 - Verify that leadership can read created project goals");
        String disableGoalColor = Constants.disableGrayColorCode;
        Assert.assertEquals(disableGoalColor, createGoalPO.getDisabledEditGoalDetailsColor(), "Goal detail color doesn't match : ");

    }

    /*Test 21 : Verify that leadership can not edit projects */
    @Test(priority = 21)
    public void VerifyThatLeadershipCanNotEditProjectsSuccessfully() throws InterruptedException {

        HeaderPO headerPO = new HeaderPO(driver);
        LeftNavPO sideHeader = new LeftNavPO(driver);
        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectsListingPO projectsListingPO = new ProjectsListingPO(driver);
        ProjectDetailsUpdateStepsPO projectDetailsUpdateStepsPO = new ProjectDetailsUpdateStepsPO(driver);
        ProjectDetailsUpdateEstimatedSubmissionDatePO projectDetailsUpdateEstimatedSubmissionDatePO = new ProjectDetailsUpdateEstimatedSubmissionDatePO(driver);
        ProjectDetailsUpdateStartEndDatePO projectDetailsUpdateStartEndDatePO = new ProjectDetailsUpdateStartEndDatePO(driver);
        ProjectDetailsUpdateLocationAndFieldRegionPO projectDetailsUpdateLocationAndFieldRegionPO = new ProjectDetailsUpdateLocationAndFieldRegionPO(driver);

        Reporter.log("Step 1 -  Login with leadership user and edit created project");
        new LoginFunctions(driver, selenium).loginByGivenUser(leaderUser);

        Reporter.log("Step 2 - Verify that project is displayed which is edited by leadership user ");
        sideHeader.clickOnProjectsMenu();
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());

        if (!projectsListingPO.isProjectPresent(leaderAdminData.getProjectName())) {
            headerPO.searchItemGloballyByName(leaderAdminData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(leaderAdminData.getProjectName());

        Reporter.log("Step 3 - Click on project location and field region button and try to edit with leadership user ");
        projectDetails.clickProjectLocationAndFieldRegionButton();

        Reporter.log("Step 4 - Verify that leadership can't edit location and field region");
        Assert.assertFalse(projectDetailsUpdateLocationAndFieldRegionPO.isFieldRegionTextBoxEnabled(), "project field region textBox is enabled : ");
        Assert.assertFalse(projectDetailsUpdateLocationAndFieldRegionPO.isLocationTextBoxEnabled(), "project location textBox is enabled : ");

        Reporter.log("Step 5 - Click on location field region close button");
        projectDetailsUpdateLocationAndFieldRegionPO.clickOnLocationFieldRegionCloseButton();

        Reporter.log("Step 6 - Click on project start end date button");
        projectDetails.clickProjectStartEndDateButton();

        Reporter.log("Step 7 - Verify that leadership can't edit start end date");
        Assert.assertFalse(projectDetailsUpdateStartEndDatePO.isStartDateTextBoxEnabled(), "project start date textBox is enabled : ");
        Assert.assertFalse(projectDetailsUpdateStartEndDatePO.isEndDateTextBoxEnabled(), "project end date textBox is enabled: ");

        Reporter.log("Step 8 - Click on start end date close button");
        projectDetailsUpdateStartEndDatePO.clickOnStartEndDateCloseButton();

        Reporter.log("Step 9 - Click on project estimated submission date button and try to edit with leadership user ");
        projectDetails.clickProjectEstimatedSubmissionButton();

        Reporter.log("Step 10 - Verify that leadership can't edit estimated submission date");
        Assert.assertFalse(projectDetailsUpdateEstimatedSubmissionDatePO.isEstimatedSubmissionDateTextBoxEnabled(), "project estimated submission date textBox is enabled:");

        Reporter.log("Step 11 - Click on estimated submission date close button");
        projectDetailsUpdateEstimatedSubmissionDatePO.clickOnEstimatedSubmissionDateCloseButton();

        Reporter.log("Step 12 - Click on project status button and try to edit with leadership user ");
        projectDetails.clickOnProjectStepByName(ProjectStep.EarlyConversations.getStep());

        Reporter.log("Step 13 - Verify that leadership can't edit status");
        String statusErrorMessage = Constants.projectStatusErrorMessage;
        Assert.assertEquals(statusErrorMessage, projectDetailsUpdateStepsPO.getProjectStatusError(), "project status error text doesn't match : ");
    }

}

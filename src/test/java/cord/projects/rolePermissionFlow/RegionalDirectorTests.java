package cord.projects.rolePermissionFlow;

import base.BaseTest;
import datafactory.*;
import dataobjects.*;
import enums.goal.GoalDistributionMethods;
import enums.menu.CreateItemTypes;
import enums.partner.PartnerType;
import enums.partnership.PartnershipFinancialReportingTypes;
import enums.partnership.PartnershipStatus;
import enums.project.ProjectInternPosition;
import enums.project.ProjectInternshipMethodologies;
import enums.project.ProjectSubTabs;
import enums.project.Sensitivity;
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
import pageobjects.cord.projects.intershipEngagement.ProjectInternshipDetailsPO;
import pageobjects.cord.projects.languageEngagement.ProjectLanguageEngagementDetailsPO;
import pageobjects.cord.projects.languageEngagement.goal.GoalDetailsPO;
import utilities.DriverManager;
import utilities.JavaHelpers;
import utilities.SeleniumHelpers;

import java.util.Arrays;

public class RegionalDirectorTests extends BaseTest {

    protected String strPeopleUrl;
    protected String strLanguageUrl;
    protected String strInternshipUrl;
    protected String strPartnershipUrl;
    protected String strLanguageTranslationUrl;
    protected String strProjectCreateGoalUrl;
    protected String strProjectTeamMemberUrl;
    protected String strFundingPartnershipUrl;
    People rdPeopleFpmData = new PeopleData().getPeopleData();
    Budget rdBudgetData = new BudgetData().getBudgetData();
    Project rdAdminData = new ProjectData().getCreateProjectData();
    Project rdAdminTranslationData = new ProjectData().getCreateTranslationProjectData();
    Project rdAdminBudgetData = new ProjectData().getCreateProjectData();
    String rdCreatedBudgetProjectName = rdAdminBudgetData.getProjectName();
    String rdCreatedTranslationProjectName = rdAdminTranslationData.getProjectName();
    String rdCreatedProjectName = rdAdminData.getProjectName();
    People rdPeopleAdminData = new PeopleData().getPeopleData();
    People rdEditPeopleAdminData = new PeopleData().getPeopleData();
    Goal rdGoalData = new GoalData().getCreateGoalData();
    Project rdEditProjectAdminData = new ProjectData().getUpdateProjectData();
    Language rdLanguageAdminData = new LanguageData().getLanguageData();
    Partner rdPartnerAdminData = new PartnerData().getCreatePartnerData();
    Partner rdBudgetPartnerAdminData = new PartnerData().getCreatePartnerData();
    Partner rdEditPartnerAdminData = new PartnerData().getEditPartnerData();
    Project rdFpmData = new ProjectData().getCreateProjectData();
    Goal rdEditGoalData = new GoalData().getCreateGoalData();
    Location rdLocationData = new LocationData().getLocationData();
    Project rdEditProjectBudgetData = new ProjectData().getUpdateProjectData();
    String rdLocationName = rdLocationData.getLocationName();
    Internship rdInternUpdateData = new EngagementData().getUpdateEngagementData();
    Internship rdInternData = new EngagementData().getCreateEngagementData();

    public RegionalDirectorTests() throws ParseException, java.text.ParseException, InterruptedException {
    }

    public static final String PROPERTY_TEST_FILE = "src/main/resources/testData/TestFiles.properties";

    @BeforeClass
    public void setupTestData() throws InterruptedException {

        WebDriver driver;
        DriverManager drivermanager;

        drivermanager = new DriverManager();
        driver = drivermanager.setUp("chrome");
        SeleniumHelpers selenium = new SeleniumHelpers(driver);

        Reporter.log("Step 1 - Create Project by admin user, add regional director and logout that user");
        new LoginFunctions(driver, selenium).loginByGivenUser(adminUser);
        new ProjectFunctions(driver, selenium).createProject(rdAdminData);
        new ProjectFunctions(driver, selenium).addMemberToProject(rdUser, rdAdminData);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 2 - Create Budget Project by admin user and logout that user");
        new ProjectFunctions(driver, selenium).createProject(rdAdminBudgetData);
        new ProjectFunctions(driver, selenium).addMemberToProject(rdUser, rdAdminBudgetData);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 3 - Create TranslationType Project by admin user, add regional director and logout that user");
        new ProjectFunctions(driver, selenium).createProject(rdAdminTranslationData);
        new ProjectFunctions(driver, selenium).addMemberToProject(rdUser, rdAdminTranslationData);
        new PartnershipFunctions(driver, selenium).createProjectPartnership(rdPartnerAdminData, rdCreatedTranslationProjectName, adminUser);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(fpmUser);

        Reporter.log("Step 4 - Create Project by project manager and add regional director that project");
        new ProjectFunctions(driver, selenium).createProject(rdFpmData);
        new ProjectFunctions(driver, selenium).addMemberToProject(rdUser, rdFpmData);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 5 - Create People by admin user");
        new PeopleFunctions(driver, selenium).createPeople(rdPeopleAdminData, adminUser);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(fpmUser);

        Reporter.log("Step 6 - Create People by project manager user");
        new PeopleFunctions(driver, selenium).createPeople(rdPeopleFpmData, fpmUser);
        strPeopleUrl = driver.getCurrentUrl();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 7 - Create Language by admin user");
        new LanguageFunctions(driver, selenium).createLanguage(rdLanguageAdminData);
        strLanguageUrl = driver.getCurrentUrl();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(rdUser);

        Reporter.log("Step 8 - Create internship engagements by regional director user");
        new InternshipEngagementsFunctions(driver, selenium).createProjectInternshipEngagements(rdUser, rdCreatedProjectName);
        strInternshipUrl = driver.getCurrentUrl();
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(rdUser);

        Reporter.log("Step 9 - Create language engagements by regional director user");
        new LanguageFunctions(driver, selenium).createProjectLanguageEngagements(rdLanguageAdminData, rdCreatedTranslationProjectName, rdUser);
        strLanguageTranslationUrl = driver.getCurrentUrl();
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 10 - Create Partner by admin user");
        new PartnerFunctions(driver, selenium).createPartner(rdPartnerAdminData);
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(rdUser);

        Reporter.log("Step 11 - Create partnership by regional director user");
        new PartnershipFunctions(driver, selenium).createProjectPartnership(rdPartnerAdminData, rdCreatedProjectName, rdUser);
        strPartnershipUrl = driver.getCurrentUrl();
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(rdUser);

        Reporter.log("Step 12 - Add project team member with multiple user role");
        new ProjectFunctions(driver, selenium).addMemberToProject(rdFodUser, rdAdminData);
        strProjectTeamMemberUrl = driver.getCurrentUrl();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 13 - Create Location by admin user");
        new LocationFunctions(driver, selenium).createLocation(rdLocationData);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(rdUser);

        Reporter.log("Step 14 - Create goal by regional director user");
        selenium.navigateToPage(strLanguageTranslationUrl);
        new GoalFunctions(driver, selenium).createGoal(rdGoalData, rdLanguageAdminData.getLanguageEngagementName(), rdPartnerAdminData.getOrganizationName());
        strProjectCreateGoalUrl = driver.getCurrentUrl();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 15 - Create funding partnership by admin user");
        new ProjectFunctions(driver, selenium).editInternshipProjectByName(rdEditProjectBudgetData, rdCreatedBudgetProjectName, adminUser);
        new PartnershipFunctions(driver, selenium).createProjectFundingPartnership(rdBudgetPartnerAdminData, rdEditProjectBudgetData.getProjectName());
        selenium.hardWait(3);
        strFundingPartnershipUrl = driver.getCurrentUrl();
        drivermanager.tearDown();

    }

    /*Test 1 : Verify that regional director can view all projects*/
    @Test(priority = 1)
    public void VerifyRegionalDirectorCanViewAllProjectsSuccessfully() throws InterruptedException {

        LeftNavPO sideHeader = new LeftNavPO(driver);
        ProjectsListingPO projectsListingPO = new ProjectsListingPO(driver);
        ProjectDetailsPO projectDetailsPO = new ProjectDetailsPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with regional director user");
        new LoginFunctions(driver, selenium).loginByGivenUser(rdUser);

        Reporter.log("Step 2 - Verify that projects is displayed which is created by admin");
        sideHeader.clickOnProjectsMenu();
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());

        if (!projectsListingPO.isProjectPresent(rdAdminData.getProjectName())) {
            headerPO.searchItemGloballyByName(rdAdminData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(rdAdminData.getProjectName());
        Assert.assertEquals(projectDetailsPO.getProjectName(), rdAdminData.getProjectName(), "Project is not present : " + rdAdminData.getProjectName());

        Reporter.log("Step 3 - Verify that projects is displayed which is created by FPM user");
        sideHeader.clickOnProjectsMenu();
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());

        if (!projectsListingPO.isProjectPresent(rdFpmData.getProjectName())) {
            headerPO.searchItemGloballyByName(rdFpmData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(rdFpmData.getProjectName());
        Assert.assertEquals(projectDetailsPO.getProjectName(), rdFpmData.getProjectName(), "Project is not present : " + rdFpmData.getProjectName());

    }

    /*Test 2 : Verify that regional director can view all peoples*/
    @Test(priority = 2)
    public void VerifyRegionalDirectorCanViewAllPeoplesSuccessfully() throws InterruptedException {

        LeftNavPO sideHeader = new LeftNavPO(driver);
        PeoplesPO peoples = new PeoplesPO(driver);
        PeopleDetailsPO peopleDetails = new PeopleDetailsPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with regional director user");
        selenium.navigateToPage(strPeopleUrl);
        new LoginFunctions(driver, selenium).loginByGivenUser(rdUser);

        Reporter.log("Step 2 - Verify that projects is displayed which is created by admin");
        sideHeader.clickOnPeopleMenu();
        if (!peoples.isPeoplePresent(rdPeopleAdminData.getPersonName())) {
            headerPO.searchItemGloballyByName(rdPeopleAdminData.getPersonName());
        }
        peoples.clickOnPeopleName(rdPeopleAdminData.getPersonName());
        Assert.assertEquals(peopleDetails.getPeopleName().replaceAll("\\s.*", ""), rdPeopleAdminData.getPersonName(), "Project is not present : " + rdPeopleAdminData.getPersonName());

        Reporter.log("Step 3 - Verify that projects is displayed which is created by FPM user");
        sideHeader.clickOnPeopleMenu();
        if (!peoples.isPeoplePresent(rdPeopleFpmData.getPersonName())) {
            headerPO.searchItemGloballyByName(rdPeopleFpmData.getPersonName());
        }
        peoples.clickOnPeopleName(rdPeopleFpmData.getPersonName());
        Assert.assertEquals(peopleDetails.getPeopleName().replaceAll("\\s.*", ""), rdPeopleFpmData.getPersonName(), "Project is not present : " + rdPeopleFpmData.getPersonName());

    }

    /*Test 3 : Verify that regional director can edit mine peoples */
    @Test(priority = 3)
    public void VerifyThatRegionalDirectorCanEditMinePeoples() throws InterruptedException {

        PeoplesPO peoples = new PeoplesPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);
        LeftNavPO sideHeader = new LeftNavPO(driver);
        UpdatePeoplePO updatePeople = new UpdatePeoplePO(driver);
        PeopleDetailsPO peopleDetails = new PeopleDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with regional director user");
        new LoginFunctions(driver, selenium).loginByGivenUser(rdUser);

        Reporter.log("Step 2 - Search user which have a regional director role by email and click on people name");
        sideHeader.clickOnPeopleMenu();
        if (!peoples.isPeoplePresent(rdUser.getEmail())) {
            headerPO.searchItemGloballyByName(rdUser.getEmail());
        }
        peoples.clickOnPeopleName(rdUser.getRealFirstName() + " " + rdUser.getRealLastName());

        Reporter.log("Step 3 - Click on edit icon and edit mine person detail");
        peopleDetails.clickOnEditButton();
        updatePeople.clearAndUpdatePersonDetail(rdEditPeopleAdminData);
        updatePeople.clickOnSubmitButton();

        Reporter.log("Step 4 - Verify updated person details");
        Assert.assertEquals(rdEditPeopleAdminData.getPersonEmail(), peopleDetails.getEmail(), "email doesn't match : ");
        Assert.assertEquals(rdEditPeopleAdminData.getPersonTitle(), peopleDetails.getTitle(), "title doesn't match : ");
        Assert.assertEquals(rdEditPeopleAdminData.getPersonPhone(), peopleDetails.getPhoneNumber(), "phone number doesn't match : ");
        Assert.assertEquals(rdEditPeopleAdminData.getPersonAbout(), peopleDetails.getAbout(), "About doesn't match : ");
        Assert.assertEquals(UserRole.RegionalDirector.getRole(), peopleDetails.getRoles(), "Roles doesn't match : ");

        Reporter.log("Step 5 - Again Click on edit icon and reset the people");
        peopleDetails.clickOnEditButton();
        updatePeople.fillFirstnameLastNameAndEmailDetail(rdUser.getRealFirstName(), rdUser.getRealLastName(), rdUser.getEmail());
        updatePeople.clickOnSubmitButton();
        Assert.assertEquals(rdUser.getRealFirstName() + " " + rdUser.getRealLastName(), peopleDetails.getPeopleName(), "personName doesn't match : ");
        Assert.assertEquals(rdUser.getEmail(), peopleDetails.getEmail(), "personEmail doesn't match : ");
    }

    /*Test 4 : Verify that regional director can view all languages and edit icon is not present*/
    @Test(priority = 4)
    public void VerifyRegionalDirectorCanViewAllLanguagesAndEditIconIsNotPresent() throws InterruptedException {

        LeftNavPO sideHeader = new LeftNavPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);
        LanguagesPO languages = new LanguagesPO(driver);
        LanguageDetailPO languageDetailPO = new LanguageDetailPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with regional director user");
        selenium.navigateToPage(strLanguageUrl);
        new LoginFunctions(driver, selenium).loginByGivenUser(rdUser);

        Reporter.log("Step 2 - Verify that language is displayed which is created by admin and edit icon is not present");
        sideHeader.clickOnLanguagesMenu();
        if (!languages.isLanguagePresent(rdLanguageAdminData.getLanguageName())) {
            headerPO.searchItemGloballyByName(rdLanguageAdminData.getLanguageName());
        }
        languages.clickOnLanguageName(rdLanguageAdminData.getLanguageName());
        Assert.assertEquals(languageDetailPO.getLanguageName(), rdLanguageAdminData.getPubliicName(), "Language is not present : " + rdLanguageAdminData.getPubliicName());
        Assert.assertFalse(languageDetailPO.isEditIconPresent(), "Language edit icon is present : ");

    }

    /*Test 5 : Verify that regional director can view all partners*/
    @Test(priority = 5)
    public void VerifyRegionalDirectorCanViewAllPartnersSuccessfully() throws InterruptedException {

        HeaderPO headerPO = new HeaderPO(driver);
        LeftNavPO sideHeader = new LeftNavPO(driver);
        PartnersListingPO partnerListing = new PartnersListingPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with regional director user");
        new LoginFunctions(driver, selenium).loginByGivenUser(rdUser);

        Reporter.log("Step 2 - Verify that partner is displayed which is created by admin");
        sideHeader.clickOnPartnersMenu();
        if (!partnerListing.isPartnerPresent(rdPartnerAdminData.getPartnerName())) {
            headerPO.searchItemGloballyByName(rdPartnerAdminData.getPartnerName());
        }
        Assert.assertTrue(partnerListing.isPartnerPresent(rdPartnerAdminData.getPartnerName()), "Partner is not present : " + rdPartnerAdminData.getPartnerName());

    }

    /*Test 6 : Verify that regional director can't edit all partners details */ //Todo bug(#762) Global R access
    @Test(priority = 6)
    public void VerifyThatRegionalDirectorCanNotEditAllPartnersDetails() throws InterruptedException {

        PartnerDetailsPO partnerDetailsPO = new PartnerDetailsPO(driver);
        EditPartnerPO editPartnerPO = new EditPartnerPO(driver);

        Reporter.log("Step 1 - Login with  regional director user");
        new LoginFunctions(driver, selenium).loginByGivenUser(rdUser);

        Reporter.log("Step 2 - Verify that partner global innovations client is not edited by  regional director user ");
        new PartnerFunctions(driver, selenium).editPartnerGlobalInnovationsClient(rdPartnerAdminData.getPartnerName());
        Assert.assertTrue(editPartnerPO.isGlobalInnovationsClientErrorMessagePresent(), "Partner global innovations client error message is not present : ");
        editPartnerPO.clickOnCancelButton();

        Reporter.log("Step 3 - Verify that partner active inActive status is not edited by  regional director user ");
        new PartnerFunctions(driver, selenium).editPartnerActiveInActiveStatus(rdPartnerAdminData.getPartnerName());
        Assert.assertTrue(editPartnerPO.isActiveInActiveErrorMessagePresent(), "Partner active inActive error message is not present : ");
        editPartnerPO.clickOnCancelButton();

        Reporter.log("Step 4 - Verify that partner pmc entity code is not edited by  regional director user ");
        new PartnerFunctions(driver, selenium).editPartnerPmcEntityCode(rdPartnerAdminData.getPartnerName(), rdEditPartnerAdminData);
        Assert.assertTrue(editPartnerPO.isPmcEntityCodeErrorMessagePresent(), "Partner pmc entity code error message is not present : ");
        editPartnerPO.clickOnCancelButton();

        Reporter.log("Step 5 - Verify that partner point of contact is not edited by  regional director user ");
        new PartnerFunctions(driver, selenium).editPartnerPointOfContact(rdPartnerAdminData.getPartnerName(), rdUser);
        Assert.assertTrue(editPartnerPO.isPointOfContactErrorMessagePresent(), "Partner point of contact error message is not present : ");
        editPartnerPO.clickOnCancelButton();

        Reporter.log("Step 6 - Verify that partner types address is not edited by  regional director user ");
        Assert.assertFalse(partnerDetailsPO.isPartnerTypesEditButtonEnabled(), "Partner types edit button is enabled : ");
        Assert.assertFalse(partnerDetailsPO.isPartnerAddressEditButtonEnabled(), "Partner address edit button is enabled: ");

    }

    /*Test 7 : Verify that regional director can upload/Edit project files */
    @Test(priority = 7)
    public void VerifyThatRegionalDirectorCanUploadProjectFilesSuccessfully() throws InterruptedException {

        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectDetailsFilesListingPO projectDetailsFilesListingPO = new ProjectDetailsFilesListingPO(driver);
        ProjectDetailsFilesHistoryListingPO projectDetailsFilesHistoryListingPO = new ProjectDetailsFilesHistoryListingPO(driver);
        ProjectDetailsFileListingRenameFileFolderPO projectDetailsFileListingRenameFileFolderPO = new ProjectDetailsFileListingRenameFileFolderPO(driver);
        ProjectDetailsFileListingDeleteFileFolderPO projectDetailsFileListingDeleteFileFolderPO = new ProjectDetailsFileListingDeleteFileFolderPO(driver);

        String textFileName = JavaHelpers.getPropertyValue(PROPERTY_TEST_FILE, "textFile");

        Reporter.log("Step 1 - Entering valid sign in details and login to application");
        new LoginFunctions(driver, selenium).loginByGivenUser(rdUser);

        Reporter.log("Step 2 -  Login with regional director user and upload file in created project");
        new FileFunctions(driver, selenium).uploadFile(rdCreatedProjectName, textFileName);

        Reporter.log("Step 3 - Verify that project file is uploaded by regional director user ");
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
        projectDetailsFileListingRenameFileFolderPO.clearAndRenameFileFolderName(rdEditProjectAdminData);
        Assert.assertTrue(projectDetailsFilesHistoryListingPO.isFilePresent(rdEditProjectAdminData.getFileName() + ".txt"), "renamed file is not present : ");
        Assert.assertFalse(projectDetailsFilesHistoryListingPO.isFilePresent(versionFileName), "renamed file is present : ");

        Reporter.log("Step 8 - Click on history kebab menu and delete created file");
        projectDetailsFilesHistoryListingPO.clickOnKebabMenuAndDeleteFileFolder(projectDetailsFilesListingPO.getFirstFileUploadText());
        projectDetailsFileListingDeleteFileFolderPO.clickOnFileDeleteButton();
        Assert.assertFalse(projectDetailsFilesListingPO.isFileFolderPresent(rdEditProjectAdminData.getFileName()), "deleted file is present : ");

    }

    /*Test 8 : Verify that regional director can edit project budget */
    @Test(priority = 8)
    public void VerifyThatRegionalDirectorCanEditProjectBudgetSuccessfully() throws InterruptedException {

        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectDetailsBudgetListingPO projectDetailsBudgetListingPO = new ProjectDetailsBudgetListingPO(driver);
        ProjectDetailsPartnershipsListingPO projectDetailsPartnershipsListingPO = new ProjectDetailsPartnershipsListingPO(driver);
        String textFileName = JavaHelpers.getPropertyValue(PROPERTY_TEST_FILE, "textFile");

        Reporter.log("Step 1 - Entering valid sign in details and login to application");
        new LoginFunctions(driver, selenium).loginByGivenUser(rdUser);

        Reporter.log("Step 2 - Navigate on partnership detail page and click on project link button");
        selenium.navigateToPage(strFundingPartnershipUrl);
        selenium.hardWait(3);
        projectDetailsPartnershipsListingPO.clickOnProjectLinkButton();

        Reporter.log("Step 3 - Click on budget detail button");
        projectDetails.clickOnBudgetButton();

        Reporter.log("Step 4 - Edit budget in created project");
        new BudgetFunctions(driver, selenium).editProjectBudget(textFileName, rdUser, rdBudgetData);

        Reporter.log("Step 5 - Verify that project budget is edited by project manager user");
        Assert.assertEquals(projectDetailsBudgetListingPO.getUploadedTemplateFileName(), textFileName, "project budget template file name doesn't match : ");
        Assert.assertEquals(projectDetailsBudgetListingPO.getBudgetAmountText().replace(",", ""), "$" + rdBudgetData.getBudgetAmount(), "project budget amount doesn't match : "); //Todo bug(#762) Member RW access
    }

    /*Test 9 : Verify that regional director can add project team member */
    @Test(priority = 9)
    public void VerifyThatRegionalDirectorCanAddProjectTeamMemberSuccessfully() throws InterruptedException {
        ProjectDetailsTeamMembersListingPO projectDetailsTeamMembersListingPO = new ProjectDetailsTeamMembersListingPO(driver);

        Reporter.log("Step 1 -  Login with regional director user and add team member in created project");
        new LoginFunctions(driver, selenium).loginByGivenUser(rdUser);
        selenium.navigateToPage(strProjectTeamMemberUrl);
        selenium.hardWait(3);

        Reporter.log("Step 2 - Verify that project team member is added by regional director user");
        Assert.assertEquals(projectDetailsTeamMembersListingPO.getMembersName(rdFodUser.getRealFirstName() + " " + rdFodUser.getRealLastName()), (rdFodUser.getRealFirstName() + " " + rdFodUser.getRealLastName()), "project member name doesn't match : ");
        Assert.assertEquals(projectDetailsTeamMembersListingPO.getMembersRoleByMemberName(rdFodUser.getRealFirstName() + " " + rdFodUser.getRealLastName()), rdFodUser.getUserRoles().get(0), "project member role doesn't match : ");
    }

    /*Test 10 : Verify that regional director can edit project team member */
    @Test(priority = 10)
    public void VerifyThatRegionalDirectorCanEditProjectTeamMemberSuccessfully() throws InterruptedException {

        ProjectDetailsTeamMembersListingPO projectDetailsTeamMembersListingPO = new ProjectDetailsTeamMembersListingPO(driver);

        Reporter.log("Step 1 -  Login with regional director user and edit team member in created project");
        new LoginFunctions(driver, selenium).loginByGivenUser(rdUser);
        selenium.navigateToPage(strProjectTeamMemberUrl);
        selenium.hardWait(3);
        new TeamMemberFunctions(driver, selenium).editProjectTeamMember(rdFodUser);

        Reporter.log("Step 2 - Verify that project team member is edited by regional director user");
        Assert.assertEquals(projectDetailsTeamMembersListingPO.getMembersRoleByMemberName(rdFodUser.getRealFirstName() + " " + rdFodUser.getRealLastName()), rdFodUser.getUserRoles().get(0) + ", " + rdFodUser.getUserRoles().get(1), "project member updated role doesn't match : ");
    }

    /*Test 11 : Verify that regional director can edit projects*/
    @Test(priority = 11)
    public void VerifyThatRegionalDirectorCanEditProjectsSuccessfully() throws InterruptedException, java.text.ParseException {

        LeftNavPO sideHeader = new LeftNavPO(driver);
        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectsListingPO projectsListingPO = new ProjectsListingPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);

        Reporter.log("Step 1 -  Login with regional director user and edit created project");
        new LoginFunctions(driver, selenium).loginByGivenUser(rdUser);
        new ProjectFunctions(driver, selenium).editInternshipProjectByName(rdEditProjectAdminData, rdCreatedProjectName, rdUser);

        Reporter.log("Step 2 - Verify that project is displayed which is edited by regional director user ");
        sideHeader.clickOnProjectsMenu();
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());

        if (!projectsListingPO.isProjectPresent(rdEditProjectAdminData.getProjectName())) {
            headerPO.searchItemGloballyByName(rdEditProjectAdminData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(rdEditProjectAdminData.getProjectName());

        Assert.assertEquals(projectDetails.getProjectName(), rdEditProjectAdminData.getProjectName(), "Project is not present ");
        Assert.assertEquals(Sensitivity.Low.toString(), projectDetails.getProjectSensitivity(), "project sensitivity doesn't match : ");
        Assert.assertEquals(projectDetails.getProjectLocationAndFieldRegion(), rdEditProjectAdminData.getLocationName() + " | " + rdEditProjectAdminData.getFieldRegionName(), "project location and field region doesn't match : ");
        Assert.assertEquals(projectDetails.getProjectStartEndDate(), new JavaHelpers().changeDateFormat(rdEditProjectAdminData.getStartDate(), "MM/dd/yyyy", "M/d/yyyy") + "\n" + " - " + "\n" + new JavaHelpers().changeDateFormat(rdEditProjectAdminData.getEndDate(), "MM/dd/yyyy", "M/d/yyyy"), "project start end date doesn't match : ");
        Assert.assertEquals(new JavaHelpers().changeDateFormat(rdEditProjectAdminData.getEstimatedSubmissionDate(), "MM/dd/yyyy", "M/d/yyyy"), projectDetails.getProjectEstimatedSubmissionDate(), "project estimated submission date doesn't match : ");
    }

    /*Test 12 : Verify that regional director can create project partnership */
    @Test(priority = 12)
    public void VerifyThatRegionalDirectorCanCreateProjectPartnershipSuccessfully() {

        ProjectDetailsPartnershipsListingPO projectDetailsPartnershipsListingPO = new ProjectDetailsPartnershipsListingPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with regional director user");
        new LoginFunctions(driver, selenium).loginByGivenUser(rdUser);
        selenium.navigateToPage(strPartnershipUrl);

        Reporter.log("Step 2 - Verify that project partnership is created by regional director user");
        Assert.assertEquals(projectDetailsPartnershipsListingPO.getPartnershipName(rdPartnerAdminData.getOrganizationName()), rdPartnerAdminData.getOrganizationName(), "project partnership doesn't match : ");
    }

    /*Test 13 : Verify that regional director can edit project partnership when member of the project */
    @Test(priority = 13)
    public void VerifyThatRegionalDirectorCanEditProjectPartnershipWhenMemberOfTheProjectSuccessfully() throws java.text.ParseException, InterruptedException {

        ProjectDetailsPartnershipsListingPO projectDetailsPartnershipsListingPO = new ProjectDetailsPartnershipsListingPO(driver);
        ProjectDetailsPartnershipsEditPO projectDetailsPartnershipsEditPO = new ProjectDetailsPartnershipsEditPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with regional director user");
        new LoginFunctions(driver, selenium).loginByGivenUser(rdUser);
        selenium.navigateToPage(strPartnershipUrl);
        selenium.hardWait(3);
        new PartnershipFunctions(driver, selenium).editProjectPartnership(rdEditProjectAdminData);

        Reporter.log("Step 3 - Click on partnership submit button");
        projectDetailsPartnershipsEditPO.clickOnPartnershipSubmitButton();

        Reporter.log("Step 4 - Verify that project partnership is edited by regional director user");
        Assert.assertEquals(projectDetailsPartnershipsListingPO.getTypeText(rdPartnerAdminData.getOrganizationName()), PartnerType.Managing.getPartnerType(), "partner types doesn't match");
        Assert.assertEquals(projectDetailsPartnershipsListingPO.getFinancialReportingTypeText(rdPartnerAdminData.getOrganizationName()), PartnershipFinancialReportingTypes.Funded.getPartnershipFinancialReportingTypes(), "partnership financial reporting types doesn't match");
        Assert.assertEquals(projectDetailsPartnershipsListingPO.getAgreementStatusText(rdPartnerAdminData.getOrganizationName()), PartnershipStatus.AwaitingSignature.getPartnerShipStatus(), " partnership agreement status doesn't match");
        Assert.assertEquals(projectDetailsPartnershipsListingPO.getMouStatusText(rdPartnerAdminData.getOrganizationName()), PartnershipStatus.NotAttached.getPartnerShipStatus(), " partnership mou status doesn't match");
        Assert.assertEquals(projectDetailsPartnershipsListingPO.getMouDateRangeText(rdPartnerAdminData.getOrganizationName()), new JavaHelpers().changeDateFormat(rdEditProjectAdminData.getStartDate(), "MM/dd/yyyy", "M/d/yyyy") + " - " + new JavaHelpers().changeDateFormat(rdEditProjectAdminData.getEndDate(), "MM/dd/yyyy", "M/d/yyyy"), "project start end date doesn't match : ");

    }

    /*Test 14 : Verify that regional director can create project internship engagements */
    @Test(priority = 14)
    public void VerifyThatRegionalDirectorCanCreateProjectInternshipEngagementsSuccessfully() throws InterruptedException {

        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with regional director user");
        new LoginFunctions(driver, selenium).loginByGivenUser(rdUser);
        selenium.navigateToPage(strInternshipUrl);
        selenium.hardWait(3);

        Reporter.log("Step 2 - Verify the created internship engagement");
        Assert.assertEquals(projectDetails.getInternshipName(rdUser.getRealFirstName() + " " + rdUser.getRealLastName()), (rdUser.getRealFirstName() + " " + rdUser.getRealLastName()), "project internship name doesn't match : ");
    }

    /*Test 15 : Verify that regional director can edit project internship engagements except DisbursementCompleteDate*/ //Todo bug(#864)
    @Test(priority = 15)
    public void VerifyThatRegionalDirectorCanEditProjectInternshipEngagementsSuccessfullyExceptDisbursementCompleteDate() throws java.text.ParseException, InterruptedException {

        ProjectInternshipDetailsPO projectInternshipDetailsPO = new ProjectInternshipDetailsPO(driver);

        Reporter.log("Step 1 -  Login with regional director user and edit internship engagement in created project");
        new LoginFunctions(driver, selenium).loginByGivenUser(rdUser);
        selenium.navigateToPage(strInternshipUrl);
        selenium.hardWait(3);

        new InternshipEngagementsFunctions(driver, selenium).editProjectInternshipEngagements(rdUser);
        String newVersionFileName = JavaHelpers.getPropertyValue(PROPERTY_TEST_FILE, "newVersionFile");

        Reporter.log("Step 2 - Verify that project internship is edited by regional director user");
        Assert.assertEquals(projectInternshipDetailsPO.getInternshipStartEndDate(), new JavaHelpers().changeDateFormat(rdInternUpdateData.getInternshipStartDate(), "MM/dd/yyyy", "M/d/yyyy") + "\n" + " - " + "\n" + new JavaHelpers().changeDateFormat(rdInternUpdateData.getInternshipEndDate(), "MM/dd/yyyy", "M/d/yyyy"), "Internship start end date doesn't match :");
        Assert.assertEquals(ProjectInternPosition.Administration.getInternPosition(), projectInternshipDetailsPO.getInternPosition(), "internship position doesn't match : ");
        Assert.assertEquals(projectInternshipDetailsPO.getGrowthPlanCompleteDate(), new JavaHelpers().changeDateFormat(rdInternUpdateData.getGrowthPlanCompleteDate(), "MM/dd/yyyy", "M/d/yyyy"), "internship growth plan complete date  doesn't match :");
        Assert.assertEquals(newVersionFileName, projectInternshipDetailsPO.getUploadedFileText(), "uploaded file name doesn't match : ");
        Assert.assertEquals(projectInternshipDetailsPO.getMethodologiesText(), ProjectInternshipMethodologies.OneStory.getInternshipMethodology() + "\n" + ProjectInternshipMethodologies.Render.getInternshipMethodology(), "internship methodologies doesn't match : ");
        Assert.assertEquals(projectInternshipDetailsPO.getActualDateText(), new JavaHelpers().changeDateFormat(rdInternUpdateData.getInternshipActualDate(), "MM/dd/yyyy", "M/d/yyyy"), "internship actual date doesn't match : ");
        Assert.assertEquals(projectInternshipDetailsPO.getEstimatedDateText(), new JavaHelpers().changeDateFormat(rdInternUpdateData.getInternshipEstimatedDate(), "MM/dd/yyyy", "M/d/yyyy"), "internship estimated date doesn't match : ");
        Assert.assertEquals(projectInternshipDetailsPO.getMentorText(), rdUser.getRealFirstName() + " " + rdUser.getRealLastName(), "internship mentor name doesn't match : ");

        Reporter.log("Step 3 - Verify that project internship DisbursementCompleteDateButton is not enabled");
        Assert.assertFalse(projectInternshipDetailsPO.isDisbursementCompleteDateButtonEnabled(), "Internship engagement disbursement complete date button is enabled : ");

    }

    /*Test 16 : Verify that regional director can read project location */
    @Test(priority = 16)
    public void VerifyThatRegionalDirectorCanReadProjectLocation() throws InterruptedException {

        HeaderPO headerPO = new HeaderPO(driver);
        LocationsListingPO locationsListingPO = new LocationsListingPO(driver);
        LocationDetailsPO locationDetailsPO = new LocationDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with regional director user");
        new LoginFunctions(driver, selenium).loginByGivenUser(rdUser);

        Reporter.log("Step 2 - Enter location name and click on view location button");
        headerPO.searchItemGloballyByName(rdLocationName);
        selenium.hardWait(3);
        locationsListingPO.clickOnFirstViewLocationButton();

        Reporter.log("Step 3 - Verify the location edit button is not present");
        Assert.assertFalse(locationDetailsPO.isLocationEditButtonPresent(), "Location edit button is present:");

    }

    /*Test 17 : Verify that regional director can not create language partner and location*/
    @Test(priority = 17)
    public void VerifyThatRegionalDirectorCanNotCreateLanguagePartnerAndLocation() throws InterruptedException {

        LeftNavPO leftNavPO = new LeftNavPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with regional director user");
        new LoginFunctions(driver, selenium).loginByGivenUser(rdUser);

        Reporter.log("Step 2 - Enter location name and click on view location button");
        leftNavPO.clickOnCreateNewItemMenuButton();

        Reporter.log("Step 3 - Verify language location and partner option is not present");
        Assert.assertFalse(leftNavPO.isMenuItemPresent(CreateItemTypes.Location.toString()), "Location menu item is present:");
        Assert.assertFalse(leftNavPO.isMenuItemPresent(CreateItemTypes.Language.toString()), "Language menu item is present:");
        Assert.assertFalse(leftNavPO.isMenuItemPresent(CreateItemTypes.Partner.toString()), "Partner menu item is present:");

        Reporter.log("Step 4 - Verify project person option is present");
        Assert.assertTrue(leftNavPO.isMenuItemPresent(CreateItemTypes.Project.toString()), "Project menu item is not present:");
        Assert.assertTrue(leftNavPO.isMenuItemPresent(CreateItemTypes.Person.toString()), "Person menu item is not present:");

    }

    /*Test 18 : Verify that regional director can create project language engagements */
    @Test(priority = 18)
    public void VerifyThatRegionalDirectorCanCreateProjectLanguageEngagements() throws InterruptedException {

        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with regional director user");
        new LoginFunctions(driver, selenium).loginByGivenUser(rdUser);
        selenium.navigateToPage(strLanguageTranslationUrl);
        selenium.hardWait(3);

        Reporter.log("Step 2 - Verify the created language engagement");
        Assert.assertEquals(projectDetails.getLanguageEngagementName(rdLanguageAdminData.getLanguageEngagementName()), rdLanguageAdminData.getLanguageEngagementName(), "project language engagement name doesn't match");
    }

    /*Test 19 : Verify that regional director can edit project language engagements except DisbursementCompleteDate*/
    @Test(priority = 19)
    public void VerifyThatRegionalDirectorCanEditProjectLanguageEngagementsExceptDisbursementCompleteDate() throws java.text.ParseException, InterruptedException {

        ProjectLanguageEngagementDetailsPO projectLanguageEngagementDetailsPO = new ProjectLanguageEngagementDetailsPO(driver);
        String textFileName = JavaHelpers.getPropertyValue(PROPERTY_TEST_FILE, "textFile");

        Reporter.log("Step 1 -  Login with regional director user and edit language engagement in created project");
        new LoginFunctions(driver, selenium).loginByGivenUser(rdUser);
        selenium.navigateToPage(strLanguageTranslationUrl);
        selenium.hardWait(3);
        new LanguageFunctions(driver, selenium).editProjectLanguageEngagements(rdInternData, rdLanguageAdminData);

        Reporter.log("Step 2 - Verify the edited language engagement");
        Assert.assertEquals(projectLanguageEngagementDetailsPO.getLanguageStartEndDate(), new JavaHelpers().changeDateFormat(rdInternData.getInternshipStartDate(), "MM/dd/yyyy", "M/d/yyyy") + "\n" + " - " + "\n" + new JavaHelpers().changeDateFormat(rdInternData.getInternshipEndDate(), "MM/dd/yyyy", "M/d/yyyy"), "Language engagement start end date doesn't match");
        Assert.assertEquals(projectLanguageEngagementDetailsPO.getTranslationCompleteDate(), new JavaHelpers().changeDateFormat(rdInternData.getGrowthPlanCompleteDate(), "MM/dd/yyyy", "M/d/yyyy"), "Internship growth plan complete date doesn't match");
        Assert.assertEquals(textFileName, projectLanguageEngagementDetailsPO.getUploadedFileText(), "uploaded file name doesn't match : ");
        Assert.assertEquals(projectLanguageEngagementDetailsPO.getActualDateTextBox(), new JavaHelpers().changeDateFormat(rdInternData.getInternshipActualDate(), "MM/dd/yyyy", "M/d/yyyy"), "Internship actual date doesn't match");
        Assert.assertEquals(projectLanguageEngagementDetailsPO.getEstimatedDateTextBox(), rdInternData.getInternshipEstimatedDate(), "Language engagement estimated date doesn't match");

        Reporter.log("Step 3 - Verify that project language engagement DisbursementCompleteDateButton is not enabled");
        Assert.assertFalse(projectLanguageEngagementDetailsPO.isDisbursementCompleteDateButtonEnabled(), "Internship engagement disbursement complete date button is enabled : "); //Todo bug(#762) Global R access
    }

    /*Test 20 : Verify that regional director can Add project goals */
    @Test(priority = 20)
    public void VerifyThatRegionalDirectorCanAddProjectGoalsSuccessfully() throws InterruptedException {

        GoalDetailsPO goalDetailsPO = new GoalDetailsPO(driver);
        ProjectLanguageEngagementDetailsPO projectLanguageEngagementDetailsPO = new ProjectLanguageEngagementDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with regional director user");
        new LoginFunctions(driver, selenium).loginByGivenUser(rdUser);
        selenium.navigateToPage(strProjectCreateGoalUrl);
        selenium.hardWait(3);

        Reporter.log("Step 2 - Verify goal type");
        Assert.assertEquals(projectLanguageEngagementDetailsPO.getGoalText(), (rdGoalData.getGoalStoryName()), "Goal type is not matched");

        Reporter.log("Step 3 - Click on created goal");
        projectLanguageEngagementDetailsPO.clickOnGoalCard();

        Reporter.log("Step 14 - verify goal details");
        Assert.assertEquals(Arrays.stream(goalDetailsPO.getGoalDistributionMethodsText().split(",")).map(String::trim).toArray(String[]::new), GoalDistributionMethods.getGoalDistributionMethodsByName(rdGoalData.getGoalDistributionMethods()), "Goal distribution method is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalDistributionMethodsPartnerText(), rdPartnerAdminData.getOrganizationName(), "Goal distribution method partner is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalMethodologyText(), rdGoalData.getMethodologyType() + " - " + rdGoalData.getMethodologyValue(), "Goal methodology is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalCompletionDescriptionText(), (rdGoalData.getGoalCompletionDescription()), "Goal completion description is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalScriptureReferenceOldTestamentText(), rdGoalData.getOldScriptureReference(), "Goal old scriptureReference is not matched");

        Reporter.log("Step 15 - Refresh page and verify Goal details");
        selenium.refreshPage();

        Assert.assertEquals(Arrays.stream(goalDetailsPO.getGoalDistributionMethodsText().split(",")).map(String::trim).toArray(String[]::new), GoalDistributionMethods.getGoalDistributionMethodsByName(rdGoalData.getGoalDistributionMethods()), "Goal distribution method is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalDistributionMethodsPartnerText(), rdPartnerAdminData.getOrganizationName(), "Goal distribution method partner is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalMethodologyText(), rdGoalData.getMethodologyType() + " - " + rdGoalData.getMethodologyValue(), "Goal methodology is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalCompletionDescriptionText(), (rdGoalData.getGoalCompletionDescription()), "Goal completion description is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalScriptureReferenceOldTestamentText(), rdGoalData.getOldScriptureReference(), "Goal old scriptureReference is not matched");

    }

    /*Test 21 : Verify that regional director can edit created project goals */
    @Test(priority = 21)
    public void VerifyThatRegionalDirectorCanEditProjectGoalsSuccessfully() throws InterruptedException {

        GoalDetailsPO goalDetailsPO = new GoalDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with regional director user");
        new LoginFunctions(driver, selenium).loginByGivenUser(rdUser);
        selenium.navigateToPage(strProjectCreateGoalUrl);
        selenium.hardWait(3);

        Reporter.log("Step 2 - Edit Story Goal by regional director user");
        new GoalFunctions(driver, selenium).editGoal(rdEditGoalData, rdGoalData, rdPartnerAdminData.getOrganizationName());

        Reporter.log("Step 3 - Verify the created goal");
        Assert.assertEquals(goalDetailsPO.getGoalNameText(), (rdGoalData.getGoalStoryName()), "Goal type is not matched");

        Reporter.log("Step 4 - Verify goal details");
        Assert.assertEquals(Arrays.stream(goalDetailsPO.getGoalDistributionMethodsText().split(",")).map(String::trim).toArray(String[]::new), GoalDistributionMethods.getGoalDistributionMethodsByName(rdEditGoalData.getGoalDistributionMethods()), "Goal mediums is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalDistributionMethodsPartnerText(), rdPartnerAdminData.getOrganizationName(), "Goal distribution method partner is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalMethodologyText(), rdEditGoalData.getMethodologyType() + " - " + rdEditGoalData.getMethodologyValue(), "Goal methodology is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalCompletionDescriptionText(), (rdEditGoalData.getGoalCompletionDescription()), "Goal completion description is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalScriptureReferenceOldTestamentText(), rdEditGoalData.getOldScriptureReference(), "Goal old scriptureReference is not matched");

        Reporter.log("Step 5 - Refresh page and verify Goal details");
        selenium.refreshPage();
        Assert.assertEquals(Arrays.stream(goalDetailsPO.getGoalDistributionMethodsText().split(",")).map(String::trim).toArray(String[]::new), GoalDistributionMethods.getGoalDistributionMethodsByName(rdEditGoalData.getGoalDistributionMethods()), "Goal mediums is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalDistributionMethodsPartnerText(), rdPartnerAdminData.getOrganizationName(), "Goal distribution method partner is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalMethodologyText(), rdEditGoalData.getMethodologyType() + " - " + rdEditGoalData.getMethodologyValue(), "Goal methodology is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalCompletionDescriptionText(), (rdEditGoalData.getGoalCompletionDescription()), "Goal completion description is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalScriptureReferenceOldTestamentText(), rdEditGoalData.getOldScriptureReference(), "Goal old scriptureReference is not matched");

    }
}

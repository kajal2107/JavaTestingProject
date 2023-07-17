package cord.projects.rolePermissionFlow;

import base.BaseTest;
import datafactory.*;
import dataobjects.*;
import enums.goal.GoalDistributionMethods;
import enums.menu.CreateItemTypes;
import enums.partner.PartnerType;
import enums.partnership.PartnershipFinancialReportingTypes;
import enums.partnership.PartnershipStatus;
import enums.project.*;
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

public class FieldOperationsDirectorTests extends BaseTest {

    protected String strFodPeopleUrl;
    protected String strFodLanguageUrl;
    protected String strFodPartnerUrl;
    protected String strFodProjectUrl;
    protected String strFodInternshipUrl;
    protected String strFodPartnershipUrl;
    protected String strFodProjectCreateGoalUrl;
    protected String strFodProjectTeamMemberUrl;
    protected String strFodLanguageTranslationUrl;
    protected String strFodFundingPartnershipUrl;
    People fodPeopleFpmData = new PeopleData().getPeopleData();
    Budget fodBudgetData = new BudgetData().getBudgetData();
    Project fodAdminTranslationData = new ProjectData().getCreateTranslationProjectData();
    String fodCreatedTranslationProjectName = fodAdminTranslationData.getProjectName();
    Project fodAdminBudgetData = new ProjectData().getCreateProjectData();
    String fodCreatedBudgetProjectName = fodAdminBudgetData.getProjectName();
    People fodPeopleAdminData = new PeopleData().getPeopleData();
    People fodEditPeopleAdminData = new PeopleData().getPeopleData();
    Project fodAdminData = new ProjectData().getCreateProjectData();
    String fodCreatedProjectName = fodAdminData.getProjectName();
    Project fodEditProjectAdminData = new ProjectData().getUpdateProjectData();
    Language fodLanguageAdminData = new LanguageData().getLanguageData();
    Partner fodPartnerAdminData = new PartnerData().getCreatePartnerData();
    Partner fodEditPartnerAdminData = new PartnerData().getEditPartnerData();
    Partner fodBudgetPartnerAdminData = new PartnerData().getCreatePartnerData();
    Goal fodGoalData = new GoalData().getCreateGoalData();
    Project fodFpmData = new ProjectData().getCreateProjectData();
    Project fodEditProjectBudgetData = new ProjectData().getUpdateProjectData();
    Goal fodEditGoalData = new GoalData().getCreateGoalData();
    Location fodLocationData = new LocationData().getLocationData();
    String fodLocationName = fodLocationData.getLocationName();
    Internship fodInternUpdateData = new EngagementData().getUpdateEngagementData();
    Internship fodInternData = new EngagementData().getCreateEngagementData();

    public FieldOperationsDirectorTests() throws ParseException, java.text.ParseException, InterruptedException {
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
        new ProjectFunctions(driver, selenium).createProject(fodAdminData);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 2 - Create Budget Project by admin user and logout that user");
        new ProjectFunctions(driver, selenium).createProject(fodAdminBudgetData);
        new ProjectFunctions(driver, selenium).addMemberToProject(fpmUser, fodAdminBudgetData);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 3 - Create TranslationType Project by admin user and logout that user");
        new ProjectFunctions(driver, selenium).createProject(fodAdminTranslationData);
        new PartnershipFunctions(driver, selenium).createProjectPartnership(fodPartnerAdminData, fodCreatedTranslationProjectName, adminUser);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(fpmUser);

        Reporter.log("Step 4 - Create Project by project manager  and add field operations director that project");
        new ProjectFunctions(driver, selenium).createProject(fodFpmData);
        strFodProjectUrl = driver.getCurrentUrl();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 5 - Create People by admin user");
        new PeopleFunctions(driver, selenium).createPeople(fodPeopleAdminData, adminUser);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(fpmUser);

        Reporter.log("Step 6 - Create People by project manager user");
        new PeopleFunctions(driver, selenium).createPeople(fodPeopleFpmData, fpmUser);
        strFodPeopleUrl = driver.getCurrentUrl();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 7 - Create Language by admin user");
        new LanguageFunctions(driver, selenium).createLanguage(fodLanguageAdminData);
        strFodLanguageUrl = driver.getCurrentUrl();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(fodUser);

        Reporter.log("Step 8 - Create internship engagements by field operations director user");
        new InternshipEngagementsFunctions(driver, selenium).createProjectInternshipEngagements(fodUser, fodCreatedProjectName);
        strFodInternshipUrl = driver.getCurrentUrl();
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(fodUser);

        Reporter.log("Step 9 - Create language engagements by field operations director user");
        new LanguageFunctions(driver, selenium).createProjectLanguageEngagements(fodLanguageAdminData, fodCreatedTranslationProjectName, fodUser);
        strFodLanguageTranslationUrl = driver.getCurrentUrl();
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 10 - Create Partner by admin user");
        new PartnerFunctions(driver, selenium).createPartner(fodPartnerAdminData);
        strFodPartnerUrl = driver.getCurrentUrl();
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(fodUser);

        Reporter.log("Step 11 - Create partnership by field operations director user");
        new PartnershipFunctions(driver, selenium).createProjectPartnership(fodPartnerAdminData, fodCreatedProjectName, fodUser);
        strFodPartnershipUrl = driver.getCurrentUrl();
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(fodUser);

        Reporter.log("Step 12 - Add project team member with multiple user role");
        new ProjectFunctions(driver, selenium).addMemberToProject(rdFodUser, fodAdminData);
        strFodProjectTeamMemberUrl = driver.getCurrentUrl();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 13 - Create Location by admin user");
        new LocationFunctions(driver, selenium).createLocation(fodLocationData);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(fodUser);

        Reporter.log("Step 14 - Create goal by field operations director user");
        selenium.navigateToPage(strFodLanguageTranslationUrl);
        new GoalFunctions(driver, selenium).createGoal(fodGoalData, fodLanguageAdminData.getLanguageEngagementName(), fodPartnerAdminData.getOrganizationName());
        strFodProjectCreateGoalUrl = driver.getCurrentUrl();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 15 - Create funding partnership by admin user");
        new ProjectFunctions(driver, selenium).editInternshipProjectByName(fodEditProjectBudgetData, fodCreatedBudgetProjectName, adminUser);
        new PartnershipFunctions(driver, selenium).createProjectFundingPartnership(fodBudgetPartnerAdminData, fodEditProjectBudgetData.getProjectName());
        selenium.hardWait(3);
        strFodFundingPartnershipUrl = driver.getCurrentUrl();
        drivermanager.tearDown();
    }

    /*Test 1 : Verify that field operations director can view all projects*/
    @Test(priority = 1)
    public void VerifyFieldOperationsDirectorCanViewAllProjectsSuccessfully() throws InterruptedException {

        LeftNavPO sideHeader = new LeftNavPO(driver);
        ProjectsListingPO projectsListingPO = new ProjectsListingPO(driver);
        ProjectDetailsPO projectDetailsPO = new ProjectDetailsPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with field operations director user");
        new LoginFunctions(driver, selenium).loginByGivenUser(fodUser);

        Reporter.log("Step 2 - Verify that projects is displayed which is created by admin");
        sideHeader.clickOnProjectsMenu();
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());

        if (!projectsListingPO.isProjectPresent(fodAdminData.getProjectName())) {
            headerPO.searchItemGloballyByName(fodAdminData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(fodAdminData.getProjectName());
        Assert.assertEquals(projectDetailsPO.getProjectName(), fodAdminData.getProjectName(), "Project is not present : " + fodAdminData.getProjectName());

        Reporter.log("Step 3 - Verify that projects is displayed which is created by FPM user");
        sideHeader.clickOnProjectsMenu();
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());

        if (!projectsListingPO.isProjectPresent(fodFpmData.getProjectName())) {
            headerPO.searchItemGloballyByName(fodFpmData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(fodFpmData.getProjectName());
        Assert.assertEquals(projectDetailsPO.getProjectName(), fodFpmData.getProjectName(), "Project is not present : " + fodFpmData.getProjectName());

    }

    /*Test 2 : Verify that field operations director can view all peoples*/
    @Test(priority = 2)
    public void VerifyFieldOperationsDirectorCanViewAllPeoplesExceptRealFirstNameAndRealLastNameOfMineSuccessfully() throws InterruptedException {

        LeftNavPO sideHeader = new LeftNavPO(driver);
        PeoplesPO peoples = new PeoplesPO(driver);
        PeopleDetailsPO peopleDetails = new PeopleDetailsPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with field operations director user");
        selenium.navigateToPage(strFodPeopleUrl);
        new LoginFunctions(driver, selenium).loginByGivenUser(fodUser);

        Reporter.log("Step 2 - Verify that peoples is displayed which is created by admin");
        sideHeader.clickOnPeopleMenu();
        if (!peoples.isPeoplePresent(fodPeopleAdminData.getPersonName())) {
            headerPO.searchItemGloballyByName(fodPeopleAdminData.getPersonName());
        }
        peoples.clickOnPeopleName(fodPeopleAdminData.getPersonName());
        Assert.assertEquals(peopleDetails.getPeopleName().replaceAll("\\s.*", ""), fodPeopleAdminData.getPersonName(), "Project is not present : " + fodPeopleAdminData.getPersonName());

        Reporter.log("Step 3 - Verify that peoples is displayed which is created by FPM user");
        sideHeader.clickOnPeopleMenu();
        if (!peoples.isPeoplePresent(fodPeopleFpmData.getPersonName())) {
            headerPO.searchItemGloballyByName(fodPeopleFpmData.getPersonName());
        }
        peoples.clickOnPeopleName(fodPeopleFpmData.getPersonName());
        Assert.assertEquals(peopleDetails.getPeopleName().replaceAll("\\s.*", ""), fodPeopleFpmData.getPersonName(), "Project is not present : " + fodPeopleFpmData.getPersonName());

        Reporter.log("Step 4 - Verify that peoples real name and last name is not displayed");
        Assert.assertFalse(peopleDetails.IsPeopleFirstNameLastNamePresent(), "Project is not present : " + fodPeopleFpmData.getPersonName());

    }

    /*Test 3 : Verify that field operations director can edit mine peoples */
    @Test(priority = 3)
    public void VerifyThatFieldOperationsDirectorCanEditMinePeoples() throws InterruptedException {

        PeoplesPO peoples = new PeoplesPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);
        LeftNavPO sideHeader = new LeftNavPO(driver);
        UpdatePeoplePO updatePeople = new UpdatePeoplePO(driver);
        PeopleDetailsPO peopleDetails = new PeopleDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with field operations director user");
        new LoginFunctions(driver, selenium).loginByGivenUser(fodUser);

        Reporter.log("Step 2 - Search user which have a field operations director role by email and click on people name");
        sideHeader.clickOnPeopleMenu();
        if (!peoples.isPeoplePresent(fodUser.getEmail())) {
            headerPO.searchItemGloballyByName(fodUser.getEmail());
        }
        peoples.clickOnPeopleName(fodUser.getRealFirstName() + " " + fodUser.getRealLastName());

        Reporter.log("Step 3 - Click on edit icon and edit mine person detail");
        peopleDetails.clickOnEditButton();
        updatePeople.clearAndUpdatePersonDetail(fodEditPeopleAdminData);
        updatePeople.clickOnSubmitButton();

        Reporter.log("Step 4 - Verify updated person details");
        Assert.assertEquals(fodEditPeopleAdminData.getPersonEmail(), peopleDetails.getEmail(), "email doesn't match : ");
        Assert.assertEquals(fodEditPeopleAdminData.getPersonTitle(), peopleDetails.getTitle(), "title doesn't match : ");
        Assert.assertEquals(fodEditPeopleAdminData.getPersonPhone(), peopleDetails.getPhoneNumber(), "phone number doesn't match : ");
        Assert.assertEquals(fodEditPeopleAdminData.getPersonAbout(), peopleDetails.getAbout(), "About doesn't match : ");
        Assert.assertEquals(UserRole.FieldOperationsDirector.getRole(), peopleDetails.getRoles(), "Roles doesn't match : ");

        Reporter.log("Step 5 - Again Click on edit icon and reset the people");
        peopleDetails.clickOnEditButton();
        updatePeople.fillFirstnameLastNameAndEmailDetail(fodUser.getRealFirstName(), fodUser.getRealLastName(), fodUser.getEmail());
        updatePeople.clickOnSubmitButton();
        Assert.assertEquals(fodUser.getRealFirstName() + " " + fodUser.getRealLastName(), peopleDetails.getPeopleName(), "personName doesn't match : ");
        Assert.assertEquals(fodUser.getEmail(), peopleDetails.getEmail(), "personEmail doesn't match : ");

    }

    /*Test 4 : Verify that field operations director can view all languages and edit icon is not present*/
    @Test(priority = 4)
    public void VerifyFieldOperationsDirectorCanViewAllLanguagesAndEditIconIsNotPresent() throws InterruptedException {

        LeftNavPO sideHeader = new LeftNavPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);
        LanguagesPO languages = new LanguagesPO(driver);
        LanguageDetailPO languageDetailPO = new LanguageDetailPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with field operations director user");
        selenium.navigateToPage(strFodLanguageUrl);
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).loginByGivenUser(fodUser);

        Reporter.log("Step 2 - Verify that language is displayed which is created by admin and edit icon is not present");
        sideHeader.clickOnLanguagesMenu();
        if (!languages.isLanguagePresent(fodLanguageAdminData.getLanguageName())) {
            headerPO.searchItemGloballyByName(fodLanguageAdminData.getLanguageName());
        }
        languages.clickOnLanguageName(fodLanguageAdminData.getLanguageName());
        Assert.assertEquals(languageDetailPO.getLanguageName(), fodLanguageAdminData.getPubliicName(), "Language is not present : " + fodLanguageAdminData.getPubliicName());
        Assert.assertFalse(languageDetailPO.isEditIconPresent(), "Language edit icon is present : ");

    }

    /*Test 5 : Verify that field operations director can view all partners*/
    @Test(priority = 5)
    public void VerifyFieldOperationsDirectorCanViewAllPartnersSuccessfully() throws InterruptedException {

        HeaderPO headerPO = new HeaderPO(driver);
        LeftNavPO sideHeader = new LeftNavPO(driver);
        PartnersListingPO partnerListing = new PartnersListingPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with field operations director user");
        new LoginFunctions(driver, selenium).loginByGivenUser(fodUser);

        Reporter.log("Step 2 - Verify that partner is displayed which is created by admin");
        sideHeader.clickOnPartnersMenu();
        if (!partnerListing.isPartnerPresent(fodPartnerAdminData.getPartnerName())) {
            headerPO.searchItemGloballyByName(fodPartnerAdminData.getPartnerName());
        }
        Assert.assertTrue(partnerListing.isPartnerPresent(fodPartnerAdminData.getPartnerName()), "Partner is not present : " + fodPartnerAdminData.getPartnerName());

    }

    /*Test 6 : Verify that field operations director can't edit all partners details */ //Todo bug(#762) Global R access
    @Test(priority = 6)
    public void VerifyThatFieldOperationsDirectorCanNotEditAllPartnersDetails() throws InterruptedException {

        PartnerDetailsPO partnerDetailsPO = new PartnerDetailsPO(driver);
        EditPartnerPO editPartnerPO = new EditPartnerPO(driver);

        Reporter.log("Step 1 - Login with fod user");
        new LoginFunctions(driver, selenium).loginByGivenUser(fodUser);

        Reporter.log("Step 2 - Verify that partner global innovations client is not edited by fod user ");
        new PartnerFunctions(driver, selenium).editPartnerGlobalInnovationsClient(fodPartnerAdminData.getPartnerName());
        Assert.assertTrue(editPartnerPO.isGlobalInnovationsClientErrorMessagePresent(), "Partner global innovations client error message is not present : ");
        editPartnerPO.clickOnCancelButton();

        Reporter.log("Step 3 - Verify that partner active inActive status is not edited by fod user ");
        new PartnerFunctions(driver, selenium).editPartnerActiveInActiveStatus(fodPartnerAdminData.getPartnerName());
        Assert.assertTrue(editPartnerPO.isActiveInActiveErrorMessagePresent(), "Partner active inActive error message is not present : ");
        editPartnerPO.clickOnCancelButton();

        Reporter.log("Step 4 - Verify that partner pmc entity code is not edited by fod user ");
        new PartnerFunctions(driver, selenium).editPartnerPmcEntityCode(fodPartnerAdminData.getPartnerName(), fodEditPartnerAdminData);
        Assert.assertTrue(editPartnerPO.isPmcEntityCodeErrorMessagePresent(), "Partner pmc entity code error message is not present : ");
        editPartnerPO.clickOnCancelButton();

        Reporter.log("Step 5 - Verify that partner point of contact is not edited by fod user ");
        new PartnerFunctions(driver, selenium).editPartnerPointOfContact(fodPartnerAdminData.getPartnerName(), fodUser);
        Assert.assertTrue(editPartnerPO.isPointOfContactErrorMessagePresent(), "Partner point of contact error message is not present : ");
        editPartnerPO.clickOnCancelButton();

        Reporter.log("Step 6 - Verify that partner types address is not edited by fod user ");
        Assert.assertFalse(partnerDetailsPO.isPartnerTypesEditButtonEnabled(), "Partner types edit button is enabled : ");
        Assert.assertFalse(partnerDetailsPO.isPartnerAddressEditButtonEnabled(), "Partner address edit button is enabled: ");

    }

    /*Test 7 : Verify that field operations director can upload/Edit project files */
    @Test(priority = 7)
    public void VerifyThatFieldOperationsDirectorCanUploadProjectFilesSuccessfully() throws InterruptedException {

        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectDetailsFilesListingPO projectDetailsFilesListingPO = new ProjectDetailsFilesListingPO(driver);
        ProjectDetailsFilesHistoryListingPO projectDetailsFilesHistoryListingPO = new ProjectDetailsFilesHistoryListingPO(driver);
        ProjectDetailsFileListingRenameFileFolderPO projectDetailsFileListingRenameFileFolderPO = new ProjectDetailsFileListingRenameFileFolderPO(driver);
        ProjectDetailsFileListingDeleteFileFolderPO projectDetailsFileListingDeleteFileFolderPO = new ProjectDetailsFileListingDeleteFileFolderPO(driver);

        String textFileName = JavaHelpers.getPropertyValue(PROPERTY_TEST_FILE, "textFile");

        Reporter.log("Step 1 - Entering valid sign in details and login to application");
        new LoginFunctions(driver, selenium).loginByGivenUser(fodUser);

        Reporter.log("Step 2 -  Login with field operations director user and upload file in created project");
        new FileFunctions(driver, selenium).uploadFile(fodCreatedProjectName, textFileName);

        Reporter.log("Step 3 - Verify that project file is uploaded by field operations director user ");
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
        projectDetailsFilesListingPO.clickOnKebabMenuAndViewFileHistory(projectDetailsFilesListingPO.getLastFileUploadText().replace(".txt", ""));
        Assert.assertTrue(projectDetailsFilesHistoryListingPO.isFilePresent(versionFileName), "file is not present : ");

        Reporter.log("Step 7 - Click on history kebab menu and rename the file");
        projectDetailsFilesHistoryListingPO.clickOnHistoryKebabMenuAndRenameFileFolder(projectDetailsFilesListingPO.getLastFileUploadText());
        projectDetailsFileListingRenameFileFolderPO.clearAndRenameFileFolderName(fodEditProjectAdminData);
        Assert.assertTrue(projectDetailsFilesHistoryListingPO.isFilePresent(fodEditProjectAdminData.getFileName() + ".txt"), "renamed file is not present : ");
        Assert.assertFalse(projectDetailsFilesHistoryListingPO.isFilePresent(versionFileName), "renamed file is present : ");

        Reporter.log("Step 8 - Click on history kebab menu and delete created file");
        projectDetailsFilesHistoryListingPO.clickOnKebabMenuAndDeleteFileFolder(projectDetailsFilesListingPO.getFirstFileUploadText());
        projectDetailsFileListingDeleteFileFolderPO.clickOnFileDeleteButton();
        Assert.assertFalse(projectDetailsFilesListingPO.isFileFolderPresent(fodEditProjectAdminData.getFileName()), "deleted file is present : ");

    }

    /*Test 8 : Verify that field operations director can edit project budget */
    @Test(priority = 8)
    public void VerifyThatFieldOperationsDirectorCanEditProjectBudgetSuccessfully() throws InterruptedException {

        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectDetailsBudgetListingPO projectDetailsBudgetListingPO = new ProjectDetailsBudgetListingPO(driver);
        ProjectDetailsPartnershipsListingPO projectDetailsPartnershipsListingPO = new ProjectDetailsPartnershipsListingPO(driver);
        String textFileName = JavaHelpers.getPropertyValue(PROPERTY_TEST_FILE, "textFile");

        Reporter.log("Step 1 - Entering valid sign in details and login to application");
        new LoginFunctions(driver, selenium).loginByGivenUser(fodUser);

        Reporter.log("Step 2 - Navigate on partnership detail page and click on project link button");
        selenium.navigateToPage(strFodFundingPartnershipUrl);
        selenium.hardWait(3);
        projectDetailsPartnershipsListingPO.clickOnProjectLinkButton();

        Reporter.log("Step 3 - Click on budget detail button");
        projectDetails.clickOnBudgetButton();

        Reporter.log("Step 4 - Edit budget in created project");
        new BudgetFunctions(driver, selenium).editProjectBudget(textFileName, fodUser, fodBudgetData);

        Reporter.log("Step 5 - Verify that project budget is edited by project manager user");
        Assert.assertEquals(projectDetailsBudgetListingPO.getUploadedTemplateFileName(), textFileName, "project budget template file name doesn't match : ");
        Assert.assertEquals(projectDetailsBudgetListingPO.getBudgetAmountText().replace(",", ""), "$" + fodBudgetData.getBudgetAmount(), "project budget amount doesn't match : ");
    }

    /*Test 9 : Verify that field operations director can add project team member */
    @Test(priority = 9)
    public void VerifyThatFieldOperationsDirectorCanAddProjectTeamMemberSuccessfully() throws InterruptedException {
        ProjectDetailsTeamMembersListingPO projectDetailsTeamMembersListingPO = new ProjectDetailsTeamMembersListingPO(driver);

        Reporter.log("Step 1 -  Login with field operations director user and add team member in created project");
        new LoginFunctions(driver, selenium).loginByGivenUser(fodUser);
        selenium.navigateToPage(strFodProjectTeamMemberUrl);

        selenium.hardWait(3);

        Reporter.log("Step 2 - Verify that project team member is added by field operations director user");
        Assert.assertEquals(projectDetailsTeamMembersListingPO.getMembersName(rdFodUser.getRealFirstName() + " " + rdFodUser.getRealLastName()), (rdFodUser.getRealFirstName() + " " + rdFodUser.getRealLastName()), "project member name doesn't match : ");
        Assert.assertEquals(projectDetailsTeamMembersListingPO.getMembersRoleByMemberName(rdFodUser.getRealFirstName() + " " + rdFodUser.getRealLastName()), rdFodUser.getUserRoles().get(0), "project member role doesn't match : ");
    }

    /*Test 10 : Verify that field operations director can edit project team member */
    @Test(priority = 10)
    public void VerifyThatFieldOperationsDirectorCanEditProjectTeamMemberSuccessfully() throws InterruptedException {

        ProjectDetailsTeamMembersListingPO projectDetailsTeamMembersListingPO = new ProjectDetailsTeamMembersListingPO(driver);

        Reporter.log("Step 1 -  Login with field operations director user and edit team member in created project");
        new LoginFunctions(driver, selenium).loginByGivenUser(fodUser);
        selenium.navigateToPage(strFodProjectTeamMemberUrl);
        selenium.hardWait(3);
        new TeamMemberFunctions(driver, selenium).editProjectTeamMember(rdFodUser);

        Reporter.log("Step 2 - Verify that project team member is edited by field operations director user");
        Assert.assertEquals(projectDetailsTeamMembersListingPO.getMembersRoleByMemberName(rdFodUser.getRealFirstName() + " " + rdFodUser.getRealLastName()), rdFodUser.getUserRoles().get(0) + ", " + rdFodUser.getUserRoles().get(1), "project member updated role doesn't match : ");
    }

    /*Test 11 : Verify that field operations director can edit projects */
    @Test(priority = 11)
    public void VerifyThatFieldOperationsDirectorCanEditProjectsSuccessfully() throws InterruptedException, java.text.ParseException {

        LeftNavPO sideHeader = new LeftNavPO(driver);
        ProjectDetailsPO projectDetailsPO = new ProjectDetailsPO(driver);
        ProjectsListingPO projectsListingPO = new ProjectsListingPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);

        Reporter.log("Step 1 -  Login with field operations director user and edit created project");
        new LoginFunctions(driver, selenium).loginByGivenUser(fodUser);
        new ProjectFunctions(driver, selenium).editInternshipProjectByName(fodEditProjectAdminData, fodCreatedProjectName, fodUser);

        Reporter.log("Step 2 - Verify that project is displayed which is edited by field operations director user ");
        sideHeader.clickOnProjectsMenu();
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());

        if (!projectsListingPO.isProjectPresent(fodEditProjectAdminData.getProjectName())) {
            headerPO.searchItemGloballyByName(fodEditProjectAdminData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(fodEditProjectAdminData.getProjectName());

        Assert.assertEquals(projectDetailsPO.getProjectName(), fodEditProjectAdminData.getProjectName(), "Project is not present ");
        Assert.assertEquals(projectDetailsPO.getProjectStepByName(ProjectStep.PendingConceptApproval.getStep()), ProjectStep.PendingConceptApproval.getStep(), "Project step is not changed");
        Assert.assertEquals(Sensitivity.Low.toString(), projectDetailsPO.getProjectSensitivity(), "project sensitivity doesn't match : ");
        Assert.assertEquals(projectDetailsPO.getProjectLocationAndFieldRegion(), fodEditProjectAdminData.getLocationName() + " | " + fodEditProjectAdminData.getFieldRegionName(), "project location and field region doesn't match : ");
        Assert.assertEquals(projectDetailsPO.getProjectStartEndDate(), new JavaHelpers().changeDateFormat(fodEditProjectAdminData.getStartDate(), "MM/dd/yyyy", "M/d/yyyy") + "\n" + " - " + "\n" + new JavaHelpers().changeDateFormat(fodEditProjectAdminData.getEndDate(), "MM/dd/yyyy", "M/d/yyyy"), "project start end date doesn't match : ");
        Assert.assertEquals(new JavaHelpers().changeDateFormat(fodEditProjectAdminData.getEstimatedSubmissionDate(), "MM/dd/yyyy", "M/d/yyyy"), projectDetailsPO.getProjectEstimatedSubmissionDate(), "project estimated submission date doesn't match : ");
    }

    /*Test 12 : Verify that field operations director can create project partnership */
    @Test(priority = 12)
    public void VerifyThatFieldOperationsDirectorCanCreateProjectPartnershipSuccessfully() throws InterruptedException {

        ProjectDetailsPartnershipsListingPO projectDetailsPartnershipsListingPO = new ProjectDetailsPartnershipsListingPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with field operations director user");
        new LoginFunctions(driver, selenium).loginByGivenUser(fodUser);
        selenium.navigateToPage(strFodPartnershipUrl);

        selenium.hardWait(3);

        Reporter.log("Step 2 - Verify that project partnership is created by field operations director user");
        Assert.assertEquals(projectDetailsPartnershipsListingPO.getPartnershipName(fodPartnerAdminData.getOrganizationName()), fodPartnerAdminData.getOrganizationName(), "project partnership doesn't match : ");
    }

    /*Test 13 : Verify that field operations director can edit project partnership */
    @Test(priority = 13)
    public void VerifyThatFieldOperationsDirectorCanEditProjectPartnershipExceptFinancialReportingTypeAndMouStatusSuccessfully() throws java.text.ParseException, InterruptedException {

        ProjectDetailsPartnershipsListingPO projectDetailsPartnershipsListingPO = new ProjectDetailsPartnershipsListingPO(driver);
        ProjectDetailsPartnershipsEditPO projectDetailsPartnershipsEditPO = new ProjectDetailsPartnershipsEditPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with field operations director user");
        new LoginFunctions(driver, selenium).loginByGivenUser(fodUser);
        selenium.navigateToPage(strFodPartnershipUrl);
        selenium.hardWait(3);
        new PartnershipFunctions(driver, selenium).editProjectPartnershipAllDetails(fodEditProjectAdminData);

        Reporter.log("Step 3 - Click on partnership submit button");
        projectDetailsPartnershipsEditPO.clickOnPartnershipSubmitButton();

        Reporter.log("Step 4 - Verify that project partnership is edited by field operations director user");
        Assert.assertEquals(projectDetailsPartnershipsListingPO.getTypeText(fodPartnerAdminData.getOrganizationName()), PartnerType.Managing.getPartnerType(), "partner types doesn't match");
        Assert.assertEquals(projectDetailsPartnershipsListingPO.getFinancialReportingTypeText(fodPartnerAdminData.getOrganizationName()), PartnershipFinancialReportingTypes.Funded.getPartnershipFinancialReportingTypes(), "partnership financial reporting types doesn't match");
        Assert.assertEquals(projectDetailsPartnershipsListingPO.getAgreementStatusText(fodPartnerAdminData.getOrganizationName()), PartnershipStatus.NotAttached.getPartnerShipStatus(), " partnership agreement status doesn't match");
        Assert.assertEquals(projectDetailsPartnershipsListingPO.getMouStatusText(fodPartnerAdminData.getOrganizationName()), PartnershipStatus.NotAttached.getPartnerShipStatus(), " partnership mou status doesn't match");
        Assert.assertEquals(projectDetailsPartnershipsListingPO.getMouDateRangeText(fodPartnerAdminData.getOrganizationName()), new JavaHelpers().changeDateFormat(fodEditProjectAdminData.getStartDate(), "MM/dd/yyyy", "M/d/yyyy") + " - " + new JavaHelpers().changeDateFormat(fodEditProjectAdminData.getEndDate(), "MM/dd/yyyy", "M/d/yyyy"), "project start end date doesn't match : ");
        Assert.assertTrue(projectDetailsPartnershipsListingPO.isCreatedPartnershipPrimary(fodPartnerAdminData.getOrganizationName()), "Created partnership is not primary");
    }

    /*Test 14 : Verify that field operations director can create project internship engagements */
    @Test(priority = 14)
    public void VerifyThatFieldOperationsDirectorCanCreateProjectInternshipEngagementsSuccessfully() {

        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with field operations director user");
        new LoginFunctions(driver, selenium).loginByGivenUser(fodUser);
        selenium.navigateToPage(strFodInternshipUrl);

        Reporter.log("Step 2 - Verify the created internship engagement");
        Assert.assertEquals(projectDetails.getInternshipName(fodUser.getRealFirstName() + " " + fodUser.getRealLastName()), (fodUser.getRealFirstName() + " " + fodUser.getRealLastName()), "project internship name doesn't match : ");
    }

    /*Test 15 : Verify that field operations director can edit project internship engagements except DisbursementCompleteDate*/
    @Test(priority = 15)
    public void VerifyThatFieldOperationsDirectorCanEditProjectInternshipEngagementsSuccessfullyExceptDisbursementCompleteDate() throws java.text.ParseException, InterruptedException {

        ProjectInternshipDetailsPO projectInternshipDetailsPO = new ProjectInternshipDetailsPO(driver);

        Reporter.log("Step 1 -  Login with field operations director user and edit internship engagement in created project");
        new LoginFunctions(driver, selenium).loginByGivenUser(fodUser);
        selenium.navigateToPage(strFodInternshipUrl);
        selenium.hardWait(3);

        new InternshipEngagementsFunctions(driver, selenium).editProjectInternshipEngagements(fodUser);
        String newVersionFileName = JavaHelpers.getPropertyValue(PROPERTY_TEST_FILE, "newVersionFile");

        Reporter.log("Step 2 - Verify that project internship is edited by field operations director user");
        Assert.assertEquals(projectInternshipDetailsPO.getInternshipStartEndDate(), new JavaHelpers().changeDateFormat(fodInternUpdateData.getInternshipStartDate(), "MM/dd/yyyy", "M/d/yyyy") + "\n" + " - " + "\n" + new JavaHelpers().changeDateFormat(fodInternUpdateData.getInternshipEndDate(), "MM/dd/yyyy", "M/d/yyyy"), "Internship start end date doesn't match :");
        Assert.assertEquals(ProjectInternPosition.Administration.getInternPosition(), projectInternshipDetailsPO.getInternPosition(), "internship position doesn't match : ");
        Assert.assertEquals(projectInternshipDetailsPO.getGrowthPlanCompleteDate(), new JavaHelpers().changeDateFormat(fodInternUpdateData.getGrowthPlanCompleteDate(), "MM/dd/yyyy", "M/d/yyyy"), "internship growth plan complete date  doesn't match :");
        Assert.assertEquals(projectInternshipDetailsPO.getGrowthPlanCompleteDate(), new JavaHelpers().changeDateFormat(fodInternUpdateData.getGrowthPlanCompleteDate(), "MM/dd/yyyy", "M/d/yyyy"), "internship growth plan complete date  doesn't match :");
        Assert.assertEquals(newVersionFileName, projectInternshipDetailsPO.getUploadedFileText(), "uploaded file name doesn't match : ");
        Assert.assertEquals(projectInternshipDetailsPO.getMethodologiesText(), ProjectInternshipMethodologies.OneStory.getInternshipMethodology() + "\n" + ProjectInternshipMethodologies.Render.getInternshipMethodology(), "internship methodologies doesn't match : ");
        Assert.assertEquals(projectInternshipDetailsPO.getActualDateText(), new JavaHelpers().changeDateFormat(fodInternUpdateData.getInternshipActualDate(), "MM/dd/yyyy", "M/d/yyyy"), "internship actual date doesn't match : ");
        Assert.assertEquals(projectInternshipDetailsPO.getEstimatedDateText(), new JavaHelpers().changeDateFormat(fodInternUpdateData.getInternshipEstimatedDate(), "MM/dd/yyyy", "M/d/yyyy"), "internship estimated date doesn't match : ");
        Assert.assertEquals(projectInternshipDetailsPO.getMentorText(), fodUser.getRealFirstName() + " " + fodUser.getRealLastName(), "internship mentor name doesn't match : ");

    }

    /*Test 16 : Verify that field operations director can read project location */
    @Test(priority = 16)
    public void VerifyThatFieldOperationsDirectorCanReadProjectLocation() throws InterruptedException {

        HeaderPO headerPO = new HeaderPO(driver);
        LocationsListingPO locationsListingPO = new LocationsListingPO(driver);
        LocationDetailsPO locationDetailsPO = new LocationDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with field operations director user");
        new LoginFunctions(driver, selenium).loginByGivenUser(fodUser);

        Reporter.log("Step 2 - Enter location name and click on view location button");
        headerPO.searchItemGloballyByName(fodLocationName);
        locationsListingPO.clickOnFirstViewLocationButton();

        Reporter.log("Step 3 - Verify the location edit button is not present");
        Assert.assertFalse(locationDetailsPO.isLocationEditButtonPresent(), "Location edit button is present:");
    }

    /*Test 17 : Verify that field operations director can not create language partner and location */
    @Test(priority = 17)
    public void VerifyThatFieldOperationsDirectorCanNotCreateLanguagePartnerAndLocation() throws InterruptedException {

        LeftNavPO leftNavPO = new LeftNavPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with field operations director user");
        new LoginFunctions(driver, selenium).loginByGivenUser(fodUser);

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

    /*Test 18 : Verify that field operations director can create project language engagements */
    @Test(priority = 18)
    public void VerifyThatFieldOperationsDirectorCanCreateProjectLanguageEngagements() throws InterruptedException {

        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with field operations director user");
        new LoginFunctions(driver, selenium).loginByGivenUser(fodUser);
        selenium.navigateToPage(strFodLanguageTranslationUrl);
        selenium.hardWait(3);

        Reporter.log("Step 2 - Verify the created language engagement");
        Assert.assertEquals(projectDetails.getLanguageEngagementName(fodLanguageAdminData.getLanguageEngagementName()), fodLanguageAdminData.getLanguageEngagementName(), "project language engagement name doesn't match");
    }

    /*Test 19 : Verify that field operations director can edit project language engagements except DisbursementCompleteDate and TranslationCompleteData*/ // need to edit language engagements case
    @Test(priority = 19)
    public void VerifyThatFieldOperationsDirectorCanEditProjectLanguageEngagementsExceptDisbursementCompleteDate() throws java.text.ParseException, InterruptedException {

        ProjectLanguageEngagementDetailsPO projectLanguageEngagementDetailsPO = new ProjectLanguageEngagementDetailsPO(driver);
        String textFileName = JavaHelpers.getPropertyValue(PROPERTY_TEST_FILE, "textFile");

        Reporter.log("Step 1 -  Login with field operations director user and edit language engagement in created project");
        new LoginFunctions(driver, selenium).loginByGivenUser(fodUser);
        selenium.navigateToPage(strFodLanguageTranslationUrl);
        selenium.hardWait(3);
        new LanguageFunctions(driver, selenium).editProjectLanguageEngagements(fodInternData, fodLanguageAdminData);

        Reporter.log("Step 2 - Verify the edited language engagement");
        Assert.assertEquals(projectLanguageEngagementDetailsPO.getLanguageStartEndDate(), new JavaHelpers().changeDateFormat(fodInternData.getInternshipStartDate(), "MM/dd/yyyy", "M/d/yyyy") + "\n" + " - " + "\n" + new JavaHelpers().changeDateFormat(fodInternData.getInternshipEndDate(), "MM/dd/yyyy", "M/d/yyyy"), "Internship start end date doesn't match :");
        Assert.assertEquals(projectLanguageEngagementDetailsPO.getTranslationCompleteDate(), new JavaHelpers().changeDateFormat(fodInternData.getGrowthPlanCompleteDate(), "MM/dd/yyyy", "M/dd/yyyy"), "Internship translation complete date  doesn't match");
        Assert.assertEquals(textFileName, projectLanguageEngagementDetailsPO.getUploadedFileText(), "uploaded file name doesn't match : ");
        Assert.assertEquals(projectLanguageEngagementDetailsPO.getActualDateTextBox(), new JavaHelpers().changeDateFormat(fodInternData.getInternshipActualDate(), "MM/dd/yyyy", "M/dd/yyyy"), "Internship actual date doesn't match");
        Assert.assertEquals(projectLanguageEngagementDetailsPO.getEstimatedDateTextBox(), new JavaHelpers().changeDateFormat(fodInternData.getInternshipEstimatedDate(), "MM/dd/yyyy", "MM/dd/yyyy"), "Internship estimated date doesn't match");

        Reporter.log("Step 3 - Verify that project language engagement DisbursementCompleteDateButton and TranslationCompleteDate TextBox is not enabled");
        Assert.assertFalse(projectLanguageEngagementDetailsPO.isDisbursementCompleteDateButtonEnabled(), "Internship engagement disbursement complete date button is enabled : "); //Todo bug(#762) Global R access
        Assert.assertFalse(projectLanguageEngagementDetailsPO.isTranslationCompleteDateTextBoxEnabled(), "Language engagement translation complete date text box is enabled : "); //Todo bug(#762) Global R access

    }

    /*Test 20 : Verify that field operations director can Add project goals */
    @Test(priority = 20)
    public void VerifyThatFieldOperationsDirectorCanAddProjectGoalsSuccessfully() throws InterruptedException {

        ProjectLanguageEngagementDetailsPO projectLanguageEngagementDetailsPO = new ProjectLanguageEngagementDetailsPO(driver);
        GoalDetailsPO goalDetailsPO = new GoalDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with field operations director user");
        new LoginFunctions(driver, selenium).loginByGivenUser(fodUser);
        selenium.navigateToPage(strFodProjectCreateGoalUrl);
        selenium.hardWait(3);

        Reporter.log("Step 2 - Verify goal type");
        Assert.assertEquals(projectLanguageEngagementDetailsPO.getGoalText(), (fodGoalData.getGoalStoryName()), "Goal type is not matched");

        Reporter.log("Step 3 - Click on created goal");
        projectLanguageEngagementDetailsPO.clickOnGoalCard();

        Reporter.log("Step 14 - verify goal details");
        Assert.assertEquals(Arrays.stream(goalDetailsPO.getGoalDistributionMethodsText().split(",")).map(String::trim).toArray(String[]::new), GoalDistributionMethods.getGoalDistributionMethodsByName(fodGoalData.getGoalDistributionMethods()), "Goal distribution method is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalDistributionMethodsPartnerText(), fodPartnerAdminData.getOrganizationName(), "Goal distribution method partner is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalMethodologyText(), fodGoalData.getMethodologyType() + " - " + fodGoalData.getMethodologyValue(), "Goal methodology is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalCompletionDescriptionText(), (fodGoalData.getGoalCompletionDescription()), "Goal completion description is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalScriptureReferenceOldTestamentText(), fodGoalData.getOldScriptureReference(), "Goal old scriptureReference is not matched");

        Reporter.log("Step 15 - Refresh page and verify Goal details");
        selenium.refreshPage();

        Assert.assertEquals(Arrays.stream(goalDetailsPO.getGoalDistributionMethodsText().split(",")).map(String::trim).toArray(String[]::new), GoalDistributionMethods.getGoalDistributionMethodsByName(fodGoalData.getGoalDistributionMethods()), "Goal distribution method is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalDistributionMethodsPartnerText(), fodPartnerAdminData.getOrganizationName(), "Goal distribution method partner is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalMethodologyText(), fodGoalData.getMethodologyType() + " - " + fodGoalData.getMethodologyValue(), "Goal methodology is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalCompletionDescriptionText(), (fodGoalData.getGoalCompletionDescription()), "Goal completion description is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalScriptureReferenceOldTestamentText(), fodGoalData.getOldScriptureReference(), "Goal old scriptureReference is not matched");

    }

    /*Test 21 : Verify that field operations director can edit created project goals */
    @Test(priority = 21)
    public void VerifyThatFieldOperationsDirectorCanEditProjectGoalsSuccessfully() throws InterruptedException {

        GoalDetailsPO goalDetailsPO = new GoalDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with field operations director user");
        new LoginFunctions(driver, selenium).loginByGivenUser(fodUser);
        selenium.navigateToPage(strFodProjectCreateGoalUrl);
        selenium.hardWait(3);

        Reporter.log("Step 2 - Edit Story Goal by field operations director user");
        new GoalFunctions(driver, selenium).editGoal(fodEditGoalData, fodGoalData, fodPartnerAdminData.getOrganizationName());

        Reporter.log("Step 3 - Verify the created goal");
        Assert.assertEquals(goalDetailsPO.getGoalNameText(), (fodGoalData.getGoalStoryName()), "Goal type is not matched");

        Reporter.log("Step 4 - Verify goal details");
        Assert.assertEquals(Arrays.stream(goalDetailsPO.getGoalDistributionMethodsText().split(",")).map(String::trim).toArray(String[]::new), GoalDistributionMethods.getGoalDistributionMethodsByName(fodEditGoalData.getGoalDistributionMethods()), "Goal mediums is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalDistributionMethodsPartnerText(), fodPartnerAdminData.getOrganizationName(), "Goal distribution method partner is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalMethodologyText(), fodEditGoalData.getMethodologyType() + " - " + fodEditGoalData.getMethodologyValue(), "Goal methodology is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalCompletionDescriptionText(), (fodEditGoalData.getGoalCompletionDescription()), "Goal completion description is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalScriptureReferenceOldTestamentText(), fodEditGoalData.getOldScriptureReference(), "Goal old scriptureReference is not matched");

        Reporter.log("Step 5 - Refresh page and verify Goal details");
        selenium.refreshPage();
        Assert.assertEquals(Arrays.stream(goalDetailsPO.getGoalDistributionMethodsText().split(",")).map(String::trim).toArray(String[]::new), GoalDistributionMethods.getGoalDistributionMethodsByName(fodEditGoalData.getGoalDistributionMethods()), "Goal mediums is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalDistributionMethodsPartnerText(), fodPartnerAdminData.getOrganizationName(), "Goal distribution method partner is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalMethodologyText(), fodEditGoalData.getMethodologyType() + " - " + fodEditGoalData.getMethodologyValue(), "Goal methodology is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalCompletionDescriptionText(), (fodEditGoalData.getGoalCompletionDescription()), "Goal completion description is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalScriptureReferenceOldTestamentText(), fodEditGoalData.getOldScriptureReference(), "Goal old scriptureReference is not matched");
    }
}

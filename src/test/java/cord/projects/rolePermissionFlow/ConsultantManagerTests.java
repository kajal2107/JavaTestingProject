package cord.projects.rolePermissionFlow;

import base.BaseTest;
import datafactory.*;
import dataobjects.*;
import enums.menu.CreateItemTypes;
import enums.goal.GoalDistributionMethods;
import enums.project.ProjectInternPosition;
import enums.project.ProjectInternshipMethodologies;
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
import pageobjects.cord.projects.intershipEngagement.ProjectInternshipDetailsPO;
import pageobjects.cord.projects.languageEngagement.ProjectLanguageEngagementDetailsPO;
import pageobjects.cord.projects.languageEngagement.goal.CreateGoalPO;
import pageobjects.cord.projects.languageEngagement.goal.GoalDetailsPO;
import utilities.DriverManager;
import utilities.JavaHelpers;
import utilities.SeleniumHelpers;

import java.util.Arrays;

public class ConsultantManagerTests extends BaseTest {

    protected String strConsulMngPeopleUrl;
    protected String strConsulMngLanguageUrl;
    protected String strConsulMngPartnerUrl;
    protected String strConsulMngInternshipUrl;
    protected String strConsulMngPartnershipUrl;
    protected String strConsulMngProjectCreateGoalUrl;
    protected String strConsulMngFundingPartnershipUrl;
    protected String strConsulMngProjectTeamMemberUrl;
    protected String strConsulMngLanguageTranslationUrl;
    Project consulMngAdminData = new ProjectData().getCreateProjectData();
    String consulMngCreatedProjectName = consulMngAdminData.getProjectName();
    Project consulMngAdminBudgetData = new ProjectData().getCreateProjectData();
    String consulMngCreatedBudgetProjectName = consulMngAdminBudgetData.getProjectName();
    People consulMngPeopleFpmData = new PeopleData().getPeopleData();
    Project consulMngAdminTranslationData = new ProjectData().getCreateTranslationProjectData();
    String consulMngCreatedTranslationProjectName = consulMngAdminTranslationData.getProjectName();
    People consulMngPeopleAdminData = new PeopleData().getPeopleData();
    People editConsulMngPeopleAdminData = new PeopleData().getPeopleData();
    Project consulMngEditProjectAdminData = new ProjectData().getUpdateProjectData();
    Project consulMngEditProjectFundingPartnershipAdminData = new ProjectData().getUpdateProjectData();
    Language consulMngLanguageAdminData = new LanguageData().getLanguageData();
    Partner consulMngPartnerAdminData = new PartnerData().getCreatePartnerData();
    Partner consulMngEditPartnerAdminData = new PartnerData().getEditPartnerData();
    Goal consulMngGoalData = new GoalData().getCreateGoalData();
    Project consulMngFpmData = new ProjectData().getCreateProjectData();
    Location consulMngLocationData = new LocationData().getLocationData();
    String consulMngLocationName = consulMngLocationData.getLocationName();
    Internship consulMngInternUpdateData = new EngagementData().getUpdateEngagementData();

    public ConsultantManagerTests() throws ParseException, java.text.ParseException, InterruptedException {
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
        new ProjectFunctions(driver, selenium).createProject(consulMngAdminData);
        new ProjectFunctions(driver, selenium).addMemberToProject(consultantManagerUser, consulMngAdminData);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 2 - Create Budget Project by admin user and logout that user");
        new ProjectFunctions(driver, selenium).createProject(consulMngAdminBudgetData);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 3 - Create TranslationType Project by admin user and logout that user");
        new ProjectFunctions(driver, selenium).createProject(consulMngAdminTranslationData);
        new PartnershipFunctions(driver, selenium).createProjectPartnership(consulMngPartnerAdminData ,consulMngCreatedTranslationProjectName,adminUser);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(fpmUser);

        Reporter.log("Step 4 - Create Project by project manager and logout the user");
        new ProjectFunctions(driver, selenium).createProject(consulMngFpmData);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 5 - Create People by admin user");
        new PeopleFunctions(driver, selenium).createPeople(consulMngPeopleAdminData, adminUser);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(fpmUser);

        Reporter.log("Step 6 - Create People by project manager user");
        new PeopleFunctions(driver, selenium).createPeople(consulMngPeopleFpmData, fpmUser);
        strConsulMngPeopleUrl = driver.getCurrentUrl();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 7- Create Language by admin user");
        new LanguageFunctions(driver, selenium).createLanguage(consulMngLanguageAdminData);
        strConsulMngLanguageUrl = driver.getCurrentUrl();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(consultantManagerUser);

        Reporter.log("Step 8 - Create internship engagements by consultant manager user");
        new InternshipEngagementsFunctions(driver, selenium).createProjectInternshipEngagements(consultantManagerUser, consulMngCreatedProjectName);
        strConsulMngInternshipUrl = driver.getCurrentUrl();
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(consultantManagerUser);

        Reporter.log("Step 9 - Create language engagements by consultant manager user");
        new LanguageFunctions(driver, selenium).createProjectLanguageEngagements(consulMngLanguageAdminData, consulMngCreatedTranslationProjectName, consultantManagerUser);
        strConsulMngLanguageTranslationUrl = driver.getCurrentUrl();
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 10- Create Partner by admin user");
        new PartnerFunctions(driver, selenium).createPartner(consulMngPartnerAdminData);
        strConsulMngPartnerUrl = driver.getCurrentUrl();
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 11 - Create partnership by admin user");
        new PartnershipFunctions(driver, selenium).createProjectPartnership(consulMngPartnerAdminData, consulMngCreatedProjectName, consultantManagerUser);
        strConsulMngPartnershipUrl = driver.getCurrentUrl();
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(consultantManagerUser);

        Reporter.log("Step 12 - Add project team member with multiple user role");
        new ProjectFunctions(driver, selenium).addMemberToProject(rdFodUser, consulMngAdminData);
        strConsulMngProjectTeamMemberUrl = driver.getCurrentUrl();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 13 - Create Location by admin user");
        new LocationFunctions(driver, selenium).createLocation(consulMngLocationData);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 14- Create goal by admin user");
        selenium.navigateToPage(strConsulMngLanguageTranslationUrl);
        new GoalFunctions(driver, selenium).createGoal(consulMngGoalData, consulMngLanguageAdminData.getLanguageEngagementName(),consulMngPartnerAdminData.getOrganizationName()); // todo bug (#1039)
        strConsulMngProjectCreateGoalUrl = driver.getCurrentUrl();

        Reporter.log("Step 15 - Create funding partnership by admin user");
        new ProjectFunctions(driver, selenium).editInternshipProjectByName(consulMngEditProjectFundingPartnershipAdminData, consulMngCreatedBudgetProjectName, adminUser);
        new PartnershipFunctions(driver, selenium).createProjectFundingPartnership(consulMngPartnerAdminData, consulMngEditProjectFundingPartnershipAdminData.getProjectName());
        strConsulMngFundingPartnershipUrl = driver.getCurrentUrl();
        drivermanager.tearDown();
    }

    /*Test 1 : Verify that consultant manager can view all projects*/
    @Test(priority = 1)
    public void VerifyThatConsultantManagerCanViewAllProjectsSuccessfully() throws InterruptedException {

        LeftNavPO sideHeader = new LeftNavPO(driver);
        ProjectsListingPO projectsListingPO = new ProjectsListingPO(driver);
        ProjectDetailsPO projectDetailsPO = new ProjectDetailsPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with consultant manager user");
        new LoginFunctions(driver, selenium).loginByGivenUser(consultantManagerUser);

        Reporter.log("Step 2 - Verify that projects is displayed which is created by admin");
        sideHeader.clickOnProjectsMenu();
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());

        if (!projectsListingPO.isProjectPresent(consulMngAdminData.getProjectName())) {
            headerPO.searchItemGloballyByName(consulMngAdminData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(consulMngAdminData.getProjectName());
        Assert.assertEquals(projectDetailsPO.getProjectName(), consulMngAdminData.getProjectName(), "Project is not present : " + consulMngAdminData.getProjectName());

        Reporter.log("Step 3 - Verify that projects is displayed which is created by FPM user");
        sideHeader.clickOnProjectsMenu();
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());

        if (!projectsListingPO.isProjectPresent(consulMngFpmData.getProjectName())) {
            headerPO.searchItemGloballyByName(consulMngFpmData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(consulMngFpmData.getProjectName());
        Assert.assertEquals(projectDetailsPO.getProjectName(), consulMngFpmData.getProjectName(), "Project is not present : " + consulMngFpmData.getProjectName());

    }

    /*Test 2 : Verify that consultant manager can view all peoples*/
    @Test(priority = 2)
    public void VerifyThatConsultantManagerCanViewAllPeoplesSuccessfully() throws InterruptedException {

        LeftNavPO sideHeader = new LeftNavPO(driver);
        PeoplesPO peoples = new PeoplesPO(driver);
        PeopleDetailsPO peopleDetails = new PeopleDetailsPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with consultant manager user");
        selenium.navigateToPage(strConsulMngPeopleUrl);
        new LoginFunctions(driver, selenium).loginByGivenUser(consultantManagerUser);

        Reporter.log("Step 2 - Verify that peoples is displayed which is created by admin");
        sideHeader.clickOnPeopleMenu();
        if (!peoples.isPeoplePresent(consulMngPeopleAdminData.getPersonName())) {
            headerPO.searchItemGloballyByName(consulMngPeopleAdminData.getPersonName());
        }
        peoples.clickOnPeopleName(consulMngPeopleAdminData.getPersonName());
        Assert.assertEquals(peopleDetails.getPeopleName().replaceAll("\\s.*", ""), consulMngPeopleAdminData.getPersonName(), "Project is not present : " + consulMngPeopleAdminData.getPersonName());

        Reporter.log("Step 3 - Verify that peoples is displayed which is created by FPM user");
        sideHeader.clickOnPeopleMenu();
        if (!peoples.isPeoplePresent(consulMngPeopleFpmData.getPersonName())) {
            headerPO.searchItemGloballyByName(consulMngPeopleFpmData.getPersonName());
        }
        peoples.clickOnPeopleName(consulMngPeopleFpmData.getPersonName());
        Assert.assertEquals(peopleDetails.getPeopleName().replaceAll("\\s.*", ""), consulMngPeopleFpmData.getPersonName(), "Project is not present : " + consulMngPeopleFpmData.getPersonName());
    }

    /*Test 3 : Verify that consultant manager can edit mine peoples */
    @Test(priority = 3)
    public void VerifyThatConsultantManagerCanEditMinePeoples() throws InterruptedException {

        PeoplesPO peoples = new PeoplesPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);
        LeftNavPO sideHeader = new LeftNavPO(driver);
        UpdatePeoplePO updatePeople = new UpdatePeoplePO(driver);
        PeopleDetailsPO peopleDetails = new PeopleDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with consultant manager user");
        new LoginFunctions(driver, selenium).loginByGivenUser(consultantManagerUser);

        Reporter.log("Step 2 - Search user which have a consultant manager role by email and click on people name");
        sideHeader.clickOnPeopleMenu();
        if (!peoples.isPeoplePresent(consultantManagerUser.getEmail())) {
            headerPO.searchItemGloballyByName(consultantManagerUser.getEmail());
        }
        peoples.clickOnPeopleName(consultantManagerUser.getRealFirstName() + " " + consultantManagerUser.getRealLastName());

        Reporter.log("Step 3 - Click on edit icon and edit mine person detail and click on submit button");
        peopleDetails.clickOnEditButton();
        updatePeople.clearAndUpdatePersonDetail(editConsulMngPeopleAdminData);
        updatePeople.clickOnSubmitButton();

        Reporter.log("Step 4 - Verify updated person details");
        Assert.assertEquals(editConsulMngPeopleAdminData.getPersonEmail(), peopleDetails.getEmail(), "email doesn't match : ");
        Assert.assertEquals(editConsulMngPeopleAdminData.getPersonTitle(), peopleDetails.getTitle(), "title doesn't match : ");
        Assert.assertEquals(editConsulMngPeopleAdminData.getPersonPhone(), peopleDetails.getPhoneNumber(), "phone number doesn't match : ");
        Assert.assertEquals(editConsulMngPeopleAdminData.getPersonAbout(), peopleDetails.getAbout(), "About doesn't match : ");
        Assert.assertEquals(UserRole.ConsultantManager.getRole(), peopleDetails.getRoles(), "Roles doesn't match : ");

        Reporter.log("Step 5 - Again Click on edit icon and reset the people");
        peopleDetails.clickOnEditButton();
        updatePeople.fillFirstnameLastNameAndEmailDetail(consultantManagerUser.getRealFirstName(), consultantManagerUser.getRealLastName(), consultantManagerUser.getEmail());
        updatePeople.clickOnSubmitButton();
        Assert.assertEquals(consultantManagerUser.getRealFirstName() + " " + consultantManagerUser.getRealLastName(), peopleDetails.getPeopleName(), "personName doesn't match : ");
        Assert.assertEquals(consultantManagerUser.getEmail(), peopleDetails.getEmail(), "personEmail doesn't match : ");

    }

    /*Test 4 : Verify that consultant manager can view all languages and edit icon is not present*/
    @Test(priority = 4)
    public void VerifyThatConsultantManagerCanViewAllLanguagesAndEditIconIsNotPresent() throws InterruptedException {

        LeftNavPO sideHeader = new LeftNavPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);
        LanguagesPO languages = new LanguagesPO(driver);
        LanguageDetailPO languageDetailPO = new LanguageDetailPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with consultant manager user");
        selenium.navigateToPage(strConsulMngLanguageUrl);
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).loginByGivenUser(consultantManagerUser);

        Reporter.log("Step 2 - Verify that language is displayed which is created by admin and edit icon is not present");
        sideHeader.clickOnLanguagesMenu();
        if (!languages.isLanguagePresent(consulMngLanguageAdminData.getLanguageName())) {
            headerPO.searchItemGloballyByName(consulMngLanguageAdminData.getLanguageName());
        }
        languages.clickOnLanguageName(consulMngLanguageAdminData.getLanguageName());
        Assert.assertEquals(languageDetailPO.getLanguageName(), consulMngLanguageAdminData.getPubliicName(), "Language is not present : " + consulMngLanguageAdminData.getPubliicName());
        Assert.assertFalse(languageDetailPO.isEditIconPresent(), "Language edit icon is present : ");

    }

    /*Test 5 : Verify that consultant manager can view all partners except point of contact id*/
    @Test(priority = 5)
    public void VerifyThatConsultantManagerCanViewAllPartnersSuccessfullyExceptPointOfContactId() throws InterruptedException {

        HeaderPO headerPO = new HeaderPO(driver);
        LeftNavPO sideHeader = new LeftNavPO(driver);
        PartnersListingPO partnerListing = new PartnersListingPO(driver);
        PartnerDetailsPO partnerDetailsPO = new PartnerDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with consultant manager user");
        new LoginFunctions(driver, selenium).loginByGivenUser(consultantManagerUser);

        Reporter.log("Step 2 - Verify that partner is displayed which is created by admin");
        sideHeader.clickOnPartnersMenu();
        if (!partnerListing.isPartnerPresent(consulMngPartnerAdminData.getPartnerName())) {
            headerPO.searchItemGloballyByName(consulMngPartnerAdminData.getPartnerName());
        }
        partnerListing.clickOnPartnerName(consulMngPartnerAdminData.getPartnerName());

        Assert.assertTrue(partnerListing.isPartnerPresent(consulMngPartnerAdminData.getPartnerName()), "Partner is not present : " + consulMngPartnerAdminData.getPartnerName());
        Assert.assertFalse(partnerDetailsPO.isPartnerPointOfContactButtonEnabled(), "Point of contact id is readable : ");// todo bug(#2150)
    }

    /*Test 6 : Verify that consultant manager can't edit partners details */
    @Test(priority = 6)
    public void VerifyThatConsultantManagerCanNotEditAllPartnersDetails() throws InterruptedException {

        PartnerDetailsPO partnerDetailsPO = new PartnerDetailsPO(driver);
        EditPartnerPO editPartnerPO = new EditPartnerPO(driver);

        Reporter.log("Step 1 - Login with consultant manager user");
        new LoginFunctions(driver, selenium).loginByGivenUser(consultantManagerUser);

        Reporter.log("Step 2 - Verify that partner global innovations client is not edited by consultant manager user ");
        new PartnerFunctions(driver, selenium).editPartnerGlobalInnovationsClient(consulMngPartnerAdminData.getPartnerName());
        Assert.assertTrue(editPartnerPO.isGlobalInnovationsClientErrorMessagePresent(), "Partner global innovations client error message is not present : ");
        editPartnerPO.clickOnCancelButton();

        Reporter.log("Step 3 - Verify that partner active inActive status is not edited by consultant manager user ");
        new PartnerFunctions(driver, selenium).editPartnerActiveInActiveStatus(consulMngPartnerAdminData.getPartnerName());
        Assert.assertTrue(editPartnerPO.isActiveInActiveErrorMessagePresent(), "Partner active inActive error message is not present : ");
        editPartnerPO.clickOnCancelButton();

        Reporter.log("Step 4 - Verify that partner pmc entity code is not edited by consultant manager user ");
        new PartnerFunctions(driver, selenium).editPartnerPmcEntityCode(consulMngPartnerAdminData.getPartnerName(), consulMngEditPartnerAdminData);
        Assert.assertTrue(editPartnerPO.isPmcEntityCodeErrorMessagePresent(), "Partner pmc entity code error message is not present : ");
        editPartnerPO.clickOnCancelButton();

        Reporter.log("Step 5 - Verify that partner point of contact is not edited by consultant manager user ");
        new PartnerFunctions(driver, selenium).editPartnerPointOfContact(consulMngPartnerAdminData.getPartnerName(), consultantUser);
        Assert.assertTrue(editPartnerPO.isPointOfContactErrorMessagePresent(), "Partner point of contact error message is not present : ");
        editPartnerPO.clickOnCancelButton();

        Reporter.log("Step 6 - Verify that partner types address is not edited by consultant manager user ");
        Assert.assertFalse(partnerDetailsPO.isPartnerTypesEditButtonEnabled(), "Partner types edit button is enabled : ");
        Assert.assertFalse(partnerDetailsPO.isPartnerAddressEditButtonEnabled(), "Partner address edit button is enabled: ");

    }

    /*Test 7 : Verify that consultant manager can upload/Edit project files while member of the project */
    @Test(priority = 7)
    public void VerifyThatConsultantManagerCanUploadEditProjectFilesWhileMemberOfTheProjectSuccessfully() throws InterruptedException {

        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectDetailsFilesListingPO projectDetailsFilesListingPO = new ProjectDetailsFilesListingPO(driver);
        ProjectDetailsFilesHistoryListingPO projectDetailsFilesHistoryListingPO = new ProjectDetailsFilesHistoryListingPO(driver);
        ProjectDetailsFileListingRenameFileFolderPO projectDetailsFileListingRenameFileFolderPO = new ProjectDetailsFileListingRenameFileFolderPO(driver);
        ProjectDetailsFileListingDeleteFileFolderPO projectDetailsFileListingDeleteFileFolderPO = new ProjectDetailsFileListingDeleteFileFolderPO(driver);

        String textFileName = JavaHelpers.getPropertyValue(PROPERTY_TEST_FILE, "textFile");

        Reporter.log("Step 1 - Entering valid sign in details and login to application");
        new LoginFunctions(driver, selenium).loginByGivenUser(consultantManagerUser);

        Reporter.log("Step 2 -  Login with consultant manager user and upload file in created project");
        new FileFunctions(driver, selenium).uploadFile(consulMngCreatedProjectName, textFileName);

        Reporter.log("Step 3 - Verify that project file is uploaded by consultant manager user ");
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
        projectDetailsFileListingRenameFileFolderPO.clearAndRenameFileFolderName(consulMngEditProjectAdminData);
        Assert.assertTrue(projectDetailsFilesHistoryListingPO.isFilePresent(consulMngEditProjectAdminData.getFileName() + ".txt"), "renamed file is not present : ");
        Assert.assertFalse(projectDetailsFilesHistoryListingPO.isFilePresent(versionFileName), "renamed file is present : ");

        Reporter.log("Step 8 - Click on history kebab menu and delete created file");
        projectDetailsFilesHistoryListingPO.clickOnKebabMenuAndDeleteFileFolder(projectDetailsFilesListingPO.getFirstFileUploadText());
        projectDetailsFileListingDeleteFileFolderPO.clickOnFileDeleteButton();
        Assert.assertFalse(projectDetailsFilesListingPO.isFileFolderPresent(consulMngEditProjectAdminData.getFileName()), "deleted file is present : ");

    }

    /*Test 8 : Verify that consultant manager can read project budget */ //todo (bug #1947)
    @Test(priority = 8)
    public void VerifyThatConsultantManagerCanReadProjectBudgetSuccessfully() throws InterruptedException {

        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectDetailsBudgetListingPO projectDetailsBudgetListingPO = new ProjectDetailsBudgetListingPO(driver);
        ProjectDetailsPartnershipsListingPO projectDetailsPartnershipsListingPO = new ProjectDetailsPartnershipsListingPO(driver);

        Reporter.log("Step 1 - Entering valid sign in details and login to application");
        new LoginFunctions(driver, selenium).loginByGivenUser(consultantManagerUser);

        Reporter.log("Step 2 - Navigate on partnership detail page and click on project link button");
        selenium.navigateToPage(strConsulMngFundingPartnershipUrl);
        selenium.hardWait(3);
        projectDetailsPartnershipsListingPO.clickOnProjectLinkButton();

        Reporter.log("Step 3 - Click on budget detail button");
        projectDetails.clickOnBudgetButton();

        Reporter.log("Step 4 - Verify that project budget is not edited by consultant manager user");
        Assert.assertFalse(projectDetailsBudgetListingPO.isAddUniversalTemplateButtonEnabled(), "Budget add universal template file button is enabled : ");
        Assert.assertFalse(projectDetailsBudgetListingPO.isClickToEditButtonLinkPresent(), "Budget click to edit button link is present: ");
    }

    /*Test 9 : Verify that consultant manager can add project team member */
    @Test(priority = 9)
    public void VerifyThatConsultantManagerCanAddProjectTeamMemberSuccessfully() throws InterruptedException {

        ProjectDetailsTeamMembersListingPO projectDetailsTeamMembersListingPO = new ProjectDetailsTeamMembersListingPO(driver);

        Reporter.log("Step 1 -  Login with consultant manager user and add team member in created project");
        new LoginFunctions(driver, selenium).loginByGivenUser(consultantManagerUser);
        selenium.navigateToPage(strConsulMngProjectTeamMemberUrl);

        selenium.hardWait(3);

        Reporter.log("Step 2 - Verify that project team member is added by consultant manager user");
        Assert.assertEquals(projectDetailsTeamMembersListingPO.getMembersName(rdFodUser.getRealFirstName() + " " + rdFodUser.getRealLastName()), (rdFodUser.getRealFirstName() + " " + rdFodUser.getRealLastName()), "project member name doesn't match : ");
        Assert.assertEquals(projectDetailsTeamMembersListingPO.getMembersRoleByMemberName(rdFodUser.getRealFirstName() + " " + rdFodUser.getRealLastName()), rdFodUser.getUserRoles().get(0), "project member role doesn't match : ");
    }

    /*Test 10 : Verify that consultant manager can edit project team member */
    @Test(priority = 10)
    public void VerifyThatConsultantManagerCanEditProjectTeamMemberSuccessfully() throws InterruptedException {

        ProjectDetailsTeamMembersListingPO projectDetailsTeamMembersListingPO = new ProjectDetailsTeamMembersListingPO(driver);

        Reporter.log("Step 1 -  Login with consultant manager user and edit team member in created project");
        new LoginFunctions(driver, selenium).loginByGivenUser(consultantManagerUser);
        selenium.navigateToPage(strConsulMngProjectTeamMemberUrl);
        selenium.hardWait(3);
        new TeamMemberFunctions(driver, selenium).editProjectTeamMember(rdFodUser);

        Reporter.log("Step 2 - Verify that project team member is edited by consultant manager user");
        Assert.assertEquals(projectDetailsTeamMembersListingPO.getMembersRoleByMemberName(rdFodUser.getRealFirstName() + " " + rdFodUser.getRealLastName()), rdFodUser.getUserRoles().get(0) + ", " + rdFodUser.getUserRoles().get(1), "project member updated role doesn't match : ");
    }

    /*Test 11 : Verify that consultant manager can not create project partnership */ //(bug) Added question in slack
    @Test(priority = 11)
    public void VerifyThatConsultantManagerCanNotCreateProjectPartnership() throws InterruptedException {

        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        LeftNavPO sideHeader = new LeftNavPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);
        ProjectsListingPO projectsListingPO = new ProjectsListingPO(driver);
        ProjectDetailsPartnershipsListingPO projectDetailsPartnershipsListingPO = new ProjectDetailsPartnershipsListingPO(driver);

        Reporter.log("Step 1 - Login with consultant manager user");
        new LoginFunctions(driver, selenium).loginByGivenUser(consultantManagerUser);

        Reporter.log("Step 2 - Verify that project is displayed which is created by admin user ");
        sideHeader.clickOnProjectsMenu();
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());

        if (!projectsListingPO.isProjectPresent(consulMngAdminData.getProjectName())) {
            headerPO.searchItemGloballyByName(consulMngAdminData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(consulMngAdminData.getProjectName());

        Reporter.log("Step 3 - Click on partnerships button");
        projectDetails.clickOnPartnershipsButton();

        Reporter.log("Step 4 - Verify that project partnership is not created by consultant manager user");
        Assert.assertFalse(projectDetailsPartnershipsListingPO.isProjectAddPartnershipButtonPresent(), "Project add partnership button is present : ");

    }

    /*Test 12 : Verify that consultant manager can read project partnership*/
    @Test(priority = 12)
    public void VerifyThatConsultantManagerCanReadProjectPartnership() throws InterruptedException {

        ProjectDetailsPartnershipsEditPO projectDetailsPartnershipsEditPO = new ProjectDetailsPartnershipsEditPO(driver);
        ProjectDetailsPartnershipsListingPO partnershipsListingPO = new ProjectDetailsPartnershipsListingPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with consultant manager user");
        new LoginFunctions(driver, selenium).loginByGivenUser(consultantManagerUser);
        selenium.navigateToPage(strConsulMngPartnershipUrl);

        selenium.hardWait(3);

        Reporter.log("Step 2 - Click on partnership edit button");
        partnershipsListingPO.clickOnEditOrganizationButton();

        Reporter.log("Step 3 - Verify that project partnership is not edited by consultant manager user");
        Assert.assertFalse(projectDetailsPartnershipsEditPO.isPartnershipTypesButtonEnabled(), "Partnership Types checkbox is enabled : "); // Todo (bug #888)
        Assert.assertFalse(projectDetailsPartnershipsEditPO.isFinancialReportingTypeRadioButtonEnabled(), "Partnership Financial Reporting Type radio button is enabled : ");
        Assert.assertFalse(projectDetailsPartnershipsEditPO.isAgreementStatusEnabled(), "Partnership Agreement  Status radio button is enabled : ");
        Assert.assertFalse(projectDetailsPartnershipsEditPO.isMouStatusEnabled(), "Partnership Mou Status radio button is enabled : ");
        Assert.assertFalse(projectDetailsPartnershipsEditPO.isMouStartDateTextBoxEnabled(), "Partnership mou start date text box is enabled : ");
        Assert.assertFalse(projectDetailsPartnershipsEditPO.isMouEndDateTextBoxEnabled(), "Partnership  mou end date text box is enabled : ");

    }

    /*Test 13 : Verify that consultant manager can create project internship engagements */
    @Test(priority = 13)
    public void VerifyThatConsultantManagerCanCreateProjectInternshipEngagementsSuccessfully() throws InterruptedException {

        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with consultant manager user");
        new LoginFunctions(driver, selenium).loginByGivenUser(consultantManagerUser);
        selenium.navigateToPage(strConsulMngInternshipUrl);

        selenium.hardWait(3);

        Reporter.log("Step 2 - Verify the created internship engagement");
        Assert.assertEquals(projectDetails.getInternshipName(consultantManagerUser.getRealFirstName() + " " + consultantManagerUser.getRealLastName()), (consultantManagerUser.getRealFirstName() + " " + consultantManagerUser.getRealLastName()), "project internship name doesn't match : ");
    }

    /*Test 14 : Verify that consultant manager can edit project internship engagements while he/she member of the project */
    @Test(priority = 14)
    public void VerifyThatConsultantManagerCanEditProjectInternshipEngagementsSuccessfullyWhileHESHEMemberOfTheProject() throws java.text.ParseException, InterruptedException {

        ProjectInternshipDetailsPO projectInternshipDetailsPO = new ProjectInternshipDetailsPO(driver);

        Reporter.log("Step 1 -  Login with consultant manager user and edit internship engagement in created project");
        new LoginFunctions(driver, selenium).loginByGivenUser(consultantManagerUser);
        selenium.navigateToPage(strConsulMngInternshipUrl);

        selenium.hardWait(3);

        new InternshipEngagementsFunctions(driver, selenium).editProjectInternshipEngagements(consultantManagerUser);
        String newVersionFileName = JavaHelpers.getPropertyValue(PROPERTY_TEST_FILE, "newVersionFile");

        Reporter.log("Step 2 - Verify that project internship is edited by consultant manager user");
        Assert.assertEquals(projectInternshipDetailsPO.getInternshipStartEndDate(), new JavaHelpers().changeDateFormat(consulMngInternUpdateData.getInternshipStartDate(), "MM/dd/yyyy", "M/d/yyyy") + "\n" + " - " + "\n" + new JavaHelpers().changeDateFormat(consulMngInternUpdateData.getInternshipEndDate(), "MM/dd/yyyy", "M/d/yyyy"), "Internship start end date doesn't match :");
        Assert.assertEquals(ProjectInternPosition.Administration.getInternPosition(), projectInternshipDetailsPO.getInternPosition(), "internship position doesn't match : ");
        Assert.assertEquals(newVersionFileName, projectInternshipDetailsPO.getUploadedFileText(), "uploaded file name doesn't match : ");
        Assert.assertEquals(projectInternshipDetailsPO.getMethodologiesText(), ProjectInternshipMethodologies.OneStory.getInternshipMethodology() + "\n" + ProjectInternshipMethodologies.Render.getInternshipMethodology(), "internship methodologies doesn't match : ");
        Assert.assertEquals(projectInternshipDetailsPO.getMentorText(), consultantManagerUser.getRealFirstName() + " " + consultantManagerUser.getRealLastName(), "internship mentor name doesn't match : ");
        Assert.assertEquals(projectInternshipDetailsPO.getGrowthPlanCompleteDate(), new JavaHelpers().changeDateFormat(consulMngInternUpdateData.getGrowthPlanCompleteDate(), "MM/dd/yyyy", "M/d/yyyy"), "Internship growth plan complete date  doesn't match :");

        Reporter.log("Step 3 - Verify that project internship DisbursementCompleteDateButton, GrowthPlanCompleteDateButton is not enabled");
        String disableEditButtonColor = "rgba(0, 0, 0, 0.26)";
        Assert.assertEquals(disableEditButtonColor, projectInternshipDetailsPO.getEditButtonColor(), "Edit button color doesn't match : ");
        Assert.assertFalse(projectInternshipDetailsPO.isDisbursementCompleteDateButtonEnabled(), "Internship engagement disbursement complete date button is enabled : ");

    }

    /*Test 15 : Verify that consultant manager can read project location */
    @Test(priority = 15)
    public void VerifyThatConsultantManagerCanReadProjectLocation() throws InterruptedException {

        HeaderPO headerPO = new HeaderPO(driver);
        LocationsListingPO locationsListingPO = new LocationsListingPO(driver);
        LocationDetailsPO locationDetailsPO = new LocationDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with consultant manager user");
        new LoginFunctions(driver, selenium).loginByGivenUser(consultantManagerUser);

        Reporter.log("Step 2 - Enter location name and click on view location button");
        headerPO.searchItemGloballyByName(consulMngLocationName);
        locationsListingPO.clickOnFirstViewLocationButton();

        Reporter.log("Step 3 - Verify the location edit button is not present");
        Assert.assertFalse(locationDetailsPO.isLocationEditButtonPresent(), "Location edit button is pre" +
                "sent:");
    }

    /*Test 16 : Verify that consultant manager can not create language partner project and location */
    @Test(priority = 16)
    public void VerifyThatConsultantManagerCanNotCreateLanguagePartnerProjectAndLocation() throws InterruptedException {

        LeftNavPO leftNavPO = new LeftNavPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with consultant manager user");
        new LoginFunctions(driver, selenium).loginByGivenUser(consultantManagerUser);

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

    /*Test 17 : Verify that consultant manager can not create project language engagements */   // Not clarification for create in new Sheet
    @Test(priority = 17)
    public void VerifyThatConsultantManagerCanNotCreateProjectLanguageEngagements() throws InterruptedException {

        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with consultant manager user");
        new LoginFunctions(driver, selenium).loginByGivenUser(consultantManagerUser);
        selenium.navigateToPage(strConsulMngLanguageTranslationUrl);

        selenium.hardWait(3);

        Reporter.log("Step 2 - Verify project language engagement add button is not present");
        Assert.assertFalse(projectDetails.isProjectLanguageEngagementAddButtonPresent(), "Add language engagement button is present:");

        Reporter.log("Step 3 - Verify the created language engagement");
        Assert.assertEquals(projectDetails.getLanguageEngagementName(consulMngLanguageAdminData.getLanguageEngagementName()), consulMngLanguageAdminData.getLanguageEngagementName(), "project language engagement name doesn't match");
    }

    /*Test 18 : Verify that consultant manager can read language engagements details  */ // Todo bug(#2140) language engagement have a R access

    /*Test 19 : Verify that consultant manager can Add project goals */
    @Test(priority = 18)
    public void VerifyThatConsultantManagerCanAddProjectGoalsSuccessfully() throws InterruptedException {

        GoalDetailsPO goalDetailsPO = new GoalDetailsPO(driver);
        ProjectLanguageEngagementDetailsPO projectLanguageEngagementDetailsPO = new ProjectLanguageEngagementDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with consultant manager user");
        new LoginFunctions(driver, selenium).loginByGivenUser(consultantManagerUser);
        selenium.navigateToPage(strConsulMngProjectCreateGoalUrl);
        selenium.hardWait(3);

        Reporter.log("Step 2 - Verify the created goal");
        Assert.assertEquals(projectLanguageEngagementDetailsPO.getGoalText(), (consulMngGoalData.getGoalStoryName()), "Goal type is not matched");

        Reporter.log("Step 3 - Click on created goal");
        projectLanguageEngagementDetailsPO.clickOnGoalCard();

        Reporter.log("Step 14 - Verify goal details");
        Assert.assertEquals(Arrays.stream(goalDetailsPO.getGoalDistributionMethodsText().split(",")).map(String::trim).toArray(String[]::new), GoalDistributionMethods.getGoalDistributionMethodsByName(consulMngGoalData.getGoalDistributionMethods()), "Goal distribution method is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalDistributionMethodsPartnerText(), consulMngPartnerAdminData.getOrganizationName(), "Goal distribution method partner is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalMethodologyText(), consulMngGoalData.getMethodologyType() + " - " + consulMngGoalData.getMethodologyValue(), "Goal methodology is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalCompletionDescriptionText(), (consulMngGoalData.getGoalCompletionDescription()), "Goal completion description is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalScriptureReferenceOldTestamentText(), consulMngGoalData.getOldScriptureReference(), "Goal old scriptureReference is not matched");

        Reporter.log("Step 15 - Refresh page and verify Goal details");
        selenium.refreshPage();

        Assert.assertEquals(Arrays.stream(goalDetailsPO.getGoalDistributionMethodsText().split(",")).map(String::trim).toArray(String[]::new), GoalDistributionMethods.getGoalDistributionMethodsByName(consulMngGoalData.getGoalDistributionMethods()), "Goal distribution method is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalDistributionMethodsPartnerText(), consulMngPartnerAdminData.getOrganizationName(), "Goal distribution method partner is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalMethodologyText(), consulMngGoalData.getMethodologyType() + " - " + consulMngGoalData.getMethodologyValue(), "Goal methodology is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalCompletionDescriptionText(), (consulMngGoalData.getGoalCompletionDescription()), "Goal completion description is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalScriptureReferenceOldTestamentText(), consulMngGoalData.getOldScriptureReference(), "Goal old scriptureReference is not matched");

    }

    /*Test 20 : Verify that consultant manager can read created project goals */
    @Test(priority = 19)
    public void VerifyThatConsultantManagerCanReadProjectGoals() throws InterruptedException {

        CreateGoalPO createGoalPO = new CreateGoalPO(driver);
        ProjectLanguageEngagementDetailsPO projectLanguageEngagementDetailsPO = new ProjectLanguageEngagementDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with consultant manager user");
        new LoginFunctions(driver, selenium).loginByGivenUser(consultantManagerUser);
        selenium.navigateToPage(strConsulMngProjectCreateGoalUrl);

        selenium.hardWait(3);

        Reporter.log("Step 2 - Click on any goal and click on edit button on goal detail page");
        projectLanguageEngagementDetailsPO.clickOnGoalCard();
        projectLanguageEngagementDetailsPO.clickEditGoalButton();

        Reporter.log("Step 3 - Verify that consultant manager can read created project goals");
        String disableGoalColor = "rgba(60, 68, 78, 1)";
        Assert.assertEquals(disableGoalColor, createGoalPO.getDisabledEditGoalDetailsColor(), "Goal detail color doesn't match : ");

    }

    /*Test 21 : Verify that consultant manager can not edit projects */
    @Test(priority = 20)
    public void VerifyThatConsultantManagerCanNotEditProjectsSuccessfully() throws InterruptedException {

        LeftNavPO sideHeader = new LeftNavPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);
        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectsListingPO projectsListingPO = new ProjectsListingPO(driver);

        Reporter.log("Step 1 -  Login with consultant manager user and edit created project");
        new LoginFunctions(driver, selenium).loginByGivenUser(consultantManagerUser);
        new ProjectFunctions(driver, selenium).editInternshipProjectByName(consulMngEditProjectAdminData, consulMngCreatedProjectName, consultantManagerUser);

        Reporter.log("Step 2 - Verify that project is displayed which is edited by consultant manager user ");
        sideHeader.clickOnProjectsMenu();
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());

        if (!projectsListingPO.isProjectPresent(consulMngEditProjectAdminData.getProjectName())) {
            headerPO.searchItemGloballyByName(consulMngEditProjectAdminData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(consulMngEditProjectAdminData.getProjectName());

        Reporter.log("Step 3 - Verify that project is not editable");//Todo bug(#2144)
        Assert.assertFalse(projectDetails.isEditIconPresent(), "Project edit icon is present : ");
    }

}

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
import pageobjects.cord.location.CreateUpdateLocationPO;
import pageobjects.cord.location.LocationDetailsPO;
import pageobjects.cord.location.LocationsListingPO;
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

public class AdministratorTests extends BaseTest {

    protected String strPeopleUrl;
    protected String strLanguageUrl;
    protected String strInternshipUrl;
    protected String strLanguageTranslationUrl;
    protected String strPartnershipUrl;
    protected String strFundingPartnershipUrl;
    protected String strProjectCreateGoalUrl;
    protected String strProjectTeamMemberUrl;

    Project adminData = new ProjectData().getCreateProjectData();
    String createdProjectName = adminData.getProjectName();
    Project adminBudgetData = new ProjectData().getCreateProjectData();
    String createdBudgetProjectName = adminBudgetData.getProjectName();
    Project adminTranslationData = new ProjectData().getCreateTranslationProjectData();
    String createdTranslationProjectName = adminTranslationData.getProjectName();
    People peopleRdData = new PeopleData().getPeopleData();
    Location locationData = new LocationData().getLocationData();
    String LocationName = locationData.getLocationName();
    Budget budgetData = new BudgetData().getBudgetData();
    Project editProjectAdminData = new ProjectData().getUpdateProjectData();
    People peopleAdminData = new PeopleData().getPeopleData();
    Goal goalData = new GoalData().getCreateGoalData();
    Project editProjectBudgetData = new ProjectData().getUpdateProjectData();
    People editPeopleAdminData = new PeopleData().getPeopleData();
    Language languageAdminData = new LanguageData().getLanguageData();
    Partner partnerAdminData = new PartnerData().getCreatePartnerData();
    Partner partnerShipAdminData = new PartnerData().getCreatePartnerData();
    Partner budgetPartnerAdminData = new PartnerData().getCreatePartnerData();
    Partner editPartnerAdminData = new PartnerData().getEditPartnerData();
    Project rdData = new ProjectData().getCreateProjectData();
    Goal editGoalData = new GoalData().getCreateGoalData();
    Internship internUpdateData = new EngagementData().getUpdateEngagementData();
    Internship internData = new EngagementData().getCreateEngagementData();

    public AdministratorTests() throws ParseException, java.text.ParseException, InterruptedException {
    }

    public static final String PROPERTY_TEST_FILE = "src/main/resources/testData/TestFiles.properties";

    @BeforeClass
    public void setupTestData() throws InterruptedException {

        WebDriver driver;
        DriverManager drivermanager;
        drivermanager = new DriverManager();
        driver = drivermanager.setUp("chrome");
        SeleniumHelpers selenium = new SeleniumHelpers(driver);

        Reporter.log("Step 1 - Create Project by admin  user, add project manager and logout that user");
        new LoginFunctions(driver, selenium).loginByGivenUser(adminUser);
        new ProjectFunctions(driver, selenium).createProject(adminData);
        new ProjectFunctions(driver, selenium).addMemberToProject(fpmUser, adminData);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 2 - Create Budget Project by admin user and logout that user");
        new ProjectFunctions(driver, selenium).createProject(adminBudgetData);
        new ProjectFunctions(driver, selenium).addMemberToProject(fpmUser, adminBudgetData);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(fpmUser);

        Reporter.log("Step 3 - Create TranslationType Project by project manager user, add admin and logout that user");
        new ProjectFunctions(driver, selenium).createProject(adminTranslationData);
        new PartnershipFunctions(driver, selenium).createProjectPartnership(partnerShipAdminData ,createdTranslationProjectName,adminUser);
        new ProjectFunctions(driver, selenium).addMemberToProject(adminUser, adminTranslationData);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(rdUser);

        Reporter.log("Step 4 - Create Project by regional director and add admin to that project");
        new ProjectFunctions(driver, selenium).createProject(rdData);
        new ProjectFunctions(driver, selenium).addMemberToProject(fpmUser, rdData);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 5 - Create People by admin user");
        new PeopleFunctions(driver, selenium).createPeople(peopleAdminData, adminUser);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(rdUser);

        Reporter.log("Step 6 - Create People by regional director user");
        new PeopleFunctions(driver, selenium).createPeople(peopleRdData, rdUser);
        strPeopleUrl = driver.getCurrentUrl();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 7 - Create Language by admin user");
        new LanguageFunctions(driver, selenium).createLanguage(languageAdminData);
        strLanguageUrl = driver.getCurrentUrl();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 8 - Create internship engagements by admin user");
        new InternshipEngagementsFunctions(driver, selenium).createProjectInternshipEngagements(adminUser, createdProjectName);
        strInternshipUrl = driver.getCurrentUrl();
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 9 - Create language engagements by admin user");
        new LanguageFunctions(driver, selenium).createProjectLanguageEngagements(languageAdminData, createdTranslationProjectName, adminUser);
        strLanguageTranslationUrl = driver.getCurrentUrl();
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(controllerUser);

        Reporter.log("Step 10 - Create Partner by controller user");
        new PartnerFunctions(driver, selenium).createPartner(partnerAdminData);
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 11 - Create partnership by admin user");
        new PartnershipFunctions(driver, selenium).createProjectPartnership(partnerShipAdminData, createdProjectName, adminUser);
        strPartnershipUrl = driver.getCurrentUrl();
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 12 - Create Location by admin user");
        new LocationFunctions(driver, selenium).createLocation(locationData);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 13 - Create goal by admin user");
        selenium.navigateToPage(strLanguageTranslationUrl);
        new GoalFunctions(driver, selenium).createGoal(goalData, languageAdminData.getLanguageEngagementName(),partnerShipAdminData.getOrganizationName());
        strProjectCreateGoalUrl = driver.getCurrentUrl();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 14 - Add project team member with multiple user role");
        new ProjectFunctions(driver, selenium).addMemberToProject(imFpmUser, adminData);
        strProjectTeamMemberUrl = driver.getCurrentUrl();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 15 - Create funding partnership by admin user");
        new ProjectFunctions(driver, selenium).editInternshipProjectByName(editProjectBudgetData, createdBudgetProjectName, adminUser);
        new PartnershipFunctions(driver, selenium).createProjectFundingPartnership(budgetPartnerAdminData, editProjectBudgetData.getProjectName());
        selenium.hardWait(3);
        strFundingPartnershipUrl = driver.getCurrentUrl();
        drivermanager.tearDown();

    }

    /*Test 1 : Verify that administrator can view all projects*/
    @Test(priority = 1)
    public void VerifyAdministratorCanViewAllProjectsSuccessfully() throws InterruptedException {

        LeftNavPO sideHeader = new LeftNavPO(driver);
        ProjectsListingPO projectsListingPO = new ProjectsListingPO(driver);
        ProjectDetailsPO projectDetailsPO = new ProjectDetailsPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with administrator user");
        new LoginFunctions(driver, selenium).loginByGivenUser(adminUser);

        Reporter.log("Step 2 - Verify that projects is displayed which is created by admin");
        sideHeader.clickOnProjectsMenu();
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());

        if (!projectsListingPO.isProjectPresent(adminData.getProjectName())) {
            headerPO.searchItemGloballyByName(adminData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(adminData.getProjectName());
        Assert.assertEquals(projectDetailsPO.getProjectName(), adminData.getProjectName(), "Project is not present : " + adminData.getProjectName());

        Reporter.log("Step 3 - Verify that projects is displayed which is created by RD user");
        sideHeader.clickOnProjectsMenu();
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());

        if (!projectsListingPO.isProjectPresent(rdData.getProjectName())) {
            headerPO.searchItemGloballyByName(rdData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(rdData.getProjectName());
        Assert.assertEquals(projectDetailsPO.getProjectName(), rdData.getProjectName(), "Project is not present : " + rdData.getProjectName());
    }

    /*Test 2 : Verify that administrator can view all peoples*/
    @Test(priority = 2)
    public void VerifyAdministratorCanViewAllPeoplesSuccessfully() throws InterruptedException {

        LeftNavPO sideHeader = new LeftNavPO(driver);
        PeoplesPO peoples = new PeoplesPO(driver);
        PeopleDetailsPO peopleDetails = new PeopleDetailsPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with administrator user");
        selenium.navigateToPage(strPeopleUrl);
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).loginByGivenUser(adminUser);

        Reporter.log("Step 2 - Verify that projects is displayed which is created by admin");
        sideHeader.clickOnPeopleMenu();
        if (!peoples.isPeoplePresent(peopleAdminData.getPersonName())) {
            headerPO.searchItemGloballyByName(peopleAdminData.getPersonName());
        }
        peoples.clickOnPeopleName(peopleAdminData.getPersonName());
        Assert.assertEquals(peopleDetails.getPeopleName().replaceAll("\\s.*", ""), peopleAdminData.getPersonName(), "Project is not present : " + peopleAdminData.getPersonName());

        Reporter.log("Step 3 - Verify that projects is displayed which is created by RD user");
        sideHeader.clickOnPeopleMenu();
        if (!peoples.isPeoplePresent(peopleRdData.getPersonName())) {
            headerPO.searchItemGloballyByName(peopleRdData.getPersonName());
        }
        peoples.clickOnPeopleName(peopleRdData.getPersonName());
        Assert.assertEquals(peopleDetails.getPeopleName().replaceAll("\\s.*", ""), peopleRdData.getPersonName(), "Project is not present : " + peopleRdData.getPersonName());

    }

    /*Test 3 : Verify that administrator can edit peoples */
    @Test(priority = 3)
    public void VerifyThatAdministratorCanEditPeoples() throws InterruptedException {

        PeoplesPO peoples = new PeoplesPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);
        LeftNavPO sideHeader = new LeftNavPO(driver);
        UpdatePeoplePO updatePeople = new UpdatePeoplePO(driver);
        PeopleDetailsPO peopleDetails = new PeopleDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with administrator user");
        new LoginFunctions(driver, selenium).loginByGivenUser(adminUser);

        Reporter.log("Step 2 - Search user which have a administrator role by email and click on people name");
        sideHeader.clickOnPeopleMenu();
        if (!peoples.isPeoplePresent(adminUser.getEmail())) {
            headerPO.searchItemGloballyByName(adminUser.getEmail());
        }
        peoples.clickOnPeopleName(adminUser.getRealFirstName() + " " + adminUser.getRealLastName());

        Reporter.log("Step 3 - Click on edit icon and edit mine person detail");
        peopleDetails.clickOnEditButton();
        updatePeople.clearAndUpdatePersonDetail(editPeopleAdminData);
        updatePeople.clickOnSubmitButton();

        Reporter.log("Step 4 - Verify updated person details");
        Assert.assertEquals(editPeopleAdminData.getPersonEmail(), peopleDetails.getEmail(), "email doesn't match : ");
        Assert.assertEquals(editPeopleAdminData.getPersonTitle(), peopleDetails.getTitle(), "title doesn't match : ");
        Assert.assertEquals(editPeopleAdminData.getPersonPhone(), peopleDetails.getPhoneNumber(), "phone number doesn't match : ");
        Assert.assertEquals(editPeopleAdminData.getPersonAbout(), peopleDetails.getAbout(), "About doesn't match : ");
        Assert.assertEquals(UserRole.Administrator.getRole(), peopleDetails.getRoles(), "Roles doesn't match : ");

        Reporter.log("Step 5 - Again Click on edit icon and reset the people");
        peopleDetails.clickOnEditButton();
        updatePeople.fillFirstnameLastNameAndEmailDetail(adminUser.getRealFirstName(), adminUser.getRealLastName(), adminUser.getEmail());
        updatePeople.clickOnSubmitButton();
        Assert.assertEquals(adminUser.getRealFirstName() + " " + adminUser.getRealLastName(), peopleDetails.getPeopleName(), "personName doesn't match : ");
        Assert.assertEquals(adminUser.getEmail(), peopleDetails.getEmail(), "personEmail doesn't match : ");

    }

    /*Test 4 : Verify that administrator can view all languages and edit icon is present*/
    @Test(priority = 4)
    public void VerifyAdministratorCanViewAllLanguagesAndEditIconIsPresent() throws InterruptedException {

        LeftNavPO sideHeader = new LeftNavPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);
        LanguagesPO languages = new LanguagesPO(driver);
        LanguageDetailPO languageDetailPO = new LanguageDetailPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with administrator user");
        selenium.navigateToPage(strLanguageUrl);
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).loginByGivenUser(adminUser);

        Reporter.log("Step 2 - Verify that language is displayed which is created by admin and edit icon is not present");
        sideHeader.clickOnLanguagesMenu();
        if (!languages.isLanguagePresent(languageAdminData.getLanguageName())) {
            headerPO.searchItemGloballyByName(languageAdminData.getLanguageName());
        }
        languages.clickOnLanguageName(languageAdminData.getLanguageName());
        Assert.assertEquals(languageDetailPO.getLanguageName(), languageAdminData.getPubliicName(), "Language is not present : " + languageAdminData.getPubliicName());
        Assert.assertTrue(languageDetailPO.isEditIconPresent(), "Language edit icon is not present : ");

    }

    /*Test 5 : Verify that administrator can view all partners*/
    @Test(priority = 5)
    public void VerifyAdministratorCanViewAllPartnersSuccessfully() throws InterruptedException {

        HeaderPO headerPO = new HeaderPO(driver);
        LeftNavPO sideHeader = new LeftNavPO(driver);
        PartnersListingPO partnerListing = new PartnersListingPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with administrator user");
        new LoginFunctions(driver, selenium).loginByGivenUser(adminUser);

        Reporter.log("Step 2 - Verify that partner is displayed which is created by admin");
        sideHeader.clickOnPartnersMenu();
        if (!partnerListing.isPartnerPresent(partnerAdminData.getPartnerName())) {
            headerPO.searchItemGloballyByName(partnerAdminData.getPartnerName());
        }
        Assert.assertTrue(partnerListing.isPartnerPresent(partnerAdminData.getPartnerName()), "Partner is not present : " + partnerAdminData.getPartnerName());

    }

    /*Test 6 : Verify that administrator can edit all partners details */
    @Test(priority = 6)
    public void VerifyThatAdministratorCanEditAllPartnersDetails() {

        PartnerDetailsPO partnerDetailsPO = new PartnerDetailsPO(driver);

        Reporter.log("Step 1 - Login with admin user");
        new LoginFunctions(driver, selenium).loginByGivenUser(adminUser);

        Reporter.log("Step 2 - Edit Partner details");
        new PartnerFunctions(driver, selenium).editProjectPartner(controllerUser, editPartnerAdminData, partnerAdminData.getPartnerName());

        Reporter.log("Step 3 - verify that partner data is edited successfully");
        Assert.assertTrue(partnerDetailsPO.isGlobalInnovationsClientPresent(), "Global Innovations Client is not present ");
        Assert.assertEquals("Active", partnerDetailsPO.getPartnerStatusByName("Active"), "Status doesn't match");
        Assert.assertEquals(editPartnerAdminData.getPartnerPmcEntityCode(), partnerDetailsPO.getPmcEntityCodeText(1), "entity code doesn't match ");
        Assert.assertEquals(editPartnerAdminData.getPartnerAddress(), partnerDetailsPO.getAddressText(), "address doesn't match ");
        Assert.assertEquals(PartnerType.Impact.toString(), partnerDetailsPO.getTypesText(), "Types doesn't match");
        Assert.assertEquals(controllerUser.getRealFirstName() + " " + controllerUser.getRealLastName(), partnerDetailsPO.getPointOfContactText(), "point of contact doesn't match ");

    }

    /*Test 7 : Verify that administrator can upload/Edit project files */
    @Test(priority = 7)
    public void VerifyThatAdministratorCanUploadEditProjectFilesSuccessfully() throws InterruptedException {

        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectDetailsFilesListingPO projectDetailsFilesListingPO = new ProjectDetailsFilesListingPO(driver);
        ProjectDetailsFilesHistoryListingPO projectDetailsFilesHistoryListingPO = new ProjectDetailsFilesHistoryListingPO(driver);
        ProjectDetailsFileListingRenameFileFolderPO projectDetailsFileListingRenameFileFolderPO = new ProjectDetailsFileListingRenameFileFolderPO(driver);
        ProjectDetailsFileListingDeleteFileFolderPO projectDetailsFileListingDeleteFileFolderPO = new ProjectDetailsFileListingDeleteFileFolderPO(driver);

        String textFileName = JavaHelpers.getPropertyValue(PROPERTY_TEST_FILE, "textFile");

        Reporter.log("Step 1 - Entering valid sign in details and login to application");
        new LoginFunctions(driver, selenium).loginByGivenUser(adminUser);

        Reporter.log("Step 2 -  Login with administrator user and upload file in created project");
        new FileFunctions(driver, selenium).uploadFile(createdProjectName, textFileName);

        Reporter.log("Step 3 - Verify that project file is uploaded by administrator user ");
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
        projectDetailsFileListingRenameFileFolderPO.clearAndRenameFileFolderName(editProjectAdminData);
        Assert.assertTrue(projectDetailsFilesHistoryListingPO.isFilePresent(editProjectAdminData.getFileName() + ".txt"), "renamed file is not present : " + editProjectAdminData.getFileName() + ".txt");
        Assert.assertFalse(projectDetailsFilesHistoryListingPO.isFilePresent(versionFileName), "renamed file is present : ");

        Reporter.log("Step 8 - Click on history kebab menu and delete created file");
        projectDetailsFilesHistoryListingPO.clickOnKebabMenuAndDeleteFileFolder(projectDetailsFilesListingPO.getFirstFileUploadText());
        projectDetailsFileListingDeleteFileFolderPO.clickOnFileDeleteButton();
        Assert.assertFalse(projectDetailsFilesListingPO.isFileFolderPresent(editProjectAdminData.getFileName()), "deleted file is present : " + editProjectAdminData.getFileName());

    }

    /*Test 8 : Verify that administrator can edit project budget */
    @Test(priority = 8)
    public void VerifyThatAdministratorCanEditProjectBudgetSuccessfully() throws InterruptedException {

        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectDetailsBudgetListingPO projectDetailsBudgetListingPO = new ProjectDetailsBudgetListingPO(driver);
        ProjectDetailsPartnershipsListingPO projectDetailsPartnershipsListingPO = new ProjectDetailsPartnershipsListingPO(driver);
        String textFileName = JavaHelpers.getPropertyValue(PROPERTY_TEST_FILE, "textFile");

        Reporter.log("Step 1 - Entering valid sign in details and login to application");
        new LoginFunctions(driver, selenium).loginByGivenUser(adminUser);

        Reporter.log("Step 2 - Navigate on partnership detail page and click on project link button");
        selenium.navigateToPage(strFundingPartnershipUrl);
        projectDetailsPartnershipsListingPO.clickOnProjectLinkButton();

        Reporter.log("Step 3 - Click on budget detail button");
        projectDetails.clickOnBudgetButton();

        Reporter.log("Step 4 - Edit budget in created project");
        new BudgetFunctions(driver, selenium).editProjectBudget(textFileName, adminUser, budgetData);

        Reporter.log("Step 5 - Verify that project budget is edited by administrator user");
        Assert.assertEquals(projectDetailsBudgetListingPO.getUploadedTemplateFileName(), textFileName, "project budget template file name doesn't match : " + textFileName);
        Assert.assertEquals(projectDetailsBudgetListingPO.getBudgetAmountText().replace(",", ""), "$" + budgetData.getBudgetAmount(), "project budget amount doesn't match : ");

    }

    /*Test 9 : Verify that administrator can add project team member */
    @Test(priority = 9)
    public void VerifyThatAdministratorCanAddProjectTeamMemberSuccessfully() {
        ProjectDetailsTeamMembersListingPO projectDetailsTeamMembersListingPO = new ProjectDetailsTeamMembersListingPO(driver);

        Reporter.log("Step 1 -  Login with administrator user and add team member in created project");
        new LoginFunctions(driver, selenium).loginByGivenUser(adminUser);
        selenium.navigateToPage(strProjectTeamMemberUrl);

        Reporter.log("Step 2 - Verify that project team member is added by administrator user");
        Assert.assertEquals(projectDetailsTeamMembersListingPO.getMembersName(imFpmUser.getRealFirstName() + " " + imFpmUser.getRealLastName()), (imFpmUser.getRealFirstName() + " " + imFpmUser.getRealLastName()), "project member name doesn't match : ");
        Assert.assertEquals(projectDetailsTeamMembersListingPO.getMembersRoleByMemberName(imFpmUser.getRealFirstName() + " " + imFpmUser.getRealLastName()), imFpmUser.getUserRoles().get(0), "project member role doesn't match : ");
    }

    /*Test 10 : Verify that administrator can edit project team member */
    @Test(priority = 10)
    public void VerifyThatAdministratorCanEditProjectTeamMemberSuccessfully() throws InterruptedException {

        ProjectDetailsTeamMembersListingPO projectDetailsTeamMembersListingPO = new ProjectDetailsTeamMembersListingPO(driver);

        Reporter.log("Step 1 -  Login with administrator user and edit team member in created project");
        new LoginFunctions(driver, selenium).loginByGivenUser(adminUser);
        selenium.navigateToPage(strProjectTeamMemberUrl);
        selenium.hardWait(3);
        new TeamMemberFunctions(driver, selenium).editProjectTeamMember(imFpmUser);

        Reporter.log("Step 2 - Verify that project team member is edited by administrator user");
        Assert.assertEquals(projectDetailsTeamMembersListingPO.getMembersRoleByMemberName(imFpmUser.getRealFirstName() + " " + imFpmUser.getRealLastName()), imFpmUser.getUserRoles().get(0) + ", " + imFpmUser.getUserRoles().get(1), "project member updated role doesn't match : ");
    }

    /*Test 11 : Verify that administrator can edit projects*/
    @Test(priority = 11)
    public void VerifyThatAdministratorCanEditProjectsSuccessfully() throws InterruptedException, java.text.ParseException {

        LeftNavPO sideHeader = new LeftNavPO(driver);
        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectsListingPO projectsListingPO = new ProjectsListingPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);

        Reporter.log("Step 1 -  Login with administrator user and edit created project");
        new LoginFunctions(driver, selenium).loginByGivenUser(adminUser);
        new ProjectFunctions(driver, selenium).editInternshipProjectByName(editProjectAdminData, createdProjectName, adminUser);

        Reporter.log("Step 2 - Verify that project is displayed which is edited by administrator user");
        sideHeader.clickOnProjectsMenu();
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());

        if (!projectsListingPO.isProjectPresent(editProjectAdminData.getProjectName())) {
            headerPO.searchItemGloballyByName(editProjectAdminData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(editProjectAdminData.getProjectName());

        Assert.assertEquals(projectDetails.getProjectName(), editProjectAdminData.getProjectName(), "Project is not present ");
        Assert.assertEquals(Sensitivity.Low.toString(), projectDetails.getProjectSensitivity(), "project sensitivity doesn't match : ");
        Assert.assertEquals(projectDetails.getProjectLocationAndFieldRegion(), editProjectAdminData.getLocationName() + " | " + editProjectAdminData.getFieldRegionName(), "project location and field region doesn't match : ");
        Assert.assertEquals(projectDetails.getProjectStartEndDate(), new JavaHelpers().changeDateFormat(editProjectAdminData.getStartDate(), "MM/dd/yyyy", "M/d/yyyy") + "\n" + " - " + "\n" + new JavaHelpers().changeDateFormat(editProjectAdminData.getEndDate(), "MM/dd/yyyy", "M/d/yyyy"), "project start end date doesn't match : ");
        Assert.assertEquals(new JavaHelpers().changeDateFormat(editProjectAdminData.getEstimatedSubmissionDate(), "MM/dd/yyyy", "M/d/yyyy"), projectDetails.getProjectEstimatedSubmissionDate(), "project estimated submission date doesn't match : ");
    }

    /*Test 12 : Verify that administrator can create project partnership */
    @Test(priority = 12)
    public void VerifyThatAdministratorCanCreateProjectPartnershipSuccessfully() throws InterruptedException {

        ProjectDetailsPartnershipsListingPO projectDetailsPartnershipsListingPO = new ProjectDetailsPartnershipsListingPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with administrator user");
        new LoginFunctions(driver, selenium).loginByGivenUser(adminUser);
        selenium.navigateToPage(strPartnershipUrl);

        selenium.hardWait(3);

        Reporter.log("Step 2 - Verify that project partnership is created by administrator user");
        Assert.assertEquals(projectDetailsPartnershipsListingPO.getPartnershipName(partnerShipAdminData.getOrganizationName()), partnerShipAdminData.getOrganizationName(), "project partnership doesn't match : ");
    }

    /*Test 13 : Verify that administrator can edit project partnership  */
    @Test(priority = 13)
    public void VerifyThatAdministratorCanEditProjectPartnershipSuccessfully() throws java.text.ParseException, InterruptedException {

        ProjectDetailsPartnershipsListingPO projectDetailsPartnershipsListingPO = new ProjectDetailsPartnershipsListingPO(driver);
        ProjectDetailsPartnershipsEditPO projectDetailsPartnershipsEditPO = new ProjectDetailsPartnershipsEditPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with administrator user");
        new LoginFunctions(driver, selenium).loginByGivenUser(adminUser);
        selenium.navigateToPage(strPartnershipUrl);
        selenium.hardWait(3);
        new PartnershipFunctions(driver, selenium).editProjectPartnershipAllDetails(editProjectAdminData);

        Reporter.log("Step 2 - Click on partnership submit button");
        projectDetailsPartnershipsEditPO.clickOnPartnershipSubmitButton();

        Reporter.log("Step 3 - Verify that project partnership is edited by administrator user");
        Assert.assertEquals(projectDetailsPartnershipsListingPO.getTypeText(partnerShipAdminData.getOrganizationName()), PartnerType.Managing.getPartnerType(), "partner types doesn't match");
        Assert.assertEquals(projectDetailsPartnershipsListingPO.getFinancialReportingTypeText(partnerShipAdminData.getOrganizationName()), PartnershipFinancialReportingTypes.Funded.getPartnershipFinancialReportingTypes(), "partnership financial reporting types doesn't match");
        Assert.assertEquals(projectDetailsPartnershipsListingPO.getAgreementStatusText(partnerShipAdminData.getOrganizationName()), PartnershipStatus.NotAttached.getPartnerShipStatus(), " partnership agreement status doesn't match");
        Assert.assertEquals(projectDetailsPartnershipsListingPO.getMouStatusText(partnerShipAdminData.getOrganizationName()), PartnershipStatus.AwaitingSignature.getPartnerShipStatus(), " partnership mou status doesn't match");
        Assert.assertEquals(projectDetailsPartnershipsListingPO.getMouDateRangeText(partnerShipAdminData.getOrganizationName()), new JavaHelpers().changeDateFormat(editProjectAdminData.getStartDate(), "MM/dd/yyyy", "M/d/yyyy") + " - " + new JavaHelpers().changeDateFormat(editProjectAdminData.getEndDate(), "MM/dd/yyyy", "M/d/yyyy"), "project start end date doesn't match : ");
        Assert.assertTrue(projectDetailsPartnershipsListingPO.isCreatedPartnershipPrimary(partnerShipAdminData.getOrganizationName()), "Created partnership is not primary");
    }

    /*Test 14 : Verify that administrator can create project internship engagements */
    @Test(priority = 14)
    public void VerifyThatAdministratorCanCreateProjectInternshipEngagementsSuccessfully() throws InterruptedException {

        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with administrator user");
        new LoginFunctions(driver, selenium).loginByGivenUser(adminUser);
        selenium.navigateToPage(strInternshipUrl);

        selenium.hardWait(3);

        Reporter.log("Step 2 - Verify the created internship engagement");
        Assert.assertEquals(projectDetails.getInternshipName(adminUser.getRealFirstName() + " " + adminUser.getRealLastName()), (adminUser.getRealFirstName() + " " + adminUser.getRealLastName()), "project internship name doesn't match : ");
    }

    /*Test 15 : Verify that administrator can edit project internship engagements */
    @Test(priority = 15)
    public void VerifyThatAdministratorCanEditProjectInternshipEngagementsSuccessfully() throws java.text.ParseException {

        ProjectInternshipDetailsPO projectInternshipDetailsPO = new ProjectInternshipDetailsPO(driver);

        Reporter.log("Step 1 -  Login with administrator user and edit internship engagement in created project");
        new LoginFunctions(driver, selenium).loginByGivenUser(adminUser);
        selenium.navigateToPage(strInternshipUrl);

        new InternshipEngagementsFunctions(driver, selenium).editProjectInternshipEngagements(adminUser);
        String newVersionFileName = JavaHelpers.getPropertyValue(PROPERTY_TEST_FILE, "newVersionFile");

        Reporter.log("Step 2 - Verify that project internship is edited by administrator user");
        Assert.assertEquals(projectInternshipDetailsPO.getInternshipStartEndDate(), new JavaHelpers().changeDateFormat(internUpdateData.getInternshipStartDate(), "MM/dd/yyyy", "M/d/yyyy") + "\n" + " - " + "\n" + new JavaHelpers().changeDateFormat(internUpdateData.getInternshipEndDate(), "MM/dd/yyyy", "M/d/yyyy"), "Internship start end date doesn't match :");
        Assert.assertEquals(ProjectInternPosition.Administration.getInternPosition(), projectInternshipDetailsPO.getInternPosition(), "internship position doesn't match : ");
        Assert.assertEquals(projectInternshipDetailsPO.getGrowthPlanCompleteDate(), new JavaHelpers().changeDateFormat(internUpdateData.getGrowthPlanCompleteDate(), "MM/dd/yyyy", "M/d/yyyy"), "internship growth plan complete date  doesn't match :");
        Assert.assertEquals(newVersionFileName, projectInternshipDetailsPO.getUploadedFileText(), "uploaded file name doesn't match : " + newVersionFileName);
        Assert.assertEquals(projectInternshipDetailsPO.getMethodologiesText(), ProjectInternshipMethodologies.OneStory.getInternshipMethodology() + "\n" + ProjectInternshipMethodologies.Render.getInternshipMethodology(), "internship methodologies doesn't match : ");
        Assert.assertEquals(projectInternshipDetailsPO.getActualDateText(), new JavaHelpers().changeDateFormat(internUpdateData.getInternshipActualDate(), "MM/dd/yyyy", "M/d/yyyy"), "internship actual date doesn't match : ");
        Assert.assertEquals(projectInternshipDetailsPO.getEstimatedDateText(), new JavaHelpers().changeDateFormat(internUpdateData.getInternshipEstimatedDate(), "MM/dd/yyyy", "M/d/yyyy"), "internship estimated date doesn't match : ");
        Assert.assertEquals(projectInternshipDetailsPO.getMentorText(), adminUser.getRealFirstName() + " " + adminUser.getRealLastName(), "internship mentor name doesn't match : ");

    }

    /*Test 16 : Verify that administrator can read project location */
    @Test(priority = 16)
    public void VerifyThatAdministratorCanReadProjectLocation() throws InterruptedException {

        HeaderPO headerPO = new HeaderPO(driver);
        LocationsListingPO locationsListingPO = new LocationsListingPO(driver);
        LocationDetailsPO locationDetailsPO = new LocationDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with administrator user");
        new LoginFunctions(driver, selenium).loginByGivenUser(adminUser);

        Reporter.log("Step 2 - Enter location name and click on view location button");
        headerPO.searchItemGloballyByName(LocationName);
        selenium.hardWait(3);
        locationsListingPO.clickOnFirstViewLocationButton();

        Reporter.log("Step 3 - Verify the location edit button is not present");
        Assert.assertTrue(locationDetailsPO.isLocationEditButtonPresent(), "Location edit button is not present:");

    }

    /*Test 17 : Verify that administrator can edit  project location */
    @Test(priority = 17)
    public void VerifyThatAdministratorCanEditProjectLocation() throws InterruptedException {

        HeaderPO headerPO = new HeaderPO(driver);
        CreateUpdateLocationPO createLocation = new CreateUpdateLocationPO(driver);
        LocationsListingPO locationsListingPO = new LocationsListingPO(driver);
        LocationDetailsPO locationDetailsPO = new LocationDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with administrator user");
        new LoginFunctions(driver, selenium).loginByGivenUser(adminUser);

        Reporter.log("Step 2 - Enter location name and click on view location button");
        headerPO.searchItemGloballyByName(LocationName);
        locationsListingPO.clickOnFirstViewLocationButton();
        locationDetailsPO.clickOnEditButton();
        createLocation.fillLocationDetailAndAddNewFundingAccount(locationData);

        Reporter.log("Step 3- Verify that location is updated successfully by comparing location details");
        Assert.assertEquals(locationData.getLocationName(), locationDetailsPO.getLocationName(), "location name doesn't match : ");
        Assert.assertEquals(locationData.getLocationType(), locationDetailsPO.getLocationType().split(","), "location type doesn't match : ");
        Assert.assertEquals(locationData.getLocationCountryCode().replace("[", "").replace("]", ""), locationDetailsPO.getLocationCountryCode(), "location country code doesn't match");
        Assert.assertTrue(locationDetailsPO.getLocationFundingAccountText().startsWith(locationData.getSearchFundingAccountName()), "location funding account doesn't match");

    }

    /*Test 18 : Verify that administrator can create project language partner person and location  */
    @Test(priority = 18)
    public void VerifyThatAdministratorCanCreateProjectLanguagePartnerPersonAndLocation() throws InterruptedException {

        LeftNavPO leftNavPO = new LeftNavPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with administrator user");
        new LoginFunctions(driver, selenium).loginByGivenUser(adminUser);

        Reporter.log("Step 2 - Enter location name and click on view location button");
        leftNavPO.clickOnCreateNewItemMenuButton();

        Reporter.log("Step 3 - Verify project person option is present");
        Assert.assertTrue(leftNavPO.isMenuItemPresent(CreateItemTypes.Project.toString()), "Project menu item is not present:");
        Assert.assertTrue(leftNavPO.isMenuItemPresent(CreateItemTypes.Language.toString()), "Language menu item is not present:");
        Assert.assertTrue(leftNavPO.isMenuItemPresent(CreateItemTypes.Person.toString()), "Person menu item is not present:");
        Assert.assertTrue(leftNavPO.isMenuItemPresent(CreateItemTypes.Partner.toString()), "Partner menu item is not present:");
        Assert.assertTrue(leftNavPO.isMenuItemPresent(CreateItemTypes.Location.toString()), "Location menu item is not present:");

    }

    /*Test 19 : Verify that administrator can create project language engagements */
    @Test(priority = 19)
    public void VerifyThatAdministratorCanCreateProjectLanguageEngagements() throws InterruptedException {

        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with administrator user");
        new LoginFunctions(driver, selenium).loginByGivenUser(adminUser);
        selenium.navigateToPage(strLanguageTranslationUrl);
        selenium.hardWait(3);

        Reporter.log("Step 2 - Verify the created language engagement");
        Assert.assertEquals(projectDetails.getLanguageEngagementName(languageAdminData.getLanguageEngagementName()), languageAdminData.getLanguageEngagementName(), "project language engagement name doesn't match");
    }

    /*Test 20 : Verify that administrator can edit project language engagements */
    @Test(priority = 20)
    public void VerifyThatAdministratorCanEditProjectLanguageEngagements() throws java.text.ParseException, InterruptedException {

        ProjectLanguageEngagementDetailsPO projectLanguageEngagementDetailsPO = new ProjectLanguageEngagementDetailsPO(driver);
        String textFileName = JavaHelpers.getPropertyValue(PROPERTY_TEST_FILE, "textFile");

        Reporter.log("Step 1 -  Login with administrator user and edit language engagement in created project");
        new LoginFunctions(driver, selenium).loginByGivenUser(adminUser);
        selenium.navigateToPage(strLanguageTranslationUrl);
        selenium.hardWait(3);
        new LanguageFunctions(driver, selenium).editProjectLanguageEngagements(internData, languageAdminData);

        Reporter.log("Step 2 - Verify the edited language engagement");
        Assert.assertEquals(projectLanguageEngagementDetailsPO.getLanguageStartEndDate(), new JavaHelpers().changeDateFormat(internData.getInternshipStartDate(), "MM/dd/yyyy", "M/d/yyyy") + "\n" + " - " + "\n" + new JavaHelpers().changeDateFormat(internData.getInternshipEndDate(), "MM/dd/yyyy", "M/d/yyyy"), "Language engagement start end date doesn't match");
        Assert.assertEquals(projectLanguageEngagementDetailsPO.getLanguageParaTextRegistryId(), "Paratext Registry ID: " + internData.getParaTextRegId(), "Language engagement start end date doesn't match");
        Assert.assertEquals(projectLanguageEngagementDetailsPO.getTranslationCompleteDate(), internData.getGrowthPlanCompleteDate(), "Language engagement growth plan complete date  doesn't match");
        Assert.assertEquals(projectLanguageEngagementDetailsPO.getDisbursementCompleteDate(), internData.getDisbursementCompleteDate(), "Language engagement disbursement complete date  doesn't match");
        Assert.assertEquals(textFileName, projectLanguageEngagementDetailsPO.getUploadedFileText(), "uploaded file name doesn't match : " + textFileName);
        Assert.assertEquals(projectLanguageEngagementDetailsPO.getActualDateTextBox(), internData.getInternshipActualDate(), "Language engagement actual date doesn't match");
        Assert.assertEquals(projectLanguageEngagementDetailsPO.getEstimatedDateTextBox(), internData.getInternshipEstimatedDate(), "Language engagement estimated date doesn't match");

    }

    /*Test 21 : Verify that administrator can Add project goals */
    @Test(priority = 21)
    public void VerifyThatAdministratorCanAddProjectGoalsSuccessfully() throws InterruptedException {

        ProjectLanguageEngagementDetailsPO projectLanguageEngagementDetailsPO = new ProjectLanguageEngagementDetailsPO(driver);
        GoalDetailsPO goalDetailsPO = new GoalDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with administrator user");
        new LoginFunctions(driver, selenium).loginByGivenUser(adminUser);
        selenium.navigateToPage(strProjectCreateGoalUrl);

        selenium.hardWait(3);

        Reporter.log("Step 2 - Verify goal type");
        Assert.assertEquals(projectLanguageEngagementDetailsPO.getGoalText(), (goalData.getGoalStoryName()), "Goal type is not matched");

        Reporter.log("Step 3 - Click on created goal");
        projectLanguageEngagementDetailsPO.clickOnGoalCard();

        Reporter.log("Step 14 - verify goal details");
        Assert.assertEquals(Arrays.stream(goalDetailsPO.getGoalDistributionMethodsText().split(",")).map(String::trim).toArray(String[]::new), GoalDistributionMethods.getGoalDistributionMethodsByName(goalData.getGoalDistributionMethods()), "Goal distribution method is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalDistributionMethodsPartnerText(), partnerShipAdminData.getOrganizationName(), "Goal distribution method partner is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalMethodologyText(), goalData.getMethodologyType() + " - " + goalData.getMethodologyValue(), "Goal methodology is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalCompletionDescriptionText(), (goalData.getGoalCompletionDescription()), "Goal completion description is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalScriptureReferenceOldTestamentText(), goalData.getOldScriptureReference(), "Goal old scriptureReference is not matched");

        Reporter.log("Step 15 - Refresh page and verify Goal details");
        selenium.refreshPage();

        Assert.assertEquals(Arrays.stream(goalDetailsPO.getGoalDistributionMethodsText().split(",")).map(String::trim).toArray(String[]::new), GoalDistributionMethods.getGoalDistributionMethodsByName(goalData.getGoalDistributionMethods()), "Goal distribution method is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalDistributionMethodsPartnerText(), partnerShipAdminData.getOrganizationName(), "Goal distribution method partner is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalMethodologyText(), goalData.getMethodologyType() + " - " + goalData.getMethodologyValue(), "Goal methodology is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalCompletionDescriptionText(), (goalData.getGoalCompletionDescription()), "Goal completion description is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalScriptureReferenceOldTestamentText(), goalData.getOldScriptureReference(), "Goal old scriptureReference is not matched");

    }

    /*Test 22 : Verify that administrator can edit created project goals */
    @Test(priority = 22)
    public void VerifyThatAdministratorCanEditProjectGoalsSuccessfully() throws InterruptedException {

        GoalDetailsPO goalDetailsPO = new GoalDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with administrator user");
        new LoginFunctions(driver, selenium).loginByGivenUser(adminUser);
        selenium.navigateToPage(strProjectCreateGoalUrl);
        selenium.hardWait(3);

        Reporter.log("Step 2 - Edit Story Goal by administrator user");
        new GoalFunctions(driver, selenium).editGoal(editGoalData ,goalData, partnerShipAdminData.getOrganizationName());

        Reporter.log("Step 3 - Verify the created goal");
        Assert.assertEquals(goalDetailsPO.getGoalNameText(),goalData.getGoalStoryName(), "Goal type is not matched");

        Reporter.log("Step 4 - Verify goal details");
        Assert.assertEquals(Arrays.stream(goalDetailsPO.getGoalDistributionMethodsText().split(",")).map(String::trim).toArray(String[]::new), GoalDistributionMethods.getGoalDistributionMethodsByName(editGoalData.getGoalDistributionMethods()), "Goal mediums is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalDistributionMethodsPartnerText(), partnerShipAdminData.getOrganizationName(), "Goal distribution method partner is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalMethodologyText(), editGoalData.getMethodologyType() + " - " + editGoalData.getMethodologyValue(), "Goal methodology is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalCompletionDescriptionText(), (editGoalData.getGoalCompletionDescription()), "Goal completion description is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalScriptureReferenceOldTestamentText(), editGoalData.getOldScriptureReference(), "Goal old scriptureReference is not matched");

        Reporter.log("Step 5 - Refresh page and verify Goal details");
        selenium.refreshPage();
        Assert.assertEquals(Arrays.stream(goalDetailsPO.getGoalDistributionMethodsText().split(",")).map(String::trim).toArray(String[]::new), GoalDistributionMethods.getGoalDistributionMethodsByName(editGoalData.getGoalDistributionMethods()), "Goal mediums is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalDistributionMethodsPartnerText(), partnerShipAdminData.getOrganizationName(), "Goal distribution method partner is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalMethodologyText(), editGoalData.getMethodologyType() + " - " + editGoalData.getMethodologyValue(), "Goal methodology is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalCompletionDescriptionText(), (editGoalData.getGoalCompletionDescription()), "Goal completion description is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalScriptureReferenceOldTestamentText(), editGoalData.getOldScriptureReference(), "Goal old scriptureReference is not matched");
    }

}

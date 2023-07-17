package cord.projects.rolePermissionFlow;

import base.BaseTest;
import datafactory.*;
import dataobjects.*;
import enums.menu.CreateItemTypes;
import enums.partner.PartnerType;
import enums.partnership.PartnershipFinancialReportingTypes;
import enums.partnership.PartnershipStatus;
import enums.goal.GoalDistributionMethods;
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

public class ProjectManagerTests extends BaseTest {

    protected String strFpmPeopleUrl;
    protected String strFpmLanguageUrl;
    protected String strFpmPartnerUrl;
    protected String strFpmInternshipUrl;
    protected String strFpmLanguageTranslationUrl;
    protected String strFpmPartnershipUrl;
    protected String strFpmProjectUrl;
    protected String strFpmProjectCreateGoalUrl;
    protected String strFpmProjectTeamMemberUrl;
    protected String strFpmFundingPartnershipUrl;
    People fpmPeopleRdData = new PeopleData().getPeopleData();
    Budget fpmBudgetData = new BudgetData().getBudgetData();
    Project fpmAdminData = new ProjectData().getCreateProjectData();
    Project fpmAdminTranslationData = new ProjectData().getCreateTranslationProjectData();
    String fpmCreatedTranslationProjectName = fpmAdminTranslationData.getProjectName();
    String fpmCreatedProjectName = fpmAdminData.getProjectName();
    Project fpmAdminBudgetData = new ProjectData().getCreateProjectData();
    String fpmCreatedBudgetProjectName = fpmAdminBudgetData.getProjectName();
    People fpmPeopleAdminData = new PeopleData().getPeopleData();
    People fpmEditPeopleAdminData = new PeopleData().getPeopleData();
    Goal fpmGoalData = new GoalData().getCreateGoalData();
    Partner fpmBudgetPartnerAdminData = new PartnerData().getCreatePartnerData();
    Project fpmEditProjectAdminData = new ProjectData().getUpdateProjectData();
    Language fpmLanguageAdminData = new LanguageData().getLanguageData();
    Partner fpmPartnerAdminData = new PartnerData().getCreatePartnerData();
    Project fpmRdData = new ProjectData().getCreateProjectData();
    Project fpmEditProjectBudgetData = new ProjectData().getUpdateProjectData();
    Goal fpmEditGoalData = new GoalData().getCreateGoalData();
    Location fpmLocationData = new LocationData().getLocationData();
    String fpmLocationName = fpmLocationData.getLocationName();
    Internship fpmInternUpdateData = new EngagementData().getUpdateEngagementData();
    Internship fpmInternData = new EngagementData().getCreateEngagementData();

    public ProjectManagerTests() throws ParseException, java.text.ParseException, InterruptedException {
    }

    public static final String PROPERTY_TEST_FILE = "src/main/resources/testData/TestFiles.properties";

    @BeforeClass
    public void setupTestData() throws InterruptedException {

        WebDriver driver;
        DriverManager drivermanager;

        drivermanager = new DriverManager();
        driver = drivermanager.setUp("chrome");
        SeleniumHelpers selenium = new SeleniumHelpers(driver);

        Reporter.log("Step 1 - Create Project by admin user, add project manager and logout that user");
        new LoginFunctions(driver, selenium).loginByGivenUser(adminUser);
        new ProjectFunctions(driver, selenium).createProject(fpmAdminData);
        new ProjectFunctions(driver, selenium).addMemberToProject(fpmUser, fpmAdminData);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 2 - Create Budget Project by admin user and logout that user");
        new ProjectFunctions(driver, selenium).createProject(fpmAdminBudgetData);
        new ProjectFunctions(driver, selenium).addMemberToProject(fpmUser, fpmAdminBudgetData);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 3 - Create TranslationType Project by admin user, add project manager and logout that user");
        new ProjectFunctions(driver, selenium).createProject(fpmAdminTranslationData);
        new ProjectFunctions(driver, selenium).addMemberToProject(fpmUser, fpmAdminTranslationData);
        new PartnershipFunctions(driver, selenium).createProjectPartnership(fpmPartnerAdminData ,fpmCreatedTranslationProjectName,adminUser);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(rdUser);

        Reporter.log("Step 4 - Create Project by regional director and add project manager to that project");
        new ProjectFunctions(driver, selenium).createProject(fpmRdData);
        new ProjectFunctions(driver, selenium).addMemberToProject(fpmUser, fpmRdData);
        strFpmProjectUrl = driver.getCurrentUrl();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 5 - Create People by admin user");
        new PeopleFunctions(driver, selenium).createPeople(fpmPeopleAdminData,adminUser);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(rdUser);

        Reporter.log("Step 6 - Create People by regional director user");
        new PeopleFunctions(driver, selenium).createPeople(fpmPeopleRdData,rdUser);
        strFpmPeopleUrl = driver.getCurrentUrl();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 7 - Create Language by admin user");
        new LanguageFunctions(driver, selenium).createLanguage(fpmLanguageAdminData);
        strFpmLanguageUrl = driver.getCurrentUrl();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(fpmUser);

        Reporter.log("Step 8 - Create internship engagements by project manager user");
        new InternshipEngagementsFunctions(driver, selenium).createProjectInternshipEngagements(fpmUser, fpmCreatedProjectName);
        strFpmInternshipUrl = driver.getCurrentUrl();
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(fpmUser);

        Reporter.log("Step 9 - Create language engagements by project manager user");
        new LanguageFunctions(driver, selenium).createProjectLanguageEngagements(fpmLanguageAdminData, fpmCreatedTranslationProjectName,fpmUser);
        strFpmLanguageTranslationUrl = driver.getCurrentUrl();
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 10 - Create Partner by admin user");
        new PartnerFunctions(driver, selenium).createPartner(fpmPartnerAdminData);
        strFpmPartnerUrl = driver.getCurrentUrl();
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(fpmUser);

        Reporter.log("Step 11 - Create partnership by project manager user");
        new PartnershipFunctions(driver, selenium).createProjectPartnership(fpmPartnerAdminData, fpmCreatedProjectName,fpmUser);
        strFpmPartnershipUrl = driver.getCurrentUrl();
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(fpmUser);

        Reporter.log("Step 12 - Add project team member with multiple user role");
        new ProjectFunctions(driver, selenium).addMemberToProject(imFpmUser, fpmAdminData);
        strFpmProjectTeamMemberUrl = driver.getCurrentUrl();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 13 - Create Location by admin user");
        new LocationFunctions(driver, selenium).createLocation(fpmLocationData);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(fpmUser);

        Reporter.log("Step 14 - Create goal by project manager user");
        selenium.navigateToPage(strFpmLanguageTranslationUrl);
        new GoalFunctions(driver, selenium).createGoal(fpmGoalData, fpmLanguageAdminData.getLanguageEngagementName(),fpmPartnerAdminData.getOrganizationName());
        strFpmProjectCreateGoalUrl = driver.getCurrentUrl();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 15 - Create funding partnership by admin user");
        new ProjectFunctions(driver, selenium).editInternshipProjectByName(fpmEditProjectBudgetData, fpmCreatedBudgetProjectName,adminUser);
        new PartnershipFunctions(driver, selenium).createProjectFundingPartnership(fpmBudgetPartnerAdminData, fpmEditProjectBudgetData.getProjectName());
        selenium.hardWait(3);
        strFpmFundingPartnershipUrl = driver.getCurrentUrl();
        drivermanager.tearDown();

    }

    /*Test 1 : Verify that project manager can view all projects*/
    @Test(priority = 1)
    public void VerifyProjectManagerCanViewAllProjectsSuccessfully() throws InterruptedException {

        LeftNavPO sideHeader = new LeftNavPO(driver);
        ProjectsListingPO projectsListingPO = new ProjectsListingPO(driver);
        ProjectDetailsPO projectDetailsPO = new ProjectDetailsPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with project manager user");
        new LoginFunctions(driver, selenium).loginByGivenUser(fpmUser);

        Reporter.log("Step 2 - Verify that projects is displayed which is created by admin");
        sideHeader.clickOnProjectsMenu();
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());

        if (!projectsListingPO.isProjectPresent(fpmAdminData.getProjectName())) {
            headerPO.searchItemGloballyByName(fpmAdminData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(fpmAdminData.getProjectName());
        Assert.assertEquals(projectDetailsPO.getProjectName(), fpmAdminData.getProjectName(), "Project is not present : " + fpmAdminData.getProjectName());

        Reporter.log("Step 3 - Verify that projects is displayed which is created by RD user");
        sideHeader.clickOnProjectsMenu();
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());

        if (!projectsListingPO.isProjectPresent(fpmRdData.getProjectName())) {
            headerPO.searchItemGloballyByName(fpmRdData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(fpmRdData.getProjectName());
        Assert.assertEquals(projectDetailsPO.getProjectName(), fpmRdData.getProjectName(), "Project is not present : " + fpmRdData.getProjectName());

    }

    /*Test 2 : Verify that project manager can view all peoples*/
    @Test(priority = 2)
    public void VerifyProjectManagerCanViewAllPeoplesSuccessfully() throws InterruptedException {

        LeftNavPO sideHeader = new LeftNavPO(driver);
        PeoplesPO peoples = new PeoplesPO(driver);
        PeopleDetailsPO peopleDetails = new PeopleDetailsPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with project manager user");
        selenium.navigateToPage(strFpmPeopleUrl);
        new LoginFunctions(driver, selenium).loginByGivenUser(fpmUser);

        Reporter.log("Step 2 - Verify that projects is displayed which is created by admin");
        sideHeader.clickOnPeopleMenu();
        if (!peoples.isPeoplePresent(fpmPeopleAdminData.getPersonName())) {
            headerPO.searchItemGloballyByName(fpmPeopleAdminData.getPersonName());
        }
        peoples.clickOnPeopleName(fpmPeopleAdminData.getPersonName());
        Assert.assertEquals(peopleDetails.getPeopleName().replaceAll("\\s.*", ""), fpmPeopleAdminData.getPersonName(), "Project is not present : " + fpmPeopleAdminData.getPersonName());

        Reporter.log("Step 3 - Verify that projects is displayed which is created by RD user");
        sideHeader.clickOnPeopleMenu();
        if (!peoples.isPeoplePresent(fpmPeopleRdData.getPersonName())) {
            headerPO.searchItemGloballyByName(fpmPeopleRdData.getPersonName());
        }
        peoples.clickOnPeopleName(fpmPeopleRdData.getPersonName());
        Assert.assertEquals(peopleDetails.getPeopleName().replaceAll("\\s.*", ""), fpmPeopleRdData.getPersonName(), "Project is not present : " + fpmPeopleRdData.getPersonName());

    }

    /*Test 3 : Verify that project manager can edit mine peoples */
    @Test(priority = 3)
    public void VerifyThatProjectManagerCanEditMinePeoples() throws InterruptedException {

        PeoplesPO peoples = new PeoplesPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);
        LeftNavPO sideHeader = new LeftNavPO(driver);
        UpdatePeoplePO updatePeople = new UpdatePeoplePO(driver);
        PeopleDetailsPO peopleDetails = new PeopleDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with project manager user");
        new LoginFunctions(driver, selenium).loginByGivenUser(fpmUser);

        Reporter.log("Step 2 - Search user which have a project manager role by email and click on people name");
        sideHeader.clickOnPeopleMenu();
        if (!peoples.isPeoplePresent(fpmUser.getEmail())) {
            headerPO.searchItemGloballyByName(fpmUser.getEmail());
        }
        peoples.clickOnPeopleName(fpmUser.getRealFirstName() + " " + fpmUser.getRealLastName());

        Reporter.log("Step 3 - Click on edit icon and edit mine person detail");
        peopleDetails.clickOnEditButton();
        updatePeople.clearAndUpdatePersonDetail(fpmEditPeopleAdminData);
        updatePeople.clickOnSubmitButton();

        Reporter.log("Step 4 - Verify updated person details");
        Assert.assertEquals(fpmEditPeopleAdminData.getPersonEmail(), peopleDetails.getEmail(), "email doesn't match : ");
        Assert.assertEquals(fpmEditPeopleAdminData.getPersonTitle(), peopleDetails.getTitle(), "title doesn't match : ");
        Assert.assertEquals(fpmEditPeopleAdminData.getPersonPhone(), peopleDetails.getPhoneNumber(), "phone number doesn't match : ");
        Assert.assertEquals(fpmEditPeopleAdminData.getPersonAbout(), peopleDetails.getAbout(), "About doesn't match : ");
        Assert.assertEquals(UserRole.ProjectManager.getRole(), peopleDetails.getRoles(), "Roles doesn't match : ");

        Reporter.log("Step 5 - Again Click on edit icon and reset the people");
        peopleDetails.clickOnEditButton();
        updatePeople.fillFirstnameLastNameAndEmailDetail(fpmUser.getRealFirstName(), fpmUser.getRealLastName(),fpmUser.getEmail());
        updatePeople.clickOnSubmitButton();
        Assert.assertEquals(fpmUser.getRealFirstName() + " " + fpmUser.getRealLastName(), peopleDetails.getPeopleName(), "personName doesn't match : ");
        Assert.assertEquals(fpmUser.getEmail(), peopleDetails.getEmail(), "personEmail doesn't match : ");
    }

    /*Test 4 : Verify that project manager can view all languages and edit icon is not present*/
    @Test(priority = 4)
    public void VerifyProjectManagerCanViewAllLanguagesAndEditIconIsNotPresent() throws InterruptedException {

        LeftNavPO sideHeader = new LeftNavPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);
        LanguagesPO languages = new LanguagesPO(driver);
        LanguageDetailPO languageDetailPO = new LanguageDetailPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with project manager user");
        selenium.navigateToPage(strFpmLanguageUrl);
        new LoginFunctions(driver, selenium).loginByGivenUser(fpmUser);

        Reporter.log("Step 2 - Verify that language is displayed which is created by admin and edit icon is not present");
        sideHeader.clickOnLanguagesMenu();
        if (!languages.isLanguagePresent(fpmLanguageAdminData.getLanguageName())) {
            headerPO.searchItemGloballyByName(fpmLanguageAdminData.getLanguageName());
        }
        languages.clickOnLanguageName(fpmLanguageAdminData.getLanguageName());
        Assert.assertEquals(languageDetailPO.getLanguageName(), fpmLanguageAdminData.getPubliicName(), "Language is not present : " + fpmLanguageAdminData.getPubliicName());
        Assert.assertFalse(languageDetailPO.isEditIconPresent(), "Language edit icon is present : ");

    }

    /*Test 5 : Verify that project manager can view all partners*/
    @Test(priority = 5)
    public void VerifyProjectManagerCanViewAllPartnersSuccessfully() throws InterruptedException {

        HeaderPO headerPO = new HeaderPO(driver);
        LeftNavPO sideHeader = new LeftNavPO(driver);
        PartnersListingPO partnerListing = new PartnersListingPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with project manager user");
        new LoginFunctions(driver, selenium).loginByGivenUser(fpmUser);

        Reporter.log("Step 2 - Verify that partner is displayed which is created by admin");
        sideHeader.clickOnPartnersMenu();
        if (!partnerListing.isPartnerPresent(fpmPartnerAdminData.getPartnerName())) {
            headerPO.searchItemGloballyByName(fpmPartnerAdminData.getPartnerName());
        }
        Assert.assertTrue(partnerListing.isPartnerPresent(fpmPartnerAdminData.getPartnerName()), "Partner is not present : " + fpmPartnerAdminData.getPartnerName());

    }

    /*Test 6 : Verify that project manager can't edit all partners details */ //Todo bug(#762) Global R access
    @Test(priority = 6)
    public void VerifyThatProjectManagerCanNotEditAllPartnersDetails() throws InterruptedException {

        HeaderPO headerPO = new HeaderPO(driver);
        LeftNavPO sideHeader = new LeftNavPO(driver);
        PartnersListingPO partnerListing = new PartnersListingPO(driver);
        PartnerDetailsPO partnerDetailsPO = new PartnerDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with project manager user");
        new LoginFunctions(driver, selenium).loginByGivenUser(fpmUser);

        Reporter.log("Step 2 - Search the partner from listing and click on partner name");
        sideHeader.clickOnPartnersMenu();
        if (!partnerListing.isPartnerPresent(fpmPartnerAdminData.getPartnerName())) {
            headerPO.searchItemGloballyByName(fpmPartnerAdminData.getPartnerName());
        }
        partnerListing.clickOnPartnerName(fpmPartnerAdminData.getPartnerName());

        Reporter.log("Step 3 - Verify that partner is not edited by project manager user ");
        Assert.assertFalse(partnerDetailsPO.isEditIconPresent(), "Partner edit icon is present : ");
        Assert.assertFalse(partnerDetailsPO.isInActiveButtonEnabled(), "Partner active inactive button is enabled : ");
        Assert.assertFalse(partnerDetailsPO.isEnterPmcEntityCodeButtonEnabled(), "Partner pmc entity code button is enabled : ");
        Assert.assertFalse(partnerDetailsPO.isPartnerTypesEditButtonEnabled(), "Partner types edit button is enabled : ");
        Assert.assertFalse(partnerDetailsPO.isPartnerAddressEditButtonEnabled(), "Partner address edit button is enabled: ");
        Assert.assertFalse(partnerDetailsPO.isPartnerPointOfContactButtonEnabled(), "Partner point of contact button is enabled : ");

    }

    /*Test 7 : Verify that project manager can upload/Edit project files */
    @Test(priority = 7)
    public void VerifyThatProjectManagerCanUploadProjectFilesSuccessfully() throws InterruptedException {

        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectDetailsFilesListingPO projectDetailsFilesListingPO = new ProjectDetailsFilesListingPO(driver);
        ProjectDetailsFilesHistoryListingPO projectDetailsFilesHistoryListingPO = new ProjectDetailsFilesHistoryListingPO(driver);
        ProjectDetailsFileListingRenameFileFolderPO projectDetailsFileListingRenameFileFolderPO = new ProjectDetailsFileListingRenameFileFolderPO(driver);
        ProjectDetailsFileListingDeleteFileFolderPO projectDetailsFileListingDeleteFileFolderPO = new ProjectDetailsFileListingDeleteFileFolderPO(driver);

        String textFileName = JavaHelpers.getPropertyValue(PROPERTY_TEST_FILE, "textFile");

        Reporter.log("Step 1 - Entering valid sign in details and login to application");
        new LoginFunctions(driver, selenium).loginByGivenUser(fpmUser);

        Reporter.log("Step 2 -  Login with project manager user and upload file in created project");
        new FileFunctions(driver, selenium).uploadFile(fpmCreatedProjectName, textFileName);

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
        projectDetailsFileListingRenameFileFolderPO.clearAndRenameFileFolderName(fpmEditProjectAdminData);
        Assert.assertTrue(projectDetailsFilesHistoryListingPO.isFilePresent(fpmEditProjectAdminData.getFileName() + ".txt"), "renamed file is not present : ");
        Assert.assertFalse(projectDetailsFilesHistoryListingPO.isFilePresent(versionFileName), "renamed file is present : ");

        Reporter.log("Step 8 - Click on history kebab menu and delete created file");
        projectDetailsFilesHistoryListingPO.clickOnKebabMenuAndDeleteFileFolder(projectDetailsFilesListingPO.getFirstFileUploadText());
        projectDetailsFileListingDeleteFileFolderPO.clickOnFileDeleteButton();
        Assert.assertFalse(projectDetailsFilesListingPO.isFileFolderPresent(fpmEditProjectAdminData.getFileName()), "deleted file is present : ");

    }

    /*Test 8 : Verify that project manager can edit project budget when member of the project */
    @Test(priority = 8)
    public void VerifyThatProjectManagerCanEditProjectBudgetWhenProjectMemberOfTheProjectSuccessfully() throws InterruptedException {

        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectDetailsBudgetListingPO projectDetailsBudgetListingPO = new ProjectDetailsBudgetListingPO(driver);
        ProjectDetailsPartnershipsListingPO projectDetailsPartnershipsListingPO = new ProjectDetailsPartnershipsListingPO(driver);
        String textFileName = JavaHelpers.getPropertyValue(PROPERTY_TEST_FILE, "textFile");

        Reporter.log("Step 1 - Entering valid sign in details and login to application");
        new LoginFunctions(driver, selenium).loginByGivenUser(fpmUser);

        Reporter.log("Step 2 - Navigate on partnership detail page and click on project link button");
        selenium.navigateToPage(strFpmFundingPartnershipUrl);
        selenium.hardWait(3);
        projectDetailsPartnershipsListingPO.clickOnProjectLinkButton();

        Reporter.log("Step 3 - Click on budget detail button");
        projectDetails.clickOnBudgetButton();

        Reporter.log("Step 4 - Edit budget in created project");
        new BudgetFunctions(driver, selenium).editProjectBudget(textFileName,fodUser, fpmBudgetData);

        Reporter.log("Step 5 - Verify that project budget is edited by project manager user");
        Assert.assertEquals(projectDetailsBudgetListingPO.getUploadedTemplateFileName(), textFileName, "project budget template file name doesn't match : ");
        Assert.assertEquals(projectDetailsBudgetListingPO.getBudgetAmountText().replace(",", ""), "$" + fpmBudgetData.getBudgetAmount(), "project budget amount doesn't match : ");
    }

    /*Test 9 : Verify that project manager can add project team member */
    @Test(priority = 9)
    public void VerifyThatProjectManagerCanAddProjectTeamMemberSuccessfully() {
        ProjectDetailsTeamMembersListingPO projectDetailsTeamMembersListingPO = new ProjectDetailsTeamMembersListingPO(driver);

        Reporter.log("Step 1 -  Login with project manager user and add team member in created project");
        new LoginFunctions(driver, selenium).loginByGivenUser(fpmUser);
        selenium.navigateToPage(strFpmProjectTeamMemberUrl);

        Reporter.log("Step 2 - Verify that project team member is added by project manager user");
        Assert.assertEquals(projectDetailsTeamMembersListingPO.getMembersName(imFpmUser.getRealFirstName() + " " + imFpmUser.getRealLastName()), (imFpmUser.getRealFirstName() + " " + imFpmUser.getRealLastName()), "project member name doesn't match : ");
        Assert.assertEquals(projectDetailsTeamMembersListingPO.getMembersRoleByMemberName(imFpmUser.getRealFirstName() + " " + imFpmUser.getRealLastName()), imFpmUser.getUserRoles().get(0), "project member role doesn't match : ");
    }

    /*Test 10 : Verify that project manager can edit project team member */
    @Test(priority = 10)
    public void VerifyThatProjectManagerCanEditProjectTeamMemberSuccessfully() throws InterruptedException {

        ProjectDetailsTeamMembersListingPO projectDetailsTeamMembersListingPO = new ProjectDetailsTeamMembersListingPO(driver);

        Reporter.log("Step 1 -  Login with project manager user and edit team member in created project");
        new LoginFunctions(driver, selenium).loginByGivenUser(fpmUser);
        selenium.navigateToPage(strFpmProjectTeamMemberUrl);
        selenium.hardWait(3);
        new TeamMemberFunctions(driver, selenium).editProjectTeamMember(imFpmUser);

        Reporter.log("Step 2 - Verify that project team member is edited by project manager user");
        Assert.assertEquals(projectDetailsTeamMembersListingPO.getMembersRoleByMemberName(imFpmUser.getRealFirstName() + " " + imFpmUser.getRealLastName()), imFpmUser.getUserRoles().get(0) + ", " + imFpmUser.getUserRoles().get(1), "project member updated role doesn't match : ");
    }

    /*Test 11 : Verify that project manager can edit projects*/
    @Test(priority = 11)
    public void VerifyThatProjectManagerCanEditProjectsSuccessfully() throws InterruptedException, java.text.ParseException {

        LeftNavPO sideHeader = new LeftNavPO(driver);
        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectsListingPO projectsListingPO = new ProjectsListingPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);

        Reporter.log("Step 1 -  Login with project manager user and edit created project");
        new LoginFunctions(driver, selenium).loginByGivenUser(fpmUser);
        new ProjectFunctions(driver, selenium).editInternshipProjectByName(fpmEditProjectAdminData, fpmCreatedProjectName,fpmUser);

        Reporter.log("Step 2 - Verify that project is displayed which is edited by project manager user ");
        sideHeader.clickOnProjectsMenu();
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());

        if (!projectsListingPO.isProjectPresent(fpmEditProjectAdminData.getProjectName())) {
            headerPO.searchItemGloballyByName(fpmEditProjectAdminData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(fpmEditProjectAdminData.getProjectName());

        Assert.assertEquals(projectDetails.getProjectName(), fpmEditProjectAdminData.getProjectName(), "Project is not present ");
        Assert.assertEquals(Sensitivity.Low.toString(), projectDetails.getProjectSensitivity(), "project sensitivity doesn't match : ");
        Assert.assertEquals(projectDetails.getProjectLocationAndFieldRegion(), fpmEditProjectAdminData.getLocationName() + " | " + fpmEditProjectAdminData.getFieldRegionName(), "project location and field region doesn't match : ");
        Assert.assertEquals(projectDetails.getProjectStartEndDate(), new JavaHelpers().changeDateFormat(fpmEditProjectAdminData.getStartDate(), "MM/dd/yyyy", "M/d/yyyy") + "\n" + " - " + "\n" + new JavaHelpers().changeDateFormat(fpmEditProjectAdminData.getEndDate(), "MM/dd/yyyy", "M/d/yyyy"), "project start end date doesn't match : ");
        Assert.assertEquals(new JavaHelpers().changeDateFormat(fpmEditProjectAdminData.getEstimatedSubmissionDate(), "MM/dd/yyyy", "M/d/yyyy"), projectDetails.getProjectEstimatedSubmissionDate(), "project estimated submission date doesn't match : ");
    }

    /*Test 12 : Verify that project manager can create project partnership */
    @Test(priority = 12)
    public void VerifyThatProjectManagerCanCreateProjectPartnershipSuccessfully() throws InterruptedException {

        ProjectDetailsPartnershipsListingPO projectDetailsPartnershipsListingPO = new ProjectDetailsPartnershipsListingPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with project manager user");
        new LoginFunctions(driver, selenium).loginByGivenUser(fpmUser);
        selenium.navigateToPage(strFpmPartnershipUrl);

        selenium.hardWait(3);

        Reporter.log("Step 2 - Verify that project partnership is created by project manager user");
        Assert.assertEquals(projectDetailsPartnershipsListingPO.getPartnershipName(fpmPartnerAdminData.getOrganizationName()), fpmPartnerAdminData.getOrganizationName(), "project partnership doesn't match : ");
    }

    /*Test 13 : Verify that project manager can edit project partnership Except Financial Reporting Type and MouStatus */
    @Test(priority = 13)
    public void VerifyThatProjectManagerCanEditProjectPartnershipExceptFinancialReportingTypeAndMouStatusSuccessfully() throws java.text.ParseException, InterruptedException {

        ProjectDetailsPartnershipsListingPO projectDetailsPartnershipsListingPO = new ProjectDetailsPartnershipsListingPO(driver);
        ProjectDetailsPartnershipsEditPO projectDetailsPartnershipsEditPO = new ProjectDetailsPartnershipsEditPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with project manager user");
        new LoginFunctions(driver, selenium).loginByGivenUser(fpmUser);
        selenium.navigateToPage(strFpmPartnershipUrl);
        selenium.hardWait(3);
        new PartnershipFunctions(driver, selenium).editProjectPartnership(fpmEditProjectAdminData);

        Reporter.log("Step 2 - Verify that project partnership is not edited Financial Reporting Type and Mou Status by project manager user");
        Assert.assertFalse(projectDetailsPartnershipsEditPO.isFinancialReportingTypeRadioButtonEnabled(), "Partnership Financial Reporting Type radio button is enabled : ");
        Assert.assertFalse(projectDetailsPartnershipsEditPO.isMouStatusEnabled(), "Partnership Mou Status radio button is enabled : ");

        Reporter.log("Step 3 - Click on partnership submit button");
        projectDetailsPartnershipsEditPO.clickOnPartnershipSubmitButton();

        Reporter.log("Step 4 - Verify that project partnership is edited by project manager user");
        Assert.assertEquals(projectDetailsPartnershipsListingPO.getTypeText(fpmPartnerAdminData.getOrganizationName()), PartnerType.Managing.getPartnerType(), "partner types doesn't match");
        Assert.assertEquals(projectDetailsPartnershipsListingPO.getFinancialReportingTypeText(fpmPartnerAdminData.getOrganizationName()), PartnershipFinancialReportingTypes.Funded.getPartnershipFinancialReportingTypes(), "partnership financial reporting types doesn't match");
        Assert.assertEquals(projectDetailsPartnershipsListingPO.getAgreementStatusText(fpmPartnerAdminData.getOrganizationName()), PartnershipStatus.AwaitingSignature.getPartnerShipStatus(), " partnership agreement status doesn't match");
        Assert.assertEquals(projectDetailsPartnershipsListingPO.getMouStatusText(fpmPartnerAdminData.getOrganizationName()), PartnershipStatus.NotAttached.getPartnerShipStatus(), " partnership mou status doesn't match");
        Assert.assertEquals(projectDetailsPartnershipsListingPO.getMouDateRangeText(fpmPartnerAdminData.getOrganizationName()), new JavaHelpers().changeDateFormat(fpmEditProjectAdminData.getStartDate(), "MM/dd/yyyy", "M/d/yyyy") + " - " + new JavaHelpers().changeDateFormat(fpmEditProjectAdminData.getEndDate(), "MM/dd/yyyy", "M/d/yyyy"), "project start end date doesn't match : ");

    }

    /*Test 14 : Verify that project manager can create project internship engagements */
    @Test(priority = 14)
    public void VerifyThatProjectManagerCanCreateProjectInternshipEngagementsSuccessfully() throws InterruptedException {

        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with project manager user");
        new LoginFunctions(driver, selenium).loginByGivenUser(fpmUser);
        selenium.navigateToPage(strFpmInternshipUrl);
        selenium.hardWait(3);

        Reporter.log("Step 2 - Verify the created internship engagement");
        Assert.assertEquals(projectDetails.getInternshipName(fpmUser.getRealFirstName() + " " + fpmUser.getRealLastName()), (fpmUser.getRealFirstName() + " " + fpmUser.getRealLastName()), "project internship name doesn't match : ");
    }

    /*Test 15 : Verify that project manager can edit project internship engagements except DisbursementCompleteDate */ //Todo Bug(#864)
    @Test(priority = 15)
    public void VerifyThatProjectManagerCanEditProjectInternshipEngagementsSuccessfullyExceptDisbursementCompleteDate() throws java.text.ParseException, InterruptedException {

        ProjectInternshipDetailsPO projectInternshipDetailsPO = new ProjectInternshipDetailsPO(driver);

        Reporter.log("Step 1 -  Login with project manager user and edit internship engagement in created project");
        new LoginFunctions(driver, selenium).loginByGivenUser(fpmUser);
        selenium.navigateToPage(strFpmInternshipUrl);

        selenium.hardWait(3);

        new InternshipEngagementsFunctions(driver, selenium).editProjectInternshipEngagements(fpmUser);
        String newVersionFileName = JavaHelpers.getPropertyValue(PROPERTY_TEST_FILE, "newVersionFile");

        Reporter.log("Step 2 - Verify that project internship is edited by project manager user");
        Assert.assertEquals(projectInternshipDetailsPO.getInternshipStartEndDate(), new JavaHelpers().changeDateFormat(fpmInternUpdateData.getInternshipStartDate(), "MM/dd/yyyy", "M/d/yyyy") + "\n" + " - " + "\n" + new JavaHelpers().changeDateFormat(fpmInternUpdateData.getInternshipEndDate(), "MM/dd/yyyy", "M/d/yyyy"), "Internship start end date doesn't match :");
        Assert.assertEquals(ProjectInternPosition.Administration.getInternPosition(), projectInternshipDetailsPO.getInternPosition(), "internship position doesn't match : ");
        Assert.assertEquals(projectInternshipDetailsPO.getGrowthPlanCompleteDate(), new JavaHelpers().changeDateFormat(fpmInternUpdateData.getGrowthPlanCompleteDate(), "MM/dd/yyyy", "M/d/yyyy"), "internship growth plan complete date  doesn't match :");
        Assert.assertEquals(newVersionFileName, projectInternshipDetailsPO.getUploadedFileText(), "uploaded file name doesn't match : " + newVersionFileName);
        Assert.assertEquals(projectInternshipDetailsPO.getMethodologiesText(), ProjectInternshipMethodologies.OneStory.getInternshipMethodology() + "\n" + ProjectInternshipMethodologies.Render.getInternshipMethodology(), "internship methodologies doesn't match : ");
        Assert.assertEquals(projectInternshipDetailsPO.getActualDateText(), new JavaHelpers().changeDateFormat(fpmInternUpdateData.getInternshipActualDate(), "MM/dd/yyyy", "M/d/yyyy"), "internship actual date doesn't match : ");
        Assert.assertEquals(projectInternshipDetailsPO.getEstimatedDateText(), new JavaHelpers().changeDateFormat(fpmInternUpdateData.getInternshipEstimatedDate(), "MM/dd/yyyy", "M/d/yyyy"), "internship estimated date doesn't match : ");
        Assert.assertEquals(projectInternshipDetailsPO.getMentorText(), fpmUser.getRealFirstName() + " " + fpmUser.getRealLastName(), "internship mentor name doesn't match : ");

        Reporter.log("Step 3 - Verify that project internship DisbursementCompleteDateButton is not enabled");
        Assert.assertFalse(projectInternshipDetailsPO.isDisbursementCompleteDateButtonEnabled(), "Internship engagement disbursement complete date button is enabled : ");

    }

    /*Test 16 : Verify that project manager can read project location */
    @Test(priority = 16)
    public void VerifyThatProjectManagerCanReadProjectLocation() throws InterruptedException {

        HeaderPO headerPO = new HeaderPO(driver);
        LocationsListingPO locationsListingPO = new LocationsListingPO(driver);
        LocationDetailsPO locationDetailsPO = new LocationDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with project manager user");
        new LoginFunctions(driver, selenium).loginByGivenUser(fpmUser);

        Reporter.log("Step 2 - Enter location name and click on view location button");
        headerPO.searchItemGloballyByName(fpmLocationName);
        locationsListingPO.clickOnFirstViewLocationButton();

        Reporter.log("Step 3 - Verify the location edit button is not present");
        Assert.assertFalse(locationDetailsPO.isLocationEditButtonPresent(), "Location edit button is present:");

    }

    /*Test 17 : Verify that project manager can not create language partner and location   */
    @Test(priority = 17)
    public void VerifyThatProjectManagerCanNotCreateLanguagePartnerAndLocation() throws InterruptedException {

        LeftNavPO leftNavPO = new LeftNavPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with project manager user");
        new LoginFunctions(driver, selenium).loginByGivenUser(fpmUser);

        Reporter.log("Step 2 - Enter location name and click on view location button");
        leftNavPO.clickOnCreateNewItemMenuButton();

        Reporter.log("Step 3 - Verify language location and partner option is not present");
        Assert.assertFalse(leftNavPO.isMenuItemPresent(CreateItemTypes.Location.toString()), "Location menu item is present:");
        Assert.assertFalse(leftNavPO.isMenuItemPresent(CreateItemTypes.Language.toString()), "Language menu item is present:");
        Assert.assertFalse(leftNavPO.isMenuItemPresent(CreateItemTypes.Partner.toString()), "Partner menu item is present:");

        Reporter.log("Step 4 - Verify project person option is present");
        Assert.assertTrue(leftNavPO.isMenuItemPresent(CreateItemTypes.Project.toString()), "Project menu item is not present:");
        Assert.assertTrue(leftNavPO.isMenuItemPresent(CreateItemTypes.Person.toString()), "Language menu item is not present:");

    }

    /*Test 18 : Verify that project manager can create project language engagements */
    @Test(priority = 18)
    public void VerifyThatProjectManagerCanCreateProjectLanguageEngagements() throws InterruptedException {

        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with project manager user");
        new LoginFunctions(driver, selenium).loginByGivenUser(fpmUser);
        selenium.navigateToPage(strFpmLanguageTranslationUrl);

        selenium.hardWait(3);

        Reporter.log("Step 2 - Verify the created language engagement");
        Assert.assertEquals(projectDetails.getLanguageEngagementName(fpmLanguageAdminData.getLanguageEngagementName()), fpmLanguageAdminData.getLanguageEngagementName(), "project language engagement name doesn't match");
    }

    /*Test 19 : Verify that project manager can edit project language engagements except DisbursementCompleteDate */
    @Test(priority = 19)
    public void VerifyThatProjectManagerCanEditProjectLanguageEngagementsExceptDisbursementCompleteDate() throws java.text.ParseException, InterruptedException {

        ProjectLanguageEngagementDetailsPO projectLanguageEngagementDetailsPO = new ProjectLanguageEngagementDetailsPO(driver);
        String textFileName = JavaHelpers.getPropertyValue(PROPERTY_TEST_FILE, "textFile");

        Reporter.log("Step 1 -  Login with project manager user and edit language engagement in created project");
        new LoginFunctions(driver, selenium).loginByGivenUser(fpmUser);
        selenium.navigateToPage(strFpmLanguageTranslationUrl);
        selenium.hardWait(3);
        new LanguageFunctions(driver, selenium).editProjectLanguageEngagements(fpmInternData,fpmLanguageAdminData);

        Reporter.log("Step 2 - Verify the edited language engagement");
        Assert.assertEquals(projectLanguageEngagementDetailsPO.getLanguageStartEndDate(), new JavaHelpers().changeDateFormat(fpmInternData.getInternshipStartDate(), "MM/dd/yyyy", "M/d/yyyy") + "\n" + " - " + "\n" + new JavaHelpers().changeDateFormat(fpmInternData.getInternshipEndDate(), "MM/dd/yyyy", "M/d/yyyy"), "Language engagement start end date doesn't match");
        Assert.assertEquals(projectLanguageEngagementDetailsPO.getTranslationCompleteDate(), fpmInternData.getGrowthPlanCompleteDate(), "Language engagement growth plan complete date  doesn't match");
        Assert.assertEquals(projectLanguageEngagementDetailsPO.getDisbursementCompleteDate(), fpmInternData.getDisbursementCompleteDate(), "Language engagement disbursement complete date  doesn't match");
        Assert.assertEquals(textFileName, projectLanguageEngagementDetailsPO.getUploadedFileText(), "uploaded file name doesn't match : " + textFileName);
        Assert.assertEquals(projectLanguageEngagementDetailsPO.getActualDateTextBox(), fpmInternData.getInternshipActualDate(), "Language engagement actual date doesn't match");
        Assert.assertEquals(projectLanguageEngagementDetailsPO.getEstimatedDateTextBox(), fpmInternData.getInternshipEstimatedDate(), "Language engagement estimated date doesn't match");

        Reporter.log("Step 3 - Verify that project language engagement DisbursementCompleteDateButton is not enabled");
        Assert.assertFalse(projectLanguageEngagementDetailsPO.isDisbursementCompleteDateButtonEnabled(), "Internship engagement disbursement complete date button is enabled : "); //Todo bug(#762) Global R access

    }

    /*Test 20 : Verify that project manager can Add project goals */
    @Test(priority = 20)
    public void VerifyThatProjectManagerCanAddProjectGoalsSuccessfully() throws InterruptedException {

        GoalDetailsPO goalDetailsPO = new GoalDetailsPO(driver);
        ProjectLanguageEngagementDetailsPO projectLanguageEngagementDetailsPO = new ProjectLanguageEngagementDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with project manager user");
        new LoginFunctions(driver, selenium).loginByGivenUser(fpmUser);
        selenium.navigateToPage(strFpmProjectCreateGoalUrl);
        selenium.hardWait(3);

        Reporter.log("Step 2 - Verify goal type");
        Assert.assertEquals(projectLanguageEngagementDetailsPO.getGoalText(), (fpmGoalData.getGoalStoryName()), "Goal type is not matched");

        Reporter.log("Step 3 - Click on created goal");
        projectLanguageEngagementDetailsPO.clickOnGoalCard();

        Reporter.log("Step 14 - verify goal details");
        Assert.assertEquals(Arrays.stream(goalDetailsPO.getGoalDistributionMethodsText().split(",")).map(String::trim).toArray(String[]::new), GoalDistributionMethods.getGoalDistributionMethodsByName(fpmGoalData.getGoalDistributionMethods()), "Goal distribution method is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalDistributionMethodsPartnerText(), fpmPartnerAdminData.getOrganizationName(), "Goal distribution method partner is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalMethodologyText(), fpmGoalData.getMethodologyType() + " - " + fpmGoalData.getMethodologyValue(), "Goal methodology is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalCompletionDescriptionText(), (fpmGoalData.getGoalCompletionDescription()), "Goal completion description is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalScriptureReferenceOldTestamentText(), fpmGoalData.getOldScriptureReference(), "Goal old scriptureReference is not matched");

        Reporter.log("Step 15 - Refresh page and verify Goal details");
        selenium.refreshPage();

        Assert.assertEquals(Arrays.stream(goalDetailsPO.getGoalDistributionMethodsText().split(",")).map(String::trim).toArray(String[]::new), GoalDistributionMethods.getGoalDistributionMethodsByName(fpmGoalData.getGoalDistributionMethods()), "Goal distribution method is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalDistributionMethodsPartnerText(), fpmPartnerAdminData.getOrganizationName(), "Goal distribution method partner is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalMethodologyText(), fpmGoalData.getMethodologyType() + " - " + fpmGoalData.getMethodologyValue(), "Goal methodology is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalCompletionDescriptionText(), (fpmGoalData.getGoalCompletionDescription()), "Goal completion description is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalScriptureReferenceOldTestamentText(), fpmGoalData.getOldScriptureReference(), "Goal old scriptureReference is not matched");

    }

    /*Test 21 : Verify that project manager can edit created project goals */
    @Test(priority = 21)
    public void VerifyThatProjectManagerCanEditProjectGoalsSuccessfully() throws InterruptedException {

        GoalDetailsPO goalDetailsPO = new GoalDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with project manager user");
        new LoginFunctions(driver, selenium).loginByGivenUser(fpmUser);
        selenium.navigateToPage(strFpmProjectCreateGoalUrl);

        selenium.hardWait(3);

        Reporter.log("Step 2 - Edit Story Goal by project manager user");
        new GoalFunctions(driver, selenium).editGoal(fpmEditGoalData , fpmGoalData ,fpmPartnerAdminData.getOrganizationName());

        Reporter.log("Step 3 - Verify the created goal");
        Assert.assertEquals(goalDetailsPO.getGoalNameText(), (fpmGoalData.getGoalStoryName()), "Goal type is not matched");

        Reporter.log("Step 4 - Verify goal details");
        Assert.assertEquals(Arrays.stream(goalDetailsPO.getGoalDistributionMethodsText().split(",")).map(String::trim).toArray(String[]::new), GoalDistributionMethods.getGoalDistributionMethodsByName(fpmEditGoalData.getGoalDistributionMethods()), "Goal mediums is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalDistributionMethodsPartnerText(), fpmPartnerAdminData.getOrganizationName(), "Goal distribution method partner is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalMethodologyText(), fpmEditGoalData.getMethodologyType() + " - " + fpmEditGoalData.getMethodologyValue(), "Goal methodology is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalCompletionDescriptionText(), (fpmEditGoalData.getGoalCompletionDescription()), "Goal completion description is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalScriptureReferenceOldTestamentText(), fpmEditGoalData.getOldScriptureReference(), "Goal old scriptureReference is not matched");

        Reporter.log("Step 5 - Refresh page and verify Goal details");
        selenium.refreshPage();
        Assert.assertEquals(Arrays.stream(goalDetailsPO.getGoalDistributionMethodsText().split(",")).map(String::trim).toArray(String[]::new), GoalDistributionMethods.getGoalDistributionMethodsByName(fpmEditGoalData.getGoalDistributionMethods()), "Goal mediums is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalDistributionMethodsPartnerText(), fpmPartnerAdminData.getOrganizationName(), "Goal distribution method partner is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalMethodologyText(), fpmEditGoalData.getMethodologyType() + " - " + fpmEditGoalData.getMethodologyValue(), "Goal methodology is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalCompletionDescriptionText(), (fpmEditGoalData.getGoalCompletionDescription()), "Goal completion description is not matched");
        Assert.assertEquals(goalDetailsPO.getGoalScriptureReferenceOldTestamentText(), fpmEditGoalData.getOldScriptureReference(), "Goal old scriptureReference is not matched");

    }

}

package cord.projects.rolePermissionFlow;

import base.BaseTest;
import datafactory.*;
import dataobjects.*;
import enums.menu.CreateItemTypes;
import enums.partner.PartnerStatus;
import enums.partner.PartnerType;
import enums.partnership.PartnershipFinancialReportingTypes;
import enums.partnership.PartnershipStatus;
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
import pageobjects.cord.projects.intershipEngagement.*;
import pageobjects.cord.projects.languageEngagement.ProjectLanguageEngagementDetailsPO;
import pageobjects.cord.projects.languageEngagement.goal.CreateGoalPO;
import utilities.Constants;
import utilities.DriverManager;
import utilities.JavaHelpers;
import utilities.SeleniumHelpers;

public class ControllerTests extends BaseTest {

    protected String strControllerFpmPeopleUrl;
    protected String strControllerLanguageUrl;
    protected String strControllerInternshipUrl;
    protected String strControllerLanguageTranslationUrl;
    protected String strControllerProjectCreateGoalUrl;
    protected String strControllerPartnershipUrl;
    protected String strControllerProjectTeamMemberUrl;
    protected String strControllerFundingPartnershipUrl;
    People controllerPeopleFpmData = new PeopleData().getPeopleData();
    People controllerEditPeopleControllerData = new PeopleData().getPeopleData();
    Project controllerAdminData = new ProjectData().getCreateProjectData();
    Project controllerAdminBudgetData = new ProjectData().getCreateProjectData();
    String controllerCreatedBudgetProjectName = controllerAdminBudgetData.getProjectName();
    Budget controllerBudgetData = new BudgetData().getBudgetData();
    Project controllerAdminTranslationData = new ProjectData().getCreateTranslationProjectData();
    String controllerCreatedTranslationProjectName = controllerAdminTranslationData.getProjectName();
    Partner controllerPartnerControllerData = new PartnerData().getCreatePartnerData();
    String controllerCreatedPartnerName = controllerPartnerControllerData.getPartnerName();
    String controllerCreatedProjectName = controllerAdminData.getProjectName();
    People controllerPeopleAdminData = new PeopleData().getPeopleData();
    Project controllerEditProjectAdminData = new ProjectData().getUpdateProjectData();
    Partner controllerEditPartnerControllerData = new PartnerData().getEditPartnerData();
    Project controllerEditProjectBudgetData = new ProjectData().getUpdateProjectData();
    Language controllerLanguageAdminData = new LanguageData().getLanguageData();
    Partner controllerPartnerAdminData = new PartnerData().getCreatePartnerData();
    Goal controllerGoalData = new GoalData().getCreateGoalData();
    Partner controllerBudgetPartnerAdminData = new PartnerData().getCreatePartnerData();
    Project controllerFpmData = new ProjectData().getCreateProjectData();

    public ControllerTests() throws ParseException, java.text.ParseException, InterruptedException {
    }

    public static final String PROPERTY_TEST_FILE = "src/main/resources/testData/TestFiles.properties";

    @BeforeClass
    public void setupTestData() throws InterruptedException {

        WebDriver driver;
        DriverManager drivermanager;
        drivermanager = new DriverManager();
        driver = drivermanager.setUp("chrome");
        SeleniumHelpers selenium = new SeleniumHelpers(driver);

        Reporter.log("Step 1 - Create Project by admin user");
        new LoginFunctions(driver, selenium).loginByGivenUser(adminUser);
        new ProjectFunctions(driver, selenium).createProject(controllerAdminData);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 2 - Create Budget Project by admin user and logout that user");
        new ProjectFunctions(driver, selenium).createProject(controllerAdminBudgetData);
        new ProjectFunctions(driver, selenium).addMemberToProject(fpmUser, controllerAdminBudgetData);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 3 - Create TranslationType Project by admin user and logout that user");
        new ProjectFunctions(driver, selenium).createProject(controllerAdminTranslationData);
        new PartnershipFunctions(driver, selenium).createProjectPartnership(controllerPartnerAdminData ,controllerCreatedTranslationProjectName,adminUser);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 4 - Create internship engagements by admin user");
        new InternshipEngagementsFunctions(driver, selenium).createProjectInternshipEngagements(adminUser, controllerCreatedProjectName);
        strControllerInternshipUrl = driver.getCurrentUrl();
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(fpmUser);

        Reporter.log("Step 5 - Create Project by project manager");
        new ProjectFunctions(driver, selenium).createProject(controllerFpmData);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 6 - Create People by admin user");
        new PeopleFunctions(driver, selenium).createPeople(controllerPeopleAdminData,adminUser);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(fpmUser);

        Reporter.log("Step 7- Create People by project manager user");
        new PeopleFunctions(driver, selenium).createPeople(controllerPeopleFpmData,fpmUser);
        strControllerFpmPeopleUrl = driver.getCurrentUrl();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 8 - Create Language by admin user");
        new LanguageFunctions(driver, selenium).createLanguage(controllerLanguageAdminData);
        strControllerLanguageUrl = driver.getCurrentUrl();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 9 - Create language engagements by admin user");
        new LanguageFunctions(driver, selenium).createProjectLanguageEngagements(controllerLanguageAdminData, controllerCreatedTranslationProjectName,adminUser);
        strControllerLanguageTranslationUrl = driver.getCurrentUrl();
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 10 - Create Partner by admin user");
        new PartnerFunctions(driver, selenium).createPartner(controllerPartnerAdminData);
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(controllerUser);

        Reporter.log("Step 11 - Create Partner by controller user");
        new PartnerFunctions(driver, selenium).createPartner(controllerPartnerControllerData);
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(controllerUser);

        Reporter.log("Step 12 - Create partnership by controller user");
        new PartnershipFunctions(driver, selenium).createProjectPartnership(controllerPartnerAdminData, controllerCreatedProjectName,controllerUser);
        strControllerPartnershipUrl = driver.getCurrentUrl();
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(controllerUser);

        Reporter.log("Step 13 - Add project team member with multiple user role");
        new ProjectFunctions(driver, selenium).addMemberToProject(rdFodUser, controllerAdminData);
        strControllerProjectTeamMemberUrl = driver.getCurrentUrl();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 14 - Create goal by admin user");
        selenium.navigateToPage(strControllerLanguageTranslationUrl);
        new GoalFunctions(driver, selenium).createGoal(controllerGoalData, controllerLanguageAdminData.getLanguageEngagementName(),controllerPartnerAdminData.getOrganizationName());
        strControllerProjectCreateGoalUrl = driver.getCurrentUrl();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 15 - Create funding partnership by admin user");
        new ProjectFunctions(driver, selenium).editInternshipProjectByName(controllerEditProjectBudgetData, controllerCreatedBudgetProjectName,adminUser);
        new PartnershipFunctions(driver, selenium).createProjectFundingPartnership(controllerBudgetPartnerAdminData, controllerEditProjectBudgetData.getProjectName());
        selenium.hardWait(3);
        strControllerFundingPartnershipUrl = driver.getCurrentUrl();
        drivermanager.tearDown();
    }

    /*Test 1 : Verify that controller can view all projects */
    @Test(priority = 1)
    public void VerifyControllerCanViewAllProjectsSuccessfully() throws InterruptedException {

        LeftNavPO sideHeader = new LeftNavPO(driver);
        ProjectsListingPO projectsListingPO = new ProjectsListingPO(driver);
        ProjectDetailsPO projectDetailsPO = new ProjectDetailsPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with controller user");
        new LoginFunctions(driver, selenium).loginByGivenUser(controllerUser);

        Reporter.log("Step 2 - Verify that projects is displayed which is created by admin");
        sideHeader.clickOnProjectsMenu();
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());

        if (!projectsListingPO.isProjectPresent(controllerAdminData.getProjectName())) {
            headerPO.searchItemGloballyByName(controllerAdminData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(controllerAdminData.getProjectName());
        Assert.assertEquals(projectDetailsPO.getProjectName(), controllerAdminData.getProjectName(), "Project is not present : " + controllerAdminData.getProjectName());

        Reporter.log("Step 3 - Verify that projects is displayed which is created by FPM user");
        sideHeader.clickOnProjectsMenu();
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());

        if (!projectsListingPO.isProjectPresent(controllerFpmData.getProjectName())) {
            headerPO.searchItemGloballyByName(controllerFpmData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(controllerFpmData.getProjectName());
        Assert.assertEquals(projectDetailsPO.getProjectName(), controllerFpmData.getProjectName(), "Project is not present : " + controllerFpmData.getProjectName());

    }

    /*Test 2 : Verify that controller can view all peoples */
    @Test(priority = 2)
    public void VerifyControllerCanViewAllPeoplesSuccessfully() throws InterruptedException {

        LeftNavPO sideHeader = new LeftNavPO(driver);
        PeoplesPO peoples = new PeoplesPO(driver);
        PeopleDetailsPO peopleDetails = new PeopleDetailsPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with controller user");
        selenium.navigateToPage(strControllerFpmPeopleUrl);
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).loginByGivenUser(controllerUser);

        Reporter.log("Step 2 - Verify that peoples is displayed which is created by admin");
        sideHeader.clickOnPeopleMenu();
        if (!peoples.isPeoplePresent(controllerPeopleAdminData.getPersonName())) {
            headerPO.searchItemGloballyByName(controllerPeopleAdminData.getPersonName());
        }
        peoples.clickOnPeopleName(controllerPeopleAdminData.getPersonName());
        Assert.assertEquals(peopleDetails.getPeopleName().replaceAll("\\s.*", ""), controllerPeopleAdminData.getPersonName(), "Project is not present : " + controllerPeopleAdminData.getPersonName());

        Reporter.log("Step 3 - Verify that peoples is displayed which is created by FPM user");
        sideHeader.clickOnPeopleMenu();
        if (!peoples.isPeoplePresent(controllerPeopleFpmData.getPersonName())) {
            headerPO.searchItemGloballyByName(controllerPeopleFpmData.getPersonName());
        }
        peoples.clickOnPeopleName(controllerPeopleFpmData.getPersonName());
        Assert.assertEquals(peopleDetails.getPeopleName().replaceAll("\\s.*", ""), controllerPeopleFpmData.getPersonName(), "Project is not present : " + controllerPeopleFpmData.getPersonName());

    }

    /*Test 3 : Verify that controller can edit mine peoples */
    @Test(priority = 3)
    public void VerifyControllerCanEditMinePeoplesSuccessfully() throws InterruptedException {

        LeftNavPO sideHeader = new LeftNavPO(driver);
        PeoplesPO peoples = new PeoplesPO(driver);
        PeopleDetailsPO peopleDetails = new PeopleDetailsPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);
        UpdatePeoplePO updatePeople = new UpdatePeoplePO(driver);

        Reporter.log("Step 1 - Navigate to url and login with controller user");
        new LoginFunctions(driver, selenium).loginByGivenUser(controllerUser);

        Reporter.log("Step 2 - Search user which have a controller role by email and click on people name");
        sideHeader.clickOnPeopleMenu();
        if (!peoples.isPeoplePresent(controllerUser.getEmail())) {
            headerPO.searchItemGloballyByName(controllerUser.getEmail());
        }
        peoples.clickOnPeopleName(controllerUser.getRealFirstName() + " " + controllerUser.getRealLastName());

        Reporter.log("Step 3 - Click on edit icon and edit mine person detail");
        peopleDetails.clickOnEditButton();
        updatePeople.clearAndUpdatePersonDetail(controllerEditPeopleControllerData);
        updatePeople.clickOnSubmitButton();

        Reporter.log("Step 4 - Verify updated person details");
        Assert.assertEquals(controllerEditPeopleControllerData.getPersonEmail(), peopleDetails.getEmail(), "email doesn't match : ");
        Assert.assertEquals(controllerEditPeopleControllerData.getPersonTitle(), peopleDetails.getTitle(), "title doesn't match : ");
        Assert.assertEquals(controllerEditPeopleControllerData.getPersonPhone(), peopleDetails.getPhoneNumber(), "phone number doesn't match : ");
        Assert.assertEquals(controllerEditPeopleControllerData.getPersonAbout(), peopleDetails.getAbout(), "About doesn't match : ");
        Assert.assertEquals(UserRole.Controller.getRole(), peopleDetails.getRoles(), "Roles doesn't match : ");

        Reporter.log("Step 5 - Again Click on edit icon and reset the people");
        peopleDetails.clickOnEditButton();
        updatePeople.fillFirstnameLastNameAndEmailDetail(controllerUser.getRealFirstName(), controllerUser.getRealLastName(),controllerUser.getEmail());
        updatePeople.clickOnSubmitButton();
        Assert.assertEquals(controllerUser.getRealFirstName() + " " + controllerUser.getRealLastName(), peopleDetails.getPeopleName(), "personName doesn't match : ");
        Assert.assertEquals(controllerUser.getEmail(), peopleDetails.getEmail(), "personEmail doesn't match : ");
    }

    /*Test 4 : Verify that controller can view all languages and edit icon is not present */
    @Test(priority = 4)
    public void VerifyControllerCanViewAllLanguagesAndEditIconIsNotPresent() throws InterruptedException {

        LeftNavPO sideHeader = new LeftNavPO(driver);
        LanguagesPO languages = new LanguagesPO(driver);
        LanguageDetailPO languageDetailPO = new LanguageDetailPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with controller user");
        selenium.navigateToPage(strControllerLanguageUrl);
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).loginByGivenUser(controllerUser);

        Reporter.log("Step 2 - Verify that language is displayed which is created by admin and edit icon is not present");
        sideHeader.clickOnLanguagesMenu();
        if (!languages.isLanguagePresent(controllerLanguageAdminData.getLanguageName())) {
            headerPO.searchItemGloballyByName(controllerLanguageAdminData.getLanguageName());
        }
        languages.clickOnLanguageName(controllerLanguageAdminData.getLanguageName());
        Assert.assertFalse(languageDetailPO.isEditIconPresent(), "Language edit button is present");
        Assert.assertEquals(languageDetailPO.getLanguageName(), controllerLanguageAdminData.getPubliicName(), "Language is not present : " + controllerLanguageAdminData.getPubliicName());
    }

    /*Test 5 : Verify that controller can view all partners */
    @Test(priority = 5)
    public void VerifyControllerCanViewAllPartnersSuccessfully() throws InterruptedException {

        LeftNavPO sideHeader = new LeftNavPO(driver);
        PartnersListingPO partnerListing = new PartnersListingPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with controller user");
        new LoginFunctions(driver, selenium).loginByGivenUser(controllerUser);

        Reporter.log("Step 2 - Verify that partner is displayed which is created by admin");
        sideHeader.clickOnPartnersMenu();
        if (!partnerListing.isPartnerPresent(controllerPartnerAdminData.getPartnerName())) {
            headerPO.searchItemGloballyByName(controllerPartnerAdminData.getPartnerName());
        }
        Assert.assertTrue(partnerListing.isPartnerPresent(controllerPartnerAdminData.getPartnerName()), "Partner is not present : " + controllerPartnerAdminData.getPartnerName());

    }

    /*Test 6 : Verify that controller can edit partners details */
    @Test(priority = 6)
    public void VerifyControllerCanEditPartnersDetailsSuccessfully() {

        PartnerDetailsPO partnerDetails = new PartnerDetailsPO(driver);

        Reporter.log("Step 1 - Login with controller user");
        new LoginFunctions(driver, selenium).loginByGivenUser(controllerUser);

        Reporter.log("Step 2 - Verify that partner is displayed which is created by admin");
        new PartnerFunctions(driver, selenium).editProjectPartner(controllerUser, controllerEditPartnerControllerData, controllerCreatedPartnerName);

        Reporter.log("Step 3 - verify that partner data is edited successfully");
        Assert.assertTrue(partnerDetails.isGlobalInnovationsClientPresent(), "Global Innovations Client is not present ");
        Assert.assertEquals(PartnerStatus.Active.toString(), partnerDetails.getPartnerStatusByName(PartnerStatus.Active.toString()), "Status doesn't match");
        Assert.assertEquals(controllerEditPartnerControllerData.getPartnerPmcEntityCode(), partnerDetails.getPmcEntityCodeText(1), "entity code doesn't match ");
        Assert.assertEquals(controllerEditPartnerControllerData.getPartnerAddress(), partnerDetails.getAddressText(), "address doesn't match ");
        Assert.assertEquals(PartnerType.Impact.toString(), partnerDetails.getTypesText(), "Types doesn't match");
        Assert.assertEquals(controllerUser.getRealFirstName() + " " + controllerUser.getRealLastName(), partnerDetails.getPointOfContactText(), "point of contact doesn't match ");

    }

    /*Test 7 : Verify that controller can upload/Edit project files */
    @Test(priority = 7)
    public void VerifyThatControllerCanUploadProjectFilesSuccessfully() throws InterruptedException {

        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectDetailsFilesListingPO projectDetailsFilesListingPO = new ProjectDetailsFilesListingPO(driver);
        ProjectDetailsFilesHistoryListingPO projectDetailsFilesHistoryListingPO = new ProjectDetailsFilesHistoryListingPO(driver);
        ProjectDetailsFileListingRenameFileFolderPO projectDetailsFileListingRenameFileFolderPO = new ProjectDetailsFileListingRenameFileFolderPO(driver);
        ProjectDetailsFileListingDeleteFileFolderPO projectDetailsFileListingDeleteFileFolderPO = new ProjectDetailsFileListingDeleteFileFolderPO(driver);

        String textFileName = JavaHelpers.getPropertyValue(PROPERTY_TEST_FILE, "textFile");

        Reporter.log("Step 1 - Entering valid sign in details and login to application");
        new LoginFunctions(driver, selenium).loginByGivenUser(controllerUser);

        Reporter.log("Step 2 -  Login with controller user and upload file in created project");
        new FileFunctions(driver, selenium).uploadFile(controllerCreatedProjectName, textFileName);

        Reporter.log("Step 3 - Verify that project file is uploaded by controller user ");
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
        projectDetailsFileListingRenameFileFolderPO.clearAndRenameFileFolderName(controllerEditProjectAdminData);
        Assert.assertTrue(projectDetailsFilesHistoryListingPO.isFilePresent(controllerEditProjectAdminData.getFileName() + ".txt"), "renamed file is not present : ");
        Assert.assertFalse(projectDetailsFilesHistoryListingPO.isFilePresent(versionFileName), "renamed file is present : ");

        Reporter.log("Step 8 - Click on history kebab menu and delete created file");
        projectDetailsFilesHistoryListingPO.clickOnKebabMenuAndDeleteFileFolder(projectDetailsFilesListingPO.getFirstFileUploadText());
        projectDetailsFileListingDeleteFileFolderPO.clickOnFileDeleteButton();
        Assert.assertFalse(projectDetailsFilesListingPO.isFileFolderPresent(controllerEditProjectAdminData.getFileName()), "deleted file is present : ");

    }

    /*Test 8 : Verify that controller can edit project budget */
    @Test(priority = 8)
    public void VerifyThatControllerCanEditProjectBudgetSuccessfully() throws InterruptedException {

        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectDetailsBudgetListingPO projectDetailsBudgetListingPO = new ProjectDetailsBudgetListingPO(driver);
        ProjectDetailsPartnershipsListingPO projectDetailsPartnershipsListingPO = new ProjectDetailsPartnershipsListingPO(driver);
        String textFileName = JavaHelpers.getPropertyValue(PROPERTY_TEST_FILE, "textFile");

        Reporter.log("Step 1 - Entering valid sign in details and login to application");
        new LoginFunctions(driver, selenium).loginByGivenUser(controllerUser);

        Reporter.log("Step 2 - Navigate on partnership detail page and click on project link button");
        selenium.navigateToPage(strControllerFundingPartnershipUrl);
        selenium.hardWait(3);
        projectDetailsPartnershipsListingPO.clickOnProjectLinkButton();

        Reporter.log("Step 3 - Click on budget detail button");
        projectDetails.clickOnBudgetButton();

        Reporter.log("Step 4 - Edit budget in created project");
        new BudgetFunctions(driver, selenium).editProjectBudget(textFileName, fodUser, controllerBudgetData);

        Reporter.log("Step 5 - Verify that project budget is edited by project manager user");
        Assert.assertEquals(projectDetailsBudgetListingPO.getUploadedTemplateFileName(), textFileName, "project budget template file name doesn't match : ");
        Assert.assertEquals(projectDetailsBudgetListingPO.getBudgetAmountText().replace(",", ""), "$" + controllerBudgetData.getBudgetAmount(), "project budget amount doesn't match : ");
    }

    /*Test 9 : Verify that controller can add project team member */
    @Test(priority = 9)
    public void VerifyThatControllerCanAddProjectTeamMemberSuccessfully() throws InterruptedException {
        ProjectDetailsTeamMembersListingPO projectDetailsTeamMembersListingPO = new ProjectDetailsTeamMembersListingPO(driver);

        Reporter.log("Step 1 -  Login with controller user and add team member in created project");
        new LoginFunctions(driver, selenium).loginByGivenUser(controllerUser);
        selenium.navigateToPage(strControllerProjectTeamMemberUrl);

        selenium.hardWait(3);

        Reporter.log("Step 2 - Verify that project team member is added by controller user");
        Assert.assertEquals(projectDetailsTeamMembersListingPO.getMembersName(rdFodUser.getRealFirstName() + " " + rdFodUser.getRealLastName()), (rdFodUser.getRealFirstName() + " " + rdFodUser.getRealLastName()), "project member name doesn't match : ");
        Assert.assertEquals(projectDetailsTeamMembersListingPO.getMembersRoleByMemberName(rdFodUser.getRealFirstName() + " " + rdFodUser.getRealLastName()), rdFodUser.getUserRoles().get(0), "project member role doesn't match : ");
    }

    /*Test 10 : Verify that controller can edit project team member */
    @Test(priority = 10)
    public void VerifyThatControllerCanEditProjectTeamMemberSuccessfully() {

        ProjectDetailsTeamMembersListingPO projectDetailsTeamMembersListingPO = new ProjectDetailsTeamMembersListingPO(driver);

        Reporter.log("Step 1 -  Login with controller user and edit team member in created project");
        new LoginFunctions(driver, selenium).loginByGivenUser(controllerUser);
        selenium.navigateToPage(strControllerProjectTeamMemberUrl);
        new TeamMemberFunctions(driver, selenium).editProjectTeamMember(rdFodUser);

        Reporter.log("Step 2 - Verify that project team member is edited by controller user");
        Assert.assertEquals(projectDetailsTeamMembersListingPO.getMembersRoleByMemberName(rdFodUser.getRealFirstName() + " " + rdFodUser.getRealLastName()), rdFodUser.getUserRoles().get(0) + ", " + rdFodUser.getUserRoles().get(1), "project member updated role doesn't match : ");
    }

    /*Test 11 : Verify that controller can create project partnership */
    @Test(priority = 11)
    public void VerifyThatControllerCanCreateProjectPartnershipSuccessfully() throws InterruptedException {

        ProjectDetailsPartnershipsListingPO projectDetailsPartnershipsListingPO = new ProjectDetailsPartnershipsListingPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with controller user");
        new LoginFunctions(driver, selenium).loginByGivenUser(controllerUser);
        selenium.navigateToPage(strControllerPartnershipUrl);

        selenium.hardWait(3);

        Reporter.log("Step 2 - Verify that project partnership is created by controller user");
        Assert.assertEquals(projectDetailsPartnershipsListingPO.getPartnershipName(controllerPartnerAdminData.getOrganizationName()), controllerPartnerAdminData.getOrganizationName(), "project partnership doesn't match : ");
    }

    /*Test 12 : Verify that controller can edit project partnership */
    @Test(priority = 12)
    public void VerifyThatControllerCanEditProjectPartnershipSuccessfully() throws InterruptedException, java.text.ParseException {

        ProjectDetailsPartnershipsEditPO projectDetailsPartnershipsEditPO = new ProjectDetailsPartnershipsEditPO(driver);
        ProjectDetailsPartnershipsListingPO projectDetailsPartnershipsListingPO = new ProjectDetailsPartnershipsListingPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with controller user");
        new LoginFunctions(driver, selenium).loginByGivenUser(controllerUser);
        selenium.navigateToPage(strControllerPartnershipUrl);
        selenium.hardWait(3);
        new PartnershipFunctions(driver, selenium).editProjectPartnership(controllerEditProjectAdminData);

        Reporter.log("Step 2 - Click on partnership submit button");
        projectDetailsPartnershipsEditPO.clickOnPartnershipSubmitButton();

        Reporter.log("Step 3 - Verify that project partnership is edited by controller user");
        Assert.assertEquals(projectDetailsPartnershipsListingPO.getTypeText(controllerPartnerAdminData.getOrganizationName()), PartnerType.Managing.getPartnerType(), "partner types doesn't match");
        Assert.assertEquals(projectDetailsPartnershipsListingPO.getFinancialReportingTypeText(controllerPartnerAdminData.getOrganizationName()), PartnershipFinancialReportingTypes.Funded.getPartnershipFinancialReportingTypes(), "partnership financial reporting types doesn't match");
        Assert.assertEquals(projectDetailsPartnershipsListingPO.getAgreementStatusText(controllerPartnerAdminData.getOrganizationName()), PartnershipStatus.AwaitingSignature.getPartnerShipStatus(), " partnership agreement status doesn't match");
        Assert.assertEquals(projectDetailsPartnershipsListingPO.getMouStatusText(controllerPartnerAdminData.getOrganizationName()), PartnershipStatus.NotAttached.getPartnerShipStatus(), " partnership mou status doesn't match");
        Assert.assertEquals(projectDetailsPartnershipsListingPO.getMouDateRangeText(controllerPartnerAdminData.getOrganizationName()), new JavaHelpers().changeDateFormat(controllerEditProjectAdminData.getStartDate(), "MM/dd/yyyy", "M/d/yyyy") + " - " + new JavaHelpers().changeDateFormat(controllerEditProjectAdminData.getEndDate(), "MM/dd/yyyy", "M/d/yyyy"), "project start end date doesn't match : ");

    }

    /*Test 13 : Verify that controller can not create project internship engagements */
    @Test(priority = 13)
    public void VerifyThatControllerCanNotCreateProjectInternshipEngagementsSuccessfully() throws InterruptedException {

        HeaderPO headerPO = new HeaderPO(driver);
        LeftNavPO sideHeader = new LeftNavPO(driver);
        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectsListingPO projectsListingPO = new ProjectsListingPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with controller user");
        new LoginFunctions(driver, selenium).loginByGivenUser(controllerUser);

        sideHeader.clickOnProjectsMenu();
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());

        if (!projectsListingPO.isProjectPresent(controllerAdminData.getProjectName())) {
            headerPO.searchItemGloballyByName(controllerAdminData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(controllerAdminData.getProjectName());

        Reporter.log("Step 2 - Verify the controller can't create internship engagement"); //todo bug(#762) internship global R access
        Assert.assertFalse(projectDetails.isProjectInternshipEngagementAddButtonPresent(), "Project internship engagement add button is present : ");
    }

    /*Test 14 : Verify that controller can not edit project internship engagements except disbursement complete date */
    @Test(priority = 14)
    public void VerifyThatControllerCanNotEditProjectInternshipEngagementsExceptDisbursementCompleteDate() throws InterruptedException {

        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectInternshipDetailsPO projectInternshipDetailsPO = new ProjectInternshipDetailsPO(driver);
        ProjectInternshipDetailsUpdateCountryOfOriginPO projectInternshipDetailsUpdateCountryOfOriginPO = new ProjectInternshipDetailsUpdateCountryOfOriginPO(driver);
        ProjectInternshipDetailsUpdateInternPositionPO projectInternshipDetailsUpdateInternPositionPO = new ProjectInternshipDetailsUpdateInternPositionPO(driver);
        ProjectEngagementDetailsUpdateStartEndDatePO projectEngagementDetailsUpdateStartEndDatePO = new ProjectEngagementDetailsUpdateStartEndDatePO(driver);
        ProjectInternshipLanguageDetailsUpdateStatusPO projectInternshipLanguageDetailsUpdateStatusPO = new ProjectInternshipLanguageDetailsUpdateStatusPO(driver);
        ProjectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO projectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO = new ProjectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO(driver);
        ProjectEngagementDetailsUpdateDisbursementCompleteDatePO projectEngagementDetailsUpdateDisbursementCompleteDatePO = new ProjectEngagementDetailsUpdateDisbursementCompleteDatePO(driver);

        Reporter.log("Step 1 -  Login with controller user and navigate in internship url");
        new LoginFunctions(driver, selenium).loginByGivenUser(controllerUser);
        selenium.navigateToPage(strControllerInternshipUrl);
        selenium.hardWait(3);

        Reporter.log("Step 2 - Try to edit internship engagement status");
        projectDetails.clickOnEditInternshipButton(adminUser.getRealFirstName() + " " + adminUser.getRealLastName());

        Reporter.log("Step 3 - Click on internship status button");
        projectInternshipDetailsPO.clickInternshipEngagementStatusButton();

        String internshipStatusErrorMessage = Constants.engagementStatusErrorMessage;
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

    /*Test 15 : Verify that controller can read project location */
    @Test(priority = 15)
    public void VerifyThatControllerCanReadProjectLocationSuccessfully() throws InterruptedException {

        HeaderPO headerPO = new HeaderPO(driver);
        LocationDetailsPO locationDetailsPO = new LocationDetailsPO(driver);
        LocationsListingPO locationsListingPO = new LocationsListingPO(driver);

        Reporter.log("Step 1 - Login with controller user");
        new LoginFunctions(driver, selenium).loginByGivenUser(controllerUser);

        Reporter.log("Step 2 - Search location by name and click on view location button");
        headerPO.searchItemGloballyByName(controllerEditProjectAdminData.getLocationName());
        locationsListingPO.clickOnFirstViewLocationButton();

        Reporter.log("Step 3 - Verify that location edit button is not present");
        Assert.assertFalse(locationDetailsPO.isLocationEditButtonPresent(), "Location edit button is present");
    }

    /*Test 16 : Verify that controller can not create language partner and location */
    @Test(priority = 16)
    public void VerifyThatControllerCanNotCreateLanguagePartnerAndLocation() throws InterruptedException {

        LeftNavPO leftNavPO = new LeftNavPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with controller user");
        new LoginFunctions(driver, selenium).loginByGivenUser(controllerUser);

        Reporter.log("Step 2 - Enter location name and click on view location button");
        leftNavPO.clickOnCreateNewItemMenuButton();

        Reporter.log("Step 3 - Verify language location and partner option is not present");
        Assert.assertFalse(leftNavPO.isMenuItemPresent(CreateItemTypes.Location.toString()), "Location menu item is present:");
        Assert.assertFalse(leftNavPO.isMenuItemPresent(CreateItemTypes.Language.toString()), "Language menu item is present:");
        Assert.assertFalse(leftNavPO.isMenuItemPresent(CreateItemTypes.Project.toString()), "Partner menu item is present:"); //Todo Bug(#) Project have Only access

        Reporter.log("Step 4 - Verify project person option is present");
        Assert.assertTrue(leftNavPO.isMenuItemPresent(CreateItemTypes.Partner.toString()), "Project menu item is not present:");
        Assert.assertTrue(leftNavPO.isMenuItemPresent(CreateItemTypes.Person.toString()), "Language menu item is not present:");

    }

    /*Test 17 : Verify that controller can not create project language engagements */
    @Test(priority = 17)
    public void VerifyThatControllerCanNotCreateProjectLanguageEngagementsSuccessfully() throws InterruptedException {

        HeaderPO headerPO = new HeaderPO(driver);
        LeftNavPO sideHeader = new LeftNavPO(driver);
        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectsListingPO projectsListingPO = new ProjectsListingPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with controller user");
        new LoginFunctions(driver, selenium).loginByGivenUser(controllerUser);

        Reporter.log("Step 2 - Search the language engagement project which is created by admin user");
        sideHeader.clickOnProjectsMenu();
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());

        if (!projectsListingPO.isProjectPresent(controllerAdminTranslationData.getProjectName())) {
            headerPO.searchItemGloballyByName(controllerAdminTranslationData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(controllerAdminTranslationData.getProjectName());

        Reporter.log("Step 3 - Verify the controller can't create internship engagement"); //Todo bug(#762) language engagement global R access
        Assert.assertFalse(projectDetails.isProjectLanguageEngagementAddButtonPresent(), "Project internship engagement add button is present : ");
    }

    /*Test 18 : Verify that controller can not edit project language engagements */
    @Test(priority = 18)
    public void VerifyThatControllerCanNotEditProjectLanguageEngagements() throws InterruptedException {

        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectLanguageEngagementDetailsPO projectLanguageEngagementDetailsPO = new ProjectLanguageEngagementDetailsPO(driver);
        ProjectEngagementDetailsUpdateParaTextRegisteryIdPO projectEngagementDetailsUpdateParaTextRegisteryIdPO = new ProjectEngagementDetailsUpdateParaTextRegisteryIdPO(driver);
        ProjectEngagementDetailsUpdateStartEndDatePO projectEngagementDetailsUpdateStartEndDatePO = new ProjectEngagementDetailsUpdateStartEndDatePO(driver);
        ProjectInternshipLanguageDetailsUpdateStatusPO projectInternshipLanguageDetailsUpdateStatusPO = new ProjectInternshipLanguageDetailsUpdateStatusPO(driver);
        ProjectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO projectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO = new ProjectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO(driver);

        Reporter.log("Step 1 -  Login with controller user and navigate in language engagement url");
        new LoginFunctions(driver, selenium).loginByGivenUser(controllerUser);
        selenium.navigateToPage(strControllerLanguageTranslationUrl);
        selenium.hardWait(3);

        Reporter.log("Step 2 - Click on view details button");
        projectDetails.clickOnViewDetailsLanguageEngagementButton(controllerLanguageAdminData.getLanguageEngagementName());

        Reporter.log("Step 3 - Click on language engagement status button");
        projectLanguageEngagementDetailsPO.clickLanguageEngagementStatusButton();
        String languageEngagementStatusErrorMessage = Constants.engagementStatusErrorMessage;
        Assert.assertEquals(languageEngagementStatusErrorMessage, projectInternshipLanguageDetailsUpdateStatusPO.getInternshipStatusErrorText(), "project language engagement status error message doesn't match : ");

        Reporter.log("Step 4 - Click on language engagement close button");
        projectInternshipLanguageDetailsUpdateStatusPO.clickOnInternshipStatusCloseButton();

        Reporter.log("Step 5 - Try to edit language engagement start end date");
        projectLanguageEngagementDetailsPO.clickLanguageEngagementStartEndDateButton();
        Assert.assertFalse(projectEngagementDetailsUpdateStartEndDatePO.isEngagementStartDateEnabled(), "Language engagement start date textBox is enabled : ");
        Assert.assertFalse(projectEngagementDetailsUpdateStartEndDatePO.isEngagementEndDateEnabled(), "Language engagement end date textBox is enabled : ");

        Reporter.log("Step 6 - Click on language engagement start end date close button");
        projectEngagementDetailsUpdateStartEndDatePO.clickOnEngagementStartEndDateCloseButton();

        Reporter.log("Step 7 - Try to edit language engagement paratext register Id");
        Assert.assertFalse(projectEngagementDetailsUpdateParaTextRegisteryIdPO.isEngagementParatextRegisterIdTextBoxEnabled(), "Language engagement paratext register Id textBox is enabled : ");

        Reporter.log("Step 9 - Try to edit language engagement translation complete date");
        Assert.assertFalse(projectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO.isTranslationCompleteDateTextBoxEnabled(), "Language engagement intern position textBox is enabled : ");

        Reporter.log("Step 10 - Try to edit language engagement disbursement complete date");
        Assert.assertFalse(projectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO.isDisbursementCompleteDateTextBoxEnabled(), "Language engagement disbursement complete date textBox is enabled : "); //todo bug(#2161)

        Reporter.log("Step 11 - Try to edit language engagement planned and actual(ceremony) date"); //todo bug(#2165)
        projectLanguageEngagementDetailsPO.clickDedicationToggleButton();
        Assert.assertFalse(projectLanguageEngagementDetailsPO.isEstimatedDateTextBoxEnabled(), "Language engagement estimated date textBox is enabled : ");
        Assert.assertFalse(projectLanguageEngagementDetailsPO.isActualDateTextBoxEnabled(), "Language engagement actual date textBox is enabled : ");

    }

    /*Test 19 : Verify that controller can read created project goals */
    @Test(priority = 19)
    public void VerifyThatControllerCanReadProjectGoals() throws InterruptedException {

        CreateGoalPO createGoalPO = new CreateGoalPO(driver);
        ProjectLanguageEngagementDetailsPO projectLanguageEngagementDetailsPO = new ProjectLanguageEngagementDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with controller user");
        new LoginFunctions(driver, selenium).loginByGivenUser(controllerUser);
        selenium.navigateToPage(strControllerProjectCreateGoalUrl);
        selenium.hardWait(3);

        Reporter.log("Step 2 - Click on goal edit button");
        projectLanguageEngagementDetailsPO.clickOnGoalCard();
        projectLanguageEngagementDetailsPO.clickEditGoalButton();

        Reporter.log("Step 3 - Verify that controller can read created project goals");
        String disableGoalColor = Constants.disableGrayColorCode;
        Assert.assertEquals(disableGoalColor, createGoalPO.getDisabledEditGoalDetailsColor(), "Goal detail color doesn't match : ");

    }

    /*Test 20 : Verify that controller can not Add project goals */
    @Test(priority = 20)
    public void VerifyThatControllerCanNotAddProjectGoals() throws InterruptedException {

        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectLanguageEngagementDetailsPO projectLanguageEngagementDetailsPO = new ProjectLanguageEngagementDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with controller user");
        new LoginFunctions(driver, selenium).loginByGivenUser(controllerUser);
        selenium.navigateToPage(strControllerLanguageTranslationUrl);
        selenium.hardWait(3);

        Reporter.log("Step 2 - Click on view details button");
        projectDetails.clickOnViewDetailsLanguageEngagementButton(controllerLanguageAdminData.getLanguageEngagementName());

        Reporter.log("Step 3 - Verify the Add goal button is not present on language engagement detail page");
        Assert.assertFalse(projectLanguageEngagementDetailsPO.isAddGoalButtonPresent(), "Add goal button is present ");

    }

    /*Test 21 : Verify that controller can not edit projects */ //Todo Bug(#2154)
    @Test(priority = 21)
    public void VerifyThatControllerCanNotEditProjectsSuccessfully() throws InterruptedException, java.text.ParseException {

        LeftNavPO sideHeader = new LeftNavPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);
        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectsListingPO projectsListingPO = new ProjectsListingPO(driver);
        ProjectDetailsUpdateStepsPO projectDetailsUpdateStepsPO = new ProjectDetailsUpdateStepsPO(driver);

        Reporter.log("Step 1 -  Login with controller user and edit created project");
        new LoginFunctions(driver, selenium).loginByGivenUser(controllerUser);
        new ProjectFunctions(driver, selenium).editInternshipProjectByName(controllerEditProjectAdminData, controllerCreatedProjectName,controllerUser);
        new ProjectFunctions(driver, selenium).clickOnProjectStatus();

        String statusErrorMessage = "The status of this project is unable to be changed.";
        Assert.assertEquals(statusErrorMessage, projectDetailsUpdateStepsPO.getProjectStatusError(), "Project step is changed");
        projectDetailsUpdateStepsPO.clickProjectStatusCloseButton();

        Reporter.log("Step 2 - Verify that project is displayed which is edited by controller user ");
        sideHeader.clickOnProjectsMenu();
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());

        if (!projectsListingPO.isProjectPresent(controllerEditProjectAdminData.getProjectName())) {
            headerPO.searchItemGloballyByName(controllerEditProjectAdminData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(controllerEditProjectAdminData.getProjectName());

        Assert.assertEquals(Sensitivity.Low.toString(), projectDetails.getProjectSensitivity(), "project sensitivity doesn't match : ");
        Assert.assertEquals(projectDetails.getProjectLocationAndFieldRegion(), controllerEditProjectAdminData.getLocationName() + " | " + controllerEditProjectAdminData.getFieldRegionName(), "project location and field region doesn't match : ");
        Assert.assertEquals(projectDetails.getProjectStartEndDate(), new JavaHelpers().changeDateFormat(controllerEditProjectAdminData.getStartDate(), "MM/dd/yyyy", "M/d/yyyy") + "\n" + " - " + "\n" + new JavaHelpers().changeDateFormat(controllerEditProjectAdminData.getEndDate(), "MM/dd/yyyy", "M/d/yyyy"), "project start end date doesn't match : ");

        Reporter.log("Step 3 - Verify that project name engagement estimated date is not editable");// todo R access list Estimated date ,project name,locations,engagement,field region
        Assert.assertFalse(projectDetails.isEditIconPresent(), "Project edit icon is present : ");
        Assert.assertFalse(projectDetails.isProjectInternshipEngagementAddButtonPresent(), "Project internship engagement add button is present : ");
        Assert.assertFalse(projectDetails.isEstimatedSubmissionDateButtonEnabled(), "Estimated submission data button is enabled : ");

    }
}



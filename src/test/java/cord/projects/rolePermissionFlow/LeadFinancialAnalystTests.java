package cord.projects.rolePermissionFlow;

import base.BaseTest;
import datafactory.*;
import dataobjects.*;
import enums.menu.CreateItemTypes;
import enums.partner.PartnerType;
import enums.partnership.PartnershipFinancialReportingTypes;
import enums.partnership.PartnershipStatus;
import enums.project.ProjectStep;
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
import org.testng.asserts.SoftAssert;
import pageobjects.cord.common.HeaderPO;
import pageobjects.cord.common.LeftNavPO;
import pageobjects.cord.languages.LanguageDetailPO;
import pageobjects.cord.languages.LanguagesPO;
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

public class LeadFinancialAnalystTests extends BaseTest {

    protected String strLfaPeopleUrl;
    protected String strLfaLanguageUrl;
    protected String strLfaPartnershipUrl;
    protected String strLfaLanguageTranslationUrl;
    protected String strLfaProjectUrl;
    protected String strLfaInternshipUrl;
    protected String strLfaProjectTeamMemberUrl;
    protected String strLfaFundingPartnershipUrl;
    protected String strLfaProjectCreateGoalUrl;
    People lfaPeopleFpmData = new PeopleData().getPeopleData();
    Project lfaAdminData = new ProjectData().getCreateProjectData();
    Budget lfaBudgetData = new BudgetData().getBudgetData();
    Project lfaAdminBudgetData = new ProjectData().getCreateProjectData();
    Partner lfaBudgetPartnerAdminData = new PartnerData().getCreatePartnerData();
    String lfaCreatedBudgetProjectName = lfaAdminBudgetData.getProjectName();
    Project lfaAdminTranslationData = new ProjectData().getCreateTranslationProjectData();
    String lfaCreatedTranslationProjectName = lfaAdminTranslationData.getProjectName();
    String lfaCreatedProjectName = lfaAdminData.getProjectName();
    People lfaPeopleAdminData = new PeopleData().getPeopleData();
    People editLfaPeopleAdminData = new PeopleData().getPeopleData();
    Project lfaEditProjectAdminData = new ProjectData().getUpdateProjectData();
    Project lfaEditProjectBudgetData = new ProjectData().getUpdateProjectData();
    Language lfaLanguageAdminData = new LanguageData().getLanguageData();
    Partner lfaPartnerAdminData = new PartnerData().getCreatePartnerData();
    Partner editLfaPartnerAdminData = new PartnerData().getEditPartnerData();
    Project lfaFpmData = new ProjectData().getCreateProjectData();
    Location lfaLocationData = new LocationData().getLocationData();
    Goal lfaGoalData = new GoalData().getCreateGoalData();
    String lfaLocationName = lfaLocationData.getLocationName();

    public LeadFinancialAnalystTests() throws ParseException, java.text.ParseException, InterruptedException {
    }

    public static final String PROPERTY_TEST_FILE = "src/main/resources/testData/TestFiles.properties";

    @BeforeClass
    public void setupTestData() throws InterruptedException {

        WebDriver driver;
        DriverManager drivermanager;
        drivermanager = new DriverManager();
        driver = drivermanager.setUp("chrome");
        SeleniumHelpers selenium = new SeleniumHelpers(driver);

        Reporter.log("Step 1 - Create Project by admin user, add lead financial analyst and logout that user");
        new LoginFunctions(driver, selenium).loginByGivenUser(adminUser);
        new ProjectFunctions(driver, selenium).createProject(lfaAdminData);
        new ProjectFunctions(driver, selenium).addMemberToProject(lfaUser, lfaAdminData);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 2 - Create Budget Project by admin user and logout that user");
        new ProjectFunctions(driver, selenium).createProject(lfaAdminBudgetData);
        new ProjectFunctions(driver, selenium).addMemberToProject(lfaUser, lfaAdminBudgetData);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 3 - Create TranslationType Project by admin user, add financial analyst and logout that user");
        new ProjectFunctions(driver, selenium).createProject(lfaAdminTranslationData);
        new PartnershipFunctions(driver, selenium).createProjectPartnership(lfaPartnerAdminData ,lfaCreatedTranslationProjectName,adminUser);
        new ProjectFunctions(driver, selenium).addMemberToProject(lfaUser, lfaAdminTranslationData);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(fpmUser);

        Reporter.log("Step 4 - Create Project by project manager and add financial analyst that project");
        new ProjectFunctions(driver, selenium).createProject(lfaFpmData);
        strLfaProjectUrl = driver.getCurrentUrl();
        new ProjectFunctions(driver, selenium).addMemberToProject(lfaUser, lfaFpmData);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 5 - Create People by admin user");
        new PeopleFunctions(driver, selenium).createPeople(lfaPeopleAdminData, adminUser);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(fpmUser);

        Reporter.log("Step 6 - Create People by project manager user");
        new PeopleFunctions(driver, selenium).createPeople(lfaPeopleFpmData, fpmUser);
        strLfaPeopleUrl = driver.getCurrentUrl();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 7 - Create Language by admin user");
        new LanguageFunctions(driver, selenium).createLanguage(lfaLanguageAdminData);
        strLfaLanguageUrl = driver.getCurrentUrl();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 8 - Create Internship engagements by admin user");
        new InternshipEngagementsFunctions(driver, selenium).createProjectInternshipEngagements(adminUser, lfaCreatedBudgetProjectName);
        strLfaInternshipUrl = driver.getCurrentUrl();
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 9 - Create Language engagements by admin user");
        new LanguageFunctions(driver, selenium).createProjectLanguageEngagements(lfaLanguageAdminData, lfaCreatedTranslationProjectName, adminUser);
        strLfaLanguageTranslationUrl = driver.getCurrentUrl();
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 10 - Create Partner by admin user");
        new PartnerFunctions(driver, selenium).createPartner(lfaPartnerAdminData);
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(lfaUser);

        Reporter.log("Step 11 - Create partnership by lead financial analyst user");
        new PartnershipFunctions(driver, selenium).createProjectPartnership(lfaPartnerAdminData, lfaCreatedProjectName, lfaUser);
        strLfaPartnershipUrl = driver.getCurrentUrl();
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(lfaUser);

        Reporter.log("Step 12 - Add project team member with multiple user role");
        new ProjectFunctions(driver, selenium).addMemberToProject(faControllerUser, lfaAdminData);
        strLfaProjectTeamMemberUrl = driver.getCurrentUrl();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 13 - Create Location by admin user");
        new LocationFunctions(driver, selenium).createLocation(lfaLocationData);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 14 - Create Goal by admin user");
        selenium.navigateToPage(strLfaLanguageTranslationUrl);
        new GoalFunctions(driver, selenium).createGoal(lfaGoalData, lfaLanguageAdminData.getLanguageEngagementName(),lfaPartnerAdminData.getOrganizationName());
        strLfaProjectCreateGoalUrl = driver.getCurrentUrl();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 15 - Create Funding partnership by admin user");
        new ProjectFunctions(driver, selenium).editInternshipProjectByName(lfaEditProjectBudgetData, lfaCreatedBudgetProjectName,adminUser);
        new PartnershipFunctions(driver, selenium).createProjectFundingPartnership(lfaBudgetPartnerAdminData, lfaEditProjectBudgetData.getProjectName());
        selenium.hardWait(3);
        strLfaFundingPartnershipUrl = driver.getCurrentUrl();
        drivermanager.tearDown();

    }

    /*Test 1 : Verify that lead financial analyst can view all projects*/
    @Test(priority = 1)
    public void VerifyThatLeadFinancialAnalystCanViewAllProjectsSuccessfully() throws InterruptedException {

        LeftNavPO sideHeader = new LeftNavPO(driver);
        ProjectsListingPO projectsListingPO = new ProjectsListingPO(driver);
        ProjectDetailsPO projectDetailsPO = new ProjectDetailsPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with financial analyst user");
        new LoginFunctions(driver, selenium).loginByGivenUser(lfaUser);

        Reporter.log("Step 2 - Verify that projects is displayed which is created by admin");
        sideHeader.clickOnProjectsMenu();
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());

        if (!projectsListingPO.isProjectPresent(lfaAdminData.getProjectName())) {
            headerPO.searchItemGloballyByName(lfaAdminData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(lfaAdminData.getProjectName());
        Assert.assertEquals(projectDetailsPO.getProjectName(), lfaAdminData.getProjectName(), "Project is not present : " + lfaAdminData.getProjectName());

        Reporter.log("Step 3 - Verify that projects is displayed which is created by FPM user");
        sideHeader.clickOnProjectsMenu();
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());

        if (!projectsListingPO.isProjectPresent(lfaFpmData.getProjectName())) {
            headerPO.searchItemGloballyByName(lfaFpmData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(lfaFpmData.getProjectName());
        Assert.assertEquals(projectDetailsPO.getProjectName(), lfaFpmData.getProjectName(), "Project is not present : " + lfaFpmData.getProjectName());

    }

    /*Test 2 : Verify that lead financial analyst can view all peoples*/
    @Test(priority = 2)
    public void VerifyThatLeadFinancialAnalystCanViewAllPeoplesSuccessfully() throws InterruptedException {

        LeftNavPO sideHeader = new LeftNavPO(driver);
        PeoplesPO peoples = new PeoplesPO(driver);
        PeopleDetailsPO peopleDetails = new PeopleDetailsPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with financial analyst user");
        selenium.navigateToPage(strLfaPeopleUrl);
        new LoginFunctions(driver, selenium).loginByGivenUser(lfaUser);

        Reporter.log("Step 2 - Verify that projects is displayed which is created by admin");
        sideHeader.clickOnPeopleMenu();
        if (!peoples.isPeoplePresent(lfaPeopleAdminData.getPersonName())) {
            headerPO.searchItemGloballyByName(lfaPeopleAdminData.getPersonName());
        }
        peoples.clickOnPeopleName(lfaPeopleAdminData.getPersonName());
        Assert.assertEquals(peopleDetails.getPeopleName().replaceAll("\\s.*", ""), lfaPeopleAdminData.getPersonName(), "Project is not present : " + lfaPeopleAdminData.getPersonName());

        Reporter.log("Step 3 - Verify that projects is displayed which is created by FPM user");
        sideHeader.clickOnPeopleMenu();
        if (!peoples.isPeoplePresent(lfaPeopleFpmData.getPersonName())) {
            headerPO.searchItemGloballyByName(lfaPeopleFpmData.getPersonName());
        }
        peoples.clickOnPeopleName(lfaPeopleFpmData.getPersonName());
        Assert.assertEquals(peopleDetails.getPeopleName().replaceAll("\\s.*", ""), lfaPeopleFpmData.getPersonName(), "Project is not present : " + lfaPeopleFpmData.getPersonName());

    }

    /*Test 3 : Verify that lead financial analyst can edit mine peoples */
    @Test(priority = 3)
    public void VerifyThatLeadFinancialAnalystCanEditMinePeoples() throws InterruptedException {

        PeoplesPO peoples = new PeoplesPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);
        LeftNavPO sideHeader = new LeftNavPO(driver);
        UpdatePeoplePO updatePeople = new UpdatePeoplePO(driver);
        PeopleDetailsPO peopleDetails = new PeopleDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with lead financial analyst user");
        new LoginFunctions(driver, selenium).loginByGivenUser(lfaUser);

        Reporter.log("Step 2 - Search user which have a lead financial analyst role by email and click on people name");
        sideHeader.clickOnPeopleMenu();
        if (!peoples.isPeoplePresent(lfaUser.getEmail())) {
            headerPO.searchItemGloballyByName(lfaUser.getEmail());
        }
        peoples.clickOnPeopleName(lfaUser.getRealFirstName() + " " + lfaUser.getRealLastName());

        Reporter.log("Step 3 - Click on edit icon and edit mine person detail");
        peopleDetails.clickOnEditButton();
        updatePeople.clearAndUpdatePersonDetail(editLfaPeopleAdminData);
        updatePeople.clickOnSubmitButton();

        Reporter.log("Step 4 - Verify updated person details");
        Assert.assertEquals(editLfaPeopleAdminData.getPersonEmail(), peopleDetails.getEmail(), "email doesn't match : ");
        Assert.assertEquals(editLfaPeopleAdminData.getPersonTitle(), peopleDetails.getTitle(), "title doesn't match : ");
        Assert.assertEquals(editLfaPeopleAdminData.getPersonPhone(), peopleDetails.getPhoneNumber(), "phone number doesn't match : ");
        Assert.assertEquals(editLfaPeopleAdminData.getPersonAbout(), peopleDetails.getAbout(), "About doesn't match : ");
        Assert.assertEquals(UserRole.LeadFinancialAnalyst.getRole(), peopleDetails.getRoles(), "Roles doesn't match : ");

        Reporter.log("Step 5 - Again Click on edit icon and reset the people");
        peopleDetails.clickOnEditButton();
        updatePeople.fillFirstnameLastNameAndEmailDetail(lfaUser.getRealFirstName(), lfaUser.getRealLastName(), lfaUser.getEmail());
        updatePeople.clickOnSubmitButton();
        Assert.assertEquals(lfaUser.getRealFirstName() + " " + lfaUser.getRealLastName(), peopleDetails.getPeopleName(), "personName doesn't match : ");
        Assert.assertEquals(lfaUser.getEmail(), peopleDetails.getEmail(), "personEmail doesn't match : ");
    }

    /*Test 4 : Verify that lead financial analyst can view all languages and edit icon is not present*/  //todo bug(#2196)
    @Test(priority = 4)
    public void VerifyThatLeadFinancialAnalystCanViewAllLanguagesAndEditIconIsNotPresent() throws InterruptedException {

        LeftNavPO sideHeader = new LeftNavPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);
        LanguagesPO languages = new LanguagesPO(driver);
        LanguageDetailPO languageDetailPO = new LanguageDetailPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with financial analyst user");
        selenium.navigateToPage(strLfaLanguageUrl);
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).loginByGivenUser(lfaUser);

        Reporter.log("Step 2 - Verify that language is displayed which is created by admin and edit icon is not present");
        sideHeader.clickOnLanguagesMenu();
        if (!languages.isLanguagePresent(lfaLanguageAdminData.getLanguageName())) {
            headerPO.searchItemGloballyByName(lfaLanguageAdminData.getLanguageName());
        }
        languages.clickOnLanguageName(lfaLanguageAdminData.getLanguageName());
        Assert.assertEquals(languageDetailPO.getLanguageName(), lfaLanguageAdminData.getPubliicName(), "Language is not present : " + lfaLanguageAdminData.getPubliicName());
        Assert.assertFalse(languageDetailPO.isEditIconPresent(), "Language edit icon is present : ");

    }

    /*Test 5 : Verify that lead financial analyst can view all partners*/
    @Test(priority = 5)
    public void VerifyThatLeadFinancialAnalystCanViewAllPartnersSuccessfully() throws InterruptedException {

        HeaderPO headerPO = new HeaderPO(driver);
        LeftNavPO sideHeader = new LeftNavPO(driver);
        PartnersListingPO partnerListing = new PartnersListingPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with financial analyst user");
        new LoginFunctions(driver, selenium).loginByGivenUser(lfaUser);
        selenium.hardWait(3);

        Reporter.log("Step 2 - Verify that partner is displayed which is created by admin");
        sideHeader.clickOnPartnersMenu();
        if (!partnerListing.isPartnerPresent(lfaPartnerAdminData.getPartnerName())) {
            headerPO.searchItemGloballyByName(lfaPartnerAdminData.getPartnerName());
        }
        Assert.assertTrue(partnerListing.isPartnerPresent(lfaPartnerAdminData.getPartnerName()), "Partner is not present : " + lfaPartnerAdminData.getPartnerName());

    }

    /*Test 6 : Verify that lead financial analyst can edit all partners details */
    @Test(priority = 6)
    public void VerifyThatLeadFinancialAnalystCanNotEditAllPartnersDetails() {
        PartnerDetailsPO partnerDetailsPO = new PartnerDetailsPO(driver);

        Reporter.log("Step 1 - Login with admin user");
        new LoginFunctions(driver, selenium).loginByGivenUser(adminUser);

        Reporter.log("Step 2 - Edit Partner details");
        new PartnerFunctions(driver, selenium).editProjectPartner(lfaUser, editLfaPartnerAdminData, lfaPartnerAdminData.getPartnerName());

        Reporter.log("Step 3 - verify that partner data is edited successfully");
        Assert.assertTrue(partnerDetailsPO.isGlobalInnovationsClientPresent(), "Global Innovations Client is not present ");
        Assert.assertEquals("Active", partnerDetailsPO.getPartnerStatusByName("Active"), "Status doesn't match");
        Assert.assertEquals(editLfaPartnerAdminData.getPartnerPmcEntityCode(), partnerDetailsPO.getPmcEntityCodeText(1), "entity code doesn't match ");
        Assert.assertEquals(editLfaPartnerAdminData.getPartnerAddress(), partnerDetailsPO.getAddressText(), "address doesn't match ");
        Assert.assertEquals(PartnerType.Impact.toString(), partnerDetailsPO.getTypesText(), "Types doesn't match");
        Assert.assertEquals(lfaUser.getRealFirstName() + " " + lfaUser.getRealLastName(), partnerDetailsPO.getPointOfContactText(), "point of contact doesn't match ");

    }

    /*Test 7 : Verify that lead financial analyst can upload/Edit project files */
    @Test(priority = 7)
    public void VerifyThatLeadFinancialAnalystCanUploadProjectFilesSuccessfully() throws InterruptedException {

        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectDetailsFilesListingPO projectDetailsFilesListingPO = new ProjectDetailsFilesListingPO(driver);
        ProjectDetailsFilesHistoryListingPO projectDetailsFilesHistoryListingPO = new ProjectDetailsFilesHistoryListingPO(driver);
        ProjectDetailsFileListingRenameFileFolderPO projectDetailsFileListingRenameFileFolderPO = new ProjectDetailsFileListingRenameFileFolderPO(driver);
        ProjectDetailsFileListingDeleteFileFolderPO projectDetailsFileListingDeleteFileFolderPO = new ProjectDetailsFileListingDeleteFileFolderPO(driver);

        String textFileName = JavaHelpers.getPropertyValue(PROPERTY_TEST_FILE, "textFile");

        Reporter.log("Step 1 - Entering valid sign in details and login to application");
        new LoginFunctions(driver, selenium).loginByGivenUser(lfaUser);

        Reporter.log("Step 2 -  Login with financial analyst user and upload file in created project");
        new FileFunctions(driver, selenium).uploadFile(lfaCreatedProjectName, textFileName);

        Reporter.log("Step 3 - Verify that project file is uploaded by financial analyst user ");
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
        projectDetailsFileListingRenameFileFolderPO.clearAndRenameFileFolderName(lfaEditProjectAdminData);
        Assert.assertTrue(projectDetailsFilesHistoryListingPO.isFilePresent(lfaEditProjectAdminData.getFileName() + ".txt"), "renamed file is not present : ");
        Assert.assertFalse(projectDetailsFilesHistoryListingPO.isFilePresent(versionFileName), "renamed file is present : ");

        Reporter.log("Step 8 - Click on history kebab menu and delete created file");
        projectDetailsFilesHistoryListingPO.clickOnKebabMenuAndDeleteFileFolder(projectDetailsFilesListingPO.getFirstFileUploadText());
        projectDetailsFileListingDeleteFileFolderPO.clickOnFileDeleteButton();
        Assert.assertFalse(projectDetailsFilesListingPO.isFileFolderPresent(lfaEditProjectAdminData.getFileName()), "deleted file is present : ");

    }

    /*Test 8 : Verify that lead financial analyst can edit project budget */
    @Test(priority = 8)
    public void VerifyThatLeadFinancialAnalystCanEditProjectBudgetSuccessfully() throws InterruptedException {

        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectDetailsBudgetListingPO projectDetailsBudgetListingPO = new ProjectDetailsBudgetListingPO(driver);
        ProjectDetailsPartnershipsListingPO projectDetailsPartnershipsListingPO = new ProjectDetailsPartnershipsListingPO(driver);
        String textFileName = JavaHelpers.getPropertyValue(PROPERTY_TEST_FILE, "textFile");

        Reporter.log("Step 1 - Entering valid sign in details and login to application");
        new LoginFunctions(driver, selenium).loginByGivenUser(lfaUser);

        Reporter.log("Step 2 - Navigate on partnership detail page and click on project link button");
        selenium.navigateToPage(strLfaFundingPartnershipUrl);
        selenium.hardWait(3);
        projectDetailsPartnershipsListingPO.clickOnProjectLinkButton();

        Reporter.log("Step 3 - Click on budget detail button");
        projectDetails.clickOnBudgetButton();

        Reporter.log("Step 4 - Edit budget in created project");
        new BudgetFunctions(driver, selenium).editProjectBudget(textFileName, fodUser, lfaBudgetData);

        Reporter.log("Step 5 - Verify that project budget is edited by lead financial analyst user");
        Assert.assertEquals(projectDetailsBudgetListingPO.getUploadedTemplateFileName(), textFileName, "project budget template file name doesn't match : ");
        Assert.assertEquals(projectDetailsBudgetListingPO.getBudgetAmountText().replace(",", ""), "$" + lfaBudgetData.getBudgetAmount(), "project budget amount doesn't match : ");
    }

    /*Test 9 : Verify that lead financial analyst can add project team member */
    @Test(priority = 9)
    public void VerifyThatLeadFinancialAnalystCanAddProjectTeamMemberSuccessfully() throws InterruptedException {
        ProjectDetailsTeamMembersListingPO projectDetailsTeamMembersListingPO = new ProjectDetailsTeamMembersListingPO(driver);

        Reporter.log("Step 1 -  Login with financial analyst user and add team member in created project");
        new LoginFunctions(driver, selenium).loginByGivenUser(lfaUser);
        selenium.navigateToPage(strLfaProjectTeamMemberUrl);

        selenium.hardWait(3);

        Reporter.log("Step 2 - Verify that project team member is added by financial analyst user");
        Assert.assertEquals(projectDetailsTeamMembersListingPO.getMembersName(faControllerUser.getRealFirstName() + " " + faControllerUser.getRealLastName()), (faControllerUser.getRealFirstName() + " " + faControllerUser.getRealLastName()), "project member name doesn't match : ");
        Assert.assertEquals(projectDetailsTeamMembersListingPO.getMembersRoleByMemberName(faControllerUser.getRealFirstName() + " " + faControllerUser.getRealLastName()), faControllerUser.getUserRoles().get(0), "project member role doesn't match : ");
    }

    /*Test 10 : Verify that lead financial analyst can edit project team member */
    @Test(priority = 10)
    public void VerifyThatLeadFinancialAnalystCanEditProjectTeamMemberSuccessfully() throws InterruptedException {

        ProjectDetailsTeamMembersListingPO projectDetailsTeamMembersListingPO = new ProjectDetailsTeamMembersListingPO(driver);

        Reporter.log("Step 1 -  Login with financial analyst user and edit team member in created project");
        new LoginFunctions(driver, selenium).loginByGivenUser(lfaUser);
        selenium.navigateToPage(strLfaProjectTeamMemberUrl);
        selenium.hardWait(3);
        new TeamMemberFunctions(driver, selenium).editProjectTeamMember(faControllerUser);

        Reporter.log("Step 2 - Verify that project team member is edited by financial analyst user");
        Assert.assertEquals(projectDetailsTeamMembersListingPO.getMembersRoleByMemberName(faControllerUser.getRealFirstName() + " " + faControllerUser.getRealLastName()), faControllerUser.getUserRoles().get(0) + ", " + faControllerUser.getUserRoles().get(1), "project member updated role doesn't match : ");
    }

    /*Test 11 : Verify that lead financial analyst can create project partnership */ //asked question in slack
    @Test(priority = 11)
    public void VerifyThatLeadFinancialAnalystCanCreateProjectPartnershipSuccessfully() throws InterruptedException {

        ProjectDetailsPartnershipsListingPO projectDetailsPartnershipsListingPO = new ProjectDetailsPartnershipsListingPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with financial analyst user");
        new LoginFunctions(driver, selenium).loginByGivenUser(lfaUser);
        selenium.navigateToPage(strLfaPartnershipUrl);

        selenium.hardWait(3);

        Reporter.log("Step 2 - Verify that project partnership is created by financial analyst user");
        Assert.assertEquals(projectDetailsPartnershipsListingPO.getPartnershipName(lfaPartnerAdminData.getOrganizationName()), lfaPartnerAdminData.getOrganizationName(), "project partnership doesn't match : ");
    }

    /*Test 12 : Verify that lead financial analyst can edit project partnership */
    @Test(priority = 12)
    public void VerifyThatLeadFinancialAnalystCanEditProjectPartnershipSuccessfully() throws InterruptedException, java.text.ParseException {

        ProjectDetailsPartnershipsEditPO projectDetailsPartnershipsEditPO = new ProjectDetailsPartnershipsEditPO(driver);
        ProjectDetailsPartnershipsListingPO projectDetailsPartnershipsListingPO = new ProjectDetailsPartnershipsListingPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with financial analyst user");
        new LoginFunctions(driver, selenium).loginByGivenUser(lfaUser);
        selenium.navigateToPage(strLfaPartnershipUrl);
        new PartnershipFunctions(driver, selenium).editProjectPartnership(lfaEditProjectAdminData);

        Reporter.log("Step 2 - Click on partnership submit button");
        projectDetailsPartnershipsEditPO.clickOnPartnershipSubmitButton();

        Assert.assertEquals(projectDetailsPartnershipsListingPO.getTypeText(lfaPartnerAdminData.getOrganizationName()), PartnerType.Managing.getPartnerType(), "partner types doesn't match");
        Assert.assertEquals(projectDetailsPartnershipsListingPO.getFinancialReportingTypeText(lfaPartnerAdminData.getOrganizationName()), PartnershipFinancialReportingTypes.Funded.getPartnershipFinancialReportingTypes(), "partnership financial reporting types doesn't match");
        Assert.assertEquals(projectDetailsPartnershipsListingPO.getAgreementStatusText(lfaPartnerAdminData.getOrganizationName()), PartnershipStatus.AwaitingSignature.getPartnerShipStatus(), " partnership agreement status doesn't match");
        Assert.assertEquals(projectDetailsPartnershipsListingPO.getMouStatusText(lfaPartnerAdminData.getOrganizationName()), PartnershipStatus.NotAttached.getPartnerShipStatus(), " partnership mou status doesn't match");
        Assert.assertEquals(projectDetailsPartnershipsListingPO.getMouDateRangeText(lfaPartnerAdminData.getOrganizationName()), new JavaHelpers().changeDateFormat(lfaEditProjectAdminData.getStartDate(), "MM/dd/yyyy", "M/d/yyyy") + " - " + new JavaHelpers().changeDateFormat(lfaEditProjectAdminData.getEndDate(), "MM/dd/yyyy", "M/d/yyyy"), "project start end date doesn't match : ");
        Assert.assertTrue(projectDetailsPartnershipsListingPO.isCreatedPartnershipPrimary(lfaPartnerAdminData.getOrganizationName()), "Created partnership is not primary");
    }

    /*Test 13 : Verify that lead financial analyst can not create project internship engagements */
    @Test(priority = 13)
    public void VerifyThatLeadFinancialAnalystCanNotCreateProjectInternshipEngagementsSuccessfully() throws InterruptedException {

        HeaderPO headerPO = new HeaderPO(driver);
        LeftNavPO sideHeader = new LeftNavPO(driver);
        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectsListingPO projectsListingPO = new ProjectsListingPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with financial analyst user");
        new LoginFunctions(driver, selenium).loginByGivenUser(lfaUser);

        Reporter.log("Step 2 - Search the engagement project which is created by admin user");
        sideHeader.clickOnProjectsMenu();
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());

        if (!projectsListingPO.isProjectPresent(lfaAdminData.getProjectName())) {
            headerPO.searchItemGloballyByName(lfaAdminData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(lfaAdminData.getProjectName());

        Reporter.log("Step 3 - Verify the financial analyst can't create internship engagement"); //todo bug(#762) internship global R access
        Assert.assertFalse(projectDetails.isProjectInternshipEngagementAddButtonPresent(), "Project internship engagement add button is present : ");
    }

    /*Test 14 : Verify that lead financial analyst can not edit project internship engagements except disbursement complete date*/ //todo bug(#2197)
    @Test(priority = 14)
    public void VerifyThatLeadFinancialAnalystCanNotEditProjectInternshipEngagementsExceptDisbursementCompleteDate() throws InterruptedException {

        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectInternshipDetailsPO projectInternshipDetailsPO = new ProjectInternshipDetailsPO(driver);
        ProjectInternshipDetailsUpdateCountryOfOriginPO projectInternshipDetailsUpdateCountryOfOriginPO = new ProjectInternshipDetailsUpdateCountryOfOriginPO(driver);
        ProjectInternshipDetailsUpdateInternPositionPO projectInternshipDetailsUpdateInternPositionPO = new ProjectInternshipDetailsUpdateInternPositionPO(driver);
        ProjectEngagementDetailsUpdateStartEndDatePO projectEngagementDetailsUpdateStartEndDatePO = new ProjectEngagementDetailsUpdateStartEndDatePO(driver);
        ProjectInternshipLanguageDetailsUpdateStatusPO projectInternshipLanguageDetailsUpdateStatusPO = new ProjectInternshipLanguageDetailsUpdateStatusPO(driver);
        ProjectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO projectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO = new ProjectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO(driver);

        Reporter.log("Step 1 -  Login with financial analyst user and navigate in internship url");
        new LoginFunctions(driver, selenium).loginByGivenUser(lfaUser);
        selenium.navigateToPage(strLfaInternshipUrl);

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

        Reporter.log("Step 13 - Try to edit internship add growth plan");
        Assert.assertFalse(projectInternshipDetailsPO.isGrowthPlanButtonEnabled(), "Internship growth plan button is enabled : ");

        Reporter.log("Step 14 - Try to edit internship mentor");
        Assert.assertFalse(projectInternshipDetailsPO.isInternshipAddMentorButtonEnabled(), "Internship add mentor button is enabled : ");

        Reporter.log("Step 15 - Try to edit internship actual estimated date");
        String disableEditButtonColor = Constants.disableEditButtonColorCode;
        Assert.assertEquals(disableEditButtonColor, projectInternshipDetailsPO.getEditButtonColor(), "edit button color doesn't match : ");

    }

//    /*Test 15 : Verify that lead financial analyst can read project location */
//    @Test(priority = 15)
//    public void VerifyThatLeadFinancialAnalystCanReadProjectLocation() throws InterruptedException {
//
//        HeaderPO headerPO = new HeaderPO(driver);
//        LocationsListingPO locationsListingPO = new LocationsListingPO(driver);
//        LocationDetailsPO locationDetailsPO = new LocationDetailsPO(driver);
//
//        Reporter.log("Step 1 - Navigate to url and login with financial analyst user");
//        new LoginFunctions(driver, selenium).loginByGivenUser(lfaUser);
//
//        Reporter.log("Step 2 - Enter location name and click on view location button");
//        headerPO.searchItemGloballyByName(lfaLocationName);
//        locationsListingPO.clickOnFirstViewLocationButton();
//
//        Reporter.log("Step 3 - Verify the location edit button is not present");
//        Assert.assertFalse(locationDetailsPO.isLocationEditButtonPresent(), "Location edit button is present:");
//
//    }

    /*Test 16 : Verify that lead financial analyst can not create language Project and location */
    @Test(priority = 16)
    public void VerifyThatLeadFinancialAnalystCanNotCreateLanguagePartnerAndLocation() throws InterruptedException {

        LeftNavPO leftNavPO = new LeftNavPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with financial analyst user");
        new LoginFunctions(driver, selenium).loginByGivenUser(lfaUser);

        Reporter.log("Step 2 - Enter location name and click on view location button");
        leftNavPO.clickOnCreateNewItemMenuButton();

        Reporter.log("Step 3 - Verify language location and Project option is not present");
        Assert.assertFalse(leftNavPO.isMenuItemPresent(CreateItemTypes.Location.toString()), "Location menu item is present:");
        Assert.assertFalse(leftNavPO.isMenuItemPresent(CreateItemTypes.Language.toString()), "Language menu item is present:");
        Assert.assertFalse(leftNavPO.isMenuItemPresent(CreateItemTypes.Project.toString()), "Partner menu item is present:");

        Reporter.log("Step 4 - Verify project person option is present");
        Assert.assertTrue(leftNavPO.isMenuItemPresent(CreateItemTypes.Partner.toString()), "Project menu item is not present:");
        Assert.assertTrue(leftNavPO.isMenuItemPresent(CreateItemTypes.Person.toString()), "Person menu item is not present:");

    }

    /*Test 17 : Verify that lead financial analyst can not create project language engagements */
    @Test(priority = 17)
    public void VerifyThatLeadFinancialAnalystCanNotCreateProjectLanguageEngagementsSuccessfully() throws InterruptedException {

        HeaderPO headerPO = new HeaderPO(driver);
        LeftNavPO sideHeader = new LeftNavPO(driver);
        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectsListingPO projectsListingPO = new ProjectsListingPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with financial analyst user");
        new LoginFunctions(driver, selenium).loginByGivenUser(lfaUser);

        Reporter.log("Step 2 - Search the language engagement project which is created by admin user");
        sideHeader.clickOnProjectsMenu();
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());

        if (!projectsListingPO.isProjectPresent(lfaAdminTranslationData.getProjectName())) {
            headerPO.searchItemGloballyByName(lfaAdminTranslationData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(lfaAdminTranslationData.getProjectName());

        Reporter.log("Step 3 - Verify the financial analyst can't create internship engagement"); //Todo bug(#762) language engagement global R access
        Assert.assertFalse(projectDetails.isProjectLanguageEngagementAddButtonPresent(), "Project language engagement add button is present : ");
    }

    /*Test 18 : Verify that lead financial analyst can not edit project language engagements except disbursement complete date */ // todo bug(#2197)
    @Test(priority = 18)
    public void VerifyThatLeadFinancialAnalystCanNotEditProjectLanguageEngagementsExceptDisbursementCompleteDate() throws InterruptedException {

        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectLanguageEngagementDetailsPO projectLanguageEngagementDetailsPO = new ProjectLanguageEngagementDetailsPO(driver);
        ProjectEngagementDetailsUpdateStartEndDatePO projectEngagementDetailsUpdateStartEndDatePO = new ProjectEngagementDetailsUpdateStartEndDatePO(driver);
        ProjectInternshipLanguageDetailsUpdateStatusPO projectInternshipLanguageDetailsUpdateStatusPO = new ProjectInternshipLanguageDetailsUpdateStatusPO(driver);
        ProjectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO projectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO = new ProjectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO(driver);

        Reporter.log("Step 1 -  Login with financial analyst user and navigate in language engagement url");
        new LoginFunctions(driver, selenium).loginByGivenUser(lfaUser);
        selenium.navigateToPage(strLfaLanguageTranslationUrl);

        Reporter.log("Step 2 - Click on view details button");
        projectDetails.clickOnViewDetailsLanguageEngagementButton(lfaLanguageAdminData.getLanguageEngagementName());

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

        Reporter.log("Step 7 - Try to edit language engagement translation complete date");
        Assert.assertFalse(projectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO.isTranslationCompleteDateTextBoxEnabled(), "Language engagement intern position textBox is enabled : ");

        Reporter.log("Step 8 - Try to edit language engagement actual estimated date");
        Assert.assertFalse(projectLanguageEngagementDetailsPO.isEstimatedDateTextBoxEnabled(), "Language engagement estimated date textBox is enabled : ");
        Assert.assertFalse(projectLanguageEngagementDetailsPO.isActualDateTextBoxEnabled(), "Language engagement actual date textBox is enabled : ");

    }

    /*Test 19 : Verify that lead financial analyst can not edit goals */
    @Test(priority = 19)
    public void VerifyThatLeadFinancialAnalystCanNotEditProjectGoalsSuccessfully() throws InterruptedException {

        CreateGoalPO createGoalPO = new CreateGoalPO(driver);
        ProjectLanguageEngagementDetailsPO projectLanguageEngagementDetailsPO = new ProjectLanguageEngagementDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with staff member user");
        new LoginFunctions(driver, selenium).loginByGivenUser(stfUser);
        selenium.navigateToPage(strLfaProjectCreateGoalUrl);

        Reporter.log("Step 2 - Click on Created goal");
        projectLanguageEngagementDetailsPO.clickOnGoalCard();

        Reporter.log("Step 3 - Click on goal edit button");
        projectLanguageEngagementDetailsPO.clickEditGoalButton();

        Reporter.log("Step 4 - Verify that staff member can read created project goals");
        String disableGoalColor = Constants.disableGrayColorCode;
        Assert.assertEquals(disableGoalColor, createGoalPO.getDisabledEditGoalDetailsColor(), "Goal detail color doesn't match : ");
    }

    /*Test 20 : Verify that lead financial analyst can edit projects */
    @Test(priority = 20)
    public void VerifyThatLeadFinancialAnalystCanEditProjectsSuccessfully() throws InterruptedException, java.text.ParseException {

        HeaderPO headerPO = new HeaderPO(driver);
        LeftNavPO sideHeader = new LeftNavPO(driver);
        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectsListingPO projectsListingPO = new ProjectsListingPO(driver);
        ProjectDetailsUpdateStepsPO projectDetailsUpdateStepsPO = new ProjectDetailsUpdateStepsPO(driver);
        ProjectDetailsPartnershipsListingPO projectDetailsPartnershipsListingPO = new ProjectDetailsPartnershipsListingPO(driver);
        ProjectDetailsUpdateEstimatedSubmissionDatePO projectDetailsUpdateEstimatedSubmissionDatePO = new ProjectDetailsUpdateEstimatedSubmissionDatePO(driver);
        ProjectDetailsUpdateLocationAndFieldRegionPO projectDetailsUpdateLocationAndFieldRegionPO = new ProjectDetailsUpdateLocationAndFieldRegionPO(driver);
        SoftAssert softAssertion = new SoftAssert();

        Reporter.log("Step 1 -  Login with lead financial analyst user");
        new LoginFunctions(driver, selenium).loginByGivenUser(lfaUser);

        Reporter.log("Step 2 - Verify that project is displayed which is edited by lead financial analyst user ");
        sideHeader.clickOnProjectsMenu();
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());

        Reporter.log("Step 3 - Navigate on partnership detail page and click on project link button");
        selenium.navigateToPage(strLfaFundingPartnershipUrl);
        selenium.hardWait(3);
        projectDetailsPartnershipsListingPO.clickOnProjectLinkButton();

        Reporter.log("Step 4 - Click on project status button and try to edit with lead financial analyst user ");
        projectDetails.clickOnProjectStepByName(ProjectStep.PendingConceptApproval.getStep());

        Reporter.log("Step 5 - Verify that lead financial analyst can't edit status");
        softAssertion.assertEquals(Constants.projectStatusErrorMessage, projectDetailsUpdateStepsPO.getProjectStatusError(), "project status error text doesn't match");
        projectDetailsUpdateStepsPO.clickProjectStatusCloseButton();

        Reporter.log("Step 6 - Click on project location and field region button and try to edit with lead financial analyst user ");
        projectDetails.clickProjectLocationAndFieldRegionButton();

        Reporter.log("Step 7 - Verify that lead financial analyst can't edit field region and location");
        softAssertion.assertFalse(projectDetailsUpdateLocationAndFieldRegionPO.isLocationTextBoxEnabled(), "project location textBox is enabled : ");
        softAssertion.assertFalse(projectDetailsUpdateLocationAndFieldRegionPO.isFieldRegionTextBoxEnabled(), "project field region textBox is enabled : ");

        Reporter.log("Step 8 - Click on location field region close button");
        projectDetailsUpdateLocationAndFieldRegionPO.clickOnLocationFieldRegionCloseButton();

        Reporter.log("Step 9 - Verify that lead financial analyst can't edit estimated submission date");
        projectDetails.clickProjectEstimatedSubmissionButton();
        softAssertion.assertFalse(projectDetailsUpdateEstimatedSubmissionDatePO.isEstimatedSubmissionDateTextBoxEnabled(), "project estimated date textBox is enabled : ");

        Reporter.log("Step 10 - Click on estimated submission date close button");
        projectDetailsUpdateEstimatedSubmissionDatePO.clickOnEstimatedSubmissionDateCloseButton();

        softAssertion.assertFalse(projectDetails.isEditIconPresent(), "Project edit icon is present : ");
        softAssertion.assertEquals(Sensitivity.Low.toString(), projectDetails.getProjectSensitivity(), "project sensitivity doesn't match : ");
        softAssertion.assertEquals(projectDetails.getProjectStartEndDate(), new JavaHelpers().changeDateFormat(lfaEditProjectAdminData.getStartDate(), "MM/dd/yyyy", "M/d/yyyy") + "\n" + " - " + "\n" + new JavaHelpers().changeDateFormat(lfaEditProjectAdminData.getEndDate(), "MM/dd/yyyy", "M/d/yyyy"), "project start end date doesn't match : ");

    }
}





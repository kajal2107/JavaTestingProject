package cord.projects.rolePermissionFlow;

import base.BaseTest;
import datafactory.*;
import dataobjects.*;
import enums.menu.CreateItemTypes;
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

    public class FinancialAnalystTests extends BaseTest {

    protected String strFaPeopleUrl;
    protected String strFaLanguageUrl;
    protected String strFaPartnershipUrl;
    protected String strFaLanguageTranslationUrl;
    protected String strFaProjectUrl;
    protected String strFaInternshipUrl;
    protected String strFaProjectTeamMemberUrl;
    protected String strFaFundingPartnershipUrl;
    protected String strFaProjectCreateGoalUrl;
    People faPeopleFpmData = new PeopleData().getPeopleData();
    Project faAdminData = new ProjectData().getCreateProjectData();
    Budget faBudgetData = new BudgetData().getBudgetData();
    Project faAdminBudgetData = new ProjectData().getCreateProjectData();
    Partner faBudgetPartnerAdminData = new PartnerData().getCreatePartnerData();
    String faCreatedBudgetProjectName = faAdminBudgetData.getProjectName();
    Project faAdminTranslationData = new ProjectData().getCreateTranslationProjectData();
    String faCreatedTranslationProjectName = faAdminTranslationData.getProjectName();
    String faCreatedProjectName = faAdminData.getProjectName();
    People faPeopleAdminData = new PeopleData().getPeopleData();
    People editFaPeopleAdminData = new PeopleData().getPeopleData();
    Project faEditProjectAdminData = new ProjectData().getUpdateProjectData();
    Project faEditProjectBudgetData = new ProjectData().getUpdateProjectData();
    Language faLanguageAdminData = new LanguageData().getLanguageData();
    Partner faPartnerAdminData = new PartnerData().getCreatePartnerData();
    Project faFpmData = new ProjectData().getCreateProjectData();
    Location faLocationData = new LocationData().getLocationData();
    Goal faGoalData = new GoalData().getCreateGoalData();
    String faLocationName = faLocationData.getLocationName();

    public FinancialAnalystTests() throws ParseException, java.text.ParseException, InterruptedException {
    }

    public static final String PROPERTY_TEST_FILE = "src/main/resources/testData/TestFiles.properties";

    @BeforeClass
    public void setupTestData() throws InterruptedException {

        WebDriver driver;
        DriverManager drivermanager;
        drivermanager = new DriverManager();
        driver = drivermanager.setUp("chrome");
        SeleniumHelpers selenium = new SeleniumHelpers(driver);

        Reporter.log("Step 1 - Create Project by admin user, add financial analyst and logout that user");
        new LoginFunctions(driver, selenium).loginByGivenUser(adminUser);
        new ProjectFunctions(driver, selenium).createProject(faAdminData);
        new ProjectFunctions(driver, selenium).addMemberToProject(faUser, faAdminData);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 2 - Create Budget Project by admin user and logout that user");
        new ProjectFunctions(driver, selenium).createProject(faAdminBudgetData);
        new ProjectFunctions(driver, selenium).addMemberToProject(faUser, faAdminBudgetData);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 3 - Create TranslationType Project by admin user, add financial analyst and logout that user");
        new ProjectFunctions(driver, selenium).createProject(faAdminTranslationData);
        new ProjectFunctions(driver, selenium).addMemberToProject(faUser, faAdminTranslationData);
        new PartnershipFunctions(driver, selenium).createProjectPartnership(faPartnerAdminData ,faCreatedTranslationProjectName,adminUser);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(fpmUser);

        Reporter.log("Step 4 - Create Project by project manager and add financial analyst that project");
        new ProjectFunctions(driver, selenium).createProject(faFpmData);
        strFaProjectUrl = driver.getCurrentUrl();
        new ProjectFunctions(driver, selenium).addMemberToProject(faUser, faFpmData);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 5 - Create People by admin user");
        new PeopleFunctions(driver, selenium).createPeople(faPeopleAdminData, adminUser);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(fpmUser);

        Reporter.log("Step 6 - Create People by project manager user");
        new PeopleFunctions(driver, selenium).createPeople(faPeopleFpmData, fpmUser);
        strFaPeopleUrl = driver.getCurrentUrl();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 7 - Create Language by admin user");
        new LanguageFunctions(driver, selenium).createLanguage(faLanguageAdminData);
        strFaLanguageUrl = driver.getCurrentUrl();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 8 - Create Internship engagements by admin user");
        new InternshipEngagementsFunctions(driver, selenium).createProjectInternshipEngagements(adminUser, faCreatedBudgetProjectName);
        strFaInternshipUrl = driver.getCurrentUrl();
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 9 - Create Language engagements by admin user");
        new LanguageFunctions(driver, selenium).createProjectLanguageEngagements(faLanguageAdminData, faCreatedTranslationProjectName, adminUser);
        strFaLanguageTranslationUrl = driver.getCurrentUrl();
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 10 - Create Partner by admin user");
        new PartnerFunctions(driver, selenium).createPartner(faPartnerAdminData);
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 11 - Create partnership by admin user");
        new PartnershipFunctions(driver, selenium).createProjectPartnership(faPartnerAdminData, faCreatedProjectName, adminUser);
        strFaPartnershipUrl = driver.getCurrentUrl();
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(faUser);

        Reporter.log("Step 12 - Add project team member with multiple user role");
        new ProjectFunctions(driver, selenium).addMemberToProject(faControllerUser, faAdminData);
        strFaProjectTeamMemberUrl = driver.getCurrentUrl();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 13 - Create Location by admin user");
        new LocationFunctions(driver, selenium).createLocation(faLocationData);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 14 - Create Goal by admin user");
        selenium.navigateToPage(strFaLanguageTranslationUrl);
        new GoalFunctions(driver, selenium).createGoal(faGoalData, faLanguageAdminData.getLanguageEngagementName(),faPartnerAdminData.getOrganizationName());
        strFaProjectCreateGoalUrl = driver.getCurrentUrl();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 15 - Create Funding partnership by admin user");
        new ProjectFunctions(driver, selenium).editInternshipProjectByName(faEditProjectBudgetData, faCreatedBudgetProjectName,adminUser);
        new PartnershipFunctions(driver, selenium).createProjectFundingPartnership(faBudgetPartnerAdminData, faEditProjectBudgetData.getProjectName());
        selenium.hardWait(3);
        strFaFundingPartnershipUrl = driver.getCurrentUrl();
        drivermanager.tearDown();

    }

    /*Test 1 : Verify that financial analyst can view all projects*/
    @Test(priority = 1)
    public void VerifyFinancialAnalystCanViewAllProjectsSuccessfully() throws InterruptedException {

        LeftNavPO sideHeader = new LeftNavPO(driver);
        ProjectsListingPO projectsListingPO = new ProjectsListingPO(driver);
        ProjectDetailsPO projectDetailsPO = new ProjectDetailsPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with financial analyst user");
        new LoginFunctions(driver, selenium).loginByGivenUser(faUser);

        Reporter.log("Step 2 - Verify that projects is displayed which is created by admin");
        sideHeader.clickOnProjectsMenu();
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());

        if (!projectsListingPO.isProjectPresent(faAdminData.getProjectName())) {
            headerPO.searchItemGloballyByName(faAdminData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(faAdminData.getProjectName());
        Assert.assertEquals(projectDetailsPO.getProjectName(), faAdminData.getProjectName(), "Project is not present : " + faAdminData.getProjectName());

        Reporter.log("Step 3 - Verify that projects is displayed which is created by FPM user");
        sideHeader.clickOnProjectsMenu();
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());

        if (!projectsListingPO.isProjectPresent(faFpmData.getProjectName())) {
            headerPO.searchItemGloballyByName(faFpmData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(faFpmData.getProjectName());
        Assert.assertEquals(projectDetailsPO.getProjectName(), faFpmData.getProjectName(), "Project is not present : " + faFpmData.getProjectName());

    }

    /*Test 2 : Verify that financial analyst can view all peoples*/
    @Test(priority = 2)
    public void VerifyFinancialAnalystCanViewAllPeoplesSuccessfully() throws InterruptedException {

        LeftNavPO sideHeader = new LeftNavPO(driver);
        PeoplesPO peoples = new PeoplesPO(driver);
        PeopleDetailsPO peopleDetails = new PeopleDetailsPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with financial analyst user");
        selenium.navigateToPage(strFaPeopleUrl);
        new LoginFunctions(driver, selenium).loginByGivenUser(faUser);

        Reporter.log("Step 2 - Verify that projects is displayed which is created by admin");
        sideHeader.clickOnPeopleMenu();
        if (!peoples.isPeoplePresent(faPeopleAdminData.getPersonName())) {
            headerPO.searchItemGloballyByName(faPeopleAdminData.getPersonName());
        }
        peoples.clickOnPeopleName(faPeopleAdminData.getPersonName());
        Assert.assertEquals(peopleDetails.getPeopleName().replaceAll("\\s.*", ""), faPeopleAdminData.getPersonName(), "Project is not present : " + faPeopleAdminData.getPersonName());

        Reporter.log("Step 3 - Verify that projects is displayed which is created by FPM user");
        sideHeader.clickOnPeopleMenu();
        if (!peoples.isPeoplePresent(faPeopleFpmData.getPersonName())) {
            headerPO.searchItemGloballyByName(faPeopleFpmData.getPersonName());
        }
        peoples.clickOnPeopleName(faPeopleFpmData.getPersonName());
        Assert.assertEquals(peopleDetails.getPeopleName().replaceAll("\\s.*", ""), faPeopleFpmData.getPersonName(), "Project is not present : " + faPeopleFpmData.getPersonName());

    }

    /*Test 3 : Verify that financial analyst can edit mine peoples */
    @Test(priority = 3)
    public void VerifyThatFinancialAnalystCanEditMinePeoples() throws InterruptedException {

        PeoplesPO peoples = new PeoplesPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);
        LeftNavPO sideHeader = new LeftNavPO(driver);
        UpdatePeoplePO updatePeople = new UpdatePeoplePO(driver);
        PeopleDetailsPO peopleDetails = new PeopleDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with financial analyst user");
        new LoginFunctions(driver, selenium).loginByGivenUser(faUser);

        Reporter.log("Step 2 - Search user which have a financial analyst role by email and click on people name");
        sideHeader.clickOnPeopleMenu();
        if (!peoples.isPeoplePresent(faUser.getEmail())) {
            headerPO.searchItemGloballyByName(faUser.getEmail());
        }
        peoples.clickOnPeopleName(faUser.getRealFirstName() + " " + faUser.getRealLastName());

        Reporter.log("Step 3 - Click on edit icon and edit mine person detail");
        peopleDetails.clickOnEditButton();
        updatePeople.clearAndUpdatePersonDetail(editFaPeopleAdminData);
        updatePeople.clickOnSubmitButton();

        Reporter.log("Step 4 - Verify updated person details");
        Assert.assertEquals(editFaPeopleAdminData.getPersonEmail(), peopleDetails.getEmail(), "email doesn't match : ");
        Assert.assertEquals(editFaPeopleAdminData.getPersonTitle(), peopleDetails.getTitle(), "title doesn't match : ");
        Assert.assertEquals(editFaPeopleAdminData.getPersonPhone(), peopleDetails.getPhoneNumber(), "phone number doesn't match : ");
        Assert.assertEquals(editFaPeopleAdminData.getPersonAbout(), peopleDetails.getAbout(), "About doesn't match : ");
        Assert.assertEquals(UserRole.FinancialAnalyst.getRole(), peopleDetails.getRoles(), "Roles doesn't match : ");

        Reporter.log("Step 5 - Again Click on edit icon and reset the people");
        peopleDetails.clickOnEditButton();
        updatePeople.fillFirstnameLastNameAndEmailDetail(faUser.getRealFirstName(), faUser.getRealLastName(), faUser.getEmail());
        updatePeople.clickOnSubmitButton();
        Assert.assertEquals(faUser.getRealFirstName() + " " + faUser.getRealLastName(), peopleDetails.getPeopleName(), "personName doesn't match : ");
        Assert.assertEquals(faUser.getEmail(), peopleDetails.getEmail(), "personEmail doesn't match : ");
    }

    /*Test 4 : Verify that financial analyst can view all languages and edit icon is not present*/
    @Test(priority = 4)
    public void VerifyFinancialAnalystCanViewAllLanguagesAndEditIconIsNotPresent() throws InterruptedException {

        LeftNavPO sideHeader = new LeftNavPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);
        LanguagesPO languages = new LanguagesPO(driver);
        LanguageDetailPO languageDetailPO = new LanguageDetailPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with financial analyst user");
        selenium.navigateToPage(strFaLanguageUrl);
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).loginByGivenUser(faUser);

        Reporter.log("Step 2 - Verify that language is displayed which is created by admin and edit icon is not present");
        sideHeader.clickOnLanguagesMenu();
        if (!languages.isLanguagePresent(faLanguageAdminData.getLanguageName())) {
            headerPO.searchItemGloballyByName(faLanguageAdminData.getLanguageName());
        }
        languages.clickOnLanguageName(faLanguageAdminData.getLanguageName());
        Assert.assertEquals(languageDetailPO.getLanguageName(), faLanguageAdminData.getPubliicName(), "Language is not present : " + faLanguageAdminData.getPubliicName());
        Assert.assertFalse(languageDetailPO.isEditIconPresent(), "Language edit icon is present : ");

    }

    /*Test 5 : Verify that financial analyst can view all partners*/
    @Test(priority = 5)
    public void VerifyFinancialAnalystCanViewAllPartnersSuccessfully() throws InterruptedException {  // todo bug(#2191)

        HeaderPO headerPO = new HeaderPO(driver);
        LeftNavPO sideHeader = new LeftNavPO(driver);
        PartnersListingPO partnerListing = new PartnersListingPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with financial analyst user");
        new LoginFunctions(driver, selenium).loginByGivenUser(faUser);
        selenium.hardWait(3);

        Reporter.log("Step 2 - Verify that partner is displayed which is created by admin");
        sideHeader.clickOnPartnersMenu();
        if (!partnerListing.isPartnerPresent(faPartnerAdminData.getPartnerName())) {
            headerPO.searchItemGloballyByName(faPartnerAdminData.getPartnerName());
        }
        Assert.assertTrue(partnerListing.isPartnerPresent(faPartnerAdminData.getPartnerName()), "Partner is not present : " + faPartnerAdminData.getPartnerName());

    }

    /*Test 6 : Verify that financial analyst can't edit all partners details */  // todo bug(#2191)
    @Test(priority = 6)
    public void VerifyThatFinancialAnalystCanNotEditAllPartnersDetails() throws InterruptedException {

        HeaderPO headerPO = new HeaderPO(driver);
        LeftNavPO sideHeader = new LeftNavPO(driver);
        PartnersListingPO partnerListing = new PartnersListingPO(driver);
        PartnerDetailsPO partnerDetailsPO = new PartnerDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with financial analyst user");
        new LoginFunctions(driver, selenium).loginByGivenUser(faUser);

        Reporter.log("Step 2 - Search the partner from listing and click on partner name");
        sideHeader.clickOnPartnersMenu();
        if (!partnerListing.isPartnerPresent(faPartnerAdminData.getPartnerName())) {
            headerPO.searchItemGloballyByName(faPartnerAdminData.getPartnerName());
        }
        partnerListing.clickOnPartnerName(faPartnerAdminData.getPartnerName());

        Reporter.log("Step 3 - Verify that partner is not edited by financial analyst user ");
        Assert.assertFalse(partnerDetailsPO.isEditIconPresent(), "Partner edit icon is present : ");
        Assert.assertFalse(partnerDetailsPO.isInActiveButtonEnabled(), "Partner active inactive button is enabled : ");
        Assert.assertFalse(partnerDetailsPO.isEnterPmcEntityCodeButtonEnabled(), "Partner pmc entity code button is enabled : ");
        Assert.assertFalse(partnerDetailsPO.isPartnerTypesEditButtonEnabled(), "Partner types edit button is enabled : ");
        Assert.assertFalse(partnerDetailsPO.isPartnerAddressEditButtonEnabled(), "Partner address edit button is enabled: ");
        Assert.assertFalse(partnerDetailsPO.isPartnerPointOfContactButtonEnabled(), "Partner point of contact button is enabled : ");

    }

    /*Test 7 : Verify that financial analyst can upload/Edit project files */
    @Test(priority = 7)
    public void VerifyThatFinancialAnalystCanUploadProjectFilesSuccessfully() throws InterruptedException {

        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectDetailsFilesListingPO projectDetailsFilesListingPO = new ProjectDetailsFilesListingPO(driver);
        ProjectDetailsFilesHistoryListingPO projectDetailsFilesHistoryListingPO = new ProjectDetailsFilesHistoryListingPO(driver);
        ProjectDetailsFileListingRenameFileFolderPO projectDetailsFileListingRenameFileFolderPO = new ProjectDetailsFileListingRenameFileFolderPO(driver);
        ProjectDetailsFileListingDeleteFileFolderPO projectDetailsFileListingDeleteFileFolderPO = new ProjectDetailsFileListingDeleteFileFolderPO(driver);

        String textFileName = JavaHelpers.getPropertyValue(PROPERTY_TEST_FILE, "textFile");

        Reporter.log("Step 1 - Entering valid sign in details and login to application");
        new LoginFunctions(driver, selenium).loginByGivenUser(faUser);

        Reporter.log("Step 2 -  Login with financial analyst user and upload file in created project");
        new FileFunctions(driver, selenium).uploadFile(faCreatedProjectName, textFileName);

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
        projectDetailsFilesListingPO.clickOnKebabMenuAndViewFileHistory(projectDetailsFilesListingPO.getLastFileUploadText().replace(".txt", ""));
        Assert.assertTrue(projectDetailsFilesHistoryListingPO.isFilePresent(versionFileName), "file is not present : ");

        Reporter.log("Step 7 - Click on history kebab menu and rename the file");
        projectDetailsFilesHistoryListingPO.clickOnHistoryKebabMenuAndRenameFileFolder(projectDetailsFilesListingPO.getLastFileUploadText());
        projectDetailsFileListingRenameFileFolderPO.clearAndRenameFileFolderName(faEditProjectAdminData);
        Assert.assertTrue(projectDetailsFilesHistoryListingPO.isFilePresent(faEditProjectAdminData.getFileName() + ".txt"), "renamed file is not present : ");
        Assert.assertFalse(projectDetailsFilesHistoryListingPO.isFilePresent(versionFileName), "renamed file is present : ");

        Reporter.log("Step 8 - Click on history kebab menu and delete created file");
        projectDetailsFilesHistoryListingPO.clickOnKebabMenuAndDeleteFileFolder(projectDetailsFilesListingPO.getFirstFileUploadText());
        projectDetailsFileListingDeleteFileFolderPO.clickOnFileDeleteButton();
        Assert.assertFalse(projectDetailsFilesListingPO.isFileFolderPresent(faEditProjectAdminData.getFileName()), "deleted file is present : ");

    }

    /*Test 8 : Verify that financial analyst can edit project budget */
    @Test(priority = 8)
    public void VerifyThatFinancialAnalystCanEditProjectBudgetSuccessfully() throws InterruptedException {

        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectDetailsBudgetListingPO projectDetailsBudgetListingPO = new ProjectDetailsBudgetListingPO(driver);
        ProjectDetailsPartnershipsListingPO projectDetailsPartnershipsListingPO = new ProjectDetailsPartnershipsListingPO(driver);
        String textFileName = JavaHelpers.getPropertyValue(PROPERTY_TEST_FILE, "textFile");

        Reporter.log("Step 1 - Entering valid sign in details and login to application");
        new LoginFunctions(driver, selenium).loginByGivenUser(faUser);

        Reporter.log("Step 2 - Navigate on partnership detail page and click on project link button");
        selenium.navigateToPage(strFaFundingPartnershipUrl);
        selenium.hardWait(3);
        projectDetailsPartnershipsListingPO.clickOnProjectLinkButton();

        Reporter.log("Step 3 - Click on budget detail button");
        projectDetails.clickOnBudgetButton();

        Reporter.log("Step 4 - Edit budget in created project");
        new BudgetFunctions(driver, selenium).editProjectBudget(textFileName, fodUser, faBudgetData);

        Reporter.log("Step 5 - Verify that project budget is edited by  financial analyst user");
        Assert.assertEquals(projectDetailsBudgetListingPO.getUploadedTemplateFileName(), textFileName, "project budget template file name doesn't match : ");
        Assert.assertEquals(projectDetailsBudgetListingPO.getBudgetAmountText().replace(",", ""), "$" + faBudgetData.getBudgetAmount(), "project budget amount doesn't match : ");
    }

    /*Test 9 : Verify that financial analyst can add project team member */
    @Test(priority = 9)
    public void VerifyThatFinancialAnalystCanAddProjectTeamMemberSuccessfully() throws InterruptedException {
        ProjectDetailsTeamMembersListingPO projectDetailsTeamMembersListingPO = new ProjectDetailsTeamMembersListingPO(driver);

        Reporter.log("Step 1 -  Login with financial analyst user and add team member in created project");
        new LoginFunctions(driver, selenium).loginByGivenUser(faUser);
        selenium.navigateToPage(strFaProjectTeamMemberUrl);

        selenium.hardWait(3);

        Reporter.log("Step 2 - Verify that project team member is added by financial analyst user");
        Assert.assertEquals(projectDetailsTeamMembersListingPO.getMembersName(faControllerUser.getRealFirstName() + " " + faControllerUser.getRealLastName()), (faControllerUser.getRealFirstName() + " " + faControllerUser.getRealLastName()), "project member name doesn't match : ");
        Assert.assertEquals(projectDetailsTeamMembersListingPO.getMembersRoleByMemberName(faControllerUser.getRealFirstName() + " " + faControllerUser.getRealLastName()), faControllerUser.getUserRoles().get(0), "project member role doesn't match : ");
    }

    /*Test 10 : Verify that financial analyst can edit project team member */
    @Test(priority = 10)
    public void VerifyThatFinancialAnalystCanEditProjectTeamMemberSuccessfully() throws InterruptedException {

        ProjectDetailsTeamMembersListingPO projectDetailsTeamMembersListingPO = new ProjectDetailsTeamMembersListingPO(driver);

        Reporter.log("Step 1 -  Login with financial analyst user and edit team member in created project");
        new LoginFunctions(driver, selenium).loginByGivenUser(faUser);
        selenium.navigateToPage(strFaProjectTeamMemberUrl);
        selenium.hardWait(3);
        new TeamMemberFunctions(driver, selenium).editProjectTeamMember(faControllerUser);

        Reporter.log("Step 2 - Verify that project team member is edited by financial analyst user");
        Assert.assertEquals(projectDetailsTeamMembersListingPO.getMembersRoleByMemberName(faControllerUser.getRealFirstName() + " " + faControllerUser.getRealLastName()), faControllerUser.getUserRoles().get(0) + ", " + faControllerUser.getUserRoles().get(1), "project member updated role doesn't match : ");
    }

   /*Test 11 : Verify that financial analyst can create project partnership */

    /*Test 12 : Verify that financial analyst can edit project partnership */
    @Test(priority = 11)
    public void VerifyThatFinancialAnalystCanEditProjectPartnershipSuccessfully() throws InterruptedException, java.text.ParseException {

        ProjectDetailsPartnershipsEditPO projectDetailsPartnershipsEditPO = new ProjectDetailsPartnershipsEditPO(driver);
        ProjectDetailsPartnershipsListingPO projectDetailsPartnershipsListingPO = new ProjectDetailsPartnershipsListingPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with financial analyst user");
        new LoginFunctions(driver, selenium).loginByGivenUser(faUser);
        selenium.navigateToPage(strFaPartnershipUrl);
        new PartnershipFunctions(driver, selenium).editProjectPartnership(faEditProjectAdminData);

        Reporter.log("Step 2 - Click on partnership submit button");
        projectDetailsPartnershipsEditPO.clickOnPartnershipSubmitButton();

        Assert.assertEquals(projectDetailsPartnershipsListingPO.getTypeText(faPartnerAdminData.getOrganizationName()), PartnerType.Managing.getPartnerType(), "partner types doesn't match");
        Assert.assertEquals(projectDetailsPartnershipsListingPO.getFinancialReportingTypeText(faPartnerAdminData.getOrganizationName()), PartnershipFinancialReportingTypes.Funded.getPartnershipFinancialReportingTypes(), "partnership financial reporting types doesn't match");
        Assert.assertEquals(projectDetailsPartnershipsListingPO.getAgreementStatusText(faPartnerAdminData.getOrganizationName()), PartnershipStatus.AwaitingSignature.getPartnerShipStatus(), " partnership agreement status doesn't match");
        Assert.assertEquals(projectDetailsPartnershipsListingPO.getMouStatusText(faPartnerAdminData.getOrganizationName()), PartnershipStatus.NotAttached.getPartnerShipStatus(), " partnership mou status doesn't match");
        Assert.assertEquals(projectDetailsPartnershipsListingPO.getMouDateRangeText(faPartnerAdminData.getOrganizationName()), new JavaHelpers().changeDateFormat(faEditProjectAdminData.getStartDate(), "MM/dd/yyyy", "M/d/yyyy") + " - " + new JavaHelpers().changeDateFormat(faEditProjectAdminData.getEndDate(), "MM/dd/yyyy", "M/d/yyyy"), "project start end date doesn't match : ");
        Assert.assertTrue(projectDetailsPartnershipsListingPO.isCreatedPartnershipPrimary(faPartnerAdminData.getOrganizationName()), "Created partnership is not primary");
    }

    /*Test 13 : Verify that financial analyst can not create project internship engagements */


    /*Test 14 : Verify that financial analyst can not edit project internship engagements except disbursement complete date*/
    @Test(priority = 12)
    public void VerifyThatFinancialAnalystCanNotEditProjectInternshipEngagementsExceptDisbursementCompleteDate() throws InterruptedException {

        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectInternshipDetailsPO projectInternshipDetailsPO = new ProjectInternshipDetailsPO(driver);
        ProjectInternshipDetailsUpdateCountryOfOriginPO projectInternshipDetailsUpdateCountryOfOriginPO = new ProjectInternshipDetailsUpdateCountryOfOriginPO(driver);
        ProjectInternshipDetailsUpdateInternPositionPO projectInternshipDetailsUpdateInternPositionPO = new ProjectInternshipDetailsUpdateInternPositionPO(driver);
        ProjectEngagementDetailsUpdateStartEndDatePO projectEngagementDetailsUpdateStartEndDatePO = new ProjectEngagementDetailsUpdateStartEndDatePO(driver);
        ProjectInternshipLanguageDetailsUpdateStatusPO projectInternshipLanguageDetailsUpdateStatusPO = new ProjectInternshipLanguageDetailsUpdateStatusPO(driver);
        ProjectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO projectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO = new ProjectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO(driver);

        Reporter.log("Step 1 -  Login with financial analyst user and navigate in internship url");
        new LoginFunctions(driver, selenium).loginByGivenUser(faUser);
        selenium.navigateToPage(strFaInternshipUrl);

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

    /*Test 15 : Verify that financial analyst can read project location */
    @Test(priority = 13)
    public void VerifyThatFinancialAnalystCanReadProjectLocation() throws InterruptedException {

        HeaderPO headerPO = new HeaderPO(driver);
        LocationsListingPO locationsListingPO = new LocationsListingPO(driver);
        LocationDetailsPO locationDetailsPO = new LocationDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with financial analyst user");
        new LoginFunctions(driver, selenium).loginByGivenUser(faUser);

        Reporter.log("Step 2 - Enter location name and click on view location button");
        headerPO.searchItemGloballyByName(faLocationName);
        locationsListingPO.clickOnFirstViewLocationButton();

        Reporter.log("Step 3 - Verify the location edit button is not present");
        Assert.assertFalse(locationDetailsPO.isLocationEditButtonPresent(), "Location edit button is present:");

    }

    /*Test 16 : Verify that financial analyst can not create language partner and location */
    @Test(priority = 14)
    public void VerifyThatFinancialAnalystCanNotCreateLanguagePartnerAndLocation() throws InterruptedException {

        LeftNavPO leftNavPO = new LeftNavPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with financial analyst user");
        new LoginFunctions(driver, selenium).loginByGivenUser(faUser);

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

    /*Test 17 : Verify that financial analyst can not create project language engagements */

    /*Test 18 : Verify that financial analyst can not edit project language engagements except disbursement complete date */
    @Test(priority = 15)
    public void VerifyThatFinancialAnalystCanNotEditProjectLanguageEngagementsExceptDisbursementCompleteDate() throws InterruptedException {

        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectLanguageEngagementDetailsPO projectLanguageEngagementDetailsPO = new ProjectLanguageEngagementDetailsPO(driver);
        ProjectEngagementDetailsUpdateStartEndDatePO projectEngagementDetailsUpdateStartEndDatePO = new ProjectEngagementDetailsUpdateStartEndDatePO(driver);
        ProjectInternshipLanguageDetailsUpdateStatusPO projectInternshipLanguageDetailsUpdateStatusPO = new ProjectInternshipLanguageDetailsUpdateStatusPO(driver);
        ProjectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO projectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO = new ProjectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO(driver);

        Reporter.log("Step 1 -  Login with financial analyst user and navigate in language engagement url");
        new LoginFunctions(driver, selenium).loginByGivenUser(faUser);
        selenium.navigateToPage(strFaLanguageTranslationUrl);

        Reporter.log("Step 2 - Click on view details button");
        projectDetails.clickOnViewDetailsLanguageEngagementButton(faLanguageAdminData.getLanguageEngagementName());

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

    /*Test 19 : Verify that financial analyst can not edit goals */
    @Test(priority = 16)
    public void VerifyThatFinancialAnalystCanNotEditProjectGoalsSuccessfully() throws InterruptedException {

        CreateGoalPO createGoalPO = new CreateGoalPO(driver);
        ProjectLanguageEngagementDetailsPO projectLanguageEngagementDetailsPO = new ProjectLanguageEngagementDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with staff member user");
        new LoginFunctions(driver, selenium).loginByGivenUser(stfUser);
        selenium.navigateToPage(strFaProjectCreateGoalUrl);

        Reporter.log("Step 2 - Click on Created goal");
        projectLanguageEngagementDetailsPO.clickOnGoalCard();

        Reporter.log("Step 3 - Click on goal edit button");
        projectLanguageEngagementDetailsPO.clickEditGoalButton();

        Reporter.log("Step 4 - Verify that staff member can read created project goals");
        String disableGoalColor = Constants.disableGrayColorCode;
        Assert.assertEquals(disableGoalColor, createGoalPO.getDisabledEditGoalDetailsColor(), "Goal detail color doesn't match : ");
    }

    /*Test 20 : Verify that financial analyst can edit projects */
    @Test(priority = 17)
    public void VerifyThatFinancialAnalystCanEditProjectsSuccessfully() throws InterruptedException, java.text.ParseException {

        LeftNavPO sideHeader = new LeftNavPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);
        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectsListingPO projectsListingPO = new ProjectsListingPO(driver);

        Reporter.log("Step 1 -  Login with financial analyst user and edit created project");
        new LoginFunctions(driver, selenium).loginByGivenUser(faUser);
        new ProjectFunctions(driver, selenium).editInternshipProjectByName(faEditProjectAdminData, faCreatedProjectName,faUser);

        Reporter.log("Step 2 - Verify that project is displayed which is edited by financial analyst user ");
        sideHeader.clickOnProjectsMenu();
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());

        if (!projectsListingPO.isProjectPresent(faEditProjectAdminData.getProjectName())) {
            headerPO.searchItemGloballyByName(faEditProjectAdminData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(faEditProjectAdminData.getProjectName());

        Assert.assertEquals(Sensitivity.Low.toString(), projectDetails.getProjectSensitivity(), "project sensitivity doesn't match : ");
        Assert.assertEquals(projectDetails.getProjectLocationAndFieldRegion(), faEditProjectAdminData.getLocationName() + " | " + faEditProjectAdminData.getFieldRegionName(), "project location and field region doesn't match : ");
        Assert.assertEquals(projectDetails.getProjectStartEndDate(), new JavaHelpers().changeDateFormat(faEditProjectAdminData.getStartDate(), "MM/dd/yyyy", "M/d/yyyy") + "\n" + " - " + "\n" + new JavaHelpers().changeDateFormat(faEditProjectAdminData.getEndDate(), "MM/dd/yyyy", "M/d/yyyy"), "project start end date doesn't match : ");

        Reporter.log("Step 3 - Verify that project name engagement estimated date is not editable");// todo R access list Estimated date ,project name,locations,engagement,field region
        Assert.assertFalse(projectDetails.isEditIconPresent(), "Project edit icon is present : ");
        Assert.assertFalse(projectDetails.isProjectInternshipEngagementAddButtonPresent(), "Project internship engagement add button is present : ");
        Assert.assertFalse(projectDetails.isEstimatedSubmissionDateButtonEnabled(), "Estimated submission data button is enabled : ");

    }

}

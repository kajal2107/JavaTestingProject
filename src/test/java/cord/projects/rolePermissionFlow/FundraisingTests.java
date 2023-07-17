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

public class FundraisingTests extends BaseTest {

    protected String strFundPeopleUrl;
    protected String strFundLanguageUrl;
    protected String strFundProjectUrl;
    protected String strFundInternshipUrl;
    protected String strFundPartnershipUrl;
    protected String strFundingPartnershipUrl;
    protected String strFundProjectCreateGoalUrl;
    protected String strFundProjectTeamMemberUrl;
    protected String strFundLanguageTranslationUrl;
    People fundPeopleFpmData = new PeopleData().getPeopleData();
    Project fundAdminData = new ProjectData().getCreateProjectData();
    Project fundAdminBudgetData = new ProjectData().getCreateProjectData();
    String fundCreatedBudgetProjectName = fundAdminBudgetData.getProjectName();
    Project fundAdminTranslationData = new ProjectData().getCreateTranslationProjectData();
    Project fundAdminTransEngagementData = new ProjectData().getCreateTranslationProjectData();
    String fundCreatedTransEngagementProjectName = fundAdminTransEngagementData.getProjectName();
    String fundCreatedTranslationProjectName = fundAdminTranslationData.getProjectName();
    String fundCreatedProjectName = fundAdminData.getProjectName();
    People fundPeopleAdminData = new PeopleData().getPeopleData();
    Project fundEditProjectAdminData = new ProjectData().getUpdateProjectData();
    Language fundLanguageAdminData = new LanguageData().getLanguageData();
    Partner fundPartnerAdminData = new PartnerData().getCreatePartnerData();
    Partner fundEditPartnerAdminData = new PartnerData().getEditPartnerData();
    Goal fundGoalData = new GoalData().getCreateGoalData();
    Location fundLocationData = new LocationData().getLocationData();
    String fundLocationName = fundLocationData.getLocationName();

    public FundraisingTests() throws ParseException, java.text.ParseException, InterruptedException {
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
        new ProjectFunctions(driver, selenium).createProject(fundAdminData);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 2 - Create Budget Project by admin user and logout that user");
        new ProjectFunctions(driver, selenium).createProject(fundAdminBudgetData);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 3 - Create TranslationType Project by admin user and logout that user");
        new ProjectFunctions(driver, selenium).createProject(fundAdminTranslationData);
        new PartnershipFunctions(driver, selenium).createProjectPartnership(fundPartnerAdminData ,fundCreatedTranslationProjectName,adminUser);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(fpmUser);

        Reporter.log("Step 4 - Create Project by project manager and logout that user");
        new ProjectFunctions(driver, selenium).createProject(fundAdminTransEngagementData);
        strFundProjectUrl = driver.getCurrentUrl();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 5 - Create People by admin user");
        new PeopleFunctions(driver, selenium).createPeople(fundPeopleAdminData,adminUser);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(fpmUser);

        Reporter.log("Step 6 - Create People by project manager user");
        new PeopleFunctions(driver, selenium).createPeople(fundPeopleFpmData,fpmUser);
        strFundPeopleUrl = driver.getCurrentUrl();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 7 - Create Language by admin user");
        new LanguageFunctions(driver, selenium).createLanguage(fundLanguageAdminData);
        strFundLanguageUrl = driver.getCurrentUrl();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 8 - Create internship engagements by admin user");
        new InternshipEngagementsFunctions(driver, selenium).createProjectInternshipEngagements(adminUser, fundCreatedProjectName);
        strFundInternshipUrl = driver.getCurrentUrl();
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 9 - Create language engagements by admin user");
        new LanguageFunctions(driver, selenium).createProjectLanguageEngagements(fundLanguageAdminData, fundCreatedTranslationProjectName,adminUser);
        strFundLanguageTranslationUrl = driver.getCurrentUrl();
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 10 - Create Partner by admin user");
        new PartnerFunctions(driver, selenium).createPartner(fundPartnerAdminData);
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 11 - Create partnership by admin user");
        new PartnershipFunctions(driver, selenium).createProjectPartnership(fundPartnerAdminData, fundCreatedTranslationProjectName,adminUser);
        strFundPartnershipUrl = driver.getCurrentUrl();
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 12 - Add project team member with multiple user role");
        new ProjectFunctions(driver, selenium).addMemberToProject(rdFodUser, fundAdminData);
        strFundProjectTeamMemberUrl = driver.getCurrentUrl();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 13 - Create Location by admin user");
        new LocationFunctions(driver, selenium).createLocation(fundLocationData);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 14 - Create goal by admin user");
        selenium.navigateToPage(strFundLanguageTranslationUrl);
        new GoalFunctions(driver, selenium).createGoal(fundGoalData, fundLanguageAdminData.getLanguageEngagementName(),fundPartnerAdminData.getOrganizationName());
        strFundProjectCreateGoalUrl = driver.getCurrentUrl();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 15 - Create funding partnership by admin user");
        new ProjectFunctions(driver, selenium).editInternshipProjectByName(fundEditProjectAdminData, fundCreatedBudgetProjectName,adminUser);
        new PartnershipFunctions(driver, selenium).createProjectFundingPartnership(fundPartnerAdminData, fundEditProjectAdminData.getProjectName());
        strFundingPartnershipUrl = driver.getCurrentUrl();
        drivermanager.tearDown();
    }

    /*Test 1 : Verify that fundraising can view all projects*/
    @Test(priority = 1)
    public void VerifyFundraisingCanViewAllProjectsSuccessfully() throws InterruptedException {

        LeftNavPO sideHeader = new LeftNavPO(driver);
        ProjectsListingPO projectsListingPO = new ProjectsListingPO(driver);
        ProjectDetailsPO projectDetailsPO = new ProjectDetailsPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with fundraising user");
        new LoginFunctions(driver, selenium).loginByGivenUser(fundraisingUser);

        Reporter.log("Step 2 - Verify that projects is displayed which is created by admin");
        sideHeader.clickOnProjectsMenu();
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());

        if (!projectsListingPO.isProjectPresent(fundAdminData.getProjectName())) {
            headerPO.searchItemGloballyByName(fundAdminData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(fundAdminData.getProjectName());
        Assert.assertEquals(projectDetailsPO.getProjectName(), fundAdminData.getProjectName(), "Project is not present : " + fundAdminData.getProjectName());

        Reporter.log("Step 3 - Verify that projects is displayed which is created by FPM user");
        sideHeader.clickOnProjectsMenu();
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());

        if (!projectsListingPO.isProjectPresent(fundAdminTransEngagementData.getProjectName())) {
            headerPO.searchItemGloballyByName(fundAdminTransEngagementData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(fundAdminTransEngagementData.getProjectName());
        Assert.assertEquals(projectDetailsPO.getProjectName(), fundAdminTransEngagementData.getProjectName(), "Project is not present : " + fundAdminTransEngagementData.getProjectName());

    }

    /*Test 2 : Verify that fundraising can view all peoples*/
    @Test(priority = 2)
    public void VerifyFundraisingCanViewAllPeoplesSuccessfully() throws InterruptedException {

        LeftNavPO sideHeader = new LeftNavPO(driver);
        PeoplesPO peoples = new PeoplesPO(driver);
        PeopleDetailsPO peopleDetails = new PeopleDetailsPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with fundraising user");
        selenium.navigateToPage(strFundPeopleUrl);
        new LoginFunctions(driver, selenium).loginByGivenUser(fundraisingUser);

        Reporter.log("Step 2 - Verify that peoples is displayed which is created by admin");
        sideHeader.clickOnPeopleMenu();
        if (!peoples.isPeoplePresent(fundPeopleAdminData.getPersonName())) {
            headerPO.searchItemGloballyByName(fundPeopleAdminData.getPersonName());
        }
        peoples.clickOnPeopleName(fundPeopleAdminData.getPersonName());
        Assert.assertEquals(peopleDetails.getPeopleName().replaceAll("\\s.*", ""), fundPeopleAdminData.getPersonName(), "Project is not present : " + fundPeopleAdminData.getPersonName());

        Reporter.log("Step 3 - Verify that peoples is displayed which is created by FPM user");
        sideHeader.clickOnPeopleMenu();
        if (!peoples.isPeoplePresent(fundPeopleFpmData.getPersonName())) {
            headerPO.searchItemGloballyByName(fundPeopleFpmData.getPersonName());
        }
        peoples.clickOnPeopleName(fundPeopleFpmData.getPersonName());
        Assert.assertEquals(peopleDetails.getPeopleName().replaceAll("\\s.*", ""), fundPeopleFpmData.getPersonName(), "Project is not present : " + fundPeopleFpmData.getPersonName());

    }

    /*Test 3 : Verify that fundraising can edit mine peoples */
    @Test(priority = 3)
    public void VerifyThatFundraisingCanEditMinePeoples() throws InterruptedException {

        PeoplesPO peoples = new PeoplesPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);
        LeftNavPO sideHeader = new LeftNavPO(driver);
        UpdatePeoplePO updatePeople = new UpdatePeoplePO(driver);
        PeopleDetailsPO peopleDetails = new PeopleDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with fundraising user");
        new LoginFunctions(driver, selenium).loginByGivenUser(fundraisingUser);

        Reporter.log("Step 2 - Search user which have a fundraising role by email and click on people name");
        sideHeader.clickOnPeopleMenu();
        if (!peoples.isPeoplePresent(fundraisingUser.getEmail())) {
            headerPO.searchItemGloballyByName(fundraisingUser.getEmail());
        }
        peoples.clickOnPeopleName(fundraisingUser.getRealFirstName() + " " + fundraisingUser.getRealLastName());

        Reporter.log("Step 3 - Click on edit icon and edit mine person detail");
        peopleDetails.clickOnEditButton();
        updatePeople.clearAndUpdatePersonDetail(fundPeopleAdminData);
        updatePeople.clickOnSubmitButton();

        Reporter.log("Step 4 - Verify updated person details");
        //  Assert.assertEquals(fundraisingUser.getEmail(), peopleDetails.getEmail(), "email doesn't match : "); //Todo bug(#762) Global W access
        Assert.assertEquals(fundPeopleAdminData.getPersonTitle(), peopleDetails.getTitle(), "title doesn't match : ");
        Assert.assertEquals(fundPeopleAdminData.getPersonPhone(), peopleDetails.getPhoneNumber(), "phone number doesn't match : ");
        Assert.assertEquals(fundPeopleAdminData.getPersonAbout(), peopleDetails.getAbout(), "About doesn't match : ");
        Assert.assertEquals(UserRole.Fundraising.getRole(), peopleDetails.getRoles(), "Roles doesn't match : ");

        Reporter.log("Step 5 - Again Click on edit icon and reset the people");
        peopleDetails.clickOnEditButton();
        updatePeople.fillFirstnameLastNameAndEmailDetail(fundraisingUser.getRealFirstName(), fundraisingUser.getRealLastName(),fundraisingUser.getEmail());
        updatePeople.clickOnSubmitButton();
        Assert.assertEquals(fundraisingUser.getRealFirstName() + " " + fundraisingUser.getRealLastName(), peopleDetails.getPeopleName(), "personName doesn't match : ");

    }

    /*Test 4 : Verify that fundraising can view all languages and edit icon is not present*/
    @Test(priority = 4)
    public void VerifyFundraisingCanViewAllLanguagesAndEditIconIsNotPresent() throws InterruptedException {

        LeftNavPO sideHeader = new LeftNavPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);
        LanguagesPO languages = new LanguagesPO(driver);
        LanguageDetailPO languageDetailPO = new LanguageDetailPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with fundraising user");
        selenium.navigateToPage(strFundLanguageUrl);
        new LoginFunctions(driver, selenium).loginByGivenUser(fundraisingUser);

        Reporter.log("Step 2 - Verify that language is displayed which is created by admin and edit icon is not present");
        sideHeader.clickOnLanguagesMenu();
        if (!languages.isLanguagePresent(fundLanguageAdminData.getLanguageName())) {
            headerPO.searchItemGloballyByName(fundLanguageAdminData.getLanguageName());
        }
        languages.clickOnLanguageName(fundLanguageAdminData.getLanguageName());
        Assert.assertEquals(languageDetailPO.getLanguageName(), fundLanguageAdminData.getPubliicName(), "Language is not present : " + fundLanguageAdminData.getPubliicName());
        Assert.assertFalse(languageDetailPO.isEditIconPresent(), "Language edit icon is present : ");

    }

    /*Test 5 : Verify that fundraising can view all partners*/
    @Test(priority = 5)
    public void VerifyFundraisingCanViewAllPartnersSuccessfully() throws InterruptedException {

        HeaderPO headerPO = new HeaderPO(driver);
        LeftNavPO sideHeader = new LeftNavPO(driver);
        PartnersListingPO partnerListing = new PartnersListingPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with fundraising user");
        new LoginFunctions(driver, selenium).loginByGivenUser(fundraisingUser);

        Reporter.log("Step 2 - Verify that partner is displayed which is created by admin");
        sideHeader.clickOnPartnersMenu();
        if (!partnerListing.isPartnerPresent(fundPartnerAdminData.getPartnerName())) {
            headerPO.searchItemGloballyByName(fundPartnerAdminData.getPartnerName());
        }
        Assert.assertTrue(partnerListing.isPartnerPresent(fundPartnerAdminData.getPartnerName()), "Partner is not present : " + fundPartnerAdminData.getPartnerName());

    }

    /*Test 6 : Verify that fundraising can't edit partners details */  //Todo bug(#837) Global R access
    @Test(priority = 6)
    public void VerifyThatFundraisingCanNotEditAllPartnersDetails() throws InterruptedException {

        PartnerDetailsPO partnerDetailsPO = new PartnerDetailsPO(driver);
        EditPartnerPO editPartnerPO = new EditPartnerPO(driver);

        Reporter.log("Step 1 - Login with fundraising user");
        new LoginFunctions(driver, selenium).loginByGivenUser(fundraisingUser);

        Reporter.log("Step 2 - Verify that partner global innovations client is not edited by fundraising user ");
        new PartnerFunctions(driver, selenium).editPartnerGlobalInnovationsClient(fundPartnerAdminData.getPartnerName());
        Assert.assertTrue(editPartnerPO.isGlobalInnovationsClientErrorMessagePresent(), "Partner global innovations client error message is not present : ");
        editPartnerPO.clickOnCancelButton();

        Reporter.log("Step 3 - Verify that partner active inActive status is not edited by fundraising user ");
        new PartnerFunctions(driver, selenium).editPartnerActiveInActiveStatus(fundPartnerAdminData.getPartnerName());
        Assert.assertTrue(editPartnerPO.isActiveInActiveErrorMessagePresent(), "Partner active inActive error message is not present : ");
        editPartnerPO.clickOnCancelButton();

        Reporter.log("Step 4 - Verify that partner pmc entity code is not edited by fundraising user ");
        new PartnerFunctions(driver, selenium).editPartnerPmcEntityCode(fundPartnerAdminData.getPartnerName(), fundEditPartnerAdminData);
        Assert.assertTrue(editPartnerPO.isPmcEntityCodeErrorMessagePresent(), "Partner pmc entity code error message is not present : ");
        editPartnerPO.clickOnCancelButton();

        Reporter.log("Step 5 - Verify that partner point of contact is not edited by fundraising user ");
        new PartnerFunctions(driver, selenium).editPartnerPointOfContact(fundPartnerAdminData.getPartnerName(), fundraisingUser);
        Assert.assertTrue(editPartnerPO.isPointOfContactErrorMessagePresent(), "Partner point of contact error message is not present : ");
        editPartnerPO.clickOnCancelButton();

        Reporter.log("Step 6 - Verify that partner types address is not edited by fundraising user ");
        Assert.assertFalse(partnerDetailsPO.isPartnerTypesEditButtonEnabled(), "Partner types edit button is enabled : ");
        Assert.assertFalse(partnerDetailsPO.isPartnerAddressEditButtonEnabled(), "Partner address edit button is enabled: ");

    }

    /*Test 7 : Verify that fundraising can read project files */
    @Test(priority = 7)
    public void VerifyThatFundraisingCanUploadEditProjectFilesSuccessfully() throws InterruptedException {

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
        new FileFunctions(driver, selenium).uploadFile(fundCreatedProjectName, textFileName);

        Reporter.log("Step 3 - Verify that project file is uploaded by admin user can view fundraising user ");
        new LoginFunctions(driver, selenium).signOutAndChangeUser(fundraisingUser);

        Reporter.log("Step 4 - Click on all tabs and find the created project");
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());
        if (!projectsListingPO.isProjectPresent(fundAdminData.getProjectName())) {
            headerPO.searchItemGloballyByName(fundAdminData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(fundAdminData.getProjectName());

        Reporter.log("Step 5 - Click on view file button and verify the uploaded file");
        projectDetails.clickOnViewFileButton();
        Assert.assertTrue(projectDetailsFilesListingPO.isFileFolderPresent(textFileName.replace(".txt", "")), "uploaded file name doesn't match : ");

        Reporter.log("Step 6 - Logout the fundraising user and login with admin user");
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 7 - Click on all tabs and find the created project");
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());
        if (!projectsListingPO.isProjectPresent(fundAdminData.getProjectName())) {
            headerPO.searchItemGloballyByName(fundAdminData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(fundAdminData.getProjectName());

        Reporter.log("Step 8 - Click on view file button");
        projectDetails.clickOnViewFileButton();

        Reporter.log("Step 9 - Click on kebab menu and add new version of file");
        String versionFileName = JavaHelpers.getPropertyValue(PROPERTY_TEST_FILE, "newVersionFile");
        projectDetailsFilesListingPO.clickOnKebabMenuAndAddNewFileVersion(textFileName.replace(".txt", ""), versionFileName);
        selenium.refreshPage();

        Reporter.log("Step 10 - Logout the admin user and login with fundraising user");
        new LoginFunctions(driver, selenium).signOutAndChangeUser(fundraisingUser);

        Reporter.log("Step 11 - Click on all tabs and find the created project");
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());
        if (!projectsListingPO.isProjectPresent(fundAdminData.getProjectName())) {
            headerPO.searchItemGloballyByName(fundAdminData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(fundAdminData.getProjectName());

        Reporter.log("Step 12 - Click on view file button");
        projectDetails.clickOnViewFileButton();

        Reporter.log("Step 13 - Click on kebab menu and view the history of file");
        projectDetailsFilesListingPO.clickOnKebabMenuAndViewFileHistory(textFileName.replace(".txt", ""));
        Assert.assertTrue(projectDetailsFilesHistoryListingPO.isFilePresent(versionFileName), "file is not present : ");

        Reporter.log("Step 14 - Logout the fundraising user and login with admin user");
        selenium.refreshPage();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());
        if (!projectsListingPO.isProjectPresent(fundAdminData.getProjectName())) {
            headerPO.searchItemGloballyByName(fundAdminData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(fundAdminData.getProjectName());

        Reporter.log("Step 15 - Click on view file button");
        projectDetails.clickOnViewFileButton();

        Reporter.log("Step 16 - Click on history kebab menu and rename the file");
        projectDetailsFilesListingPO.clickOnKebabMenuAndViewFileHistory(textFileName.replace(".txt", ""));
        projectDetailsFilesHistoryListingPO.clickOnHistoryKebabMenuAndRenameFileFolder(versionFileName);
        projectDetailsFileListingRenameFileFolderPO.clearAndRenameFileFolderName(fundEditProjectAdminData);

        Reporter.log("Step 17 - Logout the admin user and login with fundraising user");
        selenium.refreshPage();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(fundraisingUser);

        Reporter.log("Step 18 - Click on all tabs and find the created project");
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());
        if (!projectsListingPO.isProjectPresent(fundAdminData.getProjectName())) {
            headerPO.searchItemGloballyByName(fundAdminData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(fundAdminData.getProjectName());
        projectDetails.clickOnViewFileButton();

        Reporter.log("Step 19 - Click on kebab menu and view the history of file");
        projectDetailsFilesListingPO.clickOnKebabMenuAndViewFileHistory(textFileName.replace(".txt", ""));

        Reporter.log("Step 20 - Verify that renamed file is read bu fundraising user");
        Assert.assertTrue(projectDetailsFilesHistoryListingPO.isFilePresent(fundEditProjectAdminData.getFileName() + ".txt"), "renamed file is not present : ");
        Assert.assertFalse(projectDetailsFilesHistoryListingPO.isFilePresent(versionFileName), "renamed file is present : ");

    }

    /*Test 8 : Verify that fundraising can read project budget */
    @Test(priority = 8)
    public void VerifyThatFundraisingCanReadProjectBudgetSuccessfully() throws InterruptedException {

        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectDetailsBudgetListingPO projectDetailsBudgetListingPO = new ProjectDetailsBudgetListingPO(driver);
        ProjectDetailsPartnershipsListingPO projectDetailsPartnershipsListingPO = new ProjectDetailsPartnershipsListingPO(driver);

        Reporter.log("Step 1 - Entering valid sign in details and login to application");
        new LoginFunctions(driver, selenium).loginByGivenUser(fundraisingUser);

        Reporter.log("Step 2 - Navigate on partnership detail page and click on project link button");
        selenium.navigateToPage(strFundingPartnershipUrl);
        projectDetailsPartnershipsListingPO.clickOnProjectLinkButton();

        Reporter.log("Step 3 - Click on budget detail button");
        projectDetails.clickOnBudgetButton();

        Reporter.log("Step 4 - Verify that project budget is not edited by fundraising user");
        Assert.assertFalse(projectDetailsBudgetListingPO.isAddUniversalTemplateButtonEnabled(), "Budget add universal template file button is enabled : ");
        Assert.assertFalse(projectDetailsBudgetListingPO.isClickToEditButtonLinkPresent(), "Budget click to edit button link is present: ");

    }

    /*Test 9 : Verify that fundraising can add project team member */
    @Test(priority = 9)
    public void VerifyThatFundraisingCanAddProjectTeamMemberSuccessfully() throws InterruptedException {

        ProjectDetailsTeamMembersListingPO projectDetailsTeamMembersListingPO = new ProjectDetailsTeamMembersListingPO(driver);

        Reporter.log("Step 1 -  Login with fundraising user and add team member in created project");
        new LoginFunctions(driver, selenium).loginByGivenUser(fundraisingUser);
        selenium.navigateToPage(strFundProjectTeamMemberUrl);
        selenium.hardWait(3);

        Reporter.log("Step 2 - Verify that project team member is added by fundraising user");
        Assert.assertEquals(projectDetailsTeamMembersListingPO.getMembersName(rdFodUser.getRealFirstName() + " " + rdFodUser.getRealLastName()), (rdFodUser.getRealFirstName() + " " + rdFodUser.getRealLastName()), "project member name doesn't match : ");
        Assert.assertEquals(projectDetailsTeamMembersListingPO.getMembersRoleByMemberName(rdFodUser.getRealFirstName() + " " + rdFodUser.getRealLastName()), rdFodUser.getUserRoles().get(0), "project member role doesn't match : ");
    }

    /*Test 10 : Verify that fundraising can not edit project team member */
    @Test(priority = 10)
    public void VerifyThatFundraisingCanNotEditProjectTeamMemberSuccessfully() throws InterruptedException {

        ProjectMembersUpdateTeamMembersPO projectMembersUpdateTeamMembersPO = new ProjectMembersUpdateTeamMembersPO(driver);

        Reporter.log("Step 1 -  Login with fundraising user and edit team member in created project");
        new LoginFunctions(driver, selenium).loginByGivenUser(fundraisingUser);
        selenium.navigateToPage(strFundProjectTeamMemberUrl);
        selenium.hardWait(3);
        new TeamMemberFunctions(driver, selenium).editProjectTeamMember(rdFodUser);

        String teamMemberErrorMessage = Constants.teamMemberErrorMessage;

        Reporter.log("Step 2 - Verify that project team member is not edited by fundraising user");
        Assert.assertEquals(teamMemberErrorMessage, projectMembersUpdateTeamMembersPO.getTeamMemberErrorText(), "Team member error message is not matched");
    }

    /*Test 11 : Verify that fundraising can not create project partnership */
    @Test(priority = 11)
    public void VerifyThatFundraisingCanNotCreateProjectPartnership() {

        ProjectMembersCreatePartnershipPO projectMembersCreatePartnershipPO = new ProjectMembersCreatePartnershipPO(driver);

        Reporter.log("Step 1 - Login with fundraising user");
        new LoginFunctions(driver, selenium).loginByGivenUser(fundraisingUser);

        Reporter.log("Step 2 - Create Partnership with fundraising user");
        new PartnershipFunctions(driver, selenium).createProjectPartnership(fundPartnerAdminData, fundCreatedProjectName,fundraisingUser);

        String errorMessage = Constants.partnershipErrorMessage;
        Reporter.log("Step 3 - Verify that project partnership is not created by fundraising user");
        Assert.assertEquals(errorMessage, projectMembersCreatePartnershipPO.getPartnershipErrorText(), "Partnership error message is not matched");

    }

    /*Test 12 : Verify that fundraising can not edit project partnership*/
    @Test(priority = 12)
    public void VerifyThatFundraisingCanNotEditProjectPartnership() throws InterruptedException {

        ProjectDetailsPartnershipsEditPO projectDetailsPartnershipsEditPO = new ProjectDetailsPartnershipsEditPO(driver);
        ProjectDetailsPartnershipsListingPO partnershipsListingPO = new ProjectDetailsPartnershipsListingPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with fundraising user");
        new LoginFunctions(driver, selenium).loginByGivenUser(fundraisingUser);
        selenium.navigateToPage(strFundPartnershipUrl);
        selenium.hardWait(3);

        Reporter.log("Step 2 - Click on partnership edit button");
        partnershipsListingPO.clickOnEditOrganizationButton();

        Reporter.log("Step 3 - Verify that project partnership is not edited by fundraising user");
        Assert.assertFalse(projectDetailsPartnershipsEditPO.isPartnershipTypesButtonEnabled(), "Partnership Types checkbox is enabled : ");
        Assert.assertFalse(projectDetailsPartnershipsEditPO.isFinancialReportingTypeRadioButtonEnabled(), "Partnership Financial Reporting Type radio button is enabled : ");
        Assert.assertFalse(projectDetailsPartnershipsEditPO.isAgreementStatusEnabled(), "Partnership Agreement  Status radio button is enabled : ");
        Assert.assertFalse(projectDetailsPartnershipsEditPO.isMouStatusEnabled(), "Partnership Mou Status radio button is enabled : ");
        Assert.assertFalse(projectDetailsPartnershipsEditPO.isMouStartDateTextBoxEnabled(), "Partnership mou start date text box is enabled : ");
        Assert.assertFalse(projectDetailsPartnershipsEditPO.isMouEndDateTextBoxEnabled(), "Partnership  mou end date text box is enabled : ");

    }

    /*Test 13 : Verify that fundraising can not create project internship engagements */
    @Test(priority = 13)
    public void VerifyThatFundraisingCanNotCreateProjectInternshipEngagementsSuccessfully() {

        ProjectDetailsCreateInternshipPO projectDetailsCreateInternshipPO = new ProjectDetailsCreateInternshipPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with fundraising user");
        new LoginFunctions(driver, selenium).loginByGivenUser(fundraisingUser);

        Reporter.log("Step 2 - Try to create internship engagement with fundraising user");
        new InternshipEngagementsFunctions(driver, selenium).createProjectInternshipEngagements(fundraisingUser, fundCreatedProjectName);

        String internshipErrorMessage = Constants.engagementErrorMessage;

        Reporter.log("Step 3 - Verify tha user can not create internship engagement");
        Assert.assertEquals(internshipErrorMessage, projectDetailsCreateInternshipPO.getInternshipErrorMessage(), "project internship error message doesn't match : ");
    }

    /*Test 14 : Verify that fundraising can not edit project internship engagements */
    @Test(priority = 14)
    public void VerifyThatFundraisingCanNotEditProjectInternshipEngagements() throws InterruptedException {

        ProjectInternshipDetailsPO projectInternshipDetailsPO = new ProjectInternshipDetailsPO(driver);
        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectInternshipDetailsUpdateCountryOfOriginPO projectInternshipDetailsUpdateCountryOfOriginPO = new ProjectInternshipDetailsUpdateCountryOfOriginPO(driver);
        ProjectInternshipDetailsUpdateInternPositionPO projectInternshipDetailsUpdateInternPositionPO = new ProjectInternshipDetailsUpdateInternPositionPO(driver);
        ProjectEngagementDetailsUpdateStartEndDatePO projectEngagementDetailsUpdateStartEndDatePO = new ProjectEngagementDetailsUpdateStartEndDatePO(driver);
        ProjectInternshipLanguageDetailsUpdateStatusPO projectInternshipLanguageDetailsUpdateStatusPO = new ProjectInternshipLanguageDetailsUpdateStatusPO(driver);
        ProjectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO projectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO = new ProjectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO(driver);
        ProjectEngagementDetailsUpdateDisbursementCompleteDatePO projectEngagementDetailsUpdateDisbursementCompleteDatePO = new ProjectEngagementDetailsUpdateDisbursementCompleteDatePO(driver);

        Reporter.log("Step 1 -  Login with fundraising user and navigate in internship url");
        new LoginFunctions(driver, selenium).loginByGivenUser(fundraisingUser);
        selenium.navigateToPage(strFundInternshipUrl);

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

    /*Test 15 : Verify that fundraising can not edit project location */
    @Test(priority = 15)
    public void VerifyThatFundraisingCanNotEditProjectLocation() throws InterruptedException {

        HeaderPO headerPO = new HeaderPO(driver);
        LocationsListingPO locationsListingPO = new LocationsListingPO(driver);
        LocationDetailsPO locationDetailsPO = new LocationDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with fundraising user");
        new LoginFunctions(driver, selenium).loginByGivenUser(fundraisingUser);

        Reporter.log("Step 2 - Enter location name and click on view location button");
        headerPO.searchItemGloballyByName(fundLocationName);
        locationsListingPO.clickOnFirstViewLocationButton();

        Reporter.log("Step 3 - Verify the location edit button is not present");
        Assert.assertFalse(locationDetailsPO.isLocationEditButtonPresent(), "Location edit button is present:");
    }

    /*Test 16 : Verify that fundraising can not create language partner project and location */
    @Test(priority = 16)
    public void VerifyThatFundraisingCanNotCreateLanguagePartnerProjectAndLocation() throws InterruptedException {

        LeftNavPO leftNavPO = new LeftNavPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with fundraising user");
        new LoginFunctions(driver, selenium).loginByGivenUser(fundraisingUser);

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

    /*Test 17 : Verify that fundraising can not create project language engagements */
    @Test(priority = 17)
    public void VerifyThatFundraisingCanNotCreateProjectLanguageEngagements() {

        ProjectDetailsCreateLanguageEngagementPO projectDetailsCreateLanguageEngagementPO = new ProjectDetailsCreateLanguageEngagementPO(driver);

        Reporter.log("Step 1 - Login with fundraising user");
        new LoginFunctions(driver, selenium).loginByGivenUser(fundraisingUser);

        Reporter.log("Step 2 - Try to create language engagement with fundraising user");
        new LanguageFunctions(driver, selenium).createProjectLanguageEngagements(fundLanguageAdminData, fundCreatedTransEngagementProjectName,fundraisingUser);

        String languageEngagementErrorMessage = Constants.engagementErrorMessage;

        Reporter.log("Step 3 - Verify tha user can not create language engagement");
        Assert.assertEquals(languageEngagementErrorMessage, projectDetailsCreateLanguageEngagementPO.getLanguageEngagementErrorMessage(), "project language engagement error message doesn't match : ");
    }

    /*Test 18 : Verify that fundraising can not edit language engagements details  */
    @Test(priority = 18)
    public void VerifyThatFundraisingCanNotEditProjectLanguageEngagements() throws InterruptedException {

        ProjectLanguageEngagementDetailsPO projectLanguageEngagementDetailsPO = new ProjectLanguageEngagementDetailsPO(driver);
        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectEngagementDetailsUpdateStartEndDatePO projectEngagementDetailsUpdateStartEndDatePO = new ProjectEngagementDetailsUpdateStartEndDatePO(driver);
        ProjectInternshipLanguageDetailsUpdateStatusPO projectInternshipLanguageDetailsUpdateStatusPO = new ProjectInternshipLanguageDetailsUpdateStatusPO(driver);
        ProjectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO projectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO = new ProjectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO(driver);
        ProjectEngagementDetailsUpdateDisbursementCompleteDatePO projectEngagementDetailsUpdateDisbursementCompleteDatePO = new ProjectEngagementDetailsUpdateDisbursementCompleteDatePO(driver);

        Reporter.log("Step 1 -  Login with fundraising user and navigate in language engagement url");
        new LoginFunctions(driver, selenium).loginByGivenUser(fundraisingUser);
        selenium.navigateToPage(strFundLanguageTranslationUrl);
        selenium.hardWait(3);

        Reporter.log("Step 2 - Click on view details button");
        projectDetails.clickOnViewDetailsLanguageEngagementButton(fundLanguageAdminData.getLanguageEngagementName());

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

        Reporter.log("Step 9 - Try to edit language engagement disbursement complete date");
        Assert.assertFalse(projectEngagementDetailsUpdateDisbursementCompleteDatePO.isInternshipDisbursementCompleteDateTextBoxEnabled(), "Language engagement disbursement complete date textBox is enabled : ");

        Reporter.log("Step 10 - Click on language engagement disbursement complete date close button");
    }

    /*Test 19 : Verify that fundraising can not Add project goals */ //(#bug)
    @Test(priority = 19)
    public void VerifyThatFundraisingCanNotAddProjectGoals() throws InterruptedException {

        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectLanguageEngagementDetailsPO projectLanguageEngagementDetailsPO = new ProjectLanguageEngagementDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with fundraising user");
        new LoginFunctions(driver, selenium).loginByGivenUser(fundraisingUser);
        selenium.navigateToPage(strFundLanguageTranslationUrl);
        selenium.hardWait(3);

        Reporter.log("Step 2 - Click on view details button");
        projectDetails.clickOnViewDetailsLanguageEngagementButton(fundLanguageAdminData.getLanguageEngagementName());

        Reporter.log("Step 3 - Verify the Add goal button is not present on language engagement detail page");
        Assert.assertFalse(projectLanguageEngagementDetailsPO.isAddGoalButtonPresent(), "Add goal button is present ");

    }

    /*Test 20 : Verify that fundraising can read created project goals */
    @Test(priority = 20)
    public void VerifyThatFundraisingCanReadProjectGoals() throws InterruptedException {

        CreateGoalPO createGoalPO = new CreateGoalPO(driver);
        ProjectLanguageEngagementDetailsPO projectLanguageEngagementDetailsPO = new ProjectLanguageEngagementDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with fundraising user");
        new LoginFunctions(driver, selenium).loginByGivenUser(fundraisingUser);
        selenium.navigateToPage(strFundProjectCreateGoalUrl);
        selenium.hardWait(3);

        Reporter.log("Step 2 - Click on Created goal");
        projectLanguageEngagementDetailsPO.clickOnGoalCard();

        Reporter.log("Step 3 - Click on goal edit button");
        projectLanguageEngagementDetailsPO.clickEditGoalButton();

        Reporter.log("Step 4 - Verify that fundraising can read created project goals");
        String disableGoalColor = Constants.disableGrayColorCode;
        Assert.assertEquals(disableGoalColor, createGoalPO.getDisabledEditGoalDetailsColor(), "Goal detail color doesn't match : ");

    }

    /*Test 21 : Verify that fundraising can not edit projects */
    @Test(priority = 21)
    public void VerifyThatFundraisingCanNotEditProjectsSuccessfully() throws InterruptedException {

        LeftNavPO sideHeader = new LeftNavPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);
        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectsListingPO projectsListingPO = new ProjectsListingPO(driver);
        ProjectDetailsUpdateStepsPO projectDetailsUpdateStepsPO = new ProjectDetailsUpdateStepsPO(driver);
        ProjectDetailsUpdateEstimatedSubmissionDatePO projectDetailsUpdateEstimatedSubmissionDatePO = new ProjectDetailsUpdateEstimatedSubmissionDatePO(driver);
        ProjectDetailsUpdateStartEndDatePO projectDetailsUpdateStartEndDatePO = new ProjectDetailsUpdateStartEndDatePO(driver);
        ProjectDetailsUpdateLocationAndFieldRegionPO projectDetailsUpdateLocationAndFieldRegionPO = new ProjectDetailsUpdateLocationAndFieldRegionPO(driver);

        Reporter.log("Step 1 -  Login with fundraising user and edit created project");
        new LoginFunctions(driver, selenium).loginByGivenUser(fundraisingUser);

        Reporter.log("Step 2 - Verify that project is displayed which is edited by fundraising user ");
        sideHeader.clickOnProjectsMenu();
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());

        if (!projectsListingPO.isProjectPresent(fundAdminData.getProjectName())) {
            headerPO.searchItemGloballyByName(fundAdminData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(fundAdminData.getProjectName());

        Reporter.log("Step 3 - Click on project location and field region button and try to edit with fundraising user ");
        projectDetails.clickProjectLocationAndFieldRegionButton();

        Reporter.log("Step 4 - Verify that fundraising can't edit location and field region");
        Assert.assertFalse(projectDetailsUpdateLocationAndFieldRegionPO.isFieldRegionTextBoxEnabled(), "project location field region textBox is enabled : ");

        Reporter.log("Step 5 - Click on location field region close button");
        projectDetailsUpdateLocationAndFieldRegionPO.clickOnLocationFieldRegionCloseButton();

        Reporter.log("Step 6 - Click on project start end date button and try to edit with fundraising user ");
        projectDetails.clickProjectStartEndDateButton();

        Reporter.log("Step 7 - Verify that fundraising can't edit start end date");
        Assert.assertFalse(projectDetailsUpdateStartEndDatePO.isStartDateTextBoxEnabled(), "project start date textBox is enabled : ");
        Assert.assertFalse(projectDetailsUpdateStartEndDatePO.isEndDateTextBoxEnabled(), "project end date textBox is enabled: ");

        Reporter.log("Step 8 - Click on start end date close button");
        projectDetailsUpdateStartEndDatePO.clickOnStartEndDateCloseButton();

        Reporter.log("Step 9 - Click on project estimated submission date button and try to edit with fundraising user ");
        projectDetails.clickProjectEstimatedSubmissionButton();
        Assert.assertFalse(projectDetailsUpdateEstimatedSubmissionDatePO.isEstimatedSubmissionDateTextBoxEnabled(), "project estimated submission date textBox is enabled:");

        Reporter.log("Step 11 - Click on estimated submission date close button");
        projectDetailsUpdateEstimatedSubmissionDatePO.clickOnEstimatedSubmissionDateCloseButton();

        Reporter.log("Step 12 - Click on project status button and try to edit with fundraising user ");
        projectDetails.clickOnProjectStepByName(ProjectStep.EarlyConversations.getStep());

        Reporter.log("Step 13 - Verify that fundraising can't edit status");
        String statusErrorMessage = Constants.projectStatusErrorMessage;
        Assert.assertEquals(statusErrorMessage, projectDetailsUpdateStepsPO.getProjectStatusError(), "project status error text doesn't match : ");
    }

}

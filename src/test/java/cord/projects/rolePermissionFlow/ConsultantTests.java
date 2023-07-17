package cord.projects.rolePermissionFlow;

import base.BaseTest;
import datafactory.*;
import dataobjects.*;
import enums.menu.CreateItemTypes;
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
import utilities.Constants;
import utilities.DriverManager;
import utilities.JavaHelpers;
import utilities.SeleniumHelpers;

public class ConsultantTests extends BaseTest {

    protected String strConsulPeopleUrl;
    protected String strConsulLanguageUrl;
    protected String strConsulPartnerUrl;
    protected String strConsulInternshipUrl;
    protected String strConsulPartnershipUrl;
    protected String strConsulProjectCreateGoalUrl;
    protected String strConsulFundingPartnershipUrl;
    protected String strConsulProjectTeamMemberUrl;
    protected String strConsulLanguageTranslationUrl;
    Project consulAdminData = new ProjectData().getCreateProjectData();
    String consulCreatedProjectName = consulAdminData.getProjectName();
    Project consulAdminBudgetData = new ProjectData().getCreateProjectData();
    String consulCreatedBudgetProjectName = consulAdminBudgetData.getProjectName();
    People consulPeopleFpmData = new PeopleData().getPeopleData();
    Project consulAdminTranslationData = new ProjectData().getCreateTranslationProjectData();
    String consulCreatedTranslationProjectName = consulAdminTranslationData.getProjectName();
    People consulPeopleAdminData = new PeopleData().getPeopleData();
    People consulEditPeopleAdminData = new PeopleData().getPeopleData();
    Project consulEditProjectAdminData = new ProjectData().getUpdateProjectData();
    Project consulEditProjectFundingPartnershipAdminData = new ProjectData().getUpdateProjectData();
    Language consulLanguageAdminData = new LanguageData().getLanguageData();
    Partner consulPartnerAdminData = new PartnerData().getCreatePartnerData();
    Partner consulEditPartnerAdminData = new PartnerData().getEditPartnerData();
    Goal consulGoalData = new GoalData().getCreateGoalData();
    Project consulFpmData = new ProjectData().getCreateProjectData();
    Location consulLocationData = new LocationData().getLocationData();
    Project consulAdminLocationData = new ProjectData().getCreateProjectData();
    String consulLocationProjectName = consulAdminLocationData.getProjectName();
    Internship consulInternUpdateData = new EngagementData().getUpdateEngagementData();

    public ConsultantTests() throws ParseException, java.text.ParseException, InterruptedException {
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
        new ProjectFunctions(driver, selenium).createProject(consulAdminData);
        new ProjectFunctions(driver, selenium).addMemberToProject(consultantUser, consulAdminData);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 2 - Create Project by admin user,add location and logout that user");
        new ProjectFunctions(driver, selenium).createProject(consulAdminLocationData);
        new ProjectFunctions(driver, selenium).addMemberToProject(consultantUser, consulAdminLocationData);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 3 - Create Budget Project by admin user and logout that user");
        new ProjectFunctions(driver, selenium).createProject(consulAdminBudgetData);
        new ProjectFunctions(driver, selenium).addMemberToProject(consultantUser, consulAdminBudgetData);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 4 - Create TranslationType Project by admin user and logout that user");
        new ProjectFunctions(driver, selenium).createProject(consulAdminTranslationData);
        new ProjectFunctions(driver, selenium).addMemberToProject(consultantUser, consulAdminTranslationData);
        new PartnershipFunctions(driver, selenium).createProjectPartnership(consulPartnerAdminData ,consulCreatedTranslationProjectName,adminUser);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(fpmUser);

        Reporter.log("Step 5 - Create Project by project manager and logout the user");
        new ProjectFunctions(driver, selenium).createProject(consulFpmData);
        new ProjectFunctions(driver, selenium).addMemberToProject(consultantUser, consulFpmData);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 6 - Create People by admin user");
        new PeopleFunctions(driver, selenium).createPeople(consulPeopleAdminData, adminUser);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(fpmUser);

        Reporter.log("Step 7 - Create People by project manager user");
        new PeopleFunctions(driver, selenium).createPeople(consulPeopleFpmData, fpmUser);
        strConsulPeopleUrl = driver.getCurrentUrl();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 8- Create Language by admin user");
        new LanguageFunctions(driver, selenium).createLanguage(consulLanguageAdminData);
        strConsulLanguageUrl = driver.getCurrentUrl();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(consultantUser);

        Reporter.log("Step 9 - Create internship engagements by consultant user");
        new InternshipEngagementsFunctions(driver, selenium).createProjectInternshipEngagements(consultantUser, consulCreatedProjectName);
        strConsulInternshipUrl = driver.getCurrentUrl();
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 10 - Create language engagements by admin user");
        new LanguageFunctions(driver, selenium).createProjectLanguageEngagements(consulLanguageAdminData, consulCreatedTranslationProjectName, adminUser);
        strConsulLanguageTranslationUrl = driver.getCurrentUrl();
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 11- Create Partner by admin user");
        new PartnerFunctions(driver, selenium).createPartner(consulPartnerAdminData);
        strConsulPartnerUrl = driver.getCurrentUrl();
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 12 - Create partnership by consultant user");
        new PartnershipFunctions(driver, selenium).createProjectPartnership(consulPartnerAdminData, consulCreatedProjectName, adminUser);
        strConsulPartnershipUrl = driver.getCurrentUrl();
        selenium.hardWait(3);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(consultantUser);

        Reporter.log("Step 13 - Add project team member with multiple user role");
        new ProjectFunctions(driver, selenium).addMemberToProject(rdFodUser, consulAdminData);
        strConsulProjectTeamMemberUrl = driver.getCurrentUrl();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 14 - Create Location by admin user");
        new LocationFunctions(driver, selenium).createLocation(consulLocationData);
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 15- Create goal by admin user");
        selenium.navigateToPage(strConsulLanguageTranslationUrl);
        new GoalFunctions(driver, selenium).createGoal(consulGoalData, consulLanguageAdminData.getLanguageEngagementName(),consulPartnerAdminData.getOrganizationName());
        strConsulProjectCreateGoalUrl = driver.getCurrentUrl();
        new LoginFunctions(driver, selenium).signOutAndChangeUser(adminUser);

        Reporter.log("Step 16 - Create funding partnership by admin user");
        new ProjectFunctions(driver, selenium).editInternshipProjectByName(consulEditProjectFundingPartnershipAdminData, consulCreatedBudgetProjectName, adminUser);
        new PartnershipFunctions(driver, selenium).createProjectFundingPartnership(consulPartnerAdminData, consulEditProjectFundingPartnershipAdminData.getProjectName());
        strConsulFundingPartnershipUrl = driver.getCurrentUrl();
        drivermanager.tearDown();
    }

    /*Test 1 : Verify that consultant can view member of projects*/
    @Test(priority = 1)
    public void VerifyThatConsultantCanViewMemberOfProjectsSuccessfully() throws InterruptedException {

        LeftNavPO sideHeader = new LeftNavPO(driver);
        ProjectsListingPO projectsListingPO = new ProjectsListingPO(driver);
        ProjectDetailsPO projectDetailsPO = new ProjectDetailsPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with consultant user");
        new LoginFunctions(driver, selenium).loginByGivenUser(consultantUser);

        Reporter.log("Step 2 - Verify that projects is displayed which is created by admin");
        sideHeader.clickOnProjectsMenu();
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());

        if (!projectsListingPO.isProjectPresent(consulAdminData.getProjectName())) {
            headerPO.searchItemGloballyByName(consulAdminData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(consulAdminData.getProjectName());
        Assert.assertEquals(projectDetailsPO.getProjectName(), consulAdminData.getProjectName(), "Project is not present : " + consulAdminData.getProjectName());

        Reporter.log("Step 3 - Verify that projects is displayed which is created by FPM user");
        sideHeader.clickOnProjectsMenu();
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());

        if (!projectsListingPO.isProjectPresent(consulFpmData.getProjectName())) {
            headerPO.searchItemGloballyByName(consulFpmData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(consulFpmData.getProjectName());
        Assert.assertEquals(projectDetailsPO.getProjectName(), consulFpmData.getProjectName(), "Project is not present : " + consulFpmData.getProjectName());
    }

    /*Test 2 : Verify that consultant can view mine peoples*/
    @Test(priority = 2)
    public void VerifyThatConsultantCanViewMinePeoplesSuccessfully() throws InterruptedException {  //Todo bug(#2121) Mine read access

        LeftNavPO sideHeader = new LeftNavPO(driver);
        PeoplesPO peoples = new PeoplesPO(driver);
        PeopleDetailsPO peopleDetails = new PeopleDetailsPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with consultant user");
        selenium.navigateToPage(strConsulPeopleUrl);
        new LoginFunctions(driver, selenium).loginByGivenUser(consultantUser);

        Reporter.log("Step 2 - Verify that peoples is displayed which is created by admin");
        sideHeader.clickOnPeopleMenu();
        if (!peoples.isPeoplePresent(consulPeopleAdminData.getPersonName())) {
            headerPO.searchItemGloballyByName(consulPeopleAdminData.getPersonName());
        }
        peoples.clickOnPeopleName(consulPeopleAdminData.getPersonName());
        Assert.assertEquals(peopleDetails.getPeopleName().replaceAll("\\s.*", ""), consulPeopleAdminData.getPersonName(), "Project is not present : " + consulPeopleAdminData.getPersonName());

        Reporter.log("Step 3 - Verify that peoples is displayed which is created by FPM user");
        sideHeader.clickOnPeopleMenu();
        if (!peoples.isPeoplePresent(consulPeopleFpmData.getPersonName())) {
            headerPO.searchItemGloballyByName(consulPeopleFpmData.getPersonName());
        }
        peoples.clickOnPeopleName();
        peopleDetails.clickOnNullText();

        String fpmPeopleMessage = "You don't have permission to view this person's name";
        peoples.clickOnPeopleName(consulPeopleFpmData.getPersonName());

        Reporter.log("Step 4 - Verify that null value is present");
        Assert.assertEquals(peopleDetails.getPermissionToolTipText(), fpmPeopleMessage, "null user does not displayed");
    }

    /*Test 3 : Verify that consultant can edit mine peoples */
    @Test(priority = 3)
    public void VerifyThatConsultantCanEditMinePeoples() throws InterruptedException {

        PeoplesPO peoples = new PeoplesPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);
        LeftNavPO sideHeader = new LeftNavPO(driver);
        UpdatePeoplePO updatePeople = new UpdatePeoplePO(driver);
        PeopleDetailsPO peopleDetails = new PeopleDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with consultant user");
        new LoginFunctions(driver, selenium).loginByGivenUser(consultantUser);

        Reporter.log("Step 2 - Search user which have a consultant role by email and click on people name");
        sideHeader.clickOnPeopleMenu();
        if (!peoples.isPeoplePresent(consultantUser.getEmail())) {
            headerPO.searchItemGloballyByName(consultantUser.getEmail());
        }
        peoples.clickOnPeopleName(consultantUser.getRealFirstName() + " " + consultantUser.getRealLastName());

        Reporter.log("Step 3 - Click on edit icon and edit mine person detail");
        peopleDetails.clickOnEditButton();
        updatePeople.clearAndUpdatePersonDetail(consulEditPeopleAdminData);
        updatePeople.clickOnSubmitButton();

        Reporter.log("Step 4 - Verify updated person details");
        Assert.assertEquals(consulEditPeopleAdminData.getPersonEmail(), peopleDetails.getEmail(), "email doesn't match : ");
        Assert.assertEquals(consulEditPeopleAdminData.getPersonTitle(), peopleDetails.getTitle(), "title doesn't match : ");
        Assert.assertEquals(consulEditPeopleAdminData.getPersonPhone(), peopleDetails.getPhoneNumber(), "phone number doesn't match : ");
        Assert.assertEquals(consulEditPeopleAdminData.getPersonAbout(), peopleDetails.getAbout(), "About doesn't match : ");
        Assert.assertEquals(UserRole.Consultant.getRole(), peopleDetails.getRoles(), "Roles doesn't match : ");

        Reporter.log("Step 5 - Again Click on edit icon and reset the people");
        peopleDetails.clickOnEditButton();
        updatePeople.fillFirstnameLastNameAndEmailDetail(consultantUser.getRealFirstName(), consultantUser.getRealLastName(), consultantUser.getEmail());
        updatePeople.clickOnSubmitButton();
        Assert.assertEquals(consultantUser.getRealFirstName() + " " + consultantUser.getRealLastName(), peopleDetails.getPeopleName(), "personName doesn't match : ");
        Assert.assertEquals(consultantUser.getEmail(), peopleDetails.getEmail(), "personEmail doesn't match : ");
    }

    /*Test 4 : Verify that consultant can view member languages and edit icon is not present*/
    @Test(priority = 4)
    public void VerifyThatConsultantCanViewMemberLanguagesAndEditIconIsNotPresent() throws InterruptedException {

        LeftNavPO sideHeader = new LeftNavPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);
        ProjectsListingPO projectsListingPO = new ProjectsListingPO(driver);
        LanguageDetailPO languageDetailPO = new LanguageDetailPO(driver);
        ProjectDetailsPO projectDetailsPO = new ProjectDetailsPO(driver);
        ProjectLanguageEngagementDetailsPO projectLanguageEngagementDetailsPO = new ProjectLanguageEngagementDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with consultant user");
        new LoginFunctions(driver, selenium).loginByGivenUser(consultantUser);

        Reporter.log("Step 2 - Verify that projects is displayed which is created by admin");
        sideHeader.clickOnProjectsMenu();
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());

        if (!projectsListingPO.isProjectPresent(consulAdminTranslationData.getProjectName())) {
            headerPO.searchItemGloballyByName(consulAdminTranslationData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(consulAdminTranslationData.getProjectName());

        Reporter.log("Step 3 - Click on view details button of language engagement ");
        projectDetailsPO.clickOnViewDetailsLanguageEngagementButton(consulLanguageAdminData.getLanguageEngagementName());

        Reporter.log("Step 4 - Verify that language is displayed which is created by admin and edit icon is not present");
        Assert.assertEquals(projectLanguageEngagementDetailsPO.geLanguageEngagementText(), consulLanguageAdminData.getLanguageEngagementName(), "Language is not present : " + consulLanguageAdminData.getPubliicName());
        Assert.assertFalse(languageDetailPO.isEditIconPresent(), "Language edit icon is present : ");

    }

    /*Test 5 : Verify that consultant can view member partners*/
    @Test(priority = 5)
    public void VerifyConsultantCanViewMemberPartnersSuccessfully(){

        ProjectDetailsPartnershipsListingPO projectDetailsPartnershipsListingPO = new ProjectDetailsPartnershipsListingPO(driver);
        PartnersListingPO partnerListing = new PartnersListingPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with consultant user");
        new LoginFunctions(driver, selenium).loginByGivenUser(consultantUser);

        Reporter.log("Step 2 - Navigate to partnership url and get partner name");
        selenium.navigateToPage(strConsulFundingPartnershipUrl);
        String partnerName = projectDetailsPartnershipsListingPO.getPartnershipName();

        Reporter.log("Step 3 - Verify that partner name is match which is member of the project");
        Assert.assertTrue(partnerListing.isPartnerPresent(partnerName), "Partner name is not match");
    }

    /*Test 6 : Verify that consultant can't edit partners details */
    @Test(priority = 6)
    public void VerifyThatConsultantCanNotEditAllPartnersDetails() throws InterruptedException {

        PartnerDetailsPO partnerDetailsPO = new PartnerDetailsPO(driver);
        EditPartnerPO editPartnerPO = new EditPartnerPO(driver);
        ProjectDetailsPartnershipsListingPO projectDetailsPartnershipsListingPO = new ProjectDetailsPartnershipsListingPO(driver);

        Reporter.log("Step 1 - Login with consultant user");
        new LoginFunctions(driver, selenium).loginByGivenUser(consultantUser);

        selenium.navigateToPage(strConsulFundingPartnershipUrl);
        selenium.hardWait(3);
        String partnerName = projectDetailsPartnershipsListingPO.getPartnershipName();

        Reporter.log("Step 2 - Verify that partner global innovations client is not edited by consultant user ");
        new PartnerFunctions(driver, selenium).editPartnerGlobalInnovationsClient(partnerName);
        Assert.assertTrue(editPartnerPO.isGlobalInnovationsClientErrorMessagePresent(), "Partner global innovations client error message is not present : ");
        editPartnerPO.clickOnCancelButton();

        Reporter.log("Step 3 - Verify that partner active inActive status is not edited by consultant user ");
        new PartnerFunctions(driver, selenium).editPartnerActiveInActiveStatus(partnerName);
        Assert.assertTrue(editPartnerPO.isActiveInActiveErrorMessagePresent(), "Partner active inActive error message is not present : ");
        editPartnerPO.clickOnCancelButton();

        Reporter.log("Step 4 - Verify that partner pmc entity code is not edited by consultant user ");
        new PartnerFunctions(driver, selenium).editPartnerPmcEntityCode(partnerName, consulEditPartnerAdminData);
        Assert.assertTrue(editPartnerPO.isPmcEntityCodeErrorMessagePresent(), "Partner pmc entity code error message is not present : ");
        editPartnerPO.clickOnCancelButton();

        Reporter.log("Step 5 - Verify that partner point of contact is not edited by consultant user ");
        new PartnerFunctions(driver, selenium).editPartnerPointOfContact(partnerName, consultantUser);
        Assert.assertTrue(editPartnerPO.isPointOfContactErrorMessagePresent(), "Partner point of contact error message is not present : ");
        editPartnerPO.clickOnCancelButton();

        Reporter.log("Step 6 - Verify that partner types address is not edited by consultant user ");
        Assert.assertFalse(partnerDetailsPO.isPartnerTypesEditButtonEnabled(), "Partner types edit button is enabled : ");
        Assert.assertFalse(partnerDetailsPO.isPartnerAddressEditButtonEnabled(), "Partner address edit button is enabled: ");

    }

    /*Test 7 : Verify that consultant can upload/Edit project files */
    @Test(priority = 7)
    public void VerifyThatConsultantCanUploadEditProjectFilesSuccessfully() throws InterruptedException {

        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectDetailsFilesListingPO projectDetailsFilesListingPO = new ProjectDetailsFilesListingPO(driver);
        ProjectDetailsFilesHistoryListingPO projectDetailsFilesHistoryListingPO = new ProjectDetailsFilesHistoryListingPO(driver);
        ProjectDetailsFileListingRenameFileFolderPO projectDetailsFileListingRenameFileFolderPO = new ProjectDetailsFileListingRenameFileFolderPO(driver);
        ProjectDetailsFileListingDeleteFileFolderPO projectDetailsFileListingDeleteFileFolderPO = new ProjectDetailsFileListingDeleteFileFolderPO(driver);

        String textFileName = JavaHelpers.getPropertyValue(PROPERTY_TEST_FILE, "textFile");

        Reporter.log("Step 1 - Entering valid sign in details and login to application");
        new LoginFunctions(driver, selenium).loginByGivenUser(consultantUser);

        Reporter.log("Step 2 -  Login with consultant user and upload file in created project");
        new FileFunctions(driver, selenium).uploadFile(consulCreatedProjectName, textFileName);

        Reporter.log("Step 3 - Verify that project file is uploaded by consultant user ");
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
        projectDetailsFileListingRenameFileFolderPO.clearAndRenameFileFolderName(consulEditProjectAdminData);
        Assert.assertTrue(projectDetailsFilesHistoryListingPO.isFilePresent(consulEditProjectAdminData.getFileName() + ".txt"), "renamed file is not present : ");
        Assert.assertFalse(projectDetailsFilesHistoryListingPO.isFilePresent(versionFileName), "renamed file is present : ");

        Reporter.log("Step 8 - Click on history kebab menu and delete created file");
        projectDetailsFilesHistoryListingPO.clickOnKebabMenuAndDeleteFileFolder(projectDetailsFilesListingPO.getFirstFileUploadText());
        projectDetailsFileListingDeleteFileFolderPO.clickOnFileDeleteButton();
        Assert.assertFalse(projectDetailsFilesListingPO.isFileFolderPresent(consulEditProjectAdminData.getFileName()), "deleted file is present : ");

    }

    /*Test 8 : Verify that consultant can not view project budget */ //Todo bug(#2146) No read access
    @Test(priority = 8)
    public void VerifyThatConsultantCanNotViewProjectBudgetSuccessfully() throws InterruptedException {

        ProjectDetailsBudgetListingPO projectDetailsBudgetListingPO = new ProjectDetailsBudgetListingPO(driver);
        ProjectDetailsPartnershipsListingPO projectDetailsPartnershipsListingPO = new ProjectDetailsPartnershipsListingPO(driver);
        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);

        Reporter.log("Step 1 - Entering valid sign in details and login to application");
        new LoginFunctions(driver, selenium).loginByGivenUser(consultantUser);

        Reporter.log("Step 2 - Navigate on partnership detail page and click on project link button");
        selenium.navigateToPage(strConsulFundingPartnershipUrl);
        projectDetailsPartnershipsListingPO.clickOnProjectLinkButton();

        Reporter.log("Step 3 - Click on budget detail button");
        projectDetails.clickOnBudgetButton();

        Reporter.log("Step 4 - Verify that project budget name is not available");
        Assert.assertEquals(projectDetailsBudgetListingPO.getBudgetNameText(), "", "Budget name is available");
    }

    /*Test 9 : Verify that consultant can not add project team member */ //Todo bug(#2177) No create access
    @Test(priority = 9)
    public void VerifyThatConsultantCanAddProjectTeamMemberSuccessfully() {

        ProjectDetailsTeamMembersListingPO projectDetailsTeamMembersListingPO = new ProjectDetailsTeamMembersListingPO(driver);

        Reporter.log("Step 1 -  Login with consultant user and go to the team member listing page");
        new LoginFunctions(driver, selenium).loginByGivenUser(consultantUser);
        selenium.navigateToPage(strConsulProjectTeamMemberUrl);

        Reporter.log("Step 2 - Verify that project team member add button is not present");
        Assert.assertFalse(projectDetailsTeamMembersListingPO.isAddButtonPresent(), "project add button is present");
    }

    /*Test 10 : Verify that consultant can not edit project team member */
    @Test(priority = 10)
    public void VerifyThatConsultantCanNotEditProjectTeamMemberSuccessfully() {

        ProjectMembersUpdateTeamMembersPO projectMembersUpdateTeamMembersPO = new ProjectMembersUpdateTeamMembersPO(driver);

        Reporter.log("Step 1 -  Login with consultant user and edit team member in created project");
        new LoginFunctions(driver, selenium).loginByGivenUser(consultantUser);
        selenium.navigateToPage(strConsulProjectTeamMemberUrl);
        new TeamMemberFunctions(driver, selenium).editProjectTeamMember(rdFodUser);

        String teamMemberErrorMessage = Constants.teamMemberErrorMessage;

        Reporter.log("Step 2 - Verify that project team member is not edited by consultant user");
        Assert.assertEquals(teamMemberErrorMessage, projectMembersUpdateTeamMembersPO.getTeamMemberErrorText(), "Team member error message is not matched");
    }

    /*Test 11 : Verify that consultant can not create project partnership */
    @Test(priority = 11)
    public void VerifyThatConsultantCanNotCreateProjectPartnership() {

        ProjectMembersCreatePartnershipPO projectMembersCreatePartnershipPO = new ProjectMembersCreatePartnershipPO(driver);

        Reporter.log("Step 1 - Login with consultant user");
        new LoginFunctions(driver, selenium).loginByGivenUser(consultantUser);

        Reporter.log("Step 2 - Create Partnership with consultant user");
        new PartnershipFunctions(driver, selenium).createProjectPartnership(consulPartnerAdminData, consulCreatedProjectName, consultantUser);

        String searchedPartnershipName = "No options";

        Reporter.log("Step 3 - Verify that user can not create partnership");
        Assert.assertEquals(searchedPartnershipName, projectMembersCreatePartnershipPO.getSearchedPartnershipText(), "project partnership searched text is matched ");
    }

    /*Test 12 : Verify that consultant can not read project partnership */  //Todo bug(#2178) No read access
    @Test(priority = 12)
    public void VerifyThatConsultantCanNotReadProjectPartnership() {

        ProjectDetailsPartnershipsListingPO partnershipsListingPO = new ProjectDetailsPartnershipsListingPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with consultant user");
        new LoginFunctions(driver, selenium).loginByGivenUser(consultantUser);
        selenium.navigateToPage(strConsulPartnershipUrl);

        Reporter.log("Step 2 - Verify that partnership count is 0");
        Assert.assertFalse(partnershipsListingPO.isProjectPartnershipPresent(consulPartnerAdminData.getPartnerName()), "Partnership is present");
    }

    /*Test 13 : Verify that consultant can not create project internship engagements */ //Todo bug(#2179) No read access
    @Test(priority = 13)
    public void VerifyThatConsultantCanNotCreateProjectInternshipEngagementsSuccessfully() throws InterruptedException {

        HeaderPO headerPO = new HeaderPO(driver);
        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectsListingPO projectsListingPO = new ProjectsListingPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with consultant user");
        new LoginFunctions(driver, selenium).loginByGivenUser(consultantUser);

        Reporter.log("Step 2 - Search internship project and open the project");
        if (!projectsListingPO.isProjectPresent(consulAdminData.getProjectName())) {
            headerPO.searchItemGloballyByName(consulAdminData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(consulAdminData.getProjectName());

        Reporter.log("Step 3 - Verify that internship add button is not present");
        Assert.assertFalse(projectDetails.isProjectInternshipEngagementAddButtonPresent(), "Internship add button is present");

    }

    /*Test 14 : Verify that consultant can edit member project internship engagements */  // Todo bug(#2189)
    @Test(priority = 14)
    public void VerifyThatConsultantCanEditMemberProjectInternshipEngagementsSuccessfully() throws java.text.ParseException, InterruptedException {

        ProjectInternshipDetailsPO projectInternshipDetailsPO = new ProjectInternshipDetailsPO(driver);

        Reporter.log("Step 1 -  Login with consultant user and edit internship engagement in created project");
        new LoginFunctions(driver, selenium).loginByGivenUser(consultantUser);
        selenium.navigateToPage(strConsulInternshipUrl);

        selenium.hardWait(3);

        new InternshipEngagementsFunctions(driver, selenium).editProjectInternshipEngagements(consultantUser);
        String newVersionFileName = JavaHelpers.getPropertyValue(PROPERTY_TEST_FILE, "newVersionFile");

        Reporter.log("Step 2 - Verify that project internship is edited by consultant user");
        Assert.assertEquals(projectInternshipDetailsPO.getInternshipStartEndDate(), new JavaHelpers().changeDateFormat(consulInternUpdateData.getInternshipStartDate(), "MM/dd/yyyy", "M/d/yyyy") + "\n" + " - " + "\n" + new JavaHelpers().changeDateFormat(consulInternUpdateData.getInternshipEndDate(), "MM/dd/yyyy", "M/d/yyyy"), "Internship start end date doesn't match :");
        Assert.assertEquals(ProjectInternPosition.Administration.getInternPosition(), projectInternshipDetailsPO.getInternPosition(), "internship position doesn't match : ");
        Assert.assertEquals(newVersionFileName, projectInternshipDetailsPO.getUploadedFileText(), "uploaded file name doesn't match : ");
        Assert.assertEquals(projectInternshipDetailsPO.getMethodologiesText(), ProjectInternshipMethodologies.OneStory.getInternshipMethodology() + "\n" + ProjectInternshipMethodologies.Render.getInternshipMethodology(), "internship methodologies doesn't match : ");
        Assert.assertEquals(projectInternshipDetailsPO.getMentorText(), consultantUser.getRealFirstName() + " " + consultantUser.getRealLastName(), "internship mentor name doesn't match : ");
        Assert.assertEquals(projectInternshipDetailsPO.getGrowthPlanCompleteDate(), new JavaHelpers().changeDateFormat(consulInternUpdateData.getGrowthPlanCompleteDate(), "MM/dd/yyyy", "M/d/yyyy"), "Internship growth plan complete date  doesn't match :");

        Reporter.log("Step 3 - Verify that project internship DisbursementCompleteDateButton, GrowthPlanCompleteDateButton is not enabled");
        String disableEditButtonColor = "rgba(0, 0, 0, 0.26)";
        Assert.assertEquals(disableEditButtonColor, projectInternshipDetailsPO.getEditButtonColor(), "Edit button color doesn't match : ");
        Assert.assertFalse(projectInternshipDetailsPO.isDisbursementCompleteDateButtonEnabled(), "Internship engagement disbursement complete date button is enabled : ");//Bug

    }

    /*Test 15 : Verify that consultant can read member of the location and can't edit project location */  //Todo bug(#2180) No read access
    @Test(priority = 15)
    public void VerifyThatConsultantCanReadMemberOfTheLocationAndCanNotEditProjectLocation() throws InterruptedException {

        HeaderPO headerPO = new HeaderPO(driver);
        ProjectDetailsPO projectDetailsPO = new ProjectDetailsPO(driver);
        ProjectsListingPO projectsListingPO = new ProjectsListingPO(driver);
        ProjectDetailsUpdateLocationAndFieldRegionPO projectDetailLocationFieldRegionPO = new ProjectDetailsUpdateLocationAndFieldRegionPO(driver);
        ProjectDetailsUpdateLocationAndFieldRegionPO projectDetailsUpdateLocationAndFieldRegionPO = new ProjectDetailsUpdateLocationAndFieldRegionPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with consultant user");
        new LoginFunctions(driver, selenium).loginByGivenUser(adminUser);

        Reporter.log("Step 2 - Search added member project and click on location button");
        if (!projectsListingPO.isProjectPresent(consulLocationProjectName)) {
            headerPO.searchItemGloballyByName(consulLocationProjectName);
        }
        projectsListingPO.clickOnProjectName(consulLocationProjectName);

        Reporter.log("Step 3 - Click on project location and field region button and try to edit with admin user ");
        projectDetailsPO.clickProjectLocationAndFieldRegionButton();
        projectDetailLocationFieldRegionPO.fillLocationAndFieldRegionDetailsAndClickOnSaveButton(consulEditProjectAdminData);

        Reporter.log("Step 4 - sign out the admin user and login with consultant user");
        new LoginFunctions(driver, selenium).signOutAndChangeUser(consultantUser);

        Reporter.log("Step 5 - Search added member project and click on location button");
        if (!projectsListingPO.isProjectPresent(consulLocationProjectName)) {
            headerPO.searchItemGloballyByName(consulLocationProjectName);
        }
        projectsListingPO.clickOnProjectName(consulLocationProjectName);
        projectDetailsPO.clickProjectLocationAndFieldRegionButton();

        Reporter.log("Step 6 - Verify the location textBox is editable or not");
        Assert.assertFalse(projectDetailsUpdateLocationAndFieldRegionPO.isLocationTextBoxEnabled(), "Location textBox is enable");
    }

    /*Test 16 : Verify that consultant can not create language partner project and location */
    @Test(priority = 16)
    public void VerifyThatConsultantCanNotCreateLanguagePartnerProjectAndLocation() throws InterruptedException {

        LeftNavPO leftNavPO = new LeftNavPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with consultant user");
        new LoginFunctions(driver, selenium).loginByGivenUser(consultantUser);

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

    /*Test 17 : Verify that consultant can not create project language engagements */
    @Test(priority = 17)
    public void VerifyThatConsultantCanNotCreateProjectLanguageEngagements() throws InterruptedException {

        ProjectDetailsPO projectDetailsPO = new ProjectDetailsPO(driver);
        ProjectLanguageEngagementDetailsPO projectLanguageEngagementDetailsPO = new ProjectLanguageEngagementDetailsPO(driver);
        ProjectDetailsCreateLanguageEngagementPO projectDetailsCreateLanguageEngagementPO = new ProjectDetailsCreateLanguageEngagementPO(driver);

        Reporter.log("Step 1 - Login with consultant user");
        new LoginFunctions(driver, selenium).loginByGivenUser(consultantUser);

        Reporter.log("Step 2 - Try to create language engagement with consultant user");
        new LanguageFunctions(driver, selenium).createProjectLanguageEngagements(consulLanguageAdminData, consulCreatedTranslationProjectName, consultantUser);

        String searchedLanguageEngagementName = "No options";

        Reporter.log("Step 3 - Verify that user can not create language engagement");
        Assert.assertEquals(searchedLanguageEngagementName, projectDetailsCreateLanguageEngagementPO.getSearchedLanguageEngagementText(), "project language engagement error message doesn't match : ");

        Reporter.log("Step 4 - Navigate on created language engagement url");
        selenium.navigateToPage(strConsulLanguageTranslationUrl);

        Reporter.log("Step 5 - Verify that user can read language engagement");
        projectDetailsPO.clickOnViewDetailsLanguageEngagementButton(consulLanguageAdminData.getLanguageEngagementName());
        Assert.assertEquals(consulLanguageAdminData.getLanguageEngagementName(), projectLanguageEngagementDetailsPO.geLanguageEngagementText(), "project language engagement error message doesn't match : ");
    }

    /*Test 18 : Verify that consultant can not add project goals */
    @Test(priority = 18)
    public void VerifyThatConsultantCanNotAddProjectGoalsSuccessfully() { //Todo bug(#2183) No create access

        ProjectLanguageEngagementDetailsPO projectLanguageEngagementDetailsPO = new ProjectLanguageEngagementDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with consultant user");
        new LoginFunctions(driver, selenium).loginByGivenUser(consultantUser);
        selenium.navigateToPage(strConsulProjectCreateGoalUrl);

        Reporter.log("Step 2 - Verify the add goal button is present or not");
        Assert.assertFalse(projectLanguageEngagementDetailsPO.isAddGoalButtonPresent(), "Add goal button is present");
    }

    /*Test 19 : Verify that consultant can read created project goals when member of the project*/  //Todo bug(#2184) No read access
    @Test(priority = 19)
    public void VerifyThatConsultantCanReadProjectGoalsWhenMemberOfTheProject() throws InterruptedException {

        CreateGoalPO createGoalPO = new CreateGoalPO(driver);
        ProjectLanguageEngagementDetailsPO projectLanguageEngagementDetailsPO = new ProjectLanguageEngagementDetailsPO(driver);

        Reporter.log("Step 1 - Navigate to url and login with consultant user");
        new LoginFunctions(driver, selenium).loginByGivenUser(consultantUser);
        selenium.navigateToPage(strConsulProjectCreateGoalUrl);

        selenium.hardWait(3);

        Reporter.log("Step 2 - Click on goal edit button");
        projectLanguageEngagementDetailsPO.clickOnGoalCard();
        projectLanguageEngagementDetailsPO.clickEditGoalButton();

        Reporter.log("Step 3 - Verify that consultant can read created project goals");
        String disableGoalColor = Constants.disableGrayColorCode;
        Assert.assertEquals(disableGoalColor, createGoalPO.getDisabledEditGoalDetailsColor(), "Goal detail color doesn't match : ");
    }

    /*Test 20 : Verify that consultant can not edit projects */ //Todo bug(#2185) No update access
    @Test(priority = 20)
    public void VerifyThatConsultantCanNotEditProjectsSuccessfully() throws InterruptedException {

        LeftNavPO sideHeader = new LeftNavPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);
        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectsListingPO projectsListingPO = new ProjectsListingPO(driver);
        ProjectDetailsUpdateStepsPO projectDetailsUpdateStepsPO = new ProjectDetailsUpdateStepsPO(driver);
        ProjectDetailsUpdateEstimatedSubmissionDatePO projectDetailsUpdateEstimatedSubmissionDatePO = new ProjectDetailsUpdateEstimatedSubmissionDatePO(driver);
        ProjectDetailsUpdateStartEndDatePO projectDetailsUpdateStartEndDatePO = new ProjectDetailsUpdateStartEndDatePO(driver);
        ProjectDetailsUpdateLocationAndFieldRegionPO projectDetailsUpdateLocationAndFieldRegionPO = new ProjectDetailsUpdateLocationAndFieldRegionPO(driver);

        Reporter.log("Step 1 -  Login with consultant user and edit created project");
        new LoginFunctions(driver, selenium).loginByGivenUser(consultantUser);

        Reporter.log("Step 2 - Verify that project is displayed which is edited by consultant user ");
        sideHeader.clickOnProjectsMenu();
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());

        if (!projectsListingPO.isProjectPresent(consulAdminData.getProjectName())) {
            headerPO.searchItemGloballyByName(consulAdminData.getProjectName());
        }
        projectsListingPO.clickOnProjectName(consulAdminData.getProjectName());

        Reporter.log("Step 3 - Click on project location and field region button and try to edit with consultant user ");
        projectDetails.clickProjectLocationAndFieldRegionButton();

        Reporter.log("Step 4 - Verify that consultant can't edit location and field region");
        Assert.assertFalse(projectDetailsUpdateLocationAndFieldRegionPO.isFieldRegionTextBoxEnabled(), "project field region textBox is enabled : ");
        Assert.assertFalse(projectDetailsUpdateLocationAndFieldRegionPO.isLocationTextBoxEnabled(), "project location textBox is enabled : ");

        Reporter.log("Step 5 - Click on location field region close button");
        projectDetailsUpdateLocationAndFieldRegionPO.clickOnLocationFieldRegionCloseButton();

        Reporter.log("Step 6 - Click on project start end date button");
        projectDetails.clickProjectStartEndDateButton();

        Reporter.log("Step 7 - Verify that consultant can't edit start end date");
        Assert.assertFalse(projectDetailsUpdateStartEndDatePO.isStartDateTextBoxEnabled(), "project start date textBox is enabled : ");
        Assert.assertFalse(projectDetailsUpdateStartEndDatePO.isEndDateTextBoxEnabled(), "project end date textBox is enabled: ");

        Reporter.log("Step 8 - Click on start end date close button");
        projectDetailsUpdateStartEndDatePO.clickOnStartEndDateCloseButton();

        Reporter.log("Step 9 - Click on project estimated submission date button and try to edit with consultant user ");
        projectDetails.clickProjectEstimatedSubmissionButton();

        Reporter.log("Step 10 - Verify that consultant can't edit estimated submission date");
        Assert.assertFalse(projectDetailsUpdateEstimatedSubmissionDatePO.isEstimatedSubmissionDateTextBoxEnabled(), "project estimated submission date textBox is enabled:");

        Reporter.log("Step 11 - Click on estimated submission date close button");
        projectDetailsUpdateEstimatedSubmissionDatePO.clickOnEstimatedSubmissionDateCloseButton();

        Reporter.log("Step 12 - Click on project status button and try to edit with consultant user ");
        projectDetails.clickOnProjectStepByName(ProjectStep.EarlyConversations.getStep());

        Reporter.log("Step 13 - Verify that consultant can't edit status");
        String statusErrorMessage = "The status of this project is unable to be changed.";
        Assert.assertEquals(statusErrorMessage, projectDetailsUpdateStepsPO.getProjectStatusError(), "project status error text doesn't match");
    }

}

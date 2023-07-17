package funcLibs.project;

import datafactory.LanguageData;
import dataobjects.Language;
import dataobjects.Project;
import dataobjects.User;
import enums.menu.CreateItemTypes;
import enums.project.ProjectStep;
import enums.project.ProjectSubTabs;
import enums.project.Sensitivity;
import enums.users.UserRole;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import pageobjects.cord.account.LoginPO;
import pageobjects.cord.common.HeaderPO;
import pageobjects.cord.common.LeftNavPO;
import pageobjects.cord.projects.*;
import utilities.Constants;
import utilities.SeleniumHelpers;

public class ProjectFunctions {

    protected WebDriver driver;
    protected SeleniumHelpers selenium;

    public ProjectFunctions(WebDriver driver, SeleniumHelpers selenium) {
        this.driver = driver;
        this.selenium = selenium;
    }

    /**
     * create project
     *
     * @param data ProjectData
     */

    public void createProject(dataobjects.Project data) {

        try {
            CreateEditProjectPO project = new CreateEditProjectPO(driver);
            LeftNavPO sideHeader = new LeftNavPO(driver);

            Reporter.log("Step 1- Click on crate new item and select project menu");
            sideHeader.clickOnCreateNewItemAndSelectType(CreateItemTypes.Project.toString());

            Reporter.log("Step 2- Enter valid detail of project and click on submit button");
            project.fillProjectDetailAndClickOnSubmitButton(data);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * add team member to project
     *
     * @param user user
     * @param data ProjectData
     */

    public void addMemberToProject(User user, dataobjects.Project data) {

        try {
            LeftNavPO sideHeader = new LeftNavPO(driver);
            ProjectsListingPO projectsListingPO = new ProjectsListingPO(driver);
            HeaderPO headerPO = new HeaderPO(driver);
            ProjectDetailsTeamMembersListingPO projectDetailsTeamMembersListingPO = new ProjectDetailsTeamMembersListingPO(driver);
            ProjectDetailsTeamMembersAddTeamMemberPO projectMemberAddTeamMembersPO = new ProjectDetailsTeamMembersAddTeamMemberPO(driver);
            ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);

            sideHeader.clickOnProjectsMenu();
            if (!projectsListingPO.isProjectPresent(data.getProjectName())) {
                headerPO.searchItemGloballyByName(data.getProjectName());
            }
            projectsListingPO.clickOnProjectName(data.getProjectName());

            projectDetails.clickOnTeamMemberButton();
            projectDetailsTeamMembersListingPO.clickOnAddTeamMemberButton();
            projectMemberAddTeamMembersPO.clearAndFillAddTeamMember(user);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * edit project by name
     *
     * @param data        ProjectData
     * @param projectName created Project Name
     */

    public void editInternshipProjectByName(Project data, String projectName, User user) {

        try {
            LeftNavPO sideHeader = new LeftNavPO(driver);
            HeaderPO headerPO = new HeaderPO(driver);
            ProjectsListingPO projectsListingPO = new ProjectsListingPO(driver);
            ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
            CreateEditProjectPO project = new CreateEditProjectPO(driver);
            ProjectDetailsUpdateStepsPO projectDetailsUpdateStepsPO = new ProjectDetailsUpdateStepsPO(driver);
            ProjectDetailsUpdateSensitivityPO projectDetailsSensitivity = new ProjectDetailsUpdateSensitivityPO(driver);
            ProjectDetailsUpdateLocationAndFieldRegionPO projectDetailLocationFieldRegionPO = new ProjectDetailsUpdateLocationAndFieldRegionPO(driver);
            ProjectDetailsUpdateStartEndDatePO projectDetailStartEndDatePO = new ProjectDetailsUpdateStartEndDatePO(driver);
            ProjectDetailsUpdateEstimatedSubmissionDatePO projectDetailEstimatedSubmissionDatePO = new ProjectDetailsUpdateEstimatedSubmissionDatePO(driver);

            Reporter.log("Step 1 - Find created project");
            sideHeader.clickOnProjectsMenu();
            if (!projectsListingPO.isProjectPresent(projectName)) {
                headerPO.searchItemGloballyByName(projectName);
            }
            projectsListingPO.clickOnProjectName(projectName);

            Reporter.log("Step 2 - Click on edit icon and update project name");
            projectDetails.clickOnEditButton();
            project.fillProjectDetailsAndClickOnSaveButton(data);

            if (user.getUserRoles().contains(UserRole.FieldOperationsDirector.getRole()) || user.getUserRoles().contains(UserRole.Administrator.getRole())) {
                projectDetails.clickOnProjectStepByName(ProjectStep.EarlyConversations.getStep());
                projectDetailsUpdateStepsPO.advanceProjectByStepName(ProjectStep.SubmitforConceptApproval.getStep());
            }
            Reporter.log("Step 3 - Click on sensitivity button and update project sensitivity");
            projectDetails.clickOnProjectSensitivityButton();
            projectDetailsSensitivity.clickOnProjectSensitivityRadioButtonByName(Sensitivity.Low);

            Reporter.log("Step 4 - Click on location field region  button and update project location and field region");
            projectDetails.clickProjectLocationAndFieldRegionButton();
            projectDetailLocationFieldRegionPO.fillLocationAndFieldRegionDetailsAndClickOnSaveButton(data);

            Reporter.log("Step 5 - Click on start end date button and update project start end date");
            projectDetails.clickProjectStartEndDateButton();
            projectDetailStartEndDatePO.fillStartEndDateDetailsAndClickOnSaveButton(data);

            Reporter.log("Step 6 - Click on estimated submission date button and update project estimated submission date");
            projectDetails.clickProjectEstimatedSubmissionButton();
            projectDetailEstimatedSubmissionDatePO.fillEstimatedSubmissionDateDetailsAndClickOnSaveButton(data);


        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Click on project status
     */

    public void clickOnProjectStatus() {

        try {

            ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);

            Reporter.log("Step 1 - Click on Project status button");
            projectDetails.clickOnProjectStepByName(ProjectStep.EarlyConversations.getStep());

        } catch (Exception e) {
            System.out.println(e);
        }
    }


    /**
     * Create New Project
     *
     * @param user user
     * @param data project data
     * @throws InterruptedException exception
     */
    public void createProjectAndGotoProjectDetailsPage(User user, dataobjects.Project data) throws InterruptedException {

        try {
            LoginPO login = new LoginPO(driver);
            CreateEditProjectPO project = new CreateEditProjectPO(driver);
            LeftNavPO sideHeader = new LeftNavPO(driver);
            ProjectsListingPO projectsListingPO = new ProjectsListingPO(driver);
            HeaderPO headerPO = new HeaderPO(driver);

            Reporter.log("Step 1 - Navigate to admin login page");
            selenium.navigateToPage(Constants.URL);

            Reporter.log("Step 2 - Entering valid sign in details and login to application");
            login.fillLoginDetailsAndPerformLogin(user.getEmail(), user.getPassword());

            Reporter.log("Step 3- Click on crate new item and select project menu");
            sideHeader.clickOnCreateNewItemAndSelectType(CreateItemTypes.Project.toString());

            Reporter.log("Step 4- Enter valid detail of project and click on submit button");
            project.fillProjectDetailAndClickOnSubmitButton(data);

            Reporter.log("Step 5- Verify that Project is created successfully and go to project detail page from project listing");
            sideHeader.clickOnProjectsMenu();
            projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());

            if (!projectsListingPO.isProjectPresent(data.getProjectName())) {
                headerPO.searchItemGloballyByName(data.getProjectName());
            }
            projectsListingPO.clickOnProjectName(data.getProjectName());

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * create project language engagement
     */
    public void createLanguageEngagementAndGotoLanguageEngagementDetailsPage() {

        try {
            ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
            ProjectDetailsCreateLanguageEngagementPO projectDetailsCreateLanguageEngagementPO = new ProjectDetailsCreateLanguageEngagementPO(driver);

            Reporter.log("Step 1 - Click on add language engagement button and create language engagement");
            Language languageData = new LanguageData().getLanguageData();
            projectDetails.clickOnLanguageEngagementButton();
            projectDetailsCreateLanguageEngagementPO.fillLanguageEngagementDetailAndClickOnSubmitButton(languageData);

            Reporter.log("Step 2 - Click on view details button");
            projectDetails.clickOnViewDetailsLanguageEngagementButton(languageData.getLanguageEngagementName());
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

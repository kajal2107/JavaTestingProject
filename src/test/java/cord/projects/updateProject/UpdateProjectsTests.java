package cord.projects.updateProject;

import base.BaseTest;
import datafactory.ProjectData;
import dataobjects.Project;
import enums.project.ProjectPostsCategory;
import enums.project.ProjectPostsShareability;
import enums.project.ProjectSubTabs;
import enums.project.Sensitivity;
import funcLibs.project.ProjectFunctions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import pageobjects.cord.common.HeaderPO;
import pageobjects.cord.common.LeftNavPO;
import pageobjects.cord.projects.*;
import utilities.Constants;
import utilities.JavaHelpers;

import java.text.ParseException;

public class UpdateProjectsTests extends BaseTest {

    public static final String PROPERTY_TEST_FILE = "src/main/resources/testData/TestFiles.properties";

    /*Test 1 : Verify that user can update project successfully*/
    @Test
    public void verifyUserCanUpdateProjectSuccessfully() throws InterruptedException, ParseException, org.json.simple.parser.ParseException {

        HeaderPO headerPO = new HeaderPO(driver);
        LeftNavPO sideHeader = new LeftNavPO(driver);
        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        CreateEditProjectPO createProject = new CreateEditProjectPO(driver);
        ProjectsListingPO projectsListingPO = new ProjectsListingPO(driver);
        ProjectDetailsDeletePostsPO projectDetailsDeletePostsPO = new ProjectDetailsDeletePostsPO(driver);
        ProjectDetailsUpdateSensitivityPO projectDetailsSensitivity = new ProjectDetailsUpdateSensitivityPO(driver);
        ProjectDetailsUpdateStartEndDatePO projectDetailStartEndDatePO = new ProjectDetailsUpdateStartEndDatePO(driver);
        ProjectDetailsCreateInternshipPO projectDetailsCreateInternshipPO = new ProjectDetailsCreateInternshipPO(driver);
        ProjectDetailsUpdateLocationAndFieldRegionPO projectDetailLocationFieldRegionPO = new ProjectDetailsUpdateLocationAndFieldRegionPO(driver);
        ProjectDetailsUpdateEstimatedSubmissionDatePO projectDetailEstimatedSubmissionDatePO = new ProjectDetailsUpdateEstimatedSubmissionDatePO(driver);

        Reporter.log("Step 1 - Create Project");
        Project data = new ProjectData().getCreateProjectData();
        new ProjectFunctions(driver, selenium).createProjectAndGotoProjectDetailsPage(fpmUser, data);

        Reporter.log("Step 2 - Click on edit icon and update project name");
        projectDetails.clickOnEditButton();
        Project updateData = new ProjectData().getUpdateProjectData();
        createProject.fillProjectDetailsAndClickOnSaveButton(updateData);

        Reporter.log("Step 3 - Click on pin button and pinned the project");
        projectDetails.clickOnPinButton();

        Reporter.log("Step 4 - Click on sensitivity button and update project sensitivity");
        projectDetails.clickOnProjectSensitivityButton();
        projectDetailsSensitivity.clickOnProjectSensitivityRadioButtonByName(Sensitivity.Low);

        Reporter.log("Step 5 - Click on location field region  button and update project location and field region");
        projectDetails.clickProjectLocationAndFieldRegionButton();
        projectDetailLocationFieldRegionPO.fillLocationAndFieldRegionDetailsAndClickOnSaveButton(updateData);

        Reporter.log("Step 6 - Click on start end date button and update project start end date");
        projectDetails.clickProjectStartEndDateButton();
        projectDetailStartEndDatePO.fillStartEndDateDetailsAndClickOnSaveButton(updateData);

        Reporter.log("Step 7 - Click on start end date button and update project start end date");
        projectDetails.clickProjectEstimatedSubmissionButton();
        projectDetailEstimatedSubmissionDatePO.fillEstimatedSubmissionDateDetailsAndClickOnSaveButton(updateData);

        Reporter.log("Step 8 - verify that project is updated successfully");
        Assert.assertEquals(projectDetails.getProjectName(), updateData.getProjectName(), "Project is not present ");
        Assert.assertEquals(Sensitivity.Low.toString(), projectDetails.getProjectSensitivity(), "project sensitivity doesn't match : ");
        Assert.assertEquals(updateData.getLocationName() + " | " + updateData.getFieldRegionName(), projectDetails.getProjectLocationAndFieldRegion(), "project location and field region doesn't match : ");
        Assert.assertEquals(projectDetails.getProjectStartEndDate(),new JavaHelpers().changeDateFormat(updateData.getStartDate(), "MM/dd/yyyy", "M/d/yyyy") + "\n" + " - " + "\n" + new JavaHelpers().changeDateFormat(updateData.getEndDate(), "MM/dd/yyyy", "M/d/yyyy"), "project start end date doesn't match : ");
        Assert.assertEquals(new JavaHelpers().changeDateFormat(updateData.getEstimatedSubmissionDate(), "MM/dd/yyyy", "M/d/yyyy"), projectDetails.getProjectEstimatedSubmissionDate(), "project estimated submission date doesn't match : ");

        Reporter.log("Step 9 - Click on upload file button and add file");
        String textFileName = JavaHelpers.getPropertyValue(PROPERTY_TEST_FILE, "textFile");
        projectDetails.clickOnUploadFileButton(textFileName);
        selenium.hardWait(5);
        Assert.assertEquals(textFileName, projectDetails.getUploadedFileName(), "uploaded file name doesn't match : ");

        Reporter.log("Step 10 - Click on add intern engagement button and create internship engagement");
        projectDetails.clickOnInternEngagementButton();
        projectDetailsCreateInternshipPO.fillCreateInternshipDetail(fpmUser);
        Assert.assertEquals(projectDetails.getInternshipName(fpmUser.getRealFirstName() + " " + fpmUser.getRealLastName()), (fpmUser.getRealFirstName() + " " + fpmUser.getRealLastName()), "project internship name doesn't match : ");

        Reporter.log("Step 11 - Click on add posts button and add new post");
        Project postsData = new ProjectData().getCreateProjectData();
        projectDetails.clickOnAddPostsButton();
        projectDetails.clickOnPostsCategoryDropDown();
        projectDetails.clickOnProjectPostsCategory(ProjectPostsCategory.Prayer.getProjectPostsCategory());

        projectDetails.clickOnPostsShareabilityDropDown();
        projectDetails.clickOnProjectPostsShareability(ProjectPostsShareability.External.getProjectPostsShareability());
        projectDetails.clearAndFillPostsBox(postsData);

        Reporter.log("Step 12 - verify that project Post is created successfully");
        String postsShareabilityText = Constants.postsShareabilityText;
        Assert.assertEquals(projectDetails.getPostCategoryText(), ProjectPostsCategory.Prayer.getProjectPostsCategory(), "Project post category text doesn't match");
        Assert.assertEquals(postsShareabilityText,projectDetails.getPostsShareabilityText(), "Project post shareability text doesn't match");
        Assert.assertEquals(projectDetails.getPostsText(), postsData.getProjectPosts(), "Project post text doesn't match");

        Reporter.log("Step 13 - Click on posts toggle button and click on the edit post button");
        projectDetails.clickOnPostsToggleButton();
        projectDetails.clickOnPostsEditButton();

        Reporter.log("Step 14 - Update the posts details");
        String postsUpdatedShareabilityText = Constants.postsUpdatedShareabilityText;
        projectDetails.clickOnPostsCategoryDropDown();
        projectDetails.clickOnProjectPostsCategory(ProjectPostsCategory.Story.getProjectPostsCategory());

        projectDetails.clickOnPostsShareabilityDropDown();
        projectDetails.clickOnProjectPostsShareability(ProjectPostsShareability.Internal.getProjectPostsShareability());
        projectDetails.clearAndFillPostsBox(postsData);

        Reporter.log("Step 15 - verify that project Post is updated successfully");
        Assert.assertEquals(projectDetails.getPostCategoryText(), ProjectPostsCategory.Story.getProjectPostsCategory(), "Project post updated category text doesn't match");
        Assert.assertEquals(postsUpdatedShareabilityText,projectDetails.getPostsShareabilityText(), "Project post updated shareability text doesn't match");
        Assert.assertEquals(projectDetails.getPostsText(), postsData.getProjectPosts(), "Project post updated text doesn't match");

        Reporter.log("Step 16 - Click on posts toggle button and click on the delete post button");
        projectDetails.clickOnPostsToggleButton();
        projectDetails.clickOnPostsDeleteButton();
        projectDetailsDeletePostsPO.clickProjectPostDeleteConfirmationButton();

        Reporter.log("Step 17 - verify that project Post is deleted successfully");
        Assert.assertFalse(projectDetails.isPostsPresent(ProjectPostsCategory.Story.getProjectPostsCategory()), "Project posts is present");

        Reporter.log("Step 18 - Click on project menu and pinned tabs");
        sideHeader.clickOnProjectsMenu();
        projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.Pinned.getProjectSubTabs());

        Reporter.log("Step 19 - Search the project");
        if (!projectsListingPO.isProjectPresent(updateData.getProjectName())) {
            headerPO.searchItemGloballyByName(updateData.getProjectName());
        }

        Reporter.log("Step 20 - Verify the project is pinned");
        String pinnedButtonColor = Constants.disableGrayColorCode;
        Assert.assertEquals(pinnedButtonColor, projectsListingPO.getProjectPinnedButtonColor(updateData.getProjectName()), "Project pinned button color doesn't match");
    }
}

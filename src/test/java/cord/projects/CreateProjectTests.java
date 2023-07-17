package cord.projects;

import base.BaseTest;
import datafactory.ProjectData;
import dataobjects.Project;
import funcLibs.project.ProjectFunctions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import pageobjects.cord.projects.ProjectDetailsPO;

public class CreateProjectTests extends BaseTest {


    /*Test 1 : Verify that user can create project successfully*/
    @Test
    public void verifyUserCanCreateProjectSuccessfully() throws InterruptedException {

        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);

        Reporter.log("Step 1 - Create Project");
        Project data = new ProjectData().getCreateTranslationProjectData();
        new ProjectFunctions(driver, selenium).createProjectAndGotoProjectDetailsPage(fpmUser, data);

        Reporter.log("Step 2 - Verifying project details");
        Assert.assertEquals(projectDetails.getProjectName(), data.getProjectName(), "Project is not present : " + data.getProjectName());
    }

}

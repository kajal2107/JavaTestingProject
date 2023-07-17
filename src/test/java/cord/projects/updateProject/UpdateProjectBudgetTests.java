package cord.projects.updateProject;

import base.BaseTest;
import datafactory.ProjectData;
import dataobjects.Project;
import funcLibs.project.ProjectFunctions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import pageobjects.cord.projects.ProjectDetailsBudgetListingPO;
import pageobjects.cord.projects.ProjectDetailsPO;
import utilities.JavaHelpers;

import java.text.ParseException;

public class UpdateProjectBudgetTests extends BaseTest {
    public static final String PROPERTY_TEST_FILE = "src/main/resources/testData/TestFiles.properties";

    /*Test 1 : verify User Can Update Project Budget Successfully*/
    @Test
    public void verifyUserCanUpdateProjectBudgetSuccessfully() throws InterruptedException, ParseException {

        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectDetailsBudgetListingPO projectDetailsBudgetListingPO = new ProjectDetailsBudgetListingPO(driver);

        Reporter.log("Step 1 - Create Project");
        Project data = new ProjectData().getCreateProjectData();
        new ProjectFunctions(driver, selenium).createProjectAndGotoProjectDetailsPage(adminUser, data);

        Reporter.log("Step 2 - Click on budget detail button and add template file");
        projectDetails.clickOnBudgetButton();
        String textFileName = JavaHelpers.getPropertyValue(PROPERTY_TEST_FILE, "textFile");
        projectDetailsBudgetListingPO.addBudgetTemplateFile(textFileName);
        selenium.hardWait(5);

        Reporter.log("Step 3 - verify that project budget file added successfully");
        Assert.assertEquals(projectDetailsBudgetListingPO.getUploadedTemplateFileName(), textFileName, "project budget template file name doesn't match : ");
    }

}

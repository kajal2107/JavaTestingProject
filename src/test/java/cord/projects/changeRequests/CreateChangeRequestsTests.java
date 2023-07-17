package cord.projects.changeRequests;

import base.BaseTest;
import datafactory.ChangeRequestData;
import datafactory.ProjectData;
import dataobjects.ChangeRequest;
import dataobjects.Project;
import enums.changeRequest.ChangeRequestTypesDropDown;
import enums.project.ProjectStep;
import funcLibs.project.ProjectFunctions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import pageobjects.cord.changeRequest.CreateChangeRequestDetailsPO;
import pageobjects.cord.changeRequest.CreateChangeRequestPO;
import pageobjects.cord.projects.ProjectDetailsPO;
import pageobjects.cord.projects.ProjectDetailsUpdateStepsPO;

public class CreateChangeRequestsTests extends BaseTest {


    /*Test 1 : Verify that user can create project change requests successfully*/
    @Test
    public void verifyUserCanCreateProjectChangeRequestSuccessfully() throws InterruptedException {

        ProjectDetailsPO projectDetailsPO = new ProjectDetailsPO(driver);
        ProjectDetailsUpdateStepsPO projectDetailsUpdateStepsPO = new ProjectDetailsUpdateStepsPO(driver);
        CreateChangeRequestDetailsPO createChangeRequestDetailsPO = new CreateChangeRequestDetailsPO(driver);
        CreateChangeRequestPO createChangeRequestPO = new CreateChangeRequestPO(driver);
        ChangeRequest changeRequestData = new ChangeRequestData().getCreateChangeRequestData();

        Reporter.log("Step 1 - Create Project");
        Project data = new ProjectData().getCreateTranslationProjectData();
        new ProjectFunctions(driver, selenium).createProjectAndGotoProjectDetailsPage(adminUser, data);

        Reporter.log("Step 2 - Advance Project step from 'Early Conversations' to 'Pending Concept Approval'");
        projectDetailsPO.clickOnProjectStepByName(ProjectStep.EarlyConversations.getStep());
        projectDetailsUpdateStepsPO.clickProjectOverrideStatusByName(ProjectStep.Active.getStep());

        Reporter.log("Step 3 - Click on view change request button");
        projectDetailsPO.clickOnViewChangeRequestButton();

        Reporter.log("Step 4 - Click on create change request button");
        createChangeRequestDetailsPO.clickOnCreateChangeRequestButton();

        Reporter.log("Step 5 - Click on create change request types dropdown and choose types");
        createChangeRequestPO.fillChangeRequestDetails(ChangeRequestTypesDropDown.Goal.getChangeRequestsTypesDropDown(),changeRequestData);

        Reporter.log("Step 6 - Verify create change request details");
        Assert.assertEquals(createChangeRequestDetailsPO.getChangeRequestTypesText(),ChangeRequestTypesDropDown.Goal.getChangeRequestsTypesDropDown(), "Change request type is not matched");
        Assert.assertEquals(createChangeRequestDetailsPO.getChangeRequestSummaryText(),changeRequestData.getSummaryText(), "Change request summary is not matched");
    }

}

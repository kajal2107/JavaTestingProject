package cord.projects.approvalFlow;

import base.BaseTest;
import datafactory.ProjectData;
import dataobjects.Project;
import enums.project.ProjectStep;
import funcLibs.project.ProjectFunctions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import pageobjects.cord.account.LoginPO;
import pageobjects.cord.common.HeaderPO;
import pageobjects.cord.common.LeftNavPO;
import pageobjects.cord.projects.*;

public class CreateProjectControllerTests extends BaseTest {


    /*Test 1 : Verify that user having controller can advance project step from 'Early Conversations' to 'Completed'*/
    @Test
    public void verifyControllerCanTakeProjectToCompleted() throws InterruptedException {

        LoginPO login = new LoginPO(driver);
        LeftNavPO sideHeader = new LeftNavPO(driver);
        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectsListingPO projectsListingPO = new ProjectsListingPO(driver);
        ProjectDetailsPO projectDetailsPO = new ProjectDetailsPO(driver);
        ProjectDetailsUpdateStepsPO projectDetailsUpdateStepsPO = new ProjectDetailsUpdateStepsPO(driver);
        ProjectDetailsTeamMembersListingPO projectDetailsTeamMembersListingPO = new ProjectDetailsTeamMembersListingPO(driver);
        ProjectDetailsTeamMembersAddTeamMemberPO projectMemberAddTeamMembersPO = new ProjectDetailsTeamMembersAddTeamMemberPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);

        Reporter.log("Step 1 - Create Project");
        Project data = new ProjectData().getCreateProjectData();
        new ProjectFunctions(driver, selenium).createProjectAndGotoProjectDetailsPage(adminUser, data);

        Reporter.log("Step 2 - Click on team members button and assign project to the consultant");
        projectDetails.clickOnTeamMemberButton();
        projectDetailsTeamMembersListingPO.clickOnAddTeamMemberButton();
        projectMemberAddTeamMembersPO.clearAndFillAddTeamMember(controllerUser);

        Reporter.log("Step 3 - Find Project created by controller and go to project detail page from project listing");
        sideHeader.clickOnProjectsMenu();
        headerPO.searchItemGloballyByName(data.getProjectName());
        projectsListingPO.clickOnProjectName(data.getProjectName());

        Reporter.log("Step 4 - Advance Project step from 'Early Conversations' to 'Pending Concept Approval'");
        projectDetailsPO.clickOnProjectStepByName(ProjectStep.EarlyConversations.getStep());
        projectDetailsUpdateStepsPO.advanceProjectByStepName(ProjectStep.SubmitforConceptApproval.getStep());

        Reporter.log("Step 5 - Advance Project step from 'Pending Concept Approval' to 'Prep For Consultant Endorsement'");
        projectDetailsPO.clickOnProjectStepByName(ProjectStep.PendingConceptApproval.getStep());
        projectDetailsUpdateStepsPO.advanceProjectByStepName(ProjectStep.ApproveConcept.getStep());

        Reporter.log("Step 6 - Advance Project step from 'Prep For Consultant Endorsement' to 'Pending Consultant Endorsement'");
        projectDetailsPO.clickOnProjectStepByName(ProjectStep.PrepForConsultantEndorsement.getStep());
        projectDetailsUpdateStepsPO.advanceProjectByStepName(ProjectStep.SubmitforConsultantEndorsement.getStep());

        Reporter.log("Step 7 - Advance Project step from 'Pending Consultant Endorsement' to 'Prep For Financial Endorsement'");
        projectDetailsPO.clickOnProjectStepByName(ProjectStep.PendingConsultantEndorsement.getStep());
        projectDetailsUpdateStepsPO.advanceProjectByStepName(ProjectStep.EndorsePlan.getStep());

        Reporter.log("Step 8 - Advance Project step from 'Prep For Financial Endorsement' to 'Pending Financial Endorsement'");
        projectDetailsPO.clickOnProjectStepByName(ProjectStep.PrepForFinancialEndorsement.getStep());
        projectDetailsUpdateStepsPO.advanceProjectByStepName(ProjectStep.SubmitforFinancialEndorsement.getStep());

        Reporter.log("Step 9 - Logout admin user and Login again with controller");
        headerPO.signOut();
        login.fillLoginDetailsAndPerformLogin(controllerUser.getEmail(), controllerUser.getPassword());

        Reporter.log("Step 10 - Find Project created by controller and go to project detail page from project listing");
        sideHeader.clickOnProjectsMenu();
        headerPO.searchItemGloballyByName(data.getProjectName());
        projectsListingPO.clickOnProjectName(data.getProjectName());

        Reporter.log("Step 11 - Advance Project step from 'Pending Financial Endorsement' to 'Finalizing Proposal'");
        projectDetailsPO.clickOnProjectStepByName(ProjectStep.PendingFinancialEndorsement.getStep());
        projectDetailsUpdateStepsPO.advanceProjectByStepName(ProjectStep.EndorseProjectPlan.getStep());

        Reporter.log("Step 12 - Logout controller and Login with admin");
        headerPO.signOut();
        login.fillLoginDetailsAndPerformLogin(adminUser.getEmail(), adminUser.getPassword());

        Reporter.log("Step 13 - Find Project created by  controller and go to project detail page from project listing");
        sideHeader.clickOnProjectsMenu();
        headerPO.searchItemGloballyByName(data.getProjectName());
        projectsListingPO.clickOnProjectName(data.getProjectName());

        Reporter.log("Step 14 - Advance Project step from 'Finalizing Proposal' to 'Pending Regional Director Approval'");
        projectDetailsPO.clickOnProjectStepByName(ProjectStep.FinalizingProposal.getStep());
        projectDetailsUpdateStepsPO.advanceProjectByStepName(ProjectStep.SubmitforApproval.getStep());

        Reporter.log("Step 15 - Advance Project step from 'Pending Regional Director Approval' to 'Pending Finance Confirmation'");
        projectDetailsPO.clickOnProjectStepByName(ProjectStep.PendingRegionalDirectorApproval.getStep());
        projectDetailsUpdateStepsPO.advanceProjectByStepName(ProjectStep.ApproveProject.getStep()); // Todo: Getting error (Bug #1528)

        selenium.refreshPage();

        Reporter.log("Step 16 - Logout admin user and Login again with controller");
        headerPO.signOut();
        login.fillLoginDetailsAndPerformLogin(controllerUser.getEmail(), controllerUser.getPassword());

        Reporter.log("Step 17 - Find Project created by controller and go to project detail page from project listing");
        sideHeader.clickOnProjectsMenu();
        headerPO.searchItemGloballyByName(data.getProjectName());
        projectsListingPO.clickOnProjectName(data.getProjectName());

        Reporter.log("Step 18 - Advance Project step from 'Pending Finance Confirmation' to 'On Hold for Finance Confirmation'");
        projectDetailsPO.clickOnProjectStepByName(ProjectStep.PendingFinanceConfirmation.getStep());
        projectDetailsUpdateStepsPO.advanceProjectByStepName(ProjectStep.HoldProjectForConfirmation.getStep());

        Reporter.log("Step 19 - Advance Project step from 'On Hold for Finance Confirmation' to 'Active'");
        projectDetailsPO.clickOnProjectStepByName(ProjectStep.OnHoldForFinanceConfirmation.getStep());
        projectDetailsUpdateStepsPO.advanceProjectByStepName(ProjectStep.ConfirmProject.getStep());

        Reporter.log("Step 20 - Logout controller user and Login again with admin user");
        headerPO.signOut();
        login.fillLoginDetailsAndPerformLogin(adminUser.getEmail(), adminUser.getPassword());

        Reporter.log("Step 21 - Find Project created by admin and go to project detail page from project listing");
        sideHeader.clickOnProjectsMenu();
        headerPO.searchItemGloballyByName(data.getProjectName());
        projectsListingPO.clickOnProjectName(data.getProjectName());

        Reporter.log("Step 22 - Advance Project step from 'Active' to 'Finalize Completion'");
        projectDetailsPO.clickOnProjectStepByName(ProjectStep.Active.getStep());
        projectDetailsUpdateStepsPO.advanceProjectByStepName(ProjectStep.FinalizeCompletion.getStep());

        Reporter.log("Step 23- Advance Project step from 'Finalizing Completion' to 'Completed'");
        projectDetailsPO.clickOnProjectStepByName(ProjectStep.FinalizingCompletion.getStep());
        projectDetailsUpdateStepsPO.advanceProjectByStepName(ProjectStep.Complete.getStep());

        Reporter.log("Step 24 - Verifying project step");
        Assert.assertEquals(projectDetailsPO.getProjectStepByName(ProjectStep.Completed.getStep()), ProjectStep.Completed.getStep(), "Project step is not changed");
    }
}

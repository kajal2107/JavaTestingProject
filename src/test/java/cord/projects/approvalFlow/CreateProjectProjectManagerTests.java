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
import pageobjects.cord.projects.ProjectDetailsPO;
import pageobjects.cord.projects.ProjectDetailsUpdateStepsPO;
import pageobjects.cord.projects.ProjectsListingPO;

public class CreateProjectProjectManagerTests extends BaseTest {


    /*Test 1 : Verify that user can create project successfully*/
    @Test
    public void verifyUserCanCreateProjectSuccessfully() throws InterruptedException {

        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);

        Reporter.log("Step 1 - Create Project");
        Project data = new ProjectData().getCreateProjectData();
        new ProjectFunctions(driver, selenium).createProjectAndGotoProjectDetailsPage(fpmUser, data);

        Reporter.log("Step 2 - Verifying project details");
        Assert.assertEquals(projectDetails.getProjectName(), data.getProjectName(), "Project is not present : " + data.getProjectName());
    }

    /*Test 2 : Verify that Project Manager(FPM) and Admin can advance project step up to 'Completed'*/
    @Test
    public void verifyProjectManagerAndAdminCanTakeProjectToFinanceConfirmation() throws InterruptedException {

        LoginPO login = new LoginPO(driver);
        LeftNavPO sideHeader = new LeftNavPO(driver);
        ProjectsListingPO projectsListingPO = new ProjectsListingPO(driver);
        ProjectDetailsPO projectDetailsPO = new ProjectDetailsPO(driver);
        ProjectDetailsUpdateStepsPO projectDetailsUpdateStepsPO = new ProjectDetailsUpdateStepsPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);

        Reporter.log("Step 1 - Create Project");
        Project data = new ProjectData().getCreateProjectData();
        new ProjectFunctions(driver, selenium).createProjectAndGotoProjectDetailsPage(fpmUser, data);

        Reporter.log("Step 2 - Advance Project step from 'Early Conversations' to 'Pending Concept Approval'");
        projectDetailsPO.clickOnProjectStepByName(ProjectStep.EarlyConversations.getStep());
        projectDetailsUpdateStepsPO.advanceProjectByStepName(ProjectStep.SubmitforConceptApproval.getStep());

        Reporter.log("Step 3 - Logout Project Manager and Login with admin");
        headerPO.signOut();
        login.fillLoginDetailsAndPerformLogin(adminUser.getEmail(), adminUser.getPassword());

        Reporter.log("Step 4 - Find Project created by Project Manager and go to project detail page from project listing");
        sideHeader.clickOnProjectsMenu();
        headerPO.searchItemGloballyByName(data.getProjectName());
        projectsListingPO.clickOnProjectName(data.getProjectName());

        Reporter.log("Step 5 - Advance Project step from 'Pending Concept Approval' to 'Prep For Consultant Endorsement'");
        projectDetailsPO.clickOnProjectStepByName(ProjectStep.PendingConceptApproval.getStep());
        projectDetailsUpdateStepsPO.advanceProjectByStepName(ProjectStep.ApproveConcept.getStep());

        Reporter.log("Step 6 - Logout admin user and Login again with Project Manager");
        headerPO.signOut();
        login.fillLoginDetailsAndPerformLogin(fpmUser.getEmail(), fpmUser.getPassword());

        Reporter.log("Step 7 - Find Project created by Project Manager and go to project detail page from project listing");
        sideHeader.clickOnProjectsMenu();
        headerPO.searchItemGloballyByName(data.getProjectName());
        projectsListingPO.clickOnProjectName(data.getProjectName());

        Reporter.log("Step 8 - Advance Project step from 'Prep For Consultant Endorsement' to 'Pending Consultant Endorsement'");
        projectDetailsPO.clickOnProjectStepByName(ProjectStep.PrepForConsultantEndorsement.getStep());
        projectDetailsUpdateStepsPO.advanceProjectByStepName(ProjectStep.SubmitforConsultantEndorsement.getStep());

        Reporter.log("Step 9 - Logout Project Manager and Login with admin");
        headerPO.signOut();
        login.fillLoginDetailsAndPerformLogin(adminUser.getEmail(), adminUser.getPassword());

        Reporter.log("Step 10 - Find Project created by Project Manager and go to project detail page from project listing");
        sideHeader.clickOnProjectsMenu();
        headerPO.searchItemGloballyByName(data.getProjectName());
        projectsListingPO.clickOnProjectName(data.getProjectName());

        Reporter.log("Step 11 - Advance Project step from 'Pending Consultant Endorsement' to 'Prep For Financial Endorsement'");
        projectDetailsPO.clickOnProjectStepByName(ProjectStep.PendingConsultantEndorsement.getStep());
        projectDetailsUpdateStepsPO.advanceProjectByStepName(ProjectStep.EndorsePlan.getStep());

        Reporter.log("Step 12 - Logout admin user and Login again with Project Manager");
        headerPO.signOut();
        login.fillLoginDetailsAndPerformLogin(fpmUser.getEmail(), fpmUser.getPassword());

        Reporter.log("Step 13 - Find Project created by Project Manager and go to project detail page from project listing");
        sideHeader.clickOnProjectsMenu();
        headerPO.searchItemGloballyByName(data.getProjectName());
        projectsListingPO.clickOnProjectName(data.getProjectName());

        Reporter.log("Step 14 - Advance Project step from 'Prep For Financial Endorsement' to 'Pending Financial Endorsement'");
        projectDetailsPO.clickOnProjectStepByName(ProjectStep.PrepForFinancialEndorsement.getStep());
        projectDetailsUpdateStepsPO.advanceProjectByStepName(ProjectStep.SubmitforFinancialEndorsement.getStep());

        Reporter.log("Step 15 - Logout Project Manager and Login with admin");
        headerPO.signOut();
        login.fillLoginDetailsAndPerformLogin(adminUser.getEmail(), adminUser.getPassword());

        Reporter.log("Step 16 - Find Project created by Project Manager and go to project detail page from project listing");
        sideHeader.clickOnProjectsMenu();
        headerPO.searchItemGloballyByName(data.getProjectName());
        projectsListingPO.clickOnProjectName(data.getProjectName());

        Reporter.log("Step 17 - Advance Project step from 'Pending Financial Endorsement' to 'Finalizing Proposal'");
        projectDetailsPO.clickOnProjectStepByName(ProjectStep.PendingFinancialEndorsement.getStep());
        projectDetailsUpdateStepsPO.advanceProjectByStepName(ProjectStep.EndorseProjectPlan.getStep());

        Reporter.log("Step 18 - Logout admin user and Login again with Project Manager");
        headerPO.signOut();
        login.fillLoginDetailsAndPerformLogin(fpmUser.getEmail(), fpmUser.getPassword());

        Reporter.log("Step 19 - Find Project created by Project Manager and go to project detail page from project listing");
        sideHeader.clickOnProjectsMenu();
        headerPO.searchItemGloballyByName(data.getProjectName());
        projectsListingPO.clickOnProjectName(data.getProjectName());

        Reporter.log("Step 20 - Advance Project step from 'Finalizing Proposal' to 'Pending Regional Director Approval'");
        projectDetailsPO.clickOnProjectStepByName(ProjectStep.FinalizingProposal.getStep());
        projectDetailsUpdateStepsPO.advanceProjectByStepName(ProjectStep.SubmitforApproval.getStep());

        Reporter.log("Step 21- Logout Project Manager and Login with admin");
        headerPO.signOut();
        login.fillLoginDetailsAndPerformLogin(adminUser.getEmail(), adminUser.getPassword());

        Reporter.log("Step 22 - Find Project created by Project Manager and go to project detail page from project listing");
        sideHeader.clickOnProjectsMenu();
        headerPO.searchItemGloballyByName(data.getProjectName());
        projectsListingPO.clickOnProjectName(data.getProjectName());

        Reporter.log("Step 23 - Advance Project step from 'Pending Regional Director Approval' to 'Pending Finance Confirmation'");
        projectDetailsPO.clickOnProjectStepByName(ProjectStep.PendingRegionalDirectorApproval.getStep());
        projectDetailsUpdateStepsPO.advanceProjectByStepName(ProjectStep.ApproveProject.getStep()); // Todo: Getting error (Bug #1528)

        selenium.refreshPage();

        Reporter.log("Step 24 - Advance Project step from 'Pending Finance Confirmation' to 'Active''");
        projectDetailsPO.clickOnProjectStepByName(ProjectStep.PendingFinanceConfirmation.getStep());
        projectDetailsUpdateStepsPO.advanceProjectByStepName(ProjectStep.ConfirmProject.getStep());

        Reporter.log("Step 25 - Logout admin user and Login again with Project Manager");
        headerPO.signOut();
        login.fillLoginDetailsAndPerformLogin(fpmUser.getEmail(), fpmUser.getPassword());

        Reporter.log("Step 26 - Find Project created by Project Manager and go to project detail page from project listing");
        sideHeader.clickOnProjectsMenu();
        headerPO.searchItemGloballyByName(data.getProjectName());
        projectsListingPO.clickOnProjectName(data.getProjectName());

        Reporter.log("Step 27 - Advance Project step from 'Active' to 'Discussing Change To Plan'");
        projectDetailsPO.clickOnProjectStepByName(ProjectStep.Active.getStep());
        projectDetailsUpdateStepsPO.advanceProjectByStepName(ProjectStep.DiscussChangeToPlan.getStep());

        Reporter.log("Step 28 - Advance Project step from 'Discussing Change To Plan' to 'Submit for Approval'");
        projectDetailsPO.clickOnProjectStepByName(ProjectStep.DiscussingChangeToPlan.getStep());
        projectDetailsUpdateStepsPO.advanceProjectByStepName(ProjectStep.SubmitforApproval.getStep());

        Reporter.log("Step 29- Logout Project Manager and Login with admin");
        headerPO.signOut();
        login.fillLoginDetailsAndPerformLogin(adminUser.getEmail(), adminUser.getPassword());

        Reporter.log("Step 30 - Find Project created by Project Manager and go to project detail page from project listing");
        sideHeader.clickOnProjectsMenu();
        headerPO.searchItemGloballyByName(data.getProjectName());
        projectsListingPO.clickOnProjectName(data.getProjectName());

        Reporter.log("Step 31 - Advance Project step from 'Pending Change To Plan Approval' to 'Approve Change To Plan'");
        projectDetailsPO.clickOnProjectStepByName(ProjectStep.PendingChangeToPlanApproval.getStep());
        projectDetailsUpdateStepsPO.advanceProjectByStepName(ProjectStep.ApproveChangeToPlan.getStep());

        Reporter.log("Step 32 - Advance Project step from 'Pending Change To Plan Confirmation' to 'Pending Suspension Approval'");
        projectDetailsPO.clickOnProjectStepByName(ProjectStep.PendingChangeToPlanConfirmation.getStep());
        projectDetailsUpdateStepsPO.clickProjectOverrideStatusByName(ProjectStep.PendingSuspensionApproval.getStep());

        Reporter.log("Step 33 - Advance Project step from 'Pending Suspension Approval' to 'Approve Suspension'");
        projectDetailsPO.clickOnProjectStepByName(ProjectStep.PendingSuspensionApproval.getStep());
        projectDetailsUpdateStepsPO.advanceProjectByStepName(ProjectStep.ApproveSuspension.getStep());

        Reporter.log("Step 34 - Logout admin user and Login again with Project Manager");
        headerPO.signOut();
        login.fillLoginDetailsAndPerformLogin(fpmUser.getEmail(), fpmUser.getPassword());

        Reporter.log("Step 35 - Find Project created by Project Manager and go to project detail page from project listing");
        sideHeader.clickOnProjectsMenu();
        headerPO.searchItemGloballyByName(data.getProjectName());
        projectsListingPO.clickOnProjectName(data.getProjectName());

        Reporter.log("Step 36 - Advance Project step from 'Suspended' to 'Discuss Reactivation'");
        projectDetailsPO.clickOnProjectStepByName(ProjectStep.Suspended.getStep());
        projectDetailsUpdateStepsPO.advanceProjectByStepName(ProjectStep.DiscussReactivation.getStep()); // Todo: Green button invisible (Bug #760)

        Reporter.log("Step 37 - Advance Project step from 'Discussing ReActivation' to 'Submit for Approval'");
        projectDetailsPO.clickOnProjectStepByName(ProjectStep.DiscussingReActivation.getStep());
        projectDetailsUpdateStepsPO.advanceProjectByStepName(ProjectStep.SubmitforApproval.getStep());

        Reporter.log("Step 38 - Logout Project Manager and Login with admin");
        headerPO.signOut();
        login.fillLoginDetailsAndPerformLogin(adminUser.getEmail(), adminUser.getPassword());

        Reporter.log("Step 39 - Find Project created by Project Manager and go to project detail page from project listing");
        sideHeader.clickOnProjectsMenu();
        headerPO.searchItemGloballyByName(data.getProjectName());
        projectsListingPO.clickOnProjectName(data.getProjectName());

        Reporter.log("Step 40 - Advance Project step from 'Pending Reactivation Approval' to 'Approve Reactivation'");
        projectDetailsPO.clickOnProjectStepByName(ProjectStep.PendingReactivationApproval.getStep());
        projectDetailsUpdateStepsPO.advanceProjectByStepName(ProjectStep.ApproveReactivation.getStep());

        Reporter.log("Step 41 - Logout admin user and Login again with Project Manager");
        headerPO.signOut();
        login.fillLoginDetailsAndPerformLogin(fpmUser.getEmail(), fpmUser.getPassword());

        Reporter.log("Step 39 - Find Project created by Project Manager and go to project detail page from project listing");
        sideHeader.clickOnProjectsMenu();
        headerPO.searchItemGloballyByName(data.getProjectName());
        projectsListingPO.clickOnProjectName(data.getProjectName());

        Reporter.log("Step 42 - Advance Project step from 'Active Changed Plan' to 'Finalize Completion'");
        projectDetailsPO.clickOnProjectStepByName(ProjectStep.ActiveChangedPlan.getStep());
        projectDetailsUpdateStepsPO.advanceProjectByStepName(ProjectStep.FinalizeCompletion.getStep());

        Reporter.log("Step 43 - Advance Project step from 'Finalizing Completion' to 'Completed'");
        projectDetailsPO.clickOnProjectStepByName(ProjectStep.FinalizingCompletion.getStep());
        projectDetailsUpdateStepsPO.advanceProjectByStepName(ProjectStep.Complete.getStep());

        Reporter.log("Step 44 - Verifying project step");
        Assert.assertEquals(projectDetailsPO.getProjectStepByName(ProjectStep.Completed.getStep()), ProjectStep.Completed.getStep(), "Project step is not changed");

    }


}

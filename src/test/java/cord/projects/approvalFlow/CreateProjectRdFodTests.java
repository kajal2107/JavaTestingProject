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

public class CreateProjectRdFodTests extends BaseTest {


    /*Test 1 : Verify that user having regional director and field operations director can advance project step from 'Early Conversations' to 'Completed'*/
    @Test
    public void verifyRegionalDirectorAndFieldOperationsDirectorCanTakeProjectToCompleted() throws InterruptedException {

        LoginPO login = new LoginPO(driver);
        LeftNavPO sideHeader = new LeftNavPO(driver);
        ProjectsListingPO projectsListingPO = new ProjectsListingPO(driver);
        ProjectDetailsPO projectDetailsPO = new ProjectDetailsPO(driver);
        ProjectDetailsUpdateStepsPO projectDetailsUpdateStepsPO = new ProjectDetailsUpdateStepsPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);

        Reporter.log("Step 1 - Create Project");
        Project data = new ProjectData().getCreateProjectData();
        new ProjectFunctions(driver, selenium).createProjectAndGotoProjectDetailsPage(rdFodUser, data);

        Reporter.log("Step 2 - Advance Project step from 'Early Conversations' to 'Pending Concept Approval'");
        projectDetailsPO.clickOnProjectStepByName(ProjectStep.EarlyConversations.getStep());
        projectDetailsUpdateStepsPO.advanceProjectByStepName(ProjectStep.SubmitforConceptApproval.getStep());

        Reporter.log("Step 3 - Advance Project step from 'Pending Concept Approval' to 'Prep For Consultant Endorsement'");
        projectDetailsPO.clickOnProjectStepByName(ProjectStep.PendingConceptApproval.getStep());
        projectDetailsUpdateStepsPO.advanceProjectByStepName(ProjectStep.ApproveConcept.getStep());

        Reporter.log("Step 4 - Advance Project step from 'Prep For Consultant Endorsement' to 'Pending Consultant Endorsement'");
        projectDetailsPO.clickOnProjectStepByName(ProjectStep.PrepForConsultantEndorsement.getStep());
        projectDetailsUpdateStepsPO.advanceProjectByStepName(ProjectStep.SubmitforConsultantEndorsement.getStep());

        Reporter.log("Step 5 - Logout Regional Director Field Operations Director and Login with admin");
        headerPO.signOut();
        login.fillLoginDetailsAndPerformLogin(adminUser.getEmail(), adminUser.getPassword());

        Reporter.log("Step 6 - Find Project created by Regional Director Field Operations Director and go to project detail page from project listing");
        sideHeader.clickOnProjectsMenu();
        headerPO.searchItemGloballyByName(data.getProjectName());
        projectsListingPO.clickOnProjectName(data.getProjectName());

        Reporter.log("Step 7 - Advance Project step from 'Pending Consultant Endorsement' to 'Prep For Financial Endorsement'");
        projectDetailsPO.clickOnProjectStepByName(ProjectStep.PendingConsultantEndorsement.getStep());
        projectDetailsUpdateStepsPO.advanceProjectByStepName(ProjectStep.EndorsePlan.getStep());

        Reporter.log("Step 8 - Logout admin user and Login again with Regional Director Field Operations Director");
        headerPO.signOut();
        login.fillLoginDetailsAndPerformLogin(rdFodUser.getEmail(), rdFodUser.getPassword());

        Reporter.log("Step 9 - Find Project created by Regional Director Field Operations Director and go to project detail page from project listing");
        sideHeader.clickOnProjectsMenu();
        headerPO.searchItemGloballyByName(data.getProjectName());
        projectsListingPO.clickOnProjectName(data.getProjectName());

        Reporter.log("Step 10 - Advance Project step from 'Prep For Financial Endorsement' to 'Pending Financial Endorsement'");
        projectDetailsPO.clickOnProjectStepByName(ProjectStep.PrepForFinancialEndorsement.getStep());
        projectDetailsUpdateStepsPO.advanceProjectByStepName(ProjectStep.SubmitforFinancialEndorsement.getStep());

        Reporter.log("Step 11 - Logout Regional Director Field Operations Director and Login with admin");
        headerPO.signOut();
        login.fillLoginDetailsAndPerformLogin(adminUser.getEmail(), adminUser.getPassword());

        Reporter.log("Step 12 - Find Project created by Regional Director Field Operations Director and go to project detail page from project listing");
        sideHeader.clickOnProjectsMenu();
        headerPO.searchItemGloballyByName(data.getProjectName());
        projectsListingPO.clickOnProjectName(data.getProjectName());

        Reporter.log("Step 13 - Advance Project step from 'Pending Financial Endorsement' to 'Finalizing Proposal'");
        projectDetailsPO.clickOnProjectStepByName(ProjectStep.PendingFinancialEndorsement.getStep());
        projectDetailsUpdateStepsPO.advanceProjectByStepName(ProjectStep.EndorseProjectPlan.getStep());

        Reporter.log("Step 14 - Logout admin user and Login again with Regional Director Field Operations Director");
        headerPO.signOut();
        login.fillLoginDetailsAndPerformLogin(rdFodUser.getEmail(), rdFodUser.getPassword());

        Reporter.log("Step 15 - Find Project created by Regional Director Field Operations Director and go to project detail page from project listing");
        sideHeader.clickOnProjectsMenu();
        headerPO.searchItemGloballyByName(data.getProjectName());
        projectsListingPO.clickOnProjectName(data.getProjectName());

        Reporter.log("Step 16 - Advance Project step from 'Finalizing Proposal' to 'Pending Regional Director Approval'");
        projectDetailsPO.clickOnProjectStepByName(ProjectStep.FinalizingProposal.getStep());
        projectDetailsUpdateStepsPO.advanceProjectByStepName(ProjectStep.SubmitforApproval.getStep());

        Reporter.log("Step 17 - Advance Project step from 'Pending Regional Director Approval' to 'Pending Finance Confirmation'");
        projectDetailsPO.clickOnProjectStepByName(ProjectStep.PendingRegionalDirectorApproval.getStep());
        projectDetailsUpdateStepsPO.advanceProjectByStepName(ProjectStep.ApproveProject.getStep()); // Todo: Getting error (Bug #1528)

        selenium.refreshPage();

        Reporter.log("Step 18 - Logout Regional Director Field Operations Director and Login with admin");
        headerPO.signOut();
        login.fillLoginDetailsAndPerformLogin(adminUser.getEmail(), adminUser.getPassword());

        Reporter.log("Step 19 - Find Project created by Regional Director Field Operations Director and go to project detail page from project listing");
        sideHeader.clickOnProjectsMenu();
        headerPO.searchItemGloballyByName(data.getProjectName());
        projectsListingPO.clickOnProjectName(data.getProjectName());

        Reporter.log("Step 20 - Advance Project step from 'Pending Finance Confirmation' to 'Active'");
        projectDetailsPO.clickOnProjectStepByName(ProjectStep.PendingFinanceConfirmation.getStep());
        projectDetailsUpdateStepsPO.advanceProjectByStepName(ProjectStep.ConfirmProject.getStep());

        Reporter.log("Step 21 - Logout admin user and Login again with Regional Director Field Operations Director");
        headerPO.signOut();
        login.fillLoginDetailsAndPerformLogin(rdFodUser.getEmail(), rdFodUser.getPassword());

        Reporter.log("Step 22 - Find Project created by Regional Director Field Operations Director and go to project detail page from project listing");
        sideHeader.clickOnProjectsMenu();
        headerPO.searchItemGloballyByName(data.getProjectName());
        projectsListingPO.clickOnProjectName(data.getProjectName());

        Reporter.log("Step 23 - Advance Project step from 'Active' to 'Finalize Completion'");
        projectDetailsPO.clickOnProjectStepByName(ProjectStep.Active.getStep());
        projectDetailsUpdateStepsPO.advanceProjectByStepName(ProjectStep.FinalizeCompletion.getStep());

        Reporter.log("Step 24 - Advance Project step from 'Finalizing Completion' to 'Completed'");
        projectDetailsPO.clickOnProjectStepByName(ProjectStep.FinalizingCompletion.getStep());
        projectDetailsUpdateStepsPO.advanceProjectByStepName(ProjectStep.Complete.getStep());

        Reporter.log("Step 25 - Verifying project step");
        Assert.assertEquals(projectDetailsPO.getProjectStepByName(ProjectStep.Completed.getStep()), ProjectStep.Completed.getStep(), "Project step is not changed");


    }


}

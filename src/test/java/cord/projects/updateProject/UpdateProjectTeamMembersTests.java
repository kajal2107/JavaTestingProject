package cord.projects.updateProject;

import base.BaseTest;
import datafactory.ProjectData;
import dataobjects.Project;
import funcLibs.project.ProjectFunctions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import pageobjects.cord.projects.ProjectDetailsPO;
import pageobjects.cord.projects.ProjectDetailsTeamMembersAddTeamMemberPO;
import pageobjects.cord.projects.ProjectDetailsTeamMembersListingPO;
import pageobjects.cord.projects.ProjectMembersUpdateTeamMembersPO;

public class UpdateProjectTeamMembersTests extends BaseTest {

    /*Test 1 : verify User Can Update Project TeamMembers Successfully*/
    @Test
    public void verifyUserCanUpdateProjectTeamMembersSuccessfully() throws InterruptedException {

        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectDetailsTeamMembersListingPO projectDetailsTeamMembersListingPO = new ProjectDetailsTeamMembersListingPO(driver);
        ProjectDetailsTeamMembersAddTeamMemberPO projectMemberAddTeamMembersPO = new ProjectDetailsTeamMembersAddTeamMemberPO(driver);
        ProjectMembersUpdateTeamMembersPO projectMembersUpdateTeamMembersPO = new ProjectMembersUpdateTeamMembersPO(driver);

        Reporter.log("Step 1 - Create Project");
        Project data = new ProjectData().getCreateProjectData();
        new ProjectFunctions(driver, selenium).createProjectAndGotoProjectDetailsPage(adminUser, data);

        Reporter.log("Step 2 - Click on team members button and add team member");
        projectDetails.clickOnTeamMemberButton();
        projectDetailsTeamMembersListingPO.clickOnAddTeamMemberButton();
        projectMemberAddTeamMembersPO.clearAndFillAddTeamMember(imFpmUser);

        Reporter.log("Step 3 - verify that project member is added successfully");
        Assert.assertEquals(projectDetailsTeamMembersListingPO.getMembersName(imFpmUser.getRealFirstName() + " " + imFpmUser.getRealLastName()), (imFpmUser.getRealFirstName() + " " + imFpmUser.getRealLastName()), "project member name doesn't match : ");
        Assert.assertEquals(projectDetailsTeamMembersListingPO.getMembersRoleByMemberName(imFpmUser.getRealFirstName() + " " + imFpmUser.getRealLastName()), imFpmUser.getUserRoles().get(0), "project member role doesn't match : ");

        Reporter.log("Step 4 - Click on team members edit button and update team member");
        projectDetailsTeamMembersListingPO.clickOnEditTeamMemberButton(imFpmUser.getRealFirstName() + " " + imFpmUser.getRealLastName());
        projectMembersUpdateTeamMembersPO.clickOnRolesDropDownButtonAndClickOnSaveButton(imFpmUser);
        Assert.assertEquals(projectDetailsTeamMembersListingPO.getMembersRoleByMemberName(imFpmUser.getRealFirstName() + " " + imFpmUser.getRealLastName()), imFpmUser.getUserRoles().get(0) + ", " + imFpmUser.getUserRoles().get(1), "project member updated role doesn't match : ");
    }
}

package funcLibs.project;

import dataobjects.User;
import enums.users.UserRole;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import pageobjects.cord.projects.ProjectDetailsTeamMembersListingPO;
import pageobjects.cord.projects.ProjectMembersUpdateTeamMembersPO;
import utilities.SeleniumHelpers;

public class TeamMemberFunctions {

    protected WebDriver driver;
    protected SeleniumHelpers selenium;

    public TeamMemberFunctions(WebDriver driver, SeleniumHelpers selenium) {
        this.driver = driver;
        this.selenium = selenium;
    }

    /**
     * edit project team member
     *
     * @param user imFpmUser
     */

    public void editProjectTeamMember(User user) {

        try {
            ProjectDetailsTeamMembersListingPO projectDetailsTeamMembersListingPO = new ProjectDetailsTeamMembersListingPO(driver);
            ProjectMembersUpdateTeamMembersPO projectMembersUpdateTeamMembersPO = new ProjectMembersUpdateTeamMembersPO(driver);

            Reporter.log("Step 1 - Click on team members edit button and update team member");

            if (user.getUserRoles().contains(UserRole.Liaison.getRole())) {
                projectDetailsTeamMembersListingPO.clickOnEditTeamMemberButton(user.getRealFirstName() + " " + user.getRealLastName());
                projectMembersUpdateTeamMembersPO.clickOnTeamMemberClearAndSaveButton();
            }
            projectDetailsTeamMembersListingPO.clickOnEditTeamMemberButton(user.getRealFirstName() + " " + user.getRealLastName());
            projectMembersUpdateTeamMembersPO.clickOnRolesDropDownButtonAndClickOnSaveButton(user);


        } catch (Exception e) {
            System.out.println(e);
        }
    }

}

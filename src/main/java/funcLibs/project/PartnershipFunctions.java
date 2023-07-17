package funcLibs.project;

import dataobjects.Partner;
import dataobjects.Project;
import enums.partnership.PartnershipFinancialReportingFrequency;
import enums.partnership.PartnershipStatus;
import enums.project.ProjectSubTabs;
import enums.users.UserRole;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import pageobjects.cord.common.HeaderPO;
import pageobjects.cord.common.LeftNavPO;
import pageobjects.cord.projects.*;
import utilities.SeleniumHelpers;

public class PartnershipFunctions {

    protected WebDriver driver;
    protected SeleniumHelpers selenium;

    public PartnershipFunctions(WebDriver driver, SeleniumHelpers selenium) {
        this.driver = driver;
        this.selenium = selenium;
    }

    /**
     * create project partnership
     *
     * @param dataPartner PartnerData
     * @param projectName created Project Name
     */

    public void createProjectPartnership(Partner dataPartner, String projectName, dataobjects.User user) {

        try {
            ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
            LeftNavPO sideHeader = new LeftNavPO(driver);
            ProjectsListingPO projectsListingPO = new ProjectsListingPO(driver);
            ProjectMembersCreatePartnershipPO projectMembersCreatePartnershipPO = new ProjectMembersCreatePartnershipPO(driver);
            ProjectDetailsPartnershipsListingPO projectDetailsPartnershipsListingPO = new ProjectDetailsPartnershipsListingPO(driver);
            HeaderPO headerPO = new HeaderPO(driver);

            Reporter.log("Step 1 - Find created project");
            sideHeader.clickOnProjectsMenu();
            projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());

            if (!projectsListingPO.isProjectPresent(projectName)) {
                headerPO.searchItemGloballyByName(projectName);
            }
            projectsListingPO.clickOnProjectName(projectName);

            Reporter.log("Step 2 - Click on add partnerships button and fill partnerships detail");
            projectDetails.clickOnPartnershipsButton();
            projectDetailsPartnershipsListingPO.clickOnAddPartnershipButton();

            if (user.getUserRoles().contains(UserRole.Liaison.getRole())) {
                projectMembersCreatePartnershipPO.searchOrganizationName();
            } else {
                projectMembersCreatePartnershipPO.fillOrganizationDetail(dataPartner);
            }


        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * create project funding partnership
     *
     * @param dataPartner PartnerData
     * @param projectName created Project Name
     */

    public void createProjectFundingPartnership(Partner dataPartner, String projectName) {

        try {
            ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
            LeftNavPO sideHeader = new LeftNavPO(driver);
            ProjectsListingPO projectsListingPO = new ProjectsListingPO(driver);
            ProjectMembersCreatePartnershipPO projectMembersCreatePartnershipPO = new ProjectMembersCreatePartnershipPO(driver);
            ProjectDetailsPartnershipsListingPO projectDetailsPartnershipsListingPO = new ProjectDetailsPartnershipsListingPO(driver);
            HeaderPO headerPO = new HeaderPO(driver);

            Reporter.log("Step 1 - Find created project");
            sideHeader.clickOnProjectsMenu();
            projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());

            if (!projectsListingPO.isProjectPresent(projectName)) {
                headerPO.searchItemGloballyByName(projectName);
            }
            projectsListingPO.clickOnProjectName(projectName);

            Reporter.log("Step 2 - Click on add partnerships button and fill partnerships detail");
            projectDetails.clickOnPartnershipsButton();
            projectDetailsPartnershipsListingPO.clickOnAddPartnershipButton();
            projectMembersCreatePartnershipPO.fillFundingOrganizationDetail(dataPartner);
        } catch (Exception e) {
            System.out.println(e);
        }


    }

    /**
     * Edit Project Partnership
     *
     * @param updateData update data
     */
    public void editProjectPartnership(Project updateData) {

        try {
            ProjectDetailsPartnershipsListingPO partnershipsListingPO = new ProjectDetailsPartnershipsListingPO(driver);
            ProjectDetailsPartnershipsEditPO projectDetailsPartnershipsEditPO = new ProjectDetailsPartnershipsEditPO(driver);

            Reporter.log("Step 1 - Click on edit partnership button and edit detail");
            partnershipsListingPO.clickOnEditOrganizationButton();
            projectDetailsPartnershipsEditPO.clickOnPartnershipAgreementStatusByName(PartnershipStatus.AwaitingSignature.getPartnerShipStatus());
            projectDetailsPartnershipsEditPO.fillMouDateDetails(updateData);
        } catch (Exception e) {
            System.out.println(e);
        }


    }

    /**
     * Edit Project Partnership By Admin User
     *
     * @param updateData update data for Project
     */
    public void editProjectPartnershipAllDetails(Project updateData) {

        try {
            ProjectDetailsPartnershipsListingPO partnershipsListingPO = new ProjectDetailsPartnershipsListingPO(driver);
            ProjectDetailsPartnershipsEditPO projectDetailsPartnershipsEditPO = new ProjectDetailsPartnershipsEditPO(driver);

            Reporter.log("Step 1 - Click on edit partnership button and edit detail");
            partnershipsListingPO.clickOnEditOrganizationButton();

            projectDetailsPartnershipsEditPO.clickOnPartnershipFinancialReportingFrequencyByName(PartnershipFinancialReportingFrequency.Quarterly.getPartnershipFinancialReportingFrequency());
            projectDetailsPartnershipsEditPO.clickOnPartnershipAgreementStatusByName(PartnershipStatus.NotAttached.getPartnerShipStatus());
            projectDetailsPartnershipsEditPO.clickOnPartnershipMouStatusByName(PartnershipStatus.AwaitingSignature.getPartnerShipStatus());
            projectDetailsPartnershipsEditPO.fillMouDateDetails(updateData);
            if (projectDetailsPartnershipsEditPO.isPrimaryToggleButtonAvailable())
            projectDetailsPartnershipsEditPO.clickOnPrimaryToggleButton();
        } catch (Exception e) {
            System.out.println(e);
        }


    }

}

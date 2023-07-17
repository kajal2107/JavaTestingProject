package cord.projects.updateProject;

import base.BaseTest;
import datafactory.PartnerData;
import datafactory.ProjectData;
import dataobjects.Partner;
import dataobjects.Project;
import enums.partnership.PartnershipStatus;
import funcLibs.project.ProjectFunctions;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageobjects.cord.account.LoginPO;
import pageobjects.cord.projects.ProjectDetailsPO;
import pageobjects.cord.projects.ProjectDetailsPartnershipsEditPO;
import pageobjects.cord.projects.ProjectDetailsPartnershipsListingPO;
import pageobjects.cord.projects.ProjectMembersCreatePartnershipPO;
import utilities.DriverManager;
import utilities.JavaHelpers;
import utilities.SeleniumHelpers;

public class UpdateProjectPartnershipsTests extends BaseTest {

    protected String strUrl;

    @BeforeClass
    public void beforeClass() throws InterruptedException {
        WebDriver localDriver;
        DriverManager drivermanager;

        drivermanager = new DriverManager();
        localDriver = drivermanager.setUp("chrome");
        SeleniumHelpers localSelenium = new SeleniumHelpers(localDriver);

        Reporter.log("Step 1 - Create Project");
        Project data = new ProjectData().getCreateProjectData();
        new ProjectFunctions(localDriver, localSelenium).createProjectAndGotoProjectDetailsPage(adminUser, data);
        strUrl = localDriver.getCurrentUrl();
        drivermanager.tearDown();
    }

    /*Test 1 : Verify User Can Update Project Partnership Successfully*/
    @Test
    public void verifyUserCanUpdateProjectPartnershipsSuccessfully() throws InterruptedException {
        LoginPO login = new LoginPO(driver);
        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectDetailsPartnershipsListingPO projectDetailsPartnershipsListingPO = new ProjectDetailsPartnershipsListingPO(driver);
        ProjectMembersCreatePartnershipPO projectMembersCreatePartnershipPO = new ProjectMembersCreatePartnershipPO(driver);
        Partner dataPartner = new PartnerData().getCreatePartnerData();

        Reporter.log("Step 1 - Login and navigate to project detail page");
        selenium.navigateToPage(strUrl);
        login.fillLoginDetailsAndPerformLogin(adminUser.getEmail(), adminUser.getPassword());

        Reporter.log("Step 2 - Click on partner button and add partner");
        projectDetails.clickOnPartnershipsButton();
        projectDetailsPartnershipsListingPO.clickOnAddPartnershipButton();
        projectMembersCreatePartnershipPO.fillPartnershipDetail(dataPartner);

        Assert.assertEquals(projectDetailsPartnershipsListingPO.getPartnershipName(dataPartner.getPartnerName()), dataPartner.getPartnerName(), "project partnership doesn't match : ");
        Assert.assertTrue(projectDetailsPartnershipsListingPO.isCreatedPartnershipPrimary(dataPartner.getPartnerName()), "Created partnership is not primary");

    }

    /*Test 2 : Verify User Can Edit Project Partnership Successfully*/
    @Test
    public void verifyUserCanEditProjectPartnershipsSuccessfully() throws InterruptedException, ParseException, java.text.ParseException {

        LoginPO login = new LoginPO(driver);
        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectDetailsPartnershipsListingPO projectDetailsPartnershipsListingPO = new ProjectDetailsPartnershipsListingPO(driver);
        ProjectMembersCreatePartnershipPO projectMembersCreatePartnershipPO = new ProjectMembersCreatePartnershipPO(driver);
        ProjectDetailsPartnershipsEditPO projectDetailsPartnershipsEditPO = new ProjectDetailsPartnershipsEditPO(driver);

        Reporter.log("Step 1 - Login and navigate to project detail page");
        selenium.navigateToPage(strUrl);
        login.fillLoginDetailsAndPerformLogin(adminUser.getEmail(), adminUser.getPassword());

        Reporter.log("Step 2 - Click on partner button and add partner");
        Partner dataPartner = new PartnerData().getCreatePartnerData();
        projectDetails.clickOnPartnershipsButton();
        projectDetailsPartnershipsListingPO.clickOnAddPartnershipButton();
        projectMembersCreatePartnershipPO.fillPartnershipDetail(dataPartner);

        Reporter.log("Step 3 - Click on partnership edit button");
        Project updateProject = new ProjectData().getUpdateProjectData();
        projectDetailsPartnershipsListingPO.clickOnEditPartnershipButton(dataPartner.getPartnerName());
        projectDetailsPartnershipsEditPO.clickOnPartnershipAgreementStatusByName(PartnershipStatus.AwaitingSignature.getPartnerShipStatus());
        projectDetailsPartnershipsEditPO.clickOnPartnershipMouStatusByName(PartnershipStatus.Signed.getPartnerShipStatus());
        projectDetailsPartnershipsEditPO.fillMouDateDetails(updateProject);

        Reporter.log("Step 4 - Click on partnership submit button");
        projectDetailsPartnershipsEditPO.clickOnPartnershipSubmitButton();

        Assert.assertEquals(projectDetailsPartnershipsListingPO.getAgreementStatusText(dataPartner.getPartnerName()), PartnershipStatus.AwaitingSignature.getPartnerShipStatus(), " partnership agreement status doesn't match");
        Assert.assertEquals(projectDetailsPartnershipsListingPO.getMouStatusText(dataPartner.getPartnerName()), PartnershipStatus.Signed.getPartnerShipStatus(), " partnership mou status doesn't match");
        Assert.assertEquals(projectDetailsPartnershipsListingPO.getMouDateRangeText(dataPartner.getPartnerName()), new JavaHelpers().changeDateFormat(updateProject.getStartDate(), "MM/dd/yyyy", "M/d/yyyy") + " - " + new JavaHelpers().changeDateFormat(updateProject.getEndDate(), "MM/dd/yyyy", "M/d/yyyy"), "project start end date doesn't match : ");
        Assert.assertTrue(projectDetailsPartnershipsListingPO.isCreatedPartnershipPrimary(dataPartner.getPartnerName()), "Created partnership is not primary");
    }

    /*Test 3 : Verify User Can delete Project Partnership Successfully*/
    @Test
    public void verifyUserCanDeleteProjectPartnershipsSuccessfully() throws InterruptedException {

        LoginPO login = new LoginPO(driver);
        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectDetailsPartnershipsListingPO projectDetailsPartnershipsListingPO = new ProjectDetailsPartnershipsListingPO(driver);
        ProjectMembersCreatePartnershipPO projectMembersCreatePartnershipPO = new ProjectMembersCreatePartnershipPO(driver);
        ProjectDetailsPartnershipsEditPO projectDetailsPartnershipsEditPO = new ProjectDetailsPartnershipsEditPO(driver);

        Reporter.log("Step 1 - Login and navigate to project detail page");
        selenium.navigateToPage(strUrl);
        login.fillLoginDetailsAndPerformLogin(adminUser.getEmail(), adminUser.getPassword());

        Reporter.log("Step 2 - Click on partner button and add partner");
        Partner dataPartner = new PartnerData().getCreatePartnerData();
        projectDetails.clickOnPartnershipsButton();
        projectDetailsPartnershipsListingPO.clickOnAddPartnershipButton();
        projectMembersCreatePartnershipPO.fillPartnershipDetail(dataPartner);

        Reporter.log("Step 3 - Click on partnership edit button and delete partnership");
        projectDetailsPartnershipsListingPO.clickOnEditPartnershipButton(dataPartner.getPartnerName());
        projectDetailsPartnershipsEditPO.clickOnPartnershipDeleteButton();

        Assert.assertFalse(projectDetailsPartnershipsListingPO.isProjectPartnershipPresent(dataPartner.getPartnerName()), "project partnership is present");

    }
}

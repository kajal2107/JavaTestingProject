package cord.projects.internshipEngagement;

import base.BaseTest;
import datafactory.EngagementData;
import datafactory.ProjectData;
import dataobjects.Internship;
import dataobjects.Project;
import dataobjects.User;
import enums.project.ProjectInternPosition;
import enums.project.ProjectInternshipMethodologies;
import funcLibs.project.ProjectFunctions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import pageobjects.cord.projects.ProjectDetailsCreateInternshipPO;
import pageobjects.cord.projects.ProjectDetailsPO;
import pageobjects.cord.projects.intershipEngagement.*;
import utilities.JavaHelpers;

import java.text.ParseException;

public class UpdateInternshipEngagementTests extends BaseTest {

    public static final String PROPERTY_TEST_FILE = "src/main/resources/testData/TestFiles.properties";

    /*Test 1 : Verify that user can update project internship successfully*/
    @Test
    public void verifyUserCanUpdateProjectInternshipSuccessfully() throws InterruptedException, ParseException, org.json.simple.parser.ParseException {

        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectDetailsCreateInternshipPO projectDetailsCreateInternshipPO = new ProjectDetailsCreateInternshipPO(driver);
        ProjectInternshipDetailsPO projectInternshipDetailsPO = new ProjectInternshipDetailsPO(driver);
        ProjectEngagementDetailsUpdateStartEndDatePO projectEngagementDetailsUpdateStartEndDatePO = new ProjectEngagementDetailsUpdateStartEndDatePO(driver);
        ProjectInternshipDetailsUpdateCountryOfOriginPO projectInternshipDetailsUpdateCountryOfOriginPO = new ProjectInternshipDetailsUpdateCountryOfOriginPO(driver);
        ProjectInternshipDetailsUpdateInternPositionPO projectInternshipDetailsUpdateInternPositionPO = new ProjectInternshipDetailsUpdateInternPositionPO(driver);
        ProjectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO projectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO = new ProjectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO(driver);
        ProjectEngagementDetailsUpdateDisbursementCompleteDatePO projectEngagementDetailsUpdateDisbursementCompleteDatePO = new ProjectEngagementDetailsUpdateDisbursementCompleteDatePO(driver);
        ProjectInternshipDetailsUpdateInternMethodologiesPO projectInternshipDetailsUpdateInternMethodologiesPO = new ProjectInternshipDetailsUpdateInternMethodologiesPO(driver);
        ProjectEngagementDetailsUpdateActualEstimatedDatePO projectEngagementDetailsUpdateActualEstimatedDatePO = new ProjectEngagementDetailsUpdateActualEstimatedDatePO(driver);
        ProjectInternshipDetailsUpdateMentorPO projectInternshipDetailsUpdateMentorPO = new ProjectInternshipDetailsUpdateMentorPO(driver);

        /*
         * We can change whole test process for specific user from here.
         * */
        User myUser = adminUser;

        Reporter.log("Step 1 - Create Project");
        Project data = new ProjectData().getCreateProjectData();
        new ProjectFunctions(driver, selenium).createProjectAndGotoProjectDetailsPage(myUser, data);

        Reporter.log("Step 2 - Click on add intern engagement button and create internship engagement");
        projectDetails.clickOnInternEngagementButton();
        projectDetailsCreateInternshipPO.fillCreateInternshipDetail(myUser);
        Assert.assertEquals(projectDetails.getInternshipName(myUser.getRealFirstName() + " " + myUser.getRealLastName()), (myUser.getRealFirstName() + " " + myUser.getRealLastName()), "project internship name doesn't match : ");

        Reporter.log("Step 3 - Click on edit button and navigate to internship engagement details page");
        projectDetails.clickOnEditInternshipButton(myUser.getRealFirstName() + " " + myUser.getRealLastName());
        Internship internData = new EngagementData().getCreateEngagementData();
        Internship internUpdateData = new EngagementData().getUpdateEngagementData();

        Reporter.log("Step 4 - Add internship engagement start end date ");
        Project updateData = new ProjectData().getUpdateProjectData();
        projectInternshipDetailsPO.clickInternshipStartEndDateButton();
        projectEngagementDetailsUpdateStartEndDatePO.fillEngagementStartEndDateDetailsAndClickOnSaveButton(internData);

        Reporter.log("Step 5 - Add internship engagement position ");
        projectInternshipDetailsPO.clickEnterInternPositionButton();
        projectInternshipDetailsUpdateInternPositionPO.clickOnInternPositionByNameAndClickOnSaveButton(ProjectInternPosition.LanguageProgramManager.getInternPosition());

        Reporter.log("Step 6 - Add internship engagement date ");
        projectInternshipDetailsPO.clickEnterCountryOfOriginButton();
        projectInternshipDetailsUpdateCountryOfOriginPO.fillInternshipCountryOfOriginDetailsAndClickOnSaveButton(updateData);
        projectInternshipDetailsPO.clickGrowthPlanCompleteDateButton();
        projectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO.fillInternshipGrowthPlanCompleteDateDetailsAndClickOnSaveButton(internData);
        projectInternshipDetailsPO.clickOnDisbursementCompleteDateButton();
        projectEngagementDetailsUpdateDisbursementCompleteDatePO.fillEngagementDisbursementCompleteDateDetailsAndClickOnSaveButton(internData);

        Reporter.log("Step 7- Add internship engagement File ");
        String textFileName = JavaHelpers.getPropertyValue(PROPERTY_TEST_FILE, "textFile");
        projectInternshipDetailsPO.clickOnAddGrowthPlanButton(textFileName);

        selenium.hardWait(5);
        Reporter.log("Step 8- Add internship engagement Methodologies");
        projectInternshipDetailsPO.clickMethodologiesButton();
        projectInternshipDetailsUpdateInternMethodologiesPO.clickOnInternMethodologyByName(ProjectInternshipMethodologies.Film.getInternshipMethodology());
        projectInternshipDetailsUpdateInternMethodologiesPO.clickOnInternMethodologyByName(ProjectInternshipMethodologies.Paratext.getInternshipMethodology());
        projectInternshipDetailsUpdateInternMethodologiesPO.clickOnSaveButton();

        Reporter.log("Step 9- Add internship engagement actual date and estimated date");
        projectInternshipDetailsPO.clickCertificationToggleButton();
        projectInternshipDetailsPO.clickEditDatesButton();
        projectEngagementDetailsUpdateActualEstimatedDatePO.fillEngagementEstimatedActualDateDetailsAndClickOnSubmitButton(internData);

        Reporter.log("Step 10 - Add internship engagement mentor");
        projectInternshipDetailsPO.clickAddMentorButton();
        projectInternshipDetailsUpdateMentorPO.fillInternshipMentorDetailsAndClickOnSaveButton(myUser);

        Reporter.log("Step 11 - Edit internship engagement start end date ");
        projectInternshipDetailsPO.clickInternshipStartEndDateButton();
        projectEngagementDetailsUpdateStartEndDatePO.fillEngagementStartEndDateDetailsAndClickOnSaveButton(internUpdateData);

        Reporter.log("Step 12 - Edit internship engagement position ");
        projectInternshipDetailsPO.clickEnterInternPositionButton();
        projectInternshipDetailsUpdateInternPositionPO.clickOnInternPositionByNameAndClickOnSaveButton(ProjectInternPosition.Administration.getInternPosition());

        Reporter.log("Step 13 - Edit internship engagement date ");
        projectInternshipDetailsPO.clickEnterCountryOfOriginButton();
        projectInternshipDetailsUpdateCountryOfOriginPO.fillInternshipCountryOfOriginDetailsAndClickOnSaveButton(updateData);
        projectInternshipDetailsPO.clickGrowthPlanCompleteDateButton();
        projectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO.fillInternshipGrowthPlanCompleteDateDetailsAndClickOnSaveButton(internUpdateData);
        projectInternshipDetailsPO.clickOnDisbursementCompleteDateButton();
        projectEngagementDetailsUpdateDisbursementCompleteDatePO.fillEngagementDisbursementCompleteDateDetailsAndClickOnSaveButton(internUpdateData);

        Reporter.log("Step 14 - Edit internship engagement File ");
        String newVersionFileName = JavaHelpers.getPropertyValue(PROPERTY_TEST_FILE, "newVersionFile");
        projectInternshipDetailsPO.clickOnAddGrowthPlanInfoButtonAndAddNewVersionFile(newVersionFileName);

        selenium.hardWait(5);

        Reporter.log("Step 15 - Edit internship engagement Methodologies");
        projectInternshipDetailsPO.clickMethodologiesButton();
        projectInternshipDetailsUpdateInternMethodologiesPO.clickOnInternMethodologyByName(ProjectInternshipMethodologies.Film.getInternshipMethodology());
        projectInternshipDetailsUpdateInternMethodologiesPO.clickOnInternMethodologyByName(ProjectInternshipMethodologies.Paratext.getInternshipMethodology());
        projectInternshipDetailsUpdateInternMethodologiesPO.clickOnInternMethodologyByName(ProjectInternshipMethodologies.OneStory.getInternshipMethodology());
        projectInternshipDetailsUpdateInternMethodologiesPO.clickOnInternMethodologyByName(ProjectInternshipMethodologies.Render.getInternshipMethodology());
        projectInternshipDetailsUpdateInternMethodologiesPO.clickOnSaveButton();

        Reporter.log("Step 16 - Edit internship engagement start end date ");
        projectInternshipDetailsPO.clickEditDatesButton();
        projectEngagementDetailsUpdateActualEstimatedDatePO.fillEngagementEstimatedActualDateDetailsAndClickOnSubmitButton(internUpdateData);

        Reporter.log("Step 17- Edit internship engagement mentor");
        projectInternshipDetailsPO.clickAddMentorButton();
        projectInternshipDetailsUpdateMentorPO.fillInternshipMentorDetailsAndClickOnSaveButton(myUser);

        Reporter.log("Step 18 - Verify that all edited internship detail");
        Assert.assertEquals(projectInternshipDetailsPO.getInternshipStartEndDate(), new JavaHelpers().changeDateFormat(internUpdateData.getInternshipStartDate(), "MM/dd/yyyy", "M/d/yyyy") + "\n" + " - " + "\n" + new JavaHelpers().changeDateFormat(internUpdateData.getInternshipEndDate(), "MM/dd/yyyy", "M/d/yyyy"), "Internship start end date doesn't match :");
        Assert.assertEquals(ProjectInternPosition.Administration.getInternPosition(), projectInternshipDetailsPO.getInternPosition(), "internship position doesn't match : ");
        Assert.assertEquals(updateData.getLocationName(), projectInternshipDetailsPO.getInternLocationName(), "internship location doesn't match : ");
        Assert.assertEquals(projectInternshipDetailsPO.getGrowthPlanCompleteDate(), new JavaHelpers().changeDateFormat(internUpdateData.getGrowthPlanCompleteDate(), "MM/dd/yyyy", "M/d/yyyy"), "internship growth plan complete date  doesn't match :");
        Assert.assertEquals(projectInternshipDetailsPO.getDisbursementCompleteDate(), new JavaHelpers().changeDateFormat(internUpdateData.getDisbursementCompleteDate(), "MM/dd/yyyy", "M/d/yyyy"), "internship disbursement complete date  doesn't match :");
        Assert.assertEquals(newVersionFileName, projectInternshipDetailsPO.getUploadedFileText(), "uploaded file name doesn't match : ");
        Assert.assertEquals(projectInternshipDetailsPO.getMethodologiesText(), ProjectInternshipMethodologies.OneStory.getInternshipMethodology() + "\n" + ProjectInternshipMethodologies.Render.getInternshipMethodology(), "internship methodologies doesn't match : ");
        Assert.assertEquals(projectInternshipDetailsPO.getActualDateText(), new JavaHelpers().changeDateFormat(internUpdateData.getInternshipActualDate(), "MM/dd/yyyy", "M/d/yyyy"), "internship actual date doesn't match : ");
        Assert.assertEquals(projectInternshipDetailsPO.getEstimatedDateText(), new JavaHelpers().changeDateFormat(internUpdateData.getInternshipEstimatedDate(), "MM/dd/yyyy", "M/d/yyyy"), "internship estimated date doesn't match : ");
        Assert.assertEquals(projectInternshipDetailsPO.getMentorText(), myUser.getRealFirstName() + " " + myUser.getRealLastName(), "internship mentor name doesn't match : ");

    }

}


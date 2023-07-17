package cord.projects.internshipEngagement;

import base.BaseTest;
import datafactory.EngagementData;
import datafactory.ProjectData;
import dataobjects.Internship;
import dataobjects.Project;
import dataobjects.User;
import enums.engagement.InternshipLanguageEngagementStatus;
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

public class CreateInternshipEngagementTests extends BaseTest {

    public static final String PROPERTY_TEST_FILE = "src/main/resources/testData/TestFiles.properties";

    /*Test 1 : Verify that user can create project internship successfully*/
    @Test
    public void verifyUserCanCreateProjectInternshipSuccessfully() throws InterruptedException, ParseException, org.json.simple.parser.ParseException {

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
        ProjectInternshipLanguageDetailsUpdateStatusPO projectInternshipLanguageDetailsUpdateStatusPO = new ProjectInternshipLanguageDetailsUpdateStatusPO(driver);

        /*
         * We can change whole test process for specific user from here.
         * */
        User myUser = adminUser;

        Reporter.log("Step 1 - Create Project");
        Project data = new ProjectData().getCreateProjectData();
        new ProjectFunctions(driver, selenium).createProjectAndGotoProjectDetailsPage(myUser, data);

        Reporter.log("Step 2 - Click on add intern engagement  button and create internship engagement");
        projectDetails.clickOnInternEngagementButton();
        projectDetailsCreateInternshipPO.fillCreateInternshipDetail(myUser);
        Assert.assertEquals(projectDetails.getInternshipName(myUser.getRealFirstName() + " " + myUser.getRealLastName()), (myUser.getRealFirstName() + " " + myUser.getRealLastName()), "project internship name doesn't match : ");

        Reporter.log("Step 3 - Click on edit button and update internship engagement ");
        projectDetails.clickOnEditInternshipButton(myUser.getRealFirstName() + " " + myUser.getRealLastName());

        Internship internData = new EngagementData().getCreateEngagementData();

        Reporter.log("Step 4 - Advance Internship status from 'In Development' to 'Active'");
        selenium.hardWait(3);
        projectInternshipDetailsPO.clickInternshipEngagementStatusButton();
        projectInternshipLanguageDetailsUpdateStatusPO.clickInternshipEngagementOverrideStatusByName(InternshipLanguageEngagementStatus.Active.getInternShipStatus());

        Reporter.log("Step 5 - Advance Internship status from 'Active' to 'Finalizing Completion'");
        projectInternshipDetailsPO.clickInternshipEngagementStatusButton();
        projectInternshipLanguageDetailsUpdateStatusPO.clickInternshipEngagementOverrideStatusByName(InternshipLanguageEngagementStatus.FinalizingCompletion.getInternShipStatus());

        Reporter.log("Step 6 - Advance Internship status from 'Finalizing Completion' to 'Completed'");
        projectInternshipDetailsPO.clickInternshipEngagementStatusButton();
        projectInternshipLanguageDetailsUpdateStatusPO.clickOnEngagementStatusByName(InternshipLanguageEngagementStatus.Complete.getInternShipStatus());

        Reporter.log("Step 7 - Click on start end date button and update project internship start end date");
        Project updateData = new ProjectData().getUpdateProjectData();
        projectInternshipDetailsPO.clickInternshipStartEndDateButton();
        projectEngagementDetailsUpdateStartEndDatePO.fillEngagementStartEndDateDetailsAndClickOnSaveButton(internData);

        Reporter.log("Step 8 - Click on enter intern position button and update project internship position");
        projectInternshipDetailsPO.clickEnterInternPositionButton();
        projectInternshipDetailsUpdateInternPositionPO.clickOnInternPositionByNameAndClickOnSaveButton(ProjectInternPosition.LanguageProgramManager.getInternPosition());

        Reporter.log("Step 9 - Click on enter country of origin button and update project internship location");
        projectInternshipDetailsPO.clickEnterCountryOfOriginButton();
        projectInternshipDetailsUpdateCountryOfOriginPO.fillInternshipCountryOfOriginDetailsAndClickOnSaveButton(updateData);

        Reporter.log("Step 10 - Click on growth plan complete date button and update growth plan complete date");
        projectInternshipDetailsPO.clickGrowthPlanCompleteDateButton();
        projectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO.fillInternshipGrowthPlanCompleteDateDetailsAndClickOnSaveButton(internData);

        Reporter.log("Step 11 - Click on disbursement complete date button and update disbursement complete date");
        projectInternshipDetailsPO.clickOnDisbursementCompleteDateButton();
        projectEngagementDetailsUpdateDisbursementCompleteDatePO.fillEngagementDisbursementCompleteDateDetailsAndClickOnSaveButton(internData);

        Reporter.log("Step 12 - Click on add growth plan button and add file");
        String textFileName = JavaHelpers.getPropertyValue(PROPERTY_TEST_FILE, "textFile");
        projectInternshipDetailsPO.clickOnAddGrowthPlanButton(textFileName);
        selenium.hardWait(5);

        Reporter.log("Step 13 - Click on methodologies button and update methodologies");
        projectInternshipDetailsPO.clickMethodologiesButton();
        projectInternshipDetailsUpdateInternMethodologiesPO.clickOnInternMethodologyByName(ProjectInternshipMethodologies.Film.getInternshipMethodology());
        projectInternshipDetailsUpdateInternMethodologiesPO.clickOnInternMethodologyByName(ProjectInternshipMethodologies.Paratext.getInternshipMethodology());
        projectInternshipDetailsUpdateInternMethodologiesPO.clickOnSaveButton();

        Reporter.log("Step 14 - Click on certification and edit button and update estimated and actual date");
        projectInternshipDetailsPO.clickCertificationToggleButton();
        projectInternshipDetailsPO.clickEditDatesButton();
        projectEngagementDetailsUpdateActualEstimatedDatePO.fillEngagementEstimatedActualDateDetailsAndClickOnSubmitButton(internData);

        Reporter.log("Step 15 - Click on add mentor button and update mentor");
        projectInternshipDetailsPO.clickAddMentorButton();
        projectInternshipDetailsUpdateMentorPO.fillInternshipMentorDetailsAndClickOnSaveButton(myUser);

        Assert.assertEquals(projectInternshipDetailsPO.getInternshipStatus(), InternshipLanguageEngagementStatus.Completed.getInternShipStatus(), "Project step is not changed");
        Assert.assertEquals(projectInternshipDetailsPO.getInternshipStartEndDate(), new JavaHelpers().changeDateFormat(internData.getInternshipStartDate(), "MM/dd/yyyy", "M/d/yyyy") + "\n" + " - " + "\n" + new JavaHelpers().changeDateFormat(internData.getInternshipEndDate(), "MM/dd/yyyy", "M/d/yyyy"), "Internship start end date doesn't match :");
        Assert.assertEquals(ProjectInternPosition.LanguageProgramManager.getInternPosition(), projectInternshipDetailsPO.getInternPosition(), "Internship position doesn't match : ");
        Assert.assertEquals(updateData.getLocationName(), projectInternshipDetailsPO.getInternLocationName(), "Internship location doesn't match : ");
        Assert.assertEquals(projectInternshipDetailsPO.getGrowthPlanCompleteDate(), new JavaHelpers().changeDateFormat(internData.getGrowthPlanCompleteDate(), "MM/dd/yyyy", "M/d/yyyy"), "Internship growth plan complete date  doesn't match :");
        Assert.assertEquals(projectInternshipDetailsPO.getDisbursementCompleteDate(), new JavaHelpers().changeDateFormat(internData.getDisbursementCompleteDate(), "MM/dd/yyyy", "M/d/yyyy"), "Internship disbursement complete date  doesn't match :");
        Assert.assertEquals(textFileName, projectInternshipDetailsPO.getUploadedFileText(), "uploaded file name doesn't match : ");
        Assert.assertEquals(projectInternshipDetailsPO.getMethodologiesText(), ProjectInternshipMethodologies.Film.getInternshipMethodology() + "\n" + ProjectInternshipMethodologies.Paratext.getInternshipMethodology(), "internship methodologies doesn't match : ");
        Assert.assertEquals(projectInternshipDetailsPO.getActualDateText(), new JavaHelpers().changeDateFormat(internData.getInternshipActualDate(), "MM/dd/yyyy", "M/d/yyyy"), "Internship actual date doesn't match : ");
        Assert.assertEquals(projectInternshipDetailsPO.getEstimatedDateText(), new JavaHelpers().changeDateFormat(internData.getInternshipEstimatedDate(), "MM/dd/yyyy", "M/d/yyyy"), "Internship estimated date doesn't match : ");
        Assert.assertEquals(projectInternshipDetailsPO.getMentorText(), myUser.getRealFirstName() + " " + myUser.getRealLastName(), "Internship mentor name doesn't match : ");

    }

}


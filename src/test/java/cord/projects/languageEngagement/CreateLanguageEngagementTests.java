package cord.projects.languageEngagement;

import base.BaseTest;
import datafactory.EngagementData;
import datafactory.LanguageData;
import datafactory.ProjectData;
import dataobjects.Internship;
import dataobjects.Language;
import dataobjects.Project;
import dataobjects.User;
import enums.engagement.InternshipLanguageEngagementStatus;
import funcLibs.project.ProjectFunctions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import pageobjects.cord.languages.CreateUpdateLanguagePO;
import pageobjects.cord.languages.LanguageDetailPO;
import pageobjects.cord.projects.ProjectDetailsCreateLanguageEngagementPO;
import pageobjects.cord.projects.ProjectDetailsPO;
import pageobjects.cord.projects.intershipEngagement.*;
import pageobjects.cord.projects.languageEngagement.ProjectLanguageEngagementDetailsPO;
import pageobjects.cord.projects.languageEngagement.ProjectLanguageEngagementDetailsUpdateFirstScriptureAndLukePartnershipPO;
import pageobjects.cord.projects.languageEngagement.ProjectLanguageEngagementDetailsUpdateStepsPO;
import utilities.JavaHelpers;

import java.text.ParseException;

public class CreateLanguageEngagementTests extends BaseTest {

    public static final String PROPERTY_TEST_FILE = "src/main/resources/testData/TestFiles.properties";

    /*Test 1 : Verify that user can create project language engagement successfully*/
    @Test
    public void verifyUserCanCreateProjectLanguageEngagementSuccessfully() throws InterruptedException, ParseException, org.json.simple.parser.ParseException {

        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        LanguageDetailPO languageDetailPO = new LanguageDetailPO(driver);
        CreateUpdateLanguagePO createUpdateLanguagePO = new CreateUpdateLanguagePO(driver);
        ProjectEngagementDetailsUpdateParaTextRegisteryIdPO projectEngagementDetailsUpdateParaTextRegisteryIdPO = new ProjectEngagementDetailsUpdateParaTextRegisteryIdPO(driver);
        ProjectDetailsCreateLanguageEngagementPO projectDetailsCreateLanguageEngagementPO = new ProjectDetailsCreateLanguageEngagementPO(driver);
        ProjectEngagementDetailsUpdateStartEndDatePO projectEngagementDetailsUpdateStartEndDatePO = new ProjectEngagementDetailsUpdateStartEndDatePO(driver);
        ProjectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO projectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO = new ProjectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO(driver);
        ProjectEngagementDetailsUpdateDisbursementCompleteDatePO projectEngagementDetailsUpdateDisbursementCompleteDatePO = new ProjectEngagementDetailsUpdateDisbursementCompleteDatePO(driver);
        ProjectEngagementDetailsUpdateActualEstimatedDatePO projectEngagementDetailsUpdateActualEstimatedDatePO = new ProjectEngagementDetailsUpdateActualEstimatedDatePO(driver);
        ProjectInternshipLanguageDetailsUpdateStatusPO projectInternshipLanguageDetailsUpdateStatusPO = new ProjectInternshipLanguageDetailsUpdateStatusPO(driver);
        ProjectLanguageEngagementDetailsPO projectLanguageEngagementDetailsPO = new ProjectLanguageEngagementDetailsPO(driver);
        ProjectLanguageEngagementDetailsUpdateStepsPO projectLanguageEngagementDetailsUpdateStepsPO = new ProjectLanguageEngagementDetailsUpdateStepsPO(driver);
        ProjectLanguageEngagementDetailsUpdateFirstScriptureAndLukePartnershipPO projectLanguageEngagementDetailsUpdateFirstScriptureAndLukePartnershipPO = new ProjectLanguageEngagementDetailsUpdateFirstScriptureAndLukePartnershipPO(driver);
        /*
         * We can change whole test process for specific user from here.
         * */
        User myUser = adminUser;

        Reporter.log("Step 1 - Create Project");
        Project data = new ProjectData().getCreateTranslationProjectData();
        new ProjectFunctions(driver, selenium).createProjectAndGotoProjectDetailsPage(myUser, data);

        Reporter.log("Step 2 - Click on add language engagement button and create language engagement");
        Language languageData = new LanguageData().getLanguageData();
        projectDetails.clickOnLanguageEngagementButton();
        projectDetailsCreateLanguageEngagementPO.fillLanguageEngagementDetailAndClickOnSubmitButton(languageData);
        Assert.assertEquals(projectDetails.getLanguageEngagementName(languageData.getLanguageEngagementName()), languageData.getLanguageEngagementName(), "project language engagement name doesn't match");

        Reporter.log("Step 3 - Click on view details button and update language engagement ");
        projectDetails.clickOnViewDetailsLanguageEngagementButton(languageData.getLanguageEngagementName());
        Internship internData = new EngagementData().getCreateEngagementData();

        Reporter.log("Step 4 - Click on edit Language Engagement button and update first scripture and luke partnership ");
        projectLanguageEngagementDetailsPO.clickOnUpdateLanguageEngagementButton();
        projectLanguageEngagementDetailsUpdateFirstScriptureAndLukePartnershipPO.updateFirstScriptureLukePartnershipAndClickOnSaveButton();

        if (!projectLanguageEngagementDetailsPO.isFirstScriptureLabelPresent()) {
            projectLanguageEngagementDetailsPO.clickOnLanguageEngagementNameLink();
            languageDetailPO.clickOnEditButton();
            createUpdateLanguagePO.updateFirstScripture();  // Todo bug (#502)
        }

        Reporter.log("Step 5 - Advance Language Engagement status from 'In Development' to 'Active'");
        projectLanguageEngagementDetailsPO.clickLanguageEngagementStatusButton();
        projectLanguageEngagementDetailsUpdateStepsPO.clickLanguageEngagementStatusByName(InternshipLanguageEngagementStatus.Active.getInternShipStatus());

        Reporter.log("Step 6 - Advance Language Engagement status from 'Active' to 'Finalizing Completion'");
        projectLanguageEngagementDetailsPO.clickLanguageEngagementStatusButton();
        projectLanguageEngagementDetailsUpdateStepsPO.clickLanguageEngagementStatusByName(InternshipLanguageEngagementStatus.FinalizingCompletion.getInternShipStatus());

        Reporter.log("Step 7 - Advance Language Engagement status from 'Finalizing Completion' to 'Completed'");
        projectLanguageEngagementDetailsPO.clickLanguageEngagementStatusButton();
        projectInternshipLanguageDetailsUpdateStatusPO.clickOnEngagementStatusByName(InternshipLanguageEngagementStatus.Complete.getInternShipStatus());

        Reporter.log("Step 8 - Click on start end date button and update project language engagement start end date");
        projectLanguageEngagementDetailsPO.clickLanguageEngagementStartEndDateButton();
        projectEngagementDetailsUpdateStartEndDatePO.fillEngagementStartEndDateDetailsAndClickOnSaveButton(internData);

        Reporter.log("Step 9 - Click on enter para text registry ID button  and update project language engagement para text registry ID");
        projectLanguageEngagementDetailsPO.clickLanguageEngagementParaTextRegistryIdButton();
        projectEngagementDetailsUpdateParaTextRegisteryIdPO.fillEngagementParaTextRegistryIdDetailsAndClickOnSaveButton(internData);

        Reporter.log("Step 10 - Click on translation complete date button and update translation complete date");
        projectLanguageEngagementDetailsPO.clickTranslationCompleteDateTextBox();
        projectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO.fillInternshipGrowthPlanCompleteDateDetailsAndClickOnSaveButton(internData);

        Reporter.log("Step 11 - Click on disbursement complete date button and update disbursement complete date");
        projectLanguageEngagementDetailsPO.clickOnDisbursementCompleteDateTextBox();
        projectEngagementDetailsUpdateDisbursementCompleteDatePO.fillEngagementDisbursementCompleteDateDetailsAndClickOnSaveButton(internData);

        Reporter.log("Step 12 - Click on planning and progress button and add file");
        String textFileName = JavaHelpers.getPropertyValue(PROPERTY_TEST_FILE, "textFile");
        projectLanguageEngagementDetailsPO.clickOnAddPlanningAndProgressButton(textFileName);
        selenium.hardWait(5);

        Reporter.log("Step 13 - Click on dedication and edit button and update estimated and actual date");
        projectLanguageEngagementDetailsPO.clickDedicationToggleButton();
        projectLanguageEngagementDetailsPO.clickEditDatesButton();
        projectEngagementDetailsUpdateActualEstimatedDatePO.fillEngagementEstimatedActualDateDetailsAndClickOnSubmitButton(internData);

        Assert.assertEquals(projectLanguageEngagementDetailsPO.getLanguageEngagementStatus(), InternshipLanguageEngagementStatus.Completed.getInternShipStatus(), "Project step is not changed");
        Assert.assertEquals(projectLanguageEngagementDetailsPO.getLanguageStartEndDate(), new JavaHelpers().changeDateFormat(internData.getInternshipStartDate(), "MM/dd/yyyy", "M/d/yyyy")+"\n" + " - " + "\n"+new JavaHelpers().changeDateFormat(internData.getInternshipEndDate(), "MM/dd/yyyy", "M/d/yyyy"), "Language engagement start end date doesn't match");
        Assert.assertEquals(projectLanguageEngagementDetailsPO.getLanguageParaTextRegistryId().replace("Paratext Registry ID: ",""), internData.getParaTextRegId() , "ParaText register id doesn't match");
        Assert.assertEquals(projectLanguageEngagementDetailsPO.getTranslationCompleteDate(), new JavaHelpers().changeDateFormat(internData.getGrowthPlanCompleteDate(), "MM/dd/yyyy", "M/d/yyyy"), "Language engagement growth plan complete date  doesn't match");
        Assert.assertEquals(projectLanguageEngagementDetailsPO.getDisbursementCompleteDate(), new JavaHelpers().changeDateFormat(internData.getDisbursementCompleteDate(), "MM/dd/yyyy", "M/d/yyyy"), "Language engagement disbursement complete date  doesn't match");
        Assert.assertEquals(textFileName, projectLanguageEngagementDetailsPO.getUploadedFileText(), "uploaded file name doesn't match : ");
        Assert.assertEquals(projectLanguageEngagementDetailsPO.getActualDateTextBox(), new JavaHelpers().changeDateFormat(internData.getInternshipActualDate(), "MM/dd/yyyy", "M/d/yyyy"), "Language engagement actual date doesn't match");
        Assert.assertEquals(projectLanguageEngagementDetailsPO.getEstimatedDateTextBox(), new JavaHelpers().changeDateFormat(internData.getInternshipEstimatedDate(), "MM/dd/yyyy", "M/d/yyyy"), "Language engagement estimated date doesn't match");
        Assert.assertTrue(projectLanguageEngagementDetailsPO.isLukePartnershipLabelPresent(), "Language luke partnership label is not present");
    }

}


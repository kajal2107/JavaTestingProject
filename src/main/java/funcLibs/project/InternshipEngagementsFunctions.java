package funcLibs.project;

import datafactory.EngagementData;
import datafactory.ProjectData;
import dataobjects.Internship;
import dataobjects.Project;
import dataobjects.User;
import enums.project.ProjectInternPosition;
import enums.project.ProjectInternshipMethodologies;
import enums.users.UserRole;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import pageobjects.cord.common.HeaderPO;
import pageobjects.cord.common.LeftNavPO;
import pageobjects.cord.projects.ProjectDetailsCreateInternshipPO;
import pageobjects.cord.projects.ProjectDetailsPO;
import pageobjects.cord.projects.ProjectsListingPO;
import pageobjects.cord.projects.intershipEngagement.*;
import utilities.JavaHelpers;
import utilities.SeleniumHelpers;

public class InternshipEngagementsFunctions {

    protected WebDriver driver;
    protected SeleniumHelpers selenium;
    public static final String PROPERTY_TEST_FILE = "src/main/resources/testData/TestFiles.properties";

    public InternshipEngagementsFunctions(WebDriver driver, SeleniumHelpers selenium) {
        this.driver = driver;
        this.selenium = selenium;
    }

    /**
     * create project internship engagement
     *
     * @param user        user
     * @param projectName projectName
     */

    public void createProjectInternshipEngagements(User user, String projectName) {

        try {

            ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
            LeftNavPO sideHeader = new LeftNavPO(driver);
            ProjectsListingPO projectsListingPO = new ProjectsListingPO(driver);
            ProjectDetailsCreateInternshipPO projectDetailsCreateInternshipPO = new ProjectDetailsCreateInternshipPO(driver);
            HeaderPO headerPO = new HeaderPO(driver);

            Reporter.log("Step 1 - Find created project");
            sideHeader.clickOnProjectsMenu();
            if (!projectsListingPO.isProjectPresent(projectName)) {
                headerPO.searchItemGloballyByName(projectName);
            }
            projectsListingPO.clickOnProjectName(projectName);

            Reporter.log("Step 2 - Click on add intern engagement button and create internship engagement");
            projectDetails.clickOnInternEngagementButton();
            projectDetailsCreateInternshipPO.fillCreateInternshipDetail(user);
        } catch (Exception e) {
            System.out.println(e);
        }


    }

    /**
     * edit project internship engagement
     *
     * @param user user
     */

    public void editProjectInternshipEngagements(User user) {

        try {
            ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
            ProjectInternshipDetailsPO projectInternshipDetailsPO = new ProjectInternshipDetailsPO(driver);
            ProjectEngagementDetailsUpdateStartEndDatePO projectEngagementDetailsUpdateStartEndDatePO = new ProjectEngagementDetailsUpdateStartEndDatePO(driver);
            ProjectInternshipDetailsUpdateCountryOfOriginPO projectInternshipDetailsUpdateCountryOfOriginPO = new ProjectInternshipDetailsUpdateCountryOfOriginPO(driver);
            ProjectInternshipDetailsUpdateInternPositionPO projectInternshipDetailsUpdateInternPositionPO = new ProjectInternshipDetailsUpdateInternPositionPO(driver);
            ProjectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO projectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO = new ProjectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO(driver);
            ProjectEngagementDetailsUpdateDisbursementCompleteDatePO projectEngagementDetailsUpdateDisbursementCompleteDatePO = new ProjectEngagementDetailsUpdateDisbursementCompleteDatePO(driver);
            ProjectInternshipDetailsUpdateInternMethodologiesPO projectInternshipDetailsUpdateInternMethodologiesPO = new ProjectInternshipDetailsUpdateInternMethodologiesPO(driver);
            ProjectEngagementDetailsUpdateActualEstimatedDatePO projectEngagementDetailsUpdateActualEstimatedDatePO = new ProjectEngagementDetailsUpdateActualEstimatedDatePO(driver);
            ProjectInternshipDetailsUpdateMentorPO projectInternshipDetailsUpdateMentorPO = new ProjectInternshipDetailsUpdateMentorPO(driver);

            Reporter.log("Step 1 - Click on edit button and navigate to internship engagement details page");
            projectDetails.clickOnEditInternshipButton(user.getRealFirstName() + " " + user.getRealLastName());
            Internship internData = new EngagementData().getCreateEngagementData();
            Internship internUpdateData = new EngagementData().getUpdateEngagementData();

            Reporter.log("Step 2 - Add internship engagement start end date ");
            Project updateData = new ProjectData().getUpdateProjectData();
            projectInternshipDetailsPO.clickInternshipStartEndDateButton();
            projectEngagementDetailsUpdateStartEndDatePO.fillEngagementStartEndDateDetailsAndClickOnSaveButton(internData);

            Reporter.log("Step 3 - Add internship engagement position ");
            projectInternshipDetailsPO.clickEnterInternPositionButton();
            projectInternshipDetailsUpdateInternPositionPO.clickOnInternPositionByNameAndClickOnSaveButton(ProjectInternPosition.LanguageProgramManager.getInternPosition());

            Reporter.log("Step 4 - Add internship engagement date ");
            projectInternshipDetailsPO.clickEnterCountryOfOriginButton();
            projectInternshipDetailsUpdateCountryOfOriginPO.fillInternshipCountryOfOriginDetailsAndClickOnSaveButton(updateData);
            projectInternshipDetailsPO.clickGrowthPlanCompleteDateButton();
            projectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO.fillInternshipGrowthPlanCompleteDateDetailsAndClickOnSaveButton(internData);
            projectInternshipDetailsPO.clickOnDisbursementCompleteDateButton();
            projectEngagementDetailsUpdateDisbursementCompleteDatePO.fillEngagementDisbursementCompleteDateDetailsAndClickOnSaveButton(internData);

            Reporter.log("Step 5 - Add internship engagement File ");
            String textFileName = JavaHelpers.getPropertyValue(PROPERTY_TEST_FILE, "textFile");
            projectInternshipDetailsPO.clickOnAddGrowthPlanButton(textFileName);

            selenium.hardWait(5);
            Reporter.log("Step 6 - Add internship engagement Methodologies");
            projectInternshipDetailsPO.clickMethodologiesButton();
            projectInternshipDetailsUpdateInternMethodologiesPO.clickOnInternMethodologyByName(ProjectInternshipMethodologies.Film.getInternshipMethodology());
            projectInternshipDetailsUpdateInternMethodologiesPO.clickOnInternMethodologyByName(ProjectInternshipMethodologies.Paratext.getInternshipMethodology());
            projectInternshipDetailsUpdateInternMethodologiesPO.clickOnSaveButton();

            if (user.getUserRoles().contains(UserRole.ConsultantManager.getRole())) {

                Reporter.log("Step 8 - Add internship engagement mentor");
                projectInternshipDetailsPO.clickAddMentorButton();
                projectInternshipDetailsUpdateMentorPO.fillInternshipMentorDetailsAndClickOnSaveButton(user);

                Reporter.log("Step 9 - Edit internship engagement start end date ");
                projectInternshipDetailsPO.clickInternshipStartEndDateButton();
                projectEngagementDetailsUpdateStartEndDatePO.fillEngagementStartEndDateDetailsAndClickOnSaveButton(internUpdateData);

                Reporter.log("Step 10 - Edit internship engagement position ");
                projectInternshipDetailsPO.clickEnterInternPositionButton();
                projectInternshipDetailsUpdateInternPositionPO.clickOnInternPositionByNameAndClickOnSaveButton(ProjectInternPosition.Administration.getInternPosition());

                Reporter.log("Step 11 - Edit internship engagement date ");
                projectInternshipDetailsPO.clickEnterCountryOfOriginButton();
                projectInternshipDetailsUpdateCountryOfOriginPO.fillInternshipCountryOfOriginDetailsAndClickOnSaveButton(updateData);
                projectInternshipDetailsPO.clickGrowthPlanCompleteDateButton();
                projectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO.fillInternshipGrowthPlanCompleteDateDetailsAndClickOnSaveButton(internUpdateData);
                projectInternshipDetailsPO.clickOnDisbursementCompleteDateButton();
                projectEngagementDetailsUpdateDisbursementCompleteDatePO.fillEngagementDisbursementCompleteDateDetailsAndClickOnSaveButton(internUpdateData);

                Reporter.log("Step 12 - Edit internship engagement File ");
                String newVersionFileName = JavaHelpers.getPropertyValue(PROPERTY_TEST_FILE, "newVersionFile");
                projectInternshipDetailsPO.clickOnAddGrowthPlanInfoButtonAndAddNewVersionFile(newVersionFileName);

                selenium.hardWait(5);

                Reporter.log("Step 13 - Edit internship engagement Methodologies");
                projectInternshipDetailsPO.clickMethodologiesButton();
                projectInternshipDetailsUpdateInternMethodologiesPO.clickOnInternMethodologyByName(ProjectInternshipMethodologies.Film.getInternshipMethodology());
                projectInternshipDetailsUpdateInternMethodologiesPO.clickOnInternMethodologyByName(ProjectInternshipMethodologies.Paratext.getInternshipMethodology());
                projectInternshipDetailsUpdateInternMethodologiesPO.clickOnInternMethodologyByName(ProjectInternshipMethodologies.OneStory.getInternshipMethodology());
                projectInternshipDetailsUpdateInternMethodologiesPO.clickOnInternMethodologyByName(ProjectInternshipMethodologies.Render.getInternshipMethodology());
                projectInternshipDetailsUpdateInternMethodologiesPO.clickOnSaveButton();


            } else {

                Reporter.log("Step 7 - Add internship engagement actual date and estimated date");
                projectInternshipDetailsPO.clickCertificationToggleButton();
                projectInternshipDetailsPO.clickEditDatesButton();
                projectEngagementDetailsUpdateActualEstimatedDatePO.fillEngagementEstimatedActualDateDetailsAndClickOnSubmitButton(internData);

                Reporter.log("Step 8 - Add internship engagement mentor");
                projectInternshipDetailsPO.clickAddMentorButton();
                projectInternshipDetailsUpdateMentorPO.fillInternshipMentorDetailsAndClickOnSaveButton(user);

                Reporter.log("Step 9 - Edit internship engagement start end date ");
                projectInternshipDetailsPO.clickInternshipStartEndDateButton();
                projectEngagementDetailsUpdateStartEndDatePO.fillEngagementStartEndDateDetailsAndClickOnSaveButton(internUpdateData);

                Reporter.log("Step 10 - Edit internship engagement position ");
                projectInternshipDetailsPO.clickEnterInternPositionButton();
                projectInternshipDetailsUpdateInternPositionPO.clickOnInternPositionByNameAndClickOnSaveButton(ProjectInternPosition.Administration.getInternPosition());

                Reporter.log("Step 11 - Edit internship engagement date ");
                projectInternshipDetailsPO.clickEnterCountryOfOriginButton();
                projectInternshipDetailsUpdateCountryOfOriginPO.fillInternshipCountryOfOriginDetailsAndClickOnSaveButton(updateData);
                projectInternshipDetailsPO.clickGrowthPlanCompleteDateButton();
                projectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO.fillInternshipGrowthPlanCompleteDateDetailsAndClickOnSaveButton(internUpdateData);
                projectInternshipDetailsPO.clickOnDisbursementCompleteDateButton();
                projectEngagementDetailsUpdateDisbursementCompleteDatePO.fillEngagementDisbursementCompleteDateDetailsAndClickOnSaveButton(internUpdateData);

                Reporter.log("Step 12 - Edit internship engagement File ");
                String newVersionFileName = JavaHelpers.getPropertyValue(PROPERTY_TEST_FILE, "newVersionFile");
                projectInternshipDetailsPO.clickOnAddGrowthPlanInfoButtonAndAddNewVersionFile(newVersionFileName);

                selenium.hardWait(5);

                Reporter.log("Step 13 - Edit internship engagement Methodologies");
                projectInternshipDetailsPO.clickMethodologiesButton();
                projectInternshipDetailsUpdateInternMethodologiesPO.clickOnInternMethodologyByName(ProjectInternshipMethodologies.Film.getInternshipMethodology());
                projectInternshipDetailsUpdateInternMethodologiesPO.clickOnInternMethodologyByName(ProjectInternshipMethodologies.Paratext.getInternshipMethodology());
                projectInternshipDetailsUpdateInternMethodologiesPO.clickOnInternMethodologyByName(ProjectInternshipMethodologies.OneStory.getInternshipMethodology());
                projectInternshipDetailsUpdateInternMethodologiesPO.clickOnInternMethodologyByName(ProjectInternshipMethodologies.Render.getInternshipMethodology());
                projectInternshipDetailsUpdateInternMethodologiesPO.clickOnSaveButton();

                Reporter.log("Step 14 - Edit internship engagement start end date ");
                projectInternshipDetailsPO.clickEditDatesButton();
                projectEngagementDetailsUpdateActualEstimatedDatePO.fillEngagementEstimatedActualDateDetailsAndClickOnSubmitButton(internUpdateData);

            }
            Reporter.log("Step 15- Edit internship engagement mentor");
            projectInternshipDetailsPO.clickAddMentorButton();
            projectInternshipDetailsUpdateMentorPO.fillInternshipMentorDetailsAndClickOnSaveButton(user);
        } catch (Exception e) {
            System.out.println(e);
        }


    }

}

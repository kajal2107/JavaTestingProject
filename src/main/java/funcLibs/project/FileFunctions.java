package funcLibs.project;

import enums.project.ProjectSubTabs;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import pageobjects.cord.common.HeaderPO;
import pageobjects.cord.common.LeftNavPO;
import pageobjects.cord.projects.ProjectDetailsPO;
import pageobjects.cord.projects.ProjectsListingPO;
import utilities.SeleniumHelpers;

public class FileFunctions {

    protected WebDriver driver;
    protected SeleniumHelpers selenium;
    
    public FileFunctions(WebDriver driver, SeleniumHelpers selenium) {
        this.driver = driver;
        this.selenium = selenium;
    }

    /**
     * upload project file
     *
     * @param projectName project name
     * @param fileName    fileName
     */

    public void uploadFile(String projectName, String fileName) {

        try {
            ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
            LeftNavPO sideHeader = new LeftNavPO(driver);
            ProjectsListingPO projectsListingPO = new ProjectsListingPO(driver);
            HeaderPO headerPO = new HeaderPO(driver);

            Reporter.log("Step 1 - Find created project");
            sideHeader.clickOnProjectsMenu();
            projectsListingPO.clickOnProjectSubTabs(ProjectSubTabs.All.getProjectSubTabs());

            if (!projectsListingPO.isProjectPresent(projectName)) {
                headerPO.searchItemGloballyByName(projectName);
            }
            projectsListingPO.clickOnProjectName(projectName);

            Reporter.log("Step 2 - Click on upload file button and add file");
            projectDetails.clickOnUploadFileButton(fileName);
            selenium.hardWait(5);
        } catch (Exception e) {
            System.out.println(e);
        }


    }

}

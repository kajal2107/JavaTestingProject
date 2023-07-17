package cord.projects.updateProject;

import base.BaseTest;
import datafactory.ProjectData;
import dataobjects.Project;
import funcLibs.project.ProjectFunctions;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageobjects.cord.account.LoginPO;
import pageobjects.cord.projects.*;
import utilities.DriverManager;
import utilities.JavaHelpers;
import utilities.SeleniumHelpers;

import java.text.ParseException;

public class UpdateProjectFileTests extends BaseTest {

    public static final String PROPERTY_TEST_FILE = "src/main/resources/testData/TestFiles.properties";

    protected String strUrl;
    private Project updateData;

    @BeforeClass
    public void beforeClass() throws InterruptedException, org.json.simple.parser.ParseException, ParseException {
        WebDriver localDriver;
        DriverManager drivermanager;
        updateData = new ProjectData().getUpdateProjectData();

        drivermanager = new DriverManager();
        localDriver = drivermanager.setUp("chrome");
        SeleniumHelpers localSelenium = new SeleniumHelpers(localDriver);

        Reporter.log("Step 1 - Create Project");
        Project data = new ProjectData().getCreateProjectData();
        new ProjectFunctions(localDriver, localSelenium).createProjectAndGotoProjectDetailsPage(adminUser, data);
        strUrl = localDriver.getCurrentUrl();
        drivermanager.tearDown();
    }

    /*Test 1 : Verify user can add rename delete and upload newVersion of project file successfully*/
    @Test
    public void verifyUserCanUploadRenameDeleteAndUploadNewVersionOfProjectFileSuccessfully() throws InterruptedException {

        LoginPO login = new LoginPO(driver);
        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectDetailsFilesListingPO projectDetailsFilesListingPO = new ProjectDetailsFilesListingPO(driver);
        ProjectDetailsFileListingRenameFileFolderPO projectDetailsFileListingRenameFileFolderPO = new ProjectDetailsFileListingRenameFileFolderPO(driver);
        ProjectDetailsFileListingDeleteFileFolderPO projectDetailsFileListingDeleteFileFolderPO = new ProjectDetailsFileListingDeleteFileFolderPO(driver);
        ProjectDetailsFilesHistoryListingPO projectDetailsFilesHistoryListingPO = new ProjectDetailsFilesHistoryListingPO(driver);

        Reporter.log("Step 1 - Login and navigate to project detail page");
        selenium.navigateToPage(strUrl);
        login.fillLoginDetailsAndPerformLogin(adminUser.getEmail(), adminUser.getPassword());

        Reporter.log("Step 2 - Click on view file button and add new file");
        projectDetails.clickOnViewFileButton();
        String textFileName = JavaHelpers.getPropertyValue(PROPERTY_TEST_FILE, "textFile");
        projectDetailsFilesListingPO.uploadNewFile(textFileName);
        selenium.hardWait(5);
        Assert.assertEquals(projectDetailsFilesListingPO.getLastFileUploadText(), textFileName, "project new file name doesn't match : ");

        Reporter.log("Step 3 - Click on kebab menu and add new version of file");
        String versionFileName = JavaHelpers.getPropertyValue(PROPERTY_TEST_FILE, "newVersionFile");
        projectDetailsFilesListingPO.clickOnKebabMenuAndAddNewFileVersion(projectDetailsFilesListingPO.getLastFileUploadText().replace(".txt", ""), versionFileName);
        selenium.hardWait(5);
        Assert.assertEquals(projectDetailsFilesListingPO.getLastFileUploadText(), versionFileName, "project new file version name doesn't match : ");
        projectDetailsFilesListingPO.closeTheKebabMenu();

        Reporter.log("Step 4 - Click on kebab menu and view the history of file");
        projectDetailsFilesListingPO.clickOnKebabMenuAndViewFileHistory(projectDetailsFilesListingPO.getLastFileUploadText().replace(".txt", ""));
        Assert.assertTrue(projectDetailsFilesHistoryListingPO.isFilePresent(versionFileName), "file is not present : ");

        Reporter.log("Step 5 - Click on history kebab menu and rename the file");
        projectDetailsFilesHistoryListingPO.clickOnHistoryKebabMenuAndRenameFileFolder(projectDetailsFilesListingPO.getLastFileUploadText());
        projectDetailsFileListingRenameFileFolderPO.clearAndRenameFileFolderName(updateData);
        Assert.assertTrue(projectDetailsFilesHistoryListingPO.isFilePresent(updateData.getFileName() + ".txt"), "renamed file is not present : ");
        Assert.assertFalse(projectDetailsFilesHistoryListingPO.isFilePresent(versionFileName), "renamed file is present : ");

        Reporter.log("Step 6 - Click on history kebab menu and delete created file");
        projectDetailsFilesHistoryListingPO.clickOnKebabMenuAndDeleteFileFolder(projectDetailsFilesListingPO.getFirstFileUploadText());
        projectDetailsFileListingDeleteFileFolderPO.clickOnFileDeleteButton();
        Assert.assertFalse(projectDetailsFilesListingPO.isFileFolderPresent(updateData.getFileName()), "deleted file is present : ");

        Reporter.log("Step 7 - Click on kebab menu and rename created file");
        projectDetailsFilesHistoryListingPO.clickOnCloseButton();
        projectDetailsFilesListingPO.clickOnKebabMenuAndRenameFileFolder(projectDetailsFilesListingPO.getLastFileUploadText().replace(".txt", ""));
        projectDetailsFileListingRenameFileFolderPO.clearAndRenameFileFolderName(updateData);
        Assert.assertTrue(projectDetailsFilesListingPO.isFileFolderPresent(updateData.getFileName()), "renamed file is not present : ");
        Assert.assertFalse(projectDetailsFilesListingPO.isFileFolderPresent(textFileName), "renamed file is present : ");

        Reporter.log("Step 8 - Click on kebab menu and delete created file");
        projectDetailsFilesListingPO.clickOnKebabMenuAndDeleteFileFolder(updateData.getFileName());
        projectDetailsFileListingDeleteFileFolderPO.clickOnFileDeleteButton();
        Assert.assertFalse(projectDetailsFilesListingPO.isFileFolderPresent(updateData.getFileName()), "deleted file is present : ");

    }

    /*Test 2 : Verify user can create rename delete project folder successfully*/
    @Test
    public void verifyUserCanCreateRenameDeleteProjectFolderSuccessfully() throws InterruptedException, org.json.simple.parser.ParseException, ParseException {

        LoginPO login = new LoginPO(driver);
        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectDetailsFilesListingPO projectDetailsFilesListingPO = new ProjectDetailsFilesListingPO(driver);
        ProjectDetailsFileListingCreateFolderPO projectDetailsFileListingCreateFolderPO = new ProjectDetailsFileListingCreateFolderPO(driver);
        ProjectDetailsFileListingRenameFileFolderPO projectDetailsFileListingRenameFileFolderPO = new ProjectDetailsFileListingRenameFileFolderPO(driver);
        ProjectDetailsFileListingDeleteFileFolderPO projectDetailsFileListingDeleteFileFolderPO = new ProjectDetailsFileListingDeleteFileFolderPO(driver);

        Reporter.log("Step 1 - Login and navigate to project detail page");
        selenium.navigateToPage(strUrl);
        login.fillLoginDetailsAndPerformLogin(adminUser.getEmail(), adminUser.getPassword());

        Reporter.log("Step 2 - Click on create folder button and add new folder");
        projectDetails.clickOnViewFileButton();
        projectDetailsFilesListingPO.clickOnCreateNewFolderButton();
        Project updateData = new ProjectData().getUpdateProjectData();
        projectDetailsFileListingCreateFolderPO.clearAndFillFolderDetail(updateData);
        selenium.hardWait(3);
        Assert.assertTrue(projectDetailsFilesListingPO.isFileFolderPresent(updateData.getFolderName()), "file folder is not present : ");

        Reporter.log("Step 3 - Click on folder kebab menu and rename folder");
        projectDetailsFilesListingPO.clickOnKebabMenuAndRenameFileFolder(updateData.getFolderName());
        projectDetailsFileListingRenameFileFolderPO.clearAndRenameFileFolderName(updateData);
        Assert.assertTrue(projectDetailsFilesListingPO.isFileFolderPresent(updateData.getFileName()), "renamed file folder is not present : ");

        Reporter.log("Step 4 - Click on folder kebab menu and delete folder");
        projectDetailsFilesListingPO.clickOnKebabMenuAndDeleteFileFolder(updateData.getFileName());
        projectDetailsFileListingDeleteFileFolderPO.clickOnFileDeleteButton();
        Assert.assertFalse(projectDetailsFilesListingPO.isFileFolderPresent(updateData.getFileName()), "deleted file folder is present : ");
    }

    /*Test 3 : Verify User Can Add Project Sub File In Folder Successfully*/
    @Test
    public void verifyUserCanUploadRenameDeleteAndUploadNewVersionOfProjectFileAndCreateRenameDeleteFolderInSubFolderSuccessfully() throws InterruptedException {

        LoginPO login = new LoginPO(driver);
        ProjectDetailsPO projectDetails = new ProjectDetailsPO(driver);
        ProjectDetailsFileListingCreateFolderPO projectDetailsFileListingCreateFolderPO = new ProjectDetailsFileListingCreateFolderPO(driver);
        ProjectDetailsFilesListingPO projectDetailsFilesListingPO = new ProjectDetailsFilesListingPO(driver);
        ProjectDetailsFileListingRenameFileFolderPO projectDetailsFileListingRenameFileFolderPO = new ProjectDetailsFileListingRenameFileFolderPO(driver);
        ProjectDetailsFileListingDeleteFileFolderPO projectDetailsFileListingDeleteFileFolderPO = new ProjectDetailsFileListingDeleteFileFolderPO(driver);
        ProjectDetailsFilesHistoryListingPO projectDetailsFilesHistoryListingPO = new ProjectDetailsFilesHistoryListingPO(driver);

        Reporter.log("Step 1 - Login and navigate to project detail page");
        selenium.navigateToPage(strUrl);
        login.fillLoginDetailsAndPerformLogin(adminUser.getEmail(), adminUser.getPassword());

        Reporter.log("Step 2 - Click on create folder button and add new folder");
        projectDetails.clickOnViewFileButton();
        projectDetailsFilesListingPO.clickOnCreateNewFolderButton();
        projectDetailsFileListingCreateFolderPO.clearAndFillFolderDetail(updateData);

        Reporter.log("Step 3 - Go to folder");
        projectDetailsFilesListingPO.clickOnCreatedFolderByName(updateData.getFolderName());
        selenium.hardWait(5);

        Reporter.log("Step 4 - Upload new file");
        String textFileName = JavaHelpers.getPropertyValue(PROPERTY_TEST_FILE, "textFile");
        projectDetailsFilesListingPO.uploadNewFile(textFileName);
        selenium.hardWait(5);
        Assert.assertEquals(projectDetailsFilesListingPO.getLastFileUploadText(), textFileName, "project new file name doesn't match : ");

        Reporter.log("Step 5 - Click on kebab menu and add new version of file");
        String versionFileName = JavaHelpers.getPropertyValue(PROPERTY_TEST_FILE, "newVersionFile");
        projectDetailsFilesListingPO.clickOnKebabMenuAndAddNewFileVersion(projectDetailsFilesListingPO.getLastFileUploadText().replace(".txt", ""), versionFileName);
        Assert.assertEquals(projectDetailsFilesListingPO.getLastFileUploadText(), versionFileName, "project new file version name doesn't match : ");
        projectDetailsFilesListingPO.closeTheKebabMenu();

        Reporter.log("Step 6 - Click on kebab menu and view the history of file");
        projectDetailsFilesListingPO.clickOnKebabMenuAndViewFileHistory(projectDetailsFilesListingPO.getLastFileUploadText().replace(".txt", ""));
        Assert.assertTrue(projectDetailsFilesHistoryListingPO.isFilePresent(versionFileName), "file is not present : ");

        Reporter.log("Step 7 - Click on history kebab menu and rename the file");
        projectDetailsFilesHistoryListingPO.clickOnHistoryKebabMenuAndRenameFileFolder(projectDetailsFilesListingPO.getLastFileUploadText());
        projectDetailsFileListingRenameFileFolderPO.clearAndRenameFileFolderName(updateData);
        Assert.assertTrue(projectDetailsFilesHistoryListingPO.isFilePresent(updateData.getFileName() + ".txt"), "renamed file is not present : ");
        Assert.assertFalse(projectDetailsFilesHistoryListingPO.isFilePresent(versionFileName), "renamed file is not present : ");

        Reporter.log("Step 8 - Click on history kebab menu and delete created file");
        projectDetailsFilesHistoryListingPO.clickOnKebabMenuAndDeleteFileFolder(projectDetailsFilesListingPO.getFirstFileUploadText());
        projectDetailsFileListingDeleteFileFolderPO.clickOnFileDeleteButton();
        Assert.assertFalse(projectDetailsFilesListingPO.isFileFolderPresent(updateData.getFileName()), "deleted file is present : ");

        Reporter.log("Step 9 - Click on kebab menu and rename created file");
        projectDetailsFilesHistoryListingPO.clickOnCloseButton();
        projectDetailsFilesListingPO.clickOnKebabMenuAndRenameFileFolder(projectDetailsFilesListingPO.getLastFileUploadText().replace(".txt", ""));
        projectDetailsFileListingRenameFileFolderPO.clearAndRenameFileFolderName(updateData);
        Assert.assertTrue(projectDetailsFilesListingPO.isFileFolderPresent(updateData.getFileName()), "renamed file is not present : ");
        Assert.assertFalse(projectDetailsFilesListingPO.isFileFolderPresent(textFileName), "renamed file is present : ");

        Reporter.log("Step 10 - Click on kebab menu and delete created file");
        projectDetailsFilesListingPO.clickOnKebabMenuAndDeleteFileFolder(updateData.getFileName());
        projectDetailsFileListingDeleteFileFolderPO.clickOnFileDeleteButton();
        Assert.assertFalse(projectDetailsFilesListingPO.isFileFolderPresent(updateData.getFileName()), "deleted file is present : ");

        Reporter.log("Step 11 - Click on create folder button and add new folder");
        selenium.refreshPage();
        projectDetailsFilesListingPO.clickOnCreateNewFolderButton();
        projectDetailsFileListingCreateFolderPO.clearAndFillFolderDetail(updateData);
        Assert.assertTrue(projectDetailsFilesListingPO.isFileFolderPresent(updateData.getFolderName()), "folder is not present : ");

        Reporter.log("Step 12 - Click on folder kebab menu and rename folder");
        projectDetailsFilesListingPO.clickOnKebabMenuAndRenameFileFolder(updateData.getFolderName());
        projectDetailsFileListingRenameFileFolderPO.clearAndRenameFileFolderName(updateData);
        Assert.assertTrue(projectDetailsFilesListingPO.isFileFolderPresent(updateData.getFileName()), "renamed file folder is not present : ");

        Reporter.log("Step 13 - Click on folder kebab menu and delete folder");
        projectDetailsFilesListingPO.clickOnKebabMenuAndDeleteFileFolder(updateData.getFileName());
        projectDetailsFileListingDeleteFileFolderPO.clickOnFileDeleteButton();
        Assert.assertFalse(projectDetailsFilesListingPO.isFileFolderPresent(updateData.getFileName()), "deleted file folder is present : ");

    }
}

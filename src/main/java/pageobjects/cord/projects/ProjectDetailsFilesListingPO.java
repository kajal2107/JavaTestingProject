package pageobjects.cord.projects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

import java.io.File;

public class ProjectDetailsFilesListingPO extends BasePO {

    public ProjectDetailsFilesListingPO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    @FindBy(name = "files_list_uploader")
    private WebElement fileListUploadButton;

    @FindBy(name = "search")
    private WebElement searchTextBox;

    @FindBy(xpath = "//button[contains(@title,'Create Folder')]//span[1]")
    private WebElement createFolderButton;

    @FindBy(css = "#draggable-dialog-title~div div:last-child p")
    private WebElement lastUploadedFileText;

    @FindBy(xpath = "//div[@id='draggable-dialog-title']/following::p")
    private WebElement firstUploadedFileText;

    @FindBy(xpath = "//button[@id='upload-manager-close-button']")
    private WebElement uploadedFileCloseButton;

    @FindBy(xpath = "//span[text()='history']")
    private WebElement fileHistoryButton;

    @FindBy(xpath = "//span[text()='rename']")
    private WebElement fileRenameButton;

    @FindBy(xpath = "//span[text()='delete']")
    private WebElement fileDeleteButton;

    @FindBy(name = "file-version-uploader")
    private WebElement fileNewVersionButton;

    @FindBy(xpath = "//span[contains(text(),'history')]//parent::div//parent::li//parent::ul")
    private WebElement filesKebabMenu;

    /**
     * Is folder present ?
     *
     * @param folderName folder name
     * @return boolean
     */
    public boolean isFileFolderPresent(String folderName) {
        String xpathLocatorFileFolder = "//span[text()='" + folderName + "']";
        return selenium.isElementPresent(By.xpath(xpathLocatorFileFolder));
    }

    /**
     * click On upload file button
     */
    public void uploadNewFile(String fileName) {
        String textFile = "src/main/resources/testData/" + fileName;
        fileListUploadButton.sendKeys(new File(textFile).getAbsolutePath());
    }

    /**
     * click On create new folder button
     *
     * @throws InterruptedException exception
     */
    public void clickOnCreateNewFolderButton() throws InterruptedException {

        selenium.focusOnElement(createFolderButton);
        selenium.clickOn(createFolderButton);
    }

    /**
     * click On kebab menu and rename file folder
     *
     * @param fileFolderName file folder name
     * @throws InterruptedException exception
     */
    public void clickOnKebabMenuAndRenameFileFolder(String fileFolderName) throws InterruptedException {

        String xpathFileFolderName = "//span[text()='" + fileFolderName + "']/following::button[1]";
        selenium.clickOn(By.xpath(xpathFileFolderName));
        selenium.clickOn(fileRenameButton);
    }

    /**
     * click On kebab menu and delete file folder
     *
     * @throws InterruptedException exception
     */
    public void clickOnKebabMenuAndDeleteFileFolder(String fileFolderName) throws InterruptedException {

        String xpathFileFolderName = "//span[text()='" + fileFolderName + "']/following::button[1]";
        selenium.clickOn(By.xpath(xpathFileFolderName));
        selenium.clickOn(fileDeleteButton);
    }

    /**
     * click On kebab menu and add new file version
     *
     * @throws InterruptedException exception
     */
    public void clickOnKebabMenuAndAddNewFileVersion(String oldFileName, String fileName) throws InterruptedException {

        String xpathOldFileName = "//span[text()='" + oldFileName + "']/following::button[1]";
        selenium.clickOn(By.xpath(xpathOldFileName));
        String textFile = "src/main/resources/testData/" + fileName;
        fileNewVersionButton.sendKeys(new File(textFile).getAbsolutePath());
        selenium.hardWait(5);
    }

    /**
     * click On kebab menu and view history of  file
     *
     * @param oldFileName old file name
     * @throws InterruptedException exception
     */
    public void clickOnKebabMenuAndViewFileHistory(String oldFileName) throws InterruptedException {

        String xpathOldFileName = "//span[text()='" + oldFileName + "']/following::button[1]";
        selenium.clickOn(By.xpath(xpathOldFileName));
        selenium.clickOn(selenium.waitTillElementIsClickable(fileHistoryButton));
    }

    /**
     * click On created folder and add new file
     *
     * @param fileFolderName file folder name
     * @throws InterruptedException exception
     */
    public void clickOnCreatedFolderByName(String fileFolderName) throws InterruptedException {

        String xpathFileFolderName = "//span[text()='" + fileFolderName + "']";
        selenium.clickOn(By.xpath(xpathFileFolderName));
    }

    /**
     * close kebab menu
     */
    public void closeTheKebabMenu() {
        selenium.focusOnElement(filesKebabMenu);
        filesKebabMenu.sendKeys(Keys.ESCAPE);
    }

    /**
     * Get uploaded file text
     *
     * @return uploaded file text
     */
    public String getLastFileUploadText() {
        return selenium.getText(lastUploadedFileText);
    }

    /**
     * Get uploaded file text
     *
     * @return uploaded file text
     */
    public String getFirstFileUploadText() {
        return selenium.getText(firstUploadedFileText);
    }
}


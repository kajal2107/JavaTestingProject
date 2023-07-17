package pageobjects.cord.projects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class ProjectDetailsFilesHistoryListingPO extends BasePO {

    public ProjectDetailsFilesHistoryListingPO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    @FindBy(xpath = "//span[text()='rename']")
    private WebElement fileRenameButton;

    @FindBy(xpath = "//span[text()='delete']")
    private WebElement fileDeleteButton;

    @FindBy(xpath = "//span[contains(text(),'history')]//parent::div//parent::li//parent::ul")
    private WebElement filesKebabMenu;

    @FindBy(css = "#dialog-file-versions~ul li:last-child .MuiListItemText-dense span")
    private WebElement lastUploadedFileHistoryText;

    private By closeButton = By.xpath("//span[text()='Close']");

    /**
     * Is file  present ?
     *
     * @param fileName file name
     * @return boolean
     */
    public boolean isFilePresent(String fileName) {
        String xpathLocatorFileFolder = "//span[text()='" + fileName + "']";
        return selenium.isElementPresent(By.xpath(xpathLocatorFileFolder));
    }

    /**
     * click On history kebab menu and rename file
     * @param oldFileName old file name
     * @throws InterruptedException exception
     */
    public void clickOnHistoryKebabMenuAndRenameFileFolder(String oldFileName) throws InterruptedException {

        String xpathOldFileName = "//span[text()='" + oldFileName + "']/following::button[1]";
        selenium.clickOn(By.xpath(xpathOldFileName));
        selenium.hardWait(3);
        selenium.clickOn(fileRenameButton);
    }

    /**
     * click On kebab menu and delete file folder
     * @param fileFolderName file folder name
     * @throws InterruptedException exception
     */
    public void clickOnKebabMenuAndDeleteFileFolder(String fileFolderName) throws InterruptedException {

        String xpathFileFolderName = "//span[text()='" + fileFolderName + "']/following::button[1]";
        selenium.clickOn(By.xpath(xpathFileFolderName));
        selenium.clickOn(fileDeleteButton);
    }

    /**
     * click On close button
     *
     * @throws InterruptedException exception
     */
    public void clickOnCloseButton() throws InterruptedException {
        selenium.clickOn(closeButton);
        selenium.waitTillElementsCountIsLessThan(closeButton, 1);
    }
}


package pageobjects.cord.projects;

import dataobjects.Project;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class ProjectDetailsFileListingCreateFolderPO extends BasePO {

    public ProjectDetailsFileListingCreateFolderPO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    @FindBy(xpath = "//input[contains(@placeholder,'Enter folder name')]")
    private WebElement folderNameTextBox;

    private By submitButton = By.xpath("//span[text()='Submit']");

    private By cancelButton = By.xpath("//span[text()='Cancel']");

    /**
     * clear and fill folder detail and click on submit button
     *
     * @param updateData Project object
     * @throws InterruptedException exception
     */

    public void clearAndFillFolderDetail(Project updateData) throws InterruptedException {
        selenium.clearTextBoxUsingKeys(folderNameTextBox);
        selenium.enterText(folderNameTextBox, updateData.getFolderName(), false);
        selenium.clickOn(submitButton);
        selenium.waitTillElementsCountIsLessThan(submitButton, 1);
    }
}


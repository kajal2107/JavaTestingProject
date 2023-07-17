package pageobjects.cord.projects;

import dataobjects.Project;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class ProjectDetailsFileListingRenameFileFolderPO extends BasePO {

    public ProjectDetailsFileListingRenameFileFolderPO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    @FindBy(name = "name")
    private WebElement renameFileFolderTextBox;

    private By submitButton = By.xpath("//span[text()='Submit']");

    private By cancelButton = By.xpath("//span[text()='Cancel']");

    /**
     * clear and fill file folder name and click on submit button
     *
     * @param updateData Project object
     * @throws InterruptedException exception
     */

    public void clearAndRenameFileFolderName(Project updateData) throws InterruptedException {
        selenium.clearTextBoxUsingKeys(renameFileFolderTextBox);
        selenium.enterText(renameFileFolderTextBox, updateData.getFileName(), false);
        selenium.clickOn(submitButton);
        selenium.waitTillElementsCountIsLessThan(submitButton, 1);
    }
}


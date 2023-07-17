package pageobjects.cord.projects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.base.BasePO;

public class ProjectDetailsFileListingDeleteFileFolderPO extends BasePO {

    public ProjectDetailsFileListingDeleteFileFolderPO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    private By submitButton = By.xpath("//span[text()='Submit']");

    private By cancelButton = By.xpath("//span[text()='Cancel']");

    /**
     * click on file delete button
     *
     * @throws InterruptedException exception
     */

    public void clickOnFileDeleteButton() throws InterruptedException {
        selenium.clickOn(submitButton);
        selenium.waitTillElementsCountIsLessThan(submitButton, 1);
    }
}


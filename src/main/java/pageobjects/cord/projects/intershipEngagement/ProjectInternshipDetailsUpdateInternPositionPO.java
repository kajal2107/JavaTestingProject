package pageobjects.cord.projects.intershipEngagement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class ProjectInternshipDetailsUpdateInternPositionPO extends BasePO {

    public ProjectInternshipDetailsUpdateInternPositionPO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    @FindBy(xpath = "//input[contains(@class,'MuiFilledInput')]")
    private WebElement internPositionTextBox;

    @FindBy(xpath = "//ul[contains(@class,'groupUl')]")
    private WebElement addInternPositionButton;

    private final By saveButton = By.xpath("//span[text()='Save']");

    private final By closeButton = By.xpath("//span[text()='Close']");

    /**
     * Click on intern position by name
     *
     * @param internPosition internPosition
     * @throws InterruptedException exception
     */
    public void clickOnInternPositionByNameAndClickOnSaveButton(String internPosition) throws InterruptedException {
        selenium.clearTextBoxUsingKeys(internPositionTextBox);
        selenium.enterText(internPositionTextBox, internPosition, false);
        selenium.hardWait(3);
        selenium.clickOn(addInternPositionButton);
        selenium.clickOn(saveButton);
    }

    /**
     * Click on internship intern position close button
     *
     * @throws InterruptedException exception
     */
    public void clickOnInternshipInternPositionCloseButton() throws InterruptedException {
        selenium.clickOn(closeButton);
        selenium.waitTillElementsCountIsLessThan(closeButton, 1);
    }


    /**
     * Is intern position textBox enabled ?
     *
     * @return boolean
     */
    public boolean isInternPositionTextBoxEnabled() {
        return selenium.isElementEnabled(internPositionTextBox);
    }



}


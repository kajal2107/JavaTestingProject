package pageobjects.cord.projects.intershipEngagement;

import dataobjects.Internship;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class ProjectEngagementDetailsUpdateDisbursementCompleteDatePO extends BasePO {

    public ProjectEngagementDetailsUpdateDisbursementCompleteDatePO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    @FindBy(name = "engagement.disbursementCompleteDate")
    private WebElement engagementDisbursementCompleteDateTextBox;


    private final By saveButton = By.xpath("//span[text()='Save']");

    private final By closeButton = By.xpath("//span[text()='Close']");

    /**
     * Fill disbursement complete date detail and click on submit button
     *
     * @param data internship object
     */
    public void fillEngagementDisbursementCompleteDateDetailsAndClickOnSaveButton(Internship data) throws InterruptedException {
        selenium.clearTextBoxUsingKeys(engagementDisbursementCompleteDateTextBox);
        selenium.enterText(engagementDisbursementCompleteDateTextBox, data.getDisbursementCompleteDate(), false);
        selenium.clickOn(saveButton);
    }

    /**
     * Click on internship disbursement complete date close button
     *
     * @throws InterruptedException exception
     */
    public void clickOnInternshipDisbursementCompleteDateCloseButton() throws InterruptedException {
        selenium.clickOn(closeButton);
        selenium.waitTillElementsCountIsLessThan(closeButton, 1);
    }

    /**
     * Is internship disbursement complete date textBox enabled ?
     *
     * @return boolean
     */
    public boolean isInternshipDisbursementCompleteDateTextBoxEnabled() {
        return selenium.isElementEnabled(engagementDisbursementCompleteDateTextBox);
    }
}


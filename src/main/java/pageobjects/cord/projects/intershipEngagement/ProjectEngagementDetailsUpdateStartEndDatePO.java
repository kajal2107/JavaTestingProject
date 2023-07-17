package pageobjects.cord.projects.intershipEngagement;

import dataobjects.Internship;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class ProjectEngagementDetailsUpdateStartEndDatePO extends BasePO {

    public ProjectEngagementDetailsUpdateStartEndDatePO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    @FindBy(name = "engagement.startDateOverride")
    private WebElement startDateTextBox;

    @FindBy(name = "engagement.endDateOverride")
    private WebElement endDateTextBox;

    private By saveButton = By.xpath("//span[text()='Save']");

    private By closeButton = By.xpath("//span[text()='Close']");

    /**
     * Fill start end date detail and click on submit button
     *
     * @param data internship object
     * @throws InterruptedException exception
     */
    public void fillEngagementStartEndDateDetailsAndClickOnSaveButton(Internship data) throws InterruptedException {
        selenium.clearTextBoxUsingKeys(startDateTextBox);
        selenium.enterText(startDateTextBox, data.getInternshipStartDate(), false);
        selenium.clearTextBoxUsingKeys(endDateTextBox);
        selenium.enterText(endDateTextBox, data.getInternshipEndDate(), false);
        selenium.clickOn(saveButton);
        selenium.waitTillElementsCountIsLessThan(saveButton, 1);
    }

    /**
     * Click on internship start end date close button
     *
     * @throws InterruptedException exception
     */
    public void clickOnEngagementStartEndDateCloseButton() throws InterruptedException {
        selenium.clickOn(closeButton);
        selenium.waitTillElementsCountIsLessThan(closeButton, 1);
    }

    /**
     * Is internship start date enabled ?
     *
     * @return boolean
     */
    public boolean isEngagementStartDateEnabled() {
        return selenium.isElementEnabled(startDateTextBox);
    }

    /**
     * Is internship end date enabled ?
     *
     * @return boolean
     */
    public boolean isEngagementEndDateEnabled() {
        return selenium.isElementEnabled(endDateTextBox);
    }

}


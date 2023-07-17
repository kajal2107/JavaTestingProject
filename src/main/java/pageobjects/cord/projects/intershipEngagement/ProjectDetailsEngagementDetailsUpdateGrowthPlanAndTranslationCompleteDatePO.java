package pageobjects.cord.projects.intershipEngagement;

import dataobjects.Internship;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;


/**
 * Common PO for both Translation and Internship project's Engagement Detail page's GrowthPlan and Translation Complete Date.
 */

public class ProjectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO extends BasePO {

    public ProjectDetailsEngagementDetailsUpdateGrowthPlanAndTranslationCompleteDatePO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    @FindBy(name = "engagement.completeDate")
    private WebElement engagementCompleteDateTextBox;

    @FindBy(name = "engagement.disbursementCompleteDate")
    private WebElement engagementDisbursementCompleteDateTextBox;


    private final By saveButton = By.xpath("//span[text()='Save']");

    private final By closeButton = By.xpath("//span[text()='Close']");

    /**
     * Fill growth plan complete date detail and click on submit button
     *
     * @param data internship object
     */
    public void fillInternshipGrowthPlanCompleteDateDetailsAndClickOnSaveButton(Internship data) throws InterruptedException {
        selenium.clearTextBoxUsingKeys(engagementCompleteDateTextBox);
        selenium.enterText(engagementCompleteDateTextBox, data.getGrowthPlanCompleteDate(), false);
        selenium.clickOn(saveButton);
    }

    /**
     * Click on growth complete date close button
     *
     * @throws InterruptedException exception
     */
    public void clickOnInternshipGrowthCompleteDateCloseButton() throws InterruptedException {
        selenium.clickOn(closeButton);
        selenium.waitTillElementsCountIsLessThan(closeButton, 1);
    }

    /**
     * Is translation complete date textBox enabled ?
     *
     * @return boolean
     */
    public boolean isTranslationCompleteDateTextBoxEnabled() {
        return selenium.isElementEnabled(engagementCompleteDateTextBox);
    }

    /**
     * Is disbursement Complete date textBox enabled ?
     *
     * @return boolean
     */
    public boolean isDisbursementCompleteDateTextBoxEnabled() {
        return selenium.isElementEnabled(engagementDisbursementCompleteDateTextBox);
    }

}


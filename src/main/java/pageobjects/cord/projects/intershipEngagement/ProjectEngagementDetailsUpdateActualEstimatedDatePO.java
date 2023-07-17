package pageobjects.cord.projects.intershipEngagement;

import dataobjects.Internship;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class ProjectEngagementDetailsUpdateActualEstimatedDatePO extends BasePO {

    public ProjectEngagementDetailsUpdateActualEstimatedDatePO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    @FindBy(name = "ceremony.estimatedDate")
    private WebElement estimatedDateTextBox;

    @FindBy(name = "ceremony.actualDate")
    private WebElement actualDateTextBox;


    private final By saveButton = By.xpath("//span[text()='Save']");

    /**
     * Fill estimated date and actual date detail and click on submit button
     *
     * @param data internship object
     * @throws InterruptedException exception
     */
    public void fillEngagementEstimatedActualDateDetailsAndClickOnSubmitButton(Internship data) throws InterruptedException {
        selenium.clearTextBoxUsingKeys(estimatedDateTextBox);
        selenium.enterText(estimatedDateTextBox, data.getInternshipEstimatedDate(), false);
        selenium.clearTextBoxUsingKeys(actualDateTextBox);
        selenium.enterText(actualDateTextBox, data.getInternshipActualDate(), false);
        selenium.clickOn(saveButton);
    }

    /**
     * Is internship engagement estimated date textBox enabled ?
     *
     * @return boolean
     */
    public boolean isEngagementEstimatedTextBoxEnabled() {
        return selenium.isElementEnabled(estimatedDateTextBox);
    }

}


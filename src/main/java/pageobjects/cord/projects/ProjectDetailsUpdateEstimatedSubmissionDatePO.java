package pageobjects.cord.projects;

import dataobjects.Project;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class ProjectDetailsUpdateEstimatedSubmissionDatePO extends BasePO {

    public ProjectDetailsUpdateEstimatedSubmissionDatePO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    @FindBy(name = "project.estimatedSubmission")
    private WebElement estimatedSubmissionDateTextBox;

    @FindBy(xpath = "//div[@id='dialog-form']/following::p[1]")
    private WebElement estimatedSubmissionDateErrorText;


    private final By saveButton = By.xpath("//span[text()='Save']");

    private final By closeButton = By.xpath("//span[text()='Close']");

    /**
     * Fill estimated submission date detail and click on submit button
     *
     * @param updateData project object
     */
    public void fillEstimatedSubmissionDateDetailsAndClickOnSaveButton(Project updateData) {


        try {
            selenium.clearTextBoxUsingKeys(estimatedSubmissionDateTextBox);
            selenium.enterText(estimatedSubmissionDateTextBox, updateData.getEstimatedSubmissionDate(), false);
            selenium.clickOn(saveButton);
            selenium.waitTillElementsCountIsLessThan(saveButton, 1);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    /**
     * click On estimated submission date close button
     *
     * @throws InterruptedException exception
     */
    public void clickOnEstimatedSubmissionDateCloseButton() throws InterruptedException {
        selenium.clickOn(closeButton);
        selenium.waitTillElementsCountIsLessThan(closeButton, 1);
    }

    /**
     * get estimated submission date error text
     *
     * @return estimated submission date error text
     */
    public String getEstimatedSubmissionDateErrorText() {
        return selenium.getText(estimatedSubmissionDateErrorText);
    }

    /**
     * Is estimated submission date textBox enabled ?
     *
     * @return boolean
     */
    public boolean isEstimatedSubmissionDateTextBoxEnabled() {
        return selenium.isElementEnabled(estimatedSubmissionDateTextBox);
    }

}


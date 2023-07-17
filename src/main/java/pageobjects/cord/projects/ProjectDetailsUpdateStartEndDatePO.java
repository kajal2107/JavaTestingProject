package pageobjects.cord.projects;

import dataobjects.Project;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class ProjectDetailsUpdateStartEndDatePO extends BasePO {

    public ProjectDetailsUpdateStartEndDatePO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    @FindBy(name = "project.mouStart")
    private WebElement startDateTextBox;

    @FindBy(name = "project.mouEnd")
    private WebElement endDateTextBox;

    @FindBy(xpath = "//div[@id='dialog-form']/following::p[1]")
    private WebElement startEndDateErrorText;


    private final By saveButton = By.xpath("//span[text()='Save']");

    private final By closeButton = By.xpath("//span[text()='Close']");

    /**
     * Fill start end date detail and click on submit button
     *
     * @param updateData project object
     */
    public void fillStartEndDateDetailsAndClickOnSaveButton(Project updateData) {

        try {
            selenium.clearTextBoxUsingKeys(startDateTextBox);
            selenium.enterText(startDateTextBox, updateData.getStartDate(), false);
            selenium.clearTextBoxUsingKeys(endDateTextBox);
            selenium.enterText(endDateTextBox, updateData.getEndDate(), false);
            selenium.clickOn(saveButton);
            selenium.waitTillElementsCountIsLessThan(saveButton, 1);
        }
        catch (Exception e) {
            System.out.println(e);
        }

    }

    /**
     * click On start end date close button
     *
     * @throws InterruptedException exception
     */
    public void clickOnStartEndDateCloseButton() throws InterruptedException {
        selenium.clickOn(closeButton);
        selenium.waitTillElementsCountIsLessThan(closeButton, 1);
    }

    /**
     * get start end date error text
     *
     * @return start end date error text
     */
    public String getStartEndDateErrorText() {
        return selenium.getText(startEndDateErrorText);
    }

    /**
     * Is start date textBox enabled ?
     *
     * @return boolean
     */
    public boolean isStartDateTextBoxEnabled() {
        return selenium.isElementEnabled(startDateTextBox);
    }

    /**
     * Is end date textBox enabled ?
     *
     * @return boolean
     */
    public boolean isEndDateTextBoxEnabled() {
        return selenium.isElementEnabled(endDateTextBox);
    }


}


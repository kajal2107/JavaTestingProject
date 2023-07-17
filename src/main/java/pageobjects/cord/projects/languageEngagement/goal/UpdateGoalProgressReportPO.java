package pageobjects.cord.projects.languageEngagement.goal;

import dataobjects.Goal;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class UpdateGoalProgressReportPO extends BasePO {

    public UpdateGoalProgressReportPO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    @FindBy(name = "completed")
    private WebElement progressTextBox;

    @FindBy(xpath = "//div[@id='dialog-form']/following::p")
    private WebElement goalProgressTargetText;

    @FindBy(xpath = "//input[@type='checkbox']")
    private WebElement goalDoneNotDoneCheckBox;

    private final By submitButton = By.xpath("//span[text()='Submit']");

    /**
     * Fill progress report detail
     *
     * @param data goal object
     */
    public void fillProgressReportDetailsAndClickOnTheSubmitButton(Goal data) {
        try {
            selenium.clearTextBoxUsingKeys(progressTextBox);
            selenium.enterText(progressTextBox, data.getGoalStepsProgressValue().toString(), false);
            selenium.clickOn(submitButton);
            selenium.waitTillElementsCountIsLessThan(submitButton, 1);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * click on progress status
     */
    public void clickOnTheProgressStatus() throws InterruptedException {

        selenium.click(goalDoneNotDoneCheckBox);
        selenium.clickOn(submitButton);
        selenium.waitTillElementsCountIsLessThan(submitButton, 1);
    }

    /**
     * Get goal progress target text
     *
     * @return goal progress target text
     */

    public String getGoalProgressTargetText() {
        return selenium.getText(goalProgressTargetText);
    }

    /**
     * Is goal progress measurement text present ?
     *
     * @return boolean
     */
    public boolean isGoalProgressMeasurementStatusPresent() {
        String xpathLocator = "//h4[text()='Completed']/following::p";
        return selenium.isElementPresent(By.xpath(xpathLocator));
    }

}


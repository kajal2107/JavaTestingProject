package pageobjects.cord.projects.languageEngagement.goal;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class GoalDetailsPO extends BasePO {

    public GoalDetailsPO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    @FindBy(xpath = "//h2[contains(@class,'MuiTypography-h2')]")
    private WebElement goalName;

    @FindBy(xpath = "//p[text()='Distribution Methods']/following::div[contains(@class,'MuiListItemText')][1]/span")
    private WebElement goalDistributionMethodsText;

    @FindBy(xpath = "//p[text()='Distribution Methods']/following::div[contains(@class,'MuiListItemText')][1]/span/following-sibling::p/a")
    private WebElement goalDistributionMethodsPartnerText;

    @FindBy(xpath = "//p[text()='Methodology']/following-sibling::span")
    private WebElement goalMethodologyText;

    @FindBy(xpath = "//p[text()='Completion Description']/following-sibling::span")
    private WebElement goalCompletionDescriptionText;

    @FindBy(xpath = "//p[text()='Description']/following-sibling::span")
    private WebElement goalDescriptionText;

    @FindBy(xpath = "//p[text()='Scripture']/following-sibling::span//li[1]")
    private WebElement ScriptureReferenceOldTestamentText;

    @FindBy(xpath = "//p[text()='Scripture']/following-sibling::span/ul")
    private WebElement ScriptureReferenceText;

    /**
     * Get goal name text
     *
     * @return goal name text
     */

    public String getGoalNameText() {
        return selenium.getText(goalName);
    }

    /**
     * Get goal distribution methods text
     *
     * @return goal distribution methods text
     */

    public String getGoalDistributionMethodsText() {
        return selenium.getText(goalDistributionMethodsText);
    }

    /**
     * Get goal distribution methods partner text
     *
     * @return goal distribution methods partner text
     */

    public String getGoalDistributionMethodsPartnerText() {
        return selenium.getText(goalDistributionMethodsPartnerText);
    }

    /**
     * Get goal completion description text
     *
     * @return goal completion description text
     */

    public String getGoalCompletionDescriptionText() {
        return selenium.getText(goalCompletionDescriptionText);
    }

    /**
     * Get goal description text
     *
     * @return goal description text
     */

    public String getGoalDescriptionText() {
        return selenium.getText(goalDescriptionText);
    }

    /**
     * Get goal methodology text
     *
     * @return goal methodology text
     */

    public String getGoalMethodologyText() {
        return selenium.getText(goalMethodologyText);
    }

    /**
     * Get scriptureReference old testament text
     *
     * @return scriptureReference old testament text
     */

    public String getGoalScriptureReferenceOldTestamentText() {
        return selenium.getText(ScriptureReferenceOldTestamentText);
    }

    /**
     * Get scriptureReference new testament text
     *
     * @return scriptureReference new testament text
     */

    public String getGoalScriptureReferenceNewTestamentText() {
        return selenium.getText(ScriptureReferenceText);
    }

    /**
     * Click on progress report steps button
     *
     * @param stepName step name
     * @throws InterruptedException exception
     */
    public void clickOnProgressReportStepsButtonByStepName(String stepName) throws InterruptedException {
        String stepNameXpath = "//h4[text()='" + stepName + "']";
        selenium.clickOn(By.xpath(stepNameXpath));
    }

    /**
     * Get percentage of progress report by steps name
     *
     * @param stepName step name
     */
    public String getPercentageOfProgressReportSteps(String stepName) {
        String progressPercentageXpath = "//h4[text()='" + stepName + "']/following-sibling::p";
        return selenium.getText(By.xpath(progressPercentageXpath));

    }

}



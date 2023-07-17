package pageobjects.cord.projects.intershipEngagement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class ProjectInternshipLanguageDetailsUpdateStatusPO extends BasePO {

    public ProjectInternshipLanguageDetailsUpdateStatusPO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    @FindBy(xpath = "//label[text()='Override Status']/following::input")
    private WebElement overrideStatusTextBox;

    @FindBy(xpath = "//ul[contains(@class,'MuiAutocomplete-listbox')]/li[1]")
    private WebElement addInternshipEngagementStatusName;

    @FindBy(xpath = "//div[contains(@class,'MuiGrid-direction')]/p[contains(@class,'MuiTypography-paragraph')]")
    private WebElement internshipEngagementStatusErrorText;


    private By submitButton = By.xpath("//span[text()='Submit']");
    private By closeButton = By.xpath("//span[text()='Close']");


    /**
     * Click on Internship Status by name
     *
     * @param internshipStatus internshipStatus
     * @throws InterruptedException exception
     */
    public void clickOnEngagementStatusByName(String internshipStatus) throws InterruptedException {
        String internshipStatusXpathSelector = "//span[contains(text(),'" + internshipStatus + "')]";
        selenium.clickOn(By.xpath(internshipStatusXpathSelector));
    }

    /**
     * Click on Internship Override Status by name
     *
     * @throws InterruptedException exception
     */
    public void clickInternshipEngagementOverrideStatusByName(String internshipStatus) throws InterruptedException {
        selenium.clickOn(overrideStatusTextBox);
        selenium.enterText(overrideStatusTextBox, internshipStatus, false);
        selenium.clickOn(addInternshipEngagementStatusName);
        selenium.clickOn(submitButton);
        selenium.waitTillElementsCountIsLessThan(submitButton, 1);

    }

    /**
     * Click on internship status close button
     *
     * @throws InterruptedException exception
     */
    public void clickOnInternshipStatusCloseButton() throws InterruptedException {
        selenium.clickOn(closeButton);
        selenium.waitTillElementsCountIsLessThan(closeButton, 1);
    }

    /**
     * Get internship status error text
     *
     * @return internship status error text
     */
    public String getInternshipStatusErrorText() {
        return selenium.getText(internshipEngagementStatusErrorText);
    }


}


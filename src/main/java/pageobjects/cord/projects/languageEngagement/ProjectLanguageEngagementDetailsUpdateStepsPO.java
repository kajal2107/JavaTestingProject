package pageobjects.cord.projects.languageEngagement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class ProjectLanguageEngagementDetailsUpdateStepsPO extends BasePO {

    public ProjectLanguageEngagementDetailsUpdateStepsPO(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//label[text()='Override Status']/following::input")
    private WebElement overrideStatusTextBox;

    @FindBy(xpath = "//ul[contains(@class,'MuiAutocomplete-listbox')]/li[1]")
    private WebElement addLanguageEngagementStatusName;

    private By submitButton = By.xpath("//span[text()='Submit']");


    /**
     * Click language engagement status by name
     *
     * @throws InterruptedException exception
     */
    public void clickLanguageEngagementStatusByName(String internshipStatus) throws InterruptedException {
        selenium.clickOn(overrideStatusTextBox);
        selenium.enterText(overrideStatusTextBox, internshipStatus, false);
        selenium.clickOn(addLanguageEngagementStatusName);
        selenium.clickOn(submitButton);
        selenium.waitTillElementsCountIsLessThan(submitButton, 1);

    }

}


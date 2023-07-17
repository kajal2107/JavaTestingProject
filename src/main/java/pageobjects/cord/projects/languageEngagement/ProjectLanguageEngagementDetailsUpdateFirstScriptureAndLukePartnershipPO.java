package pageobjects.cord.projects.languageEngagement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class ProjectLanguageEngagementDetailsUpdateFirstScriptureAndLukePartnershipPO extends BasePO {

    public ProjectLanguageEngagementDetailsUpdateFirstScriptureAndLukePartnershipPO(WebDriver driver) {
        super(driver);
    }

    @FindBy(name = "engagement.firstScripture")
    private WebElement firstScriptureCheckBox;

    @FindBy(name = "engagement.lukePartnership")
    private WebElement lukePartnershipCheckBox;

    @FindBy(xpath = "//p[contains(@class,'Mui-error')]")
    private WebElement firstScriptureErrorMessage;

    private final By saveButton = By.xpath("//span[text()='Save']");

    /**
     * update first scripture and luke partnership
     *
     * @throws InterruptedException exception
     */
    public void updateFirstScriptureLukePartnershipAndClickOnSaveButton() throws InterruptedException {
        selenium.click(firstScriptureCheckBox);
        selenium.click(lukePartnershipCheckBox);
        selenium.clickOn(saveButton);
        if (selenium.isElementPresent(firstScriptureErrorMessage)) {
            selenium.click(firstScriptureCheckBox);
            selenium.clickOn(saveButton);
        }
    }
}


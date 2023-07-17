package pageobjects.cord.projects.languageEngagement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.base.BasePO;

public class ProjectLanguageEngagementDetailsUpdateExtractGoalsPO extends BasePO {

    public ProjectLanguageEngagementDetailsUpdateExtractGoalsPO(WebDriver driver) {
        super(driver);
    }

    private final By uploadButton = By.xpath("//span[text()='Upload']");

    /**
     * Click on extract goal methodology radio button
     *
     * @throws InterruptedException exception
     */
    public void clickOnExtractGoalMethodologyRadioButton(String goalMethodology) throws InterruptedException {

        String goalMethodologyRadioButtonXpathSelector = "//span[text()='" + goalMethodology + "']";
        selenium.clickOn(By.xpath(goalMethodologyRadioButtonXpathSelector));
        selenium.clickOn(uploadButton);
        selenium.waitTillElementsCountIsLessThan(uploadButton, 1);
    }
}


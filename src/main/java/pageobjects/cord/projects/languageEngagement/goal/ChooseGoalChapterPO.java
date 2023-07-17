package pageobjects.cord.projects.languageEngagement.goal;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class ChooseGoalChapterPO extends BasePO {

    public ChooseGoalChapterPO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    @FindBy(name = "fullBook")
    private WebElement goalChapterToggleButton;

    @FindBy(name = "updatingScriptures")
    private WebElement goalChapterTextBox;

    private By submitButton = By.xpath("//span[text()='Submit']");

    private By cancelButton = By.xpath("//span[text()='Cancel']");

    /**
     * Click on goal chapter toggle button
     *
     * @throws InterruptedException exception
     */
    public void clickOnGoalChapterToggleButton() throws InterruptedException {
        selenium.click(goalChapterToggleButton);
        selenium.clickOn(submitButton);
        selenium.waitTillElementsCountIsLessThan(submitButton, 1);

    }

}


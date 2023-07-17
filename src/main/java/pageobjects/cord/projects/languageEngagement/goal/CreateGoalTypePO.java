package pageobjects.cord.projects.languageEngagement.goal;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class CreateGoalTypePO extends BasePO {

    public CreateGoalTypePO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    @FindBy(xpath = "//input[@placeholder ='Enter film name']")
    private WebElement createFilmTextBox;

    @FindBy(xpath = "//input[@placeholder ='Enter story name']")
    private WebElement createStoryTextBox;

    @FindBy(xpath = "//input[@placeholder ='Enter song name']")
    private WebElement createSongTextBox;

    @FindBy(xpath = "//input[@placeholder ='Enter literacy material name']")
    private WebElement createLiteracyMaterialTextBox;


    private By submitButton = By.xpath("//span[text()='Submit']");

    private By cancelButton = By.xpath("//span[text()='Cancel']");

    /**
     * Click on goal type submit button
     *
     * @throws InterruptedException exception
     */
    public void clickOnGoalTypeSubmitButton() throws InterruptedException {
        selenium.clickOn(submitButton);
        selenium.waitTillElementsCountIsLessThan(submitButton, 1);

    }

}


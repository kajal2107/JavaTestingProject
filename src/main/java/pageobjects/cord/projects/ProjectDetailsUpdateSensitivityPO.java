package pageobjects.cord.projects;

import enums.project.Sensitivity;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class ProjectDetailsUpdateSensitivityPO extends BasePO {

    public ProjectDetailsUpdateSensitivityPO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    @FindBy(name = "Low")
    private WebElement lowRadioButton;

    @FindBy(name = "Medium")
    private WebElement mediumRadioButton;

    @FindBy(name = "High")
    private WebElement highRadioButton;

    private By submitButton = By.xpath("//span[text()='Save']");

    private By closeButton = By.xpath("//span[text()='Close']");


    public void clickOnProjectSensitivityRadioButtonByName(Sensitivity sensitivity) throws InterruptedException {
        String xpathLocator = "//span[text()= '" + sensitivity + "']";
        selenium.clickOn(By.xpath(xpathLocator));
        selenium.clickOn(submitButton);
        selenium.waitTillElementsCountIsLessThan(submitButton, 1);
    }
}


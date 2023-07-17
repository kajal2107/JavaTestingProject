package pageobjects.cord.account;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class PasswordChangeRequestPO extends BasePO {

    public PasswordChangeRequestPO(WebDriver driver) {
        super(driver);
    }


    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */


    @FindBy(xpath = "//div[contains(text(),'You have submitted a password change request!')]")
    private WebElement passwordChangeRequestText;

    @FindBy(xpath = "//a[contains(@href,'reset-password')]")
    private WebElement confirmPasswordLink;


    /**
     * Click on confirm Password Link
     *
     * @throws InterruptedException exception
     */
    public void clickOnPasswordLink() throws InterruptedException {
        selenium.clickOn(confirmPasswordLink);
    }

    /**
     * Click password Change Request page Text
     *
     * @throws InterruptedException exception
     */
    public String getPasswordChangeRequestText() {
        return passwordChangeRequestText.getText();
    }

}


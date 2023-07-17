package pageobjects.cord.account;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class ResetPasswordPO extends BasePO {

    public ResetPasswordPO(WebDriver driver) {
        super(driver);
    }


    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */


    @FindBy(name= "password")
    private WebElement passwordTextBox;

    @FindBy(name= "confirm")
    private WebElement confirmPasswordTextbox;

    @FindBy(xpath = "//button[contains(@class,'ResetPasswordForm-submit')]")
    private WebElement savePasswordButton;


    /**
     * Fill out detail and Reset the password
     *
     * @param password password
     * @throws InterruptedException exception
     */
    public void fillDetailAndClickOnSavePassword(String password) throws InterruptedException {
        selenium.enterText(passwordTextBox, password, false);
        selenium.enterText(confirmPasswordTextbox, password, false);
        selenium.clickOn(savePasswordButton);
    }

}


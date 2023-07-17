package pageobjects.cord.account;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class LoginPO extends BasePO {

    public LoginPO(WebDriver driver) {
        super(driver);
    }


    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    @FindBy(name = "email")
    private WebElement emailTextBox;

    @FindBy(name = "password")
    private WebElement passwordTextBox;

    @FindBy(xpath = "//Span[contains(text(),'Sign In')]")
    private WebElement signInButton;

    @FindBy(css = "p[class*='MuiTypography-colorError']")
    private WebElement errorMessage;

    @FindBy(css = "a[href='/forgot-password']")
    private WebElement forgotPasswordLink;

    @FindBy(css = "form button")
    private WebElement resetPasswordButton;

    @FindBy(xpath = "//a[contains(@href,'reset-password')]")
    private WebElement confirmPasswordLink;

    /**
     * Fill out login details and Login to application (uses STATIC DATA )
     *
     * @param email    Enter email or phone details
     * @param password Enter password details
     * @throws InterruptedException exception
     */
    public void fillLoginDetailsAndPerformLogin(String email, String password) throws InterruptedException {
        selenium.clearTextBoxUsingKeys(emailTextBox);
        selenium.enterText(emailTextBox, email, true);

        selenium.clearTextBoxUsingKeys(passwordTextBox);
        selenium.enterText(passwordTextBox, password, true);

        selenium.clickOn(signInButton);
    }

    /**
     * Fill out detail and Reset the password
     *
     * @param email email
     * @throws InterruptedException exception
     */
    public void fillResetPasswordDetailAndRedirectToPasswordChangeRequestPage(String email) throws InterruptedException {
        selenium.enterText(emailTextBox, email, false);
        selenium.clickOn(resetPasswordButton);
        String winHandleBefore = driver.getWindowHandle();
        selenium.switchToWindow(winHandleBefore);
        selenium.clickOn(confirmPasswordLink);
    }

    /**
     * Is error message  present ?
     *
     * @return status
     */
    public boolean isErrorMessagePresent() {
        return selenium.isElementPresent(errorMessage);
    }

    /**
     * Get error message
     *
     * @return error message
     */
    public String getErrorMessage() {
        return selenium.getText(errorMessage);
    }

    /**
     * Click on forgot password link
     */
    public void clickOnForgotPasswordLink() throws InterruptedException {
        selenium.clickOn(forgotPasswordLink);
    }

}


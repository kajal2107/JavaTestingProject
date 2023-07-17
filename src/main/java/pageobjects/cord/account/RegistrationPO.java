package pageobjects.cord.account;

import dataobjects.Registration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class RegistrationPO extends BasePO {

    public RegistrationPO(WebDriver driver) {
        super(driver);
    }


    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    @FindBy(name = "realFirstName")
    private WebElement firstNameTextBox;

    @FindBy(name = "realLastName")
    private WebElement lastNameTextBox;

    @FindBy(name = "email")
    private WebElement emailTextBox;

    @FindBy(name = "password")
    private WebElement passwordTextBox;

    @FindBy(name = "confirmPassword")
    private WebElement confirmPasswordTextBox;

    @FindBy(xpath = "//button[contains(@class,'RegisterForm-submit') or @type='submit']")
    private WebElement signUpButton;

    @FindBy(xpath = "//label[text()='First Name']/following-sibling::p")
    private WebElement firstNameValidationMessage;

    @FindBy(xpath = "//label[text()='Last Name']/following-sibling::p")
    private WebElement lastNameValidationMessage;

    @FindBy(xpath = "//label[text()='Email']/following-sibling::p")
    private WebElement emailValidationMessage;

    @FindBy(xpath = "//label[text()='Password']/following-sibling::p")
    private WebElement passwordValidationMessage;

    @FindBy(xpath = "//label[text()='Re-Type Password']/following-sibling::p")
    private WebElement ConfirmPasswordValidationMessage;

    @FindBy(css = "a[href='/login']")
    private WebElement alreadyHaveAccountLink;


    /**
     * Fill out registration details and Register a new user
     *
     * @param data Registration object
     * @throws InterruptedException exception
     */
    public void fillRegistrationDetailsAndPerformSignUp(Registration data) throws InterruptedException {
        selenium.enterText(firstNameTextBox, data.getFirstName(), false);
        selenium.enterText(lastNameTextBox, data.getLastName(), false);
        selenium.enterText(emailTextBox, data.getEmail(), false);
        selenium.enterText(passwordTextBox, data.getPassword(), false);
        selenium.enterText(confirmPasswordTextBox, data.getConfirmPassword(), false);
        selenium.clickOn(signUpButton);
    }

    /**
     * Fill out registration details and Register a new user
     *
     * @param data Registration object
     * @throws InterruptedException exception
     */
    public void fillRegistrationDetailsWithoutPassword(Registration data) {
        selenium.enterText(firstNameTextBox, data.getFirstName(), false);
        selenium.enterText(lastNameTextBox, data.getLastName(), false);
        selenium.enterText(emailTextBox, data.getEmail(), false);

    }

    /**
     * Enter Email
     *
     * @param email email value
     */
    public void enterEmail(String email) {
        selenium.enterText(emailTextBox, email, false);
    }

    /**
     * Enter password-confirm password details
     *
     * @param password        password
     * @param confirmPassword confirm password
     */
    public void enterPasswordDetails(String password, String confirmPassword) {
        selenium.enterText(passwordTextBox, password, false);
        selenium.enterText(confirmPasswordTextBox, confirmPassword, false);
    }

    /**
     * Click on already have account link
     */
    public void clickOnAlreadyHaveLink() throws InterruptedException {
        selenium.clickOn(alreadyHaveAccountLink);
    }

    /**
     * Click on Sign up button
     *
     * @throws InterruptedException exception
     */
    public void clickOnSignUpButton() throws InterruptedException {
        selenium.clickOn(signUpButton);
    }

    /**
     * Get first name field validation message
     *
     * @return validation message
     */
    public String firstNameMandatoryFieldText() {
        return selenium.getText(firstNameValidationMessage);
    }

    /**
     * Get last name field validation message
     *
     * @return validation message
     */
    public String lastNameMandatoryFieldText() {
        return selenium.getText(lastNameValidationMessage);
    }

    /**
     * Get email field validation message
     *
     * @return validation message
     */
    public String emailMandatoryFieldText() {
        return selenium.getText(emailValidationMessage);
    }

    /**
     * Get password field validation message
     *
     * @return validation message
     */
    public String passwordMandatoryFieldText() {
        return selenium.getText(passwordValidationMessage);
    }

    /**
     * Get password field validation message
     *
     * @return validation message
     */
    public String confirmPasswordMandatoryFieldText() {
        return selenium.getText(ConfirmPasswordValidationMessage);
    }


}


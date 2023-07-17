package cord.account;

import base.BaseTest;
import datafactory.RegistrationData;
import dataobjects.Registration;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import pageobjects.cord.account.RegistrationPO;
import pageobjects.cord.common.HeaderPO;
import utilities.Constants;

public class RegistrationTests extends BaseTest {

    /*Test 1 : Verify that user can sign up successfully*/
    @Test
    public void verifySignUpWithValidDetail() throws InterruptedException {

        RegistrationPO register = new RegistrationPO(driver);
        HeaderPO header = new HeaderPO(driver);

        Reporter.log("Step 1 - Navigate to signUp page");
        selenium.navigateToPage(Constants.URL + Constants.registerEndPoint);

        Reporter.log("Step 2 - Fill out Sign up form with valid details and click on 'Sign Up' button");
        Registration data = new RegistrationData().getRegistrationData();
        register.fillRegistrationDetailsAndPerformSignUp(data);

        Reporter.log("Step 3- Verify that user is logged in successfully by comparing first name");
        Assert.assertEquals(header.getProfileName(), Constants.welcomeLabel + data.getFirstName(), "Logged in user name doesn't match");
    }

    /*Test 2 : Verify that validation messages are displayed for mandatory fields*/
    @Test
    public void verifyValidationMessageDisplayedAllMandatoryFields() throws InterruptedException {

        RegistrationPO register = new RegistrationPO(driver);

        Reporter.log("Step 1 - Navigate to signUp page");
        selenium.navigateToPage(Constants.URL + Constants.registerEndPoint);

        Reporter.log("Step 2 - Click on 'Sign up' button without entering detail");
        register.clickOnSignUpButton();

        Reporter.log("Step 3- Verify that validation messages are displayed for all mandatory fields ");
        String expectedMessage = Constants.requiredMessage;
        Assert.assertEquals(register.firstNameMandatoryFieldText(), expectedMessage, "First Name validation message doesn't match");
        Assert.assertEquals(register.lastNameMandatoryFieldText(), expectedMessage, "Last Name validation  message doesn't match");
        Assert.assertEquals(register.emailMandatoryFieldText(), expectedMessage, "Email Name validation message doesn't match");
        Assert.assertEquals(register.passwordMandatoryFieldText(), expectedMessage, "Password validation message doesn't match");
        Assert.assertEquals(register.confirmPasswordMandatoryFieldText(), expectedMessage, "Confirm Password validation message doesn't match");

    }

    /*Test 3 : Verify that validation message for invalid email*/
    @Test
    public void verifyValidationMessageDisplayedForInvalidEmail() throws InterruptedException {

        RegistrationPO register = new RegistrationPO(driver);

        Reporter.log("Step 1 - Navigate to signUp page");
        selenium.navigateToPage(Constants.URL + Constants.registerEndPoint);

        Reporter.log("Step 2 - Enter invalid email");
        register.enterEmail("invalid");
        register.clickOnSignUpButton();

        Reporter.log("Step 3- Verify that validation message is displayed for invalid email data");
        String expectedMessage = Constants.invalidEmailErrorMessage;
        Assert.assertEquals(register.emailMandatoryFieldText(), expectedMessage, "Email validation message doesn't match");
    }

    /*Test 4 : Verify that validation message for incomplete password */
    @Test
    public void verifyValidationMessageDisplayedForIncompletePassword() throws InterruptedException {

        RegistrationPO register = new RegistrationPO(driver);

        Reporter.log("Step 1 - Navigate to signUp page");
        selenium.navigateToPage(Constants.URL + Constants.registerEndPoint);

        Reporter.log("Step 2 - Enter incomplete password e.g 1 ");
        Registration data = new RegistrationData().getRegistrationData();
        register.fillRegistrationDetailsWithoutPassword(data);
        register.enterPasswordDetails("1", "1");
        register.clickOnSignUpButton();

        Reporter.log("Step 3- Verify that validation message is displayed for incomplete password ");
        String expectedMessage = Constants.passwordCharacterLimitValidationMessage;
        Assert.assertEquals(register.passwordMandatoryFieldText(), expectedMessage, "Password validation message doesn't match");
    }

    /*Test 5 : Verify that validation message when password - confirm password doesn't match*/
    @Test
    public void verifyValidationMessageDisplayedForPasswordAndConfirmPasswordDoesNotMatch() {

        RegistrationPO register = new RegistrationPO(driver);

        Reporter.log("Step 1 - Navigate to signUp page");
        selenium.navigateToPage(Constants.URL + Constants.registerEndPoint);

        Reporter.log("Step 2 - Enter different values for password and confirm password");
        register.enterPasswordDetails("12345", "123456");

        Reporter.log("Step 3- Verify that validation message is displayed when password - confirm password doesn't match");
        String expectedMessage = Constants.comparePasswordMessage;
        Assert.assertEquals(register.passwordMandatoryFieldText(), expectedMessage, "Message is not displayed");
    }

    /*Test 6 : Verify that 'Already have an account? Login' link works as expected */
    @Test
    public void verifyThatAlreadyHaveAccountLinkWorkAsExcepted() throws InterruptedException {

        RegistrationPO register = new RegistrationPO(driver);

        Reporter.log("Step 1 - Navigate to signUp page");
        selenium.navigateToPage(Constants.URL + Constants.registerEndPoint);

        Reporter.log("Step 2 - Click on already have account link");
        register.clickOnAlreadyHaveLink();

        Reporter.log("Step 3- Verify that  login page is displayed");
        String expectedUrl = Constants.URL + Constants.logInEndPoint;
        Assert.assertEquals(selenium.getURL(), expectedUrl, "Url doesn't match");
    }
}


package cord.account;

import base.BaseTest;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import pageobjects.cord.account.LoginPO;
import pageobjects.cord.account.PasswordChangeRequestPO;
import pageobjects.cord.common.HeaderPO;
import utilities.Constants;

public class LoginTests extends BaseTest {

    /*Test 1 : Verify that user can log in successfully*/
    @Test
    public void verifyLoginWithValidCredentials() throws InterruptedException {

        LoginPO login = new LoginPO(driver);
        HeaderPO header = new HeaderPO(driver);

        Reporter.log("Step 1 - Navigate to admin login page");
        selenium.navigateToPage(Constants.URL);

        Reporter.log("Step 2 - Entering valid sign in details and login to application");
        login.fillLoginDetailsAndPerformLogin(fpmUser.getEmail(), fpmUser.getPassword());

        Reporter.log("Step 3- Verify that user is logged in successfully by comparing account name");
        String expectedName = "Hi, " + fpmUser.getName().split(" ")[0];
        Assert.assertEquals(header.getProfileName(), expectedName, "Profile name doesn't match");
    }

    /*Test 2 : Verify that user can't log in with invalid credentials*/
    @Test
    public void verifyLoginWithInvalidCredentials() throws InterruptedException {

        LoginPO login = new LoginPO(driver);

        Reporter.log("Step 1 - Navigate to admin login page");
        selenium.navigateToPage(Constants.URL);

        Reporter.log("Step 2 - Entering invalid sign in details and login to application");
        login.fillLoginDetailsAndPerformLogin(Constants.invalidLoginEmail, Constants.invalidLoginPassword);

        Reporter.log("Step 3- Verify that validation message is displayed");
        String expectedErrorMessage = Constants.changePasswordErrorMessage;

        Assert.assertEquals(login.getErrorMessage(), expectedErrorMessage, "Error Message doesn't match");
    }

    /*Test 3 : Verify that user can log out successfully*/
    @Test
    public void verifyUserCanLogoutSuccessfully() throws InterruptedException {

        LoginPO login = new LoginPO(driver);
        HeaderPO header = new HeaderPO(driver);

        Reporter.log("Step 1 - Navigate to admin login page");
        selenium.navigateToPage(Constants.URL);

        Reporter.log("Step 2 - Entering valid sign in details and login to application");
        login.fillLoginDetailsAndPerformLogin(fpmUser.getEmail(), fpmUser.getPassword());

        Reporter.log("Step 3 - Click on Profile Icon and click on Sign Out button");
        header.signOut();

        Reporter.log("Step 4- Verify that login page message displayed");
        String expectedUrl = Constants.URL + Constants.logInEndPoint;
        Assert.assertEquals(selenium.getURL(), expectedUrl, "Url doesn't match");
    }

    /*Test 4 : Verify that 'Forgot Password?' link works as expected*/
    @Test
    public void verifyForgotPasswordLinkWorkProperly() throws InterruptedException {

        LoginPO login = new LoginPO(driver);

        Reporter.log("Step 1 - Navigate to admin login page");
        selenium.navigateToPage(Constants.URL);

        Reporter.log("Step 2 - Click on Forgot password link");
        login.clickOnForgotPasswordLink();

        Reporter.log("Step 3- Verify that forgot password page is displayed");
        String expectedUrl = Constants.URL + Constants.forgotPasswordEndPoint;
        Assert.assertEquals(selenium.getURL(), expectedUrl, "Url doesn't match");
    }

    /*Test 5 : Verify that 'Reset Password?' button redirects to 'password change request' page*/
    @Test
    public void verifyForgotPasswordWorkProperly() throws InterruptedException {

        LoginPO login = new LoginPO(driver);
        PasswordChangeRequestPO passwordChangeRequest = new PasswordChangeRequestPO(driver);

        Reporter.log("Step 1 - Navigate to admin login page");
        selenium.navigateToPage(Constants.URL);

        Reporter.log("Step 2 - Click on Forgot password link");
        login.clickOnForgotPasswordLink();

        Reporter.log("Step 3- Entering valid detail and reset the password");
        login.fillResetPasswordDetailAndRedirectToPasswordChangeRequestPage(fpmUser.getEmail());

        Reporter.log("Step 4- Verify that password change request page displayed");
        String expectedMessage = Constants.passwordChangeRequestMessage;
        Assert.assertEquals(passwordChangeRequest.getPasswordChangeRequestText(), expectedMessage, "Message is not displayed");

    }


}

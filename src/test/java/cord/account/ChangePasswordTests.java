package cord.account;

import base.BaseTest;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import pageobjects.cord.account.ChangePasswordPO;
import pageobjects.cord.account.LoginPO;
import pageobjects.cord.common.HeaderPO;
import utilities.Constants;

public class ChangePasswordTests extends BaseTest {

    /*Test 1 : Verify that user can change password and login again successfully with new password*/
    @Test
    public void verifyUserCanChangePasswordAndLoginAgainSuccessfullyWithNewPassword() throws InterruptedException {

        LoginPO login = new LoginPO(driver);
        HeaderPO header = new HeaderPO(driver);
        ChangePasswordPO changePassword = new ChangePasswordPO(driver);

        Reporter.log("Step 1 - Navigate to admin login page");
        selenium.navigateToPage(Constants.URL);

        Reporter.log("Step 2 - Entering valid sign in details and login to application");
        String password = passwordUser.getPassword();
        String newPassword = passwordUser.getNewPassword();
        String changePasswordErrorMessage = Constants.changePasswordErrorMessage;
        selenium.hardWait(3);
        login.fillLoginDetailsAndPerformLogin(passwordUser.getEmail(), password);

        if (login.isErrorMessagePresent() && login.getErrorMessage().equals(changePasswordErrorMessage)) {
            password = passwordUser.getNewPassword();
            newPassword = passwordUser.getPassword();

            login.fillLoginDetailsAndPerformLogin(passwordUser.getEmail(), password);
        }

        Reporter.log("Step 3- Click on Change Password button");
        header.clickOnChangePasswordButton();

        Reporter.log("Step 4 - Enter old and new passwords and click on submit button");
        changePassword.fillPasswordDetailAndClickOnSubmitButton(password, newPassword); // working

        Reporter.log("Step 5 - Refresh the page to hide the Profile click popup");
        selenium.refreshPage();

        Reporter.log("Step 6- Sign out using sign out button");
        header.signOut();

        Reporter.log("Step 7 - Entering valid sign in details and again login to application");
        login.fillLoginDetailsAndPerformLogin(passwordUser.getEmail(), newPassword);

        Reporter.log("Step 8- Verify that user is logged in successfully by comparing first name");
        String expectedName = "Hi, " + passwordUser.getName().split(" ")[0];
        Assert.assertEquals(header.getProfileName(), expectedName, "Logged in user name doesn't match");
    }

}

package funcLibs.project;

import dataobjects.User;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import pageobjects.cord.account.LoginPO;
import pageobjects.cord.common.HeaderPO;
import utilities.Constants;
import utilities.SeleniumHelpers;

public class LoginFunctions {

    protected WebDriver driver;
    protected SeleniumHelpers selenium;

    public LoginFunctions(WebDriver driver, SeleniumHelpers selenium) {
        this.driver = driver;
        this.selenium = selenium;
    }

    /**
     * login with given user
     *
     * @param user user
     */

    public void loginByGivenUser(User user) {
        try {
            LoginPO login = new LoginPO(driver);

            Reporter.log("Step 1 - Navigate to admin login page");
            selenium.navigateToPage(Constants.URL);

            Reporter.log("Step 2 - Entering valid sign in details and login to application");
            login.fillLoginDetailsAndPerformLogin(user.getEmail(), user.getPassword());

        } catch (Exception e) {
            System.out.println(e);

        }

    }

    /**
     * sign out and change user
     *
     * @param user user
     */

    public void signOutAndChangeUser(User user) {
        try {

            HeaderPO headerPO = new HeaderPO(driver);
            headerPO.signOut();
            loginByGivenUser(user);

        } catch (Exception e) {
            System.out.println(e);

        }

    }

}

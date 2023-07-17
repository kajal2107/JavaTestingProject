package base;

import dataobjects.User;
import dataobjects.Users;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utilities.Constants;
import utilities.DriverManager;
import utilities.JavaHelpers;
import utilities.SeleniumHelpers;

import java.io.IOException;

public class BaseTest {

    protected WebDriver driver;
    protected SeleniumHelpers selenium;
    private DriverManager drivermanager;
    protected static User fpmUser;
    protected static User passwordUser;
    protected static User adminUser;
    protected static User rdUser;
    protected static User rdFodUser;
    protected static User rdFpmUser;
    protected static User imFpmUser;
    protected static User faControllerUser;
    protected static User faUser;
    protected static User consultantUser;
    protected static User consultantManagerUser;
    protected static User internUser;
    protected static User controllerUser;
    protected static User fundraisingUser;
    protected static User fodUser;
    protected static User mrktUser;
    protected static User stfUser;
    protected static User leaderUser;
    protected static User liaisonUser;
    protected static User lfaUser;

    @BeforeMethod
    @Parameters({"browser"})
    public void setUp(@Optional("chrome") String browser) {
        drivermanager = new DriverManager();
        driver = drivermanager.setUp(browser);
        selenium = new SeleniumHelpers(driver);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        try {
            //capturing screenshot if failed
            if (ITestResult.FAILURE == result.getStatus()) {
                selenium.takeScreenshot(result.getName());
            }
        } catch (Exception e) {
            //ignore
        }

        drivermanager.tearDown();
    }

    @BeforeSuite
    public void beforeSuite() throws IOException, ParseException {

        //User Setup
        fpmUser = this.setUserDataByDescription("fpm");
        passwordUser = this.setUserDataByDescription("password");
        adminUser = this.setUserDataByDescription("admin");
        rdUser = this.setUserDataByDescription("rd");
        rdFodUser = this.setUserDataByDescription("rdFod");
        rdFpmUser = this.setUserDataByDescription("rdFpm");
        imFpmUser = this.setUserDataByDescription("imFpm");
        faControllerUser = this.setUserDataByDescription("faController");
        faUser = this.setUserDataByDescription("fa");
        consultantUser = this.setUserDataByDescription("consultant");
        consultantManagerUser = this.setUserDataByDescription("consultantManager");
        internUser = this.setUserDataByDescription("intern");
        controllerUser = this.setUserDataByDescription("controller");
        fodUser = this.setUserDataByDescription("fod");
        fundraisingUser = this.setUserDataByDescription("fd");
        mrktUser = this.setUserDataByDescription("mrkt");
        stfUser = this.setUserDataByDescription("stf");
        leaderUser = this.setUserDataByDescription("leader");
        liaisonUser = this.setUserDataByDescription("lsn");
        lfaUser = this.setUserDataByDescription("lfa");

        //Deletes screenshots
        new JavaHelpers().deleteAllFilesFromFolder(Constants.SCREENSHOT_LOCATION);
    }

    public User setUserDataByDescription(String userType) throws IOException, ParseException {

        //User Setup
        String USERS_JSON = "src/main/resources/users.json";

        return JavaHelpers.jsonDeserialization(JavaHelpers.jsonToString(USERS_JSON), Users.class).getUsers().stream().filter(x -> x.getDescription().equalsIgnoreCase(userType)).findFirst().get();
    }

}

package funcLibs.project;

import enums.menu.CreateItemTypes;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import pageobjects.cord.common.LeftNavPO;
import pageobjects.cord.location.CreateUpdateLocationPO;
import utilities.SeleniumHelpers;

public class LocationFunctions {

    protected WebDriver driver;
    protected SeleniumHelpers selenium;

    public LocationFunctions(WebDriver driver, SeleniumHelpers selenium) {
        this.driver = driver;
        this.selenium = selenium;
    }

    /**
     * create location
     *
     * @param data LocationData
     */

    public void createLocation(dataobjects.Location data) {

        try {

            CreateUpdateLocationPO createUpdateLocationPO = new CreateUpdateLocationPO(driver);
            LeftNavPO sideHeader = new LeftNavPO(driver);

            Reporter.log("Step 1 - Click on crate new item and select person menu");
            sideHeader.clickOnCreateNewItemAndSelectType(CreateItemTypes.Location.toString());

            Reporter.log("Step 2 - Enter valid details and click on submit button");
            createUpdateLocationPO.fillLocationDetailAndAddNewFundingAccount(data);
        } catch (Exception e) {
            System.out.println(e);
        }


    }

}

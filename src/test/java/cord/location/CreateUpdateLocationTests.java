package cord.location;

import base.BaseTest;
import datafactory.LocationData;
import dataobjects.Location;
import enums.menu.CreateItemTypes;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import pageobjects.cord.account.LoginPO;
import pageobjects.cord.common.HeaderPO;
import pageobjects.cord.common.LeftNavPO;
import pageobjects.cord.location.CreateUpdateLocationPO;
import pageobjects.cord.location.LocationDetailsPO;
import pageobjects.cord.location.LocationsListingPO;
import utilities.Constants;

public class CreateUpdateLocationTests extends BaseTest {

    /*Test 1 : Verify that user can create/update location successfully*/
    @Test
    public void verifyUserCanCreateUpdateLocationSuccessfully() throws InterruptedException {

        LoginPO login = new LoginPO(driver);
        LeftNavPO leftNav = new LeftNavPO(driver);
        CreateUpdateLocationPO createLocation = new CreateUpdateLocationPO(driver);
        LocationDetailsPO locationDetails = new LocationDetailsPO(driver);
        LocationsListingPO locations = new LocationsListingPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);

        Reporter.log("Step 1 - Navigate to admin login page");
        selenium.navigateToPage(Constants.URL);

        Reporter.log("Step 2 - Entering valid sign in details and login to application");
        login.fillLoginDetailsAndPerformLogin(adminUser.getEmail(), adminUser.getPassword());

        Reporter.log("Step 3- Click on crate new item and select Location menu");
         leftNav.clickOnCreateNewItemAndSelectType(CreateItemTypes.Location.toString());

        Reporter.log("Step 4- Enter valid details and click on submit button");
        Location data = new LocationData().getLocationData();
        createLocation.fillLocationDetailAndAddNewFundingAccount(data);

        if (!locations.isLocationPresent(data.getLocationName())) {
            headerPO.searchItemGloballyByName(data.getLocationName());
        }
        locations.clickOnViewLocationButton(data.getLocationName());

        Reporter.log("Step 5- Verify that location is created successfully by comparing location details");
        Assert.assertEquals(data.getLocationName(), locationDetails.getLocationName(), "location name doesn't match : ");
        Assert.assertEquals(data.getLocationType(), locationDetails.getLocationType().split(","), "location type doesn't match : ");
        Assert.assertEquals(data.getLocationCountryCode().replace("[","").replace("]",""), locationDetails.getLocationCountryCode(), "location country code doesn't match");
        Assert.assertTrue(locationDetails.getLocationFundingAccountText().startsWith(data.getSearchFundingAccountName()), "location funding account doesn't match");

        locationDetails.clickOnEditButton();
        createLocation.fillLocationDetailAndAddNewFundingAccount(data);

        Reporter.log("Step 6- Verify that location is updated successfully by comparing location details");
        Assert.assertEquals(data.getLocationName(), locationDetails.getLocationName(), "location name doesn't match : ");
        Assert.assertEquals(data.getLocationType(), locationDetails.getLocationType().split(","), "location type doesn't match : ");
        Assert.assertEquals(data.getLocationCountryCode().replace("[","").replace("]",""), locationDetails.getLocationCountryCode(), "location country code doesn't match");
        Assert.assertTrue(locationDetails.getLocationFundingAccountText().startsWith(data.getSearchFundingAccountName()), "location funding account doesn't match");

    }

    /*Test 2 : Verify that user can create location with New funding account successfully*/
    @Test
    public void verifyUserCanCreateLocationWithNewFundingAccountSuccessfully() throws InterruptedException {

        LoginPO login = new LoginPO(driver);
        LeftNavPO leftNav = new LeftNavPO(driver);
        CreateUpdateLocationPO createLocation = new CreateUpdateLocationPO(driver);
        LocationDetailsPO locationDetails = new LocationDetailsPO(driver);
        LocationsListingPO locations = new LocationsListingPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);

        Reporter.log("Step 1 - Navigate to admin login page");
        selenium.navigateToPage(Constants.URL);

        Reporter.log("Step 2 - Entering valid sign in details and login to application");
        login.fillLoginDetailsAndPerformLogin(adminUser.getEmail(), adminUser.getPassword());

        Reporter.log("Step 3- Click on crate new item and select Location menu");
        leftNav.clickOnCreateNewItemAndSelectType(CreateItemTypes.Location.toString());

        Reporter.log("Step 4- Enter valid details and click on submit button");
        Location fundingAccData = new LocationData().getLocationFundingAccountData();
        createLocation.fillLocationDetailAndAddNewFundingAccount(fundingAccData);

        if (!locations.isLocationPresent(fundingAccData.getLocationName())) {
            headerPO.searchItemGloballyByName(fundingAccData.getLocationName());
        }
        locations.clickOnViewLocationButton(fundingAccData.getLocationName());

        Reporter.log("Step 5- Verify that location is created successfully by comparing location details");
        Assert.assertEquals(fundingAccData.getLocationName(), locationDetails.getLocationName(), "location name doesn't match : ");
        Assert.assertEquals(fundingAccData.getLocationCountryCode().replace("[","").replace("]",""), locationDetails.getLocationCountryCode(), "location country code doesn't match");
        Assert.assertEquals(fundingAccData.getLocationCountryCode().replace("[","").replace("]",""), locationDetails.getLocationCountryCode(), "location country code doesn't match");
        Assert.assertEquals(fundingAccData.getSearchFundingAccountName() + " " + "(" + fundingAccData.getEnterFundingAccountNumber() + ")", locationDetails.getLocationFundingAccountText(), "location country code doesn't match");

    }

}

package cord.partners;

import base.BaseTest;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import pageobjects.cord.account.LoginPO;
import pageobjects.cord.common.LeftNavPO;
import pageobjects.cord.partners.PartnersListingPO;
import utilities.Constants;

import java.util.Collections;
import java.util.List;

public class PartnersSortListTests extends BaseTest {

    /*Test 1 : Verify that user can short Partners list z-a successfully*/
    @Test
    public void verifyUserCanSortPartnerListZToASuccessfully() throws InterruptedException {
        LoginPO login = new LoginPO(driver);
        LeftNavPO leftNav = new LeftNavPO(driver);
        PartnersListingPO partnerListing = new PartnersListingPO(driver);

        Reporter.log("Step 1 - Navigate to admin login page");
        selenium.navigateToPage(Constants.URL);

        Reporter.log("Step 2 - Entering valid sign in details and login to application");
        login.fillLoginDetailsAndPerformLogin(fpmUser.getEmail(), fpmUser.getPassword());

        Reporter.log("Step 3 - Click on Partners menu from left navigation");
        leftNav.clickOnPartnersMenu();

        List<String> originalData = partnerListing.getPartnersList();

        Reporter.log("Step 4 - Click on sort option button and select 'Z-A' option");
        partnerListing.clickOnSortOption();
        partnerListing.clickOnZToASortOption();

        Reporter.log("Step 5 - Verify partners name should be sorted");
        List<String> sortedZtoAList = partnerListing.getPartnersList();
        originalData.sort(Collections.reverseOrder());
        Assert.assertEquals(sortedZtoAList, originalData, "List isn't sorted correctly alphabetically (Z-A)");

    }

    /*Test 2 : Verify that user can sort Partners list a-z successfully*/
    @Test
    public void verifyUserCanSortPartnerListAToZSuccessfully() throws InterruptedException {
        LoginPO login = new LoginPO(driver);
        LeftNavPO leftNav = new LeftNavPO(driver);
        PartnersListingPO partnerListing = new PartnersListingPO(driver);

        Reporter.log("Step 1 - Navigate to admin login page");
        selenium.navigateToPage(Constants.URL);

        Reporter.log("Step 2 - Entering valid sign in details and login to application");
        login.fillLoginDetailsAndPerformLogin(fpmUser.getEmail(), fpmUser.getPassword());

        Reporter.log("Step 3 - Click on Partners menu from left navigation");
        leftNav.clickOnPartnersMenu();

        List<String> originalData = partnerListing.getPartnersList();

        Reporter.log("Step 4 - Click on sort option button and select 'A-Z' option");
        partnerListing.clickOnSortOption();
        partnerListing.clickOnAToZSortOption();

        Reporter.log("Step 5 - Verify partners name should be sorted");
        List<String> sortedAtoZList = partnerListing.getPartnersList();
        Collections.sort(sortedAtoZList);
        Assert.assertEquals(sortedAtoZList, originalData, "List isn't sorted alphabetically (A-Z)");
    }
}

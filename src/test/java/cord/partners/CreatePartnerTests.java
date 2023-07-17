package cord.partners;

import base.BaseTest;
import datafactory.PartnerData;
import dataobjects.Partner;
import enums.menu.CreateItemTypes;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import pageobjects.cord.account.LoginPO;
import pageobjects.cord.common.HeaderPO;
import pageobjects.cord.common.LeftNavPO;
import pageobjects.cord.partners.CreateUpdatePartnerPO;
import pageobjects.cord.partners.PartnersListingPO;
import utilities.Constants;

public class CreatePartnerTests extends BaseTest {

    /*Test 1 : Verify that user can create partner successfully*/
    @Test
    public void verifyUserCanCreatePartnerSuccessfully() throws InterruptedException {

        LoginPO login = new LoginPO(driver);
        CreateUpdatePartnerPO partner = new CreateUpdatePartnerPO(driver);
        LeftNavPO leftNav = new LeftNavPO(driver);
        PartnersListingPO partnerListing = new PartnersListingPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);

        Reporter.log("Step 1 - Navigate to admin login page");
        selenium.navigateToPage(Constants.URL);

        Reporter.log("Step 2 - Entering valid sign in details and login oto application");
        login.fillLoginDetailsAndPerformLogin(adminUser.getEmail(), adminUser.getPassword());

        Reporter.log("Step 3- Click on crate new item and select Partner menu");
        leftNav.clickOnCreateNewItemAndSelectType(CreateItemTypes.Partner.toString());

        Reporter.log("Step 4- Enter valid detail of partner and click on submit button");
        Partner data = new PartnerData().getCreatePartnerData();
        partner.fillPartnerDetail(data);

        Reporter.log("Step 5- Go to Partner page and verify that partner is created successfully");
        leftNav.clickOnPartnersMenu();
        if (!partnerListing.isPartnerPresent(data.getPartnerName())) {
            headerPO.searchItemGloballyByName(data.getPartnerName());
        }
        Assert.assertTrue(partnerListing.isPartnerPresent(data.getPartnerName()), "Partner is not present : " + data.getPartnerName());

    }
}

package cord.partners;

import base.BaseTest;
import datafactory.PartnerData;
import dataobjects.Partner;
import enums.menu.CreateItemTypes;
import enums.partner.PartnerStatus;
import enums.partner.PartnerType;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import pageobjects.cord.account.LoginPO;
import pageobjects.cord.common.HeaderPO;
import pageobjects.cord.common.LeftNavPO;
import pageobjects.cord.partners.CreateUpdatePartnerPO;
import pageobjects.cord.partners.EditPartnerPO;
import pageobjects.cord.partners.PartnerDetailsPO;
import pageobjects.cord.partners.PartnersListingPO;
import utilities.Constants;

public class UpdatePartnerTests extends BaseTest {

    /*Test 1 : Verify that user can update partner successfully*/
    @Test
    public void verifyUserCanUpdatePartnerSuccessfully() throws InterruptedException {

        LoginPO login = new LoginPO(driver);
        CreateUpdatePartnerPO createUpdatePartner = new CreateUpdatePartnerPO(driver);
        LeftNavPO leftNav = new LeftNavPO(driver);
        PartnersListingPO partnersListing = new PartnersListingPO(driver);
        PartnerDetailsPO partnerDetails = new PartnerDetailsPO(driver);
        EditPartnerPO partnerEdit = new EditPartnerPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);

        Reporter.log("Step 1 - Navigate to admin login page");
        selenium.navigateToPage(Constants.URL);

        Reporter.log("Step 2 - Entering valid sign in details and login to application");
        login.fillLoginDetailsAndPerformLogin(adminUser.getEmail(), adminUser.getPassword());

        Reporter.log("Step 3- Click on crate new item and select Partner menu");
        leftNav.clickOnCreateNewItemAndSelectType(CreateItemTypes.Partner.toString());

        Reporter.log("Step 4- Enter Organization name and click on submit button");
        Partner data = new PartnerData().getCreatePartnerData();
        createUpdatePartner.fillPartnerDetail(data);

        Reporter.log("Step 5 - Go to Partners listing page and click on recently created Partner item");
        leftNav.clickOnPartnersMenu();
        if (!partnersListing.isPartnerPresent(data.getPartnerName())) {
            headerPO.searchItemGloballyByName(data.getPartnerName());
        }
        partnersListing.clickOnPartnerName(data.getPartnerName());

        Reporter.log("Step 6 - Click on edit icon and select global innovations client");
        partnerDetails.clickOnEditButton();
        partnerEdit.clickOnGlobalInnovationsClientCheckBox();

        Reporter.log("Step 7 - Click on inactive button and make it active");
        partnerDetails.clickOnPartnerStatusByName(PartnerStatus.Inactive.toString());
        partnerEdit.clickOnActiveInActiveCheckBox();

        Reporter.log("Step 8 - Click on pmc code button and fill the code detail");
        Partner editPartnerData = new PartnerData().getEditPartnerData();
        partnerDetails.clickOnEntityCodeButton();
        partnerEdit.fillPmcEntityCode(editPartnerData);

        Reporter.log("Step 9 - Click on add partner types button and select partner types");
        partnerDetails.clickOnAddPartnerTypesButton();
        partnerEdit.clickOnPartnerTypesByName(PartnerType.Impact.toString());

        Reporter.log("Step 10 - Click on add address button and fill the address detail");
        partnerDetails.clickOnAddAddressButton();
        partnerEdit.fillAddress(editPartnerData);

        Reporter.log("Step 11 - Click on a point of contact button and fill the point of contact detail");
        partnerDetails.clickOnAddPointOfContactButton();
        partnerEdit.clearAndFillPartnerPointOfContact(adminUser);

        Reporter.log("Step 12- verify that partner data is updated successfully");
        Assert.assertTrue(partnerDetails.isGlobalInnovationsClientPresent(), "Global Innovations Client is not present ");
        Assert.assertEquals(PartnerStatus.Active.toString(), partnerDetails.getPartnerStatusByName(PartnerStatus.Active.toString()), "Status doesn't match");
        Assert.assertEquals(editPartnerData.getPartnerPmcEntityCode(), partnerDetails.getPmcEntityCodeText(1), "entity code doesn't match ");
        Assert.assertEquals(editPartnerData.getPartnerAddress(), partnerDetails.getAddressText(), "address doesn't match ");
        Assert.assertEquals(PartnerType.Impact.toString(), partnerDetails.getTypesText(), "Types doesn't match");
        Assert.assertEquals(adminUser.getRealFirstName() + " " + adminUser.getRealLastName(), partnerDetails.getPointOfContactText(), "point of contact doesn't match ");
    }
}

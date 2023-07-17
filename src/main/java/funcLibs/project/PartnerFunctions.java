package funcLibs.project;

import dataobjects.Partner;
import dataobjects.User;
import enums.menu.CreateItemTypes;
import enums.partner.PartnerType;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import pageobjects.cord.common.HeaderPO;
import pageobjects.cord.common.LeftNavPO;
import pageobjects.cord.partners.CreateUpdatePartnerPO;
import pageobjects.cord.partners.EditPartnerPO;
import pageobjects.cord.partners.PartnerDetailsPO;
import pageobjects.cord.partners.PartnersListingPO;
import utilities.SeleniumHelpers;

public class PartnerFunctions {

    protected WebDriver driver;
    protected SeleniumHelpers selenium;

    public PartnerFunctions(WebDriver driver, SeleniumHelpers selenium) {
        this.driver = driver;
        this.selenium = selenium;
    }

    /**
     * create partner
     *
     * @param data PartnerData
     */

    public void createPartner(dataobjects.Partner data) {

        try {
            CreateUpdatePartnerPO partner = new CreateUpdatePartnerPO(driver);
            LeftNavPO leftNav = new LeftNavPO(driver);

            Reporter.log("Step 1- Click on crate new item and select partner menu");
            leftNav.clickOnCreateNewItemAndSelectType(CreateItemTypes.Partner.toString());

            Reporter.log("Step 2- Enter valid detail of partner and click on submit button");
            partner.fillPartnerDetail(data);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Edit Partner
     *
     * @param editPartnerData edit Partner Data
     */
    public void editProjectPartner(User user, Partner editPartnerData, String partnerName) {

        LeftNavPO leftNav = new LeftNavPO(driver);
        PartnersListingPO partnersListing = new PartnersListingPO(driver);
        PartnerDetailsPO partnerDetails = new PartnerDetailsPO(driver);
        EditPartnerPO partnerEdit = new EditPartnerPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);

        try {

            Reporter.log("Step 1 - Go to Partners listing page and click on recently created Partner item");
            leftNav.clickOnPartnersMenu();
            if (!partnersListing.isPartnerPresent(partnerName)) {
                headerPO.searchItemGloballyByName(partnerName);
            }
            partnersListing.clickOnPartnerName(partnerName);

            Reporter.log("Step 2 - Click on edit icon and select global innovations client");
            partnerDetails.clickOnEditButton();
            partnerEdit.clickOnGlobalInnovationsClientCheckBox();

            Reporter.log("Step 3 - Click on inactive button and make it active");
            partnerDetails.clickOnPartnerStatusByName("Inactive");
            partnerEdit.clickOnActiveInActiveCheckBox();

            Reporter.log("Step 4 - Click on pmc code button and fill the code detail");
            partnerDetails.clickOnEntityCodeButton();
            partnerEdit.fillPmcEntityCode(editPartnerData);

            Reporter.log("Step 5 - Click on add partner types button and select partner types");
            partnerDetails.clickOnAddPartnerTypesButton();
            partnerEdit.clickOnPartnerTypesByName(PartnerType.Impact.toString());

            Reporter.log("Step 6 - Click on add address button and fill the address detail");
            partnerDetails.clickOnAddAddressButton();
            partnerEdit.fillAddress(editPartnerData);

            Reporter.log("Step 7 - Click on a point of contact button and fill the point of contact detail");
            partnerDetails.clickOnAddPointOfContactButton();
            partnerEdit.clearAndFillPartnerPointOfContact(user);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     *
     * Edit Partner Global Innovations Client
     * @param partnerName partner name
     */
    public void editPartnerGlobalInnovationsClient(String partnerName) {

        LeftNavPO leftNav = new LeftNavPO(driver);
        PartnersListingPO partnersListing = new PartnersListingPO(driver);
        PartnerDetailsPO partnerDetails = new PartnerDetailsPO(driver);
        EditPartnerPO partnerEdit = new EditPartnerPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);

        try {

            Reporter.log("Step 1 - Go to Partners listing page and click on recently created Partner item");
            leftNav.clickOnPartnersMenu();
            if (!partnersListing.isPartnerPresent(partnerName)) {
                headerPO.searchItemGloballyByName(partnerName);
            }
            partnersListing.clickOnPartnerName(partnerName);

            Reporter.log("Step 2 - Click on edit icon and select global innovations client");
            partnerDetails.clickOnEditButton();
            partnerEdit.clickOnGlobalInnovationsClientCheckBox();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     *
     * Edit Partner Inactive Active status
     * @param partnerName partner name
     */
    public void editPartnerActiveInActiveStatus(String partnerName) {

        LeftNavPO leftNav = new LeftNavPO(driver);
        PartnersListingPO partnersListing = new PartnersListingPO(driver);
        PartnerDetailsPO partnerDetails = new PartnerDetailsPO(driver);
        EditPartnerPO partnerEdit = new EditPartnerPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);

        try {

            Reporter.log("Step 1 - Go to Partners listing page and click on recently created Partner item");
            leftNav.clickOnPartnersMenu();
            if (!partnersListing.isPartnerPresent(partnerName)) {
                headerPO.searchItemGloballyByName(partnerName);
            }
            partnersListing.clickOnPartnerName(partnerName);

            Reporter.log("Step 2 - Click on inactive button and make it active");
            partnerDetails.clickOnPartnerStatusByName("Inactive");
            partnerEdit.clickOnActiveInActiveCheckBox();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     *
     * Edit Partner PMC Entity Code
     * @param partnerName partner name
     */
    public void editPartnerPmcEntityCode(String partnerName,Partner editPartnerData) {

        LeftNavPO leftNav = new LeftNavPO(driver);
        PartnersListingPO partnersListing = new PartnersListingPO(driver);
        PartnerDetailsPO partnerDetails = new PartnerDetailsPO(driver);
        EditPartnerPO partnerEdit = new EditPartnerPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);

        try {

            Reporter.log("Step 1 - Go to Partners listing page and click on recently created Partner item");
            leftNav.clickOnPartnersMenu();
            if (!partnersListing.isPartnerPresent(partnerName)) {
                headerPO.searchItemGloballyByName(partnerName);
            }
            partnersListing.clickOnPartnerName(partnerName);

            Reporter.log("Step 2 - Click on pmc code button and fill the code detail");
            partnerDetails.clickOnPmcEntityCodeButton();
            partnerEdit.fillPmcEntityCode(editPartnerData);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     *
     * Edit Partner point of contact
     * @param partnerName partner name
     */
    public void editPartnerPointOfContact(String partnerName,User user) {

        LeftNavPO leftNav = new LeftNavPO(driver);
        PartnersListingPO partnersListing = new PartnersListingPO(driver);
        PartnerDetailsPO partnerDetails = new PartnerDetailsPO(driver);
        EditPartnerPO partnerEdit = new EditPartnerPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);

        try {

            Reporter.log("Step 1 - Go to Partners listing page and click on recently created Partner item");
            leftNav.clickOnPartnersMenu();
            if (!partnersListing.isPartnerPresent(partnerName)) {
                headerPO.searchItemGloballyByName(partnerName);
            }
            partnersListing.clickOnPartnerName(partnerName);

            Reporter.log("Step 2 - Click on a point of contact button and fill the point of contact detail");
            partnerDetails.clickOnAddPointOfContactButton();
            partnerEdit.clearAndFillPartnerPointOfContact(user);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

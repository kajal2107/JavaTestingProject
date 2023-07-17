package pageobjects.cord.location;

import dataobjects.Location;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;


public class CreateUpdateLocationPO extends BasePO {

    public CreateUpdateLocationPO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    @FindBy(name = "location.name")
    private WebElement locationNameTextBox;

    @FindBy(id = "mui-component-select-location.type")
    private WebElement locationTypeDropdown;

    @FindBy(name = "location.isoAlpha3")
    private WebElement locationIsoCountryCodeTextBox;

    @FindBy(xpath = "//input[contains(@placeholder,'Search for a funding account by name')]")
    private WebElement searchFundingAccountNameTextBox;

    @FindBy(xpath = "//ul[contains(@class,'MuiAutocomplete-listbox')]/li[1]")
    private WebElement addFundingAccountName;

    @FindBy(name = "fundingAccount.name")
    private WebElement fundingAccountNameTextBox;

    @FindBy(name = "fundingAccount.accountNumber")
    private WebElement fundingAccountNumberTextBox;

    @FindBy(xpath = "//h2[contains(text(),'Create Funding Account')]/following::div[contains(@class,'MuiDialogActions-root')]//span[text()='Submit']")
    private WebElement fundingAccSubmitButton;

    private final By submitButton = By.xpath("//span[text()='Submit']");

    /**
     * Select location type by name
     *
     * @param LocationTypes LocationTypes
     * @throws InterruptedException exception
     */
    public void selectLocationTypesByName(String[] LocationTypes) throws InterruptedException {
        for (String locationTypes : LocationTypes) {
            String locationTypesXpathSelector = "//li[contains(text(),'" + locationTypes + "')]";
            selenium.clickOn(By.xpath(locationTypesXpathSelector));
        }
    }

    /**
     * Fill location detail and add new fundingAccount
     *
     * @param data location object
     */
    public void fillLocationDetailAndAddNewFundingAccount(Location data) throws InterruptedException {
        selenium.clearTextBoxUsingKeys(locationNameTextBox);
        selenium.enterText(locationNameTextBox, data.getLocationName(), true);
        selenium.clickOn(locationTypeDropdown);
        selectLocationTypesByName(data.getLocationType());
        selenium.enterText(locationIsoCountryCodeTextBox, data.getLocationCountryCode(), true);
        selenium.clearTextBoxUsingKeys(searchFundingAccountNameTextBox);
        selenium.enterText(searchFundingAccountNameTextBox, data.getSearchFundingAccountName(), true);

        if (addFundingAccountName.getText().contains("Create")) {
            selenium.clickOn(addFundingAccountName);
            selenium.clearTextBoxUsingKeys(fundingAccountNameTextBox);
            selenium.enterText(fundingAccountNameTextBox, data.getSearchFundingAccountName(), true);
            selenium.enterText(fundingAccountNumberTextBox, data.getEnterFundingAccountNumber(), true);
            selenium.hardWait(3);
            selenium.click(fundingAccSubmitButton);
        } else {
            selenium.hardWait(3);
            data.setSearchFundingAccountName(addFundingAccountName.getText());
            selenium.clickOn(addFundingAccountName);
        }
        selenium.clickOn(submitButton);
        selenium.waitTillElementsCountIsLessThan(submitButton, 1);
    }

}


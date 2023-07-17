package pageobjects.cord.partners;

import dataobjects.Partner;
import dataobjects.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class EditPartnerPO extends BasePO {

    public EditPartnerPO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    @FindBy(name = "partner.globalInnovationsClient")
    private WebElement globalInnovationsClientCheckBox;

    @FindBy(name = "partner.active")
    private WebElement activeInactiveCheckBox;

    @FindBy(name = "partner.pmcEntityCode")
    private WebElement pmcEntityCodeTextBox;

    @FindBy(name = "partner.address")
    private WebElement addressTextBox;

    @FindBy(xpath = "//input[@placeholder= 'Search for a person by name']")
    private WebElement pointOfContactTextBox;

    @FindBy(xpath = "//button[contains(@class,'outlined')]/span[contains(text(),'Enter PMC Entity Code')]'")
    //Need to create Dynamic Locator
    private WebElement entityCodeButton;

    @FindBy(xpath = "//button[contains(@class,'PartnerTypesCard')]")
    private WebElement addPartnerTypesButton;

    @FindBy(xpath = "//div[contains(@class,'MuiAutocomplete-popper')]")
    private WebElement addPointOfContactName;

    @FindBy(xpath = "//ul[contains(@class,'MuiAutocomplete-listbox')]/li[1]")
    private WebElement searchSuggestionPointOfContactName;

    @FindBy(xpath = "//span[text()='Global Innovations Client']/following::p[contains(@class,'MuiFormHelperText-root')]")
    private WebElement GlobalInnovationsClientErrorMessageText;

    @FindBy(xpath = "//span[text()='Active']/following::p[contains(@class,'MuiFormHelperText-root')]")
    private WebElement activeInActiveErrorMessageText;

    @FindBy(xpath = "//input[@name='partner.pmcEntityCode']//following::p[contains(@class,'MuiFormHelperText-root')]")
    private WebElement pmcEntityCodeErrorMessageText;

    @FindBy(xpath = "//input[@placeholder='Search for a person by name']/following::p[contains(@class,'MuiFormHelperText-root')]")
    private WebElement pointOfContactErrorMessageText;


    private By submitButton = By.xpath("//span[text()='Submit']");

    private By cancelButton = By.xpath("//span[text()='Cancel']");


    /**
     * Click on global innovations client checkBox
     *
     * @throws InterruptedException exception
     */
    public void clickOnGlobalInnovationsClientCheckBox() throws InterruptedException {

        selenium.click(globalInnovationsClientCheckBox);
        selenium.clickOn(submitButton);
        selenium.waitTillElementsCountIsLessThan(submitButton, 1);
    }

    /**
     * Click on active checkBox
     *
     * @throws InterruptedException exception
     */
    public void clickOnActiveInActiveCheckBox() throws InterruptedException {

        if (!selenium.isCheckboxSelected(activeInactiveCheckBox)) {
            selenium.click(activeInactiveCheckBox);
        }
        selenium.clickOn(submitButton);
        selenium.waitTillElementsCountIsLessThan(submitButton, 1);
    }

    /**
     * Fill pmc entity code
     *
     * @throws InterruptedException exception
     */
    public void fillPmcEntityCode(Partner data) throws InterruptedException {

        selenium.enterText(pmcEntityCodeTextBox, data.getPartnerPmcEntityCode(), true);
        selenium.clickOn(submitButton);
        selenium.waitTillElementsCountIsLessThan(submitButton, 1);
    }

    /**
     * Fill Address
     *
     * @throws InterruptedException exception
     */
    public void fillAddress(Partner data) throws InterruptedException {

        selenium.enterText(addressTextBox, data.getPartnerAddress(), false);
        selenium.clickOn(submitButton);
        selenium.waitTillElementsCountIsLessThan(submitButton, 1);
    }

    /**
     * Click on Partner Types by name
     *
     * @param partnerType partnerType
     * @throws InterruptedException exception
     */
    public void clickOnPartnerTypesByName(String partnerType) throws InterruptedException {

        String partnerTypesXpathSelector = "//span[contains(text(),'" + partnerType + "')]";
        selenium.clickOn(By.xpath(partnerTypesXpathSelector));
        selenium.clickOn(submitButton);
        selenium.waitTillElementsCountIsLessThan(submitButton, 1);
    }

    /**
     * @param userName
     * @throws InterruptedException
     */

    public void selectPartnerPointOfContactByName(String userName) throws InterruptedException {
        String xpathUserName = "//ul[contains(@class,'MuiAutocomplete-listbox')]/li[text()= '" + userName + "']";
        selenium.clickOn(By.xpath(xpathUserName));
    }

    /**
     * clear and Fill all partner point of contact detail and click on submit button
     *
     * @param data user
     * @throws InterruptedException exception
     */

    public void clearAndFillPartnerPointOfContact(User data) throws InterruptedException {

        selenium.clearTextBoxUsingKeys(pointOfContactTextBox);
        selenium.enterText(pointOfContactTextBox, data.getRealFirstName(), false);
        selenium.hardWait(3);
        selectPartnerPointOfContactByName(data.getRealFirstName() + " " + data.getRealLastName());
        selenium.clickOn(submitButton);
        selenium.waitTillElementsCountIsLessThan(submitButton, 1);
    }

    /**
     * Click on cancel button
     * @throws InterruptedException
     */
    public void clickOnCancelButton() throws InterruptedException {
        selenium.clickOn(cancelButton);
        selenium.waitTillElementsCountIsLessThan(submitButton, 1);
    }

    /**
     * Is Partner global innovations client error message present ?
     *
     * @return boolean
     */
    public boolean isGlobalInnovationsClientErrorMessagePresent() {
        return selenium.isElementPresent(GlobalInnovationsClientErrorMessageText);
    }

    /**
     * Is Partner active inActive error message present ?
     *
     * @return boolean
     */
    public boolean isActiveInActiveErrorMessagePresent() {
        return selenium.isElementPresent(activeInActiveErrorMessageText);
    }

    /**
     * Is Partner pmc entity code error message present ?
     *
     * @return boolean
     */
    public boolean isPmcEntityCodeErrorMessagePresent() {
        return selenium.isElementPresent(pmcEntityCodeErrorMessageText);
    }

    /**
     * Is Partner point of contact error message present ?
     *
     * @return boolean
     */
    public boolean isPointOfContactErrorMessagePresent() {
        return selenium.isElementPresent(pointOfContactErrorMessageText);
    }

}



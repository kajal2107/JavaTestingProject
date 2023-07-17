package pageobjects.cord.partners;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class PartnerDetailsPO extends BasePO {

    public PartnerDetailsPO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    @FindBy(xpath = "//h2[contains(@class,'PartnerDetail-name')]")
    private WebElement partnerName;

    @FindBy(xpath = "//button[contains(@aria-label,'Update Global Innovations Client')]")
    private WebElement editPartnerButton;

    @FindBy(xpath = "//span[contains(text(),'Inactive')]")
    private WebElement activeInactiveButton;

    @FindBy(xpath = "//span[contains(text(),'Enter PMC Entity Code')]")
    private WebElement enterEntityCodeButton;

    @FindBy(xpath = "//span[contains(text(),'PMC Entity Code:')]")
    private WebElement pmcEntityCodeButton;

    @FindBy(xpath = "//h3[contains(text(),'Partner Types')]/following-sibling::div/button")
    private WebElement addPartnerTypesButton;

    @FindBy(xpath = "//h3[contains(text(),'Address')]/following-sibling::div/button")
    private WebElement addAddressButton;

    @FindBy(xpath = "//h3[text()='Point of Contact']/following::button[contains(@class,'MuiCardActionArea-root')]")
    private WebElement addPointOfContactButton;

    @FindBy(xpath = "//div[contains(@class,'MuiAvatar-circle')]/following-sibling::h4")
    private WebElement pointOfContactText;

    private By partnerDetailsButton = By.xpath("//button[contains(@class,'outlined')]");

    /**
     * Click on edit button
     *
     * @throws InterruptedException exception
     */
    public void clickOnEditButton() throws InterruptedException {
        selenium.clickOn(editPartnerButton);
    }

    /**
     * Click on Partner Status by name
     */
    public void clickOnPartnerStatusByName(String partnerStatus) throws InterruptedException {

        String partnerStatusXpathSelector = "//span[contains(text(),'" + partnerStatus + "')]";
        selenium.clickOn(By.xpath(partnerStatusXpathSelector));
    }

    /**
     * Is Global Innovations Client present ?
     *
     * @return boolean
     */
    public boolean isGlobalInnovationsClientPresent() {
        String xpathLocator = "//span[contains(text(),'Global Innovations Client')]";
        return selenium.isElementPresent(By.xpath(xpathLocator));
    }

    /**
     * Click on entity Code button
     *
     * @throws InterruptedException exception
     */
    public void clickOnEntityCodeButton() throws InterruptedException {
        selenium.clickOn(enterEntityCodeButton);
    }

    /**
     * Click on add partner types button
     *
     * @throws InterruptedException exception
     */
    public void clickOnAddPartnerTypesButton() throws InterruptedException {
        selenium.clickOn(addPartnerTypesButton);
    }

    /**
     * Click on add address button
     *
     * @throws InterruptedException exception
     */
    public void clickOnAddAddressButton() throws InterruptedException {
        selenium.clickOn(addAddressButton);
    }

    /**
     * Click on add point  of contact  button
     *
     * @throws InterruptedException exception
     */
    public void clickOnAddPointOfContactButton() throws InterruptedException {
        selenium.clickOn(addPointOfContactButton);
    }


    /**
     * Click on partner PMC entity code button by div index
     *
     * @throws InterruptedException exception
     */
    public void clickOnPmcEntityCodeButton() throws InterruptedException {
        selenium.clickOn(selenium.waitTillAllElementsAreLocated(partnerDetailsButton).get(1));
    }

    /**
     * Get Organization name
     *
     * @return Organization name
     */
    public String getOrganizationName() {
        return partnerName.getText();
    }

    /**
     * Get partner status
     *
     * @return Partner status
     */
    public String getPartnerStatusByName(String status) {
        String xpathLocator = "//span[contains(text(),'" + status + "')]";
        return selenium.getText(By.xpath(xpathLocator));
    }

    /**
     * Get pmc Entity Code by index
     *
     * @param index index
     * @return pmcEntityCode
     */
    public String getPmcEntityCodeText(int index) {
        String pmcEntityCodeIndex = "//button[contains(@class,'outlined')]";
        String pmcCode = selenium.getText(selenium.waitTillAllElementsAreLocated(By.xpath(pmcEntityCodeIndex)).get(index));
        pmcCode = pmcCode.substring(pmcCode.lastIndexOf((" ")) + 1).trim();
        return pmcCode;
    }

    /**
     * Get partner address
     *
     * @return partner address
     */
    public String getAddressText() {
        return selenium.getText(addAddressButton);
    }

    /**
     * Get partner types
     *
     * @return partner types
     */
    public String getTypesText() {
        return selenium.getText(addPartnerTypesButton);
    }

    /**
     * Get point of contact text
     *
     * @return point of contact text
     */
    public String getPointOfContactText() {
        return selenium.getText(pointOfContactText);
    }

    /**
     * Is Partner edit icon present ?
     *
     * @return boolean
     */
    public boolean isEditIconPresent() {
        return selenium.isElementPresent(editPartnerButton);
    }

    /**
     * Is Inactive button enabled ?
     *
     * @return boolean
     */
    public boolean isInActiveButtonEnabled() {
        return selenium.isElementEnabled(activeInactiveButton);
    }

    /**
     * Is Enter pmc entity code button enabled ?
     *
     * @return boolean
     */
    public boolean isEnterPmcEntityCodeButtonEnabled() {
        return selenium.isElementEnabled(pmcEntityCodeButton);
    }

    /**
     * Is Partner types edit button enabled ?
     *
     * @return boolean
     */
    public boolean isPartnerTypesEditButtonEnabled() {
        return selenium.isElementEnabled(addPartnerTypesButton);
    }

    /**
     * Is Partner address edit button enabled ?
     *
     * @return boolean
     */
    public boolean isPartnerAddressEditButtonEnabled() {
        return selenium.isElementEnabled(addAddressButton);
    }

    /**
     * Is Partner point of contact edit button enabled ?
     *
     * @return boolean
     */
    public boolean isPartnerPointOfContactButtonEnabled() {
        return selenium.isElementEnabled(addPointOfContactButton);

    }

}


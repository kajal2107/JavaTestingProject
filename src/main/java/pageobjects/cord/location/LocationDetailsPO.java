package pageobjects.cord.location;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class LocationDetailsPO extends BasePO {

    public LocationDetailsPO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    @FindBy(xpath = "//h2[contains(@class,'MuiTypography-h2')]")
    private WebElement locationName;

    @FindBy(xpath = "//button[contains(@aria-label,'edit location')]")
    private WebElement editLocationButton;

    @FindBy(xpath = "//p[contains(text(),'Type')]/following-sibling::span")
    private WebElement locationTypeText;

    @FindBy(xpath = "//p[contains(text(),'ISO Alpha-3 Country Code')]/following-sibling::span")
    private WebElement locationCountryCodeText;

    @FindBy(xpath = "//p[contains(text(),'Funding Account')]/following-sibling::span")
    private WebElement locationFundingAccText;

    /**
     * Click on edit button
     *
     * @throws InterruptedException exception
     */
    public void clickOnEditButton() throws InterruptedException {
        selenium.clickOn(editLocationButton);
    }

    /**
     * Get locationName name
     *
     * @return LocationName name
     */
    public String getLocationName() {
        return selenium.getText(locationName);
    }

    /**
     * Get location type
     *
     * @return Location type
     */
    public String getLocationType() {
        return selenium.getText(locationTypeText);
    }

    /**
     * Get location country code
     *
     * @return Location country code
     */
    public String getLocationCountryCode() {
        return selenium.getText(locationCountryCodeText);
    }

    /**
     * Get location funding account text
     *
     * @return Location Funding Account
     */
    public String getLocationFundingAccountText() {
        return selenium.getText(locationFundingAccText);
    }

    /**
     * Is location edit button present
     *
     * @return boolean
     */
    public boolean isLocationEditButtonPresent() {
        String xpathLocator = "//button[contains(@aria-label,'edit location')]";
        return selenium.isElementPresent(By.xpath(xpathLocator));
    }
}


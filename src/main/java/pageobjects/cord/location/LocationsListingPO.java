package pageobjects.cord.location;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class LocationsListingPO extends BasePO {

    public LocationsListingPO(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//span[text()='View Location']")
    private WebElement viewLocationButton;

    /**
     * Is location  present ?
     *
     * @param locationName location name
     * @return true or false
     */
    public boolean isLocationPresent(String locationName) {
        String xpathLocator = "//p[contains(text(),'" + locationName + "')]";
        return selenium.isElementPresent(By.xpath(xpathLocator));
    }

    /**
     * click On view location Button
     *
     * @param locationName location Name
     * @throws InterruptedException exception
     */
    public void clickOnViewLocationButton(String locationName) throws InterruptedException {
        String viewLocation = "//div[contains(@class,'MuiCardContent-root')]/h4[text()='" + locationName + "']/ancestor::a[contains(@class,'MuiButtonBase-root')]/following-sibling::div[contains(@class,'MuiCardActions-root')]//span[text()='View Location']";
        selenium.clickOn(By.xpath(viewLocation));
    }

    /**
     * Click on first view location button
     * @throws InterruptedException
     */
    public void clickOnFirstViewLocationButton() throws InterruptedException {
        selenium.clickOn(viewLocationButton);
    }


}


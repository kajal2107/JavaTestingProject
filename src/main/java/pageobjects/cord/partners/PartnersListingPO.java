package pageobjects.cord.partners;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

import java.util.ArrayList;
import java.util.List;

public class PartnersListingPO extends BasePO {

    public PartnersListingPO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    @FindBy(className = "MuiButton-label")
    private WebElement sortOptionButton;

    @FindBy(xpath = "//span[text()='Z-A']")
    private WebElement ZToASortOption;

    @FindBy(xpath = "//span[text()='A-Z']")
    private WebElement AToZSortOption;

    @FindBy(xpath = "//p[contains(@class,'MuiTypography-h3')]")
    private WebElement partnerCountText;


    private final By partnerName = By.xpath("//div[contains(@class,'MuiGrid-item')]//h4");


    public void clickOnPartnerName(String partnerName) throws InterruptedException {

        String partnerXpathSelector =  "//h4[text()='" + partnerName + "']";
        selenium.clickOn(By.xpath(partnerXpathSelector));
    }


    /**
     * Is organization present ?
     *
     * @param partnerName partner name
     * @return boolean
     */
    public boolean isPartnerPresent(String partnerName) {
        String xpathLocator = "//h4[text()='" + partnerName + "']";
        return selenium.isElementPresent(By.xpath(xpathLocator));
    }

    /**
     * Click on sort option button from partners menu
     *
     * @throws InterruptedException exception
     */
    public void clickOnSortOption() throws InterruptedException {
        selenium.clickOn(sortOptionButton);
    }

    /**
     * Click on Z-A sort Option of partners menu
     *
     * @throws InterruptedException exception
     */
    public void clickOnZToASortOption() throws InterruptedException {
        selenium.clickOn(ZToASortOption);
    }

    /**
     * Click on A-Z sort Option of partners menu
     *
     * @throws InterruptedException exception
     */
    public void clickOnAToZSortOption() throws InterruptedException {
        selenium.clickOn(AToZSortOption);
    }

    /**
     * Get all partners info
     *
     * @return List of partners
     */
    public List<String> getPartnersList() {
        List<WebElement> allPartnersName = selenium.waitTillAllElementsAreLocated(partnerName);
        List<String> allPartners = new ArrayList<>();
        for (WebElement e : allPartnersName) {
            allPartners.add(selenium.getText(e));
        }
        return allPartners;
    }

    /**
     * Get Partner count
     *
     * @return Partner count
     */
    public String getPartnerCount() {
        return partnerCountText.getText();
    }
}


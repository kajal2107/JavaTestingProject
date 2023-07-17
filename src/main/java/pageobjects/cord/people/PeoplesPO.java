package pageobjects.cord.people;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class PeoplesPO extends BasePO {

    public PeoplesPO(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//p[contains(@class,'MuiTypography-h4')]")
    private WebElement peopleName;

    /**
     * Is people  present ?
     *
     * @param peopleName people name
     * @return status
     */
    public boolean isPeoplePresent(String peopleName) {
        String xpathLocator = "//p[contains(text(),'" + peopleName + "')]";
        return selenium.isElementPresent(By.xpath(xpathLocator));
    }

    /**
     * click on People by name
     */
    public void clickOnPeopleName(String peopleName) throws InterruptedException {
        String xpathLocator = "//p[contains(text(),'" + peopleName + "')]";
        selenium.clickOn(By.xpath(xpathLocator));
    }

    /**
     * click on people name
     *
     * @throws InterruptedException Interrupted Exception
     */
    public void clickOnPeopleName() throws InterruptedException {
        selenium.clickOn(peopleName);
    }

}


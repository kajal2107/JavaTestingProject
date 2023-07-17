package pageobjects.cord.common;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class HeaderPO extends BasePO {

    public HeaderPO(WebDriver driver) {
        super(driver);
    }


    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    @FindBy(name = "search")
    private WebElement searchTextBox;

    @FindBy(css = "form ~ div > p")
    private WebElement profileName;

    @FindBy(css = "button[aria-controls='profile-menu']")
    private WebElement profileIcon;

    @FindBy(css = "a[href='/logout']")
    private WebElement signOutButton;

    @FindBy(xpath = "//li[contains(text(),'Change Password')]")
    private WebElement changePasswordButton;

    /**
     * Get profile name
     *
     * @return name
     */
    public String getProfileName() {
        return selenium.getText(profileName);
    }


    /**
     * Click on Profile Icon and click on Sign out button
     *
     * @throws InterruptedException InterruptedException
     */
    public void signOut() throws InterruptedException {
        selenium.clickOn(profileIcon);
        selenium.clickOn(signOutButton);
        selenium.hardWait(3);
    }

    /**
     * Click on Profile Icon and click on Change password button
     *
     * @throws InterruptedException InterruptedException
     */
    public void clickOnChangePasswordButton() throws InterruptedException {
        selenium.clickOn(profileIcon);
        selenium.clickOn(changePasswordButton);
        selenium.hardWait(3);
    }

    /**
     * if created module is not within listing then we will search globally by exact name
     *
     * @param search search string entered in search bar
     * @throws InterruptedException InterruptedException
     */
    public void searchItemGloballyByName(String search) throws InterruptedException {
        selenium.clickOn(searchTextBox);
        selenium.enterText(searchTextBox, search, false);
        driver.findElement(By.name("search")).sendKeys(Keys.ENTER);
    }
}


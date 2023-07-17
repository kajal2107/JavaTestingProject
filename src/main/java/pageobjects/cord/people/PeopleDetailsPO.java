package pageobjects.cord.people;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;


public class PeopleDetailsPO extends BasePO {

    public PeopleDetailsPO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    @FindBy(xpath = "//h2[contains(@class,'MuiTypography-h2')]")
    private WebElement peopleNameText;

    @FindBy(xpath = "//button[contains(@aria-label,'edit person')]")
    private WebElement editPersonButton;

    @FindBy(xpath = "//p[contains(text(),'Email')]/following-sibling::span")
    private WebElement emailText;

    @FindBy(xpath = "//p[contains(text(),'Title')]/following-sibling::span")
    private WebElement titleText;

    @FindBy(xpath = "//p[contains(text(),'Roles')]/following-sibling::span")
    private WebElement rolesText;

    @FindBy(xpath = "//p[contains(text(),'Phone')]/following-sibling::span")
    private WebElement phoneText;

    @FindBy(xpath = "//p[contains(text(),'About')]/following-sibling::span")
    private WebElement aboutText;

    @FindBy(xpath = "//span[contains(@class,'MuiSkeleton-root')]")
    private WebElement nullValueText;

    @FindBy(xpath = "//div[contains(@role,'tooltip')]")
    private WebElement permissionToolTip;

    /**
     * Get People name
     *
     * @return People name
     */
    public String getPeopleName() {
        return selenium.getText(peopleNameText);
    }

    /**
     * Get email
     *
     * @return email
     */
    public String getEmail() {
        return selenium.getText(emailText);
    }

    /**
     * Get title
     *
     * @return title
     */
    public String getTitle() {
        return selenium.getText(titleText);
    }

    /**
     * Get phone
     *
     * @return phone
     */
    public String getPhoneNumber() {
        return selenium.getText(phoneText);
    }

    /**
     * Get about
     *
     * @return about
     */
    public String getAbout() {
        return selenium.getText(aboutText);
    }

    /**
     * Get roles
     *
     * @return about
     */
    public String getRoles() {
        return selenium.getText(rolesText);
    }

    /**
     * Get permission tool tip text
     *
     * @return permission tool tip text
     */
    public String getPermissionToolTipText() {
        return selenium.getText(permissionToolTip);
    }

    /**
     * Click on edit button
     *
     * @throws InterruptedException exception
     */
    public void clickOnEditButton() throws InterruptedException {
        selenium.clickOn(editPersonButton);
    }

    /**
     * click on Null Text
     *
     * @throws InterruptedException exception
     */
    public void clickOnNullText() throws InterruptedException {
        selenium.clickOn(nullValueText);
    }

    /**
     * Is people first name last name present
     */
    public boolean IsPeopleFirstNameLastNamePresent() {
        return selenium.isElementPresent(peopleNameText);
    }
}

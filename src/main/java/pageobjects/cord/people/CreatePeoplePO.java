package pageobjects.cord.people;

import dataobjects.People;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class CreatePeoplePO extends BasePO {

    public CreatePeoplePO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    @FindBy(name = "person.realFirstName")
    private WebElement personNameTextBox;

    @FindBy(name = "person.realLastName")
    private WebElement personRealLastNameTextBox;

    @FindBy(name = "person.email")
    private WebElement personEmailTextBox;

    @FindBy(name = "person.title")
    private WebElement personTitleTextBox;

    @FindBy(name = "person.phone")
    private WebElement personPhoneTextBox;

    @FindBy(name = "person.about")
    private WebElement personAboutTextBox;

    @FindBy(xpath = "//label[text()='First Name']/following-sibling::p")
    private WebElement FirstNameMandatoryFieldText;

    @FindBy(xpath = "//label[text()='Last Name']/following-sibling::p")
    private WebElement lastNameMandatoryFieldText;

    @FindBy(xpath = "//label[text()='Public First Name']/following-sibling::p")
    private WebElement publicFirstNameMandatoryFieldText;

    @FindBy(xpath = "//label[text()='Public Last Name']/following-sibling::p")
    private WebElement publicLastNameMandatoryFieldText;

    @FindBy(xpath = "//div[contains(@class,'MuiAutocomplete-input')]")
    private WebElement roleDropdown;

    private final By submitButton = By.xpath("//span[text()='Submit']");

    /**
     * Fill Project detail and click on submit button
     *
     * @param data Project object
     */
    public void clearAndFillPersonDetail(People data) {

        selenium.clearTextBoxUsingKeys(personNameTextBox);
        selenium.enterText(personNameTextBox, data.getPersonName(), false);

        selenium.clearTextBoxUsingKeys(personRealLastNameTextBox);
        selenium.enterText(personRealLastNameTextBox, data.getPersonRealLastName(), false);

        selenium.clearTextBoxUsingKeys(personEmailTextBox);
        selenium.enterText(personEmailTextBox, data.getPersonEmail(), false);

        selenium.clearTextBoxUsingKeys(personTitleTextBox);
        selenium.enterText(personTitleTextBox, data.getPersonTitle(), false);

        selenium.clearTextBoxUsingKeys(personPhoneTextBox);
        selenium.enterText(personPhoneTextBox, data.getPersonPhone(), false);

        selenium.clearTextBoxUsingKeys(personAboutTextBox);
        selenium.enterText(personAboutTextBox, data.getPersonAbout(), false);

    }

    /**
     * Select UserRoles by type name
     *
     * @param UserRoles type name
     * @throws InterruptedException exception
     */
    public void selectUserRole(String[] UserRoles) throws InterruptedException {
        selenium.clickOn(roleDropdown);
        for (String userRole : UserRoles) {
            String userTypeXpathSelector = "//li[text()='" + userRole + "']";
            selenium.clickOn(By.xpath(userTypeXpathSelector));
        }
    }

    /**
     * Click on Submit button
     *
     * @throws InterruptedException exception
     */
    public void clickOnSubmitButton() throws InterruptedException {
        selenium.clickOn(submitButton);
        selenium.waitTillElementsCountIsLessThan(submitButton, 5);
    }

    /**
     * Get first name field validation message
     *
     * @return validation message
     */
    public String getFirstNameMandatoryFieldText() {
        return selenium.getText(FirstNameMandatoryFieldText);
    }

    /**
     * Get last name field validation message
     *
     * @return validation message
     */
    public String getLastNameMandatoryFieldText() {
        return selenium.getText(lastNameMandatoryFieldText);
    }


    /**
     * Get public first name field validation message
     *
     * @return validation message
     */
    public String getPublicFirstNameMandatoryFieldText() {
        return selenium.getText(publicFirstNameMandatoryFieldText);
    }

    /**
     * Get public last name field validation message
     *
     * @return validation message
     */
    public String getPublicLastNameMandatoryFieldText() {
        return selenium.getText(publicLastNameMandatoryFieldText);
    }

}
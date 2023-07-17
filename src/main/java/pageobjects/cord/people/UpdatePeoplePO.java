package pageobjects.cord.people;

import dataobjects.People;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class UpdatePeoplePO extends BasePO {

    public UpdatePeoplePO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    @FindBy(name = "user.realFirstName")
    private WebElement userNameTextBox;

    @FindBy(name = "user.realLastName")
    private WebElement userRealLastNameTextBox;

    @FindBy(name = "user.email")
    private WebElement userEmailTextBox;

    @FindBy(name = "user.title")
    private WebElement userTitleTextBox;

    @FindBy(name = "user.phone")
    private WebElement userPhoneTextBox;

    @FindBy(name = "user.about")
    private WebElement userAboutTextBox;

    private final By submitButton = By.xpath("//span[text()='Submit']");

    /**
     * Fill People detail and click on submit button
     *
     * @param data People object
     */
    public void clearAndUpdatePersonDetail(People data) {

        selenium.clearTextBoxUsingKeys(userNameTextBox);
        selenium.enterText(userNameTextBox, data.getPersonName(), false);

        selenium.clearTextBoxUsingKeys(userRealLastNameTextBox);
        selenium.enterText(userRealLastNameTextBox, data.getPersonRealLastName(), false);

        selenium.clearTextBoxUsingKeys(userEmailTextBox);
        selenium.enterText(userEmailTextBox, data.getPersonEmail(), false);

        selenium.clearTextBoxUsingKeys(userTitleTextBox);
        selenium.enterText(userTitleTextBox, data.getPersonTitle(), false);

        selenium.clearTextBoxUsingKeys(userPhoneTextBox);
        selenium.enterText(userPhoneTextBox, data.getPersonPhone(), false);

        selenium.clearTextBoxUsingKeys(userAboutTextBox);
        selenium.enterText(userAboutTextBox, data.getPersonAbout(), false);

    }

    /**
     * Fill firstname and last name detail and click on submit button
     *
     * @param peopleFirstName people first name
     * @param peopleLastName people last name
     */
    public void fillFirstnameLastNameAndEmailDetail(String peopleFirstName, String peopleLastName,String peopleEmail) {

        selenium.clearTextBoxUsingKeys(userNameTextBox);
        selenium.enterText(userNameTextBox, peopleFirstName, false);

        selenium.clearTextBoxUsingKeys(userRealLastNameTextBox);
        selenium.enterText(userRealLastNameTextBox, peopleLastName, false);

        selenium.clearTextBoxUsingKeys(userEmailTextBox);
        selenium.enterText(userEmailTextBox, peopleEmail, false);
    }

    /**
     * Click on Submit button
     *
     * @throws InterruptedException exception
     */
    public void clickOnSubmitButton() throws InterruptedException {
        selenium.clickOn(submitButton);
        selenium.waitTillElementsCountIsLessThan(submitButton, 1);
    }

}
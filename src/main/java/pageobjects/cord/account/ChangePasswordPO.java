package pageobjects.cord.account;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class ChangePasswordPO extends BasePO {

    public ChangePasswordPO(WebDriver driver) {
        super(driver);
    }


    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    @FindBy(name = "oldPassword")
    private WebElement oldPasswordTextBox;

    @FindBy(name = "newPassword")
    private WebElement newPasswordTextbox;

    @FindBy(name = "confirmPassword")
    private WebElement confirmPasswordTextbox;

    private By submitButton = By.xpath("//span[contains(text(),\"Submit\")]");

    /**
     * @param password    current password
     * @param newPassword new password
     */
    public void fillPasswordDetailAndClickOnSubmitButton(String password, String newPassword) throws InterruptedException {
        selenium.enterText(oldPasswordTextBox, password, false);
        selenium.enterText(newPasswordTextbox, newPassword, false);
        selenium.enterText(confirmPasswordTextbox, newPassword, false);
        selenium.clickOn(submitButton);
    }
}


package pageobjects.cord.partners;

import dataobjects.Partner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class CreateUpdatePartnerPO extends BasePO {

    public CreateUpdatePartnerPO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    @FindBy(xpath = "//input[contains(@placeholder,'Enter organization name')]")
    private WebElement organizationName;

    @FindBy(xpath = "//div[contains(@class,'MuiAutocomplete-popper')]")
    private WebElement addOrganizationName;

    @FindBy(xpath = "//span[text()='Submit']")
    private WebElement submitButton;

    private By cancelButton = By.xpath("//span[text()='Cancel']");

    /**
     * Fill organization detail and click on submit button
     *
     * @param data organization object
     */
    public void fillPartnerDetail(Partner data) throws InterruptedException {
        selenium.clearTextBoxUsingKeys(organizationName);
        selenium.enterText(organizationName, data.getPartnerName(), false);
        selenium.hardWait(3);
        selenium.clickOn(addOrganizationName);
        selenium.doubleClickOnElement(submitButton);
        selenium.hardWait(3);
        selenium.doubleClickOnElement(submitButton);
    }

}


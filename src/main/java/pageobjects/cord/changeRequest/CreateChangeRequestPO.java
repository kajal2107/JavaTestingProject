package pageobjects.cord.changeRequest;

import dataobjects.ChangeRequest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;


public class CreateChangeRequestPO extends BasePO {

    public CreateChangeRequestPO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    @FindBy(name = "projectChangeRequest.summary")
    private WebElement summaryTextBox;

    @FindBy(xpath = "//label[text()='Types']/following::input")
    private WebElement typesTextBox;

    private final By submitButton = By.xpath("//span[text()='Submit']");

    /**
     * Fill create change request details
     *
     * @param typesName types Name
     * @param data      ChangeRequest data
     * @throws InterruptedException exception
     */
    public void fillChangeRequestDetails(String typesName, ChangeRequest data) throws InterruptedException {
        selenium.click(typesTextBox);
        String changeRequestTypesDropdownXpathSelector = "//ul[contains(@class,'MuiAutocomplete-listbox')]/li[text()= '" + typesName + "']";
        selenium.waitTillElementIsClickable(By.xpath(changeRequestTypesDropdownXpathSelector));
        selenium.clickOn(By.xpath(changeRequestTypesDropdownXpathSelector));
        selenium.enterText(summaryTextBox, data.getSummaryText(), true);
        selenium.clickOn(submitButton);
        selenium.waitTillElementsCountIsLessThan(submitButton, 1);
    }
}


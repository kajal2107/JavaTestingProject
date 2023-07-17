package pageobjects.cord.projects;

import dataobjects.Language;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class ProjectDetailsCreateLanguageEngagementPO extends BasePO {

    public ProjectDetailsCreateLanguageEngagementPO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */
    @FindBy(xpath = "//input[contains(@placeholder,'Search for a language by name')]")
    private WebElement searchLanguageTextBox;

    @FindBy(xpath = "//ul[contains(@class,'MuiAutocomplete-listbox')]/li[1]")
    private WebElement addLanguageName;

    @FindBy(xpath = "//div[contains(@class,'noOptions')]")
    private WebElement searchedLanguageEngagementText;

    @FindBy(xpath = "//div[(@class='MuiDialogContent-root')]/p")
    private WebElement languageEngagementErrorMessage;

    private By submitButton = By.xpath("//span[text()='Submit']");

    private By cancelButton = By.xpath("//span[text()='Cancel']");

    /**
     * Fill language engagement detail and click on submit button
     *
     * @param data language object
     * @throws InterruptedException exception
     */

    public void fillLanguageEngagementDetailAndClickOnSubmitButton(Language data) throws InterruptedException {
        selenium.clearTextBoxUsingKeys(searchLanguageTextBox);
        selenium.enterText(searchLanguageTextBox, data.getLanguageEngagementName(), false);
        data.setLanguageEngagementName(addLanguageName.getText());
        selenium.clickOn(addLanguageName);
        selenium.hardWait(5);
        selenium.clickOn(submitButton);
        selenium.waitTillElementsCountIsLessThan(submitButton, 1);
    }

    /**
     * Search language engagement name
     *
     * @param data language object
     * @throws InterruptedException exception
     */

    public void searchLanguageEngagementName(Language data) throws InterruptedException {
        selenium.clearTextBoxUsingKeys(searchLanguageTextBox);
        selenium.enterText(searchLanguageTextBox, data.getLanguageEngagementName(), false);
    }

    /**
     * Get Language engagement error message
     *
     * @return Language engagement error message
     */
    public String getLanguageEngagementErrorMessage() {
        return selenium.getText(languageEngagementErrorMessage);
    }

    /**
     * get searched language name text
     *
     * @return searched language name text
     */
    public String getSearchedLanguageEngagementText() {
        return selenium.getText(searchedLanguageEngagementText);
    }

}


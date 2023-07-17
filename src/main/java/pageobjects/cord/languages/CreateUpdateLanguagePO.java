package pageobjects.cord.languages;

import dataobjects.Language;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class CreateUpdateLanguagePO extends BasePO {

    public CreateUpdateLanguagePO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    @FindBy(id = "dialog-form")
    private WebElement createLanguageHeaderNameText;

    @FindBy(name = "language.name")
    private WebElement nameTextBox;

    @FindBy(name = "language.displayName")
    private WebElement displayNameTextBox;

    @FindBy(name = "language.ethnologue.name")
    private WebElement ethnologueNameTextBox;

    @FindBy(name = "language.ethnologue.code")
    private WebElement ethnologueCodeTextBox;

    @FindBy(name = "language.ethnologue.provisionalCode")
    private WebElement ethnologueProvisionalCodeTextBox;

    @FindBy(name = "language.displayNamePronunciation")
    private WebElement displayNamePronunciationTextBox;

    @FindBy(name = "language.ethnologue.population")
    private WebElement ethnologuePopulationTextBox;

    @FindBy(name = "language.registryOfDialectsCode")
    private WebElement registryOfDialectsCodeTextBox;

    @FindBy(name = "language.populationOverride")
    private WebElement populationOverrideTextBox;

    @FindBy(name = "language.isDialect")
    private WebElement isDialectCheckBox;

    @FindBy(name = "language.isSignLanguage")
    private WebElement isSignLanguageCheckBox;

    @FindBy(name = "language.signLanguageCode")
    private WebElement signLanguageCodeTextBox;

    @FindBy(name = "language.hasExternalFirstScripture")
    private WebElement hasExternalFirstScriptureCheckBox;

    @FindBy(id = "mui-component-select-language.sensitivity")
    private WebElement sensitivityDropdown;

    @FindBy(xpath = "//li[contains(@data-value,'Low')]")
    private WebElement sensitivityLow;

    @FindBy(xpath = "//li[contains(@data-value,'Medium')]")
    private WebElement sensitivityMedium;

    @FindBy(xpath = "//li[contains(@data-value,'High')]")
    private WebElement sensitivityHigh;

    @FindBy(name = "language.sponsorEstimatedEndFY")
    private WebElement sponsorEstimatedEndFYTextBox;

    @FindBy(name = "language.leastOfThese")
    private WebElement leastOfTheseCheckBox;

    @FindBy(name = "language.leastOfTheseReason")
    private WebElement leastOfTheseReasonTextBox;

    private By submitButton = By.xpath("//span[contains(text(),'Submit')]");

    @FindBy(xpath = "//label[text()='Name']/following-sibling::p")
    private WebElement nameMandatoryFieldText;

    @FindBy(xpath = "//label[text()='Public Name']/following-sibling::p")
    private WebElement publicNameMandatoryFieldText;

    /**
     * Click on Submit button
     *
     * @throws InterruptedException exception
     */
    public void clickOnSubmitButton() throws InterruptedException {
        selenium.clickOn(submitButton);
    }

    /**
     * Get name field validation message
     *
     * @return validation message
     */
    public String getNameMandatoryFieldText() {
        return selenium.getText(nameMandatoryFieldText);
    }

    /**
     * Get display name field validation message
     *
     * @return validation message
     */
    public String getPublicNameMandatoryFieldText() {
        return selenium.getText(publicNameMandatoryFieldText);
    }

    /**
     * clear and Fill all language detail and click on submit button
     *
     * @param data language object
     * @throws InterruptedException exception
     */
    public void clearAndFillLanguageDetails(Language data) throws InterruptedException {

        selenium.clearTextBoxUsingKeys(nameTextBox);
        selenium.enterText(nameTextBox, data.getLanguageName(), false);

        selenium.clearTextBoxUsingKeys(displayNameTextBox);
        selenium.enterText(displayNameTextBox, data.getPubliicName(), false);

        selenium.clearTextBoxUsingKeys(ethnologueNameTextBox);
        selenium.enterText(ethnologueNameTextBox, data.getEthnologueName(), false);

        selenium.clearTextBoxUsingKeys(ethnologueCodeTextBox);
        selenium.enterText(ethnologueCodeTextBox, data.getEthnologueCode(), false);

        selenium.clearTextBoxUsingKeys(ethnologueProvisionalCodeTextBox);
        selenium.enterText(ethnologueProvisionalCodeTextBox, data.getProvisionalCode(), false);

        selenium.clearTextBoxUsingKeys(displayNamePronunciationTextBox);
        selenium.enterText(displayNamePronunciationTextBox, data.getPronunciationGuide(), false);

        selenium.clearTextBoxUsingKeys(ethnologuePopulationTextBox);
        selenium.enterText(ethnologuePopulationTextBox, data.getEthnologuePopulation(), false);

        selenium.clearTextBoxUsingKeys(registryOfDialectsCodeTextBox);
        selenium.enterText(registryOfDialectsCodeTextBox, data.getRegistryOfDialectsCode(), false);

        selenium.clearTextBoxUsingKeys(populationOverrideTextBox);
        selenium.enterText(populationOverrideTextBox, data.getPopulation(), false);

        if (data.isLeastOfThese()) {
            if (this.getFormName().contains("Create")) {
                selenium.click(leastOfTheseCheckBox);
                selenium.clearTextBoxUsingKeys(leastOfTheseReasonTextBox);
                selenium.enterText(leastOfTheseReasonTextBox, data.getReasoning(), false);
            } else {
                selenium.click(leastOfTheseCheckBox);
                data.setLeastOfThese(false);
            }
        } else {
            data.setLeastOfThese(true);
        }

        if (data.isSignLanguage()) {
            if (this.getFormName().contains("Create")) {

                selenium.click(isSignLanguageCheckBox);
                selenium.clearTextBoxUsingKeys(signLanguageCodeTextBox);
                selenium.enterText(signLanguageCodeTextBox, data.getSignLanguageCode(), false);

            } else {
                selenium.click(isSignLanguageCheckBox);
                data.setSignLanguage(false);
            }
        } else {
            selenium.click(isSignLanguageCheckBox);
            data.setSignLanguage(true);
        }

        selenium.click(isDialectCheckBox);
        selenium.click(hasExternalFirstScriptureCheckBox);

        selenium.click(sensitivityDropdown);
        selenium.click(sensitivityLow);

        selenium.clearTextBoxUsingKeys(sponsorEstimatedEndFYTextBox);
        selenium.enterText(sponsorEstimatedEndFYTextBox, data.getSponsorEstimatedEndFY(), false);

        selenium.clickOn(submitButton);
        selenium.waitTillElementsCountIsLessThan(submitButton, 1);
    }

    /**
     * update first scripture
     *
     * @throws InterruptedException exception
     */
    public void updateFirstScripture() throws InterruptedException {
        selenium.click(hasExternalFirstScriptureCheckBox);
        selenium.clickOn(submitButton);
        selenium.waitTillElementsCountIsLessThan(submitButton, 1);
        selenium.back();
    }

    /**
     * get dialog name ? create : update
     */
    public String getFormName() {

        return selenium.getText(createLanguageHeaderNameText);
    }
}

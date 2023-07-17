package pageobjects.cord.languages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class LanguageDetailPO extends BasePO {

    public LanguageDetailPO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    @FindBy(xpath = "//span[(text()='Least of These')]")
    private WebElement languageLeastOfTheseTextbox;

    @FindBy(xpath = "//div[contains(@class,'MuiTooltip')]")
    private WebElement languageLeastOfTheseTooltip;

    @FindBy(xpath = "//h2[contains(@class,'MuiTypography-root')]")
    private WebElement languageNameText;

    @FindBy(xpath = "//button[contains(@aria-label,'edit language')]")
    private WebElement editLanguageButton;

    @FindBy(xpath = "//div[contains(@class,'sizeSmall')]")
    private WebElement sensitivityButton;

    @FindBy(xpath = "//span[contains(text(),'Dialect')]")
    private WebElement dialectButton;

    @FindBy(xpath = "//span[contains(text(),'Sign Language')]")
    private WebElement signLanguageButton;

    @FindBy(xpath = "//p[contains(text(),'Pronunciation Guide')]/following-sibling::span")
    private WebElement pronunciationGuideText;

    @FindBy(xpath = "//p[contains(text(),'Ethnologue Name')]/following-sibling::span")
    private WebElement ethnologueNameText;

    @FindBy(xpath = "//p[contains(text(),'Sign Language Code')]/following-sibling::span")
    private WebElement signLanguageCodeText;

    @FindBy(xpath = "//p[contains(text(),'Provisional Code')]/following-sibling::span")
    private WebElement provisionalCodeText;

    @FindBy(xpath = "//p[contains(text(),'Registry of Dialects Code')]/following-sibling::span")
    private WebElement registryOfDialectsCodeText;

    @FindBy(xpath = "//p[contains(text(),'Population')]/following-sibling::span")
    private WebElement populationText;

    @FindBy(xpath = "//p[contains(text(),'End Fiscal Year')]/following-sibling::span")
    private WebElement endFiscalYearText;

    @FindBy(xpath = "//div[contains(@class,'MuiGrid-item')]/div/p")
    private WebElement hasExternalFirstScriptureUncheckedText;

    @FindBy(xpath = "//button[contains(@aria-label,'add location')]")
    private WebElement addLocationsButton;


    /**
     * Get Language name
     *
     * @return Language name
     */
    public String getLanguageName() {
        return selenium.getText(languageNameText);
    }

    /**
     * Click on edit button
     *
     * @throws InterruptedException exception
     */
    public void clickOnEditButton() throws InterruptedException {
        selenium.clickOn(editLanguageButton);
    }

    /**
     * Get Sensitivity
     *
     * @return Sensitivity
     */
    public String getSensitivity() {
        return selenium.getText(sensitivityButton);
    }

    /**
     * Is edit icon present ?
     *
     * @return boolean
     */
    public boolean isEditIconPresent() {
        return selenium.isElementPresent(editLanguageButton);
    }

    /**
     * Is Dialect present ?
     *
     * @return boolean
     */
    public boolean isDialectPresent() {
        String xpathLocator = "//span[contains(text(),'Dialect')]";
        return selenium.isElementPresent(By.xpath(xpathLocator));
    }

    /**
     * Is LeastOfThesePresent  present ?
     *
     * @return boolean
     */
    public boolean isLeastOfThesePresent() {
        String xpathLocator = "//span[(text()='Least of These')]";
        return selenium.isElementPresent(By.xpath(xpathLocator));
    }

    /**
     * Is SignLanguage  present ?
     *
     * @return boolean
     */
    public boolean isSignLanguagePresent() {
        String xpathLocator = "//span[(text()='Sign Language')]";
        return selenium.isElementPresent(By.xpath(xpathLocator));
    }

    /**
     * Click on language least of these button
     *
     * @throws InterruptedException exception
     */
    public String clickAndGetLanguageLeastOfThese() throws InterruptedException {
        selenium.clickOn(languageLeastOfTheseTextbox);
        selenium.hardWait(3);
        return selenium.getText(languageLeastOfTheseTooltip);
    }

    /**
     * Get Language Pronunciation Guide
     *
     * @return Pronunciation Guide
     */
    public String getPronunciationGuide() {
        return selenium.getText(pronunciationGuideText);
    }

    /**
     * Get Language Ethnologue Name
     *
     * @return Ethnologue Name
     */
    public String getEthnologueName() {
        return selenium.getText(ethnologueNameText);
    }

    /**
     * Get Language Sign Language Code
     *
     * @return Sign Language Code
     */

    public String getSignLanguageCode() {
        return selenium.getText(signLanguageCodeText);
    }

    /**
     * Get Language Provisional Code
     *
     * @return Provisional Code
     */

    public String getProvisionalCode() {
        return selenium.getText(provisionalCodeText);
    }

    /**
     * Get Language Registry of Dialects Code
     *
     * @return Registry of Dialects Code
     */

    public String getRegistryOfDialectsCodeText() {
        return selenium.getText(registryOfDialectsCodeText);
    }


    /**
     * Get has external FirstScripture
     *
     * @return has external FirstScripture
     */

    public String getHasExternalFirstScriptureText() {
        return selenium.getText(hasExternalFirstScriptureUncheckedText);
    }


    /**
     * Get Language Population
     *
     * @return Population
     */
    public String getPopulationText() {
        return selenium.getText(populationText);
    }

    /**
     * Get Language End Fiscal Year
     *
     * @return End Fiscal Year
     */

    public String getEndFiscalYear() {
        return selenium.getText(endFiscalYearText);
    }

    /**
     * Click on locations button
     *
     * @throws InterruptedException exception
     */
    public void clickOnLocationsButton() throws InterruptedException {
        selenium.clickOn(addLocationsButton);
    }

}

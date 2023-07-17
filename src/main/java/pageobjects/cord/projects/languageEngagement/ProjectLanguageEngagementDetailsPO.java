package pageobjects.cord.projects.languageEngagement;

import dataobjects.Internship;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

import java.io.File;

public class ProjectLanguageEngagementDetailsPO extends BasePO {

    public ProjectLanguageEngagementDetailsPO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    @FindBy(name = "engagement.completeDate")
    private WebElement translationCompleteDateTextBox;

    @FindBy(name = "engagement.disbursementCompleteDate")
    private WebElement disbursementCompleteDateTextBox;

    @FindBy(name = "defined_file_version_uploader")
    private WebElement addPlanningAndProgressButton;

    @FindBy(css = "#draggable-dialog-title~div div:last-child p")
    private WebElement uploadedFileText;

    @FindBy(name = "planned")
    private WebElement dedicationToggleButton;

    @FindBy(xpath = "//a[contains(@class,'MuiTypography-h2 MuiTypography')]")
    private WebElement LanguageEngagementNameLink;

    @FindBy(xpath = "//button[contains(@aria-label,'Update language engagement')]")
    private WebElement editLanguageEngagementButton;

    @FindBy(xpath = "//button[contains(@class,'MuiButton')]/span[contains(text(),'Edit dates')]")
    private WebElement editDatesButton;

    @FindBy(xpath = "//div[contains(@class,'BooleanProperty')]/span[text()='First Scripture']")
    private WebElement firstScriptureLabel;

    @FindBy(xpath = "//div[contains(@class,'BooleanProperty')]/span[text()='Luke Partnership']")
    private WebElement lukePartnershipLabel;

    @FindBy(name = "ceremony.estimatedDate")
    private WebElement estimatedDateTextBox;

    @FindBy(name = "ceremony.actualDate")
    private WebElement actualDateTextBox;

    @FindBy(xpath = "//a[@title='Create Goal']")
    private WebElement createGoalButton;

    @FindBy(xpath = "//p[(text()='Goals')]/following::div[contains(@class,'MuiGrid-root')]/following::h4[last()-1]")
    private WebElement lastUpdatedGoalText;

    @FindBy(xpath = "//p[contains(text(),'Scripture')]")
    private WebElement goalTypeText;

    @FindBy(xpath = " //p[(text()='Goal')]/following-sibling::div//div[last()-1]//p[1]//span[2]")
    private WebElement lastUpdatedGoalMethodologyText;

    @FindBy(xpath = "//a[@title='Edit Goal']")
    private WebElement goalEditButton;

    private final By languageEngagementDetailsButton = By.xpath("//button[contains(@class,'outlined')]");

    /**
     * click On language engagement name link
     *
     * @throws InterruptedException exception
     */
    public void clickOnLanguageEngagementNameLink() throws InterruptedException {
        selenium.clickOn(LanguageEngagementNameLink);
    }

    /**
     * click On update language engagement Button
     *
     * @throws InterruptedException exception
     */
    public void clickOnUpdateLanguageEngagementButton() throws InterruptedException {
        selenium.clickOn(editLanguageEngagementButton);
    }

    /**
     * Click on language engagement Step button by div index
     *
     * @throws InterruptedException exception
     */
    public void clickLanguageEngagementStatusButton() throws InterruptedException {
        selenium.clickOn(selenium.waitTillAllElementsAreLocated(languageEngagementDetailsButton).get(0));
    }

    /**
     * Click on start and end date button by div index
     *
     * @throws InterruptedException exception
     */
    public void clickLanguageEngagementStartEndDateButton() throws InterruptedException {
        selenium.waitTillElementIsClickable(languageEngagementDetailsButton);
        selenium.clickOn(selenium.waitTillAllElementsAreLocated(languageEngagementDetailsButton).get(1));
    }

    /**
     * Click on enter para text registry id button by div index
     *
     * @throws InterruptedException exception
     */
    public void clickLanguageEngagementParaTextRegistryIdButton() throws InterruptedException {
        selenium.clickOn(selenium.waitTillAllElementsAreLocated(languageEngagementDetailsButton).get(2));
    }

    /**
     * Click on translation complete date button
     *
     * @throws InterruptedException exception
     */
    public void clickTranslationCompleteDateTextBox() throws InterruptedException {
        selenium.clickOn(translationCompleteDateTextBox);
    }

    /**
     * Click on translation complete date button and fill detail
     *
     * @throws InterruptedException exception
     */
    public void clickTranslationCompleteDateAndFillDetail(Internship data) throws InterruptedException {
        selenium.clickOn(translationCompleteDateTextBox);
        selenium.clearTextBoxUsingKeys(translationCompleteDateTextBox);
        selenium.enterText(translationCompleteDateTextBox, data.getGrowthPlanCompleteDate(), false);

    }

    /**
     * click on add Planning and Progress button
     */
    public void clickOnAddPlanningAndProgressButton(String fileName) {

        String textFile = "src/main/resources/testData/" + fileName;
        addPlanningAndProgressButton.sendKeys(new File(textFile).getAbsolutePath());
    }

    /**
     * click On disbursement Complete date Button
     *
     * @throws InterruptedException exception
     */
    public void clickOnDisbursementCompleteDateTextBox() throws InterruptedException {
        selenium.clickOn(disbursementCompleteDateTextBox);
    }

    /**
     * Fill estimated date and actual date detail
     *
     * @param data internship object
     */
    public void fillLanguageEngagementEstimatedActualDateDetails(Internship data) {
        selenium.clearTextBoxUsingKeys(estimatedDateTextBox);
        selenium.enterText(estimatedDateTextBox, data.getInternshipEstimatedDate(), false);
        selenium.clearTextBoxUsingKeys(actualDateTextBox);
        selenium.enterText(actualDateTextBox, data.getInternshipActualDate(), false);
    }

    /**
     * click On disbursement Complete date and fill detail
     *
     * @throws InterruptedException exception
     */
    public void clickOnDisbursementCompleteDateAndFillDetail(Internship data) throws InterruptedException {
        selenium.clickOn(disbursementCompleteDateTextBox);
        selenium.clearTextBoxUsingKeys(disbursementCompleteDateTextBox);
        selenium.enterText(disbursementCompleteDateTextBox, data.getDisbursementCompleteDate(), false);

    }

    /**
     * click On dedication toggle  button
     *
     * @throws InterruptedException exception
     */
    public void clickDedicationToggleButton() throws InterruptedException {
        selenium.click(dedicationToggleButton);
    }

    /**
     * click On edit dates button
     *
     * @throws InterruptedException exception
     */
    public void clickEditDatesButton() throws InterruptedException {
        selenium.clickOn(editDatesButton);
    }

    /**
     * click On add goal button
     *
     * @throws InterruptedException exception
     */
    public void clickCreateGoalButton() throws InterruptedException {
        selenium.clickOn(createGoalButton);
    }

    /**
     * Get language engagement type text
     */

    public String geLanguageEngagementText() {
        return selenium.getText(LanguageEngagementNameLink);
    }

    /**
     * click On goal text
     *
     * @throws InterruptedException exception
     */

    public void clickOnGoalCard() throws InterruptedException {
        selenium.clickOn(selenium.waitTillElementIsClickable(lastUpdatedGoalText));
    }

    /**
     * Get goal type text
     */

    public String getGoalTypeText() {
        return selenium.getText(goalTypeText);
    }

    /**
     * click On edit goal button
     *
     * @throws InterruptedException exception
     */
    public void clickEditGoalButton() throws InterruptedException {
        selenium.clickOn(goalEditButton);
    }

    /**
     * Get language status button text by index
     *
     * @return internship status
     */
    public String getLanguageEngagementStatus() {
        return selenium.getText(selenium.waitTillAllElementsAreLocated(languageEngagementDetailsButton).get(0));
    }

    /**
     * Get start and end date button text by index
     *
     * @return start and end date
     */
    public String getLanguageStartEndDate() {

        return selenium.getText(selenium.waitTillAllElementsAreLocated(languageEngagementDetailsButton).get(1));
    }

    /**
     * Get para text registry id button text by index
     *
     * @return para text registry id
     */
    public String getLanguageParaTextRegistryId() {

        return selenium.getText(selenium.waitTillAllElementsAreLocated(languageEngagementDetailsButton).get(2));
    }

    /**
     * Get translation plan complete date
     *
     * @return translation plan complete date
     */
    public String getTranslationCompleteDate() {
        return selenium.getElementAttributeValue(translationCompleteDateTextBox, "value");
    }

    /**
     * Get disbursement complete date
     *
     * @return disbursement complete date
     */
    public String getDisbursementCompleteDate() {
        return selenium.getElementAttributeValue(disbursementCompleteDateTextBox, "value");
    }

    /**
     * Get uploaded file text
     *
     * @return upload file text
     */

    public String getUploadedFileText() {
        return selenium.getText(uploadedFileText);
    }

    /**
     * Get actual date text
     *
     * @return actual date text
     */

    public String getActualDateTextBox() {
        return selenium.getElementAttributeValue(actualDateTextBox, "value");
    }

    /**
     * Get estimated date text
     *
     * @return estimated date text
     */

    public String getEstimatedDateTextBox() {
        return selenium.getElementAttributeValue(estimatedDateTextBox, "value");
    }

    /**
     * Get goal text
     *
     * @return goal text
     */

    public String getGoalText() {
        return selenium.getText(lastUpdatedGoalText);
    }

    /**
     * Get goal methodology text
     *
     * @return goal methodology text
     */

    public String getGoalMethodologyText() {
        return selenium.getText(lastUpdatedGoalMethodologyText);
    }

    /**
     * get disabled edit button color
     *
     * @return disabled edit button color
     */
    public String getEditButtonColor() {
        return selenium.getElementCssValue(editDatesButton, "color");
    }

    /**
     * Is disbursement Complete Date Button enabled ?
     *
     * @return boolean
     */
    public boolean isDisbursementCompleteDateButtonEnabled() {
        return disbursementCompleteDateTextBox.isEnabled();
    }

    /**
     * Is translation Complete Date Text Box enabled ?
     *
     * @return boolean
     */
    public boolean isTranslationCompleteDateTextBoxEnabled() {
        return translationCompleteDateTextBox.isEnabled();
    }

    /**
     * Is first scripture label present ?
     *
     * @return boolean
     */
    public boolean isFirstScriptureLabelPresent() {
        return selenium.isElementPresent(firstScriptureLabel);
    }

    /**
     * Is luke partnership label present ?
     *
     * @return boolean
     */
    public boolean isLukePartnershipLabelPresent() {
        return selenium.isElementPresent(lukePartnershipLabel);
    }

    /**
     * Is goal present ?
     *
     * @return boolean
     */
    public boolean isGoalPresent() {
        String xpathLocator = "//p[(text()='Products')]/following-sibling::div/div[last()-1]//h4";
        return selenium.isElementPresent(By.xpath(xpathLocator));
    }

    /**
     * Is Add goal button present ?
     *
     * @return boolean
     */
    public boolean isAddGoalButtonPresent() {
        return selenium.isElementPresent(createGoalButton);
    }

    /**
     * Is estimated date textBox enabled ?
     *
     * @return boolean
     */
    public boolean isEstimatedDateTextBoxEnabled() {
        return estimatedDateTextBox.isEnabled();
    }

    /**
     * Is actual date textBox enabled ?
     *
     * @return boolean
     */
    public boolean isActualDateTextBoxEnabled() {
        return actualDateTextBox.isEnabled();
    }
}


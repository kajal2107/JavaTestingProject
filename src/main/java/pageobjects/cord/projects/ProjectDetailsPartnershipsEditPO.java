package pageobjects.cord.projects;

import dataobjects.Project;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class ProjectDetailsPartnershipsEditPO extends BasePO {

    public ProjectDetailsPartnershipsEditPO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    private final By submitButton = By.xpath("//span[text()='Submit']");

    private final By deleteButton = By.xpath("//span[text()='Delete']");

    @FindBy(name = "partnership.mouStartOverride")
    private WebElement mouStartOverrideDateTextBox;

    @FindBy(name = "partnership.mouEndOverride")
    private WebElement mouEndOverrideDateTextBox;

    @FindBy(xpath = "//legend[(text()='Types')]/following::input[@type='checkbox']")
    private WebElement partnershipTypesCheckbox;

    @FindBy(xpath = "//legend[(text()='Financial Reporting Type')]/following::input[@type='radio']")
    private WebElement financialReportingTypeRadioButton;

    @FindBy(xpath = "//legend[text()='Mou Status']/following::input[@type='radio']")
    private WebElement mouStatusRadioButton;

    @FindBy(xpath = "//legend[text()='Agreement Status']/following::input[@type='radio'][1]")
    private WebElement agreementStatusRadioButton;

    @FindBy(name = "partnership.primary")
    private WebElement partnershipPrimaryToggleButton;

    /**
     * Click on partnership financial reporting frequency by name
     *
     * @param financialReportingFrequency financialReportingFrequency
     * @throws InterruptedException exception
     */
    public void clickOnPartnershipFinancialReportingFrequencyByName(String financialReportingFrequency) throws InterruptedException {

        String partnershipFinancialReportingFrequencyXpathSelector = "//legend[(text()='Financial Reporting Frequency')]/following::span[(text()='" + financialReportingFrequency + "')]";
        selenium.clickOn(By.xpath(partnershipFinancialReportingFrequencyXpathSelector));
    }

    /**
     * Click on partnership agreement status by name
     *
     * @param partnershipStatus partnershipStatus
     * @throws InterruptedException exception
     */
    public void clickOnPartnershipAgreementStatusByName(String partnershipStatus) throws InterruptedException {

        String partnershipAgreementStatusXpathSelector = "//legend[(text()='Agreement Status')]/following::span[(text()='" + partnershipStatus + "')]";
        selenium.clickOn(By.xpath(partnershipAgreementStatusXpathSelector));
    }

    /**
     * Click on partnership mou status by name
     *
     * @param partnershipStatus partnershipStatus
     * @throws InterruptedException exception
     */
    public void clickOnPartnershipMouStatusByName(String partnershipStatus) throws InterruptedException {

        String partnershipMouStatusXpathSelector = "//legend[(text()='Mou Status')]/following::span[(text()='" + partnershipStatus + "')]";
        selenium.clickOn(By.xpath(partnershipMouStatusXpathSelector));
    }

    /**
     * Click on delete button
     *
     * @throws InterruptedException exception
     */
    public void clickOnPartnershipDeleteButton() throws InterruptedException {
        selenium.clickOn(deleteButton);
    }

    /**
     * Click on submit button
     *
     * @throws InterruptedException exception
     */
    public void clickOnPartnershipSubmitButton() throws InterruptedException {
        selenium.clickOn(submitButton);
    }

    /**
     * Is Partnership types  button enabled ?
     *
     * @return boolean
     */
    public boolean isPartnershipTypesButtonEnabled() {
        return selenium.isElementEnabled(partnershipTypesCheckbox);
    }


    /**
     * Is Financial reporting type radio button enabled ?
     *
     * @return boolean
     */
    public boolean isFinancialReportingTypeRadioButtonEnabled() {
        return selenium.isElementEnabled(financialReportingTypeRadioButton);
    }

    /**
     * Is Mou Status enabled ?
     *
     * @return boolean
     */
    public boolean isMouStatusEnabled() {
        return selenium.isElementEnabled(mouStatusRadioButton);
    }

    /**
     * Is Agreement status enabled ?
     *
     * @return boolean
     */
    public boolean isAgreementStatusEnabled() {
        return selenium.isElementEnabled(agreementStatusRadioButton);
    }

    /**
     * Is Mou start date textBox enabled ?
     *
     * @return boolean
     */
    public boolean isMouStartDateTextBoxEnabled() {
        return selenium.isElementEnabled(mouStartOverrideDateTextBox);
    }

    /**
     * Is Mou end date textBox enabled ?
     *
     * @return boolean
     */
    public boolean isMouEndDateTextBoxEnabled() {
        return selenium.isElementEnabled(mouEndOverrideDateTextBox);
    }

    /**
     * Is Mou start date enabled ?
     *
     * @return boolean
     */
    public boolean isMouStartDateEnabled() {
        return selenium.isElementEnabled(mouStartOverrideDateTextBox);
    }

    /**
     * Is Mou end date enabled ?
     *
     * @return boolean
     */
    public boolean isMouEndDateEnabled() {
        return selenium.isElementEnabled(mouEndOverrideDateTextBox);
    }

    /**
     * Fill mou date detail
     *
     * @param updateData project object
     */

    public void fillMouDateDetails(Project updateData) {
        selenium.clearTextBoxUsingKeys(mouStartOverrideDateTextBox);
        selenium.enterText(mouStartOverrideDateTextBox, updateData.getStartDate(), false);
        selenium.clearTextBoxUsingKeys(mouEndOverrideDateTextBox);
        selenium.enterText(mouEndOverrideDateTextBox, updateData.getEndDate(), false);
    }

    /**
     * Click on primary toggle button
     *
     * @throws InterruptedException exception
     */

    public void clickOnPrimaryToggleButton() throws InterruptedException {
        selenium.clickOn(partnershipPrimaryToggleButton);
    }

    /**
     * Is primary toggle button available
     */

    public boolean isPrimaryToggleButtonAvailable() {
        return selenium.isElementPresent(partnershipPrimaryToggleButton);
    }
}


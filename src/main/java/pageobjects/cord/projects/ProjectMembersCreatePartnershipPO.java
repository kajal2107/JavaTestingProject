package pageobjects.cord.projects;

import dataobjects.Partner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;
import utilities.Constants;

public class ProjectMembersCreatePartnershipPO extends BasePO {

    public ProjectMembersCreatePartnershipPO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */
    @FindBy(xpath = "//input[contains(@placeholder,'Search for an partner by name')]")
    private WebElement searchPartnerTextBox;

    @FindBy(xpath = "//input[contains(@placeholder,'Enter organization name')]")
    private WebElement organizationName;

    @FindBy(xpath = "//div[contains(@class,'MuiAutocomplete-popper')]")
    private WebElement addPartnerName;

    @FindBy(xpath = "//ul[contains(@class,'MuiAutocomplete-listbox')]/li[1]")
    private WebElement addOrgName;

    @FindBy(xpath = "//div[contains(@class,'noOptions')]")
    private WebElement searchedPartnershipText;

    @FindBy(name = "Managing")
    private WebElement orgManagingCheckBox;

    @FindBy(name = "Funding")
    private WebElement orgFundingCheckBox;

    @FindBy(id = "notistack-snackbar")
    private WebElement createPartnershipErrorText;

    @FindBy(xpath = "//legend[text()='Financial Reporting Type']/following::input[@type='radio']")
    private WebElement orgFinancialReportingTypeRadioButton;

    @FindBy(name = "partnership.primary")
    private WebElement partnershipPrimaryToggleButton;

    private final By createOrgSubmitButton = By.xpath("//h2[text()='Create Organization']//ancestor::div//span[text()='Submit']");
    private final By createPartnerSubmitButton = By.xpath("//h2[text()='Create Partner']//ancestor::div//span[text()='Submit']");
    private final By createPartnershipSubmitButton = By.xpath("//h2[text()='Create Partnership']//ancestor::div//span[text()='Submit']");
    private final By submitButton = By.xpath("//span[text()='Submit']");

    /**
     * Fill partnership detail and click on submit button
     *
     * @param data partner object
     * @throws InterruptedException exception
     */
    public void fillPartnershipDetail(Partner data) throws InterruptedException {
        selenium.clearTextBoxUsingKeys(searchPartnerTextBox);
        selenium.enterText(searchPartnerTextBox, data.getPartnerName(), false);
        selenium.hardWait(3);
        selenium.clickOn(addPartnerName);
        selenium.enterText(organizationName, data.getPartnerName(), false);
        selenium.hardWait(5);
        selenium.clickOn(addPartnerName);
        selenium.clickOn(createOrgSubmitButton);
        selenium.hardWait(5);
        selenium.clickOn(createPartnerSubmitButton);
        selenium.hardWait(5);
        selenium.click(partnershipPrimaryToggleButton);
        selenium.clickOn(createPartnershipSubmitButton);
    }

    /**
     * Fill organization detail and click on submit button
     *
     * @throws InterruptedException exception
     */
    public void fillOrganizationDetail(Partner data) throws InterruptedException {
        selenium.clearTextBoxUsingKeys(searchPartnerTextBox);
        selenium.enterText(searchPartnerTextBox, "AOrg", false);
        selenium.hardWait(5);
        data.setOrganizationName(addOrgName.getText());
        selenium.clickOn(addOrgName);
        selenium.click(orgManagingCheckBox);

        selenium.click(orgFinancialReportingTypeRadioButton);
        selenium.click(partnershipPrimaryToggleButton);
        selenium.clickOn(createPartnershipSubmitButton);

    }

    /**
     * Fill funding organization detail and click on submit button
     *
     * @throws InterruptedException exception
     */
    public void fillFundingOrganizationDetail(Partner data) throws InterruptedException {
        selenium.clearTextBoxUsingKeys(searchPartnerTextBox);
        selenium.enterText(searchPartnerTextBox, "Budget", false);
        selenium.hardWait(5);
        data.setPartnerName(addOrgName.getText());
        selenium.clickOn(addOrgName);
        selenium.click(orgFundingCheckBox);
        selenium.click(partnershipPrimaryToggleButton);
        selenium.clickOn(createPartnershipSubmitButton);
    }

    /**
     * Search organization name
     */
    public void searchOrganizationName() {
        selenium.clearTextBoxUsingKeys(searchPartnerTextBox);
        selenium.enterText(searchPartnerTextBox, Constants.searchPartnerSuggestionText, false);
    }

    /**
     * Click on edit partnership submit button
     *
     * @throws InterruptedException exception
     */
    public void clickOnEditPartnershipButton() throws InterruptedException {
        selenium.clickOn(submitButton);
    }

    /**
     * get partnership error text
     *
     * @return partnership error text
     */
    public String getPartnershipErrorText() {
        return selenium.getText(createPartnershipErrorText);
    }

    /**
     * get searched partnership name text
     *
     * @return searched partnership name text
     */
    public String getSearchedPartnershipText() {
        return selenium.getText(searchedPartnershipText);
    }

}


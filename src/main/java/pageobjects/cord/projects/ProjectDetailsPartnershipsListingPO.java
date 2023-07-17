package pageobjects.cord.projects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class ProjectDetailsPartnershipsListingPO extends BasePO {

    public ProjectDetailsPartnershipsListingPO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    @FindBy(xpath = "//button[@aria-label='add partnership']")
    private WebElement addPartnershipButton;

    @FindBy(xpath = "//span[text()='Edit']")
    private WebElement editPartnershipButton;

    @FindBy(xpath = "//a[contains(@class,'MuiLink-underlineHover')]")
    private WebElement projectNameLinkbutton;

    @FindBy(xpath = "//div[contains(@class,'MuiGrid-container')]/h4")
    private WebElement partnershipNameText;

    /**
     * Is project partnership present ?
     *
     * @param partnershipName partnership name
     * @return boolean
     */
    public boolean isProjectPartnershipPresent(String partnershipName) {
        String xpathLocatorFileFolder = "//h4[text()='" + partnershipName + "']";
        return selenium.isElementPresent(By.xpath(xpathLocatorFileFolder));
    }

    /**
     * Is created partnership primary ?
     *
     * @return boolean
     */
    public boolean isCreatedPartnershipPrimary(String partnershipName) {
        String xpathPrimaryStar = "//h4[text()='" + partnershipName + "']/parent::div//*[2]";
        return selenium.isElementPresent(By.xpath(xpathPrimaryStar));
    }

    /**
     * Is project add partnership button present ?
     *
     * @return boolean
     */
    public boolean isProjectAddPartnershipButtonPresent() {
        return selenium.isElementPresent(addPartnershipButton);
    }

    /**
     * click On Add Partnership Button
     *
     * @throws InterruptedException exception
     */
    public void clickOnAddPartnershipButton() throws InterruptedException {
        selenium.clickOn(addPartnershipButton);
    }

    /**
     * click On project link button
     *
     * @throws InterruptedException exception
     */
    public void clickOnProjectLinkButton() throws InterruptedException {

        selenium.clickOn(projectNameLinkbutton);
    }

    /**
     * click On edit partnership Button
     *
     * @param partnershipName partnership Name
     * @throws InterruptedException exception
     */
    public void clickOnEditPartnershipButton(String partnershipName) throws InterruptedException {
        String editPartnership = "//h4[text()='" + partnershipName + "']//parent::div/parent::div/parent::div/following-sibling::div//button";
        selenium.clickOn(By.xpath(editPartnership));
    }

    /**
     * click On edit partnership Button
     *
     * @throws InterruptedException exception
     */
    public void clickOnEditOrganizationButton() throws InterruptedException {
        selenium.click(editPartnershipButton);
    }

    /**
     * Get Partnership name
     *
     * @param partnershipName partnership Name
     * @return Partnership name
     */
    public String getPartnershipName(String partnershipName) {
        String partnershipByName = "//div[contains(@class,'MuiGrid-item')]/h4[contains(text(),'" + partnershipName + "')]";
        return selenium.getText(By.xpath(partnershipByName));
    }

    /**
     * Get type text
     *
     * @param partnershipName partnership Name
     * @return type text
     */
    public String getTypeText(String partnershipName) {
        String typeText = "//div[contains(@class,'MuiGrid-item')]/h4[text()='" + partnershipName + "']//following::div[contains(@class,'MuiGrid-root')][1]/p";
        return selenium.getText(By.xpath(typeText));
    }

    /**
     * Get financial reporting type text
     *
     * @param partnershipName partnership Name
     * @return financial reporting type text
     */
    public String getFinancialReportingTypeText(String partnershipName) {
        String financialReportingTypeText = "//div[contains(@class,'MuiGrid-item')]/h4[text()='" + partnershipName + "']/following::div/following::span[text()='Financial Reporting Type']/following-sibling::span";
        return selenium.getText(By.xpath(financialReportingTypeText));
    }

    /**
     * Get agreement status text
     *
     * @param partnershipName partnership Name
     * @return agreement status text
     */
    public String getAgreementStatusText(String partnershipName) {
        String agreementStatusText = "//div[contains(@class,'MuiGrid-item')]/h4[text()='" + partnershipName + "']/following::div/following::span[text()='Agreement Status']/following-sibling::span";
        return selenium.getText(By.xpath(agreementStatusText));
    }

    /**
     * Get mou status text
     *
     * @param partnershipName partnership Name
     * @return mou status text
     */
    public String getMouStatusText(String partnershipName) {
        String mouStatusText = "//div[contains(@class,'MuiGrid-item')]/h4[text()='" + partnershipName + "']/following::div/following::span[text()='MOU Status']/following-sibling::span";
        return selenium.getText(By.xpath(mouStatusText));
    }

    /**
     * Get MOU date range text
     *
     * @param partnershipName partnership Name
     * @return mou status text
     */
    public String getMouDateRangeText(String partnershipName) {
        String mouDateRangeText = "//div[contains(@class,'MuiGrid-item')]/h4[text()='" + partnershipName + "']/following::div/following::span[text()='MOU Date Range']/following-sibling::span";
        return selenium.getText(By.xpath(mouDateRangeText));
    }
    /**
     * Get Partnership name
     *
     * @return Partnership name
     */
    public String getPartnershipName() {
        return partnershipNameText.getText();
    }
}


package pageobjects.cord.projects.intershipEngagement;

import dataobjects.Project;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class ProjectInternshipDetailsUpdateCountryOfOriginPO extends BasePO {

    public ProjectInternshipDetailsUpdateCountryOfOriginPO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    @FindBy(xpath = "//input[contains(@placeholder,'Search for a location by name')]")
    private WebElement countryOfOriginTextBox;

    @FindBy(xpath = "//li[contains(@class,'MuiAutocomplete-option')][1]")
    private WebElement addCountryOfOriginName;


    private final By saveButton = By.xpath("//span[text()='Save']");

    private final By closeButton = By.xpath("//span[text()='Close']");

    /**
     * Fill country of origin detail and click on submit button
     *
     * @param updateData project object
     * @throws InterruptedException exception
     */
    public void fillInternshipCountryOfOriginDetailsAndClickOnSaveButton(Project updateData) throws InterruptedException {
        selenium.clearTextBoxUsingKeys(countryOfOriginTextBox);
        selenium.enterText(countryOfOriginTextBox, updateData.getLocationName(), false);
        selenium.hardWait(3);
        updateData.setLocationName(addCountryOfOriginName.getText());
        selenium.clickOn(addCountryOfOriginName);
        selenium.clickOn(saveButton);
        selenium.waitTillElementsCountIsLessThan(saveButton, 1);
    }

    /**
     * Click on internship country of origin close button
     *
     * @throws InterruptedException exception
     */
    public void clickOnInternshipCountryOfOriginCloseButton() throws InterruptedException {
        selenium.clickOn(closeButton);
        selenium.waitTillElementsCountIsLessThan(closeButton, 1);
    }

    /**
     * Is internship country of origin textBox enabled ?
     *
     * @return boolean
     */
    public boolean isInternshipCountryOfOriginTextBoxEnabled() {
        return selenium.isElementEnabled(countryOfOriginTextBox);
    }

}


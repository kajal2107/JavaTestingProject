package pageobjects.cord.projects;

import dataobjects.Project;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class ProjectDetailsUpdateLocationAndFieldRegionPO extends BasePO {

    public ProjectDetailsUpdateLocationAndFieldRegionPO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    @FindBy(xpath = "//label[contains(text(),'Primary Location')]/following::input[contains(@placeholder,'Search for a location by name')]")
    private WebElement locationTextBox;

    @FindBy(xpath = "//label[contains(text(),'Field Region')]/following::input[contains(@placeholder,'Search for a field region by name')]")
    private WebElement regionTextBox;

    @FindBy(xpath = "//li[contains(@class,'MuiAutocomplete-option')][1]")
    private WebElement addLocationFieldRegionName;

    @FindBy(xpath = "//div[@id='dialog-form']/following::p[1]")
    private WebElement locationFieldRegionErrorText;


    private final By saveButton = By.xpath("//span[text()='Save']");

    private final By closeButton = By.xpath("//span[text()='Close']");


    /**
     * Fill location and field region  detail and click on submit button
     *
     * @param updateData project object
     */
    public void fillLocationAndFieldRegionDetailsAndClickOnSaveButton(Project updateData) {

        try {

            selenium.clearTextBoxUsingKeys(locationTextBox);
            selenium.enterText(locationTextBox, updateData.getLocationName(), false);
            selenium.hardWait(3);
            updateData.setLocationName(addLocationFieldRegionName.getText());
            selenium.clickOn(addLocationFieldRegionName);
            selenium.clearTextBoxUsingKeys(regionTextBox);
            selenium.enterText(regionTextBox, updateData.getFieldRegionName(), false);
            selenium.hardWait(3);
            updateData.setFieldRegionName(addLocationFieldRegionName.getText());
            selenium.clickOn(addLocationFieldRegionName);
            selenium.clickOn(saveButton);
            selenium.waitTillElementsCountIsLessThan(saveButton, 1);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * click On location field region close button
     *
     * @throws InterruptedException exception
     */
    public void clickOnLocationFieldRegionCloseButton() throws InterruptedException {
        selenium.clickOn(closeButton);
        selenium.waitTillElementsCountIsLessThan(closeButton, 1);
    }

    /**
     * get location field region error text
     *
     * @return location field region error  text
     */
    public String getLocationFieldRegionErrorText() {
        return selenium.getText(locationFieldRegionErrorText);
    }

    /**
     * Is field region textBox enabled ?
     *
     * @return boolean
     */
    public boolean isFieldRegionTextBoxEnabled() {
        return selenium.isElementEnabled(regionTextBox);
    }

    /**
     * Is location textBox enabled ?
     *
     * @return boolean
     */
    public boolean isLocationTextBoxEnabled() {
        return selenium.isElementEnabled(locationTextBox);
    }
}


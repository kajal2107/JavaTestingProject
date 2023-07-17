package pageobjects.cord.projects;

import dataobjects.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class ProjectDetailsCreateInternshipPO extends BasePO {

    public ProjectDetailsCreateInternshipPO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */
    @FindBy(xpath = "//input[contains(@placeholder,'Enter person')]")
    private WebElement searchPersonTextBox;

    @FindBy(xpath = "//ul[contains(@class,'MuiAutocomplete-listbox')]/li[1]")
    private WebElement addPersonName;

    @FindBy(xpath = "//div[(@class='MuiDialogContent-root')]/p")
    private WebElement internshipErrorMessage;


    private By submitButton = By.xpath("//span[text()='Submit']");
    private By cancelButton = By.xpath("//span[text()='Cancel']");

    /**
     * Fill partnership detail and click on submit button
     *
     * @param data User object
     * @throws InterruptedException exception
     */

    public void fillCreateInternshipDetail(User data) throws InterruptedException {
        selenium.clearTextBoxUsingKeys(searchPersonTextBox);
        selenium.enterText(searchPersonTextBox, data.getRealFirstName(), false);
        selenium.hardWait(3);
        selectPersonFromDropDownByName(data.getRealFirstName() + " " + data.getRealLastName());
        selenium.clickOn(submitButton);
    }

    /**
     * Fill partnership detail and click on submit button
     *
     * @param uname UserName
     * @throws InterruptedException exception
     */

    public void selectPersonFromDropDownByName(String uname) throws InterruptedException {
        String xPathUserName = "//ul[contains(@class,'MuiAutocomplete-listbox')]/li[contains(text(),'" + uname + "')]";
        selenium.hardWait(5);
        selenium.clickOn(By.xpath(xPathUserName));
    }

    /**
     * Get Internship error message
     *
     * @return Internship error message
     */
    public String getInternshipErrorMessage() {
        return selenium.getText(internshipErrorMessage);
    }

}


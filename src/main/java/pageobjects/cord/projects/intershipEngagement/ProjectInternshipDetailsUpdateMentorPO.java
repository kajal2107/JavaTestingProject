package pageobjects.cord.projects.intershipEngagement;

import dataobjects.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class ProjectInternshipDetailsUpdateMentorPO extends BasePO {

    public ProjectInternshipDetailsUpdateMentorPO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    @FindBy(xpath = "//input[contains(@placeholder,'Search for a person by name')]")
    private WebElement mentorTextBox;

    private final By saveButton = By.xpath("//span[text()='Save']");

    /**
     * Add mentor by name
     *
     * @param userName User name
     * @throws InterruptedException exception
     */

    public void selectMentorByName(String userName) throws InterruptedException {
        String xpathUserName = "//ul[contains(@class,'MuiAutocomplete-listbox')]/li[text()= '" + userName + "']";
        selenium.clickOn(By.xpath(xpathUserName));
    }

    /**
     * Fill mentor detail and click on submit button
     *
     * @param userData user object
     * @throws InterruptedException exception
     */
    public void fillInternshipMentorDetailsAndClickOnSaveButton(User userData) throws InterruptedException {
        selenium.clearTextBoxUsingKeys(mentorTextBox);
        selenium.enterText(mentorTextBox, userData.getRealFirstName(), false);
        selenium.hardWait(3);
        selectMentorByName(userData.getRealFirstName() + " " + userData.getRealLastName());
        selenium.clickOn(saveButton);
        selenium.waitTillElementsCountIsLessThan(saveButton, 1);
    }
}


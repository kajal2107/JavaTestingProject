package pageobjects.cord.projects;

import dataobjects.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class ProjectMembersUpdateTeamMembersPO extends BasePO {

    public ProjectMembersUpdateTeamMembersPO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    @FindBy(xpath = "//button[contains(@aria-label,'Open')]")
    private WebElement rolesDropDownButton;

    @FindBy(xpath = "//label[contains(text(),'Roles')]/following::input")
    private WebElement searchRolesTextBox;

    @FindBy(xpath = "//ul[contains(@class,'MuiAutocomplete-listbox')]/li[1]")
    private WebElement searchSuggestionRolesName;

    @FindBy(id = "dialog-form")
    private WebElement updateTeamMemberRoleNameText;

    @FindBy(xpath = "//div[@id='dialog-form']/following::p[1]")
    private WebElement teamMemberErrorMessage;

    @FindBy(xpath = "//button[contains(@class,'clearIndicatorDirty')]")
    private WebElement teamMemberClearButton;


    private final By saveButton = By.xpath("//span[text()='Save']");

    /**
     * click On roles drop - down button and change role
     *
     * @param data user object
     * @throws InterruptedException exception
     */
    public void clickOnRolesDropDownButtonAndClickOnSaveButton(User data) throws InterruptedException {
        selenium.clickOn(rolesDropDownButton);
        selenium.enterText(searchRolesTextBox, data.getUserRoles().get(1), false);
        selenium.clickOn(searchSuggestionRolesName);
        selenium.clickOn(updateTeamMemberRoleNameText);
        selenium.clickOn(saveButton);
        selenium.waitTillElementsCountIsLessThan(saveButton, 1);
    }

    /**
     * click On team member clear and save button
     * @throws InterruptedException exception
     */
    public void clickOnTeamMemberClearAndSaveButton() throws InterruptedException {
        selenium.clickOn(teamMemberClearButton);
        selenium.clickOn(saveButton);
        selenium.waitTillElementsCountIsLessThan(saveButton, 1);
    }


    /**
     * get team member error text
     *
     * @return team member  error text
     */
    public String getTeamMemberErrorText() {
        return selenium.getText(teamMemberErrorMessage);
    }

}


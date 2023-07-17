package pageobjects.cord.projects;

import dataobjects.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class ProjectDetailsTeamMembersAddTeamMemberPO extends BasePO {

    public ProjectDetailsTeamMembersAddTeamMemberPO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    @FindBy(xpath = "//input[contains(@placeholder,'Search for a person by name')]")
    private WebElement searchPersonTextBox;

    @FindBy(xpath = "//ul[contains(@class,'MuiAutocomplete-listbox')]/li[1]")
    private WebElement searchSuggestionPersonAndRoleName;

    @FindBy(xpath = "//button[contains(@aria-label,'Open')]")
    private WebElement rolesDropDownButton;

    @FindBy(xpath = "//label[contains(text(),'Roles')]/following::input")
    private WebElement searchRolesTextBox;

    @FindBy(xpath = "//ul[contains(@class,'MuiAutocomplete-listbox')]/li[9]")
    private WebElement searchSuggestionRolesName;

    @FindBy(id = "dialog-form")
    private WebElement addTeamMemberDialogHeaderNameText;

    private By submitButton = By.xpath("//span[text()='Submit']");

    private By cancelButton = By.xpath("//span[text()='Cancel']");


    /**
     * Add team member by name
     *
     * @param userName User name
     * @throws InterruptedException exception
     */

    public void selectTeamMemberByName(String userName) throws InterruptedException {
        String xpathUserName = "//ul[contains(@class,'MuiAutocomplete-listbox')]/li[text()= '"+ userName +"']";
        selenium.clickOn(By.xpath(xpathUserName));
    }

    /**
     * Add role by name
     *
     * @param role role
     * @throws InterruptedException exception
     */

    public void selectRoleByName(String role) throws InterruptedException {
        String xpathRoleName = "//ul[contains(@class,'MuiAutocomplete-listbox')]/li[text()= '"+ role +"']";
        selenium.clickOn(By.xpath(xpathRoleName));
    }

    /**
     * clear and Fill all partner point of contact detail and click on submit button
     *
     * @param roleName user role
     * @throws InterruptedException exception
     */

    public void selectRolesFromDropdownByName(String roleName) throws InterruptedException {

        String xpathRoleName = "//ul[contains(@class,'MuiAutocomplete')]/li[contains(text()," + roleName + ")]";
        selenium.hardWait(5);
        selenium.clickOn(By.xpath(xpathRoleName));
    }

    /**
     * clear and Fill all partner point of contact detail and click on submit button
     *
     * @param data user
     * @throws InterruptedException exception
     */

    public void clearAndFillAddTeamMember(User data) throws InterruptedException {

        selenium.clearTextBoxUsingKeys(searchPersonTextBox);
        selenium.enterText(searchPersonTextBox, data.getRealFirstName(), false);
        selenium.hardWait(3);
        selectTeamMemberByName(data.getRealFirstName()+" "+data.getRealLastName());
        selenium.clearTextBoxUsingKeys(searchRolesTextBox);
        selenium.enterText(searchRolesTextBox, data.getUserRoles().get(0), false);
        selectRoleByName(data.getUserRoles().get(0));
        selenium.click(addTeamMemberDialogHeaderNameText);
        selenium.clickOn(submitButton);
        selenium.waitTillElementsCountIsLessThan(submitButton, 1);
    }

    /**
     * get person name
     * @return person name
     */
    public String getPersonName() {
        return selenium.getText(searchPersonTextBox);
    }


}


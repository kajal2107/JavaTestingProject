package pageobjects.cord.projects;

import dataobjects.Project;
import enums.project.ProjectType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class CreateEditProjectPO extends BasePO {

    public CreateEditProjectPO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    @FindBy(name = "project.name")
    private WebElement projectName;

    @FindBy(xpath = "//button[(@value='Translation')]")
    private WebElement translationType;

    @FindBy(xpath = "//button[(@value='Internship')]")
    private WebElement internshipType;

    private final By submitButton = By.xpath("//span[text()='Submit']");

    private final By saveButton = By.xpath("//span[text()='Save']");

    /**
     * Fill Project detail and click on submit button
     *
     * @param data Project object
     * @throws InterruptedException exception
     */
    public void fillProjectDetailAndClickOnSubmitButton(Project data) throws InterruptedException {
        selenium.enterText(projectName, data.getProjectName(), false);
        if (data.getType() == ProjectType.Internship) {
            selenium.click(internshipType);
        } else {
            selenium.click(translationType);
        }
        selenium.clickOn(submitButton);
        selenium.waitTillElementsCountIsLessThan(submitButton, 1);
    }

    public void fillProjectDetailsAndClickOnSaveButton(Project data) throws InterruptedException {
        selenium.clearTextBoxUsingKeys(projectName);
        selenium.enterText(projectName, data.getProjectName(), false);
        selenium.clickOn(saveButton);
        selenium.waitTillElementsCountIsLessThan(submitButton, 1);
    }
}


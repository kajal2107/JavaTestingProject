package pageobjects.cord.projects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class ProjectDetailsUpdateStepsPO extends BasePO {

    public ProjectDetailsUpdateStepsPO(WebDriver driver) {
        super(driver);
    }


    @FindBy(xpath = "//label[text()='Override Step']/following::input")
    private WebElement overrideStatusTextBox;

    @FindBy(xpath = "//ul[contains(@class,'MuiAutocomplete-listbox')]/li[1]")
    private WebElement addProjectOverrideStatusName;

    @FindBy(xpath = "//div[@id='dialog-form']/following-sibling::div/div/p[1]")
    private WebElement projectStatusErrorText;

    private By submitButton = By.xpath("//span[text()='Submit']");
    private By closeButton = By.xpath("//span[text()='Close']");

    /**
     * Click on Project Step by name
     *
     * @throws InterruptedException exception
     */
    public void advanceProjectByStepName(String projectStep) throws InterruptedException {

        String projectStepXpathSelector = "//span[contains(text(),'" + projectStep + "')]";
        selenium.clickOn(By.xpath(projectStepXpathSelector));
    }


    /**
     * Click on project Override Status by name
     *
     * @throws InterruptedException exception
     */
    public void clickProjectOverrideStatusByName(String projectStatus) throws InterruptedException {
        selenium.clickOn(overrideStatusTextBox);
        selenium.enterText(overrideStatusTextBox, projectStatus, false);
        selenium.clickOn(addProjectOverrideStatusName);
        selenium.clickOn(submitButton);
        selenium.waitTillElementsCountIsLessThan(submitButton, 1);

    }

    /**
     * Get Project Status Error text
     *
     * @return Project Status Error text
     */

    public String getProjectStatusError() {
        return selenium.getText(projectStatusErrorText);
    }

    /**
     * Click on Project status close button
     *
     * @throws InterruptedException exception
     */
    public void clickProjectStatusCloseButton() throws InterruptedException {

        selenium.clickOn(closeButton);
        selenium.waitTillElementsCountIsLessThan(submitButton, 1);
    }



}


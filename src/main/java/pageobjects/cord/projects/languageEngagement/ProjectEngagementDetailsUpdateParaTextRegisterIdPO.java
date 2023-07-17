package pageobjects.cord.projects.languageEngagement;

import dataobjects.Internship;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class ProjectEngagementDetailsUpdateParaTextRegisterIdPO extends BasePO {

    public ProjectEngagementDetailsUpdateParaTextRegisterIdPO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    @FindBy(name = "engagement.paratextRegistryId")
    private WebElement paraTextRegistryTextBox;

    private final By saveButton = By.xpath("//span[text()='Save']");

    /**
     * Fill paraText registry id detail and click on submit button
     *
     * @param data internship object
     * @throws InterruptedException exception
     */
    public void fillEngagementParaTextRegistryIdDetailsAndClickOnSaveButton(Internship data) throws InterruptedException {
        selenium.clearTextBoxUsingKeys(paraTextRegistryTextBox);
        selenium.enterText(paraTextRegistryTextBox, data.getParaTextRegId(), false);
        selenium.clickOn(saveButton);
        selenium.waitTillElementsCountIsLessThan(saveButton, 1);
    }
}


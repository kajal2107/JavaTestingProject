package pageobjects.cord.projects.intershipEngagement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.base.BasePO;

public class ProjectInternshipDetailsUpdateInternMethodologiesPO extends BasePO {

    public ProjectInternshipDetailsUpdateInternMethodologiesPO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    private By saveButton = By.xpath("//span[text()='Save']");

    private By closeButton = By.xpath("//span[text()='Close']");

    /**
     * Click on internship methodologies by name
     *
     * @param internMethodologies internMethodologies
     * @throws InterruptedException exception
     */
    public void clickOnInternMethodologyByName(String internMethodologies) throws InterruptedException {
        String internMethodologiesXpathSelector = "//span[contains(text(),'" + internMethodologies + "')]";
        selenium.clickOn(By.xpath(internMethodologiesXpathSelector));
    }

    /**
     * Click on save button
     *
     * @throws InterruptedException exception
     */
    public void clickOnSaveButton() throws InterruptedException {
        selenium.clickOn(saveButton);
    }

}


package pageobjects.cord.changeRequest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;


public class CreateChangeRequestDetailsPO extends BasePO {

    public CreateChangeRequestDetailsPO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    @FindBy(xpath = "//button[contains(@title,'Create Change Request')]")
    private WebElement createChangeRequestButton;

    @FindBy(xpath = "//p[contains(@class,'MuiTypography-root')]//following-sibling::span[contains(@class,'displayInline')]")
    private WebElement changeRequestTypesText;

    @FindBy(xpath = "//span[text()='ID']/following::p")
    private WebElement changeRequestSummaryText;

    /**
     * Click on create change request button
     *
     * @throws InterruptedException exception
     */
    public void clickOnCreateChangeRequestButton() throws InterruptedException {
        selenium.clickOn(createChangeRequestButton);
    }

    /**
     * Get change request types text
     *
     * @return change request types text
     */
    public String getChangeRequestTypesText() {
        return selenium.getText(changeRequestTypesText);
    }

    /**
     * Get change request summary text
     *
     * @return change request summary text
     */
    public String getChangeRequestSummaryText() {
        return selenium.getText(changeRequestSummaryText);
    }

}


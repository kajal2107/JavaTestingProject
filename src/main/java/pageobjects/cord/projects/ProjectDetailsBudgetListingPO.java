package pageobjects.cord.projects;

import dataobjects.Budget;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

import java.io.File;

public class ProjectDetailsBudgetListingPO extends BasePO {

    public ProjectDetailsBudgetListingPO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    @FindBy(name = "defined_file_version_uploader")
    private WebElement addTemplateFile;

    @FindBy(xpath = "//div[@id='draggable-dialog-title']/following::p")
    private WebElement uploadedFileText;

    @FindBy(xpath = "//button[contains(@class,'MuiCardActionArea')]")
    private WebElement addUniversalTemplateButton;

    @FindBy(xpath = "//div[@data-rbd-draggable-id=3]/following::div[contains(@style,'border-bottom')][1]")
    private WebElement clickToEditLink;

    @FindBy(xpath = "//div[text()='Funding Partner']/following::tr[1]/td[1]")
    private WebElement budgetNameText;

    @FindBy(xpath = "//input[@placeholder='Amount']")
    private WebElement amountTextBox;

    @FindBy(xpath = "//button[contains(@class,'MuiIconButton-sizeSmall')][1]")
    private WebElement budgetSaveButton;

    @FindBy(xpath = "//p[text()='Oops, Sorry.']/following::h2")
    private WebElement budgetErrorMessage;


    /**
     * Add template file
     */
    public void addBudgetTemplateFile(String fileName) {
        String textFile = "src/main/resources/testData/" + fileName;
        addTemplateFile.sendKeys(new File(textFile).getAbsolutePath());
    }

    /**
     * get uploaded template file name
     *
     * @return uploaded file text
     */
    public String getUploadedTemplateFileName() {
        return selenium.getText(uploadedFileText);
    }

    /**
     * get budget amount text
     *
     * @return budget amount text
     */
    public String getBudgetAmountText() {
        return selenium.getText(clickToEditLink);
    }

    /**
     * get budget name text
     *
     * @return budget name text
     */
    public String getBudgetNameText() {
        return selenium.getText(budgetNameText);
    }

    /**
     * get budget error text
     *
     * @return budget error text
     */
    public String getBudgetErrorText() {
        return selenium.getText(budgetErrorMessage);
    }

    /**
     * Is add universal template file button enabled ?
     *
     * @return boolean
     */
    public boolean isAddUniversalTemplateButtonEnabled() {
        return selenium.isElementEnabled(addUniversalTemplateButton);
    }

    /**
     * Is click to add button link present ?
     *
     * @return boolean
     */
    public boolean isClickToEditButtonLinkPresent() {
        return selenium.isElementPresent(clickToEditLink);
    }


    /**
     * Click on click to edit link
     *
     * @throws InterruptedException exception
     */
    public void clickOnClickToEditLink() throws InterruptedException {
        selenium.clickOn(clickToEditLink);
    }

    /**
     * clear and Fill all budget amount detail and click on submit button
     *
     * @param data budget
     * @throws InterruptedException exception
     */

    public void clearAndFillBudgetAmountDetailAndClickOnSubmit(Budget data) throws InterruptedException {

        selenium.clearTextBoxUsingKeys(amountTextBox);
        selenium.enterText(amountTextBox, String.valueOf(data.getBudgetAmount()), false);
        selenium.focusOnElement(budgetSaveButton);
        selenium.clickOn(budgetSaveButton);

    }
}


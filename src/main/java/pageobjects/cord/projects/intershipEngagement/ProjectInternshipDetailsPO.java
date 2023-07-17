package pageobjects.cord.projects.intershipEngagement;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

import java.io.File;

public class ProjectInternshipDetailsPO extends BasePO {

    public ProjectInternshipDetailsPO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    @FindBy(xpath = "//button[contains(@class,'MuiButtonBase-root')]/following::h4[contains(text(),'Growth Plan Complete Date')]/following-sibling::h1")
    private WebElement growthPlanCompleteDateButton;

    @FindBy(xpath = "//button[contains(@class,'MuiButtonBase-root')]/following::h4[contains(text(),'Disbursement Complete Date')]/following-sibling::h1")
    private WebElement disbursementCompleteDateButton;

    @FindBy(name = "defined_file_version_uploader")
    private WebElement addGrowthPlanButton;

    @FindBy(xpath = "//input[@name='defined_file_version_uploader']/following-sibling::button")
    private WebElement disabledAddGrowthPlanButton;

    @FindBy(xpath = "//input[@type='file']/following-sibling::button/following::button[contains(@class,'MuiIconButton-root')][1]")
    private WebElement growthPlanInfoButton;

    @FindBy(xpath = "//span[contains(text(),'history')]//parent::div//parent::li//parent::ul")
    private WebElement growthPlanKebabMenu;

    @FindBy(name = "file-version-uploader")
    private WebElement addNewVersionButton;

    @FindBy(css = "#draggable-dialog-title~div div:last-child p")
    private WebElement uploadedFileText;

    @FindBy(xpath = "//button[contains(@class,'MuiButtonBase-root')]//p[contains(text(),'Methodologies')]")
    private WebElement methodologiesButton;

    @FindBy(xpath = "//p[contains(text(),'Methodologies')]/following-sibling::div")
    private WebElement methodologiesList;

    @FindBy(name = "planned")
    private WebElement certificationToggleButton;

    @FindBy(xpath = "//button[contains(@class,'MuiButton')]/span[contains(text(),'Edit dates')]")
    private WebElement editDatesButton;

    @FindBy(xpath = "//p[contains(text(),'Actual Date')]/following-sibling::h2")
    private WebElement actualDateText;

    @FindBy(xpath = "//p[contains(text(),'Estimated Date')]/following-sibling::p")
    private WebElement estimatedDateText;

    @FindBy(xpath = "//h4[contains(text(),'Mentor')]/following::div/div/following::button")
    private WebElement addMentorButton;

    @FindBy(xpath = "//div[contains(@class,'MuiAvatar')]/following-sibling::h4")
    private WebElement mentorText;

    private final By internshipDetailsButton = By.xpath("//button[contains(@class,'outlined')]");

    /**
     * Click on internship engagement Step button by div index
     *
     * @throws InterruptedException exception
     */
    public void clickInternshipEngagementStatusButton() throws InterruptedException {
        selenium.waitTillElementIsClickable(internshipDetailsButton);
        selenium.clickOn(selenium.waitTillAllElementsAreLocated(internshipDetailsButton).get(0));
    }

    /**
     * Click on start and end date button by div index
     *
     * @throws InterruptedException exception
     */
    public void clickInternshipStartEndDateButton() throws InterruptedException {
        selenium.waitTillElementIsClickable(internshipDetailsButton);
        selenium.clickOn(selenium.waitTillAllElementsAreLocated(internshipDetailsButton).get(1));
    }

    /**
     * Click on enter intern position button by div index
     *
     * @throws InterruptedException exception
     */
    public void clickEnterInternPositionButton() throws InterruptedException {
        selenium.clickOn(selenium.waitTillAllElementsAreLocated(internshipDetailsButton).get(2));
    }

    /**
     * Click on enter country of origin button by div index
     *
     * @throws InterruptedException exception
     */
    public void clickEnterCountryOfOriginButton() throws InterruptedException {
        selenium.waitTillElementIsClickable(internshipDetailsButton);
        selenium.clickOn(selenium.waitTillAllElementsAreLocated(internshipDetailsButton).get(3));
    }

    /**
     * Click on growth plan complete date button
     *
     * @throws InterruptedException exception
     */
    public void clickGrowthPlanCompleteDateButton() throws InterruptedException {
        selenium.clickOn(growthPlanCompleteDateButton);
    }

    /**
     * click on add growth plan button
     */
    public void clickOnAddGrowthPlanButton(String fileName) {

        String textFile = "src/main/resources/testData/" + fileName;
        addGrowthPlanButton.sendKeys(new File(textFile).getAbsolutePath());
    }

    /**
     * click on add growth info button and add new version file
     *
     * @throws InterruptedException exception
     */
    public void clickOnAddGrowthPlanInfoButtonAndAddNewVersionFile(String fileName) throws InterruptedException {

        selenium.clickOn(growthPlanInfoButton);
        String newVersionFile = "src/main/resources/testData/" + fileName;
        addNewVersionButton.sendKeys(new File(newVersionFile).getAbsolutePath());
        selenium.hardWait(5);
        growthPlanKebabMenu.sendKeys(Keys.ESCAPE);
    }

    /**
     * click On disbursement Complete date Button
     *
     * @throws InterruptedException exception
     */
    public void clickOnDisbursementCompleteDateButton() throws InterruptedException {
        selenium.clickOn(disbursementCompleteDateButton);
    }

    /**
     * click on methodologies button
     *
     * @throws InterruptedException exception
     */
    public void clickMethodologiesButton() throws InterruptedException {
        selenium.clickOn(methodologiesButton);
    }

    /**
     * click On certification toggle  button
     *
     * @throws InterruptedException exception
     */
    public void clickCertificationToggleButton() throws InterruptedException {
        selenium.click(certificationToggleButton);
    }

    /**
     * click On edit dates button
     *
     * @throws InterruptedException exception
     */
    public void clickEditDatesButton() throws InterruptedException {
        selenium.clickOn(editDatesButton);
    }

    /**
     * click On add mentor button
     *
     * @throws InterruptedException exception
     */
    public void clickAddMentorButton() throws InterruptedException {
        selenium.clickOn(addMentorButton);
    }

    /**
     * Get internship status button text by index
     *
     * @return internship status
     */
    public String getInternshipStatus() {
        return selenium.getText(selenium.waitTillAllElementsAreLocated(internshipDetailsButton).get(0));
    }

    /**
     * Get start and end date button text by index
     *
     * @return start and end date
     */
    public String getInternshipStartEndDate() {

        return selenium.getText(selenium.waitTillAllElementsAreLocated(internshipDetailsButton).get(1));

    }

    /**
     * Get intern position button text by index
     *
     * @return intern position
     */
    public String getInternPosition() {
        return selenium.getText(selenium.waitTillAllElementsAreLocated(internshipDetailsButton).get(2));
    }

    /**
     * Get location button text by index
     *
     * @return intern location name
     */
    public String getInternLocationName() {
        return selenium.getText(selenium.waitTillAllElementsAreLocated(internshipDetailsButton).get(3));
    }

    /**
     * Get growth plan complete date
     *
     * @return growth plan complete date
     */
    public String getGrowthPlanCompleteDate() {
        return selenium.getText(growthPlanCompleteDateButton);
    }

    /**
     * Get disbursement complete date
     *
     * @return disbursement complete date
     */
    public String getDisbursementCompleteDate() {
        return selenium.getText(disbursementCompleteDateButton);
    }

    /**
     * Get uploaded file text
     *
     * @return upload file text
     */

    public String getUploadedFileText() {
        return selenium.getText(uploadedFileText);
    }

    /**
     * Get methodologies text
     *
     * @return methodologies text
     */

    public String getMethodologiesText() {
        return selenium.getText(methodologiesList);
    }

    /**
     * Get actual date text
     *
     * @return actual date text
     */

    public String getActualDateText() {
        return selenium.getText(actualDateText);
    }

    /**
     * Get estimated date text
     *
     * @return estimated date text
     */

    public String getEstimatedDateText() {
        return selenium.getText(estimatedDateText);
    }

    /**
     * Get mentor text
     *
     * @return mentor text
     */

    public String getMentorText() {
        return selenium.getText(mentorText);
    }

    /**
     * get disabled edit button color
     *
     * @return disabled edit button color
     */
    public String getEditButtonColor() {
        return selenium.getElementCssValue(editDatesButton, "color");
    }

    /**
     * Is Growth plan Complete Date Button enabled ?
     *
     * @return boolean
     */
    public boolean isGrowthPlanCompleteDateButtonEnabled() {
        return selenium.isElementEnabled(growthPlanCompleteDateButton);
    }

    /**
     * Is disbursement Complete Date Button enabled ?
     *
     * @return boolean
     */
    public boolean isDisbursementCompleteDateButtonEnabled() {
        return selenium.isElementEnabled(disbursementCompleteDateButton);
    }

    /**
     * Is add growth plan Button enabled ?
     *
     * @return boolean
     */
    public boolean isGrowthPlanButtonEnabled() {
        return selenium.isElementEnabled(disabledAddGrowthPlanButton);
    }

    /**
     * Is internship add mentor button enabled ?
     *
     * @return boolean
     */
    public boolean isInternshipAddMentorButtonEnabled() {
        return selenium.isElementEnabled(addMentorButton);
    }

}


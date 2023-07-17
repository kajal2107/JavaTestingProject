package pageobjects.cord.projects;

import dataobjects.Project;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

import java.io.File;

public class ProjectDetailsPO extends BasePO {

    public ProjectDetailsPO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    @FindBy(id = "mui-component-select-post.type")
    private WebElement projectCategoryDropDown;

    @FindBy(id = "mui-component-select-post.shareability")
    private WebElement projectPostsShareabilityDropDown;

    @FindBy(name = "post.body")
    private WebElement projectPostsTextArea;

    @FindBy(xpath = "//h2[contains(@class,'MuiTypography-h2')]")
    private WebElement projectNameText;

    @FindBy(xpath = "//span[contains(text(),'Sensitivity')]")
    private WebElement sensitivityButton;

    @FindBy(xpath = "//input[contains(@type,'file')]")
    private WebElement uploadFileButton;

    @FindBy(xpath = "//div[@id='draggable-dialog-title']/following::p")
    private WebElement uploadedFileText;

    @FindBy(xpath = "//span[contains(text(),'Budget Details')]")
    private WebElement budgetDetails;

    @FindBy(xpath = "//span[contains(text(),'View Files')]")
    private WebElement viewFileDetails;

    @FindBy(xpath = "//span[contains(text(),'View Requests')]")
    private WebElement viewChangeRequestsDetails;

    @FindBy(xpath = "//p[contains(text(),'Team Members')]//parent::div")
    private WebElement teamMembersButton;

    @FindBy(xpath = "//div[contains(@class,'MuiGrid-item')]/following::p[contains(text(),'Partners')]")
    private WebElement partnershipsButton;

    @FindBy(xpath = "//button[contains(@aria-label,'Add Language Engagement')]")
    private WebElement addLanguageEngagementButton;

    @FindBy(xpath = "//button[contains(@aria-label,'edit project name')]")
    private WebElement editProjectButton;

    @FindBy(xpath = "//button[(@title='Pin Project (only affects you)')]")
    private WebElement pinProjectButton;

    @FindBy(xpath = "//button[contains(@aria-label,'Add Intern Engagement')]")
    private WebElement addInternEngagementButton;

    @FindBy(xpath = "//button[contains(@title,'Add Post')]")
    private WebElement addPostButton;

    @FindBy(xpath = "//button[contains(@class,'MuiButton-disableElevation')]")
    private WebElement postPostsButton;

    @FindBy(xpath = "//p[contains(text(),'Edit Info')]")
    private WebElement editButtonWithoutUser;

    @FindBy(xpath = "//h3[(text()='Posts')]/following::div[contains(@class,'MuiGrid-root')]/div[contains(@class,'MuiPaper-rounded')]//h4")
    private WebElement postCategoryText;

    @FindBy(xpath = "//h3[(text()='Posts')]/following::div[contains(@class,'MuiGrid-root')]/div[contains(@class,'MuiPaper-rounded')]//h4/following-sibling::div/p")
    private WebElement postsShareabilityText;

    @FindBy(xpath = "//h3[(text()='Posts')]/following::div[contains(@class,'MuiGrid-root')]/div[contains(@class,'MuiPaper-rounded')]//h4/following-sibling::div/parent::div/following-sibling::p")
    private WebElement postsText;

    @FindBy(xpath = "//h3[(text()='Posts')]/following::button[contains(@class,'MuiIconButton')]")
    private WebElement postsToggleButton;

    @FindBy(xpath = "//span[(text()='Edit Post')]")
    private WebElement postsEditButton;

    @FindBy(xpath = "//span[(text()='Delete Post')]")
    private WebElement postsDeleteButton;

    private final By projectDetailsButton = By.xpath("//button[contains(@class,'outlined')]");

    /**
     * Click on edit button
     *
     * @throws InterruptedException exception
     */
    public void clickOnEditButton() throws InterruptedException {
        selenium.clickOn(editProjectButton);
    }

    /**
     * Click on pin  button
     *
     * @throws InterruptedException exception
     */
    public void clickOnPinButton() throws InterruptedException {
        selenium.click(pinProjectButton);
    }

    /**
     * Get Project name
     *
     * @return Project name
     */
    public String getProjectName() {
        return selenium.getText(projectNameText);
    }

    /**
     * Get Location and Field Region button text by index
     *
     * @return Project Location And FieldRegion button text
     */
    public String getProjectLocationAndFieldRegion() {
        return selenium.getText(selenium.waitTillAllElementsAreLocated(projectDetailsButton).get(1));
    }

    /**
     * Click on start and end date button by div index
     *
     * @throws InterruptedException exception
     */
    public void clickProjectStartEndDateButton() throws InterruptedException {
        selenium.clickOn(selenium.waitTillAllElementsAreLocated(projectDetailsButton).get(2));
    }

    /**
     * Click on estimatedSubmission button by div index
     *
     * @throws InterruptedException exception
     */
    public void clickProjectEstimatedSubmissionButton() throws InterruptedException {
        selenium.clickOn(selenium.waitTillAllElementsAreLocated(projectDetailsButton).get(3));
    }

    /**
     * Get start end date button text by div index
     *
     * @return start and end date button text
     */
    public String getProjectStartEndDate() {
        return selenium.getText(selenium.waitTillAllElementsAreLocated(projectDetailsButton).get(2));
    }

    /**
     * Get estimated submission date button text by div index
     *
     * @return start and end date button text
     */
    public String getProjectEstimatedSubmissionDate() {
        return selenium.getText(selenium.waitTillAllElementsAreLocated(projectDetailsButton).get(3));
    }

    /**
     * Click on Location and Field Region button by index
     *
     * @throws InterruptedException exception
     */
    public void clickProjectLocationAndFieldRegionButton() throws InterruptedException {
        selenium.clickOn(selenium.waitTillAllElementsAreLocated(projectDetailsButton).get(1));
    }

    /**
     * Get Project sensitivity
     *
     * @return Project sensitivity
     */
    public String getProjectSensitivity() {
        String sensitivityXpathLocator = "//span[contains(text(),'Sensitivity')]";
        String sensitivity = selenium.getText((By.xpath(sensitivityXpathLocator)));
        sensitivity = sensitivity.split(" ")[0];    // Getting first substring of string
        return sensitivity;
    }

    /**
     * Click on Project sensitivity
     *
     * @throws InterruptedException exception
     */
    public void clickOnProjectSensitivityButton() throws InterruptedException {
        selenium.click(sensitivityButton);
    }

    /**
     * Click on Project Step by name
     *
     * @param projectStep project step
     * @throws InterruptedException exception
     */
    public void clickOnProjectStepByName(String projectStep) throws InterruptedException {
        String projectStepXpathSelector = "//span[contains(text(),'" + projectStep + "')]";
        selenium.clickOn(By.xpath(projectStepXpathSelector));
    }

    /**
     * Get Project step name
     *
     * @param stepName step name
     * @return Project step name
     */
    public String getProjectStepByName(String stepName) {
        String projectStepXpathSelector = "//span[contains(text(),'" + stepName + "')]";
        return selenium.getText(By.xpath(projectStepXpathSelector));
    }

    /**
     * click on budget detail Button
     *
     * @throws InterruptedException exception
     */
    public void clickOnBudgetButton() throws InterruptedException {
        selenium.clickOn(budgetDetails);
    }

    /**
     * click On TeamMember Button
     *
     * @throws InterruptedException exception
     */
    public void clickOnTeamMemberButton() throws InterruptedException {
        selenium.waitTillElementIsClickable(teamMembersButton);
        selenium.clickOn(teamMembersButton);
    }

    /**
     * click On view file  Button
     *
     * @throws InterruptedException exception
     */
    public void clickOnViewFileButton() throws InterruptedException {
        selenium.clickOn(viewFileDetails);
    }

    /**
     * click On view change requests Button
     *
     * @throws InterruptedException exception
     */
    public void clickOnViewChangeRequestButton() throws InterruptedException {
        selenium.clickOn(viewChangeRequestsDetails);
    }

    /**
     * click On Partners Button
     *
     * @throws InterruptedException exception
     */
    public void clickOnPartnershipsButton() throws InterruptedException {
        selenium.clickOn(partnershipsButton);
    }

    /**
     * click On upload file Button
     */
    public void clickOnUploadFileButton(String fileName) {
        String textFile = "src/main/resources/testData/" + fileName;
        uploadFileButton.sendKeys(new File(textFile).getAbsolutePath());

    }

    /**
     * click On add posts button
     *
     * @throws InterruptedException exception
     */
    public void clickOnAddPostsButton() throws InterruptedException {
        selenium.clickOn(addPostButton);
    }

    /**
     * click On posts category dropDown
     *
     * @throws InterruptedException exception
     */
    public void clickOnPostsCategoryDropDown() throws InterruptedException {
        selenium.clickOn(projectCategoryDropDown);
    }

    /**
     * click On posts toggle button
     *
     * @throws InterruptedException exception
     */
    public void clickOnPostsShareabilityDropDown() throws InterruptedException {
        selenium.click(projectPostsShareabilityDropDown);
    }

    /**
     * Select project posts shareability
     *
     * @param postsShareability posts shareability
     * @throws InterruptedException exception
     */
    public void clickOnProjectPostsShareability(String postsShareability) throws InterruptedException {
        String projectPostsShareabilityXpathSelector = "//li[(text()='" + postsShareability + "')]";
        selenium.clickOn(By.xpath(projectPostsShareabilityXpathSelector));
    }

    /**
     * Select project posts category
     *
     * @param postsCategory posts Category
     * @throws InterruptedException exception
     */
    public void clickOnProjectPostsCategory(String postsCategory) throws InterruptedException {
        String projectPostsCategoryXpathSelector = "//li[(text()='" + postsCategory + "')]";
        selenium.clickOn(By.xpath(projectPostsCategoryXpathSelector));
    }

    /**
     * get uploaded file name
     *
     * @return file name
     */
    public String getUploadedFileName() {
        return selenium.getText(uploadedFileText);
    }

    /**
     * get project post category text
     *
     * @return post category text
     */
    public String getPostCategoryText() {
        return selenium.getText(postCategoryText);
    }

    /**
     * get project post toggleButton text
     *
     * @return post category text
     */
    public String getPostsShareabilityText() {
        return selenium.getText(postsShareabilityText);
    }

    /**
     * get project post text
     *
     * @return post text
     */
    public String getPostsText() {
        return selenium.getText(postsText);
    }

    /**
     * click On intern engagement Button
     *
     * @throws InterruptedException exception
     */
    public void clickOnInternEngagementButton() throws InterruptedException {
        selenium.clickOn(addInternEngagementButton);
    }

    /**
     * click On language engagement Button
     *
     * @throws InterruptedException exception
     */
    public void clickOnLanguageEngagementButton() throws InterruptedException {
        selenium.clickOn(addLanguageEngagementButton);
    }

    /**
     * Click on posts toggle button
     *
     * @throws InterruptedException exception
     */
    public void clickOnPostsToggleButton() throws InterruptedException {
        selenium.clickOn(postsToggleButton);
    }

    /**
     * Click on posts edit button
     *
     * @throws InterruptedException exception
     */
    public void clickOnPostsEditButton() throws InterruptedException {
        selenium.clickOn(postsEditButton);
    }

    /**
     * Click on posts delete button
     *
     * @throws InterruptedException exception
     */
    public void clickOnPostsDeleteButton() throws InterruptedException {
        selenium.clickOn(postsDeleteButton);
    }

    /**
     * Get Internship engagement name
     *
     * @param internshipName Internship engagement name
     * @return Internship engagement name
     */
    public String getInternshipName(String internshipName) {
        String internshipByName = "//div[contains(@class,'MuiPaper-root')]/following::div[contains(@class,'MuiGrid-item')]/h4[contains(text(),'" + internshipName + "')]";
        return selenium.getText(By.xpath(internshipByName));
    }

    /**
     * Get Language engagement name
     *
     * @param languageName Language engagement name
     * @return Language engagement name
     */
    public String getLanguageEngagementName(String languageName) {
        String languageByName = "//div[contains(@class,'MuiGrid-direction')]/div[contains(@class,'MuiGrid')]/h4[text()='" + languageName + "']";
        return selenium.getText(By.xpath(languageByName));
    }

    /**
     * click On Edit Internship Button
     *
     * @param internshipName InternshipName
     * @throws InterruptedException exception
     */
    public void clickOnEditInternshipButton(String internshipName) throws InterruptedException {
        String EditButtonByInternshipName = "//h4[text()='" + internshipName + "']/ancestor::a[contains(@class,'MuiCardActionArea-root')]/following-sibling::div//span[text()='View Details']";
        selenium.clickOn(By.xpath(EditButtonByInternshipName));
    }

    /**
     * click On Edit Internship Button
     *
     * @throws InterruptedException exception
     */
    public void clickOnEditInternshipButtonWithOutUser() throws InterruptedException {
        selenium.clickOn(editButtonWithoutUser);
    }

    /**
     * click On view details  Button
     *
     * @param languageName language Name
     * @throws InterruptedException exception
     */
    public void clickOnViewDetailsLanguageEngagementButton(String languageName) throws InterruptedException {
        String ViewDetailsButtonByLanguageEngagementName = "//h4[text()='" + languageName + "']/ancestor::a[contains(@class,'MuiCardActionArea-root')]/following-sibling::div//span[text()='View Details']";
        selenium.clickOn(By.xpath(ViewDetailsButtonByLanguageEngagementName));
    }

    /**
     * Is estimated submission date Button enabled ?
     *
     * @return boolean
     */
    public boolean isEstimatedSubmissionDateButtonEnabled() {
        return selenium.waitTillAllElementsAreLocated(projectDetailsButton).get(3).isEnabled();
    }

    /**
     * Is location field region Button enabled ?
     *
     * @return boolean
     */
    public boolean isLocationFieldRegionButtonEnabled() {
        return selenium.waitTillAllElementsAreLocated(projectDetailsButton).get(1).isEnabled();
    }

    /**
     * Is Project edit icon present ?
     *
     * @return boolean
     */
    public boolean isEditIconPresent() {
        return selenium.isElementPresent(editProjectButton);
    }

    /**
     * Is Project language engagement add button present ?
     *
     * @return boolean
     */
    public boolean isProjectLanguageEngagementAddButtonPresent() {
        return selenium.isElementPresent(addLanguageEngagementButton);
    }

    /**
     * Is Project internship engagement add button present ?
     *
     * @return boolean
     */
    public boolean isProjectInternshipEngagementAddButtonPresent() {
        return selenium.isElementPresent(addInternEngagementButton);
    }

    /**
     * Is posts present ?
     *
     * @param postName post Name
     * @return boolean
     */
    public boolean isPostsPresent(String postName) {
        String xpathLocatorFileFolder = "//h4[text()='" + postName + "']";
        return selenium.isElementPresent(By.xpath(xpathLocatorFileFolder));
    }

    /**
     * clear and Fill post box
     *
     * @param data project
     * @throws InterruptedException exception
     */

    public void clearAndFillPostsBox(Project data) throws InterruptedException {

        selenium.clearTextBoxUsingKeys(projectPostsTextArea);
        selenium.enterText(projectPostsTextArea, data.getProjectPosts(), false);
        selenium.clickOn(postPostsButton);
    }

}
package pageobjects.cord.projects.languageEngagement.goal;

import dataobjects.Goal;
import enums.goal.GoalType;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;
import utilities.Constants;

public class CreateGoalPO extends BasePO {

    public CreateGoalPO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    @FindBy(name = "product.scriptureReferences")
    private WebElement goalPartialKnownBookTextBox;

    @FindBy(name = "product.unspecifiedScripture.totalVerses")
    private WebElement goalPartialUnKnownBookTextBox;

    @FindBy(xpath = "//input[@placeholder ='Search for a story by name']")
    private WebElement searchStoryTextBox;

    @FindBy(xpath = "//input[@placeholder ='Search for a film by name']")
    private WebElement searchFilmTextBox;

    @FindBy(xpath = "//input[@placeholder = 'Search for an EthnoArt by name']")
    private WebElement searchEthnoArtTextBox;

    @FindBy(xpath = "//label[text()='Book']//following-sibling::div/input")
    private WebElement searchBookTextBox;

    @FindBy(xpath = "//label[text()='Total verse count']/following-sibling::p")
    private WebElement totalVersesLabel;

    @FindBy(xpath = "//label[text()='Completion means...']/following::input")
    private WebElement completionDescriptionTextBox;

    @FindBy(name = "product.progressTarget")
    private WebElement progressTargetTextBox;

    @FindBy(name = "product.title")
    private WebElement goalTitleTextBox;

    @FindBy(name = "product.description")
    private WebElement goalDescriptionTextBox;

    @FindBy(xpath = "//li[contains(@class,'MuiAutocomplete-option')][1]")
    private WebElement addGoalTypeName;

    @FindBy(xpath = "//h2[text()='Edit Goal']//following::div[contains(@class,'Mui-disabled')][1]")
    private WebElement disabledGoalDetails;

    @FindBy(xpath = "//p[contains(text(),'Pick the partners that are producing each distribution method')]/following::input[contains(@class,'MuiFilledInput')]")
    private WebElement distributionMethodsTextBox;

    private final By saveGoalButton = By.xpath("//span[text()='Save Goal']");

    private final By deleteGoalButton = By.xpath("//span[text()='Delete Goal']");

    /**
     * Select goal type by name
     *
     * @param goalType goalType
     * @throws InterruptedException exception
     */
    public void selectGoalTypeByName(String goalType) throws InterruptedException {
        String goalTypeXpathSelector = "//span[text()='" + goalType + "']";
        selenium.clickOn(By.xpath(goalTypeXpathSelector));
    }

    /**
     * Click on goal type dropdown by name
     *
     * @param goalDropdown goalDropdown
     * @throws InterruptedException exception
     */
    public void clickOnGoalTypeDropdownByName(String goalDropdown) throws InterruptedException {
        String goalDropdownXpathSelector = "//h4[text()='" + goalDropdown + "']";
        selenium.waitTillElementIsClickable(By.xpath(goalDropdownXpathSelector));
        selenium.clickOn(By.xpath(goalDropdownXpathSelector));
    }

    /**
     * select goal scripture ref old testament by name
     *
     * @param goalData goalData
     * @throws InterruptedException exception
     */
    public void selectGoalScriptureRefOldTestamentByName(Goal goalData) throws InterruptedException {
        selenium.click(searchBookTextBox);
        String bookName = goalData.getOldScriptureReference();
        String goalOldTestamentBookXpath = "//ul[contains(@class,'MuiAutocomplete-groupUl')]/li[text()= '" + bookName + "']";
        selenium.clickOn(By.xpath(goalOldTestamentBookXpath));
    }

    /**
     * select partial Known radio button with chapter
     *
     * @param goalPartialKnownRadioButton GoalPartialKnownRadioButton
     * @throws InterruptedException exception
     */
    public void selectGoalPartialKnownRadioButtonWithChapters(String goalPartialKnownRadioButton, String[] chapters) throws InterruptedException {

        String goalPartialKnownRadioButtonXpathSelector = "//span[text()='" + goalPartialKnownRadioButton + "']";
        selenium.clickOn(By.xpath(goalPartialKnownRadioButtonXpathSelector));

        for (String s : chapters) {
            selenium.enterText(goalPartialKnownBookTextBox, s, false);
            goalPartialKnownBookTextBox.sendKeys(Keys.ENTER);
        }
    }

    /**
     * select partial unKnown radio button with verses
     *
     * @param goalPartialUnKnownRadioButton GoalPartialUnKnownRadioButton
     * @throws InterruptedException exception
     */
    public void selectGoalPartialUnKnownRadioButtonWithTotalVerses(String goalPartialUnKnownRadioButton, Integer verses) throws InterruptedException {
        String goalPartialKnownRadioButtonXpathSelector = "//span[text()='" + goalPartialUnKnownRadioButton + "']";
        selenium.clickOn(By.xpath(goalPartialKnownRadioButtonXpathSelector));
        selenium.enterText(goalPartialUnKnownBookTextBox, verses.toString(), false);
    }

    /**
     * select goal scripture ref new testament by name
     *
     * @param goalData goalData
     * @throws InterruptedException exception
     */
    public void selectGoalScriptureRefNewTestamentByName(Goal goalData) throws InterruptedException {
        selenium.click(searchBookTextBox);
        String bookName = goalData.getNewScriptureReference();
        String goalNewTestamentXpathSelector = "//ul[contains(@class,'MuiAutocomplete-groupUl')]/li[text()= '" + bookName + "']";
        goalData.setNewScriptureReference((bookName));
        selenium.clickOn(By.xpath(goalNewTestamentXpathSelector));
    }

    /**
     * select goal scripture ref new testament by name
     *
     * @throws InterruptedException exception
     */
    public void selectGoalScriptureRefBookByName() throws InterruptedException {
        selenium.click(searchBookTextBox);
        String book = Constants.scriptureReferenceBook;
        String goalNewTestamentXpathSelector = "//ul[contains(@class,'MuiAutocomplete-groupUl')]/li[text()= '" + book + "']";
        selenium.clickOn(By.xpath(goalNewTestamentXpathSelector));
    }

    /**
     * Select goal mediums by name
     *
     * @param GoalDistributionMethods GoalDistributionMethods
     * @throws InterruptedException exception
     */
    public void selectGoalDistributionMethodsByName(String[] GoalDistributionMethods) throws InterruptedException {
        for (String goalDistributionMethods : GoalDistributionMethods) {
            String goalDistributionMethodsXpathSelector = "//h4[contains(text(),'Choose ')]/following::span[text()='" + goalDistributionMethods + "']";
            selenium.clickOn(By.xpath(goalDistributionMethodsXpathSelector));
        }
    }

    /**
     * Select goal partner by name
     *
     * @param partner partner
     * @throws InterruptedException exception
     */
    public void selectPartnerByName(String partner) throws InterruptedException {
        String xpathPartnerName = "//ul[contains(@class,'MuiAutocomplete-listbox')]/li[text()= '" + partner + "']";
        selenium.clickOn(By.xpath(xpathPartnerName));
    }

    /**
     * Select goal purposes by name
     *
     * @param partnerName partnerName
     * @throws InterruptedException exception
     */
    public void selectGoalDistributionMethodsByName(String partnerName) throws InterruptedException {
        selenium.clearTextBoxUsingKeys(distributionMethodsTextBox);
        selenium.enterText(distributionMethodsTextBox, partnerName, true);
        selectPartnerByName(partnerName);
    }

    /**
     * Select goal progress measurement by name
     *
     * @param GoalProgressMeasurement Goal Progress Measurement
     * @throws InterruptedException exception
     */
    public void selectGoalProgressMeasurementByName(String GoalProgressMeasurement) throws InterruptedException {
        String goalProgressMeasurementXpathSelector = "//span[text()='" + GoalProgressMeasurement + "']";
        selenium.clickOn(By.xpath(goalProgressMeasurementXpathSelector));
    }

    /**
     * Select methodology by type and name
     *
     * @param methodologyType  methodologyType
     * @param methodologyValue methodologyValue
     * @throws InterruptedException Interrupted Exception
     */
    public void selectGoalMethodologyByTypeAndValue(String methodologyType, String methodologyValue) throws InterruptedException {
        String goalMethodologyXpathSelector = "//p[text()='" + methodologyType + "']//parent::div//span[text()='" + methodologyValue + "']";
        selenium.clickOn(By.xpath(goalMethodologyXpathSelector));
    }

    /**
     * deselect methodology by type and name
     *
     * @param methodologyType methodologyType
     * @param stepsValue      stepsValue
     * @throws InterruptedException interrupted exception
     */
    public void deSelectGoalStepsByNameMethodologyType(String methodologyType, String[] stepsValue) throws InterruptedException {
        for (String s : stepsValue) {
            String goalStepXpathSelector = "//p[text()='" + methodologyType + "']//following::h4/following::button[@label='" + s + "']";
            selenium.clickOn(By.xpath(goalStepXpathSelector));
        }
    }

    /**
     * Select methodology by type and name
     *
     * @param methodologyType methodologyType
     * @param stepsValue      stepsValue
     * @throws InterruptedException interrupted exception
     */
    public void selectGoalStepsByNameMethodologyType(String methodologyType, String[] stepsValue) throws InterruptedException {
        for (String s : stepsValue) {
            String goalStepXpathSelector = "//p[text()='" + methodologyType + "']//following::h4/following::button[@label='" + s + "']";
            selenium.clickOn(By.xpath(goalStepXpathSelector));
        }
    }

    /**
     * click On save button and save the goal
     *
     * @throws InterruptedException exception
     */
    public void clickOnSaveGoalButton() throws InterruptedException {
        selenium.clickOn(saveGoalButton);
        selenium.waitTillElementsCountIsLessThan(saveGoalButton, 1);
    }

    /**
     * click On delete button and delete the goal
     *
     * @throws InterruptedException exception
     */
    public void clickOnDeleteGoalButton() throws InterruptedException {
        selenium.clickOn(deleteGoalButton);
        selenium.waitTillElementsCountIsLessThan(deleteGoalButton, 1);
    }

    /**
     * Fill goal type name
     *
     * @param goalData goal object
     * @throws InterruptedException exception
     */
    public void enterGoalNameAndAddGoal(GoalType goalType, Goal goalData) throws InterruptedException {

        if (goalType == GoalType.Story) {
            selenium.clearTextBoxUsingKeys(searchStoryTextBox);
            selenium.enterText(searchStoryTextBox, goalData.getGoalStoryName(), false);

        } else if (goalType == GoalType.Film) {
            selenium.clearTextBoxUsingKeys(searchFilmTextBox);
            selenium.enterText(searchFilmTextBox, goalData.getGoalFilmName(), false);

        } else if (goalType == GoalType.EthnoArt) {
            selenium.clearTextBoxUsingKeys(searchEthnoArtTextBox);
            selenium.enterText(searchEthnoArtTextBox, goalData.getGoalEthnoArtName(), false);
        }
        selenium.clickOn(addGoalTypeName);
    }

    /**
     * Fill progress target detail
     *
     * @param goalData goal data
     */

    public void fillProgressTargetDetail(Goal goalData) {
        selenium.clearTextBoxUsingKeys(progressTargetTextBox);
        selenium.enterText(progressTargetTextBox, goalData.getGoalProgressTarget().toString(), false);
    }

    /**
     * Fill other goal title and description detail
     *
     * @param goalData goal object
     */
    public void fillOtherGoalTitleAndDescriptionDetail(Goal goalData) {
        selenium.clearTextBoxUsingKeys(goalTitleTextBox);
        selenium.enterText(goalTitleTextBox, goalData.getGoalOtherTitle(), false);
        selenium.clearTextBoxUsingKeys(goalDescriptionTextBox);
        selenium.enterText(goalDescriptionTextBox, goalData.getGoalOtherDescription(), false);
    }

    /**
     * Fill goal completion description detail
     *
     * @param goalData goal object
     */
    public void fillCompletionDescriptionDetail(Goal goalData) {
        selenium.clearTextBoxUsingKeys(completionDescriptionTextBox);
        selenium.enterText(completionDescriptionTextBox, goalData.getGoalCompletionDescription(), false);
    }

    /**
     * Search and add goal type name
     *
     * @param searchGoalData goal object
     * @throws InterruptedException exception
     */
    public void searchGoalNameAndAddGoal(GoalType goalType, Goal searchGoalData) throws InterruptedException {

        if (goalType == GoalType.Story) {
            selenium.clearTextBoxUsingKeys(searchStoryTextBox);
            selenium.enterText(searchStoryTextBox, searchGoalData.getGoalStoryName(), false);
            searchGoalData.setGoalStoryName(addGoalTypeName.getText());

        } else if (goalType == GoalType.Film) {
            selenium.clearTextBoxUsingKeys(searchFilmTextBox);
            selenium.enterText(searchFilmTextBox, searchGoalData.getGoalFilmName(), false);
            searchGoalData.setGoalFilmName(addGoalTypeName.getText());

        } else if (goalType == GoalType.EthnoArt) {
            selenium.clearTextBoxUsingKeys(searchEthnoArtTextBox);
            selenium.enterText(searchEthnoArtTextBox, searchGoalData.getGoalEthnoArtName(), false);
            searchGoalData.setGoalEthnoArtName(addGoalTypeName.getText());
        }
        selenium.clickOn(addGoalTypeName);
    }

    /**
     * get disabled goal details color
     *
     * @return disabled goal details color
     */
    public String getDisabledEditGoalDetailsColor() {
        return selenium.getElementCssValue(disabledGoalDetails, "color");
    }

    /**
     * Get goal total verses text
     *
     * @return goal total verses text
     */

    public String getGoalTotalVersesText() {
        return selenium.getText(totalVersesLabel);
    }

}



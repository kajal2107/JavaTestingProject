package cord.languages;

import base.BaseTest;
import datafactory.LanguageData;
import dataobjects.Language;
import enums.menu.CreateItemTypes;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import pageobjects.cord.account.LoginPO;
import pageobjects.cord.common.HeaderPO;
import pageobjects.cord.common.LeftNavPO;
import pageobjects.cord.languages.CreateUpdateLanguagePO;
import pageobjects.cord.languages.LanguageDetailPO;
import pageobjects.cord.languages.LanguagesPO;
import utilities.Constants;

public class CreateLanguageTests extends BaseTest {

    /*Test 1 : Verify that user can create language successfully*/
    @Test
    public void verifyUserCanCreateLanguageSuccessfully() throws InterruptedException {

        LoginPO login = new LoginPO(driver);
        LeftNavPO leftNav = new LeftNavPO(driver);
        CreateUpdateLanguagePO createUpdateLanguage = new CreateUpdateLanguagePO(driver);
        LanguagesPO languages = new LanguagesPO(driver);
        LanguageDetailPO languageDetail = new LanguageDetailPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);

        Reporter.log("Step 1 - Navigate to admin login page");
        selenium.navigateToPage(Constants.URL);

        Reporter.log("Step 2 - Entering valid sign in details and login to application");
        login.fillLoginDetailsAndPerformLogin(adminUser.getEmail(), adminUser.getPassword());

        Reporter.log("Step 3- Click on crate new item and select Language menu");
        leftNav.clickOnCreateNewItemAndSelectType(CreateItemTypes.Language.toString());

        Reporter.log("Step 4- Enter valid details and click on submit button");
        Language data = new LanguageData().getLanguageData();
        createUpdateLanguage.clearAndFillLanguageDetails(data);

        Reporter.log("Step 5- Verify that language is created successfully by comparing language details");
        leftNav.clickOnLanguagesMenu();

        leftNav.clickOnLanguagesMenu();
        if (!languages.isLanguagePresent(data.getLanguageName())) {
            headerPO.searchItemGloballyByName(data.getLanguageName());
        }
        languages.clickOnLanguageName(data.getLanguageName());

        String hasExternalFirstScriptureCheckedText = Constants.hasExternalFirstScriptureCheckedMessage;
        Assert.assertEquals(data.getPubliicName(), languageDetail.getLanguageName(), "language name doesn't match : ");
        Assert.assertEquals(data.getSensitivity().toString(), languageDetail.getSensitivity(), "language sensitivity doesn't match : ");
        Assert.assertEquals(data.isDialect(), languageDetail.isDialectPresent(), "Language Dialect doesn't match");
        Assert.assertEquals(data.isLeastOfThese(), languageDetail.isLeastOfThesePresent(), "Language Least of These doesn't match");
        Assert.assertEquals(data.isSignLanguage(), languageDetail.isSignLanguagePresent(), "Language Sign Language doesn't match");
        Assert.assertEquals(data.getPronunciationGuide(), languageDetail.getPronunciationGuide(), " Language pronunciation guide doesn't match");
        Assert.assertEquals(data.getEthnologueName(), languageDetail.getEthnologueName(), " Language ethnologue name doesn't match");
        Assert.assertEquals(data.getSignLanguageCode(), languageDetail.getSignLanguageCode(), " Language sign language code doesn't match");
        Assert.assertEquals(data.getProvisionalCode(), languageDetail.getProvisionalCode(), " Language provisional code doesn't match");
        Assert.assertEquals(data.getRegistryOfDialectsCode(), languageDetail.getRegistryOfDialectsCodeText(), " Language registry of dialects code doesn't match");
        Assert.assertEquals(data.getPopulation(), languageDetail.getPopulationText(), " Language population doesn't match");
        Assert.assertEquals(hasExternalFirstScriptureCheckedText, languageDetail.getHasExternalFirstScriptureText(), "Language has external firstScripture doesn't match");
        Assert.assertEquals(data.getSponsorEstimatedEndFY(), languageDetail.getEndFiscalYear(), " Language end fiscal year doesn't match");

    }

    /*Test 2 : Verify mandatory fields validation messages are displayed for Name/Public Name fields*/
    @Test
    public void verifyValidationMessageDisplayedAllMandatoryFields() throws InterruptedException {

        LoginPO login = new LoginPO(driver);
        LeftNavPO leftNav = new LeftNavPO(driver);
        CreateUpdateLanguagePO language = new CreateUpdateLanguagePO(driver);

        Reporter.log("Step 1 - Navigate to admin login page");
        selenium.navigateToPage(Constants.URL);

        Reporter.log("Step 2 - Entering valid sign in details and login to application");
        login.fillLoginDetailsAndPerformLogin(adminUser.getEmail(), adminUser.getPassword());

        Reporter.log("Step 3- Click on crate new item and select Language menu");
        leftNav.clickOnCreateNewItemAndSelectType(CreateItemTypes.Language.toString());

        Reporter.log("Step 4 - Click on 'Submit' button without entering any detail");
        language.clickOnSubmitButton();

        Reporter.log("Step 5- Verify that validation messages are displayed for name/displayName fields ");
        String expectedMessage = Constants.requiredMessage;
        Assert.assertEquals(language.getNameMandatoryFieldText(), expectedMessage, "Name field validation message doesn't match");
        Assert.assertEquals(language.getPublicNameMandatoryFieldText(), expectedMessage, "Public Name filed validation  message doesn't match");
    }

}

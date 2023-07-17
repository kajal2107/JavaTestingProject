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

public class UpdateLanguageTests extends BaseTest {

    /*Test 1 : Verify that user can update language successfully*/
    @Test
    public void verifyUserCanUpdateLanguageSuccessfully() throws InterruptedException {

        LoginPO login = new LoginPO(driver);
        LeftNavPO leftNav = new LeftNavPO(driver);
        CreateUpdateLanguagePO language = new CreateUpdateLanguagePO(driver);
        LanguagesPO languages = new LanguagesPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);
        LanguageDetailPO languageDetail = new LanguageDetailPO(driver);

        Reporter.log("Step 1 - Navigate to admin login page");
        selenium.navigateToPage(Constants.URL);

        Reporter.log("Step 2 - Entering valid sign in details and login to application");
        login.fillLoginDetailsAndPerformLogin(adminUser.getEmail(), adminUser.getPassword());

        Reporter.log("Step 3- Click on crate new item and select Language menu");
        leftNav.clickOnCreateNewItemAndSelectType(CreateItemTypes.Language.toString());

        Reporter.log("Step 4 - Enter valid details and click on submit button");
        Language data = new LanguageData().getLanguageData();
        language.clearAndFillLanguageDetails(data);

        Reporter.log("Step 5 - Go to Languages listing page and click on recently created Language item");
        leftNav.clickOnLanguagesMenu();

        if (!languages.isLanguagePresent(data.getLanguageName())) {
            headerPO.searchItemGloballyByName(data.getLanguageName());
        }
        languages.clickOnLanguageName(data.getLanguageName());

        Reporter.log("Step 6 - Click on edit icon and fill the language details");
        languageDetail.clickOnEditButton();
        language.clearAndFillLanguageDetails(data);

        Reporter.log("Step 7 - Verify updated language details");
        String hasExternalFirstScriptureUnCheckedText = Constants.hasExternalFirstScriptureUnCheckedMessage;

        Assert.assertEquals(data.getPubliicName(), languageDetail.getLanguageName(), "language name doesn't match : ");
        Assert.assertEquals(data.getSensitivity().toString(), languageDetail.getSensitivity(), "language sensitivity doesn't match : ");
        Assert.assertFalse(languageDetail.isDialectPresent(), "Language dialect doesn't match");
        Assert.assertFalse(languageDetail.isLeastOfThesePresent(), "Language least of these doesn't match");
        Assert.assertFalse(languageDetail.isSignLanguagePresent(), "Language sign language doesn't match");
        Assert.assertEquals(data.getPronunciationGuide(), languageDetail.getPronunciationGuide(), " Language pronunciation guide doesn't match");
        Assert.assertEquals(data.getEthnologueName(), languageDetail.getEthnologueName(), " Language ethnologue name doesn't match");
        Assert.assertEquals(data.getProvisionalCode(), languageDetail.getProvisionalCode(), " Language provisional code doesn't match");
        Assert.assertEquals(data.getRegistryOfDialectsCode(), languageDetail.getRegistryOfDialectsCodeText(), " Language registry of dialects code doesn't match");
        Assert.assertEquals(data.getPopulation(), languageDetail.getPopulationText(), " Language population doesn't match");
        Assert.assertEquals(data.getSponsorEstimatedEndFY(), languageDetail.getEndFiscalYear(), " Language end fiscal year doesn't match");
        Assert.assertEquals(hasExternalFirstScriptureUnCheckedText,languageDetail.getHasExternalFirstScriptureText(), " Language has external firstScripture doesn't match");

    }

}

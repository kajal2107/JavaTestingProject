package pageobjects.cord.languages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.base.BasePO;

public class LanguagesPO extends BasePO {

    public LanguagesPO(WebDriver driver) {
        super(driver);
    }

    /**
     * Is language  present ?
     *
     * @param languageName language name
     * @return language name
     */
    public boolean isLanguagePresent(String languageName) {
        String xpathLocator = "//h2[contains(text(),'" + languageName + "')]";
        return selenium.isElementPresent(By.xpath(xpathLocator));
    }

    /**
     * Get ethnologueCode  by language name
     *
     * @return ethnologueCode
     */
    public String getLanguageEthnologueCode(String languageName) {
        String EthnologueCodeByLanguage = "//h2[contains(text(),'" + languageName + "')]/following::span[contains(@class,'colorTextSecondary')][2]";
        return selenium.getText(By.xpath(EthnologueCodeByLanguage));
    }

    /**
     * Get registryOfCode  by language name
     *
     * @return registryOfCode
     */
    public String getLanguageRegistryOfDialectsCode(String languageName) {
        String RegistryOfDialectsCodeByLanguage = "//h2[contains(text(),'" + languageName + "')]/following::span[contains(@class,'colorTextSecondary')][4]";
        return selenium.getText(By.xpath(RegistryOfDialectsCodeByLanguage));
    }

    /**
     * Get population  by language name
     *
     * @return population
     */
    public String getLanguagePopulation(String languageName) {
        String RodByLanguage = "//h2[contains(text(),'" + languageName + "')]/following-sibling::div/div/h3";
        return selenium.getText(By.xpath(RodByLanguage));
    }

    /**
     * Get name  by language index
     *
     * @return name
     */
    public String getLanguageName(int languageIndex) {

        String NameByIndex = "//h2[contains(@class, 'LanguageListItemCard')]";
        return selenium.getText(selenium.waitTillAllElementsAreLocated(By.xpath(NameByIndex)).get(languageIndex - 1));
    }

    /**
     * Get ethnologueName  by language index
     *
     * @return name
     */
    public String getLanguageEthnologueName(int languageIndex) {

        String EthnologueNameByIndex = "//span[contains(@class, 'MuiTypography')]/following-sibling::span[contains(@class, 'colorTextSecondary')]";
        return selenium.getText(selenium.waitTillAllElementsAreLocated(By.xpath(EthnologueNameByIndex)).get(languageIndex - 1));
    }

    /**
     * Get rodNumber  by language index
     *
     * @return rodNumber
     */
    public String getLanguageRodNumber(int languageIndex) {

        String RodNumberByIndex = "//span[contains(@class, 'MuiTypography')]/following-sibling::span[contains(@class, 'colorTextSecondary')]";
        return selenium.getText(selenium.waitTillAllElementsAreLocated(By.xpath(RodNumberByIndex)).get(languageIndex - 1));
    }

    /**
     * Get population  by language index
     *
     * @return population
     */
    public String getLanguagePopulation(int languageIndex) {

        String PopulationByIndex = "//p[contains(@class, 'colorTextSecondary')]/following-sibling::h3[contains(@class, 'h3')]";
        return selenium.getText(selenium.waitTillAllElementsAreLocated(By.xpath(PopulationByIndex)).get(languageIndex - 1));
    }

    /**
     * click on Language by name
     */
    public void clickOnLanguageName(String languageName) throws InterruptedException {

        String projectXpathSelector = "//h4[text()='" + languageName + "']";
        selenium.clickOn(By.xpath(projectXpathSelector));
    }


}


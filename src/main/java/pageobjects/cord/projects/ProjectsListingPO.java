package pageobjects.cord.projects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.base.BasePO;

public class ProjectsListingPO extends BasePO {

    public ProjectsListingPO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    /**
     * Click on Project sub tabs
     *
     * @param subTabsName sub tabs name
     * @throws InterruptedException exception
     */

    public void clickOnProjectSubTabs(String subTabsName) throws InterruptedException {

        String projectSubTabsXpathSelector = "//span[text()='" + subTabsName + "']";
        selenium.clickOn(By.xpath(projectSubTabsXpathSelector));
    }

    /**
     * Click on Project name
     *
     * @param projectName project name
     * @throws InterruptedException exception
     */

    public void clickOnProjectName(String projectName) throws InterruptedException {

        String projectXpathSelector = "//h4[text()='" + projectName + "']";
        selenium.clickOn(By.xpath(projectXpathSelector));
    }

    /**
     * Is project present ?
     *
     * @param projectName project name
     * @return boolean
     */
    public boolean isProjectPresent(String projectName) {
        String xpathLocator = "//h4[text()='" + projectName + "']";
        return selenium.isElementPresent(By.xpath(xpathLocator));
    }

    /**
     * Get project pinned button color by project name
     *
     * @param projectName project name
     * @return pinned button color
     */
    public String getProjectPinnedButtonColor(String projectName) {
        WebElement e = driver.findElement(By.xpath("//h4[text()='" + projectName + "']/following::div/following::button[contains(@title,'Unpin Project')]"));
        return selenium.getElementCssValue(e, "color");
    }

    /**
     * Get date by project name
     *
     * @param projectName project name
     * @return date
     */
    public String getProjectDate(String projectName) {
        String dateByProjectName = "//h4[text()='" + projectName + "']/following::div[contains(@class,'ProjectListItemCard-rightContent')]//span[contains(@class,'colorPrimary')]";
        return selenium.getText(By.xpath(dateByProjectName));
    }

    /**
     * Get name by project index
     *
     * @param projectIndex index
     * @return name
     */
    public String getProjectName(int projectIndex) {
        String nameByProjectIndex = "//h4[starts-with(@class, 'MuiTypography')]";
        return selenium.getText(selenium.waitTillAllElementsAreLocated(By.xpath(nameByProjectIndex)).get(projectIndex - 1));
    }

    /**
     * Get date by project index
     *
     * @param projectIndex index
     * @return date
     */
    public String getProjectDate(int projectIndex) {
        String dateByProjectIndex = "//span[contains(@class, 'colorPrimary')]";
        return selenium.getText(selenium.waitTillAllElementsAreLocated(By.xpath(dateByProjectIndex)).get(projectIndex - 1));
    }

}


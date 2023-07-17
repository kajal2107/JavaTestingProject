package pageobjects.cord.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class LeftNavPO extends BasePO {

    public LeftNavPO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    @FindBy(xpath = "//button[contains(@class,'Sidebar-createNewItem') or @type='button']")
    private WebElement createNewItemButton;

    @FindBy(xpath = "//span[text()='Partners']")
    private WebElement partnersMenu;

    @FindBy(xpath = "//span[text()='Languages']")
    private WebElement languagesMenu;

    @FindBy(xpath = "//span[text()='People']")
    private WebElement peopleMenu;

    @FindBy(xpath = "//span[text()='Projects']")
    private WebElement projectsMenu;

    /**
     * Click on Create New Item and click on menu item
     *
     * @param menuItemName menu item name
     * @throws InterruptedException InterruptedException
     */
    public void clickOnCreateNewItemAndSelectType(String menuItemName) throws InterruptedException {

        selenium.clickOn(createNewItemButton);
        String menuXpathSelector = "//button[contains(@class,'Sidebar-createNewItem')or @type='button']/following::li[text  ()='" + menuItemName + "']";
        selenium.clickOn(By.xpath(menuXpathSelector));
    }

    /**
     * Click on create new item button
     *
     * @throws InterruptedException exception
     */
    public void clickOnCreateNewItemMenuButton() throws InterruptedException {
        selenium.clickOn(createNewItemButton);
    }

    /**
     * Click on Partner Menu
     *
     * @throws InterruptedException exception
     */
    public void clickOnPartnersMenu() throws InterruptedException {
        selenium.hardWait(5);
        selenium.clickOn(partnersMenu);
        selenium.hardWait(2);
    }

    /**
     * Click on languages Menu
     *
     * @throws InterruptedException exception
     */
    public void clickOnLanguagesMenu() throws InterruptedException {
        selenium.clickOn(languagesMenu);
    }

    /**
     * Click on people Menu
     *
     * @throws InterruptedException exception
     */
    public void clickOnPeopleMenu() throws InterruptedException {
        selenium.clickOn(peopleMenu);
    }

    /**
     * Click on projects  Menu
     *
     * @throws InterruptedException exception
     */
    public void clickOnProjectsMenu() throws InterruptedException {
        selenium.clickOn(projectsMenu);
    }


    /**
     * Is menu item present
     *
     * @param menuItemName menu item name
     * @return boolean
     */
    public boolean isMenuItemPresent(String menuItemName) {
        String menuXpathSelector = "//button[contains(@class,'Sidebar-createNewItem')or @type='button']/following::li[text  ()='" + menuItemName + "']";
        return selenium.isElementPresent(By.xpath(menuXpathSelector));
    }


}


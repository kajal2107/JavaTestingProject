package pageobjects.cord.projects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class ProjectDetailsTeamMembersListingPO extends BasePO {

    public ProjectDetailsTeamMembersListingPO(WebDriver driver) {
        super(driver);
    }

    /*
     * All WebElements are identified by @FindBy annotation
     * @FindBy can accept tagName, partialLinkText, name, linkText, id, css, className, xpath as attributes.
     */

    @FindBy(xpath = "//button[contains(@aria-label,'Add Team Member')]")
    private WebElement addTeamMemberButton;

    @FindBy(xpath = "//li[contains(@class,'MuiBreadcrumbs')]/a")
    private WebElement projectDetailLinkButton;

    /**
     * click On Add TeamMember Button
     *
     * @throws InterruptedException exception
     */
    public void clickOnAddTeamMemberButton() throws InterruptedException {
        selenium.clickOn(addTeamMemberButton);
    }

    /**
     * click On add project detail linkButton button
     *
     * @throws InterruptedException exception
     */
    public void clickOnProjectDetailLinkButton() throws InterruptedException {
        selenium.clickOn(projectDetailLinkButton);
    }

    /**
     * click On Edit TeamMember Button
     *
     * @param memberName MemberName
     * @throws InterruptedException exception
     */
    public void clickOnEditTeamMemberButton(String memberName) throws InterruptedException {
        String EditButtonByMemberName = "//h2[contains(@class,'MuiTypography-h2')]/following::p[text() = '" + memberName + "']/following::div[contains(@class,'MuiCardActions')]/button";
        selenium.clickOn(By.xpath(EditButtonByMemberName));
    }

    /**
     * Get Member name
     *
     * @param memberName MemberName
     * @return Member name
     */
    public String getMembersName(String memberName) {
        String MemberByName = "//h2[contains(@class,'MuiTypography-h2')]/following::p[contains(text(),'" + memberName + "')]";
        return selenium.getText(By.xpath(MemberByName));
    }

    /**
     * Get Member role
     *
     * @param memberName member Name
     * @return Member role
     */
    public String getMembersRoleByMemberName(String memberName) {
        String memberRoleXpath = "//h2[contains(@class,'MuiTypography-h2')]/following::p[contains(text(),'" + memberName + "')]/following-sibling::p[contains(@class,'colorTextSecondary')]";
        return selenium.getText(By.xpath(memberRoleXpath));
    }

    /**
     * Is Project member add button present ?
     *
     * @return boolean
     */
    public boolean isAddButtonPresent() {
        return selenium.isElementPresent(addTeamMemberButton);
    }
}


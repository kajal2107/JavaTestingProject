package funcLibs.project;

import dataobjects.Budget;
import dataobjects.User;
import enums.users.UserRole;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import pageobjects.cord.projects.ProjectDetailsBudgetListingPO;
import utilities.SeleniumHelpers;

public class BudgetFunctions {

    protected WebDriver driver;
    protected SeleniumHelpers selenium;

    public BudgetFunctions(WebDriver driver, SeleniumHelpers selenium) {
        this.driver = driver;
        this.selenium = selenium;
    }

    /**
     * edit project budget
     *
     * @param fileName fileName
     */

    public void editProjectBudget(String fileName, User user, Budget data) {

        try {

            ProjectDetailsBudgetListingPO projectDetailsBudgetListingPO = new ProjectDetailsBudgetListingPO(driver);
            Reporter.log("Step 1 - Click on budget detail button and add template file");
            projectDetailsBudgetListingPO.addBudgetTemplateFile(fileName);
            selenium.hardWait(5);

            Reporter.log("Step 2 - Find particular user role and click on edit link");
            if (user.getUserRoles().contains(UserRole.Administrator.getRole())
                    || user.getUserRoles().contains(UserRole.FieldOperationsDirector.getRole())
                    || user.getUserRoles().contains(UserRole.RegionalDirector.getRole())
                    || user.getUserRoles().contains(UserRole.Controller.getRole())
                    || user.getUserRoles().contains(UserRole.FinancialAnalyst.getRole())
                    || user.getUserRoles().contains(UserRole.ProjectManager.getRole())
            ) {
                projectDetailsBudgetListingPO.clickOnClickToEditLink();
                projectDetailsBudgetListingPO.clearAndFillBudgetAmountDetailAndClickOnSubmit(data);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

}

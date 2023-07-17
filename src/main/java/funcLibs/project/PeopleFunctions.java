package funcLibs.project;

import enums.menu.CreateItemTypes;
import enums.users.UserRole;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import pageobjects.cord.common.LeftNavPO;
import pageobjects.cord.people.CreatePeoplePO;
import utilities.SeleniumHelpers;

public class PeopleFunctions {

    protected WebDriver driver;
    protected SeleniumHelpers selenium;

    public PeopleFunctions(WebDriver driver, SeleniumHelpers selenium) {
        this.driver = driver;
        this.selenium = selenium;
    }

    /**
     * create people
     *
     * @param data PeopleData
     */

    public void createPeople(dataobjects.People data, dataobjects.User user) {

        try {
            CreatePeoplePO createPeople = new CreatePeoplePO(driver);
            LeftNavPO sideHeader = new LeftNavPO(driver);

            Reporter.log("Step 1 - Click on crate new item and select person menu");
            sideHeader.clickOnCreateNewItemAndSelectType(CreateItemTypes.Person.toString());

            Reporter.log("Step 2 - Enter valid details and click on submit button");
            createPeople.clearAndFillPersonDetail(data);
            if (user.getUserRoles().contains(UserRole.Administrator.getRole())) {
                createPeople.selectUserRole(data.getPersonRole());
            }
            createPeople.clickOnSubmitButton();
        } catch (Exception e) {
            System.out.println(e);
        }


    }

}

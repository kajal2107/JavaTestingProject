package cord.people;

import base.BaseTest;
import datafactory.PeopleData;
import dataobjects.People;
import enums.menu.CreateItemTypes;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import pageobjects.cord.account.LoginPO;
import pageobjects.cord.common.HeaderPO;
import pageobjects.cord.common.LeftNavPO;
import pageobjects.cord.people.CreatePeoplePO;
import pageobjects.cord.people.PeopleDetailsPO;
import pageobjects.cord.people.PeoplesPO;
import utilities.Constants;

public class CreatePeopleTests extends BaseTest {

    /*Test 1 : Verify that user can create people successfully*/
    @Test
    public void verifyUserCanCreatePeopleSuccessfully() throws InterruptedException {

        LoginPO login = new LoginPO(driver);
        LeftNavPO leftNav = new LeftNavPO(driver);
        CreatePeoplePO createPeople = new CreatePeoplePO(driver);
        PeopleDetailsPO peopleDetails = new PeopleDetailsPO(driver);
        PeoplesPO peoples = new PeoplesPO(driver);
        HeaderPO headerPO = new HeaderPO(driver);

        Reporter.log("Step 1 - Navigate to admin login page");
        selenium.navigateToPage(Constants.URL);

        Reporter.log("Step 2 - Entering valid sign in details and login to application");
        login.fillLoginDetailsAndPerformLogin(adminUser.getEmail(), adminUser.getPassword());

        Reporter.log("Step 3- Click on crate new item and select People menu");
        leftNav.clickOnCreateNewItemAndSelectType(CreateItemTypes.Person.toString());

        Reporter.log("Step 4- Enter valid details and click on submit button");
        People data = new PeopleData().getPeopleData();
        createPeople.clearAndFillPersonDetail(data);
        createPeople.selectUserRole(data.getPersonRole());
        createPeople.clickOnSubmitButton();
        selenium.hardWait(3);

        Reporter.log("Step 5- Verify that createPeople is created successfully by comparing createPeople details");
        leftNav.clickOnPeopleMenu();

        leftNav.clickOnPeopleMenu();

        if (!peoples.isPeoplePresent(data.getPersonName())) {
            headerPO.searchItemGloballyByName(data.getPersonName());
        }
        selenium.hardWait(3);
        peoples.clickOnPeopleName(data.getPersonName());

        Assert.assertEquals(data.getPersonEmail(), peopleDetails.getEmail(), "email doesn't match : ");
        Assert.assertEquals(data.getPersonTitle(), peopleDetails.getTitle(), "title doesn't match : ");
        Assert.assertEquals(data.getPersonPhone(), peopleDetails.getPhoneNumber(), "phone number doesn't match : ");
        Assert.assertEquals(data.getPersonRole(), peopleDetails.getRoles().split(","), "Roles does not match");

    }

    /*Test 2 : Verify mandatory fields validation messages are displayed for Name/Public Name fields*/
    @Test
    public void verifyValidationMessageDisplayedAllMandatoryFields() throws InterruptedException {

        LoginPO login = new LoginPO(driver);
        LeftNavPO leftNav = new LeftNavPO(driver);
        CreatePeoplePO people = new CreatePeoplePO(driver);

        Reporter.log("Step 1 - Navigate to admin login page");
        selenium.navigateToPage(Constants.URL);

        Reporter.log("Step 2 - Entering valid sign in details and login to application");
        login.fillLoginDetailsAndPerformLogin(fpmUser.getEmail(), fpmUser.getPassword());

        Reporter.log("Step 3- Click on crate new item and select People menu");
        leftNav.clickOnCreateNewItemAndSelectType(CreateItemTypes.Person.toString());

        Reporter.log("Step 4 - Click on 'Submit' button without entering any detail");
        people.clickOnSubmitButton();

        Reporter.log("Step 5- Verify that validation messages are displayed for name/displayName fields ");
        String expectedMessage = Constants.requiredMessage;
        Assert.assertEquals(people.getFirstNameMandatoryFieldText(), expectedMessage, "First name field validation message doesn't match");
        Assert.assertEquals(people.getLastNameMandatoryFieldText(), expectedMessage, "Last name field validation  message doesn't match");
        Assert.assertEquals(people.getPublicFirstNameMandatoryFieldText(), expectedMessage, "Public First Name field validation message doesn't match");
        Assert.assertEquals(people.getPublicLastNameMandatoryFieldText(), expectedMessage, "Public Last Name field validation  message doesn't match");

    }

}

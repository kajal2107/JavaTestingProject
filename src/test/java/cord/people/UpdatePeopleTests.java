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
import pageobjects.cord.people.UpdatePeoplePO;
import utilities.Constants;

public class UpdatePeopleTests extends BaseTest {

    /*Test 1 : Verify that user can update people successfully*/
    @Test
    public void verifyUserCanUpdatePeopleSuccessfully() throws InterruptedException {

        LoginPO login = new LoginPO(driver);
        LeftNavPO leftNav = new LeftNavPO(driver);
        CreatePeoplePO people = new CreatePeoplePO(driver);
        UpdatePeoplePO updatePeople = new UpdatePeoplePO(driver);
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
        people.clearAndFillPersonDetail(data);
        people.clickOnSubmitButton();

        Reporter.log("Step 5- Go to Peoples listing page and click on recently created people item");
        leftNav.clickOnPeopleMenu();

        if (!peoples.isPeoplePresent(data.getPersonName())) {
            headerPO.searchItemGloballyByName(data.getPersonName());
        }
        peoples.clickOnPeopleName(data.getPersonName());

        Reporter.log("Step 6 - Click on edit icon and fill the people details");
        peopleDetails.clickOnEditButton();
        updatePeople.clearAndUpdatePersonDetail(data);
        updatePeople.clickOnSubmitButton();

        Reporter.log("Step 7 - Verify updated person details");
        Assert.assertEquals(data.getPersonEmail(), peopleDetails.getEmail(), "email doesn't match : ");
        Assert.assertEquals(data.getPersonTitle(), peopleDetails.getTitle(), "title doesn't match : ");
        Assert.assertEquals(data.getPersonPhone(), peopleDetails.getPhoneNumber(), "phone number doesn't match : ");

    }


}

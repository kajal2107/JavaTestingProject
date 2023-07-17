package datafactory;

import dataobjects.People;
import enums.users.UserRole;
import utilities.JavaHelpers;

public class PeopleData {

    public People getPeopleData() throws InterruptedException {

        People data = new People();
        String timeStamp = new JavaHelpers().getTimeStamp();
        data.setPersonName("PersonFirstName" + timeStamp);
        data.setPersonRealLastName("personLastName" + timeStamp);
        data.setPersonEmail("test_people" + timeStamp + JavaHelpers.generateId(true,false,true,3) + "@mail.com");
        data.setPersonTitle("Tester");
        data.setPersonPhone("123456789" + timeStamp);
        data.setPersonTimeZone("Europe/Andorra");
        data.setPersonAbout("I'm Tester");
        String[] userRole = JavaHelpers.generateRandomArrayFromArray(1, UserRole.getAllUserRole()).toArray(new String[0]);
        data.setPersonRole(userRole);

        return data;
    }
}

package datafactory;

import dataobjects.Registration;
import utilities.JavaHelpers;

public class RegistrationData {

    /**
     * Get registration data
     *
     * @return Registration object
     */
    public Registration getRegistrationData() throws InterruptedException {
        Registration data = new Registration();
        String timeStamp = new JavaHelpers().getTimeStamp();

        data.setFirstName("FirstName" + timeStamp);
        data.setLastName("LastName" + timeStamp);
        data.setEmail("email" + timeStamp + "@test.com");
        data.setPassword("123456");
        data.setConfirmPassword("123456");
        data.setNewPassword("456789");
        return data;
    }

}

package datafactory;

import dataobjects.ChangeRequest;
import dataobjects.Partner;
import utilities.JavaHelpers;

public class ChangeRequestData {

    /**
     * Get create change request data
     *
     * @return change request object
     */
    public ChangeRequest getCreateChangeRequestData() throws InterruptedException {
        ChangeRequest data =new ChangeRequest();
        String timeStamp = new JavaHelpers().getTimeStamp();
        data.setSummaryText("Change request summary" + timeStamp);
        return data;
    }

}



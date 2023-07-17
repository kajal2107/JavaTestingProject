package datafactory;

import dataobjects.Partner;
import utilities.JavaHelpers;

public class PartnerData {

    /**
     * Get create partner data
     *
     * @return partner object
     */
    public Partner getCreatePartnerData() throws InterruptedException {
        Partner data = new Partner();
        String timeStamp = new JavaHelpers().getTimeStamp();
        data.setPartnerName("TestPartner" + timeStamp);
        return data;
    }

    /**
     * Get create partner data
     *
     * @return partner object
     */
    public Partner getEditPartnerData() {
        Partner editData = new Partner();
        editData.setPartnerPmcEntityCode("AAA");
        editData.setPartnerAddress("test address");
        return editData;
    }


}



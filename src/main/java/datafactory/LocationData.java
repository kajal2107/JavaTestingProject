package datafactory;

import dataobjects.Location;
import enums.location.LocationTypes;
import utilities.JavaHelpers;

public class LocationData {

    public Location getLocationData() throws InterruptedException {

        Location data = new Location();
        String timeStamp = new JavaHelpers().getTimeStamp();
        String[] countryArray = new String[]{"ABW", "USA", "ALA", "IND", "AUS"};
        String randomISOCountryCode = JavaHelpers.generateRandomArrayFromArray(1, countryArray).toString();
        data.setLocationCountryCode(randomISOCountryCode);
        data.setLocationName("LocationName" + timeStamp);
        String[] randomLocationType = JavaHelpers.generateRandomArrayFromArray(1, LocationTypes.getAllLocationTypes()).toArray(new String[0]);
        data.setLocationType(randomLocationType);
        data.setSearchFundingAccountName("Funding");

        return data;
    }

    public Location getLocationFundingAccountData() throws InterruptedException {

        Location fundingAccData = new Location();
        String timeStamp = new JavaHelpers().getTimeStamp();
        String[] countryArray = new String[]{"ABW", "USA", "ALA", "IND", "AUS"};
        fundingAccData.setLocationName("LocationName" + timeStamp);
        String[] randomLocationType = JavaHelpers.generateRandomArrayFromArray(1, LocationTypes.getAllLocationTypes()).toArray(new String[0]);
        fundingAccData.setLocationType(randomLocationType);
        fundingAccData.setSearchFundingAccountName("FundingAcc" + timeStamp);
        String randomISOCountryCode = JavaHelpers.generateRandomArrayFromArray(1, countryArray).toString();
        fundingAccData.setLocationCountryCode(randomISOCountryCode);
        String randomAccountNumber = JavaHelpers.getRandomNumber(1, 9);
        fundingAccData.setEnterFundingAccountNumber(randomAccountNumber);
        return fundingAccData;
    }
}

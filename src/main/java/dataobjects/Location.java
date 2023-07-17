package dataobjects;

public class Location {

    private String locationName;
    private String searchFundingAccountName;
    private String[] locationType;
    private String enterFundingAccountNumber;
    private String locationCountryCode;

    public String getSearchFundingAccountName() {
        return searchFundingAccountName;
    }

    public void setSearchFundingAccountName(String searchFundingAccountName) {
        this.searchFundingAccountName = searchFundingAccountName;
    }

    public String getEnterFundingAccountNumber() {
        return enterFundingAccountNumber;
    }

    public void setEnterFundingAccountNumber(String enterFundingAccountNumber) {
        this.enterFundingAccountNumber = enterFundingAccountNumber;
    }

    public String getLocationCountryCode() {
        return locationCountryCode;
    }

    public void setLocationCountryCode(String locationCountryCode) {
        this.locationCountryCode = locationCountryCode;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String[] getLocationType() {
        return locationType;
    }

    public void setLocationType(String[] locationType) {
        this.locationType = locationType;
    }

}

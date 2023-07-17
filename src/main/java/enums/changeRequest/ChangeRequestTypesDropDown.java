package enums.changeRequest;

public enum ChangeRequestTypesDropDown {

    Budget("Budget"),
    Engagement("Engagement"),
    Goal("Goal"),
    Other("Other"),
    Time("Time");

    public String getChangeRequestsTypesDropDown() {
        return changeRequestsTypesDropDown;
    }

    // declaring private variable for getting values
    private final String changeRequestsTypesDropDown;

    // Constructor that will set value from bracket i.e. String
    ChangeRequestTypesDropDown(String changeRequestsTypesDropDown) {
        this.changeRequestsTypesDropDown = changeRequestsTypesDropDown;
    }

}

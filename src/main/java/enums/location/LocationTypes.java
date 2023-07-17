package enums.location;

public enum LocationTypes {

    Country("Country"),
    City("City"),
    State("State"),
    CrossBorderArea("Cross-Border Area");

    // declaring private variable for getting values

    String locationTypes;

    public String getLocationTypes() {
        return locationTypes;
    }

    public static String[] getLocationTypeByName(String[] type) {
        return type;
    }

    // Constructor that will set value from bracket i.e. String
    LocationTypes(String productMediums) {
        this.locationTypes = productMediums;
    }


    public static String[] getAllLocationTypes() {
        LocationTypes[] signals = LocationTypes.values();

        String[] names = new String[signals.length];

        for (int i = 0; i < signals.length; i++) {
            names[i] = signals[i].locationTypes;
        }
        return names;
    }


}

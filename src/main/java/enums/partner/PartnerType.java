package enums.partner;

public enum PartnerType {

    Managing("Managing"),
    Impact("Impact"),
    Resource("Resource"),
    Funding("Funding"),
    Technical("Technical");

    // declaring private variable for getting values
    private String partnerType;

    public String getPartnerType() {
        return partnerType;
    }

    public void setPartnerType(String partnerType) {
        this.partnerType = partnerType;
    }

    // Constructor that will set value from bracket i.e. String
    PartnerType(String partnerType) {
        this.partnerType = partnerType;
    }


}

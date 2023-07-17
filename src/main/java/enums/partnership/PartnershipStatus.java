package enums.partnership;

public enum PartnershipStatus {


    NotAttached("Not Attached"),
    AwaitingSignature("Awaiting Signature"),
    Signed("Signed");

    // declaring private variable for getting values
    private String partnerShipStatus;

    public String getPartnerShipStatus() {
        return partnerShipStatus;
    }

    public void setPartnerShipStatus(String partnerShipStatus) {
        this.partnerShipStatus = partnerShipStatus;
    }

    // Constructor that will set value from bracket i.e. String
    PartnershipStatus(String partnerShipStatus) {
        this.partnerShipStatus = partnerShipStatus;
    }

}

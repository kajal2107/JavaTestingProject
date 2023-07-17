package dataobjects;

public class Partner {

    private String partnerName;

    private String organizationName;

    private String partnerPmcEntityCode;

    private String partnerAddress;

    private String partnerStatus;

    public String getPartnerStatus() {
        return partnerStatus;
    }

    public void setPartnerStatus(String partnerStatus) {
        this.partnerStatus = partnerStatus;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getPartnerPmcEntityCode() {
        return partnerPmcEntityCode;
    }

    public void setPartnerPmcEntityCode(String partnerPmcEntityCode) {
        this.partnerPmcEntityCode = partnerPmcEntityCode;
    }

    public String getPartnerAddress() {
        return partnerAddress;
    }

    public void setPartnerAddress(String partnerAddress) {
        this.partnerAddress = partnerAddress;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

}

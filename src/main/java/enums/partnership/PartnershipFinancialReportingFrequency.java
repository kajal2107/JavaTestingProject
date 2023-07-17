package enums.partnership;

public enum PartnershipFinancialReportingFrequency {

    Monthly("Monthly"),
    Quarterly("Quarterly");

    // declaring private variable for getting values
    private String partnershipFinancialReportingFrequency;

    public String getPartnershipFinancialReportingFrequency() {
        return partnershipFinancialReportingFrequency;
    }

    public void setPartnershipFinancialReportingFrequency(String partnershipFinancialReportingFrequency) {
        this.partnershipFinancialReportingFrequency = partnershipFinancialReportingFrequency;
    }

    // Constructor that will set value from bracket i.e. String
    PartnershipFinancialReportingFrequency(String partnershipFinancialReportingFrequency) {
        this.partnershipFinancialReportingFrequency = partnershipFinancialReportingFrequency;
    }

}

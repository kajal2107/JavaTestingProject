package enums.partnership;

public enum PartnershipFinancialReportingTypes {


    Funded("Funded"),
    FieldEngaged("Field Engaged");

    public String getPartnershipFinancialReportingTypes() {
        return partnershipFinancialReportingTypes;
    }

    public void setPartnershipFinancialReportingTypes(String partnershipFinancialReportingTypes) {
        this.partnershipFinancialReportingTypes = partnershipFinancialReportingTypes;
    }

    // declaring private variable for getting values
    private String partnershipFinancialReportingTypes;

    // Constructor that will set value from bracket i.e. String
    PartnershipFinancialReportingTypes(String partnershipFinancialReportingTypes) {
        this.partnershipFinancialReportingTypes = partnershipFinancialReportingTypes;
    }

}

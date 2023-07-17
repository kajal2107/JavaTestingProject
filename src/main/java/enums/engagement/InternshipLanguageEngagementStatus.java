package enums.engagement;

public enum InternshipLanguageEngagementStatus {

    InDevelopment("In Development"),
    DidNotDevelop("Project did not develop"),
    Active("Active"),
    ProjectWasMadeActive("Project was made active"),
    DiscussingTermination("Discuss Termination"),
    DiscussingReactivation("Prep For Consultant Endorsement"),
    DiscussingChangeToPlan("Discuss Change to Plan"),
    DiscussingSuspension("Discuss Suspension"),
    FinalizeCompletion("Finalize Completion"),
    FinalizingCompletion("Finalizing Completion"),
    ActiveChangedPlan("Prep For Financial Endorsement"),
    Suspended("Submit for Financial Endorsement"),
    Terminated("Pending Financial Endorsement"),
    Completed("Completed"),
    Complete("Complete 🎉"),
    Converted("Finalizing Proposal"),
    Unapproved("Submit for Approval"),
    Transferred("Pending Regional Director Approval"),
    NotRenewed("Approve Project"),
    Rejected("Pending Finance Confirmation");


    // declaring private variable for getting values
    private String internShipStatus;

    public String getInternShipStatus() {
        return internShipStatus;
    }

    // Constructor that will set value from bracket i.e. String
    InternshipLanguageEngagementStatus(String internShipStatus) {
        this.internShipStatus = internShipStatus;
    }


}

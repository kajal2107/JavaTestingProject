package enums.project;

public enum ProjectStep {

    EarlyConversations("Early Conversations"),
    SubmitforConceptApproval("Submit for Concept Approval"),
    PendingConceptApproval("Pending Concept Approval"),
    PendingSuspensionApproval("Pending Suspension Approval"),
    ApproveConcept("Approve Concept"),
    ApproveReactivation("Approve Reactivation"),
    DiscussingReActivation("Discussing Reactivation"),
    DiscussReactivation("Discuss Reactivation"),
    PrepForConsultantEndorsement("Prep For Consultant Endorsement"),
    SubmitforConsultantEndorsement("Submit for Consultant Endorsement"),
    PendingConsultantEndorsement("Pending Consultant Endorsement"),
    PendingReactivationApproval("Pending Reactivation Approval"),
    EndorsePlan("Endorse Plan"),
    Suspended("Suspended"),
    PrepForFinancialEndorsement("Prep For Financial Endorsement"),
    SubmitforFinancialEndorsement("Submit for Financial Endorsement"),
    PendingFinancialEndorsement("Pending Financial Endorsement"),
    EndorseProjectPlan("Endorse Project Plan"),
    FinalizingProposal("Finalizing Proposal"),
    SubmitforApproval("Submit for Approval"),
    PendingRegionalDirectorApproval("Pending Regional Director Approval"),
    ApproveProject("Approve Project"),
    PendingFinanceConfirmation("Pending Finance Confirmation"),
    ConfirmProject("Confirm Project 🎉"),
    Active("Active"),
    DiscussChangeToPlan("Discuss Change to Plan"),
    DiscussingChangeToPlan("Discussing Change To Plan"),
    PendingChangeToPlanApproval ("Pending Change To Plan Approval"),
    PendingChangeToPlanConfirmation ("Pending Change To Plan Confirmation"),
    ApproveChangeToPlan("Approve Change to Plan"),
    ActiveChangedPlan("Active Changed Plan"),
    HoldProjectForConfirmation("Hold Project for Confirmation"),
    OnHoldForFinanceConfirmation("On Hold Finance Confirmation"),
    ApproveSuspension("Approve Suspension"),
    FinalizeCompletion("Finalize Completion"),
    FinalizingCompletion("Finalizing Completion"),
    Complete("Complete 🎉"),
    Completed("Completed");


    public void setStep(String step) {
        this.step = step;
    }

    // declaring private variable for getting values
    private String step;

    // Constructor that will set value from bracket i.e. String
    ProjectStep(String step) {
        this.step = step;
    }

    // getter method
    public String getStep() {
        return this.step;
    }

}

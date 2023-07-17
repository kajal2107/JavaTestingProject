package dataobjects;

public class Internship {
    private String internshipStartDate;
    private String paraTextRegId;
    private String internshipEstimatedDate;
    private String internshipActualDate;
    private String internshipEndDate;
    private String growthPlanCompleteDate;
    private String disbursementCompleteDate;

    public String getParaTextRegId() {
        return paraTextRegId;
    }

    public void setParaTextRegId(String paraTextRegId) {
        this.paraTextRegId = paraTextRegId;
    }

    public String getInternshipStartDate() {
        return internshipStartDate;
    }

    public void setInternshipStartDate(String internshipStartDate) {
        this.internshipStartDate = internshipStartDate;
    }

    public String getInternshipEndDate() {
        return internshipEndDate;
    }

    public void setInternshipEndDate(String internshipEndDate) {
        this.internshipEndDate = internshipEndDate;
    }

    public String getGrowthPlanCompleteDate() {
        return growthPlanCompleteDate;
    }

    public void setGrowthPlanCompleteDate(String growthPlanCompleteDate) {
        this.growthPlanCompleteDate = growthPlanCompleteDate;
    }

    public String getDisbursementCompleteDate() {
        return disbursementCompleteDate;
    }

    public void setDisbursementCompleteDate(String disbursementCompleteDate) {
        this.disbursementCompleteDate = disbursementCompleteDate;
    }
    public String getInternshipEstimatedDate() {
        return internshipEstimatedDate;
    }

    public void setInternshipEstimatedDate(String internshipEstimatedDate) {
        this.internshipEstimatedDate = internshipEstimatedDate;
    }

    public String getInternshipActualDate() {
        return internshipActualDate;
    }

    public void setInternshipActualDate(String internshipActualDate) {
        this.internshipActualDate = internshipActualDate;
    }

}

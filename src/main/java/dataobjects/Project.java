package dataobjects;

import enums.project.ProjectSubTabs;
import enums.project.ProjectType;
import enums.project.Sensitivity;

public class Project {
    private String projectName;
    private ProjectType Type;
    private ProjectSubTabs Menu;
    private String locationName;
    private String fieldRegionName;
    private String startDate;
    private String endDate;
    private String estimatedSubmissionDate;
    private String growthPlanCompleteDate;
    private String disbursementCompleteDate;
    private String folderName;
    private String fileName;
    private String projectPosts;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getDisbursementCompleteDate() {
        return disbursementCompleteDate;
    }

    public void setDisbursementCompleteDate(String disbursementCompleteDate) {
        this.disbursementCompleteDate = disbursementCompleteDate;
    }

    public String getGrowthPlanCompleteDate() {
        return growthPlanCompleteDate;
    }

    public void setGrowthPlanCompleteDate(String growthPlanCompleteDate) {
        this.growthPlanCompleteDate = growthPlanCompleteDate;
    }

    public String getEstimatedSubmissionDate() {
        return estimatedSubmissionDate;
    }

    public void setEstimatedSubmissionDate(String estimatedSubmissionDate) {
        this.estimatedSubmissionDate = estimatedSubmissionDate;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public ProjectType getType() {
        return Type;
    }

    public void setType(ProjectType type) {
        Type = type;
    }

    public ProjectSubTabs getMenu() {
        return Menu;
    }

    public void setMenu(ProjectSubTabs menu) {
        Menu = menu;
    }

    public Sensitivity getSensitivity() {
        return sensitivity;
    }

    public void setSensitivity(Sensitivity sensitivity) {
        this.sensitivity = sensitivity;
    }

    private Sensitivity sensitivity;

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getFieldRegionName() {
        return fieldRegionName;
    }

    public void setFieldRegionName(String fieldRegionName) {
        this.fieldRegionName = fieldRegionName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getProjectPosts() {
        return projectPosts;
    }

    public void setProjectPosts(String projectPosts) {
        this.projectPosts = projectPosts;
    }

}

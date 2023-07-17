package datafactory;

import dataobjects.Project;
import enums.project.ProjectType;
import org.json.simple.parser.ParseException;
import utilities.JavaHelpers;

public class ProjectData {

    /**
     * Get create project data
     *
     * @return Project object
     */
    public Project getCreateProjectData() throws InterruptedException {

        String timeStamp = new JavaHelpers().getTimeStamp();
        Project data = new Project();
        data.setProjectName("Test_InternProject" + timeStamp + JavaHelpers.generateId(true,false,true,3));
        data.setType(ProjectType.Internship);
        data.setProjectPosts("TestProject Post" +" "+ JavaHelpers.generateId(true,true,true,10));
        return data;
    }

    /**
     * Get create project data
     *
     * @return Project object
     */
    public Project getCreateTranslationProjectData() throws InterruptedException {
        Project data = new Project();
        String timeStamp = new JavaHelpers().getTimeStamp();
        data.setProjectName("Test_TransProject" + timeStamp + JavaHelpers.generateId(true,false,true,3));
        data.setType(ProjectType.Translation);
        return data;
    }

    /**
     * Get update project data
     *
     * @return Project object
     */
    public Project getUpdateProjectData() throws ParseException, java.text.ParseException, InterruptedException {
        Project updateData = new Project();
        String timeStamp = new JavaHelpers().getTimeStamp("MM/dd/yyyy");
        String fileFolderTimeStamp = new JavaHelpers().getTimeStamp();

        updateData.setProjectName("UpdatedProject_" + timeStamp + JavaHelpers.generateId(true,false,true,3));
        updateData.setFieldRegionName("Field");
        updateData.setLocationName("Location");
        updateData.setFolderName("Folder" + fileFolderTimeStamp);
        updateData.setFileName("renameFile" + fileFolderTimeStamp);
        updateData.setStartDate(timeStamp);
        updateData.setEndDate(new JavaHelpers().updateTime("MM/dd/yyyy", timeStamp, "MM/dd/yyyy", 0, 0, 1));
        updateData.setGrowthPlanCompleteDate(new JavaHelpers().updateTime("MM/dd/yyyy", timeStamp, "MM/dd/yyyy", 4, 2, 2));
        updateData.setDisbursementCompleteDate(new JavaHelpers().updateTime("MM/dd/yyyy", timeStamp, "MM/dd/yyyy", 3, 7, 3));
        updateData.setEstimatedSubmissionDate(new JavaHelpers().updateTime("MM/dd/yyyy", timeStamp, "MM/dd/yyyy", 3, 0, 3));
        return updateData;
    }

}

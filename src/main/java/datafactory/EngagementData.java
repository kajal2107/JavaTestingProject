package datafactory;

import dataobjects.Internship;
import org.json.simple.parser.ParseException;
import utilities.JavaHelpers;

public class EngagementData {

    /**
     * Get create internship  data
     *
     * @return internship object
     */
    public Internship getCreateEngagementData() throws ParseException, java.text.ParseException {
        Internship data = new Internship();
        String timeStamp = new JavaHelpers().getTimeStamp("MM/dd/yyyy");

        data.setInternshipStartDate(new JavaHelpers().updateTime("MM/dd/yyyy", timeStamp, "MM/dd/yyyy", 0, 0, -2));
        data.setInternshipEndDate(new JavaHelpers().updateTime("MM/dd/yyyy", timeStamp, "MM/dd/yyyy", 0, 0, 3));
        data.setGrowthPlanCompleteDate(new JavaHelpers().updateTime("MM/dd/yyyy", timeStamp, "MM/dd/yyyy", 4, 2, 2));
        data.setDisbursementCompleteDate(new JavaHelpers().updateTime("MM/dd/yyyy", timeStamp, "MM/dd/yyyy", 3, 7, 3));
        data.setInternshipActualDate(new JavaHelpers().updateTime("MM/dd/yyyy", timeStamp, "MM/dd/yyyy", 1, 2, 1));
        data.setInternshipEstimatedDate(new JavaHelpers().updateTime("MM/dd/yyyy", timeStamp, "MM/dd/yyyy", 3, 0, 3));
        data.setParaTextRegId(JavaHelpers.getRandomNumber(100, 500));
        return data;
    }

    /**
     * Get update internship  data
     *
     * @return internship object
     */
    public Internship getUpdateEngagementData() throws ParseException, java.text.ParseException {
        Internship updateData = new Internship();

        String timeStamp = new JavaHelpers().getTimeStamp("MM/dd/yyyy");

        updateData.setInternshipStartDate(timeStamp);
        updateData.setInternshipEndDate(new JavaHelpers().updateTime("MM/dd/yyyy", timeStamp, "MM/dd/yyyy", 1, 0, 1));
        updateData.setGrowthPlanCompleteDate(new JavaHelpers().updateTime("MM/dd/yyyy", timeStamp, "MM/dd/yyyy", 5, 2, 2));
        updateData.setDisbursementCompleteDate(new JavaHelpers().updateTime("MM/dd/yyyy", timeStamp, "MM/dd/yyyy", 4, 7, 3));
        updateData.setInternshipActualDate(new JavaHelpers().updateTime("MM/dd/yyyy", timeStamp, "MM/dd/yyyy", 2, 2, 1));
        updateData.setInternshipEstimatedDate(new JavaHelpers().updateTime("MM/dd/yyyy", timeStamp, "MM/dd/yyyy", 4, 0, 3));
        return updateData;
    }

}

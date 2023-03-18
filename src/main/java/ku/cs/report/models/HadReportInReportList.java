package ku.cs.report.models;

import ku.cs.report.models.Report;
import ku.cs.report.models.ReportList;
import ku.cs.report.services.DataSource;
import ku.cs.report.services.ReportListFileDataSource;

public class HadReportInReportList {
    public HadReportInReportList(){
    }

    public boolean checkReport(Report report){
        boolean flag = false;
        DataSource<ReportList> dataSourceInappropriate = new ReportListFileDataSource("data", "Inappropriate.csv");
        ReportList reportInappropriateList = dataSourceInappropriate.readData();

        for (Report buffer: reportInappropriateList.getAllList()){
            if (buffer.getCategory().equals(report.getCategory()) &&
                    buffer.getHead().equals(report.getHead()) &&
                    buffer.getSubject().equals(report.getSubject()) &&
                    buffer.getSubjectSpecific().equals(report.getSubjectSpecific()) &&
                    buffer.getUsernameReport().equals(report.getUsernameReport()) &&
                    buffer.getVote() == report.getVote() &&
                    buffer.getTime().equals(report.getTime()) &&
                    buffer.getUsernameStaffManage().equals(report.getUsernameStaffManage()) &&
                    buffer.getFeedback().equals(report.getFeedback()) &&
                    buffer.getStatus().equals(report.getStatus()) &&
                    buffer.getPicture().equals(report.getPicture())){
                return true;
            }
        }
        return flag;
    }
}

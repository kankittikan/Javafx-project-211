package ku.cs.report.services;

import ku.cs.report.models.Report;
import ku.cs.report.models.ReportList;

public class ReportListCategorySource {


    public ReportList sortBy(ReportList reportList, String username) {
        ReportList myReportList = new ReportList();

        for (Report report: reportList.getAllList()) {
            if (report.getUsernameReport().equals(username)){
                myReportList.addReport(report);
            }
        }
        return  myReportList;
    }
}

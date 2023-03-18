package ku.cs.report.models;

import ku.cs.report.models.Report;
import ku.cs.report.models.ReportList;
import ku.cs.report.services.DataSource;
import ku.cs.report.services.ReportListFileDataSource;

import java.lang.reflect.InvocationTargetException;

public class VoteReport {
    public VoteReport(ReportList reportList, String head , String subject, String time) throws InvocationTargetException {

        DataSource<ReportList> dataSourceReport = new ReportListFileDataSource("data", "ReportData.csv");
        DataSource<ReportList> dataSourceInappropriate = new ReportListFileDataSource("data", "Inappropriate.csv");
        ReportList allReportList = dataSourceReport.readData();
        ReportList reportInappropriateList = dataSourceInappropriate.readData();

        Report voteReport = null;
        for (Report buffer: reportList.getAllList()){
            if (buffer.getHead().equals(head) && buffer.getSubject().equals(subject) && buffer.getTime().equals(time)){
                buffer.vote();
                voteReport = buffer;
            }
        }

        // vote and write in ReportData.csv
        int index1 = 0;
        for (Report allReport: allReportList.getAllList()){
            if (voteReport != null && allReport.getHead().equals(voteReport.getHead()) &&
                    allReport.getSubject().equals(voteReport.getSubject()) &&
                    allReport.getSubjectSpecific().equals(voteReport.getSubjectSpecific()) &&
                    allReport.getTime().equals(voteReport.getTime())) {

                allReportList.getList(index1).vote();
                dataSourceReport.writeData(allReportList);
            }

            index1++;
        }

        //vote and writ in Inappropriate.csv
        int index2 = 0;
        for (Report reportInappropriate: reportInappropriateList.getAllList()){
            if (voteReport != null && reportInappropriate.getHead().equals(voteReport.getHead()) &&
                    reportInappropriate.getSubject().equals(voteReport.getSubject()) &&
                    reportInappropriate.getSubjectSpecific().equals(voteReport.getSubjectSpecific()) &&
                    reportInappropriate.getTime().equals(voteReport.getTime())) {

                reportInappropriateList.getList(index2).vote();
                dataSourceInappropriate.writeData(reportInappropriateList);
            }

            index2++;
        }
    }
}

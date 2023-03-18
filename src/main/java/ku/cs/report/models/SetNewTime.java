package ku.cs.report.models;

public class SetNewTime {

    public ReportList newTime(ReportList reportList) {
        ReportList newReportList = new ReportList();

        for (Report report: reportList.getAllList()) {
            String time = report.getTime();
            String[] time2 = time.split("\\|");
            String date[] = time2[0].split("-");
            String dateNew = date[2].trim() + "-" + date[1].trim() + "-"+  date[0].trim() + " |" + time2[1];

            report.setTime(dateNew);
            newReportList.addReport(report);
        }
        return newReportList;
    }
}

package ku.cs.report.models;

import ku.cs.report.models.Category;
import ku.cs.report.models.Report;
import ku.cs.report.models.ReportList;
import ku.cs.report.services.CategoryListFileDataSource;
import ku.cs.report.services.DataSource;
import ku.cs.report.services.ReportListFileDataSource;

public class CheckCategory {
    public CheckCategory(ReportList reportList){
        checkCategory(reportList);
    }
    public void checkCategory (ReportList reportList) {

        DataSource<ReportList> dataSourceReport = new ReportListFileDataSource("data", "ReportData.csv");
        DataSource<Category> dataSourceCategory = new CategoryListFileDataSource("data", "CategoryReport.csv");
        Category categoryList = dataSourceCategory.readData();

        int i = 0;
        for (Report report: reportList.getAllList()) {
            if (!categoryList.getCategory(report.getCategory()).equals(report.getCategory())) {
                reportList.getList(i).setCategory("ไม่มีหมวดหมู่");
                dataSourceReport.writeData(reportList);
            }
            i++;
        }
    }
}

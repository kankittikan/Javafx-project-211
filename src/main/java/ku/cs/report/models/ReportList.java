package ku.cs.report.models;

import java.util.ArrayList;
import java.util.Comparator;

public class ReportList {
    private final ArrayList<Report> reportArrayList;

    public ReportList() {
        reportArrayList = new ArrayList<>();
    }

    public void addReport(Report report) {
        reportArrayList.add(report);
    }

    public void addReport(int index, Report report) {
        reportArrayList.add(index, report);
    }

    public void removeReport(String head) {
       for (int i = 0; i < getSize(); i++){
           Report check = getList(i);
           if (head.equals(check.getHead())){
               reportArrayList.remove(i);
               break;
           }
       }
    }

    public ArrayList<Report> getAllList() {
        return reportArrayList;
    }

    public Report getList(int i) {
        return reportArrayList.get(i);
    }

    public int getSize() {
        return reportArrayList.size();
    }

    public void changeCategoryName(String oldName, String newName) {
        for (Report i : reportArrayList) {
            if (i.getCategory().equals(oldName)) {
                i.setCategory(newName);
            }
        }
    }

    @Override
    public String toString() {
        return "" + reportArrayList;
    }

    public void ascendingTime() {
        reportArrayList.sort(new Comparator<>() {
            @Override
            public int compare(Report o1, Report o2) throws NullPointerException {
                return o1.getTime().compareTo(o2.getTime());
            }
        });
    }

    public void descendingTime() {
        reportArrayList.sort(new Comparator<>() {
            @Override
            public int compare(Report o1, Report o2) throws NullPointerException {
                return o2.getTime().compareTo(o1.getTime());
            }
        });
    }

    public void ascendingVote() {
        reportArrayList.sort(new Comparator<>() {
            @Override
            public int compare(Report o1, Report o2) throws NullPointerException {
                return Integer.compare(o1.getVote(), o2.getVote());
            }
        });
    }

    public ReportList sortStatusReportList(String status){
        ReportList sortStatusReport = new ReportList();
        for (Report report: reportArrayList){
            if (report.getStatus().equals(status)){
                sortStatusReport.addReport(report);
            }
        }
        return sortStatusReport;
    }

    public ReportList sortByNameCategory(String category,ReportList reportList) {
        ReportList sortNameCategory = new ReportList();
        for (Report report: reportList.getAllList()){
            if (report.getCategory().equals(category)){
                sortNameCategory.addReport(report);
            }
        }
        return sortNameCategory;
    }

    public ReportList greaterX(int x ,ReportList reportList) {
        ReportList greaterReportList = new ReportList();
        for (Report report: reportList.getAllList()){
            if (report.getVote() > x){
                greaterReportList.addReport(report);
            }
        }
        return greaterReportList;
    }

    public ReportList betweenMAndN(int m, int n, ReportList reportList){
        ReportList betweenReportList = new ReportList();
        for (Report report: reportList.getAllList()){
            if (report.getVote() >= m && report.getVote() <= n) {
                betweenReportList.addReport(report);
            }
        }
        return  betweenReportList;
    }

    public void descendingVote() {
        reportArrayList.sort(new Comparator<>() {
            @Override
            public int compare(Report o1, Report o2) throws NullPointerException {
                return Integer.compare(o2.getVote(), o1.getVote());
            }
        });
    }

    public ReportList filterReport(Filterer<Report> filterer){
        ReportList filtered = new ReportList();
        for(Report report : reportArrayList){
            if(filterer.filter(report)){
                filtered.addReport(report);
            }
        }

        return filtered;
    }

    public Report findReport(String head){
        for(Report report : reportArrayList){
            if(report.getHead().equals(head)){
                return report;
            }
        }
        return null;
    }

    public ArrayList<String> getTimeAndHeadList(){
        ArrayList<String> arrayList = new ArrayList<>();
        for(Report report : reportArrayList){
            String timeAndHead = report.getTime() +","+report.getHead();
            arrayList.add(timeAndHead);
        }
        return arrayList;
    }

    public ArrayList<String> getCategoryHeadTime() {
        ArrayList<String> arrayList = new ArrayList<>();
        for (Report report : reportArrayList ) {
            String categoryHeadTime = report.getCategory() + "          " + report.getHead() + "           " + report.getTime();
            arrayList.add(categoryHeadTime);
        }
        return arrayList;
    }

}

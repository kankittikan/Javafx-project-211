package ku.cs.report.models;

public class Report {
    private String category;
    private String head;
    private String subject;
    private String subjectSpecific;
    private String usernameReport;
    private int vote;
    private String time;
    private String usernameStaffManage;
    private String feedback;
    private String status;
    private String picture;

    public Report(String category, String head, String subject, String subjectSpecific, String usernameReport) {
        this.head = head;
        this.category = category;
        this.subject = subject;
        this.subjectSpecific = subjectSpecific;
        this.usernameReport = usernameReport;
        this.vote = 0;
        this.time = "ระบุไม่ได้";
        this.usernameStaffManage = "ระบุไม่ได้";
        this.status = "รอดำเนินการ";
        this.picture = null;
    }

    public Report(String category, String head, String subject, String subjectSpecific, String usernameReport, int vote, String time) {
        this.head = head;
        this.category = category;
        this.subject = subject;
        this.subjectSpecific = subjectSpecific;
        this.usernameReport = usernameReport;
        this.vote = vote;
        this.time = time;
        this.usernameStaffManage = "ระบุไม่ได้";
        this.status = "รอดำเนินการ";
        this.feedback = "ไม่มีการตอบกลับ";
        this.picture = null;
    }

    public Report(String category, String head, String subject, String subjectSpecific, String usernameReport, int vote, String time,String picture) {
        this.head = head;
        this.category = category;
        this.subject = subject;
        this.subjectSpecific = subjectSpecific;
        this.usernameReport = usernameReport;
        this.vote = vote;
        this.time = time;
        this.usernameStaffManage = "ระบุไม่ได้";
        this.status = "รอดำเนินการ";
        this.feedback = "ไม่มีการตอบกลับ";
        this.picture = picture;
    }

    public Report(String category, String head, String subject, String subjectSpecific, String usernameReport, int vote, String time, String usernameStaff, String feedback, String status, String picture) {
        this.head = head;
        this.category = category;
        this.subject = subject;
        this.subjectSpecific = subjectSpecific;
        this.usernameReport = usernameReport;
        this.vote = vote;
        this.time = time;
        this.usernameStaffManage = usernameStaff;
        this.status = status;
        this.feedback = feedback;
        this.picture = picture;
    }

    public String getHead() {
        return head;
    }

    public String getCategory() {
        return category;
    }

    public String getSubject() {
        return subject;
    }

    public String getSubjectSpecific() {
        return subjectSpecific;
    }

    public String getUsernameReport() {
        return usernameReport;
    }

    public int getVote() {
        return vote;
    }

    public String getTime() {
        return time;
    }

    public String getUsernameStaffManage() {
        return usernameStaffManage;
    }

    public String getFeedback() {
        return feedback;
    }

    public String getStatus() {
        return status;
    }

    public String getPicture() {return picture;}

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setUsernameStaffManage(String usernameStaff) {
        this.usernameStaffManage = usernameStaff;
    }

    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }
    public void setPicture(String picture) {this.picture = picture;}


    public Report vote() {
        this.vote += 1;
        if (this.vote < 0) {
            this.vote = 0;
        }
        return null;
    }

    public void setFeedback(String text){
        this.feedback = text;
    }

}

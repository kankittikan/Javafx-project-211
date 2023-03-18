package ku.cs.report.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import ku.cs.report.Animations.Direction;
import ku.cs.report.Animations.LabelAnimation;
import ku.cs.report.models.*;
import com.github.saacsos.FXRouter;
import ku.cs.report.services.AppearanceDataSource;
import ku.cs.report.services.DataSource;
import ku.cs.report.services.ReportListFileDataSource;
import ku.cs.report.services.StaffMemberListFileDataSource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class StaffHomeController implements AppearanceConfig {
    @FXML
    private ImageView imageStaff;
    @FXML
    private Label nameLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private Label agencyLabel;
    @FXML
    private ListView<String> categoryListView;
    @FXML
    private Label headLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Label staffNameLabel;
    @FXML
    private Label noticeLabel;
    @FXML
    private TextArea howToSolveTextArea;
    @FXML
    private TextArea subjectReportTextArea;
    @FXML
    private MenuButton filterReportButton;
    @FXML
    private Label topicLabel, agencyTopicLabel, complaintListLabel, subjectLabel, headTopicLabel, statusTopicLabel, staffNameTopicLabel, noticeTopicLabel;
    @FXML
    private Label howToSolveLabel;
    @FXML
    private AnchorPane backgroundStaffHomeAnchorPane, topLayoutAnchorPane;
    @FXML
    private BorderPane borderPane;
    @FXML
    private CheckBox finishStatusCheckbox;

    private StaffMember staffMember;
    private StaffMemberList staffMemberList;
    private DataSource<StaffMemberList> staffMemberListDataSource;
    private ReportList allReportList, filterReport;
    private DataSource<ReportList> reportListDataSource;
    private Report report;
    private ArrayList<String> timeAndHeadList;
    private LabelAnimation labelAnimation;

    private String themeStr, fontSizeStr, fontStr;
    private DataSource<Appearance> fontSizeDataSource = new AppearanceDataSource("data/appearance", "FontSize.csv");
    private DataSource<Appearance> fontDataSource = new AppearanceDataSource("data/appearance", "Font.csv");
    private DataSource<Appearance> themeDataSource = new AppearanceDataSource("data/appearance", "theme.csv");
    private Appearance appearanceSize;
    private Appearance appearanceFont;
    private Appearance appearanceTheme;

    @FXML
    public void initialize() {
        CheckTimeTheme.check();
        appearanceSize = fontSizeDataSource.readData();
        appearanceFont = fontDataSource.readData();
        appearanceTheme = themeDataSource.readData();
        themeStr = getClass().getResource("/ku/cs/css/" + appearanceTheme.getData() + ".css").toExternalForm();
        fontStr = getClass().getResource("/ku/cs/css/Font" + appearanceFont.getData() + ".css").toExternalForm();
        fontSizeStr = getClass().getResource("/ku/cs/css/FontSize" + appearanceSize.getData() + ".css").toExternalForm();
        setStyle();

        labelAnimation = new LabelAnimation();

        staffMemberListDataSource = new StaffMemberListFileDataSource("data", "StaffDataUser.csv");
        staffMember = (StaffMember) FXRouter.getData();
        readStaffDataUser();

        staffMember.setTimeLogin(DateTime.getDateTime());
        staffMemberListDataSource.writeData(staffMemberList);
        showStaffDataUser();
        DateTime.getDateTime(timeLabel);

        reportListDataSource = new ReportListFileDataSource("data", "ReportData.csv");
        readFilterReportData();

        timeAndHeadList = filterReport.getTimeAndHeadList();
        showListView();
        clearSelectedReport();
        handleSelectedListView();

    }

    @Override
    public void setStyle() {
        borderPane.getStylesheets().add(fontStr);
        borderPane.getStylesheets().add(fontSizeStr);
        borderPane.getStylesheets().add(themeStr);

        borderPane.getStyleClass().add("background");
        backgroundStaffHomeAnchorPane.getStyleClass().add("background");
        topLayoutAnchorPane.getStyleClass().add("anchorPane");
        timeLabel.getStyleClass().add("contentTextInAnchorPane");
        topicLabel.getStyleClass().add("contentTextInAnchorPane");
        topicLabel.getStyleClass().add("title");
        agencyTopicLabel.getStyleClass().add("contentTextInAnchorPane");
        agencyTopicLabel.getStyleClass().add("title");
        nameLabel.getStyleClass().add("contentTextInAnchorPane");
        nameLabel.getStyleClass().add("title");
        agencyLabel.getStyleClass().add("contentTextInAnchorPane");
        complaintListLabel.getStyleClass().add("title");
        headTopicLabel.getStyleClass().add("title");
        statusTopicLabel.getStyleClass().add("title");
        staffNameTopicLabel.getStyleClass().add("title");
        subjectLabel.getStyleClass().add("title");
        noticeLabel.getStyleClass().add("title");
        howToSolveLabel.getStyleClass().add("title");
        noticeTopicLabel.getStyleClass().add("title");
    }

    @FXML
    public void handleLogOutButton(ActionEvent actionEvent) {
        try {
            staffMember.setTimeLogin(DateTime.getDateTime());

            com.github.saacsos.FXRouter.goTo("login");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า login ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
            e.printStackTrace();
        }
    }

    @FXML
    public void handleProfileButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("staff_profile", staffMember);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า staff_profile ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
            e.printStackTrace();
        }
    }

    private void showStaffDataUser() {
        nameLabel.setText(staffMember.getName());
        File file = new File("data/profiles/" + staffMember.getPicture());
        imageStaff.setImage(new Image(file.toURI().toString()));
        imageStaff.setPreserveRatio(false);
        agencyLabel.setText(staffMember.getAgency());
    }

    private void readFilterReportData() {
        allReportList = reportListDataSource.readData();
        filterReport = allReportList.filterReport(new Filterer<Report>() {
            @Override
            public boolean filter(Report report) {
                return report.getCategory().equals(staffMember.getAgency());
            }
        });
    }

    private void showListView() {
        categoryListView.getItems().addAll(timeAndHeadList);
        categoryListView.refresh();
    }

    private void clearSelectedReport() {
        headLabel.setText("-");
        statusLabel.setText("-");
        staffNameLabel.setText("-");
        noticeLabel.setText("-");
        subjectReportTextArea.setText("");
        howToSolveTextArea.setText("");
    }

    private void handleSelectedListView() {
        categoryListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String report, String t1) {
                System.out.println(t1 + " is selected.");
                showSelectedReport(t1);
            }
        });
    }

    private void showSelectedReport(String reportString) {
        try {
            String[] strings = reportString.split(",");
//        System.out.println(strings[1]);
            report = allReportList.findReport(strings[1]);
            headLabel.setText(report.getHead());
            statusLabel.setText(report.getStatus());
            staffNameLabel.setText(report.getUsernameStaffManage());
            noticeLabel.setText("-");
            subjectReportTextArea.setText("เนื้อหา:\n" + report.getSubject() + "\n\nเนื้อหาเฉพาะ:\n" + report.getSubjectSpecific());
            if (report.getFeedback().equals("ไม่มีการตอบกลับ")) {
                howToSolveTextArea.setText("");
            } else {
                howToSolveTextArea.setText(report.getFeedback());
            }

            if(report.getStatus().equals("ดำเนินการเสร็จสิ้น")){
                finishStatusCheckbox.setSelected(true);
            }else{
                finishStatusCheckbox.setSelected(false);
            }

            labelAnimation.fadeIn(headLabel, Direction.up);
            labelAnimation.fadeIn(statusLabel, Direction.up);
            labelAnimation.fadeIn(staffNameLabel, Direction.up);

        } catch (NullPointerException e) {
            System.err.println("รายการเรื่องร้องเรียนถูกคัลกรอง");
        }

    }

    private void readStaffDataUser() {
        staffMemberList = staffMemberListDataSource.readData();
        staffMember = staffMemberList.findStaffMember(staffMember.getUsername());
    }

    @FXML
    public void handleAcceptToChooseReportButton(ActionEvent actionEvent) {
        try {
            if (report.getUsernameStaffManage().equals("ระบุไม่ได้")) {
                report.setUsernameStaffManage(staffMember.getUsername());
                report.updateStatus("กำลังดำเนินการ");
                noticeLabel.setText("ยืนยันเลือกเรื่องร้องเรียนนี้");
                noticeLabel.setStyle("-fx-text-fill: #63a82d");
                statusLabel.setText(report.getStatus());
                staffNameLabel.setText(report.getUsernameStaffManage());
                labelAnimation.fadeIn(staffNameLabel, Direction.up);
                labelAnimation.fadeIn(statusLabel, Direction.up);
                reportListDataSource.writeData(allReportList);
            } else if (report.getUsernameStaffManage().equals(staffMember.getUsername())) {
                noticeLabel.setText("เรื่องร้องเรียนนี้อยู่ในความดูแลโดยคุณ");
                noticeLabel.setStyle("-fx-text-fill: #63a82d");
            } else {
                noticeLabel.setText("มีเจ้าหน้าที่หน่วยงานนี้รับผิดชอบอยู่");
                noticeLabel.setStyle("-fx-text-fill: #63a82d");
            }
        } catch (NullPointerException e) {
            System.err.println("ยังไม่ได้เลือกรายการจาก ListView");
            noticeLabel.setText("ยังไม่ได้เลือกรายการเรื่องร้องเรียน");
            noticeLabel.setStyle("-fx-text-fill: #ff7070");
        }

        labelAnimation.fadeIn(noticeLabel, Direction.left);
    }

    @FXML
    public void handleSentFeedbackButton(ActionEvent actionEvent) {
        try {
            if (report.getUsernameStaffManage().equals(staffMember.getUsername())) {
                String text = howToSolveTextArea.getText().replace(",", " ").replace("\n", " ").trim();
                if(text.isEmpty()){
                    noticeLabel.setText("คุณยังไม่ได้ใส่วิธีการจัดการ");
                    noticeLabel.setStyle("-fx-text-fill: #ff7070");
                } else {
                    if (finishStatusCheckbox.isSelected()) {
                        report.updateStatus("ดำเนินการเสร็จสิ้น");
                    }else{
                        report.updateStatus("กำลังดำเนินการ");
                    }
                    report.setFeedback(text);
                    statusLabel.setText(report.getStatus());
                    labelAnimation.fadeIn(statusLabel, Direction.up);
                    noticeLabel.setText("ส่งวิธีการจัดการเรียบร้อย");
                    noticeLabel.setStyle("-fx-text-fill: #63a82d");
                    reportListDataSource.writeData(allReportList);
                }
            } else {
                noticeLabel.setText("คุณไม่สามารถจัดการเรื่องร้องเรียนนี้ได้");
                noticeLabel.setStyle("-fx-text-fill: #ff7070");
            }

        } catch (NullPointerException e) {
            System.err.println("ยังไม่ได้เลือกรายการเรื่องร้องเรียน");
            noticeLabel.setText("ยังไม่ได้เลือกรายการเรื่องร้องเรียน");
            noticeLabel.setStyle("-fx-text-fill: #ff7070");
            e.printStackTrace();
        }
        labelAnimation.fadeIn(noticeLabel, Direction.left);

    }

    @FXML
    public void handleGoToShowImageViewReportButton(ActionEvent actionEvent) {
        try {
            if (report.getPicture().equals("null")) {
                noticeLabel.setText("เรื่องร้องเรียนนี้ไม่มีภาพ");
                noticeLabel.setStyle("-fx-text-fill: #ff7070");
                labelAnimation.fadeIn(noticeLabel, Direction.left);
            } else {
                FXRouter.goTo("image_view_report", report);
            }
        } catch (NullPointerException e) {
            System.err.println("ยังไม่ได้เลือกเรื่องร้องเรียน");
            noticeLabel.setText("ยังไม่ได้เลือกรายการเรื่องร้องเรียน");
            noticeLabel.setStyle("-fx-text-fill: #ff7070");
            labelAnimation.fadeIn(noticeLabel, Direction.left);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void handleFilterAllReportButton(ActionEvent actionEvent) {
        filterReportButton.setText("ทั้งหมด");
        filterReport = allReportList.filterReport(new Filterer<Report>() {
            @Override
            public boolean filter(Report report) {
                return report.getCategory().equals(staffMember.getAgency());
            }
        });

        timeAndHeadList = filterReport.getTimeAndHeadList();
        categoryListView.getItems().clear();
        clearSelectedReport();
        showListView();
    }

    @FXML
    public void handleFilterWaitReportButton(ActionEvent actionEvent) {
        filterReportButton.setText("รอดำเนินการ");
        filterReport = allReportList.filterReport(new Filterer<Report>() {
            @Override
            public boolean filter(Report report) {
                return report.getCategory().equals(staffMember.getAgency()) && report.getStatus().equals("รอดำเนินการ");
            }
        });

        timeAndHeadList = filterReport.getTimeAndHeadList();
        clearSelectedReport();
        categoryListView.getItems().clear();
        showListView();
    }

    @FXML
    public void handleFilterManagingReportButton(ActionEvent actionEvent) {
        filterReportButton.setText("กำลังดำเนินการ");
        filterReport = allReportList.filterReport(new Filterer<Report>() {
            @Override
            public boolean filter(Report report) {
                return report.getCategory().equals(staffMember.getAgency()) && report.getStatus().equals("กำลังดำเนินการ");
            }
        });

        timeAndHeadList = filterReport.getTimeAndHeadList();
        categoryListView.getItems().clear();
        clearSelectedReport();
        showListView();
    }

    @FXML
    public void handleFilterFinishReportButton(ActionEvent actionEvent) {
        filterReportButton.setText("ดำเนินการเสร็จสิ้น");
        filterReport = allReportList.filterReport(new Filterer<Report>() {
            @Override
            public boolean filter(Report report) {
                return report.getCategory().equals(staffMember.getAgency()) && report.getStatus().equals("ดำเนินการเสร็จสิ้น");
            }
        });

        timeAndHeadList = filterReport.getTimeAndHeadList();
        categoryListView.getItems().clear();
        clearSelectedReport();
        showListView();
    }

}
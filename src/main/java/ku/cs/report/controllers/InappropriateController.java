package ku.cs.report.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import ku.cs.report.Animations.AnchorPaneAnimation;
import ku.cs.report.Animations.Direction;
import ku.cs.report.models.*;
import ku.cs.report.services.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class InappropriateController implements AppearanceConfig {

    @FXML
    private ListView<String> listViewReport;

    @FXML
    private Label categoryReportLabel;
    @FXML
    private Label headReportLabel;
    @FXML
    private Label timeReportLabel;
    @FXML
    private TextArea subjectTextArea;
    @FXML
    private Label dateTime;
    @FXML
    private Label noticeLabel;
    @FXML
    private AnchorPane anchorPane, mainAnchorPane, bodyAnchorPane;
    @FXML
    private Label nameLabel;
    private StudentMember studentMember;

    @FXML
    private ImageView imageView;
    private ReportList inappropriateList, reportList;
    private StudentMemberList studentMemberList;
    private Report report, inappropriate;
    private ArrayList<String> categoryHeadTimeList;
    private DataSource<ReportList> InappropriateDataSource = new ReportListFileDataSource("data", "Inappropriate.csv");
    private DataSource<StudentMemberList> memberListDataSource = new StudentMemberListFileDataSource("data", "StudentDataUser.csv");
    private DataSource<ReportList> reportListDataSource = new ReportListFileDataSource("data", "ReportData.csv");

    private String themeStr, fontSizeStr, fontStr;
    private final DataSource<Appearance> fontSizeDataSource = new AppearanceDataSource("data/appearance", "FontSize.csv");
    private final DataSource<Appearance> fontDataSource = new AppearanceDataSource("data/appearance", "Font.csv");
    private final DataSource<Appearance> themeDataSource = new AppearanceDataSource("data/appearance", "theme.csv");

    @FXML
    public void initialize() {
        AnchorPaneAnimation anchorPaneAnimation = new AnchorPaneAnimation();
        anchorPaneAnimation.fadeIn(mainAnchorPane, Direction.down);
        anchorPaneAnimation.fadeIn(bodyAnchorPane,Direction.up);

        CheckTimeTheme.check();
        Appearance appearanceSize = fontSizeDataSource.readData();
        Appearance appearanceFont = fontDataSource.readData();
        Appearance appearanceTheme = themeDataSource.readData();
        themeStr = getClass().getResource("/ku/cs/css/" + appearanceTheme.getData() + ".css").toExternalForm();
        fontStr = getClass().getResource("/ku/cs/css/Font" + appearanceFont.getData() + ".css").toExternalForm();
        fontSizeStr = getClass().getResource("/ku/cs/css/FontSize" + appearanceSize.getData() + ".css").toExternalForm();
        setStyle();

        inappropriateList = InappropriateDataSource.readData();
        studentMemberList = memberListDataSource.readData();
        reportList = reportListDataSource.readData();

        headReportLabel.setText("");
        categoryReportLabel.setText("");
        subjectTextArea.setText("");
        timeReportLabel.setText("");

        DateTime.getDateTime(dateTime);

        categoryHeadTimeList = inappropriateList.getCategoryHeadTime();
        showReportListView();
        handleSelectedListView();
        clearSelectedReport();

    }

    public void setStyle() {
        anchorPane.getStylesheets().add(fontStr);
        anchorPane.getStylesheets().add(fontSizeStr);
        anchorPane.getStylesheets().add(themeStr);

        anchorPane.getStyleClass().add("background");
        mainAnchorPane.getStyleClass().add("anchorPane");
        dateTime.getStyleClass().add("contentTextInAnchorPane");
    }

    @FXML
    public void handleBackButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("admin_home");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า admin_home ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
            e.printStackTrace();
        }
    }

    @FXML
    public void handleRequestUnbanButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("request_unban");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า request_unban ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
            e.printStackTrace();
        }
    }

    private void showReportListView() {
        listViewReport.getItems().addAll(categoryHeadTimeList);
        listViewReport.refresh();
    }

    private void clearSelectedReport() {
        categoryReportLabel.setText("----");
        timeReportLabel.setText("----");
        headReportLabel.setText("----");
        subjectTextArea.setText("----");
        noticeLabel.setText("");
        nameLabel.setText("----");
    }

    private void handleSelectedListView() {
        listViewReport.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String report, String t1) {
                System.out.println(t1 + " is selected.");
                showSelectedReport(t1);
            }
        });
    }

    private void showSelectedReport(String reportString) {
        try {
            String[] strings = reportString.split("          ");
            System.out.println(strings[1]);
            inappropriate = inappropriateList.findReport(strings[1]);
            headReportLabel.setText(inappropriate.getHead());
            categoryReportLabel.setText(inappropriate.getCategory());
            timeReportLabel.setText(inappropriate.getTime());
            nameLabel.setText(inappropriate.getUsernameReport());

            subjectTextArea.setText("เนื้อหา:\n" + inappropriate.getSubject());

            File file = new File("data/reportImages/" + inappropriate.getPicture());
            System.out.println(inappropriate.getPicture());
            imageView.setImage(new Image(file.toURI().toString()));


        } catch (NullPointerException e) {
            System.err.println("รายการเรื่องร้องเรียนถูกคัลกรอง");
        }
    }

    @FXML
    private void handleBanUserButton(ActionEvent actionEvent) {
        try {
            studentMember = studentMemberList.findMember(inappropriate.getUsernameReport());
            report = reportList.findReport(inappropriate.getHead());
            System.out.println(report.getHead());
            DeleteFile deleteFile = new DeleteFile("data/reportImages/" + inappropriate.getPicture());
            reportList.removeReport(report.getHead());
            reportListDataSource.writeData(reportList);
            studentMember.setBan();
            memberListDataSource.writeData(studentMemberList);
            handleDeleteReport(actionEvent);
            noticeLabel.setText("ระงับสิทธิ์ผู้ใช้งานเรียบร้อย");
            imageView.setImage(null);
        } catch (NullPointerException e) {
            noticeLabel.setText("คุณยังไม่ได้เลือกรายการ");
        }

    }

    @FXML
    private void handleDeleteReport(ActionEvent actionEvent) {
        try {
            inappropriateList.removeReport(inappropriate.getHead());
            InappropriateDataSource.writeData(inappropriateList);
            listViewReport.getItems().clear();
            listViewReport.refresh();
            categoryHeadTimeList = inappropriateList.getCategoryHeadTime();
            noticeLabel.setText("ลบรายการแล้ว");
            showReportListView();
            imageView.setImage(null);
        } catch (NullPointerException e) {
            noticeLabel.setText("กรุณาเลือกเรื่องร้องเรียน");
        }
    }

}

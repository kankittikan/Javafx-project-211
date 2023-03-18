package ku.cs.report.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import ku.cs.report.Animations.AnchorPaneAnimation;
import ku.cs.report.Animations.Direction;
import ku.cs.report.Animations.LabelAnimation;
import ku.cs.report.models.*;
import ku.cs.report.services.*;

import java.io.File;
import java.io.IOException;


public class StudentNewReportController implements AppearanceConfig {
    @FXML private TextField headTextField;
    @FXML private TextArea subjectTextField;
    @FXML private TextField subjectWantTextField;
    @FXML private Label dateTime;
    @FXML private Label usernameLabel;
    @FXML private Label subjectSpecificLabel;
    @FXML private ImageView reportImageView;
    @FXML private MenuButton categoryMenuButton = new MenuButton();
    @FXML private Label statusLabel;
    @FXML private ImageView miniImageView;

    @FXML private AnchorPane anchorPaneNav;
    @FXML private AnchorPane anchorPane;
    @FXML private Label newReportLabel;
    @FXML private AnchorPane bodyAnchorPane;


    private StudentMember student;
    private Boolean notEnterNewReportButton = false;
    private String pictureNameReport;
    private ReportList reportList = new ReportList();


    private String themeStr, fontSizeStr, fontStr;
    private final DataSource<ReportList> dataSourceReport = new ReportListFileDataSource("data", "ReportData.csv");
    private final DataSource<Appearance> fontSizeDataSource = new AppearanceDataSource("data/appearance", "FontSize.csv");
    private final DataSource<Appearance> fontDataSource = new AppearanceDataSource("data/appearance", "Font.csv");
    private final DataSource<Appearance> themeDataSource = new AppearanceDataSource("data/appearance", "theme.csv");
    private final LabelAnimation labelAnimation = new LabelAnimation();
    private final AnchorPaneAnimation anchorPaneAnimation = new AnchorPaneAnimation();

    @FXML
    public void initialize() {
        CheckTimeTheme.check();
        Appearance appearanceSize = fontSizeDataSource.readData();
        Appearance appearanceFont = fontDataSource.readData();
        Appearance appearanceTheme = themeDataSource.readData();
        themeStr = getClass().getResource("/ku/cs/css/" + appearanceTheme.getData() + ".css").toExternalForm();
        fontStr = getClass().getResource("/ku/cs/css/Font" + appearanceFont.getData() + ".css").toExternalForm();
        fontSizeStr = getClass().getResource("/ku/cs/css/FontSize" + appearanceSize.getData() + ".css").toExternalForm();
        setStyle();

        student = (StudentMember) com.github.saacsos.FXRouter.getData();
        student = StudentMemberList.readStudentDataUser(student.getUsername());
        DateTime.getDateTime(dateTime);

        statusLabel.setText("");
        File file = new File("data/profiles/" + student.getPicture());
        miniImageView.setImage(new Image(file.toURI().toString()));

        reportList = dataSourceReport.readData();
        usernameLabel.setText(student.getUsername());

        DynamicCategoryMenuButton setDynamicCategoryMenuButton = new DynamicCategoryMenuButton(categoryMenuButton, subjectSpecificLabel);

        System.out.println("Initialize StudentNewReportController");
    }

    public void setStyle() {
        anchorPane.getStylesheets().add(fontStr);
        anchorPane.getStylesheets().add(fontSizeStr);
        anchorPane.getStylesheets().add(themeStr);

        anchorPane.getStyleClass().add("background");
        anchorPaneNav.getStyleClass().add("anchorPane");
        newReportLabel.getStyleClass().add("title");
        usernameLabel.getStyleClass().add("contentTextInAnchorPane");
        dateTime.getStyleClass().add("contentTextInAnchorPane");
    }

    public void handleAddImageReportButton(ActionEvent actionEvent) {
        PictureFileChooser fileChooser = new PictureFileChooser();
        ImageWrite imageWrite = new ImageWrite(fileChooser.getDirName(), fileChooser.getFileName(), "reportImages");

        pictureNameReport = fileChooser.getFileName();

        //System.out.println(fileChooser.getFileName());
        File file = new File("data/reportImages/" + pictureNameReport);
        reportImageView.setImage(new Image(file.toURI().toString()));
    }

    public void handleDeleteImageReportButton(ActionEvent actionEvent) {
        File file = new File("data/reportImages/" + null);
        reportImageView.setImage(new Image(file.toURI().toString()));

        DeleteFile deleteFile = new DeleteFile("data/reportImages/" + pictureNameReport);
        pictureNameReport = null;
    }



    @FXML
    public void handleEnterButton(ActionEvent actionEvent) {
        String head = headTextField.getText();
        String subject = subjectTextField.getText();
        String subjectWant = subjectWantTextField.getText();
        String time = dateTime.getText();

        if (!head.isEmpty() && !categoryMenuButton.getText().equals("หมวดหมู่") && !subject.isEmpty() && !subjectWant.isEmpty()) {

            String categoryMenuItemText = categoryMenuButton.getText();
            reportList.addReport(new Report(categoryMenuItemText, headTextField.getText(), subject, subjectWant, student.getUsername(), 0, time, pictureNameReport));
            dataSourceReport.writeData(reportList);

            statusLabel.setText("แจ้งเรื่องร้องเรียนเรียบร้อย");
            statusLabel.setStyle("-fx-text-fill: #63a82d");
            labelAnimation.fadeIn(statusLabel, Direction.right);

            headTextField.clear();
            subjectWantTextField.clear();
            subjectTextField.clear();
            reportImageView.setImage(null);
            categoryMenuButton.setText("หมวดหมู่");
            notEnterNewReportButton = false;
            pictureNameReport = null;
        }
        else {
            anchorPaneAnimation.fadeIn(bodyAnchorPane,Direction.right);
            statusLabel.setText("กรอกข้อมูลไม่ครบถ้วน");
            statusLabel.setStyle("-fx-text-fill: #ff7070");
            labelAnimation.fadeIn(statusLabel,Direction.left);

            reportImageView.setImage(null);
            DeleteFile deleteFile = new DeleteFile("data/reportImages/" + pictureNameReport);
            notEnterNewReportButton = true;
            pictureNameReport = null;
        }
    }

    @FXML
    public void handleAllReportButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("student_all_report", student);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า student_home ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
            e.printStackTrace();
        }

    }
    @FXML
    public void handleBackButton(ActionEvent actionEvent) {
        if (notEnterNewReportButton) {
            handleDeleteImageReportButton(new ActionEvent());
        }
        try {
            com.github.saacsos.FXRouter.goTo("student_home");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า student_home ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
            e.printStackTrace();
        }
    }
}

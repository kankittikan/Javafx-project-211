package ku.cs.report.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import ku.cs.report.Animations.AnchorPaneAnimation;
import ku.cs.report.Animations.Direction;
import ku.cs.report.models.*;
import ku.cs.report.services.AppearanceDataSource;
import ku.cs.report.services.DataSource;
import ku.cs.report.services.StudentMemberListFileDataSource;

import java.io.File;
import java.io.IOException;

public class StudentHomeController implements AppearanceConfig {
    @FXML
    Label time;

    @FXML private ImageView miniImageStudent;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label username;
    @FXML
    private Label reportTop;

    @FXML
    private AnchorPane Ap;
    @FXML
    private AnchorPane ApCentral;

    @FXML
    private AnchorPane ApTop;


    private StudentMember student;
    private StudentMemberList studentMemberList;
    private final DataSource<StudentMemberList> dataSource = new StudentMemberListFileDataSource("data", "StudentDataUser.csv");

    private String themeStr, fontSizeStr, fontStr;
    private DataSource<Appearance> fontSizeDataSource = new AppearanceDataSource("data/appearance", "FontSize.csv");
    private DataSource<Appearance> fontDataSource = new AppearanceDataSource("data/appearance", "Font.csv");
    private DataSource<Appearance> themeDataSource = new AppearanceDataSource("data/appearance", "theme.csv");
    private Appearance appearanceSize;
    private Appearance appearanceFont;
    private Appearance appearanceTheme;

    private AnchorPaneAnimation anchorPaneAnimation = new AnchorPaneAnimation();

    @FXML
    public void initialize() {
        AnchorPaneAnimation anchorPaneAnimation = new AnchorPaneAnimation();
        anchorPaneAnimation.fadeIn(ApTop,Direction.down);
        anchorPaneAnimation.fadeIn(ApCentral,Direction.up);

        CheckTimeTheme.check();
        appearanceSize = fontSizeDataSource.readData();
        appearanceFont = fontDataSource.readData();
        appearanceTheme = themeDataSource.readData();
        themeStr = getClass().getResource("/ku/cs/css/" + appearanceTheme.getData() + ".css").toExternalForm();
        fontStr = getClass().getResource("/ku/cs/css/Font" + appearanceFont.getData() + ".css").toExternalForm();
        fontSizeStr = getClass().getResource("/ku/cs/css/FontSize" + appearanceSize.getData() + ".css").toExternalForm();
        setStyle();

        student = (StudentMember) com.github.saacsos.FXRouter.getData();
        DateTime.getDateTime(time);
        studentMemberList = dataSource.readData();
        student = studentMemberList.findMember(student.getUsername());
        usernameLabel.setText(student.getUsername());
        student.setTimeLogin(DateTime.getDateTime());
        dataSource.writeData(studentMemberList);
        anchorPaneAnimation.fadeIn(ApTop, Direction.down);
        anchorPaneAnimation.fadeIn(ApCentral, Direction.up);

        File file = new File("data/profiles/" + student.getPicture());
        miniImageStudent.setImage(new Image(file.toURI().toString()));

    }

    public void setStyle() {
        Ap.getStylesheets().add(fontStr);
        Ap.getStylesheets().add(fontSizeStr);
        Ap.getStylesheets().add(themeStr);

        Ap.getStyleClass().add("background");
        ApTop.getStyleClass().add("anchorPane");
        reportTop.getStyleClass().add("title");
        reportTop.getStyleClass().add("contentTextInAnchorPane");
        time.getStyleClass().add("contentTextInAnchorPane");
        username.getStyleClass().add("contentTextInAnchorPane");
        usernameLabel.getStyleClass().add("contentTextInAnchorPane");
        ApCentral.getStyleClass().add("anchorPanePopup");
    }

    @FXML
    public void handleLogOutButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("login");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า login ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
            e.printStackTrace();
        }

    }

    @FXML
    public void handleStudentProfileButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("student_profile", student);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า student_profile ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
            e.printStackTrace();
        }
    }

    @FXML
    public void handleStudentNewReportButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("student_new_report", student);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า student_profile ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
            e.printStackTrace();
        }
    }

    @FXML
    public void handleStudentAllReportButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("student_all_report", student);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า student_profile ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
            e.printStackTrace();
        }
    }

    @FXML
    public void handleStudentMyReportButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("student_my_report", student);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า student_profile ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
            e.printStackTrace();
        }
    }
}
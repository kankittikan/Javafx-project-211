package ku.cs.report.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.github.saacsos.FXRouter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import ku.cs.report.Animations.*;
import ku.cs.report.models.*;
import ku.cs.report.services.*;

import java.io.IOException;
import java.util.ArrayList;

public class LoginController implements AppearanceConfig {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField userNameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label loginFailText;
    @FXML
    private Label time;
    @FXML
    private ImageView image;
    @FXML
    private ImageView logo;
    @FXML
    private Button requestButton;
    @FXML
    private AnchorPane background;
    @FXML
    private AnchorPane topAnchorPane;
    @FXML
    private AnchorPane downAnchorPane;
    @FXML
    private Button aboutButton;
    @FXML
    private Button howToUseButton;
    @FXML
    private Button settingButton;
    @FXML
    private Label titleLabel;
    int count;
    private DataSource<MemberList> adminMemberListDataSource = new AdminMemberListFileDataSource("data", "AdminDataUser.csv");
    private DataSource<StudentMemberList> studentMemberListDataSource = new StudentMemberListFileDataSource("data", "StudentDataUser.csv");
    private DataSource<StaffMemberList> staffMemberListDataSource = new StaffMemberListFileDataSource("data", "StaffDataUser.csv");
    private AnchorPaneAnimation anchorPaneAnimation;
    private ButtonAnimation buttonAnimation;
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

        anchorPaneAnimation = new AnchorPaneAnimation();
        labelAnimation = new LabelAnimation();
        buttonAnimation = new ButtonAnimation();
        anchorPaneAnimation.fadeIn(anchorPane, Direction.up);
        labelAnimation.fadeIn(titleLabel, Direction.down);
        System.out.println("Initialize LoginController");
        logo.setImage(new Image(getClass().getResource("/ku/cs/images/KU_Logo.png").toExternalForm()));
        slideShow();
        DateTime.getDateTime(time);
        loginFailText.setAlignment(Pos.CENTER);
        requestButton.setOpacity(0);
    }

    public void setStyle() {
        background.getStylesheets().add(fontStr);
        background.getStylesheets().add(fontSizeStr);
        background.getStylesheets().add(themeStr);

        background.getStyleClass().add("background");
        topAnchorPane.getStyleClass().add("anchorPane");
        downAnchorPane.getStyleClass().add("anchorPane");
        aboutButton.getStyleClass().add("buttonInAnchorPane");
        howToUseButton.getStyleClass().add("buttonInAnchorPane");
        settingButton.getStyleClass().add("buttonInAnchorPane");
        titleLabel.getStyleClass().add("title");
        titleLabel.getStyleClass().add("contentTextInAnchorPane");
        time.getStyleClass().add("contentTextInAnchorPane");
        loginFailText.setStyle("-fx-text-fill: #ff7070");
    }

    public void slideShow() {
        image.setImage(new Image(getClass().getResource("/ku/cs/images/ku4.jpg").toExternalForm()));
        ArrayList<Image> images = new ArrayList<Image>();
        images.add(new Image(getClass().getResource("/ku/cs/images/ku1.jpg").toExternalForm()));
        images.add(new Image(getClass().getResource("/ku/cs/images/ku2.jpg").toExternalForm()));
        images.add(new Image(getClass().getResource("/ku/cs/images/ku3.jpg").toExternalForm()));
        images.add(new Image(getClass().getResource("/ku/cs/images/ku4.jpg").toExternalForm()));

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(4), event -> {
            image.setImage(images.get(count));
            count++;
            if (count == images.size()) count = 0;
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    public void handleAboutButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("about");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า about ไม่ได้");
            System.err.println("ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleSettingButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("setting");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า setting ไม่ได้");
            System.err.println("ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleHowtoButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("how_to_use");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า how to use ไม่ได้");
            System.err.println("ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleRegisterButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("register");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า register ไม่ได้");
            System.err.println("ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleLoginButton(ActionEvent actionEvent) {
        if (requestButton.getOpacity() > 0.5) buttonAnimation.fadeOut(requestButton, Direction.down);
        try {
            MemberList adminMemberList = adminMemberListDataSource.readData();
            StudentMemberList studentMemberList = studentMemberListDataSource.readData();
            StaffMemberList staffMemberList = staffMemberListDataSource.readData();
            Member adminMember;
            StudentMember studentMember;
            StaffMember staffMember;

            adminMember = adminMemberList.findMemberWithPassword(userNameTextField.getText(), passwordField.getText());
            studentMember = studentMemberList.findMemberWithPassword(userNameTextField.getText(), passwordField.getText());
            staffMember = staffMemberList.findStaffMemberWithPassword(userNameTextField.getText(), passwordField.getText());

            if (adminMember == null && studentMember == null && staffMember == null) {
                userNameTextField.clear();
                passwordField.clear();
                loginFailText.setText("ชื่อผู้ใช้ หรือรหัสผ่านไม่ถูกต้อง");
                labelAnimation.fadeIn(loginFailText, Direction.right);

            } else {
                if (adminMember != null) FXRouter.goTo("admin_home", adminMember);
                if (staffMember != null && !staffMember.getAgency().equals("ไม่มีหน่วยงาน"))
                    FXRouter.goTo("staff_home", staffMember);
                if (staffMember != null && staffMember.getAgency().equals("ไม่มีหน่วยงาน")) {
                    loginFailText.setText("ไม่พบหน่วยงาน ติดต่อผู้ดูแลระบบ");
                    labelAnimation.fadeIn(loginFailText, Direction.right);
                    userNameTextField.clear();
                    passwordField.clear();
                    return;
                }
                if (studentMember != null && studentMember.getIsBaned().equals("No"))
                    FXRouter.goTo("student_home", studentMember);
                else {
                    loginFailText.setText("คุณถูกแบน ติดต่อเจ้าหน้าที่");
                    labelAnimation.fadeIn(loginFailText, Direction.right);
                    userNameTextField.clear();
                    passwordField.clear();
                    buttonAnimation.fadeIn(requestButton, Direction.up);
                }
            }

        } catch (IOException e) {
            System.err.println("ไปที่หน้า Admin, Student, Staff ไม่ได้");
            System.err.println("ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleRequestButton(ActionEvent actionEvent) {
        if (requestButton.getOpacity() > 0.5) {
            try {
                FXRouter.goTo("request");
            } catch (IOException e) {
                System.err.println("ไปที่หน้า request ไม่ได้");
                System.err.println("ตรวจสอบการกำหนด route");
                e.printStackTrace();
            }
        }
    }


    @FXML
    public void handleForgotPasswordButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("forgot_password");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า forgot password ไม่ได้");
            System.err.println("ตรวจสอบการกำหนด route");
        }
    }
}

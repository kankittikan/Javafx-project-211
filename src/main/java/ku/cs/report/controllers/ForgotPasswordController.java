package ku.cs.report.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.github.saacsos.FXRouter;
import javafx.scene.layout.AnchorPane;
import ku.cs.report.Animations.AnchorPaneAnimation;
import ku.cs.report.Animations.Direction;
import ku.cs.report.Animations.LabelAnimation;
import ku.cs.report.models.*;
import ku.cs.report.services.AppearanceDataSource;
import ku.cs.report.services.DataSource;
import ku.cs.report.services.StudentMemberListFileDataSource;
import ku.cs.report.services.StaffMemberListFileDataSource;

import java.io.IOException;

public class ForgotPasswordController implements AppearanceConfig {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private AnchorPane topAnchorPane;
    @FXML
    private AnchorPane downAnchorPane;

    @FXML
    private ImageView logo;
    @FXML
    private Button backButton;
    @FXML
    private Label time;
    @FXML
    private TextField userNameTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField newPasswordTextField;
    @FXML
    private Label resetFailTextField;
    @FXML private Label titleLabel;
    @FXML private AnchorPane background;
    private DataSource<StudentMemberList> studentMemberListDataSource = new StudentMemberListFileDataSource("data", "StudentDataUser.csv");
    private DataSource<StaffMemberList> staffMemberListDataSource = new StaffMemberListFileDataSource("data", "StaffDataUser.csv");

    private AnchorPaneAnimation anchorPaneAnimation;
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
        anchorPaneAnimation.fadeIn(anchorPane, Direction.up);
        labelAnimation.fadeIn(titleLabel, Direction.down);
        System.out.println("Initialize ResetPasswordController");
        logo.setImage(new Image(getClass().getResource("/ku/cs/images/KU_Logo.png").toExternalForm()));
        DateTime.getDateTime(time);
        resetFailTextField.setAlignment(Pos.CENTER);
    }

    public void setStyle() {
        background.getStylesheets().add(fontStr);
        background.getStylesheets().add(fontSizeStr);
        background.getStylesheets().add(themeStr);

        background.getStyleClass().add("background");
        topAnchorPane.getStyleClass().add("anchorPane");
        downAnchorPane.getStyleClass().add("anchorPane");
        backButton.getStyleClass().add("buttonInAnchorPane");
        titleLabel.getStyleClass().add("title");
        titleLabel.getStyleClass().add("contentTextInAnchorPane");
        time.getStyleClass().add("contentTextInAnchorPane");
    }

    @FXML
    public void handlebackButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("login");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า login ไม่ได้");
            System.err.println("ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleresetPasswordButton(ActionEvent actionEvent) {
        StudentMemberList studentMemberList = studentMemberListDataSource.readData();
        StaffMemberList staffMemberList = staffMemberListDataSource.readData();
        StaffMember staffMember;
        StudentMember studentMember;

        staffMember = staffMemberList.findStaffMember(userNameTextField.getText());
        studentMember = studentMemberList.findMember(userNameTextField.getText());

        if (staffMember == null && studentMember == null) {
            userNameTextField.clear();
            nameTextField.clear();
            newPasswordTextField.clear();
            resetFailTextField.setText("ชื่อผู้ใช้ หรือชื่อ-นามสกุลไม่ถูกต้อง");
            labelAnimation.fadeIn(resetFailTextField, Direction.left);
            resetFailTextField.setStyle("-fx-text-fill: #ff7070");
            return;
        }
        if (newPasswordTextField.getText().equals("")) {
            userNameTextField.clear();
            nameTextField.clear();
            newPasswordTextField.clear();
            resetFailTextField.setText("ตรวจสอบรหัสผ่านใหม่");
            labelAnimation.fadeIn(resetFailTextField, Direction.left);
            resetFailTextField.setStyle("-fx-text-fill: #ff7070");
            return;
        }
        else {
            if (studentMember != null) {
                studentMember.changePassword(userNameTextField.getText(), studentMember.getPassword(), newPasswordTextField.getText());
                studentMemberListDataSource.writeData(studentMemberList);
            }
            if (staffMember != null) {
                staffMember.changePassword(userNameTextField.getText(), staffMember.getPassword(), newPasswordTextField.getText());
                staffMemberListDataSource.writeData(staffMemberList);
            }
            resetFailTextField.setText("รีเซ็ตรหัสผ่านสำเร็จ");
            labelAnimation.fadeIn(resetFailTextField, Direction.left);
            resetFailTextField.setStyle("-fx-text-fill: #63a82d");
            userNameTextField.clear();
            nameTextField.clear();
            newPasswordTextField.clear();
        }

    }
}

package ku.cs.report.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import ku.cs.report.Animations.AnchorPaneAnimation;
import ku.cs.report.Animations.Direction;
import ku.cs.report.Animations.LabelAnimation;
import ku.cs.report.models.*;
import ku.cs.report.services.AppearanceDataSource;
import ku.cs.report.services.DataSource;
import ku.cs.report.services.StudentMemberListFileDataSource;

import java.io.IOException;

public class RegisterController implements AppearanceConfig {

    @FXML
    private TextField usernameRegister;
    @FXML
    private PasswordField passwordRegister;
    @FXML
    private PasswordField confirmPasswordRegister;
    @FXML
    private Label passwordStatus;
    @FXML
    private Label usernameStatus;
    @FXML
    private Label registerStatus;
    @FXML
    private TextField nameRegister;
    @FXML
    private Label time;
    @FXML
    private Label nameStatus;

    @FXML
    private Label registerLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label confirmPasswordLabel;
    @FXML
    private AnchorPane anchorPaneNav;
    @FXML
    private AnchorPane anchorPaneBottom;
    @FXML
    private Label alreadyAccountLabel;
    @FXML
    private AnchorPane registerAnchorPane;
    @FXML
    private Button haveAccount;
    @FXML private AnchorPane anchorPane;

    private StudentMemberList studentMemberList = new StudentMemberList();
    private final DataSource<StudentMemberList> dataSource = new StudentMemberListFileDataSource("data", "StudentDataUser.csv");

    private final AnchorPaneAnimation anchorPaneAnimation = new AnchorPaneAnimation();
    private final LabelAnimation labelAnimation = new LabelAnimation();

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

        anchorPaneAnimation.fadeIn(registerAnchorPane, Direction.up);
        labelAnimation.fadeIn(registerLabel, Direction.down);
        DateTime.getDateTime(time);
        registerStatus.setOpacity(0);
        usernameStatus.setOpacity(0);
        nameStatus.setOpacity(0);
        passwordStatus.setOpacity(0);

        studentMemberList = dataSource.readData();
        System.out.println("Initialize RegisterController");
    }

    public void setStyle() {
        anchorPane.getStylesheets().add(fontStr);
        anchorPane.getStylesheets().add(fontSizeStr);
        anchorPane.getStylesheets().add(themeStr);

        anchorPane.getStyleClass().add("background");
        anchorPaneNav.getStyleClass().add("anchorPane");
        anchorPaneBottom.getStyleClass().add("anchorPane");
        registerAnchorPane.getStyleClass().add("anchorPanePopup");
        registerLabel.getStyleClass().add("title");
        registerLabel.getStyleClass().add("contentTextInAnchorPane");
        time.getStyleClass().add("contentTextInAnchorPane");
        haveAccount.getStyleClass().add("buttonInAnchorPane");
        alreadyAccountLabel.getStyleClass().add("contentTextInAnchorPane");
        usernameStatus.setStyle("-fx-text-fill: #ff7070");
        passwordStatus.setStyle("-fx-text-fill: #ff7070");
        nameStatus.setStyle("-fx-text-fill: #ff7070");
        registerStatus.setStyle("-fx-text-fill: #63a82d");
    }

    public void clearField() {
        usernameRegister.clear();
        nameRegister.clear();
        passwordRegister.clear();
        confirmPasswordRegister.clear();
    }

    public void setUsernameStatus(String text, String color) {
        usernameStatus.setOpacity(1);

        usernameStatus.setText(text);
    }

    public void setNameStatus(String text, String color) {
        nameStatus.setOpacity(1);

        nameStatus.setText(text);
    }

    public void setPasswordStatus(String text) {
        passwordStatus.setOpacity(1);

        passwordStatus.setText(text);
    }

    @FXML
    public void handleRegisterButton(ActionEvent actionEvent) {
        String username = usernameRegister.getText();
        String password = passwordRegister.getText();
        String name = nameRegister.getText();
        String confirmPassword = confirmPasswordRegister.getText();

        if (username.isEmpty() && name.isEmpty() && password.isEmpty() && confirmPassword.isEmpty()) {
            System.err.println("Username name and password not in field box.");
            setUsernameStatus("กรุณาระบุชื่อผู้ใช้", "Red");
            labelAnimation.fadeIn(usernameStatus, Direction.left);
            setNameStatus("กรุณาระบุชื่อ-นามสกุล", "Red");
            labelAnimation.fadeIn(nameStatus, Direction.left);
            setPasswordStatus("กรุณากรอกรหัสผ่านให้ครบถ้วน");
            labelAnimation.fadeIn(passwordStatus, Direction.left);
            clearField();
            registerStatus.setOpacity(0);
        }
        if (username.equals("")) {
            setUsernameStatus("กรุณาระบุชื่อผู้ใช้", "Red");
            labelAnimation.fadeIn(usernameStatus, Direction.left);
            registerStatus.setOpacity(0);
        } else if (!username.isEmpty()) {
            if (studentMemberList.checkUsername(username) == 1) {
                setUsernameStatus("ชื่อผู้ใช้ถูกใช้งานไปแล้ว", "Red");
                labelAnimation.fadeIn(usernameStatus, Direction.left);
                System.err.println("Already username.");
                registerStatus.setOpacity(0);
            } else if (studentMemberList.checkUsername(username) == 0) {
                usernameStatus.setOpacity(0);
                registerStatus.setOpacity(0);
            }
        }

        if (name.equals("")) {
            setNameStatus("กรุณาระบุชื่อ-นามสกุล", "Red");
            labelAnimation.fadeIn(nameStatus, Direction.left);
            registerStatus.setOpacity(0);
        } else if (!name.isEmpty()) {
            nameStatus.setOpacity(0);
            registerStatus.setOpacity(0);
        }

        if (password.equals("") && confirmPassword.equals("")) {
            setPasswordStatus("กรุณากรอกรหัสผ่านให้ครบถ้วน");
            labelAnimation.fadeIn(passwordStatus, Direction.left);
            registerStatus.setOpacity(0);
        } else if (password.isEmpty() && !confirmPassword.isEmpty() || !password.isEmpty() && confirmPassword.isEmpty()) {
            setPasswordStatus("กรุณากรอกรหัสผ่านให้ครบถ้วน");
            labelAnimation.fadeIn(passwordStatus, Direction.left);
            registerStatus.setOpacity(0);
        } else if (!password.equals(confirmPassword)) {
            setPasswordStatus("รหัสผ่านไม่ตรงกัน ลองอีกครั้ง");
            labelAnimation.fadeIn(passwordStatus, Direction.left);
            registerStatus.setOpacity(0);
        }

        if (!username.isEmpty() && !name.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty() && password.equals(confirmPassword)) {
            if (studentMemberList.checkUsername(username) == 0) {
                studentMemberList.addMember(new StudentMember(username, name, password));
                dataSource.writeData(studentMemberList);
                usernameStatus.setOpacity(0);
                passwordStatus.setOpacity(0);
                clearField();
                registerStatus.setOpacity(1);
                registerStatus.setText("ลงทะเบียนเรียบร้อยแล้ว");

                labelAnimation.fadeIn(registerStatus, Direction.left);
                System.out.println("Register Success.");

            } else if (studentMemberList.checkUsername(username) == 1) {
                setUsernameStatus("ชื่อผู้ใช้ถูกใช้งานไปแล้ว", "Red");
                labelAnimation.fadeIn(usernameStatus, Direction.left);
                System.err.println("Already username.");
                passwordStatus.setOpacity(0);
                passwordRegister.clear();
                confirmPasswordRegister.clear();
                registerStatus.setOpacity(0);
            }
        }

    }

    @FXML
    public void handleAlreadyHaveAccountButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("login");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า login ไม่ได้");
            System.err.println("ตรวจสอบการกำหนด route");
        }
    }
}

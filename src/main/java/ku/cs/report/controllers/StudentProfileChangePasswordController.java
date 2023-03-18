package ku.cs.report.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import ku.cs.report.Animations.AnchorPaneAnimation;
import ku.cs.report.Animations.Direction;
import ku.cs.report.Animations.LabelAnimation;
import ku.cs.report.models.*;
import ku.cs.report.services.AppearanceDataSource;
import ku.cs.report.services.DataSource;
import ku.cs.report.services.StudentMemberListFileDataSource;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;


import java.io.File;
import java.io.IOException;

public class StudentProfileChangePasswordController implements AppearanceConfig {
    @FXML private TextField usernameField;
    @FXML private PasswordField oldPasswordField;
    @FXML private PasswordField newPasswordField;
    @FXML private PasswordField confirmNewPasswordFiled;
    @FXML private Label usernameStatus;
    @FXML private Label oldPasswordStatus;
    @FXML private Label newPasswordStatus;
    @FXML private Label status;
    @FXML private Label time;
    @FXML private Label usernameLabel;
    @FXML private ImageView  miniImageView;

    @FXML private AnchorPane changePasswordAnchorPane;
    @FXML private AnchorPane navAnchorPane;
    @FXML private AnchorPane mainAnchorPane;
    @FXML private Label changePasswordLabel; //#838485
    @FXML private Label usernameHeadLabel;
    @FXML private Label passwordHeadLabel;
    @FXML private Label newPasswordHeadLabel;
    @FXML private Label confirmNewPasswordLabel;
    @FXML private Button backButton;
    @FXML private Button enterButton;

    private StudentMemberList studentMemberList = new StudentMemberList();
    private StudentMember student;
    private final DataSource<StudentMemberList> dataSource = new StudentMemberListFileDataSource("data","StudentDataUser.csv");

    private AnchorPaneAnimation anchorPaneAnimation = new AnchorPaneAnimation();
    private LabelAnimation labelAnimation = new LabelAnimation();

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

        anchorPaneAnimation.fadeIn(changePasswordAnchorPane, Direction.up);

        System.out.println("Initialize StudentProfileChangePassword");
        DateTime.getDateTime(time);

        studentMemberList = dataSource.readData();
        student = (StudentMember) com.github.saacsos.FXRouter.getData();
        student = StudentMemberList.readStudentDataUser(student.getUsername());
        usernameLabel.setText(student.getUsername());
        File file = new File("data/profiles/" + student.getPicture());
        miniImageView.setImage(new Image(file.toURI().toString()));

        usernameStatus.setOpacity(0);
        oldPasswordStatus.setOpacity(0);
        newPasswordStatus.setOpacity(0);
        status.setOpacity(0);

    }

    public void setStyle() {
        mainAnchorPane.getStylesheets().add(fontStr);
        mainAnchorPane.getStylesheets().add(fontSizeStr);
        mainAnchorPane.getStylesheets().add(themeStr);

        mainAnchorPane.getStyleClass().add("background");
        navAnchorPane.getStyleClass().add("anchorPane");
        changePasswordAnchorPane.getStyleClass().add("anchorPanePopup");
        time.getStyleClass().add("contentTextInAnchorPane");
        usernameLabel.getStyleClass().add("contentTextInAnchorPane");
        usernameStatus.setStyle("-fx-text-fill: #ff7070");
        oldPasswordStatus.setStyle("-fx-text-fill: #ff7070");
        newPasswordStatus.setStyle("-fx-text-fill: #ff7070");
        status.setStyle("-fx-text-fill: #63a82d");
    }

    @FXML
    public void handleChangePasswordButton(ActionEvent actionEvent){
        String usernameFXML = usernameField.getText();
        String oldPasswordFXML = oldPasswordField.getText();
        String newPasswordFXML = newPasswordField.getText();
        String confirmNewPasswordFXML = confirmNewPasswordFiled.getText();


        //username
        if (!usernameFXML.equals(student.getUsername())){
            setStatus(usernameStatus,"ชื่อผู้ใช้ไม่ถูกต้อง");
            labelAnimation.fadeIn(usernameStatus,Direction.left);
        } else {
            usernameStatus.setOpacity(0);
        }

        //password
        if (!oldPasswordFXML.equals(student.getPassword())){
            setStatus(oldPasswordStatus,"รหัสผ่านไม่ถูกต้อง");
            labelAnimation.fadeIn(oldPasswordStatus,Direction.left);
        } else {
            oldPasswordStatus.setOpacity(0);
        }

        //new password
        if (!newPasswordFXML.equals(confirmNewPasswordFXML) && !newPasswordFXML.isEmpty() && !confirmNewPasswordFXML.isEmpty()){
            setStatus(newPasswordStatus,"รหัสผ่านไม่ตรงกัน");
            labelAnimation.fadeIn(newPasswordStatus,Direction.left);
        }else {
            newPasswordStatus.setOpacity(0);
        }

        //All Check pass
        if (studentMemberList.findMemberData(student) &&
                student.getPassword().equals(oldPasswordFXML) &&
                newPasswordFXML.equals(confirmNewPasswordFXML) &&
                !newPasswordFXML.isEmpty() &&
                !confirmNewPasswordFXML.isEmpty()){

            studentMemberList.addMember(studentMemberList.findIndexMember(student),new StudentMember(student.getUsername(),student.getName(),newPasswordFXML,student.getPicture(),student.getTimeLogin()));
            studentMemberList.removeMember(studentMemberList.findIndexMember(student));
            dataSource.writeData(studentMemberList);
            student = StudentMemberList.readStudentDataUser(student.getUsername());
            clearFiled();
            setStatus(status,"แก้ไขรหัสผ่านเรียบร้อยแล้ว");
            labelAnimation.fadeIn(status,Direction.right);
        }else {
            status.setOpacity(0);
        }
    }

    @FXML
    public void handleBackToProfileButton(ActionEvent actionEvent){
        try{
            com.github.saacsos.FXRouter.goTo("student_profile");
        } catch (IOException e){
            System.err.println("ไปที่หน้า student_profile ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
            e.printStackTrace();
        }
    }

    public void setStatus(Label status, String text){
        status.setOpacity(1);
        status.setText(text);
    }

    public void clearFiled(){
        usernameField.clear();
        oldPasswordField.clear();
        newPasswordField.clear();
        confirmNewPasswordFiled.clear();
    }
}

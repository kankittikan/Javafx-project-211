package ku.cs.report.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.github.saacsos.FXRouter;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import ku.cs.report.Animations.AnchorPaneAnimation;
import ku.cs.report.Animations.Direction;
import ku.cs.report.Animations.LabelAnimation;
import ku.cs.report.models.*;
import ku.cs.report.services.AppearanceDataSource;
import ku.cs.report.services.DataSource;
import ku.cs.report.services.StaffMemberListFileDataSource;

import java.io.IOException;

public class StaffProfileChangePasswordController implements AppearanceConfig {

    @FXML private Label timeLabel;
    @FXML private Label noticeLabel;
    @FXML private TextField usernameTextField;
    @FXML private PasswordField oldPasswordField;
    @FXML private PasswordField newPasswordField;
    @FXML private PasswordField acceptPasswordField;
    @FXML private AnchorPane changePasswordAnchorPane, backgroundStaffChangePasswordAnchorPane, topLayoutAnchorPane, bottomLayoutAnchorPane;
    @FXML private Button backButton;
    @FXML private Label topicLabel;
    AnchorPaneAnimation anchorPaneAnimation;
    @FXML private BorderPane borderPane;

    private DataSource<StaffMemberList> dataSource = new StaffMemberListFileDataSource("data","StaffDataUser.csv");
    private StaffMemberList staffMemberList;
    private StaffMember staffMember;
    private LabelAnimation labelAnimation;

    private String themeStr, fontSizeStr, fontStr;
    private DataSource<Appearance> fontSizeDataSource = new AppearanceDataSource("data/appearance", "FontSize.csv");
    private DataSource<Appearance> fontDataSource = new AppearanceDataSource("data/appearance", "Font.csv");
    private DataSource<Appearance> themeDataSource = new AppearanceDataSource("data/appearance", "theme.csv");
    private Appearance appearanceSize;
    private Appearance appearanceFont;
    private Appearance appearanceTheme;

    @FXML void initialize(){
        CheckTimeTheme.check();
        appearanceSize = fontSizeDataSource.readData();
        appearanceFont = fontDataSource.readData();
        appearanceTheme = themeDataSource.readData();
        themeStr = getClass().getResource("/ku/cs/css/" + appearanceTheme.getData() + ".css").toExternalForm();
        fontStr = getClass().getResource("/ku/cs/css/Font" + appearanceFont.getData() + ".css").toExternalForm();
        fontSizeStr = getClass().getResource("/ku/cs/css/FontSize" + appearanceSize.getData() + ".css").toExternalForm();
        setStyle();

        System.out.println("Initialize StaffProfileChangePassword");
        DateTime.getDateTime(timeLabel);
        labelAnimation = new LabelAnimation();
        anchorPaneAnimation = new AnchorPaneAnimation();

        // getData from StaffProfile
        staffMember = (StaffMember) FXRouter.getData();
        readStaffDataUser();
        // not show noticeLabel
        noticeLabel.setOpacity(0);
        clearTextField();

        anchorPaneAnimation.fadeIn(changePasswordAnchorPane, Direction.up);
    }
    @Override
    public void setStyle() {
        borderPane.getStylesheets().add(fontStr);
        borderPane.getStylesheets().add(fontSizeStr);
        borderPane.getStylesheets().add(themeStr);

        borderPane.getStyleClass().add("background");
        backgroundStaffChangePasswordAnchorPane.getStyleClass().add("background");
        topLayoutAnchorPane.getStyleClass().add("anchorPane");
        bottomLayoutAnchorPane.getStyleClass().add("anchorPane");
        backButton.getStyleClass().add("buttonInAnchorPane");
        changePasswordAnchorPane.getStyleClass().add("anchorPanePopup");
        timeLabel.getStyleClass().add("contentTextInAnchorPane");
        topicLabel.getStyleClass().add("contentTextInAnchorPane");
        topicLabel.getStyleClass().add("title");
    }

    @FXML
    public void handleLogOutButton(ActionEvent actionEvent){
        try{
            FXRouter.goTo("login");
        } catch (IOException e){
            System.err.println("ไปที่หน้า login ไม่ได้");
            System.err.println("โปรดตรวจสอบการกําหนด route");
            e.printStackTrace();
        }
    }
    @FXML
    public void handleUndoButton(ActionEvent actionEvent){
        try {
            FXRouter.goTo("staff_profile", staffMember);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า staff_home ไม่ได้");
            System.err.println("โปรดตรวจสอบการกําหนด route");
            e.printStackTrace();
        }
    }
    @FXML
    public void handleChangePasswordButton(ActionEvent actionEvent){
        String username = usernameTextField.getText();
        String oldPassword = oldPasswordField.getText();
        String newPassword = newPasswordField.getText();
        String acceptPassword = acceptPasswordField.getText();

        if(username.isEmpty() || oldPassword.isEmpty() || newPassword.isEmpty() || acceptPassword.isEmpty()){
            setStatus("กรอกข้อมูลยังไม่ครบ");
            noticeLabel.setStyle("-fx-text-fill: #ff7070");
        }else {
            if(!username.equals(staffMember.getUsername())){
                setStatus("ชื่อผู้ใช้งานไม่ถูกต้อง");
                noticeLabel.setStyle("-fx-text-fill: #ff7070");
            } else if (!oldPassword.equals(staffMember.getPassword())) {
                setStatus("รหัสผ่านไม่ถูกต้อง");
                noticeLabel.setStyle("-fx-text-fill: #ff7070");
            } else if(!newPassword.equals(acceptPassword)){
                setStatus("ยืนยันรหัสผ่านไม่ถูกต้อง");
                noticeLabel.setStyle("-fx-text-fill: #ff7070");
            } else if (oldPassword.equals(newPassword)) {
                setStatus("รหัสผ่านไม่มีการเปลี่ยนแปลง");
                noticeLabel.setStyle("-fx-text-fill: #ff7070");
            } else{
                staffMember.changePassword(username, oldPassword, newPassword);
                dataSource.writeData(staffMemberList);
                setStatus("แก้ไขรหัสผ่านเรียบร้อยแล้ว");
                noticeLabel.setStyle("-fx-text-fill: #63a82d");
                System.out.println("Success: " + staffMember.toString());
            }
            clearTextField();
        }

    }

    public void setStatus(String notice){
        noticeLabel.setText(notice);
        noticeLabel.setOpacity(1);
        labelAnimation.fadeIn(noticeLabel, Direction.up);
    }

    public void clearTextField(){
        usernameTextField.clear();
        oldPasswordField.clear();
        newPasswordField.clear();
        acceptPasswordField.clear();
    }

    private void readStaffDataUser() {
        staffMemberList = dataSource.readData();
        staffMember = staffMemberList.findStaffMember(staffMember.getUsername());
    }
}

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
import ku.cs.report.services.AdminMemberListFileDataSource;
import ku.cs.report.services.AppearanceDataSource;
import ku.cs.report.services.DataSource;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;


import java.io.File;
import java.io.IOException;

public class AdminProfileChangePasswordController implements AppearanceConfig{
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
    @FXML private ImageView  image;
    @FXML private AnchorPane mainAnchorPane;
    @FXML private AnchorPane navAnchorPane;
    @FXML private AnchorPane changePasswordAnchorPane;
    @FXML private Label changePasswordLabel;

    private MemberList memberList = new MemberList();
    private Member member;
    private final DataSource<MemberList> dataSource = new AdminMemberListFileDataSource("data","AdminDataUser.csv");

    private String themeStr, fontSizeStr, fontStr;
    private final DataSource<Appearance> fontSizeDataSource = new AppearanceDataSource("data/appearance", "FontSize.csv");
    private final DataSource<Appearance> fontDataSource = new AppearanceDataSource("data/appearance", "Font.csv");
    private final DataSource<Appearance> themeDataSource = new AppearanceDataSource("data/appearance", "theme.csv");


    @FXML
    public void initialize() {
        AnchorPaneAnimation anchorPaneAnimation = new AnchorPaneAnimation();
        anchorPaneAnimation.fadeIn(navAnchorPane, Direction.down);
        anchorPaneAnimation.fadeIn(changePasswordAnchorPane,Direction.up);

        CheckTimeTheme.check();
        Appearance appearanceSize = fontSizeDataSource.readData();
        Appearance appearanceFont = fontDataSource.readData();
        Appearance appearanceTheme = themeDataSource.readData();
        themeStr = getClass().getResource("/ku/cs/css/" + appearanceTheme.getData() + ".css").toExternalForm();
        fontStr = getClass().getResource("/ku/cs/css/Font" + appearanceFont.getData() + ".css").toExternalForm();
        fontSizeStr = getClass().getResource("/ku/cs/css/FontSize" + appearanceSize.getData() + ".css").toExternalForm();
        setStyle();


        System.out.println("Initialize AdminProfileChangePassword");
        DateTime.getDateTime(time);

        memberList = dataSource.readData();
        member = (Member) com.github.saacsos.FXRouter.getData();
        member = memberList.readAdminDataUser(member.getUsername());
        usernameLabel.setText(member.getUsername());
        File file = new File("data/profiles/" + member.getPicture());
        image.setImage(new Image(file.toURI().toString()));

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
        time.getStyleClass().add("contentTextInAnchorPane");
        usernameLabel.getStyleClass().add("contentTextInAnchorPane");
        changePasswordAnchorPane.getStyleClass().add("anchorPanePopup");
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
        if (!usernameFXML.equals(member.getUsername())){
            setStatus(usernameStatus,"ชื่อผู้ใช้ไม่ถูกต้อง");
        } else {
            usernameStatus.setOpacity(0);
        }

        //password
        if (!oldPasswordFXML.equals(member.getPassword())){
            setStatus(oldPasswordStatus,"รหัสผ่านไม่ถูกต้อง");
        } else {
            oldPasswordStatus.setOpacity(0);
        }

        //new password
        if (!newPasswordFXML.equals(confirmNewPasswordFXML) && !newPasswordFXML.isEmpty() && !confirmNewPasswordFXML.isEmpty()){
            setStatus(newPasswordStatus,"รหัสผ่านไม่ตรงกัน");
        }else {
            newPasswordStatus.setOpacity(0);
        }

        //All Check pass
        if (memberList.findMemberData(member) &&
                member.getPassword().equals(oldPasswordFXML) &&
                newPasswordFXML.equals(confirmNewPasswordFXML) &&
                !newPasswordFXML.isEmpty() &&
                !confirmNewPasswordFXML.isEmpty()){

            memberList.addMember(memberList.findIndexMember(member),new Member(member.getUsername(), member.getName(),newPasswordFXML, member.getPicture(), member.getTimeLogin()));
            memberList.removeMember(memberList.findIndexMember(member));
            dataSource.writeData(memberList);
            member = memberList.findMember(member.getUsername());
            clearFiled();
            setStatus(status,"แก้ไขรหัสผ่านเรียบร้อยแล้ว");
        }else {
            status.setOpacity(0);
        }
    }

    @FXML
    public void handleBackToProfileButton(ActionEvent actionEvent){
        try{
            com.github.saacsos.FXRouter.goTo("admin_profile");
        } catch (IOException e){
            System.err.println("ไปที่หน้า admin_profile ไม่ได้");
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

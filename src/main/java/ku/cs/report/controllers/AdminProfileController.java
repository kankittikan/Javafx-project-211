package ku.cs.report.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import ku.cs.report.Animations.AnchorPaneAnimation;
import ku.cs.report.Animations.Direction;
import ku.cs.report.models.*;
import ku.cs.report.services.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class AdminProfileController implements AppearanceConfig{

    private Member member;

    @FXML private Label nameLabel;
    @FXML private Label usernameLabel;

    @FXML private ImageView image;
    @FXML private Label timeLabel, adminProfile;
    @FXML private AnchorPane AP, topAP, downAP, APCenter;
    @FXML private Button backToHome;

    private MemberList MemberList;
    private DataSource<MemberList> dataSource = new AdminMemberListFileDataSource("data", "AdminDataUser.csv");

    private String themeStr, fontSizeStr, fontStr;
    private final DataSource<Appearance> fontSizeDataSource = new AppearanceDataSource("data/appearance", "FontSize.csv");
    private final DataSource<Appearance> fontDataSource = new AppearanceDataSource("data/appearance", "Font.csv");
    private final DataSource<Appearance> themeDataSource = new AppearanceDataSource("data/appearance", "theme.csv");

    @FXML
    public void initialize(){
        AnchorPaneAnimation anchorPaneAnimation = new AnchorPaneAnimation();
        anchorPaneAnimation.fadeIn(topAP, Direction.right);
        anchorPaneAnimation.fadeIn(APCenter,Direction.left);
        anchorPaneAnimation.fadeIn(downAP,Direction.up);

        CheckTimeTheme.check();
        Appearance appearanceSize = fontSizeDataSource.readData();
        Appearance appearanceFont = fontDataSource.readData();
        Appearance appearanceTheme = themeDataSource.readData();
        themeStr = getClass().getResource("/ku/cs/css/" + appearanceTheme.getData() + ".css").toExternalForm();
        fontStr = getClass().getResource("/ku/cs/css/Font" + appearanceFont.getData() + ".css").toExternalForm();
        fontSizeStr = getClass().getResource("/ku/cs/css/FontSize" + appearanceSize.getData() + ".css").toExternalForm();
        setStyle();

        DateTime.getDateTime(timeLabel);
        member = (Member) com.github.saacsos.FXRouter.getData();
        MemberList = dataSource.readData();

        File file = new File("data/profiles/" + member.getPicture());
        image.setImage(new Image(file.toURI().toString()));

        nameLabel.setText(member.getName());
        usernameLabel.setText(member.getUsername());

        nameLabel.setAlignment(Pos.CENTER);
        member.setTimeLogin(DateTime.getDateTime());
        dataSource.writeData(MemberList);


    }

    public void setStyle() {
        AP.getStylesheets().add(fontStr);
        AP.getStylesheets().add(fontSizeStr);
        AP.getStylesheets().add(themeStr);

        AP.getStyleClass().add("background");
        topAP.getStyleClass().add("anchorPane");
        downAP.getStyleClass().add("anchorPane");
        backToHome.getStyleClass().add("buttonInAnchorPane");
        APCenter.getStyleClass().add("anchorPanePopup");
        adminProfile.getStyleClass().add("title");
        adminProfile.getStyleClass().add("contentTextInAnchorPane");
        timeLabel.getStyleClass().add("contentTextInAnchorPane");
    }

    @FXML
    public void handleBackAdminHomeButton(ActionEvent actionEvent){
        try {
            com.github.saacsos.FXRouter.goTo("admin_home");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า staff_home ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
            e.printStackTrace();
        }
    }

    @FXML
    public void handleLogOutButton(ActionEvent actionEvent){
        try{
            com.github.saacsos.FXRouter.goTo("login");
        } catch (IOException e){
            System.err.println("ไปที่หน้า login ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
            e.printStackTrace();
        }
    }

    @FXML
    public void handleAdminChangePasswordButton(ActionEvent actionEvent){
        try{
            com.github.saacsos.FXRouter.goTo("admin_profile_change_password",member);
        } catch (IOException e){
            System.err.println("ไปที่หน้า admin_profile_change_password ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
            e.printStackTrace();
        }
    }

    @FXML
    public void handleChangeImageButton(ActionEvent actionEvent){
        String old = member.getPicture();

        PictureFileChooser fileChooser = new PictureFileChooser();
        ImageWrite imageWrite = new ImageWrite(fileChooser.getDirName(), fileChooser.getFileName(), "profiles");

        member.setPicture(fileChooser.getFileName());
        dataSource.writeData(MemberList);

        int index = MemberList.findIndexMember(member);
        boolean flag = MemberList.findMemberData(member);
        if (flag){
            MemberList.addMember(index, new Member(member.getUsername(),member.getName(),member.getPassword(), fileChooser.getFileName()));
            MemberList.removeMember(index + 1);
            dataSource.writeData(MemberList);
            MemberList = dataSource.readData();
        }

        File file = new File("data/profiles/" + member.getPicture());
        image.setImage(new Image(file.toURI().toString()));

        Path path= Paths.get("data/profiles/" + old);
        if(!old.equals("UnknownProfile.jpg")) {
            try {
                Files.delete(path);
            } catch (NoSuchFileException x) {
                System.err.format("%s: no such" + " file or directory%n", path);
            } catch (DirectoryNotEmptyException x) {
                System.err.format("%s not empty%n", path);
            } catch (IOException x) {
                System.err.println(x);
            }
        }
    }


}

package ku.cs.report.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import ku.cs.report.Animations.Direction;
import ku.cs.report.Animations.LabelAnimation;
import ku.cs.report.models.*;
import com.github.saacsos.FXRouter;
import ku.cs.report.services.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class StaffProfileController implements AppearanceConfig {
    @FXML private Label nameLabel;
    @FXML private Label usernameLabel;
    @FXML private Label agencyLabel;
    @FXML private ImageView imageStaff;
    @FXML private Label timeLabel;
    @FXML private AnchorPane profileAnchorPane, topLayout, bottomLayoutAnchorPane, backgroundStaffProfileAnchorPane;
    @FXML private Button backStaffHomeButton;
    @FXML private Label topicLabel;
    @FXML private BorderPane borderPane;
    private StaffMember staffMember;

    private StaffMemberList staffMemberList;
    private DataSource<StaffMemberList> dataSource;
    private LabelAnimation labelAnimation;

    private String themeStr, fontSizeStr, fontStr;
    private DataSource<Appearance> fontSizeDataSource = new AppearanceDataSource("data/appearance", "FontSize.csv");
    private DataSource<Appearance> fontDataSource = new AppearanceDataSource("data/appearance", "Font.csv");
    private DataSource<Appearance> themeDataSource = new AppearanceDataSource("data/appearance", "theme.csv");
    private Appearance appearanceSize;
    private Appearance appearanceFont;
    private Appearance appearanceTheme;


    @FXML
    public void initialize(){
        CheckTimeTheme.check();
        appearanceSize = fontSizeDataSource.readData();
        appearanceFont = fontDataSource.readData();
        appearanceTheme = themeDataSource.readData();
        themeStr = getClass().getResource("/ku/cs/css/" + appearanceTheme.getData() + ".css").toExternalForm();
        fontStr = getClass().getResource("/ku/cs/css/Font" + appearanceFont.getData() + ".css").toExternalForm();
        fontSizeStr = getClass().getResource("/ku/cs/css/FontSize" + appearanceSize.getData() + ".css").toExternalForm();
        setStyle();

        labelAnimation = new LabelAnimation();
        dataSource = new StaffMemberListFileDataSource("data", "StaffDataUser.csv");
        staffMember = (StaffMember) FXRouter.getData();
        readStaffDataUser();
        showStaffDataUser();
        DateTime.getDateTime(timeLabel);
    }

    @Override
    public void setStyle() {
        borderPane.getStylesheets().add(fontStr);
        borderPane.getStylesheets().add(fontSizeStr);
        borderPane.getStylesheets().add(themeStr);

        borderPane.getStyleClass().add("background");
        backgroundStaffProfileAnchorPane.getStyleClass().add("background");
        topLayout.getStyleClass().add("anchorPane");
        bottomLayoutAnchorPane.getStyleClass().add("anchorPane");
        backStaffHomeButton.getStyleClass().add("buttonInAnchorPane");
        profileAnchorPane.getStyleClass().add("anchorPanePopup");
        timeLabel.getStyleClass().add("contentTextInAnchorPane");
        topicLabel.getStyleClass().add("contentTextInAnchorPane");
        topicLabel.getStyleClass().add("title");
    }

    @FXML
    public void handleBackStaffHomeButton(ActionEvent actionEvent){
        try {
            FXRouter.goTo("staff_home", staffMember);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า staff_home ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
            e.printStackTrace();
        }
    }

    @FXML
    public void handleLogOutButton(ActionEvent actionEvent){
        try{
            FXRouter.goTo("login");
        } catch (IOException e){
            System.err.println("ไปที่หน้า login ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
            e.printStackTrace();
        }
    }

    @FXML
    public void handleStaffProfileChangePasswordButton(ActionEvent actionEvent){
        try{
            FXRouter.goTo("staff_profile_changePassword", staffMember);
        } catch (IOException e){
            System.err.println("ไปที่หน้า change_password_staff ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
            e.printStackTrace();
        }
    }

    @FXML
    public void handleChangeImageButton(ActionEvent actionEvent){
        String old = staffMember.getPicture();
        PictureFileChooser fileChooser = new PictureFileChooser();
        ImageWrite imageWrite = new ImageWrite(fileChooser.getDirName(), fileChooser.getFileName(), "profiles");
        staffMember.setPicture(fileChooser.getFileName());
        showStaffDataUser();
        dataSource.writeData(staffMemberList);

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

    private void readStaffDataUser() {
        staffMemberList = dataSource.readData();
        staffMember = staffMemberList.findStaffMember(staffMember.getUsername());
    }

    private void showStaffDataUser(){
        nameLabel.setText(staffMember.getName());
        usernameLabel.setText(staffMember.getUsername());
        agencyLabel.setText(staffMember.getAgency());
        File file = new File("data/profiles/" + staffMember.getPicture());
        imageStaff.setImage(new Image(file.toURI().toString()));
        imageStaff.setPreserveRatio(false);

        labelAnimation.fadeIn(nameLabel, Direction.left);
        labelAnimation.fadeIn(usernameLabel, Direction.left);
        labelAnimation.fadeIn(agencyLabel, Direction.left);
    }
}

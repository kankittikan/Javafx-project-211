package ku.cs.report.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import ku.cs.report.Animations.AnchorPaneAnimation;
import ku.cs.report.Animations.Direction;
import javafx.scene.control.Button;
import ku.cs.report.models.*;
import ku.cs.report.services.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class StudentProfileController implements AppearanceConfig {
    @FXML private Label time;
    @FXML private Label usernameLabel;
    @FXML private Label usernameHeadLabel;
    @FXML private Label nameLabel;
    @FXML private ImageView imageStudent;
    @FXML private ImageView miniImageStudent;
    @FXML private AnchorPane anchorPane1;
    @FXML private AnchorPane anchorPane2;
    @FXML private AnchorPane anchorPane3;

    @FXML private AnchorPane navAnchorPane;
    @FXML private Label profileLabel;
    @FXML private Label username;
    @FXML private Label head;
    @FXML private Button changePasswordButton;
    @FXML private Button changePictureButton;
    @FXML private Button backButton;
    @FXML private Button logoutButton;
    @FXML private AnchorPane mainAnchorPane;


    private StudentMember student;
    private DataSource<StudentMemberList> dataSource = new StudentMemberListFileDataSource("data","StudentDataUser.csv");

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

        AnchorPaneAnimation anchorPaneAnimation = new AnchorPaneAnimation();
        anchorPaneAnimation.fadeIn(anchorPane1,Direction.right);
        anchorPaneAnimation.fadeIn(anchorPane2,Direction.left);
        anchorPaneAnimation.fadeIn(anchorPane3,Direction.up);

        student = (StudentMember) com.github.saacsos.FXRouter.getData();
        student = StudentMemberList.readStudentDataUser(student.getUsername());
        DateTime.getDateTime(time);

        usernameLabel.setText(student.getUsername());
        usernameHeadLabel.setText(student.getUsername());
        nameLabel.setText(student.getName());

        File file = new File("data/profiles/" + student.getPicture());
        imageStudent.setImage(new Image(file.toURI().toString()));
        miniImageStudent.setImage(new Image(file.toURI().toString()));

        System.out.println("Initialize StudentProfileController");
    }

    public void setStyle() {
        mainAnchorPane.getStylesheets().add(fontStr);
        mainAnchorPane.getStylesheets().add(fontSizeStr);
        mainAnchorPane.getStylesheets().add(themeStr);

        mainAnchorPane.getStyleClass().add("background");
        navAnchorPane.getStyleClass().add("anchorPane");
        anchorPane1.getStyleClass().add("anchorPanePopup");
        anchorPane2.getStyleClass().add("anchorPanePopup");
        anchorPane3.getStyleClass().add("anchorPanePopup");
        time.getStyleClass().add("contentTextInAnchorPane");
        usernameHeadLabel.getStyleClass().add("contentTextInAnchorPane");
        profileLabel.getStyleClass().add("title");
    }

    @FXML
    public void handleChangePasswordButton(ActionEvent actionEvent) {
        try{
            com.github.saacsos.FXRouter.goTo("student_profile_changePassword",student);
        } catch (IOException e){
            System.err.println("ไปที่หน้า student_profile_changePassword ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
            e.printStackTrace();
        }
    }

    @FXML
    public void handleChangeProfilePictureButton(ActionEvent actionEvent){
        String old = student.getPicture();

        PictureFileChooser fileChooser = new PictureFileChooser();
        ImageWrite imageWrite = new ImageWrite(fileChooser.getDirName(), fileChooser.getFileName(), "profiles");
        StudentMemberList studentMemberList = dataSource.readData();

        int index = studentMemberList.findIndexMember(student);
        boolean flag = studentMemberList.findMemberData(student);
        if (flag){
            studentMemberList.addMember(index, new StudentMember(student.getUsername(),student.getName(),student.getPassword(), fileChooser.getFileName()));
            studentMemberList.removeMember(index + 1);
            dataSource.writeData(studentMemberList);
            studentMemberList = dataSource.readData();
        }

        student.setPicture(fileChooser.getFileName());
        dataSource.writeData(studentMemberList);

        File file = new File("data/profiles/" + student.getPicture());
        imageStudent.setImage(new Image(file.toURI().toString()));
        miniImageStudent.setImage(new Image(file.toURI().toString()));

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
    public void handleBackToHomeButton(ActionEvent actionEvent){
        try{
            com.github.saacsos.FXRouter.goTo("student_home");
        } catch (IOException e){
            System.err.println("ไปที่หน้า student home ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
            e.printStackTrace();
        }
    }

}

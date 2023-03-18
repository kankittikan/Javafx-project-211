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
import ku.cs.report.Animations.LabelAnimation;
import ku.cs.report.models.*;
import com.github.saacsos.FXRouter;
import ku.cs.report.services.AdminMemberListFileDataSource;
import ku.cs.report.services.AppearanceDataSource;
import ku.cs.report.services.DataSource;

import java.io.File;
import java.io.IOException;

public class AdminHomeController implements AppearanceConfig {
    @FXML
    private AnchorPane anchorPane, topLayout, anchorPanePopup;
    @FXML
    private Label time, title, titleMenu, titleAdmin;
    @FXML
    private Label nameLabel;
    @FXML
    private ImageView image;
    @FXML
    private Button logoutButton;
    private Member Member;
    private MemberList memberList;
    private DataSource<MemberList> dataSource = new AdminMemberListFileDataSource("data", "AdminDataUser.csv");

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

        System.out.println("Initialize ResetPasswordController");
        anchorPaneAnimation = new AnchorPaneAnimation();
        anchorPaneAnimation.fadeIn(anchorPanePopup, Direction.up);
        labelAnimation = new LabelAnimation();
        labelAnimation.fadeIn(nameLabel, Direction.right);
        labelAnimation.fadeIn(title, Direction.down);
        DateTime.getDateTime(time);
        Member = (Member) FXRouter.getData();
        memberList = dataSource.readData();
        Member = memberList.findMember(Member.getUsername());

        File file = new File("data/profiles/" + Member.getPicture());
        image.setImage(new Image(file.toURI().toString()));

        nameLabel.setText(Member.getName());
        nameLabel.setAlignment(Pos.CENTER);
        Member.setTimeLogin(DateTime.getDateTime());
        dataSource.writeData(memberList);
    }

    public void setStyle() {
        anchorPane.getStylesheets().add(fontStr);
        anchorPane.getStylesheets().add(fontSizeStr);
        anchorPane.getStylesheets().add(themeStr);

        anchorPane.getStyleClass().add("background");
        topLayout.getStyleClass().add("anchorPane");
        anchorPanePopup.getStyleClass().add("anchorPanePopup");
        title.getStyleClass().add("title");
        title.getStyleClass().add("contentTextInAnchorPane");
        titleAdmin.getStyleClass().add("title");
        titleAdmin.getStyleClass().add("contentTextInAnchorPane");
        time.getStyleClass().add("contentTextInAnchorPane");
        titleMenu.getStyleClass().add("title");
        titleAdmin.getStyleClass().add("titleAdmin");
        nameLabel.getStyleClass().add("contentTextInAnchorPane");
        logoutButton.getStyleClass().add("buttonInAnchorPane");
    }

    @FXML
    public void handleBackButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("login");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า login ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
            e.printStackTrace();
        }
    }

    @FXML
    public void handleAdminProfileButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("admin_profile", Member);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า admin_profile ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
            e.printStackTrace();
        }
    }

    @FXML
    public void handleStaffRegisterButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("staff_register");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า staff register ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
            e.printStackTrace();
        }
    }

    @FXML
    public void handleManageAgencyButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("staff_manage");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า staff manage ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
            e.printStackTrace();
        }
    }

    @FXML
    public void handleManageCategoryButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("manage_category");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า manage_category ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
            e.printStackTrace();
        }
    }

    @FXML
    public void handleUserListButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("user_list");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า user_list ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
            e.printStackTrace();
        }
    }

    @FXML
    public void handleInappropriateButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("inappropriate");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า inappropriate ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
            e.printStackTrace();
        }
    }
}

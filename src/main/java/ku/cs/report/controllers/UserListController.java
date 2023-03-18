package ku.cs.report.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
import ku.cs.report.services.StaffMemberListFileDataSource;

import java.io.File;
import java.io.IOException;


public class UserListController implements AppearanceConfig {
    @FXML
    private String picture;
    @FXML
    private Label time;
    private StudentMember studentMember;
    private StudentMemberList studentMemberList;
    private DataSource<StudentMemberList> memberListDataSource = new StudentMemberListFileDataSource("data", "StudentDataUser.csv");
    private DataSource<StaffMemberList> staffMemberListDataSource = new StaffMemberListFileDataSource("data", "StaffDataUser.csv");
    private StaffMemberList staffMemberList;
    private StaffMember staffMember;
    @FXML
    private ListView<String> listView1;
    @FXML
    private ListView<String> listView2;
    @FXML
    private ImageView imageUser;
    @FXML
    private Label nameLabel;
    @FXML
    private Label UserListNameTop;

    @FXML
    private Label agencyLabel;
    @FXML
    private Label timeLoginLabel;
    @FXML
    private AnchorPane AP, APTop, APDown, APCental;
    @FXML
    private Button BackToHome;



    private String themeStr, fontSizeStr, fontStr;
    private final DataSource<Appearance> fontSizeDataSource = new AppearanceDataSource("data/appearance", "FontSize.csv");
    private final DataSource<Appearance> fontDataSource = new AppearanceDataSource("data/appearance", "Font.csv");
    private final DataSource<Appearance> themeDataSource = new AppearanceDataSource("data/appearance", "theme.csv");


    @FXML
    public void initialize() {
        AnchorPaneAnimation anchorPaneAnimation = new AnchorPaneAnimation();
        anchorPaneAnimation.fadeIn(APTop,Direction.right);
        anchorPaneAnimation.fadeIn(APDown,Direction.left);
        anchorPaneAnimation.fadeIn(APCental,Direction.up);

        CheckTimeTheme.check();
        Appearance appearanceSize = fontSizeDataSource.readData();
        Appearance appearanceFont = fontDataSource.readData();
        Appearance appearanceTheme = themeDataSource.readData();
        themeStr = getClass().getResource("/ku/cs/css/" + appearanceTheme.getData() + ".css").toExternalForm();
        fontStr = getClass().getResource("/ku/cs/css/Font" + appearanceFont.getData() + ".css").toExternalForm();
        fontSizeStr = getClass().getResource("/ku/cs/css/FontSize" + appearanceSize.getData() + ".css").toExternalForm();
        setStyle();

        DateTime.getDateTime(time);

        studentMemberList = memberListDataSource.readData();
        staffMemberList = staffMemberListDataSource.readData();

        showMemberListView();
        showStaffMemberLitView();
        handleSelectedUserListView1();
        handleSelectedUserListView2();
        memberDescendingTime();
        staffDescendingTime();
        clearSelectedUser();

    }

    public void setStyle() {
        AP.getStylesheets().add(fontStr);
        AP.getStylesheets().add(fontSizeStr);
        AP.getStylesheets().add(themeStr);

        AP.getStyleClass().add("background");
        APDown.getStyleClass().add("anchorPane");
        APTop.getStyleClass().add("anchorPane");
        BackToHome.getStyleClass().add("buttonInAnchorPane");
        APCental.getStyleClass().add("anchorPanePopup");
        UserListNameTop.getStyleClass().add("title");
        UserListNameTop.getStyleClass().add("contentTextInAnchorPane");
        time.getStyleClass().add("contentTextInAnchorPane");
    }

    @FXML
    public void handleBackButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("admin_home");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า admin_home ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
            e.printStackTrace();
        }
    }

    private void showMemberListView() {
        listView1.getItems().addAll(studentMemberList.timeAndUserNameToString());
        listView1.refresh();
    }


    private void showStaffMemberLitView() {
        listView2.getItems().addAll(staffMemberList.timeAndUserNameAndAgencyToString());
        listView2.refresh();
    }

    private void handleSelectedUserListView1() {
        listView1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String member, String t1) {
                System.out.println(t1 + " is selected.");
                showSelectedUser1(t1);
            }
        });

    }

    private void handleSelectedUserListView2() {
        listView2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String staffMember, String t1) {
                System.out.println(t1 + " is selected.");
                showSelectedUser2(t1);
            }
        });

    }


    private void showSelectedUser1(String memberString) {
        try {
            String[] strings = memberString.split("     ");
            System.out.println(strings[1]);
            studentMember = studentMemberList.findMember(strings[1]);
            nameLabel.setText(studentMember.getUsername());
            agencyLabel.setText("----");
            timeLoginLabel.setText(studentMember.getTimeLogin());

            File file = new File("data/profiles/" + studentMember.getPicture());
            imageUser.setImage(new Image(file.toURI().toString()));


        } catch (NullPointerException e) {
            System.err.println("รายการเรื่องร้องเรียนถูกคัลกรอง");
        }
    }

    private void showSelectedUser2(String memberString) {
        try {
            String[] strings = memberString.split("          ");
            System.out.println(strings[1]);
            staffMember = staffMemberList.findStaffMember(strings[1]);
            nameLabel.setText(staffMember.getUsername());
            agencyLabel.setText(staffMember.getAgency());
            timeLoginLabel.setText(staffMember.getTimeLogin());

            File file = new File("data/profiles/" + staffMember.getPicture());
            imageUser.setImage(new Image(file.toURI().toString()));

        } catch (NullPointerException e) {
            System.err.println("รายการเรื่องร้องเรียนถูกคัลกรอง");
        }
    }

    public void memberDescendingTime() {
        studentMemberList.descendingTimeMember();
        listView1.getItems().clear();
        showMemberListView();

    }

    public void staffDescendingTime() {
        staffMemberList.descendingTimeStaff();
        listView2.getItems().clear();
        showStaffMemberLitView();

    }

    private void clearSelectedUser() {
        timeLoginLabel.setText("----");
        agencyLabel.setText("----");
        nameLabel.setText("----");
    }

}

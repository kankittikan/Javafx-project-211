package ku.cs.report.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import ku.cs.report.Animations.AnchorPaneAnimation;
import ku.cs.report.Animations.Direction;
import ku.cs.report.Animations.LabelAnimation;
import ku.cs.report.models.*;
import ku.cs.report.services.AppearanceDataSource;
import ku.cs.report.services.CategoryListFileDataSource;
import ku.cs.report.services.DataSource;
import ku.cs.report.services.StaffMemberListFileDataSource;

import java.io.IOException;

public class StaffRegisterController implements AppearanceConfig {
    @FXML
    private Label registerFailText;
    @FXML
    private TextField userNameTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Label time;
    @FXML
    private TextField rePasswordTextField;
    @FXML
    private ListView<String> agencyListView;

    @FXML
    private AnchorPane anchorPane, topAnchorPane, downAnchorPane, anchorPanePopup;
    @FXML
    private Label title;
    @FXML
    private Button backButton;

    private String themeStr, fontSizeStr, fontStr;
    private final DataSource<Appearance> fontSizeDataSource = new AppearanceDataSource("data/appearance", "FontSize.csv");
    private final DataSource<Appearance> fontDataSource = new AppearanceDataSource("data/appearance", "Font.csv");
    private final DataSource<Appearance> themeDataSource = new AppearanceDataSource("data/appearance", "theme.csv");


    private Category agency;
    private StaffMemberList staffMemberList;
    private StaffMember staffMember;
    private String agencySelected;
    private DataSource<Category> categoryDataSource = new CategoryListFileDataSource("data", "CategoryReport.csv");
    private DataSource<StaffMemberList> dataSource = new StaffMemberListFileDataSource("data", "StaffDataUser.csv");
    private AnchorPaneAnimation anchorPaneAnimation = new AnchorPaneAnimation();
    private LabelAnimation labelAnimation = new LabelAnimation();

    @FXML
    void initialize() {
        CheckTimeTheme.check();
        Appearance appearanceSize = fontSizeDataSource.readData();
        Appearance appearanceFont = fontDataSource.readData();
        Appearance appearanceTheme = themeDataSource.readData();
        themeStr = getClass().getResource("/ku/cs/css/" + appearanceTheme.getData() + ".css").toExternalForm();
        fontStr = getClass().getResource("/ku/cs/css/Font" + appearanceFont.getData() + ".css").toExternalForm();
        fontSizeStr = getClass().getResource("/ku/cs/css/FontSize" + appearanceSize.getData() + ".css").toExternalForm();
        setStyle();

        anchorPaneAnimation.fadeIn(anchorPanePopup, Direction.up);
        labelAnimation.fadeIn(title, Direction.down);
        agency = categoryDataSource.readData();
        staffMemberList = dataSource.readData();
        DateTime.getDateTime(time);
        showListView();
        handleSelectedListView();
        registerFailText.setAlignment(Pos.CENTER);
    }

    public void setStyle() {
        anchorPane.getStylesheets().add(fontStr);
        anchorPane.getStylesheets().add(fontSizeStr);
        anchorPane.getStylesheets().add(themeStr);

        anchorPane.getStyleClass().add("background");
        topAnchorPane.getStyleClass().add("anchorPane");
        downAnchorPane.getStyleClass().add("anchorPane");
        time.getStyleClass().add("contentTextInAnchorPane");
        title.getStyleClass().add("contentTextInAnchorPane");
        title.getStyleClass().add("title");
        anchorPanePopup.getStyleClass().add("anchorPanePopup");
        backButton.getStyleClass().add("buttonInAnchorPane");
    }

    @FXML
    public void handleRegisterButton(ActionEvent actionEvent) {
        if (userNameTextField.getText().equals("")
                || nameTextField.getText().equals("")
                || passwordTextField.getText().equals("")
                || rePasswordTextField.getText().equals("")
                || agencySelected == null
        ) {
            registerFailText.setText("ใส่ข้อมูลให้ครบถ้วน");
            labelAnimation.fadeIn(registerFailText, Direction.left);
            registerFailText.setStyle("-fx-text-fill: #ff7070");
            cleartextField();
            return;
        }
        if (!passwordTextField.getText().equals(rePasswordTextField.getText())) {
            registerFailText.setText("ตรวจสอบรหัสผ่าน");
            labelAnimation.fadeIn(registerFailText, Direction.left);
            registerFailText.setStyle("-fx-text-fill: #ff7070");
            passwordTextField.setText("");
            rePasswordTextField.setText("");
            return;
        }
        if (staffMemberList.findStaffMember(userNameTextField.getText()) != null) {
            registerFailText.setText("มีชื่อผู้ใช้นี้อยู่แล้ว");
            labelAnimation.fadeIn(registerFailText, Direction.left);
            registerFailText.setStyle("-fx-text-fill: #ff7070");
            cleartextField();
            return;
        }
        staffMember = new StaffMember(userNameTextField.getText(), nameTextField.getText(), passwordTextField.getText(), agencySelected);
        staffMemberList.addMember(staffMember);
        dataSource.writeData(staffMemberList);
        registerFailText.setText("ลงทะเบียนสำเส็จ");
        labelAnimation.fadeIn(registerFailText, Direction.left);
        registerFailText.setStyle("-fx-text-fill: #63a82d");
        userNameTextField.setText("");
        nameTextField.setText("");
        passwordTextField.setText("");
        rePasswordTextField.setText("");
        agencyListView.getSelectionModel().clearSelection();
        agencySelected = null;
    }

    private void cleartextField() {
        userNameTextField.setText("");
        nameTextField.setText("");
        passwordTextField.setText("");
        rePasswordTextField.setText("");
    }

    private void showListView() {
        agencyListView.getItems().clear();
        agencyListView.getItems().addAll(agency.getAllList());
        agencyListView.refresh();
    }

    private void handleSelectedListView() {

        agencyListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        System.out.println(newValue + " is selected");
                        agencySelected = newValue;
                    }
                });
    }

    @FXML
    public void handlebackButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("admin_home");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า admin home ไม่ได้");
            System.err.println("ตรวจสอบการกำหนด route");
        }
    }

}

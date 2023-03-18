package ku.cs.report.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import ku.cs.report.Animations.AnchorPaneAnimation;
import ku.cs.report.Animations.Direction;
import ku.cs.report.Animations.LabelAnimation;
import ku.cs.report.models.*;
import ku.cs.report.services.*;

import java.io.IOException;

public class ManageCategoryController implements AppearanceConfig{
    @FXML
    private AnchorPane anchorPane, anchorPanePopup, anchorPanePopup2, topAnchorPane, downAnchorPane;
    @FXML
    private ListView<String> categoryListView;
    @FXML
    private Label label0;
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private TextField textField0;
    @FXML
    private TextField textField1;
    @FXML
    private TextField textField2;
    @FXML
    private Label time, title, selectLabel, changeLabel;
    @FXML
    private Label failText;
    @FXML
    private Button button;
    @FXML
    private MenuButton menuButton;
    @FXML
    private MenuItem addCategoryButton;
    @FXML
    private MenuItem deleteCategoryButton;
    @FXML
    private MenuItem changeNameButton;
    @FXML
    private Button backButton;
    private DataSource<StaffMemberList> staffMemberListDataSource = new StaffMemberListFileDataSource("data", "StaffDataUser.csv");
    private DataSource<ReportList> reportListDataSource = new ReportListFileDataSource("data", "ReportData.csv");
    private DataSource<Category> categoryDataSource = new CategoryListFileDataSource("data", "CategoryReport.csv");
    private StaffMemberList staffMemberList;
    private Category category;
    private String buttonMenuSelected;
    private ReportList reportList;
    private AnchorPaneAnimation anchorPaneAnimation = new AnchorPaneAnimation();
    private LabelAnimation labelAnimation = new LabelAnimation();

    private String themeStr, fontSizeStr, fontStr;
    private final DataSource<Appearance> fontSizeDataSource = new AppearanceDataSource("data/appearance", "FontSize.csv");
    private final DataSource<Appearance> fontDataSource = new AppearanceDataSource("data/appearance", "Font.csv");
    private final DataSource<Appearance> themeDataSource = new AppearanceDataSource("data/appearance", "theme.csv");


    @FXML
    public void initialize() {
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
        staffMemberList = staffMemberListDataSource.readData();
        category = categoryDataSource.readData();
        reportList = reportListDataSource.readData();
        DateTime.getDateTime(time);
        showListView();
        handleSelectedListView();
        failText.setAlignment(Pos.CENTER);
        clearPane();
        buttonMenuSelected = null;
        categoryListView.setDisable(true);
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
        selectLabel.getStyleClass().add("title");
        changeLabel.getStyleClass().add("title");
        anchorPanePopup.getStyleClass().add("anchorPanePopup");
        anchorPanePopup2.getStyleClass().add("anchorPanePopup");
        backButton.getStyleClass().add("buttonInAnchorPane");
    }

    private void showListView() {
        categoryListView.getItems().clear();
        categoryListView.getItems().addAll(category.getAllList());
        categoryListView.refresh();
    }

    private void handleSelectedListView() {

        categoryListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        System.out.println(newValue + " is selected");
                        if (buttonMenuSelected != null && (buttonMenuSelected.equals("change") || buttonMenuSelected.equals("delete")))
                            textField0.setText(newValue);
                    }
                });
    }

    @FXML
    public void handlebackButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("admin_home");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า admin_home ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
            e.printStackTrace();
        }
    }

    @FXML
    public void handleAddCategoryButton(ActionEvent actionEvent) {
        categoryListView.setDisable(false);
        menuButton.setText(addCategoryButton.getText());
        clearPane();
        buttonMenuSelected = "add";
        label0.setDisable(false);
        label1.setDisable(false);
        textField0.setDisable(false);
        textField0.setEditable(true);
        textField1.setDisable(false);
        button.setDisable(false);
        label0.setText("ระบุชื่อใหม่");
        label1.setText("ยืนยันชื่อใหม่");
        button.setText("เพิ่มหมวดหมู่");
    }

    @FXML
    public void handleChangeNameButton(ActionEvent actionEvent) {
        categoryListView.setDisable(false);
        menuButton.setText(changeNameButton.getText());
        clearPane();
        buttonMenuSelected = "change";
        label0.setDisable(false);
        label1.setDisable(false);
        label2.setDisable(false);
        textField0.setDisable(false);
        textField0.setEditable(false);
        textField1.setDisable(false);
        textField2.setDisable(false);
        button.setDisable(false);
        label0.setText("หมวดหมู่ที่เลือก");
        label1.setText("ชื่อใหม่");
        label2.setText("ยืนยันชื่อใหม่");
        button.setText("เปลี่ยนชื่อหมวดหมู่");
    }

    @FXML
    public void handleDeleteCategoryButton(ActionEvent actionEvent) {
        categoryListView.setDisable(false);
        menuButton.setText(deleteCategoryButton.getText());
        clearPane();
        buttonMenuSelected = "delete";
        label0.setDisable(false);
        label1.setDisable(false);
        label2.setDisable(true);
        textField0.setDisable(false);
        textField0.setEditable(false);
        textField1.setDisable(false);
        button.setDisable(false);
        label0.setText("หมวดหมู่ที่เลือก");
        label1.setText("ยืนยันชื่อหมวดหมู่");
        button.setText("ลบหมวดหมู่");
    }

    @FXML
    public void handleButton(ActionEvent actionEvent) {
        if (buttonMenuSelected.equals("add")) {
            if (category.findCategory(textField0.getText())) {
                failText.setText("มีชื่อหมวดหมู่นี้แล้ว");
                labelAnimation.fadeIn(failText, Direction.right);
                failText.setStyle("-fx-text-fill: #ff7070");
                clearTextField();
                return;
            }
            if (!textField0.getText().equals(textField1.getText()) || textField0.getText().equals("")) {
                failText.setText("ตรวจสอบชื่อหมวดหมู่");
                labelAnimation.fadeIn(failText, Direction.right);
                failText.setStyle("-fx-text-fill: #ff7070");
                clearTextField();
                return;
            }
            category.newCategory(textField0.getText());
            categoryDataSource.writeData(category);
            failText.setText("เพิ่มหมวดหมู่สำเร็จ");
            labelAnimation.fadeIn(failText, Direction.right);
            failText.setStyle("-fx-text-fill: #63a82d");
        }
        if (buttonMenuSelected.equals("change")) {
            if (textField0.getText().equals("")) {
                failText.setText("โปรดเลือกหมวดหมู่");
                labelAnimation.fadeIn(failText, Direction.right);
                failText.setStyle("-fx-text-fill: #ff7070");
                return;
            }
            if (!textField1.getText().equals(textField2.getText()) || textField1.getText().equals("")) {
                failText.setText("ตรวจสอบชื่อหมวดหมู่");
                labelAnimation.fadeIn(failText, Direction.right);
                failText.setStyle("-fx-text-fill: #ff7070");
                clearTextField();
                return;
            }
            reportList.changeCategoryName(textField0.getText(), textField1.getText());
            category.changeName(textField0.getText(), textField1.getText());
            staffMemberList.changeAgencyName(textField0.getText(), textField1.getText());
            staffMemberListDataSource.writeData(staffMemberList);
            categoryDataSource.writeData(category);
            reportListDataSource.writeData(reportList);
            failText.setText("เปลี่ยนชื่อสำเร็จ");
            labelAnimation.fadeIn(failText, Direction.right);
            failText.setStyle("-fx-text-fill: #63a82d");
        }
        if (buttonMenuSelected.equals("delete")) {
            if (textField0.getText().equals("")) {
                failText.setText("โปรดเลือกหมวดหมู่");
                labelAnimation.fadeIn(failText, Direction.right);
                failText.setStyle("-fx-text-fill: #ff7070");
                return;
            }
            if (!textField1.getText().equals(textField0.getText())) {
                failText.setText("ตรวจสอบชื่อหมวดหมู่");
                labelAnimation.fadeIn(failText, Direction.right);
                failText.setStyle("-fx-text-fill: #ff7070");
                clearTextField();
                return;
            }
            category.removeCategory(textField0.getText());
            staffMemberList.changeAgencyName(textField0.getText(), "ไม่มีหน่วยงาน");
            staffMemberListDataSource.writeData(staffMemberList);
            categoryDataSource.writeData(category);
            failText.setText("ลบสำเร็จ");
            labelAnimation.fadeIn(failText, Direction.right);
            failText.setStyle("-fx-text-fill: #63a82d");
        }
        showListView();
        clearPane();
    }

    private void clearPane() {
        label0.setText("");
        label1.setText("");
        label2.setText("");
        textField0.setDisable(true);
        textField1.setDisable(true);
        textField2.setDisable(true);
        clearTextField();
        button.setText("");
        button.setDisable(true);
        categoryListView.getSelectionModel().clearSelection();
        buttonMenuSelected = null;
    }

    private void clearTextField() {
        textField0.clear();
        textField1.clear();
        textField2.clear();
    }
}
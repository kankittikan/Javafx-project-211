package ku.cs.report.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import ku.cs.report.Animations.AnchorPaneAnimation;
import ku.cs.report.Animations.Direction;
import ku.cs.report.Animations.LabelAnimation;
import ku.cs.report.models.*;
import ku.cs.report.services.*;

import java.io.IOException;

public class StaffManageController implements AppearanceConfig{
    @FXML
    private Label usernameLabel;
    @FXML
    private Label agencyLabel;
    @FXML
    private MenuButton menuButton;
    @FXML
    private Label failLabel;
    @FXML
    private Label time, title, selectLabel, changeLabel;
    @FXML
    private TableView<StaffMember> tableView;
    @FXML
    private AnchorPane anchorPane, topAnchorPane, downAnchorPane, anchorPanePopup1, anchorPanePopup2;
    @FXML private Button backButton;

    private DynamicCategoryMenuButton setDynamicCategoryMenuButton;

    private StaffMemberList staffMemberList;
    private StaffMember staffMember;
    private DataSource<StaffMemberList> staffMemberListDataSource = new StaffMemberListFileDataSource("data", "StaffDataUser.csv");

    private AnchorPaneAnimation anchorPaneAnimation = new AnchorPaneAnimation();
    private LabelAnimation labelAnimation = new LabelAnimation();
    private String selectedStaff = null;

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

        anchorPaneAnimation.fadeIn(anchorPanePopup1, Direction.up);
        labelAnimation.fadeIn(title, Direction.down);

        DateTime.getDateTime(time);
        anchorPanePopup2.setOpacity(0);
        staffMemberList = staffMemberListDataSource.readData();
        showTableViews(staffMemberList);
        failLabel.setAlignment(Pos.CENTER);
        failLabel.setStyle("-fx-text-fill: #ff7070");
        failLabel.setText("");
        handleSelectedTableView(tableView);
        setDynamicCategoryMenuButton = new DynamicCategoryMenuButton(menuButton);
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
        anchorPanePopup1.getStyleClass().add("anchorPanePopup");
        anchorPanePopup2.getStyleClass().add("anchorPanePopup");
        backButton.getStyleClass().add("buttonInAnchorPane");
    }

    private void showTableViews(StaffMemberList staffMemberList) {
        tableView.getColumns().clear();
        TableColumn<StaffMember, String> userNameColumn = new TableColumn<>("ชื่อผู้ใช้");
        TableColumn<StaffMember, String> agencyColumn = new TableColumn<>("หน่วยงาน");

        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        agencyColumn.setCellValueFactory(new PropertyValueFactory<>("agency"));

        ObservableList<StaffMember> list = FXCollections.observableList(staffMemberList.getAllList());

        tableView.setItems(list);
        tableView.getColumns().addAll(userNameColumn, agencyColumn);
    }

    private void handleSelectedTableView(TableView<StaffMember> tableView) {
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    try {
                        System.out.println(newValue.getUsername() + "is selected");
                        selectedStaff = newValue.getUsername();
                        failLabel.setText("");
                    } catch (NullPointerException e) {
                        System.err.println("newValue: is " + newValue + ".");
                    }
                });
    }

    @FXML
    public void handleChangeAgencyButton(ActionEvent actionEvent) {
        if (selectedStaff == null) {
            failLabel.setText("โปรดเลือกเจ้าหน้าที่");
            labelAnimation.fadeIn(failLabel, Direction.right);
            return;
        }
        tableView.setDisable(true);
        anchorPanePopup2.setOpacity(1);
        anchorPaneAnimation.move(anchorPanePopup1, 200, Direction.left);
        anchorPaneAnimation.move(anchorPanePopup2, 400, Direction.left);
        staffMember = staffMemberList.findStaffMember(selectedStaff);
        usernameLabel.setText(staffMember.getUsername());
        agencyLabel.setText(staffMember.getAgency());
    }

    @FXML
    public void handleChangeAgencyButton2(ActionEvent actionEvent) {
        if (menuButton.getText().equals("หน่วยงาน")) return;
        staffMember.setAgency(menuButton.getText());
        staffMemberListDataSource.writeData(staffMemberList);
        showTableViews(staffMemberList);
        tableView.setDisable(false);
        anchorPaneAnimation.move(anchorPanePopup1, 200, Direction.right);
        anchorPaneAnimation.move(anchorPanePopup2, 400, Direction.right);
        anchorPanePopup2.setOpacity(0);
        clearField();
    }

    @FXML
    public void handleCancelButton(ActionEvent actionEvent) {
        tableView.setDisable(false);
        anchorPaneAnimation.move(anchorPanePopup1, 200, Direction.right);
        anchorPaneAnimation.move(anchorPanePopup2, 400, Direction.right);
        anchorPanePopup2.setOpacity(0);
        clearField();
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

    private void clearField() {
        usernameLabel.setText("");
        agencyLabel.setText("");
        menuButton.setText("หน่วยงาน");
    }
}

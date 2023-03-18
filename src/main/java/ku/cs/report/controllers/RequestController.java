package ku.cs.report.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import ku.cs.report.Animations.Direction;
import ku.cs.report.Animations.LabelAnimation;
import ku.cs.report.models.*;
import ku.cs.report.services.*;
import com.github.saacsos.FXRouter;

import java.io.IOException;

public class RequestController implements AppearanceConfig{
    @FXML
    private Button acceptRequestButton;

    @FXML private Button backButton;

    @FXML private TextField nameTextField;

    @FXML private Label nameTopicLabel;

    @FXML private Label noticeLabel;

    @FXML private PasswordField passwordField;

    @FXML private Label passwordTopicLabel;

    @FXML private TextArea reasonTextArea;

    @FXML private Label reasonTopicLabel;

    @FXML private Label timeLabel;

    @FXML private AnchorPane topLayoutAnchorPane, backgroundRequestAnchorPane;

    @FXML private Label topicLabel;

    @FXML private TextField usernameTextField;

    @FXML private Label usernameTopicLabel;
    @FXML private BorderPane borderPane;

    private String themeStr, fontSizeStr, fontStr;
    private final DataSource<Appearance> fontSizeDataSource = new AppearanceDataSource("data/appearance", "FontSize.csv");
    private final DataSource<Appearance> fontDataSource = new AppearanceDataSource("data/appearance", "Font.csv");
    private final DataSource<Appearance> themeDataSource = new AppearanceDataSource("data/appearance", "theme.csv");


    private DataSource<StudentMemberList> memberListDataSource;
    private DataSource<RequestUnbanList> requestUnbanListDataSource;
    private StudentMemberList studentMemberList;
    private RequestUnbanList requestUnbanList;
    private StudentMember studentMember;
    LabelAnimation labelAnimation;

    @FXML
    public void initialize(){
        CheckTimeTheme.check();
        Appearance appearanceSize = fontSizeDataSource.readData();
        Appearance appearanceFont = fontDataSource.readData();
        Appearance appearanceTheme = themeDataSource.readData();
        themeStr = getClass().getResource("/ku/cs/css/" + appearanceTheme.getData() + ".css").toExternalForm();
        fontStr = getClass().getResource("/ku/cs/css/Font" + appearanceFont.getData() + ".css").toExternalForm();
        fontSizeStr = getClass().getResource("/ku/cs/css/FontSize" + appearanceSize.getData() + ".css").toExternalForm();
        setStyle();

        labelAnimation = new LabelAnimation();
        memberListDataSource = new StudentMemberListFileDataSource("data", "StudentDataUser.csv");
        requestUnbanListDataSource = new RequestUnbanListFileDataSource("data", "RequestUnbanData.csv");
        studentMemberList = memberListDataSource.readData();
        requestUnbanList = requestUnbanListDataSource.readData();

        DateTime.getDateTime(timeLabel);
        noticeLabel.setText("");
    }

    public void setStyle() {
        borderPane.getStylesheets().add(fontStr);
        borderPane.getStylesheets().add(fontSizeStr);
        borderPane.getStylesheets().add(themeStr);

        borderPane.getStyleClass().add("background");
        backgroundRequestAnchorPane.getStyleClass().add("background");
        topLayoutAnchorPane.getStyleClass().add("anchorPane");
        timeLabel.getStyleClass().add("contentTextInAnchorPane");
        topicLabel.getStyleClass().add("contentTextInAnchorPane");
        topicLabel.getStyleClass().add("title");
    }
    
    @FXML
    public void handleAcceptToRequestButton(ActionEvent actionEvent){
        String name = nameTextField.getText();
        String username = usernameTextField.getText();
        String password = passwordField.getText();
        String reason = reasonTextArea.getText();

        studentMember = studentMemberList.findMember(username);
        if(studentMember != null){
            if(name.isEmpty() || username.isEmpty() || password.isEmpty() || reason.isEmpty()){
                showNoticeLabel("กรอกข้อมูลไม่สมบูรณ์", "Red");

            }else if(studentMember.getName().equals(name) && studentMember.getUsername().equals(username) && studentMember.getPassword().equals(password)){
                if(studentMember.getIsBaned().equals("No")){
                    showNoticeLabel("ข้อมูลนี้ไม่ได้ถูกระงับสิทธิ์", "Red");
                    clearLabel();

                }else{
                    requestUnbanList.addRequest(studentMember, reason);
                    showNoticeLabel("ยื่นคำร้องขอสำเร็จ", "Green");
                    requestUnbanListDataSource.writeData(requestUnbanList);
                    clearLabel();

                }
            }else{
                showNoticeLabel("ข้อมูลไม่ถูกต้อง", "Red");
                clearLabel();

            }
        }else{
            showNoticeLabel("ไม่มีข้อมูลในระบบ", "Red");
            clearLabel();
        }
    }

    @FXML
    public void handleBackButton(ActionEvent actionEvent){
        try {
            FXRouter.goTo("login");
        } catch (IOException e) {
            System.err.println("เข้าหน้า login ไม่ได้");
            throw new RuntimeException(e);
        }
    }

    private void clearLabel(){
        nameTextField.setText("");
        usernameTextField.setText("");
        passwordField.setText("");
        reasonTextArea.setText("");
    }

    private void showNoticeLabel(String string, String color){
        noticeLabel.setText(string);
        if(color.equals("Red")) noticeLabel.setStyle("-fx-text-fill: #ff7070");
        if(color.equals("Green")) noticeLabel.setStyle("-fx-text-fill: #63a82d");
        labelAnimation.fadeIn(noticeLabel, Direction.up);
    }
}

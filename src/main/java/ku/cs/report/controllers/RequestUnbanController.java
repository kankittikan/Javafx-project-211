package ku.cs.report.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import ku.cs.report.Animations.AnchorPaneAnimation;
import ku.cs.report.Animations.Direction;
import ku.cs.report.models.*;
import ku.cs.report.services.*;

import java.io.IOException;
import java.util.ArrayList;

public class RequestUnbanController implements AppearanceConfig{

    @FXML
    private Label timeLabel;
    @FXML
    private Label noticeLabel;
    @FXML
    private TextArea reasonTextArea;
    @FXML
    private ListView <String> requestListView;
    @FXML
    private AnchorPane anchorPane, topLayoutAnchorPane, APCenter;
    @FXML
    private Label topicLabel;

    private RequestUnban requestUnban;
    private RequestUnbanList requestUnbanList;
    private StudentMember studentMember;
    private StudentMemberList studentMemberList;
    private ArrayList<String> nameAndUsername;
    private DataSource<RequestUnbanList> requestUnbanListDataSource = new RequestUnbanListFileDataSource("data","RequestUnbanData.csv");
    private DataSource<StudentMemberList> memberListDataSource = new StudentMemberListFileDataSource("data","StudentDataUser.csv");

    private String themeStr, fontSizeStr, fontStr;
    private final DataSource<Appearance> fontSizeDataSource = new AppearanceDataSource("data/appearance", "FontSize.csv");
    private final DataSource<Appearance> fontDataSource = new AppearanceDataSource("data/appearance", "Font.csv");
    private final DataSource<Appearance> themeDataSource = new AppearanceDataSource("data/appearance", "theme.csv");

    @FXML
    public void initialize() {
        AnchorPaneAnimation anchorPaneAnimation = new AnchorPaneAnimation();
        anchorPaneAnimation.fadeIn(topLayoutAnchorPane, Direction.down);
        anchorPaneAnimation.fadeIn(APCenter,Direction.up);

        CheckTimeTheme.check();
        Appearance appearanceSize = fontSizeDataSource.readData();
        Appearance appearanceFont = fontDataSource.readData();
        Appearance appearanceTheme = themeDataSource.readData();
        themeStr = getClass().getResource("/ku/cs/css/" + appearanceTheme.getData() + ".css").toExternalForm();
        fontStr = getClass().getResource("/ku/cs/css/Font" + appearanceFont.getData() + ".css").toExternalForm();
        fontSizeStr = getClass().getResource("/ku/cs/css/FontSize" + appearanceSize.getData() + ".css").toExternalForm();
        setStyle();

        DateTime.getDateTime(timeLabel);

        requestUnbanList = requestUnbanListDataSource.readData();
        studentMemberList = memberListDataSource.readData();

        nameAndUsername = requestUnbanList.getNameAndUsername();

//        showListView();
        showUnbanRequestListView();
        handleSelectedListView();
        clearSelectedRequestUnban();


    }

    public void setStyle() {
        anchorPane.getStylesheets().add(fontStr);
        anchorPane.getStylesheets().add(fontSizeStr);
        anchorPane.getStylesheets().add(themeStr);

        anchorPane.getStyleClass().add("background");
        APCenter.getStyleClass().add("background");
        topLayoutAnchorPane.getStyleClass().add("anchorPane");
        timeLabel.getStyleClass().add("contentTextInAnchorPane");
        topicLabel.getStyleClass().add("contentTextInAnchorPane");
        topicLabel.getStyleClass().add("title");
    }

    @FXML
    public void handleBackButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("inappropriate");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า inappropriate ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUnBanUserButton(ActionEvent actionEvent){
        try{
            studentMember = studentMemberList.findMember(requestUnban.getUsername());
            if (studentMember.getIsBaned().equals("Baned")){
                studentMember.setUnBan();
                memberListDataSource.writeData(studentMemberList);
                noticeLabel.setText("ให้สิทธิ์ผู้ใช้งานเรียบร้อย");
            }else{
                noticeLabel.setText("ผู้ใช้งานนี้ให้สิทธิก่อนหน้านี้แล้ว");
            }
            handleDeleteRequest(actionEvent);
        }catch (NullPointerException e){
            noticeLabel.setText("คุณยังไม่ได้เลือกรายการ");
        }

    }
    @FXML
    private void handleDeleteRequest(ActionEvent actionEvent){
        requestUnbanList.removeRequest(requestUnban.getUsername());
        requestUnbanListDataSource.writeData(requestUnbanList);
        requestListView.getItems().clear();
        requestListView.refresh();
        nameAndUsername = requestUnbanList.getNameAndUsername();
        noticeLabel.setText("ลบรายการแล้ว");
        showUnbanRequestListView();
    }

    private void clearSelectedRequestUnban() {
        reasonTextArea.setText("----");
        noticeLabel.setText("");
    }

    private void showUnbanRequestListView(){
        requestListView.getItems().addAll(nameAndUsername);
        requestListView.refresh();
    }

    private void handleSelectedListView() {
        requestListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String requestUnban, String t1) {
                System.out.println(t1 + " is selected.");
                showSelectedRequest(t1);
            }
        });
    }

    private void showSelectedRequest(String requestUnbanString) {
        try {
            String[] strings = requestUnbanString.split("          ");
            System.out.println(strings[0]);
            requestUnban = requestUnbanList.findRequest(strings[0]);
            reasonTextArea.setText("เนื้อหา:\n" + requestUnban.getReason() );
        } catch (NullPointerException e) {
            System.err.println("รายการเรื่องร้องเรียนถูกคัลกรอง");
        }
    }

}

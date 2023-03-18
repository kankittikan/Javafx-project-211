package ku.cs.report.controllers;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;

import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ku.cs.report.Animations.ButtonAnimation;
import ku.cs.report.Animations.Direction;
import ku.cs.report.Animations.LabelAnimation;
import ku.cs.report.models.*;

import ku.cs.report.services.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;


public class StudentAllReportController implements AppearanceConfig {

    @FXML
    private String picture;

    //Navigation menu ===============================
    @FXML
    private AnchorPane anchorPaneNavigation; // #006b66c2
    @FXML
    private Label dateTime;
    @FXML
    private ImageView miniImageStudent;
    @FXML
    private Label usernameLabel;
    @FXML
    private MenuButton categoryMenuButton;
    @FXML
    private TextField increaseXLabel;
    @FXML
    private TextField mVoteTextField;
    @FXML
    private TextField nVoteTextFiled;
    @FXML
    private TableView<Report> tablePendingView;
    @FXML
    private TableView<Report> tableNotCompleteView;
    @FXML
    private TableView<Report> tableCompleteView;

    //body====================================
    @FXML
    private AnchorPane anchorPaneBody;
    @FXML
    private AnchorPane anchorPaneNav;
    @FXML
    private AnchorPane insideAnchorPane;
    @FXML
    private Label headAllReportLabel;
    @FXML
    private Label categoryAllReportLabel;
    @FXML
    private TextArea subjectTextArea;
    @FXML
    private TextArea subjectSpecificTextArea;
    @FXML
    private Label voteAllReportLabel;
    @FXML
    private Label timeAllReportLabel;
    @FXML
    private Button voteButton;
    @FXML
    private Tab pendingTab;
    @FXML
    private Tab notComTab;
    @FXML
    private Tab comTab;
    @FXML
    private Label statusAllLabel;
    @FXML
    private Label statusShowLabel; //#3fc735
    @FXML
    private ImageView pictureImage;
    @FXML private TextArea feedbackTextArea;


    private String usernameStaff;
    private String feedback;
    private StudentMember student;
    private DynamicCategoryMenuButton setDynamicCategoryMenuButtonPendingTab;

    private ReportList reportPendingList = new ReportList();
    private ReportList reportNotCompleteList = new ReportList();
    private ReportList reportCompleteList = new ReportList();
    private ReportList reportInappropriateList = new ReportList();
    private StudentMemberList studentMemberList = new StudentMemberList();

    private final DataSource<StudentMemberList> dataSourceMember = new StudentMemberListFileDataSource("data","StudentDataUser.csv");
    private final DataSource<ReportList> dataSourceReport = new ReportListFileDataSource("data", "ReportData.csv");
    private final DataSource<ReportList> dataSourceInappropriate = new ReportListFileDataSource("data", "Inappropriate.csv");

    private String themeStr, fontSizeStr, fontStr;
    private final DataSource<Appearance> fontSizeDataSource = new AppearanceDataSource("data/appearance", "FontSize.csv");
    private final DataSource<Appearance> fontDataSource = new AppearanceDataSource("data/appearance", "Font.csv");
    private final DataSource<Appearance> themeDataSource = new AppearanceDataSource("data/appearance", "theme.csv");
    private final LabelAnimation labelAnimation = new LabelAnimation();

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
        studentMemberList = dataSourceMember.readData();

        student = (StudentMember) com.github.saacsos.FXRouter.getData();
        student = StudentMemberList.readStudentDataUser(student.getUsername());
        int index = studentMemberList.findIndexMember(student);
        String username = student.getUsername();
        DateTime.getDateTime(dateTime);
        usernameLabel.setText(username);
        studentMemberList = dataSourceMember.readData();

        ReportList reportAllList = dataSourceReport.readData();
        reportInappropriateList = dataSourceInappropriate.readData();

        File file = new File("data/profiles/" + student.getPicture());
        miniImageStudent.setImage(new Image(file.toURI().toString()));
        System.out.println(student.getPicture());


        headAllReportLabel.setText("");
        categoryAllReportLabel.setText("");
        subjectTextArea.setText("");
        voteAllReportLabel.setText("");
        timeAllReportLabel.setText("");
        statusAllLabel.setText("");
        statusShowLabel.setText("");


        //Check category if no change to no category
        CheckCategory checkCategory = new CheckCategory(reportAllList);

        //Check reportAllList and change to reportPending or reportNotCom or reportCom to show in TableView
        reportPendingList = reportAllList.sortStatusReportList("รอดำเนินการ");
        reportNotCompleteList = reportAllList.sortStatusReportList("กำลังดำเนินการ");
        reportCompleteList = reportAllList.sortStatusReportList("ดำเนินการเสร็จสิ้น");

        SetNewTime setNewTime = new SetNewTime();
        reportPendingList = setNewTime.newTime(reportPendingList);
        reportNotCompleteList = setNewTime.newTime(reportNotCompleteList);
        reportCompleteList = setNewTime.newTime(reportCompleteList);

        showTableViews("รอดำเนินการ", reportPendingList);
        showTableViews("กำลังดำเนินการ", reportNotCompleteList);
        showTableViews("ดำเนินการเสร็จสิ้น", reportCompleteList);

        handleSelectedReportListView(tablePendingView);
        handleSelectedReportListView(tableNotCompleteView);
        handleSelectedReportListView(tableCompleteView);

        setDynamicCategoryMenuButtonPendingTab = new DynamicCategoryMenuButton(categoryMenuButton,
                reportPendingList, reportNotCompleteList, reportCompleteList, pendingTab, notComTab, comTab,
                tablePendingView, tableNotCompleteView, tableCompleteView);

        if (!student.getVoted().equals("No")) {
            if (Integer.parseInt(student.getVoted()) < LocalDateTime.now().getDayOfYear()) {
                student.setVoted("No");
                studentMemberList.addMember(index,student);
                studentMemberList.removeMember(index + 1);
                dataSourceMember.writeData(studentMemberList);
            }
        }
        System.out.println("Initialize StudentAllReportController");
    }
//    public void setTime(ReportList reportList) {
//        for (Report report: reportList.getAllList()) {
//            String time = report.getTime();
//            String[] time2 = time.split("\\|");
//            String date[] = time2[0].split("-");
//            String dateNew = date[2].trim() + "-" + date[1].trim() + "-"+  date[0].trim() + " |" + time2[1];
//            report.setTime(dateNew);
//        }
//    }

    public void setStyle() {
        anchorPaneNav.getStylesheets().add(fontStr);
        anchorPaneNav.getStylesheets().add(fontSizeStr);
        anchorPaneNav.getStylesheets().add(themeStr);

        anchorPaneBody.getStyleClass().add("background");
        anchorPaneNavigation.getStyleClass().add("anchorPane");
        insideAnchorPane.getStyleClass().add("anchorPanePopup");
        dateTime.getStyleClass().add("contentTextInAnchorPane");
        usernameLabel.getStyleClass().add("contentTextInAnchorPane");
    }

    private void showTableViews(String require, ReportList checkList) {
        TableColumn<Report, String> categoryCol = new TableColumn<>("หมวดหมู่");
        TableColumn<Report, String> headCol = new TableColumn<>("ชื่อเรื่อง");
        TableColumn<Report, Integer> voteCol = new TableColumn<>("คะแนน");
        TableColumn<Report, String> timeCol = new TableColumn<>("วันที่ / เวลา");
        TableColumn<Report, String> feedbackCol = new TableColumn<>("การตอบกลับ");
        TableColumn<Report, String> statusCol = new TableColumn<>("สถานะ");

        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        headCol.setCellValueFactory(new PropertyValueFactory<>("head"));
        voteCol.setCellValueFactory(new PropertyValueFactory<>("vote"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        feedbackCol.setCellValueFactory(new PropertyValueFactory<>("feedback"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        ObservableList<Report> list = FXCollections.observableList(checkList.getAllList());

        switch (require) {
            case "รอดำเนินการ" -> {
                tablePendingView.setItems(list);
                tablePendingView.getColumns().addAll(categoryCol, headCol, voteCol, timeCol, feedbackCol, statusCol);
            }
            case "กำลังดำเนินการ" -> {
                tableNotCompleteView.setItems(list);
                tableNotCompleteView.getColumns().addAll(categoryCol, headCol, voteCol, timeCol, feedbackCol, statusCol);
            }
            case "ดำเนินการเสร็จสิ้น" -> {
                tableCompleteView.setItems(list);
                tableCompleteView.getColumns().addAll(categoryCol, headCol, voteCol, timeCol, feedbackCol, statusCol);
            }
        }
    }

    private void handleSelectedReportListView(TableView<Report> reportSelectListView) {
        reportSelectListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    try {
                        System.out.println(newValue.getHead() + " is selected");
                        headAllReportLabel.setText(newValue.getHead());
                        labelAnimation.fadeIn(headAllReportLabel,Direction.right);
                        categoryAllReportLabel.setText(newValue.getCategory());
                        labelAnimation.fadeIn(categoryAllReportLabel,Direction.right);
                        subjectTextArea.setText(newValue.getSubject());
                        subjectSpecificTextArea.setText(newValue.getSubjectSpecific());
                        voteAllReportLabel.setText(String.valueOf(newValue.getVote()));
                        labelAnimation.fadeIn(voteAllReportLabel,Direction.right);
                        timeAllReportLabel.setText(newValue.getTime());
                        labelAnimation.fadeIn(timeAllReportLabel,Direction.right);
                        usernameStaff = newValue.getUsernameStaffManage();
                        feedback = newValue.getFeedback();
                        statusShowLabel.setText("");

                        switch (newValue.getStatus()) {
                            case "รอดำเนินการ" -> {
                                statusAllLabel.setText(newValue.getStatus());
                                statusAllLabel.setStyle("-fx-text-fill: #ff7070");
                                labelAnimation.fadeIn(statusAllLabel,Direction.right);
                            }
                            case "กำลังดำเนินการ" -> {
                                statusAllLabel.setText(newValue.getStatus());
                                statusAllLabel.setStyle("-fx-text-fill: #E4D00A");
                                labelAnimation.fadeIn(statusAllLabel,Direction.right);
                            }
                            case "ดำเนินการเสร็จสิ้น" -> {
                                statusAllLabel.setText(newValue.getStatus());
                                statusAllLabel.setStyle("-fx-text-fill: #63a82d");
                                labelAnimation.fadeIn(statusAllLabel,Direction.right);
                            }
                        }
                        feedbackTextArea.setText(newValue.getFeedback());
                        picture = newValue.getPicture();
                        if (picture != null) {
                            File file = new File("data/reportImages/" + picture);
                            pictureImage.setImage(new Image(file.toURI().toString()));
                        } else {
                            pictureImage.setImage(null);
                        }

                        voteButton.setText("โหวต" + " " + newValue.getHead());
                    } catch (NullPointerException e) {
                        System.err.println("newValue: is " + newValue + ".");
                    }
                });
    }

    public void betweenMAndN() {
        if (!mVoteTextField.getText().equals("") && !nVoteTextFiled.getText().equals("")) {
            if (pendingTab.isSelected()) {
                ReportList betweenMNList = reportPendingList.betweenMAndN(Integer.parseInt(mVoteTextField.getText()),
                        Integer.parseInt(nVoteTextFiled.getText()),
                        reportPendingList);
                tablePendingView.getColumns().clear();
                showTableViews("รอดำเนินการ", betweenMNList);
            } else if (notComTab.isSelected()) {
                ReportList betweenMNList = reportNotCompleteList.betweenMAndN(Integer.parseInt(mVoteTextField.getText()),
                        Integer.parseInt(nVoteTextFiled.getText()),
                        reportNotCompleteList);
                tableNotCompleteView.getColumns().clear();
                showTableViews("กำลังดำเนินการ", betweenMNList);
            } else if (comTab.isSelected()) {
                ReportList betweenMNList = reportCompleteList.betweenMAndN(Integer.parseInt(mVoteTextField.getText()),
                        Integer.parseInt(nVoteTextFiled.getText()),
                        reportCompleteList);
                tableCompleteView.getColumns().clear();
                showTableViews("ดำเนินการเสร็จสิ้น", betweenMNList);
            }
        }
    }

    public void handleClearBetweenSortMN(ActionEvent actionEvent) {
        mVoteTextField.clear();
        nVoteTextFiled.clear();
        if (pendingTab.isSelected()) {
            tablePendingView.getColumns().clear();
            showTableViews("รอดำเนินการ", reportPendingList);
        } else if (notComTab.isSelected()) {
            tableNotCompleteView.getColumns().clear();
            showTableViews("กำลังดำเนินการ", reportNotCompleteList);
        } else if (comTab.isSelected()) {
            tableCompleteView.getColumns().clear();
            showTableViews("ดำเนินการเสร็จสิ้น", reportCompleteList);
        }
    }

    public void greaterXVote(ActionEvent actionEvent) {
        if (!increaseXLabel.getText().equals("")) {
            int x = Integer.parseInt(increaseXLabel.getText());

            if (pendingTab.isSelected()) {
                ReportList greaterReportList = reportPendingList.greaterX(x, reportPendingList);
                tablePendingView.getColumns().clear();
                showTableViews("รอดำเนินการ", greaterReportList);
            } else if (notComTab.isSelected()) {
                ReportList greaterReportList = reportNotCompleteList.greaterX(x, reportNotCompleteList);
                tableNotCompleteView.getColumns().clear();
                showTableViews("กำลังดำเนินการ", greaterReportList);
            } else if (comTab.isSelected()) {
                ReportList greaterReportList = reportCompleteList.greaterX(x, reportCompleteList);
                tableCompleteView.getColumns().clear();
                showTableViews("ดำเนินการเสร็จสิ้น", greaterReportList);
            }
        }
    }

    public void reportAscendingTime(ActionEvent actionEvent) {
        if (pendingTab.isSelected()) {
            reportPendingList.ascendingTime();
            tablePendingView.getColumns().clear();
            showTableViews("รอดำเนินการ", reportPendingList);
        } else if (notComTab.isSelected()) {
            reportNotCompleteList.ascendingTime();
            tableNotCompleteView.getColumns().clear();
            showTableViews("กำลังดำเนินการ", reportNotCompleteList);
        } else if (comTab.isSelected()) {
            reportCompleteList.ascendingTime();
            tableCompleteView.getColumns().clear();
            showTableViews("ดำเนินการเสร็จสิ้น", reportCompleteList);
        }
    }

    public void reportDescendingTime(ActionEvent event) {
        if (pendingTab.isSelected()) {
            reportPendingList.descendingTime();
            tablePendingView.getColumns().clear();
            showTableViews("รอดำเนินการ", reportPendingList);
        } else if (notComTab.isSelected()) {
            reportNotCompleteList.descendingTime();
            tableNotCompleteView.getColumns().clear();
            showTableViews("กำลังดำเนินการ", reportNotCompleteList);
        } else if (comTab.isSelected()) {
            reportCompleteList.descendingTime();
            tableCompleteView.getColumns().clear();
            showTableViews("ดำเนินการเสร็จสิ้น", reportCompleteList);
        }
    }


    public void reportAscendingVote(ActionEvent actionEvent) {
        if (pendingTab.isSelected()) {
            reportPendingList.ascendingVote();
            tablePendingView.getColumns().clear();
            showTableViews("รอดำเนินการ", reportPendingList);
        } else if (notComTab.isSelected()) {
            reportNotCompleteList.ascendingVote();
            tableNotCompleteView.getColumns().clear();
            showTableViews("กำลังดำเนินการ", reportNotCompleteList);
        } else if (comTab.isSelected()) {
            reportCompleteList.ascendingVote();
            tableCompleteView.getColumns().clear();
            showTableViews("ดำเนินการเสร็จสิ้น", reportCompleteList);
        }
    }

    public void reportDescendingVote(ActionEvent actionEvent) {
        if (pendingTab.isSelected()) {
            reportPendingList.descendingVote();
            tablePendingView.getColumns().clear();
            showTableViews("รอดำเนินการ", reportPendingList);
        } else if (notComTab.isSelected()) {
            reportNotCompleteList.descendingVote();
            tableNotCompleteView.getColumns().clear();
            showTableViews("กำลังดำเนินการ", reportNotCompleteList);
        } else if (comTab.isSelected()) {
            reportCompleteList.descendingVote();
            tableCompleteView.getColumns().clear();
            showTableViews("ดำเนินการเสร็จสิ้น", reportCompleteList);
        }
    }


    public void voteReport(ActionEvent actionEvent) throws InvocationTargetException {
        if (!headAllReportLabel.getText().equals("") && student.getVoted().equals("No")) {
            studentMemberList = dataSourceMember.readData();
            int index = studentMemberList.findIndexMember(student);
            student = studentMemberList.getList(index);

            if (pendingTab.isSelected()) {
                VoteReport voteReportList = new VoteReport(reportPendingList, headAllReportLabel.getText(), subjectTextArea.getText(), timeAllReportLabel.getText());
                tablePendingView.getColumns().clear();
                showTableViews("รอดำเนินการ", reportPendingList);

                student.setVoted(String.valueOf(LocalDateTime.now().getDayOfYear()));
                studentMemberList.addMember(index,student);
                studentMemberList.removeMember(index + 1);
                dataSourceMember.writeData(studentMemberList);
                statusShowLabel.setText("โหวตเรียบร้อย");
                labelAnimation.fadeIn(statusShowLabel,Direction.left);
                statusShowLabel.setStyle("-fx-text-fill: #63a82d");

            } else if (notComTab.isSelected()) {
                VoteReport voteReportList = new VoteReport(reportNotCompleteList, headAllReportLabel.getText(), subjectTextArea.getText(), timeAllReportLabel.getText());
                tableNotCompleteView.getColumns().clear();
                showTableViews("กำลังดำเนินการ", reportNotCompleteList);

                student.setVoted("Yes");
                studentMemberList.addMember(index,student);
                studentMemberList.removeMember(index + 1);
                dataSourceMember.writeData(studentMemberList);
                statusShowLabel.setText("โหวตเรียบร้อย");
                labelAnimation.fadeIn(statusShowLabel,Direction.left);
                statusShowLabel.setStyle("-fx-text-fill: #63a82d");
            }
        }
        else if (!comTab.isSelected()){
            statusShowLabel.setText("โหวตได้ 1 ครั้งต่อวัน");
            labelAnimation.fadeIn(statusShowLabel,Direction.left);
            statusShowLabel.setStyle("-fx-text-fill: #ff7070");
        }
    }

    public void handleInappropriateButton(ActionEvent actionEvent) {
        if (!categoryAllReportLabel.getText().equals("") && !headAllReportLabel.getText().equals("") && !subjectTextArea.getText().equals("")) {
            popupStage();
        }
    }

    public void handleShowImageButton(ActionEvent actionEvent) throws NullPointerException{
        try {
            if (picture.equals("null")) {
                statusShowLabel.setText("เรื่องร้องเรียนนี้ไม่มีภาพ");
                statusShowLabel.setStyle("-fx-text-fill: #ff7070");
                labelAnimation.fadeIn(statusShowLabel, Direction.left);
            } else {
                com.github.saacsos.FXRouter.goTo("student_all_report_show_image", picture);
            }
        } catch (NullPointerException e) {
            System.err.println("ยังไม่ได้เลือกเรื่องร้องเรียน");
            statusShowLabel.setText("ยังไม่ได้เลือกรายการเรื่องร้องเรียน");
            statusShowLabel.setStyle("-fx-text-fill: #ff7070");
            labelAnimation.fadeIn(statusShowLabel, Direction.left);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void handleBackButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("student_home");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า student_home ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
            e.printStackTrace();
        }
    }
    @FXML
    public void handleMyReportButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("student_my_report",student);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า student_home ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
            e.printStackTrace();
        }
    }

    public void popupStage() {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("ระบบ");
        stage.setWidth(200);
        stage.setHeight(200);

        Label label = new Label("  รายงานความไม่เหมาะสมหรือไม่");

        Button button1 = new Button("ยกเลิก");
        button1.setPrefWidth(100);
        button1.setPrefHeight(40);
        button1.setStyle("-fx-padding: 10px");

        Button button2 = new Button("รายงาน");
        button2.setPrefWidth(100);
        button2.setPrefHeight(40);
        button2.setStyle("-fx-padding: 10px");

        TilePane tilepane = new TilePane();
        tilepane.setLayoutX(0);
        tilepane.setLayoutY(0);
        Popup popup = new Popup();
        popup.setAutoHide(true);

        EventHandler<ActionEvent> event1 = e -> {
            anchorPaneNav.setOpacity(1);
            stage.close();
        };
        EventHandler<ActionEvent> event2 = e -> {
            Report reportBuffer = new Report(categoryAllReportLabel.getText(), headAllReportLabel.getText(),
                    subjectTextArea.getText(), subjectSpecificTextArea.getText(),
                    student.getUsername(), Integer.parseInt(voteAllReportLabel.getText()),
                    timeAllReportLabel.getText(), usernameStaff,
                    feedback, statusAllLabel.getText(), picture);

            boolean flag = new HadReportInReportList().checkReport(reportBuffer);
            popUpWrite(flag, reportBuffer);
            anchorPaneNav.setOpacity(1);
            stage.close();
        };
        button2.setOnAction(event2);
        button1.setOnAction(event1);
        Label label1 = new Label("");
        tilepane.getChildren().addAll(label, button1, label1, button2);
        Scene scene = new Scene(tilepane, 200, 200);
        stage.setScene(scene);
        stage.show();
        anchorPaneNav.setOpacity(0.3);
        stage.focusedProperty().addListener((ov, onHidden, onShown) -> {
            if(!stage.isFocused()) {
                anchorPaneNav.setOpacity(1);
                Platform.runLater(stage::close);
            }
        });
        scene.getStylesheets().add(themeStr);
        scene.getStylesheets().add(fontStr);
        scene.getStylesheets().add(fontSizeStr);

        tilepane.getStyleClass().add("background");
        LabelAnimation labelAnimation = new LabelAnimation();
        ButtonAnimation buttonAnimation = new ButtonAnimation();
        labelAnimation.fadeIn(label, Direction.left);
        buttonAnimation.fadeIn(button1, Direction.left);
        buttonAnimation.fadeIn(button2, Direction.left);
    }

    public void popUpWrite(boolean flag, Report report) {
        if (!flag) {
            reportInappropriateList.addReport(report);
            dataSourceInappropriate.writeData(reportInappropriateList);
            statusShowLabel.setText("แจ้งความไม่เหมาะสมแล้ว");
            statusShowLabel.setStyle("-fx-text-fill: #63a82d");
            labelAnimation.fadeIn(statusShowLabel, Direction.left);
        } else statusShowLabel.setText("เรื่องร้องเรียนนี้ถูกแจ้งแล้ว");
        LabelAnimation labelAnimation = new LabelAnimation();
        labelAnimation.fadeIn(statusShowLabel, Direction.left);
        statusShowLabel.setStyle("-fx-text-fill: #ff7070");


    }
}

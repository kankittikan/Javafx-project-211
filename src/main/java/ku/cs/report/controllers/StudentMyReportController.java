package ku.cs.report.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import ku.cs.report.Animations.AnchorPaneAnimation;
import ku.cs.report.Animations.Direction;
import ku.cs.report.Animations.LabelAnimation;
import ku.cs.report.models.*;
import ku.cs.report.services.*;

import java.io.File;
import java.io.IOException;

public class StudentMyReportController implements AppearanceConfig {
    @FXML private AnchorPane mainAnchorPane;
    @FXML private AnchorPane anchorPane;
    @FXML private AnchorPane bodyAnchorPane;
    @FXML private Label dateTime;
    @FXML private Label usernameLabel;
    @FXML private ImageView miniImageStudent;
    @FXML private TableView<Report> tableMyReportView;


    // handle select my list view
    @FXML private Label headAllReportLabel;
    @FXML private Label categoryAllReportLabel;
    @FXML private TextArea subjectTextArea;
    @FXML private TextArea subjectSpecificTextArea;
    @FXML private Label voteAllReportLabel;
    @FXML private Label timeAllReportLabel;
    @FXML private Label statusLabel;
    @FXML private String picture;
    @FXML private ImageView pictureImage;
    @FXML private TextArea feedbackTextArea;
    @FXML private Label statusAllLabel;

    @FXML private AnchorPane navAnchorPane;
    @FXML private TextField mVoteTextField;
    @FXML private TextField nVoteTextFiled;



    private StudentMember student;
    private Report sendReport;
    private final DataSource<ReportList> dataSourceReport = new ReportListFileDataSource("data", "ReportData.csv");

    private String themeStr, fontSizeStr, fontStr;
    private final DataSource<Appearance> fontSizeDataSource = new AppearanceDataSource("data/appearance", "FontSize.csv");
    private final DataSource<Appearance> fontDataSource = new AppearanceDataSource("data/appearance", "Font.csv");
    private final DataSource<Appearance> themeDataSource = new AppearanceDataSource("data/appearance", "theme.csv");

    private ReportList myReportList;
    private final LabelAnimation labelAnimation = new LabelAnimation();

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

        AnchorPaneAnimation anchorPaneAnimation = new AnchorPaneAnimation();
        anchorPaneAnimation.fadeIn(mainAnchorPane, Direction.up);

        student = (StudentMember) com.github.saacsos.FXRouter.getData();
        student = StudentMemberList.readStudentDataUser(student.getUsername());
        String username = student.getUsername();
        DateTime.getDateTime(dateTime);
        usernameLabel.setText(username);
        File file = new File("data/profiles/" + student.getPicture());
        miniImageStudent.setImage(new Image(file.toURI().toString()));

        ReportList reportAllList = dataSourceReport.readData();
        CheckCategory checkCategory = new CheckCategory(reportAllList);  //Check category if no change to no category

        myReportList = new ReportListCategorySource().sortBy(reportAllList, student.getUsername());

        SetNewTime setNewTime = new SetNewTime();
        myReportList = setNewTime.newTime(myReportList);

        showTableViews(myReportList);
        handleSelectedReportListView(tableMyReportView);

        headAllReportLabel.setText("");
        categoryAllReportLabel.setText("");
        subjectTextArea.setText("");
        voteAllReportLabel.setText("");
        timeAllReportLabel.setText("");
        statusLabel.setText("");
        statusAllLabel.setText("");
        CheckCategory checkCategory1 = new CheckCategory(myReportList);

        System.out.println("Initialize StudentMyReportController");
    }

    public void setStyle() {
        anchorPane.getStylesheets().add(fontStr);
        anchorPane.getStylesheets().add(fontSizeStr);
        anchorPane.getStylesheets().add(themeStr);

        bodyAnchorPane.getStyleClass().add("background");
        mainAnchorPane.getStyleClass().add("anchorPane");
        navAnchorPane.getStyleClass().add("anchorPanePopup");
        dateTime.getStyleClass().add("contentTextInAnchorPane");
        usernameLabel.getStyleClass().add("contentTextInAnchorPane");
    }

    private void showTableViews(ReportList checkList) {
        TableColumn<Report,String> categoryCol = new TableColumn<>("หมวดหมู่");
        TableColumn<Report,String> headCol = new TableColumn<>("ชื่อเรื่อง");
        TableColumn<Report,Integer> voteCol = new TableColumn<>("คะแนน");
        TableColumn<Report,String> timeCol = new TableColumn<>("วันที่ / เวลา");
        TableColumn<Report,String> feedbackCol = new TableColumn<>("การตอบกลับ");
        TableColumn<Report,String> statusCol = new TableColumn<>("สถานะ");

        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        headCol.setCellValueFactory(new PropertyValueFactory<>("head"));
        voteCol.setCellValueFactory(new PropertyValueFactory<>("vote"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        feedbackCol.setCellValueFactory(new PropertyValueFactory<>("feedback"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        ObservableList<Report> list = FXCollections.observableList(checkList.getAllList());

        tableMyReportView.setItems(list);
        tableMyReportView.getColumns().addAll(categoryCol, headCol, voteCol, timeCol, feedbackCol, statusCol);
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

                        timeAllReportLabel.setText(newValue.getTime());
                        labelAnimation.fadeIn(timeAllReportLabel,Direction.right);

                        statusAllLabel.setText("");

                        switch (newValue.getStatus()) {
                            case "รอดำเนินการ" -> {
                                statusLabel.setText(newValue.getStatus());
                                statusLabel.setStyle("-fx-text-fill: #ff7070");
                                labelAnimation.fadeIn(statusLabel,Direction.right);
                            }
                            case "กำลังดำเนินการ" -> {
                                statusLabel.setText(newValue.getStatus());
                                statusLabel.setStyle("-fx-text-fill: #E4D00A");
                                labelAnimation.fadeIn(statusLabel,Direction.right);
                            }
                            case "ดำเนินการเสร็จสิ้น" -> {
                                statusLabel.setText(newValue.getStatus());
                                statusLabel.setStyle("-fx-text-fill: #63a82d");
                                labelAnimation.fadeIn(statusLabel,Direction.right);
                            }
                        }

                        subjectTextArea.setText(newValue.getSubject());

                        feedbackTextArea.setText(newValue.getFeedback());
                        subjectSpecificTextArea.setText(newValue.getSubjectSpecific());
                        voteAllReportLabel.setText(String.valueOf(newValue.getVote()));
                        labelAnimation.fadeIn(voteAllReportLabel,Direction.right);
                        sendReport = newValue;

                        picture = newValue.getPicture();
                        if (picture != null) {
                            File file = new File("data/reportImages/" + picture);
                            pictureImage.setImage(new Image(file.toURI().toString()));
                        }
                        else {
                            pictureImage.setImage(null);
                        }
                    } catch (NullPointerException e) {
                        System.err.println("newValue: is " + newValue + ".");
                    }
                });
    }
    public void reportBetweenMAndN() {
        if (!mVoteTextField.getText().equals("") && !nVoteTextFiled.getText().equals("")) {
            ReportList betweenMNList = myReportList.betweenMAndN(Integer.parseInt(mVoteTextField.getText()),
                    Integer.parseInt(nVoteTextFiled.getText()),
                    myReportList);
            tableMyReportView.getColumns().clear();
            showTableViews(betweenMNList);
        }
    }
    public void handleClearBetweenSortMAndN(ActionEvent actionEvent) {
        mVoteTextField.clear();
        nVoteTextFiled.clear();
        tableMyReportView.getColumns().clear();
        showTableViews(myReportList);
    }
    public void reportAscendingTime(ActionEvent actionEvent) {
        myReportList.ascendingTime();
        tableMyReportView.getColumns().clear();
        showTableViews(myReportList);
    }
    public void reportDescendingTime(ActionEvent event) {
        myReportList.descendingTime();
        tableMyReportView.getColumns().clear();
        showTableViews(myReportList);
    }
    public void reportAscendingVote(ActionEvent actionEvent) {
        myReportList.ascendingVote();
        tableMyReportView.getColumns().clear();
        showTableViews(myReportList);
    }
    public void reportDescendingVote(ActionEvent actionEvent) {
        myReportList.descendingVote();
        tableMyReportView.getColumns().clear();
        showTableViews(myReportList);
    }

    @FXML
    public void handleNewReportButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("student_new_report",student);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า student_home ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
            e.printStackTrace();
        }

    }
    @FXML
    public void handleAllReportButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("student_all_report",student);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า student_home ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
            e.printStackTrace();
        }

    }
    public void handleShowImageButton(ActionEvent actionEvent) throws NullPointerException{
        try {
            if (picture.equals("null")) {
                statusAllLabel.setText("เรื่องร้องเรียนนี้ไม่มีภาพ");
                statusAllLabel.setStyle("-fx-text-fill: #ff7070");
                labelAnimation.fadeIn(statusAllLabel, Direction.left);
            } else {
                com.github.saacsos.FXRouter.goTo("student_my_report_show_image", sendReport);
            }
        } catch (NullPointerException e) {
            System.err.println("ยังไม่ได้เลือกเรื่องร้องเรียน");
            statusAllLabel.setText("ยังไม่ได้เลือกรายการเรื่องร้องเรียน");
            statusAllLabel.setStyle("-fx-text-fill: #ff7070");
            labelAnimation.fadeIn(statusAllLabel, Direction.left);
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
}

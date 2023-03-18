package ku.cs.report.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import ku.cs.report.models.*;
import ku.cs.report.services.AppearanceDataSource;
import ku.cs.report.services.DataSource;

import java.io.File;
import java.io.IOException;

public class StudentMyReportShowImageReportController implements AppearanceConfig{
    @FXML
    private ImageView imageView;
    @FXML
    private AnchorPane mainAnchorPane;

    private Report report;
    private StudentMember student;

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

        report = (Report) com.github.saacsos.FXRouter.getData();
        student = StudentMemberList.readStudentDataUser(report.getUsernameReport());

        if (report.getPicture() != null) {
            File file = new File("data/reportImages/" + report.getPicture());
            imageView.setImage(new Image(file.toURI().toString()));
        } else {
            imageView.setImage(null);
        }
    }

    public void setStyle() {
        mainAnchorPane.getStylesheets().add(fontStr);
        mainAnchorPane.getStylesheets().add(fontSizeStr);
        mainAnchorPane.getStylesheets().add(themeStr);

        mainAnchorPane.getStyleClass().add("background");
    }

    @FXML
    public void handleBackButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("student_my_report",student);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า student_home ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
            e.printStackTrace();
        }
    }
}

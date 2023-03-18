package ku.cs.report.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.github.saacsos.FXRouter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import ku.cs.report.models.Appearance;
import ku.cs.report.models.CheckTimeTheme;
import ku.cs.report.services.AppearanceDataSource;
import ku.cs.report.services.DataSource;

import java.io.File;
import java.io.IOException;

public class StudentAllReportShowImageReportController implements AppearanceConfig{
    @FXML private ImageView imageView;
    @FXML private AnchorPane mainAnchorPane;

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

        String picture = (String) FXRouter.getData();

        if (picture != null) {
            File file = new File("data/reportImages/" + picture);
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
            com.github.saacsos.FXRouter.goTo("student_all_report");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า student_home ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
            e.printStackTrace();
        }
    }

}

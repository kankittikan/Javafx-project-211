package ku.cs.report.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import ku.cs.report.models.Appearance;
import ku.cs.report.models.CheckTimeTheme;
import ku.cs.report.models.DateTime;
import ku.cs.report.models.Report;
import com.github.saacsos.FXRouter;
import ku.cs.report.services.AppearanceDataSource;
import ku.cs.report.services.DataSource;

import java.io.File;
import java.io.IOException;

public class ImageViewReportInStaffController implements AppearanceConfig{
    @FXML private ImageView reportImageView;
    @FXML private AnchorPane anchorPane;

    private Report report;

    private String themeStr, fontSizeStr, fontStr;
    private final DataSource<Appearance> fontSizeDataSource = new AppearanceDataSource("data/appearance", "FontSize.csv");
    private final DataSource<Appearance> fontDataSource = new AppearanceDataSource("data/appearance", "Font.csv");
    private final DataSource<Appearance> themeDataSource = new AppearanceDataSource("data/appearance", "theme.csv");

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

        report = (Report) FXRouter.getData();
        File file = new File("data/ReportImages/" + report.getPicture());
        reportImageView.setImage(new Image(file.toURI().toString()));
    }

    public void setStyle() {
        anchorPane.getStylesheets().add(fontStr);
        anchorPane.getStylesheets().add(fontSizeStr);
        anchorPane.getStylesheets().add(themeStr);

        anchorPane.getStyleClass().add("background");
    }

    @FXML
    public void handleBackButton(ActionEvent actionEvent){
        try {
            FXRouter.goTo("staff_home");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

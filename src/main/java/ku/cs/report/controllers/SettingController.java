package ku.cs.report.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.AnchorPane;
import ku.cs.report.Animations.AnchorPaneAnimation;
import ku.cs.report.Animations.Direction;
import ku.cs.report.Animations.LabelAnimation;
import ku.cs.report.models.Appearance;
import ku.cs.report.models.CheckTimeTheme;
import ku.cs.report.models.DateTime;
import ku.cs.report.services.DataSource;
import ku.cs.report.services.AppearanceDataSource;

import java.io.IOException;
import java.time.LocalTime;

public class SettingController implements AppearanceConfig {
    @FXML
    private AnchorPane background;
    @FXML
    private AnchorPane topAnchorPane;
    @FXML
    private AnchorPane downAnchorPane;
    @FXML
    private AnchorPane anchorPanePopup;
    @FXML
    private Button backButton;
    @FXML
    private Label time;
    @FXML
    private Label titleLabel;
    @FXML
    private MenuButton menuButton1;
    @FXML
    private MenuButton menuButton2;
    @FXML
    private MenuButton menuButton3;
    @FXML private CheckBox checkBox;
    private String themeStr, fontSizeStr, fontStr;
    private AnchorPaneAnimation anchorPaneAnimation = new AnchorPaneAnimation();
    private LabelAnimation labelAnimation = new LabelAnimation();
    private DataSource<Appearance> fontSizeDataSource = new AppearanceDataSource("data/appearance", "FontSize.csv");
    private DataSource<Appearance> fontDataSource = new AppearanceDataSource("data/appearance", "Font.csv");
    private DataSource<Appearance> themeDataSource = new AppearanceDataSource("data/appearance", "theme.csv");
    private Appearance appearanceSize;
    private Appearance appearanceFont;
    private Appearance appearanceTheme;

    @FXML
    public void initialize() {
        CheckTimeTheme.check();
        appearanceSize = fontSizeDataSource.readData();
        appearanceFont = fontDataSource.readData();
        appearanceTheme = themeDataSource.readData();
        themeStr = getClass().getResource("/ku/cs/css/" + appearanceTheme.getData() + ".css").toExternalForm();
        fontStr = getClass().getResource("/ku/cs/css/Font" + appearanceFont.getData() + ".css").toExternalForm();
        fontSizeStr = getClass().getResource("/ku/cs/css/FontSize" + appearanceSize.getData() + ".css").toExternalForm();
        setStyle();

        menuButton1.setText(appearanceTheme.getData());
        menuButton2.setText("ขนาด " + appearanceSize.getData());
        menuButton3.setText(appearanceFont.getData());
        DateTime.getDateTime(time);
        anchorPaneAnimation.fadeIn(anchorPanePopup, Direction.up);
        labelAnimation.fadeIn(titleLabel, Direction.down);
        handleCheckBox();
    }

    public void setStyle() {
        background.getStylesheets().add(fontStr);
        background.getStylesheets().add(fontSizeStr);
        background.getStylesheets().add(themeStr);

        background.getStyleClass().add("background");
        topAnchorPane.getStyleClass().add("anchorPane");
        downAnchorPane.getStyleClass().add("anchorPane");
        backButton.getStyleClass().add("buttonInAnchorPane");
        anchorPanePopup.getStyleClass().add("anchorPanePopup");
        time.getStyleClass().add("contentTextInAnchorPane");
        titleLabel.getStyleClass().add("contentTextInAnchorPane");
        titleLabel.getStyleClass().add("title");
    }

    private void handleCheckBox() {
        if(appearanceTheme.getData2().equals("System")) {
            checkBox.setSelected(true);
            menuButton1.setDisable(true);
        }
        checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    menuButton1.setDisable(true);
                    appearanceTheme.setData2("System");
                    themeDataSource.writeData(appearanceTheme);
                    if(LocalTime.now().getHour() > 18) handleDarkTheme(new ActionEvent());
                    else handleBrightTheme(new ActionEvent());
                }else{
                    menuButton1.setDisable(false);
                    appearanceTheme.setData2("No System");
                    themeDataSource.writeData(appearanceTheme);
                }
            }
        });
    }

    @FXML
    public void handleBackButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("login");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า login ไม่ได้");
            System.err.println("ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleSize14(ActionEvent actionEvent) {
        appearanceSize.setData("14");
        fontSizeDataSource.writeData(appearanceSize);
        try {
            com.github.saacsos.FXRouter.goTo("setting");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า setting ไม่ได้");
            System.err.println("ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleSize15(ActionEvent actionEvent) {
        appearanceSize.setData("15");
        fontSizeDataSource.writeData(appearanceSize);
        try {
            com.github.saacsos.FXRouter.goTo("setting");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า setting ไม่ได้");
            System.err.println("ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleSize16(ActionEvent actionEvent) {
        appearanceSize.setData("16");
        fontSizeDataSource.writeData(appearanceSize);
        try {
            com.github.saacsos.FXRouter.goTo("setting");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า setting ไม่ได้");
            System.err.println("ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleKanitFont(ActionEvent actionEvent) {
        appearanceFont.setData("Kanit");
        fontDataSource.writeData(appearanceFont);
        try {
            com.github.saacsos.FXRouter.goTo("setting");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า setting ไม่ได้");
            System.err.println("ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleSarabunFont(ActionEvent actionEvent) {
        appearanceFont.setData("Sarabun");
        fontDataSource.writeData(appearanceFont);
        try {
            com.github.saacsos.FXRouter.goTo("setting");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า setting ไม่ได้");
            System.err.println("ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleKoHoFont(ActionEvent actionEvent) {
        appearanceFont.setData("KoHo");
        fontDataSource.writeData(appearanceFont);
        try {
            com.github.saacsos.FXRouter.goTo("setting");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า setting ไม่ได้");
            System.err.println("ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleMaliFont(ActionEvent actionEvent) {
        appearanceFont.setData("Mali");
        fontDataSource.writeData(appearanceFont);
        try {
            com.github.saacsos.FXRouter.goTo("setting");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า setting ไม่ได้");
            System.err.println("ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleBrightTheme(ActionEvent actionEvent) {
        appearanceTheme.setData("BrightTheme");
        themeDataSource.writeData(appearanceTheme);
        try {
            com.github.saacsos.FXRouter.goTo("setting");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า setting ไม่ได้");
            System.err.println("ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleDarkTheme(ActionEvent actionEvent) {
        appearanceTheme.setData("DarkTheme");
        themeDataSource.writeData(appearanceTheme);
        try {
            com.github.saacsos.FXRouter.goTo("setting");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า setting ไม่ได้");
            System.err.println("ตรวจสอบการกำหนด route");
        }
    }

    public void handleSystemFont(ActionEvent actionEvent) {
        appearanceFont.setData("System");
        fontDataSource.writeData(appearanceFont);
        try {
            com.github.saacsos.FXRouter.goTo("setting");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า setting ไม่ได้");
            System.err.println("ตรวจสอบการกำหนด route");
        }
    }
}

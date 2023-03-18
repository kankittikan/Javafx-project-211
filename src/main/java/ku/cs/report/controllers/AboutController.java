package ku.cs.report.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import ku.cs.report.Animations.AnchorPaneAnimation;
import ku.cs.report.Animations.Direction;
import ku.cs.report.Animations.LabelAnimation;
import ku.cs.report.models.Appearance;
import ku.cs.report.models.CheckTimeTheme;
import ku.cs.report.models.DateTime;
import ku.cs.report.services.AppearanceDataSource;
import ku.cs.report.services.DataSource;

import java.io.IOException;

public class AboutController implements AppearanceConfig {
    @FXML private Button backButton;

    @FXML private AnchorPane bottomLayoutAnchorPane;

    @FXML private Label idStudentAboutLabel;

    @FXML private Label idStudentLabel;

    @FXML private Label nameAboutLabel;

    @FXML private Label nameLabel;

    @FXML private Label nicknameAboutLabel;

    @FXML private Label nicknameLabel;

    @FXML private Label personAboutLabel;

    @FXML private ImageView picAbout;

    @FXML private Button showPersonButton1;

    @FXML private Button showPersonButton2;

    @FXML private Button showPersonButton3;

    @FXML private Button showPersonButton4;

    @FXML private AnchorPane tabPersonButtonAnchorPane;

    @FXML private Label timeLabel;

    @FXML private AnchorPane topLayoutAnchorPane, backgroundAboutAnchorPane;

    @FXML private Label topicLabel;
    @FXML private BorderPane borderPane;
    AnchorPaneAnimation anchorPaneAnimation;
    LabelAnimation labelAnimation;

    String themeStr, fontSizeStr, fontStr;
    private DataSource<Appearance> fontSizeDataSource = new AppearanceDataSource("data/appearance", "FontSize.csv");
    private DataSource<Appearance> fontDataSource = new AppearanceDataSource("data/appearance", "Font.csv");
    private DataSource<Appearance> themeDataSource = new AppearanceDataSource("data/appearance", "theme.csv");
    private Appearance appearanceSize;
    private Appearance appearanceFont;
    private Appearance appearanceTheme;

    @FXML
    public void initialize(){
        CheckTimeTheme.check();
        appearanceSize = fontSizeDataSource.readData();
        appearanceFont = fontDataSource.readData();
        appearanceTheme = themeDataSource.readData();
        themeStr = getClass().getResource("/ku/cs/css/" + appearanceTheme.getData() + ".css").toExternalForm();
        fontStr = getClass().getResource("/ku/cs/css/Font" + appearanceFont.getData() + ".css").toExternalForm();
        fontSizeStr = getClass().getResource("/ku/cs/css/FontSize" + appearanceSize.getData() + ".css").toExternalForm();
        setStyle();

        System.out.println("Initialize AboutController");

        String urlUnknown = getClass().getResource("/ku/cs/images/UnknownProfile.jpg").toExternalForm();

        picAbout.setImage(new Image(urlUnknown));
        picAbout.setPreserveRatio(false);
        nameLabel.setText("---");
        nicknameLabel.setText("---");
        idStudentLabel.setText("---");

        anchorPaneAnimation = new AnchorPaneAnimation();
        anchorPaneAnimation.fadeIn(tabPersonButtonAnchorPane, Direction.up);

        labelAnimation = new LabelAnimation();
        labelAnimation.fadeIn(topicLabel, Direction.down);
        labelAnimation.fadeIn(nameLabel, Direction.left);
        labelAnimation.fadeIn(nicknameLabel, Direction.left);
        labelAnimation.fadeIn(idStudentLabel, Direction.left);

        DateTime.getDateTime(timeLabel);

    }

    public void setStyle() {
        borderPane.getStylesheets().add(fontStr);
        borderPane.getStylesheets().add(fontSizeStr);
        borderPane.getStylesheets().add(themeStr);

        backgroundAboutAnchorPane.getStyleClass().add("background");
        topLayoutAnchorPane.getStyleClass().add("anchorPane");
        bottomLayoutAnchorPane.getStyleClass().add("anchorPane");
        backButton.getStyleClass().add("buttonInAnchorPane");
        topicLabel.getStyleClass().add("title");
        topicLabel.getStyleClass().add("contentTextInAnchorPane");
        timeLabel.getStyleClass().add("contentTextInAnchorPane");
    }

    public void showFirstPersonButton(){
        String urlKittikan = getClass().getResource("/ku/cs/images/pic_Kittikan.jpg").toExternalForm();

        picAbout.setImage(new Image(urlKittikan));
        nameLabel.setText("กิตติกานต์ มากผล");
        nicknameLabel.setText("กาน");
        idStudentLabel.setText("6410450087");


        labelAnimation.fadeIn(nameLabel, Direction.left);
        labelAnimation.fadeIn(nicknameLabel, Direction.left);
        labelAnimation.fadeIn(idStudentLabel, Direction.left);


    }

    public void showSecondPersonButton(){
        String urlNisit = getClass().getResource("/ku/cs/images/pic_Nisit.jpg").toExternalForm();

        picAbout.setImage(new Image(urlNisit));
        nameLabel.setText("นิสิต นะมิตร");
        nicknameLabel.setText("อ้น");
        idStudentLabel.setText("6410451148");


        labelAnimation.fadeIn(nameLabel, Direction.left);
        labelAnimation.fadeIn(nicknameLabel, Direction.left);
        labelAnimation.fadeIn(idStudentLabel, Direction.left);
    }

    public void showThirdPersonButton(){
        String urlPeerasit = getClass().getResource("/ku/cs/images/pic_Peerasit.jpg").toExternalForm();

        picAbout.setImage(new Image(urlPeerasit));
        nameLabel.setText("พีรสิษฐ์ พลอยอร่าม");
        nicknameLabel.setText("เอิทธ์");
        idStudentLabel.setText("6410451237");


        labelAnimation.fadeIn(nameLabel, Direction.left);
        labelAnimation.fadeIn(nicknameLabel, Direction.left);
        labelAnimation.fadeIn(idStudentLabel, Direction.left);
    }

    public void showFourthPersonButton(){
        String urlSiwakorn = getClass().getResource("/ku/cs/images/pic_Siwakorn.jpg").toExternalForm();

        picAbout.setImage(new Image(urlSiwakorn));
        nameLabel.setText("ศิวกร ภาสว่าง");
        nicknameLabel.setText("โอ๊ต");
        idStudentLabel.setText("6410451423");


        labelAnimation.fadeIn(nameLabel, Direction.left);
        labelAnimation.fadeIn(nicknameLabel, Direction.left);
        labelAnimation.fadeIn(idStudentLabel, Direction.left);
    }
    @FXML
    public void handleBackButton(ActionEvent actionEvent){
        try {
            com.github.saacsos.FXRouter.goTo("login");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า login ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
            e.printStackTrace();
        }
    }
}

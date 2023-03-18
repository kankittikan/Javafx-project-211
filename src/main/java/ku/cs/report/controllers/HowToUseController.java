package ku.cs.report.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import ku.cs.report.Animations.Direction;
import ku.cs.report.Animations.LabelAnimation;
import ku.cs.report.models.Appearance;
import ku.cs.report.models.CheckTimeTheme;
import ku.cs.report.services.AppearanceDataSource;
import ku.cs.report.services.DataSource;

import java.io.IOException;

public class HowToUseController implements AppearanceConfig {
    @FXML
    private Button backButton;
    @FXML
    private BorderPane borderPane;

    @FXML
    private AnchorPane backgroundHowToUseAnchorPane;

    @FXML
    private AnchorPane bottomLayoutAnchorPane;

    @FXML
    private AnchorPane topLayoutAnchorPane;

    @FXML
    private Label topicLabel;

    @FXML
    private RadioButton banRButton, changeAgencyRButton, unBanRButton, registerStaffRButton, manageCategoryRButton;
    @FXML
    private RadioButton chooseReportRButton, feedbackRButton;
    @FXML
    private RadioButton voteRButton, wrongRButton, requestRButton, createReportRButton;
    @FXML
    private TextArea howToUseAdminTextArea, howToUseStaffTextArea, howToUseStudentTextArea, changePasswordTextArea, changeImageTextArea, registerStudentTextArea;

    private String themeStr, fontSizeStr, fontStr;
    private DataSource<Appearance> fontSizeDataSource = new AppearanceDataSource("data/appearance", "FontSize.csv");
    private DataSource<Appearance> fontDataSource = new AppearanceDataSource("data/appearance", "Font.csv");
    private DataSource<Appearance> themeDataSource = new AppearanceDataSource("data/appearance", "theme.csv");

    private LabelAnimation labelAnimation = new LabelAnimation();
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

        String registerStudentText = "กดลงทะเบียนในหน้าเข้าสู่ระบบ -> กรอกข้อมูลให้ครบถ้วนในหน้าลงทะเบียน -> กดลงทะเบียนในหน้าลงทะเบียน -> กดลงชื่อเข้าใช้เพื่อลองเข้าหน้าเข้าสู่ระบบ";
        registerStudentTextArea.setText(registerStudentText);
        String changePasswordText = "กดโปรไฟล์ในหน้าหลักของผู้ใช้งาน -> กดเปลี่ยนรหัสผ่าน -> กรอกข้อมูลให้ครบถ้วนพร้อมพิมพ์รหัสผ่านที่ต้องการเปลี่ยน -> กดยืนยันเปลี่ยนรหัสผ่าน";
        changePasswordTextArea.setText(changePasswordText);
        String changeImageText = "กดโปรไฟล์ในหน้าหลักของผู้ใช้งาน -> กดเปลี่ยนรูปภาพ -> เลือกไฟล์ภาพใน File Explorer -> กดยืนยันใน File Explorer เพื่อเปลี่ยนรูปภาพ (แล้วแต่ประเภท OS ของผู้ใช้งาน)";
        changeImageTextArea.setText(changeImageText);

        labelAnimation.fadeIn(topicLabel, Direction.down);
    }

    public void setStyle() {
        borderPane.getStylesheets().add(fontStr);
        borderPane.getStylesheets().add(fontSizeStr);
        borderPane.getStylesheets().add(themeStr);

        borderPane.getStyleClass().add("background");
        backgroundHowToUseAnchorPane.getStyleClass().add("background");
        topLayoutAnchorPane.getStyleClass().add("anchorPane");
        bottomLayoutAnchorPane.getStyleClass().add("anchorPane");
        backButton.getStyleClass().add("buttonInAnchorPane");
        topicLabel.getStyleClass().add("title");
        topicLabel.getStyleClass().add("contentTextInAnchorPane");
    }

    @FXML
    public void handleHowToUseAdminRButton(ActionEvent event) {
        String text = "";
        if (banRButton.isSelected()) { // วิธีการระงับสิทธ์ผู้ใช้งาน
            text = "ในหน้าหลักของผู้ดูแลระบบ กดรายงานความไม่เหมาะสม -> เลือกเรื่องร้องเรียนที่นิสิตส่งรายงานความไม่เหมาะสม -> กดระงับสิทธิ์ผู้ใช้งาน";
        } else if (changeAgencyRButton.isSelected()) { // วิธีการเปลี่ยนหน่วยงานของผู้ใช้งาน
            text = "ในหน้าหลักของผู้ดูแลระบบ กดจัดการหน่วยงานเจ้าหน้าที่ -> เลือกเจ้าหน้าที่ที่อยากจัดการมา 1 คน -> เลือกหน่วยงานใหม่ -> กดแก้ไขหน่วยงาน";
        } else if (unBanRButton.isSelected()) { // วิธีการคืนสิทธิ์ผู้ใช้งาน
            text = "ในหน้าหลักของผู้ดูแลระบบ กดรายงานความไม่เหมาะสม -> กดคำร้องขอคืนสิทธิ์ -> เลือกผู้ใช้งานที่อยากจะคืนสิทธิ์ผู้ใช้งาน -> กดคืนสิทธิ์ เพื่อผู้ใช้สามารถเข้าใช้งานได้อีกครั้ง";
        } else if (registerStaffRButton.isSelected()) { // วิธีการลงทะเบียนของเจ้าหน้าที่
            text = "ในหน้าหลักของผู้ดูแลระบบ กดลงทะเบียนเจ้าหน้าที่ -> กรอกข้อมูลให้ครบถ้วนและเลือกหน้วนงานให้กับเจ้าหน้าที่ -> กดลงทะเบียนเป็นอันเสร็จสิ้น";
        } else if (manageCategoryRButton.isSelected()) { // วิธีการจัดการหมวดหมู่
            text = "ในหน้าหลักของผู้ดูแลระบบ กดจัดการหมวดหมู่ร้องเรียน -> เลือกการแก้ไข (เพิ่มหมวดหมู่, เปลี่ยนชื่อ, ลบหมวดหมู่) :" + "\n\n" +
                    "เพิ่มหมวดหมู่: กรอกข้อมูลให้ครบถ้วน -> กดเพิ่มหมวดหมู่" + "\n\n" +
                    "เปลี่ยนชื่อ: เลือกหมวดหมู่ -> กรอกข้อมูลให้ครบถ้วน -> เปลี่ยนชื่อหมวดหมู่" + "\n\n" +
                    "ลบหมวดหมู่: เลือกหมวดหมู่ -> กรอกข้อมูลให้ครบถ้วน -> ลบหมวดหมู่";
        }

        howToUseAdminTextArea.setText(text);
    }

    @FXML
    public void handleHowToUseStaffRButton(ActionEvent event) {
        String text = "";
        if (chooseReportRButton.isSelected()) { // เลือกเรื่องร้องเรียนมาจัดการ
            text = "ในหน้าหลักของเจ้าห้าที่ เลือกรายการเรื่องร้องเรียน -> กดยืนยันดำเนินการเรื่องร้องเรียนนี้ \n\nเงื่อนไข: โดยจะสามารถกดยืนยันดำเนินการเรื่องร้องเรียนได้ก็ต่อเมื่อเรื่องร้องร้องเรียนดังกล่าวแสดงสถานะว่า รอดำเนินการ และ แสดงผู้รับผิดชอบว่า ระบุไม่ได้";
        } else if (feedbackRButton.isSelected()) { // ส่งวิธีการจัดการ
            text = "ในหน้าหลักของเจ้าห้าที่ เลือกรายการเรื่องร้องเรียนที่ผู้ใช้งานรับผิดชอบอยู่ -> ใส่ข้อความวิธีการจัดการของเรื่องร้องเรียนนั้น -> กดส่งวิธีการจัดการเมื่อใส่ข้อความเสร็จสิ้น (สามารถแก้ไขข้อความได้โดยเพียงแก้ไขข้อความของเรื่องร้องเรียนนั้น และกดส่งวิธีการจัดการอีกครั้ง)" +
                    "\n\nเพิ่มเติม: สามารถเปลี่ยนสถานะเรื่องร้องเรียนนั้นได้โดยกด Checkbox ดำเนินการเสร็จสิ้น บริเวณนอกมุมล่างซ้ายของกล่องใส่ข้อความวิธีการจัดการ ก่อนกดส่งวิธีการจัดการ แต่เมื่อไม่ได้กดไว้จะถูกเปลี่ยนสถานะเป็น กำลังดำเนินการ\n";
        }

        howToUseStaffTextArea.setText(text);
    }

    @FXML
    public void handleHowToUseStudentRButton(ActionEvent event) {
        String text = "";
        if (createReportRButton.isSelected()) { // สร้างเรื่องร้องเรียน
            text = "ในหน้าหลักของนิสิต กดแจ้งเรื่องร้องเรียนภายในมหาวิทยาลัย -> กรอกข้อมูลให้ครบถ้วนและเลือกหมวด (ถ้ามีรูปภาพ ให้กดเพิ่มรูปภาพ เลือกไฟล์ภาพที่ต้องการและกดยืนยัน) -> กดยืนยันเพื่อเสร็จสิ้นการสร้างเรื่องร้องเรียน ";
        } else if (requestRButton.isSelected()) { // ทำคำร้องขอคืนสิทธิ์
            text = "จะสามารถใช้ได้ก็ต่อเมื่อผู้ใช้งานไม่สามารถเข้าหน้าหลักของผู้ใช้งานได้และมีปุ่มเพิ่มขึ้นมาว่า ส่งคำร้องขอ -> กรอกข้อมูลให้ครบถ้วนและใส่ข้อความเหตุผลในด้านขวา -> กดยืนยันคำร้องขอและรอให้ผู้ดูแลระบบพิจารณาคืนสิทธ์ผู้ใช้งาน";
        } else if (wrongRButton.isSelected()) { // รายงานไม่เหมาะสม
            text = "ในหน้าหลักของนิสิต กดรวมเรื่องร้องเรียนทั้งหมดจากทุกหมวดหมู่ หรือเรื่องร้องเรียนเฉพาะผู้ใช้ระบบ -> เลือกเรื่องร้องเรียนที่คิดว่าเรื่องดังกล่าวไม่เหมาะสม -> กดรายงานไม่เหมาะสม -> กดรายงาน";
        } else if (voteRButton.isSelected()) { // โหวต
            text = "ในหน้าหลักของนิสิต กดรวมเรื่องร้องเรียนทั้งหมดจากทุกหมวดหมู่ หรือเรื่องร้องเรียนเฉพาะผู้ใช้ระบบ -> เลือกเรื่องร้องเรียนที่อยากจะโหวต -> กดโหวต (สามารถกดได้วันละ 1 ครั้ง)";
        }

        howToUseStudentTextArea.setText(text);
    }

    @FXML
    public void handleBackButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("login");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า login ไม่ได้");
            System.err.println("ให้ตรวจสอบการกําหนด route");
            e.printStackTrace();
        }
    }

}

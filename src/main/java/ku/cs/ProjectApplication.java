package ku.cs;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;

import com.github.saacsos.FXRouter;

public class ProjectApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXRouter.bind(this, stage, "Report System Kasetsart University", 1024, 720);
        stage.getIcons().add(new Image(getClass().getResource("/ku/cs/images/KU_Logo.png").toExternalForm()));
        configRoute();
        FXRouter.goTo("login");
    }

    private static void configRoute() {
        String packageStr = "ku/cs/";
        FXRouter.when("about", packageStr + "about.fxml");
        FXRouter.when("how_to_use", packageStr + "how_to_use.fxml");
        FXRouter.when("login", packageStr + "login.fxml");
        FXRouter.when("register", packageStr + "register.fxml");
        FXRouter.when("student_home", packageStr + "student_home.fxml");
        FXRouter.when("student_profile", packageStr + "student_profile.fxml");
        FXRouter.when("staff_profile", packageStr + "staff_profile.fxml");
        FXRouter.when("staff_home", packageStr + "staff_home.fxml");
        FXRouter.when("admin_home", packageStr + "admin_home.fxml");
        FXRouter.when("forgot_password", packageStr + "forgot_password.fxml");
        FXRouter.when("admin_profile", packageStr + "admin_profile.fxml");
        FXRouter.when("staff_profile_changePassword", packageStr + "staff_profile_change_password_.fxml");
        FXRouter.when("student_profile_changePassword", packageStr + "student_profile_changePassword.fxml");
        FXRouter.when("student_new_report", packageStr + "student_new_report.fxml");
        FXRouter.when("student_my_report", packageStr + "student_my_report.fxml");
        FXRouter.when("student_my_report_show_image", packageStr + "student_my_report_show_image.fxml");
        FXRouter.when("student_all_report", packageStr + "student_all_report.fxml");
        FXRouter.when("student_all_report_show_image", packageStr + "student_all_report_show_image.fxml");
        FXRouter.when("staff_register", packageStr + "staff_register.fxml");
        FXRouter.when("manage_category", packageStr + "manage_category.fxml");
        FXRouter.when("staff_manage", packageStr + "staff_manage.fxml");
        FXRouter.when("image_view_report", packageStr + "image_view_report.fxml");
        FXRouter.when("request", packageStr + "request.fxml");
        FXRouter.when("setting", packageStr + "setting.fxml");
        FXRouter.when("user_list", packageStr + "user_list.fxml");
        FXRouter.when("admin_profile", packageStr + "admin_profile.fxml");
        FXRouter.when("inappropriate", packageStr + "inappropriate.fxml");
        FXRouter.when("request_unban", packageStr + "request_unban.fxml");
        FXRouter.when("admin_profile_change_password", packageStr + "admin_profile_change_password.fxml");
    }

    public static void main(String[] args) {
        launch();
    }
}

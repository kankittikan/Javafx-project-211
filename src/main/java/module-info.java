module cs.ku {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens ku.cs to javafx.fxml;
    exports ku.cs;

    exports ku.cs.report.controllers;
    exports ku.cs.report.models;
    opens ku.cs.report.models to javafx.base, javafx.fxml;
    opens ku.cs.report.controllers to javafx.base, javafx.fxml;

}
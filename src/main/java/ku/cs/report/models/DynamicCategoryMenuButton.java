package ku.cs.report.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ku.cs.report.models.Category;
import ku.cs.report.models.Report;
import ku.cs.report.models.ReportList;
import ku.cs.report.services.CategoryListFileDataSource;
import ku.cs.report.services.DataSource;

public class DynamicCategoryMenuButton {
    public DynamicCategoryMenuButton(MenuButton categoryMenuButton, ReportList reportPendingList,ReportList reportNotComList,ReportList reportComList ,
                                     Tab pendingTab,Tab notComTab,Tab comTab,
                                     TableView<Report> tablePendingView,TableView<Report> tableNotComView,TableView<Report> tableComView) {

        DataSource<Category> dataSourceCategory = new CategoryListFileDataSource("data", "CategoryReport.csv");
        Category categoryList = dataSourceCategory.readData();


        for (String i : categoryList.getAllList()) {
            MenuItem menuItem = new MenuItem(i);
            menuItem.setOnAction(new EventHandler<>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    categoryMenuButton.setText(menuItem.getText());
                    if (pendingTab.isSelected()){
                        setSelectDynamicButton(categoryMenuButton,reportPendingList,tablePendingView);
                    } else if (notComTab.isSelected()){
                        setSelectDynamicButton(categoryMenuButton,reportNotComList,tableNotComView);
                    } else if (comTab.isSelected()) {
                        setSelectDynamicButton(categoryMenuButton,reportComList,tableComView);
                    }
                }
            });
            categoryMenuButton.getItems().add(menuItem);
        }
    }

    public DynamicCategoryMenuButton(MenuButton categoryMenuButton, Label text) {
        DataSource<Category> dataSourceCategory = new CategoryListFileDataSource("data", "CategoryReport.csv");
        Category categoryList = dataSourceCategory.readData();

        for (String i : categoryList.getAllList()) {
            MenuItem menuItem = new MenuItem(i);
            menuItem.setOnAction(new EventHandler<>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    categoryMenuButton.setText(menuItem.getText());
                    text.setText("เนื้อหาเฉพาะเรื่องที่ต้องการ: " + " " + categoryMenuButton.getText());
                }
            });
            categoryMenuButton.getItems().add(menuItem);
        }
    }

    public DynamicCategoryMenuButton(MenuButton categoryMenuButton) {
        DataSource<Category> dataSourceCategory = new CategoryListFileDataSource("data", "CategoryReport.csv");
        Category categoryList = dataSourceCategory.readData();

        for (String i : categoryList.getAllList()) {
            MenuItem menuItem = new MenuItem(i);
            ReportList buffer = new ReportList();
            menuItem.setOnAction(new EventHandler<>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    categoryMenuButton.setText(menuItem.getText());
                }
            });
            categoryMenuButton.getItems().add(menuItem);
        }
    }

    public void setSelectDynamicButton(MenuButton categoryMenuButton,ReportList reportList,TableView<Report> tableView){
        TableColumn<Report, String> categoryCol = new TableColumn<>("หมวดหมู่");
        TableColumn<Report, String> headCol = new TableColumn<>("ชื่อเรื่อง");
        TableColumn<Report, Integer> voteCol = new TableColumn<>("คะแนนโหวต");
        TableColumn<Report, String> timeCol = new TableColumn<>("วันที่ / เวลา");
        TableColumn<Report, String> feedbackCol = new TableColumn<>("การตอบกลับ");
        TableColumn<Report, String> statusCol = new TableColumn<>("สถานะ");

        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        headCol.setCellValueFactory(new PropertyValueFactory<>("head"));
        voteCol.setCellValueFactory(new PropertyValueFactory<>("vote"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        feedbackCol.setCellValueFactory(new PropertyValueFactory<>("feedback"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        ReportList reportSortCategory = reportList.sortByNameCategory(categoryMenuButton.getText(), reportList);
        tableView.getColumns().clear();
        ObservableList<Report> list = FXCollections.observableList(reportSortCategory.getAllList());
        tableView.setItems(list);
        tableView.getColumns().addAll(categoryCol, headCol, voteCol, timeCol, feedbackCol, statusCol);
    }

}


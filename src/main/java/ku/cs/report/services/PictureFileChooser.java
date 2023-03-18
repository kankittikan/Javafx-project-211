package ku.cs.report.services;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class PictureFileChooser {
    private String fileName = null;
    private String dirName = null;

    public PictureFileChooser(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg","*.jpeg"));
        fileChooser.setTitle("Open File");
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            fileName = file.getName();
            dirName = file.getParent();
        }
        System.out.println(fileName);
        System.out.println(dirName);
    }

    public String getFileName(){
        return fileName;
    }

    public String getDirName(){
        return dirName;
    }


}

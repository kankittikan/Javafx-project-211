package ku.cs.report.services;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageWrite {
    private String path;
    private String filename;
    private String directory;

    public ImageWrite(String path, String filename, String directory){
        this.path = path;
        this.filename = filename;
        this.directory = directory;
        checkFileIsExisted();
        imageToWrite(path, filename);
    }

    private void checkFileIsExisted() {
        File file = new File("data/" + directory);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    private void imageToWrite(String path, String filename) {
        BufferedImage bImage = null;
        String filePath = "data/" + directory;
        try {
            File initialImage = new File(path + "/"+ filename);
            bImage = ImageIO.read(initialImage);

            ImageIO.write(bImage, "jpeg", new File(filePath + "/" + filename));
            ImageIO.write(bImage, "jpg", new File(filePath + "/" + filename));
            ImageIO.write(bImage, "png", new File(filePath + "/" + filename));

            System.out.println("Images written successfully.");

        } catch (IOException e) {
            System.out.println("Picture error :" + e.getMessage());
        }

    }
}

package ku.cs.report.services;

import java.io.File;

public class DeleteFile {
    private File file;

    public DeleteFile(String name){
        file = new File(name);

        if (file.delete()) {
            System.out.println("file has been delete");
        }
        else System.out.println("not delete file");
    }
}

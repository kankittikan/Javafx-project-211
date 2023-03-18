package ku.cs.report.services;

import ku.cs.report.models.Category;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class CategoryListFileDataSource implements DataSource<Category>{

    private String directoryName;
    private String fileName;

    public CategoryListFileDataSource(String directoryName, String filename) {
        this.directoryName = directoryName;
        this.fileName = filename;
        checkFileIsExisted();
    }

    private void checkFileIsExisted() {
        File file = new File(directoryName);
        if ( ! file.exists()) {
            file.mkdirs();
        }

        String filePath = directoryName + File.separator + fileName;
        file = new File(filePath);
        if (! file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @Override
    public Category readData() {
        Category category = new Category();

        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);

        FileReader reader = null;
        BufferedReader buffer = null;

        Category categoryBuffer = new Category();

        try {
            reader = new FileReader(file, StandardCharsets.UTF_8);
            buffer = new BufferedReader(reader);

            String line = "";
            while ((line = buffer.readLine()) != null) {
                String[] data =line.split(",");
                categoryBuffer.newCategory(data[0]);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                buffer.close();
                reader.close();
            }catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return categoryBuffer;
    }

    @Override
    public void writeData(Category category) {
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);
        FileWriter writer = null;
        BufferedWriter buffer = null;
        int index = 0;

        try {
            writer = new FileWriter(file, StandardCharsets.UTF_8);
            buffer = new BufferedWriter(writer);

            for (String categoryBuffer : category.getAllList()) {
                String line = category.getCategory(index);
                buffer.append(line);
                buffer.newLine();
                index++;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                buffer.close();
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

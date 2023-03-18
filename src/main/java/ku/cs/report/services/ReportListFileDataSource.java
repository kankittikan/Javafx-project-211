package ku.cs.report.services;

import ku.cs.report.models.Report;
import ku.cs.report.models.ReportList;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ReportListFileDataSource implements DataSource<ReportList>{

    private String directoryName;
    private String fileName;

    public ReportListFileDataSource(String directoryName, String filename) {
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
    public ReportList readData() {
        ReportList reportList = new ReportList();

        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);

        FileReader reader = null;
        BufferedReader buffer = null;

        try {
            reader = new FileReader(file, StandardCharsets.UTF_8);
            buffer = new BufferedReader(reader);

            String line = "";
            while ((line = buffer.readLine()) != null) {
                String[] data =line.split(",");
                Report report = new Report(data[0].trim()
                        , data[1].trim()
                        , data[2].trim()
                        , data[3].trim()
                        , data[4].trim()
                        , Integer.parseInt(data[5].trim())
                        , data[6].trim()
                        , data[7].trim()
                        , data[8].trim()
                        , data[9].trim()
                        , data[10].trim());
                reportList.addReport(report);
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
        return reportList;
    }

    @Override
    public void writeData(ReportList reportList) {
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);
        FileWriter writer = null;
        BufferedWriter buffer = null;

        try {
            writer = new FileWriter(file, StandardCharsets.UTF_8);
            buffer = new BufferedWriter(writer);

            for (Report report : reportList.getAllList()) {
                String line = report.getCategory() + ","
                        + report.getHead() + ","
                        + report.getSubject() + ","
                        + report.getSubjectSpecific() + ","
                        + report.getUsernameReport() + ","
                        + report.getVote() + ","
                        + report.getTime() + ","
                        + report.getUsernameStaffManage() + ","
                        + report.getFeedback() + ","
                        + report.getStatus() + ","
                        + report.getPicture();
                buffer.append(line);
                buffer.newLine();
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

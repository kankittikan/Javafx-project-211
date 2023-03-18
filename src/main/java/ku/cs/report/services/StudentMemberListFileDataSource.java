package ku.cs.report.services;

import ku.cs.report.models.StudentMember;
import ku.cs.report.models.StudentMemberList;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class StudentMemberListFileDataSource implements DataSource<StudentMemberList> {

    private String directoryName;
    private String fileName;

    public StudentMemberListFileDataSource(String directoryName, String filename) {
        this.directoryName = directoryName;
        this.fileName = filename;
        checkFileIsExisted();
    }

    private void checkFileIsExisted() {
        File file = new File(directoryName);
        if (!file.exists()) {
            file.mkdirs();
        }

        String filePath = directoryName + File.separator + fileName;
        file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public StudentMemberList readData() {
        StudentMemberList studentMemberList = new StudentMemberList();

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
                StudentMember studentMember = new StudentMember(data[0].trim(),
                        data[1].trim(),
                        data[2].trim(),
                        data[3].trim(),
                        data[4].trim(),
                        data[5].trim(),
                        data[6].trim());

//                String[] data = line.split(",");
//                Member member = new Member(data[0].trim(), data[1].trim(),
//                        data[2].trim(), data[3].trim());
                studentMemberList.addMember(studentMember);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                buffer.close();
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return studentMemberList;
    }

    @Override
    public void writeData(StudentMemberList studentMemberList) {
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);
        FileWriter writer = null;
        BufferedWriter buffer = null;

        try {
            writer = new FileWriter(file, StandardCharsets.UTF_8);
            buffer = new BufferedWriter(writer);

//            String HeadLine = "Username,name,password,picture,type";
//            buffer.append(HeadLine);
//            buffer.newLine();

            for (StudentMember studentMember : studentMemberList.getAllList()) {
                String line = studentMember.getUsername() + ","
                        + studentMember.getName() + ","
                        + studentMember.getPassword() + ","
                        + studentMember.getPicture() + ","
                        + studentMember.getTimeLogin() + ","
                        + studentMember.getIsBaned() + ","
                        + studentMember.getVoted();
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

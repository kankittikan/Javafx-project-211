package ku.cs.report.services;

import ku.cs.report.models.Member;
import ku.cs.report.models.MemberList;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class AdminMemberListFileDataSource implements DataSource<MemberList> {

    private String directoryName;
    private String fileName;

    public AdminMemberListFileDataSource(String directoryName, String filename) {
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
    public MemberList readData() {
        MemberList memberList = new MemberList();

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
                Member Member = new Member(data[0].trim(),
                        data[1].trim(),
                        data[2].trim(),
                        data[3].trim(),
                        data[4].trim());
                
                memberList.addMember(Member);
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
        return memberList;
    }

    @Override
    public void writeData(MemberList memberList) {
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);
        FileWriter writer = null;
        BufferedWriter buffer = null;

        try {
            writer = new FileWriter(file, StandardCharsets.UTF_8);
            buffer = new BufferedWriter(writer);

            for (Member Member : memberList.getAllList()) {
                String line = Member.getUsername() + ","
                        + Member.getName() + ","
                        + Member.getPassword() + ","
                        + Member.getPicture() + ","
                        + Member.getTimeLogin();
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

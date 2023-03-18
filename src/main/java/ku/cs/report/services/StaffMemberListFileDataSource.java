package ku.cs.report.services;

import ku.cs.report.models.StaffMember;
import ku.cs.report.models.StaffMemberList;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class StaffMemberListFileDataSource implements DataSource<StaffMemberList>{
    private String directoryName;
    private String fileName;

    public StaffMemberListFileDataSource(String directoryName, String fileName){
        this.directoryName = directoryName;
        this.fileName = fileName;
        checkFileExisted();
    }

    private void checkFileExisted(){
        File file = new File(directoryName);
        if( ! file.exists()){
            file.mkdirs(); // add new directory
        }

        String filePath = directoryName + File.separator + fileName;
        file = new File(filePath);
        if( ! file.exists()){ // exists
            try {
                file.createNewFile(); // add new file
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public StaffMemberList readData() {
        StaffMemberList list = new StaffMemberList();
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);
        FileReader reader = null;
        BufferedReader buffer = null;

        try {
            reader = new FileReader(file, StandardCharsets.UTF_8);
            buffer = new BufferedReader(reader);

            String line = "";
            while (  (line = buffer.readLine()) != null ){
                String[] data = line.split(",");
                StaffMember staff = new StaffMember(
                        data[0].trim(),
                        data[1].trim(),
                        data[2].trim(),
                        data[3].trim(),
                        data[4].trim(),
                        data[5].trim()
                );
                list.addMember(staff);
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
        return list;
    }
    @Override
    public void writeData(StaffMemberList staffMemberList) {
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);

        FileWriter writer = null;
        BufferedWriter buffer = null;

        try {
            writer = new FileWriter(file, StandardCharsets.UTF_8);
            buffer = new BufferedWriter(writer);

            for(StaffMember staff : staffMemberList.getAllList()){
                String line = staff.getUsername() + ","
                        + staff.getName() + ","
                        + staff.getPassword() + ","
                        + staff.getPicture() + ","
                        + staff.getTimeLogin() + ","
                        + staff.getAgency();

                buffer.append(line);
                buffer.newLine();
            }

            buffer.close(); // close file for save during end program
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

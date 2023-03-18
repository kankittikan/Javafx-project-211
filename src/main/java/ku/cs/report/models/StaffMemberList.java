package ku.cs.report.models;

import java.util.ArrayList;
import java.util.Comparator;

public class StaffMemberList {
    private ArrayList<StaffMember> staffMembers;

    public StaffMemberList() {
        staffMembers = new ArrayList<>();
    }
    public void addMember(StaffMember staffMember) {
        staffMembers.add(staffMember);
    }

    public void addMember(int index, StaffMember staffMember) {
        staffMembers.add(index, staffMember);
    }

    public ArrayList<StaffMember> getAllList() {
        return staffMembers;
    }

    public StaffMember getList(int i) {
        return staffMembers.get(i);
    }

    public int getSize() {
        return staffMembers.size();
    }
    public void removeMember(int index) {staffMembers.remove(index);}

    public StaffMember findStaffMember(String username){
        for(int i=0; i<getSize(); i++){
            StaffMember check = getList(i);
            if(check.getUsername().equals(username)){
                return check;
            }
        }
        return null;
    }

    public StaffMember findStaffMemberWithPassword(String username, String password){
        for(int i=0; i<getSize(); i++){
            StaffMember check = getList(i);
            if(check.getUsername().equals(username) && check.getPassword().equals(password)){
                return check;
            }
        }
        return null;
    }

    public void changeAgencyName(String oldName, String newName){
        for(int i=0; i<getSize(); i++){
            StaffMember check = getList(i);
            if(check.getAgency().equals(oldName)){
                check.setAgency(newName);
            }
        }
    }

    public ArrayList<String> getAllUserName() {
        ArrayList<String> strings = new ArrayList<>();
        for (StaffMember staffMember : staffMembers) {
            strings.add(staffMember.getUsername());
        }
        return strings;
    }

    public ArrayList<String> timeAndUserNameAndAgencyToString() {
        ArrayList<String> stringArrayList = new ArrayList<>();
        for (StaffMember staffMember : staffMembers ) {
            String string = staffMember.getTimeLogin() + "          " + staffMember.getUsername() + "           " + staffMember.getAgency();
            stringArrayList.add(string);
        }
        return stringArrayList;
    }

    public void ascendingTimeStaff() {
        staffMembers.sort(new Comparator<>() {
            @Override
            public int compare(StaffMember o1, StaffMember o2) throws NullPointerException {
                return o1.getTimeLogin().compareTo(o2.getTimeLogin());
            }
        });
    }

    public void descendingTimeStaff() {
        staffMembers.sort(new Comparator<>() {
            @Override
            public int compare(StaffMember o1, StaffMember o2) throws NullPointerException {
                return o2.getTimeLogin().compareTo(o1.getTimeLogin());
            }
        });

        int sizeArrayList = getSize();
        int numNoTime = 0;
        for( int i = 0; i< sizeArrayList; i++){
            StaffMember staffMemberCheck = getList(i);
            if (staffMemberCheck.getTimeLogin().equals("No login time.")){
                addMember(staffMemberCheck);
                numNoTime++;
//                removeMember(0);
            }

        }

        for (int i = 0; i < numNoTime; i++) {
            removeMember(0);
        }
    }

//    public void ascendingTime() {
//        staffMembers.sort(new Comparator<>() {
//            @Override
//            public int compare(StaffMember o1, StaffMember o2) throws NullPointerException {
//                return o1.getTimeLogin().compareTo(o2.getTimeLogin());
//            }
//        });
//    }
}

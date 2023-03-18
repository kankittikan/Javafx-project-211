package ku.cs.report.models;

import ku.cs.report.services.DataSource;
import ku.cs.report.services.StudentMemberListFileDataSource;

import java.util.ArrayList;
import java.util.Comparator;

public class StudentMemberList {
    private final ArrayList<StudentMember> studentMemberArrayList;

    public StudentMemberList() {
        studentMemberArrayList = new ArrayList<>();
    }

    public void addMember(StudentMember studentMember) {
        studentMemberArrayList.add(studentMember);
    }

    public void addMember(int index, StudentMember studentMember) {
        studentMemberArrayList.add(index, studentMember);
    }

    public void removeMember(int index) {
        studentMemberArrayList.remove(index);
    }

    public ArrayList<StudentMember> getAllList() {
        return studentMemberArrayList;
    }

    public StudentMember getList(int i) {
        return studentMemberArrayList.get(i);
    }

    public int getSize() {
        return studentMemberArrayList.size();
    }

    public static StudentMember readStudentDataUser(String username) {
        DataSource<StudentMemberList> dataSource = new StudentMemberListFileDataSource("data", "StudentDataUser.csv");
        StudentMemberList studentMemberListInnit = dataSource.readData();
        StudentMember student = null;
        for (int index = 0; index < studentMemberListInnit.getSize(); index++) {
            if (studentMemberListInnit.getList(index).getUsername().equals(username)) {
                student = new StudentMember(studentMemberListInnit.getList(index).getUsername()
                        , studentMemberListInnit.getList(index).getName()
                        , studentMemberListInnit.getList(index).getPassword()
                        , studentMemberListInnit.getList(index).getPicture()
                        , studentMemberListInnit.getList(index).getTimeLogin()
                        , studentMemberListInnit.getList(index).getIsBaned()
                        , studentMemberListInnit.getList(index).getVoted());
            }
        }
        return student;
    }

    public int checkUsername(String username) {
        int i = 0;
        for (StudentMember studentMember : studentMemberArrayList) {
            if (studentMember.getUsername().equals(username)) {
                i = 1;
                break;
            }
        }
        return i;
    }

    public StudentMember findMember(String username) {
        for (int i = 0; i < getSize(); i++) {
            StudentMember check = getList(i);
            if (check.getUsername().equals(username)) {
                return check;
            }
        }
        return null;
    }

    public StudentMember findMemberWithPassword(String username, String password) {
        for (int i = 0; i < getSize(); i++) {
            StudentMember check = getList(i);
            if (check.getUsername().equals(username) && check.getPassword().equals(password)) {
                return check;
            }
        }
        return null;
    }

    public boolean findMemberData(StudentMember studentMemberTemp) {
        boolean temp = false;
        for (StudentMember studentMember : studentMemberArrayList) {
            if (studentMember.getUsername().equals(studentMemberTemp.getUsername()) &&
                    studentMember.getName().equals(studentMemberTemp.getName()) &&
                    studentMember.getPassword().equals(studentMemberTemp.getPassword())) {
                temp = true;
                break;
            }

        }
        return temp;
    }

    public int findIndexMember(StudentMember studentMemberTemp) {
        int index = 0;
        for (StudentMember studentMember : studentMemberArrayList) {
            if (studentMember.getUsername().equals(studentMemberTemp.getUsername()) &&
                    studentMember.getName().equals(studentMemberTemp.getName()) &&
                    studentMember.getPassword().equals(studentMemberTemp.getPassword())) {
                break;
            }
            index++;
        }
        return index;
    }

    public ArrayList<String> timeAndUserNameToString() {
        ArrayList<String> stringArrayList = new ArrayList<>();
        for (StudentMember studentMember : studentMemberArrayList) {
            String string = studentMember.getTimeLogin() + "     " + studentMember.getUsername();
            stringArrayList.add(string);
        }
        return stringArrayList;
    }

    public static StudentMember readAdminDataUser(String username) {
        DataSource<StudentMemberList> dataSource = new StudentMemberListFileDataSource("data", "AdminDataUser.csv");
        StudentMemberList studentMemberListInnit = dataSource.readData();
        StudentMember studentMember = null;
        for (int index = 0; index < studentMemberListInnit.getSize(); index++) {
            if (studentMemberListInnit.getList(index).getUsername().equals(username)) {
                studentMember = new StudentMember(studentMemberListInnit.getList(index).getUsername()
                        , studentMemberListInnit.getList(index).getName()
                        , studentMemberListInnit.getList(index).getPassword()
                        , studentMemberListInnit.getList(index).getPicture()
                        , studentMemberListInnit.getList(index).getTimeLogin()
                        , studentMemberListInnit.getList(index).getIsBaned()
                        , studentMemberListInnit.getList(index).getVoted());
            }
        }
        return studentMember;
    }

//    public void ascendingTimeMember() {
//        memberArrayList.sort(new Comparator<>() {
//            @Override
//            public int compare(Member o1, Member o2) throws NullPointerException {
//                return o1.getTimeLogin().compareTo(o2.getTimeLogin());
//            }
//        });
//    }

    public void descendingTimeMember() {
        studentMemberArrayList.sort(new Comparator<>() {
            @Override
            public int compare(StudentMember o1, StudentMember o2) throws NullPointerException {
                return o2.getTimeLogin().compareTo(o1.getTimeLogin());
            }
        });

        int sizeArrayList = getSize();
        int numNoTime = 0;
        for( int i = 0; i< sizeArrayList; i++){
            StudentMember studentMemberCheck = getList(i);
            if (studentMemberCheck.getTimeLogin().equals("No login time.")){
                addMember(studentMemberCheck);
                numNoTime++;
//                removeMember(0);
            }

        }

        for (int i = 0; i < numNoTime; i++) {
            removeMember(0);
        }

    }

//    public MemberList filterMember(Filterer<Member> filterer){
//        MemberList filtered = new MemberList();
//        for(Member member : memberArrayList){
//            if(filterer.filter(member)){
//                filtered.addMember(member);
//
//            }
//
//        }
//        return filtered;
//    }


}
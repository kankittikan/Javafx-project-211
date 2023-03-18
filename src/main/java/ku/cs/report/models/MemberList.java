package ku.cs.report.models;

import ku.cs.report.services.AdminMemberListFileDataSource;
import ku.cs.report.services.DataSource;

import java.util.ArrayList;
import java.util.Comparator;

public class MemberList {
    private final ArrayList<Member> MemberArrayList;

    public MemberList() {
        MemberArrayList = new ArrayList<>();
    }

    public void addMember(Member Member) {
        MemberArrayList.add(Member);
    }

    public void addMember(int index, Member Member) {
        MemberArrayList.add(index, Member);
    }

    public void removeMember(int index) {
        MemberArrayList.remove(index);
    }

    public ArrayList<Member> getAllList() {
        return MemberArrayList;
    }

    public Member getList(int i) {
        return MemberArrayList.get(i);
    }

    public int getSize() {
        return MemberArrayList.size();
    }

    public int checkUsername(String username) {
        int i = 0;
        for (Member Member : MemberArrayList) {
            if (Member.getUsername().equals(username)) {
                i = 1;
                break;
            }
        }
        return i;
    }

    public Member findMember(String username) {
        for (int i = 0; i < getSize(); i++) {
            Member check = getList(i);
            if (check.getUsername().equals(username)) {
                return check;
            }
        }
        return null;
    }

    public Member findMemberWithPassword(String username, String password) {
        for (int i = 0; i < getSize(); i++) {
            Member check = getList(i);
            if (check.getUsername().equals(username) && check.getPassword().equals(password)) {
                return check;
            }
        }
        return null;
    }

    public boolean findMemberData(Member MemberTemp) {
        boolean temp = false;
        for (Member Member : MemberArrayList) {
            if (Member.getUsername().equals(MemberTemp.getUsername()) &&
                    Member.getName().equals(MemberTemp.getName()) &&
                    Member.getPassword().equals(MemberTemp.getPassword())) {
                temp = true;
                break;
            }

        }
        return temp;
    }

    public int findIndexMember(Member MemberTemp) {
        int index = 0;
        for (Member Member : MemberArrayList) {
            if (Member.getUsername().equals(MemberTemp.getUsername()) &&
                    Member.getName().equals(MemberTemp.getName()) &&
                    Member.getPassword().equals(MemberTemp.getPassword())) {
                break;
            }
            index++;
        }
        return index;
    }

    public ArrayList<String> timeAndUserNameToString() {
        ArrayList<String> stringArrayList = new ArrayList<>();
        for (Member Member : MemberArrayList) {
            String string = Member.getTimeLogin() + "     " + Member.getUsername();
            stringArrayList.add(string);
        }
        return stringArrayList;
    }

    public static Member readAdminDataUser(String username) {
        DataSource<MemberList> dataSource = new AdminMemberListFileDataSource("data", "AdminDataUser.csv");
        MemberList memberListInnit = dataSource.readData();
        Member Member = null;
        for (int index = 0; index < memberListInnit.getSize(); index++) {
            if (memberListInnit.getList(index).getUsername().equals(username)) {
                Member = new Member(memberListInnit.getList(index).getUsername()
                        , memberListInnit.getList(index).getName()
                        , memberListInnit.getList(index).getPassword()
                        , memberListInnit.getList(index).getPicture()
                        , memberListInnit.getList(index).getTimeLogin());
            }
        }
        return Member;
    }

    public void descendingTimeMember() {
        MemberArrayList.sort(new Comparator<>() {
            @Override
            public int compare(Member o1, Member o2) throws NullPointerException {
                return o2.getTimeLogin().compareTo(o1.getTimeLogin());
            }
        });

        int sizeArrayList = getSize();
        int numNoTime = 0;
        for( int i = 0; i< sizeArrayList; i++){
            Member MemberCheck = getList(i);
            if (MemberCheck.getTimeLogin().equals("No login time.")){
                addMember(MemberCheck);
                numNoTime++;
//                removeMember(0);
            }

        }

        for (int i = 0; i < numNoTime; i++) {
            removeMember(0);
        }

    }
}
package ku.cs.report.models;

import java.util.ArrayList;

public class RequestUnbanList {
    private final ArrayList<RequestUnban> requestUnbanArrayList;

    public RequestUnbanList() {
        this.requestUnbanArrayList = new ArrayList<>();
    }

    public void addRequest(RequestUnban requestUnBan){
        requestUnbanArrayList.add(requestUnBan);
    }

    public void addRequest(StudentMember studentMember, String reason){
        boolean check = false;
        for(RequestUnban requestUnban : requestUnbanArrayList){
            if(requestUnban.getUsername().equals(studentMember.getUsername()) && requestUnban.getName().equals(studentMember.getName()) && requestUnban.getPassword().equals(studentMember.getPassword()) ){
                requestUnban.setReason(reason);
                check = true;
                break;
            }
        }
        if(!check){
            RequestUnban requestUnban = new RequestUnban(studentMember.getUsername(), studentMember.getName(), studentMember.getPassword(), reason);
            requestUnbanArrayList.add(requestUnban);
        }
    }

    public void removeRequest(String username){
//        arrayList.remove(index);
        for (int i = 0; i < getSize(); i++){
            RequestUnban check = getList(i);
            if (username.equals(check.getUsername())){
                requestUnbanArrayList.remove(i);
                break;
            }
        }
    }

    public ArrayList<RequestUnban> getAllList(){
        return requestUnbanArrayList;
    }

    public RequestUnban getList(int i){
        return requestUnbanArrayList.get(i);
    }

    public int getSize(){
        return requestUnbanArrayList.size();
    }

    public RequestUnban findRequestObject(String username){
        for(RequestUnban requestUnBan : requestUnbanArrayList){
            if(requestUnBan.getUsername().equals(username)){
                return requestUnBan;
            }
        }
        return null;
    }

    public ArrayList<String> getNameAndUsername() {
        ArrayList<String> requestArrayList = new ArrayList<>();
        for (RequestUnban requestUnban : requestUnbanArrayList ) {
            String nameAndUsername = requestUnban.getName() + "          " + requestUnban.getUsername() + "           " ;
            requestArrayList.add(nameAndUsername);
        }
        return requestArrayList;
    }

    public RequestUnban findRequest(String head){
        for(RequestUnban requestUnban : requestUnbanArrayList){
            if(requestUnban.getName().equals(head)){
                return requestUnban;
            }
        }
        return null;
    }
}

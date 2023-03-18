package ku.cs.report.models;

public class StudentMember extends Member {
    private String isBaned;
    private String voted;

    public StudentMember(String username, String name, String password) {
        super(username, name, password);
        isBaned = "No";
        this.voted = "No";
    }

    public StudentMember(String username, String name, String password, String picture) {
        super(username, name, password, picture);
        isBaned = "No";
        this.voted = "No";
    }

    public StudentMember(String username, String name, String password, String picture, String timeLogin) {
        super(username, name, password, picture, timeLogin);
        isBaned = "No";
        this.voted = "No";
    }

    public StudentMember(String username, String name, String password, String picture, String timeLogin, String isBaned, String voted) {
        super(username, name, password, picture, timeLogin);
        this.isBaned = isBaned;
        this.voted = voted;
    }

    public void setBan() {
        isBaned = "Baned";
    }

    public void setUnBan() {
        isBaned = "No";
    }

    public String getIsBaned() {
        return isBaned;
    }

    public String getVoted() {return  voted;}

    public void setVoted(String voted) {
        this.voted = voted;
    }
}

package ku.cs.report.models;

public class Member {
    private String username;
    private String name;
    private String password;
    private String picture;
    private String timeLogin;

    public Member(String username, String name, String password) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.picture = "UnknownProfile.jpg";
        this.timeLogin = "No login time.";
    }

    public Member(String username, String name, String password, String picture) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.picture = picture;
        if(picture == null) this.picture = "UnknownProfile.jpg";
        this.timeLogin = "No login time.";
    }

    public Member(String username, String name, String password, String picture, String timeLogin) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.picture = picture;
        this.timeLogin = timeLogin;
    }

    public Member(String username, String name, String password, String picture, String timeLogin, String isBaned, String voted) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.picture = picture;
        this.timeLogin = timeLogin;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getPicture() {
        return picture;
    }

    public String getTimeLogin() {
        return timeLogin;
    }

    public void setTimeLogin(String timeLogin) {
        this.timeLogin = timeLogin;
    }

    public void setPicture(String picture) {
        if(picture == null) this.picture = "UnknownProfile.jpg";
        else this.picture = picture;
    }

    public void changePassword(String username, String password, String new_password) {
        if (this.username.equals(username) && this.password.equals(password)) {
            this.password = new_password;
        }
    }

    @Override
    public String toString() {
        return "Member{" +
                "username='" + username + '\'' +
                ", timeLogin='" + timeLogin + '\'' +
                '}';
    }
}

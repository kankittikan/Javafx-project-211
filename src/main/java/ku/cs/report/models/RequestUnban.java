package ku.cs.report.models;

public class RequestUnban {
    private String username;
    private String name;
    private String password;
    private String reason;

    public RequestUnban(String username, String name, String password, String reason) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.reason = reason;
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

    public String getReason() {
        return reason;
    }

    void setReason(String reason){
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "RequestUnban{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

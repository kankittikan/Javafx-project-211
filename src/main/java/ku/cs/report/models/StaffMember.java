package ku.cs.report.models;

public class StaffMember extends Member {
    private String agency;

    public StaffMember(String username, String name, String password, String agency) {
        super(username, name, password);
        this.agency = agency;
    }

    public StaffMember(String username, String name, String password, String picture, String agency) {
        super(username, name, password, picture);
        this.agency = agency;
    }

    public StaffMember(String username, String name, String password, String picture, String timeLogin, String agency){
        super(username, name, password, picture, timeLogin);
        this.agency = agency;
    }
    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    @Override
    public String toString() {
        return "StaffMember => "
                +"Username: "+ this.getUsername() + " | "
                +"Name: "+ this.getName() + " | "
                +"Password: "+ this.getPassword() + " | "
                +"Picture: "+ this.getPicture() + " | "
                +"Agency: "+ this.getAgency();
    }
}

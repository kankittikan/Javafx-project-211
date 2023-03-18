package ku.cs.report.models;

public class Appearance {
    private String data;
    private String data2;

    public Appearance(String data, String data2) {
        this.data = data;
        this.data2 = data2;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData2() {
        return data2;
    }

    public void setData2(String data2) {
        this.data2 = data2;
    }
}

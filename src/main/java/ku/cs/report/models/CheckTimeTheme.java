package ku.cs.report.models;

import ku.cs.report.models.Appearance;
import ku.cs.report.services.AppearanceDataSource;
import ku.cs.report.services.DataSource;

import java.time.LocalTime;

public class CheckTimeTheme {
    public static void check() {
        DataSource<Appearance> themeDataSource = new AppearanceDataSource("data/appearance", "theme.csv");
        Appearance appearanceTheme;
        appearanceTheme = themeDataSource.readData();
        if(appearanceTheme.getData2().equals("System")) {
            if(LocalTime.now().getHour() > 18 || LocalTime.now().getHour() < 6) {
                appearanceTheme.setData("DarkTheme");
                themeDataSource.writeData(appearanceTheme);
            }
            else {
                appearanceTheme.setData("BrightTheme");
                themeDataSource.writeData(appearanceTheme);
            }
        }
    }
}

package pl.kuchmaczpogoda.model;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class Cities {

    public static List<String> cities = new ArrayList<String>() {{
        add("Krakow");
        add("Warszawa");
        add("Londyn");
    }};
    public static ArrayList<String> timeZones = new ArrayList<>() {{
        for (String zoneId : ZoneId.getAvailableZoneIds()) {
            add(zoneId);
        }
    }};

    public static List<String> getTimeZones() {
        return timeZones;
    }

    public static List<String> getCities() {
        return cities;
    }
}

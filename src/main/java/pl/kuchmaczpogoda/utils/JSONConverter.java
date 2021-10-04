package pl.kuchmaczpogoda.utils;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONConverter {
    public static JSONArray convertStringObjectToJSONArrayWithWeatherData(String str) {
        JSONObject jsonObject = new JSONObject(str);
        return jsonObject.getJSONArray("list");
    }
}


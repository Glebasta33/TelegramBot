import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class Weather {

    public static String lang = "ru";

    public static String getWeather(String message) throws IOException {

        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + message + "&units=metric&lang=" + lang + "&appid=" + TokenStorage.API_TOKEN_OPEN_WEATHER);
        Scanner in = new Scanner((InputStream) url.getContent());
        StringBuilder builder = new StringBuilder();
        while (in.hasNext()) {
            builder.append(in.nextLine());
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject(builder.toString());
        String name = jsonObject.getString("name");

        JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
        double temp = jsonObjectMain.getDouble("temp");
        double humidity = jsonObjectMain.getDouble("humidity");

        String main = "Погода";
        JSONArray getArray = jsonObject.getJSONArray("weather");
        for (int i = 0; i < getArray.length(); i++) {
            JSONObject obj = getArray.getJSONObject(i);
            main = (String) obj.get("main");
        }

        String NAME, TEMP, HUMIDITY, MAIN;
        if (lang.equals("ru")) {
            NAME = "Город: ";
            TEMP = "\nТемпература: ";
            HUMIDITY = "\nВлажность: ";
            MAIN = "\nПогода: ";
        } else {
            NAME = "City: ";
            TEMP = "\nTemperature: ";
            HUMIDITY = "\nHumidity: ";
            MAIN = "\nWeather: ";
        }

        String resultJSON = NAME + name +
                TEMP + temp + " C" +
                HUMIDITY + humidity + "%" +
                MAIN + main;

        return resultJSON;
    }
}

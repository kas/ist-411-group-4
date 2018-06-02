package com.mycompany.l03;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class L03 {

    public static void main(String[] args) throws MalformedURLException, IOException {
        System.out.println("Here is the 7 day weather forcast for Penn State:");

        String coordinates = "40.7982,-77.8599"; // Penn State coordinates
        String stringUrl = String.format("https://api.weather.gov/points/%s/forecast", coordinates);

        URL url = new URL(stringUrl);
        URLConnection urlConnection = url.openConnection();
        urlConnection.connect();

        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(new InputStreamReader((InputStream) urlConnection.getContent()));

        JsonObject jsonObject = jsonElement.getAsJsonObject();
        
        for(int i=0;i<14;i++){
            JsonObject period = jsonObject.get("properties").getAsJsonObject().get("periods").getAsJsonArray().get(i)
                .getAsJsonObject();

        String name = period.get("name").getAsString();
        String temperature = period.get("temperature").getAsString();
        String temperatureUnit = period.get("temperatureUnit").getAsString();

        System.out.println(String.format("%s at coordinates %s: %s%s", name, coordinates, temperature, temperatureUnit));
        
    }

        // String jsonString = jsonObject.toString();
        
    }
}

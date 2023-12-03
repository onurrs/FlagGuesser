package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestAPI {

    private JSONArray jsonArray;

    RestAPI(String category){
        try {
            // REST API endpoint'i
            URL url = new URL("https://restcountries.com/v3.1/"+category);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // İstek yapıp cevabı almak
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Cevabı JSON olarak işlemek
            jsonArray = new JSONArray(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Country RandomCountry(){
        if (jsonArray != null) {
            int randomIndex = (int) (Math.random() * jsonArray.length());
            JSONObject country = jsonArray.getJSONObject(randomIndex);
            String countryName = country.getJSONObject("name").getString("common");
            String flagUrl = country.getJSONObject("flags").getString("png");
            return new Country(countryName, flagUrl);
        }else {
            return new Country("Connection Error",null);
        }
    }

    String RandomCountryName(){
        if (jsonArray != null){
            int randomIndex = (int) (Math.random() * jsonArray.length());
            JSONObject country = jsonArray.getJSONObject(randomIndex);
            return country.getJSONObject("name").getString("common");
        }else {
            return "Connection Error";
        }
    }

}

package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public class Sports {
    public static ArrayList<String> getSports () throws IOException, ParseException {
        ArrayList<String> sportIds = new ArrayList<>();
        ArrayList<String> allSportIds = new ArrayList<>();

        String path = "https://1xbet.es/LineFeed/GetSportsShortZip?lng=es&tf=2200000&tz=3&country=78&partner=229&virtualSports=true";
        JSONObject jsonObject = SendRequest.getData(path);
        JSONArray jsonArray = (JSONArray) jsonObject.get("Value");
        for (int i = 0; i < jsonArray.size(); i++) {
            jsonObject = (JSONObject) jsonArray.get(i);
            String id = jsonObject.get("I").toString();
            allSportIds.add(id);
        }
        for (String id: allSportIds) {
            if (!sportIds.contains(id)) sportIds.add(id);
        }
        return sportIds;
    }
}

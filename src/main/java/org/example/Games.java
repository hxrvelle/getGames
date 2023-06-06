package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public class Games {
    public static ArrayList<String> getGames (ArrayList<String> sportIds) throws IOException, ParseException {
        ArrayList<String> gameIds = new ArrayList<>();
        String sportId;
        for (int i = 0; i < sportIds.size(); i++) {
            sportId = sportIds.get(i);
            String path = "https://1xbet.es/LineFeed/Get1x2_VZip?sports=" + sportId + "&count=1000&lng=es&tf=2200000&tz=3&mode=4&country=78&partner=229&getEmpty=true";
            JSONObject jsonObject = SendRequest.getData(path);
            JSONArray jsonArray = (JSONArray) jsonObject.get("Value");
            for (int j = 0; j < jsonArray.size(); j++) {
                jsonObject = (JSONObject) jsonArray.get(j);
                String id = jsonObject.get("CI").toString();
                gameIds.add(id);
            }
        }
        return gameIds;
    }
}
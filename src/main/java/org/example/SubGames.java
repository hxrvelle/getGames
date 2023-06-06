package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public class SubGames {
    public static ArrayList<String> getSubGames (ArrayList<String> gameIds) throws IOException, ParseException {
        ArrayList<String> subGameIds = new ArrayList<>();
        String gameId;
        for (int i = 0; i < gameIds.size(); i++) {
            gameId = gameIds.get(i).toString();
            String path = "https://1xbet.es/LineFeed/GetGameZip?id=" + gameId + "&lng=es&cfview=0&isSubGames=true&GroupEvents=true&allEventsGroupSubGames=true&countevents=250&partner=229";
            JSONObject jsonObject = SendRequest.getData(path);
            if (!jsonObject.containsValue("¡El partido no se encuentra en Deportes!")) {
                jsonObject = (JSONObject) jsonObject.get("Value");
                if (jsonObject.containsKey("SG")) {
                    JSONArray jsonArray = (JSONArray) jsonObject.get("SG");
                    for (int j = 0; j < jsonArray.size(); j++) {
                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(j);
                        String subGameId = jsonObject1.get("CI").toString();
                        subGameIds.add(subGameId);
                    }
                }
            }
        }
        return subGameIds;
    }
}
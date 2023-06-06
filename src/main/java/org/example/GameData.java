package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public class GameData {
    public static ArrayList<String> getGameData (ArrayList<String> gameIds) throws IOException, ParseException {
        ArrayList<String> gameData = new ArrayList<>();
        //Игры
        for (int i = 0; i < gameIds.size(); i++) {
            String gameId = gameIds.get(i);
            String path = "https://1xbet.es/LineFeed/GetGameZip?id=" + gameId + "&lng=es&cfview=0&isSubGames=true&GroupEvents=true&allEventsGroupSubGames=true&countevents=250&partner=229";
            JSONObject jsonObject = SendRequest.getData(path);
            if (!jsonObject.containsValue("¡El partido no se encuentra en Deportes!")) {
                jsonObject = (JSONObject) jsonObject.get("Value");
                // Команды
                String team1 = jsonObject.get("O1").toString();
                String team2;
                if (jsonObject.get("O2") != null) team2 = jsonObject.get("O2").toString();
                else team2 = "";
                String sportName = jsonObject.get("SN").toString();
                String champName = jsonObject.get("L").toString();
                if (team1.matches(".*\\p{InCyrillic}.*") || team2.matches(".*\\p{InCyrillic}.*")) {
                    String data = "Команды: " + sportName + " - " + champName + " - " + team1 + " & " + team2;
                    gameData.add(data + "\n");
                }
                // Ставки
                if (jsonObject.containsKey("GE")) {
                    JSONArray jsonArray = (JSONArray) jsonObject.get("GE");
                    for (int j = 0; j < jsonArray.size(); j++) {
                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(j);
                        JSONArray jsonArray1 = (JSONArray) jsonObject1.get("E");
                        for (int k = 0; k < jsonArray1.size(); k++) {
                            JSONArray jsonArray2 = (JSONArray) jsonArray1.get(k);
                            for (int l = 0; l < jsonArray2.size(); l++) {
                                JSONObject jsonObject2 = (JSONObject) jsonArray2.get(l);
                                if (jsonObject2.containsKey("PL")) {
                                    jsonObject2 = (JSONObject) jsonObject2.get("PL");
                                    String bet = jsonObject2.get("N").toString();
                                    if (bet.matches(".*\\p{InCyrillic}.*")) {
                                        String betData = "Ставки: " + sportName + " - " + champName + " - " + team1 + " & " + team2 + " - " + bet;
                                        gameData.add(betData + "\n");
                                    }
                                }
                            }
                        }
                    }
                }
                // Осадки
                if (jsonObject.toString().contains("Осадки, %")) {
                    String precipitacion = "Осадки: " + sportName + " - " + champName + " - " + team1 + " & " + team2;
                    gameData.add(precipitacion + "\n");
                }
            }
        }
        return gameData;
    }
}
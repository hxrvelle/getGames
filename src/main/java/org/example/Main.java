package org.example;

import org.json.simple.parser.ParseException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;

public class Main {
    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //Получение даты
                    Calendar calendar = Calendar.getInstance();
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    int month = calendar.get(Calendar.MONTH);
                    String date = day + "-" + month;
                    // Получение id спортов
                    ArrayList<String> sportIds = Sports.getSports();
                    System.out.println("Number of sports: " + sportIds.size());
                    // Получение id игр и подыгр
                    ArrayList<String> gameIds = Games.getGames(sportIds);
                    System.out.println("Number of games: " + gameIds.size());
                    ArrayList<String> subGameIds = SubGames.getSubGames(gameIds);
                    System.out.println("Number of subgames: " + subGameIds.size());
                    gameIds.addAll(subGameIds);
                    // Получение данных игр
                    ArrayList<String> gameData = GameData.getGameData(gameIds);
                    PrintWriter printWriter = new PrintWriter(new FileOutputStream("/Users/hxrvelle/Desktop/RU/ru-lang-" + date + ".txt", true));
                    for (int i = 0; i < gameData.size(); i++) {
                        String data = gameData.get(i);
                        printWriter.write(data + "\n");
                    }
                    printWriter.close();
                } catch (IOException | ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();
    }
}
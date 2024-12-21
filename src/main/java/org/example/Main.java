package org.example;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try {
            List<Game> games = FileLoader.loadGames("src/main/java/org/example/Игры.csv");
            DataBase.connectDB();
            //DataBase.createTables(); // создание таблиц в бд
            //DataBase.insertGames(games); // загрузка в бд данных из CSV
            Map<String, Double> averageSalesByPlatform = DataBase.getAverageGlobalSalesByPlatform();
            SwingUtilities.invokeLater(() -> {
                Graph graph = new Graph(averageSalesByPlatform);
                graph.setVisible(true);
            });
            DataBase.getHighestEuSalesIn2000();
            DataBase.getHighestJpSalesSportsGame();
            //List<Game> games1 = DataBase.getAllGames();
            //games1.forEach(System.out::println);  // вывод всех данных из бд
            DataBase.closeDB();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}

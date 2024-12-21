package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataBase {
    private static Connection connection;
    private static Statement statement;

    public static void connectDB() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:GamesDB.db");
        statement = connection.createStatement();
    }

    public static void createTables() throws SQLException {
        String sqlGame = "CREATE TABLE IF NOT EXISTS Game (" +
                "rank INTEGER PRIMARY KEY, " +
                "name TEXT NOT NULL, " +
                "platform TEXT NOT NULL, " +
                "year TEXT NOT NULL, " +
                "genre TEXT NOT NULL, " +
                "publisher_name TEXT NOT NULL)";
        statement.execute(sqlGame);

        String sqlSales = "CREATE TABLE IF NOT EXISTS Sales (" +
                "game_rank INTEGER, " +
                "na_sales REAL NOT NULL, " +
                "eu_sales REAL NOT NULL, " +
                "jp_sales REAL NOT NULL, " +
                "other_sales REAL NOT NULL, " +
                "global_sales REAL NOT NULL, " +
                "FOREIGN KEY(game_rank) REFERENCES Game(rank))";
        statement.execute(sqlSales);
    }

    public static void insertGame(Game game) throws SQLException {
        String sql = "INSERT INTO Game (rank, name, platform, year, genre, publisher_name) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, game.getRank());
            preparedStatement.setString(2, game.getName());
            preparedStatement.setString(3, game.getPlatform());
            preparedStatement.setString(4, game.getYear());
            preparedStatement.setString(5, game.getGenre());
            preparedStatement.setString(6, game.getPublisher());
            preparedStatement.executeUpdate();
        }
    }

    public static void insertSales(Game game) throws SQLException {
        String sql = "INSERT INTO Sales (game_rank, na_sales, eu_sales, jp_sales, other_sales, global_sales) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, game.getRank());
            preparedStatement.setDouble(2, game.getNaSales());
            preparedStatement.setDouble(3, game.getEuSales());
            preparedStatement.setDouble(4, game.getJpSales());
            preparedStatement.setDouble(5, game.getOtherSales());
            preparedStatement.setDouble(6, game.getGlobalSales());
            preparedStatement.executeUpdate();
        }
    }

    public static void closeDB() {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertGames(List<Game> games) throws SQLException {
        for (Game game : games) {
            insertGame(game);
            insertSales(game);
        }
    }

    public static Game mapToGame(ResultSet resultSet) throws SQLException {
        Game game = new Game();
        game.setRank(resultSet.getInt("rank"));
        game.setName(resultSet.getString("name"));
        game.setPlatform(resultSet.getString("platform"));
        game.setYear(resultSet.getString("year"));
        game.setGenre(resultSet.getString("genre"));
        game.setPublisher(resultSet.getString("publisher_name"));
        game.setNaSales(resultSet.getDouble("na_sales"));
        game.setEuSales(resultSet.getDouble("eu_sales"));
        game.setJpSales(resultSet.getDouble("jp_sales"));
        game.setOtherSales(resultSet.getDouble("other_sales"));
        game.setGlobalSales(resultSet.getDouble("global_sales"));
        return game;
    }

    public static List<Game> getAllGames() throws SQLException {
        List<Game> games = new ArrayList<>();
        String sql = "SELECT g.rank, g.name, g.platform, g.year, g.genre, g.publisher_name, " +
                "s.na_sales, s.eu_sales, s.jp_sales, s.other_sales, s.global_sales " +
                "FROM Game g " +
                "JOIN Sales s ON g.rank = s.game_rank";
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            games.add(mapToGame(resultSet));
        }

        return games;
    }



    // 1. Построение графика по средним показателям глобальных продаж по платформам
    public static Map<String, Double> getAverageGlobalSalesByPlatform() throws SQLException {
        String sql = "SELECT g.platform, AVG(s.global_sales) AS avg_global_sales " +
                "FROM Game g JOIN Sales s ON g.rank = s.game_rank " +
                "GROUP BY g.platform";
        ResultSet resultSet = statement.executeQuery(sql);

        Map<String, Double> platformSales = new HashMap<>();
        while (resultSet.next()) {
            String platform = resultSet.getString("platform");
            double avgGlobalSales = resultSet.getDouble("avg_global_sales");
            platformSales.put(platform, avgGlobalSales);
        }
        return platformSales;
    }

    // 2. Игра с самым высоким показателем продаж в Европе за 2000 год
    public static void getHighestEuSalesIn2000() throws SQLException {
        String sql = "SELECT g.name, MAX(s.eu_sales) AS max_eu_sales " +
                "FROM Game g JOIN Sales s ON g.rank = s.game_rank " +
                "WHERE g.year = '2000' " +
                "GROUP BY g.name " +
                "ORDER BY max_eu_sales DESC LIMIT 1";
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            String name = resultSet.getString("name");
            double maxEuSales = resultSet.getDouble("max_eu_sales");
            System.out.println("Игра с самым высоким показателем продаж в Европе за 2000 год: " + name + ", с продажами " + maxEuSales);
        }
    }

    // 3. Игра с самым высоким показателем продаж в Японии с 2000 по 2006 год в жанре спортивных игр
    public static void getHighestJpSalesSportsGame() throws SQLException {
        String sql = "SELECT g.name, MAX(s.jp_sales) AS max_jp_sales " +
                "FROM Game g JOIN Sales s ON g.rank = s.game_rank " +
                "WHERE g.year BETWEEN '2000' AND '2006' AND g.genre = 'Sports' " +
                "GROUP BY g.name " +
                "ORDER BY max_jp_sales DESC LIMIT 1";
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            String name = resultSet.getString("name");
            double maxJpSales = resultSet.getDouble("max_jp_sales");
            System.out.println("Игра с самым высоким показателем продаж в Японии с 2000 по 2006 год в жанре спортивных игр: " + name + ", с продажами " + maxJpSales);
        }
    }
}



package com.epam.carsharing;

import java.sql.*;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String url = "jdbc:mysql://localhost:3306/car_sharing";
        Properties prop = new Properties();
        prop.put("user", "root");
        prop.put("password", "AlexDasha8921_qwe");
        try (Connection connection = DriverManager.getConnection(url, prop);
             Statement statement = connection.createStatement()) {
//            String sql="INSERT INTO users (lastname,phone) VALUES ('преступник',5424)";
//            statement.execute(sql);
//            System.out.println("ok");
            String sql = "SELECT  id_cars,color,model FROM cars";
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    String color = resultSet.getString(2);
                    String model = resultSet.getString("model");
                    System.out.println(id + " " + color + " " + model);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
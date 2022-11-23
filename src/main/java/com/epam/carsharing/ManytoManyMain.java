package com.epam.carsharing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ManytoManyMain {
    public static void main(String[] args) {
        ConnectionPool connectionPool=ConnectionPool.getInstance();
        Connection connection= connectionPool.getConnection();
        try(
            Statement statement=connection.createStatement())
        {
            String sql="INSERT INTO orders (id_car,id_client) VALUES(113,22)";
            statement.execute(sql);
            System.out.println("ok");
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            connectionPool.returnConnection(connection);
        }
    }
}

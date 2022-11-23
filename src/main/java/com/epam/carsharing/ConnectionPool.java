package com.epam.carsharing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static Lock locker = new ReentrantLock();
    private static ConnectionPool portInstance;
    private static final int POOL_SIZE = 4;
    private BlockingQueue<Connection> queue;

    static {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            e.printStackTrace(); //log  todo
        }
    }
    private ConnectionPool() {
        String url = "jdbc:mysql://localhost:3306/car_sharing";
        Properties prop = new Properties();
        prop.put("user", "root");
        prop.put("password", "AlexDasha8921_qwe");
        prop.put("autoReconnect", "true");
        prop.put("characterEncoding", "UTF-8");
        prop.put("useUnicode", "true");
        prop.put("useSSL", "true");
        prop.put("useJDBCCompliantTimezoneShift", "true");
        prop.put("useLegacyDatetimeCode", "false");
        prop.put("serverTimezone", "UTC");
        prop.put("serverSslCert", "classpath:server.crt");
        this.queue = new LinkedBlockingQueue<>();
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                queue.offer(DriverManager.getConnection(url, prop));
            } catch (SQLException e) {
                e.printStackTrace(); //log todo
            }
        }
    }

    public static ConnectionPool getInstance() {
        if (portInstance == null) {

            try {
                locker.lock();
                if (portInstance == null) {
                    portInstance = new ConnectionPool();
                }
            } finally {
                locker.unlock();
            }
        }
        return portInstance;
    }

    public Connection getConnection()  {
        Connection connection = null;
        try {
            connection = queue.take();
        } catch (InterruptedException e) {
            // todo
        }
        return connection;
    }

    public void returnConnection(Connection connection) {
        try {
            queue.put(connection);
        } catch (InterruptedException e) {
            //todo
        }
    }
    // deregister drivers todo
    public void destroyPool() {

    }
}

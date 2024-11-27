package com.example.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;

public class MyJDBC {
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/sdaproj";
    private static final String DB_USERNAME = "INAM";
    private static final String DB_PASSWORD = "bababoi8N";

    // Singleton connection (optional for efficiency)
    private static Connection connection;


    /**
     * Establish and return a database connection.
     *
     * @return Connection object
     * @throws Exception if unable to connect
     */
    public static Connection getConnection() throws Exception {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        }
        return connection;
    }
}

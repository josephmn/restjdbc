package com.aws.restjdbc.util;

import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class MysqlConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/crudjdbc";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

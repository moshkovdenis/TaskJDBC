package jm.task.core.jdbc.util;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public class ConnectionUtil {

    private static final String URL = "jdbc:mysql://localhost:3306/jm?useSSL=false&serverTimezone=GMT";
    private static final String USER = "root";
    private static final String PSSWD = "root";

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PSSWD);
        } catch (SQLException e) {
            log.error("Error while connecting to DB");
            throw new SQLException(e);
        }
    }
}

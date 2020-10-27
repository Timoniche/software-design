package ru.akirakozov.sd.refactoring.database;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ControllerDB {
    private static final String DATABASE = "jdbc:sqlite:test.db";

    public static final String SQL_TEST_INPUT =
            "INSERT INTO PRODUCT(NAME, PRICE) " +
            "VALUES ('iphone', 200), ('table', 250), ('guitar', 300), ('chair', 350)";
    public static final String DROP_PRODUCT =
            "DROP TABLE IF EXISTS PRODUCT";
    public static final String CREATE_PRODUCT =
            "CREATE TABLE IF NOT EXISTS PRODUCT" +
            "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            " NAME           TEXT    NOT NULL, " +
            " PRICE          INT     NOT NULL)";

    public static Statement createStatement() throws SQLException {
        return DriverManager.getConnection(DATABASE).createStatement();
    }

    public static void createDatabase() throws SQLException {
        try (Statement stmt = createStatement()) {
            stmt.executeUpdate(CREATE_PRODUCT);
        }
    }
}

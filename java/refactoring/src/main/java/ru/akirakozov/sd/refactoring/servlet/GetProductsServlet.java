package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import static ru.akirakozov.sd.refactoring.database.ControllerDB.getConnection;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends AbstractServlet {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException, SQLException {
        try (Connection c = getConnection()) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT");
            ArrayList<String> log = new ArrayList<>();
            printResultSet(rs, log);
            logHttp(log, response);
            rs.close();
            stmt.close();
        }
    }

    static void printResultSet(ResultSet rs, ArrayList<String> log)
            throws SQLException {
        while (rs.next()) {
            String name = rs.getString("name");
            int price = rs.getInt("price");
            log.add(name + "\t" + price + "</br>");
        }
    }
}

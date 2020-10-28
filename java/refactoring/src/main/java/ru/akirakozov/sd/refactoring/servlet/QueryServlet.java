package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import static ru.akirakozov.sd.refactoring.database.ControllerDB.getConnection;

/**
 * @author akirakozov
 */
public class QueryServlet extends AbstractServlet {
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        switch (command) {
            case "max":
                try {
                    try (Connection c = getConnection()) {
                        Statement stmt = c.createStatement();
                        ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1");
                        ArrayList<String> log = new ArrayList<>();
                        log.add("<h1>Product with max price: </h1>");
                        GetProductsServlet.printResultSet(rs, log);
                        logHttp(log, response);
                        rs.close();
                        stmt.close();
                    }

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                break;
            case "min":
                try {
                    try (Connection c = getConnection()) {
                        Statement stmt = c.createStatement();
                        ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1");
                        ArrayList<String> log = new ArrayList<>();
                        log.add("<h1>Product with min price: </h1>");
                        GetProductsServlet.printResultSet(rs, log);
                        logHttp(log, response);
                        rs.close();
                        stmt.close();
                    }

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                break;
            case "sum":
                try {
                    try (Connection c = getConnection()) {
                        Statement stmt = c.createStatement();
                        ResultSet rs = stmt.executeQuery("SELECT SUM(price) FROM PRODUCT");
                        ArrayList<String> log = new ArrayList<>();
                        log.add("Summary price: ");
                        if (rs.next()) {
                            log.add(rs.getString(1));
                        }
                        logHttp(log, response);
                        rs.close();
                        stmt.close();
                    }

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                break;
            case "count":
                try {
                    try (Connection c = getConnection()) {
                        Statement stmt = c.createStatement();
                        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM PRODUCT");
                        ArrayList<String> log = new ArrayList<>();
                        log.add("Number of products: ");
                        if (rs.next()) {
                            log.add(rs.getString(1));
                        }
                        logHttp(log, response);
                        rs.close();
                        stmt.close();
                    }

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                break;
            default:
                response.getWriter().println("Unknown command: " + command);
        }
    }

}

package ru.akirakozov.sd.refactoring.servlet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import static ru.akirakozov.sd.refactoring.database.ControllerDB.*;

public class AddProductServletTest {
    private final StringWriter writer = new StringWriter();

    @Mock
    private HttpServletRequest mockRequest;

    @Mock
    private HttpServletResponse mockResponse;

    private void addOneProduct(String name, String price) throws IOException {
        when(mockRequest.getParameter("name")).thenReturn(name);
        when(mockRequest.getParameter("price")).thenReturn(price);
        new AddProductServlet().doGet(mockRequest, mockResponse);
    }

    private void runSQL(String sql) {
        try (Statement stmt = createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Before
    public void setMocks() throws IOException {
        MockitoAnnotations.openMocks(this);
        when(mockResponse.getWriter()).thenReturn(new PrintWriter(writer));
    }

    @Before
    public void createProductDB() {
        runSQL(CREATE_PRODUCT);
    }

    @After
    public void dropProductDB() {
        runSQL(DROP_PRODUCT);
    }

    @Test
    @DisplayName("testing adding 1 product")
    public void addOneTest() throws IOException {
        addOneProduct("iphone", "100");
        String result = writer.toString();
        assertTrue(result.contains("OK"));
        new GetProductsServlet().doGet(mockRequest, mockResponse);
        result = writer.toString();
        assertTrue(result.contains("iphone"));
    }

    @Test
    @DisplayName("testing adding many products")
    public void addManyTest() throws IOException {
        addOneProduct("iphone1", "101");
        String result = writer.toString();
        assertTrue(result.contains("OK"));

        addOneProduct("iphone2", "102");
        result = writer.toString();
        assertTrue(result.contains("OK"));

        addOneProduct("iphone3", "103");
        result = writer.toString();
        assertTrue(result.contains("OK"));

        new GetProductsServlet().doGet(mockRequest, mockResponse);
        result = writer.toString();
        assertTrue(result.contains("iphone1"));
        assertTrue(result.contains("iphone2"));
        assertTrue(result.contains("iphone3"));
    }
}

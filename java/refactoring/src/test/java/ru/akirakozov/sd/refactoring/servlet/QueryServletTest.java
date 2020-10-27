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

public class QueryServletTest {
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
    @DisplayName("Sum test")
    public void testSum() throws IOException {
        addOneProduct("iphone1", "100");
        addOneProduct("iphone2", "300");
        addOneProduct("iphone3", "600");
        when(mockRequest.getParameter("command")).thenReturn("sum");
        new QueryServlet().doGet(mockRequest, mockResponse);
        String result = writer.toString();
        assertTrue(result.contains("1000"));
    }
}


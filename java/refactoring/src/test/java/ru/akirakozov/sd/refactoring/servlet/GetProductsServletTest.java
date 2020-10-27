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

public class GetProductsServletTest {
    private final StringWriter writer = new StringWriter();

    @Mock
    private HttpServletRequest mockRequest;

    @Mock
    private HttpServletResponse mockResponse;

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
    @DisplayName("testing GetProductServlet")
    public void someProductsTest() {
        runSQL(SQL_TEST_INPUT);
        new GetProductsServlet().doGet(mockRequest, mockResponse);
        String result = writer.toString();
        assertTrue(result.contains("phone\t200"));
        assertTrue(result.contains("table\t250"));
        assertTrue(result.contains("guitar\t300"));
        assertTrue(result.contains("chair\t350"));
    }
}

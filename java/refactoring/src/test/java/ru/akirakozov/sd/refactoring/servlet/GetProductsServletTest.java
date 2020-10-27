package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertTrue;

import static ru.akirakozov.sd.refactoring.database.ControllerDB.*;

public class GetProductsServletTest extends ServletTestWrapper {

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

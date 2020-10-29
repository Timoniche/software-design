package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import ru.akirakozov.sd.refactoring.model.ProductDAO;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class AddProductServletTest extends ServletTestWrapper {

    private void addOneProduct(String name, String price) throws IOException {
        when(mockRequest.getParameter("name")).thenReturn(name);
        when(mockRequest.getParameter("price")).thenReturn(price);
        new AddProductServlet(new ProductDAO()).doGet(mockRequest, mockResponse);
    }

    @Test
    @DisplayName("testing adding 1 product")
    public void addOneTest() throws IOException {
        addOneProduct("iphone", "100");
        String result = writer.toString();
        assertTrue(result.contains("OK"));
        new GetProductsServlet(new ProductDAO()).doGet(mockRequest, mockResponse);
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

        new GetProductsServlet(new ProductDAO()).doGet(mockRequest, mockResponse);
        result = writer.toString();
        assertTrue(result.contains("iphone1"));
        assertTrue(result.contains("iphone2"));
        assertTrue(result.contains("iphone3"));
    }
}

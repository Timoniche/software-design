package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import ru.akirakozov.sd.refactoring.model.ProductDAO;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class QueryServletTest extends ServletTestWrapper{

    private void addOneProduct(String name, String price) throws IOException {
        when(mockRequest.getParameter("name")).thenReturn(name);
        when(mockRequest.getParameter("price")).thenReturn(price);
        new AddProductServlet(new ProductDAO()).doGet(mockRequest, mockResponse);
    }

    @Test
    @DisplayName("Sum test")
    public void testSum() throws IOException {
        addOneProduct("iphone1", "100");
        addOneProduct("iphone2", "300");
        addOneProduct("iphone3", "600");
        when(mockRequest.getParameter("command")).thenReturn("sum");
        new QueryServlet(new ProductDAO()).doGet(mockRequest, mockResponse);
        String result = writer.toString();
        assertTrue(result.contains("1000"));
    }

    @Test
    @DisplayName("Max test")
    public void testMax() throws IOException {
        addOneProduct("iphone1", "100");
        addOneProduct("iphone2", "300");
        addOneProduct("iphone3", "600");
        when(mockRequest.getParameter("command")).thenReturn("max");
        new QueryServlet(new ProductDAO()).doGet(mockRequest, mockResponse);
        String result = writer.toString();
        assertTrue(result.contains("iphone3\t600"));
    }

    @Test
    @DisplayName("Min test")
    public void testMin() throws IOException {
        addOneProduct("iphone1", "100");
        addOneProduct("iphone2", "300");
        addOneProduct("iphone3", "600");
        when(mockRequest.getParameter("command")).thenReturn("min");
        new QueryServlet(new ProductDAO()).doGet(mockRequest, mockResponse);
        String result = writer.toString();
        assertTrue(result.contains("iphone1\t100"));
    }

    @Test
    @DisplayName("Count test")
    public void testCount() throws IOException {
        addOneProduct("iphone1", "100");
        addOneProduct("iphone2", "300");
        addOneProduct("iphone3", "600");
        when(mockRequest.getParameter("command")).thenReturn("count");
        new QueryServlet(new ProductDAO()).doGet(mockRequest, mockResponse);
        String result = writer.toString();
        assertTrue(result.contains("3"));
    }
}


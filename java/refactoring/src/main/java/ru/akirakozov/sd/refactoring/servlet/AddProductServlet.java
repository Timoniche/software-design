package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import ru.akirakozov.sd.refactoring.model.Product;
import ru.akirakozov.sd.refactoring.model.ProductDAO;

/**
 * @author akirakozov
 */
public class AddProductServlet extends AbstractServlet {
    private final ProductDAO productDAO;

    public AddProductServlet(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String name = request.getParameter("name");
        long price = Long.parseLong(request.getParameter("price"));
        Product product = new Product(name, price);
        productDAO.addProduct(product);
        response.getWriter().println("OK");
    }
}

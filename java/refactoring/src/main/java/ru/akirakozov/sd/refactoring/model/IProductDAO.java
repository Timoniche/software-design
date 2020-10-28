package ru.akirakozov.sd.refactoring.model;

import java.util.List;

public interface IProductDAO {
    public List<Product> getProducts();
    public void addProduct(Product product);
    public Product maxProduct();
    public Product minProduct();
    public long sumPrices();
    public long countProducts();
}
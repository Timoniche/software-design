package ru.akirakozov.sd.refactoring.model;

import java.util.List;

public class ProductDAO implements IProductDAO {
    @Override
    public List<Product> getProducts() {
        return null;
    }

    @Override
    public void addProduct(Product product) {

    }

    @Override
    public Product maxProduct() {
        return null;
    }

    @Override
    public Product minProduct() {
        return null;
    }

    @Override
    public long sumPrices() {
        return 0;
    }

    @Override
    public long countProducts() {
        return 0;
    }
}

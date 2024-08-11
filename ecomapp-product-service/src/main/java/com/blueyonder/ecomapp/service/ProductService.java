package com.blueyonder.ecomapp.service;

import com.blueyonder.ecomapp.exception.ProductException;
import com.blueyonder.ecomapp.exception.ProductNotFoundException;
import com.blueyonder.ecomapp.model.Product;
import com.blueyonder.ecomapp.model.ProductWithCategory;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface ProductService
{
    public int createProduct(Product product);
    public Product viewProduct(int id) throws ProductException;
    public Collection<Product> listProducts();
    public boolean updateProduct(int id, Product product) throws ProductException;
    public boolean deleteProduct(int id) throws ProductException;

    public Collection<Product> listProductsWithCategory(int categoryId);

    public boolean linkProductsWithCategories(ProductWithCategory productWithCategory);

//    public Collection<Product> findProductsByName(String name);

    public boolean updateProductByField(int id, Map<String, Object> updates) throws ProductException, NoSuchFieldException, IllegalAccessException;

    public Collection<Product> getProductsByField(Map<String, Object> fileds);
}

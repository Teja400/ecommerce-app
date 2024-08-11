package com.blueyonder.ecomapp.service;

import com.blueyonder.ecomapp.dao.ProductRepository;
import com.blueyonder.ecomapp.exception.ProductException;
import com.blueyonder.ecomapp.model.Category;
import com.blueyonder.ecomapp.model.Product;
import com.blueyonder.ecomapp.model.ProductWithCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.util.Map;

import java.util.Collection;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService
{
    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
    @Autowired
    private ProductRepository productRepository;

    @Override
    public int createProduct(Product product) {
        return productRepository.save(product).getId();
    }

    @Override
    public Product viewProduct(int id) {
       return productRepository.findById(id).orElse(null);
    }

    @Override
    public Collection<Product> listProducts() {
        return productRepository.findAll();
    }

    @Override
    public boolean updateProduct(int id, Product product) {
        Product productForUpdate = productRepository.findById(id).orElse(null);
        if(productForUpdate == null) return false;
        product.setId(id);
        productRepository.save(product);
        return true;
    }

    @Override
    public boolean deleteProduct(int id)
    {
        Product productForDelete = productRepository.findById(id).orElse(null);
        if(productForDelete == null) return false;
        productRepository.deleteById(id);
        return true;
    }

    @Override
    public Collection<Product> listProductsWithCategory(int categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    @Override
    public boolean linkProductsWithCategories(ProductWithCategory productWithCategory)
    {
        Product product = productRepository.findById(productWithCategory.getProductId()).orElse(null);
        if(product == null) return false;
        product.setCategoryId(productWithCategory.getCategoryId());
        productRepository.save(product);
        return true;
    }



// ...

    @Override
    public boolean updateProductByField(int id, Map<String, Object> updates) throws ProductException, NoSuchFieldException, IllegalAccessException {
        Product productForPatch = productRepository.findById(id).orElse(null);
        if(productForPatch == null) return false;
        for (Map.Entry<String, Object> entry : updates.entrySet()) {
                Field field = productForPatch.getClass().getDeclaredField(entry.getKey());
                field.setAccessible(true);
                field.set(productForPatch, entry.getValue());
        }
        productRepository.save(productForPatch);
        return true;
    }

    @Override
    public Collection<Product> getProductsByField(Map<String, Object> fields) {
        if(fields.containsKey("categoryId"))
        {
            return productRepository.findByCategoryId((Integer) fields.get("categoryId"));
        }
        else if(fields.containsKey("name"))
        {
            return productRepository.findByNameContaining((String) fields.get("name"));
        }
        else if(fields.containsKey("description"))
        {
            return productRepository.findByDescriptionContaining((String) fields.get("description"));
        }
        else if(fields.containsKey("price"))
        {
            return productRepository.findByPrice((Double) fields.get("price"));
        }
        return null;

    }

}

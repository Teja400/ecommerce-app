package com.blueyonder.ecomapp.dao;

import com.blueyonder.ecomapp.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;

public interface ProductRepository extends MongoRepository<Product, Integer>
{
    Collection<Product> findByCategoryId(int categoryId);
    Collection<Product> findByNameContaining(String name);
    Collection<Product> findByDescriptionContaining(String description);
    Collection<Product> findByPrice(double price);



}

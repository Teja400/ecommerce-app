package com.blueyonder.ecomapp.dao;

import com.blueyonder.ecomapp.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;

public interface CategoryRepository extends MongoRepository<Category, Integer>
{
    public Collection<Category> findByNameContaining(String name);
    public Collection<Category> findByDescriptionContaining(String description);

    public Collection<Category> findByNameContainingAndDescriptionContaining(String description, String name);
}

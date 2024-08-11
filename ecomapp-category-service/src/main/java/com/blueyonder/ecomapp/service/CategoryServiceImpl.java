package com.blueyonder.ecomapp.service;

import com.blueyonder.ecomapp.dao.CategoryRepository;
import com.blueyonder.ecomapp.exception.CategoryException;
import com.blueyonder.ecomapp.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService
{
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public int createCategory(Category category) {
        return categoryRepository.save(category).getId();
    }

    @Override
    public Category viewCategory(int id)  {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Collection<Category> listCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public boolean updateCategory(int id, Category category) {
        Category categoryForUpdate = categoryRepository.findById(id).orElse(null);
        if(categoryForUpdate == null) return false;
        categoryForUpdate.setName(category.getName());
        categoryForUpdate.setDescription(category.getDescription());
        categoryRepository.save(categoryForUpdate);
        return true;
    }

    @Override
    public boolean deleteCategory(int id) {
        Category categoryForDelete = categoryRepository.findById(id).orElse(null);
        if(categoryForDelete == null) return false;
        categoryRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean updateCategoryByAnyField(int id, Map<String, Object> updates) throws NoSuchFieldException, IllegalAccessException {
        Category categoryForPatch = categoryRepository.findById(id).orElse(null);
        if(categoryForPatch == null) return false;
        for (Map.Entry<String, Object> entry : updates.entrySet()) {
                Field field = categoryForPatch.getClass().getDeclaredField(entry.getKey());
                field.setAccessible(true);
                field.set(categoryForPatch, entry.getValue());
        }
        categoryRepository.save(categoryForPatch);
        return true;
    }

    @Override
    public Collection<Category> getCategoriesByField(Map<String, Object> fieldValues) {
        Collection<Category> filteredCategories = new ArrayList<>();
            if (fieldValues.containsKey("name")&& fieldValues.containsKey("description"))
            {
                return categoryRepository.findByNameContainingAndDescriptionContaining((String) fieldValues.get("name"), (String) fieldValues.get("description"));
            }
            else if(fieldValues.containsKey("name"))
            {
                return categoryRepository.findByNameContaining((String) fieldValues.get("name"));
//                return categoryRepository.findByName((String) entry.getValue());
//                return categoryRepository.findById((Integer) entry.getValue()).orElse(null).getProducts();
            }
            else if(fieldValues.containsKey("description"))
            {
                return categoryRepository.findByDescriptionContaining((String) fieldValues.get("description"));

            }
        return null;
    }

}

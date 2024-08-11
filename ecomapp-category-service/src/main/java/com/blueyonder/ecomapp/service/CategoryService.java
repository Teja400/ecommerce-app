package com.blueyonder.ecomapp.service;

import com.blueyonder.ecomapp.exception.CategoryException;
import com.blueyonder.ecomapp.model.Category;

import java.util.Collection;
import java.util.Map;

public interface CategoryService
{
    public int createCategory(Category category);
    public Category viewCategory(int id);
    public Collection<Category> listCategories();
    public boolean updateCategory(int id, Category category);
    public boolean deleteCategory(int id);

    public boolean updateCategoryByAnyField(int id, Map<String, Object> updates) throws NoSuchFieldException, IllegalAccessException;

    public Collection<Category> getCategoriesByField(Map<String, Object> fileds);

}

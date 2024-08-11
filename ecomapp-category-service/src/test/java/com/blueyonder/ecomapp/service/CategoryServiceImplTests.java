package com.blueyonder.ecomapp.service;

import com.blueyonder.ecomapp.dao.CategoryRepository;
import com.blueyonder.ecomapp.exception.CategoryException;
import com.blueyonder.ecomapp.model.Category;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CategoryServiceImplTests
{
    @Autowired
    private CategoryService categoryService;

    @MockBean
    private CategoryRepository categoryRepository;

    @BeforeAll
    public static void init() {
        // Logic to initialize test data goes here
        System.out.println("Test data initialization at class level..");
    }

    @AfterAll
    public static void tearDown() {
        // Logic to clean up test data goes here
        System.out.println("Test data clean up at class level..");
    }

    @Test
    public void shouldCreateCategoryWhenPassingValidCategory() {
        Category newCategory = new Category();
        newCategory.setName("Electronics");
        newCategory.setDescription("All electronic items");

        Category savedCategory = new Category();
        savedCategory.setId(1);
        savedCategory.setName("Electronics");
        savedCategory.setDescription("All electronic items");

        Mockito.when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(savedCategory);
        int id = categoryService.createCategory(newCategory);
        assertEquals(1, id);
        assertNotNull(id);
    }

    @Test
    public void shouldViewCategoryWhenPassingValidCategoryId() throws CategoryException {
        Category category = new Category();
        category.setId(1);
        category.setName("Electronics");
        category.setDescription("All electronic items");

        Mockito.when(categoryRepository.findById(1)).thenReturn(java.util.Optional.of(category));
        Category categoryFromDb = categoryService.viewCategory(1);
        assertNotNull(categoryFromDb);
        assertEquals("Electronics", categoryFromDb.getName());
    }


    @Test
    public void shouldNotViewCategoryWhenPassingInvalidCategoryId() throws CategoryException {
        Mockito.when(categoryRepository.findById(1)).thenReturn(java.util.Optional.empty());
        Category categoryFromDb = categoryService.viewCategory(1);
        assertEquals(null, categoryFromDb);
    }

    @Test
    public void shouldListCategoriesWhenCategoriesExist() {
        Category category1 = new Category();
        category1.setId(1);
        category1.setName("Electronics");
        category1.setDescription("All electronic items");

        Category category2 = new Category();
        category2.setId(2);
        category2.setName("Clothing");
        category2.setDescription("All clothing items");

        Mockito.when(categoryRepository.findAll()).thenReturn(java.util.List.of(category1, category2));
        assertEquals(2, categoryService.listCategories().size());
    }

    @Test
    public void shouldNotListCategoriesWhenCategoriesDoNotExist() {
        Mockito.when(categoryRepository.findAll()).thenReturn(java.util.List.of());
        assertEquals(0, categoryService.listCategories().size());
    }

    @Test
    public void shouldUpdateCategoryWhenPassingValidCategoryIdAndCategory() {
        Category category = new Category();
        category.setId(1);
        category.setName("Electronics");
        category.setDescription("All electronic items");

        Mockito.when(categoryRepository.findById(1)).thenReturn(java.util.Optional.of(category));
        Mockito.when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(category);
        boolean isUpdated = categoryService.updateCategory(1, category);
        assertEquals(true, isUpdated);
    }

    @Test
    public void shouldNotUpdateCategoryWhenPassingInvalidCategoryId() {
        Category category = new Category();
        category.setId(1);
        category.setName("Electronics");
        category.setDescription("All electronic items");

        Mockito.when(categoryRepository.findById(1)).thenReturn(java.util.Optional.empty());
        boolean isUpdated = categoryService.updateCategory(1, category);
        assertEquals(false, isUpdated);
    }

    @Test
    public void shouldDeleteCategoryWhenPassingValidCategoryId() {
        Category category = new Category();
        category.setId(1);
        category.setName("Electronics");
        category.setDescription("All electronic items");

        Mockito.when(categoryRepository.findById(1)).thenReturn(java.util.Optional.of(category));
        Mockito.doNothing().when(categoryRepository).deleteById(1);
        boolean isDeleted = categoryService.deleteCategory(1);
        assertEquals(true, isDeleted);
    }

    @Test
    public void shouldNotDeleteCategoryWhenPassingInvalidCategoryId() {
        Mockito.when(categoryRepository.findById(1)).thenReturn(java.util.Optional.empty());
        boolean isDeleted = categoryService.deleteCategory(1);
        assertEquals(false, isDeleted);
    }

    @Test
    public void shouldUpdateCategoryByAnyFieldWhenPassingValidCategoryIdAndUpdates() throws NoSuchFieldException, IllegalAccessException {
        Category category = new Category();
        category.setId(1);
        category.setName("Electronics");
        category.setDescription("All electronic items");

        Mockito.when(categoryRepository.findById(1)).thenReturn(java.util.Optional.of(category));
        Mockito.when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(category);
        boolean isUpdated = categoryService.updateCategoryByAnyField(1, java.util.Map.of("name", "Electronics", "description", "All electronic items"));
        assertEquals(true, isUpdated);
    }

    @Test
    public void shouldNotUpdateCategoryByAnyFieldWhenPassingInvalidCategoryId() throws NoSuchFieldException, IllegalAccessException {
        Mockito.when(categoryRepository.findById(1)).thenReturn(java.util.Optional.empty());
        boolean isUpdated = categoryService.updateCategoryByAnyField(1, java.util.Map.of("name", "Electronics", "description", "All electronic items"));
        assertEquals(false, isUpdated);
//        assertThrows(NoSuchFieldException.class, () -> categoryService.updateCategoryByAnyField(1, java.util.Map.of("name", "Electronics", "description", "All electronic items")));
    }

    @Test
    public void shouldThrowExceptionWhenPassingInvalidFieldForUpdateCategoryByAnyField() throws NoSuchFieldException, IllegalAccessException {
        Category category = new Category();
        category.setId(1);
        category.setName("Electronics");
        category.setDescription("All electronic items");

        Mockito.when(categoryRepository.findById(1)).thenReturn(java.util.Optional.of(category));
        Mockito.when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(category);
        assertThrows(NoSuchFieldException.class, () -> categoryService.updateCategoryByAnyField(1, java.util.Map.of("invalidField", "Electronics", "description", "All electronic items")));
    }

    @Test
    public void shouldGetCategoriesByFieldWhenPassingValidFieldValues() {
        Category category1 = new Category();
        category1.setId(1);
        category1.setName("Electronics");
        category1.setDescription("All electronic items");

        Category category2 = new Category();
        category2.setId(2);
        category2.setName("Clothing");
        category2.setDescription("All clothing items");

        Mockito.when(categoryRepository.findByNameContaining("Electronics")).thenReturn(java.util.List.of(category1));
        assertEquals(1, categoryService.getCategoriesByField(java.util.Map.of("name", "Electronics")).size());
    }

    @Test
    public void shouldNotGetCategoriesByFieldWhenPassingInvalidFieldValues() {
        Mockito.when(categoryRepository.findByNameContaining("Electronics")).thenReturn(java.util.List.of());
        assertEquals(0, categoryService.getCategoriesByField(java.util.Map.of("name", "Electronics")).size());
    }

    @Test
    public void shouldGetCategoriesByFieldWhenPassingValidFieldValuesForDescription() {
        Category category1 = new Category();
        category1.setId(1);
        category1.setName("Electronics");
        category1.setDescription("All electronic items");

        Category category2 = new Category();
        category2.setId(2);
        category2.setName("Clothing");
        category2.setDescription("All clothing items");

        Mockito.when(categoryRepository.findByDescriptionContaining("All electronic items")).thenReturn(java.util.List.of(category1));
        assertEquals(1, categoryService.getCategoriesByField(java.util.Map.of("description", "All electronic items")).size());
    }

    @Test
    public void shouldGetCategoriesByFieldWhenPassingValidFieldValuesForNameAndDescription() {
        Category category1 = new Category();
        category1.setId(1);
        category1.setName("Electronics");
        category1.setDescription("All electronic items");

        Category category2 = new Category();
        category2.setId(2);
        category2.setName("Clothing");
        category2.setDescription("All clothing items");

        Mockito.when(categoryRepository.findByNameContainingAndDescriptionContaining("Electronics", "All electronic items")).thenReturn(java.util.List.of(category1));
        assertEquals(1, categoryService.getCategoriesByField(java.util.Map.of("name", "Electronics", "description", "All electronic items")).size());
    }

    @Test
    public void shouldNotGetCategoriesByFieldWhenPassingInvalidFields() {

        Mockito.when(categoryRepository.findByNameContainingAndDescriptionContaining("Electronics", "All electronic items")).thenReturn(java.util.List.of());
        assertEquals(null, categoryService.getCategoriesByField(java.util.Map.of("nadfdme", "Electronics")));
    }










}

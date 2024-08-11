package com.blueyonder.ecomapp.controller;

import com.blueyonder.ecomapp.exception.CategoryException;
import com.blueyonder.ecomapp.exception.CategoryNotFoundException;
import com.blueyonder.ecomapp.model.Category;
import com.blueyonder.ecomapp.model.ResponseMessage;
import com.blueyonder.ecomapp.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.antlr.stringtemplate.language.Cat;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class CategoryControllerTests
{
    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private CategoryService categoryService;

    @Test
    public void shouldCreateProductWhenValidInput() throws URISyntaxException {
        Mockito.lenient().when(categoryService.createCategory(Mockito.any())).thenReturn(1);
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setMessage("Category created successfully with id 1");
        responseMessage.setId(1);
        responseMessage.setStatus("CREATED");
        responseMessage.setStatusCode(201);
        ResponseEntity<ResponseMessage> responseEntity = ResponseEntity.created(new URI("/products")).body(responseMessage);
        Category category = new Category();
        category.setName("Electronics");
        category.setDescription("All electronic items");
        ResponseEntity<ResponseMessage> response = this.restTemplate.postForEntity("/categories/add-category", category, ResponseMessage.class);
        assertEquals(responseEntity.getStatusCode(), response.getStatusCode());
    }

    @Test
    public void shouldReturnCategoryWhenValidId() throws CategoryException {
        Mockito.lenient().when(categoryService.viewCategory(1)).thenReturn(new Category(1, "Electronics", "All electronic items"));
        ResponseEntity<Category> responseEntity = ResponseEntity.ok().body(new Category(1, "Electronics", "All electronic items"));
        ResponseEntity<Category> response = this.restTemplate.getForEntity("/categories/1", Category.class);
        assertEquals(responseEntity.getStatusCode(), response.getStatusCode());
    }

    @Test
    public void shouldReturnListOfCategories() {

        Category category = new Category();
        category.setName("Electronics");
        category.setDescription("All electronic items");

        Category category2 = new Category();
        category2.setName("Clothing");
        category2.setDescription("All clothing items");

        Mockito.lenient().when(categoryService.listCategories()).thenReturn(List.of(category, category2));
        ResponseEntity<Collection<Category>> responseEntity = ResponseEntity.ok().body(List.of(category, category2));
        ResponseEntity<Category[]> response = this.restTemplate.getForEntity("/categories", Category[].class);
        assertEquals(responseEntity.getStatusCode(), response.getStatusCode());
        assertEquals(responseEntity.getBody().size(), response.getBody().length);
    }

    @Test
    public void shouldUpdateCategoryWhenValidInput() throws URISyntaxException {
        Mockito.lenient().when(categoryService.updateCategory(Mockito.anyInt(), Mockito.any())).thenReturn(true);
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setMessage("Category updated successfully");
        responseMessage.setId(1);
        responseMessage.setStatus("OK");
        responseMessage.setStatusCode(200);
        ResponseEntity<ResponseMessage> responseEntity = ResponseEntity.ok().body(responseMessage);
        Category category = new Category();
        category.setName("Electronics");
        category.setDescription("All electronic items");
        RequestEntity requestEntity = RequestEntity.put(new URI("/categories/1")).body(category);
        ResponseEntity<ResponseMessage> response = restTemplate.exchange(requestEntity, ResponseMessage.class);
        assertEquals(responseEntity.getStatusCode(), response.getStatusCode());
    }

    @Test
    public void shouldDeleteCategoryWhenValidId() throws URISyntaxException {
        Mockito.lenient().when(categoryService.deleteCategory(Mockito.anyInt())).thenReturn(true);
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setMessage("Category deleted successfully");
        responseMessage.setId(1);
        responseMessage.setStatus("OK");
        responseMessage.setStatusCode(200);
        ResponseEntity<ResponseMessage> responseEntity = ResponseEntity.ok().body(responseMessage);
        RequestEntity requestEntity = RequestEntity.delete(new URI("/categories/1")).build();
        ResponseEntity<ResponseMessage> response = restTemplate.exchange(requestEntity, ResponseMessage.class);
        assertEquals(responseEntity.getStatusCode(), response.getStatusCode());
    }

    @Test
    public void shouldUpdateCategoryByFieldWhenValidInput() throws URISyntaxException, NoSuchFieldException, IllegalAccessException {
        Mockito.lenient().when(categoryService.updateCategoryByAnyField(Mockito.anyInt(), Mockito.any())).thenReturn(true);
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setMessage("Category updated successfully");
        responseMessage.setId(1);
        responseMessage.setStatus("OK");
        responseMessage.setStatusCode(200);
        ResponseEntity<ResponseMessage> responseEntity = ResponseEntity.ok().body(responseMessage);
        RequestEntity requestEntity = RequestEntity.patch(new URI("/categories/1")).body(Map.of("name", "Electronics"));
        ResponseEntity<ResponseMessage> response = restTemplate.exchange(requestEntity, ResponseMessage.class);
        assertEquals(responseEntity.getStatusCode(), response.getStatusCode());
    }

    @Test
    public void shouldReturnCategoriesWhenValidFields() throws URISyntaxException {
        Category category = new Category();
        category.setName("Electronics");
        category.setDescription("All electronic items");

        Category category2 = new Category();
        category2.setName("Clothing");
        category2.setDescription("All clothing items");

        Mockito.lenient().when(categoryService.getCategoriesByField(Mockito.any())).thenReturn(List.of(category, category2));
        ResponseEntity<Collection<Category>> responseEntity = ResponseEntity.ok().body(List.of(category, category2));
        RequestEntity requestEntity = RequestEntity.post(new URI("/categories")).body(Map.of("name", "Electronics"));
        ResponseEntity<Category[]> response = restTemplate.postForEntity("/categories", requestEntity, Category[].class);
        assertEquals(responseEntity.getStatusCode(), response.getStatusCode());
        assertEquals(responseEntity.getBody().size(), response.getBody().length);
    }


    @Test
    public void shouldThrowCategoryNotFoundExceptionWhenInvalidId() {
        Mockito.lenient().when(categoryService.viewCategory(1)).thenReturn(null);
        RequestEntity requestEntity = RequestEntity.get(URI.create("/categories/1")).build();
        ResponseEntity<ResponseMessage> response = restTemplate.exchange(requestEntity, ResponseMessage.class);
        assertEquals(500, response.getStatusCodeValue());
        assertEquals("CategoryNotFoundException: No Category Found for given Category Id: 1", response.getBody().getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenUpdateCategoryWithInvalidId() {
        Mockito.lenient().when(categoryService.updateCategory(1, new Category())).thenReturn(false);
        Category category = new Category();
        category.setName("Electronics");
        category.setDescription("All electronic items");
        RequestEntity requestEntity = RequestEntity.put(URI.create("/categories/1")).body(category);
        ResponseEntity<ResponseMessage> response = restTemplate.exchange(requestEntity, ResponseMessage.class);
        assertEquals(500, response.getStatusCodeValue());
        assertEquals("CategoryNotFoundException: Category not found with id 1", response.getBody().getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenDeleteCategoryWithInvalidId() {
        Mockito.lenient().when(categoryService.deleteCategory(1)).thenReturn(false);
        RequestEntity requestEntity = RequestEntity.delete(URI.create("/categories/1")).build();
        ResponseEntity<ResponseMessage> response = restTemplate.exchange(requestEntity, ResponseMessage.class);
        assertEquals(500, response.getStatusCodeValue());
        assertEquals("CategoryNotFoundException: Category not found with id 1", response.getBody().getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenUpdateCategoryByFieldWithInvalidId() throws NoSuchFieldException, IllegalAccessException {
        Mockito.lenient().when(categoryService.updateCategoryByAnyField(1, Map.of("name", "Electronics"))).thenReturn(false);
        RequestEntity requestEntity = RequestEntity.patch(URI.create("/categories/1")).body(Map.of("name", "Electronics"));
        ResponseEntity<ResponseMessage> response = restTemplate.exchange(requestEntity, ResponseMessage.class);
        assertEquals(500, response.getStatusCodeValue());
        assertEquals("CategoryNotFoundException: Product not found with id 1", response.getBody().getMessage());
    }





}

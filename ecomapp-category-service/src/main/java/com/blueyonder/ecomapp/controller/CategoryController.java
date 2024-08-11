package com.blueyonder.ecomapp.controller;

import com.blueyonder.ecomapp.exception.CategoryException;
import com.blueyonder.ecomapp.exception.CategoryNotFoundException;
import com.blueyonder.ecomapp.model.Category;
import com.blueyonder.ecomapp.model.ResponseMessage;
import com.blueyonder.ecomapp.service.CategoryService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping(path = "/categories")
//@CrossOrigin("*")
@Slf4j
public class CategoryController
{
    @Autowired
    private CategoryService categoryService;
    @PostMapping("/add-category")
    public ResponseEntity<ResponseMessage> createCategory(@RequestBody @Valid Category category)
    {
        int id = categoryService.createCategory(category);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(location).body(this.getResponse(id, "Category Created successfully with id " + id));
    }

    @GetMapping("/{id}")
    public Category viewCategory(@PathVariable int id) throws CategoryException
    {
        log.info("viewCategory called with id: " + id);
        if(categoryService.viewCategory(id) == null)
        {
            log.info("No Category Found for given Category Id: " + id);
            throw new CategoryNotFoundException("No Category Found for given Category Id: " + id);
        }
        return categoryService.viewCategory(id);
    }

    @GetMapping
    public Collection<Category> listCategories()
    {
        return categoryService.listCategories();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> updateCategory(@RequestBody @Valid Category category, @PathVariable int id) throws CategoryException
    {

        if(!categoryService.updateCategory(id, category))
        {
            throw new CategoryNotFoundException("Category not found with id " + id);
        }
        return ResponseEntity.ok().body(this.getResponse(id, "Category Updated Successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteCategory(@PathVariable int id) throws CategoryException
    {
        if(!categoryService.deleteCategory(id))
        {
            throw new CategoryNotFoundException("Category not found with id " + id);
        }
        return ResponseEntity.ok().body(this.getResponse(id, "Category Deleted Successfully"));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseMessage> updateProductByField(@PathVariable int id, @RequestBody Map<String, Object> updates) throws CategoryException, NoSuchFieldException, IllegalAccessException {
        if(!categoryService.updateCategoryByAnyField(id, updates))
        {
            throw new CategoryNotFoundException("Product not found with id " + id);
        }
        return ResponseEntity.ok().body(this.getResponse(id, "Category Updated"));
    }

    @PostMapping
    public Collection<Category> getCategoriesByField(@RequestBody Map<String, Object> fields)
    {
        return categoryService.getCategoriesByField(fields);
    }




    private ResponseMessage getResponse(Integer id, String message) {
        ResponseMessage response = new ResponseMessage();
        response.setId(id);
        response.setStatus(HttpStatus.OK.name());
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage(message);
        return response;
    }



}

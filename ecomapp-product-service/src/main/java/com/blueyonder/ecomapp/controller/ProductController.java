package com.blueyonder.ecomapp.controller;

import com.blueyonder.ecomapp.exception.ProductException;
import com.blueyonder.ecomapp.exception.ProductNotFoundException;
import com.blueyonder.ecomapp.model.Category;
import com.blueyonder.ecomapp.model.Product;
import com.blueyonder.ecomapp.model.ProductWithCategory;
import com.blueyonder.ecomapp.model.ResponseMessage;
import com.blueyonder.ecomapp.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/products")
//@CrossOrigin("*")
@Slf4j
public class ProductController
{
    @Autowired
    public ProductService productService;

    @Operation(summary = "Add a new product")
    @PostMapping("/add-product")
    public ResponseEntity<ResponseMessage> createProduct(@RequestBody @Valid Product product)
    {
        int id = productService.createProduct(product);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();

        return ResponseEntity.created(location).body(this.getResponse(id, "Product Created"));
    }

    @Operation(summary = "List all products")
    @GetMapping
    public Collection<Product> listProducts()
    {
        return productService.listProducts();
    }

    @Operation(summary = "View a product by ID")
    @GetMapping("/{id}")
    public Product viewProduct(@PathVariable int id) throws ProductException
    {
        if(productService.viewProduct(id) == null)
        {
            throw new ProductNotFoundException("No Product Found for given Product ID - " + id);
        }
        return productService.viewProduct(id);
    }

    @Operation(summary = "Update a product by Id")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> updateProduct(@RequestBody @Valid Product product, @PathVariable int id) throws ProductException
    {
        if(!productService.updateProduct(id, product))
        {
            throw new ProductNotFoundException("Product not found with id " + id);
        }
        return ResponseEntity.ok().body(this.getResponse(id, "Product Updated"));
    }



    @Operation(summary = "Delete a product by Id")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteProduct(@PathVariable int id) throws ProductException {
        if(!productService.deleteProduct(id))
        {
            throw new ProductNotFoundException("Product not found with id " + id);
        }
        return ResponseEntity.ok().body(this.getResponse(id, "Product Deleted"));
    }


    private ResponseMessage getResponse(Integer id, String message) {
        ResponseMessage response = new ResponseMessage();
        response.setId(id);
        response.setStatus(HttpStatus.OK.name());
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage(message);
        return response;
    }


    @Operation(summary = "List all products with category")
    @GetMapping("/product-with-category")
    public Collection<Product> listProductsWithCategory(@RequestParam int categoryId)
    {
        return productService.listProductsWithCategory(categoryId);
    }

    @Operation(summary = "Link a product with category")
    @PostMapping("/product-with-category")
    public ResponseEntity<ResponseMessage> linkProductWithCategory(@RequestBody ProductWithCategory productWithCategory) throws ProductException
    {
        String serviceName = "ecomapp-category-service";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Category> response = restTemplate.getForEntity("http://" + serviceName + ":8083/categories/" + productWithCategory.getCategoryId(), Category.class);
        if(response.getStatusCode() != HttpStatus.OK) {
            throw new ProductException("Category not found with id " + productWithCategory.getCategoryId());
        }
        if(!productService.linkProductsWithCategories(productWithCategory))
        {
            throw new ProductException("Category or product have invalid id ");
        }
        return ResponseEntity.ok().body(this.getResponse(productWithCategory.getProductId(), "Product Linked with Category"));
    }

    @Operation(summary = "Update a product by field")
   @PatchMapping("/{id}")
    public ResponseEntity<ResponseMessage> updateProductByField(@PathVariable int id, @RequestBody Map<String, Object> updates) throws ProductException, NoSuchFieldException, IllegalAccessException
    {
        if(!productService.updateProductByField(id, updates))
        {
            throw new ProductNotFoundException("Product not found with id " + id);
        }
        return ResponseEntity.ok().body(this.getResponse(id, "Product Updated"));
    }

    @Operation(summary = "Get products by field")
    @PostMapping
    public Collection<Product> getProductsByField(@RequestBody Map<String, Object> fields)
    {
        return productService.getProductsByField(fields);
    }


}

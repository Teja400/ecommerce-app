package com.blueyonder.ecomapp.controller;


import com.blueyonder.ecomapp.exception.ProductException;
import com.blueyonder.ecomapp.model.Product;
import com.blueyonder.ecomapp.model.ProductWithCategory;
import com.blueyonder.ecomapp.model.ResponseMessage;
import com.blueyonder.ecomapp.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTests
{
    @Autowired
    TestRestTemplate restTemplate;
    @MockBean
    private ProductService productService;

    @Test
    public void shouldCreateProductWhenValidInput() throws URISyntaxException {
        Mockito.lenient().when(productService.createProduct(Mockito.any())).thenReturn(1);
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setMessage("Product Created");
        responseMessage.setId(1);
        responseMessage.setStatusCode(201);
        responseMessage.setStatus("CREATED");
        ResponseEntity<ResponseMessage> responseEntity = ResponseEntity.created(new URI("/products")).body(responseMessage);


        Product product = new Product();
        product.setName("Test Product");
        product.setDescription("Test Category");
        product.setPrice(100.0);

        RequestEntity requestEntity = RequestEntity.post("/products/add-product").body(product);
        ResponseEntity<ResponseMessage> response = restTemplate.exchange(requestEntity, ResponseMessage.class);
        assertEquals(responseEntity.getStatusCode(), response.getStatusCode());
    }

    @Test
    public void shouldReturnProductWhenValidInput() throws URISyntaxException, ProductException {
        Mockito.lenient().when(productService.viewProduct(Mockito.anyInt())).thenReturn(new Product());
        Product product = new Product();
        product.setName("Test Product");
        product.setDescription("Test Category");
        product.setPrice(100.0);
        ResponseEntity<Product> responseEntity = ResponseEntity.ok(product);
        RequestEntity requestEntity = RequestEntity.get(new URI("/products/1")).build();
        ResponseEntity<Product> response = restTemplate.exchange(requestEntity, Product.class);
        assertEquals(responseEntity.getStatusCode(), response.getStatusCode());
    }


    @Test
    public void testViewProductThrowsException() throws ProductException, URISyntaxException {
        int invalidId = 999; // ID that doesn't exist
        Mockito.when(productService.viewProduct(invalidId)).thenReturn(null); // Mock the product service to return null

        RequestEntity requestEntity = RequestEntity.get(new URI("/products/999")).build();
        ResponseEntity<ResponseMessage> response = restTemplate.exchange(requestEntity, ResponseMessage.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(response.getBody().getMessage(), "ProductNotFoundException: No Product Found for given Product ID - 999");

    }

    @Test
    public void testUpdateProductThrowsException() throws ProductException, URISyntaxException {
        int invalidId = 999; // ID that doesn't exist
        Product product = new Product();
        product.setName("Test Product");
        product.setDescription("Test Category");
        product.setPrice(100.0);
        Mockito.when(productService.updateProduct(invalidId, product)).thenReturn(false); // Mock the product service to return null

        RequestEntity requestEntity = RequestEntity.put(new URI("/products/999")).body(product);
        ResponseEntity<ResponseMessage> response = restTemplate.exchange(requestEntity, ResponseMessage.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(response.getBody().getMessage(), "ProductNotFoundException: Product not found with id 999");
    }



    @Test
    public void shouldListProducts()
    {
        Product product = new Product();
        product.setName("Test Product");
        product.setDescription("Test Category");
        product.setPrice(100.0);
        product.setId(1);
        Product product2 = new Product();
        product2.setName("Test Product2");
        product2.setDescription("Test Category2");
        product2.setPrice(200.0);
        product2.setId(2);
        Mockito.lenient().when(productService.listProducts()).thenReturn(List.of(product, product2));
        ResponseEntity<Product[]> responseEntity = ResponseEntity.ok(new Product[]{product, product2});
        ResponseEntity<Product[]> response = restTemplate.getForEntity("/products", Product[].class);
        assertEquals(responseEntity.getStatusCode(), response.getStatusCode());
        assertEquals(responseEntity.getBody().length, response.getBody().length);
    }


    @Test
    public void shouldUpdateProductWhenValidInput() throws URISyntaxException, ProductException {
        Mockito.lenient().when(productService.updateProduct(Mockito.anyInt(), Mockito.any())).thenReturn(true);
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setMessage("Product Updated");
        responseMessage.setId(1);
        responseMessage.setStatusCode(200);
        responseMessage.setStatus("OK");
        ResponseEntity<ResponseMessage> responseEntity = ResponseEntity.ok().body(responseMessage);
        Product product = new Product();
        product.setName("Test Product");
        product.setDescription("Test Category");
        product.setPrice(100.0);
        RequestEntity requestEntity = RequestEntity.put(new URI("/products/1")).body(product);
        ResponseEntity<ResponseMessage> response = restTemplate.exchange(requestEntity, ResponseMessage.class);
        assertEquals(responseEntity.getStatusCode(), response.getStatusCode());
    }

    @Test
    public void shouldDeleteProductWhenValidInput() throws URISyntaxException, ProductException {
        Mockito.lenient().when(productService.deleteProduct(Mockito.anyInt())).thenReturn(true);
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setMessage("Product Deleted");
        responseMessage.setId(1);
        responseMessage.setStatusCode(200);
        responseMessage.setStatus("OK");
        ResponseEntity<ResponseMessage> responseEntity = ResponseEntity.ok(responseMessage);
        RequestEntity requestEntity = RequestEntity.delete(new URI("/products/1")).build();
        ResponseEntity<ResponseMessage> response = restTemplate.exchange(requestEntity, ResponseMessage.class);
        assertEquals(responseEntity.getStatusCode(), response.getStatusCode());
    }
    @Test
    public void shouldListProductsWithCategoryWhenValidInput() throws URISyntaxException {
        Product product = new Product();
        product.setName("Test Product");
        product.setDescription("Test Category");
        product.setPrice(100.0);
        product.setId(1);
        Product product2 = new Product();
        product2.setName("Test Product2");
        product2.setDescription("Test Category2");
        product2.setPrice(200.0);
        product2.setId(2);
        Mockito.lenient().when(productService.listProductsWithCategory(Mockito.anyInt())).thenReturn(List.of(product, product2));
        ResponseEntity<Product[]> responseEntity = ResponseEntity.ok(new Product[]{product, product2});
        RequestEntity requestEntity = RequestEntity.get(new URI("/products/product-with-category?categoryId=1")).build();
        ResponseEntity<Product[]> response = restTemplate.exchange(requestEntity, Product[].class);
        assertEquals(responseEntity.getStatusCode(), response.getStatusCode());
        assertEquals(responseEntity.getBody().length, response.getBody().length);
    }

    @Test
    public void shouldLinkProductWithCategoryWhenValidInput() throws URISyntaxException, ProductException {
        Mockito.lenient().when(productService.linkProductsWithCategories(Mockito.any())).thenReturn(true);
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setMessage("Product Linked with Category");
        responseMessage.setId(1);
        responseMessage.setStatusCode(200);
        responseMessage.setStatus("OK");
        ResponseEntity<ResponseMessage> responseEntity = ResponseEntity.ok(responseMessage);
        ProductWithCategory productWithCategory = new ProductWithCategory();
        productWithCategory.setProductId(1);
        productWithCategory.setCategoryId(2);
        RequestEntity requestEntity = RequestEntity.post(new URI("/products/product-with-category")).body(productWithCategory);
        ResponseEntity<ResponseMessage> response = restTemplate.exchange(requestEntity, ResponseMessage.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void shouldUpdateProductByFieldWhenValidInput() throws URISyntaxException, ProductException, NoSuchFieldException, IllegalAccessException {
        Mockito.lenient().when(productService.updateProductByField(Mockito.anyInt(), Mockito.any())).thenReturn(true);
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setMessage("Product Updated");
        responseMessage.setId(1);
        responseMessage.setStatusCode(200);
        responseMessage.setStatus("OK");
        ResponseEntity<ResponseMessage> responseEntity = ResponseEntity.ok(responseMessage);
        RequestEntity requestEntity = RequestEntity.patch(new URI("/products/1")).body(Map.of( "name", "Test Product"));
        ResponseEntity<ResponseMessage> response = restTemplate.exchange(requestEntity, ResponseMessage.class);
        assertEquals(responseEntity.getStatusCode(), response.getStatusCode());
    }

    @Test
    public void shouldGetProductsByFieldWhenValidInput() throws URISyntaxException {
        Product product = new Product();
        product.setName("Test Product");
        product.setDescription("Test Category");
        product.setPrice(100.0);
        product.setId(1);
        Product product2 = new Product();
        product2.setName("Test Product2");
        product2.setDescription("Test Category2");
        product2.setPrice(200.0);
        product2.setId(2);
        Mockito.lenient().when(productService.getProductsByField(Mockito.any())).thenReturn(List.of(product, product2));
        ResponseEntity<Product[]> responseEntity = ResponseEntity.ok(new Product[]{product, product2});
        RequestEntity requestEntity = RequestEntity.post(new URI("/products")).body(Map.of("categoryId", 1));
        ResponseEntity<Product[]> response = restTemplate.exchange(requestEntity, Product[].class);
        assertEquals(responseEntity.getStatusCode(), response.getStatusCode());
        assertEquals(responseEntity.getBody().length, response.getBody().length);
    }

    @Test
    public void testDeleteProductThrowsException() throws ProductException {
        int invalidId = 999; // ID that doesn't exist
        Mockito.when(productService.deleteProduct(invalidId)).thenReturn(false); // Mock the product service to return null

        RequestEntity requestEntity = RequestEntity.delete("/products/999").build();
        ResponseEntity<ResponseMessage> response = restTemplate.exchange(requestEntity, ResponseMessage.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(response.getBody().getMessage(), "ProductNotFoundException: Product not found with id 999");

    }

    @Test
    public void testUpdateProductByFieldThrowsExceptionForInvalidField() throws ProductException, NoSuchFieldException, IllegalAccessException {
        int validId = 1; // assuming this ID exists in the database
        Map<String, Object> updates = new HashMap<>();
        updates.put("invalidField", "Invalid Value"); // Invalid field

        Mockito.when(productService.updateProductByField(validId, updates)).thenReturn(false); // Mock the product service to return null

        RequestEntity requestEntity = RequestEntity.patch("/products/1").body(updates);
        ResponseEntity<ResponseMessage> response = restTemplate.exchange(requestEntity, ResponseMessage.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(response.getBody().getMessage(), "ProductNotFoundException: Product not found with id 1");
    }

       @Test
       public void testLinkProductWithCategoryThrowsExceptionForInvalidCategory() throws ProductException {
           ProductWithCategory productWithCategory = new ProductWithCategory();
           productWithCategory.setProductId(1);
           productWithCategory.setCategoryId(2); // Assuming this category doesn't exist
           Mockito.when(productService.linkProductsWithCategories(productWithCategory)).thenReturn(false); // Mock the product service to return null

           RequestEntity requestEntity = RequestEntity.post("/products/product-with-category").body(productWithCategory);
           ResponseEntity<ResponseMessage> response = restTemplate.exchange(requestEntity, ResponseMessage.class);
           assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
//           assertEquals(response.getBody().getMessage(), "ProductException: Category or product have invalid id ");
       }











}

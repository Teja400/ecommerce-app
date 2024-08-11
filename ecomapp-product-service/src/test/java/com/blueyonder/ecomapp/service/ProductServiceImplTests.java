package com.blueyonder.ecomapp.service;

import com.blueyonder.ecomapp.dao.ProductRepository;
import com.blueyonder.ecomapp.exception.ProductException;
import com.blueyonder.ecomapp.exception.ProductNotFoundException;
import com.blueyonder.ecomapp.model.Category;
import com.blueyonder.ecomapp.model.Product;
import com.blueyonder.ecomapp.model.ProductWithCategory;
import org.antlr.stringtemplate.language.Cat;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProductServiceImplTests
{
    @Autowired
    private ProductService productService;
    @MockBean
    private ProductRepository productRepository;


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
    public void shouldCreateProductWhenPassingValidProduct() {
        Product newProduct = new Product();
        newProduct.setName("Samsung Galaxy Note10");
        newProduct.setDescription("Samsung Galaxy Note10");
        newProduct.setPrice(1000);

        Product savedProduct = new Product();
        savedProduct.setId(1);
        savedProduct.setName("Samsung Galaxy Note10");
        savedProduct.setDescription("Samsung Galaxy Note10");
        savedProduct.setPrice(1000);

        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(savedProduct);
        int id  = productService.createProduct(newProduct);
        assertNotNull(id);
        assertEquals(1, id);
    }


    @Test
    public void shouldUpdateProductWhenPassingValidProduct() throws ProductException {

        Product newProduct = new Product();
        newProduct.setId(1);
        newProduct.setName("Samsung Galaxy Note10");
        newProduct.setDescription("Samsung Galaxy Note10");
        newProduct.setPrice(1000);

        Product updatedProduct = new Product();
        updatedProduct.setId(1);
        updatedProduct.setName("Samsung Galaxy Note10");
        updatedProduct.setDescription("Samsung Galaxy Note10");
        updatedProduct.setPrice(2000);

        Mockito.lenient().when(productRepository.findById(1)).thenReturn(java.util.Optional.of(newProduct));

        newProduct.setPrice(2000);
        Mockito.lenient().when(productRepository.save(newProduct)).thenReturn(updatedProduct);

        assertTrue(productService.updateProduct(1, updatedProduct));
    }

    @Test
    public void shouldNotUpdateWhenPassingInvalidProductId() throws ProductException {

        Product newProduct = new Product();
        newProduct.setId(1);
        newProduct.setName("Samsung Galaxy Note10");
        newProduct.setDescription("Samsung Galaxy Note10");
        newProduct.setPrice(1000);

        Product updatedProduct = new Product();
        updatedProduct.setId(1);
        updatedProduct.setName("Samsung Galaxy Note10");
        updatedProduct.setDescription("Samsung Galaxy Note10");
        updatedProduct.setPrice(2000);

        Mockito.lenient().when(productRepository.findById(1)).thenReturn(Optional.of(newProduct));

        newProduct.setPrice(2000);
        Mockito.lenient().when(productRepository.save(newProduct)).thenReturn(updatedProduct);

        assertFalse(productService.updateProduct(2, updatedProduct));
    }

    @Test
    public void shouldDeleteProductWhenPassingValidProductId() throws ProductException {
        Product newProduct = new Product();
        newProduct.setId(1);
        newProduct.setName("Samsung Galaxy Note10");
        newProduct.setDescription("Samsung Galaxy Note10");
        newProduct.setPrice(1000);

        Mockito.lenient().when(productRepository.findById(1)).thenReturn(Optional.of(newProduct));
        Mockito.lenient().doNothing().when(productRepository).deleteById(1);

        assertTrue(productService.deleteProduct(1));
    }

    @Test
    public void shouldNotDeleteProductWhenPassingInvalidProductId() throws ProductException {
        Product newProduct = new Product();
        newProduct.setId(1);
        newProduct.setName("Samsung Galaxy Note10");
        newProduct.setDescription("Samsung Galaxy Note10");
        newProduct.setPrice(1000);

        Mockito.lenient().when(productRepository.findById(1)).thenReturn(Optional.of(newProduct));
        Mockito.lenient().doNothing().when(productRepository).deleteById(1);

        assertFalse(productService.deleteProduct(2));
    }

    @Test
    public void shouldViewProductWhenPassingValidProductId() throws ProductException {
        Product newProduct = new Product();
        newProduct.setId(1);
        newProduct.setName("Samsung Galaxy Note10");
        newProduct.setDescription("Samsung Galaxy Note10");
        newProduct.setPrice(1000);

        Mockito.lenient().when(productRepository.findById(1)).thenReturn(Optional.of(newProduct));

        Product product = productService.viewProduct(1);
        assertNotNull(product);
        assertEquals(1, product.getId());
    }

    @Test
    public void shouldNotViewProductWhenPassingInvalidProductId() throws ProductException {
        Product newProduct = new Product();
        newProduct.setId(1);
        newProduct.setName("Samsung Galaxy Note10");
        newProduct.setDescription("Samsung Galaxy Note10");
        newProduct.setPrice(1000);

        Mockito.lenient().when(productRepository.findById(1)).thenReturn(Optional.of(newProduct));

        Product product = productService.viewProduct(2);
        assertNull(product);
    }

    @Test
    public void shouldListProducts() {
        Product newProduct = new Product();
        newProduct.setId(1);
        newProduct.setName("Samsung Galaxy Note10");
        newProduct.setDescription("Samsung Galaxy Note10");
        newProduct.setPrice(1000);

        Product newProduct2 = new Product();
        newProduct2.setId(2);
        newProduct2.setName("Lenovo Thinkpad E490");
        newProduct2.setDescription("Lenovo Thinkpad E490");
        newProduct2.setPrice(1000);

        Mockito.lenient().when(productRepository.findAll()).thenReturn(List.of(newProduct, newProduct2));

        assertEquals(2, productService.listProducts().size());
    }

    @Test
    public void shouldListProductsWithCategory() {
        Product newProduct = new Product();
        newProduct.setId(1);
        newProduct.setName("Samsung Galaxy Note10");
        newProduct.setDescription("Samsung Galaxy Note10");
        newProduct.setPrice(1000);

        Product newProduct2 = new Product();
        newProduct2.setId(2);
        newProduct2.setName("Lenovo Thinkpad E490");
        newProduct2.setDescription("Lenovo Thinkpad E490");
        newProduct2.setPrice(1000);

        Mockito.lenient().when(productRepository.findByCategoryId(1)).thenReturn(List.of(newProduct, newProduct2));

        assertEquals(2, productService.listProductsWithCategory(1).size());
    }

    @Test
    public void shouldLinkProductsWithCategories() {
        Product newProduct = new Product();
        newProduct.setId(1);
        newProduct.setName("Samsung Galaxy Note10");
        newProduct.setDescription("Samsung Galaxy Note10");
        newProduct.setPrice(1000);

        Category category = new Category();
        category.setId(2);
        category.setName("Mobiles");

        Mockito.lenient().when(productRepository.findById(1)).thenReturn(Optional.of(newProduct));
        ProductWithCategory productWithCategory = new ProductWithCategory();
        productWithCategory.setProductId(newProduct.getId());
        productWithCategory.setCategoryId(category.getId());

        assertTrue(productService.linkProductsWithCategories(productWithCategory));
    }

    @Test
    public void shouldNotLinkProductsWithCategoriesWhenProductNotFound() {
        Product newProduct = new Product();
        newProduct.setId(1);
        newProduct.setName("Samsung Galaxy Note10");
        newProduct.setDescription("Samsung Galaxy Note10");
        newProduct.setPrice(1000);

        Category category = new Category();
        category.setId(2);
        category.setName("Mobiles");

        Mockito.lenient().when(productRepository.findById(1)).thenReturn(Optional.of(newProduct));
        ProductWithCategory productWithCategory = new ProductWithCategory();
        productWithCategory.setProductId(2);
        productWithCategory.setCategoryId(category.getId());

        assertFalse(productService.linkProductsWithCategories(productWithCategory));
    }

    @Test
    public void shouldUpdateProductByField() throws ProductException, NoSuchFieldException, IllegalAccessException {
        Product newProduct = new Product();
        newProduct.setId(1);
        newProduct.setName("Samsung Galaxy Note10");
        newProduct.setDescription("Samsung Galaxy Note10");
        newProduct.setPrice(1000);

        Product updatedProduct = new Product();
        updatedProduct.setId(1);
        updatedProduct.setName("Samsung Galaxy Note10");
        updatedProduct.setDescription("Samsung Galaxy Note10");
        updatedProduct.setPrice(2000);

        Mockito.lenient().when(productRepository.findById(1)).thenReturn(Optional.of(newProduct));
        newProduct.setPrice(2000);
        Mockito.lenient().when(productRepository.save(newProduct)).thenReturn(updatedProduct);

        assertTrue(productService.updateProductByField(1, Map.of("price", 2000)));
    }

    @Test
    public void shouldGetProductsByField() {
        Product newProduct = new Product();
        newProduct.setId(1);
        newProduct.setName("Samsung Galaxy Note10");
        newProduct.setDescription("Samsung Galaxy Note10");
        newProduct.setPrice(1000);

        Product newProduct2 = new Product();
        newProduct2.setId(2);
        newProduct2.setName("Lenovo Thinkpad E490");
        newProduct2.setDescription("Lenovo Thinkpad E490");
        newProduct2.setPrice(1000.0);

        Mockito.lenient().when(productRepository.findByPrice(1000)).thenReturn(List.of(newProduct, newProduct2));

        assertEquals(2, productService.getProductsByField(Map.of("price", 1000.0)).size());
    }

    @Test
    public void shouldNotUpdateProductByFieldWhenProductNotFound() throws ProductException, NoSuchFieldException, IllegalAccessException {
        Product newProduct = new Product();
        newProduct.setId(1);
        newProduct.setName("Samsung Galaxy Note10");
        newProduct.setDescription("Samsung Galaxy Note10");
        newProduct.setPrice(1000);

        Product updatedProduct = new Product();
        updatedProduct.setId(1);
        updatedProduct.setName("Samsung Galaxy Note10");
        updatedProduct.setDescription("Samsung Galaxy Note10");
        updatedProduct.setPrice(2000);

        Mockito.lenient().when(productRepository.findById(1)).thenReturn(Optional.of(newProduct));
        newProduct.setPrice(2000);
        Mockito.lenient().when(productRepository.save(newProduct)).thenReturn(updatedProduct);

        assertFalse(productService.updateProductByField(2, Map.of("price", 2000)));
    }

    @Test
    public void shouldThrowExceptionWhenInvalidFieldsPassedToUpdateProductByField()  {
        Product newProduct = new Product();
        newProduct.setId(1);
        newProduct.setName("Samsung Galaxy Note10");
        newProduct.setDescription("Samsung Galaxy Note10");
        newProduct.setPrice(1000);

        Product updatedProduct = new Product();
        updatedProduct.setId(1);
        updatedProduct.setName("Samsung Galaxy Note10");
        updatedProduct.setDescription("Samsung Galaxy Note10");
        updatedProduct.setPrice(2000);

        Mockito.lenient().when(productRepository.findById(1)).thenReturn(Optional.of(newProduct));
        newProduct.setPrice(2000);
        Mockito.lenient().when(productRepository.save(newProduct)).thenReturn(updatedProduct);

        assertThrows(NoSuchFieldException.class,() -> productService.updateProductByField(1, Map.of("invalidField", 2000)));
    }

    @Test
    public void shouldGetProductsByName()
    {
        Product newProduct = new Product();
        newProduct.setId(1);
        newProduct.setName("Samsung Galaxy Note10");
        newProduct.setDescription("Samsung Galaxy Note10");
        newProduct.setPrice(1000);

        Product newProduct2 = new Product();
        newProduct2.setId(2);
        newProduct2.setName("Samsung Thinkpad E490");
        newProduct2.setDescription("Lenovo Thinkpad E490");
        newProduct2.setPrice(1000);

        Mockito.lenient().when(productRepository.findByNameContaining("Samsung")).thenReturn(List.of(newProduct));

        assertEquals(1, productService.getProductsByField(Map.of("name", "Samsung")).size());
    }

    @Test
    public void shouldGetProductsByDescription()
    {
        Product newProduct = new Product();
        newProduct.setId(1);
        newProduct.setName("Samsung Galaxy Note10");
        newProduct.setDescription("Samsung Galaxy Note10");
        newProduct.setPrice(1000);

        Product newProduct2 = new Product();
        newProduct2.setId(2);
        newProduct2.setName("Lenovo Thinkpad E490");
        newProduct2.setDescription("Samsung Thinkpad E490");
        newProduct2.setPrice(1000);

        Mockito.lenient().when(productRepository.findByDescriptionContaining("Samsung")).thenReturn(List.of(newProduct));

        assertEquals(1, productService.getProductsByField(Map.of("description", "Samsung")).size());
    }

    @Test
    public void shouldGetProductsByCategory()
    {
        Product newProduct = new Product();
        newProduct.setId(1);
        newProduct.setName("Samsung Galaxy Note10");
        newProduct.setDescription("Samsung Galaxy Note10");
        newProduct.setPrice(1000);
        newProduct.setCategoryId(1);

        Product newProduct2 = new Product();
        newProduct2.setId(2);
        newProduct2.setName("Lenovo Thinkpad E490");
        newProduct2.setDescription("Lenovo Thinkpad E490");
        newProduct2.setPrice(1000);
        newProduct2.setCategoryId(2);

        Mockito.lenient().when(productRepository.findByCategoryId(1)).thenReturn(List.of(newProduct));

        assertEquals(1, productService.getProductsByField(Map.of("categoryId", 1)).size());
    }

    @Test
    public void shouldNotGetProductsByInvalidField()
    {
        Product newProduct = new Product();
        newProduct.setId(1);
        newProduct.setName("Samsung Galaxy Note10");
        newProduct.setDescription("Samsung Galaxy Note10");
        newProduct.setPrice(1000);
        newProduct.setCategoryId(1);

        Product newProduct2 = new Product();
        newProduct2.setId(2);
        newProduct2.setName("Lenovo Thinkpad E490");
        newProduct2.setDescription("Lenovo Thinkpad E490");
        newProduct2.setPrice(1000);
        newProduct2.setCategoryId(2);

        Mockito.lenient().when(productRepository.findByCategoryId(1)).thenReturn(List.of(newProduct));

        assertEquals(null, productService.getProductsByField(Map.of("raadd", 2)));
    }
}

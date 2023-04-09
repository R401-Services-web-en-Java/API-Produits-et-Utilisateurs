package fr.univamu.iut.apiproduitsetutilisateurs.model;

import fr.univamu.iut.apiproduitsetutilisateurs.domain.Products;
import org.apache.log4j.BasicConfigurator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ProductsRepositoryTest {

    private ProductsRepositoryMariadb productsRepo;
    private Connection dbConnection;

    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        BasicConfigurator.configure();
        String infoConnection = "jdbc:mariadb://mysql-fred.alwaysdata.net/fred_api_users_products_test";
        String user = "fred_api_project";
        String pwd = "Frederic13!";
        Class.forName("org.mariadb.jdbc.Driver");
        dbConnection = DriverManager.getConnection(infoConnection, user, pwd);
        productsRepo = new ProductsRepositoryMariadb(infoConnection, user, pwd);
    }

    @After
    public void tearDown() throws SQLException {
        productsRepo.close();
        dbConnection.close();
    }

    @Test
    public void testGetAllProducts() {
        ArrayList<Products> productsList = productsRepo.getAllProducts();
        assertFalse(productsList.isEmpty());
    }

    @Test
    public void testGetProduct() {
        String productName = "Beurre";
        Products product = productsRepo.getProduct(productName);
        assertEquals(productName, product.getName());
    }

    @Test
    public void testAddProduct() {
        Products product = new Products("NewProduct", 10, 9.99f, "pcs");
        productsRepo.addProduct(product);
        Products addedProduct = productsRepo.getProduct(product.getName());
        assertNotNull(addedProduct);
        assertEquals(product.getName(), addedProduct.getName());
        productsRepo.deleteProduct(product.getName());
    }

    @Test
    public void testUpdateProduct() {
        String productName = "Viande";
        Products product = productsRepo.getProduct(productName);
        assertNotNull(product);

        product.setQuantity_stock(20);
        product.setPrice(19.99f);
        productsRepo.updateProduct(product);

        Products updatedProduct = productsRepo.getProduct(product.getName());
        assertNotNull(updatedProduct);
        assertEquals(product.getQuantity_stock(), updatedProduct.getQuantity_stock());
        assertEquals(product.getPrice(), updatedProduct.getPrice(), 0.001);

        product.setQuantity_stock(10); // reset for next test
        product.setPrice(9.99f); // reset for next test
        productsRepo.updateProduct(product);
    }

    @Test
    public void testDeleteProduct() {
        String productName = "Product1";
        Products productAdded = new Products("Product1", 10, 9.99f, "pcs");
        productsRepo.addProduct(productAdded); // add product to delete it after
        Products product = productsRepo.getProduct(productName);
        assertNotNull(product);
        productsRepo.deleteProduct(productName);

        Products deletedProduct = productsRepo.getProduct(productName);
        assertNull(deletedProduct);
    }
}
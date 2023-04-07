package fr.univamu.iut.apiproduitsetutilisateurs.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProductsTest {
    private Products product;

    @Before
    public void setUp() {
        product = new Products("Chocolat", 10, 9.99f, "kg");
    }

    @Test
    public void testGetName() {
        Assert.assertEquals("Chocolat", product.getName());
    }

    @Test
    public void testSetName() {
        product.setName("Chocolat Noir");
        Assert.assertEquals("Chocolat Noir", product.getName());
    }

    @Test
    public void testGetQuantityStock() {
        Assert.assertEquals(10, product.getQuantity_stock());
    }

    @Test
    public void testSetQuantityStock() {
        product.setQuantity_stock(20);
        Assert.assertEquals(20, product.getQuantity_stock());
    }

    @Test
    public void testGetPrice() {
        Assert.assertEquals(9.99f, product.getPrice(), 0.001);
    }

    @Test
    public void testSetPrice() {
        product.setPrice(14.99f);
        Assert.assertEquals(14.99f, product.getPrice(), 0.001);
    }

    @Test
    public void testGetUnit() {
        Assert.assertEquals("kg", product.getUnit());
    }

    @Test
    public void testSetUnit() {
        product.setUnit("lbs");
        Assert.assertEquals("lbs", product.getUnit());
    }
}

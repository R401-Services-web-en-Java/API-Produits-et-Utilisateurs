package fr.univamu.iut.apiproduitsetutilisateurs.services;

import fr.univamu.iut.apiproduitsetutilisateurs.domain.Products;
import fr.univamu.iut.apiproduitsetutilisateurs.model.ProductsRepositoryInterface;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class ProductsServiceTest {
    private ProductsService productsService;
    private ProductsRepositoryInterface productsRepo;

    @Before
    public void setUp() {
        productsRepo = mock(ProductsRepositoryInterface.class);
        productsService = new ProductsService(productsRepo);
    }

    @Test
    public void testAddProduct() {
        Products product = new Products("Product1", 10, 9.99f, "kg");
        when(productsRepo.getProduct(product.getName())).thenReturn(null);
        productsService.addProduct(product);
        verify(productsRepo, times(1)).addProduct(product);
    }

    @Test(expected = RuntimeException.class)
    public void testAddProductAlreadyExists() {
        Products product = new Products("Product1", 10, 9.99f, "kg");
        when(productsRepo.getProduct(product.getName())).thenReturn(product);
        productsService.addProduct(product);
    }

    @Test
    public void testDeleteProduct() {
        String productName = "Product1";
        when(productsRepo.getProduct(productName)).thenReturn(new Products("Product1", 10, 9.99f, "kg"));
        productsService.deleteProduct(productName);
        verify(productsRepo, times(1)).deleteProduct(productName);
    }

    @Test
    public void testUpdateProduct() {
        Products product = new Products("Product1", 10, 9.99f, "kg");
        when(productsRepo.getProduct(product.getName())).thenReturn(product);
        productsService.updateProduct(product);
        verify(productsRepo).updateProduct(product);
    }
}

package fr.univamu.iut.apiproduitsetutilisateurs.services;

import fr.univamu.iut.apiproduitsetutilisateurs.domain.Products;
import fr.univamu.iut.apiproduitsetutilisateurs.model.ProductsRepositoryInterface;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;

/**
 * The service class that provides CRUD (Create, Read, Update, Delete) operations
 * for Products.
 */
public class ProductsService {
    protected ProductsRepositoryInterface productsRepo;

    /**
     * Constructor for ProductsService.
     *
     * @param productsRepo The repository interface for Products.
     */
    public ProductsService(ProductsRepositoryInterface productsRepo) {
        this.productsRepo = productsRepo;
    }

    /**
     * Retrieves all products as a JSON string.
     *
     * @return A JSON string containing all products.
     */
    public String getAllProducts() {
        ArrayList<Products> allProducts = productsRepo.getAllProducts();

        String result = null;
        try (Jsonb jsonb = JsonbBuilder.create()) {
            result = jsonb.toJson(allProducts);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return result;
    }

    /**
     * Retrieves a product as a JSON string by its name.
     *
     * @param name The name of the product.
     * @return A JSON string containing the product information.
     */
    public String getProductJSON(String name) {
        String result = null;
        Products myUser = productsRepo.getProduct(name);

        if (myUser != null) {
            try (Jsonb jsonb = JsonbBuilder.create()) {
                result = jsonb.toJson(myUser);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return result;
    }

    /**
     * Adds a new product.
     *
     * @param product The product object to be added.
     * @throws RuntimeException If the product already exists.
     */
    public void addProduct(Products product) {
        if (productsRepo.getProduct(product.getName()) != null) {
            throw new RuntimeException("Product already exists");
        }
        productsRepo.addProduct(product);
    }

    /**
     * Deletes a product by its name.
     *
     * @param name The name of the product to be deleted.
     * @throws NotFoundException If the product does not exist.
     */
    public void deleteProduct(String name) {
        if (productsRepo.getProduct(name) == null) {
            throw new NotFoundException();
        }
        productsRepo.deleteProduct(name);
    }

    /**
     * Updates an existing product.
     *
     * @param product The product object to be updated.
     * @throws NotFoundException If the product does not exist.
     */
    public void updateProduct(Products product) {
        if (productsRepo.getProduct(product.getName()) == null) {
            throw new NotFoundException();
        }
        productsRepo.updateProduct(product);
    }
}

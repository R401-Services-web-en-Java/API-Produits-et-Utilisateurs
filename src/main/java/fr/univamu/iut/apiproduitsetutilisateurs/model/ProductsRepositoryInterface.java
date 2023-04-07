package fr.univamu.iut.apiproduitsetutilisateurs.model;

import fr.univamu.iut.apiproduitsetutilisateurs.domain.Products;

import java.util.ArrayList;

/**
 * Interface for ProductsRepository that provides methods to interact with products data.
 */
public interface ProductsRepositoryInterface {
    /**
     * Get all products from the repository.
     *
     * @return An ArrayList of Products containing all products in the repository.
     */
    public ArrayList<Products> getAllProducts();

    /**
     * Get a product from the repository by its name.
     *
     * @param name The name of the product to retrieve.
     * @return The Product object corresponding to the given name, or null if not found.
     */
    public Products getProduct(String name);

    /**
     * Add a product to the repository.
     *
     * @param product The Product object to add.
     */
    public void addProduct(Products product);

    /**
     * Delete a product from the repository by its name.
     *
     * @param name The name of the product to delete.
     */
    public void deleteProduct(String name);

    /**
     * Update a product in the repository.
     *
     * @param product The Product object to update.
     */
    public void updateProduct(Products product);

    /**
     * Close the repository.
     */
    public void close();
}

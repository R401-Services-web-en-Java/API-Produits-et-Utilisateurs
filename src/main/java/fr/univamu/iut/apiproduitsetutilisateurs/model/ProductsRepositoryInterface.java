package fr.univamu.iut.apiproduitsetutilisateurs.model;

import fr.univamu.iut.apiproduitsetutilisateurs.domain.Products;

import java.util.ArrayList;

public interface ProductsRepositoryInterface {

    public ArrayList<Products> getAllProducts();

    public Products getProduct(String name);

    public void addProduct(Products product);

    public void deleteProduct(String name);

    public void updateProduct(Products product);

    public void close();
}

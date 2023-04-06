package fr.univamu.iut.apiproduitsetutilisateurs.model;

import fr.univamu.iut.apiproduitsetutilisateurs.domain.Products;
import fr.univamu.iut.apiproduitsetutilisateurs.domain.User;

import java.util.ArrayList;

public interface UserProductsRepositoryInterface {
    public User getUser(String username);

    public ArrayList<User> getAllUsers() ;

    public void addUser(User user);

    public void deleteUser(String username);

    public void updateUser(User user);

    public void close();

    public ArrayList<Products> getAllProducts();

    public Products getProduct(String name);

    public void addProduct(Products product);

    public void deleteProduct(String name);

    public void updateProduct(Products product);
}

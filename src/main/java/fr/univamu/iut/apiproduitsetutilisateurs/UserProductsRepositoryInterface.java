package fr.univamu.iut.apiproduitsetutilisateurs;

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

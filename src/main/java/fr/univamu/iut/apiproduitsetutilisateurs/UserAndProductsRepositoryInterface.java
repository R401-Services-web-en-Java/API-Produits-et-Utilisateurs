package fr.univamu.iut.apiproduitsetutilisateurs;

import java.util.ArrayList;

public interface UserAndProductsRepositoryInterface {
    public User getUser(String username);

    public ArrayList<User> getAllUsers() ;

    public void addUser(User user);

    public void close();
}

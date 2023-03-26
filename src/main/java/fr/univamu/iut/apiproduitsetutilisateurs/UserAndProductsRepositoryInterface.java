package fr.univamu.iut.apiproduitsetutilisateurs;

import java.util.ArrayList;

public interface UserAndProductsRepositoryInterface {
    public User getUser(String id);

    public ArrayList<User> getAllUsers() ;

    public void close();
}

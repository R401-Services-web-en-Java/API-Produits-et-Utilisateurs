package fr.univamu.iut.apiproduitsetutilisateurs.model;

import fr.univamu.iut.apiproduitsetutilisateurs.domain.User;

import java.util.ArrayList;

public interface UserRepositoryInterface {
    public User getUser(String username);

    public ArrayList<User> getAllUsers() ;

    public void addUser(User user);

    public void deleteUser(String username);

    public void updateUser(User user);

    public void close();
}

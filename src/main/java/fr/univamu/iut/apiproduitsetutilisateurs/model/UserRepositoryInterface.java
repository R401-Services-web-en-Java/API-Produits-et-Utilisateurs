package fr.univamu.iut.apiproduitsetutilisateurs.model;

import fr.univamu.iut.apiproduitsetutilisateurs.domain.User;

import java.util.ArrayList;

/**
 * Interface for UserRepository that provides methods to interact with user data.
 */
public interface UserRepositoryInterface {

    /**
     * Retrieves a user by username.
     *
     * @param username The username of the user to retrieve.
     * @return The user with the given username, or null if not found.
     */
    public User getUser(String username);

    /**
     * Retrieves all users.
     *
     * @return An ArrayList of all users.
     */
    public ArrayList<User> getAllUsers();

    /**
     * Adds a user.
     *
     * @param user The user to add.
     */
    public void addUser(User user);

    /**
     * Deletes a user by username.
     *
     * @param username The username of the user to delete.
     */
    public void deleteUser(String username);

    /**
     * Updates a user.
     *
     * @param user The updated user object.
     */
    public void updateUser(User user);

    /**
     * Closes the connection to the user repository.
     */
    public void close();
}

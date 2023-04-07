package fr.univamu.iut.apiproduitsetutilisateurs.services;

import fr.univamu.iut.apiproduitsetutilisateurs.domain.User;
import fr.univamu.iut.apiproduitsetutilisateurs.model.UserRepositoryInterface;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;

/**
 * The service class that provides user management functionality.
 * It interacts with a user repository to perform CRUD operations on user data.
 */
public class UserService {

    protected UserRepositoryInterface userProductsRepo;

    /**
     * Constructor for UserService.
     *
     * @param userProductsRepo The repository interface for user and products data.
     */
    public UserService(UserRepositoryInterface userProductsRepo) {
        this.userProductsRepo = userProductsRepo;
    }

    /**
     * Retrieves all users as JSON, but with sensitive information (e.g., email and password) removed.
     *
     * @return A JSON string containing all users with sensitive information removed.
     */
    public String getAllUsersJSON() {

        ArrayList<User> allUsers = userProductsRepo.getAllUsers();

        for (User currentUser : allUsers) {
            currentUser.setMail("");
            currentUser.setPassword("");
        }

        String result = null;
        try (Jsonb jsonb = JsonbBuilder.create()) {
            result = jsonb.toJson(allUsers);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return result;
    }

    /**
     * Retrieves a user by username as JSON, but with sensitive information (e.g., email and password) removed.
     *
     * @param username The username of the user to be retrieved.
     * @return A JSON string containing the user with sensitive information removed, or null if user not found.
     */
    public String getUserJSON(String username) {
        String result = null;
        User myUser = userProductsRepo.getUser(username);

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
     * Adds a new user to the repository.
     *
     * @param user The user to be added.
     * @throws RuntimeException If user already exists.
     */
    public void addUser(User user) {
        if (userProductsRepo.getUser(user.getUsername()) != null) {
            throw new RuntimeException("User already exists");
        }
        userProductsRepo.addUser(user);
    }

    /**
     * Deletes a user from the repository by username.
     *
     * @param username The username of the user to be deleted.
     * @throws NotFoundException If user not found.
     */
    public void deleteUser(String username) {
        if (userProductsRepo.getUser(username) == null) {
            throw new NotFoundException();
        }
        userProductsRepo.deleteUser(username);
    }

    /**
     * Updates an existing user in the repository.
     *
     * @param user The user to be updated.
     * @throws NotFoundException If user not found.
     */
    public void updateUser(User user) {
        if (userProductsRepo.getUser(user.getUsername()) == null) {
            throw new NotFoundException();
        }
        userProductsRepo.updateUser(user);
    }
}


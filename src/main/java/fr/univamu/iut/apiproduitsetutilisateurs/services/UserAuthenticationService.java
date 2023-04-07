package fr.univamu.iut.apiproduitsetutilisateurs.services;

import fr.univamu.iut.apiproduitsetutilisateurs.domain.User;
import fr.univamu.iut.apiproduitsetutilisateurs.model.UserRepositoryInterface;

/**
 * The service class that provides user authentication functionality.
 * It validates user credentials against a user repository.
 */
public class UserAuthenticationService {

    protected UserRepositoryInterface userAndProductsRepo;

    /**
     * Constructor for UserAuthenticationService.
     *
     * @param userAndProductsRepo The repository interface for user and products data.
     */
    public UserAuthenticationService(UserRepositoryInterface userAndProductsRepo) {
        this.userAndProductsRepo = userAndProductsRepo;
    }

    /**
     * Validates if the provided username and password match a user in the repository.
     *
     * @param username The username to be validated.
     * @param password The password to be validated.
     * @return True if the username and password are valid, false otherwise.
     */
    public boolean isValidUser(String username, String password) {
        User currentUser = userAndProductsRepo.getUser(username);
        if (currentUser == null) {
            return false;
        }
        if (!currentUser.getPassword().equals(password)) {
            return false;
        }
        return true;
    }
}



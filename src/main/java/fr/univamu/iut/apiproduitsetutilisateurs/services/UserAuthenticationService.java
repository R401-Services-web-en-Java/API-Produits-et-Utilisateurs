package fr.univamu.iut.apiproduitsetutilisateurs.services;

import fr.univamu.iut.apiproduitsetutilisateurs.domain.User;
import fr.univamu.iut.apiproduitsetutilisateurs.model.UserRepositoryInterface;

public class UserAuthenticationService {

    protected UserRepositoryInterface userAndProductsRepo ;

    public UserAuthenticationService(UserRepositoryInterface userAndProductsRepo) {
        this.userAndProductsRepo = userAndProductsRepo;
    }

    public boolean isValidUser( String username, String password){
        User currentUser = userAndProductsRepo.getUser(username);
        if( currentUser == null ) {
            return false;
        }
        if( ! currentUser.getPassword().equals(password) ) {
            return false;
        }
        return true;
    }
}


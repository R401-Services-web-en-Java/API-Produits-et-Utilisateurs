package fr.univamu.iut.apiproduitsetutilisateurs;

public class UserAuthenticationService {

    protected UserAndProductsRepositoryInterface userAndProductsRepo;

    public UserAuthenticationService(UserAndProductsRepositoryInterface userAndProductsRepo) {
        this.userAndProductsRepo = userAndProductsRepo;
    }

    public boolean isValidUser( String username, String password){

        User currentUser = userAndProductsRepo.getUser(username);

        if( currentUser == null )
            return false;

        if( ! currentUser.password.equals(password) )
            return false;

        return true;
    }
}

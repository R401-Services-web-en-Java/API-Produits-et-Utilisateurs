package fr.univamu.iut.apiproduitsetutilisateurs;

public class UserAuthenticationService {

    protected UserAndProductsRepositoryInterface userRepo ;

    public UserAuthenticationService(UserAndProductsRepositoryInterface userRepo) {
        this.userRepo = userRepo;
    }

    public boolean isValidUser( String username, String pwd){

        User currentUser = userRepo.getUser(username);

        // si l'utilisateur n'a pas été trouvé
        if( currentUser == null )
            return false;

        // si le mot de passe n'est pas correcte
        if( ! currentUser.password.equals(pwd) )
            return false;

        return true;
    }
}


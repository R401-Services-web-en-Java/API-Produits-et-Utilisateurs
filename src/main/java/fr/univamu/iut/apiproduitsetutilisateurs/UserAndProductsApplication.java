package fr.univamu.iut.apiproduitsetutilisateurs;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
@ApplicationScoped
public class UserAndProductsApplication extends Application {

    @Produces
    private UserAndProductsRepositoryInterface openDbConnection(){
        UserAndProductsRepositoryMariadb db = null;

        try{
            db = new UserAndProductsRepositoryMariadb("jdbc:mariadb://mysql-fred.alwaysdata.net/fred_library_db", "fred_library", "Frederic13!");
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
        return db;
    }

    private void closeDbConnection(@Disposes UserAndProductsRepositoryInterface userRepo ) {
        userRepo.close();
    }

    @Override
    public Set<Object> getSingletons() {
        Set<Object> set = new HashSet<>();

        // Création de la connection à la base de données et initialisation du service associé
        UserAndProductsService service = null ;
        try{
            UserAndProductsRepositoryMariadb db = new UserAndProductsRepositoryMariadb("jdbc:mariadb://mysql-fred.alwaysdata.net/fred_library_db", "fred_library", "Frederic13!");
            service = new UserAndProductsService(db);
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }

        // Création de la ressource en lui passant paramètre les services à exécuter en fonction
        // des différents endpoints proposés (i.e. requêtes HTTP acceptées)
        set.add(new UserResource(service));
        set.add(new AuthFilter());

        return set;
    }
}

